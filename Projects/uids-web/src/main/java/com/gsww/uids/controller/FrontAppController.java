package com.gsww.uids.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;

@Controller
public class FrontAppController extends BaseController{
	
	
	private static Logger logger = LoggerFactory.getLogger(FrontAppController.class);
	
	
	/**
	 * 首页index页面加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/frontIndex")
	public String getSysIndex(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute("theme");
			String theme = request.getParameter("theme");
			if(StringUtils.isBlank(theme)) {
				theme = "default";
			}
			SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			session.getRoleIds();
			
			request.getSession().setAttribute("theme", theme);
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		return "main/frontIndex";
	}
	
	@RequestMapping(value = "/backIndex")
	public String toBackIndex(HttpServletRequest request) {
		try {
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		return "main/backIndex";
	}
	

	@RequestMapping(value = "/appSetting")
	public String appSetting(HttpServletRequest request) {
		try {
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		return "main/appSetting";
	}
}
