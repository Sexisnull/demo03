package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.gsww.sag.util.MD5;

/**
 * Created on 2017-09-07
 * <p>
 * Title:统一身份认证管理平台
 * </p>
 * <p>
 * Description: 个人用户认证接口实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: wanwei.com
 * </p>
 * 
 */
// 接口地址：http://www.gszwfw.gov.cn/gsjis/services/PerAuth?wsdl
// 本地接口地址：http://127.0.0.1:8081/services/WsPerAuth?wsdl
@WebService(serviceName = "WsPerAuth", endpointInterface = "com.gsww.uids.gateway.ws.WsPerAuth", portName = "WsPerAuthPort", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WsPerAuth")
public class WsPerAuthImpl implements WsPerAuth {
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	// 个人-票据认证
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		/**
		 * 字段 ---- 中文 ---- 数据库 appmark----应用标识----jis_application ticket ---- 票据
		 * ---- time ---- ---- sign ---- ----
		 * 
		 */
		logger.info("WsPerAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time + "sign="
				+ sign);
		if (appmark != null && ticket != null && time != null && sign != null) {

			/* MD5加密 */
			MD5 md5 = new MD5();

		} else {
			return new String("{" + "errormsg：错误信息" + "}");
		}
		System.out.println("WsPerAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
				+ "sign=" + sign);
		return "hello";
	}

	@Override
	// 个人-根据令牌获取用户详细信息
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		logger.info("WsPerAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time + "sign="
				+ sign);
		System.out.println("WsPerAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
				+ "sign=" + sign);
		return null;
	}

	@Override
	// 个人-根据令牌获取第三方接口资源票据
	public String generateTicket(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign,
			@WebParam(name = "proxyapp") String proxyapp) {
		logger.info("WsPerAuth-generateTicket：" + "appmark=" + appmark + "token=" + token + "time=" + time + "sign="
				+ sign);
		System.out.println("WsPerAuth-generateTicket：" + "appmark=" + appmark + "token=" + token + "time=" + time
				+ "sign=" + sign);
		return null;
	}

	@Override
	// 个人-用户认证
	public String userValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "sign") String sign,
			@WebParam(name = "loginname") String loginname, @WebParam(name = "password") String password) {
		logger.info("WsPerAuth-userValidate：" + "appmark=" + appmark + "sign=" + sign + "loginname=" + loginname
				+ "password=" + password);
		System.out.println("WsPerAuth-userValidate：" + "appmark=" + appmark + "sign=" + sign + "loginname=" + loginname
				+ "password=" + password);
		return null;
	}

	// 个人-用户信息新增、修改
	@WebMethod(operationName = "userSynResponse")
	public String userSyn(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "args") String args,
			@WebParam(name = "oprty") String oprty) {
		logger.info("WsPerAuth-userSyn：" + "appmark=" + appmark + "time=" + time + "sign=" + sign + "args=" + args
				+ "oprty=" + oprty);
		System.out.println("WsPerAuth-userSyn：" + "appmark=" + appmark + "time=" + time + "sign=" + sign + "args="
				+ args + "oprty=" + oprty);
		return null;
	}

	// 个人-添加、变更绑定信息
	@WebMethod(operationName = "letBindKnownResponse")
	public String letBindKnown(@WebParam(name = "appmark") String appmark, @WebParam(name = "time") String time,
			@WebParam(name = "sign") String sign, @WebParam(name = "userLoginName") String userLoginName,
			@WebParam(name = "state") String state) {
		logger.info("WsPerAuth-letBindKnown：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
				+ "userLoginName=" + userLoginName + "state=" + state);
		System.out.println("WsPerAuth-letBindKnown：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
				+ "userLoginName=" + userLoginName + "state=" + state);
		return null;
	}
}
