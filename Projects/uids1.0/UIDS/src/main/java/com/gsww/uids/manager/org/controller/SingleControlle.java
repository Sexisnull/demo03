package com.gsww.uids.manager.org.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsww.common.util.WebConstants;

@Controller
@RequestMapping("/single")
public class SingleControlle {
	
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/single", method = RequestMethod.GET)
	public String organizationMgr(Model model) {
		
		return "manager/single/single";
	}

}
