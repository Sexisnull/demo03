<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>重置密码</title>
<link href="${ctx }/ui/images/lsc_findPwd.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/grzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/syl_fpqd.css"/>
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
<style>
#scrollUp { display:none;}
</style>
<script type="text/javascript">
	function match(){
		password = $('#password').val();
		password2 = $('#password2').val();
		if (password == password2){
			return true;
		} else {
			return false;
		}
	}
	
	$.validity.setup({
		outputMode : "showErr"
	}); //校验错误弹出
	window.alert = function (msg,type,fu){
		top.$.messager.alert(' ',msg,type,fu);
	};

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
		$('#resetpwdform').validity(function(){
			$('#password').require('请填写密码');
			$('#password2').require('请填写确认密码').assert(match(),'两次输入密码不一致，请正确填写');
		},{
			success:function(result){
				if(result.success){
					var typeEntity = "${typeEntity}";
						var domain=document.domain;
					if(typeEntity == "per"){
						
						
						alert("您的新密码设置成功！", "", function(){
							//location.href='perlogin.do?appmark='+"${appmark}";//李德隆于20160526添加appmark='+"${appmark}"
							window.location.href= "${ctx}/front/perlogin.do?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw";
						//	location.href='perlogin.do?domain='+domain+'&action=ticketLogin&gotoUrl=&appmark=gszw';

						});
					}else{
						alert("您的新密码设置成功！", "", function(){
							//location.href='corlogin.do?appmark='+"${appmark}";//李德隆于20160526添加appmark='+"${appmark}"
							window.location.href= "${ctx}/front/corlogin.do?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw";
						});
					}
				}else{
					alert(result.message);
				}
			}
		});
		
	});
	
	/*function toSubmit(){
		var pwd = $("#password").val();
		var pwd2 = $("#password2").val();
		var posturl = "${url}";
		var typeEntity = "${typeEntity}";
		if(pwd=='' || pwd2==''){
			alert("密码不能为空");
			return;
		}
		if(pwd!=pwd2){
			alert("两次密码不一致！");
			return;
		}
		$.ajax({
	   		type:"post",		   	
	   		url:posturl,
	     	data:{"pwd":pwd},	
			dataType:'json',
			success:function(json){//如果成功与第三方连接					
				if(json.success){
					if(typeEntity == "per"){
						alert("您的新密码设置成功！");
						window.location.href="${ctx}/front/perlogin.do?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw"
								
					}else{
						alert("您的新密码设置成功！");
						window.location.href="${ctx}/front/corlogin.do?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw"
					}
				}else{
					alert(json.message);
				}
			}  ,
			error:function(){					
				alert("系统异常，请稍后重试！");
			}	
			
	   	});	
	}*/
</script>
<link href="${ctx }/ui/images/resetpsw.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/syl_fpqd.css"/>
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

<div class="mainbody">
	<form action="${url}" method="post" id="resetpwdform">
	<input type="hidden" name="type" id="type" value="${type}" />
	<div class="resettop"><img src="${ctx }/ui/images/reset_08.jpg" width="47" height="47" /><div class="resettitle">重置密码</div> </div>
    <div class="yzfs"> <img src="${ctx }/ui/images/reset_12.jpg" width="565" height="56" /></div>
  <div class="user">登&nbsp;录&nbsp;名：&emsp;&nbsp;&nbsp;${loginName}</div>
  <div class="resetpsw"> 请设置新密码：<input id="password" name="pwd" class="psw" type="password" /></div>
  <div class="yespsw">请确认你的密码：<input id="password2" class="psw" type="password" /></div>
	<div class="sure" ><input type="submit"  class="btn btn-primary" value="保 存" style="width:80px" /></div>
</form>
</div>
</div>
<div id="foot">
    <div> 
      <script language="javascript" src="${ctx }/ui/js/1512101421288942.js"></script>
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


