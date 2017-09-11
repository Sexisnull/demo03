package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created on 2017-09-07
 * <p>
 * Title:统一身份认证管理平台
 * </p>
 * <p>
 * Description: 个人用户认证接口类
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
@WebService(name = "WsPerAuth", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WsPerAuth")
public interface WsPerAuth {
	// 个人-票据认证
	@WebMethod(operationName = "ticketValidateResponse")
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign);

	// 个人-根据令牌获取用户详细信息
	@WebMethod(operationName = "findUserByTokenRequest")
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign);

	// 个人-根据令牌获取第三方接口资源票据
	@WebMethod(operationName = "generateTicketRequest")
	public String generateTicket(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign,
			@WebParam(name = "proxyapp") String proxyapp);

	// 个人-用户认证
	@WebMethod(operationName = "userValidateResponse")
	public String userValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "sign") String sign,
			@WebParam(name = "loginname") String loginname, @WebParam(name = "password") String password);

	// 个人-用户信息新增、修改
	@WebMethod(operationName = "userSynResponse")
	public String userSyn(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "args") String args,
			@WebParam(name = "oprty") String oprty);

	// 个人-添加、变更绑定信息
	@WebMethod(operationName = "letBindKnownResponse")
	public String letBindKnown(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "userLoginName") String userLoginName,
			@WebParam(name = "state") String state);
}
