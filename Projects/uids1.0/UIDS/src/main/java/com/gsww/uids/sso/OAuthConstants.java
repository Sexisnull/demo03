package com.gsww.uids.sso;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-17
 * <p>Version: 1.0
 */
public class OAuthConstants {

    public static String RESOURCE_SERVER_NAME = "uids-server";
    public static final String INVALID_CLIENT_DESCRIPTION = "客户端验证失败，如错误的client_id/client_secret。";
    
    /**
     * 反向代理时，应用传过来的统一身份认证的clientId
     */
    public static final String UIDS_CLIENT_ID = "uids_client_id";
    
    /**
     * 反向代理时，单点登录时使用的账号类型：1-统一身份认证账号；2-本应用下的账号
     */
    public static final String SSO_ACCOUNT_TYPE = "sso_account_type";
}
