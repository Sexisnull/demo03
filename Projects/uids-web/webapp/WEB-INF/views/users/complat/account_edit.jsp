<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<head>
<title>甘肃万维JUP课题</title>
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
</style>

<script type="text/javascript">


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
			cnRangelength: [0,127]
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

});



</script>

</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a>政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty outsideUser.iid}">用户新增</c:if><c:if test="${not empty outsideUser.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/complatSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${complatUser.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${corporation.enable}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    </div>

    
    <!--表单的主内容区域-->
     <div class="form-content">
		 	<table class="form-table">
		 		<tr>
		 		  <td class="td_1" rowspan="7" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
				  <th><b class="mustbe">*</b>姓名：</th>
				  <td>
					<input type="text" id="name" name="name" value="${complatUser.name}" />
	        	    <input type="hidden" id="croleId" class="input" name="iid" value="${complatUser.iid}"  />	  
	              </td>
	        	  <th><b class="mustbe">*</b>性别：</th>
				  <td>
					<input type="radio" name="sex" value = 1 <c:if test="${complatUser.sex == 1}">checked="checked" </c:if>>男&nbsp&nbsp&nbsp
    				<input type="radio" name="sex" value = 0 <c:if test="${complatUser.sex == 0}">checked="checked" </c:if>>女		
				  </td>
		 		</tr> 
		 		<tr>  
		 		  <th><b class="mustbe">*</b> 年龄：</th>
				  <td>
					<input type="text" id="age" name="age" value="${complatUser.age}"">
	              </td>
	        	  <th><b class="mustbe">*</b> 用户职务：</th>
	        	  <td>
					<input type="text" id="headship" name="headship" value="${complatUser.headship}" />
				  </td>		 		    		              				
			    </tr>
			    <tr>
    		      <th> 组织机构Id：</th>
				  <td>
					<input type="text" id="groupid" name="groupid" value="${complatUser.groupid}"">
	              </td>
	        	  <th>IP地址：</th>
				  <td>
				    <input type="text" class="input" id="ip" name="ip" value="${complatUser.ip}" />
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
				  <th><b class="mustbe">*</b> MSN：</th>				
				  <td>
					<input type="text"  id="msn" name="msn" value="${complatUser.msn}" />
				  </td>
			    </tr>
			    <tr>
			      <th class="td_5"><b class="mustbe">*</b>地址：</th>
				  <td class="td_3">					
					 <input type="text" id="address" name="address" value="${complatUser.address}" />
				  </td>
				  <th class="td_6"><b class="mustbe">*</b>邮编：</th>
				  <td class="td_4">
					  <input type="text"  id="post" name="post" value="${complatUser.post}" />
				  </td>				
			    </tr>			   		
		        <tr>
		           <td class="td_2" rowspan="3" style="max-width:0px;width:100px;ont-weight:bold;" align="center"">账号信息</td>
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
		           <th> 密码强度：</th>
			       <td>			                 
				      <table id="pwdpower" title="字母加数字加符号就会强" style="width: 100%" cellspacing="0" cellpadding="0" border="0">
					      <tbody>
							  <tr>
							     <td id="pweak" style="">弱</td>
						         <td id="pmedium" style="">中</td>
								 <td id="pstrong" style="">强</td>
							  </tr>
					      </tbody>
				      </table>
			       </td>
			       <th><b class="mustbe">*</b> 请设置密码找回问题答案：</th>
				   <td>
					  <input type="text"  class="input" id="pwdanswer" name="pwdanswer" value="${complatUser.pwdanswer}"  />
				   </td>
			    </tr>	
	    </table>
  </div>  
    
    <div style="width: 1000px;margin: 10px; display:none;">
		 <table style="float: left;width: 20%;line-height: 100px">
		 	<tr>
		 		<th>扩展属性</th>
		 	</tr>
		</table>
    </div>
    
    <div style="width: 1000px;margin: 10px; display:none;">
		 <table style="float: left;width: 20%;line-height: 100px">
		 	<tr>
		 		<th>
		 		   <span></span>
		 		</th>
		 		<td>
		 		   <input type="text"  class="input" id="" name="" value=""  />
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
