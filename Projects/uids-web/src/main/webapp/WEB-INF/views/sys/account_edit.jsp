<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<link rel="stylesheet" href="${ctx}/res/skin/default/plugin/z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/z-tree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
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
//点击页面其它部分收缩选择角色下拉框
	$('body').on('click',function(event){
		if (!$(event.target).is($('.user-role>ul'))
			&& !$(event.target).is($('#areaRole'))
			&& !$(event.target).is($('.user-role').parent())
			&& !$(event.target).is($('.user-role>ul li'))
			&& !$(event.target).is($('.user-role>ul li input'))
			&& !$(event.target).is($('.user-role>ul li span'))
			&& !$(event.target).is($('.user-role .form-selectinput i'))
			&& !$(event.target).is($('.user-role'))
			&& !$(event.target).is($('.user-role .form-selectinput'))) {
				$('.user-role>ul').slideUp(200);
			}
	});
	 //角色下拉框点击效果
	$(".ulRoleList li label").on("click", function() {
					var str = '';
					var val = '';
					$(".ulRoleList li input:checkbox").each(function(index, element) {
						if ($(this).attr('checked') == 'checked') {
							if (str) {
								str += ',' + $(this).attr('id');
								val += ',' + $(this).attr('roleName');
							} else {
								str = $(this).attr('id');
								val = $(this).attr('roleName');
							}
						}
					});
		
					$("#userRole").val(str);
					$("#roleNames").val(val);
				});
	//选择角色
	$(".user-role").on("click",function(event){
			$('.error-info-checkedbox').remove();
			$(this).removeClass('erro');
	});
	
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
				<a >系统管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty sysAccount.userAcctId}">用户新增</c:if><c:if test="${not empty sysAccount.userAcctId}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
	<%-- <div class="form-title"><c:if test="${empty sysAccount.userAcctId}">用户新增</c:if><c:if test="${not empty sysAccount.userAcctId}">用户编辑</c:if></div> --%>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/sys/accountSave">
    
    <div style="display:none;">
    	<input type="hidden" id="userAcctId" name="userAcctId" value="${sysAccount.userAcctId}"/>
    	<input type="hidden" id="userState" name="userState" value="${sysAccount.userState}"/>	
    	<input type="hidden" name="loginPassword" value="${sysAccount.loginPassword}"  />
    	<input type="hidden" name="setId" value="1"  />
    	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
    		<!--  <ul class="form-table"> -->
	        	<!--,nameCheck: true,isUnique:true,cnRangelength:[1,64] -->
	        	 <th><b class="mustbe">*</b> 请输入用户账号：</th>
	        	 <td>
					<input type="text" placeholder="格式：如zhangsan" id="loginAccount" name="loginAccount" value="${sysAccount.loginAccount}" />
					<input type="hidden" id="oldLoginAccount" name="oldLoginAccount" value="${sysAccount.loginAccount}" />
				</td>
				<th><b class="mustbe">*</b> 请输入用户姓名：</th>
				<td>
					<input type="text" placeholder="格式：如 张三" id="userName" name="userName" value="${sysAccount.userName}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 请选择用户角色：</th>
                <td>
                	<input type="text" class="select" id="roleNames" name="roleNames" value="${userRolemap[sysAccount.userAcctId]}" />
					<input type="hidden" id="userRole" name="userRole" value="${userRoleId}" />
					<ul class="ulRoleList menu" style="display: none;">
						<c:forEach items="${sysRoleList}" var="sysRole" varStatus="state">
							<c:set var="isDoing" value="0" />
							<c:forEach items="${userRoleList}" var="userRole" varStatus="state1">
								<c:if test="${userRole.roleId==sysRole.roleId}">
									<li>
										<label class="check-label">
											<i value="${sysRole.roleId}" class="check_btn active"></i>
											<input type="checkbox" name="userRoleName" roleName="${sysRole.roleName}" id="${sysRole.roleId}" value="${sysRole.roleId}" style="display: none;" checked />
											${sysRole.roleName}
										</label>
									</li>
									<c:set var="isDoing" value="1" />
								</c:if>
							</c:forEach>
							<c:if test="${isDoing!='1'}">
								<li>
									<label class="check-label">
										<i value="${sysRole.roleId}" class="check_btn"></i>
										<input type="checkbox" name="userRoleName" roleName="${sysRole.roleName}" id="${sysRole.roleId}" value="${sysRole.roleId}" style="display: none;" />
										${sysRole.roleName}
									</label>
								</li>
							</c:if>
						</c:forEach>
					</ul>
                </td>
				<th><b class="mustbe">*</b> 请选择性别：</th>
				
				<td>
					<gsww:checkboxTag name="userSex" type="XB" inputType="radio" value="${sysAccount.userSex}"></gsww:checkboxTag>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 请选择出生日期：</th>
				<td>
					<input tabindex="5" type="text" class="input" name="userBirthday" value="${sysAccount.userBirthday}" onClick="WdatePicker()"  ><i class="form-icon-clear" ></i>
	            </td>
	        	<th>请输入用户毕业学校：</th>
	        	<td>
	        		<input tabindex="6" type="text" class="input" name="userSchool" value="${sysAccount.userSchool}" />
	            	<i class="form-icon-clear"></i>
	        	</td>
			</tr>
			<tr>
				<th>请输入学历：</th>
				<td>
					<input type="text" tabindex="7" class="input" name="userEducation" value="${sysAccount.userEducation}" />
	            	<i class="form-icon-clear"></i>
	            </td>
	        	<th>请输入职位：</th>
	        	<td>
	        		<input type="text" tabindex="7" class="input" name="userPostion" value="${sysAccount.userPostion}" />
	            	<i class="form-icon-clear"></i>
	        	</td>
			</tr>
			<tr>
				<th>请输入身份证号：</th>
				<td>
					<input type="text" tabindex="9" class="input" name="userIdentityCode" value="${sysAccount.userIdentityCode}" />
	            	<i class="form-icon-clear"></i>
				</td>
				<th>请输入用户住址：</th>
				<td>
					<input type="text" tabindex="10" class="input" name="userAddress" value="${sysAccount.userAddress}" />
	            	<i class="form-icon-clear"></i>
				</td>
			</tr>
			<tr>
				<th>请输入邮编：</th>
				<td>
					<input type="text" tabindex="11" class="input" name="userPostcode" value="${sysAccount.userPostcode}" />
	            	<i class="form-icon-clear"></i>
				</td>
				<th>请输入联系电话：</th>
				<td>
					<input type="text" tabindex="12" class="input" name="userTele" value="${sysAccount.userTele}"  />
	            	<i class="form-icon-clear"></i>
				</td>
			</tr>
			<tr>
				<th>请输入用户固定电话：</th>
				<td>
					<input type="text" tabindex="12" class="input" name="userTele" value="${sysAccount.userTele}"  />
	            	<i class="form-icon-clear"></i>
				</td>
				<th>请输入用户邮箱：</th>
				<td>
					<input type="text" tabindex="14" class="input" name="userEmail" value="${sysAccount.userEmail}"  />
	            	<i class="form-icon-clear"></i>
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
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/sys/accountList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
