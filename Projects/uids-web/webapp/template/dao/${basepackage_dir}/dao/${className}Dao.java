<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<#-- 取包名 -->
package ${basepackage}.dao;

import org.springframework.stereotype.Repository;
import com.gsww.jup.HibernateDao;
import ${basepackage}.entity.${className};

@Repository("${classNameLower}Dao")
public class ${className}Dao extends HibernateDao<${className}, ${table.idColumn.javaType}>{

}