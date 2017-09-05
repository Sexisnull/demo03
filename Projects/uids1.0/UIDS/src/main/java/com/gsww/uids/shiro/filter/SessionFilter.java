package com.gsww.uids.shiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

/**
 * 用于检查用户是否登录了系统或者过期的过滤器
 * 这里只处理ajax请求
 * 
 * @author taolm
 *
 */
public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response; 
        
        // 如果没有通过身份认证 
        if ( !SecurityUtils.getSubject().isAuthenticated() ) {
        	// 如果是ajax请求响应头,会有 : x-requested-with
            if ( "XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("x-requested-with")) ) {
            	// 在响应头设置session状态  
                httpServletResponse.setHeader("sessionstatus", "timeout");
                httpServletResponse.setHeader("basepath", httpServletRequest.getContextPath()); 
            }  
        }  
  
        chain.doFilter(request, response);		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
