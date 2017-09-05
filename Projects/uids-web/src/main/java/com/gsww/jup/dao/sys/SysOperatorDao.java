package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysOperator;

public interface SysOperatorDao extends
		PagingAndSortingRepository<SysOperator, String>,
		JpaSpecificationExecutor<SysOperator> {
	/**
	 * 根据主键查找对象
	 * @param operatorId
	 * @throws Exception
	 */
	SysOperator findByOperatorId(String operatorId);
	/**
	 * 
	 * 方法描述 : 通过操作id更新该操作状态
	 * @param state 状态
	 * @param id  账号id
	 */
	@Modifying
	@Query("update SysOperator t set t.operatorState =?1 where t.operatorId =?2")
	void updateState(String state, String operatorId);
	
	/**
	 * 方法描述 : 根据菜单ID查找操作
	 * @param mId
	 * @return
	 */
	List<SysOperator> findBySysMenu(SysMenu sysMenu);
}
