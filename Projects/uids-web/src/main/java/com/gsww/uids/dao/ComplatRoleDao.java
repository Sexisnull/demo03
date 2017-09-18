package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatRole;


public interface ComplatRoleDao extends PagingAndSortingRepository<ComplatRole, Integer>,JpaSpecificationExecutor<ComplatRole>{
	//根据主键查询角色
	ComplatRole findByIid(int roleId);
	//查询所有角色
	List<ComplatRole> findAll();
	//根据角色名查询
	List<ComplatRole> findByName(String name);
}
