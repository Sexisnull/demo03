package com.gsww.uids.manager.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;

@Controller
@RequestMapping({"/single"})
public class SingleLoginViewController {

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 浏览前台
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/appall", method = RequestMethod.GET)
	public String appall(Model model,HttpServletRequest request, HttpServletResponse response) {
		String uuid =(String) request.getSession().getAttribute("ONLINE_ACCOUNT_ID");
		Account account = accountService.findById(uuid);
		List<Application> list = applicationService.findAll();
		model.addAttribute("list", list);
		model.addAttribute("name", account.getName());
		return "appall";
	}	
}
