package com.gsww.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

import com.gsww.entity.SSOUser;
import com.gsww.entity.Token;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
public class OAuth2Token implements AuthenticationToken {

	/**
	 * 授权code
	 */
	private String code;
	
	/**
	 * 用户信息
	 */
	private SSOUser user;
	
	/**
	 * token
	 */
    private Token token;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SSOUser getUser() {
		return user;
	}

	public void setUser(SSOUser user) {
		this.user = user;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
