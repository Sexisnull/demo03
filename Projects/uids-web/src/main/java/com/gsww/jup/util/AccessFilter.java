package com.gsww.jup.util;

import java.io.IOException;

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
		try {
			if (((HttpServletRequest) request).getSession().getAttribute("sysUserSession") != null) {
				SysContent.setRequest((HttpServletRequest) request);
				chain.doFilter(request, response);
				return;
			} else {
				for (String url : excludedPageArray) {
					if (path.contains(url)) {
						chain.doFilter(request, response);
						return;
					}
				}
				HttpServletResponse hres = (HttpServletResponse) response;
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
		return;
	}
}
