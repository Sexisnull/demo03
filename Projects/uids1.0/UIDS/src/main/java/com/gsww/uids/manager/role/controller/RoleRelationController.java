package com.gsww.uids.manager.role.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.role.service.RoleService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;

import net.sf.json.JSONObject;

/**
 * 用户或账号角色的控制类
 * 
 * @author taolm
 *
 */
@Controller
@RequestMapping("/relation")
public class RoleRelationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleRelationService roleRelationService;
	
	/**
	 * 某用户的角色展示
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Model model, String userId) throws Exception {
		
		model.addAttribute("userId", userId);
		User user = userService.findById(userId);
		
		// 所有角色
		List<Role> roles = null;
		// 如果是政府用户，可以选择所有角色
		if ( UserTypeEnum.NATURAL.getNumber() == user.getType() && user.getOrg() != null ) {
			roles = roleService.findAll();
		} else { // 如果是法人或者公众用户，则不能拥有后台管理员角色
			roles = roleService.findAllOfNonGovernment();
		}
		model.addAttribute("roles", roles);	
		
		return "manager/user/relation/index";
	}
	
	/**
	 * 获取用户所有角色数据：分页
	 * 
	 * @param model
	 * @param userId
	 * @param currentPage
	 * @param size
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(Model model, String userId,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="rows", defaultValue="10") int rows) {		
		PageObject<RoleRelation> pageList = roleRelationService.findPage(userId, page, rows);	
		return pageList.toJSONString();
	}
		
	/**
	 * 查询当前用户能否操作用户模块中的机构，即作为用户管理员能能否访问某个机构
	 * 
	 * @param userIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAdminPermission", method = RequestMethod.POST)
	@ResponseBody
	public String checkAdminPermission(String orgId) {
		
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		Organization org = orgService.findById(orgId);
		if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "对不起，您没有权限使用此机构！");
			return resultJson.toString();
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "允许使用");		
		return resultJson.toString();
	}
	
	/**
	 * 保存用户角色
	 * 
	 * @param userId
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public String save(String userId, String orgId, String roleId) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		Organization org = orgService.findById(orgId);
		if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", String.format("对不起，您没有权限使用机构【%s】！", org.getShortName()));
			return resultJson.toString();
		}
		
		// 构建用户角色
		RoleRelation relation = new RoleRelation();
		relation.setUser(userService.findById(userId));				
		relation.setOrg(org);
		relation.setRole(roleService.findById(roleId));
		
		// 查重
		StringBuilder errInfo = new StringBuilder();
		if ( !roleRelationService.checkUnique(relation, errInfo) ) {
			resultJson.put("state", 0);
			resultJson.put("info", errInfo.toString() + "， 请重新选择！");
			return resultJson.toString();
		}
		
		// 保存
		roleRelationService.saveOrUpdate(relation);
		
		// 返回保存成功结果
		resultJson.put("state", 1);
		resultJson.put("info", "保存角色关系成功！");
		return resultJson.toString();
	}
	
	/**
	 * 删除用户角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(ids) ) {
			for ( String relationId : ids.split(",") ) {
				RoleRelation relation = roleRelationService.findById(relationId);
				Organization org = relation.getOrg();
				if ( !orgService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_USER_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限删除这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !roleRelationService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		roleRelationService.delete(ids);
		
		// 返回删除成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
}
