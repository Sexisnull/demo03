<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<META http-equiv="X-UA-Compatible" content="IE=9">
		</META>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
		<!-- Jquery类库 -->
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/plugin/jquery.poshytip/jquery.poshytip.js"></script>
		<link rel="stylesheet" href="${ctx}/res/plugin/bootstrap/2.3.2/css/bootstrap.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/login.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/reset.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/res/plugin/jquery.poshytip/tip-yellow/tip-yellow.css" type="text/css">


		<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
		<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
		<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
		<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
		<script type="text/javascript" src="${ctx}/ui/lib/security/jquery.cookie.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/BigInt.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/Barrett.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/RSA.js"></script>
		<script type="text/javascript">
$(function(){
	var groupMenu = [{"name":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];

	$('#groupname').menu({
		tree : 'groupmenu',
		height : 200,
		init : function() {
			setting('groupmenu', onClickGroup, onDbClickGroup, groupMenu);
		}
	});



});
function hideGroupMenu(){
	$('#groupname_menu').css('display','none');
}
function onClickGroup(event, treeId, treeNode) {
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	hideGroupMenu();
}
function onDbClickGroup(event, treeId, treeNode) {
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled )//根节点及失效节点双击无效
		return;
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	$('#groupname_menu').fadeOut(50);
}

/**
 *	初始化树
 */
function setting(treeName, onClickFunction, onDblClickFunction, rootNode) {
	var setting = {
		async : {
			enable : true,
			url : 'login/getGroup',
			autoParam : [ "id=groupId", "isDisabled" ]
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClickFunction,
			onDblClick : onDblClickFunction
		}
	};
	$("#" + treeName).tree(setting, rootNode);
//	$("#" + treeName).tree().refreshNode('');
}
/**
 *	机构选择节点点击前回调
 */
function beforeClick(treeId, treeNode, clickFlag) {
	if (treeNode.isDisabled)
		return false;
	return (treeNode.id != 0);
}
function resetform() {
	$('form').find(':input').not(':button,:hidden,:submit,:reset').val('');
}



</script>

		<title>${systemMap.title }</title>
	</head>
	<body>
		<div class="nav-bar">
			<div class="logo-container">
				<div class="row">
					<div class="col-md-3">
						<div class="header-left clearfix">
							<div class="logo"></div>
							<div class="logo1">
							</div>
							<div class="title">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="main-swap">
			<div class="container">
				<div class="row">
					<div class="col-md-3" style="height: 20px;"></div>
					<div class="col-md-9">
						<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
							<input type="hidden" name="groupid" id="groupid" value="128">
							<div class="login-swap">
								<fieldset>
									<legend>
										用户登录
									</legend>
									<div class="control-group">
										<label class="control-label" for="inputUser">
											机构：
										</label>
										<div class="controls">
											<input name="groupname" id="groupname" value="甘肃省" type="text" style="cursor: pointer;"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="inputUser">
											用户名：
										</label>
										<div class="controls">
											<input type="text" class="input-large" id="userName" name="userName" maxlength="32" placeholder="请输入用户名">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="inputPassword">
											密码：
										</label>
										<div class="controls">
											<input type="password" class="input-large" id="passWord" name="passWord" maxlength="32" placeholder="请输入密码">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">
											验证码：
										</label>
										<div class="controls">
											<input type="text" class="input-medium" id="authCode" name="authCode" maxlength="4" placeholder="请输入验证码">
											<img id="code_" src="kaptcha/image" alt="点击更换验证码" onclick="chimg();" style="width: 53px; height: 25px; cursor: pointer;" />
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<input type="checkbox" class="pass_box" id="rememberme">
											&nbsp;
											<label class="checkLabel" for="rememberme">
												&nbsp;记住密码
											</label>
											</input>
											<!--<input type="checkbox" class="pass_box" id="autologin" style="margin-left: 42px;">&nbsp;<label class="checkLabel" for="autologin">&nbsp;自动登录</label></input>
			    		-->
										</div>
									</div>
									<hr style="margin-bottom: 5px;" />
									<div class="control-group">
										<div class="controls">
											<a id="btn_reset" class="btn" href="#"><i class="icon-repeat"></i> 重置</a>
											<a id="btn_submit" class="btn btn-primary" href="#" onclick="login();"><i class="icon-lock icon-white"></i> 登录</a>
											<%--<a href="http://127.0.0.1:8081/sso-web/login">返回</a>
			    		--%>
										</div>
									</div>
								</fieldset>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			技术支持 甘肃万维信息技术有限责任公司
		</div>
		<script type="text/javascript">
	function resizeWindow() {
		var winHeight = $(window).height();
		var topHeight = (winHeight - 400)/2-150;
		if (topHeight>0) {
			$(".nav-bar").css({"margin-top": topHeight*1, "margin-bottom": topHeight*0});
		}
	}
	$(document).ready(function(e) {
		resizeWindow();
		$(window).resize(function(){
			resizeWindow();
		});
		$("#userName").blur(function() {
			$("#userName").poshytip('hide');
			if ($("#userName").val().length == 0) {
				$("#userName").poshytip('update', '用户名不能为空！');
				$("#userName").poshytip('show');
				$("#userName").focus();
				return false;
			}
		});
		
		$('#userName,#passWord,#authCode').poshytip({
			showTimeout : 1,
			showOn : 'none',
			alignTo : 'target',
			alignX : 'left',//inner-left
			alignY: 'center',
			offsetY : 5,
			offsetX: 5,
			allowTipHover : false
		});
		
		$('#btn_reset').click(function(){
			$('#userName,#passWord,#authCode').val('');
			
		});
						
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		           $('#btn_submit').click();//处理事件
		     }
		}
		
		$('#rememberme').on('click', function() {
			if ($(this).hasClass('active')) {
				$(this).removeClass('active');
				delCookie("rememberme");
			} else {
				$(this).toggleClass("active");
				SetCookie("rememberme", "YES");
			}
		});
		var rememberme = getCookie("rememberme");
		if (rememberme == 'YES') {
			$('#rememberme').addClass('active');
			$('#userName').val(getCookie("userName"));
			$('#passWord').val(getCookie("passWord"));
			$("#rememberme").attr("checked", true);
		} else {
			delCookie("userName");
			delCookie("passWord");
			$("#rememberme").attr("checked", false);
		}
	});
		
	window.onbeforeunload = function() {
	//关闭窗口时自动退出
		if (event.clientX > 360 && event.clientY < 0 || event.altKey) {
			//alert(parent.document.location);
		}
	};

	//用户登录
	function login() {
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		var groupid = $("#groupid").val();
		var authCode = $("#authCode").val();
		var loginURL = '${ctx}/login/sysLogin';
		var indexURL = '${ctx}/frontIndex';
		if (!userName || userName == '') {
			$("#userName").poshytip('update', '用户名不能为空！');
			$("#userName").poshytip('show');
			$("#userName").focus();
			return false;
		} else {
			$("#userName").poshytip('hide');
		}
		if (!passWord || passWord == '') {
			$("#passWord").poshytip('update', '密码不能为空!');
			$("#passWord").poshytip('show');
			$("#passWord").focus();
			return false;
		} else {
			$("#passWord").poshytip('hide');;
		}
		if (!groupid || groupid == '') {
			$("#groupid").poshytip('update', '请选择所属机构!');
			$("#groupid").poshytip('show');
			$("#groupid").focus();
			chimg();
			return false;
		}else{
			$("#authCode").poshytip('hide');
		}
		if (!authCode || authCode == '') {
			$("#authCode").poshytip('update', '验证码不能为空!');
			$("#authCode").poshytip('show');
			$("#authCode").focus();
			chimg();
			return false;
		}else{
			$("#authCode").poshytip('hide');
		}
		 $.ajax({
         	type : "post",
				url : "sys/mybatis/getkey",
				data : {},
				dataType : "json",
				async: false,
				success : function(data) {
	                  //data为获取到的公钥数据
	                  var pubexponent =data.pubexponent;
	                  var pubmodules =data.pubmodules;
	                  setMaxDigits(200);  
	                  var key = new RSAKeyPair(pubexponent, "", pubmodules);
	                  passWord= encryptedString(key, encodeURIComponent(passWord));
	                  userName = encryptedString(key, encodeURIComponent(userName));
				}
         });
		$.ajax({
			type : "POST",
			url : loginURL,
			data : "userName=" + userName + "&passWord=" + passWord+"&groupid="+groupid+"&kaptcha="+authCode,
			dataType : "json",
			success : function(msg) {
				if (msg.ret == 0) {
					if (getCookie('rememberme') == 'YES') {
						SetCookie("userName", userName);
						SetCookie("passWord", passWord);
						SetCookie("groupId", groupid);
						SetCookie("authCode", authCode);
					}
					window.location.href = indexURL;
				} else if (msg.ret == 2 || msg.ret == 3) {
					$("#userName").poshytip('update', msg.msg);
					$("#userName").poshytip('show');
					//$("#sub").removeClass("login_submit");
					//$("#sub").addClass("login_box");;
					$("#userName").focus();
					chimg();
				}else if(msg.ret == 1){
					$("#authCode").poshytip('update', msg.msg);
					$("#authCode").poshytip('show');
					//$("#sub").removeClass("login_submit");
					$("#authCode").focus();
					chimg();
				}
			}
		});
		//$("#login").submit();
	}
	//显示错误信息方法
	// obj : 当前错误的元素
	// msg :　错误信息
	function showError(obj, msg) {
		var $e = $(obj);
		$('.error').css('top', ($e.offset().top - 12)).css('left',
			$e.offset().left - 222).css('display', 'block').find('span')
			.html(msg);
			$e.addClass('error-input');
			shake($('.error'), $e.offset().left - 222);
	}
	//抖动
	function shake(o, box_left) {
		var $panel = $(o);
		//box_left = ($(window).width() -  $panel.width()) / 2;
		//$panel.css({'left': box_left,'position':'absolute'});
		for ( var i = 1; 4 >= i; i++) {
			$panel.animate({
				left : box_left - 0.3 * (40 - 10 * i)
			}, 50);
			$panel.animate({
				left : box_left + 0.3 * (40 - 10 * i)
			}, 50);
		}
	}
	//取消登陆框错误显示，并注销错误信息显示框
	function delErrorConten(obj) {
		$('.error').css('display', 'none');
		$(obj).removeClass('error-input');
	}
		
	//cookie操作
	function addCookie(objName, objValue, objHours) { //添加cookie
		var str = objName + "=" + escape(objValue);
		if (objHours > 0) { //为时不设定过期时间，浏览器关闭时cookie自动消失
			var date = new Date();
			var ms = objHours * 3600 * 1000;
			date.setTime(date.getTime() + ms);
			str += "; expires=" + date.toGMTString();
		}
		document.cookie = str;
	}
	
	function SetCookie(name, value){//两个参数，一个是cookie的名子，一个是值
		var Days = 30; //此 cookie 将被保存 30 天
		var exp = new Date(); //new Date("December 31, 9998");
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires="
				+ exp.toGMTString();
	}
	function getCookie(name){//取cookies函数
		var arr = document.cookie.match(new RegExp("(^| )" + name
			+ "=([^;]*)(;|$)"));
		if (arr != null)
			return unescape(arr[2]);
		return null;
	}
	
	function delCookie(name){//删除cookie
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if (cval != null)
			document.cookie = name + "=" + cval + ";expires="
				+ exp.toGMTString();
	}
			
	function chimg() {
		$("#code_").attr(
    				'src',
    				'kaptcha/image?'	+ Math.floor(Math.random() * 100)).fadeIn();
		$("#authCode").val("");
	}
</script>
	</body>
</html>
