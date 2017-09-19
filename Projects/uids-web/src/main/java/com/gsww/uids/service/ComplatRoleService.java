
package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;

public interface ComplatRoleService {
	//角色分页查询
	public Page<ComplatRole> getRolePage(Specification<ComplatRole> spec, PageRequest pageRequest);
	//根据主键查询
	public ComplatRole findByKey(int id) throws Exception;
	//保存
	public void save(ComplatRole entity) throws Exception;
	//查询所有
	public List<ComplatRole> findRoleList() throws Exception;
	//根据主键删除
	public void delete(int id) throws Exception;
	//根据角色查询用户关系
	public List<ComplatRolerelation> findAcctByroleId(Integer roleId)throws Exception;
	//根据角色名查询
	List<ComplatRole> findByName(String name);
	//获取授权
	public String getAuthorizeTree(String id) throws Exception;
	//保存授权书
	public void saveAuthorize(String id,String keys,String types);
	
	/**
	 * 根据用户ID查询对应的角色信息
	 */
	public List<ComplatRolerelation> findByUserId(Integer userId) throws Exception;

}