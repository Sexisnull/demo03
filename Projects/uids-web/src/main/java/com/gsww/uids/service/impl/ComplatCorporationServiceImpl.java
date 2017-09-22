package com.gsww.uids.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanweb.common.util.StringUtil;
import com.gsww.uids.dao.ComplatCorporationDao;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;



@Transactional
@Service("ComplatCorporationService")
public class ComplatCorporationServiceImpl implements ComplatCorporationService{
	
	@Autowired
	private ComplatCorporationDao complatCorporationDao;

	@Override
	public Page<ComplatCorporation> getCorporationPage(Specification<ComplatCorporation> spec, PageRequest pageRequest) {
		return complatCorporationDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatCorporation findByKey(Integer iid) throws Exception {
		
		return complatCorporationDao.findByIid(iid);
	}

	@Override
	public void save(ComplatCorporation corporation) {
		
		complatCorporationDao.save(corporation);
	}

	@Override
	public void updateCorporation(Integer iid) throws Exception {
		
		complatCorporationDao.updateCorporation(iid);
	}

	@Override
	public ComplatCorporation findByLoginName(String loginName) {
	    return this.complatCorporationDao.findByLoginName(loginName);
	}

	@Override
	public boolean updatePwd(String loginName, String pwd) {
	    if (StringUtil.isEmpty(loginName)) {
	        return false;
	      }
	      return this.complatCorporationDao.updatePwd(loginName, pwd);
	}
	
	  public ComplatCorporation findByLoginName1(String loginName)
	  {
	    return this.complatCorporationDao.findByLoginName(loginName);
	  }

	@Override
	public ComplatCorporation findByLoginNameIsUsed(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
