package com.gsww.uids.sso.rpc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.sso.entity.AccessTokenRequest;
import com.gsww.uids.manager.sso.entity.AccessTokenResponse;
import com.gsww.uids.manager.sso.entity.AuthcodeRequest;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;
import com.gsww.uids.manager.sso.entity.SSOUser;
import com.gsww.uids.manager.sso.service.AccessTokenRequestService;
import com.gsww.uids.manager.sso.service.AccessTokenResponseService;
import com.gsww.uids.manager.sso.service.AuthcodeRequestService;
import com.gsww.uids.manager.sso.service.AuthcodeResponseService;
import com.gsww.uids.manager.sso.service.OauthService;
import com.gsww.uids.sso.rpc.service.AuthenticateService;

import net.sf.json.JSONObject;

/**
 * 单点登录时提供身份认证RPC服务的实现
 * 
 * @author taolm
 *
 */
@Service
public class AuthenticateServiceImpl extends UnicastRemoteObject implements AuthenticateService {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1756880928559695425L;
	
	@Autowired
	private transient AuthcodeRequestService authcodeRequestService;
	
	@Autowired
	private transient AuthcodeResponseService authcodeResponseService;
	
	@Autowired
	private transient AccessTokenRequestService tokenRequestService;
	
	@Autowired
	private transient AccessTokenResponseService tokenResponseService;
	
    @Autowired
    private transient OauthService authService;
    
    @Autowired
    private transient ApplicationService appService;
    
    @Autowired
    private transient AccountService accountService;

	public AuthenticateServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String authenticate(String responseType, String clientId, String clientIdOfAccount, String redirectUri, String accountName,
			String password, String scope, String state) throws RemoteException {
		
		// 请求时间
    	String requestTime = TimeHelper.getCurrentTime();
    	
    	// responseType仅支持CODE
        StringBuilder err = new StringBuilder(100);
        if ( !authService.checkResponseType(responseType, err) ) {
        	throw new RemoteException(err.toString());
        }
        
        // 检查传入的客户端id是否正确
        if ( !authService.checkClientId(clientId, err) || !authService.checkClientId(clientIdOfAccount, err) ) {
        	throw new RemoteException(err.toString());
        }
        
        // 检查redirectUri不能为空
        if ( StringUtil.isBlank(redirectUri) ) {
        	throw new RemoteException("OAuth callback url needs to be provided by client!!!");
        }
        
        // 现将用户名和密码解密
        String decryptAccountName = null;
        String decryptAccountPassword = null;
        try {
        	decryptAccountName = authService.decrypt(clientId, accountName);
        	decryptAccountPassword = authService.decrypt(clientId, password);
    	} catch ( OAuthRuntimeException e ) {
    		throw new RemoteException(e.getMessage());
    	} 
        
        // 验证用户名密码
        Application app = appService.findByClientId(clientIdOfAccount);
        if ( !accountService.validate(app.getUuid(), decryptAccountName, decryptAccountPassword) ) {
        	throw new RemoteException("账号或密码有误");
        }
        
 	    // 获取authcode
        String authcode = null;
        Subject subject = SecurityUtils.getSubject();
        if ( subject.isAuthenticated() ) {
        	throw new RemoteException("不能重复获取code");
        } else {
        	try {
				authcode = authService.generateAuthcode();
			} catch (OAuthSystemException e) {
				throw new RemoteException("生成code失败");
			}
        	
        	// 保存请求
            AuthcodeRequest authcodeRequest = new AuthcodeRequest();
            authcodeRequest.setAccountName(decryptAccountName);
            authcodeRequest.setClientId(clientId);
            authcodeRequest.setClientIdOfAccount(clientIdOfAccount);
            authcodeRequest.setRedirectUri(redirectUri);
            // TODO 如何获取调用方的ip地址
            authcodeRequest.setRequestIp("127.0.0.1");
            authcodeRequest.setRequestTime(requestTime);
            authcodeRequest.setResponseType(responseType);
            authcodeRequest.setScope(scope);
            authcodeRequest.setState(state);
            authcodeRequestService.persist(authcodeRequest);
            // 保存应答
            AuthcodeResponse authcodeResponse = new AuthcodeResponse();
            authcodeResponse.setAuthcode(authcode);
            authcodeResponse.setRequest(authcodeRequest);
            authcodeResponse.setResponseTime(TimeHelper.getCurrentTime());
            authcodeResponseService.persist(authcodeResponse);        	
        }
            
        // 返回authcode
        JSONObject result = new JSONObject();
    	result.put(OAuth.OAUTH_CODE, authcode);
    	return result.toString();  
	}

	@Override
	public String getAccessToken(String grantType, String authcode, String clientId, String clientSecret, 
			String redirectUri) throws RemoteException {
		
		// 请求时间
    	String requestTime = TimeHelper.getCurrentTime();
    	
    	// 检查验证类型，此处只检查AUTHORIZATION_CODE类型
        if ( !GrantType.AUTHORIZATION_CODE.toString().equals(grantType) ) {
        	throw new RemoteException("只支持grantType=authorization_code");
        }

        // 检查提交的客户端id和secret是否正确
        StringBuilder err = new StringBuilder(100);
        if ( !authService.checkClient(clientId, clientSecret, err) ) {
        	throw new RemoteException(err.toString());
        }

        // 验证授权码的有效性
        if ( !authService.checkAuthcode(authcode, err) ) {
        	throw new RemoteException(err.toString());
        }

        // 验证clientId，redirectUri等的一致性
        AuthcodeResponse authcodeResponse = authcodeResponseService.getByAuthcode(authcode);
        if ( !authcodeResponse.getRequest().getClientId().equals(clientId) ) {
        	throw new RemoteException("clientId和请求code时不一致");
        }
        if ( !authcodeResponse.getRequest().getRedirectUri().equals(redirectUri) ) {
        	throw new RemoteException("redirect_uri和请求code时不一致");
        }
        
        // 生成Access Token
        String accessToken = null;
		try {
			accessToken = authService.generateAccessTokenWithAuthcode(authcode);
		} catch (Exception e) {
			throw new RemoteException("生成accessToken失败");
		}
		
		// 生成refreshToken
		String refreshToken = null;
		try {
			refreshToken = authService.generateRefreshToken();
		} catch (Exception e) {
			throw new RemoteException("生成refreshToken失败");
		}
        
        // 保存accessToken请求记录
        AccessTokenRequest tokenRequest = new AccessTokenRequest();
        tokenRequest.setAuthcode(authcode);
        tokenRequest.setClientId(clientId);
        tokenRequest.setClientSecret(clientSecret);
        tokenRequest.setGrantType(grantType);
        tokenRequest.setRedirectUri(redirectUri);
        // TODO 如何获取调用方的ip地址 
        tokenRequest.setRequestIp("127.0.0.1");
        tokenRequest.setRequestTime(requestTime);
        tokenRequestService.persist(tokenRequest);
        // 保存accessToken应答记录
        AccessTokenResponse tokenResponse = new AccessTokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString());
        tokenResponse.setRefreshToken(refreshToken);
        // TODO 令牌有效时间，应该从系统设置中获取，这里暂时写一个固定的值
        tokenResponse.setExpiresIn(3600L);
        tokenResponse.setRequest(tokenRequest);
        tokenResponse.setResponseTime(TimeHelper.getCurrentTime());
        tokenResponse.setScope(authcodeResponse.getRequest().getScope());
        tokenResponseService.persist(tokenResponse);
        
        // 返回accessToken
    	JSONObject result = new JSONObject();
    	result.put(OAuth.OAUTH_ACCESS_TOKEN, accessToken);
    	result.put(OAuth.OAUTH_TOKEN_TYPE, tokenResponse.getTokenType());
    	result.put(OAuth.OAUTH_REFRESH_TOKEN, refreshToken);
    	result.put(OAuth.OAUTH_EXPIRES_IN, tokenResponse.getExpiresIn());
    	result.put(OAuth.OAUTH_SCOPE, tokenResponse.getScope());
    	return result.toString();  
	}

	@Override
	public String getUserInfo(String accessToken, String coClientId, String coClientSecret, String redirectUri) 
			throws RemoteException {  	
    	
		// 检查redirectUri不能为空
        if ( StringUtil.isBlank(redirectUri) ) {
        	throw new RemoteException("OAuth请求必须携带redirect_uri");
        }
        
        // 要获取账号信息的应用
        StringBuilder err = new StringBuilder(100);
		if ( !authService.checkClient(coClientId, coClientSecret, err) ) {
			throw new RemoteException(err.toString());
		}
		
        // 验证Access Token
        if ( !authService.checkAccessToken(accessToken, err) ) {
            // 如果不存在/过期了，返回未验证错误，需重新验证
        	throw new RemoteException(err.toString());
        }
        
    	// 获取用户信息
    	SSOUser user = null;
    	try {
    		user = authService.getUserByAccessToken(accessToken, coClientId, redirectUri);
    	} catch ( OAuthRuntimeException e ) {
    		throw new RemoteException(e.getMessage());
    	}
        
        // 将用户信息封装成json字符串
    	String userInfo = JSONObject.fromObject(user).toString();
    	
    	try {
	    	// 加密用户信息
	        return authService.encrypt(coClientId, userInfo);
    	} catch ( OAuthRuntimeException e ) {
    		throw new RemoteException(e.getMessage());
    	}        
	}

	@Override
	public String refreshAccessToken(String grantType, String refreshToken, String clientId, String clientSecret) throws RemoteException {
		
		// 检查验证类型，此处只检查AUTHORIZATION_CODE类型
        if ( !GrantType.REFRESH_TOKEN.toString().equals(grantType) ) {
        	throw new RemoteException("只支持grantType=refresh_token");
        }
        
        // 检查提交的客户端id和secret是否正确
        StringBuilder err = new StringBuilder(100);
        if ( !authService.checkClient(clientId, clientSecret, err) ) {
        	throw new RemoteException(err.toString());
        }

        // 验证refreshToken的有效性
        AccessTokenResponse tokenResponse = tokenResponseService.getByRefreshToken(refreshToken);
        if ( tokenResponse == null ) {
        	throw new RemoteException("无效的refresh_token");
        }

        // 刷新accessToken
		try {
			authService.refreshAccessToken(tokenResponse);
		} catch ( Exception e ) {
			throw new RemoteException(e.getMessage());
		}
		
		// 返回有效期
		JSONObject result = new JSONObject();
		result.put(OAuth.OAUTH_ACCESS_TOKEN, tokenResponse.getAccessToken());
    	result.put(OAuth.OAUTH_TOKEN_TYPE, tokenResponse.getTokenType());
    	result.put(OAuth.OAUTH_REFRESH_TOKEN, tokenResponse.getRefreshToken());
    	result.put(OAuth.OAUTH_EXPIRES_IN, tokenResponse.getExpiresIn());
    	result.put(OAuth.OAUTH_SCOPE, tokenResponse.getScope());
    	return result.toString();  
	}

	@Override
	public String logout(String accessToken, String clientId, String clientSecret) throws RemoteException {
		
		// 检查client
		StringBuilder err = new StringBuilder(100);
        if ( !authService.checkClient(clientId, clientSecret, err) ) {
        	throw new RemoteException(err.toString());
        }
        
        // 检查accessToken
        if ( !authService.checkAccessToken(accessToken, err) ) {
        	throw new RemoteException(err.toString());
        }

        // 退出
        AccessTokenResponse accessTokenResponse = tokenResponseService.getByAccessToken(accessToken);
        tokenResponseService.logout(accessTokenResponse);
        
        // 返回
        JSONObject result = new JSONObject();
        result.put("status", 1);
        result.put("body", "单点退出成功");
    	return result.toString(); 
	}
}