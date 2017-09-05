package com.gsww.uids.sso.rpc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 服务端：单点登录时提供身份认证RPC服务的接口
 * 
 * @author taolm
 *
 */
public interface AuthenticateService extends Remote {
	
	/**
	 * 验证用户名密码，如果通过，则返回授权码，否则返回错误信息
	 * 
	 * @param responseType
	 * @param clientId
	 * @param clientIdOfAccount
	 * @param redirectUri
	 * @param accountName
	 * @param password
	 * @param scope
	 * @param state
	 * @return
	 */
	String authenticate(String responseType, String clientId, String clientIdOfAccount, String redirectUri, String accountName, String password, String scope, String state) throws RemoteException;

	/**
	 * 根据code获取accessToken，如果成功返回accessToken，否则返回错误信息
	 * 
	 * @param grantType
	 * @param authcode
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @return
	 */
	String getAccessToken(String grantType, String authcode, String clientId, String clientSecret, String redirectUri) throws RemoteException;
	
	/**
	 * 根据accessToken获取账号信息
	 * 
	 * @param accessToken
	 * @param coClientId
	 * @param coClientSecret
	 * @param redirectUri
	 * @return
	 */
	String getUserInfo(String accessToken, String coClientId, String coClientSecret, String redirectUri) throws RemoteException;
	
	/**
	 * 刷新accessToken
	 * 
	 * @param grantType
	 * @param refreshToken
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	String refreshAccessToken(String grantType, String refreshToken, String clientId, String clientSecret) throws RemoteException;
	
	/**
	 * 退出
	 * 
	 * @param accessToken
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws RemoteException
	 */
	String logout(String accessToken, String clientId, String clientSecret) throws RemoteException;}
