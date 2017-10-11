package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.uids.entity.ComplatGroup;

public interface ComplatGroupDao extends  PagingAndSortingRepository<ComplatGroup, String>,
JpaSpecificationExecutor<ComplatGroup>{
	
	/**
	 * 根据pid查询对象
	 * @param pid
	 * @return
	 */
	@Query(value = "select group from ComplatGroup group where group.pid=?1 order by group.orderid asc")
	public List<ComplatGroup> findByPid(Integer pid);
	/**
	 * 根据机构名称查询机构信息
	 */
	List<ComplatGroup> findByName(String name);
	/**
	 * 根据机构主键查询机构信息
	 * @param Iid
	 * @return
	 */
	public ComplatGroup findByIid(Integer iid);
	

	public List<ComplatGroup> findByCodeid(String paramString);
	
	/**
	 * 查询没有pid的机构
	 * @return
	 */
	public List<ComplatGroup> findByPidIsNullOrderByCodeidDesc();

	/**
	 * 查询所有对象
	 * @return
	 */
	@Query(value = "select group from ComplatGroup group where group.opersign!=3 order by group.orderid asc")
	public List<ComplatGroup> findAllOrg();
//	@Query(value = "select group from ComplatGroup group where group.pid=? order by group.orderid asc")
//	public List<ComplatGroup> findByNoPid();
}
