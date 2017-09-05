<%@page import="org.omg.CORBA.Request, java.util.Date, 
org.apache.oltu.oauth2.client.OAuthClient,
org.apache.oltu.oauth2.client.URLConnectionClient,
org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest,
org.apache.oltu.oauth2.client.request.OAuthClientRequest,
org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse,
org.apache.oltu.oauth2.client.response.OAuthResourceResponse,
org.apache.oltu.oauth2.common.OAuth,
org.apache.oltu.oauth2.common.message.types.GrantType,
org.apache.shiro.SecurityUtils,
org.apache.shiro.authc.AuthenticationInfo,
org.apache.shiro.authc.AuthenticationToken,
org.apache.shiro.authc.SimpleAuthenticationInfo,
org.apache.shiro.authz.AuthorizationInfo,
org.apache.shiro.authz.SimpleAuthorizationInfo,
org.apache.shiro.realm.AuthorizingRealm,
org.apache.shiro.subject.PrincipalCollection,
org.springframework.util.StringUtils,
com.gsww.entity.SSOUser,
com.gsww.entity.Token,
net.sf.json.JSONException,
net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>刷新token</title>
</head>
<body>	

<p>刷新前的accessToken为'<%= ((Token)request.getSession().getAttribute("token")).getAccessToken() %>'，
刷新前的refreshToken为'<%= ((Token)request.getSession().getAttribute("token")).getRefreshToken() %>'</p>
	
<%

	final String serverRefreshTokenUrl = "http://10.18.27.74:8080/uids/oauth/accessToken.uids";
	final String clientId = "725eabbf8e0d4fc6bc9aacaac04e0701";
	final String clientSecret = "781a2339a1b14bb68d456ab85300f647";

	Token oldToken = (Token)request.getSession().getAttribute("token");
	try {
		System.out.print("刷新前的refreshtoken：" + oldToken.getRefreshToken());
		
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        // 根据code请求accessToken
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(serverRefreshTokenUrl)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(oldToken.getRefreshToken())
                .buildQueryMessage();

        // 应答
        OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

        // 获取应答中的token
        Token newToken = new Token();
        newToken.setAccessToken(oAuthResponse.getAccessToken());
        newToken.setExpiresIn(oAuthResponse.getExpiresIn());
        newToken.setRefreshToken(oAuthResponse.getRefreshToken());
        newToken.setScopes(oAuthResponse.getScope());
        newToken.setStartTime(new Date());
        newToken.setTokenType(oAuthResponse.getParam(OAuth.OAUTH_TOKEN_TYPE));
        System.out.print("刷新后的refreshtoken：" + newToken.getRefreshToken());
        
        // 放进session中
        request.getSession().setAttribute("token", newToken);
        
	} catch (Exception e) {
        e.printStackTrace();
    }
%>
	
<p>刷新后的accessToken为'<%= ((Token)request.getSession().getAttribute("token")).getAccessToken() %>'，
刷新后的refreshToken为'<%= ((Token)request.getSession().getAttribute("token")).getRefreshToken() %>'</p>
	
</body>
</html>