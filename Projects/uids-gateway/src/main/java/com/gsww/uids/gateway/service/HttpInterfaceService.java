package com.gsww.uids.gateway.service;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.entity.ApplicationMule;
import com.gsww.uids.gateway.util.SpringContextHolder;

@Path("/uids")
public class HttpInterfaceService {
	
	protected Logger logger = Logger.getLogger(getClass());	
	private ObjectMapper objectMapper = new ObjectMapper();
	private ApplicationDao applicationDao = SpringContextHolder.getBean("applicationDao");
	
	/**
	 * 应用注册接口
	 * 
	 * @param map
	 * @return
	 */
	@POST
	@Path("/app/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean appRegister(String reqJson) {
		boolean returnMark = false; //注册成功标志
		try {
			ApplicationMule app = objectMapper.readValue(reqJson, ApplicationMule.class);
			logger.info("<应用注册接口>接收到请求内容:" + reqJson);
			System.out.println("<应用注册接口>接收到请求内容:" + reqJson);
			if (!reqJson.isEmpty()) {
				applicationDao.appRegister(app);
				returnMark = true;
				logger.info("<应用注册接口>成功:" + returnMark);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return returnMark;
	}
	
	/**
	 * 机构新增接口
	 * @param map
	 * @return
	 */
	@POST
	@Path("/group/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean doGroupInsertExcute(Map map){
		logger.info("<机构新增接口>接收到请求内容:"+map);
		System.out.println("<机构新增接口>接收到请求内容:"+map);
		return false;
	}
	
	@POST
	@Path("/group/update")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public boolean doGroupUpdateExcute(Map map){
		logger.info("<修改机构接口>接收到请求内容:"+map);
		System.out.println("<修改机构接口>接收到请求内容:"+map);
		return false;
	}
	
	@POST
	@Path("/group/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public boolean doGroupDeleteExcute(Map map){
		logger.info("<删除机构接口>接收到请求内容:"+map);
		System.out.println("<删除机构接口>接收到请求内容:"+map);
		return false;
	}
	
	
}

