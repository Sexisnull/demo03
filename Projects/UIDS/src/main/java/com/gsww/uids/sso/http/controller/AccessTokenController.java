package com.gsww.uids.sso.http.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsww.common.util.HttpUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.sso.entity.AccessTokenRequest;
import com.gsww.uids.manager.sso.entity.AccessTokenResponse;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;
import com.gsww.uids.manager.sso.service.AccessTokenRequestService;
import com.gsww.uids.manager.sso.service.AccessTokenResponseService;
import com.gsww.uids.manager.sso.service.AuthcodeResponseService;
import com.gsww.uids.manager.sso.service.OauthService;
import com.gsww.uids.sso.OAuthConstants;

/**
 * oauth认证第二步：利用授权码 authcode 获取 accessToken
 * 
 * @author taolm
 *
 */
@RestController
@RequestMapping("/oauth")
public class AccessTokenController {
	
	@Autowired
	private AuthcodeResponseService authcodeResponseService;

	@Autowired
	private AccessTokenRequestService tokenRequestService;
	
	@Autowired
	private AccessTokenResponseService tokenResponseService;
	
	@Autowired
	private OauthService oauthService;

	@Autowired
    private ApplicationService appService;

    /**
     * 请求accessToken
     * 
     * @param request
     * @return
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    @RequestMapping("/accessToken")
    public HttpEntity token(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

    	// 请求时间
    	String requestTime = TimeHelper.getCurrentTime();
    	
        try {
            // 构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            
            // 检查验证类型
            StringBuilder err = new StringBuilder(100);
            String grantType = oauthRequest.getGrantType();
            if ( !oauthService.checkGrantType(grantType, err) ) {
            	return new ResponseEntity(err.toString(), HttpStatus.BAD_REQUEST);
            }

            // 检查提交的客户端id是否正确
            String clientId = oauthRequest.getClientId();
            Application app = appService.findByClientId(clientId);
            if ( app == null ) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription(OAuthConstants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 检查客户端安全KEY是否正确
            String clientSecret = oauthRequest.getClientSecret();
            if ( !app.getClientKey().equalsIgnoreCase(clientSecret) ) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                                .setErrorDescription(OAuthConstants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
            
            // 如果是请求accessToken
            AccessTokenResponse accessTokenResponse = null; 
            if ( GrantType.AUTHORIZATION_CODE.toString().equals(grantType) ) {
            	// 验证授权码的有效性
                String authcode = oauthRequest.getCode();
                if ( !oauthService.checkAuthcode(authcode, err) ) {
                	OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription(err.toString())
                            .buildJSONMessage();
                    return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }

                // 验证clientId，redirectUri等的一致性
                String redirectURI = oauthRequest.getRedirectURI();
                AuthcodeResponse authcodeResponse = authcodeResponseService.getByAuthcode(authcode);
                if ( !authcodeResponse.getRequest().getClientId().equals(clientId) ) {
                	OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                            .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                            .setErrorDescription(OAuthConstants.INVALID_CLIENT_DESCRIPTION)
                            .buildJSONMessage();
                	return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
                if ( !authcodeResponse.getRequest().getRedirectUri().equals(redirectURI) ) {
                	return new ResponseEntity("无效的redirect_uri！", HttpStatus.NOT_ACCEPTABLE);
                }
                
                // 生成Access Token和refreshToken
                final String accessToken = oauthService.generateAccessTokenWithAuthcode(authcode); 
                final String refreshToken = oauthService.generateRefreshToken();
                
                // 保存accessToken请求记录
                AccessTokenRequest tokenRequest = new AccessTokenRequest();
                tokenRequest.setAuthcode(authcode);
                tokenRequest.setClientId(clientId);
                tokenRequest.setClientSecret(clientSecret);
                tokenRequest.setGrantType(grantType);
                tokenRequest.setRedirectUri(redirectURI);
                tokenRequest.setRequestIp(HttpUtil.getIpAddress(request));
                tokenRequest.setRequestTime(requestTime);
                tokenRequestService.persist(tokenRequest);
                // 保存accessToken应答记录
                accessTokenResponse = new AccessTokenResponse();
                accessTokenResponse.setAccessToken(accessToken);
                accessTokenResponse.setRefreshToken(refreshToken);
                accessTokenResponse.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString());
                // TODO 令牌有效时间，应该从系统设置中获取，这里暂时写一个固定的值
                accessTokenResponse.setExpiresIn(3600L);
                accessTokenResponse.setRequest(tokenRequest);
                accessTokenResponse.setResponseTime(TimeHelper.getCurrentTime());
                accessTokenResponse.setScope(oauthService.concatScopes(oauthRequest.getScopes(), ","));
                tokenResponseService.persist(accessTokenResponse);                
            } else { // refresh_token
            	// 获得refresh_token
            	String refreshToken = oauthRequest.getRefreshToken();
            	accessTokenResponse = tokenResponseService.getByRefreshToken(refreshToken);
            	if ( accessTokenResponse == null ) {
            		OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_ACCEPTED)
                            .setError(OAuthError.TokenResponse.INVALID_REQUEST)
                            .setErrorDescription("无效的refresh_token")
                            .buildJSONMessage();
                	return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            	}
                
                // 刷新
                oauthService.refreshAccessToken(accessTokenResponse);
            }      
            
            // 生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessTokenResponse.getAccessToken())
                    .setTokenType(accessTokenResponse.getTokenType())
                    .setRefreshToken(accessTokenResponse.getRefreshToken())
                    .setExpiresIn(String.valueOf(accessTokenResponse.getExpiresIn()))
                    .setScope(accessTokenResponse.getScope())
                    .buildJSONMessage();

            // 根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            // 构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }
    }
}
