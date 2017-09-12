package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRoleRelation;

public interface ComplatRoleService {
	//角色分页查询
	public Page<ComplatRole> getRolePage(Specification<ComplatRole> spec, PageRequest pageRequest);
	//保存
	public void save(ComplatRole entity) throws Exception;
	//查询所有
	public List<ComplatRole> findRoleList() throws Exception;
	//根据主键查询
	public ComplatRole findByKey(int id) throws Exception;
	//根据主键删除
	public void delete(int id) throws Exception;
	//根据角色查询用户关系
	public List<ComplatRoleRelation> findAcctByroleId(Integer roleId)throws Exception;

}
