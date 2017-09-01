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

import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.AppResourceAuth;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.service.OrganizationGroupService;

/**
 * 角色授权
 * @author simplife
 *
 */
@Controller
@RequestMapping({ "/role/auth" })
public class RoleAuthController {
	
	@Autowired
	private SourceAuthService sourceAuthService;
	
	@Autowired
	private OrganizationGroupService organizationGroupService;
	
	@Autowired
	private ResourceService resourceService;

	/**
	 * 授权列表
	 * @param page
	 * @param size
	 * @param sourceId
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/listData" }, method = RequestMethod.POST)
	@ResponseBody
	public String sourceAuthList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows, String roleId) {
		return this.sourceAuthService.findPageByRole(page, rows, roleId);
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_APP_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/newOrEdit" }, method = RequestMethod.GET)
	public String editSourceAuth(Model model, String roleId, String id) {
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
		List<AppResource> sourceList = resourceService.findAll();
		
		List<OrganizationGroup> orgGroups = this.organizationGroupService.findAll();
		model.addAttribute("parent", org);
		model.addAttribute("sources", sourceList);
		model.addAttribute("orgGroups", orgGroups);
		model.addAttribute("roleId", roleId);
		model.addAttribute("appResourceAuth", appResourceAuth);
		return "manager/role/grantForm";
	}

	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = { "/grant" }, method = RequestMethod.GET)
	public String sourceGrant(Model model, String id) {
		Organization org = new Organization();
		model.addAttribute("parent", org);
		model.addAttribute("roleId", id);
		return "manager/role/grant";
	}

}
