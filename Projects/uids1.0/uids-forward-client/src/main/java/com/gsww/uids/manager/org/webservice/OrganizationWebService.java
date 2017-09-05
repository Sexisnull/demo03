package com.gsww.uids.manager.org.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 为机构同步提供的webservice服务
 * 注意：包路径必须和服务端一致
 * 
 * @author taolm
 *
 */
@WebService
public interface OrganizationWebService {

	/**
	 * 根据机构简称查询机构
	 * 
	 * @param shortName 要查询的机构的简称
	 * @return
	 */
	@WebMethod(operationName="queryOrganizaitonByShortName")
	@WebResult String queryOrganizaitonByShortName(@WebParam(name = "shortName") String shortName);
	
	/**
	 * 根据机构编码查询机构
	 * 
	 * @param code 要查询的机构的机构编码
	 * @return
	 */
	@WebMethod(operationName="queryOrganizaitonByCode")
	@WebResult String queryOrganizaitonByCode(@WebParam(name = "code") String code);
	
	/**
	 * 分页查询机构
	 * 
	 * @param page 当前页码
	 * @param pagesize 页面大小
	 * @return
	 */
	@WebMethod(operationName="queryOrganizaitonPage")
	@WebResult String queryOrganizaitonPage(@WebParam(name = "page") int page, @WebParam(name = "pagesize") int pagesize);
}