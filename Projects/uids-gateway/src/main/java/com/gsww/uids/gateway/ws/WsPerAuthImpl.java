package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinatelecom.udb.util.TimeHelper;
import com.gsww.uids.gateway.dao.perAuth.PerAuthDao;

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
	PerAuthDao perAuthDao = new PerAuthDao();
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	// 个人-票据认证
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {

		if (appmark != null && ticket != null && time != null && sign != null) {
			logger.info("WsPerAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
					+ "sign=" + sign);
			System.out.println("WsPerAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
					+ "sign=" + sign);
			/* 应用标识处理 */

			/* 票据处理 */

			/* 时间截处理 */
			TimeHelper timeHelper = new TimeHelper();
			time = timeHelper.getCurrentTime1();
			/* 获取appword */

			/* sign拼接 */

			/* MD5加密 */
			com.hanweb.sso.ldap.util.MD5 md5 = new com.hanweb.sso.ldap.util.MD5();
			sign = md5.encrypt(appmark + "appword" + time, time);
			/* 调用Dao查询票据信息，返回map.get("token") */

			perAuthDao.findTokenByTicket(ticket);
			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;

		} else {
			return new String("{" + "errormsg：错误信息" + "}");
		}

	}

	@Override
	// 个人-根据令牌获取用户详细信息
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		if (appmark != null && token != null && time != null && sign != null) {
			logger.info("WsPerAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
					+ "sign=" + sign);
			System.out.println("WsPerAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
					+ "sign=" + sign);
			/* appmark处理 */

			/* token获取 */

			/* 时间截处理 */
			TimeHelper timeHelper = new TimeHelper();
			time = timeHelper.getCurrentTime1();
			/* 获取appword */
			String appword = "";
			/* sign拼接 */

			/* MD5加密 */
			com.hanweb.sso.ldap.util.MD5 md5 = new com.hanweb.sso.ldap.util.MD5();
			sign = md5.encrypt(appmark + "appword" + time, time);

			/* 调用Dao查询票据信息，返回map.get("token") */

			perAuthDao.findUserByToken(token);
			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;
		}
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
		if (appmark != null && sign != null && loginname != null && password != null) {
			logger.info("WsPerAuth-userValidate：" + "appmark=" + appmark + "sign=" + sign + "loginname=" + loginname
					+ "password=" + password);
			System.out.println("WsPerAuth-userValidate：" + "appmark=" + appmark + "sign=" + sign + "loginname="
					+ loginname + "password=" + password);
			/* appmark处理 */

			/* 时间截处理 */
			TimeHelper timeHelper = new TimeHelper();
			String time = timeHelper.getCurrentTime1();
			/* 获取appword */
			String appword = "";
			/* sign拼接 */

			/* MD5加密 */
			com.hanweb.sso.ldap.util.MD5 md5 = new com.hanweb.sso.ldap.util.MD5();
			sign = md5.encrypt(appmark + "appword" + time, time);
			/* loginname处理 */

			/* password处理 */

			/* 调用Dao查询票据信息，返回map.get("token") */
			perAuthDao.findUserByLoginNameAndPassWord(loginname, password);

			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;
		}
		return null;
	}

	// --------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------

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
