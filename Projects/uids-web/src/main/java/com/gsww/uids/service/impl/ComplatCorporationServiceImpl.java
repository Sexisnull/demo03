package com.gsww.uids.service.impl;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatCorporationDao;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;



@Transactional
@Service("complatCorporationService")
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
	public ComplatCorporation findByLoginNameIsUsed(String loginName) {
		List<ComplatCorporation> cList = complatCorporationDao.findByLoginName(loginName);
		if (CollectionUtils.isNotEmpty(cList)) {
			return cList.get(0);
		} else {
			return null;
		}
	}

	
	
	

	
}
