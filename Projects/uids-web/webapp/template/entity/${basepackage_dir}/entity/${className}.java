<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<#-- 取包名 -->
package ${basepackage}.entity;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="${table.sqlName}")
public class ${className} implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
	
	//fields
	<@generateFields/>
	
	//Constructor
	<@generateConstructor/>
	
	//Properties
	<@generateProperties/>
}

<#macro generateFields>
	<#list table.columns as column>
	private ${column.javaType} ${column.columnNameLower};
	</#list>
</#macro>

<#macro generateConstructor>
	public ${className}() {
		
	}
</#macro>

<#macro generateProperties>
<#list table.columns as column>
	
	<#if column.pk>
	@Id
	<#if column.isStringColumn>
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	<#else>
	@GeneratedValue(strategy = IDENTITY)
	</#if>
	<#elseif column.isDateTimeColumn>
	@Temporal(TemporalType.DATE)
	</#if>
	@Column(name = "${column.sqlName}", unique = ${column.unique?string}, nullable = ${column.nullable?string}, length = ${column.size})
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
</#list>
</#macro>