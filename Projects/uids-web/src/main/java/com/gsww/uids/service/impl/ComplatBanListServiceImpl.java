package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatBanListDao;
import com.gsww.uids.entity.ComplatBanlist;
import com.gsww.uids.service.ComplatBanListService;

@Transactional
@Service("ComplatBanListService")
public class ComplatBanListServiceImpl  implements ComplatBanListService{
	@Autowired
	private ComplatBanListDao complatBanListDao;
	
	@Override
	public Page<ComplatBanlist> getComplatBanPage(
			Specification<ComplatBanlist> spec, PageRequest pageRequest) {
		
		return complatBanListDao.findAll(spec, pageRequest);
	}

	@Override
	public List<ComplatBanlist> findComplatbanList() throws Exception {
		
		return complatBanListDao.findAll();
	}

	@Override
	public void delete(ComplatBanlist entity) throws Exception {
		complatBanListDao.delete(entity);
	}

	@Override
	public ComplatBanlist findByIid(Integer iid) throws Exception {
		return complatBanListDao.findByIid(iid);
	}
	

}
