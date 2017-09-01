package com.gsww.uids.manager.app.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.entity.PageObject;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.entity.AppIcon;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.AppIconService;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.system.controller.SystemLog;

@Controller
@RequestMapping({ "/source" })
public class SourceController {

	@Autowired
	private ApplicationService applicationServive;

	@Autowired
	private AppIconService appIconService;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private OrganizationService organizationService;

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceMgr" }, method = RequestMethod.GET)
	public String applicationMgr() {
		return "manager/app/sourceMgr";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceList" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public String sourceList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows, String searchName) {
		return this.resourceService.findPage(Integer.valueOf(page), Integer.valueOf(rows), searchName, null);
	}

	/**
	 * 查询当前用户能否操作某些资源
	 * 
	 * @param resourceIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAdminPermission", method = RequestMethod.POST)
	@ResponseBody
	public String checkAdminPermission(String resourceIds) {
		
		JSONObject resultJson = new JSONObject();
		
		if ( StringUtil.isNotBlank(resourceIds) ) {
			for ( String resourceId : resourceIds.split(",") ) {
				AppResource resource = resourceService.findById(resourceId);
				Organization org = resource.getApp().getOrganization();
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
	
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceNewOrEdit" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String sourceNewOrEdit(Model model, String id) {

		AppResource info = this.resourceService.findById(id);
		if (info.getSysIcon() == null) {
			info.setSysIcon(new AppIcon());
		}
		model.addAttribute("info", info);

		List<Application> appList = this.applicationServive.findAll();
		model.addAttribute("appList", appList);
		
		return "manager/app/sourceNewOrEdit";
	}

	/**
	 * 获取一个应用下的所有资源
	 * 
	 * @param model
	 * @param appId
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/resourceOfApp" }, method = RequestMethod.POST)
	@ResponseBody
	public String getResourceOfApp(Model model, String appId) {
		List<AppResource> resourceList = resourceService.findAppResourceByApp(appId);
		PageObject<AppResource> pageList = new PageObject<>();
		pageList.setDataList(resourceList);
		return pageList.toJSONString();
	}
	
	@SystemLog(description = "资源", module = OperationLog.SOURCE_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceSaveOrUpdate" }, method = RequestMethod.POST)
	@ResponseBody
	public String appSaveOrUpdate(Model model, AppResource appResource, String id, String iconId, String currentPage, String operate) {
		
		JSONObject resultJson = new JSONObject();
		
		Application application = this.applicationServive.findById(id);
		
		// 检查权限
		Organization organization = application.getOrganization();
		if ( !organizationService.canCurrentLoginUserAccessOrganization(organization, WebConstants.ROLE_KEY_APP_ADMIN) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", String.format("对不起，您没有权限操作应用【%s】下的资源！", application.getName()));
			return resultJson.toString();
		}
				
		AppIcon appIcon = appIconService.findIconById(iconId);
		appResource.setApp(application);
		
		if (appIcon == null || StringUtil.isBlank(appIcon.getUuid())) {
			appResource.setSysIcon(null);
		} else {
			appResource.setSysIcon(appIcon);
			appResource.setIconPath(appIcon.getPath());
		}
		//检查唯一性
		StringBuilder errInfo = new StringBuilder();
		if (!resourceService.checkUnique(appResource, errInfo)) {
			resultJson.put("state", 0);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
		boolean save  = resourceService.saveOrUpdate(appResource);
		if (save) {
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("message", "保存成功！");
			return resultJson.toString();
		} else {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("message", "保存失败！");
			return resultJson.toString();

		}
	}
	
	@SystemLog(description = "资源", module = OperationLog.SOURCE_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/deleteSource" }, method = RequestMethod.POST)
	@ResponseBody
	public String appDelete(String ids) {

		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(ids) ) {
			String idArray[] = ids.split(",");
			for ( String id : idArray ) {
				AppResource resource = resourceService.findById(id);
				Organization organization = resource.getApp().getOrganization();
				 
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
		if ( !resourceService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		resourceService.deleteSource(ids);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);		
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
}
