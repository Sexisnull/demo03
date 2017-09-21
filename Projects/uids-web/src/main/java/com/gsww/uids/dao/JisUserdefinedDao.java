package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisUserdefined;

/**
 * 自定义应用账号dao层
 * @author zhanglei
 *
 */
public interface JisUserdefinedDao extends PagingAndSortingRepository<JisUserdefined, String>,JpaSpecificationExecutor<JisUserdefined>{
	
	JisUserdefined findByAppidAndLoginallname(Integer appid,String loginAllName);

	JisUserdefined findByIid(int key);
}
