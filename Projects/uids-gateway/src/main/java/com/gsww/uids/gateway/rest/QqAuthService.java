package com.gsww.uids.gateway.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

public class QqAuthService {
	private OutsideUserDao outsideUserDAO = SpringContextHolder.getBean("outsideUserDao");
	protected Logger logger = Logger.getLogger(WeChatAuthService.class);

	@GET
	@Path("/qq/login")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;
			if ("".equals(accessTokenObj.getAccessToken())) {
				System.out.print("没有获取到响应参数");
				// 进行跳转
				return new ModelAndView();
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				// 获取qq用户信息
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				String name = userInfoBean.getNickname();
				System.out.println("欢迎你，" + name + "!");
				OutsideUser outsideUser = new OutsideUser();
				outsideUser = outsideUserDAO.findByQQOpenId(openID);
				if (outsideUser != null) {
					logger.info("登录成功");
					request.setAttribute("outsideUserInfo", outsideUser);
					// 跳转
					return new ModelAndView();
				} else {
					System.out.println("该用户不存在，或未绑定qq账号");
					return new ModelAndView();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 绑定接口
	 * 
	 * @param map
	 * @return
	 */
	@GET
	@Path("/qq/merge")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean mergeUserId(@QueryParam("openID") String openID, @QueryParam("userId") String userId) {
		logger.info("<WeChat接口>接收到请求内容:" + openID);
		if (!openID.isEmpty() && !userId.isEmpty()) {
			outsideUserDAO.saveQQOpenId(openID, userId);
			if (outsideUserDAO.saveQQOpenId(openID, userId) > 0) {
				logger.info(userId + "成功绑定微信账号，openid为：" + openID);
			}
			return true;
		} else {
			return false;
		}
	}
}
