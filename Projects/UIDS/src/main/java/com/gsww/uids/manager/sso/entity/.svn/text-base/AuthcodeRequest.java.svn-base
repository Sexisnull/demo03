package com.gsww.uids.manager.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 记录authcode的请求信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_AUTHCODE_REQUEST")
public class AuthcodeRequest extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8961739566088015512L;
	
	/**
	 * response_type：此处固定为code（授权码类型）
	 */
	@Column(name = "RESPONSE_TYPE", nullable = false, length = 10)
	private String responseType = "code";

	/**
	 * 提交请求的应用的clientId
	 */
	@Column(name = "CLIENT_ID", nullable = false, length = 32)
	private String clientId;
	
	/**
	 * 账号所属应用的clientId
	 */
	@Column(name = "CLIENT_ID_OF_ACCOUNT", nullable = false, length = 32)
	private String clientIdOfAccount;
	
	/**
	 * redirect_uri
	 */
	@Column(name = "REDIRECT_URI", nullable = false, length = 256)
	private String redirectUri;
	
	/**
	 * scope
	 */
	@Column(name = "SCOPE", length = 256)
	private String scope;
	
	/**
	 * state
	 */
	@Column(name = "STATE", length = 32)
	private String state;
	
	/**
	 * 账号名
	 */
	@Column(name = "ACCOUNT_NAME", nullable = false, length = 128)
	private String accountName;
	
	/**
	 * 请求时间
	 */
	@Column(name = "REQUEST_TIME", nullable = false, length = 23)
	private String requestTime;
	
	/**
	 * 请求的ip地址
	 */
	@Column(name = "REQUEST_IP", length = 15)
	private String requestIp;

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientIdOfAccount() {
		return clientIdOfAccount;
	}

	public void setClientIdOfAccount(String clientIdOfAccount) {
		this.clientIdOfAccount = clientIdOfAccount;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
