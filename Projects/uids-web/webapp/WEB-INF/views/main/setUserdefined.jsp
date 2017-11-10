<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/include/meta.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>设置应用自定义帐号</title>
<script type="text/javascript">
$(function(){
	if($('#username').val() == ""){
		$('#username').prev().show();
	}
	if($('#password').val() == ""){
		$('#password').prev().show();
	}
	if($('#randCode').val() == ""){
		$('#randCode').prev().show();
	}
	if($('#groupname').val() == ""){		
		$('#groupname').prev().show();
	}
	$('#username,#password,#randCode,#groupname').on('blur',function(){
		if($(this).val() == ""){
			$(this).prev().show();
		}
	});
	$('#username,#password,#randCode').on('focus',function(){
		if($(this).val() == ""){
			$(this).prev().hide();
		}
	});
	$('.log_dlm label').on('click',function(){
		$(this).hide();
		$(this).next().focus();
	});
	$('.log_mm label').on('click',function(){
		$(this).hide();
		$(this).next().focus();
	});
	$('.log_gm label').on('click',function(){
		$(this).hide();
		$(this).next().trigger('click');
	});	
	
	$("#submit").on('click',function(){
		var id = $("#strid").val();
		var loginname=$("#loginname").val();
		var loginAllname=$("#loginallname").val();
		var appid = $("#appid").val();
		var apploginName=$("#apploginname").val();
		var appPwd = $("#apppwd").val();
		if(apploginName==''){
			alert("登录名不能为空");
			return;
		}
		if(id=='' && appPwd==''){
			alert("初次设置登录密码不能为空");
			return;
		}
		
		$.ajax({
			url : "${ctx}/submit_userdefined",
			data:{"definedId":id,"loginname":loginname,"loginAllname":loginAllname,"appid":appid,"apploginName":apploginName,"appPwd":appPwd},
			dataType : "JSON",
			type : "POST",
			async : false,
			success : function(data) {
				if(data.state){
					alert(data.msg);
					window.parent.document.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});
		
	});
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


</script>
<style>
.fr_name{width:80px;text-align: right;float:left;}
.fr_form{width:400px;height:200x;margin:0 auto}
.fr_text{float:left}
.fr_log{height:30px;line-height:30px;margin-top:15px;overflow:hidden}
.fr_input{width:170px;height:27px;line-height:28px;height:25px!important;line-height:25px!important;padding:0 10px;font-size:14px;border:solid 1px #ccc}
.loginbutton {
	background-color: #4d90fe;
	background-image: linear-gradient(bottom, #4d90fe 0%, #2e74e6 100%);
	background-image: -o-linear-gradient(bottom, #4d90fe 0%, #2e74e6 100%);
	background-image: -moz-linear-gradient(bottom, #4d90fe 0%, #2e74e6 100%);
	background-image: -webkit-linear-gradient(bottom, #4d90fe 0%, #2e74e6 100%);
	background-image: -ms-linear-gradient(bottom, #4d90fe 0%, #2e74e6 100%);
	background-image: -webkit-gradient(
		linear,
		left bottom,
		left top,
		color-stop(0, #4d90fe),
		color-stop(1, #2e74e6)
	);
	background-position: center top;
	background-repeat: no-repeat;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	border: none;
	color: #E8E8E8;
	font: bold 15px Arial, Helvetica, sans-serif;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0,0,0,0.25);
	width: 171px;
	height: 47px;
	cursor: pointer;
	margin-right: 10px;
	font-size: 21px;
	float:left;
}
.loginbutton:hover {
	background-color: #2e74e6;
	background-image: linear-gradient(bottom, #2e74e6 0%, #4d90fe 100%);
	background-image: -o-linear-gradient(bottom, #2e74e6 0%, #4d90fe 100%);
	background-image: -moz-linear-gradient(bottom, #2e74e6 0%, #4d90fe 100%);
	background-image: -webkit-linear-gradient(bottom, #2e74e6 0%, #4d90fe 100%);
	background-image: -ms-linear-gradient(bottom, #2e74e6 0%, #4d90fe 100%);
	background-image: -webkit-gradient(
		linear,
		left bottom,
		left top,
		color-stop(0, #2e74e6),
		color-stop(1, #4d90fe)
	);
	background-position: center bottom;
}
</style>
</head>
<body>
	<div class="fr_form" >
			<form  id="loginform">
				<input type="hidden" id="strid" name="strid" value="${userDefinedId}">
				<input type="hidden" id="loginname" name="loginname" value="${loginName}">
				<input type="hidden" id="loginallname" name="loginallname" value="${loginAllName}">
				<input type="hidden" id="appid" name="appid" value="${appid}">
				<div class="fr_log">
					<ul>
						<li class="fr_name">
							应用名称：
						</li>
						<li class="fr_text">
							${appname}
						</li>
					</ul>
				</div>
				<div class="fr_log">
					<ul>
						<li class="fr_name">
							登&nbsp;&nbsp;录&nbsp;&nbsp;名：
						</li>
						<li class="fr_text">
							<input id="apploginname" name="apploginname" type="text" class="fr_input" value="${apploginname}">
						</li>
					</ul>
				</div>
				<div class="fr_log">
					<ul>
						<li class="fr_name">
							密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
						</li>
						<li class="fr_text">
							<input id="apppwd" name="apppwd" type="password" class="fr_input" value="">
							&nbsp
							<font style="color: gray;">若不修改请置为空</font>
						</li>
					</ul>
				</div>
				<div class="fr_log">
					<ul>
						<li class="fr_name"></li>
						<li class="fr_text">
							<input type="button" id="submit" class="loginbutton" value="保 存" style="width: 81px; height: 25px; font-size: 16px; margin-left: 130px;">
						</li>
					</ul>
				</div>
			</form>
		</div>
</body>
</html>