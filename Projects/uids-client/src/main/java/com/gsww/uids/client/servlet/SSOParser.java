package com.gsww.uids.client.servlet;

import java.util.Date;

import org.apache.oltu.oauth2.common.OAuth;
import org.codehaus.jettison.json.JSONException;

import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.client.user.SSOUser;

import net.sf.json.JSONObject;

/**
 * 单点登录相关的rpc返回数据的解析
 * 
 * @author taolm
 *
 */
public class SSOParser {
	
	private SSOParser() {
		
	}
	
	/**
	 * 从json字符串中解析出code
	 * 
	 * @param codeJsonStr
	 * @return
	 * @throws JSONException
	 */
	public static String parseCode(String codeJsonStr) throws JSONException {
		
		JSONObject json = JSONObject.fromObject(codeJsonStr);
		return json.getString(OAuth.OAUTH_CODE);		
	}
	
	/**
	 * 从json字符串中解析出token
	 * 
	 * @param tokenJsonStr
	 * @return
	 * @throws JSONException
	 */
	public static Token parseToken(String tokenJsonStr) throws JSONException, NumberFormatException {
		
		JSONObject json = JSONObject.fromObject(tokenJsonStr);
		
		Token token = new Token();
		token.setAccessToken((String)json.get(OAuth.OAUTH_ACCESS_TOKEN));
		token.setTokenType((String)json.get(OAuth.OAUTH_TOKEN_TYPE));
		token.setRefreshToken((String)json.get(OAuth.OAUTH_REFRESH_TOKEN));
		token.setExpiresIn(json.getLong(OAuth.OAUTH_EXPIRES_IN));
		token.setScopes((String)json.get(OAuth.OAUTH_SCOPE));
		token.setStartTime(new Date());
		
		return token;
	}
	
	/**
	 * 从json字符串中解析出用户数据
	 * 
	 * @param userJsonStr
	 * @return
	 */
	public static SSOUser parseUser(String userJsonStr) {
		
		JSONObject json = JSONObject.fromObject(userJsonStr); 
		SSOUser user = (SSOUser)JSONObject.toBean(json, SSOUser.class);		
		return user;
	}
}
