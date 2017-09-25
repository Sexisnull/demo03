package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.gsww.jup.util.StringHelper;
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
			if (jisFields.getIssys() != null && jisFields.getIssys() == 1) {
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
		//查出字段
		String queryFieldsName = "";
		//拼接查询条件
		String whereFieldsName = "";
		
		String defValue = "";
		String fieldkeys = "";
		String fieldvalues = "";
		if(type == 1){
			for(int i=0;i<FieldsList.size();i++){
				defValue = FieldsList.get(i).getDefvalue();
				fieldkeys = FieldsList.get(i).getFieldkeys();
				fieldvalues = FieldsList.get(i).getFieldvalues();
				if(!StringHelper.isNotBlack(defValue) && !StringHelper.isNotBlack(fieldkeys) && !StringHelper.isNotBlack(fieldvalues)){
					String fieldName = FieldsList.get(i).getFieldname();
					queryFieldsName += fieldName + ",";
				}
			}
			queryFieldsName = queryFieldsName.substring(0,queryFieldsName.length()-1);
			
			for(int i=0;i<FieldsList.size();i++){
				defValue = FieldsList.get(i).getDefvalue();
				fieldkeys = FieldsList.get(i).getFieldkeys();
				fieldvalues = FieldsList.get(i).getFieldvalues();
				if(!StringHelper.isNotBlack(defValue) && !StringHelper.isNotBlack(fieldkeys) && !StringHelper.isNotBlack(fieldvalues)){
					String fieldName = FieldsList.get(i).getFieldname();
					whereFieldsName += "'"+fieldName + "',";
				}
			}
			whereFieldsName = whereFieldsName.substring(0,whereFieldsName.length()-1);
			if(userId.equals(null)){
				querySql = "select distinct type,"+ queryFieldsName +" from jis_fields a  where"+
				"type = '1' and a.fieldname in("+whereFieldsName+")";
			}else{
				querySql = "select distinct b.userid,type,"+ queryFieldsName +" from jis_fields a ,jis_userdetail b where b.userid = '"+userId+"'" +
				" and type = '1' and a.fieldname in("+whereFieldsName+")";
			}			
		}else if(type == 2){
			querySql = "select a.fieldkeys,a.fieldvalues,type,a.fieldname from jis_fields a where type = '2'";
		}
		listMap = jdbcTemplate.queryForList(querySql);
		return listMap;
	}

	
	
	@Override
	public List<Integer> findFieldsType() {
		
		return jisFieldsDao.findFieldsType();
	}

	@Override
	public List<JisFields> findByType(Integer type) throws Exception {
		
		return jisFieldsDao.findByType(type);
	}

	@Override
	public Map<String, Object> findByUserIdAndType(List<JisFields> fieldsList,Integer userId) throws Exception {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		String names = "";
		String fieldName = "";
		for(int i=0;i<fieldsList.size();i++){
			fieldName = fieldsList.get(i).getFieldname();
			names += fieldName+",";
		}
		names = names.substring(0, names.length()-1);
		String querySql = "select distinct "+names+" from jis_fields a ,jis_userdetail b where  a.type='2' and b.userid = '1'";
		fieldsMap = jdbcTemplate.queryForMap(querySql);
		return fieldsMap;
	}

	@Override
	public List<JisFields> findByFieldname(String fieldname) {
		return jisFieldsDao.findByFieldname(fieldname);
	}
}
