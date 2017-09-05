package com.gsww.jup.service.sys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysApps;

/**
 * 应用系统管理接口
 * @author taoweixin
 *
 */
public interface SysAppsService {
	

	/**
	 * 保存操作
	 * @param entity
	 * @throws Exception
	 */
	public void save(SysApps entity) throws Exception;
	
	/**
	 * 删除操作
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SysApps entity) throws Exception;	
	
	/**
	 * 根据主键查找对象
	 * @param operatorId
	 * @throws Exception
	 */
	public SysApps findBykey(String key) throws Exception;
	
	/**
	 * 分页查询操作
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<SysApps> getSysAppsPage(Specification<SysApps> spec, PageRequest pageRequest);
	/**
	 * 根据主键停用对象
	 * @param operatorId
	 * @throws Exception
	 */
	public void stop(String key) throws Exception;
	/**
	 * 根据主键启用对象
	 * @param operatorId
	 * @throws Exception
	 */

	public void start(String key) throws Exception;	
	/**
	 * 根据主键停用绿色通道
	 * @param operatorId
	 * @throws Exception
	 */
	public void  stopAppGreenChanal(String key) throws Exception;
	/**
	 * 根据主键启用绿色通道
	 * @param operatorId
	 * @throws Exception
	 */

	public void startAppGreenChanal(String key) throws Exception;	

}
