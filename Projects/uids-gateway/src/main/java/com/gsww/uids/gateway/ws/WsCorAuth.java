package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created on 2017-09-08
 * <p>
 * Title:统一身份认证管理平台
 * </p>
 * <p>
 * Description: 法人用户认证接口类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: wanwei.com
 * </p>
 * 
 */
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@WebService(name = "WsCorAuth", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WsCorAuth")
public interface WsCorAuth {
	// 法人-票据认证
	@WebMethod(operationName = "ticketValidateRequest")
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign);

	// 法人-根据令牌获取用户详细信息
	@WebMethod(operationName = "findUserByTokenResponse")
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign);

	// 法人-根据令牌获取第三方接口资源票据
	@WebMethod(operationName = "generateTicketResponse")
	public String generateTicket(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign,
			@WebParam(name = "proxyapp") String proxyapp);

	// 法人-用户认证
	@WebMethod(operationName = "userValidateRequest")
	public String userValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "loginname") String loginname,
			@WebParam(name = "password") String password);

	// 法人-用户信息新增、修改
	@WebMethod(operationName = "userSynRequest")
	public String userSyn(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "args") String args,
			@WebParam(name = "oprty") String oprty);

	// 法人-添加、变更绑定信息
	@WebMethod(operationName = "letBindKnownRequest")
	public String letBindKnown(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "userLoginName") String userLoginName,
			@WebParam(name = "state") String state);
}
