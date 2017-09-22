/*package com.gsww.uids.gateway.wsClient;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

*//**
 * webService客户端接口
 * @author gaopf
 *
 *//*
public class WsClientInterface {
	protected Logger logger = Logger.getLogger(getClass());
	private String syncToApplication(String responseJson,String appUrl){
		try{
			Service service = new Service();
		      Call call = (Call)service.createCall();
		      call.setTargetEndpointAddress(appUrl);
		      call.setOperationName(new QName("http://www.BNET.cn/v3.0/", "dealResult"));
		      call.addParameter("xml", XMLType.XSD_STRING, ParameterMode.IN);
		      call.setSOAPActionURI("http://www.BNET.cn/v3.0/dealResult");
		      call.setUseSOAPAction(true);
		      call.setTimeout(Integer.valueOf(30000));
		      call.setReturnType(XMLType.XSD_STRING);
		      Object[] obj = { responseJson };
		      String responseXml = (String)call.invoke(obj);
		      this.logger.info("统一身份认证系统接收到处理结果反馈请求，返回给统一身份认证系统信息包：\n" + responseXml);
		      return responseXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";	
	}
}
*/