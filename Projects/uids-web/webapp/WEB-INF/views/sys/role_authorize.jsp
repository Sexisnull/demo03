<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<!--<link rel="stylesheet" href="../res/skin/default/plugin/jqtree/skin/default/jqtree.css" type="text/css"></link>
--><link rel="stylesheet" href="${ctx}/res/plugin/jqtree/skin/default/jqtree.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/res/plugin/jqtree/jquery.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/jqtree/jqtree.js"></script>
<!--<script type="text/javascript" src="../res/skin/default/plugin/jqtree/jquery.js"></script>
<script type="text/javascript" src="../res/skin/default/plugin/jqtree/jqtree.js"></script>
--><script type="text/javascript">
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
			url : '${ctx}/sys/roleAuthorizeSave',
			data : 'roleId=${roleId}&keys=' + tree.val("id") + '&types=' + tree.val("customType"),
			async : false,
			success : function(msg) {
				if (msg.indexOf('success') >= 0) {
					alert('授权成功');
					//location.href = '/sys/roleList';
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
			loadUrl : '${ctx}/sys/roleAuthorizeList?roleId=${roleId}', // 请求路径
			timeout : 10000, // 超时时间
			indent : 30, // 节点缩进距离
			treeType : 'checkbox' // 树类型 ['normal', 'checkbox', 'radio']，默认值normal
		});
		
		var api = frameElement.api, W = api.opener;
		api.button({
		    id:'valueOk',
		    name:'确定',
		    callback:function(){
			    accreditSubmit();//调用弹出窗口里的accreditSubmit函数提交子页面
			    api.close();
		    }
		});
		api.button({
		    id:'close',
		    name:'关闭',
		    callback:function(){
			    api.close();
		    }
		});
	});	
</script>
</head>
<body>
	<div id='accreditDiv'></div>
</body>
</html>