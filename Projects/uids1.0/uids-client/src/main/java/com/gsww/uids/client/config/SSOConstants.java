package com.gsww.uids.client.config;

/**
 * sso相关的常量
 * 
 * @author taolm
 *
 */
public class SSOConstants {
	
	/**
	 * 处理从应用端单点登录的servlet路径
	 */
	public static final String SSO_LOGIN_PROXY_SERVLET = "/SSOProxyServlet";
	
	/**
	 * 处理从服务端单点登录的servlet路径
	 */
	public static final String GET_LONG_URL_SERVLET = "/GetLongUrlServlet";
	
	/**
	 * redirect转向类型
	 */
	public static final String REDIRECT_TYPE = "redirect";
	
	/**
	 * forward转向类型
	 */
	public static final String FORWARD_TYPE = "forward";
	
	/**
	 * 单点登录结果码
	 */
	public static final String RESPONSE_CODE = "code";
	
	/**
	 * 单点登录结果描述信息
	 */
	public static final String 	RESPONSE_DESCRIPTION = "description";
	
	/**
	 * AES + BASE64 加密类型
	 */
	public static final String AES_AND_BASE64_ENCRYPT_TYPE = "AES+BASE64";
}
