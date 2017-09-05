package com.gsww.uids.manager.sso.service;

import java.util.Set;

import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.stereotype.Service;

import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.sso.entity.AccessTokenResponse;
import com.gsww.uids.manager.sso.entity.SSOUser;
import com.gsww.uids.manager.user.entity.User;

/**
 * oauth2的业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface OauthService {

	/**
	 * 定时清空过期记录
	 */
	void timedClean();
	
	/**
	 * 生成authcode
	 * 
	 * @return
	 * @throws OAuthSystemException
	 */
	String generateAuthcode() throws OAuthSystemException;
	
	/**
	 * 生成refreshToken
	 * 
	 * @return
	 * @throws OAuthSystemException
	 */
	String generateRefreshToken() throws OAuthSystemException;
	
	/**
	 * 生成accessToken
	 * 
	 * @return
	 * @throws OAuthSystemException; 
	 */
	String generateAccessToken() throws OAuthSystemException;
	
	/**
	 * 根据authcode，生成accessToken
	 * 
	 * @param authcode
	 * @return
	 * @throws OAuthSystemException
	 * @throws OAuthRuntimeException
	 */
	String generateAccessTokenWithAuthcode(String authcode) throws OAuthSystemException, OAuthRuntimeException;
	
	/**
	 * 根据accessToken获取用户信息
	 * 相互信任的应用可以使用同一个accessToken获取用户信息
	 * 
	 * @param accessToken
	 * @param coClientId
	 * @param redirectUri
	 * @return
	 * @throws OAuthRuntimeException; 
	 */
	SSOUser getUserByAccessToken(String accessToken, String coClientId, String redirectUri) throws OAuthRuntimeException;
	
	/**
	 * 验证授权码的有效性
	 * 
	 * @param authcode
	 * @param err
	 * @return
	 */
	boolean checkAuthcode(String authcode, StringBuilder err);
	
	/**
	 * 验证accessToken的有效性
	 * 
	 * @param accessToken
	 * @param err
	 * @return
	 */
	boolean checkAccessToken(String accessToken, StringBuilder err);
	
	/**
	 * 验证response_type
	 * 
	 * @param responseType
	 * @param err
	 * @return
	 */
	boolean checkResponseType(String responseType, StringBuilder err);
	
	/**
	 * 验证grant_type
	 * 
	 * @param grantType
	 * @param err
	 * @return
	 */
	boolean checkGrantType(String grantType, StringBuilder err);
	
	/**
	 * 验证client
	 * 
	 * @param clientId
	 * @param clientKey
	 * @param err
	 * @return
	 */
	boolean checkClient(String clientId, String clientKey, StringBuilder err);
	
	/**
	 * 验证clientId
	 * 
	 * @param clientId
	 * @param err
	 * @return
	 */
	boolean checkClientId(String clientId, StringBuilder err);
	
	/**
	 * 刷新accessToken
	 * 重载 void refreshAccessToken(AccessTokenResponse obj)
	 * 
	 * @param refreshToken
	 * @throws OAuthSystemException, OAuthRuntimeException; 
	 */
	void refreshAccessToken(String refreshToken) throws OAuthSystemException, OAuthRuntimeException;
	
	/**
	 * 刷新accessToken
	 * 
	 * @param obj
	 * @throws OAuthSystemException, OAuthRuntimeException; 
	 */
	void refreshAccessToken(AccessTokenResponse obj) throws OAuthSystemException, OAuthRuntimeException;
	
	/**
     * 将scope用分隔符串成一个字符串
     * 
     * @param scopes
     * @param split
     * @return
     */
    String concatScopes(Set<String> scopes, String split);
    
    /**
     * 将用户封装成json字符串，提供给应用
     * 
     * @param user
     * @param loginAccount 单点登录账号
     * @param loginApp 发起单点登录的应用
     * @return
     */
    SSOUser encapsulateUserInfo(User user, Account loginAccount, Application loginApp);
    
    /**
     * 加密某个应用下的数据
     * 
     * @param clientId
     * @param data
     * @return
     * @throws OAuthRuntimeException
     */
    String encrypt(String clientId, String data) throws OAuthRuntimeException;
    
    /**
     * 解密某个应用下的数据
     * 
     * @param clientId
     * @param data
     * @return
     * @throws OAuthRuntimeException
     */
    String decrypt(String clientId, String data) throws OAuthRuntimeException;
}