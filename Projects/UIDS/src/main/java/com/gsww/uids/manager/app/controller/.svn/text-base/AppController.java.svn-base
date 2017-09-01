package com.gsww.uids.manager.app.controller;

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
import com.gsww.uids.manager.app.entity.AppIcon;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.AppIconService;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.service.OrganizationRelationService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.system.controller.SystemLog;

/**
 * 应用的控制层处理
 * 
 * @author cyl
 *
 */
@Controller
@RequestMapping({ "/app" })
public class AppController {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private AppIconService appIconService;

	@Autowired
	private OrganizationRelationService organizationRelationService;

	/**
	 * 
	 * 
	 * @param model
	 * @param groupId
	 * @param page
	 * @param size
	 * @param name
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/appMgr", method = RequestMethod.GET)
	public String applicationMgr(Model model, String groupId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String name) {
		
		if ( StringUtil.isBlank(groupId) ) {
			groupId = "0";
			model.addAttribute("groupId", groupId);
			model.addAttribute("groupName", "全部应用");
		} else {
			Organization org = organizationService.findById(groupId);
			model.addAttribute("groupId", groupId);
			model.addAttribute("groupName", org.getShortName());
		}
		return "manager/app/appMgr";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/appList" }, method = RequestMethod.POST)
	@ResponseBody
	public String appList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String searchName, String groupId) {
		return this.applicationService.findPage(page, size, searchName, groupId);
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/appNewOrEdit" }, method = RequestMethod.GET)
	public String appNewOrEdit(Model model, String id, String orgId) {
		
		Application info = applicationService.findById(id);		
		
		if (info.getOrganization() == null) {
			Organization org = null;
			if ( orgId.equalsIgnoreCase("0") ) {
				org = new Organization();
			} else {
				org = organizationService.findById(orgId);
			}
			model.addAttribute("parent", org);
		} else {
			model.addAttribute("parent", info.getOrganization());
		}
		
		if (info.getSysIcon() == null) {
			info.setSysIcon(new AppIcon());
		}
		
		model.addAttribute("info", info);
				
		return "manager/app/appNewOrEdit";
	}
	
	/**
	 * 查询当前用户能否操作某些应用
	 * 
	 * @param appIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAdminPermission", method = RequestMethod.POST)
	@ResponseBody
	public String checkAdminPermission(String appIds) {
		
		JSONObject resultJson = new JSONObject();
		
		if ( StringUtil.isNotBlank(appIds) ) {
			for ( String appId : appIds.split(",") ) {
				Application app = applicationService.findById(appId);
				Organization org = app.getOrganization();
				if ( !organizationService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_APP_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限操作这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "允许操作");		
		return resultJson.toString();
	}
	
	@SystemLog(description = "应用", module = OperationLog.APP_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/appSaveOrUpdate" }, method = RequestMethod.POST)
	@ResponseBody
	public String appSaveOrUpdate(Model model, Application application, String parentId, String currentPage,
			String iconId, String operate) {
		JSONObject resultJson = new JSONObject();
		
		if (StringUtil.isNotBlank(parentId)) {
			Organization organization = this.organizationService.findById(parentId);
			
			// 检查权限
			if ( !organizationService.canCurrentLoginUserAccessOrganization(organization, WebConstants.ROLE_KEY_APP_ADMIN) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", String.format("对不起，您没有权限使用机构【%s】！", organization.getShortName()));
				return resultJson.toString();
			}
			
			application.setOrganization(organization);
		}
		AppIcon appIcon = appIconService.findIconById(iconId);
		if (StringUtil.isBlank(appIcon.getUuid()) || appIcon == null) {
			application.setSysIcon(null);
		} else {
			application.setSysIcon(appIcon);
			application.setIconPath(appIcon.getPath());
		}
		// 验证唯一性
	    StringBuilder errInfo = new StringBuilder();
		if ( !applicationService.checkUnique(application, errInfo) ) {
			resultJson.put("state", 0);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
		if (application.getType().equalsIgnoreCase("system")) {
			List<Application> appTypes = applicationService.findByAppType("system");
			if (appTypes.size() == 1) {
				if (application.getUuid() == null || StringUtil.isBlank(application.getUuid())) {
					resultJson.put("state", 0);
					resultJson.put("info", errInfo.append("统一身份认证系统已经存在，请重新选择"));
					return resultJson.toString();
				} else if (application.getUuid() != null && !application.getUuid().equalsIgnoreCase(appTypes.get(0).getUuid())) {
					resultJson.put("state", 0);
					resultJson.put("info", errInfo.append("统一身份认证系统已经存在，请重新选择"));
					return resultJson.toString();
				}
			} else if (appTypes.size() > 1) {
				resultJson.put("state", 0);
				resultJson.put("info", errInfo.append("该应用类型不匹配，请重新选择"));
				return resultJson.toString();
			}
		}
		//保存
		boolean save = applicationService.saveOrUpdate(application);
		if (save) {
			resultJson.put("state", 1);
			resultJson.put("info", "保存成功！");
		} else {
			resultJson.put("state", 2);
			resultJson.put("info", "保存失败！");
		}
		return resultJson.toString();
	}

	@SystemLog(description = "应用", module = OperationLog.APP_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/appDelete" }, method = RequestMethod.POST)
	@ResponseBody
	public String appDelete(Model model, Application application, String currentPage, String ids) {
		
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(ids) ) {
			String idArray[] = ids.split(",");
			for ( String id : idArray ) {
				Application app = applicationService.findById(id);
				Organization organization = app.getOrganization();
				 
				// 检查权限
				if ( !organizationService.canCurrentLoginUserAccessOrganization(organization, WebConstants.ROLE_KEY_APP_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限删除这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !applicationService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		applicationService.deleteApp(ids);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);

		return resultJson.toString();
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/orgTree" }, method = RequestMethod.GET)
	public String organizationMenu(Model model) {
		model.addAttribute("classifyList", organizationRelationService.findAll());
		return "manager/app/appOrgTree";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/groupDetail" }, method = RequestMethod.POST)
	@ResponseBody
	public String groupDetail(String groupId) {
		JSONArray result = new JSONArray();
		JSONObject json = null;
		if (StringUtil.isBlank(groupId)) {
			groupId = "0";
			Organization org = organizationService.findById(groupId);
			json = new JSONObject();
			json.put("id", groupId);
			json.put("name", "应用机构");
			json.put("url", "appMgr.uids?groupId=" + org.getUuid()) ;
			json.put("target", "page");
			if (org.getParentIs() == 1) {
				json.put("isParent", true);
			} else {
				json.put("isParent", false);
			}
			result.put(json);
		} else {
			List<Organization> list = organizationService.findChild(groupId);
			for (Organization org : list) {
				json = new JSONObject();
				json.put("id", org.getUuid());
				json.put("name", org.getShortName());
				json.put("url", "appMgr.uids?groupId=" + org.getUuid());
				json.put("target", "page");
				if (org.getParentIs() == 1) {
					json.put("isParent", true);
				} else {
					json.put("isParent", false);
				}
				result.put(json);
			}
		}
		return result.toString();
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/initSourceByApp" }, method = RequestMethod.GET)
	public String initSourceByApp(Model model, String appid) {
		model.addAttribute("appUuid", appid);
		return "manager/app/appSource";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/checkSource" }, method = RequestMethod.POST)
	@ResponseBody
	public String checkSource(Model model, String id, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return this.resourceService.findPage(page, size, null, id);
	}

	/**
	 * 机构管理树
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/detailData", method = RequestMethod.POST)
	@ResponseBody
	public String detailData(String pId, String code) {

		JSONArray result = new JSONArray();
		JSONObject json = null;
		List<OrganizationRelation> list = null;

		if (StringUtil.isBlank(pId)) {
			Organization org = organizationService.getRootOrganization();
			OrganizationRelation relation = new OrganizationRelation();
			list = new ArrayList<OrganizationRelation>();
			relation.setOrganization(org);
			list.add(relation);
		} else {
			list = organizationRelationService.findChildByPIdAndCode(pId, code);
		}

		for (OrganizationRelation relation : list) {
			json = new JSONObject();
			json.put("id", relation.getOrganization().getUuid());
			json.put("pId",
					(relation.getParentOrganization() == null) ? null : relation.getParentOrganization().getUuid());
			json.put("name", relation.getOrganization().getShortName());
			// 是否为父节点

			List<OrganizationRelation> list1 = organizationRelationService
					.findChildByPIdAndCode(relation.getOrganization().getUuid(), code);
			if (list1.size() > 0) {
				json.put("isParent", true);
			} else {
				json.put("isParent", false);
			}
			result.put(json);
		}
		return result.toString();
	}
}