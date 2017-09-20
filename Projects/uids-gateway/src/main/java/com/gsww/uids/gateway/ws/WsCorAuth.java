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
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.service.AppService;
import com.gsww.uids.gateway.service.AuthLogService;
import com.gsww.uids.gateway.service.CorporationService;
import com.gsww.uids.gateway.sso.ldap.util.MD5;
import com.gsww.uids.gateway.util.CacheUtil;
import com.gsww.uids.gateway.util.DateTime;
import com.gsww.uids.gateway.util.DateUtil;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.MathUtil;
import com.gsww.uids.gateway.util.NumberUtil;
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
	private AuthLogService authLogService = new AuthLogService();
	private AppService appService = new AppService();
	private CorporationService corporationService = new CorporationService();
	protected Logger logger = Logger.getLogger(getClass());

	@WebMethod
	public String ticketValidate(String appmark, String ticket, String time, String sign) {
		System.out.println("appmark=" + appmark + "ticket=" + ticket + "time=" + time + "sign=" + sign);
		Map<String, String> map = new HashMap<String, String>();
		if ((StringHelper.isNotBlack(appmark)) && (StringHelper.isNotBlack(ticket)) && (StringHelper.isNotBlack(time))
				&& (StringHelper.isNotBlack(sign))) {

			JisAuthLog jisAuthLog = this.authLogService.findByTicket(ticket, 2);
			if (jisAuthLog != null) {
				Date outTime = jisAuthLog.getOutTicketTime();

				if (DateTime.dayDiff(new Date(), outTime) < 0L) {
					jisAuthLog.setSpec("票据已过期");
					map.put("errormsg", "票据已过期");
					return new JSONUtil().writeMapSJSON(map);
				}

				Application appLication = this.appService.findByMark(appmark);

				MD5 md5 = new MD5();
				String token = "";
				if (appLication != null) {
					String decodeSign = md5.decrypt(sign, time);
					if (decodeSign.equals(appmark + appLication.getEncryptKey() + time)) {
						String loginName = jisAuthLog.getLoginName();
						if (StringUtil.isNotEmpty(StringUtil.getString(jisAuthLog.getLoginName()))) {
							token = StringUtil.getString(jisAuthLog.getLoginName());
							if (jisAuthLog.getToken() == null) {
								jisAuthLog.setToken(token);

								jisAuthLog.setState(Integer.valueOf(1));
							}
						} else {
							token = md5.encrypt(
									DateUtil.getCurrDate("MMddHHmmssSSS") + MathUtil.randomNumeric(Integer.valueOf(6)),
									MathUtil.randomNumeric(Integer.valueOf(6)));
							jisAuthLog.setToken(token);

							jisAuthLog.setState(Integer.valueOf(1));

						}
						map.put("token", token);
						map.put("loginname", loginName);
						map.put("username", jisAuthLog.getUserName());
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
			return new JSONUtil().writeMapSJSON(map);
		} else {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}
	}

	@WebMethod
	public String findUserByToken(String appmark, String token, String time, String sign) {
		logger.info("=cor====findUserByToken====start===" + token + "=time==" + sign);
		System.out.println("appmark=" + appmark + "token=" + token + "time=" + time + "sign=" + sign);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(token)) || (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		JisAuthLog jisAuthLog = this.authLogService.findByToken(token, 2);

		if (jisAuthLog == null) {
			map.put("errormsg", "没有该令牌");
			return new JSONUtil().writeMapSJSON(map);
		}

		int userType = jisAuthLog.getUserType().intValue();
		if (userType == 2) {
			Corporation corporation = this.corporationService.findByLoginName(jisAuthLog.getLoginName());

			map.put("uuid", corporation.getUuid());
			map.put("type", corporation.getType() + "");
			map.put("name", corporation.getName());
			map.put("regnumber", StringUtil.getString(corporation.getRegNumber()));
			map.put("orgnumber", StringUtil.getString(corporation.getOrgNumber()));
			map.put("scope", StringUtil.getString(corporation.getScope()));
			map.put("regorgname", StringUtil.getString(corporation.getRegorgName()));
			map.put("loginname", StringUtil.getString(corporation.getLoginName()));
			map.put("realname", StringUtil.getString(corporation.getRealName()));
			map.put("mobile", StringUtil.getString(corporation.getMobile()));
			map.put("phone", StringUtil.getString(corporation.getPhone()));
			map.put("sex", StringUtil.getString(corporation.getSex()));
			map.put("address", StringUtil.getString(corporation.getAddress()));
			map.put("cardid", StringUtil.getString(corporation.getCardNumber()));
			map.put("email", StringUtil.getString(corporation.getEmail()));

			map.put("isauth", StringUtil.getString(corporation.getIsauth()));
			map.put("authstate", StringUtil.getString(corporation.getAuthState()));
			map.put("usertype", "2");
			map.put("declarationiid", Integer.valueOf(NumberUtil.getInt(corporation.getDeclarationIid())) + "");
			map.put("nation", StringUtil.getString(corporation.getNation()));
		} else {
			map.put("errormsg", "登录名不是法人用户");
		}

		this.logger.info("====findUserByToken===返回结果===" + new JSONUtil().writeMapSJSON(map));
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String generateTicket(String appmark, String token, String time, String sign, String proxyapp) {
		this.logger.info("=generateTicket==cor==start===");
		System.out.println(
				"appmark=" + appmark + "token=" + token + "time=" + time + "sign=" + sign + "proxyapp=" + proxyapp);
		Map<String, String> map = new HashMap<String, String>();

		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(token)) || (!StringHelper.isNotBlack(time))
				|| (!StringHelper.isNotBlack(sign)) || (!StringHelper.isNotBlack(proxyapp))) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		Application appLication = this.appService.findByMark(proxyapp);
		if (appLication != null) {
			/*if (CacheUtil.getValue(token, "cortoken") == null) {
				map.put("errormsg", "令牌已失效");
				return new JSONUtil().writeMapSJSON(map);
			}*/
			JisAuthLog jisAuthLog = this.authLogService.findByToken(token, 2);
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
				// "corticket");
				if (ticket != null)
					map.put("ticket", ticket);
				else
					map.put("errormsg", "票据生成失败");
			} else {
				map.put("errormsg", "无效令牌");
			}
		} else {
			map.put("errormsg", "接入应用不存在");
		}
		this.logger.info("====generateTicket===返回结果===" + new JSONUtil().writeMapSJSON(map));
		return new JSONUtil().writeMapSJSON(map);
	}

	@WebMethod
	public String userValidate(String appmark, String time, String sign, String loginname, String password) {
		System.out.println("appmark=" + appmark + "time=" + time + "sign=" + sign + "loginname=" + loginname
				+ "password=" + password);
		this.logger.info("=cor认证====userValidate====appmark===" + appmark + "=time==" + time + "=sign=" + sign
				+ "=loginname=" + loginname + "=password=" + password);
		Map<String, String> map = new HashMap<String, String>();
		if ((!StringHelper.isNotBlack(appmark)) || (!StringHelper.isNotBlack(sign))
				|| (!StringHelper.isNotBlack(password)) || (time == null)) {
			map.put("errormsg", "必填参数为空");
			return new JSONUtil().writeMapSJSON(map);
		}

		Application appLication = this.appService.findByMark(appmark);
		if (appLication == null) {
			System.out.println("appLication==" + appLication);
			map.put("errormsg", "应用标识不正确");
		}
		String ip = "";
		MD5 md5 = new MD5();
		password = md5.decrypt(sign, time);
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			Corporation corporation = this.corporationService.checkUserLogin(loginname, password, ip);
			if (corporation != null) {
				jisAuthLog.setAppmark(appmark);
				jisAuthLog.setLoginName(corporation.getLoginName());
				jisAuthLog.setUserType(Integer.valueOf(2));
				jisAuthLog.setAuthType(Integer.valueOf(2));
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

		return new JSONUtil().writeMapSJSON(map);
	}
}
