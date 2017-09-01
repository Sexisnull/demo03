package com.gsww.uids.sso.http.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.sso.entity.SSOUser;
import com.gsww.uids.manager.sso.service.OauthService;
import com.gsww.uids.sso.OAuthConstants;

import net.sf.json.JSONObject;

/**
 * oauth认证第三步：利用 accessToken 获取保护信息，即用户名等信息
 * 
 * @author taolm
 *
 */
@RestController
@RequestMapping("/oauth")
public class UserInfoController {

	@Autowired
	private OauthService oauthService;

    /**
     * 获取用户信息
     * 
     * @param request
     * @return
     * @throws OAuthSystemException
     */
    @RequestMapping("/userInfo")
    public HttpEntity userInfo(HttpServletRequest request) throws OAuthSystemException {
    	
        try {
            // 构建OAuth资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            
            // 查找用户信息：标准的oauth协议中只有accessToken，附加信息都放在header中
            String redirectURI = request.getHeader(OAuth.OAUTH_REDIRECT_URI);
            String coClientId = request.getHeader(OAuth.OAUTH_CLIENT_ID);
            String coClientSecret = request.getHeader(OAuth.OAUTH_CLIENT_SECRET);
            
            // 检查redirectUri不能为空
            if ( StringUtil.isBlank(redirectURI) ) {
            	return new ResponseEntity("OAuth请求必须携带redirect_uri", HttpStatus.NOT_FOUND);
            }
            
            // 要获取账号信息的应用
            StringBuilder err = new StringBuilder(100);
    		if ( !oauthService.checkClient(coClientId, coClientSecret, err) ) {
    			OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(OAuthConstants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
    			return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    		}
    		
            // 验证Access Token
            String accessToken = oauthRequest.getAccessToken();            
            if ( !oauthService.checkAccessToken(accessToken, err) ) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(OAuthConstants.RESOURCE_SERVER_NAME)
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
            
            // 获取用户信息
            SSOUser user = oauthService.getUserByAccessToken(accessToken, coClientId, redirectURI);
            if ( user == null ) {
            	return new ResponseEntity("账号没有绑定用户", HttpStatus.UNAUTHORIZED);
            }
            
            // 将用户信息封装成json字符串
            String userInfo = JSONObject.fromObject(user).toString();
            
            // 加密用户信息
            String userCipherInfo = oauthService.encrypt(coClientId, userInfo);
            return new ResponseEntity(userCipherInfo, HttpStatus.OK);
            
        } catch (OAuthProblemException e) {
        	
            // 检查是否设置了错误码
            String errorCode = e.getError();
            if ( OAuthUtils.isEmpty(errorCode) ) {
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(OAuthConstants.RESOURCE_SERVER_NAME)
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(OAuthConstants.RESOURCE_SERVER_NAME)
                    .setError(e.getError())
                    .setErrorDescription(e.getDescription())
                    .setErrorUri(e.getUri())
                    .buildHeaderMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (OAuthRuntimeException e2) {
        	 OAuthResponse oauthResponse = OAuthRSResponse
                     .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                     .setRealm(OAuthConstants.RESOURCE_SERVER_NAME)
                     .buildHeaderMessage();

             HttpHeaders headers = new HttpHeaders();
             headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
             return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
        }
    }
}
