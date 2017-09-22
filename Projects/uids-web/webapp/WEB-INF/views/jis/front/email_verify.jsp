<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>甘肃政务服务网</title>
<style>
#scrollUp { display:none;}
</style>
<script>
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
		$('#verifyform').validity(function(){
			$('#loginId').require('请填写登录名');
			$('#email').require('请填写邮箱').match('email','对不起，您输入的邮箱错误');
			$('#emailrandcode').require("请填写邮箱验证码");
		},{
			success:function(result){
				if(result.success){
					window.location.href='resetpwd_show.do';
				}else{
					alert(result.message);
				}
			}
		});
		
	});
	function actEmail(){
		if($("#email").val() != ""){
			$("#emailspan").show();
		}
		var email = $("#email").val();
		$.ajax({
			type:"post",
			url:"./register/activateemail.do", 
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
	
	function checkLoginName(obj) {
		var loginname = $(obj).val();
		$.ajax({
			type:"post",
			url:"checkperloginname.do",
			data:"loginname="+loginname,
			success:function(result){
				if(result.success) {
					var email = result.params.email;
					$('#hint').text("");
					$('#email').val(email);
				}else {
					$('#hint').text("用户名不存在");
					$('#hint').css('color','red');
				}			
			}
			
		});
	}
	
	function emptyEmail() {
		$('#email').val("");
	}
</script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	padding: 0px;
}
</style>
<link href="./images/lsc_findPwd.css" rel="stylesheet" type="text/css" />
<link href="http://www.gszwfw.gov.cn/./images/11/reset.css" rel="stylesheet" type="text/css" >
<link rel="stylesheet" type="text/css" href="./images/style.css"/>
<link rel="stylesheet" type="text/css" href="./images/syl_fpqd.css"/>

</head>

<body>
<div id="gsbody">
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421282896.js"></script>
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101146476750.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
  <div class="mainWth_faren back">
  <div id="main" class="mainWth_email" >
  <form action="${url}" method="post" id="verifyform">
    <table width="955" height="477" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-bottom:8px; margin-top:10px;" id="tt">
      <tr height="50">
        <td style="background:url(./images/yxfind-pwd_07.png); background-repeat:no-repeat;"></td>
      </tr>
      <tr height="20">
        <td></td>
      </tr>
      <tr height="60" align="center">
        <td  style="background:url(./images/yxfind-pwd_04.png); background-repeat:no-repeat; background-position:center;"></td>
      </tr>
      <tr height="170" align="center">
        <td><table width="490" height="280" border="0" cellspacing="0" cellpadding="0" align="center">
            <tr height="60">
              <td width="70" align="right">登录名：</td><td width="20"><img src="./images/yxfind-pwd_06.png" /></td> <td >
              <input type="text" id="loginId" name="loginName" style=" width:270px; height:30px;" onblur="checkLoginName(this)"
              onfocus="emptyEmail()"/></td> <td id="hint">&nbsp;&nbsp;&nbsp;输入登录名</td>
            </tr>
             <tr height="60">
              <td width="70" align="right">邮&nbsp;&nbsp;&nbsp;箱：</td><td width="20"><img src="./images/yxfind-pwd_06.png" /></td><td>
              	<input id="email" name="email" type="text" readonly="readonly" style=" width:270px; height:30px;" /></td>
              <td>&nbsp;&nbsp;&nbsp;<span><input type="button" name="emailreg" id="emailreg" value="发送验证码" onclick="actEmail()" style="height:30px;width:103px;font-size:13px" class="btn btn-small" /></span>
					</td>
            </tr>
             <tr height="60">
              <td width="70" align="right">验证码：</td><td width="20"><img src="./images/yxfind-pwd_06.png" /></td><td>
              <input name="emailcode" id="emailrandcode" type="text" style=" width:270px; height:30px;" /></td><td></td>
            </tr>
            <tr align="center">
              <td colspan="4">
              <input type="submit" class="btn btn-primary" value="上一步" style="width:100px" onclick="javascript:history.go(-1)"/>&nbsp;&nbsp;
              <input type="submit" class="btn btn-primary" value="下一步" style="width:100px" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
    </form>
  </div>
  </div>
  <div id="foot">
    <div> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421288942.js"></script>
    </div>
  </div>
</div>
<script type="text/javascript">
$("#shixian,#shixianlist").hover(
    function(){
	$("#shixianlist").show();
	},function(){
	$("#shixianlist").hide();
	}	);
$("#shengji,#shengjilist").hover(
    function(){
	$("#shengjilist").show();
	},function(){
	$("#shengjilist").hide();
	}	);

</script> 
<script type="text/javascript">
$("#nr").css("height","0");
$("#zt").click(function(){
	$(this).addClass("zt1").removeClass("zt");
	$("#bm").addClass("bm1").removeClass("bm");
	$("#nr1").css("height","auto");$("#nr").css("height","0");
	});
	

$("#bm").click(function(){
	$(this).addClass("bm").removeClass("bm1");
	$("#zt").addClass("zt").removeClass("zt1");
	$("#nr").css("height","auto");$("#nr1").css("height","0");
	});
</script>
</body>
</html>
