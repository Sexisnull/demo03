<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/ui/css/global.css"/> --%>
<script type="text/javascript" src="${ctx}/ui/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.parser.js"></script>
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/linkbutton.css"/> --%>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.linkbutton.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.resizable.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.draggable.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/panel.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.panel.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/window.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.window.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/messager.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.messager.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/hanweb/validity/css/validity.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/validity/validity.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/validity/validity.js"></script>
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/icomoon/style.css"/> --%>
<!--[if IE 8]>
<link type="text/css" rel="stylesheet" href="/gsjis/ui/lib/icomoon/style-ie8.css"/>
<![endif]-->
<!--[if lte IE 8]>
<script type="text/javascript" src="/gsjis/ui/lib/icomoon/lte-ie7.js"></script>
<![endif]-->
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/scrollup/css/themes/image.css"/> --%>
<script type="text/javascript" src="${ctx}/ui/widgets/scrollup/js/jquery.scrollUp.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/ie6fixed/ie6fixed.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/cookie/jquery.cookie.js"></script>
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/ui/css/page.css"/> --%>
<script type="text/javascript" src="${ctx}/ui/script/ui.js"></script>
<script type="text/javascript" src="${ctx}/ui/script/page.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/checkpwd/css/checkpwd.css"/>
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法人信息修改</title>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/frzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/style.css"/>
<script type="text/javascript" src="${ctx}/ui/script/idcardValidity.js"></script>
<script>
	var levelNum;
	function trim(str){
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}//过滤左右空格
	
	function match(){
		password = trim($('#password').val());
		password2 = trim($('#password2').val());
		if (password == password2){
			return true;
		} else {
			return false;
		}
	}

	window.alert = function (msg,type,fu){
		top.$.messager.alert(' ',msg,type,fu);
	};
	
	$.validity.setup({
		outputMode : "showErr"
	}); //校验错误弹出

	window.confirm = function(msg,okCall,cancelCall){
		top.$.messager.confirm(' ',msg,function(flag){
			if(flag){
				if(typeof(okCall) != 'undefined'){
					okCall();
				}
			}else{
				if(typeof(cancelCall) != 'undefined'){
					cancelCall();
				}
			}
		});
	};
	$(function(){
		
	//	var nation222= '${nation}';
	//	var sex = '${sex}';
		
	//	alert(nation222+sex);
		var gpresidenceId='${gpresidenceId}';
		var presidenceId='${presidenceId}';
		var residenceId='${residenceId}';  //这个引号要加
		
		var  livingAreaId='${livingAreaId}';
		var  plivingAreaId='${plivingAreaId}';
		var  gplivingAreaId='${gplivingAreaId}';	
	
			
		//李德隆20160110 日曜 残业 独 访问本网页，自动加载省份。20160113夜大修
		/*
		var selProvince = document.getElementById('selProvince');	
		var CselProvince = document.getElementById('CselProvince');	
		var selCity = document.getElementById('selCity');	
		var CselCity = document.getElementById('CselCity');	
		var selTown = document.getElementById('selTown');	
		var CselTown = document.getElementById('CselTown');			
	  //  var urldl = ${contextPath}; 如何打印出这个contextPath
	  */
	 	
	 	
		//加载户籍三项的下拉列	 	
	 	searchSubZoneByIidForHtml(0,"selProvince",gpresidenceId);
	 	searchSubZoneByIidForHtml(gpresidenceId,"selCity",presidenceId);
	 	searchSubZoneByIidForHtml(presidenceId,"selTown",residenceId);	 	
	 
	 	//加载常住三项的下拉列	 
	 	searchSubZoneByIidForHtml(0,"CselProvince",gplivingAreaId);
	 	searchSubZoneByIidForHtml(gplivingAreaId,"CselCity",plivingAreaId);
	 	searchSubZoneByIidForHtml(plivingAreaId,"CselTown",livingAreaId);
		
		//nation
		/*if(${isauth} == 0){
			if(${type} == 1){
				var select = document.getElementById("nation1");  
				if('${sex}' == '女'){
					$("#female1").attr("checked","checked");
				}else{
					$("#male1").attr("checked","checked");
				}
			}else{
				if('${sex}' == '女'){
					$("#female2").attr("checked","checked");
				}else{
					$("#male2").attr("checked","checked");
				}
				var select = document.getElementById("nation4");  
			}
			for(var i=0; i<select.options.length; i++){  
			    if(select.options[i].innerHTML == '${nation}'){  
			        select.options[i].selected = true;  
			        break;  
			    }  
			} 
		}else{
			*/
		
			
			$(".baseinfo").attr("readonly","readonly");
		//}
		
		$("#loginId").focus();
		$('#registerform').validity(function(){
			var type=$("#type").val();
			if(type == '非企业法人'){
				$('#name2').require('请填写机构名称');
				$('#orgNumber').require('请填写组织机构代码');
				$('#realName2').require('请填写机构负责人姓名');
			}else{
				$('#name').require('请填写企业名称');
				$('#regNumber').require('请填写企业工商注册号');
				$('#realName').require('请填写企业法人姓名');	
			}
			var password = $("#password").val();
			if(password != "" && password.length>0){
				$('#password').minLength(6,'密码不少于6位').maxLength(18, "密码最多18个字");
				$('#password2').require('请填写确认密码').assert(match(),'两次输入密码不一致，请正确填写');
			}
			$('#mobile').require('请填写移动电话').match('mobile','对不起，您输入的移动电话错误');
			$('#email').require('请填写电子邮箱').match('email','对不起，您输入的电子邮箱不合法');
			/*朱工要求暂时隐藏户籍与常住地的表单以及脚本校验20160129
			$('#selTown').require('请填写户籍区县');	
			$('#selCity').require('请填写户籍市');
			$('#selProvince').require('请填写户籍省');			
			$('#residenceDetail').require('请填写户籍所在详细地址');
			$('#CselTown').require('请填写居住地区县');
			$('#CselCity').require('请填写居住地市');
			$('#CselProvince').require('请填写居住省');
			$('#livingAreaDetail').require('请填写居住地详细地址 ');
			*/
			$('#randCode').require('请填写验证码');
			if("${regType}" == "1"){
				$('#emailrandcode').require("请填写邮箱验证码");
			}else if("${regType}" == "2"){
				//$('#phonerandcode').require("请填写短信验证码");
			}
		},{
			success:function(result){
				if(result.success){
					alert("修改成功！");
					//window.location.href='corindex.do';
				}else{
					alert(result.message);
					$('#verifyImg').click();
				}
			}
		});
		$(".fqyfr").hide();
		if(${type} == 1){
			$(".fqyfr").hide();
			$(".qyfr").show();
		}else{
			$(".fqyfr").show();
			$(".qyfr").hide();
		}
		
	});

	function sexValidate(sex, idcard){
		if('${isauth}' == '1'){
			if('${type}' == 1){
				sex = $("#sex2").val();
			}else{
				sex = $("#sex3").val();
			}
		}
		var s;
		if('男' == sex){
			s = 'male';
		}else{
			s = 'female';
		}
		if(s == maleOrFemalByIdCard(idcard)){
			return true;
		}else{
			return false;
		}
	}
	
	function checkLoginId(obj){
		var loginid = $(obj).val();
		$.ajax({
			type:"post",
			url:"checkcorloginid.do",
			data:"loginid="+loginid,
			success:function(msg){
				if($.trim(msg) != ""){
					$(".lg").css("color","red");
					$(".lg").text(msg);
				}else{
					$(".lg").css("color","#424242");
					$(".lg").text("登录名由字母，数字，下划线组成");
				}
			}
		});
	}

	function actEmail(){
		if($("#email").val() != ""){
			$("#emailspan").show();
		}
		var email = $("#email").val();
		$.ajax({
			type:"post",
			url:"activateemail.do", 
			data : "email=" + email, 
			success:function(result){  
				if(result.success){
					window.open("http://mail."+email.split("@")[1]);
				}else{
					alert(result.message);
				}
			}
		});
	}

	function actSms(){
		if($("#mobile").val() != ""){
			$("#phonespan").show();
		}
		var mobile = $("#mobile").val();
		$.ajax({
			type:"post",
			url:"activatemobile.do",
			data : "mobile=" + mobile, 
			success:function(result){  
				if(result.success){
					var count = 60;
					var countdown = setInterval(CountDown, 1000);
					function CountDown() { 
						$("#smsreg").attr("disabled", true); 
						$("#smsreg").val("短信激活"+count); 
						if (count == 0) { 
							$("#smsreg").val("短信激活").removeAttr("disabled"); 
							clearInterval(countdown); 
						} 
						count--; 
					}
					alert("已发送短信，注意接收");
				}else{
					alert(result.message);
				}
			}
		});
	}
	
</script>
<script>


	//以下就是我写脚本库，以后可以直接调。根据上级城市iid，获取下级城市的城市名以及iid。返回josn。
	//这个是比较原生态的。
function  searchSubZoneByIidForHtml(iid,sel,optionIid){
		/*
		第一参数，iid，父城市的iid；
		第二个参数，sel，是select标签的id。
		第三参数，presidenceId，当前列表被选中的选项的iid。
		*/		
//	alert("1,进入这个方法了吗；");
	$.ajax({
		type:"post",    			   
    	url: '../front/register/searchSubZone.do', //前面的../要加。在“控制器”中的地址。	
		data:"iid="+iid,			
		dataType:'json',					
		success:function(json){		
//			alert("2，看一下进来的三个参数。"+iid+sel+optionIid);
			var select = document.getElementById(sel+'');	
//			alert("2,看一下select："+select);
			if($.trim(json) != ""){		//如果得到列表，就直接加载。
				select.options.add(new Option("请选择",""));
				for(var key in json) {
					 select.options.add(new Option(json[key], key));					
	//				 alert(json[key]);
					 }
				 //选项加载完毕。现在来循环判断：
				 var selectOption = "#"+sel+" option";			 
				 $(selectOption).each(function(){	//不知道这样行不行？这样的iid选择器能选上不？
					   if($(this).val() == optionIid){
					      $(this).attr("selected", "selected");
					   }
					});	 				
			}else{//如果没有得到列表，有3种可能：1，新增。则提示“请选择”。  2，没有上级城市的iid，提示“请选择”。  3，上级城市下没有城市，提示“请选择”。
				select.options.add(new Option("请选择",""));
				}
		}
	});
}	
</script>
<script type="text/javascript">
 
    function addCities(iid,selCityStr,selTownStr) {	//此iid，即相对上级的value。要根据上级的value，加载下级列表。    
    	
    	var selCity = document.getElementById("selCity");
    	var selTown = document.getElementById("selTown");
    	
    	selCity.options.length = 0; //清空原来的市option
    	selTown.options.length = 0;	//清空原来的县区options//要是不加这句话，当换省时，区却不变，还是旧值。不置空。    
    	selTown.options.add(new Option("请选择",""));
    	searchSubZoneByIidForHtml(iid,selCityStr,"");    	
    }      
 
    function addTowns(iid,selTownStr){
    	var selTown = document.getElementById("selTown");   
    	selTown.options.length=0;
    	searchSubZoneByIidForHtml(iid,selTownStr,""); 
    }
    
    function CaddCities(iid,selCityStr,selTownStr) {	
    	
   		var CselCity = document.getElementById("CselCity");
    	var CselTown = document.getElementById("CselTown");
    	
    	CselCity.options.length = 0; //清空原来的市option
    	CselTown.options.length = 0;	//清空原来的县区options//要是不加这句话，当换省时，区却不变，还是旧值。不置空。
    	CselTown.options.add(new Option("请选择",""));
    	searchSubZoneByIidForHtml(iid,selCityStr,""); 
    	}    
  
    function CaddTowns(iid,selTownStr){
    	var CselTown = document.getElementById("CselTown");
    	CselTown.options.length=0;   
    	searchSubZoneByIidForHtml(iid,selTownStr,"");
    }
    
</script>
<style>
.main{height:1100px}
.frzc{ width:1000px; height:400px; color:#4d4d4d; margin-top:20px; margin-left:20px; float:left;}
.zhxxnr{ width:1100px; height:330px; color:#4d4d4d; margin-top:20px; margin-left:20px; float:left;}
</style>
</head>
<body style="background-image:url('');">

<div>
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="${ctx}/ui/js/1512101421282896.js"></script>
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="${ctx}/ui/js/1512101146476750.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
<div class="mainWth_faren back">
<div class="main">

	<div class="tb">
    	<img src="${ctx}/ui/images/frzc_03.jpg" />
    </div>
    <div class="bt">
    	法人用户信息
    </div>
    <form action="${url}" method="post" id="registerform">
    <div class="frzc">
   		<input type="hidden" name="iid" value="${iid}" />
    	<input type="hidden" name="type" value="${type}" />
    	<input type="hidden" name="cardPic" value="${cardPic}" />
    	<input type="hidden" name="licencePic" value="${licencePic}" />
    	<input type="hidden" name="orgPic" value="${orgPic}" />
    	<input type="hidden" name="cardRenamePic" value="${cardRenamePic}" />
    	<input type="hidden" name="licenceRenamePic" value="${licenceRenamePic}" />
    	<input type="hidden" name="orgRenamePic" value="${orgRenamePic}" />
    	<input type="hidden" name="isupload" value="${isupload}" />
    	<input type="hidden" name="isauth" value="${isauth}" />
    	<input type="hidden" name="authState" value="${authState}" />
    	<input type="hidden" name="rejectReason" value="${rejectReason}" />
    	<table width="90%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate;border-spacing:8px 15px; ">
    		<tr>
            	<td align="right" width="280px">
                	法人类型：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text"  id="type" style=" width:283px; height:30px;" value="${corname}" readonly="readonly" />
                </td>
            </tr>
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业名称：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="name"  style=" width:283px; height:30px;" value="${name}"/>
                </td>
            </tr>
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业工商注册号或统一社会信用代码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="regNumber"  style=" width:283px; height:30px;" value="${regNumber}"/>
                </td>
            </tr>
            <tr class="qyfr" style="display: ${isshoworgnumber}">
            	<td align="right" width="280px">
                	组织机构代码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="orgNumber1"  style=" width:283px; height:30px;" value="${orgNumber}"/>
                </td>
            </tr>
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业法人姓名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="realName"  style=" width:283px; height:30px;" value="${realName}"/>
                </td>
            </tr>
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业法人民族：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text" id="nation2"  readonly="readonly" style=" width:283px; height:30px;" value="${nation}"/>
                </td>
            </tr>
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业法人性别：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text" id="sex2"  readonly="readonly" style=" width:283px; height:30px;" value="${sex}"/>
                
                </td>
            </tr>
            
            <tr class="qyfr">
            	<td align="right" width="280px">
                	企业负责人身份证号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="cardNumber"  style=" width:283px; height:30px;"  value="${cardNumber}"/>
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	机构名称：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="name2"  style=" width:283px; height:30px;" value="${name}"/>
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	组织机构代码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="orgNumber"  style=" width:283px; height:30px;" value="${orgNumber}"/>
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	机构负责人姓名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="realName2"  style=" width:283px; height:30px;" value="${realName}"/>
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	机构负责人性别：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text" id="sex3"  readonly="readonly" style=" width:283px; height:30px;" value="${sex}"/>
                	
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	机构负责人民族：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text" id="nation3"  readonly="readonly" style=" width:283px; height:30px;" value="${nation}"/>
                	
                </td>
            </tr>
            <tr class="fqyfr">
            	<td align="right" width="280px">
                	机构负责人身份证号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input class="baseinfo" type="text" id="cardNumber2"   style=" width:283px; height:30px;" value="${cardNumber}"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="zhxx">
    	账户信息
    </div>
    <div class="zhxxnr">
    	<table width="95%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate;border-spacing:8px 15px; ">
            <tr>
            	<td align="right" width="280px">
                	用户名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="text" id="loginId"  style=" width:285px; height:30px;"  readonly="readonly" value="${loginName}"/>
                </td>
                <td width="410px" style=" font-size:14px;" class="lg">
                	由字母、数字和下划线组成
                </td>
            </tr>
            <tr>
            	<td align="right" width="280px">
                	密码：
                </td>
                <td width="20px"></td>
                <td>
                	<input type="password" id="password" name="pwd2" style=" width:285px; height:30px;" onkeyup="javascript:EvalPwd(this.value);" onkeydown="if(event.keyCode==32) {event.returnValue = false;return false;}" onpaste="return false"/>
                </td>
                <td width="410px" style=" font-size:14px;">
                	若不修改密码，请勿填写
                </td>
            </tr>
            <tr>
				<td align="right" width="280px">
                </td>
                <td width="20px"></td>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" title="字母加数字加符号就会强" id="pwdpower" style="width: 90%">
						<tr>
							<td id="pweak" style="">弱</td>
							<td id="pmedium" style="">中</td>
							<td id="pstrong" style="">强</td>
						</tr>
					</table>
				</td>
			</tr>
            <tr>
            	<td align="right" width="280px">
                	确认密码：
                </td>
                <td width="20px"></td>
                <td>
                	<input id="password2" type="password" style=" width:285px; height:30px;" />
                </td>
                <td width="410px">两次密码必须一致</td>
            </tr>
            <tr>
            	<td align="right" width="280px">
                	手机号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="mobile" name="mobile" type="text" style=" width:285px; height:30px;" value="${mobile}"/>
                </td>
                <td width="410px">请填写正确的手机号码
                </td>
            </tr>
            <tr>
            	<td align="right" width="280px">
                	电子邮箱：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="email" name="email" type="text" style=" width:285px; height:30px;" value="${email}"/>
                </td>
                <td width="410px">请填写正确邮箱，找回密码时使用
                </td>
            </tr>
            <tr>
            	<td align="right" width="280px">
                	经营范围：
                </td>
                <td width="20px"></td>
                <td>
                	<input id="scope" name="scope" type="text" style=" width:285px; height:30px;" value="${scope}"/>
                </td>
                <td width="410px">
                </td>
            </tr>
            
              <!-- 以下企业法人户籍省市区、详细地址，以及居住地所在省市区、详细地址，李德隆于201601数日做成  0113又大修 -->
					<tr style="display:none;" >
					<!-- 20160129 朱工要求暂时隐藏户籍与常住地的表单以及脚本校验 -->
						<td align="right" width="235px">户籍省市区：</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>

						<td>
							<table>
								<tr>
									<td><select id="selProvince"  
										onchange="addCities(this.value,'selCity','selTown')" 
										name="gpresidenceId"  style="width: 94px; height: 35px;">	
									</select>
									</td>
									
									<td><select id="selCity" name="presidenceId" 
										onchange="addTowns(this.value,'selTown')"
										style="width: 94px; height: 35px;">
											
									</select>
									</td>
									
									<td><select id="selTown" name="residenceId"	
										style="width: 94px; height: 35px;">
										
											
									</select>
									</td>
								</tr>
							</table></td>
					</tr>

					<tr style="display:none;" >
						<td align="right" width="235px">户籍详细地址</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>
						<td><input type="text" id="residenceDetail" value="${residenceDetail}"
							name="residenceDetail" style="width: 283px; height: 30px;" />
						</td>
					</tr>


					<tr style="display:none;" >
						<td align="right" width="235px">居住地省市区：</td>

						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>					
						<td>
						<table>
							<td><select id="CselProvince" name="gplivingAreaId"
								onchange="CaddCities(this.value,'CselCity','CselTown')"
								 style="width: 94px; height: 35px;">
									

							</select>
							</td>
							<td><select id="CselCity" name="plivingAreaId"
								onchange="CaddTowns(this.value,'CselTown')"
								style="width: 94px; height: 35px;"">
									
							</select>
							</td>
							<td><select id="CselTown" name="livingAreaId"
								style="width: 94px; height: 35px;">
									
							</select>
							</td>

						</table>
						</td>

					</tr>
					<tr style="display:none;" >
						<td align="right" width="235px">居住地详细地址</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>
						<td><input type="text" id="livingAreaDetail"
							name="livingAreaDetail" value="${livingAreaDetail}" style="width: 283px; height: 30px;" />
						</td>
					</tr>
            
            
            
            <tr>
            	<td align="right" width="235px">
                	办公电话：
                </td>
                <td width="20px"></td>
                <td>
                	<input id="phone" name="phone" type="text" style=" width:285px; height:30px;" value="${phone}"/>
                </td>
                <td width="410px">
                </td>
            </tr>
            
            <tr>
            	<td align="right" width="235px">
                	联系地址：
                </td>
                <td width="20px"></td>
                <td>
                	<input id="address" name="address" type="text" style=" width:285px; height:30px;" value="${address}"/>
                </td>
                <td width="410px">
                </td>
            </tr>
            
            <tr>
                <td colspan="3" align="center">
                	
                </td>
            </tr>
             <tr>
            	 <td align="right" width="235px">
                 </td>
                 <td width="20px"></td>
                <td align="center">
                	<input type="submit" class="btn btn-primary" value="保  存" style="height:40px;width:100px;font-size:16px;" />
                </td>
                <td width="410px">
                </td>
            </tr>
        </table>
    </div>
    </form>
</div>

</div>
  <div id="foot">
    <div> 
      <script language="javascript" src="${ctx}/ui/js/1512101421288942.js"></script>
    </div>
  </div>
</div>

</body>

</html>
