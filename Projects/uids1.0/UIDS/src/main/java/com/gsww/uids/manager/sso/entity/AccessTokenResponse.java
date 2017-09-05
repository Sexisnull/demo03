package com.gsww.uids.manager.sso.entity;

import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.oltu.oauth2.common.OAuth;

import com.gsww.common.entity.PO;
import com.gsww.common.util.TimeHelper;

/**
 * 记录accessToken的应答信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ACCESS_TOKEN_RESPONSE")
public class AccessTokenResponse extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6019449255061559462L;
	
	/**
	 * accessToken请求
	 */
	@ManyToOne
	@JoinColumn(name = "ACCESS_TOKEN_REQUEST_ID", nullable = false)
	private AccessTokenRequest request;

	/**
	 * access_token
	 */
	@Column(name = "ACCESS_TOKEN", unique = true, nullable = false, length = 32)
	private String accessToken;
	
	/**
	 * token_type：推荐的标准设置为bearer
	 */
	@Column(name = "TOKEN_TYPE", nullable = false, length = 6)
	private String tokenType = OAuth.DEFAULT_TOKEN_TYPE.toString();
	
	/**
	 * access_token过期时间：单位：秒
	 */
	@Column(name = "EXPIRES_IN", columnDefinition = "BIGINT default 3600")
	private Long expiresIn = 3600L;
	
	/**
	 * refresh_token过期时间：单位：秒
	 * 默认：24小时
	 * 正常情况下，refreshToken的有效期比accessToken长
	 */
	@Column(name = "REFRESH_EXPIRES_IN", columnDefinition = "BIGINT default 86400")
	private Long refreshExpiresIn = 86400L;
	
	/**
	 * refresh_token
	 */
	@Column(name = "REFRESH_TOKEN", unique = true, nullable = false, length = 32)
	private String refreshToken;
	
	/**
	 * scope
	 */
	@Column(name = "SCOPE", length = 32)
	private String scope;
	
	/**
	 * 应答时间
	 */
	@Column(name = "RESPONSE_TIME", nullable = false, length = 23)
	private String responseTime;

	public AccessTokenRequest getRequest() {
		return request;
	}

	public void setRequest(AccessTokenRequest request) {
		this.request = request;
	}

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

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public void setRefreshExpiresIn(Long refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
	public boolean canRefresh() {
		String curTime = TimeHelper.getCurrentTime();
		long timeToNow = 0L;
		try {
			timeToNow = TimeHelper.secondsBetween(this.getResponseTime(), curTime);
			return timeToNow <= this.refreshExpiresIn.longValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
