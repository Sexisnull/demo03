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

 .menuContent{border: 0px solid #ddd;overflow-x:auto;overflow-y:auto;float: left;}
	.menuContent #areaTree{ width:1500px;display: block;height:330px;float: left;overflow-x:auto;overflow-y:auto;}

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
	top:18px;
	border-radius:15px;
	border:1px solid #F68A8A;
	width:400px;
	height:520px;
	background-color:white;
	z-index:1000;
	position:absolute;
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
	
function syngroup(iid,state){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#syngroupdiv").show();
	$("#state").val(state);
	$("#syniid").val(iid);			
}

function synfuction(){
   	if($("#state").val()==0){
   		$("#synaction").attr("action","${ctx}/application/syngroup");
   	}
   	if($("#state").val()==1){
   		$("#synaction").attr("action","${ctx}/application/synuser");
   	}
   	$("#synaction").submit();
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
							<input type="text" maxlength="30" style="width: 170px;" placeholder="应用名称" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
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
	                    	<c:if test="${application.loginType=='1' && application.isSyncGroup=='1'}">不支持</c:if>
							<c:if test="${(application.loginType=='0' && application.isSyncGroup=='1')
							 || (application.loginType=='0' && application.isSyncGroup=='0')}">支持</c:if>
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
									<li class="bluegreen" onclick="syngroup(${application.iid},0);">
										<!-- <i></i> -->
										<a>同步机构</a>
									</li>
									<li class="bluegreen" onclick="syngroup(${application.iid},1);">
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
    	<form action="" id="synaction">
	    	<input type="hidden" id="state" name="state" value=""/>
	    	
	    		<input id="syniid" type="hidden" name ="syniid"/>
	    		<div style="font-size: 20px;padding-top:15px;padding-bottom:10px;cursor: auto;padding-left:5px;">
	    		&nbsp;&nbsp;&nbsp;同步列表
	    		<a href="${ctx}/application/applicationList?findNowPage
						 =true&orderField=${orderField}&orderSort=${orderSort}" title="关闭" 
					 style="padding-left:260px;line-height:25px;font-size:24px;color: black;text-decoration:none">x</a>
	    		<hr/><br/></div>
	    		<div style="text-align: left;padding-top:5px;font-size: 16px;height: 300px;padding-left:42px">
	    			<div style="padding-left: 6px;padding-bottom:10px; font-size: 16px;">选择机构：</div>
	    			<div id="menuContent" class="menuContent">
						<ul id="areaTree" class="ztree" style="margin-top:0; width:300px;"></ul>
					</div>
	   				<input type="hidden" id="groupid2" name="groupid2" value=""/>
	   			</div>
			<div id="synsubmit" style="text-align: right;padding-right: 10px;padding-top: 30px;">
				
					<input type="button" class="btn bluegreen" value="同步" onclick="synfuction()" 
					 style="background-color: #36c6d3;color:#ffffff;font-size: 15px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%-- <input type="button" class="synbtn" value="取消" 
						 onclick="javascript:window.location.href=
						 '${ctx}/application/applicationList?findNowPage
						 =true&orderField=${orderField}&orderSort=${orderSort}'" /> --%>
			</div>
		</form>
    </div>
    <!-- 分页 -->
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>
</body>

<script type="text/javascript">
$(function(){
var zNodes = [];
		var setting = {
			async : {
				enable : true,
				type:"post",
				url:"${ctx}/login/getGroup",
		        autoParam: ["id=groupId", "isDisabled"],
		        otherParam: { "type": "1"}
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType:{ "Y": "s", "N": "s" }
		},
			callback : {
				 onClick : function(event, treeId, treeNode){
					var zTree = $.fn.zTree.getZTreeObj("areaTree");
					var nodes = zTree.getSelectedNodes();
					nodes.sort(function compare(a, b) {
						return a.id - b.id;
					});
					var _name=$.trim(nodes[0].name);
					var _code=nodes[0].id;
					var param="";
					 $.ajax({
					       type:"get",
					       url:  "${ctx}/complat/complatList",
					       data:{"search_EQ_groupid":_code},
					       datatype:  "json",
					       success: function (value) {                                                                   
	                           var   data=JSON.parse(value);                         
	                           handleResponse(data) ;
                           }, 
                           error: function (returnValue) {
                           }
					});
					
					/* if(_code!="1"&&_code.length>0 && _name.length>0)
						param="?search_EQ_groupid="+_code;
						
					if(_code.length>0 && _name.length>0){
						window.location.href="${ctx}/complat/complatList"+param;
					} */
					$("#areacode").next('.error').hide();
				}
		,onCheck:onCheck
		 
			}
		};
		
		//初始化组织机构树
		$.fn.zTree.init($("#areaTree"), setting, zNodes);
		
	});

	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) return false;
		return check;
	}
	
	function showMenu() {
		var cityObj = $("#areaname");
		var cityOffset = $("#areaname").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	function checkSubmit(){
		var powername=$("#qlName").val();
		var name=$.trim(powername);
		$("#qlName").val(name);
		form1.submit();
	}
	 function onCheck(e,treeId,treeNode){
		 debugger;
		 treeNode.isParent.checked=false;
         var treeObj=$.fn.zTree.getZTreeObj("areaTree"),
         nodes=treeObj.getCheckedNodes(true),
         v="";
         
         for(var i=0;i<nodes.length;i++){
        	 
        		 v+=nodes[i].id+",";
                 //获取选中节点的值
         }
         $("#groupid2").val(v);
         
         }

</script>
</html>
