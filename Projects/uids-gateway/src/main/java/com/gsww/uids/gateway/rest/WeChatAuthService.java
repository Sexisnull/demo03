package com.gsww.uids.gateway.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;
import com.gsww.uids.gateway.util.WeChatUtil;

import net.sf.json.JSONObject;

@Path("/uids-web")
public class WeChatAuthService {
	private OutsideUserDao outsideUserDAO = SpringContextHolder.getBean("outsideUserDao");
	protected Logger logger = Logger.getLogger(WeChatAuthService.class);

	/**
	 * 微信登录
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	@GET
	@Path("/weChat/login")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String auth(@Context HttpServletRequest request, @QueryParam("code") String code) {
		Map<String, String> map = new HashMap<String, String>();
		if (code != null) {
			OutsideUser outsideUser = new OutsideUser();
			WeChatUtil weChatUtil = new WeChatUtil();
			Map<String, String> result = weChatUtil.getUserInfoAccessToken(code);// 通过这个code获取access_token
			String openId = result.get("openid");
			if (StringHelper.isNotBlack(openId)) {
				String accessToken = result.get("access_token");// 使用access_token获取用户信息
				outsideUser = outsideUserDAO.findByUserId(openId);
				if (outsideUser != null) {
					// 登陆成功 返回个人微信信息--用户已绑定微信
					logger.info("登录成功");
					JSONObject outSideUserJson = JSONObject.fromObject(outsideUser);
					map = weChatUtil.getUserInfo(accessToken, openId);
					return outSideUserJson.toString();
				} else {
					map.put("errormsg", "该用户不存在或未绑定微信账号，登录失败");
					return new JSONUtil().writeMapSJSON(map);
				}
			} else {
				map.put("errormsg", "未获得合法的openId");
				return new JSONUtil().writeMapSJSON(map);
			}
		}
		map.put("errormsg", "未获得合法的code");
		return new JSONUtil().writeMapSJSON(map);
	}

	/**
	 * 绑定接口
	 * 
	 * @param map
	 * @return
	 */
	@GET
	@Path("/weChat/merge")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean mergeUserId(@QueryParam("openid") String openid, @QueryParam("userId") String userId) {
		logger.info("<WeChat接口>接收到请求内容:" + openid);
		if (!openid.isEmpty() && !userId.isEmpty()) {
			outsideUserDAO.saveWeChatOpenId(openid, userId);
			if (outsideUserDAO.saveWeChatOpenId(openid, userId) > 0) {
				logger.info(userId + "成功绑定微信账号，openid为：" + openid);
			}
			return true;
		} else {
			return false;
		}
	}

}
