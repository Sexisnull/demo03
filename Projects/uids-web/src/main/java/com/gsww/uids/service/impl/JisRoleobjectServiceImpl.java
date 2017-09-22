package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisRoleobjectDao;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.service.JisRoleobjectService;

@Transactional
@Service("jisRoleobjectService")
public class JisRoleobjectServiceImpl implements JisRoleobjectService{
	
	@Autowired
	private JisRoleobjectDao jisRoleobjectDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<JisRoleobject> findByRoleIdAndType(Integer roleId, Integer type) {
		return jisRoleobjectDao.findByRoleidAndType(roleId, type);
	}

	@Override
	public boolean modifyRoleApps(Integer iid, String apps) {
		try {
			List<JisRoleobject> list = findByRoleIdAndType(iid,3);
			for(JisRoleobject object : list){
				jisRoleobjectDao.delete(object);
			}
			String[] app = apps.split(",");
			if(app.length>0){
				for(int i=0;i<app.length;i++){
					if(app[i]!=null && app[i].trim()!=""){
						JisRoleobject obj = new JisRoleobject();
						obj.setRoleid(iid);
						obj.setObjectid(Integer.parseInt(app[i]));
						obj.setType(3);
						jisRoleobjectDao.save(obj);
					}
				}
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public boolean add(int roleid, int objectid, int type) {
		JisRoleobject roleObject = new JisRoleobject();
	    roleObject.setObjectid(Integer.valueOf(objectid));
	    roleObject.setRoleid(Integer.valueOf(roleid));
	    roleObject.setType(Integer.valueOf(type));
	    
	    return jisRoleobjectDao.save(roleObject)!=null;
	}
	
	@Override
	public int findObjectSize(int roleid, int objectid, int type) {
		String sql = "SELECT COUNT(iid) FROM jis_roleobject " +
				"WHERE roleid = "+roleid+" AND objectid = "+objectid+" AND type = "+type;
		
		return jdbcTemplate.queryForObject(sql,Integer.class);
	}

	@Override
	public void deleteByRoleId(int id) {
		String sql="delete from jis_roleobject where roleid = "+id;
		jdbcTemplate.execute(sql);
	}

}
