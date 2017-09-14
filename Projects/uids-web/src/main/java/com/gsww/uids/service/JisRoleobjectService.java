package com.gsww.uids.service;

import java.util.List;

import com.gsww.uids.entity.JisRoleobject;

public interface JisRoleobjectService {
	
	List<JisRoleobject> findByRoleIdAndType(Integer roleId,Integer type);
	
	boolean modifyRoleApps(Integer iid,String apps);
}
