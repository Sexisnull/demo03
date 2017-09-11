package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatGroupDao;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.service.ComplatGroupService;

@Transactional
@Service("complatService")
public class ComplatGroupServiceImpl implements ComplatGroupService{
	@Autowired
	private ComplatGroupDao complatGroupDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<ComplatGroup> findAll() {
		// TODO Auto-generated method stub
		String sql = "select * from complat_group";
		return jdbcTemplate.queryForList(sql, ComplatGroup.class);
		
	}
	
	
	
}
