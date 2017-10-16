
package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.JisRoleobject;

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
	public List<JisRoleobject> findAcctByroleId(Integer roleId)throws Exception;
	//根据角色名查询
	List<ComplatRole> findByName(String name);
	//获取授权
	public String getAuthorizeTree(String id) throws Exception;
	//保存授权书
	public void saveAuthorize(String id,String keys,String types);
	
	/**
	 * 根据用户ID查询对应的角色信息
	 */
	public List<JisRoleobject> findByUserId(Integer userId,Integer groupId) throws Exception;
	
	/**
	 * 查询角色所属用户
	 * @param roleId
	 * @param memberType
	 * @param memberName
	 * @return
	 */
	public List<Map<String, Object>> findRoleMember(String roleId,
			String memberType, String memberName);
	/**
	 * 根据用户角色清空关联用户和机构
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId);
	
	/**
	 * 删除与角色关联的相关用户和机构
	 * @param roleId
	 * @param users
	 * @param groups
	 */
	public void deleteUsersByRoleId(String roleId,String[] users,String[] groups);

}
