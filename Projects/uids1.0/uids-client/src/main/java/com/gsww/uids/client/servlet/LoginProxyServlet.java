package com.gsww.uids.client.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import com.gsww.uids.client.actor.StdHttpSessionBasedActor;
import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.config.SSOConstants;
import com.gsww.uids.client.config.SingleLoginResponseCode;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.client.user.SSOUser;
import com.gsww.uids.sso.rpc.service.AuthenticateService;
import com.gsww.uids.util.AESOperator;

/**
 * 处理单点登录请求的servlet
 * 
 * @author taolm
 *
 */
@SuppressWarnings("serial")
public class LoginProxyServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(LoginProxyServlet.class);	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doLogin(req, resp);	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doLogin(req, resp);
	}
	
	/**
	 * 登录：提交uids认证，如果认证通过，获取用户信息并转到登录成功后的页面，否则，跳转到登录失败后的页面
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// sso配置信息
		SSOConfig config = SSOConfig.getInstance();
		if ( !SSOConstants.AES_AND_BASE64_ENCRYPT_TYPE.equalsIgnoreCase(config.getDeliverDataEncrtyType()) ) {
			throw new IOException(String.format("不支持的加密方式【%s】，目前只支持【AES+BASE64】加密方式", config.getDeliverDataEncrtyType()));
		}
		
		try {
			// 应用actor
			StdHttpSessionBasedActor actor = config.getActor();
			
			// 获取用户名和密码，并且加密
			String userName = AESOperator.encrypt(config.getDeliverDataEncryptSecret(), config.getDeliverDataEncryptSalt(), actor.extractUserName(req));
			String password = AESOperator.encrypt(config.getDeliverDataEncryptSecret(), config.getDeliverDataEncryptSalt(), actor.extractUserPwd(req));
					
			// 获取账号所属应用的clientId
			String clientIdOfAccount = actor.extractClientIdOfUser();
			
			logger.info("正在执行单点登录......");
			
			// 远程服务接口
			AuthenticateService remoteAuthenticateService = config.getRemoteAuthenticateService();
			
			// 验证用户名和密码，获取code
			String codeJsonStr = remoteAuthenticateService.authenticate(ResponseType.CODE.toString(), 
					config.getClientId(), clientIdOfAccount, config.getLoginSuccessUrl(), userName, password, null, null);
			String code = SSOParser.parseCode(codeJsonStr);		
			
			// 获取token
			String tokenJsonStr = remoteAuthenticateService.getAccessToken(GrantType.AUTHORIZATION_CODE.toString(),
					code, config.getClientId(), config.getClientSecret(), config.getLoginSuccessUrl());
			Token token = SSOParser.parseToken(tokenJsonStr);
			
			// 获取用户信息
			String userinfoJsonStr = remoteAuthenticateService.getUserInfo(token.getAccessToken(), 
					config.getClientId(), config.getClientSecret(), config.getLoginSuccessUrl());
			// 解密用户信息
			String decryptUserInfo = AESOperator.decrypt(config.getDeliverDataEncryptSecret(), config.getDeliverDataEncryptSalt(), userinfoJsonStr);
			SSOUser user = SSOParser.parseUser(decryptUserInfo);
			
			// 将token加载到session中
			actor.loadToken(req, token);
						
			// 加载用户信息到session中
			actor.loadLoginUser(req, user);		
			
			// 跳转到成功页面
			String path = config.getLoginSuccessUrl() + "?" + SSOConstants.RESPONSE_CODE + "=" + SingleLoginResponseCode.SSO_OK 
					+ "&" + SSOConstants.RESPONSE_DESCRIPTION + "=" + URLEncoder.encode("单点登录成功", "UTF-8");				
			if ( SSOConstants.REDIRECT_TYPE.equalsIgnoreCase(config.getLoginSuccessGotoType()) ) {
				logger.info("单点登录成功");
				resp.sendRedirect(path);
			} else if ( SSOConstants.FORWARD_TYPE.equalsIgnoreCase(config.getLoginSuccessGotoType()) ) {
				logger.info("单点登录成功");
				RequestDispatcher dispatcher = req.getRequestDispatcher(path);
				dispatcher.forward(req, resp);
			} else { 
				throw new IOException(String.format("错误的跳转方式【%s】", config.getLoginSuccessGotoType()));
			}
			
		} catch (Exception e) {
			
			logger.info("单点登录失败", e);
			
			Throwable rootEx = e;
			while ( rootEx.getCause() != null ) {
				rootEx = rootEx.getCause();
			}
			String description = URLEncoder.encode(rootEx.getMessage(), "UTF-8");
			
			// 跳转到失败页面
			String path = config.getLoginFailUrl() + "?" + SSOConstants.RESPONSE_CODE + "=" + SingleLoginResponseCode.SSO_ERROR 
					+ "&" + SSOConstants.RESPONSE_DESCRIPTION + "=" + description;	
			if ( SSOConstants.REDIRECT_TYPE.equalsIgnoreCase(config.getLoginFailGotoType()) ) {
				resp.sendRedirect(path);
			} else if ( SSOConstants.FORWARD_TYPE.equalsIgnoreCase(config.getLoginFailGotoType()) ) {
				RequestDispatcher dispatcher = req.getRequestDispatcher(path);
				dispatcher.forward(req, resp);
			} else {
				throw new IOException(String.format("错误的跳转方式【%s】", config.getLoginFailGotoType()));
			}
		}
	}
}