package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisFields;

/**
 * Title: JisFieldsDao.java Description: 用户扩展属性Dao
 * @author yangxia
 * @created 2017年9月12日 下午5:12:47
 */
public interface JisFieldsDao
		extends PagingAndSortingRepository<JisFields, Integer>, JpaSpecificationExecutor<JisFields> {

	/**
	 * @discription 根据iid查找用户扩展属性
	 * @param iid
	 * @return
	 */
	JisFields findByIid(Integer iid);
}
