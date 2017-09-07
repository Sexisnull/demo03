package com.gsww.jup.dao.sys;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysApps;

public interface SysAppsDao  extends
PagingAndSortingRepository<SysApps, String>,JpaSpecificationExecutor<SysApps>{

	/**
	 * 根据主键查找对象
	 * @param operatorId
	 * @throws Exception
	 */
	SysApps findByKey(String key);
	/**
	 * 
	 * 方法描述 : 通过操作id更新该应用状态
	 * @param state 状态
	 * @param id  账号id
	 */
	@Modifying
	@Query("update SysApps t set t.appState =?1 where t.key =?2")
	void updateState(String state, String key);
	/**
	 * 
	 * 方法描述 : 通过操作id更新开通绿色通道状态
	 * @param state 状态
	 * @param id  账号id
	 */
	@Modifying
	@Query("update SysApps t set t.appGreenChanal =?1 where t.key =?2")
	void updateAppGreenChanal(String appGreenChanal, String key);

}
