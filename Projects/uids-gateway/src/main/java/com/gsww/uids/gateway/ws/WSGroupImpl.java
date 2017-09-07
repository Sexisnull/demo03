package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
/**
 * 获取机构信息接口实现
 * @author gaopf
 *
 */
@WebService(serviceName = "WSGroup", endpointInterface = "com.gsww.uids.gateway.ws.WSGroup", portName = "WSGroupPort", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WSGroup")
public class WSGroupImpl implements WSGroup{
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	@WebMethod
	public String findgroup(@WebParam(name = "userName") String userName,
			@WebParam(name = "passWord") String passWord,
			@WebParam(name = "groupCode") String groupCode,
			@WebParam(name = "includeChild") String includeChild,
			@WebParam(name = "appmark") String appmark) {
		// TODO Auto-generated method stub
		
		logger.info("userName="+userName+"passWord="+passWord);
		System.out.println("userName="+userName+"passWord="+passWord+"groupCode="+groupCode+"includeChild="+includeChild+"appmark="+appmark);
		return null;
	}

}
