package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatGroup;

public interface ComplatGroupDao extends  PagingAndSortingRepository<ComplatGroup, String>,JpaSpecificationExecutor<ComplatGroup>{
	
	@Query(value = "select group from ComplatGroup group where group.pid=?1 order by group.orderid asc")
	public List<ComplatGroup> findByPid(Integer pid);
	
	public ComplatGroup findByIid(Integer iid);
}
