package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.uids.entity.ComplatGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface ComplatGroupService {
	
	public List<ComplatGroup> findAll();
	
	/**
	 * 根据pid查找对象
	 * @param pid
	 * @throws Exception
	 */
	public List<ComplatGroup> findByPid(Integer pid) throws Exception;
	
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
	public boolean queryNameIsUsed(String name, Integer pid)throws Exception;
	

	/**
	 * 查找机构是否存在
	 * @param loginAccount
	 * @throws Exception
	 * @author anhang
	 */
	public boolean getByName(String name) throws Exception;
	
	/**
	 * 查询所有机构
	 * @return
	 * @throws Exception
	 * @author Seven
	 */
	public List<Map<String, Object>> getComplatGroupList() throws Exception;
	
		public List<Map<String,Object>> findChildGroupByIid(Integer iid);
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> findByNameOrPinYin(String keyword);
	
	/**
	 * 根据名字查找对象保存到列表里
	 */
	public List<ComplatGroup> findByAllName(String name);
	
	public List<Map<String,Object>> findAllIidsAndName();
	
	/**
	 * 根据机构编码查找对象
	 * @param codeid
	 * @return
	 * @throws Exception
	 */
	public ComplatGroup findByCodeid(String codeid) throws Exception;
	
	/**
	 * 查询没有pid的机构
	 * @return
	 * @throws Exception
	 */
	public List<ComplatGroup> findByNoPid() throws Exception;

	/**
	 * 部门树专用
	 * @return
	 * @throws Exception
	 */
	public List<ComplatGroup> findAllOrg() throws Exception;

}
