package com.gsww.uids.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisApplicationDao;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.service.JisApplicationService;
/**
 * 应用模块业务实现类
 * @author Seven
 *
 */
@Transactional
@Service("JisApplicationService")
public class JisApplicationServiceImpl implements JisApplicationService {

	@Autowired
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

	@Override
	public JisApplication findByKey(Integer iid) throws Exception {
		JisApplication jisApplication=jisApplicationDao.findByIid(iid);
		return jisApplication;
	}

	@Override
	public Page<JisApplication> getApplicationPage(Specification<JisApplication> spec,
			PageRequest pageRequest) {
		return jisApplicationDao.findAll(spec, pageRequest);
	}

	@Override
	public JisApplication findByMark(String mark) throws Exception {
		JisApplication jisApplication=jisApplicationDao.findByMark(mark).get(0);
		return jisApplication;
	}

	public JisApplication queryMarkIsUsed(String mark) throws Exception {
		List<JisApplication> list = jisApplicationDao.findByMark(mark);
		if (CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getJisApplicationList() throws Exception {
		String sql ="select t.iid,t.name from jis_application t";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
}
