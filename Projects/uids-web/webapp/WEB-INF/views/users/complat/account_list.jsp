<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

	<%@ include file="/include/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/uploadifyLang_zh.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/uploadify/css/uploadify.css">
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	
	<head>
		<meta charset="utf-8" />
		<title>甘肃万维JUP课题</title>
		<style>
/*设置弹出层样式*/
/*Popup*/
.Popup {
	width: 400px;
	height: 200px;
	position: absolute;
	left: 400px;
	top: 130px;;
	background-color: #1C70B8;
	border: 1px solid #ccc;
	z-index: 999;
	border-radius: 5px;
}

.Popup_top {
	height: 40px;
	background: #1C70B8;
	border-radius: 5px 5px 0 0;
}

.Popup_top h1 {
	float: left;
	margin-left: 10px;
	line-height: 40px;
	font-size: 15px;
	color: #ffffff;
}

.Popup_top h1 .close{
    margin-left:255px;
   color: #ffffff;
}

.Popup_top h1 .close:hover{
    cursor:pointer;
}

.Close {
	float: right;
	font-family: Arial, Helvetica, sans-serif;
	margin-right: 10px;
	margin-top: 10px;
}

.Popup_cen {
	height: 120px;
	overflow: auto;
	background: #FFF;
}

.overlay {
	position: fixed;
	z-index: 990;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	filter: alpha(opacity =       60);
	opacity: 0.6;
	overflow: hidden;
	background-color: #000;
}

.Popup_bottom {
	width: 100%;
	height: 40px;
	background: #1C70B8;
	border-radius: 0 0 5px 5px;
	line-height: 40px;
	overflow: hidden;
	clear: both;
	padding-top: 10px;
}

.Popup_bottom a {
	color: #fff;
	font-size: 15px;
}

.btnImport {
	float: right;
	right: 20px;
	position: relative;
}

#templet {
	margin-right: 35px;
}

.alert_tb {
	left: 180px;
	top: 120px;
	border: 1px solid #F68A8A;
	width: 640px;
	height: 150px;
	background-color: #e2ecf5;
	z-index: 1000;
	position: absolute;
}

.alert_tb p span .close {
	margin-left: 550px;
	font-weight: 500;
	cursor: pointer;
	font-size: 16px;
}

.userForm {
	height: 0px;
}

.input_one {
	line-height: 30px;
	background-color: #fbedce;
	height: 30px;
	font-size: 13px;
}

.input_one p .inputUser {
	margin-left: 5px;
}

.input_two {
	line-height: 90px;
	height: 90px;
}

.input_two ul li {
	text-align: center;
	display: inline;
	list-style-type: none;
}

.alert_tb p span {
	float: left;
}

.mybg {
	background-color: #000;
	width: 100%;
	height: 100%;
	position: absolute;
	top: 0;
	left: 0;
	zIndex: 500;
	opacity: 0.3;
	filter: Alpha(opacity =   30);
}
</style>
<script type="text/javascript">

function checkSubmitForm(){
		var nameSearch = $("#nameSearch").val();
		//alert(nameSearch);
		var loginnameSearch = $("#loginnameSearch").val();
		//alert(loginnameSearch);
		var loginallnameSearch = $("#loginallnameSearch").val();
		//alert(loginallnameSearch);
		if(nameSearch ==  '' || isNumbOrLett1(nameSearch)){
			if(loginnameSearch ==  '' || isNumbOrLett2(loginnameSearch)){
				if(loginallnameSearch ==  '' || isNumbOrLett3(loginallnameSearch)){
			       form1.submit();
		        }else{
			       $.validator.errorShow($("#loginallnameSearch"),'只能包括字母、数字、下划线、英文句号、中文，且不能超过255个字符');
		        }  
		    }else{
		    	$.validator.errorShow($("#loginnameSearch"),'只能包括字母、数字、下划线、中文，且不能超过255个字符');
		    }
		}else{
			$.validator.errorShow($("#nameSearch"),'只能包括字母、数字、中文，且不能超过255个字符');
		}
}
	
	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
			function isNumbOrLett1( s ){
				var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9\u4e00-\u9fa5]{1,255}$/;
				var re = new RegExp(regu);
				if (re.test(s)) {
					return true;
				}else{
					return false;
				}
			}
			
			function isNumbOrLett2( s ){
				var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]{1,255}$/;
				var re = new RegExp(regu);
				if (re.test(s)) {
					return true;
				}else{
					return false;
				}
			}
			
			function isNumbOrLett3( s ){
				var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_.\u4e00-\u9fa5]{1,255}$/;
				var re = new RegExp(regu);
				if (re.test(s)) {
					return true;
				}else{
					return false;
				}
			}
	

		$(function(){
		//高级搜索按钮点击事件
		$('#advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$("#advanced-search-btn").click(function(){
			$("#form2").submit();
		});
		//阻止按键盘Enter键提交表单
		var $inp = $('input');
		$inp.keypress(function (e) { 
		    var key = e.which; 
		    if (key == 13) {
		        return false;
		    }
		});
	});


/***新增***/
//function addComplatUser(){
     //window.location.href="${ctx}/complat/complatUserEdit";
//}
function toAdd(){
 var main = $('#main');
	console.log(main);
	parent.location = "${ctx}/complat/complatUserEdit";
}


function toEdit(iids){
	if(iids==null || iids==""){
		parent.location = "${ctx}/complat/complatUserEdit";
	}else{
		parent.location = "${ctx}/complat/complatUserEdit?iid="+iids;
	}
	
}

//删除
	function deleteData() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要删除吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/complat/complatUserDelete?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
			}
	}

/*导出*/	
function outPutComplatUser() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/complat/complatExport?iid="+ids.substring(0,ids.length-1);
			}else{
				$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
	
	
	
	/*批量启用*/
	function startData() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要开启吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/complat/startUserEnable?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
	



   /**批量停用操作**/	
	function stopData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要关闭吗？',function(){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/complat/stopUserEnable?iid="+ids.substring(0,ids.length-1);
			});
		}else{
			$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
		}
	}



/*});*/





/***用户导入***/
//弹出层
/*function intPutComplatUser(){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$(".Popup").show(); 				
}*/
	
	
function intPutComplatUser(){
	var api = $.dialog({
    title : '政府用户-用户导入',
	width : 450,
	height: 125,
	max : false,
	min : false,
	lock : true,
	padding : '40px 20px',
	content : 'url:${ctx}/complat/showInport',
	fixed : true,
	drag : false,
	resize : false
	});
	

		//window.location.href="${ctx}/complat/complatList";

}
 
//关闭的单击事件
$(".close").click(function(){	
	  $(".Popup").css("display","none");
	  $(".mybg").css("display","none");
	window.location.reload();//刷新页面，清除缓存
});




/*********************机构树开始************************/
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
	
	$('#groupid').val(treeNode.id);
	$('#groupname1').val(treeNode.name);
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
	
	
	$('#groupid').val(treeNode.id);
	$('#groupname1').val(treeNode.name);
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
/*********************机构树结束************************/
</script>
		<%--<script type="text/javascript" src="${ctx}/res/js/region/orgTree.js"></script>--%>
	</head>
	<body>
        <!-- 提示信息开始 -->
		<div class="form-alert;">
			<tags:message msgMap="${msgMap}"></tags:message>
		</div>
		<!-- 提示信息结束 -->
		<div class="list-warper">
			<!--左侧树形结构-->
			<%--<div id="tablelist" style="width:20%;float:left;min-width:0px;">--%>
				<%--<table class="tablelist" >--%>
					<%--<tbody>--%>
					<%--<tr>--%>
						<%--<td>--%>
							<%--<div class="zTreeDemoBackground left" style="overflow:scroll;">--%>
								<%--<ul id="treeDemo" class="ztree" style=" display: block;"></ul>--%>
							<%--</div>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<%--</tbody>--%>
				<%--</table>--%>
			<%--</div>--%>
			<!--左侧树形结构-->
			<div style="width:100%;float:left;">
				<div class="search-content">
					<form id="form1" name="form2" action="${ctx}/complat/complatList"
						  method="get">
						<table class="advanced-content">
							<tr>
								<!--  <th style="padding-left: 5px">
									所属机构：
								</th>
								<td>
									<input name="groupname" id="groupname" value="${groupName }" readonly="true" type="text" style="cursor: pointer;width: 150px;" placeholder="所属机构"/>
									<input type="hidden" id="groupid" name="search_EQ_groupid" value="${groupid}">
									<input type="hidden" id="orgId" name="orgId" value="${orgId}">
								</td>-->
								<th style="padding-left: 5px">
									姓名：
								</th>
								<td>
									<input type="text" placeholder="姓名" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
									<input type="hidden" id="orgId" name="orgId" value="${orgId}">
								</td>
								<th style="padding-left: 5px">
									登录名：
								</th>
								<td>
									<input type="text" placeholder="登录名" value="${sParams['LIKE_loginname']}" id="loginnameSearch" name="search_LIKE_loginname" />
								</td>
								<th style="padding-left: 5px">
									登录名全称：
								</th>
								<td>
									<input type="text" placeholder="登录名全称" value="${sParams['LIKE_loginallname']}" id="loginallnameSearch" name="search_LIKE_loginallname" />
								</td>
								<td class="btn-group">
									<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				
				
				
				<!--列表内容区域-->
				<div >
					<input type="hidden" id="orderField" name="orderField"
						   value="${orderField}" />
					<input type="hidden" id="orderSort" name="orderSort"
						   value="${orderSort}" />
					<div class="list-topBar  advanced-search">
						<div class="list-toolbar">
							<!--  操作按钮开始 	    -->
							<ul class="list-Topbtn">
								<li class="add"><a title="新增" onclick="toAdd()">新增</a></li>
								<li class="del"><a title="删除" onclick="deleteData('complat/complatUserDelete','iid');">删除</a></li>
								<li class="query"><a title="导入" onclick="intPutComplatUser( ${ complatUser.iid});">导入</a></li>
								<li class="exportData"><a title="导出" onclick="outPutComplatUser()">导出</a></li>
								<li class="startData"><a title="开启" onclick="startData('complat/startUserEnable','iid');">开启</a></li>
								<li class="edit"><a title="关闭" onclick="stopData('complat/stopUserEnable','complatUserId');">关闭</a></li>
							</ul>
							<!--   操作按钮结束  -->
						</div>
						
						<!-- 列表开始 -->
						<table cellpadding="0" cellspacing="0" border="0" width="80%"
							   id="list-table" class="list-table">
							<thead>
							<tr>
								<th width="1%">
									<div class="label">
										<i class="check_btn check_all"></i>
										<input id="${ComplatUser.iid}" value="${ComplatUser.iid}"
											   type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</th>
								<th width="10%" style="text-align: center;">
									姓名
								</th>
								<th width="10%" style="text-align: center;">
									登录名
								</th>
								<th width="15%" style="text-align: center;">
									登录全名
								</th>
								<th width="10%" style="text-align: center;">
									所属机构
								</th>
								<th width="10%" class="alignL" style="text-align: center;">
									用户职务
								</th>
								<th width="10%" style="text-align: center;">
									办公电话
								</th>
								<th width="5%" style="text-align: center;">
									账号开启
								</th>
								<!--
                <th width="18%" class="alignL" style="text-align: center;">注册时间</th>-->
								<th width="10%" style="text-align: center;">
									操作
								</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${pageInfo.content}" var="complatUser"
									   varStatus="state">
								<tr class="myclass">
									<td>
										<div class="label">
											<i class="check_btn"></i>
											<input id="${complatUser.iid}" value="${complatUser.iid}"
												   type="checkbox" class="check_btn" style="display: none;" />
										</div>
									</td>
									<td style="text-align: center;">
										<div title="${complatUser.name}" class="word_break">
												${complatUser.name}
										</div>
									</td>
									<td style="text-align: center;">
										<div title="${complatUser.loginname}" class="word_break">
												${complatUser.loginname}
										</div>
									</td>
									<td style="text-align: center;">
										<div title="${complatUser.loginallname}" class="word_break">
												${complatUser.loginallname}
										</div>
									</td>
									<td style="text-align: center;">
										<div class="word_break">
												${groupMap[complatUser.groupid]}
										</div>
									</td>
									<td style="text-align: center;">
										<div class="word_break">
												${complatUser.headship}
										</div>
									</td>
									<td style="text-align: center;">
										<div title="${complatUser.phone}" class="word_break">
												${complatUser.phone}
										</div>
									</td>
									<td style="text-align: center;">
										<div class="alignL">
											<div class="list-longtext">
												<c:if test="${complatUser.enable == '0'}">
													<font color="red">关闭</font>
												</c:if>
												<c:if test="${complatUser.enable == '1'}">
													<font color="#32CD32">开启</font>
												</c:if>
											</div>
										</div>

										<!-- <td style="text-align: center;">
	                        <div title="${complatUser.createtime}" class="word_break">${complatUser.createtime}</div>
	                    </td> -->
									<td  class="listOper">
										<!--<gsww:opTag menuId="8a929c9e5e5fbde5015e5fdb125c0002" tabIndex="1" operatorType="2"></gsww:opTag>-->									   
									     	   <ul>
									     	       <li class="blue"><a title="编辑" onclick="toEdit(${complatUser.iid});">编辑</a></li>											     	
									         </ul>									     										     									    
									</td>
								</tr>
							</c:forEach>

							</tbody>
						</table>
					</div>
					<!-- 列表结束 -->
				</div>
				<!-- 分页 -->
				<tags:pagination page="${pageInfo}" paginationSize="5" />
			</div>

			</div>
		</div>
	</body>
	
</html>
