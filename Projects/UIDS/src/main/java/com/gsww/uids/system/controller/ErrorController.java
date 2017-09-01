package com.gsww.uids.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常页面处理
 * 
 * @author simplife
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorController implements HandlerExceptionResolver {

	/**
	 * 页面不存在错误
	 * 
	 * @return
	 */
	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String exception404() {
		
		return "error/404";
	}
	
	/**
	 * 系统内部错误跳转页面
	 */
	@Override 
	@RequestMapping(value="/500", method = RequestMethod.GET)
	public ModelAndView resolveException(HttpServletRequest request,   
            HttpServletResponse response, Object handler, Exception ex) {
		
		ex.printStackTrace();
		
		if ( ex instanceof UnauthenticatedException ) {
			// 未通过身份验证错误
			return new ModelAndView("error/unauthenticated");
		} else if ( ex instanceof UnauthorizedException ) {
			// 未授权错误
			return new ModelAndView("error/unauthorized");
		} else {		
			return new ModelAndView("error/500");
		}
	}	
}