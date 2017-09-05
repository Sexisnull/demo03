package com.gsww.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String exception404() {
		
		return "error/404";
	}
	
	@Override 
	@RequestMapping(value="/500", method = RequestMethod.GET)
	public ModelAndView resolveException(HttpServletRequest request,   
            HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		return new ModelAndView("error/500");
	}
	
}
