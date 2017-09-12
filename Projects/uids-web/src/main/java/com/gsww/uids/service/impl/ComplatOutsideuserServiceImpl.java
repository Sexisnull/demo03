package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatOutsideuserDao;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;

/**
 * 
 * Title: OutsideUserServiceImpl.java Description: 个人用户Service实现层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:40:50
 */
@Transactional
@Service("outsideUserService")
public class ComplatOutsideuserServiceImpl implements ComplatOutsideuserService {
	@Autowired
	private ComplatOutsideuserDao outsideUserDao;

	@Override
	public Page<ComplatOutsideuser> getOutsideUserPage(Specification<ComplatOutsideuser> spec, PageRequest pageRequest) {
		return outsideUserDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatOutsideuser findByKey(Integer iid) {
		return outsideUserDao.findByIid(iid);
	}

	@Override
	public void save(ComplatOutsideuser outsideUser) {
		outsideUserDao.save(outsideUser);
	}

	@Override
	public void delete(ComplatOutsideuser outsideUser) {
		outsideUserDao.delete(outsideUser);
	}
}
