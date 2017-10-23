<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息修改</title>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/grzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/style.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.parser.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/linkbutton.css"/>
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
<script type="text/javascript" src="${ctx}/ui/lib/security/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx }/ui/script/idcardValidity.js"></script>
<link rel="stylesheet" href="${ctx }/ui/widgets/checkpwd/css/checkpwd.css" type="text/css"></link>
<script>
window.alert = function (msg,type,fu){
	top.$.messager.alert(' ',msg,type,fu);
};

$.validity.setup({
	outputMode : "showErr"
}); 

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
	
	$.validity.setup({
		outputMode : "showErr"
	}); //校验错误弹出

	$(function(){
		
		var gpresidenceId='${user.gpresidenceId}';//这样rrr是可以取到的。且本来就是string类型。		
		var presidenceId='${user.presidenceId}';
		var residenceId='${user.residenceId}';  //这个引号不加，真是害死人。。。浪费了我至少一个钟头的时间。
		
		var  livingAreaId='${user.livingAreaId}';
		var  plivingAreaId='${user.plivingAreaId}';
		var  gplivingAreaId='${user.gplivingAreaId}';	
	//	alert("看看六个iid是多少："+gpresidenceId+presidenceId+residenceId+livingAreaId+plivingAreaId+gplivingAreaId);
			
		//李德隆20160110 日曜 残业 独 访问本网页，自动加载省份。20160113夜大修
		/*此六行似乎也不再需要。因为加载函数的引入。
		var selProvince = document.getElementById('selProvince');	
		var CselProvince = document.getElementById('CselProvince');	
		var selCity = document.getElementById('selCity');	
		var CselCity = document.getElementById('CselCity');	
		var selTown = document.getElementById('selTown');	
		var CselTown = document.getElementById('CselTown');			
	 */
	  
	  
	  //	searchSubZoneByIidForHtml(iid,sel,optionIid){
		/*
		第一参数，iid，父城市的iid；
		第二个参数，sel，是select标签的id。
		第三参数，presidenceId，当前列表被选中的选项的iid。
		*/		
	 	
	 	//加载户籍三项的下拉列	 	
	 	searchSubZoneByIidForHtml(0,"selProvince",gpresidenceId);
	 	searchSubZoneByIidForHtml(gpresidenceId,"selCity",presidenceId);
	 	searchSubZoneByIidForHtml(presidenceId,"selTown",residenceId);	 	
	 
	 	//加载常住三项的下拉列	 
	 	searchSubZoneByIidForHtml(0,"CselProvince",gplivingAreaId);
	 	searchSubZoneByIidForHtml(gplivingAreaId,"CselCity",plivingAreaId);
	 	searchSubZoneByIidForHtml(plivingAreaId,"CselTown",livingAreaId);	 	
		
		
		
		//degree
		var select = document.getElementById("degree");  
		for(var i=0; i<select.options.length; i++){  
		    if(select.options[i].innerHTML == '${user.degree}'){  
		        select.options[i].selected = true;  
		        break;  
		    }  
		} 
		if(${user.isAuth} == 1){
			$(".baseinfo").attr("readonly","readonly");
			$("#female").attr("disabled","disabled");
			$("#male").attr("disabled","disabled");
			if('${user.sex}' == '女'){
				$("#female").attr("checked","checked");
			}else{
				$("#male").attr("checked","checked");
			}
		}else{
			if('${user.sex}' == '女'){
				$("#female").attr("checked","checked");
			}else if('${user.sex}' == '男'){
				$("#male").attr("checked","checked");
			}
		} 
		$("#divinfo").toggle(function(){
			$(".main").css("height","700px");
			$(".xxnr2").css("height","150px");
			$("#moreinfo").hide();
		},function(){
			$(".main").css("height","1280px");
			$(".xxnr2").css("height","620px");
			$("#moreinfo").show();
		});
		
		$("#loginId").focus();
		$('#registerform').validity(function(){
			$("#papersNumber").require('请填写身份证号');
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
			
			
			var password = $("#password").val();
			if(password != "" && password.length>0){
				$('#password').minLength(6,'密码不少于6位').maxLength(18, "密码最多18个字");
				$('#password2').require('请填写确认密码').assert(match(),'两次输入密码不一致，请正确填写');
			}
			if($("#age").val().length > 0){
				$("#age").assert(function(){
					if($("#age").val() > 0 && $("#age").val() < 120){
						return true;
					}else{
						return false;
					}
				}, "对不起，您输入的年龄错误");
			}
			if($("#phone").val().length > 0){
				$("#phone").match('tel','对不起，您输入的固定电话错误');
			}
			if($("#qq").val().length > 0){
				$("#qq").match('qq','对不起，您输入的qq号错误');
			}
			if($("#papersNumber").val().length > 0){
				$("#papersNumber").assert(IdCardValidate($('#papersNumber').val()),"对不起,您输入的身份证格式不正确")
				.assert(checkSexByIdcard(),"对不起，您输入的身份证号与您的性别不符合").assert(checkAgeByIdcard(),"对不起，您输入的身份证号与您的年龄不符合");
			}
			if($("#post").val().length > 0){
				$("#post").match('zipcode','对不起，您输入的邮编错误');
			}
			$('#name').require('请填写姓名');
			$('#email').require('请填写电子邮箱').match('email','对不起，您输入的电子邮箱不合法');
			
			$('#mobile').require('请填写手机号码').match('mobile','对不起，您输入的手机号码错误');
			$('#randCode').require('请填写验证码');
			if("${regType}" == "1"){
				$('#emailrandcode').require("请填写邮箱验证码");
			}else if("${regType}" == "2"){
				//$('#phonerandcode').require("请填写短信验证码");
			}			
		},{
			success:function(result){
				if(result.success){
					var appmark = result.message;
					alert(appmark);
					//window.location.href='perindex';
				}else{
					alert(result.message);
					$('#verifyImg').click();
				}
			}
		});
        
	});

	function checkSexByIdcard(){
		var sex = $("input[name='sex']:checked").val();
		var s;
		if(sex == '男'){
			s = 'male';
		}
		if(sex == '女'){
			s = 'female';
		}
		if(s == 'male' || s == 'female'){
			if(s != maleOrFemalByIdCard($("#papersNumber").val())){
				return false;
			}
		}
		return true;
	}
	
	function checkAgeByIdcard(){
		if($("#age").val().length > 0){
			if(getAgeByIdCard($('#papersNumber').val()) != $("#age").val()){
				return false;
			}
		}
		return true;
	}

	function checkLoginId(obj){
		var loginid = $(obj).val();
		$.ajax({
			type:"post",
			url:"checkperloginid",
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
			url:"activateemail", 
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
			url:"activatemobile",
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

	$.ajax({
		type:"post",    			   
    	url: '../front/register/searchSubZone', //前面的../要加。在“控制器”中的地址。	
		data:"iid="+iid,			
		dataType:'json',					
		success:function(json){		
			var select = document.getElementById(sel+'');	
			if($.trim(json) != ""){		
				select.options.add(new Option("请选择",""));
				for(var key in json) {
					 select.options.add(new Option(json[key], key));	
					 }
				 var selectOption = "#"+sel+" option";			 
				 $(selectOption).each(function(){	
					   if($(this).val() == optionIid){
					      $(this).attr("selected", "selected");
					   }
					});	 				
			}else{
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
.main{height:1280px}
.xxnr{height:400px}
.xxnr2{height:620px}
</style>
</head>
<body>
<div>
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="${ctx }/ui/js/1512101421282896.js"></script>
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="${ctx }/ui/js/1512101146476750.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
<div class="mainWth_faren back">
<div class="main">
	<div class="tb">
    	<img src="${ctx }/ui/images/grzc_03.jpg" />
    </div>
    <div class="bt">
    	个人用户信息修改
    </div>
    
    <div class="xx">
    	账户信息
    </div>
    <form action="${url}" method="post" id="registerform">
    <div class="xxnr">
    	<input type="hidden" name="headPic" value="${user.headPic}" />
    	<input type="hidden" name="bodyPic" value="${user.bodyPic}" />
    	<input type="hidden" name="headRenamePic" value="${user.headRenamePic}" />
    	<input type="hidden" name="bodyRenamePic" value="${user.bodyRenamePic}" />
    	<input type="hidden" name="isUpload" value="${user.isUpload}" />
    	<input type="hidden" name="isAuth" value="${user.isAuth}" />
    	<input type="hidden" name="authState" value="${user.authState}" />
    	<input type="hidden" name="rejectReason" value="${user.rejectReason}" />
    	
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            	<td align="right" width="235px">
                	用&nbsp;户&nbsp;&nbsp;名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="loginId"  type="text" name="loginName" style=" width:283px; height:30px;" value="${user.loginName}" readonly="readonly"/>
                </td>
                <td width="385px" style=" font-size:14px;" class="lg">
                	用户名由字母、数字和下划线组成
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
                </td>
                <td>&nbsp;</td>
                <td>
                	<input id="password" name="pwd2" type="password" style=" width:283px; height:30px;" onkeyup="javascript:EvalPwd(this.value);" 
                	  	onkeydown="if(event.keyCode==32) {event.returnValue = false;return false;}" onpaste="return false" />
                </td>
                <td width="385px" style=" font-size:14px;">
                	若不修改密码，请勿填写
                </td>
            </tr>
            <tr>
				<td align="right" class="label">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
				<table cellpadding="0" cellspacing="0" border="0" title="字母加数字加符号就会强" id="pwdpower" style="width: 90%">
					<tr>
						<td id="pweak" style="">弱</td>
						<td id="pmedium" style="">中</td>
						<td id="pstrong" style="">强</td>
							</tr>
					<input type="hidden" id="level"/>
				</table>
				</td>
			</tr>
             <tr>
            	<td align="right" width="235px">
                	确认密码：
                </td>
                <td>&nbsp;</td>
                <td>
                	<input id="password2" type="password" style=" width:283px; height:30px;" />
                </td>
                <td width="385px" style=" font-size:14px;">
                	两次密码必须一致
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="name"  type="text" name="name" style=" width:283px; height:30px;"  class="baseinfo" value="${user.name}"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写真实姓名
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	电子邮箱：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="email" name="email" type="text" style=" width:283px; height:30px;" value="${user.email}"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写正确邮箱，找回密码时使用
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	手机号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="mobile" name="mobile" type="text" style=" width:283px; height:30px;" value="${user.mobile}"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写正确的手机号码
                	<!--  <div >
                    	<input type="button" name="smsreg" id="smsreg" value="短信激活" onclick="actSms();" style="width:83px;" class="phonebutton" />
                    </div>
                    <div>
                    <span id="phonespan" style="display:none;margin-left:3px;">验证码&nbsp;<input type="text" name="mobilecode" id="phonerandcode" style="width:55px;border:1px solid #ededed;" maxlength="6"/></span>
                    </div>-->
                </td>
            </tr>  
            
            <tr>
            	<td align="right" width="235px">
                	身份证号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input type="hidden" name="papersType" value="1"/>
                	<input id="papersNumber" name="papersNumber" class="baseinfo" type="text" style="width:283px; height:30px;" value="${user.papersNumber}"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	
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
										name="gpresidenceId"  style="width: 92px; height: 35px;">	
									</select>
									</td>
									
									<td><select id="selCity" name="presidenceId" 
										onchange="addTowns(this.value,'selTown')"
										style="width: 92px; height: 35px;">
											
									</select>
									</td>
									
									<td><select id="selTown" name="residenceId"	
										style="width: 92px; height: 35px;">
										
											
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
						<td><input type="text" id="residenceDetail" value="${user.residenceDetail}"
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
								 style="width: 92px; height: 35px;">
									

							</select>
							</td>
							<td><select id="CselCity" name="plivingAreaId"
								onchange="CaddTowns(this.value,'CselTown')"
								style="width: 92px; height: 35px;"">
									
							</select>
							</td>
							<td><select id="CselTown" name="livingAreaId"
								style="width: 92px; height: 35px;">
									
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
							name="livingAreaDetail" value="${user.livingAreaDetail}" style="width: 283px; height: 30px;" />
						</td>
					</tr>
            
            
            
            
            
            
            
            
            
        </table>
        <div class="xx" id="divinfo" style="cursor:pointer">
    	以下为选填项
    </div>
    <div class="xxnr2">
    	<table id="moreinfo" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
    		<tr><td height="10px"></td></tr>
    		<tr>
            	<td align="right" width="235px">
                	年&nbsp;&nbsp;龄：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="age" name="age" type="text" style="width:283px; height:30px;" maxlength="3" value="${user.age}"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写数值
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	性&nbsp;&nbsp;别：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<!--  <input type="text" id="sex2" name="sex2" readonly="readonly" style=" width:283px; height:30px;display:none" value="${sex}"/>-->
                	<span ><input type="radio" id="male" name="sex" value="男"/>男&nbsp;&nbsp;&nbsp;
                    <input type="radio" id="female" name="sex" value="女" />女</span>
                </td>
                <td width="385px" style=" font-size:14px;">
                	
                </td>
            </tr>
            
            <tr>
            	<td align="right" width="235px">
                	学&nbsp;&nbsp;历：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<select name="degree" id="degree" style=" width:286px; height:35px;" data-value="${user.degree}" > 
                	    <option value="">请选择学历</option>
                		<option value="其他">其他</option>
                		<option value="小学">小学</option>
                		<option value="初中">初中</option>
                		<option value="高中">高中</option>
                		<option value="专科">专科</option>
                		<option value="大学">大学</option>
                		<option value="硕士">硕士</option>
                		<option value="博士">博士</option>
                	</select>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	工作单位 ：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="workunit" name="workUnit" type="text" style=" width:283px; height:30px;" value="${user.workUnit}" maxlength="50"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	职&nbsp;&nbsp;务 ：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="headship" name="headShip" type="text" style=" width:283px; height:30px;" value="${user.headShip}" maxlength="50"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	传&nbsp;&nbsp;真：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="fax" name="fax" type="text" style=" width:283px; height:30px;" value="${user.fax}" maxlength="30"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	固定电话：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="phone" name="phone" type="text" style=" width:283px; height:30px;" value="${user.phone}" maxlength="30"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	办公电话：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id="comptel" name="compTel" type="text" style=" width:283px; height:30px;" value="${user.compTel}" maxlength="30"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	QQ：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id=qq name="qq" type="text" style=" width:283px; height:30px;" value="${user.qq}"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	MSN：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id=msn name="msn" type="text" style=" width:283px; height:30px;" value="${user.msn}"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
                </td>
            </tr>
            <tr>
            	<td align="right" width="235px">
                	邮&nbsp;&nbsp;编 ：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td>
                	<input id=post name="post" type="text" style=" width:283px; height:30px;" value="${user.post}" maxlength="6"/>
                </td>
                <td width="385px" style=" font-size:14px;" >
               	 请填写6位数字的邮编
                </td>
            	</tr>
           	<tr>
           		<td align="right" width="235px">
               	联系地址 ：
               </td>
               <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
               <td>
               	<input id=address name="address" type="text" style=" width:283px; height:30px;" value="${user.address}"/>
               </td>
               <td width="385px" style=" font-size:14px;" >
               </td>
           </tr>
    	</table>
    	<table width="100%" height="60px" border="0" cellspacing="0" cellpadding="0">
           <tr>
            	<td align="right" width="235px">
               </td>
               <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center"></td>
                <td align="center">
                	<input type="submit" class="btn btn-primary" value="保  存" style="height:40px;width:100px;font-size:16px;" />
                </td>
               <td width="385px" style=" font-size:14px;" >
               </td>
            </tr>
    	</table>
    </div>
    </div>
   </form>
</div>
</div>
  <div id="foot">
    <div> 
      <script language="javascript" src="${ctx }/ui/js/1512101421288942.js"></script>
      <script type="text/javascript" src="${ctx }/res/js/region/checkpwd.js"></script>
    </div>
  </div>
</div>
</body>
</html>