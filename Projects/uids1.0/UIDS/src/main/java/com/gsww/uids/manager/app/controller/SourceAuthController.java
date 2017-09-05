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

import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.AppResourceAuth;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.service.OrganizationGroupService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.service.RoleService;

@Controller
@RequestMapping({ "/auth" })
public class SourceAuthController {
	
	@Autowired
	private SourceAuthService sourceAuthService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationGroupService organizationGroupService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceAuthList" }, method = RequestMethod.POST)
	@ResponseBody
	public String sourceAuthList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String sourceId) {
		return this.sourceAuthService.findPageBySource(page, size, sourceId);
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/selectFresh" }, method = RequestMethod.POST)
	@ResponseBody
	public String selectFresh(Model model, String selectValue) {
		JSONObject json = new JSONObject();
		json.put("selectValue", selectValue);
		return json.toString();
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/editSourceAuth" }, method = RequestMethod.GET)
	public String editSourceAuth(Model model, String sourceId, String id) {
		AppResourceAuth appResourceAuth = new AppResourceAuth();
		Organization org = new Organization();
		if (!StringUtil.isBlank(id)) {
			appResourceAuth = sourceAuthService.findSourceAuthById(id);
		}
		if (appResourceAuth.getOrg() != null) {
			if (!StringUtil.isBlank(appResourceAuth.getOrg().getUuid())) {
				org = appResourceAuth.getOrg();
			}
		}
		List<Role> roleList = this.roleService.findAll();
		List<OrganizationGroup> orgGroups = this.organizationGroupService.findAll();
		model.addAttribute("parent", org);
		model.addAttribute("roles", roleList);
		model.addAttribute("orgGroups", orgGroups);
		model.addAttribute("sourceId", sourceId);
		model.addAttribute("appResourceAuth", appResourceAuth);
		return "manager/app/sourceGrantForm";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/authDelete" }, method = RequestMethod.POST)
	@ResponseBody
	public String authDelete(String authId) {
		
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(authId) ) {
			for ( String id : authId.split(",") ) {
				AppResourceAuth auth = sourceAuthService.findSourceAuthById(id);
				if ( AppResourceAuth.ROLE_GRANT.equalsIgnoreCase(auth.getType()) ) {
					Organization organization = auth.getOrg();
					if ( !organizationService.canCurrentLoginUserAccessOrganization(organization, WebConstants.ROLE_KEY_APP_ADMIN) ) {
						resultJson.put("state", MessageInfo.STATE_FAIL);
						resultJson.put("info", "对不起，您没有权限删除这些记录！");
						return resultJson.toString();
					}
				}
			}
		}
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !sourceAuthService.checkDelete(authId, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		sourceAuthService.authDelete(authId);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/sourceGrant" }, method = RequestMethod.GET)
	public String sourceGrant(Model model, String id) {
		Organization org = new Organization();
		model.addAttribute("parent", org);
		model.addAttribute("sourceId", id);
		return "manager/app/sourceGrant";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/grantSourceSave" }, method = RequestMethod.POST)
	@ResponseBody
	public String grantSourceSave(Model model, AppResourceAuth sourceAuth, String orgType, String sourceId,
			String orgId, String roleId, String orgGroupId) {
		
		JSONObject resultJson = new JSONObject();
		
		// 如果是编辑，需要判断是否有编辑权限
		if ( StringUtil.isNotBlank(sourceAuth.getUuid()) ) {
			AppResourceAuth oldSourceAuth = sourceAuthService.findSourceAuthById(sourceAuth.getUuid());
			if ( AppResourceAuth.ROLE_GRANT.equalsIgnoreCase(oldSourceAuth.getType()) ) {
				Organization oldOrg = oldSourceAuth.getOrg();
				if ( !organizationService.canCurrentLoginUserAccessOrganization(oldOrg, WebConstants.ROLE_KEY_APP_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限修改这条记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 判断授权信息的唯一性
		StringBuilder errInfo = new StringBuilder();
		boolean bauth= sourceAuthService.checkUnique(sourceAuth, orgId, orgGroupId, roleId, sourceId, errInfo);
		if (!bauth) {
			resultJson.put("state", 0);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
		
		// 检查权限
		Organization organization = organizationService.findById(orgId);
		if ( AppResourceAuth.ROLE_GRANT.equalsIgnoreCase(sourceAuth.getType()) ) {
			if ( !organizationService.canCurrentLoginUserAccessOrganization(organization, WebConstants.ROLE_KEY_APP_ADMIN) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", String.format("对不起，您没有权限使用机构【%s】！", organization.getShortName()));
				return resultJson.toString();
			}
		}

		AppResource appSource = this.resourceService.findById(sourceId);
		OrganizationGroup organizationGroup = this.organizationGroupService.findById(orgGroupId);
		if (StringUtil.isNotBlank(roleId)) {
			Role role = this.roleService.findById(roleId);
			sourceAuth.setRole(role);
		}
		
		if (organizationGroup == null || StringUtil.isBlank(organizationGroup.getUuid())) {
			sourceAuth.setOrgGroup(null);
		} else {
			sourceAuth.setOrgGroup(organizationGroup);
		}
		if (organization == null || StringUtil.isBlank(organization.getUuid())) {
			sourceAuth.setOrg(null);
		} else {
			sourceAuth.setOrg(organization);
		}
		sourceAuth.setResource(appSource);
		boolean saveOrUpdate = this.sourceAuthService.saveOrUpdateSourceAuth(sourceAuth);
		if (saveOrUpdate) {
			resultJson.put("state", 1);
		} else {
			resultJson.put("state", 2);
		}
		return resultJson.toString();
	}
}
