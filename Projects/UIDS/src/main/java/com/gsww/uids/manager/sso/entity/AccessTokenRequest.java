package com.gsww.uids.manager.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 记录accessToken的请求信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ACCESS_TOKEN_REQUEST")
public class AccessTokenRequest extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1696707007493074441L;

	/**
	 * grant_type：此处固定为authorization_code（授权码类型）
	 * 实际上，请求的时候有2种类型，还有refresh_token，但是这里并不需要保存这种请求
	 */
	@Column(name = "GRANT_TYPE", nullable = false, length = 20)
	private String grantType = "authorization_code";

	/**
	 * 授权码code
	 */
	@Column(name = "AUTHCODE", nullable = false, length = 32)
	private String authcode;
	
	/**
	 * redirect_uri
	 */
	@Column(name = "REDIRECT_URI", nullable = false, length = 256)
	private String redirectUri;
	
	/**
	 * 请求的应用的client_id
	 */
	@Column(name = "CLIENT_ID", nullable = false, length = 32)
	private String clientId;
	
	/**
	 * client_secret
	 */
	@Column(name = "CLIENT_SECRET", nullable = false, length = 32)
	private String clientSecret;
	
	/**
	 * 请求时间
	 */
	@Column(name = "REQUEST_TIME", nullable = false, length = 23)
	private String requestTime;
	
	/**
	 * 请求的ip地址
	 */
	@Column(name = "REQUEST_IP", nullable = false, length = 15)
	private String requestIp;

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
}
