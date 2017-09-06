<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="${basepackage}.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title><%=${className}.TABLE_ALIAS%>更新</title>
		<%@ include file="/include/meta.jsp"%>
	</head>

	<body>
		<ul class="tab">
			<li class="selected">
				<a>
					<img src="${'$'}{ctx}/res/icons/16x16/application_side_list.png" />
					<c:if test="${'$'}{empty entity.${table.idColumn.columnNameLower}}">
						新建${table.tableAlias}
					</c:if>
					<c:if test="${'$'}{not empty entity.${table.idColumn.columnNameLower}}">
						编辑${table.tableAlias}
					</c:if>
				</a>
			</li>
		</ul>
		<s:form action="/${classNameLower}!save.do">
			<s:hidden key="entity.${table.idColumn.columnNameLower}" />
			<#list table.columns as column>
			<#if !column.pk>
			<p>
				<label>${column.columnAlias}：</label>
				<#if column.isDateTimeColumn>
				<s:textfield key="entity.${column.columnNameLower}" cssClass="{date:true,required:${(!column.nullable)?string},maxlength:${column.size}} input_text Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				<#elseif column.isNumberColumn>
				<s:textfield key="entity.${column.columnNameLower}" cssClass="{number:true,required:${(!column.nullable)?string},maxlength:${column.size}} input_text" />
				<#elseif (column.size>=256)>
				<s:textarea key="entity.${column.columnNameLower}" cssClass="{required:${(!column.nullable)?string},maxlength:${column.size}} input-textarea" />
				<#else>
				<s:textfield key="entity.${column.columnNameLower}" cssClass="{required:${(!column.nullable)?string},maxlength:${column.size}} input_text" />
				</#if>
				<b>*</b>
				<span>用户账号为3-16个字符，可以为数字、字母、下划线以及中文</span>
			</p>
			</#if>
			</#list>
		    <div class="toolbar">
			    <button type="submit">
			    	<img src="${'$'}{ctx}/res/icons/16x16/tick.png" />
			    	确定
			    </button>
				<button type="button" onclick="history.back();">
					<img src="${'$'}{ctx}/res/icons/16x16/arrow_redo.png" />
					返回
				</button>
		    </div>
		</s:form>
	</body>
</html>
