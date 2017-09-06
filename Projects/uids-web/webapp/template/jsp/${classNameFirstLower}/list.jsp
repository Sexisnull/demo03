<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="${basepackage}.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	    <title><%=${className}.TABLE_ALIAS%>管理</title>
		<%@ include file="/include/meta.jsp"%>
		<script>
			${'$'}(function() {
				//转换列表样式
				${'$'}(".grid").tablesorter();
				// 查询对话框
				${'$'}( "#dialog-form" ).dialog({ height: 350, width: 450, autoOpen: false });
				// 操作完成提示
				highlight(<s:actionmessage/>);
			});
		</script>
	</head>

	<body>
		<!-- 查询弹出框 -->
		<div id="dialog-form" title="查询">
			<s:form action="/${classNameLower}.do">
				<#list table.columns as column>
				<p>
					<label>${column.columnAlias}：</label>
					<#if column.isDateTimeColumn>
					<s:textfield key="filter_EQD_${column.columnNameLower}" cssClass="input_text Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<#elseif column.isNumberColumn>
					<s:textfield key="filter_EQI_${column.columnNameLower}" cssClass="input_text" />
					<#else>
					<s:textfield key="filter_LIKES_${column.columnNameLower}" cssClass="input_text" />
					</#if>
				</p>
				</#list>
			    <div class="toolbar">
				    <button type="submit">
				    	<img src="${'$'}{ctx}/res/icons/16x16/tick.png" />
				    	确定
				    </button>
					<button type="button" onclick="${'$'}('#dialog-form').dialog('close');">
						<img src="${'$'}{ctx}/res/icons/16x16/arrow_redo.png" />
						取消
					</button>
			    </div>
			</s:form>
		</div>
		<!-- 按钮操作 -->
		<div class="toolbar">
			<button onclick="location.href='${'$'}{ctx}/${classNameLower}!input.do'">
				<img src="${'$'}{ctx}/res/icons/16x16/application_form_add.png" />
				新建
			</button>
			<button onclick="doSingle('${'$'}{ctx}/${classNameLower}!input.do')">
				<img src="${'$'}{ctx}/res/icons/16x16/application_form_edit.png" />
				编辑
			</button>
			<button onclick="doBatch('${'$'}{ctx}/${classNameLower}!delete.do', '确定要删除选中的项吗？');">
				<img src="${'$'}{ctx}/res/icons/16x16/application_form_delete.png" />
				删除
			</button>
			<button onclick="${'$'}('#dialog-form').dialog('open');">
				<img src="${'$'}{ctx}/res/icons/16x16/application_form_magnify.png" />
				搜索
			</button>
		</div>
		<!-- 列表区 -->
		<ul class="tab">
			<li class="selected">
				<a>
					<img src="${'$'}{ctx}/res/icons/16x16/application_side_list.png" />
					${table.tableAlias}
				</a>
			</li>
		</ul>
		<table class="grid">
			<thead>
				<tr>
					<th width="30px"><input type="checkbox" name="chkAll" title="全选/取消" onclick="selectCheckbox(this)" /></th>
					<#list table.columns as column>
					<th orderBy="${column.columnNameLower}"><span>${column.columnAlias}</span></th>
					</#list>
					<th width="50px"><span>编辑</span></th>
					<th width="50px"><span>详情</span></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${'$'}{pager.result}" var="item" varStatus="status"> 
				<tr value="${'$'}{item.${table.idColumn.columnNameLower}}" index="${'$'}{status.index}">
					<td align="center"><input type="checkbox" /></td>
					<#list table.columns as column>
					<#if column.isStringColumn>
					<td><span class="substr" style="width: 100px;">${'$'}{item.${column.columnNameLower}}</span></td>
					<#else>
					<td><span>${'$'}{item.${column.columnNameLower}}</span></td>
					</#if>
					</#list>
					<td align="center"><a href="${'$'}{ctx}/${classNameLower}!input.do?id=${'$'}{item.${table.idColumn.columnNameLower}}"><span>编辑</span></a></td>
					<td align="center"><a href="${'$'}{ctx}/${classNameLower}!view.do?id=${'$'}{item.${table.idColumn.columnNameLower}}"><span>详情</span></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<gsww:simplePageView url="/${classNameLower}.do"/>
	</body>
</html>