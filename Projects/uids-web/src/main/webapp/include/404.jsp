<!doctype html>
<html style="*overflow-y:hidden;">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/meta.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>404</title>
<!-- 初始化样式 -->
<link href="${ctx}/res/skin/default/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctx}/res/skin/default/css/index.css" rel="stylesheet" type="text/css">
<style>
	body,html { background-color: #eeeeee;}
</style>
</head>
<body >
<!--页面主体区域-->
<div class="erro-warper">
	<div class="top-404"></div>
    <div class="content-404" style="overflow:hidden;">
    	<div class="erro-infor">
        	<p>您所查找的页面不存在</p>
            
        </div>
    </div>
    <div class="footer-404">
    	CopyRight©1999-2011 甘肃万维信息技术有限责任公司 | 技术支持：WWUI课题组
    </div>
</div>
</body>
</html>
