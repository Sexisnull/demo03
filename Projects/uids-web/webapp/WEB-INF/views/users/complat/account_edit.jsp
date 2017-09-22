<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<title>甘肃万维JUP课题</title>
    <script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>


<style type="text/css">
.form-table td{
width: 0;
color: rgb(119, 119, 119);
}
.form-table th:first-child {
    padding-left: 0px;
	width:130px;
}
.form-table td {
    height: 32px;
    line-height: 32px;
    white-space: nowrap;
    padding: 2px 2px;
}

.td_1 {
	border-bottom : 1px solid #C6E6FF;
	border-right : 1px solid #C6E6FF;
}
.td_2 {
	border-right : 1px solid #C6E6FF;
}
.td_3 {
	border-bottom : 1px solid #C6E6FF;
}
.td_4 {
	border-bottom : 1px solid #C6E6FF;
}
.td_5 {
	border-bottom : 1px solid #C6E6FF;
}
.td_6 {
	border-bottom : 1px solid #C6E6FF;
}  
/*角色信息分区样式*/
.role1,.role2{
    border:1px solid #000;
    margin:0px 40px 0px 40px;
    width:200px;
    height:300px;
}

</style>

<script type="text/javascript">

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
/*********************机构树结束************************/



$().ready(function() {

//表单校验
var complatUserNameInput=$("#name").val();
 $("#editForm").validate({
    rules: {
	   name: {
	        required: true,
	        cnRangelength: [0,32],
	        stringCheck:complatUserNameInput
	   },
	   sex: {
	        required: true
	   },
	   age: {
	        cnRangelength: [0,64]
	   },	  	   
	   headship:{
	        cnRangelength: [0,64]
	   },
	   phone:{
	    	isMobile:true,
	     	maxlength: 16
	   },
	   mobile : {//移动电话
			required: true,
			isPhone:true,
	   		maxlength: 16
		},
		email : {//email校验
			required: true,
			email:true,
	   		maxlength: 64
		},
	    msn:{
	   		cnRangelength: [0,64]
	   	},
	   	address:{
	   		cnRangelength: [0,127]
	   	},
		post:{
	   		maxlength: 6
	   	},
		loginname : {//重命名校验*
			required: true,
			cnRangelength: [0,127]
		}, 
	    pwd : {
			required: true,
			cnRangelength: [6,18]
		},
	   
	   pwdquestion : {
			required: true,
			cnRangelength: [0,127]
		},
	   pwdanswer : {
			required: true,
			cnRangelength: [0,127]
	   },
	   submitHandler:function(form){        
				 form.submit();		
        } 
     }
   });   



   //用户扩展属性
  /* var table = $("#dataTable");
   var tr=$("#kzsxTr");
   var list=eval('${fieldsListMap}');
   var td1="";
   var td2="";
   table.append("<tr><td class='td_2' rowspan='6' style='max-width:0px;width:100px;ont-weight:bold;' align='center'>"+"扩展属性"+"</td></tr>");
   var type="2";//1：字符型     2：枚举型
   for(var i=0;i<5;i++){  
        if(type=="2"){
            td1=table.append("<tr id='kzsxTr'><th>"+"shenxh"+"</th><td>"+"<input type='text'/>"+"</td></tr>");   
       }else if(type=="2"){
            td2=tr.append("<tr id='kzsxTr'><th>"+"shenxh"+"</th><td>"+"<select id=''><option>"+"政府干部"+"</option>"+"</td></tr>"); 
       }     
   }*/
   
   
   //获取用户扩展属性				   
	var count = 1;						
	var table = $(".form-table");		
	var fieldsListMap = eval('${fieldsListMap}');	
	var size=fieldsListMap.length;
	/*if(size%2==1){
	   var rows=(size/2)+1;
	   table.append("<tr><td class='td_2' rowspan='rows' style='max-width:0px;width:100px;ont-weight:bold;' align='center'>"+"扩展属性"+"</td></tr>");	
	}
	if(size%2==0){
	   var rows=(size/2);
	   table.append("<tr><td class='td_2' rowspan='rows' style='max-width:0px;width:100px;ont-weight:bold;' align='center'>"+"扩展属性"+"</td></tr>");	
	}*/								
	for ( var i = 0; i < size; i++) {
		var fieldsList = fieldsListMap[i];
		for ( var j = 0; j < fieldsList.length; j++) {
			var fields = fieldsList[j];
			if (fields.type == 1) {
				for ( var key in fields) {
					var value = fields[key];
					if (key != 'type' && key != 'userid') {
						alert(count % 2);
					    if (count % 2 == 1) {
							table.append("<tr><th>"+ key+ "</th><td><input type='text' value='"+value+"'></td>");
						}
						if (count % 2 == 0) {
							table.append("<th>"+ key+ "</th><td><input type='text' value='"+value+"'></td></tr>");
						}
						count++;
					}
				}
			}

			var values;
			if (fields.type == 2) {
			   for ( var key in fields) {
			      var value = fields[key];
			      //alert("key:"+key+"  value:"+value);
			      if (key == 'fieldkeys') {
				      keys = value.split(",");
			      }

			      if (key == 'fieldvalues') {
				      values = value.split(",");
			      }

			      if (key != 'type' && key != 'userid') {
				      if (key == 'fieldname') {
					      table.append("<th>"+ value+ "</th><td><select id='filedValues'><select></td></tr>");
				      }
			      }
		      }
	      }
		}
	}

});





$(function(){
    $("#p1").click(function(){
      $("#p1").css("display","none");
      var p = $("#p1").val().trim();
      $("#p1").css("display","none");
      $("#b1").val(p);
    });
});


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
				<a>政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty complatUser.iid}">用户新增</c:if><c:if test="${not empty complatUser.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/complatSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${complatUser.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${complatUser.enable}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    </div>

    
    <!--表单的主内容区域-->
     <div class="form-content">
		 	<table class="form-table" id="dataTable">
		 		<tr>
		 		  <td class="td_1" rowspan="6" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
				  <th><b class="mustbe">*</b>姓名：</th>
				  <td>
					<input type="text" id="name" name="name" value="${complatUser.name}" />
	        	    <input type="hidden" id="croleId" class="input" name="iid" value="${complatUser.iid}"  />	  
	              </td>
	        	  <th><b class="mustbe">*</b>性别：</th>
				  <td>	
				 	<gsww:checkboxTag name="type" defaultValue="1" type="ZFYHXB" inputType="radio" value="${complatUser.sex}"></gsww:checkboxTag>
				  </td>
		 		</tr> 
		 		<tr>  
		 		  <th><b class="mustbe">*</b> 年龄：</th>
				  <td>
					<input type="text" id="age" name="age" value="${complatUser.age}"">
	              </td>
	        	   <th><b class="mustbe">*</b> 用户职务：</th>
				  <td>
					<input type="text" id="headship" name="headship" value="${complatUser.headship}"">
				  </td>
			    </tr>			    
			    <tr>
			      <th><b class="mustbe">*</b> 固定电话：</th>
				  <td>
					<input type="text"  id="phone" name="phone" value="${complatUser.phone}" />
				  </td>
				  <th><b class="mustbe">*</b> 移动电话：</th>
                  <td>
                	<input type="text"  id="mobile" name="mobile" value="${complatUser.mobile}" />
                  </td>				
			    </tr>
			    <tr>
				  <th> 传真：</th>
				  <td>
					<input type="text" id="fax" name="fax" value="${complatUser.fax}" />
				  </td>
				  <th><b class="mustbe">*</b> Email：</th>				
				  <td>
					<input type="text"  id="email" name="email" value="${complatUser.email}" />
				  </td>
			    </tr>			    
			    <tr>
				  <th> QQ：</th>
				  <td>
					<input type="text" id="qq" name="qq" value="${complatUser.qq}" />
				  </td>
				  <th><b class="mustbe">*</b> 所属机构：</th>
				  <td>
				    <c:if test="${empty complatUser.iid}">
				        <input id="groupname" value="${groupName}" name="groupid" type="text" style="cursor: pointer;"/> 
					    <input type="hidden" id="groupid" name="search_EQ_groupId">	
				    </c:if>
				    <c:if test="${not empty complatUser.iid}">
				        <input id="groupname1" value="${groupMap[complatUser.groupid]}" readonly="readonly" type="text" style="cursor: pointer;"/> 
					    <input type="hidden" id="groupid" name="groupid">	
				    </c:if>											
				  </td>
			    </tr>
			    <tr>
			       <th><b class="mustbe">*</b> 角色信息：</th>
			       <td>			          
					  <ul id="role1" class="role1">
					      <li id="p1">系统管理员</li>
					      <li id="p2">机构管理员</li>
					      <li id="p3">实名认证审核员</li>
					      <li id="p4">网站群管理系统角色</li>
					      <li id="p5">统一申报管理系统</li>
					      <li id="p6">综合管理查询系统</li>
					      <li id="p7">行政权力事项管理系统</li>					      
					  </ul>
				   </td>
				   <td>			          
					  <ul id="role1" class="role1">
					      <li id="b1"></li>
					      <li id="b2"></li>
					      <li id="b3"></li>
					      <li id="b4"></li>
					      <li id="b5"></li>
					      <li id="b6"></li>
					      <li id="b7"></li>					      
					  </ul>
				   </td>
			    </tr>
			    <!--<tr>
			      <th class="td_5"><b class="mustbe">*</b>地址：</th>
				  <td class="td_3">					
					 <input type="text" id="address" name="address" value="${complatUser.address}" />
				  </td>
				  <th class="td_6"><b class="mustbe">*</b>邮编：</th>
				  <td class="td_4">
					  <input type="text"  id="post" name="post" value="${complatUser.post}" />
				  </td>				
			    </tr>-->			   		
		        <tr>
		           <td class="td_1" rowspan="3" style="max-width:0px;width:100px;ont-weight:bold;" align="center"">账号信息</td>
                   <th><b class="mustbe">*</b>登录名：</th>
                   <td>
					  <input type="text"  class="loginname" name="loginname" value="${complatUser.loginname}" />
	               </td>
	        	   <th>姓名的首字母全称：</th>
	        	   <td>
	        		  <input type="text" id="loginallname" name="loginallname" value="${complatUser.loginallname}" />
	        	   </td>
			    </tr>	
				<tr>		
				   <th><b class="mustbe">*</b> 密码：</th>
	        	   <td>
	        		  <input type="password" id="pwd" name="pwd" value="${complatUser.pwd}" onkeyup="javascript:EvalPwd(this.value);"/>	            	
	        	   </td>
				   <th><b class="mustbe">*</b> 请设置密码找回问题：</th>
				   <td>
					  <input type="text"  class="input" id="pwdquestion" name="pwdquestion" value="${complatUser.pwdquestion}"  />
				   </td>
			    </tr>
			    <tr>				
		           <th class="td_5"> 密码强度：</th>
			       <td class="td_3">			                 
				      <table id="pwdpower" style="width: 85%" cellspacing="0"
							cellpadding="0" border="0">
						<tbody>
							<tr>
								<td id="pweak" style="text-align: center;width: 100px;border: 1px solid  grey">弱</td>
								<td id="pmedium" style="text-align: center;width: 100px;border: 1px solid  grey">中</td>
								<td id="pstrong" style="text-align: center;width: 100px;border: 1px solid  grey">强</td>
							</tr>
						</tbody>
				</table>
			       </td>
			       <th class="td_6"><b class="mustbe">*</b> 请设置密码找回问题答案：</th>
				   <td class="td_4">
					  <input type="text"  class="input" id="pwdanswer" name="pwdanswer" value="${complatUser.pwdanswer}"  />
				   </td>
			    </tr>	
	    </table>
  </div> 
    

    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/complatList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
    
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;"></div>
	
</div>

</body>
</html>
