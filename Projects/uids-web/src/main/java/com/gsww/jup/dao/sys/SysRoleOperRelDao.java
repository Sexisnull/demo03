package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.entity.sys.SysRoleOperRel;

public interface SysRoleOperRelDao extends PagingAndSortingRepository<SysRoleOperRel, String>,
JpaSpecificationExecutor<SysRoleOperRel> {

	List<SysRoleOperRel> findByRoleId(String roleId);

	List<SysRoleOperRel> findBySysOperator(SysOperator o);
	
}