<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<style type="text/css">
/* 	.menuContent{background: #fff;
    width: 270px;
    border: 1px solid #ccc;
    height: 400px;
    border-top: 0px;
    overflow-y: auto;} */
    .menuContent{border: 1px solid #ddd;overflow-x:auto;overflow-y:auto;float: left;}
	.menuContent #areaTree{ width:450px;display: block;height:450px;float: left;overflow-x:auto;overflow-y:auto;}
    .form-content{margin-left: 200px;}
</style>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
<style>
/*设置按钮样式*/
.list-toolbar ul {
    display: block;
    list-style: none;
    text-align: right;
    margin: 8px 10px;
}
.list-toolbar ul li.edit a {
    background-color: #f1c40f;
    color: #ffffff;
}
/*设置弹出层样式*/
.alert_tb {	
	left:180px;
	top:120px;
	border:1px solid #F68A8A;
	width:640px;
	height:150px;
	background-color:#e2ecf5;
	z-index:1000;
	position:absolute;
} 
.alert_tb p span .close {
	margin-left:550px;
	font-weight:500;
	cursor:pointer;
	font-size:16px;
}
.userForm{
   height: 0px;
}
.input_one{
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
.input_one p .inputUser{
    margin-left:5px;
}
.input_two{
  line-height:90px;
  height:90px;
}
.input_two ul li{
    text-align:center;
    display: inline;
    list-style-type: none;
}
.alert_tb p span {
	float:left;
}
		
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

</style>
<script type="text/javascript"> 
function checkSubmitForm(){
		var loginnameSearch = $("#loginnameSearch").val();
		if(loginnameSearch ==  '' || isNumbOrLett(loginnameSearch)){
			form1.submit();
		}else{
			$.validator.errorShow($("#loginnameSearch"),'只能包括数字和字母');
		}
	}
	
	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett( s ){//判断是否是字母、数字组成
		//var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
		var regu = /^([a-zA-Z0-9]+)$/;
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
function addComplatUser(){
     window.location.href="${ctx}/complat/complatUserEdit";
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
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}

	
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
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
	
	
	
	/*批量启用*/
	function startData() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要启用吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/complat/startUserEnable?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
	



   /**批量停用操作**/	
	function stopData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要停用吗？',function(){
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
function intPutComplatUser(){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#alerttb").show(); 				
}
	
 


//下载模板
function downloadTemplate(fileName){	    
	window.location.href="${ctx}/complat/downloadComplaUserTemplate";
}	  


//导入
/*$(function(){  
  $("#excel_button").click(function(){		  	
	var file = $("#excelFile").val();
	if(file==null || file==""){
		 alert("请选择文件..");
		 return;
	}else{
		 console.log(file);
		 alert(33333);
		 $("#form3").submit();
	}
			/*var index = file.subString(file.lastIndexOf(".")+1);
			if(file == ""){
				alert("请选择文件..");
				return;
			}else if(!(index == "xls" ||index == "xlsx")){
				alert("请上传后缀名为xls或xlsx的文件!");
				return;
			}else{
				$("#form3").submit();
			}
   });
});*/

function fileUpload(){
		var picPath=document.getElementById('excelFile').value;
		if(picPath==null||picPath==""){
			alert("请选择要导入的excel文件！");
			return false ;
						}
		var type=picPath.substring(picPath.lastIndexOf(".")+1,picPath.length).toLowerCase();
		if(type=="xls"||type=="xlsx"){
			$("#form3").submit();							
		}else{
			alert("请上传正确的EXCEL表格文档");
			document.getElementById("excelFile").value="";
			return false;
		}
			     
}	


/***导出***/
/*function outPutComplatUser(){
   if($("input[type=checkbox]:checked").length == 0){      
       var ids = "";
	   $('#list-table tbody input[type=checkbox]').each(function(i, o) {
			if($(o).attr('')) {
				ids += $(o).val() + ",";
			}
	   });	
	   console.log(ids);	                     
       window.location.href="${ctx}/complat/complatExport"; 
       alert("==========");            						       
     }   
}
*/






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
				<a >政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				用户列表
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/complatList" method="get">
			<table class="advanced-content">
				<tr>
					<th style="padding-left: 300px">请输入登录名：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="请输入登录名" value="${sParams['LIKE_loginname']}" id="loginnameSearch" name="search_LIKE_loginname" />
						</td>
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
					<td class="btn-group"> <a id="advanced-btn" class="btnSearch" >高级搜索</a></td>					
				</tr>
			</table>
		</form>
		
		 <!-- 高级探索表单 -->
			<form id="form2" name="form2" action="${ctx}/complat/complatList" method="get">
				<table class="advanced-content" style="display:none;">
					<tr>
						  <th style="padding-left: 10px">请输入姓名：</th>
					      <td width="20%">
							   <input type="text"  style="width: 170px;" placeholder="请输入姓名:" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
						  </td>
						  <th style="padding-left: 5px">请输入登录名：</th>
						  <td width="20%">
							   <input type="text"  style="width: 170px;" placeholder="请输入登录名:" value="${sParams['LIKE_loginname']}" id="loginnameSearch" name="search_LIKE_loginname" />
						  </td>
						  <th style="padding-left: 10px">请输入登录名全称：</th>
						  <td width="20%">
							   <input type="text"  style="width: 170px;" placeholder="请输入登录名全称:" value="${sParams['LIKE_loginallname']}" id="loginallnameSearch" name="search_LIKE_loginallname" />
						  </td>
						  <td class="btn-group"> <a id="advanced-search-btn" class="btnSearch" >搜索</a></td>
						<!--<td class="btn-group"> <a id="advanced-search-btn-cancel" class="btnSearch" >取消</a></td>-->
					</tr>
				</table>
			</form>
	</div>
    
    
    
	<!--列表内容区域-->
	<div class="list">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
	<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
        <div class="list-topBar  advanced-search">
        	<div class="list-toolbar" style="margin-left:730px;height:30px;">
             <!--  操作按钮开始 	    -->   
                 <ul class="list-Topbtn">
						<li class="add"><a title="新增" onclick="add('complat/complatUserEdit');">新增</a></li>
						<li class="del"><a title="删除" onclick="deleteData('complat/complatUserDelete','iid');">删除</a></li>
						<li class="query" id="importFile" onclick="intPutComplatUser()"><a title="导入">导入</a></li>
						<li class="exportData"><a title="导出" onclick="outPutComplatUser()">导出</a></li>
						<li class="startData"><a title="启用" onclick="startData('complat/startUserEnable','iid');">启用</a></li>
						<li class="edit"><a title="停用" onclick="stopData('complat/stopUserEnable','complatUserId');">停用</a></li>
				 </ul>
           <!--   操作按钮结束  --> 
        	 <div class="list-toolbar">
            <!-- 操作按钮开始 -->	 
             <gsww:opTag menuId="11" tabIndex="1" operatorType="1"></gsww:opTag>
             <!-- 操作按钮结束 -->
           </div> 
        </div>
       
        
        
       
        
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
    	<!-- 列表开始 -->
    	<div id="menuContent" class="menuContent">
			<ul id="areaTree" class="ztree" style="margin-top:0; width:180px;"></ul>
		</div>
		<div class="form-content">
        <table cellpadding="0" cellspacing="0" border="0" width="100%" id="list-table" class="list-table">
        	<thead>
            	<tr>
                	<th width="10px">   
                		<div class="label">
									<i class="check_btn check_all"></i>
									<input id="${ComplatUser.iid}" value="${ComplatUser.iid}" type="checkbox" class="check_btn" style="display:none;"/>
								</div>             		
                	</th>
                	
                	
                	<th width="10%" style="text-align: center;">姓名</th>
                    <th width="8%" style="text-align: center;">登录名</th>
                    <th width="12%" style="text-align: center;">登录全名</th>
                    <th width="12%" style="text-align: center;">所属机构</th>
                    <th width="14%" class="alignL" style="text-align: center;">用户职务</th>
                    <th width="15%" style="text-align: center;">办公电话</th>
                    <th width="6%" style="text-align: center;">账号开启</th><!--                                                     
                    <th width="18%" class="alignL" style="text-align: center;">注册时间</th>-->  
                    <th width="8%" style="text-align: center;">操作</th> 
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatUser" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatUser.iid}" value="${complatUser.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.name}" class="word_break">${complatUser.name}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.loginname}" class="word_break">${complatUser.loginname}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.loginallname}" class="word_break">${complatUser.loginallname}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="word_break">${groupMap[complatUser.groupid]}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="word_break">${complatUser.headship}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.phone}" class="word_break">${complatUser.phone}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatUser.enable == '0'}"><font color="red">未启用</font></c:if>
	                           		<c:if test="${complatUser.enable == '1'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>

	                    <!-- <td style="text-align: center;">
	                        <div title="${complatUser.createtime}" class="word_break">${complatUser.createtime}</div>
	                    </td> -->	                    	                   
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a929c9e5e5fbde5015e5fdb125c0002" tabIndex="1" operatorType="2"></gsww:opTag>
	                    </td>
	                </tr>
				</c:forEach>
                
            </tbody>       
        </table>
        </div>
        <!-- 列表结束 -->
    </div>
    <!-- 分页 -->
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>


   <!-- 导入数据时的弹出层 -->
        <div id="alerttb" class="alert_tb" style="display:none;"> 
           <div class="input_one">
               <p>
                  <span id="inputUser">用户导入</span>
                  <span><a class="close" href="${ctx}/complat/complatList">关闭</a></span>                                    
               </p>  
           </div>    
           <div class="input_two">
            	<form id="form3" name="form3" action="${ctx}/complat/complatImport" method="post" enctype="multipart/form-data">
       			 <ul class="advanced-content-one">
		            <li style="width:96%">				    
		            	<label style="width:300px; padding-left: 50px;">选择文件：</label>
		                <input type="file" id="excelFile" class="required" name="excelFile" style="width:300px;height:22px; background: #659be0;cursor: pointer;border: none;font-size: 12px;"/>
		            </li>  	            
		            <!--<li><input style="width:60px;height:25px;background:#f79638;color:#fff;border:1px solid #f79638;border-radius:3px;cursor: pointer;" type="button" value="下载模板" onclick= "downloadTemplate();" /></li>-->		            
		            <li><a class="advanced-btn" href="${ctx}/uploadFile/complat/userList.xlsx" target="_blank">模板下载</a></li>
		            
		            <li><input style="width:60px;height:25px;background:#f79638;color:#fff;border:1px solid #f79638;border-radius:3px;cursor: pointer;" type="button" class="input"  id="excel_button" value="导入" onclick="fileUpload()" /></li>
		        </ul>
             </form>
             
           </div>  	               	              	             	  
        </div>
</div>
</body>
<script type="text/javascript">
$(function(){
var zNodes = [];
		var setting = {
			async : {
				enable : true,
				type:"post",
				//url : "${ctx}/area/areaTree" //获取节点数据的URL地址
				url:"${ctx}/login/getGroup",
		        autoParam: ["id=groupId", "isDisabled"],
		        otherParam: { "type": "1" }
			},
			data : {
				simpleData : {
					enable : true
				}
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
					alert(_name+"--"+_code);
					var param="";
					 $.ajax({
					       type:"get",
					       url:  "${ctx}/complat/complatList",
					       data:{"search_EQ_groupid":_code},
					       datatype:  "json",
					       success: function (value) {
						     /*  alert(value)	;				      
	                           alert("请求成功"+_code);   */                                                                     
	                           var   data=JSON.parse(value);                         
	                           handleResponse(data) ;
                           }, 
                           
                           error: function (returnValue) {
                       /*   alert(_code);*/
                           }
					});
					
					if(_code!="1"&&_code.length>0 && _name.length>0)
						param="?search_EQ_groupid="+_code;
						
					if(_code.length>0 && _name.length>0){
						window.location.href="${ctx}/complat/complatList"+param;
					}
					$("#areacode").next('.error').hide();
				}
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


</script>
</html>
