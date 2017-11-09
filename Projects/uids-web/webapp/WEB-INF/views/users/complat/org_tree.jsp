<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

	<%@ include file="/include/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	
	<head>
		<meta charset="utf-8" />
		<title>甘肃万维JUP课题</title>
		<script>
			var pageUrl = "complatList";
		</script>
		<script type="text/javascript" src="${ctx}/res/js/region/orgTree.js"></script>
	</head>
	<body>
		<input type="hidden" id="groupid" name="groupid" value="${groupid}">
		<input type="hidden" id="orgId" name="orgId" value="${orgId}">
		<div class="ui-layout-north">
			<!--列表的面包屑区域-->
			<div class="position">
				<ol class="breadcrumb">
					<li>
						首页
					</li>
					<li class="split"></li>
					<li class="active">
						政府用户
					</li>
					<li class="split"></li>
					<li class="active">
						用户列表
					</li>
				</ol>
			</div>

			<!--左侧树形结构-->
			<div id="tablelist" style="width:20%;float:left;min-width:0px;">
				<table class="tablelist" >
					<tbody>
					<tr>
						<td>
							<div class="zTreeDemoBackground left" style="overflow:scroll;">
								<ul id="treeDemo" class="ztree" style=" display: block;"></ul>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
			<!--左侧树形结构-->
			<div style="width:78%; height:600px; float:left;">
				<iframe id="main1" src="${ctx}/complat/complatList?editGroupId=${editGroupId1}" class="ui-layout-center" frameborder="0" height="100%" width="100%" name="iframe" scrolling="yes">

				</iframe>

			</div>
		</div>
	</body>
	
</html>
