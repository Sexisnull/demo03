package com.gsww.uids.gateway.ws;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.constant.Settings;
import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.service.AppService;
import com.gsww.uids.gateway.service.AuthLogService;
import com.gsww.uids.gateway.service.OutsideUserService;
import com.gsww.uids.gateway.sso.ldap.util.MD5;
import com.gsww.uids.gateway.util.CacheUtil;
import com.gsww.uids.gateway.util.DateTime;
import com.gsww.uids.gateway.util.DateUtil;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.MathUtil;
import com.gsww.uids.gateway.util.NumberUtil;
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
	private AppService appService = new AppService();
	private AuthLogService authLogService = new AuthLogService();
	private OutsideUserService outSideUserService = new OutsideUserService();

	protected Logger logger = Logger.getLogger(getClass());

	@WebMethod
	public String ticketValidate(String appmark, String ticket, String time, String sign) {
		System.out.println("appmark=" + appmark + "ticket=" + ticket + "time=" + time + "sign=" + sign);
		this.logger.debug("=ticketValidate=start==" + DateUtil.getCurrDateTime());

		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(ticket))
				|| (!StringHelper.isNotBlack(time)) || (!StringHelper.isNotBlack(sign))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
		String token = "";
		JisAuthLog jisAuthLog = this.authLogService.findByTicket(ticket, 1);
		if (jisAuthLog != null) {
			Date outTicketTime = jisAuthLog.getOutTicketTime();

			if (DateTime.dayDiff(new Date(), outTicketTime) < 0L) {
				map.put("errormsg", "票据已过期");
				// CacheUtil.removeKey(ticket, "perticket");
				return new JSONUtil().writeMapSJSON(map);
			}
			Application appLication = this.appService.findByMark(appmark);
			MD5 md5 = new MD5();
			if (appLication != null) {
				String decodeSign = md5.decrypt(sign, time);
				if (decodeSign.equals(appmark + appLication.getEncryptKey() + time)) {
					String loginName = jisAuthLog.getLoginName();
					if (StringUtil.isNotEmpty(StringUtil.getString(jisAuthLog.getLoginName()))) {
						token = StringUtil.getString(jisAuthLog.getLoginName());
						if (jisAuthLog.getToken() == null) {
							jisAuthLog.setToken(token);
							// CacheUtil.setValue(token, jisAuthLog,
							// "pertoken");
						}
					} else {
						token = md5.encrypt(
								DateUtil.getCurrDate("MMddHHmmssSSS") + MathUtil.randomNumeric(Integer.valueOf(6)),
								MathUtil.randomNumeric(Integer.valueOf(6)));
						jisAuthLog.setToken(token);
						// CacheUtil.setValue(loginName, token, "pertokenkey");
						// CacheUtil.setValue(token, jisAuthLog, "pertoken");
					}
					map.put("token", token);
					map.put("loginname", loginName);
					map.put("username", jisAuthLog.getUserName());
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
		this.logger.debug("====ticketValidate===返回结果===" + new JSONUtil().writeMapSJSON(map) + "==="
				+ DateUtil.getCurrDateTime());
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String findUserByToken(String appmark, String token, String time, String sign) {
		this.logger.debug("=findUserByToken====start===");
		System.out.println("appmark=" + appmark + "token=" + token + "time=" + time + "sign=" + sign);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(token)) || (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign))) {

			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		JisAuthLog jisAuthLog = this.authLogService.findByToken(token, 1);
		if (jisAuthLog == null) {
			map.put("errormsg", "没有该令牌");
			return new JSONUtil().writeMapSJSON(map);
		}

		int userType = jisAuthLog.getUserType().intValue();
		if (userType == 1) {
			OutsideUser outSideUser = this.outSideUserService.findByLoginName(jisAuthLog.getLoginName());
			map.put("uuid", outSideUser.getUuid());
			map.put("loginname", outSideUser.getLoginName());
			map.put("mobile", StringUtil.getString(outSideUser.getMobile()));
			map.put("email", StringUtil.getString(outSideUser.getEmail()));
			map.put("name", StringUtil.getString(outSideUser.getName()));
			map.put("sex", StringUtil.getString(outSideUser.getSex()));
			map.put("cardid", StringUtil.getString(outSideUser.getPapersNumber()));
			map.put("birthdate", StringUtil.getString(outSideUser.getBirthDate()));
			map.put("phone", StringUtil.getString(outSideUser.getPhone()));
			map.put("address", StringUtil.getString(outSideUser.getAddress()));
			map.put("workunit", StringUtil.getString(outSideUser.getWorkUnit()));
			map.put("msn", StringUtil.getString(outSideUser.getMsn()));
			map.put("qq", StringUtil.getString(outSideUser.getQq()));
			map.put("isauth", StringUtil.getString(outSideUser.getIsauth()));
			map.put("usertype", "1");
			map.put("authstate", StringUtil.getString(outSideUser.getAuthState()));
		} else {
			map.put("errormsg", "登录名不是个人用户");
		}

		this.logger.debug("====findUserByToken===返回结果===" + new JSONUtil().writeMapSJSON(map));
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String generateTicket(String appmark, String token, String time, String sign, String proxyapp) {
		this.logger.debug("=generateTicket====start===");
		System.out.println(
				"appmark=" + appmark + "token=" + token + "time=" + time + "sign=" + sign + "proxyapp=" + proxyapp);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(token)) || (!StringHelper.isNotBlack(sign))
				|| (!StringHelper.isNotBlack(proxyapp)) || (!StringHelper.isNotBlack(time))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		Application appLication = this.appService.findByMark(proxyapp);
		if (appLication != null) {
			/*if (CacheUtil.getValue(token, "pertoken") == null) {
				map.put("errormsg", "令牌已失效");
				return new JSONUtil().writeMapSJSON(map);
			}*/
			JisAuthLog jisAuthLog = this.authLogService.findByToken(token, 1);
			if (jisAuthLog != null) {
				JisAuthLog jisAuthLogNew = new JisAuthLog();
				jisAuthLogNew.setAppId(appLication.getIid());
				jisAuthLogNew.setAppmark(proxyapp);
				jisAuthLogNew.setAuthType(Integer.valueOf(1));
				jisAuthLogNew.setCreateTime(new Date());
				jisAuthLogNew.setLoginName(jisAuthLog.getLoginName());
				Settings settings = Settings.getSettings();
				String authEffectiveTime = settings.getTicketEffectiveTime();
				long times = new Date().getTime() + NumberUtil.getLong(authEffectiveTime) * 1000L;
				Date outTime = new Date(times);
				jisAuthLogNew.setOutTicketTime(outTime);

				String ticket = MD5
						.encodeMd5(proxyapp + appLication.getEncryptKey() + jisAuthLog.getLoginName() + new Date());
				jisAuthLogNew.setTicket(ticket);
				jisAuthLogNew.setUserId(jisAuthLog.getUserId());
				jisAuthLogNew.setUserName(jisAuthLog.getUserName());
				jisAuthLogNew.setUserType(jisAuthLog.getUserType());
				jisAuthLogNew.setState(Integer.valueOf(0));
				// boolean isSuccess = CacheUtil.setValue(ticket, jisAuthLogNew,
				// "perticket");

				if (ticket != null)
					map.put("ticket", ticket);
				else
					map.put("errormsg", "票据生成失败");
			} else {
				map.put("errormsg", "没有该令牌");
			}
		} else {
			map.put("errormsg", "接入应用不存在");
		}

		this.logger.debug("====generateTicket===返回结果===" + new JSONUtil().writeMapSJSON(map));
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String userValidate(String appmark, String time, String sign, String loginname, String password) {
		System.out.println("appmark=" + appmark + "time=" + time + "sign=" + sign + "loginname=" + loginname
				+ "password=" + password);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(sign))
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
		password = md5.decrypt(password, time);
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			if (UserUtil.isMobilelegal(loginname)) {
				OutsideUser outsideUser = this.outSideUserService.findByMobile(loginname);
				if (outsideUser != null) {
					loginname = outsideUser.getLoginName();
				}
			} else if (UserUtil.isIDnumberlegal(loginname)) {
				OutsideUser outsideUser = this.outSideUserService.findByIdCard(loginname);
				if (outsideUser != null) {
					loginname = outsideUser.getLoginName();
				}
			}

			OutsideUser outsideUser = this.outSideUserService.checkUserLogin(loginname, password, ip);
			if (outsideUser != null) {
				jisAuthLog.setAppmark(appmark);
				jisAuthLog.setLoginName(outsideUser.getLoginName());
				jisAuthLog.setUserType(Integer.valueOf(1));
				jisAuthLog.setAuthType(Integer.valueOf(2));
				String token = md5.encrypt(loginname + appmark, loginname);
				jisAuthLog.setToken(token);

				long tokenEffectiveTime = NumberUtil.getLong(Settings.getSettings().getTokenEffectiveTime());
				// CacheUtil.setValue(loginname, token, "pertokenkey");
				// CacheUtil.setValue(token, jisAuthLog, "pertoken");
				map.put("token", token);
				map.put("loginname", loginname);
				map.put("username", outsideUser.getName());
			} else {
				map.put("errormsg", "登录名或密码不正确");
			}
		} catch (LoginException e) {
			map.put("errormsg", "登录名未被激活");
		}
		return new JSONUtil().writeMapSJSON(map);
	}

}
