package com.gsww.uids.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gsww.uids.client.actor.StdHttpSessionBasedActor;
import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.sso.rpc.service.AuthenticateService;

/**
 * 实现退出时的服务器端退出（正向代理）
 * 
 * @author taolm
 *
 */
public class LogoutProxy {

	private static final Logger logger = Logger.getLogger(LogoutProxy.class);
	
	/**
	 * 通知服务端退出
	 * 
	 * @param req
	 * @throws ServletException
	 * @throws IOException
	 */
	public void notityServerLogout(HttpServletRequest request) throws ServletException, IOException {
		
		// sso配置信息
		SSOConfig config = SSOConfig.getInstance();
		
		try {			
			// 应用actor
			StdHttpSessionBasedActor actor = config.getActor();
			
			// 获取token信息
			Token token = actor.extractToken(request);
			if ( token == null ) {
				logger.info("token不存在，无法通知统一身份认证服务端退出！");
				return ;
			}
			
			// 通知服务端单点退出
			logger.info("正在单点退出......");
			
			// 远程服务接口
			AuthenticateService remoteAuthenticateService = config.getRemoteAuthenticateService();
			
			// 服务器退出
			remoteAuthenticateService.logout(token.getAccessToken(), config.getClientId(), config.getClientSecret());
			
			logger.info("单点退出成功");
			
		} catch (Exception e) {		
			
			logger.info("单点退出失败", e);
			
			throw new ServletException(e);
		}
	}
}
