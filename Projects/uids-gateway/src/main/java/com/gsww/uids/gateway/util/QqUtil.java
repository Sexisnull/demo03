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
		String requestUrl = String.format(
				"https://graph.qq.com/oauth2.0/authorize?response_type=CODE&client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&state=1");
		String clientId = "";
		String state = "";
		requestUrl = requestUrl.replace("CLIENT_ID", clientId);
		requestUrl = requestUrl.replace("REDIRECT_URI", redirectUrl);
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
			String grantType = "authorization_code";
			// 应用唯一标识
			String clientId = "";
			// APP秘钥
			String clientSecret = "";
			// 用于保持请求和回调的状态，授权请求后原样带回给第三方。
			String redirectUri = "";
			String url = String.format(
					"https://graph.qq.com/oauth2.0/token?grant_type=GRANT_TYPE&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&code=CODE&redirect_uri=REDIRECT_URL");
			url = url.replace("GRANT_TYPE", grantType);
			url = url.replace("CLIENT_ID", clientId);
			url = url.replace("CLIENT_SECRET", clientSecret);
			url = url.replace("CODE", code);
			url = url.replace("REDIRECT_URL", redirectUri);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
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
			String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=ACCESS_TOKEN");
			url = url.replace("ACCESS_TOKEN", accessToken);
			System.out.println(url + "url--------------------------");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
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
