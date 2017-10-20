package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.util.ReflectionUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.dao.ComplatGroupDao;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatUser;
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
	public List<ComplatGroup> findByPid(Integer pid) throws Exception {
		List<ComplatGroup> list=new ArrayList<ComplatGroup>();
		list=complatGroupDao.findByPid(pid);
		return list;	
	}
	
	@Override
	public List<ComplatGroup> findAll() {
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
	public boolean queryNameIsUsed(String name, Integer pid) throws Exception {
		String sql = "SELECT iid FROM complat_group WHERE name LIKE '%" + name + "%' AND pid LIKE '%" + pid + "%' AND opersign <> '3'";
	    if (jdbcTemplate.queryForList(sql).size() > 0) {
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
	
	@Override
	public List<Map<String,Object>> findChildGroupByIid(Integer iid) {
		
		String sql = "SELECT a.iid, a.name, a.codeid, a.pid, CASE WHEN EXISTS(SELECT 1 FROM complat_group b WHERE b.pid = a.iid AND opersign <> '3') THEN 1 ELSE 0 END isparent,a.spec,a.orderid,a.pinyin  FROM complat_group a ";
		if(iid!=null && iid>0){
			sql = sql + "WHERE a.pid="+iid;
		}else{
			sql = sql + "WHERE a.pid IS NULL";
		}
		sql = sql + " AND opersign <> '3' ";
	    sql = sql + " ORDER BY a.orderid ASC,a.iid ASC";
	    
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findByNameOrPinYin(String keyword) {
		String sql = "SELECT iid, name, codeid FROM complat_group WHERE name" +
				" LIKE '%"+keyword+"%' OR pinyin LIKE '%"+keyword+"%' AND opersign <> '3' ";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	@Override
	public List<ComplatGroup> findByAllName(String name) {
		List<ComplatGroup> list=new ArrayList<ComplatGroup>();
		list=complatGroupDao.findByName(name);
		return list;
	}
	
	@Override
	public List<Map<String,Object>> findAllIidsAndName() {
		String sql = "select iid, name from complat_group where opersign<>3 ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public ComplatGroup findByCodeid(String codeid) throws Exception {
		List<ComplatGroup> list = complatGroupDao.findByCodeid(codeid);
		if(list.size()!=0){
			return complatGroupDao.findByCodeid(codeid).get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<ComplatGroup> findByNoPid() throws Exception{
		List<ComplatGroup> list=new ArrayList<ComplatGroup>();
		list=complatGroupDao.findByPidIsNullOrderByCodeidDesc();
		return list;
	}

	@Override
	public List<ComplatGroup> findAllOrg() throws Exception {
		List<ComplatGroup> list=new ArrayList<ComplatGroup>();
		list=complatGroupDao.findAllOrg();
		return list;
	}

	public List<ComplatGroup> findAllDept(String deptId) throws Exception {
		List<ComplatGroup> list=new ArrayList<ComplatGroup>();
		list=complatGroupDao.findAllDepId(deptId);
		return list;
	}

}
