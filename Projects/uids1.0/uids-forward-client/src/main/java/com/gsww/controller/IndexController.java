package com.gsww.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.actor.CustomActor;
import com.gsww.uids.client.actor.Exception.ActorException;
import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.client.user.SSOUser;

import net.sf.json.JSONObject;

/**
 * 主页控制
 * 
 * @author simplife
 *
 */
@Controller
@RequestMapping("/mgr")
public class IndexController {
	
	private static final String reverseClientEntry = "http://10.18.27.74:8082/uids-client-agent/shareEntry.jsp";
	private static final String shareAccessTokenUrl = "http://10.18.27.74:8081/uids-forword-client/mgr/shareAccessToken.do";
	
	/**
	 * 如何共享token，这里简单地将token放在这里
	 */
	private Token oAuth2Token;
	
	/**
	 * 进入应用登陆页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "mgr/login";
	}
	
	/**
	 * 欢迎页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String welcome(Model model, HttpServletRequest request) throws ActorException {
		CustomActor actor = new CustomActor();
		if ( actor.checkLocalLogin(request.getSession()) ) {
			SSOUser user = actor.extractLoginUser(request);
			model.addAttribute("userName", user.getTrueName());
			
			// 将这时的token放进model
			Token token = actor.extractToken(request);
			model.addAttribute("access_token", token.getAccessToken());
			model.addAttribute("refresh_token", token.getRefreshToken());
			
			// 缓存token
			oAuth2Token = token;
			
			// 提供反向代理应用入口
			model.addAttribute("reverse_client_entry", reverseClientEntry);	
			model.addAttribute("share_token_provider", shareAccessTokenUrl);
		}
		
		return "mgr/welcome";
	}
	
	/**
	 * 刷新token
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws ActorException
	 */
	@RequestMapping(value="/refreshToken", method = RequestMethod.GET)
	public String refreshToken(Model model, HttpServletRequest request) throws ActorException {
		 
		// 刷新前的token
		String oldAccessToken = request.getParameter("accessToken");
		String oldRefreshToken = request.getParameter("refreshToken");
		
		// 刷新后的token
		CustomActor actor = new CustomActor();
		Token token = actor.extractToken(request);
		String newAccessToken = token.getAccessToken();
		String newRefreshToken = token.getRefreshToken();
		
		model.addAttribute("old_access_token", oldAccessToken);
		model.addAttribute("old_refresh_token", oldRefreshToken);
		model.addAttribute("new_access_token", newAccessToken);
		model.addAttribute("new_refresh_token", newRefreshToken);
		
		// 缓存token
		oAuth2Token = token;
		
		return "mgr/refreshToken";
	}
	
	/**
	 * 退出
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		return "mgr/login";
	}
	
	/**
	 * 给其它应用提供一个获取共享token的接口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/shareAccessToken", method = RequestMethod.POST)
	@ResponseBody
	public String shareAccessToken(Model model) {
		// TODO 可以对请求者做一个限制
		if ( oAuth2Token == null ) {
			return "";
		} else {
			JSONObject tokenJson = JSONObject.fromObject(oAuth2Token);
			return tokenJson.toString();
		}
	}
	
	/**
	 * 单点登录
	 * 
	 * @param model
	 * @param code
	 * @param loginuser
	 * @param loginpass
	 * @param appname
	 * @param groupcode
	 * @param loginallname
	 * @return
	 */
	@RequestMapping(value="/singleLogin", method = RequestMethod.GET)
	public String singleLogin(Model model, String code, String loginuser, String loginpass, String appname,
			String groupcode, String loginallname) throws Exception {
		if ( StringUtils.isEmpty(code) ) {
			throw new Exception("授权码不能为空！");
		}
		
		// 转到单点登录servlet处理
		SSOConfig config = SSOConfig.getInstance();
		String path = config.getServerLoginUri() + "?" + OAuth.OAUTH_CODE + "=" + code;
		return "redirect:" + path;
	}
}
