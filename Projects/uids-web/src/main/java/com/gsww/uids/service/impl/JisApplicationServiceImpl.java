package com.gsww.uids.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.service.JisApplicationService;
@Transactional
@Service("jisApplicationService")

public class JisApplicationServiceImpl implements JisApplicationService {

	@Autowired
	private JdbcTemplate jdbcTemplate ;
	
	@Override
	public List<Map<String, Object>> getJisApplicationList() throws Exception {
		String sql ="select t.iid,t.name from jis_application t";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
}
