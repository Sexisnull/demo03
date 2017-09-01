package com.gsww.uids.sso.http.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gsww.uids.manager.sso.entity.AccessTokenResponse;
import com.gsww.uids.manager.sso.service.AccessTokenResponseService;
import com.gsww.uids.manager.sso.service.OauthService;

import net.sf.json.JSONObject;

/**
 * 单点退出的Controller
 * 
 * @author taolm
 *
 */
@RestController
@RequestMapping("/sso")
public class LogoutConroller {
	
	@Autowired
	private AccessTokenResponseService tokenResponseService;
	
	@Autowired
	private OauthService oauthService;
	
	/**
	 * 单点退出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String token(HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = new JSONObject();
		
		// 获取参数
		String accessToken = request.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
		String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
		String clientSecret = request.getParameter(OAuth.OAUTH_CLIENT_SECRET);
		
		// 检查client
		StringBuilder err = new StringBuilder(100);
        if ( !oauthService.checkClient(clientId, clientSecret, err) ) {
        	resultJson.put("state", 0);
        	resultJson.put("info", err.toString());
        	return resultJson.toString();
        }
        
        // 检查accessToken
        if ( !oauthService.checkAccessToken(accessToken, err) ) {
        	resultJson.put("state", 0);
        	resultJson.put("info", err.toString());
        	return resultJson.toString();
        }

        // 退出
        AccessTokenResponse accessTokenResponse = tokenResponseService.getByAccessToken(accessToken);
        tokenResponseService.logout(accessTokenResponse);
        
        // 返回
        resultJson.put("state", 1);
    	resultJson.put("info", "单点退出成功");
    	return resultJson.toString();
	}
}
