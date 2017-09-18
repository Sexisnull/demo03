package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatZone;

/**
 * Title: ComplatZoneDao.java Description: 区域管理Dao
 * 
 * @author yangxia
 * @created 2017年9月15日 下午9:16:29
 */
public interface ComplatZoneDao
		extends PagingAndSortingRepository<ComplatZone, Integer>, JpaSpecificationExecutor<ComplatZone> {

	/**
	 * @discription 根据iid查询实体
	 * @param iid
	 * @return
	 */
	public ComplatZone findByIid(Integer iid);

	/**
	 * @discription 找出name和codeid相同的区域实体
	 * @param nameInput
	 * @param codeId
	 * @return
	 */
	@Query("select t from ComplatZone t where  t.name =?1  and t.codeId like %?2")
	public List<ComplatZone> findByNameAndLikeCodeId(String nameInput, String codeId);
}
