<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="gsww" uri="http://www.wanwei.com.cn/tags/gsww"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<!-- css -->
<link href="${ctx}/res/skin/${theme }/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctx}/res/skin/${theme }/css/elements.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/skin/default/css/showLoading.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/res/plugin/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<!--JS-->
<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
<!-- Jquery缓动类 -->
<script type="text/javascript" src="${ctx}/res/plugin/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/jquery.poshytip/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/jquery.showLoading.min.js"></script>

<%--<script type="text/javascript" src="${ctx}/res/plugin/zdialog/zDrag.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/res/plugin/zdialog/zDialog.js"></script>--%>

<script type="text/javascript" src="${ctx}/res/plugin/validate/jquery.metadata.js" ></script>
<script type="text/javascript" src="${ctx}/res/plugin/validate/jquery.validate.js" ></script>
<script type="text/javascript" src="${ctx}/res/plugin/validate/jquery.validate.extension.js" ></script>

<%--<script type="text/javascript" src="${ctx}/res/plugin/jplaceholder/jquery.jplaceholder.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>--%>
<!-- 页面中使用的公共JS类 -->
<script type="text/javascript" src="${ctx}/res/skin/${theme }/js/public.js"></script>
<%--<script type="text/javascript" src="${ctx}/res/plugin/kindeditor/kindeditor-4.1.9/kindeditor.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/res/plugin/kindeditor/kindeditor-4.1.9/lang/zh_CN.js"></script>--%>
