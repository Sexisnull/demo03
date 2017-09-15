<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>

<!doctype html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="${ctx}/res/plugin/jqtree/skin/default/jqtree.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/res/plugin/jqtree/jquery.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/jqtree/jqtree.js"></script>
<script type="text/javascript">
	/*//授权树提交按钮添加事件处理
	function accreditSubmit() {
		$.ajax({
			type : "POST",
			url : '../sys/roleAuthorizeSave',
			data : 'roleId=${roleId}&keys=' + tree.val("id") + '&types=' + tree.val("customType"),
			async : false,
			success : function(msg) {
				if (msg.indexOf('success') >= 0) {
					alert('授权成功');
					location.href = '/sys/roleList';
				} else {
					alert('授权失败');
					location.href = '/sys/roleList';
				}
			}
		});
	}
	$(function() {
		alert("ddddsss");
		//展现授权树
		tree = $('#accreditDiv').jqTree({
			path : '../res/skin/default/plugin/jqtree/', // 相对于页面的js文件根路径
			loadUrl : '../sys/roleAuthorizeList?roleId=${roleId}', // 请求路径
			timeout : 10000, // 超时时间
			indent : 30, // 节点缩进距离
			treeType : 'checkbox' // 树类型 ['normal', 'checkbox', 'radio']，默认值normal
		});
	});*/
	
	//授权树提交按钮添加事件处理
	function accreditSubmit() {
		$.ajax({
			type : "POST",
			url : '${ctx}/complat/roleAuthorizeSave',
			data : 'roleId=${roleId}&keys=' + tree.val("id") + '&types=' + tree.val("customType"),
			async : false,
			success : function(msg) {
				if (msg.indexOf('success') >= 0) {
					alert('授权成功');
					location.href = '${ctx}/complat/croleList';
				} else {
					alert('授权失败');
					//location.href = '/sys/roleList';
				}
			}
		});
	}
	$(function() {
		//展现授权树
		tree = $('#accreditDiv').jqTree({
			path : '${ctx}/res/plugin/jqtree/', // 相对于页面的js文件根路径
			loadUrl : '${ctx}/complat/croleAuthorizeList?roleId=${roleId}', // 请求路径
			timeout : 10000, // 超时时间
			indent : 30, // 节点缩进距离
			treeType : 'checkbox', // 树类型 ['normal', 'checkbox', 'radio']，默认值normal
			autoOpen: false,
			slide: false
		});
		
	});	
</script>
<style>
.ui_buttons {
    white-space: nowrap;
    padding: 4px 0 4px 8px;
    height: 52px;
    line-height: 52px;
    text-align: right;
    background-color: #ffffff;
    border-top: solid 1px #e5e5e5;
}
.ui_buttons input::-moz-focus-inner {
    border: 0;
    padding: 0;
    margin: 0;
}

.ui_buttons input {
    padding: 4px 12px 4px 14px;
    padding: 6px 12px 6px 14px\0;
    *padding: 5px 12px 5px 12px;
    margin-left: 6px;
    cursor: pointer;
    display: inline-block;
    text-align: center;
    line-height: 1;
    height: 28px;
    letter-spacing: 3px;
    overflow: visible;
    color: #4cadf1;
    font-size: 14px;
    border: solid 1px #4cadf1;
    border-radius: 3px;
    border-radius: 0\9;
    background: #ffffff;
}

.ui_buttons input:hover {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
.ui_buttons input:active {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
</style>
</head>
<body>
	<div id='accreditDiv' style="height:300px;overflow:auto"></div>
	<div class="ui_buttons">
	<input type="button" value="确定" onclick="accreditSubmit();">
	<input type="button" value="关闭" onclick="javascript:window.location.href='${ctx}/complat/croleList'">
	</div>
</body>
</html>