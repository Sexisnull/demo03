package com.gsww.uids.service;

import java.util.List;

import com.gsww.uids.entity.JisRoleobject;

public interface JisRoleobjectService {
	
	List<JisRoleobject> findByRoleIdAndType(Integer roleId,Integer type);
	
	boolean modifyRoleApps(Integer iid,String apps);
	
	
	int findObjectSize(int parseInt, int parseInt2, int i);
	
	/**
	 * 保存关系
	 * @param parseInt
	 * @param parseInt2
	 * @param i
	 * @return
	 */
	boolean add(int parseInt, int parseInt2, int i);
	public void deleteByRoleId(int id);
	
	/**
	 * 根据对象id以及类型查找角色关系
	 * @param iid
	 * @param i
	 * @return
	 */
	List<JisRoleobject> findByObjectIdAndType(Integer iid, int i);
}
