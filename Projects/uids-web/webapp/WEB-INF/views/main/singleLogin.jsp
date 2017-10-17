<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
	<head>

		<c:set var="ctx" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="-1" />
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>统一身份认证系统</title>
		<script>
	$(function(){
		$("#editForm").submit();
		
	})
</script>


	</head>
	<body>
		<form id="editForm" action="${url }" method="post" name="frm">
			<input type="hidden" name="loginuser" value="${name }"/>
			<input type="hidden" name="loginpass" value="${pwd}"/>
			<input type="hidden" name="appname" value="${appMark }"/>
			<input type="hidden" name="groupcode" value="${groupCode }"/>
			<input type="hidden" name="loginallname" value="${loginAllName }"/>
		</form>

	</body>
</html>
