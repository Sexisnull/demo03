package com.gsww.uids.manager.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.entity.AppIcon;
import com.gsww.uids.manager.app.service.AppIconService;

@Controller
@RequestMapping({ "/icon" })

public class IconController {
	@Autowired
	private AppIconService appIconService;
	
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping({ "/fileUpload" })
	public void upload(HttpServletRequest request, HttpServletResponse response, String iconType) {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		String result = this.appIconService.fileUpload(request, response, upload, iconType);
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
  	@RequestMapping({ "/viewIcon"})
	public void viewIcon(Model model, String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			AppIcon appIcon = this.appIconService.findIconById(id);
			this.appIconService.viewIcon(appIcon.getPath(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN,
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
  	@RequestMapping(value = { "/iconSelect" }, method = RequestMethod.GET)
	public String iconSelect(Model model, String iconType) {
		List<AppIcon> list = this.appIconService.findAll(iconType);
		model.addAttribute("lists", list);
		return "manager/app/iconList";
	}
}