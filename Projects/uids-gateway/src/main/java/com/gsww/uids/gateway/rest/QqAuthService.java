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
import com.gsww.uids.gateway.util.QqUtil;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;

import net.sf.json.JSONArray;

@Path("/uids-web")
public class QqAuthService {
	private OutsideUserDao outsideUserDAO = SpringContextHolder.getBean("outsideUserDao");
	protected Logger logger = Logger.getLogger(WeChatAuthService.class);

	/**
	 * QQ登录
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	@GET
	@Path("/qq/login")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String auth(@QueryParam("code") String code) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (code != null) {
				OutsideUser outsideUser = new OutsideUser();
				QqUtil qqUtil = new QqUtil();
				Map<String, String> result = qqUtil.getUserInfoAccessToken(code);// 通过这个code获取access_token
				if (result.get("access_token") == null) {
					map.put("errormsg", "未获得合法的access_token" + result.get("error") + result.get("error_description"));
					return new JSONUtil().writeMapSJSON(map);
				} else {
					String openId = qqUtil.getUserOpenId(result.get("access_token")).get("openid");
					/*
					 * OpenID openIDObj = new
					 * OpenID(result.get("access_token")); String openId =
					 * openIDObj.getUserOpenID();
					 */
					if (StringHelper.isNotBlack(openId)) {
						outsideUser = outsideUserDAO.findByQQOpenId(openId);
						if (outsideUser != null) {
							logger.info("登录成功");
							return JSONArray.fromObject(outsideUser).toString();
						} else {
							map.put("errormsg", "该用户不存在或未绑定qq账号，登录失败");
							return new JSONUtil().writeMapSJSON(map);
						}
					} else {
						map.put("errormsg", "未获得合法的openId");
						return new JSONUtil().writeMapSJSON(map);
					}
				}
			}
			map.put("errormsg", "未获得合法的code");
			return new JSONUtil().writeMapSJSON(map);
		} catch (Exception e) {
			logger.error("<QQAuth接口>异常", e);
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
		logger.info("<QQ接口>接收到请求内容:" + openID);
		if (!openID.isEmpty() && !userId.isEmpty() && outsideUserDAO.findByQQOpenId(openID) == null) {
			outsideUserDAO.saveQQOpenId(openID, userId);
			if (outsideUserDAO.saveQQOpenId(openID, userId) > 0) {
				logger.info(userId + "成功绑定QQ账号，openid为：" + openID);
			}
			return true;
		} else {
			logger.info(userId + "绑定账号失败，openid为：" + openID + "该QQ账号已被使用");
			return false;
		}
	}
}
