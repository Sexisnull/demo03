package com.gsww.uids.gateway.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
/**
 * 获取机构信息接口
 * @author gaopf
 *
 */
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
@WebService(name = "WSGroup", targetNamespace = "http://www.gszwfw.gov.cn/gsjis/services/WSGroup")
public interface WSGroup {
	@WebMethod
	public String findgroup(@WebParam(name = "userName")String userName,@WebParam(name = "passWord")String passWord,
			@WebParam(name = "groupCode")String groupCode,@WebParam(name = "includeChild")String includeChild,@WebParam(name = "appmark")String appmark);
}
