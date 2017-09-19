package com.gsww.uids.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatUser;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   政府用户模块service层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">shenxh</a>
 */
public interface ComplatUserService {

	
	

	/**
	 * 
	 * 查询政府用户列表
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatUser> getComplatUserPage(Specification<ComplatUser> spec, PageRequest pageRequest);
	
	
	/**
	 * 根据主键查询用户信息
	 */
	ComplatUser findByKey(Integer iid) throws Exception;
	
	/**
	 * 保存
	 */
	void save(ComplatUser complatUser);
	
	/**
	 * 删除
	 */
	void delete(ComplatUser complatUser) throws Exception;
	
	
	/**
	 * 根据用户名查询用户信息
	 */
	List<ComplatUser> findByUserAllName(String loginallname);
	
	
	/**
	 * 用户设置 保存     
	 * @author yaoxi
	 */
	void updateUser(Integer iid,String name,String headShip,String phone,String mobile,String fax,
			String email,String qq, Date modifyTime,String pwd) throws Exception;

	public List<Map<String,Object>> findByGroupIds(String id);

	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> findByNameOrPinYin(String keyword);
	
}
