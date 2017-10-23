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
import com.gsww.uids.gateway.rest.QqAuthService;

/**
 * QQ登录工具类
 * 
 * @author ZhangCongChao
 * @time 2017年9月26日
 */
public class QqUtil {
	protected Logger logger = Logger.getLogger(QqAuthService.class);

	/**
	 * 生成用于获取access_token的Code的Url
	 *
	 * @param redirectUrl
	 * @return
	 */
	public String getRequestCodeUrl(String redirectUrl) {
		String qq_auth_url = ReadProperties.getPropertyByStr("qq_auth_url");
		String clientId = "";
		String state = "";
		qq_auth_url = qq_auth_url.replace("CLIENT_ID", clientId);
		qq_auth_url = qq_auth_url.replace("REDIRECT_URI", redirectUrl);
		qq_auth_url = qq_auth_url.replace("STATE", state);
		// System.out.println(requestUrl);
		return String.format(qq_auth_url);
	}

	/**
	 * 获取请求用户信息的access_token
	 *
	 * @param code
	 * @return
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public Map<String, String> getUserInfoAccessToken(String code) {
		JsonObject jsonObject = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			String grantType = "authorization_code";
			// 应用唯一标识
			String clientId = "";
			// APP秘钥
			String clientSecret = "";
			// 用于保持请求和回调的状态，授权请求后原样带回给第三方。
			String redirectUri = "";
			String get_qq_accesstoken_url = ReadProperties.getPropertyByStr("get_qq_accesstoken_url");
			get_qq_accesstoken_url = get_qq_accesstoken_url.replace("GRANT_TYPE", grantType);
			get_qq_accesstoken_url = get_qq_accesstoken_url.replace("CLIENT_ID", clientId);
			get_qq_accesstoken_url = get_qq_accesstoken_url.replace("CLIENT_SECRET", clientSecret);
			get_qq_accesstoken_url = get_qq_accesstoken_url.replace("CODE", code);
			get_qq_accesstoken_url = get_qq_accesstoken_url.replace("REDIRECT_URL", redirectUri);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(get_qq_accesstoken_url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String tokens = EntityUtils.toString(httpEntity, "utf-8");
			System.out.println("tokens长度" + tokens.length());
			String token = tokens.substring(9, tokens.length() - 3);
			Gson token_gson = new Gson();
			jsonObject = token_gson.fromJson(token, JsonObject.class);
			if (jsonObject == null) {
			} else {
				if (jsonObject.get("access_token") == null) {
					data.put("error", jsonObject.get("error").toString().replaceAll("\"", ""));
					data.put("error_description", jsonObject.get("error_description").toString().replaceAll("\"", ""));
				} else {
					data.put("access_token", jsonObject.get("access_token").toString().replaceAll("\"", ""));
				}
			}
		} catch (Exception ex) {
			logger.error("fail to request qq access token. [error={}]", ex);
		}
		// System.out.println(data.get("errcode"));
		return data;
	}

	/**
	 * 获取请求用户信息的openid
	 *
	 * @param accesstoken
	 * @return
	 */
	public Map<String, String> getUserOpenId(String accesstoken) {
		JsonObject jsonObject = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			String accessToken = "";
			String get_qq_openid_url=ReadProperties.getPropertyByStr("get_qq_openid_url");
			get_qq_openid_url = get_qq_openid_url.replace("ACCESS_TOKEN", accessToken);
			//System.out.println(get_qq_openid_url + "url--------------------------");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(get_qq_openid_url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String tokens = EntityUtils.toString(httpEntity, "utf-8");
			// System.out.println("tokens长度" + tokens.length());
			String token = tokens.substring(9, tokens.length() - 3);
			Gson token_gson = new Gson();
			jsonObject = token_gson.fromJson(token, JsonObject.class);
			if (jsonObject == null) {
			} else {
				if (jsonObject.get("openid") == null) {
					data.put("error", jsonObject.get("error").toString().replaceAll("\"", ""));
					data.put("error_description", jsonObject.get("error_description").toString().replaceAll("\"", ""));
				} else {
					data.put("openid", jsonObject.get("openid").toString().replaceAll("\"", ""));
				}
			}
		} catch (Exception ex) {
			logger.error("fail to request qq openId. [error={}]", ex);
		}
		// System.out.println(data.get("errcode"));
		return data;
	}
}
