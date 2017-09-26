package com.gsww.jup.service.sys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.gsww.jup.entity.sys.SysOperator;
import java.util.*;
/**操作表接口
 * @author Administrator
 *
 */
public interface SysOperatorService {
	/**
	 * 保存操作
	 * @param entity
	 * @throws Exception
	 */
	public void save(SysOperator entity) throws Exception;
	
	/**
	 * 删除操作
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SysOperator entity) throws Exception;	
	
	/**
	 * 根据主键查找对象
	 * @param operatorId
	 * @throws Exception
	 */
	public SysOperator findByOperatorId(String operatorId) throws Exception;
	
	/**
	 * 分页查询操作
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<SysOperator> getSysOperatorPage(Specification<SysOperator> spec, PageRequest pageRequest);
	/**
	 * 根据主键停用对象
	 * @param operatorId
	 * @throws Exception
	 */
	public void stop(String operatorId) throws Exception;
	/**
	 * 根据主键启用对象
	 * @param operatorId
	 * @throws Exception
	 */

	public void start(String operatorId) throws Exception;	


	/**
	 * 方法描述 : 获取用户操作
	 * @param menuId
	 * @param tabIndex
	 * @param operatorType
	 * @param roleIds
	 * @return
	 */
	public List<Map<String, Object>> getOptionByPageName(String roleIds, String menuId, String operatorType, String tabIndex) throws Exception;

	public List<SysOperator> findList(Object object); 

}
