package com.gsww.uids.client.oauth;

import java.util.Date;

/**
 * 从uids服务器上获取的token信息
 * 
 * @author taolm
 *
 */
public class Token {
	
	/**
	 * access_token
	 */
	private String accessToken;
	
	/**
	 * token类型
	 */
	private String tokenType;
	
	/**
	 * refresh_token
	 */
	private String refreshToken;
	
	/**
	 * 有效期：秒
	 */
	private long expiresIn;
	
	/**
	 * 有效期的计时起点
	 */
	private Date startTime;
	
	/**
	 * scope
	 */
	private String scopes;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}
}
