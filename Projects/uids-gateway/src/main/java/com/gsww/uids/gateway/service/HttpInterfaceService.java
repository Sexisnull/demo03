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

/**
 * 
 * @author zhl
 *
 */
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
		logger.info("<应用注册接口>接收到请求内容:" + reqJson);
		boolean returnMark = false; //注册成功标志
		try {
			ApplicationMule app = objectMapper.readValue(reqJson, ApplicationMule.class);
			if (!reqJson.isEmpty()) {
				//此处应该回调第三方接口,并将Map传递过去
				returnMark = true;
				logger.info("<应用注册接口>成功:" + returnMark);
			}
		} catch (Exception e) {
			logger.info("<应用注册接口>异常:" + e.getMessage());
			e.printStackTrace();
		} 

		return returnMark;
	}
	
}

