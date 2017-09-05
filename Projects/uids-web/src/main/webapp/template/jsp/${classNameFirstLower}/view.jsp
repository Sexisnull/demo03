<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="${basepackage}.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title><%=${className}.TABLE_ALIAS%>详情</title>
		<%@ include file="/include/meta.jsp"%>
	</head>
	
	<body>
		<ul class="tab">
			<li class="selected">
				<a>
					<img src="${'$'}{ctx}/res/icons/16x16/application_side_list.png" />
					${table.tableAlias}详细信息
				</a>
			</li>
		</ul>
		<form>
			<s:hidden key="entity.${table.idColumn.columnNameLower}" />
			<#list table.columns as column>
			<p>
				<label>${column.columnAlias}：</label>${'$'}{entity.${column.columnNameLower}}
			</p>
			</#list>
		    <div class="toolbar">
				<button type="button" onclick="history.back();">
					<img src="${'$'}{ctx}/res/icons/16x16/arrow_redo.png" />
					返回
				</button>
		    </div>
	    </form>
	</body>
</html>