package com.gsww.jup.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.uids.entity.ComplatRole;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-util</p>
 * <p>创建时间 : 2014年7月24日 下午11:56:34</p>
 * <p>类描述 : 过滤器        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
public class AccessFilter implements Filter {

	/**
	 * 需要排除的页面
	 */
	private String excludedPages;

	private String[] excludedPageArray;
	//没有后台权限用户可以访问的url
	private List<String> withoutBackrigthUrl;

	/**
	 * @see javax.servlet.Filter#void ()
	 */
	public void destroy() {

	}

	/**
	 * @see javax.servlet.Filter#void (javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		String path = ((HttpServletRequest) request).getServletPath();
		SysUserSession session = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
		HttpServletResponse hres = (HttpServletResponse) response;
		try {
			if (session != null) {
				SysContent.setRequest((HttpServletRequest) request);
				if(session.getAccountId().equals("1")){
					chain.doFilter(request, response);
					return;
				}
				String [] types = session.getRoleTypes().split(",");
				if(types==null || types.length==0){
					request.setAttribute("errorMessage", "请重新登录！");
					hres.sendRedirect(((HttpServletRequest) request).getContextPath()+"/time_out.jsp");
					return;
				}
				for(int i=0;i<types.length;i++){
					if ((types[i].equals("0") || types[i].equals("1") || types[i].equals("2"))) {
						chain.doFilter(request, response);
						return;
				    }
				}
				for (String url : excludedPageArray) {
					if (path.contains(url)) {
						chain.doFilter(request, response);
						return;
					}
				}
				for(String url : withoutBackrigthUrl){
					if (path.contains(url)) {
						chain.doFilter(request, response);
						return;
					}
				}
				request.setAttribute("errorMessage", "无权访问该模块！");
				hres.sendRedirect(((HttpServletRequest) request).getContextPath()+"/noRightAccess.jsp");
				
				
				return;
			} else {
				for (String url : excludedPageArray) {
					if (path.contains(url)) {
						chain.doFilter(request, response);
						return;
					}
				}
				request.setAttribute("errorMessage", "请重新登录！");
				hres.sendRedirect(((HttpServletRequest) request).getContextPath()+"/time_out.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method init.
	 * 
	 * @param config
	 * @throws javax.servlet.ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		excludedPages = config.getInitParameter("excludedPages");
		if (StringUtils.isNotEmpty(excludedPages)) {
			excludedPageArray = excludedPages.split(",");
		}
		withoutBackrigthUrl = new ArrayList<String>();
		withoutBackrigthUrl.add("/frontIndex");
		withoutBackrigthUrl.add("/appSetting");
		withoutBackrigthUrl.add("/setUserDefined");
		withoutBackrigthUrl.add("/submit_userdefined");
		withoutBackrigthUrl.add("complat/userSetUpEdit");
		withoutBackrigthUrl.add("complat/userSetUpSave");
		withoutBackrigthUrl.add("/noRightAccess.jsp");
		withoutBackrigthUrl.add("/login/loginOut");
		
		return;
	}
}
