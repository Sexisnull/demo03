package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysMenu;


public interface SysMenuDao extends PagingAndSortingRepository<SysMenu, String>,
JpaSpecificationExecutor<SysMenu> {
	@Query("select t from SysMenu t where t.parentMenuId != '0'")
	List<SysMenu> findSecondMenu();
	
	@Query("select t from SysMenu t where t.parentMenuId ='0'")
	List<SysMenu> findFirstMenu();
	
	@Query("select t from SysMenu t where t.parentMenuId =?")
	List<SysMenu> findMenuByParentMenuId(String parentMenuId);
}
