package com.gsww.uids.client.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.gsww.uids.client.actor.StdHttpSessionBasedActor;
import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.sso.rpc.service.AuthenticateService;

/**
 * 刷新token（正向代理）
 * 
 * @author taolm
 *
 */
public class RefreshTokenProxy {
	
	private static final Logger logger = Logger.getLogger(RefreshTokenProxy.class);	
	
	/**
	 * 刷新token
	 * 
	 * @param req
	 * @throws ServletException
	 */
	public void doRefreshToken(HttpServletRequest request) throws ServletException {
		
		// sso配置信息
		SSOConfig config = SSOConfig.getInstance();
		
		try {			
			// 应用actor
			StdHttpSessionBasedActor actor = config.getActor();
			
			// 获取原来的token信息
			Token oldToken = actor.extractToken(request);
			if ( oldToken == null ) {
				logger.info("token信息不存在，无法刷新！");
				return ;
			}
			
			// 远程服务接口
			AuthenticateService remoteAuthenticateService = config.getRemoteAuthenticateService();
			
			logger.info("正在刷新token......");
			
			// 获取刷新后token的json字符串
			String tokenJsonStr = remoteAuthenticateService.refreshAccessToken(GrantType.REFRESH_TOKEN.toString(), 
					oldToken.getRefreshToken(), config.getClientId(), config.getClientSecret());
			Token newToken = SSOParser.parseToken(tokenJsonStr);
			
			// 将新的token放入session中
			actor.loadToken(request, newToken);
			
			logger.info("刷新token成功");
			
		} catch (Exception e) {		
			
			logger.info("刷新token失败", e);
			
			throw new ServletException(e);
		}
	}
}
