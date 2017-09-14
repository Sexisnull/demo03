/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.webtag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.gsww.jup.SpringContextHolder;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysOperatorService;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-util</p>
 * <p>创建时间 : 2014-7-28 上午10:08:32</p>
 * <p>类描述 : 操作按钮标签        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */

public class OperatorTag extends TagSupport {

	/** 
	 * serialVersionUID :  
	 */
	private static final long serialVersionUID = 5046664949848454867L;
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private String baseUrl = "";
	private JspWriter out;
	private String menuId;
	private String tabIndex;
	private String operatorType;

	private SysOperatorService sysOperatorService = (SysOperatorService) SpringContextHolder.getApplicationContext().getBean("sysOperatorService");

	private void init() {
		request = (HttpServletRequest) pageContext.getRequest();
		baseUrl = request.getContextPath();
		out = pageContext.getOut();
	}

	public int doStartTag() throws JspException {
		init();
		// 1.创建输出的html的流
		StringBuffer sb = new StringBuffer();
		// 2.取到系统当前session
		SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		// 3.判断session为空的话，则什么按钮都不输出
		if (sysUserSession == null) {
			return SKIP_BODY;
		}
		// 4.session不为空的话，则开始给输出流中写入相应的html代码
		try {
			List<Map<String, Object>> operList = sysOperatorService.getOptionByPageName(sysUserSession.getRoleIds(),menuId,operatorType,tabIndex);
			sb.append(this.getButtonHtml(operList,operatorType));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	private String getButtonHtml(List<Map<String, Object>> operList, String oType) {
		StringBuffer returnString = new StringBuffer();
		if(oType.equals("1")){
			returnString.append("<ul class=\"list-Topbtn\">");
			for (Map<String, Object> oper : operList) {
				returnString.append("<li class=\""+oper.get("operator_image")+"\"  onclick=\""+oper.get("operator_url")+"\" ><a title=\""+oper.get("operator_name")+"\">"+oper.get("operator_name")+"</a></li>");
				returnString.append("&nbsp&nbsp");
			}
			returnString.append("</ul>");
		}else if(oType.equals("2")){
			returnString.append("<div class=\"listOper\"><ul>");
			for (Map<String, Object> oper : operList) {
				returnString.append("<li class=\""+oper.get("operator_image")+"\" onclick=\""+oper.get("operator_url")+"\" ><i></i><a>"+oper.get("operator_name")+"</a></li>");
				//returnString.append("<li class=\"list-line\"></li>");
			}
			returnString.append("</ul></div>");
		}
		return returnString.toString();
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * 方法描述 : 该方法实现的功能描述
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
