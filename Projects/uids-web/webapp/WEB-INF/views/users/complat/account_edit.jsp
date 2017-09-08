<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<style type="text/css">
.ztree {margin-top: 10px;border: 1px solid #cfd9db; display:none;background: #f0f6e4;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
<script type="text/javascript">
$().ready(function() {

//表单校验
var userNameInput=$("#userName").val();
 $("#editForm").validate({
    rules: {
	   userName: {
	    required: true,
	    cnRangelength: [0,32],
	    stringCheck:userNameInput
	   },
	   userRole: {
	    required: true
	   },
	   loginAccount: {
	    required: true,
	    chrnum:true,
	    uniqueLoginAccount:true,
	    maxlength: 32
	   },
	   userSchool:{
	   cnRangelength: [0,64]
	   },
	   userEducation:{
	   	cnRangelength: [0,32]
	   },
	   userPostion:{
	   	cnRangelength: [0,64]
	   },
	   userIdentityCode:{
	   	isIdCardNo:true,
	   	 maxlength: 18
	   },
	   userPostcode:{
	   	isZipCode:true,
	   	maxlength: 6
	   },
	   userAddress:{
	   	cnRangelength: [0,256]
	   },
	   userTele:{
	   	isMobile:true,
	   	maxlength: 16
	   },
	   userHomeTele:{
	   	isPhone:true,
	   	maxlength: 16
	   },
	   userEmail:{
	   	email:true,
	   	maxlength: 64
	   },
	   userSexs:
	   {
	   	 required: true	
	   }
	  },submitHandler:function(form){
            var userRole=$("#userRole").val(); 
            var deptId=$("#deptId").val();
            if(userRole==''){
            	$.validator.errorShow($("#userRole"),'请选择角色');
            	return false;
            }else if(deptId==''){
            	$.validator.errorShow($("#deptId"),'请选择机构');
            	return false;
            }else{
				 form.submit();
			}
        } 
    });
    

});

$(function(){
	// Ajax重命名校验
	$.uniqueValidate('uniqueLoginAccount', '${ctx}/sys/checkAccount', ['loginAccount','oldLoginAccount'], '对不起，这个账号重复了');
	$("#roleNames").Popup($(".ulRoleList"), { width: "auto" });
	//
	$(".icon-date-r").click(function(){ $(this).prev("input").click(); });
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
				<a>法人管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position">用户新增</a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/complatSave">
    
    <div style="display:none;">
    	<input type="hidden" id="complatUserId" name="complatUserId" value="${complatUser.uuid}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
    			<th>基本属性</th>
    		</tr>
    		<tr>
	        	 <th> 法人类型：</th>
	        	 <td>
	        	 	<gsww:checkboxTag name="type" type="corporationType" inputType="radio" value="${corporation.type}"></gsww:checkboxTag>
	        	 </td>
	        	 <th><b class="mustbe">*</b> 企业名称：</th>
				 <td>
					<input type="text" id="name" name="name" value="${corporation.name}" />
				</td>
			</tr>
    		<tr>
	        	 <th><b class="mustbe">*</b>工商注册号/社会信用代码：</th>
	        	 <td>
					<input type="text" id="regNumber" name="regNumber" value="${corporation.regNumber}" />
				</td>
				<th> 组织机构代码：</th>
				<td>
					<input type="text"  id="orgNumber" name="orgNumber" value="${corporation.orgNumber}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 企业法人姓名：</th>
                <td>
                	<input type="text"  id="realName" name="realName" value="${corporation.realName}" />
                </td>
				<th> 企业法人民族：</th>
				
				<td>
					<gsww:checkboxTag name="userSex" type="XB" inputType="radio" value="${sysAccount.userSex}"></gsww:checkboxTag>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 企业法人身份证号：</th>
				<td>
					<input type="text" id="cardNumber" name="cardNumber" value="${corporation.cardNumber}"">
	            </td>
			</tr>
			<tr>
				<th>账户信息</th>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>用户名：</th>
				<td>
					<input type="text"  class="loginName" name="loginName" value="${corporation.loginName}" />
	            	
	            </td>
	        	<th><b class="mustbe">*</b> 手机号码：</th>
	        	<td>
	        		<input type="text" id="mobile" name="mobile" value="${corporation.mobile}" />
	        	</td>
			</tr>
			<tr>
				<th> 密码：</th>
	        	<td>
	        		<input type="password" id="pwd" name="pwd" value="${corporation.pwd}" />
	            	
	        	</td>
				<td>
					密码强度
				</td>
			</tr>
			<tr>
				<th>邮箱：</th>
				<td>
					<input type="text" class="input" id="email" name="email" value="${corporation.email}" />
	            	<i class="form-icon-clear"></i>
				</td>
				<th>办公电话：</th>
				<td>
					<input type="text" id="phone" name="phone" value="${corporation.phone}" />
				</td>
			</tr>
			<tr>
				<th>经营范围：</th>
				<td>
					<input type="text"  class="input" id="scope" name="scope" value="${corporation.scope}"  />
				</td>
			</tr>
			</table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/corporationList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
