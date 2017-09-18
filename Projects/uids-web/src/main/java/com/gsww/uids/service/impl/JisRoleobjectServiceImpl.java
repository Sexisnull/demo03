package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public List<JisRoleobject> findByRoleIdAndType(Integer roleId, Integer type) {
		// TODO Auto-generated method stub
		return jisRoleobjectDao.findByRoleidAndType(roleId, type);
	}

	@Override
	public boolean modifyRoleApps(Integer iid, String apps) {
		// TODO Auto-generated method stub
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

}
