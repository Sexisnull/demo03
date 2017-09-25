<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link href="./images/css.css" rel="stylesheet" type="text/css" />
<script src="./images/html5.js"></script>
<style>
body {
	background-color:#F2F2F2;
}
</style>
<script type="text/javascript">
</script>
</head>

<body>
<div class="contain" style="min-height: 95%;">
	<div class="top">
	  <div class="nav">
	  	  <span style="padding-left: 5%;">${sysName}</span>
		  <div class="hyzx">
		  	<span>您好！${loginname}</span>
			<span title="个人信息"><a href="modifyperinfo_show.do"><img  src="./images/vip_head.png" width="23" height="23"></a></span>
			<!--  <span title="实名认证"><a href="register/perauth_show.do"><img  src="./images/vip_head.png" width="23" height="23"></a></span>-->
			<span title="注销"><a href="perlogout.do"><img src="./images/logout.png" width="22" height="24"></a></span>
		  </div>    	
	  </div>   
	</div>
	
 	<div style="height: 50px;"></div>   
</div>
<div class="bottom">
  <div class="foot"> <span class="copyright">${copyRight}</span> <span class="instruction">建议使用1024*768，IE8浏览器以上为最佳浏览模式</span> </div>
</div>
</body>
</html>