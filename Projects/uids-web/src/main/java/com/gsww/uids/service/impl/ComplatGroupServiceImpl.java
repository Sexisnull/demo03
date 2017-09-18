package com.gsww.uids.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.util.ReflectionUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.dao.ComplatGroupDao;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.service.ComplatGroupService;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


@Transactional
@Service("complatGroupService")
public class ComplatGroupServiceImpl implements ComplatGroupService{
	@Autowired
	private ComplatGroupDao complatGroupDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ComplatGroup> findByPid(Integer pid) {
		// TODO Auto-generated method stub
		
		return complatGroupDao.findByPid(pid);
		
	}
	
	@Override
	public List<ComplatGroup> findAll() {
		// TODO Auto-generated method stub
		String sql = "select * from complat_group";
		return jdbcTemplate.queryForList(sql, ComplatGroup.class);
		
	}

	@Override
	public String delete(ComplatGroup entity) throws Exception {		
		JSONObject jsonObject = JSONObject.fromObject(entity);  
		String logMsg=jsonObject.toString();
		complatGroupDao.delete(entity);
		return logMsg;
	}
    //根据机构主键查询机构信息
	@Override
	public ComplatGroup findByKey(String pk) throws Exception {
		ComplatGroup complatGroup=complatGroupDao.findByIid(Integer.valueOf(pk));
		return complatGroup;
	}
	@Override
	public ComplatGroup findByIid(Integer iid) {
		ComplatGroup complatGroup=complatGroupDao.findByIid(iid);
		return complatGroup;
	}
    
	@Override
	public Page<ComplatGroup> getUserPage(Specification<ComplatGroup> spec,
			PageRequest pageRequest) {
		return complatGroupDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatGroup save(ComplatGroup complatGroup) throws Exception {
		if(StringHelper.isNotBlack(String.valueOf(complatGroup.getIid()))){
			ComplatGroup groupTemp = complatGroupDao.findByIid(complatGroup.getIid());
			//将表单未携带的信息取出保存
			BeanUtils.copyProperties(complatGroup, groupTemp, ReflectionUtils.getNullPropertyNames(complatGroup));
			return complatGroupDao.save(groupTemp);
		}else{
			
			return complatGroupDao.save(complatGroup);
		}
		
	}

	@Override
	public ComplatGroup findByName(String name) throws Exception {
		List<ComplatGroup> list = complatGroupDao.findByName(name);
		if(list.size()!=0){
			return complatGroupDao.findByName(name).get(0);
		}else{
			return null;
		}
	}
	@Override
	public ComplatGroup queryNameIsUsed(String name) throws Exception {
		List<ComplatGroup> list = complatGroupDao.findByName(name);
		if (CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}	
	
//	/**
//	 *同步用户 1
//	 */
//	@Override
//	public ComplatGroup saveUser(ComplatGroup complatGroup){				
//		return complatGroupDao.save(complatGroup);
//	}

	/**
	 *查找用户是否存在
	 */
	@Override
	public boolean getByName(String name)
			throws Exception {
	List list= complatGroupDao.findByName(name);
	if(list.size()>0){
		return true;	
	}else{
		return false;	
		}	
	}
	
	@Override
	public List<Map<String, Object>> getComplatGroupList() throws Exception {
		String sql ="select g.iid,g.name from complat_group g";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
	
}
