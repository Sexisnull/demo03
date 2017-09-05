package com.gsww.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import com.gsww.entity.SSOUser;
import com.gsww.entity.Token;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 */
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {
	
	/**
	 * 将token和用户信息放在session中，对应的属性名
	 */
	private static final String USER_INFO_ATTRIBUTE = "user";
	private static final String TOKEN_INFO_ATTRIBUTE = "token";

    //oauth2 authc code参数名
    private String authcCodeParam = "code";

    private String failureUrl;
    
    public void setAuthcCodeParam(String authcCodeParam) {
        this.authcCodeParam = authcCodeParam;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String code = httpRequest.getParameter(authcCodeParam);
        OAuth2Token token = new OAuth2Token();
        token.setCode(code);
        return token;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	SSOUser user = (SSOUser) httpRequest.getSession().getAttribute(USER_INFO_ATTRIBUTE);
    	return (user != null);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

    	String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        if(!StringUtils.isEmpty(error)) {//如果服务端返回了错误
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated()) {
            if(StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
                //如果用户没有身份验证，且没有auth code，则重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {    	
    	// 将token和用户信息放入session中
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	OAuth2Token oAuth2Token = (OAuth2Token) token;
    	SSOUser user = oAuth2Token.getUser();
    	Token tokenEntity = oAuth2Token.getToken();
    	
    	httpRequest.getSession().setAttribute(USER_INFO_ATTRIBUTE, user);
    	httpRequest.getSession().setAttribute(TOKEN_INFO_ATTRIBUTE, tokenEntity);
    	
    	// 转发请求
    	issueSuccessRedirect(request, response);
    	
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
