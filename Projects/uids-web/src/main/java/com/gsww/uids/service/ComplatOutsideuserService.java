package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatOutsideuser;

/**
 * Title: OutsideUserService.java Description: 个人用户Service层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:41:40
 */
public interface ComplatOutsideuserService {

	/**
	 * @discription 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatOutsideuser> getOutsideUserPage(Specification<ComplatOutsideuser> spec, PageRequest pageRequest);

	/**
	 * @discription 根据iid查询个人用户信息
	 * @param iid
	 * @return
	 */
	ComplatOutsideuser findByKey(Integer iid);

	/**
	 * @discription 保存个人用户信息
	 * @param iid
	 * @return
	 */
	void save(ComplatOutsideuser outsideUser);

	/**
	 * @discription 删除个人用户信息
	 * @param iid
	 * @return
	 */
	void delete(ComplatOutsideuser outsideUser);

	public List<ComplatOutsideuser> findAll();
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> findByNameOrPinYin(String keyword);
}
