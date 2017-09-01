package com.gsww.controller;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.gsww.uids.manager.org.webservice.OrganizationWebService;

/**
 * 客户端同步机构数据
 * 
 * @author taolm
 *
 */
public class OrganizationWebServiceClient {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(OrganizationWebService.class); 
		factory.setAddress("http://localhost:8080/uids/webservice/org");
		 
		OrganizationWebService ws = (OrganizationWebService) factory.create();  
		String orgs = ws.queryOrganizaitonPage(1, 10);
		String orgByCode = ws.queryOrganizaitonByCode("003007004");
		String orgByName = ws.queryOrganizaitonByShortName("西宁市");
		
		System.out.println(orgs);
		System.out.println(orgByCode);
		System.out.println(orgByName);
	}
}
