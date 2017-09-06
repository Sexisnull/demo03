package com.gsww.uids.gateway.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.uids.gateway.dao.sso.SsoDao;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.hanweb.sso.ldap.util.MD5;
import com.sun.xml.bind.v2.TODO;

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
		
		if(StringUtils.isBlank(action)){
			return "login.jsp";
		}else{
			if("ticketLogin".equals(action) && !StringUtils.isBlank(appmark)){//统一登录
				String url = dao.findUrlByAppmark(appmark);
				return url;
			}else if(StringUtils.isBlank(appmark)){
				return "没有传入应用标识！";
			}
			
			if("register".equals(action)){//统一注册
				return "login.jsp"; //TODO
			}
			if("logout".equals(action)){//统一注销
				return "logout.jsp";//TODO
			}
			if("editPassword".equals(action) && !StringUtils.isBlank(ticket)){//修改密码
				return "login.jsp"; //TODO
			}else if("editPassword".equals(action) && StringUtils.isBlank(ticket)){
				return "票据不能为空！";
			}
			if("editUser".equals(action)){//修改个人信息
				return "login.jsp"; //TODO
			}else if("editUser".equals(action) && StringUtils.isBlank(ticket)){
				return "票据不能为空！";
			}
		}
		return "系统错误！";
	}
	
}
