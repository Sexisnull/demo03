package com.gsww.uids.manager.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.role.entity.Role;

/**
 * 角色
 * @author sunbw
 *
 */
@Service
public interface RoleService {
	
	/**
	 * 保存、修改
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(Role role);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	void delete(String ids);
	
	/**
	 * 获取对象
	 * @return
	 */
	Role findById(String id);
	
	/**
	 * 根据表示标识符查找
	 * @param mark 标识符
	 * @return
	 */
	List<Role> findByMark(String mark);
	
	/**
	 * 根据角色名查找
	 * @param name
	 * @return
	 */
	List<Role> findByName(String name); 
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(Role info, StringBuilder errInfo);

	/**
	 * 获取分页列表
	 * @param page 页数
	 * @param size 大小
	 * @param searchText 检索内容
	 * @return
	 */
	PageObject<Role> findPage(Integer page, Integer size,String searchText);
	
	/**
	 * 查询全部列表
	 */
	List<Role> findAll();
	
	/**
	 * 查询所有非政府用户能拥有的角色
	 * 
	 * @return
	 */
	List<Role> findAllOfNonGovernment();
	
	/**
	 * 打开或者关闭有效状态
	 * @param ids
	 * @return
	 */
	void openOrClose(String ids,String oper);
	
	/**
	 * 检查能否批量删除：如果有一个不能删除，则返回false
	 * 
	 * @param ids
	 * @param errInfo
	 * @return
	 */
	boolean checkDelete(String ids, StringBuilder errInfo);
}
