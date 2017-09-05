package com.gsww.uids.client.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;

import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.config.SSOConstants;
import com.gsww.uids.client.servlet.LogoutProxy;
import com.gsww.uids.client.servlet.RefreshTokenProxy;

/**
 * 单点登录的过滤器
 * 
 * @author taolm
 *
 */
public class GeneralSSOFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 解决中文乱码
		response.setCharacterEncoding("utf-8");

		HttpServletRequest req = (HttpServletRequest) request;
		
		// 过滤出4类页面：应用端单点登录、服务端开始的单点登录、退出、刷新token
		SSOConfig config = SSOConfig.getInstance();
		String servletUri = req.getServletPath();
		String pathInfo = req.getPathInfo();
		if ( StringUtils.isNotBlank(pathInfo) ) {
			servletUri = servletUri + pathInfo;
		}
		
		// 应用端单点登录
		if ( servletUri.equals(config.getLoginActionUri()) ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(SSOConstants.SSO_LOGIN_PROXY_SERVLET);
			dispatcher.forward(request, response); 	
		} else if ( servletUri.equals(config.getServerLoginUri()) ) { // 服务端开始的单点登录
			String code = req.getParameter(OAuth.OAUTH_CODE);
			String path = SSOConstants.GET_LONG_URL_SERVLET + "?" + OAuth.OAUTH_CODE + "=" + code;
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else {
			// 退出
			if ( servletUri.equals(config.getLogoutUri()) ) { 
				// 先通知服务端退出
				LogoutProxy logoutProxy = new LogoutProxy();
				logoutProxy.notityServerLogout(req);
			} 
			// 刷新token
			else if ( servletUri.equals(config.getRefreshTokenUri()) ) {
				// 刷新token
				RefreshTokenProxy refreshTokenProxy = new RefreshTokenProxy();
				refreshTokenProxy.doRefreshToken(req);
			}
			
			// 放行
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
