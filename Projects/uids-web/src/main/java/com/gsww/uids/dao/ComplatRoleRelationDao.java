package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatRolerelation;

public interface ComplatRoleRelationDao extends PagingAndSortingRepository<ComplatRolerelation, Integer>,
JpaSpecificationExecutor<ComplatRolerelation>{
	
	@Query("select t from ComplatRolerelation t where t.userId = ?")
	List<ComplatRolerelation> findByUserId(Integer userId);

	List<ComplatRolerelation> findByRoleId(Integer roleId);
}
