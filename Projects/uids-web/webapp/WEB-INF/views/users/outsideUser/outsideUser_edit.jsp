
<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
$().ready(function() {
	//表单校验
	$("#editForm").validate({
		rules: {
			loginName : {//重命名校验*
				required: true,
				cnRangelength: [0,127],
				isLoginName : true,
				uniqueLoginName: true 
			},
		   	pwd : {
				required: true,
				cnRangelength: [6,18]
			},
			name : {
				required: true,
				cnRangelength: [0,32],
		    	isName: true
			},
			email : {//email校验
				required: true,
				email:true,
		   		maxlength: 64
			},
			mobile : {//移动电话
				required: true,
				isPhone:true,
		   		uniqueMobile:true
			},
			residenceDetail : {
				required: true,
				cnRangelength: [0,127],
				isName:true
				/* isResidenceDetail：true */
				
			},
			livingAreaDetail : {
				required: true,
				/* isAddressInfo：true, */
				cnRangelength: [0,127],
				isName:true
			},
			sex : {
				required: true
			},
			papersNumber : {
				required: true,
				isIdCardNo:true,
				uniquePapersNumber:true
			},
			age:{
		   		cnRangelength: [0,64]
		   	},
			degree:{//学历
		   		//cnRangelength: [0,64]
		   	},
			workUnit:{
		   		cnRangelength: [0,64]
		   	},
			headShip:{
		   		cnRangelength: [0,64]
		   	},
			fax:{
		   		cnRangelength: [0,64]
		   	},
			phone:{
		   		isMobile:true,
		   		maxlength: 16
		   	},
			compTel:{
		   		isPhone:true,
		   		maxlength: 16
		   	},
			qq:{
		   		maxlength: 11
		   	},
			msn:{
		   		cnRangelength: [0,64]
		   	},
			post:{
				/* isEmail：true, */
		   		maxlength: 6,
		   		isPost:true
		   	},
			address:{
				/* isAddressInfo:true, */
		   		cnRangelength: [0,127]
		   	},
		   	submitHandler:function(form){
				form.submit();
			}
		}
	});
	// Ajax重命名校验
	$.uniqueValidate('uniqueLoginName', '${ctx}/complat/checkOutisideUserLoginName', ['loginName','oldLoginName'], '对不起，这个登录名重复了');
	$.uniqueValidate('uniqueMobile', '${ctx}/complat/checkOutisideUserMobile', ['mobile','oldMobile'], '对不起，这个手机号重复了');
	$.uniqueValidate('uniquePapersNumber', '${ctx}/complat/checkOutisideUserPapersNumber', ['papersNumber','oldPapersNumber'], '对不起，这个身份证号重复了');
	//个人用户名校验     
    jQuery.validator.addMethod("isName", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾");
    
    jQuery.validator.addMethod("isLoginName", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "名称只能由字母、数字、下划线组成，不能以下划线开头和结尾");
    
    jQuery.validator.addMethod("isPost", function(value, element) { 
           var corporName = /^[1-9][0-9]{5}$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "邮政编码格式不正确（共6位,开头不能为0)");
    
    $('#pwd').attachEvent('oninput',EvalPwd(this));
});
</script>

<style>
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
				<a>公网用户</a>
			</li>
			<li class="split"></li>
			<li>
				<a>个人用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty outsideUser.iid}">用户新增</c:if><c:if test="${not empty outsideUser.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/outsideuserSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${outsideUser.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${outsideUser.enable}"/>
    	<input type="hidden" id="authState" name="authState" value="${outsideUser.authState}"/>
    	<input type="hidden" id="isAuth" name="isAuth" value="${outsideUser.isAuth}"/>
    	<input type="hidden" id="time" name="time" value="${time}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
			<tr>
				<td class="td_1" rowspan="4" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
				<th><b class="mustbe">*</b>登录名：</th>
				<td style="width:300px;">
					<input type="text" id="loginName" name="loginName" value="${outsideUser.loginName}" />
					<input type="hidden" id="oldLoginName" name="oldLoginName" value="${outsideUser.loginName}" />
	            </td>
	        	<th><b class="mustbe">*</b>密码：</th>
				<td style="width:300px;">
					<input type="password" id="pwd" class="pwd" name="pwd" value="${outsideUser.pwd}" onkeyup="javascript:EvalPwd(this.value);"/>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>姓名：</th>
				<td style="width:300px;">
					<input type="text"  class="name" name="name" value="${outsideUser.name}" />
				</td>
				<th>密码强度：</th>
				<td style="width:300px;">
					<table id="pwdpower" style="width: 86%" cellspacing="0"
					cellpadding="0" border="0">
						<tbody>
							<tr>
								<td id="pweak" style="text-align: center;width: 100px;border: 1px solid gray;">弱</td>
								<td id="pmedium" style="text-align: center;width: 100px;border: 1px solid gray;">中</td>
								<td id="pstrong" style="text-align: center;width: 100px;border: 1px solid gray;">强</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>邮箱：</th>
				<td style="width:300px;">
					<input type="text"  class="email" name="email" value="${outsideUser.email}" />
				</td>
				<th><b class="mustbe">*</b>手机号码：</th>
				<td style="width:300px;">
					<input type="text"  id="mobile" name="mobile" value="${outsideUser.mobile}" />
					<input type="hidden" id="oldMobile" name="oldMobile" value="${outsideUser.mobile}" />
				</td>
			</tr>
			<tr>
				<th class="td_5"><b class="mustbe">*</b>户籍详细地址：</th>
				<td class="td_3" style="width:300px;">
					<input type="text"  class="residenceDetail" name="residenceDetail" value="${outsideUser.residenceDetail}" />
	            </td>
	            <th class="td_6"><b class="mustbe">*</b>居住地详细地址：</th>
				<td class="td_4" style="width:300px;">
					<input type="text"  class="livingAreaDetail" name="livingAreaDetail" value="${outsideUser.livingAreaDetail}" />
				</td>
			</tr>
			
			<tr>
				<td class="td_2" rowspan="7" tyle="max-width:0px;width:100px;ont-weight:bold;" align="center"">详细属性</td>
				<th><b class="mustbe">*</b>性别：</th>
				<td style="width:300px;">
					<%-- <gsww:checkboxTag name="type" defaultValue="1" type="ZFYHXB" inputType="radio" value="${outsideUser.sex}"></gsww:checkboxTag> --%>
					<input type="radio" name="sex" value = '男' <c:if test="${outsideUser.sex == '男'}">checked="checked" </c:if>>男&nbsp&nbsp&nbsp
    				<input type="radio" name="sex" value = '女' <c:if test="${outsideUser.sex == '女'}">checked="checked" </c:if>>女
				</td>
				<th>年龄：</th>
				<td style="width:300px;">
					<input type="text"  class="age" name="age" value="${outsideUser.age}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>身份证号：</th>
				<td style="width:300px;">
					<input type="text" <c:if test="${outsideUser.papersNumber != null}">readonly="readonly"</c:if> id="papersNumber" class="papersNumber" name="papersNumber" value="${outsideUser.papersNumber}" />
					<input type="hidden" id="oldPapersNumber" class="oldPapersNumber" name="oldPapersNumber" value="${outsideUser.papersNumber}" />
				</td>
				<th>学历：</th>
				<td style="width:300px;">
					<select name="degree" value="${outsideUser.degree}" style="width: 86%">
						<option value="">请选择学历</option>
						<option value='其他' <c:if test="${outsideUser.degree == '其他'}">selected</c:if>>其他</option>
						<option value='小学' <c:if test="${outsideUser.degree == '小学'}">selected</c:if>>小学</option>
						<option value='初中' <c:if test="${outsideUser.degree == '初中'}">selected</c:if>>初中</option>
						<option value='高中' <c:if test="${outsideUser.degree == '高中'}">selected</c:if>>高中</option>
						<option value='专科' <c:if test="${outsideUser.degree == '专科'}">selected</c:if>>专科</option>
						<option value='大学' <c:if test="${outsideUser.degree == '大学'}">selected</c:if>>大学</option>
						<option value='硕士' <c:if test="${outsideUser.degree == '硕士'}">selected</c:if>>硕士</option>
						<option value='博士' <c:if test="${outsideUser.degree == '博士'}">selected</c:if>>博士</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>工作单位：</th>
				<td style="width:300px;">
					<input type="text"  class="workUnit" name="workUnit" value="${outsideUser.workUnit}" />
				</td>
				<th>职务：</th>
				<td style="width:300px;">
					<input type="text"  class="headShip" name="headShip" value="${outsideUser.headShip}" />
				</td>
			</tr>
			<tr>
				<th>传真：</th>
				<td style="width:300px;">
					<input type="text"  class="fax" name="fax" value="${outsideUser.fax}" />
				</td>
				<th>固定电话：</th>
				<td style="width:300px;">
					<input type="text"  class="phone" name="phone" value="${outsideUser.phone}" />
				</td>
			</tr>
			<tr>
				<th>办公电话：</th>
				<td style="width:300px;">
					<input type="text"  class="compTel" name="compTel" value="${outsideUser.compTel}" />
				</td>
				<th>QQ：</th>
				<td style="width:300px;">
					<input type="text"  class="qq" name="qq" value="${outsideUser.qq}" />
				</td>
			</tr>
			<tr>
				<th>MSN：</th>
				<td style="width:300px;">
					<input type="text"  class="msn" name="msn" value="${outsideUser.msn}" />
				</td>
				<th>邮编：</th>
				<td style="width:300px;">
					<input type="text"  class="post" id="post" name="post" value="${outsideUser.post}" />
				</td>
			</tr>
			<tr>
				<th>联系地址：</th>
				<td style="width:300px;">
					<input type="text"  class="address" name="address" value="${outsideUser.address}" />
				</td>
				<th></th>
				<td></td>
			</tr>
		</table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/outsideuserList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
