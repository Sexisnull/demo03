<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>

<script type="text/javascript">


$(function(){
	var groupMenu = [{"name":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];

	$('#groupname').menu({
		tree : 'groupmenu',
		height : 200,
		init : function() {
			setting('groupmenu', onClickGroup, onDbClickGroup, groupMenu);
		}
	});
});
function hideGroupMenu(){
	$('#groupname_menu').css('display','none');
}
function onClickGroup(event, treeId, treeNode) {
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	hideGroupMenu();
}
function onDbClickGroup(event, treeId, treeNode) {
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled )//根节点及失效节点双击无效
		return;
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	$('#groupname_menu').fadeOut(50);
}

/**
 *	初始化树
 */
function setting(treeName, onClickFunction, onDblClickFunction, rootNode) {
	var setting = {
		async : {
			enable : true,
			url : '../login/getGroup',
			autoParam : [ "id=groupId", "isDisabled" ]
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClickFunction,
			onDblClick : onDblClickFunction
		}
	};
	$("#" + treeName).tree(setting, rootNode);
//	$("#" + treeName).tree().refreshNode('');
}
/**
 *	机构选择节点点击前回调
 */
function beforeClick(treeId, treeNode, clickFlag) {
	if (treeNode.isDisabled)
		return false;
	return (treeNode.id != 0);
}
function resetform() {
	$('form').find(':input').not(':button,:hidden,:submit,:reset').val('');
}
</script>
</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >机构管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty complatGroup.iid}">用户新增</c:if><c:if test="${not empty complatGroup.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
	<%-- <div class="form-title"><c:if test="${empty sysAccount.userAcctId}">用户新增</c:if><c:if test="${not empty sysAccount.userAcctId}">用户编辑</c:if></div> --%>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/uids/complatgroupSave">
    
    <div style="display:none;">
          <input type="hidden" id="iid" name="iid" value="${complatGroup.iid}"/>
<%--           <input type="hidden" id="codeid" name="codeid" value="${complatGroup.codeid}"/>
          <input type="hidden" id="pid" name="pid" value="${complatGroup.pid}"/>
          <input type="hidden" id="orderid" name="orderid" value="${complatGroup.orderid}"/>
          <input type="hidden" id="pinyin" name="pinyin" value="${complatGroup.pinyin}"/>
          <input type="hidden" id="iscombine" name="iscombine" value="${complatGroup.iscombine}"/>
          <input type="hidden" id="opersign" name="opersign" value="${complatGroup.opersign}"/>
          <input type="hidden" id="createtime" name="createtime" value="${complatGroup.createtime}"/>
          <input type="hidden" id="modifytime" name="modifytime" value="${complatGroup.modifytime}"/>
          <input type="hidden" id="synState" name="synState" value="${complatGroup.synState}"/> --%>
          <input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		  <input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
    		<!--  <ul class="form-table"> -->
	        	<!--,nameCheck: true,isUnique:true,cnRangelength:[1,64] -->
	        	 <th><b class="mustbe">*</b> 请输入机构名称：</th>
	        	 <td>
					<input type="text" placeholder="请填写机构简称，例如：省发改委" id="name" name="name" value="${complatGroup.name}" />
				</td>
				<th><b class="mustbe">*</b> 请输入机构全名：</th>
				<td>
					<input type="text" placeholder="请填写机构全称，例如：甘肃省发展和改革委员会" id="groupallname" name="groupallname" value="${complatGroup.groupallname}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 请选择节点类型：</th>
                <td>
					<select name="nodetype" id="nodetype" class="input-select" >
						<option value="">---请选择---</option>
						<c:forEach var="nodetype" items="${nodetypeMap}">
							<option value="${nodetype.key}"<c:if test="${complatGroup.nodetype==nodetype.key}">selected</c:if>>${nodetype.value}</option>
		                </c:forEach>
					</select>
				</td>
				<th><b class="mustbe">*</b> 请选择区域类型：</th>
				<td>
					<select name="areatype" id="areatype" class="input-select" >
						<option value="">---请选择---</option>
						<c:forEach var="areatype" items="${areatypeMap}">
							<option value="${areatype.key}"<c:if test="${complatGroup.areatype==areatype.key}">selected</c:if>>${areatype.value}</option>
		                </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 请输入区域编码：</th>
				<td>
					<input type="text" placeholder="请输入区域编码" class="input" name="areacode" value="${complatGroup.areacode}" />
				</td>
	        	<th><b class="mustbe">*</b> 请输入机构后缀：</th>
	        	<td>
					<input type="text" placeholder="请填写最简洁的机构缩写，例如：fwg（发改委）" class="input" name="suffix" value="${complatGroup.suffix}" />
				</td>
			</tr>
			<tr>
				<th>请输入组织机构代码：</th>
				<td>
					<input type="text" placeholder="请填写标准的九位机构代码" class="input" name="orgcode" value="${complatGroup.orgcode}" />
	            </td>
	        	<th>请输入上级机构：</th>
	        	<td>
	        		<input name="groupname" id="groupname" type="text" style="cursor: pointer;" value="${complatGroup.parentName}"/> 
	        	</td>
			</tr>
			<tr>
				<th>请输入机构描述：</th>
				<td>
    			<textarea class="textarea" name="spec" style="width: 89.1%;">${complatGroup.spec}</textarea>
    			</td>
			</tr>
			</table>
	        
	        <!-- </ul> -->
        
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/uids/complatgroupList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
    </div>
    </form>
    <!--表单的底部区域-->
    <div class="form-footer"></div>
    <!--表单的预览或提示区域-->
    <div class="form-preview"></div>
    <!--表单的提示区域-->
    <div class="form-tip"></div>
    <!--表单的弹出层区域-->
    <div class="form-dialog"></div>
    
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	
</div>
</div>
</body>
</html>
