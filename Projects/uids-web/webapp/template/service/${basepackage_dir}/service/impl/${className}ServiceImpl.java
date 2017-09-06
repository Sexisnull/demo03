<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<#-- 取包名 -->
package ${basepackage}.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import java.util.*;
import com.gsww.jup.util.*;
import ${basepackage}.entity.${className};
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.service.${className}Service;

@Transactional
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements ${className}Service {
	@Autowired
	private ${className}Dao ${classNameLower}Dao;

	/**
	 * 保存或修改对象
	 * @param ${classNameLower}
	 * @throws Exception
	 */
	public void save(${className} entity) throws Exception {
		Assert.notNull(entity, "${className}Service.save(${className} entity): entity不能为空");
		if(entity.get${table.idColumn.columnName}()==null || entity.get${table.idColumn.columnName}().toString().trim().equals(""))
			${classNameLower}Dao.onlySave(entity);
		else
			${classNameLower}Dao.update(entity);
	}
	
	/**
	 * 删除单个对象
	 * @param ${classNameLower}
	 * @throws Exception
	 */
	public void delete(${className} entity) throws Exception {
		Assert.notNull(entity, "${className}Service.delete(${className} entity): entity不能为空");
		${classNameLower}Dao.delete(entity);
	}
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		Assert.notNull(id, "${className}Service.delete(String id): id不能为空");
		${classNameLower}Dao.delete(id);
	}
	
	/**
	 * 根据主键查找对象
	 * @param pk
	 * @throws Exception
	 */
	public ${className} findByKey(${table.idColumn.javaType} pk) throws Exception {
		Assert.notNull(pk, "${className}Service.findByKey(${table.idColumn.javaType} pk): pk不能为空");
		return ${classNameLower}Dao.get(pk);
	}
	
	/**
	 * 分页
	 * @param page
	 * @param filters
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public Page<${className}> findPage(Page<${className}> page, List<PropertyFilter> filters) throws Exception {
		return ${classNameLower}Dao.findPage(page, filters);
	}
	
	/**
	 * 查询列表
	 * @param filters
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public List<${className}> findList(List<PropertyFilter> filters) throws Exception {
		return ${classNameLower}Dao.find(filters);
	}
}
