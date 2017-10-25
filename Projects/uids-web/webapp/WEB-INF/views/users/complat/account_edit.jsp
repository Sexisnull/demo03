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
    <!-- Handlebars模板组件 -->
	<script type="text/javascript" src="${ctx}/res/plugin/handlebars/handlebars.js"></script>

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
	width:100px;
}
.td_2 {
	border-right : 1px solid #C6E6FF;
	width:100px;
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
.td_7{
   border-right : 1px solid #C6E6FF;  
}
#td_7{
   align:center;
    vertical-align: middle;
}
.kzsx{
   width:300px;
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
	var groupMenu = [{"name":"单位选择","title":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];

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
			url : '../uids/getGroup',
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
	     name : {
				   required: true,
				   cnRangelength: [0,32],
		    	 isName: true
			 },
	     sex: {
	         required: true
	     },
	     age: {
	     	   required: true,
	         cnRangelength: [0,64],
	         maxlength: 3,
	         isAge: true	         
	     },	
	     pinyin: {
	         cnRangelength: [0,64],
	         isPinYin: true	         
	     },		     	       	   	     
	     headship: {
	     	   required: true,
	         cnRangelength: [0,64],
	         isHeadship: true
	     },	   
	     phone: {//办公电话
		   		 isCompTel:true,
		   		 maxlength:12 
		   },
	     mobile : {//移动电话
				   required: true,
				   isPhone:true,
		   		 uniqueMobile:true
			 },
		   email : {//email校验
				   required: true,
				   email:true,
		   		 maxlength: 64
		   },
		   qq:{
		   		 maxlength: 11,
		   		 isQq: true
		   },
	     msn:{
	   		   cnRangelength: [0,64],
	   		   isMsn: true
	   	 },
	   	 fax:{
		   		 cnRangelength: [0,64],
		   		 isFax: true
		   },
	   	 address:{
	   	 	   required: true,
				   /* isAddressInfo:true, */
		   		 cnRangelength: [0,127],
		   		 isAddress: true
		   	},
		   post:{
				  /* isEmail：true, */
				  required: true,
		   		maxlength: 6,
		   		isPost:true
		   	},
		   loginname : {//重命名校验*
				  required: true,
				  cnRangelength: [0,127],
				  isLoginname : true,
				  uniqueLoginname: true 
			 }, 
	     pwd : {
				  required: true,
				  cnRangelength: [6,18]
			 },
	   
	     pwdquestion : {
	     	  required: true,
			    cnRangelength: [0,127],
			    //isName : true
		   },
	     pwdanswer : {
	     	  required: true,
			    cnRangelength: [0,127],
			    //isName : true
	     },
	     cardid : {
	     	required: true,
				isIdCardNo:true,
				uniqueCardid:true
	    },
	    groupname : {
	    	required: true,	    	
	    },
	    submitHandler:function(form){
				form.submit();
			}
    } 
    
    /*submitHandler : function() {
				$.ajax({
						type : "POST",
						async : false,
						url : '${ctx}/complat/complatSave',
						data : $("#editForm").serialize(),
						dataType : "json",
						success : function(data) {
							if(data.ret == 0){
								   window.location.href="${ctx}/complat/complatList";
						   }else if (data.ret == 1) {
						   	  $.dialog.confirm(data.msg ,function(){
					            return null;
				          });
						   }else if(data.ret == 2){
								   window.location.href="${ctx}/complat/complatList";
						   }else if(data.ret == 3){
								   $.dialog.confirm(data.msg ,function(){
					            return null;
				          });
						   }
						}
				});
		}*/
   });   

   
   // Ajax重命名校验
	$.uniqueValidate('uniqueLoginname', '${ctx}/complat/checkLoginName', ['loginname','oldLoginname'], '对不起，这个登录名重复了');
	$.uniqueValidate('uniqueMobile', '${ctx}/complat/checkUserMobile', ['mobile','oldMobile'], '对不起，这个手机号重复了');	
  $.uniqueValidate('uniqueCardid', '${ctx}/complat/checkUserCardid', ['cardid','oldCardid'], '对不起，这个身份证号重复了');
 
    //政府用户名校验     
    jQuery.validator.addMethod("isName", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9\u4e00-\u9fa5]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "名称只能由字母、数字、中文组成，不能以下划线开头和结尾");
    //登录名
    jQuery.validator.addMethod("isLoginname", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾");
    //邮政编码
    jQuery.validator.addMethod("isPost", function(value, element) { 
           var corporName = /^[1-9][0-9]{5}$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "邮政编码格式不正确（共6位,开头不能为0)");
    //年龄
    jQuery.validator.addMethod("isAge", function(value, element) { 
           //var corporName = /^([1-9]\d|\d)$/; 
           var corporName = /^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
           return this.optional(element) || (corporName.test(value));     
    }, "年龄为1至120之间");
    //传真
    jQuery.validator.addMethod("isFax", function(value, element) { 
           var corporName = /^(\d{3,4}-)?\d{7,8}$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "传真格式错误");
    //qq
    jQuery.validator.addMethod("isQq", function(value, element) { 
           var corporName = /[1-9][0-9]{4,}/;   
           return this.optional(element) || (corporName.test(value));     
    }, "QQ格式错误");
    //Msn
    jQuery.validator.addMethod("isMsn", function(value, element) { 
           var corporName = /\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;   
           return this.optional(element) || (corporName.test(value));     
    }, "MSN格式错误");
    //办公电话
    jQuery.validator.addMethod("isCompTel", function(value, element) { 
           var corporName = /^(((0\d{3}[\-])?\d{7}|(0\d{2}[\-])?\d{8}))([\-]\d{2,4})?$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "电话号码格式错误,例如:XXX-XXXXXXXX或者XXXX-XXXXXXX");  
    //身份证号
     jQuery.validator.addMethod("isIdCard", function(value, element) { 
           var corporName = /([1-6]\d{5}(19|20)\d\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])\d{3}[0-9xX])|([1-6]\d{5}\d\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])\d{3})/;   
           return this.optional(element) || (corporName.test(value));     
    }, "身份证号格式错误"); 
     //职务
     jQuery.validator.addMethod("isHeadship", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9\u4e00-\u9fa5]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "职务只能由字母、数字、中文组成，不能以下划线开头和结尾"); 
    //姓名首字母
     jQuery.validator.addMethod("isPinYin", function(value, element) { 
           var corporName = /^[A-Z]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "请不要输入除大写字母以外的其他内容");
    //地址
     jQuery.validator.addMethod("isAddress", function(value, element) { 
           var corporName = /^(?=.*?[\u4E00-\u9FA5])[\dA-Za-z\u4E00-\u9FA5]/;   
           return this.optional(element) || (corporName.test(value));     
    }, "地址由汉字，字母和数字构成"); 
   //编辑时密码强度回显
    var pwding = $("#pwd").val();
	  EvalPwd(pwding);

   
   
	 //获取用户扩展属性
	
					var htmlString = [];
						var count = 1;
						var table = $(".form-table");
						var fieldsListMap = eval('${fieldsListMap}');
						var sum=0;
						for ( var i = 0; i < fieldsListMap.length; i++) {
							 var length=fieldsListMap[i].length;
							 sum +=	length;						 
						}
						if(sum!=0){
							if(sum%2==0){
	 	              sum=sum/2;
	            }else if(sum%2==1){
	 	              sum=Math.ceil(sum/2);
	            }
	            htmlString.push("<tr><td  class='td_2' id='td_7' rowspan='"+sum+"' align='center'>"+"扩展属性"+"</td>");
						var textName;
						var textValue;
						var userid = $("#iid").val().trim();
						//alert("userid===="+userid);
						for ( var i = 0; i < fieldsListMap.length; i++) {
							var fieldsList = fieldsListMap[i];
							for ( var j = 0; j < fieldsList.length; j++) {
								var fields = fieldsList[j];
								if (fields.type == 1) {
									for ( var key in fields) {
										if(key == "fieldname"){
											textName = fields[key];//类似ex_test
											textValue = fields[textName];
											if(textValue == null){
												textValue = "";
											}
										}
										if(key == "showname"){
											var textTitle = fields[key];
											if(count==1){  
    					if(count%2==1){
    					       htmlString.push("<th>"+ textTitle+ "：</th><td><input name='"+textName+"' type='text' value='"+textValue+"' style='width: 79.9%;'></td>");   	    			       
   	    			    }
     					 if(count%2==0){
											 htmlString.push("<th>"+ textTitle+ "：</th><td><input type='text' name='"+textName+"' value='"+textValue+"' style='width: 79.9%;'></td></tr>");
     	    			}   					
    				}else{
    				    if(count%2==1){
    					 	       htmlString.push("<tr><th>"+ textTitle+ "：</th><td><input type='text' name='"+textName+"' value='"+textValue+"' style='width: 79.9%;'></td>");
    					 }
    	    			    if(count%2==0){
    	    			    	htmlString.push("<th>"+ textTitle+ "：</th><td><input type='text' name='"+textName+"' value='"+textValue+"' style='width: 79.9%;'></td></tr>");
    	    			    }
    				}
												count++;
										}
										
									}
								}


								var values;
								var keys;
								var selectTitle;
								if (fields.type == 2) {
									for ( var key in fields) {
										var value = fields[key];
										if(key == "showname"){
										   //alert("key======"+key);
											selectTitle = fields[key];
										}
										//alert("selectTitle======"+selectTitle);
										if (key == 'fieldkeys') {
											keys = value.split(",");
										}
                    //alert("keykey======"+key);
										if (key == 'fieldvalues') {
											values = value.split(",");
										}
										//alert("values======"+values);
										if (key != 'type' && key != 'userid') {										
											if (key == 'fieldname') {
												if(count == 1){
    			    		if(count%2==1){
    			    			htmlString.push("<tr><th>"+ selectTitle+ "：</th><td><select id='"+value+"' name= '"+value+"' style='width: 86.9%;'>");
		    			    	//htmlString.push("<th>"+value+"</th><td><select  class='kzsx' id='"+value+"' name= '"+value+"' style='width=:88%;'>");
		    			    	//循环key；
		    			        for(var i=0;i<keys.length;i++){
		    			 			htmlString.push("<option value='"+keys[i]+"'");
		    			            //获取下拉列表默认值
								    var select = eval('${jsonMap}');
								    for(var selectKey in select[0]){
								    	var selectValue = select[0][selectKey];
								    	if(selectValue == keys[i]){
		    			        			htmlString.push("selected = 'selected'");
		    			        		}
							    	}
							    	htmlString.push(">"+values[i]+"</option>");
		    				    }
		    			       htmlString.push("</select></td>");
		    			       
		    				   
		    			    }
		    			    if(count%2==0){
		    			    	htmlString.push("<th>"+ selectTitle+ "：</th><td><select id='"+value+"' name= '"+value+"' style='width: 86.9%;'>");
		    			    	//循环key；
		    			        for(var i=0;i<keys.length;i++){
		    			        	htmlString.push("<option value='"+keys[i]+"'");
		    			          //获取下拉列表默认值
		    			          if(userid!=""){
		    			          	 var select = eval('${jsonMap}');
								    for(var selectKey in select[0]){
								    	var selectValue = select[0][selectKey];
								    	if(selectValue == keys[i]){
		    			        			htmlString.push("selected = 'selected'");
		    			        		}
							    	}
		    			          }
							    	htmlString.push(">"+values[i]+"</option>");
		    				    }
		    			       htmlString.push("</select></td></tr>");
		    			    }	
    			    	}else{
    			    		if(count%2==1){
		    			    	htmlString.push("<tr><th>"+ selectTitle+ "：</th><td><select id='"+value+"' name= '"+value+"' style='width: 86.9%;'>");
		    			    	//循环key；
		    			        for(var i=0;i<keys.length;i++){
		    			 			htmlString.push("<option value='"+keys[i]+"'");
		    			          //获取下拉列表默认值
								    if(userid!=""){
		    			          	 var select = eval('${jsonMap}');
								    for(var selectKey in select[0]){
								    	var selectValue = select[0][selectKey];
								    	if(selectValue == keys[i]){
		    			        			htmlString.push("selected = 'selected'");
		    			        		}
							    	}
		    			          }
							    	htmlString.push(">"+values[i]+"</option>");
		    				    }
		    			       htmlString.push("</select></td>");
		    			       
		    				   
		    			    }
		    			    if(count%2==0){
		    			    	htmlString.push("<th>"+ selectTitle+ "：</th><td><select id='"+value+"' name= '"+value+"' style='width: 86.9%;'>");
		    			    	//循环key；
		    			        for(var i=0;i<keys.length;i++){
		    			        	htmlString.push("<option value='"+keys[i]+"'");
		    			          //获取下拉列表默认值
								    if(userid!=""){
		    			          	 var select = eval('${jsonMap}');
								    for(var selectKey in select[0]){
								    	var selectValue = select[0][selectKey];
								    	if(selectValue == keys[i]){
		    			        			htmlString.push("selected = 'selected'");
		    			        		}
							    	}
		    			          }
							    	htmlString.push(">"+values[i]+"</option>");
		    				    }
		    			       htmlString.push("</select></td></tr>");
		    			    }
    			    	}
												
							
												count++;
											}
										}
									}
								}
							}
						}
						table.append(htmlString.join(""));
						}else if(sum==0){
	 	           htmlString.push("");
	 	           table.append(htmlString.join(""));
	          } 
						
  //编辑页面密码强度判断
  //编辑页面密码强度判断
	var pwding = $("#pwd").val();
	EvalPwd(pwding);
	//$('#pwd').attachEvent('oninput',EvalPwd(pwding));	

});


function checkAndSave() { 
    var level = $("#level").val(); 
    var pwdLevel=${pwdLevel};
    var pwd = $("#pwd").val();
    var msg = "";
    if(pwd==""){
    	$.dialog.alert("请填写密码！",function(){
				return null;
		});
		return;
    }    
    if(level>=pwdLevel){
    	$("#editForm").submit();
    	return;
    }else{
    	if(pwdLevel==0){
    		msg= "密码强度至少为弱!";
    	}else if(pwdLevel==1){
    		msg = "密码强度至少为中!";
    	}else if(pwdLevel==2){
    		msg = "密码强度至少为强!";
    	}
    	$.dialog.alert(msg,function(){
				return null;	            
		});
    }
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
				<a>政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty complatUser.iid}">用户新增</c:if><c:if test="${not empty complatUser.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
    <!-- 消息提示 -->
    <div class="callDisp nav_wrap">
		<ul>
			<li class="display">
				<font class="fonts" id="fontss"></font>
			</li>
		</ul>
	</div>
			
			
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/complatSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${complatUser.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${complatUser.enable}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    	<input type="hidden" id="level" name="level">
    </div>

    
    <!--表单的主内容区域-->
     <div class="form-content">
		 	<table class="form-table" id="dataTable">
		 		<tr>
		 		  <td class="td_1" rowspan="7" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
				  <th><b class="mustbe">*</b>姓名：</th>
				  <td style="width:300px;">
					<input type="text" id="name" name="name" value="${complatUser.name}" />
	        	    <input type="hidden" id="croleId" class="iid" name="iid" value="${complatUser.iid}"  />	  
	              </td>
	        	  <th><b class="mustbe">*</b>性别：</th>
				  <td style="width:300px;">	
				 	<gsww:checkboxTag name="sex" defaultValue="1" type="ZFYHXB" inputType="radio" value="${complatUser.sex}"></gsww:checkboxTag>
				  </td>
		 		</tr> 
		 		<tr>  
		 		  <th><b class="mustbe">*</b> 年龄：</th>
				  <td style="width:300px;">
					   <input type="text" id="age" name="age" value="${complatUser.age}"">
	        </td>
	         <th><b class="mustbe">*</b> 所属机构：</th>
				  <td style="width:300px;">
				    <c:if test="${empty complatUser.iid}"> 
				        <input id="groupname" value="${groupMap[complatUser.groupid]}" name="groupname" type="text" style="cursor: pointer;"/> 
					    <input type="hidden" id="groupid" name="groupid">	
				    </c:if>
				    <c:if test="${not empty complatUser.iid}">
				          <input id="groupname1" value="${groupMap[complatUser.groupid]}" name="groupname" readonly="readonly" type="text" style="cursor: pointer;"/> 
					      <input type="hidden" id="groupid" name="groupid" value="${complatUser.groupid }">	
				    </c:if>											
				  </td>
	        <!--<th> MSN：</th>
	        <td>
	        	 <input type="text" id="msn" name="msn" value="${complatUser.msn}"">	        	
	        </td>
	             -->
	             <!--  <th>姓名的首字母全拼(大写)：</th>
	        	   <td style="width:300px;">
	        		  <input type="text" id="pinyin" name="pinyin" value="${complatUser.pinyin}" />
	        	   </td> -->
			    </tr>	
			    <tr>
			       <th> QQ：</th>
				   <td style="width:300px;">
					   <input type="text" id="qq" name="qq" value="${complatUser.qq}" />
				   </td>
				   <th><b class="mustbe">*</b> 身份证号：</th>
				   <td style="width:300px;">
					<input type="text" <c:if test="${userDetail.cardid != null}">readonly="readonly"</c:if> id="cardid" class="cardid" name="cardid" value="${userDetail.cardid}" />
					<input type="hidden" id="oldCardid" class="oldCardid" name="oldCardid" value="${userDetail.cardid}" />
				</td>
			    </tr>		    
			    <tr>
			      <th>办公电话：</th>
				  <td style="width:300px;">
					<input type="text"  id="phone" name="phone" value="${complatUser.phone}" />
				  </td>
				  <th><b class="mustbe">*</b> 移动电话：</th>
                  <td style="width:300px;">
                	<input type="text"  id="mobile" name="mobile" value="${complatUser.mobile}" />
					        <input type="hidden" id="oldMobile" name="oldMobile" value="${complatUser.mobile}" />
                  </td>				
			    </tr>
			    <tr>
				  <th> 传真：</th>
				  <td style="width:300px;">
					<input type="text" id="fax" name="fax" value="${complatUser.fax}" />
				  </td>
				  <th><b class="mustbe">*</b> Email：</th>				
				  <td style="width:300px;">
					<input type="text"  id="email" name="email" value="${complatUser.email}" />
				  </td>
			    </tr>	
			     <tr>
				  <th><b class="mustbe">*</b> 地址：</th>
				  <td style="width:300px;">
					<input type="text" id="address" name="address" value="${complatUser.address}" />
				  </td>
				  <th><b class="mustbe">*</b> 邮政编码：</th>				
				  <td style="width:300px;">
					<input type="text"  id="post" name="post" value="${complatUser.post}" />
				  </td>
			    </tr>		    
			    <tr>
			      <th class="td_5"><b class="mustbe">*</b> 用户职务：</th>
				  <td class="td_3" style="width:300px;">
					<input type="text" id="headship" name="headship" value="${complatUser.headship}"">
				  </td>
				 <!--  <th class="td_6"><b class="mustbe">*</b> 所属机构：</th>
				  <td class="td_4" style="width:300px;">
				    <c:if test="${empty complatUser.iid}"> 
				        <input id="groupname" value="${groupMap[complatUser.groupid]}" name="groupname" type="text" style="cursor: pointer;"/> 
					    <input type="hidden" id="groupid" name="groupid">	
				    </c:if>
				    <c:if test="${not empty complatUser.iid}">
				          <input id="groupname1" value="${groupMap[complatUser.groupid]}" name="groupname" readonly="readonly" type="text" style="cursor: pointer;"/> 
					      <input type="hidden" id="groupid" name="groupid" value="${complatUser.groupid }">	
				    </c:if>											
				  </td>
				  -->
				  <th class="td_6"></th> 
				  <td class="td_4"></td> 
			    </tr>			   		
		        <tr>
		           <td class="td_1" rowspan="3" style="max-width:0px;width:100px;ont-weight:bold;" align="center"">账号信息</td>
                   <th><b class="mustbe">*</b>登录名：</th>
                   <td style="width:300px;">
                   	   <input type="text" id="loginname" name="loginname" value="${complatUser.loginname}" />
					             <input type="hidden" id="oldLoginname" name=oldLoginname" value="${complatUser.loginname}" />					           
	               </td>
	        	  <!-- <th><b class="mustbe">*</b> 请设置密码找回问题：</th>
				   <td style="width:300px;">
					  <input type="text"  class="input" id="pwdquestion" name="pwdquestion" value="${complatUser.pwdquestion}"  />
				   </td>-->
			    </tr>	
				<tr style="width:300px;">		
				   <th><b class="mustbe">*</b> 密码：</th>
        	       <td style="width:300px;">
        		      <input type="password" id="pwd" name="pwd"  value="${pwd}" onkeyup="javascript:EvalPwd(this.value);"/>
        	       </td>
	        	 <!--  <th><b class="mustbe">*</b> 请设置密码找回问题答案：</th>
				   <td style="width:300px;">
					  <input type="text"  class="input" id="pwdanswer" name="pwdanswer" value="${complatUser.pwdanswer}"  />
				   </td>-->
				   
			    </tr>
			    <tr>				
		           <th class="td_6">密码强度：</th>
			       <td class="td_6" style="width:300px;">
				      <table id="pwdpower" style="width: 86%" cellspacing="0"  cellpadding="0" border="0">
					     <tbody>
						    <tr>
							   <td id="pweak" style="text-align: center;width: 100px;border: 1px solid  grey">弱</td>
							   <td id="pmedium" style="text-align: center;width: 100px;border: 1px solid  grey">中</td>
							   <td id="pstrong" style="text-align: center;width: 100px;border: 1px solid  grey">强</td>
						    </tr>
					     </tbody>
				      </table>
			       </td>
			       <th class="td_6"></th>
			       <td class="td_4"></td>
			    </tr>	
	    </table>
  </div> 
    

    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="button" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen" onclick="checkAndSave();"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/groupOrgTree?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
