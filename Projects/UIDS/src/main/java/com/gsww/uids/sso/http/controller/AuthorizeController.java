package com.gsww.uids.sso.http.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.gsww.common.util.HttpUtil;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.UrlBuilder;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.sso.entity.AuthcodeRequest;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;
import com.gsww.uids.manager.sso.service.AuthcodeRequestService;
import com.gsww.uids.manager.sso.service.AuthcodeResponseService;
import com.gsww.uids.manager.sso.service.OauthService;
import com.gsww.uids.sso.OAuthConstants;

import net.sf.json.JSONObject;

/**
 * oauth认证第一步：获取授权码 authcode
 * 
 * @author taolm
 *
 */
@Controller
@RequestMapping("/oauth")
public class AuthorizeController {
	
	// 错误参数名
	private static final String ERROR_PARAMETER = "error";
	private static final String ERROR_DESCRIPTION_PARAMETER = "error_description";

	@Autowired
	private AuthcodeRequestService authcodeRequestService;
	
	@Autowired
	private AuthcodeResponseService authcodeResponseService;
	
    @Autowired
    private OauthService authService;
    
    @Autowired
    private ApplicationService organizationService;
    
    @Autowired
    private AccountService accountService;

    /**
     * 请求授权码code
     * 
     * @param model
     * @param request
     * @return
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String authorize(Model model, HttpServletRequest request) throws URISyntaxException, OAuthSystemException, IOException {
    	
    	// 请求时间
    	String requestTime = TimeHelper.getCurrentTime();
    	   	
    	try {
    		// 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            
            // 检查redirectUri不能为空
            String redirectURI = oauthRequest.getRedirectURI();
            if ( StringUtil.isBlank(redirectURI) ) {
            	UrlBuilder urlBuilder = new UrlBuilder(request.getRequestURL().toString())
            			.appendParameter(ERROR_PARAMETER, HttpStatus.NOT_FOUND.toString(), "?")
            			.appendParameter(ERROR_DESCRIPTION_PARAMETER, "OAuth请求必须携带redirect_uri", "&");
            	return "redirect:" + urlBuilder.toString();
            }       	
            
            // responseType仅支持CODE
            StringBuilder err = new StringBuilder(100);
            String responseType = oauthRequest.getResponseType();
            if ( !authService.checkResponseType(responseType, err) ) {
            	UrlBuilder urlBuilder = new UrlBuilder(redirectURI)
            			.appendParameter(ERROR_PARAMETER, HttpStatus.BAD_REQUEST.toString(), "?")
            			.appendParameter(ERROR_DESCRIPTION_PARAMETER, err.toString(), "&");
            	return "redirect:" + urlBuilder.toString();
            }
            
            // 检查传入的客户端id是否正确
            String clientId = oauthRequest.getClientId(); 
            String uidsClientId = request.getParameter(OAuthConstants.UIDS_CLIENT_ID);
            if ( !authService.checkClientId(clientId, err) || !authService.checkClientId(uidsClientId, err) ) {
            	UrlBuilder urlBuilder = new UrlBuilder(redirectURI)
            			.appendParameter(ERROR_PARAMETER, HttpStatus.BAD_REQUEST.toString(), "?")
            			.appendParameter(ERROR_DESCRIPTION_PARAMETER, err.toString(), "&");
            	return "redirect:" + urlBuilder.toString();
            }
            
            // 获取单点登录的账号所属应用的clientId
            String ssoAccountType = request.getParameter(OAuthConstants.SSO_ACCOUNT_TYPE);
            String clientIdOfAccount = getClientIdOfAccount(clientId, uidsClientId, ssoAccountType);
            		
            // 请求登录页面
            model.addAttribute("clientId", clientId);
    		model.addAttribute("clientIdOfAccount", clientIdOfAccount);
    		model.addAttribute("responseType", responseType);
    		model.addAttribute("redirectUri", redirectURI);
    		model.addAttribute("scope", authService.concatScopes(oauthRequest.getScopes(), ","));
    		model.addAttribute("state", oauthRequest.getState());
    		model.addAttribute("requestTime", requestTime);
    		return "manager/sys/personal/ssoLogin";
    		
        } catch (OAuthProblemException e) {

        	String url = request.getParameter(OAuth.OAUTH_REDIRECT_URI);
        	if ( StringUtil.isBlank(url) ) {
        		url = request.getRequestURL().toString();
        	}
        	
        	UrlBuilder urlBuilder = new UrlBuilder(url)
        			.appendParameter(ERROR_PARAMETER, e.getError(), "?")
        			.appendParameter(ERROR_DESCRIPTION_PARAMETER, e.getDescription(), "&");        	
            return "redirect:" + urlBuilder.toString();
        }
    }
    
    /**
     * 统一身份认证系统上的单点登陆后，获取code
     * 
     * @param model
     * @param request
     * @return
     * @throws URISyntaxException
     * @throws OAuthSystemException
     * @throws IOException
     */
    @RequestMapping(value = "/authorizeLocal", method = RequestMethod.POST)
	@ResponseBody
	public String authorizeLocal(Model model, HttpServletRequest request, String accountName, String accountPass, String checkCode, 
			String clientId, String accountClientId, String responseType, String redirectUri, String scope, String state, String requestTime) 
			throws OAuthSystemException {
    	
    	// 验证账号信息的结果数据
    	JSONObject resultJson = new JSONObject();
    			
    	// 验证用户名密码，如果失败，继续跳回到登录页面
    	StringBuilder err = new StringBuilder(256);
    	Subject subject = SecurityUtils.getSubject();
        if ( !login(subject, request, accountName, accountPass, checkCode, accountClientId, err) ) {        	
        	resultJson.put("state", MessageInfo.STATE_FAIL);
    		resultJson.put("info",  err.toString());
    		return resultJson.toString();
        }
        
        // 身份验证通过，生成授权码，并保存记录
        // 生成authcode
        String authcode = authService.generateAuthcode();
        
        // 保存请求
        AuthcodeRequest authcodeRequest = new AuthcodeRequest();
        authcodeRequest.setAccountName(accountName);
        authcodeRequest.setClientId(clientId);
        authcodeRequest.setClientIdOfAccount(accountClientId);
        authcodeRequest.setRedirectUri(redirectUri);
        authcodeRequest.setRequestIp(HttpUtil.getIpAddress(request));
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
        
        // redirect回去
        UrlBuilder urlBuilder = new UrlBuilder(redirectUri).appendParameter(OAuth.OAUTH_CODE, authcode, "?");
        resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info",  urlBuilder.toString());
		return resultJson.toString();
    }

    /**
     * 验证单点登录页面提交的用户名密码等信息
     * 
     * @param subject
     * @param request
     * @param clientId
     * @param errInfo
     * @return
     */
    private boolean login(Subject subject, HttpServletRequest request, String accountName, String accountPass, String checkCode, 
    		String clientId, StringBuilder errInfo) {
    	
    	// 验证用户输入的验证码是否一致：大小写不敏感
 		String standardCheckCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
 		if ( !standardCheckCode.equalsIgnoreCase(checkCode) ) {
 			errInfo.append("验证码输入错误，请重新输入！");
 			return false;
 		}
 		
 		// 登录
 		Application app = organizationService.findByClientId(clientId);
 		String userName = app.getUuid() + accountName;
 		UsernamePasswordToken token = new UsernamePasswordToken(userName, accountPass);
 		// 设置rememberMe
 	    token.setRememberMe(true);
 	    try {
            subject.login(token);
        } catch (Exception e) {
        	errInfo.append("账号名不存在或密码错误，或者账号已经封停！");
            return false;
        }
 	    
 	    List<Account> accounts = accountService.findByAppAndAccountName(app.getUuid(), accountName);
 	    Account account = accounts.get(0);
 	    
 	    // 将账号信息写入session
        subject.getSession().setAttribute(WebConstants.ONLINE_ACCOUNT_ID, account.getUuid());
        subject.getSession().setAttribute(WebConstants.LOGIN_TIME, TimeHelper.getCurrentTime());
        
        return true;
    } 
    
    /**
     * 根据类型获取反向代理单点登录账号所属应用的clientId
     * 
     * @param clientId
     * @param uidsClientId
     * @param ssoAccountType
     * @return
     */
    private String getClientIdOfAccount(String clientId, String uidsClientId, String ssoAccountType) 
    		throws OAuthSystemException {
    	
    	// 统一身份认证下的账号
		final String UIDS_ACCOUNT_TYPE = "1";
		// 接入应用下的账号
		final String APP_ACCOUNT_TYPE = "2";
		
		// 获取单点登录账号所属应用的clientId
		if ( UIDS_ACCOUNT_TYPE.equalsIgnoreCase(ssoAccountType) ) {
			return uidsClientId;
		} else if ( APP_ACCOUNT_TYPE.equalsIgnoreCase(ssoAccountType) ) {
			return clientId;
		} else {
			throw new OAuthSystemException(String.format("错误的登录账号类型【%s】，请检查配置是否正确！", ssoAccountType));
		}
    }
}