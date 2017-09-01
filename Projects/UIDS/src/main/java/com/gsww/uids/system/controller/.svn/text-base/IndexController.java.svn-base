package com.gsww.uids.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;

/**
 * 主页控制
 * 
 * @author simplife
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	
	@Autowired
	private ApplicationService appService;
	
	/**
	 * 进入前台登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/gsjis/login", method = RequestMethod.GET)
	public String loginForeground(Model model) {
		
		List<Application> apps = appService.findCommonApplications();
		Application systemApp = appService.findSystemApplication();
		if ( systemApp == null ) {
			systemApp = new Application();
		}
		model.addAttribute("apps", apps);
		model.addAttribute("systemApp", systemApp);
		model.addAttribute("type", "gsjis");
		
		return "manager/sys/personal/login";
	}
	
	/**
	 * 进入后台登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/login", method = RequestMethod.GET)
	public String loginPlatform(Model model) {
		
		List<Application> apps = appService.findCommonApplications();
		Application systemApp = appService.findSystemApplication();
		if ( systemApp == null ) {
			systemApp = new Application();
		}
		model.addAttribute("apps", apps);
		model.addAttribute("systemApp", systemApp);
		model.addAttribute("type", "admin");
		
		return "manager/sys/personal/login";
	}
}
