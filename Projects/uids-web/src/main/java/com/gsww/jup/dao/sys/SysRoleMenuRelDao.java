package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysRoleMenuRel;

public interface SysRoleMenuRelDao extends PagingAndSortingRepository<SysRoleMenuRel, String>,
JpaSpecificationExecutor<SysRoleMenuRel> {

	List<SysRoleMenuRel> findByRoleId(String roleId);

	List<SysRoleMenuRel> findBySysMenu(SysMenu sysMenu);
}