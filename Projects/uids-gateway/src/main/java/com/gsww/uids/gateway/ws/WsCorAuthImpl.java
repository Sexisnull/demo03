package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * Created on 2017-09-08
 * <p>
 * Title:统一身份认证管理平台
 * </p>
 * <p>
 * Description: 法人用户认证接口实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: wanwei.com
 * </p>
 * 
 */
// 接口地址：http://www.gszwfw.gov.cn/gsjis/services/CorAuth?wsdl
// 本地接口地址：http://127.0.0.1:8081/services/WsCorAuth?wsdl
@WebService(serviceName = "WsCorAuth", endpointInterface = "com.gsww.uids.gateway.ws.WsCorAuth", portName = "WsCorAuthPort", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WsCorAuth")
public class WsCorAuthImpl implements WsCorAuth {
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	// 法人-票据认证
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		// TODO Auto-generated method stub
		logger.info("WsCorAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time + "sign="
				+ sign);
		System.out.println("WsCorAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
				+ "sign=" + sign);
		return null;
	}

	@Override
	// 法人-根据令牌获取用户详细信息
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		// TODO Auto-generated method stub
		logger.info("WsCorAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time + "sign="
				+ sign);
		System.out.println("WsCorAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
				+ "sign=" + sign);
		return null;
	}

	@Override
	// 法人-根据令牌获取第三方接口资源票据
	public String generateTicket(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign,
			@WebParam(name = "proxyapp") String proxyapp) {
		// TODO Auto-generated method stub
		logger.info("WsCorAuth-generateTicket：" + "appmark=" + appmark + "token=" + token + "time=" + time + "sign="
				+ sign + "proxyapp=" + proxyapp);
		System.out.println("WsCorAuth-generateTicket：" + "appmark=" + appmark + "token=" + token + "time=" + time
				+ "sign=" + sign + "proxyapp=" + proxyapp);
		return null;
	}

	@Override
	// 法人-用户认证
	public String userValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "loginname") String loginname,
			@WebParam(name = "password") String password) {
		// TODO Auto-generated method stub
		logger.info("WsCorAuth-userValidate：" + "appmark=" + appmark + "time=" + time + "sign=" + sign + "loginname="
				+ loginname + "password=" + password);
		System.out.println("WsCorAuth-userValidate：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
				+ "loginname=" + loginname + "password=" + password);
		return null;
	}

	// 法人-用户信息新增、修改
	@WebMethod(operationName = "userSynResponse")
	public String userSyn(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "args") String args,
			@WebParam(name = "oprty") String oprty) {
		logger.info("WsCorAuth-userSyn：" + "appmark=" + appmark + "time=" + time + "sign=" + sign + "args=" + args
				+ "oprty=" + oprty);
		System.out.println("WsCorAuth-userSyn：" + "appmark=" + appmark + "time=" + time + "sign=" + sign + "args="
				+ args + "oprty=" + oprty);
		return null;
	}

	// 法人-添加、变更绑定信息
	@WebMethod(operationName = "letBindKnownResponse")
	public String letBindKnown(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "userLoginName") String userLoginName,
			@WebParam(name = "state") String state) {
		logger.info("WsCorAuth-letBindKnown：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
				+ "userLoginName=" + userLoginName + "state=" + state);
		System.out.println("WsCorAuth-letBindKnown：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
				+ "userLoginName=" + userLoginName + "state=" + state);
		return null;
	}
}
