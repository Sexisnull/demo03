package com.gsww.uids.manager.org.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.service.OrganizationGroupService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.system.controller.SystemLog;

/**
 * 机构分组控制类
 * @author jinwei
 *
 */

@Controller
@RequestMapping("/org/group")
public class OrgGroupController {
	
	@Autowired
	private OrganizationGroupService organizationGroupService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 机构分组
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getGroup(Model model) {
		
		return "manager/org/group/index";
	}
	
	/**
	 * 机构管理树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/orgTree/detailData", method = RequestMethod.POST)
	@ResponseBody
	public String detailData(String pId, String code) {
		
		List<Organization>	list = null;
		
		if(StringUtil.isBlank(pId)){
			list = new ArrayList<Organization>();
			list.add(organizationService.getRootOrganization());
		}else{
			list = organizationService.findChild(pId);
		}
		
		JSONArray result = new JSONArray();
		JSONObject json = null;
	
		for(Organization org : list){
			json = new JSONObject();
			json.put("id", org.getUuid());
			json.put("pId", (org.getParent() == null) ? null : org.getParent().getUuid());
			json.put("name", org.getShortName());
			json.put("isParent", (org.getParentIs() == 1) ? true : false);
			json.accumulate("isDisabled", (org.getParent() == null) ? true : false);
			result.put(json);
		}

		return result.toString();
	}
	
	/**
	 * 分组list数据
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(String groupName, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows) {
	
		return organizationGroupService.findPage(page, rows, groupName);
	}
	
	/**
	 * 分组成员list数据
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/groupData", method = RequestMethod.POST)
	@ResponseBody
	public String getGroupData(String id, String orgName) {
	
		return organizationGroupService.findGroupOrg(id, orgName);
	}
	
	/**
	 * 按名称检索机构
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/searchOrg", method = RequestMethod.POST)
	@ResponseBody
	public String searchOrg(String orgName, String pId) {
		List<Organization> orgList = new ArrayList<Organization>();
		if ( StringUtil.isNotBlank(orgName) ){
			orgList = organizationService.findListByFullName(orgName);
		}
		if ( StringUtil.isNotBlank(pId) ){
			orgList = organizationService.findChild(pId);
		}
		JSONArray result = new JSONArray();
		JSONObject json = null;
		for(Organization org : orgList){
			json = new JSONObject();
			json.put("uuid", org.getUuid());
			json.put("shortName", org.getShortName());
			result.put(json);
		}
		return result.toString();
	}
	
	/**
	 * 删除分组成员list数据
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/groupOrgDelete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGroupOrg(String ids, String groupId) {
		Organization org = null;
		OrganizationGroup group = organizationGroupService.findById(groupId);
		//给机构增加分组
		for (String id : ids.split(",")) {
			org = organizationService.findById(id);
			org.getGroupSet().remove(group);
			organizationService.saveOrUpdate(org);
		}
		//返回结果
		JSONObject resultJson = new JSONObject();
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "删除成功！");
		return resultJson.toString();
	}
	
	/**
	 * 新增或者编辑
	 * @param model
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/newOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, String id) {
		OrganizationGroup info = organizationGroupService.findById(id);
		model.addAttribute("info", info);
		return "manager/org/group/newOrEdit";
	}
	
	/**
	 * 保存机构分组成员
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/saveGroupOrg",method = RequestMethod.POST)
	@ResponseBody
	public String save(String groupId, String orgIds) {
		
		return organizationGroupService.saveGroupOrg(orgIds, groupId);
	}
	
	/**
	 * 编辑分组成员
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/addOrg", method = RequestMethod.GET)
	public String addOrg(Model model, String id) {
		OrganizationGroup info = organizationGroupService.findById(id);
		model.addAttribute("info", info);
		return "manager/org/group/addOrgToGroup";
	}
	
	/**
	 * 保存分组
	 */
	@SystemLog(description = "机构分组", module = OperationLog.ORGANIZATION_GROUP_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/saveOrUpdate",method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(OrganizationGroup group, String operate) {
		//返回结果
		JSONObject resultJson = new JSONObject();
		try {
			StringBuilder errInfo = new StringBuilder(100);
			if(!organizationGroupService.checkUnique(group, errInfo)){
				resultJson.put("state", 7);
				resultJson.put("info", errInfo.toString());
				return resultJson.toString();
			}
			organizationGroupService.saveOrUpdate(group);
		} catch (Exception e) {
			resultJson.put("state", 7);
			resultJson.put("info", "保存失败，请重新配置信息！");
			return resultJson.toString();
		}
		
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 删除分组
	 */
	@SystemLog(description = "机构分组", module = OperationLog.ORGANIZATION_GROUP_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids){
		
		JSONObject resultJson = new JSONObject();
		
		StringBuilder errInfo = new StringBuilder(100);
		if ( !organizationGroupService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		organizationGroupService.delete(ids);
		
		//返回结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "删除成功！");
		return resultJson.toString();
	}
	
	/**
	 * 向分组中添加机构
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/addOrganization", method = RequestMethod.GET)
	public String addOrganization(Model model, String id) {
		OrganizationGroup info = organizationGroupService.findById(id);
		model.addAttribute("info", info);
		return "manager/org/group/addOrganization";
	}
}