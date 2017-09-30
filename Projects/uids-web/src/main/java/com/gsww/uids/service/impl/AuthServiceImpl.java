package com.gsww.uids.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.ReadProperties;
import com.gsww.uids.service.AuthService;
import com.gsww.uids.util.HttpClientUtil;

public class AuthServiceImpl implements AuthService {
	protected static Logger logger = Logger.getLogger(AuthService.class);

	@Override
	public String authGetToken() {
		String auth_url = ReadProperties.getPropertyByStr("auth_url");
		// syscode = "I3eUZj";
		// username = "tysfrzxt";
		// password = "tysfrzxt";
		// auth_url = auth_url.replace("SYSCODE", syscode);
		// auth_url = auth_url.replace("USERNAME", username);
		// auth_url = auth_url.replace("PASSWORD", password);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String auth_info = httpClientUtil.doUrlGet(auth_url);
		JsonObject jsonObject = null;
		Gson info_gson = new Gson();
		jsonObject = info_gson.fromJson(auth_info, JsonObject.class);
		if (jsonObject == null) {
			return null;
		} else {
			jsonObject = info_gson.fromJson(jsonObject.get("rst"),
					JsonObject.class);
			String token = jsonObject
					.get("token")
					.toString()
					.substring(1,
							jsonObject.get("token").toString().length() - 1);
			return token;
		}
	}

	// 法人：实名比对--实名认证
	@Override
	public String corporCont(
			@PathVariable("verify_mode") int verify_mode,
			// @PathVariable("syscode") String syscode,
			// @PathVariable("token") String token,
			@RequestParam("zzjgdm") String zzjgdm,
			@RequestParam("tyshxydm") String tyshxydm,
			@RequestParam("gszch") String gszch,
			@RequestParam("qymcqc") String qymcqc) {
		String token = this.authGetToken();
		if (token == null && "".equals(token)) {
			// verify_mode="";
			String corpor_cont_url = ReadProperties
					.getPropertyByStr("corpor_cont_url");
			corpor_cont_url = corpor_cont_url.replace("VERIFY_MODE",
					verify_mode + "");
			// corpor_cont_url = corpor_cont_url.replace("SYSCODE", syscode);
			corpor_cont_url = corpor_cont_url.replace("TOKEN", token);
			corpor_cont_url = corpor_cont_url.replace("ZZJGDM", zzjgdm);
			corpor_cont_url = corpor_cont_url.replace("TYSHXYDM", tyshxydm);
			corpor_cont_url = corpor_cont_url.replace("GSZCH", gszch);
			corpor_cont_url = corpor_cont_url.replace("QYMCQC", qymcqc);
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String corpor_cont_info = httpClientUtil.doUrlGet(corpor_cont_url);
			String msg = AuthServiceImpl.processInformation(corpor_cont_info);
			return msg;
		} else {
			return this.returnFalse();
		}
	}

	// 法人：实名认证--实名认证
	@Override
	public String corporAuth(
			// @PathVariable("syscode") String syscode,
			// @PathVariable("token") String token,
			@PathVariable("verify_mode") int verify_mode,
			@RequestParam("frmc") String frmc,
			@RequestParam("dbrxm") String dbrxm, @RequestParam("id") String id,
			@RequestParam("tyshxydm") String tyshxydm,
			@RequestParam("zzjgdm") String zzjgdm,
			@RequestParam("gszch") String gszch) {
		String token = this.authGetToken();
		if (token == null && "".equals(token)) {
			String corpor_auth_url = ReadProperties
					.getPropertyByStr("corpor_auth_url");
			// corpor_auth_url = corpor_auth_url.replace("SYSCODE", syscode);
			corpor_auth_url = corpor_auth_url.replace("TOKEN", token);
			corpor_auth_url = corpor_auth_url.replace("VERIFY_MODE",
					verify_mode + "");
			corpor_auth_url = corpor_auth_url.replace("FRMC", frmc);
			corpor_auth_url = corpor_auth_url.replace("DBRXM", dbrxm);
			corpor_auth_url = corpor_auth_url.replace("ID", id);
			corpor_auth_url = corpor_auth_url.replace("TYSHXYDM", tyshxydm);
			corpor_auth_url = corpor_auth_url.replace("ZZJGDM", zzjgdm);
			corpor_auth_url = corpor_auth_url.replace("GSZCH", gszch);
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String corpor_auth_info = httpClientUtil.doUrlGet(corpor_auth_url);
			String msg = AuthServiceImpl.processInformation(corpor_auth_info);
			return msg;
		} else {
			return this.returnFalse();
		}
	}

	// 个人：验证个人信息
	@Override
	public String perporAuth(
			// final @PathVariable("syscode") String syscode,
			// final @PathVariable("token") String req_token, // token
			final @RequestParam("id") String id,
			final @RequestParam("name") String name) {
		String req_token = this.authGetToken();
		if (req_token == null && "".equals(req_token)) {
			String perpor_auth_url = ReadProperties
					.getPropertyByStr("perpor_auth_url");
			// perpor_auth_url = perpor_auth_url.replace("SYSCODE", syscode);
			perpor_auth_url = perpor_auth_url.replace("TOKEN", req_token);
			perpor_auth_url = perpor_auth_url.replace("ID", id);
			perpor_auth_url = perpor_auth_url.replace("NAME", name);
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String perpor_auth_info = httpClientUtil.doUrlGet(perpor_auth_url);
			String msg = AuthServiceImpl.processInformation(perpor_auth_info);
			return msg;
		} else {
			return this.returnFalse();
		}
	}

	// 公务人员：公务人员身份认证接口
	@Override
	public String pubporAuth(
			final @PathVariable("verify_mode") int verify_mode,
			// final @PathVariable("syscode") String syscode,
			// final @PathVariable("token") String req_token,
			final @RequestParam("id") String id,
			final @RequestParam("name") String name,
			final @RequestParam("jgbm") String jgbm,
			final @RequestParam("jgmc") String jgmc,
			final @RequestParam("qybm") String qybm) {
		String req_token = this.authGetToken();
		if ("".equals(req_token) && req_token == null) {
			String pubpor_auth_url = ReadProperties
					.getPropertyByStr("pubpor_auth_url");
			pubpor_auth_url = pubpor_auth_url.replace("VERIFY_MODE",
					verify_mode + "");
			// pubpor_auth_url = pubpor_auth_url.replace("SYSCODE", syscode);
			pubpor_auth_url = pubpor_auth_url.replace("TOKEN", req_token);
			pubpor_auth_url = pubpor_auth_url.replace("ID", id);
			pubpor_auth_url = pubpor_auth_url.replace("NAME", name);
			pubpor_auth_url = pubpor_auth_url.replace("JGBM", jgbm);
			pubpor_auth_url = pubpor_auth_url.replace("JGMC", jgmc);
			pubpor_auth_url = pubpor_auth_url.replace("QYBM", qybm);
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String pubpor_auth_info = httpClientUtil.doUrlGet(pubpor_auth_url);
			String msg = AuthServiceImpl.processInformation(pubpor_auth_info);
			return msg;
		} else {
			return this.returnFalse();
		}
	}

	private static String processInformation(String info) {
		try {
			JsonObject jsonObject = null;
			Map<String, String> data = new HashMap<String, String>();
			Gson info_gson = new Gson();
			jsonObject = info_gson.fromJson(info, JsonObject.class);
			if (jsonObject == null) {
				data.put("rst", "false");
				return new JSONUtil().writeMapSJSON(data);
			} else {
				if (jsonObject.get("error") == null
						|| jsonObject.get("rst") == null) {
					data.put("rst", "false".replaceAll("\"", ""));
					return new JSONUtil().writeMapSJSON(data);
				} else {
					data.put("error", jsonObject.get("error").toString()
							.replaceAll("\"", ""));
					data.put("rst", jsonObject.get("rst").toString()
							.replaceAll("\"", ""));
					return new JSONUtil().writeMapSJSON(data);
				}
			}
		} catch (Exception e) {
			logger.error("<接口>异常", e);
			e.printStackTrace();
		}
		return null;
	}

	private String returnFalse() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("error", "Http异常，无法访问资源".replaceAll("\"", ""));
		data.put("rst", "false".replaceAll("\"", ""));
		return new JSONUtil().writeMapSJSON(data);
	}
}
