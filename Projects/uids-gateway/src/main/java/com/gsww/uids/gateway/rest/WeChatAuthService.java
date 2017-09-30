package com.gsww.uids.gateway.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.JSONUtil;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;
import com.gsww.uids.gateway.util.WeChatUtil;

import net.sf.json.JSONArray;

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
	public String auth(@QueryParam("code") String code) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (code != null) {
				OutsideUser outsideUser = new OutsideUser();
				WeChatUtil weChatUtil = new WeChatUtil();
				Map<String, String> result = weChatUtil.getUserInfoAccessToken(code);// 通过这个code获取access_token
				String openId = result.get("openid");
				if (StringHelper.isNotBlack(openId)) {
					outsideUser = outsideUserDAO.findByWeChatOpenId(openId);
					if (outsideUser != null) {
						logger.info("登录成功");
						return JSONArray.fromObject(outsideUser).toString();
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
		} catch (Exception e) {
			logger.error("<WeChatAuth接口>异常", e);
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
	@Path("/weChat/merge")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean mergeUserId(@QueryParam("openid") String openid, @QueryParam("userId") String userId) {
		logger.info("<WeChat接口>接收到请求内容:" + openid);
		if (!openid.isEmpty() && !userId.isEmpty() && outsideUserDAO.findByWeChatOpenId(openid) == null) {
			outsideUserDAO.saveWeChatOpenId(openid, userId);
			if (outsideUserDAO.saveWeChatOpenId(openid, userId) > 0) {
				logger.info(userId + "成功绑定微信账号，openid为：" + openid);
			}
			return true;
		} else {
			logger.info(userId + "绑定账号失败，openid为：" + openid+"该微信账号已被使用");
			return false;
		}
	}

}
