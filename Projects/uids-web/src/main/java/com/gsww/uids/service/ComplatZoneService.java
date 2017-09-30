package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatZone;

/**
 * Title: ComplatZoneService.java Description: 区域管理Service
 * 
 * @author yangxia
 * @created 2017年9月15日 下午2:15:13
 */
public interface ComplatZoneService {

	public Page<ComplatZone> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

	/**
	 * @discription 获取分页数据
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatZone> getPage(Specification<ComplatZone> spec, PageRequest pageRequest);

	/**
	 * @discription 获取全部区域信息
	 * @return
	 */
	List<ComplatZone> getAll();

	/**
	 * @discription 获取指定(iid)区域实体
	 * @param iid
	 * @return
	 */
	public ComplatZone fingByKey(Integer iid);

	/**
	 * @discription 保存区域实体
	 * @param complatZone
	 */
	public void save(ComplatZone complatZone);

	/**
	 * @discription 删除区域实体
	 * @param complatZone
	 */
	public void delete(ComplatZone complatZone);

	/**
	 * @discription 检查区域唯一性
	 * @param nameInput
	 * @param codeId
	 * @return
	 */
	public List<ComplatZone> checkUniqueDeptName(String nameInput, String codeId);
	
	/**
	 * 根据父Id找出子区域
	 * @param int1
	 * @return
	 */
	public List<ComplatZone> findChildByIid(int int1);
}
