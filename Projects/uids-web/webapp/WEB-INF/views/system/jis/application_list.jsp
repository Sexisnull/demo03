<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
<style type="text/css">
.mybg{
	background-color:#000;
	width:100%;
	height:100%;
	position:absolute;
	top:0; 
	left:0; 
	zIndex:500; 
	opacity:0.3; 
	filter:Alpha(opacity=30); 
}
.alert_tb {	
	left:300px;
	top:30px;
	border:1px solid #F68A8A;
	width:380px;
	height:480px;
	background-color:#e2ecf5;
	z-index:1000;
	position:absolute;
} 
.synbtn{
	border-radius:4px;
	font-size: 16px;
	text-align:center;
	width:60px;
	heigth:40px;
}
</style>
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
	var groupMenu2 = [{"name":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];
	$('#groupname2').menu({
		tree : 'groupmenu2',
		height : 250,
		init : function() {
			setting2('groupmenu2', onClickGroup2, onDbClickGroup, groupMenu2);
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
	console.log("-----"+treeName);
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
//第二个树
function hideGroupMenu2(){
	$('#groupname2_menu').css('display','none');
}
function onClickGroup2(event, treeId, treeNode) {
	$('#groupid2').val(treeNode.id);
	$('#groupname2').val(treeNode.name);
	hideGroupMenu2();
}
function onDbClickGroup2(event, treeId, treeNode) {
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled )//根节点及失效节点双击无效
		return;
	$('#groupid2').val(treeNode.id);
	$('#groupname2').val(treeNode.name);
	$('#groupname2_menu').fadeOut(50);
}
/**
 *	初始化树
 */
function setting2(treeName2, onClickFunction2, onDblClickFunction2, rootNode) {
	var setting2 = {
		async : {
			enable : true,
			url : '../login/getGroup',
			autoParam : [ "id=groupId", "isDisabled" ]
		},
		callback : {
			beforeClick : beforeClick2,
			onClick : onClickFunction2,
			onDblClick : onDblClickFunction2
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType:{ "Y": "ps", "N": "ps" }
		} 
	};
	console.log("-----"+treeName2);
	$("#" + treeName2).tree(setting2, rootNode);
//	$("#" + treeName).tree().refreshNode('');
}
/**
 *	机构选择节点点击前回调
 */
function beforeClick2(treeId, treeNode, clickFlag) {
	if (treeNode.isDisabled)
		return false;
	return (treeNode.id != 0);
}
function resetform2() {
	$('form').find(':input').not(':button,:hidden,:submit,:reset').val('');
} 
//搜索校验
function checkSubmitForm(){
	var nameSearch = $("#nameSearch").val();
	if(nameSearch ==  '' || isNumbOrLett(nameSearch)){
	}else{
		$.validator.errorShow($("#nameSearch"),'只能包括数字、字母、下划线或中文');
		return;
	}
	var groupname = $("#groupname").val();
	if(groupname ==  '' ){
	}else{
		/* $.validator.errorShow($("#groupname"),'只能包括数字和字母'); */
	}
	$("#form1").submit();
}
/*
用途：检查输入字符串是否只由汉字、字母、数字组成
输入：
value：字符串
返回：
如果通过验证返回true,否则返回false
*/
function isNumbOrLett( s ){//判断是否是字母、数字组成
	var regu = /^(\w|[\u4E00-\u9FA5])*$/;/* /^([a-zA-Z0-9]+)$/ */
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	}else{
		return false;
	}
}
	
function syngroup(iid){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#syngroupdiv").show(); 	
	$("#syniid").val(iid);			
}
	
</script>
</head>
<body>
<div class="list-warper">
	<!--列表的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >应用管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				应用列表
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/application/applicationList" method="get">
			<table class="">
				<tr>
					<th style="padding-left: 300px">应用名称：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="应用名称" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
						</td>
					<th>所属机构：</th>
						 <td>
						 	<input id="groupname" value="${groupName}" name="groupname" type="text" style="cursor: pointer;" placeholder="所属机构"/> 
							<input type="hidden" id="groupid" value="${sParams['EQ_groupId']}" name="search_EQ_groupId">
							<%-- <select  style="width: 170px;" placeholder="数据标识" value="${sParams['LIKE_remark']}" id="remarkSearch" name="search_LIKE_remark" /> --%>
						</td> 
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!--列表内容区域-->
	<div class="list">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
	<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
		
        <div class="list-topBar  advanced-search">
        	 <div class="list-toolbar">
            <!-- 操作按钮开始 -->	 
             <gsww:opTag menuId="8a929cb35e7a893b015e7a925b900001" tabIndex="1" operatorType="1"></gsww:opTag>
             <!-- 操作按钮结束 -->
           </div> 
            
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/application/applicationList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>名称:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li class="advanced-search-btn">搜索</li>
        </ul>
        </form>
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
    	<!-- 列表开始 -->
        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
        	<thead>
            	<tr>
                	<th width="10px">   
                		<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>             		
                	</th>
                    <th width="25%" style="text-align: center;">
                                                             应用名称        
                    </th>
                    <th width="20%" style="text-align: center;">应用标识</th>
                    <th width="20%" style="text-align: center;">所属机构</th>
                    <th width="15%" style="text-align: center;">同步用户</th>
                    <th width="30%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="application" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${application.iid}" value="${application.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                    <%-- <td style="text-align: center;">
	                    	<div title="${application.name}" class="word_break">${application.name}</div>
	                    </td> --%>
	                	<td style="text-align: center;">
	                    	<div title="${application.name}" class="word_break">${application.name}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${application.mark}" class="word_break">${application.mark}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="word_break">${groupMap[application.groupId]}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<c:if test="${application.isSyncGroup==0}">不支持</c:if>
							<c:if test="${application.isSyncGroup==1}">支持</c:if>
	                    </td>
	                	<td class="position-content" style="text-align: center;" >
	                		<div class="listOper">
								<ul>
									<li class="blue" onclick="add('application/applicationEdit','iid',this);">
										<!-- <i></i> -->
										<a>编辑</a>
									</li>
									<li class="red" onclick="deleteSingle('application/applicationDelete','iid',this);">
										<!-- <i></i> -->
										<a>删除</a>
									</li>
									<li class="bluegreen" onclick="syngroup(${application.iid});">
										<!-- <i></i> -->
										<a>同步机构</a>
									</li>
									<li class="bluegreen" onclick="synuser();">
										<!-- <i></i> -->
										<a>同步用户</a>
									</li>
								</ul>
							</div>
	                        <%-- <gsww:opTag menuId="8a929cb35e7a893b015e7a925b900001" tabIndex="1" operatorType="2"></gsww:opTag> --%>
	                    </td>
	                </tr>
				</c:forEach>
                
            </tbody>       
        </table>
        <!-- 列表结束 -->
    </div>
    <div id="syngroupdiv" class="alert_tb" style="display:none;">
    	<form action="${ctx}/application/syngroup">
    	
    		<input id="syniid" type="hidden" name ="syniid"/>
    		<div style="font-size: 20px;padding-top: 10px;">&nbsp;&nbsp;&nbsp;同步机构<br/><hr/><br/></div>
    		<div style="text-align: left;padding-top:35px;font-size: 16px;height: 300px;padding-left: 25px">
    			选择机构：<input name="groupname2" id="groupname2" type="text" style="cursor: pointer;width: 230px"/>
   				<input type="hidden" id="groupid2" name="groupid2" value=""/>
   			</div>
		<div id="synsubmit" style="text-align: right;padding-right: 30px;">
			<div id="dialogpic-toolbar-panel">
				<input type="submit" class="synbtn" value="同步"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="synbtn" value="取消" 
					 onclick="javascript:window.location.href='${ctx}/application/applicationList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" />
			</div>
		</div>
    	</form>
    </div>
    <!-- 分页 -->
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>
</body>
</html>
