package com.gsww.jup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.ComplatCorporation;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   法人管理模块DAO层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface ComplatCorporationDao extends  PagingAndSortingRepository<ComplatCorporation, Integer>,JpaSpecificationExecutor<ComplatCorporation>{
	
	/**
	 * 根据主键查询
	 */
	public ComplatCorporation findByIid(Integer iid);
}
