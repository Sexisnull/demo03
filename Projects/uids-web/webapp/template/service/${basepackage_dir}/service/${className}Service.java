<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<#-- 取包名 -->
package ${basepackage}.service;

import java.util.*;
import com.gsww.jup.util.*;
import ${basepackage}.entity.${className};

public interface ${className}Service{

	/**
	 * 保存或修改对象
	 * @param ${classNameLower}
	 * @throws Exception
	 */
	public void save(${className} entity) throws Exception;
	
	/**
	 * 删除单个对象
	 * @param ${classNameLower}
	 * @throws Exception
	 */
	public void delete(${className} entity) throws Exception;
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception;
	
	/**
	 * 根据主键查找对象
	 * @param pk
	 * @throws Exception
	 */
	public ${className} findByKey(${table.idColumn.javaType} pk) throws Exception;
	
	/**
	 * 分页
	 * @param page
	 * @param filters
	 * @throws Exception
	 */
	public Page<${className}> findPage(Page<${className}> page, List<PropertyFilter> filters) throws Exception;
	
	/**
	 * 查询列表
	 * @param filters
	 * @throws Exception
	 */
	public List<${className}> findList(List<PropertyFilter> filters) throws Exception;
}
