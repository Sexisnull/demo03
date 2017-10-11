<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx }/res/skin/login/js/login.js"></script>
<title>主页权限</title>
<script type="text/javascript">
	$(function(){
		alert("你没有权限访问该模块！");
		$.get("/uids-web/login/loginOut");
		window.location.href="/uids-web";
	})
</script>
</head>
<body>	
	
</body>
</html>