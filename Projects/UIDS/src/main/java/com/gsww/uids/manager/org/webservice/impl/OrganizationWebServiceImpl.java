package com.gsww.uids.manager.org.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.org.webservice.OrganizationWebService;

import net.sf.json.JSONObject;

/**
 * 为机构同步提供的webservice服务实现类
 * 
 * @author taolm
 *
 */
public class OrganizationWebServiceImpl implements OrganizationWebService {

	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public String queryOrganizaitonByShortName(String shortName) {
		Organization org = organizationService.findByShortName(shortName);
		if ( org == null ) {
			return "";
		}
		
		JSONObject json = JSONObject.fromObject(org);
		return json.toString();
	}

	@Override
	public String queryOrganizaitonByCode(String code) {
		
		Organization org = organizationService.findByCode(code);
		if ( org == null ) {
			return "";
		}
		
		JSONObject json = JSONObject.fromObject(org);
		return json.toString();
	}

	@Override
	public String queryOrganizaitonPage(int page, int pagesize) {
		PageObject<Organization> pageObj = organizationService.findPage(page, pagesize, null, null, null, null, null, null);
		return pageObj.toJSONString();
	}
}
