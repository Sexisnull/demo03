package com.gsww.uids.gateway.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.uids.gateway.dao.sso.SsoDao;
import com.gsww.uids.gateway.util.SpringContextHolder;

@Path("/jis/sso")
public class AuthenticateService {
	
	private SsoDao dao = SpringContextHolder.getBean("ssoDao");
	
	@GET
	@Path("/usp.do")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String usp(@QueryParam("action")String action,
			@QueryParam("appmark")String appmark,
			@QueryParam("redirectUrl")String redirectUrl,
			@QueryParam("ticket")String ticket){
		
		if("ticketLogin".equals(action)){//统一登录
			String url = dao.findUrlByAppmark(appmark);
			return url;
		}else if("register".equals(action)){//统一注册
			
		}else if("logout".equals(action)){//统一注销
			
		}else if("editPassword".equals(action)){//修改密码
			
		}else if("editUser".equals(action)){//修改个人信息
			
		}else{
			
		}
		
		
		return "";
	}
	
}
