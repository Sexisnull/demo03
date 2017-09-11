package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.OutsideUser;

/**
 * Title: OutsideUserService.java Description: 个人用户Service层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:41:40
 */
public interface OutsideUserService {

	/**
	 * @discription 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<OutsideUser> getOutsideUserPage(Specification<OutsideUser> spec, PageRequest pageRequest);

	/**
	 * @discription 根据iid查询个人用户信息
	 * @param iid
	 * @return
	 */
	OutsideUser findByKey(Integer iid);

	/**
	 * @discription 保存个人用户信息
	 * @param iid
	 * @return
	 */
	void save(OutsideUser outsideUser);

	/**
	 * @discription 删除个人用户信息
	 * @param iid
	 * @return
	 */
	void delete(OutsideUser outsideUser);
}
