package com.gsww.uids.gateway.ws;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.service.AppService;
import com.gsww.uids.gateway.service.AuthLogService;
import com.gsww.uids.gateway.service.OutsideUserService;
import com.gsww.uids.gateway.util.DateTime;
import com.gsww.uids.gateway.util.DateUtil;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.MD5;
import com.gsww.uids.gateway.util.MathUtil;
import com.gsww.uids.gateway.util.NumberUtil;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;
import com.gsww.uids.gateway.util.StringUtil;
import com.gsww.uids.gateway.util.UserUtil;

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
public class WsPerAuth {

	private static AuthLogService authLogService;
	private static AppService appService;
	private static OutsideUserService outsideUserService;
	static {
		authLogService = SpringContextHolder.getBean("authLogService");
		appService = SpringContextHolder.getBean("appService");
		outsideUserService = SpringContextHolder.getBean("outsideUserService");
	}
	protected Logger logger = Logger.getLogger(getClass());

	@WebMethod
	public String ticketValidate(String appmark, String ticket, String time,
			String sign) {
		// 打印日志
		this.logger.debug("<WsPerAuth接口-个人用户-ticketValidate-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsPerAuth接口-个人用户-ticketValidate>接收到请求内容:" + appmark
				+ ticket + time + sign);

		Map<String, String> map = new HashMap<String, String>();
		// 参数为必填项，不允许为空
		if ((!StringHelper.isNotBlack(appmark))
				|| (!StringHelper.isNotBlack(ticket))
				|| (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
		String token = "";
		// 通过ticket查询JisAuthLog
		JisAuthLog jisAuthLog = authLogService.findByTicket(ticket, 1);
		if (jisAuthLog != null) {
			// 设置ticket过期时间
			Date outTicketTime = jisAuthLog.getOuttickettime();
			if (DateTime.dayDiff(new Date(), outTicketTime) < 0L) {
				jisAuthLog.setSpec("票据已过期");
				map.put("errormsg", "票据已过期");
				// CacheUtil.removeKey(ticket, "perticket");
				return new JSONUtil().writeMapSJSON(map);
			}
			// 通过appmark查询Application
			Application appLication = appService.findByMark(appmark);
			// MD5加密规则
			MD5 md5 = new MD5();
			if (appLication != null) {
				String decodeSign = md5.decrypt(sign, time);
				// 秘钥匹配判断
				if (decodeSign.equals(appmark + appLication.getEncryptkey()
						+ time)) {
					String loginName = jisAuthLog.getLoginname();
					if (!(StringHelper.isNotBlack((StringUtil
							.getString(jisAuthLog.getLoginname()))))) {
						token = StringUtil.getString(jisAuthLog.getLoginname());
						if (jisAuthLog.getToken() == null) {
							jisAuthLog.setToken(token);
						}
					} else {
						// 设置token
						token = md5.encrypt(
								DateUtil.getCurrDate("MMddHHmmssSSS")
										+ MathUtil.randomNumeric(Integer
												.valueOf(6)),
								MathUtil.randomNumeric(Integer.valueOf(6)));
						jisAuthLog.setToken(token);
					}
					map.put("token", token);
					map.put("loginname", loginName);
					map.put("username", jisAuthLog.getUsername());
				} else {
					map.put("errormsg", "sign中的appmark或appkey值与参数不同");
				}
			} else {
				map.put("errormsg", "应用标识不正确");
				jisAuthLog.setSpec("应用标识不正确");
				jisAuthLog.setState(Integer.valueOf(2));
			}

		} else {
			map.put("errormsg", "该票据不存在或已过期");
		}
		this.logger.info("<WsPerAuth接口-个人用户-ticketValidate>返回结果："
				+ new JSONUtil().writeMapSJSON(map) + "---"
				+ DateUtil.getCurrDateTime());
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String findUserByToken(String appmark, String token, String time,
			String sign) {
		// 打印日志
		this.logger.debug("<WsPerAuth接口-个人用户-findUserByToken-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsPerAuth接口-个人用户-findUserByToken>接收到请求内容:" + appmark
				+ token + time + sign);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark))
				|| (!StringHelper.isNotBlack(token))
				|| (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign))) {

			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		JisAuthLog jisAuthLog = authLogService.findByToken(token, 1);
		if (jisAuthLog == null) {
			map.put("errormsg", "没有该令牌");
			return new JSONUtil().writeMapSJSON(map);
		}

		int userType = jisAuthLog.getUsertype().intValue();
		if (userType == 1) {
			OutsideUser outSideUser = outsideUserService
					.findByLoginName(jisAuthLog.getLoginname());
			map.put("uuid", outSideUser.getUuid());
			map.put("loginname", outSideUser.getLoginname());
			map.put("mobile", StringUtil.getString(outSideUser.getMobile()));
			map.put("email", StringUtil.getString(outSideUser.getEmail()));
			map.put("name", StringUtil.getString(outSideUser.getName()));
			map.put("sex", StringUtil.getString(outSideUser.getSex()));
			map.put("cardid",
					StringUtil.getString(outSideUser.getPapersnumber()));
			map.put("birthdate",
					StringUtil.getString(outSideUser.getBirthdate()));
			map.put("phone", StringUtil.getString(outSideUser.getPhone()));
			map.put("address", StringUtil.getString(outSideUser.getAddress()));
			map.put("workunit", StringUtil.getString(outSideUser.getWorkunit()));
			map.put("msn", StringUtil.getString(outSideUser.getMsn()));
			map.put("qq", StringUtil.getString(outSideUser.getQq()));
			map.put("isauth", StringUtil.getString(outSideUser.getIsauth()));
			map.put("usertype", "1");
			map.put("authstate",
					StringUtil.getString(outSideUser.getAuthstate()));
		} else {
			map.put("errormsg", "登录名不是个人用户");
		}
		this.logger.info("<WsPerAuth接口-个人用户-findUserByToken>返回结果："
				+ new JSONUtil().writeMapSJSON(map) + "---"
				+ DateUtil.getCurrDateTime());
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String generateTicket(String appmark, String token, String time,
			String sign, String proxyapp) {
		// 打印日志
		this.logger.info("<WsPerAuth接口-个人用户-generateTicket>接收到请求内容:" + appmark
				+ token + time + sign + proxyapp);
		this.logger.debug("<WsPerAuth接口-个人用户-generateTicket-start>"
				+ DateUtil.getCurrDateTime());
		Map<String, String> map = new HashMap<String, String>();
		if ((StringHelper.isNotBlack(appmark))
				|| (StringHelper.isNotBlack(token))
				|| (StringHelper.isNotBlack(sign))
				|| (StringHelper.isNotBlack(proxyapp))
				|| (StringHelper.isNotBlack(time))) {
			Application appLication = appService.findByMark(proxyapp);
			if (appLication != null) {
				JisAuthLog jisAuthLog = authLogService.findByToken(token, 1);
				if (jisAuthLog != null) {
					JisAuthLog jisAuthLogNew = new JisAuthLog();
					jisAuthLogNew.setAppid(appLication.getIid());
					jisAuthLogNew.setAppmark(proxyapp);
					jisAuthLogNew.setAuthtype(Integer.valueOf(1));
					jisAuthLogNew.setCreatetime(new Date());
					jisAuthLogNew.setLoginname(jisAuthLog.getLoginname());
					long times = new Date().getTime() + NumberUtil.getLong(300)
							* 1000L;
					Date outTime = new Date(times);
					jisAuthLogNew.setOuttickettime(outTime);

					String ticket = MD5.encodeMd5(proxyapp
							+ appLication.getEncryptkey()
							+ jisAuthLog.getLoginname() + new Date());
					jisAuthLogNew.setTicket(ticket);
					jisAuthLogNew.setUserId(jisAuthLog.getUserId());
					jisAuthLogNew.setUsername(jisAuthLog.getUsername());
					jisAuthLogNew.setUsertype(jisAuthLog.getUsertype());
					jisAuthLogNew.setState(Integer.valueOf(0));
					if (ticket != null && jisAuthLogNew != null)
						map.put("ticket", ticket);
					else
						map.put("errormsg", "票据生成失败");
				} else {
					map.put("errormsg", "没有该令牌");
				}
			} else {
				map.put("errormsg", "接入应用不存在");
			}
			this.logger.info("<WsPerAuth接口-个人用户-generateTicket>返回结果："
					+ new JSONUtil().writeMapSJSON(map) + "---"
					+ DateUtil.getCurrDateTime());
			return new JSONUtil().writeMapSJSON(map);
		} else {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
	}

	@WebMethod
	public String userValidate(String appmark, String time, String sign,
			String loginname, String password)
			throws UnsupportedEncodingException {
		// 打印日志
		this.logger.debug("<WsPerAuth接口-个人用户-userValidate-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsPerAuth接口-个人用户-userValidate>接收到请求内容:" + appmark
				+ time + sign + loginname + password);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark))
				|| (!StringHelper.isNotBlack(sign))
				|| (!StringHelper.isNotBlack(password)) || (time == null)) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		Application appLication = appService.findByMark(appmark);
		if (appLication == null) {
			map.put("errormsg", "应用标识不正确");
		}
		String ip = "";
		MD5 md5 = new MD5();
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			if (UserUtil.isMobilelegal(loginname)) {
				OutsideUser outsideUser = outsideUserService
						.findByMobile(loginname);
				if (outsideUser != null) {
					loginname = outsideUser.getLoginname();
				}
			} else if (UserUtil.isIDnumberlegal(loginname)) {
				OutsideUser outsideUser = outsideUserService
						.findByIdCard(loginname);
				if (outsideUser != null) {
					loginname = outsideUser.getLoginname();
				}
			}
			OutsideUser outsideUser = outsideUserService.checkUserLogin(
					loginname, password, ip);
			if (outsideUser != null) {
				jisAuthLog.setAppmark(appmark);
				jisAuthLog.setLoginname(outsideUser.getLoginname());
				jisAuthLog.setUsertype(Integer.valueOf(1));
				jisAuthLog.setAuthtype(Integer.valueOf(2));
				String token = md5.encrypt(loginname + appmark, loginname);
				jisAuthLog.setToken(token);

				@SuppressWarnings("unused")
				long tokenEffectiveTime = NumberUtil.getLong(300);
				map.put("token", token);
				map.put("loginname", loginname);
				map.put("username", outsideUser.getName());
			} else {
				map.put("errormsg", "登录名或密码不正确");
			}
		} catch (LoginException e) {
			map.put("errormsg", "登录名未被激活");
		}
		this.logger.info("<WsPerAuth接口-个人用户-userValidate>返回结果："
				+ new JSONUtil().writeMapSJSON(map) + "---"
				+ DateUtil.getCurrDateTime());
		return new JSONUtil().writeMapSJSON(map);
	}
}
