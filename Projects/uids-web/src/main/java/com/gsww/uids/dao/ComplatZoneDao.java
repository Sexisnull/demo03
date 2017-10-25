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
	
	/**
	 * 根据父id找出子区域
	 * @param pid
	 * @return
	 */
	@Query("select t from ComplatZone t where  t.pid =?1 ORDER BY t.orderId ASC,t.iid ASC")
	public List<ComplatZone> findChildByPid(int pid);
	
	/**
	 * 根据pid查询对象
	 * @author Lincx
	 * @param pid
	 * @return
	 */
	@Query(value = "select group from ComplatZone group where group.pid=?1 order by group.type asc")
	public List<ComplatZone> findByPid(Integer pid);
	@Query(value = "select * from complat_zone where iid =?1 union (select * from complat_zone where pid =?1)",nativeQuery=true)
	public List<ComplatZone>findAllByIid(Integer iid);
	
	/**
	 * 通过区域编码查找对象
	 * @param codeId
	 * @return
	 */
	public ComplatZone findByCodeId(String codeId);
}
