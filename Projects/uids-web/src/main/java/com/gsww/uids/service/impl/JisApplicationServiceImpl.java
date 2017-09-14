package com.gsww.uids.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
>>>>>>> c7e81aa39defc07a6e9db6fade5afb87be69e0dd
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysOperatorDao;
import com.gsww.uids.dao.JisApplicationDao;
import com.gsww.uids.service.JisApplicationService;
@Transactional
@Service("jisApplicationService")

public class JisApplicationServiceImpl implements JisApplicationService {

	@Autowired
<<<<<<< HEAD
	private JdbcTemplate jdbcTemplate ;
	
=======
	private JisApplicationDao jisApplicationDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate ;

	@Override
	public JisApplication save(JisApplication entity) throws Exception {
		return jisApplicationDao.save(entity);
	}

	@Override
	public String delete(JisApplication entity) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(entity);  
		String logMsg=jsonObject.toString();
		jisApplicationDao.delete(entity);
		return logMsg;
	}

>>>>>>> c7e81aa39defc07a6e9db6fade5afb87be69e0dd
	@Override
	public List<Map<String, Object>> getJisApplicationList() throws Exception {
		String sql ="select t.iid,t.name from jis_application t";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
	
	@Override
	public List<Map<String, Object>> getJisApplicationList() throws Exception {
		String sql ="select t.iid,t.name from jis_application t";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
}
