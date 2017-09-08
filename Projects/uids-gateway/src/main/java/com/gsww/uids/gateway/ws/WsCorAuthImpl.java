package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinatelecom.udb.util.TimeHelper;
import com.gsww.uids.gateway.dao.corAuth.CorAuthDao;

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
	CorAuthDao authDao = new CorAuthDao();
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	// 法人-票据认证
	public String ticketValidate(@WebParam(name = "appmark") String appmark, @WebParam(name = "ticket") String ticket,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		// TODO Auto-generated method stub
		if (appmark != null && ticket != null && time != null && sign != null) {
			logger.info("WsCorAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
					+ "sign=" + sign);
			System.out.println("WsCorAuth-ticketValidate：" + "appmark=" + appmark + "ticket=" + ticket + "time=" + time
					+ "sign=" + sign);
			/* 应用标识处理 */
			appmark = "";
			/* 票据处理 */
			ticket = "";
			/* 时间截处理 */
			TimeHelper timeHelper = new TimeHelper();
			time = timeHelper.getCurrentTime1();
			/* 获取appword */
			String appword = "";
			/* sign加密前拼接 */

			/* MD5加密 */
			com.hanweb.sso.ldap.util.MD5 md5 = new com.hanweb.sso.ldap.util.MD5();
			sign = md5.encrypt(appmark + "appword" + time, time);
			/* 调用Dao查询票据信息，返回map.get("token") */

			authDao.findTokenByTicket(ticket);
			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;
		}
		return null;
	}

	@Override
	// 法人-根据令牌获取用户详细信息
	public String findUserByToken(@WebParam(name = "appmark") String appmark, @WebParam(name = "token") String token,
			@WebParam(name = "time") String time, @WebParam(name = "sign") String sign) {
		// TODO Auto-generated method stub
		if (appmark != null && token != null && time != null && sign != null) {
			logger.info("WsCorAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
					+ "sign=" + sign);
			System.out.println("WsCorAuth-findUserByToken：" + "appmark=" + appmark + "token=" + token + "time=" + time
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
			/* 调用Dao层查询用户全部信息，返回Map */
			authDao.findUserByToken(token);
			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;
		}
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
		if (appmark != null && time != null && sign != null && loginname != null && password != null) {
			logger.info("WsCorAuth-userValidate：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
					+ "loginname=" + loginname + "password=" + password);
			System.out.println("WsCorAuth-userValidate：" + "appmark=" + appmark + "time=" + time + "sign=" + sign
					+ "loginname=" + loginname + "password=" + password);
			/* appmark处理 */

			/* 时间截处理 */
			TimeHelper timeHelper = new TimeHelper();
			time = timeHelper.getCurrentTime1();
			/* 获取appword */
			String appword = "";
			/* sign拼接 */

			/* MD5加密 */
			com.hanweb.sso.ldap.util.MD5 md5 = new com.hanweb.sso.ldap.util.MD5();
			sign = md5.encrypt(appmark + "appword" + time, time);
			/* loginname处理 */

			/* password处理 */

			/* 调用Dao查询票据信息，返回map.get("token") */
			/* 调用Dao层通过用户名和密码查询用户信息，返回Map */
			authDao.findUserByLoginNameAndPassWord(loginname, password);
			/* 处理数据拼接成String类型的Json数据 */
			String str = "";

			return str;
		}
		return null;
	}

	// ------------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------------------

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
