package com.gsww.uids.gateway.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gsww.uids.gateway.rest.WeChatAuthService;

/**
 * 微信登录工具类
 * 
 * @author ZhangCongChao
 * @time 2017年9月26日
 */
public class WeChatUtil {
	protected Logger logger = Logger.getLogger(WeChatAuthService.class);

	/**
	 * 生成用于获取access_token的Code的Url
	 *
	 * @param redirectUrl
	 * @return
	 */
	public String getRequestCodeUrl(String redirectUrl) {
		String requestUrl = String.format(
				"https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=CODE&scope=SCOPE&state=STATE#wechat_redirect");
		String appId = "";
		String scope = "";
		String state = "";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REDIRECT_URI", redirectUrl);
		requestUrl = requestUrl.replace("SCOPE", scope);
		requestUrl = requestUrl.replace("STATE", state);
		// System.out.println(requestUrl);
		return String.format(requestUrl);
	}

	/**
	 * 获取请求用户信息的access_token
	 *
	 * @param code
	 * @return
	 */
	@SuppressWarnings("resource")
	public Map<String, String> getUserInfoAccessToken(String code) {
		JsonObject jsonObject = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			// 应用唯一标识
			String appId = "wxe34a90ac7bxxcab85c";
			// APP秘钥
			String secret = "www.localhost.com";
			// 用于保持请求和回调的状态，授权请求后原样带回给第三方。
			String state = "wechat_state";
			String url = String.format(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code");
			url = url.replace("APPID", appId);
			url = url.replace("SECRET", secret);
			url = url.replace("CODE", code);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String tokens = EntityUtils.toString(httpEntity, "utf-8");
			Gson token_gson = new Gson();
			jsonObject = token_gson.fromJson(tokens, JsonObject.class);
			if (jsonObject == null) {
			} else {
				if (jsonObject.get("openid") == null || jsonObject.get("access_token") == null) {
					data.put("errcode", jsonObject.get("errcode").toString().replaceAll("\"", ""));
					data.put("errmsg", jsonObject.get("errmsg").toString().replaceAll("\"", ""));
				} else {
					data.put("openid", jsonObject.get("openid").toString().replaceAll("\"", ""));
					data.put("access_token", jsonObject.get("access_token").toString().replaceAll("\"", ""));
				}
			}
		} catch (Exception ex) {
			logger.error("fail to request wechat access token. [error={}]", ex);
		}
		// System.out.println(data.get("errcode"));
		return data;
	}

	/**
	 * 获取用户信息
	 *
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public Map<String, String> getUserInfo(String accessToken, String openId) {
		Map<String, String> data = new HashMap<String, String>();
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
				+ "&lang=zh_CN";
		logger.info("request user info from url: {}" + url);
		JsonObject userInfo = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String response = EntityUtils.toString(httpEntity, "utf-8");
			Gson token_gson = new Gson();
			userInfo = token_gson.fromJson(response, JsonObject.class);
			logger.info("get userinfo success. [result={}]" + userInfo);
			data.put("openid", userInfo.get("openid").toString().replaceAll("\"", ""));
			data.put("nickname", userInfo.get("nickname").toString().replaceAll("\"", ""));
			data.put("city", userInfo.get("city").toString().replaceAll("\"", ""));
			data.put("province", userInfo.get("province").toString().replaceAll("\"", ""));
			data.put("country", userInfo.get("country").toString().replaceAll("\"", ""));
			data.put("headimgurl", userInfo.get("headimgurl").toString().replaceAll("\"", ""));
		} catch (Exception ex) {
			logger.error("fail to request wechat user info. [error={}]", ex);
		}
		return data;
	}
}
