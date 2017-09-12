package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatGroupDao;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatGroupService;

@Transactional
@Service("complatService")
public class ComplatGroupServiceImpl implements ComplatGroupService{
	@Autowired
	private ComplatGroupDao complatGroupDao;
	
	
	@Override
	public List<ComplatGroup> findAll(Specification<ComplatGroup> spec) {
		// TODO Auto-generated method stub
		
		return complatGroupDao.findAll(spec);
		
	}
	
	
	
}
