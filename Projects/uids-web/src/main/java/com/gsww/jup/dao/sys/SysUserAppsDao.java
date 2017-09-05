package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.entity.sys.SysUserApps;

public interface SysUserAppsDao extends
PagingAndSortingRepository<SysUserApps, String>,
JpaSpecificationExecutor<SysUserApps>{
	
}
