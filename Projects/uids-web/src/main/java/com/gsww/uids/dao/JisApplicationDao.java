package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisApplication;

/**
 * 应用表DAO接口
 * @author Seven
 *
 */
public interface JisApplicationDao extends PagingAndSortingRepository<JisApplication, String>,JpaSpecificationExecutor<JisApplication>{
	
	/**
	 * 根据主键查询信息
	 * @param iid
	 * @return
	 */
	JisApplication findByIid(Integer iid);
	
	/**
	 * 根据标识获得应用对象
	 * @param mark
	 * @return
	 */
	List<JisApplication> findByMark(String mark);
	
	/**
	 * 根据组织获得对象
	 * @param complatGroup
	 * @return
	 */
	List<JisApplication> findByGroupId(Integer groupId);
	
	/**
	 * 查询所有对象
	 */
	List<JisApplication> findAll();
	
	/**
	 * 通过是否支持同步查找应用
	 * @author Lincx
	 */
	List<JisApplication> findByIsSyncGroupNotNullAndLoginType(Integer loginType);
	
	/**
	 * 通过网络类型查询
	 * @param netType
	 * @return
	 */
	List<JisApplication> findByNetType(Integer netType);
}
