


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
var corNameInput=$("#name").val();
 $("#editForm").validate({
    rules: {
	   name: {
	    required: true,
	    cnRangelength: [0,33],
	    stringCheck:corNameInput  
	   },
	   realName:{
	   	required: true	
	   },
	   regNumber: {
	    required: true	
	   },
	   cardNumber:{
	   	isIdCardNo:true,
	   	required:true,
	   	 maxlength: 18
	   },
	   orgNumber:{
	   	required: true
	   },
	   loginName:{
	   	required: true,
	   	uniqueLoginName:true
	   },
	   mobile:{
	   	isMobile:true,
	   	required: true
	   },
	   pwd:{
	   	required: true,
	   	cnRangelength: [6,18]
	   },
	   email:{
	   	required: true,
	   	email:true
	   },
	   phone:{
	   	isPhone:true
	   },
	   nation:{
	   	required: true
	   }
	  }
    });
    // Ajax重命名校验
	$.uniqueValidate('uniqueLoginName', '${ctx}/complat/checkCorporationLoginName', ['loginName','oldLoginName'], '对不起，这个账号重复了');
    //民族
	var nations = ["汉族","壮族","回族","满族","维吾尔族","苗族","彝族","土家族","藏族","蒙古族",
	               "侗族","布依族","瑶族","白族","朝鲜族","哈尼族","黎族","哈萨克族","傣族","畲族",
	               "傈僳族","东乡族","仡佬族","拉祜族","佤族","水族","纳西族","羌族","土族","仫佬族",
	               "锡伯族","柯尔克孜族","景颇族","达斡尔族","撒拉族","布朗族","毛南族","塔吉克族","普米族","阿昌族",
	               "怒族","鄂温克族","京族","基诺族","德昂族","保安族","俄罗斯族","裕固族","乌孜别克族","门巴族",
	               "鄂伦春族","独龙族","赫哲族","高山族","珞巴族","塔塔尔族"];
	var nation = $("#nation");
	for(var i=0;i<nations.length;i++){
		var corNation = document.getElementById("corNation").value;
		if(corNation == nations[i]){
			nation.append("<option value='"+nations[i]+"' selected='selected'>"+nations[i]+"</option>");	
		}else{
			nation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
		}
		
	}
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
				<a>法人管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<c:if test="${corporation.iid==null}">用户新增</c:if>
				<c:if test="${corporation.iid!=null}">用户编辑</c:if>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/corporationSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${corporation.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${corporation.enable}"/>
        <input type="hidden" id="authState" name="authState" value="${corporation.authState}"/>
    	<input type="hidden" id="isAuth" name="isAuth" value="${corporation.isAuth}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    	<input type="hidden" name="corNation" id="corNation" value="${corNation}">
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    <table class="form-table">
		<tr>
			<td class="td_1" rowspan="4" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
			<th> 法人类型：</th>
	       	<td>
	       	 <gsww:checkboxTag name="type" defaultValue="1" type="FRLX" inputType="radio" value="${corporation.type}"></gsww:checkboxTag>
	       	</td>
	       	<th></th>
			<td></td>
		</tr>
		<tr>
			<th><b class="mustbe">*</b> 企业名称：</th>
			 <td width="20%">
				<input type="text" id="name" name="name" value="${corporation.name}"/>
			</td>
        	<th><b class="mustbe">*</b> 企业法人姓名：</th>
               <td>
               	<input type="text"  id="realName" name="realName" maxlength="33" value="${corporation.realName}" />
               </td> 
		</tr>
		<tr>
			<th><b class="mustbe">*</b> 企业法人身份证号：</th>
				<td>
					<input type="text" <c:if test="${corporation.cardNumber != null}">readonly="readonly"</c:if> id="cardNumber" name="cardNumber" value="${corporation.cardNumber}">
	            </td>
				<th> 企业法人民族：</th>
				<td>
					<select id="nation" name="nation">
						<option value="">请选择</option>
					</select>
				</td>
		</tr>
		<tr>
			<th class="td_3"><b class="mustbe">*</b>工商注册号/社会信用代码：</th>
	        	 <td class="td_4">
					<input type="text" <c:if test="${corporation.regNumber != null}">readonly="readonly"</c:if> id="regNumber" name="regNumber" value="${corporation.regNumber}" />
				</td>
	            <c:if test="${corporation.iid==null}">
					<th class="td_5"> 组织机构代码：</th>
					<td class="td_6">
						<input type="text"  id="orgNumber" name="orgNumber" value="${corporation.orgNumber}" style=""/>
					</td>
				</c:if>
		</tr>
		
		
		<tr>
			<td class="td_2" rowspan="4" tyle="max-width:0px;width:100px;ont-weight:bold;" align="center"">账户信息</td>
			<th><b class="mustbe">*</b>用户名：</th>
			<td>
				<input type="text"  id="loginName" name="loginName" value="${corporation.loginName}" />
            	<input type="hidden"  id="oldLoginName" name="oldLoginName" value="${corporation.loginName}" />
            </td>
        	<th><b class="mustbe">*</b> 手机号码：</th>
        	<td>
        		<input type="text" id="mobile" name="mobile" value="${corporation.mobile}" />
        	</td>
		</tr>
		<tr>
			<th> 密码：</th>
        	<td>
        		<input type="password" id="pwd" name="pwd"  value="${corporation.pwd}" onkeyup="javascript:EvalPwd(this.value);"/>
            	
        	</td>
			<th>邮箱：</th>
			<td>
				<input type="text" class="input" id="email" name="email" value="${corporation.email}" />
            	<i class="form-icon-clear"></i>
			</td>
		</tr>
		<tr>
			<th>密码强度：</th>
			<td>
				<table id="pwdpower" style="width: 84%" cellspacing="0"
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
