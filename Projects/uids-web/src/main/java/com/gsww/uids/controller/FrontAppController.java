package com.gsww.uids.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisUserdefined;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisUserdefinedService;

@Controller
public class FrontAppController extends BaseController {

	@Autowired
	private JisApplicationService jisApplicationService;
	@Autowired
	private JisUserdefinedService jisUserdefinedService;
	@Autowired
	private ComplatUserService complatUserService;

	private static Logger logger = LoggerFactory
			.getLogger(FrontAppController.class);

	/**
	 * 首页index页面加载
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/frontIndex")
	public String getSysIndex(HttpServletRequest request, Model model) {
		try {
			request.getSession().removeAttribute("theme");
			String theme = request.getParameter("theme");
			if (StringUtils.isBlank(theme)) {
				theme = "default";
			}
			SysUserSession session = (SysUserSession) request.getSession()
					.getAttribute("sysUserSession");
			String roleIds = session.getRoleIds();
			List<Map<String, Object>> apps = jisApplicationService
					.findAppByRoleIds(roleIds);
			model.addAttribute("application", apps);
			request.getSession().setAttribute("theme", theme);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/frontIndex";
	}

	@RequestMapping(value = "/backIndex")
	public String toBackIndex(HttpServletRequest request) {
		try {

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/backIndex";
	}
	/**
	 * 跳转至应用账号设置页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appSetting")
	public String appSetting(HttpServletRequest request, Model model) {
		try {
			SysUserSession session = (SysUserSession) request.getSession()
					.getAttribute("sysUserSession");
			String roleIds = session.getRoleIds();
			List<Map<String, Object>> apps = jisApplicationService
					.findAppByRoleIds(roleIds);
			model.addAttribute("application", apps);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/appSetting";
	}
	/**
	 * 设置自定义账号
	 * @param request
	 * @param appid
	 * @param appname
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/setUserDefined")
	public String setUserdefined(HttpServletRequest request, String appid,
			String appname, Model model) {
		try {
			JisUserdefined userDefined = new JisUserdefined();
			SysUserSession sysUserSession = (SysUserSession) request
					.getSession().getAttribute("sysUserSession");
			String userId = sysUserSession.getAccountId();
			ComplatUser user = complatUserService.findByKey(Integer
					.parseInt(userId));
			if (appid != null && user.getLoginallname() != null) {
				userDefined = jisUserdefinedService.findByAppidAndLoginAllName(
						Integer.parseInt(appid), user.getLoginallname());
				if (userDefined == null) {
					userDefined = new JisUserdefined();
				}
				model.addAttribute("appname", appname);
				model.addAttribute("userDefinedId", userDefined.getIid());
				model.addAttribute("loginAllName", user.getLoginallname());
				model.addAttribute("appid", appid);
				model.addAttribute("apploginname", userDefined.getApploginname());
				model.addAttribute("loginName", user.getLoginname());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "main/setUserdefined";
	}
	
	/**
	 * 保存自定义账号
	 * @param definedId
	 * @param loginname
	 * @param loginAllname
	 * @param appid
	 * @param apploginName
	 * @param appPwd
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/submit_userdefined", method = RequestMethod.POST)
	public void submitUserdefined(String definedId, String loginname,
			String loginAllname, String appid, String apploginName,
			String appPwd, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			JisUserdefined defined = new JisUserdefined();
			if(StringHelper.isNotBlack(definedId)){
				defined = jisUserdefinedService.findByKey(Integer.parseInt(definedId));
			}
			defined.setAppid(Integer.parseInt(appid));
			defined.setLoginname(loginname);
			defined.setLoginallname(loginAllname);
			defined.setApploginname(apploginName);
			if(StringHelper.isNotBlack(definedId) && !StringHelper.isNotBlack(appPwd)){
			}else{
				defined.setApppwd(appPwd);
			}
			
			jisUserdefinedService.save(defined);
			
			result.put("state", true);
			result.put("msg", "设置成功");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("state", false);
			result.put("msg", "设置失败");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(JSONUtil.writeMapJSON(result));
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
	}
}
