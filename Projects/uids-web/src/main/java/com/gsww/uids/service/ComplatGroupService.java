package com.gsww.uids.service;

import java.util.List;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.uids.entity.ComplatGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface ComplatGroupService {
	
	public List<ComplatGroup> findByPid(Integer pid);
	
	public List<ComplatGroup> findAll();
	
	/**
	 * 保存或修改对象
	 * @param userInfo
	 * @throws Exception
	 */
	public ComplatGroup save(ComplatGroup entity) throws Exception;
	
	/**
	 * 删除单个对象
	 * @param userInfo
	 * @throws Exception
	 */
	public String delete(ComplatGroup entity) throws Exception;

	
	/**
	 * 根据主键查找对象
	 * @param pk(iid为主键)
	 * @throws Exception
	 */
	public ComplatGroup findByKey(String pk) throws Exception;
	public ComplatGroup findByIid(Integer iid);
	
	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatGroup> getUserPage(Specification<ComplatGroup> spec, PageRequest pageRequest);

	/**
	 * 根据机构名称查找对象
	 * @param loginAccount
	 * @throws Exception
	 */
	public ComplatGroup findByName(String name) throws Exception;
	
	/**
	 * 判断机构是否存在
	 * @param loginAccount
	 * @return
	 * @throws Exception
	 */
	public ComplatGroup queryNameIsUsed(String name)throws Exception;
	
//	/**
//	 * 同步用户
//	 * @param sysAccount
//	 * @throws Exception
//	 * @author anhang
//	 */
//	public ComplatGroup saveUser(ComplatGroup complatGroup);
	/**
	 * 查找机构是否存在
	 * @param loginAccount
	 * @throws Exception
	 * @author anhang
	 */
	public boolean getByName(String name) throws Exception;
	
}
