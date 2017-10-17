package com.gsww.uids.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.entity.JisUserdefined;
import com.gsww.uids.service.ComplatRoleService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisRoleobjectService;
import com.gsww.uids.service.JisUserdefinedService;
import com.hanweb.common.util.Md5Util;
import com.hanweb.sso.ldap.util.MD5;

@Controller
public class FrontAppController extends BaseController {

	@Autowired
	private JisApplicationService jisApplicationService;
	@Autowired
	private JisUserdefinedService jisUserdefinedService;
	@Autowired
	private JisSettings jisSetting;
	@Autowired
	private JisRoleobjectService jisRoleobjectService;
	@Autowired
	private ComplatUserService complatUserService;
	@Autowired
	private ComplatRoleService complatRoleService;

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
			String managerIcon = "display:none";
			if (checkHaveRight(session.getAccountId(), roleIds)) {
				managerIcon = "";
			}
			String copyRight = jisSetting.getCopyRight();
			model.addAttribute("application", apps);
			model.addAttribute("rightMsg", copyRight);
			model.addAttribute("managerIcon", managerIcon);
			request.getSession().setAttribute("theme", theme);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/frontIndex";
	}

	private boolean checkHaveRight(String iid, String roleIds) {
		if (iid == null || iid == "" || roleIds == null
				|| roleIds.split(",").length == 0) {
			return false;
		}
		List<ComplatRole> roles = new ArrayList<ComplatRole>();
		String[] roleId = roleIds.split(",");
		if (Integer.parseInt(iid) == 1) {
			return true;
		}
		for (int i = 0; i < roleId.length; i++) {
			try {
				String roleod = roleId[i];
				if (roleod != null && roleod.trim() != "") {
					roles.add(complatRoleService.findByKey(Integer
							.parseInt(roleod)));
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		for (ComplatRole role : roles) {
			if ((role != null) && (role.getType() != null)
					&& (role.getType().intValue() == 0)) {
				return true;
			}
			if ((role != null) && (role.getType() != null)
					&& (role.getType().intValue() == 1)) {
				return true;
			}
			if ((role != null) && (role.getType() != null)
					&& (role.getType().intValue() == 2)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/backIndex")
	public String toBackIndex(HttpServletRequest request) {
		try {
			SysUserSession session = (SysUserSession) request.getSession()
					.getAttribute("sysUserSession");
			String roleIds = session.getRoleIds();
			if (!checkHaveRight(session.getAccountId(), roleIds)) {
				return "main/noRightAccess";
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/backIndex";
	}

	/**
	 * 跳转至应用账号设置页面
	 * 
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
			String copyRight = jisSetting.getCopyRight();
			model.addAttribute("rightMsg", copyRight);
			model.addAttribute("application", apps);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "main/appSetting";
	}

	@RequestMapping(value = "/RedirectSingleLogin")
	public String singleLogin(String appid, HttpServletRequest request,
			Model model) {
		String singleUrl = "";
		String userDefinedName = "";
		String userDefinedPwd = "";
		String loginAllName = "";
		String appMark = "";
		String groupCode = "";
		try {

			SysUserSession session = (SysUserSession) request.getSession()
					.getAttribute("sysUserSession");
			String roleIds = session.getRoleIds();
			ComplatUser user = complatUserService.findByKey(Integer
					.parseInt(session.getAccountId()));
			String[] roles = roleIds.split(",");
			List<JisRoleobject> objs = new ArrayList<JisRoleobject>();
			for (int i = 0; i < roles.length; i++) {
				objs.addAll(jisRoleobjectService.findByRoleIdAndType(
						Integer.parseInt(roles[i]), 3));
			}
			MD5 md5 = new MD5();
			for (JisRoleobject obj : objs) {
				if (appid != null
						&& obj.getObjectid() == Integer.parseInt(appid)) {
					JisApplication app = jisApplicationService
							.findByKey(Integer.parseInt(appid));
					Integer userDefinedType = app.getUserDefined();
					Integer loginType = app.getLoginType();
					Integer encryptType = app.getEncryptType();
					String encryptKey = app.getEncryptKey();
					if (loginType == 1) {
						app.setSsoUrl(app.getAppUrl());
						if (userDefinedType == 0) {
							JisUserdefined userDefined = this.jisUserdefinedService
									.findByAppidAndLoginAllName(app.getIid()
											.intValue(), user.getLoginallname());
							if (userDefined != null) {
								userDefinedName = userDefined.getApploginname();
								userDefinedPwd = userDefined.getApppwd();
							}
						} else if (userDefinedType == 1) {
							userDefinedName = app.getAllLoginIid();
							userDefinedPwd = app.getAllPwd();
						} else {
							userDefinedName = user.getLoginallname();
							userDefinedPwd = user.getPwd();
							userDefinedPwd = md5.decrypt(userDefinedPwd,
									"jcms2008");
							loginAllName = user.getLoginallname();
						}
						if (encryptType == 1) {
							userDefinedName = md5.encrypt(userDefinedName,
									encryptKey);
							userDefinedPwd = md5.encrypt(userDefinedPwd,
									encryptKey);
							loginAllName = md5
									.encrypt(loginAllName, encryptKey);
						} else if (encryptType == 2) {
							userDefinedName = md5.encryptMB(userDefinedName,
									encryptKey);
							userDefinedPwd = md5.encryptMB(userDefinedPwd,
									encryptKey);
							loginAllName = md5.encryptMB(loginAllName,
									encryptKey);
						}

					} else {
						userDefinedName = user.getLoginname();
						loginAllName = user.getLoginallname();
						userDefinedPwd = user.getPwd();
						userDefinedPwd = md5
								.decrypt(userDefinedPwd, "jcms2008");
						if (encryptType == 1) {
							userDefinedName = md5.encrypt(userDefinedName,
									encryptKey);
							userDefinedPwd = md5.encrypt(userDefinedPwd,
									encryptKey);
							loginAllName = md5
									.encrypt(loginAllName, encryptKey);
						} else if (encryptType == 2) {
							userDefinedName = md5.encryptMB(userDefinedName,
									encryptKey);
							userDefinedPwd = md5.encryptMB(userDefinedPwd,
									encryptKey);
							loginAllName = md5.encryptMB(loginAllName,
									encryptKey);
						}
					}
					singleUrl = app.getSsoUrl();
					groupCode = user.getGroupid() + "";
					appMark = app.getMark();
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		if (singleUrl == "" && userDefinedName == "" && userDefinedPwd == "") {
			return "main/noRightAccess";
		}
		model.addAttribute("url", singleUrl);
		model.addAttribute("name", userDefinedName);
		model.addAttribute("pwd", userDefinedPwd);
		model.addAttribute("loginAllName", loginAllName);
		model.addAttribute("appMark", appMark);
		model.addAttribute("groupCode", groupCode);
		return "main/singleLogin";
	}

	/**
	 * 设置自定义账号
	 * 
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
				model.addAttribute("apploginname",
						userDefined.getApploginname());
				model.addAttribute("loginName", user.getLoginname());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "main/setUserdefined";
	}

	/**
	 * 保存自定义账号
	 * 
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
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JisUserdefined defined = new JisUserdefined();
			if (StringHelper.isNotBlack(definedId)) {
				defined = jisUserdefinedService.findByKey(Integer
						.parseInt(definedId));
			}
			defined.setAppid(Integer.parseInt(appid));
			defined.setLoginname(loginname);
			defined.setLoginallname(loginAllname);
			defined.setApploginname(apploginName);
			if (StringHelper.isNotBlack(definedId)
					&& !StringHelper.isNotBlack(appPwd)) {
			} else {
				defined.setApppwd(Md5Util.md5encode(appPwd));
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
