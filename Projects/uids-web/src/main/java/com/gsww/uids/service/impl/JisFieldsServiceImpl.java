package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisFieldsDao;
import com.gsww.uids.entity.JisFields;
import com.gsww.uids.entity.JisUserdetail;
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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@Override
	public List<Map<String, Object>> findExtendAttr(List<JisFields> FieldsList, Integer userId,Integer type)
			throws Exception {
		
		List<Map<String, Object>> listMap = null;
		
		String querySql = "";
		//查出条件
		String queryFieldsName = "";
		//拼接查询条件
		String whereFieldsName = "";
		if(type == 1){
			for(int i=0;i<FieldsList.size();i++){
				if(type == 1){
					String fieldName = FieldsList.get(i).getFieldname();
					if(i == FieldsList.size()-1){
						queryFieldsName += fieldName;
					}else{
						queryFieldsName += fieldName + ",";
					}
				}
			}
			
			for(int i=0;i<FieldsList.size();i++){
				String fieldName = FieldsList.get(i).getFieldname();
				if(i == FieldsList.size()-1){
					whereFieldsName += "'"+fieldName+"'";
				}else{
					whereFieldsName += "'"+fieldName + "',";
				}
			}
			
			querySql = "select distinct b.userid,"+ queryFieldsName +" from jis_fields a ,jis_userdetail b where b.userId = '"+userId+"'" +
			" and type = '1' and a.fieldname in("+whereFieldsName+")";
			System.out.println("querySql:"+querySql);
		}else if(type == 2){
			querySql = "select a.fieldkeys,a.fieldvalues from jis_fields a where type = '2'";
			System.out.println("querySql:"+querySql);
		}
		listMap = jdbcTemplate.queryForList(querySql);
		
		return listMap;
	}

	
	
	@Override
	public List<Integer> findFieldsType() {
		
		return jisFieldsDao.findFieldsType();
	}
	
	

}
