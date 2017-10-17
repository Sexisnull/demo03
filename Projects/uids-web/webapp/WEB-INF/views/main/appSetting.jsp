<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="gsww" uri="http://www.wanwei.com.cn/tags/gsww"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>

		<c:set var="ctx" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="-1" />
		<!-- css -->
		<link href="${ctx}/res/skin/${theme }/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/res/skin/${theme }/css/index.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/res/skin/login/css/login.css" />
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>统一身份认证系统</title>
		<script>
	$(document).ready(function() {
		$('#calltest').removeClass();
	});
		function openSetting(appid, appname){
			var url = "${ctx}/setUserDefined?appid="+appid+"&appname="+appname;
			$.dialog({
				title:'参数设置',
				width: '500px',
			    height: 260,
				max: false,
			    min: false,
			    drag: false,
			    resize: false,
			    content:'url:'+url,
			    fixed:true,
			    lock:true,
			    cache:false
				
			});
		}

</script>
		<style type="text/css">
body {
	background-color: #F2F2F2;
}

.logo-font {
	float: left;
	line-height: 68px;
	padding: 0 20px;
	color: white;
	font-size: 27px;
	font-weight: 600;
}

.sz_yy {
	align: left;
	padding-left: 70px;
	background-color: #fff;
	text-align: left;
	line-height: 33px;
}

.sz_cs {
	align: center;
	background-color: #fff;
	text-align: center
}

</style>

	</head>
	<body>
		<div style="min-height: 95%" id="source">
			<div id="header" class="ui-layout-north">
				<div class="logo">
					<%--  <img class="logo_localhost" src="${ctx}/res/skin/${theme }/images/login/${systemMap.icon }">
                <span class="title">${systemMap.title }</span> --%>
					<div class="logo1"></div>
					<div class="logo2"></div>
					<div class="logo-font">
						<font>统一身份认证系统</font>
					</div>
				</div>
				<div class="callDisp nav_wrap">
					<ul>
						<li class="display">
							<font class="fonts" id="fontss"></font>
						</li>
					</ul>
				</div>
				<div class="header_userinfo" <c:if test="${topmenueValue eq '1' and nodeType != '1'}"> style="width: 575px;" </c:if>>
					<ul class="header_nav">

						<li class="home" onclick="toBack();">
							<p>
								后台管理
							</p>
						</li>
						<li class="speaker modify-msgs"  onclick="javascript:window.location.href='${ctx}/complat/userSetUpEdit?userMenu=2&isFront=1'">
							<p>
								账户设置
							</p>
						</li>
						<li class="logout" onclick="loginOut();">
							<p>
								退出系统
							</p>
						</li>
					</ul>
					<div class="nav_wrap" style="margin-top: 10px; max-width: 170px;">
						<div class="nav_userinfo">
							<p class="name" title="${sysUserSession.userName}">
								你好，
								<c:if test="${fn:length(sysUserSession.userName)>7 }">
		                         ${fn:substring(sysUserSession.userName, 0, 7)}...
		                   </c:if>
								<c:if test="${fn:length(sysUserSession.userName)<=7 }">
		                         ${sysUserSession.userName}
		                   </c:if>
							</p>

						</div>
					</div>
				</div>
			</div>
			<div class="main" style="margin-top: 10px">
				<div class="sso">
					<h3>
						<span>〈 <a href="frontIndex">返回</a> </span>应用设置
					</h3>
					<table width="80%" border="0" align="center" cellpadding="2" cellspacing="1" style="margin: 50px; display: in" bgcolor="#CBC9CC">
						<tr>
							<td width="80%" class="sz_yy" style="font-weight: bold; border: 1px grey solid;">
								应用系统
							</td>
							<td width="20%" class="sz_cs" style="font-weight: bold; border: 1px grey solid;">
								参数设置
							</td>
						</tr>
						<c:forEach items="${application }" var="app">
							<tr>
								<td class='sz_yy' style="border: 1px grey solid;">
									${app.name }
								</td>
								<td class='sz_cs' style="border: 1px grey solid;">
									<c:if test="${app.userdefined!=0 || app.logintype==0 }">
										<a style='color: #ccc;'>设置</a>
									</c:if>
									<c:if test="${app.userdefined==0 && app.logintype!=0 }">
										<a href='#' onclick="openSetting('${app.iid}','${app.name }')">设置</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>

					<div style="display: inline"></div>
				</div>
			</div>
			<div class="msgs-center" style="display: none">
				<div class="content">
				</div>
			</div>
		</div>
		<div class="bottom">
			<div class="foot" style="height: 36px">
				<span class="copyright">${rightMsg }</span>
				<span class="instruction">建议使用1024*768，IE8浏览器以上为最佳浏览模式</span>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
	</body>
</html>
