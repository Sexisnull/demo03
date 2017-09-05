package com.gsww.uids.manager.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 记录authcode的应答信息
 * authcode只能使用一次，用来换取accessToken
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_AUTHCODE_RESPONSE")
public class AuthcodeResponse extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1484950439064106727L;
	
	/**
	 * authcode请求
	 */
	@ManyToOne
	@JoinColumn(name = "AUTHCODE_REQUEST_ID", nullable = false)
	private AuthcodeRequest request;

	/**
	 * 授权码code
	 */
	@Column(name = "AUTHCODE", unique = true, nullable = false, length = 32)
	private String authcode;
	
	/**
	 * 应答时间
	 */
	@Column(name = "RESPONSE_TIME", nullable = false, length = 23)
	private String responseTime;
	
	/**
	 * 票据有效时间
	 */
	@Column(name = "EXPIRES_IN", columnDefinition = "BIGINT default 60")
	private Long expiresIn = 60L;
	
	/**
	 * 授权码是否使用过，即是否被用来获取过accessToken
	 */
	@Column(name = "IS_AUTHCODE_USED", columnDefinition = "INT default 0")
	private int authcodeUsed = 0;

	public AuthcodeRequest getRequest() {
		return request;
	}

	public void setRequest(AuthcodeRequest request) {
		this.request = request;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public int getAuthcodeUsed() {
		return authcodeUsed;
	}

	public void setAuthcodeUsed(int authcodeUsed) {
		this.authcodeUsed = authcodeUsed;
	}

	public boolean isAuthcodeUsed() {
		return (authcodeUsed != 0);
	}
}
