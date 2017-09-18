<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色成员设置</title>
<h:head pagetype="dialog" fonticon="true"></h:head>
<h:import type="js" path="/ui/widgets/hotkeys/jquery.hotkeys-0.7.9.min.js"></h:import>
<h:import type="js" path="/resources/complat/role/script/members_opr.js"></h:import>
<style>
.batchselect li {
	height: 26px;
	line-height: 26px;
	padding: 0 10px;
	cursor: default;
	display: block;
}

.batchselect .hover {
	background-color: #E6F2FF;
}

.batchselect .selected,.batchselect li.user.selected,.batchselect li.group.selected
	{
	background-color: #4488BB;
	color: #FFF;
	color: #FFF !important;
}

.ic {
	color: #CCC;
}

.batchselect li.user {
	color: #226699;
}

.batchselect li.group {
	color: #F88E32;
}

.batchselect i {
	margin-right: 5px;
}
</style>
</head>
<body>
	<input type="hidden" name="iid" id="iid" value="${iid }" /> 
	<div id="dialog-content" style="padding: 20px 20px 0 20px; overflow: hidden;">
		<a class="btn btn-primary btn-small" style="margin-right:5px;" onclick="openOrgselect();">
			<i class="icon-plus-sign"></i> 添加
		</a>
		<a class="btn btn-primary btn-small" onclick="removeMembers();">
			<i class="icon-minus-sign"></i>删除
		</a>
		<div id="memberslist" style="width: 100%; height: 300px; border: 1px solid #CCC; margin-top: 10px; overflow-y: auto; overflow-x: hidden;">
			<ul class="batchselect" style="width: 100%;">
				<c:forEach items="${groupList }" var="group">
					<c:if test="${group.iid > 0 }">
						<li class="group" id="${group.iid}">
							<i class="icon-sitemap"></i>${group.name }
							<span class="ic">&lt;${group.codeId }&gt;</span>
						</li>
					</c:if>
				</c:forEach>
				<c:forEach items="${userList }" var="user">
					<c:if test="${user.iid > 0 }">
						<li class="user" id="${user.iid }">
							<i class="icon-user"></i>${user.name }
							<span class="ic">&lt;${user.loginName }&gt;</span>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div id="dialog-toolbar">
		<div id="dialog-toolbar-panel">
			<input type="submit" class="btn btn-primary" value="保存" onclick="save();" />
			<input type="button" class="btn" value="取消" onclick="closeDialog();" />
		</div>
	</div>
</body>
</html>