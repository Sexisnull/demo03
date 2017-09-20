/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysLoginService;
import com.gsww.jup.service.sys.SysMenuService;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisLogService;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014年7月23日 下午10:57:16</p>
 * <p>类描述 : 用户登录controller        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Controller
public class SysLoginController extends BaseController{
	
	@Autowired
	private ComplatUserService complatUserService;
	@Autowired
	private SysLoginService sysLoginService;
//	@Autowired
//	private SysLogService sysLogService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ComplatGroupService complatGroupService;
	@Autowired
	private JisLogService jisLogService;
	
	private static Logger logger = LoggerFactory.getLogger(SysLoginController.class);

	/**
	 * 验证用户账号及密码
	 * @param request
	 * @param response
	 * @return mav
	 * @throws IOException 
	 */
	@RequestMapping(value = "/login/sysLogin", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = (String) request.getParameter("userName");
		String passWord = (String) request.getParameter("passWord");
		String group = (String) request.getParameter("groupid");
		String inputKaptcha = (String) request.getParameter("kaptcha");
		String sessionKaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		Map<String, Object> resMap = new HashMap<String, Object>();
		String loginIp = getIpAddr(request);
		//为了自动化测试先屏蔽掉验证码，请大家先别修改回去，手下留情。谢谢。
//		if (true) {
		if (sessionKaptcha.equals(inputKaptcha)) {
			// 验证码正确，检查用户名和密码
			try {
				SysUserSession sysUserSession = sysLoginService.login(userName,passWord,group,loginIp);
				if (sysUserSession != null) {
					if(sysUserSession.getUserState().equals("1")){
						request.getSession().setAttribute("sysUserSession", sysUserSession);
						resMap.put("ret", "0");
						resMap.put("msg", "登录成功！");
						response.getWriter().write(JSONObject.toJSONString(resMap));
						try {
							// 登录日志
							JisLog log = new JisLog();
							log.setUserId(sysUserSession.getAccountId());
							log.setIp(sysUserSession.getUserIp());
							log.setOperateTime(new Date());
							log.setSpec("系统登录成功");
							log.setModuleName(8);
							log.setOperateType(9);
							jisLogService.save(log);

						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
					}else{
						resMap.put("ret", "3");
						resMap.put("msg", "用户已停用！");
						response.getWriter().write(JSONObject.toJSONString(resMap));
						try {
							JisLog log = new JisLog();
							log.setUserId(sysUserSession.getAccountId());
							log.setIp(sysUserSession.getUserIp());
							log.setOperateTime(new Date());
							log.setSpec("系统登录失败[停用]");
							log.setModuleName(8);
							log.setOperateType(9);
							jisLogService.save(log);
						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
					}
				} else {
					resMap.put("ret", "2");
					resMap.put("msg", "用户名或密码错误！");
					response.getWriter().write(JSONObject.toJSONString(resMap));
					try {
						JisLog log = new JisLog();
						log.setUserId(userName);
						log.setIp(loginIp);
						log.setOperateTime(new Date());
						log.setSpec("系统登录失败[用户名密码错误]");
						log.setModuleName(8);
						log.setOperateType(9);
						jisLogService.save(log);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
				resMap.put("ret", "4");
				resMap.put("msg", "系统错误！");
				response.getWriter().write(JSONObject.toJSONString(resMap));
			}
		} else {
			// 验证码错误，返回首页并提示验证码错误
			resMap.put("ret", "1");
			resMap.put("msg", "验证码错误！");
			response.getWriter().write(JSONObject.toJSONString(resMap));
		}
	}

	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "/login/loginOut")
	public void loginOut(HttpServletRequest request, HttpServletResponse response) {
		SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		if (null !=sysUserSession ) {
			request.getSession().removeAttribute("sysUserSession");
		}
	}
	
	@RequestMapping(value = "/iframe")
	public String getiframe(HttpServletRequest request) {
		try {
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "main/iframe";
	}
	/**
	 * 首页main页面加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/getSysMain")
	public String getSysIndexMain(ServletRequest request) {
		try {

		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		return "main/main";
	}

	/**
	 * 首页left菜单加载 getSysMenuJson
	 * @return
	 * @throws Exception
	 */								
	@RequestMapping(value = "/login/getSysMenu")
	public void getSysMenu(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String roleIds = sysUserSession.getRoleIds();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(sysMenuService.getSysMenu(roleIds));
			response.flushBuffer();
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
	}
	/**
	 * 首页left菜单加载 getSysMenuJson
	 * @return
	 * @throws Exception
	 */								
	@RequestMapping(value = "/login/getSysMenuJson")
	public void getSysMenuJson(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String roleIds = sysUserSession.getRoleIds();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(sysMenuService.getSysMenuJson(roleIds));
			response.flushBuffer();
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
	}
	/**
	 * 加载用户中心
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "/login/gotoUserDetail")
	public ModelAndView gotoUserDetail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		try{
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			ComplatUser sysAccount = complatUserService.findByKey(Integer.parseInt(sysUserSession.getAccountId()));
			mav.addObject("sysAccount", sysAccount);
			mav.setViewName("/main/userDetail");
		}catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		return mav;
	}

	/**
	 * 加载修改密码页面
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/login/getPwd")
	public String getPwd(HttpServletRequest request) {
		try {
			request.setAttribute("sysUserSession", request.getSession().getAttribute("sysUserSession"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "main/pwd";
	}*/
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @return 
	 */
	/*@RequestMapping(value = "/login/changePwd", method = RequestMethod.POST)
	public void changePwd(HttpServletRequest request, HttpServletResponse response) {
		String returnStr = "chanePwdSuccess";
		String oldPwd, newPwd, passWord;
		oldPwd = (String) request.getParameter("oldPwd");
		newPwd = (String) request.getParameter("newPwd");
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			ComplatUser sysAccount = complatUserService.findByKey(Integer.parseInt(sysUserSession.getAccountId()));
			passWord = sysAccount.getPwd();
			if (passWord.equals(new MD5().getMD5ofStr(oldPwd))) {
				sysAccount.setPwd(new MD5().getMD5ofStr(newPwd));
				complatUserService.save(sysAccount);
				request.getSession().removeAttribute("sysUserSession");
			} else {
				returnStr = "旧密码输入错误";
			}
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnStr = "没有找到用户或存在同名账号的用户，请联系管理员！";
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 获取登陆IP
	 * @param request
	 * @param response
	 * @return 
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("PRoxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 加载组织机构页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login/getGroup",method = RequestMethod.POST)
	public void getGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			String groupId = request.getParameter("groupId");
			String isDisabled = request.getParameter("isDisabled");

			List<ComplatGroup> list = new ArrayList<ComplatGroup>();
			
			if(!"0".equals(groupId)){
				list = complatGroupService.findByPid(Integer.parseInt(groupId));
			}else{
				list.add(complatGroupService.findByIid(128));
			}
			
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(ComplatGroup c:list){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", c.getIid()+"");
				map.put("name", c.getName());
				map.put("icon", null);
				map.put("target", "page");
				map.put("url", null);
				//List<ComplatGroup> sets = complatGroupService.findByPid(c.getIid());
				/*if(sets.isEmpty()){
					map.put("isParent", false);
				}else{
					map.put("isParent", true);
				}*/
				map.put("isParent", true);
				map.put("isDisabled", false);
				map.put("open", true);
				map.put("nocheck",false);
				map.put("click", null);
				map.put("checked", false);
				map.put("iconClose", null);
				map.put("iconOpen", null);
				map.put("iconSkin", null);
				map.put("pId", c.getPid());
				map.put("chkDisabled", false);
				map.put("halfCheck", false);
				map.put("dynamic", null);
				map.put("moduleId", null);
				map.put("functionId", null);
				map.put("allowedAdmin", null);
				map.put("allowedGroup", null);
				result.add(map);
			}
			String groups = JSONUtil.writeListMapJSONMap(result);
			response.setContentType("text/json"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write(groups);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
	}

}
