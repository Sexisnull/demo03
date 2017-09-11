package com.gsww.uids.gateway.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.gsww.uids.gateway.dao.sso.SsoDao;
import com.gsww.uids.gateway.util.SpringContextHolder;

@Path("/front")
public class AuthenticateService {
	
	private SsoDao dao = SpringContextHolder.getBean("ssoDao");
	
	/**
	 * 个人用户单点接口
	 * @param action
	 * @param appmark
	 * @param redirectUrl
	 * @param ticket
	 * @return
	 */
	@GET
	@Path("/per/interface.do")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String per(@QueryParam("action")String action,
			@QueryParam("appmark")String appmark,
			@QueryParam("gotoUrl")String gotoUrl,
			@QueryParam("ticket")String ticket){
		
		if(StringUtils.isBlank(action)){
			return "http://www.gszwfw.gov.cn/gsjis/front/perlogin.do";
		}else{
			if("ticketLogin".equals(action) && !StringUtils.isBlank(appmark)){//统一登录
				String url = dao.findUrlByAppmark(appmark);
				return url;
			}
			
			if("register".equals(action)){//统一注册
				return "http://www.gszwfw.gov.cn/gsjis/front/register/perregister.do"; //TODO
			}
			if("logout".equals(action)){//统一注销
				return "http://www.gszwfw.gov.cn/gsjis/front/perlogin.do";//TODO
			}
			if("editPassword".equals(action) && !StringUtils.isBlank(ticket)){//修改密码
				return "http://www.gszwfw.gov.cn/gsjis/front/modifyperinfo_show.do"; //TODO
			}else if("editPassword".equals(action) && StringUtils.isBlank(ticket)){
				return "http://www.gszwfw.gov.cn/gsjis/front/perlogin.do";
			}
			if("editUser".equals(action)){//修改个人信息
				return "http://www.gszwfw.gov.cn/gsjis/front/modifyperinfo_show.do"; //TODO
			}else if("editUser".equals(action) && StringUtils.isBlank(ticket)){
				return "http://www.gszwfw.gov.cn/gsjis/front/perlogin.do";
			}
		}
		return "http://www.gszwfw.gov.cn/gsjis/front/perlogin.do";
	}
	
	/**
	 * 法人用户单点接口
	 * @param action
	 * @param appmark
	 * @param redirectUrl
	 * @param ticket
	 * @return
	 */
	@GET
	@Path("/cor/interface.do")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String cor(@QueryParam("action")String action,
			@QueryParam("appmark")String appmark,
			@QueryParam("gotoUrl")String gotoUrl,
			@QueryParam("ticket")String ticket){
		
		if(StringUtils.isBlank(action)){
			return "http://www.gszwfw.gov.cn/gsjis/front/corlogin.do";
		}else{
			if("ticketLogin".equals(action) && !StringUtils.isBlank(appmark)){//统一登录
				String url = dao.findUrlByAppmark(appmark);
				return url;
			}
			
			if("register".equals(action)){//统一注册
				return "http://www.gszwfw.gov.cn/gsjis/front/register/corregister.do"; //TODO
			}
			
			if("logout".equals(action)){//统一注销
				return "http://www.gszwfw.gov.cn/gsjis/front/corlogin.do";//TODO
			}
			
			if("editPassword".equals(action) && !StringUtils.isBlank(ticket)){//修改密码
				return "http://www.gszwfw.gov.cn/gsjis/front/modifyperinfo_show.do"; //TODO
			}else if("editPassword".equals(action) && StringUtils.isBlank(ticket)){
				return "http://www.gszwfw.gov.cn/gsjis/front/corlogin.do";
			}
			
			if("editUser".equals(action)){//修改个人信息
				return "http://www.gszwfw.gov.cn/gsjis/front/modifyperinfo_show.do"; //TODO
			}else if("editUser".equals(action) && StringUtils.isBlank(ticket)){
				return "http://www.gszwfw.gov.cn/gsjis/front/corlogin.do";
			}
		}
		return "http://www.gszwfw.gov.cn/gsjis/front/corlogin.do";
	}
}
