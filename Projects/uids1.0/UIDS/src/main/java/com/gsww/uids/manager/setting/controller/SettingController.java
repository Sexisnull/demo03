package com.gsww.uids.manager.setting.controller;

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
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.setting.entity.ActiveAccount;
import com.gsww.uids.manager.setting.service.ActiveAccountService;

@Controller
@RequestMapping("/setting")
public class SettingController {
	
	@Autowired
	private ActiveAccountService activeAccountService;
	
	/**
	 * 请求在线用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/onlineMgr", method = RequestMethod.GET)
	public String onlineMgr(Model model) {
		
		return "manager/setting/onlineMgr";
	}
	
	/**
	 * 获取在线用户数据
	 * 
	 * @param model
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public String list( Model model,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		PageObject<ActiveAccount> pageObject = activeAccountService.findPage(page, pageSize);
	    return pageObject.toJSONString();
   }
}