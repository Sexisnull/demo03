package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisFieldsDao;
import com.gsww.uids.entity.JisFields;
import com.gsww.uids.service.JisFieldsService;

/**
 * Title: JisFieldsServiceImpl.java Description: 用户扩展属性ServiceImpl
 * 
 * @author yangxia
 * @created 2017年9月12日 下午5:25:40
 */
@Transactional
@Service("jisFieldsService")
public class JisFieldsServiceImpl implements JisFieldsService {
	@Autowired
	private JisFieldsDao jisFieldsDao;

	@Override
	public Page<JisFields> getJisFieldsPage(Specification<JisFields> spec, PageRequest pageRequest) {
		return jisFieldsDao.findAll(spec, pageRequest);
	}

	@Override
	public JisFields findByKey(Integer iid) {
		return jisFieldsDao.findByIid(iid);
	}

	@Override
	public void save(JisFields jisFields) {
		jisFieldsDao.save(jisFields);
	}

	@Override
	public void delete(JisFields jisFields) {
		jisFieldsDao.delete(jisFields);
	}

	@Override
	public List<JisFields> findAllJisFields() {
		List<JisFields> jisFieldsList = new ArrayList<JisFields>();
		Iterable<JisFields> jisFieldsIterables = jisFieldsDao.findAll();
		Iterator<JisFields> jisFieldsIterable = jisFieldsIterables.iterator();
		while (jisFieldsIterable.hasNext()) {
			JisFields jisFields = (JisFields) jisFieldsIterable.next();
			if (jisFields.getIssys() == 1) {
				jisFieldsList.add(jisFields);
			}
		}
		return jisFieldsList;
	}

}
