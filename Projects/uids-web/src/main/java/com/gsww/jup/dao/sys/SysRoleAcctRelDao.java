package com.gsww.jup.dao.sys;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysRoleAcctRel;

public interface SysRoleAcctRelDao extends PagingAndSortingRepository<SysRoleAcctRel, String>,
JpaSpecificationExecutor<SysRoleAcctRel> {

	List<SysRoleAcctRel> findByUserAcctId(String userAcctId);

	List<SysRoleAcctRel> findByRoleId(String roleId);

	
}