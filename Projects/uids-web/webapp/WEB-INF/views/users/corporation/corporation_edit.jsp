



<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgdialog.js"></script>
<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
$().ready(function() {

	//表单校验
	 $("#editForm").validate({
	    rules: {
		   qyName: {
		    required: true,
		    cnRangelength: [0,33],
		    isCorporName:true  
		   },
		   qyRealName:{
		   	required: true,
		   	cnRangelength: [0,33],
		   	isCorporName:true	
		   },
		   qyCardNumber:{
		   	isIdCardNo:true,
		   	required:true,
		   	maxlength: 18
		   },
		   loginName:{
		   	required: true,
		   	cnRangelength: [0,33],
		   	isCorporName:true
		   	//uniqueLoginName:true
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
		   	isCompTel:true
		   }
		  },submitHandler : function() {								
				$.ajax({
					type : "POST",
					async : false,
					url : '${ctx}/complat/corporationSave',
					data : $("#editForm").serialize(),
					dataType : "json",
					success : function(data) {
						if (data.ret == 0) {
							$.dialog.alert(data.msg);
						} else if (data.ret == 1) {
							 $.dialog.alertSuccess(data.msg,function(){
								 window.location.href="${ctx}/complat/corporationList";
							 });
						}else if(data.ret == 2){
							$.dialog.alert(data.msg);
						}
					}
	   			});
	   		}
	   	});
	    //编辑页面密码强度显示
		var pwding = $("#pwd").val();
		EvalPwd(pwding);
		
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
				
				
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					qyNation.append("<option value='"+nations[i]+"' selected=selected>"+nations[i]+"</option>");
					qyNation.attr("disabled","disabled");
				}else{
					qyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				}
			}
			
			//企业标题显示隐藏
			$(".fqyBusName").hide();
			$(".fqyUserInfo").hide();
			$(".qyBusName").show();
			$(".qyUserInfo").show();
			$("#fqyOrgNumber").hide();
			$("#qyOrgNumber").show();
			showOrHideRegNumber();
		}else{
			var fqyNation = $("#fqyNation");
			for(var i=0;i<nations.length;i++){
				
				
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					fqyNation.append("<option value='"+nations[i]+"' selected=selected>"+nations[i]+"</option>");
					fqyNation.attr("disabled","disabled");
				}else{
					fqyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				}
			}
			
			$(".qyBusName").hide();
			$(".qyUserInfo").hide();
			$(".fqyBusName").show();
			$(".fqyUserInfo").show();
			$(".userScope").hide();
			$(".regNum").hide();
			$(".td_regNum").hide();
			$("#qyOrgNumber").hide();
			$("#fqyOrgNumber").show();
			
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
	      //办公电话
	     jQuery.validator.addMethod("isCompTel", function(value, element) { 
	           var corporName = /^(((0\d{3}[\-])?\d{7}|(0\d{2}[\-])?\d{8}))([\-]\d{2,4})?$/;   
	           return this.optional(element) || (corporName.test(value));     
	     }, "电话号码格式错误");
	    
	});

	
	
	//企业法人 页面初始化  
	function changeQYTitle(){
		$(".fqyBusName").hide();
		$(".fqyUserInfo").hide();
		$(".qyBusName").show();
		$(".qyUserInfo").show();
		$(".regNum").show();
		$(".td_regNum").show();
		$("#fqyOrgNumber").hide();
		$("#qyOrgNumber").show();
		$(".userScope").show();		
		
		//输入字段校验
		$("#fqyName").rules("remove","required");
		$("#fqyName").rules("remove","cnRangelength");
		$("#fqyName").rules("remove","isCorporName");
		$("#fqyRealName").rules("remove","required");
		$("#fqyRealName").rules("remove","cnRangelength");
		$("#fqyRealName").rules("remove","isCorporName");
		$("#fqyCardNumber").rules("remove","required");
		$("#fqyCardNumber").rules("remove","isIdCardNo");
		$("#fqyCardNumber").rules("remove","maxlength");
		$("#qyName").rules("add",{required:true});
		$("#qyName").rules("add",{cnRangelength:[0,33]});
		$("#qyName").rules("add",{isCorporName:true});
		$("#qyRealName").rules("add",{required:true});
		$("#qyRealName").rules("add",{cnRangelength:[0,33]});
		$("#qyRealName").rules("add",{isCorporName:true});
		$("#qyCardNumber").rules("add",{required:true});
		$("#qyCardNumber").rules("add",{isIdCardNo:true});
		$("#qyCardNumber").rules("add",{maxlength:18});
		
		//当点击企业法人再点击非企业法人时，清空文本框内容
		$("#regNumber").val("");
		$("#loginName").val("");
		$("#pwd").val("");
		$("#mobile").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#scope").val("");
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
				var corNation = document.getElementById("corNation").value;
				if(corNation == nations[i]){
					fqyNation.append("<option value='"+nations[i]+"' selected=selected>"+nations[i]+"</option>");
					fqyNation.attr("disabled","disabled");
				}else{
					fqyNation.append("<option value='"+nations[i]+"'>"+nations[i]+"</option>");
				}
			}
			
			$(".qyBusName").hide();
			$(".qyUserInfo").hide();
			$(".fqyBusName").show();
			$(".fqyUserInfo").show();
			$(".userScope").hide();
			$(".regNum").hide();
			$(".td_regNum").hide();
			$(".orgNum").show();
			$(".td_orgNum").show();
			$("#fqyOrgNumber").show();
			$("#qyOrgNumber").hide();
			
			//输入字段校验
			$("#qyName").rules("remove","required");
			$("#qyName").rules("remove","cnRangelength");
			$("#qyName").rules("remove","isCorporName");
			$("#qyRealName").rules("remove","required");
			$("#qyRealName").rules("remove","cnRangelength");
			$("#qyRealName").rules("remove","isCorporName");
			$("#qyCardNumber").rules("remove","required");
			$("#qyCardNumber").rules("remove","isIdCardNo");
			$("#qyCardNumber").rules("remove","maxlength");
			$("#fqyName").rules("add",{required:true});
			$("#fqyName").rules("add",{cnRangelength:[0,33]});
			$("#fqyName").rules("add",{isCorporName:true});
			$("#fqyRealName").rules("add",{required:true});
			$("#fqyRealName").rules("add",{cnRangelength:[0,33]});
			$("#fqyRealName").rules("add",{isCorporName:true});
			$("#fqyCardNumber").rules("add",{required:true});
			$("#fqyCardNumber").rules("add",{isIdCardNo:true});
			$("#fqyCardNumber").rules("add",{maxlength:18});
			
			//当点击非企业法人再点击企业法人时，清空文本框内容
			$("#regNumber").val("");
			$("#loginName").val("");
			$("#pwd").val("");
			$("#mobile").val("");
			$("#email").val("");
			$("#phone").val("");			
	}
	
	//提交表单,并校验企业工商注册号或统一社会信用代码、组织机构代码  
	function checkOrgNumber(){
		var type = $('input:radio[name="type"]:checked').val();
		if(type == 1){
			var regNumber = $("#regNumber").val(); //企业工商注册号或统一社会信用代码	  
			var qyOrgNumber = $("#qyOrgNumber").val(); //组织机构代码  
			if(regNumber == null || regNumber == ""){
				$.dialog.alert("请填写企业工商注册号或统一社会信用代码！");
				return false;
			}
			if(regNumber.length > 18){
				$.dialog.alert("企业工商注册号长度最多为18位！");
				return false;
			}else{
				if(regNumber.substring(0, 1) == '9' && regNumber.length == 18){
					if(!isNumbOrLett(regNumber)){
						$.dialog.alert("企业工商注册号由数字、字母和下划线组成，不能以下划线开头和结尾！");
						return false;
					}else{
						$("#editForm").submit();
					}
				}else{
					if(!isNumbOrLett(regNumber)){
						$.dialog.alert("企业工商注册号由数字、字母和下划线组成，不能以下划线开头和结尾！");
						return false;
					}
					if(qyOrgNumber == null || qyOrgNumber == ""){
						$.dialog.alert("请填写组织机构代码！");
						return false;
					}
					if(qyOrgNumber.length != 10 || qyOrgNumber.substring(8, 9) != '-'){
						$.dialog.alert("组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
						return false;
					}else{
						if(!isNumbOrLett2(qyOrgNumber)){
							$.dialog.alert("组织机构代码由数字、字母、下划线和短横杠组成，不能以下划线和短横杠(“-”)开头和结尾！");
							return false;
						}else{
							$("#editForm").submit();
						}
					}
				}
			}
			
		}else{
			var fqyOrgNumber = $("#fqyOrgNumber").val(); //组织机构代码
			if(fqyOrgNumber == null || fqyOrgNumber == ""){
					$.dialog.alert("请填写组织机构代码！");
					return false;
			}
			if(fqyOrgNumber.length > 18){
				$.dialog.alert("组织机构代码长度最多为18位！");
				return false;
			}
			if (fqyOrgNumber.indexOf("-") != -1) {
				$("#fqyOrgNumber").attr("name", "fqyOrgNumber");
				if (fqyOrgNumber.length != 10 || fqyOrgNumber.substring(8, 9) != '-') {
					$.dialog.alert("组织机构代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
					return false;
				}else{
					if(!isNumbOrLett2(fqyOrgNumber)){
						$.dialog.alert("组织机构代码由数字、字母、下划线和短横杠组成，不能以下划线和短横杠(“-”)开头和结尾！");
						return false;
					}else{
						$("#editForm").submit();
					}
				}
			}else{
				$("#fqyOrgNumber").attr("name", "regNumber");
				if ( fqyOrgNumber.length != 18) {
					$.dialog.alert("组织机构代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）");
					return false;
				}else{
					if(!isNumbOrLett(fqyOrgNumber)){
						$.dialog.alert("组织机构代码由数字、字母和下划线组成，不能以下划线开头和结尾！");
						return false;
					}else{
						$("#editForm").submit();
					}
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
						$("#qyOrgNumber").hide();
					} else {
						$(".orgNum").show();
						$("#qyOrgNumber").show();
					}
				});

	});

	function showOrHideRegNumber() {
		var orgNumber = $("#regNumber").val();
		if (orgNumber.substring(0, 1) == '9' && orgNumber.length == 18) {
			$(".orgNum").hide();
			$("#qyOrgNumber").hide();
		} else {
			$(".orgNum").show();
			$("#qyOrgNumber").show();
		}
	}
	
	
	function isNumbOrLett( s ){
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function isNumbOrLett2( s ){
		var regu = /^(?!_)(?!.*?_$)(?!-)(?!.*?-$)[a-zA-Z0-9_-]+$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
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
.rules{
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
			<li class="active">
				公网用户
			</li>
			<li class="split"></li>
			<li class="active">
				法人用户
			</li>
			<li class="split"></li>
			<li class="active">
				<c:if test="${corporation.iid==null}">用户新增</c:if>
				<c:if test="${corporation.iid!=null}">用户编辑</c:if>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm">
    
    <div style="display: none;">
    	<input type="hidden" id="iid" name="iid" value="${corporation.iid}"/>
    	<input type="hidden" id="enable" name="enable" value="${corporation.enable}"/>
        <input type="hidden" id="authState" name="authState" value="${corporation.authState}"/>
    	<input type="hidden" id="isAuth" name="isAuth" value="${corporation.isAuth}"/>
    	<input type="hidden" id="time" name="time" value="${time}">
    	<input type="hidden" name="corNation" id="corNation" value="${corNation}">
    	<input type="hidden" name="operSign" id="operSign" value="${corporation.operSign}">
    	<input type="hidden" id="level">
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
		<tr class="rules">
			<th class="regNum"><b class="mustbe">*</b>工商注册号/社会信用代码：</th>
        	<td style="width:300px;" class="td_regNum">
				<input type="text" <c:if test="${corporation.regNumber != null}">readonly="readonly"</c:if> id="regNumber" name="regNumber" value="${corporation.regNumber}" />
			</td>
			<th class="orgNum"><b class="mustbe">*</b> 组织机构代码：</th>
			<td style="width:300px;" class="td_orgNum">
				<input type="text"  id="qyOrgNumber" name="qyOrgNumber" value="${corporation.orgNumber}"/>
				<input type="text"  id="fqyOrgNumber" name="fqyOrgNumber" 
					<c:if test="${corporation.orgNumber != null}">
						value="${corporation.orgNumber}"
					</c:if>
					<c:if test="${corporation.orgNumber == null && corporation.regNumber != null}">
						value="${corporation.regNumber}" readonly = "readonly"
					</c:if>
				/>
			</td>
			<td><input type="hidden"></td>
			<td><input type="hidden"></td>
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
		     </td>
		</tr>
		<tr>
			<th><b class="mustbe">*</b> 密码：</th>
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
