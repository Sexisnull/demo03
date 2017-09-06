<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>甘肃万维java统一开发平台</title>
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		
					<script>
			$(document).ready(function() {
				alert('对不起，由于您长时间没有操作，请重新登录！');
				parent.parent.location.href='login.jsp';
			});
		</script>
	</head>

</html>
