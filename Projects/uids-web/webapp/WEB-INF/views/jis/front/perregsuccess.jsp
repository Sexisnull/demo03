<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实名认证</title>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/css/global.css"/>
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
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/loadmask/jquery.loadmask.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/loadmask/jquery.loadmask.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/checkpwd/css/checkpwd.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/checkpwd/checkpwd.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/hanweb/calendar/css/calendar.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/grzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/syl_fpqd.css"/>

<script type="text/javascript" src="../../ui/script/idcardValidity.js"></script>
<script type="text/javascript" src="../../ui/lib/security/jquery.cookie.js"></script>
<script type="text/javascript" src="../../ui/lib/security/base64.js"></script>
<script type="text/javascript" src="../../ui/lib/security/jsencrypt.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/rsa_util.js"></script>
<script type="text/javascript" src="../../ui/lib/security/security.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/Barrett.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/RSA.js"></script>
<script>
function auth(){
	window.location.href="../perauth_show";
}
function login(){
	
	//window.location.href="../perlogin?appmark=gszw";
	//window.location.href="../perlogin?appmark=gszw";
	//top.location = "../perlogin?domain=118.180.24.32%3A8081&action=ticketLogin&gotoUrl=&appmark=gszw";
	
	var domain=document.domain;
	//alert(domain);
	window.location.href= "../perlogin?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw";

	//window.location.href= "../perlogin?domain=118.180.24.32%3A8081&action=ticketLogin&gotoUrl=&appmark=gszw";
}
</script>
<script type="text/javascript">
	function gid(id) {
		return document.getElementById ? document.getElementById(id) : null;
	}
	function timeDesc() {
		if (all <= 0) {
			//top.location = "../perlogin?appmark=gszw";杨工说此不行。要改。
			//top.location = "../perlogin?domain=118.180.24.32%3A8081&action=ticketLogin&gotoUrl=&appmark=gszw";
			//http://118.180.24.32:8081/gsjis/front/perlogin?domain=118.180.24.32%3A8081&action=ticketLogin&gotoUrl=&appmark=gszw
			var domain=document.domain;
		//	alert(domain);
			window.location.href= "../perlogin?domain="+domain+"&action=ticketLogin&gotoUrl=&appmark=gszw";
		}
		var obj = gid("tS");
		if (obj)
			obj.innerHTML = all + " 秒后";
		all--;
	}
	var all = 5;//一共5秒
	if (all > 0)
		window.setInterval("timeDesc();", 1000);//每次减1秒
</script>
</head>

<body>
<div>
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="${ctx}/ui/js/1512101421282897.js"></script>
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="${ctx}/ui/js/1512101146476751.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
<div class="mainWth_faren back">
<div class="main">
	<div class="tb">
    	<img src="${ctx}/ui/images/smrz_03.jpg" />
    </div>
    <div class="bt">
    	注册成功
    </div>
    <div style="width: 100%;text-align: center; height:100px;">
    <!-- 	<img  src="./images/regsuccess.jpg" /> -->
    </div>
    
     <div style="margin-top: 50px;" >
			<div style="padding: 0;text-align:center;">
				<p>
					<img src="${ctx}/ui/images/regsuccess.png" />
					<span style="position: relative;top: -5px;left:10px;font-size: 30px;">恭喜您，注册成功</span>
				</p>
			</div>
			<!-- <div style="font-size: 18px;color: orange;margin-left:20px;margin-top:30px;">您可以进行以下操作：</div>   -->
			<!-- <div style="font-size: 16px;color: blue;margin-left:20px;margin-top:30px;">1、如果您想在本站进行网上办事，请进行实名认证&nbsp;&nbsp;<input type="button" class="btn btn-primary" onclick="auth()" value="申请实名认证" /></div> -->
			<!-- <div style="font-size: 16px;color: blue;margin-left:20px;margin-top:30px;">2、暂时不想实名认证，先直接登录&nbsp;&nbsp;<input type="button" onclick="login()"  class="btn btn-primary"  value="登录" /></div>-->
			<div style="font-size: 18px;color: orange;margin-left:20px;margin-top:30px;">
				<p style="text-align: center;">恭喜您，注册成功。
					<span id="tS">5秒后</span>将自动返回登录页面，或点此<a onclick="login()"  target="_top">立即返回</a><!--  a href="${loginurl}" -->
				</p>
			</div>
	</div>
</div>
</div>
  <div id="foot">
    <div> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421288942.js"></script>
    </div>
  </div>
</div>
</body>
</html>
