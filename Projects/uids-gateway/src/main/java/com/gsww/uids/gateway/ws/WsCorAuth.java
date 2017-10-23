package com.gsww.uids.gateway.ws;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.service.AppService;
import com.gsww.uids.gateway.service.AuthLogService;
import com.gsww.uids.gateway.service.CorporationService;
import com.gsww.uids.gateway.util.DateTime;
import com.gsww.uids.gateway.util.DateUtil;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.MD5;
import com.gsww.uids.gateway.util.MathUtil;
import com.gsww.uids.gateway.util.NumberUtil;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;
import com.gsww.uids.gateway.util.StringUtil;

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
public class WsCorAuth {
	private static AuthLogService authLogService;
	private static AppService appService;
	private static CorporationService corporationService;
	static {
		authLogService = SpringContextHolder.getBean("authLogService");
		appService = SpringContextHolder.getBean("appService");
		corporationService = SpringContextHolder.getBean("corporationService");
	}
	protected Logger logger = Logger.getLogger(getClass());

	@WebMethod
	public String ticketValidate(String appmark, String ticket, String time,
			String sign) {
		// 打印日志
		this.logger.debug("<WsCorAuth接口-法人用户-ticketValidate-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsCorAuth接口-法人用户-ticketValidate>接收到请求内容:" + appmark
				+ ticket + time + sign);
		// 声明map用于存储返回信息--最终转换为json格式
		Map<String, String> map = new HashMap<String, String>();
		// 判断获取数据是否为空
		if ((StringHelper.isNotBlack(appmark))
				&& (StringHelper.isNotBlack(ticket))
				&& (StringHelper.isNotBlack(time))
				&& (StringHelper.isNotBlack(sign))) {
			// 通过ticket, userType获取jis_authlog中的信息 --2：政府用户
			JisAuthLog jisAuthLog = authLogService.findByTicket(ticket, 2);
			if (jisAuthLog != null) {
				Date outTime = jisAuthLog.getOuttickettime();
				if (DateTime.dayDiff(new Date(), outTime) < 0L) {
					// System.out.println("判断票据是否过期--过期");
					jisAuthLog.setSpec("票据已过期");
					map.put("errormsg", "票据已过期");
					return new JSONUtil().writeMapSJSON(map);
				}
				Application appLication = appService.findByMark(appmark);
				MD5 md5 = new MD5();
				String token = "";
				if (appLication != null) {
					String decodeSign = md5.decrypt(sign, time);
					if (decodeSign.equals(appmark + appLication.getEncryptkey()
							+ time)) {
						// if (true) { 放过判断规则
						String loginName = jisAuthLog.getLoginname();
						if (!(StringHelper.isNotBlack((StringUtil
								.getString(jisAuthLog.getLoginname()))))) {
							token = StringUtil.getString(jisAuthLog
									.getLoginname());
							if (jisAuthLog.getToken() == null) {
								jisAuthLog.setToken(token);
								jisAuthLog.setState(Integer.valueOf(1));
							}
						} else {
							token = md5.encrypt(
									DateUtil.getCurrDate("MMddHHmmssSSS")
											+ MathUtil.randomNumeric(Integer
													.valueOf(6)),
									MathUtil.randomNumeric(Integer.valueOf(6)));
							jisAuthLog.setToken(token);
							jisAuthLog.setState(Integer.valueOf(1));

						}
						map.put("token", token);
						map.put("loginname", loginName);
						map.put("username", jisAuthLog.getUsername());
					} else {
						map.put("errormsg", "sign中的appmark或appkey值与参数不同");
						jisAuthLog.setSpec("sign中的appmark或appkey值与参数不同");
						jisAuthLog.setState(Integer.valueOf(2));
					}
				} else {
					map.put("errormsg", "应用标识不正确");
					jisAuthLog.setSpec("应用标识不正确");
					jisAuthLog.setState(Integer.valueOf(2));
				}
			} else {
				map.put("errormsg", "该票据不存在或已过期");
			}
			this.logger.info("<WsCorAuth接口-法人用户-ticketValidate>返回结果："
					+ new JSONUtil().writeMapSJSON(map));
			return new JSONUtil().writeMapSJSON(map);

		} else {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
	}

	@WebMethod
	public String findUserByToken(String appmark, String token, String time,
			String sign) {
		this.logger.debug("<WsCorAuth接口-法人用户-findUserByToken-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsCorAuth接口-法人用户-findUserByToken>接收到请求内容:" + appmark
				+ token + time + sign);// 打印日志
		Map<String, String> map = new HashMap<String, String>();
		if ((StringHelper.isNotBlack(appmark))
				|| (StringHelper.isNotBlack(token))
				|| (StringHelper.isNotBlack(time))
				|| (StringHelper.isNotBlack(sign))) {// 非空判断
			JisAuthLog jisAuthLog = authLogService.findByToken(token, 2);
			if (jisAuthLog == null) {
				map.put("errormsg", "没有该令牌");
				return new JSONUtil().writeMapSJSON(map);
			} else {
				int userType = jisAuthLog.getUsertype().intValue();
				if (userType == 2) {
					Corporation corporation = corporationService
							.findByLoginName(jisAuthLog.getLoginname());
					if (corporation != null) {
						map.put("uuid", corporation.getUuid());
						map.put("type", corporation.getType() + "");
						map.put("name", corporation.getName());
						map.put("regnumber", StringUtil.getString(corporation
								.getRegnumber()));
						map.put("orgnumber", StringUtil.getString(corporation
								.getOrgnumber()));
						map.put("scope",
								StringUtil.getString(corporation.getScope()));
						map.put("regorgname", StringUtil.getString(corporation
								.getRegorgname()));
						map.put("loginname", corporation.getLoginname());
						map.put("realname",
								StringUtil.getString(corporation.getRealname()));
						map.put("mobile",
								StringUtil.getString(corporation.getMobile()));
						map.put("phone",
								StringUtil.getString(corporation.getPhone()));
						map.put("sex",
								StringUtil.getString(corporation.getSex()));
						map.put("address",
								StringUtil.getString(corporation.getAddress()));
						map.put("cardid", StringUtil.getString(corporation
								.getCardnumber()));
						map.put("email",
								StringUtil.getString(corporation.getEmail()));
						map.put("isauth",
								StringUtil.getString(corporation.getIsauth()));
						map.put("authstate", StringUtil.getString(corporation
								.getAuthstate()));
						map.put("usertype", "2");
						map.put("declarationiid",
								Integer.valueOf(NumberUtil.getInt(corporation
										.getDeclarationIid())) + "");
						map.put("nation",
								StringUtil.getString(corporation.getNation()));
					}
				} else {
					map.put("errormsg", "登录名不是法人用户");
				}
			}
			this.logger.info("<WsCorAuth接口-法人用户-findUserByToken>返回结果："
					+ new JSONUtil().writeMapSJSON(map));
			return new JSONUtil().writeMapSJSON(map);
		} else {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
	}

	@WebMethod
	public String generateTicket(String appmark, String token, String time,
			String sign, String proxyapp) {
		this.logger.debug("<WsCorAuth接口-法人用户-generateTicket-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsCorAuth接口-法人用户-generateTicket>接收到请求内容:" + appmark
				+ token + time + sign + proxyapp);// 打印日志
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark))
				|| (!StringHelper.isNotBlack(token))
				|| (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign))
				|| (!StringHelper.isNotBlack(proxyapp))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		Application appLication = appService.findByMark(proxyapp);
		if (appLication != null) {
			JisAuthLog jisAuthLog = authLogService.findByToken(token, 2);
			if (jisAuthLog != null) {
				JisAuthLog jisAuthLogNew = new JisAuthLog();
				jisAuthLogNew.setAppid(appLication.getIid());
				jisAuthLogNew.setAppmark(proxyapp);
				jisAuthLogNew.setAuthtype(Integer.valueOf(1));
				jisAuthLogNew.setCreatetime(new Date());
				jisAuthLogNew.setLoginname(jisAuthLog.getLoginname());
				// 设置Ticket有效时间
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
				map.put("errormsg", "无效令牌");
			}
		} else {
			map.put("errormsg", "接入应用不存在");
		}
		this.logger.info("<WsCorAuth接口-法人用户-generateTicket>返回结果："
				+ new JSONUtil().writeMapSJSON(map));
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String userValidate(String appmark, String time, String sign,
			String loginname, String password) {
		this.logger.debug("<WsCorAuth接口-法人用户-userValidate-start>"
				+ DateUtil.getCurrDateTime());
		this.logger.info("<WsCorAuth接口-法人用户-userValidate>接收到请求内容:" + appmark
				+ time + sign + loginname + password);// 打印日志
		Map<String, String> map = new HashMap<String, String>();
		if ((StringHelper.isNotBlack(appmark))
				|| (StringHelper.isNotBlack(sign))
				|| (StringHelper.isNotBlack(loginname))
				|| (StringHelper.isNotBlack(password)) || (time != null)) {
			Application appLication = appService.findByMark(appmark);
			if (appLication == null) {
				System.out.println("appLication==" + appLication);
				map.put("errormsg", "应用标识不正确");
			}
			String ip = "";
			MD5 md5 = new MD5();
			JisAuthLog jisAuthLog = new JisAuthLog();
			try {
				Corporation corporation = corporationService.checkUserLogin(
						loginname, password, ip);
				if (corporation != null) {
					jisAuthLog.setAppmark(appmark);
					jisAuthLog.setLoginname(corporation.getLoginname());
					jisAuthLog.setUsertype(Integer.valueOf(2));
					jisAuthLog.setAuthtype(Integer.valueOf(2));
					String token = md5.encrypt(loginname + appmark, loginname);
					jisAuthLog.setToken(token);

					map.put("token", token);
					map.put("loginname", loginname);
					map.put("username", corporation.getName());
				} else {
					map.put("errormsg", "登录名或密码不正确");
				}
			} catch (LoginException e) {
				map.put("errormsg", "登录名未被激活");
			}
			this.logger.info("<WsCorAuth接口-法人用户-userValidate>返回结果："
					+ new JSONUtil().writeMapSJSON(map));
			return new JSONUtil().writeMapSJSON(map);
		} else {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
	}
}
