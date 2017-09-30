



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
		    isCorporName:true  
		   },
		   realName:{
		   	required: true,
		   	cnRangelength: [0,33],
		   	isCorporName:true	
		   },
		   cardNumber:{
		   	isIdCardNo:true,
		   	required:true,
		   	maxlength: 18
		   },
		   loginName:{
		   	required: true,
		   	cnRangelength: [0,33],
		   	isCorporName:true,
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
		$.uniqueValidate('uniqueMobile', '${ctx}/complat/uniqueCorporationMobile', ['mobile','oldMobile'], '对不起，工商注册号/社会信用代码重复了');  
	    //民族及页面初始化
		var nations = ["汉族","壮族","回族","满族","维吾尔族","苗族","彝族","土家族","藏族","蒙古族",
		               "侗族","布依族","瑶族","白族","朝鲜族","哈尼族","黎族","哈萨克族","傣族","畲族",
		               "傈僳族","东乡族","仡佬族","拉祜族","佤族","水族","纳西族","羌族","土族","仫佬族",
		               "锡伯族","柯尔克孜族","景颇族","达斡尔族","撒拉族","布朗族","毛南族","塔吉克族","普米族","阿昌族",
		               "怒族","鄂温克族","京族","基诺族","德昂族","保安族","俄罗斯族","裕固族","乌孜别克族","门巴族",
		               "鄂伦春族","独龙族","赫哲族","高山族","珞巴族","塔塔尔族"];
		var type = $('input:radio[name="type"]:checked').val();
		if(type == 1){
			var qyNation = $("#qyNation");
			for(var i=0;i<nations.length;i++){
				qyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					qyNation.attr("disabled","disabled");
				}
			}
			
			//企业标题显示隐藏
			$(".fqyBusName").hide();
			$(".fqyUserInfo").hide();
			$(".qyBusName").show();
			$(".qyUserInfo").show();
			showOrHideRegNumber();
		}else{
			var fqyNation = $("#fqyNation");
			for(var i=0;i<nations.length;i++){
				fqyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					fqyNation.attr("disabled","disabled");
				}
			}
			
			$(".qyBusName").hide();
			$(".qyUserInfo").hide();
			$(".fqyBusName").show();
			$(".fqyUserInfo").show();
			$(".userScope").hide();
			$(".regNum").hide();
			$(".td_regNum").hide();
			
		}
	
		//编辑页面用户属性不显示
		var userId = $("#iid").val();
		if(userId != ""){
			$(".userType").hide();
		}
		
		
		 //企业名称和法人名称校验     
	     jQuery.validator.addMethod("isCorporName", function(value, element) { 
	            var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/;   
	            return this.optional(element) || (corporName.test(value));     
	     }, "名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾"); 
	    
	});

	
	
	//企业法人 页面初始化  
	function changeQYTitle(){
		$(".fqyBusName").hide();
		$(".fqyUserInfo").hide();
		$(".qyBusName").show();
		$(".qyUserInfo").show();
	}
	
	//非企业法人页面初始化  
	function changeFQYTitle(){
		  //民族及页面初始化
		var nations = ["汉族","壮族","回族","满族","维吾尔族","苗族","彝族","土家族","藏族","蒙古族",
		               "侗族","布依族","瑶族","白族","朝鲜族","哈尼族","黎族","哈萨克族","傣族","畲族",
		               "傈僳族","东乡族","仡佬族","拉祜族","佤族","水族","纳西族","羌族","土族","仫佬族",
		               "锡伯族","柯尔克孜族","景颇族","达斡尔族","撒拉族","布朗族","毛南族","塔吉克族","普米族","阿昌族",
		               "怒族","鄂温克族","京族","基诺族","德昂族","保安族","俄罗斯族","裕固族","乌孜别克族","门巴族",
		               "鄂伦春族","独龙族","赫哲族","高山族","珞巴族","塔塔尔族"];
	
			var fqyNation = $("#fqyNation");
			for(var i=0;i<nations.length;i++){
				fqyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					fqyNation.attr("disabled","disabled");
				}
			}
			
			$(".qyBusName").hide();
			$(".qyUserInfo").hide();
			$(".fqyBusName").show();
			$(".fqyUserInfo").show();
			$(".userScope").hide();
			$(".regNum").hide();
			$(".td_regNum").hide();
			
	}
	
	//提交表单,并校验企业工商注册号或统一社会信用代码、组织机构代码  
	function checkOrgNumber(){
		var type = $('input:radio[name="type"]:checked').val();
		if(type == 1){
			var regNumber = $("#regNumber").val(); //企业工商注册号或统一社会信用代码	  
			var orgNumber = $("#orgNumber").val(); //组织机构代码  
			if(regNumber == null || regNumber == ""){
					alert("请填写企业工商注册号或统一社会信用代码！");
					return false;
			}
			if(regNumber.substring(0, 1) == '9' && regNumber.length == 18){
				$("#editForm").submit();
			}else{
				if(orgNumber == null || orgNumber == ""){
					alert("请填写组织机构代码！");
					return false;
				}
				if(orgNumber.length != 10 || orgNumber.substring(8, 9) != '-'){
					alert("组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
					return false;
				}else{
					$("#editForm").submit();
				}
			}
		}else{
			var orgNumber = $("#orgNumber").val(); //组织机构代码
			if(orgNumber == null || orgNumber == ""){
					alert("请填写组织机构代码！");
					return false;
			}
			if (orgNumber.indexOf("-") != -1) {
				$("#orgNumber").attr("name", "orgNumber");
				if (orgNumber.length != 10 || orgNumber.substring(8, 9) != '-') {
					alert("组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
					return false;
				}
			}else{
				$("#orgNumber").attr("name", "regNumber");
				if ( orgNumber.length != 18) {
					alert("组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
					return false;
				}else{
					$("#editForm").submit();
				}
			}
			
		}
	}
	/**
	 *企业工商注册号或统一社会信用代码  信用代码是18位， 工商码为13或15位 ，输入工商码显示：组织机构代码
	 */
	$(function() {
		$("#regNumber").bind(
				"input propertychange",
				function() {
					if ($(this).val().substring(0, 1) == '9'
							&& $(this).val().length == 18) {
						$(".orgNum").hide();
						$("#orgNumber").hide();
					} else {
						$(".orgNum").show();
						$("#orgNumber").show();
					}
				});

	});

	function showOrHideRegNumber() {
		var orgNumber = $("#regNumber").val();
		if (orgNumber.substring(0, 1) == '9' && orgNumber.length == 18) {
			$(".orgNum").hide();
			$("#orgNumber").hide();
		} else {
			$(".orgNum").show();
			$("#orgNumber").show();
		}
	}

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
				<a>公网用户</a>
			</li>
			<li class="split"></li>
			<li>
				<a>法人用户</a>
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
    
    <div style="display: none;">
    	<input type="hidden" id="iid" name="iid" value="${corporation.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${corporation.enable}"/>
        <input type="hidden" id="authState" name="authState" value="${corporation.authState}"/>
    	<input type="hidden" id="isAuth" name="isAuth" value="${corporation.isAuth}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    	<input type="hidden" name="corNation" id="corNation" value="${corNation}">
    	<input type="hidden" name="operSign" id="operSign" value="${corporation.operSign}">
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    <table class="form-table">
		<tr>
			<c:if test = "${corporation.iid != null}">
				<td class="td_1" rowspan="4" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
			</c:if>
			<c:if test = "${corporation.iid == null}">
				<td class="td_1" rowspan="5" style="max-width:0px;width:100px;ont-weight:bold;" align="center">基本属性</td>
			</c:if>
		</tr>
		<tr class="userType">
			<th> 法人类型：</th>
	       	<td style="width:300px;">
	       	 <input type="radio" id="qyRadio" name="type" 
	       	  	<c:if test="${corporation.type == 1}">checked="checked"</c:if> 
	       	  	<c:if test="${corporation.type == null}">checked="checked"</c:if> 
	       	  	onclick = "changeQYTitle();" value="1"/>企业法人&nbsp;&nbsp; 
	       	 <input type="radio" id="fqyRadio" name="type" <c:if test="${corporation.type == 2}">checked</c:if>  value="2" onclick="changeFQYTitle();"/>非企业法人
	       	</td>
	       	<th></th>
			<td></td>
		</tr>
		<tr class="qyBusName">
			<th style="width: 10%"><b class="mustbe">*</b>企业名称：</th>
			 <td style="width:300px;">
				<input type="text" id="qyName" name="qyName" value="${corporation.name}" maxlength="33"/>
			</td>
        	<th><b class="mustbe">*</b>企业法人姓名： </th>
               <td style="width:300px;">
               	<input type="text"  id="qyRealName" name="qyRealName" maxlength="33" value="${corporation.realName}" />
               </td> 
		</tr>
		<tr class="qyUserInfo">
			<th><b class="mustbe">*</b>企业法人身份证号码： </th>
				<td style="width:300px;">
					<input type="text" <c:if test="${corporation.cardNumber != null}">readonly="readonly"</c:if> id="qyCardNumber" name="qyCardNumber" value="${corporation.cardNumber}">
	            </td>
			<th class="userNation"><b class="mustbe">*</b>企业法人民族：</th>
			<td style="width:300px;">
				<select id="qyNation" name="qyNation" style="width: 86%"></select>
			</td>
		</tr>
		<tr class="fqyBusName">
			<th style="width: 10%"><b class="mustbe">*</b>机构名称：</th>
			<td style="width:300px;">
				<input type="text" id="fqyName" name="fqyName" value="${corporation.name}" maxlength="33"/>
			</td>
        	<th><b class="mustbe">*</b>机构负责人姓名： </th>
            <td style="width:300px;">
            	<input type="text"  id="fqyRealName" name="fqyRealName" maxlength="33" value="${corporation.realName}" />
            </td> 
		</tr>
		<tr class="fqyUserInfo">
			<th><b class="mustbe">*</b>机构负责人身份证号码： </th>
				<td style="width:300px;">
					<input type="text" <c:if test="${corporation.cardNumber != null}">readonly="readonly"</c:if> id="fqyCardNumber" name="fqyCardNumber" value="${corporation.cardNumber}">
	            </td>
			<th><b class="mustbe">*</b>机构负责人民族：</th>
			<td style="width:300px;">
				<select id="fqyNation" name="fqyNation" style="width: 86%"></select>
			</td>
		</tr>
		<tr>
			<th class="td_3"><b class="mustbe">*</b>工商注册号/社会信用代码：</th>
        	<td style="width:300px;">
				<input type="text" <c:if test="${corporation.regNumber != null}">readonly="readonly"</c:if> id="regNumber" name="regNumber" value="${corporation.regNumber}" />
				<input type="text" id="oldRegNumber" name="oldRegNumber" value="${corporation.regNumber}" />
			</td>
			<th> 组织机构代码：</th>
			<td style="width:300px;">
				<input type="text"  id="orgNumber" name="orgNumber" value="${corporation.orgNumber}"/>
			</td>
		</tr>
		<tr>
			<td class="td_2" rowspan="4" tyle="max-width:0px;width:100px;ont-weight:bold;" align="center">账户信息</td>
			<th><b class="mustbe">*</b>用户名：</th>
			<td style="width:300px;">
				<input type="text"  id="loginName" name="loginName" value="${corporation.loginName}" maxlength="33"/>
       			<input type="hidden"  id="oldLoginName" name="oldLoginName" value="${corporation.loginName}" />
     		 </td>
		     <th><b class="mustbe">*</b> 手机号码：</th>
		     <td style="width:300px;">
		    	<input type="text" id="mobile" name="mobile" value="${corporation.mobile}" />
		    	<input type="text" id="oldMobile" name="oldMobile" value="${corporation.mobile}" />
		     </td>
		</tr>
		<tr>
			<th> 密码：</th>
        	<td style="width:300px;">
        		<input type="password" id="pwd" name="pwd"  value="${corporation.pwd}" onkeyup="javascript:EvalPwd(this.value);"/>
            	
        	</td>
			<th><b class="mustbe">*</b>邮箱：</th>
			<td style="width:300px;">
				<input type="text" class="input" id="email" name="email" value="${corporation.email}" />
            	<i class="form-icon-clear"></i>
			</td>
		</tr>
		<tr>
			<th>密码强度：</th>
			<td style="width:300px;">
				<table id="pwdpower" style="width: 86%" cellspacing="0"
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
			<td style="width:300px;">
				<input type="text" id="phone" name="phone" value="${corporation.phone}" />
			</td>
		</tr>
		<tr class="userScope">
			<th>经营范围：</th>
			<td style="width:300px;">
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
    	<input type="button" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"  onclick="checkOrgNumber();"/>
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
