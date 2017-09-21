package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisRoleobject;

/**
 * 角色授权关联表
 * @author zhanglei
 *
 */
public interface JisRoleobjectDao extends PagingAndSortingRepository<JisRoleobject, Integer>,JpaSpecificationExecutor<JisRoleobject> {
	
	public List<JisRoleobject> findByRoleidAndType(Integer roleId,Integer type);
	
}
