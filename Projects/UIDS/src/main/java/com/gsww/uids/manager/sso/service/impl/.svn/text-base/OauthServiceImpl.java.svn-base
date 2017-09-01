package com.gsww.uids.manager.sso.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
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
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.sso.AESOperator;

/**
 * oauth2的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class OauthServiceImpl implements OauthService {

	@Autowired
	private AuthcodeRequestService authcodeRequestService;
	
	@Autowired
	private AuthcodeResponseService authcodeResponseService;
	
	@Autowired
	private AccessTokenRequestService accessTokenRequestService;
	
	@Autowired
	private AccessTokenResponseService accessTokenResponseService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private SourceAuthService resourceAuthService;	
	
	@Autowired
	private AccountService accountService;

	@Override
	@Scheduled(cron = "0 0 1 * * ? ")
	@Transactional
	public void timedClean() {		
		// 删除token已经过期的记录
		List<AccessTokenResponse> accessTokenResponses = accessTokenResponseService.getInvalid();
		for ( AccessTokenResponse accessTokenResponse : accessTokenResponses ) {
			AccessTokenRequest accessTokenRequest = accessTokenResponse.getRequest();
			String authcode = accessTokenRequest.getAuthcode();
			accessTokenResponseService.remove(accessTokenResponse);
			accessTokenRequestService.remove(accessTokenRequest);
			
			// 同时将关联的authcode请求和应答记录删除
			AuthcodeResponse authcodeResponse = authcodeResponseService.getByAuthcode(authcode);
			AuthcodeRequest authcodeRequest = authcodeResponse.getRequest();
			authcodeResponseService.remove(authcodeResponse);
			authcodeRequestService.remove(authcodeRequest);
		}
		
		// 删除授权码过期或者使用过的记录
		List<AuthcodeResponse> authcodeResponses = authcodeResponseService.getInvalid();
		for ( AuthcodeResponse authcodeResponse : authcodeResponses ) {
			AuthcodeRequest authcodeRequest = authcodeResponse.getRequest();
			authcodeResponseService.remove(authcodeResponse);
			authcodeRequestService.remove(authcodeRequest);
		}
	}

	@Override
	public String generateAuthcode() throws OAuthSystemException {
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        return oauthIssuerImpl.authorizationCode();
	}

	@Override
	public String generateRefreshToken() throws OAuthSystemException {
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        return oauthIssuerImpl.refreshToken();
	}

	@Override
	public String generateAccessToken() throws OAuthSystemException {
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		return oauthIssuerImpl.accessToken();
	}

	@Override
	@Transactional
	public String generateAccessTokenWithAuthcode(String authcode) throws OAuthSystemException, OAuthRuntimeException {
		// 如果authcode不存在，或者已经用过，则不能生成accessToken
		StringBuilder err = new StringBuilder(100);
		if ( !this.checkAuthcode(authcode, err) ) {
			throw new OAuthRuntimeException(err.toString());
		}
		AuthcodeResponse authcodeResponse = authcodeResponseService.getByAuthcode(authcode);
	
		// 生成accessToken
		String accessToken = null;
        try {
			accessToken = this.generateAccessToken();
		} catch (OAuthSystemException e) {
			throw new OAuthSystemException("系统错误：无法生成accessToken");
		}
        
        // 标记authcode已经使用过
        authcodeResponseService.useAuthcode(authcodeResponse);
        
        // 返回accessToken
        return accessToken;
	}

	@Override
	@Transactional
	public SSOUser getUserByAccessToken(String accessToken, String coClientId, String redirectUri)
			throws OAuthRuntimeException {
		// 验证accessToken
		AccessTokenResponse accessTokenResponse = accessTokenResponseService.getByAccessToken(accessToken);
		if ( accessTokenResponse == null ) {
			throw new OAuthRuntimeException(String.format("无效的accessToken【%s】", accessToken));
		}
		
		// 查找登录的账号
		Account loginAccount = authcodeResponseService.getAccountByAuthcode(accessTokenResponse.getRequest().getAuthcode());
		if ( loginAccount == null ) {
			throw new OAuthRuntimeException("系统错误：无法获取用户信息");
		}
		
		// 绑定的用户
		User user = loginAccount.getUser();
		if ( user == null ) {
			throw new OAuthRuntimeException("系统错误：账号没有绑定用户");
		}

		// 应用
		Application app = applicationService.findByClientId(coClientId);
		if ( app == null ) {
			throw new OAuthRuntimeException(String.format("无效的clientId【%】", coClientId));
		}
		
		// 检查是否有权限访问redirectUri
		if ( !resourceAuthService.canAccessResource(loginAccount, app, redirectUri) ) {
			throw new OAuthRuntimeException("用户没有访问此url的权限");
		}
		
		// 封装成返回信息
		return encapsulateUserInfo(user, loginAccount, app);
	}

	@Override
	public boolean checkAuthcode(String authcode, StringBuilder err) {
		return authcodeResponseService.canUseAuthcode(authcode, err);
	}

	@Override
	public boolean checkAccessToken(String accessToken, StringBuilder err) {
		return accessTokenResponseService.isAccessTokenValid(accessToken, err);
	}
	
	@Override
	public boolean checkResponseType(String responseType, StringBuilder err) {
		if ( !ResponseType.CODE.toString().equals(responseType) ) {
			err.append("response_type 仅支持 code 类型");
        	return false;
        }
		
		return true;
	}

	@Override
	public boolean checkGrantType(String grantType, StringBuilder err) {
		if ( !GrantType.AUTHORIZATION_CODE.toString().equals(grantType) 
				&& !GrantType.REFRESH_TOKEN.toString().equals(grantType) ) {
			err.append("grant_type仅支持 authorization_code和refresh_token类型");
        	return false;
        }
		
		return true;
	}

	@Override
	public boolean checkClient(String clientId, String clientKey, StringBuilder err) {
		if ( !applicationService.checkClientAndKey(clientId, clientKey) ) {
			err.append("应用的clientId和clientSecret错误");
			return false;
		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean checkClientId(String clientId, StringBuilder err) {
		Application app = applicationService.findByClientId(clientId);
        if ( app == null ) {
        	err.append("错误的clientId");
        	return false;
        }
        
        return true;
	}
	
	@Override
	@Transactional
	public void refreshAccessToken(String refreshToken) throws OAuthSystemException, OAuthRuntimeException {
		
		AccessTokenResponse tokenResponse = accessTokenResponseService.getByRefreshToken(refreshToken);
		refreshAccessToken(tokenResponse);
		
	}

	@Override
	public void refreshAccessToken(AccessTokenResponse obj) throws OAuthSystemException, OAuthRuntimeException {
		if ( obj == null ) {
			throw new OAuthRuntimeException("错误或过期的refreshToken");
		}
		
		// 检查是否过期
		if ( !obj.canRefresh() ) {
			throw new OAuthRuntimeException("过期的refreshToken");
		}
		
		// 生成信息accessToken和refreshToken
		String newAccessToken = this.generateAccessToken();
		String newRefreshToken = this.generateRefreshToken();
		
		// 刷新记录
		accessTokenResponseService.refreshAccessToken(obj, newAccessToken, newRefreshToken);
	}

	@Override
	public String concatScopes(Set<String> scopes, String split) {
    	
    	String tempSplit = "";
    	StringBuilder scopeBuilder = new StringBuilder(100);
    	for ( String scope : scopes ) {
    		scopeBuilder.append(tempSplit);
    		scopeBuilder.append(scope);
    		tempSplit = split;
    	}
    	
    	return scopeBuilder.toString();
    }

	@Override
	public SSOUser encapsulateUserInfo(User user, Account loginAccount, Application loginApp) {
		
		SSOUser ssoUser = new SSOUser();
		
		ssoUser.setUuid(user.getUuid());
		ssoUser.setUserType(user.getType());
		ssoUser.setCorporateType(user.getCorporateType());
		ssoUser.setIdentity(user.getIdentity());
		ssoUser.setOrgCode(user.getOrgCode());
		ssoUser.setCorpName(user.getCorporateName());
		ssoUser.setCreditID(user.getCreditCode());
		ssoUser.setFax(user.getDetail().getFax());
		ssoUser.setTel(user.getDetail().getCompanyTel());
		ssoUser.setTrueName(user.getName());
		ssoUser.setEmail(user.getEmail());
		ssoUser.setMobile(user.getMobile());
		Area birthArea = user.getBirthArea();
		if ( birthArea != null ) {
			ssoUser.setRegFrom(birthArea.generateFullName());
		}
		ssoUser.setCreatedDate(user.getCreateTime());
		ssoUser.setLoginAccountType(loginAccount.getType());
		
		Organization org = user.getOrg();
		if ( org != null ) {
			ssoUser.setOrgId(org.getUuid());
			ssoUser.setOrgShortName(org.getShortName());
			ssoUser.setOrgFullName(org.getFullName());
			ssoUser.setOrgInnerCode(org.getCode());
			ssoUser.setGroupName(org.getShortName());
		}
		
		Account mainAccount = accountService.findMainAccount(user, loginAccount, loginApp);
		if ( mainAccount != null ) {
			ssoUser.setAccountType(mainAccount.getType());
			ssoUser.setUserName(mainAccount.getName());
			ssoUser.setNickName(mainAccount.getNickname());	
		}
			
		return ssoUser;
	}

	@Override
	public String encrypt(String clientId, String data) throws OAuthRuntimeException {
		Application app = applicationService.findByClientId(clientId);
		if ( app == null ) {
			throw new OAuthRuntimeException("应用不存在，无法加密数据！");
		}
		
		final String AESAndBase64Type = "1";
		if ( !AESAndBase64Type.equals(app.getEnctyptionType()) ) {
			throw new OAuthRuntimeException("无法加密数据，目前只支持【AES + BASE64】加密方式！");
		}
		
		try {
			return AESOperator.encrypt(app.getEncryptionKey(), app.getEncryptionSalt(), data);
		} catch (Exception e) {
			throw new OAuthRuntimeException(e);
		}
	}

	@Override
	public String decrypt(String clientId, String data) throws OAuthRuntimeException {
		Application app = applicationService.findByClientId(clientId);
		if ( app == null ) {
			throw new OAuthRuntimeException("应用不存在，无法解密数据！");
		}
		
		final String AESAndBase64Type = "1";
		if ( !AESAndBase64Type.equals(app.getEnctyptionType()) ) {
			throw new OAuthRuntimeException("无法解密数据，目前只支持【AES + BASE64】加密方式！");
		}
		
		try {
			return AESOperator.decrypt(app.getEncryptionKey(), app.getEncryptionSalt(), data);
		} catch (Exception e) {
			throw new OAuthRuntimeException(e);
		}
	}
}
