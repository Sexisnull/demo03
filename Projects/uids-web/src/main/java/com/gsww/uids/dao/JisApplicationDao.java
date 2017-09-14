package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.JisApplication;
/**
 * 应用表dao接口
 * @author zhanglei
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
	List<JisApplication> findByComplatGroup(ComplatGroup complatGroup);
}
