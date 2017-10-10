<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<h:head message="true" validity="true"></h:head>
<script type="text/javascript" src="${contextPath}/resources/jis/front/script/login.js"></script>
<link href="${contextPath}/resources/jis/front/css/css.css" rel="stylesheet" type="text/css" />
<script>
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

	$(function(){
		$('#loginform').validity(function(){
			$('#username').require('请填写用户名');
			$('#password').require('请填写密码');
		});
		window.alert = function (msg,type,fu){
			top.$.messager.alert(' ',msg,type,fu);
		};
	});
	
	
</script>

</head>

<body>
<div class="fr_form">
	<form action="${url}" method="post" id="loginform">
		<div class="fr_log">
	    	<li class="fr_name">用户名：</li><li class="fr_text"><input id="username" name="username" type="text" class="fr_input"></li>
	    </div>
		<div class="fr_log">
	    	<li class="fr_name">密&nbsp;&nbsp;码：</li><li class="fr_text"><input id="password" name="password" type="password" class="fr_input"></li>
	    </div>
		<div class="fr_log">
	    	<li class="fr_name"></li><li class="fr_text"><input type="submit" class="fr_dl" value="">
	    	<input type="button" class="fr_zc" value="" onclick="location.href='register'"></li>
	    </div>
    </form>
</div>


</body>
</html>
