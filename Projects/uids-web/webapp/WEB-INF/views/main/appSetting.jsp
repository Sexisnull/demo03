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
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>统一身份认证系统</title>
		<script>
	$(document).ready(function() {
		$('#calltest').removeClass();
	});
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
		<div style="min-height:95%">
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
						<li class="speaker modify-msgs">
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
							<td width="80%" class="sz_yy" style="font-weight: bold;border: 1px grey solid;">
								应用系统
							</td>
							<td width="20%" class="sz_cs" style="font-weight: bold;border: 1px grey solid;">
								参数设置
							</td>
						</tr>
						<tr>
							<td class='sz_yy' style="border: 1px grey solid;">
								网站管理系统
							</td>
							<td class='sz_cs' style="border: 1px grey solid;">
								<a style='color: #ccc;'>设置</a>
							</td>
						</tr>
					</table>

					<table align="center" width="80%" border="0">
						<tr>
							<td width="34%" margin-right="20px" height="46" border="0" align="right" valign="middle">
								<a style='color: #ccc'>上一页</a>
							</td>
							<td width="30%" border="0" align="center" valign="middle">
								第1页 /共1页
							</td>
							<td width="36%" border="0" margin-left="20px" align="left" valign="middle">
								<a style='color: #ccc'>下一页</a>
							</td>
						</tr>
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
				<span class="copyright">甘肃省人民政府办公厅版权所有</span>
				<span class="instruction">建议使用1024*768，IE8浏览器以上为最佳浏览模式</span>
			</div>
		</div>

		<!-- Jquery类库 -->
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<!-- Handlebars模板组件 -->
		<script type="text/javascript" src="${ctx}/res/plugin/handlebars/handlebars.js"></script>

		<script type="text/javascript" src="${ctx}/res/plugin/jquery.layout/jquery.layout-latest.min.js"></script>
		<!-- 滚动条组件 -->
		<script type="text/javascript" src="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<%--	    <script type="text/javascript" src="${ctx}/res/plugin/nicescroll/jquery.nicescroll.min.js"></script>--%>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
		<script type="text/javascript">
	$(function() {

		//$('#nav').height($(document).height()-$('#header').height());
		$('#nav').mCustomScrollbar({
			scrollButtons : {
				enable : false
			},
			theme : "custom"
		});

		//生成NAV菜单
		$.ajax({
			url : "${ctx}/login/getSysMenuJson?rs=" + Math.random(),
			dataType : "JSON",
			async : false,
			success : function(data) {
				///alert(data);
				//如果数据返回成功，则拼接HTML元素
				var menuLength = data.length;
				if (menuLength == 0) {
					alert("您没有分配访问此系统的权限,请联系管理员!");
					$.get("${ctx}/login/loginOut");
					location.href = '${ctx}/login';
				} else {
					var nav_template = Handlebars.compile($("#nav_template")
							.html());
					$("#nav ul").html(nav_template(data));
					if (menuLength == 1) {
						$('.home').hide();
						$('#nav ul li').eq(0).find("dt").slideUp(100);
						$('#nav ul li').eq(0).addClass("active");
						var url = $('#nav ul li').eq(0).find("dt").eq(0).find(
								"a").attr("_href");
						$('#nav ul li').eq(0).find("dt").eq(0).find("a").find(
								"dl").eq(0).addClass("active");
						;
						$('#nav ul li').eq(0).find("dt").slideToggle(400);
						if (url) {
							$("#main").attr("src", url);
						}
					}
				}
			}
		});

	});
</script>
		<script type="text/javascript">
	function fade() {

		var docHeight = $(document).height(); //获取窗口高度  

		$('body').append('<div id="overlay"></div>');

		$('#overlay').height(docHeight).css({
			'opacity' : .10, //透明度  
			'position' : 'absolute',
			'top' : 0,
			'left' : 0,
			'background-color' : '#E5E5E5',
			'width' : '100%',
			'z-index' : 5000
		//保证这个悬浮层位于其它内容之上  
		});
		setTimeout(function() {
			$('#overlay').fadeOut('slow')
		}, 3000); //设置3秒后覆盖层自动淡出  
	}
</script>
	</body>
</html>
