package com.gsww.uids.manager.user.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.entity.DegreeEnum;
import com.gsww.common.entity.NationalityEnum;
import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

import net.sf.json.JSONObject;

/**
 * 用户管理控制类
 * 
 * @author jinwei
 * @author taolm 修改
 *
 */

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OrganizationService orgService;
	
	/**
	 * 展示
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Model model, int type) {
		
		model.addAttribute("type", type);
		
		return "manager/user/" + UserTypeEnum.valueOf(type) + "/index";
	}
	
	/**
	 * 获取列表信息
	 * 
	 * @param model
	 * @param name
	 * @param identity
	 * @param mobile
	 * @param corporateName
	 * @param currentPage
	 * @param size
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(Model model, int type, String name, String identity, String mobile, String corporateName,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="rows", defaultValue="10") int rows) {
		
		PageObject<User> pageList = userService.findPage(type, name, identity, mobile, corporateName, page, rows);
		
		// 给敏感信息增加掩码
		userService.addMaskToSeceretInfo(pageList);
		
		return pageList.toJSONString();
	}
	
	/**
	 * 查询当前用户能否操作某些用户
	 * 
	 * @param userIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAdminPermission", method = RequestMethod.POST)
	@ResponseBody
	public String checkAdminPermission(String userIds) {
		
		JSONObject resultJson = new JSONObject();
		
		// 如果是超管，则有权限
		Subject subject = SecurityUtils.getSubject();
		if ( subject.isPermitted(WebConstants.ROLE_KEY_ADMIN) ) {
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("info", "允许操作");		
			return resultJson.toString();
		}
		
		// 如果不是超管，就要看是否有权限
		for ( String userId : userIds.split(",") ) {
			User user = userService.findById(userId);
			
			// 如果没有所属机构，说明是公众用户，则没有权限
			Organization org = user.getOrg();
			if ( org == null ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "对不起，您没有权限操作这些记录！");
				return resultJson.toString(); 
			}
			
			// 如果是政府用户，则继续检查是否有权限访问			
			if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "对不起，您没有权限操作这些记录！");
				return resultJson.toString();
			}
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "允许操作");		
		return resultJson.toString();
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param model
	 * @param type
	 * @param userId
	 * @param isEditSelf
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/newOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, int type, String userId, int isEditSelf) {
		
		// 用户基本信息
		User info = userService.findById(userId);
		if ( StringUtil.isBlank(info.getUuid()) ) {
			info.setType(type);
		}
		model.addAttribute("info", info);
		model.addAttribute("isEditSelf", isEditSelf);
		
		// 自然人用户才有的属性
		if ( type == UserTypeEnum.NATURAL.getNumber() ) {
			Area birthArea = info.getBirthArea();
			if ( birthArea == null ) {
				birthArea = areaService.generateAreaOfLevel(4);
			}
			model.addAttribute("birthArea", birthArea);
			
			Area liveArea = info.getLiveArea();
			if ( liveArea == null ) {
				liveArea = areaService.generateAreaOfLevel(4);
			}
			model.addAttribute("liveArea", liveArea);
			
			// 查找所有省级区域
			List<Area> provinces = areaService.findAllByLevel(2);
			model.addAttribute("provinces", provinces);
			
			// 学历
			model.addAttribute("degrees", DegreeEnum.getValueArray());
			
			// 机构
			Organization org = info.getOrg();
			if ( org == null ) {
				org = new Organization();
			}
			model.addAttribute("org", org);
		}
		
		// 用户详细信息
		UserDetail detail = info.getDetail();
		if ( detail == null ) {
			detail = new UserDetail();
		}
		model.addAttribute("detail", detail);
		
		// 民族
		model.addAttribute("nationalities", NationalityEnum.getValueArray());
		
		return "manager/user/" + UserTypeEnum.valueOf(type) + "/newOrEdit";
	}
	
	/**
	 * 保存、更新
	 * 
	 * @param model
	 * @param info
	 * @param areaId
	 * @return
	 */
	@SystemLog(description = "用户", module = OperationLog.USER_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(Model model, String operate, String userId, String detailId, User user, UserDetail detail, 
			String birthAreaId, String liveAreaId, String orgId) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 自然人用户才有的属性
		if ( user.getType() == UserTypeEnum.NATURAL.getNumber() ) {
			user.setBirthArea(areaService.findById(birthAreaId));
			user.setLiveArea(areaService.findById(liveAreaId));
			
			// 所属机构
			if ( StringUtil.isNotBlank(orgId) ) {
				Organization org = orgService.findById(orgId);
				user.setOrg(org);
				
				// 验证权限
				if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", String.format("对不起，您没有权限使用机构【%】", org.getShortName()));
					return resultJson.toString(); 
				}
			}
		}
		
		// 处理详细信息
		detail.setUuid(detailId);
		
		// 处理基本信息
		user.setUuid(userId);
		user.setDetail(detail);
		
		// 验证唯一性
		StringBuilder errInfo = new StringBuilder();
		if ( !userService.checkUnique(user, errInfo) ) {
			resultJson.put("state", 0);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
			
		// 保存
		userService.saveOrUpdateCompleteUser(user, detail);
		
		// 返回保存成功结果
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@SystemLog(description = "用户", module = OperationLog.USER_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		Subject subject = SecurityUtils.getSubject();
		if ( !subject.isPermitted(WebConstants.ROLE_KEY_ADMIN) ) {
			// 如果不是超管，就要看是否有权限
			for ( String userId : ids.split(",") ) {
				User user = userService.findById(userId);
				
				// 如果没有所属机构，说明是公众用户，则没有权限
				Organization org = user.getOrg();
				if ( org == null ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限删除这些记录！");
					return resultJson.toString(); 
				}
				
				// 如果是政府用户，则继续检查是否有权限访问			
				if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限删除这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !userService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		userService.delete(ids);
		
		// 返回删除成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
}
