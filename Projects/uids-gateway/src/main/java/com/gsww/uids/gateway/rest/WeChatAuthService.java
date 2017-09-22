package com.gsww.uids.gateway.rest;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;

@Path("/uids-web")
@SuppressWarnings("deprecation")
public class WeChatAuthService {
	//private OutsideUserDao outsideUserDAO = SpringContextHolder.getBean("outsideUserDao");
	private static WeChatAuthService weChatAuthService;
	static {
		weChatAuthService = SpringContextHolder.getBean("weChatAuthService");
	}
	protected Logger logger = Logger.getLogger(WeChatAuthService.class);

	/**
	 * 生成用于获取access_token的Code的Url
	 *
	 * @param redirectUrl
	 * @return
	 */
	public String getRequestCodeUrl(String redirectUrl) {
		return String.format(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
				"APPID", redirectUrl, "snsapi_userinfo", "xxxx_state");
	}

	/**
	 * 路由控制
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	@GET
	@Path("auth")
	public Response auth(@Context HttpServletRequest request, @QueryParam("code") String code) {
		Map<String, String> result = weChatAuthService.getUserInfoAccessToken(code);// 通过这个code获取access_token
		String openId = result.get("openid");
		if (StringHelper.isNotBlack(openId)) {
			Map<String, String> userInfo = weChatAuthService.getUserInfo(result.get("access_token"), openId);// 使用access_token获取用户信息
			// return forward("auth", userInfo);
		}
		return Response.ok("openid为空").build();
	}

	/**
	 * 获取请求用户信息的access_token
	 *
	 * @param code
	 * @return
	 */
	public Map<String, String> getUserInfoAccessToken(String code) {
		JsonObject object = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			String url = String.format(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
					"APPID", "APPSECRET", code);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String tokens = EntityUtils.toString(httpEntity, "utf-8");
			Gson token_gson = new Gson();
			object = token_gson.fromJson(tokens, JsonObject.class);
			data.put("openid", object.get("openid").toString().replaceAll("\"", ""));
			data.put("access_token", object.get("access_token").toString().replaceAll("\"", ""));
		} catch (Exception ex) {
			logger.error("fail to request wechat access token. [error={}]", ex);
		}
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
