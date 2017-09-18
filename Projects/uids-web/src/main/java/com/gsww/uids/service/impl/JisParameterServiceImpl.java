package com.gsww.uids.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisParameterDao;
import com.gsww.uids.entity.JisParameter;
import com.gsww.uids.service.JisParameterService;
/**
 * 系统参数业务实现类
 * @author Seven
 *
 */
@Transactional
@Service("JisParameterService")
public class JisParameterServiceImpl implements JisParameterService {

	@Autowired
	private JisParameterDao jisParameterDao ;

	@Override
	public JisParameter save(JisParameter entity) throws Exception {
		return jisParameterDao.save(entity);
	}

	@Override
	public Page<JisParameter> getParameterPage(Specification<JisParameter> spec,
			PageRequest pageRequest) {
		return jisParameterDao.findAll(spec, pageRequest);
	}

	@Override
	public JisParameter findByKey(Integer iid) throws Exception {
		JisParameter jisParameter=jisParameterDao.findByIid(iid);
		return jisParameter;
	}
}
