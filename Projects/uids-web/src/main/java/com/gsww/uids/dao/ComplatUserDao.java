package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatUser;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   政府用户模块DAO层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">shenxh</a>
 */


public interface ComplatUserDao extends PagingAndSortingRepository<ComplatUser, Integer>,JpaSpecificationExecutor<ComplatUser>{

	ComplatUser findByIid(Integer iid);

	/**
	 * 根据登录名称和密码获取用户实体
	 * @param userName
	 * @param password
	 * @author 张磊
	 * @return
	 */
	public List<ComplatUser> findByLoginnameAndPwdAndGroupid(String userName,
			String password,Integer groupId);
	
	
	

	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @param password
	 * @author shenxh
	 * @return
	 */
	public List<ComplatUser> findByName(String name);
	

}








