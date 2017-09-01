package com.gsww.oauth2;

import java.io.IOException;
import java.util.Date;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import com.gsww.entity.SSOUser;
import com.gsww.entity.Token;

import net.sf.json.JSONObject;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 */
public class OAuth2Realm extends AuthorizingRealm {

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl;
    private String encryptType;
    private String encryptSecret;
    private String encryptSalt;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public void setEncryptSecret(String encryptSecret) {
		this.encryptSecret = encryptSecret;
	}

	public void setEncryptSalt(String encryptSalt) {
		this.encryptSalt = encryptSalt;
	}

	@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;//琛ㄧず姝ealm鍙敮鎸丱Auth2Token绫诲瀷
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	
    	// Token：token可能从其它应用跳转过来时已经携带了
    	OAuth2Token oAuth2Token = (OAuth2Token) token;
    	Token tokenEntity = oAuth2Token.getToken();
    	
    	// 如果不是从其它应用跳转过来的，则需要去获取code
    	if ( tokenEntity == null ) {
    		// 取出code        
    		String code = oAuth2Token.getCode();
    		if ( StringUtils.isEmpty(code) ) {
    			return null;
    		}
        
    		// 获取token
    		tokenEntity = extractToken(code);
    		if ( tokenEntity ==  null ) {
    			return null;
    		}
    		oAuth2Token.setToken(tokenEntity);
    	}
        
        // 根据token获取用户信息
        SSOUser user = extractUsername(tokenEntity.getAccessToken());
        if ( StringUtils.isEmpty(user.getUuid()) ) {
        	return null;
        }
        oAuth2Token.setUser(user);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, tokenEntity, getName());
        return authenticationInfo;
    }

    /**
     * 根据code获取token
     * 
     * @param code
     * @return
     */
    private Token extractToken(String code) {
    	
    	try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            // 根据code请求accessToken
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
	                .tokenLocation(this.accessTokenUrl)
	                .setGrantType(GrantType.AUTHORIZATION_CODE)
	                .setClientId(this.clientId)
	                .setClientSecret(this.clientSecret)
	                .setCode(code)
	                .setRedirectURI(this.redirectUrl)
	                .buildQueryMessage();

            // 应答
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

            // 获取应答中的token
	        Token token = new Token();
	        token.setAccessToken(oAuthResponse.getAccessToken());
	        token.setExpiresIn(oAuthResponse.getExpiresIn());
	        token.setRefreshToken(oAuthResponse.getRefreshToken());
	        token.setScopes(oAuthResponse.getScope());
	        token.setStartTime(new Date());
	        token.setTokenType(oAuthResponse.getParam(OAuth.OAUTH_TOKEN_TYPE));
	        
	        return token;
	        
    	} catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e);
        }
    }
    
    /**
     * 根据accessToken获取用户信息
     * 
     * @param accessToken
     * @return
     */
    private SSOUser extractUsername(String accessToken) {

        try {            
        	OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        	
        	// 根据accessToken获取用户信息
            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(this.userInfoUrl)
                    .setAccessToken(accessToken).buildQueryMessage();
            userInfoRequest.setHeader(OAuth.OAUTH_CLIENT_ID, this.clientId);
            userInfoRequest.setHeader(OAuth.OAUTH_CLIENT_SECRET, this.clientSecret);
            userInfoRequest.setHeader(OAuth.OAUTH_REDIRECT_URI, this.redirectUrl);

            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String userinfo = resourceResponse.getBody();
            
            // 解密用户信息
            final String AESAndBase64EncryptType = "AES+BASE64";
            if ( !AESAndBase64EncryptType.equalsIgnoreCase(encryptType) ) {
				throw new IOException(String.format("不支持的加密方式【%s】，目前只支持【AES+BASE64】加密方式", encryptType));
			}
			String decryptUserInfo = AESOperator.decrypt(encryptSecret, encryptSalt, userinfo);
            
            // 解析json字符串
            return parseUser(decryptUserInfo);  
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e);
        }
    }
    
    /**
     * 解析用户信息
     * 
     * @param userJsonStr
     * @return
     */
    private SSOUser parseUser(String userJsonStr) {
		
    	JSONObject json = JSONObject.fromObject(userJsonStr); 
		SSOUser user = (SSOUser)JSONObject.toBean(json, SSOUser.class);		
		return user;
	}
}
