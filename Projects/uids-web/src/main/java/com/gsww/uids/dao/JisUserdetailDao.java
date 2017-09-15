package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisUserdetail;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   用户模块DAO层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface JisUserdetailDao extends  PagingAndSortingRepository<JisUserdetail, Integer>,JpaSpecificationExecutor<JisUserdetail>{
	
	JisUserdetail findByUserid(Integer userId);
	
	@Modifying
	@Query("update JisUserdetail t set t.cardid = ?2 where t.iid = ?1")
	void update(Integer iid,String cardId);
}
