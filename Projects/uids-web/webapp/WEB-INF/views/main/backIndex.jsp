
<!doctype html>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<%@ taglib prefix="gsww" uri="http://www.wanwei.com.cn/tags/gsww"%>
		<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		
		<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
		<meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>
		<!-- css -->
		<link href="${ctx}/res/skin/${theme }/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/res/skin/${theme }/css/index.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.css" />
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>统一身份认证系统</title>
		<script>
			$(document).ready(function(){
				$('#calltest').removeClass();
			});
		</script>
		<style type="text/css">
		.logo-font{
				 float: left;
		    line-height: 68px;
		    padding: 0 20px;
		    color: white;
		    font-size: 27px;
		    font-weight: 600;
		}
		</style>
	</head>
	<body>
        <div id="header" class="ui-layout-north">
            <div class="logo">
               <%--  <img class="logo_localhost" src="${ctx}/res/skin/${theme }/images/login/${systemMap.icon }">
                <span class="title">${systemMap.title }</span> --%>
                <div class="logo1"></div>
                <div class="logo2"></div>
                <div class="logo-font"><font>统一身份认证系统</font></div>
            </div>
            <div class="callDisp nav_wrap">
            	<ul>
            		<li class="display"><font class="fonts" id="fontss"></font></li>
            	</ul>
            </div>
            <div class="header_userinfo" style="width: 575px;">
                <ul class="header_nav">
                    
                    <li class="home" onclick="toFront();">
                        <p>返回首页</p>
                    </li>
                    <li class="speaker modify-msgs" onclick="javascript:window.location.href='${ctx}/complat/userSetUpEdit?userMenu=2'">
                    	<p>账户设置</p>
                    </li>
                    <li class="pwd modify-pwd"  onclick="toCountUser();">
                        <p>在线用户</p>
                    </li>
                    
                    <li class="logout" onclick="loginOut();">
                        <p>退出系统</p>
                    </li>
				</ul>
				<div class="nav_wrap" style="margin-top: 10px;max-width:170px;">
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
                      <%--  <c:choose>
                       		<c:when test="${not empty sysUserSession.windowsName}">
                       			<p style="cursor: pointer;" onclick="CheckOk('${sysUserSession.accountId}','${groupInfo.pkId}');"  id="pkId">${groupInfo.winName}</p>
                       		</c:when>
                       		<c:otherwise>
                       			<p>${sysUserSession.deptName}</p>
                       		</c:otherwise>
                       </c:choose> --%>
                    </div>
                </div>
            </div>
        </div>
        <div id="nav" class="ui-layout-west">
        	<div class="nav-content">
        		<ul></ul>
        	</div>
        </div>
        <iframe id="main" src="${ctx}/login/getSysMain" class="ui-layout-center" frameborder="0" height="100%" width="100%" name="iframe" scrolling="yes"></iframe>
	    <div class="per-center">
	    	<div class="title">个人中心<div class="close" onclick="$('.per-center').hide()"></div></div>
	    	<div class="content">
	    	<%-- <%@include file="../personalCenter/personal_dashboard.jsp"%> --%>
	    	</div>
	    </div>
	    <div class="msgs-center" style="display: none">
	    	<div class="content">
	    	</div>
	    </div>
	    <!-- nav菜单导航模板 -->
    	<script id="nav_template" type="text/x-handlebars-template">
        {{#each this}}
        <li class="{{icon}}" id={{id}}>
            <a class="nav_content" _href="{{url}}">
                <i title="{{name}}"></i>
                <span>{{name}}</span>
            </a>
            <dt style="display:none;">
                {{#with menuitem}}
                {{#each this}}
                <a id={{id}} target="iframe" _href="{{url}}">
                    <dl><span>{{name}}</span></dl>
                </a>
                {{/each}}
                {{/with}}
            </dt>
        </li>
        {{/each}}
    	</script>
	    <!-- Jquery类库 -->
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js" ></script>
		<!-- Handlebars模板组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/handlebars/handlebars.js"></script>
	    
	    <script type="text/javascript" src="${ctx}/res/plugin/jquery.layout/jquery.layout-latest.min.js"></script>
	    <!-- 滚动条组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
<%--	    <script type="text/javascript" src="${ctx}/res/plugin/nicescroll/jquery.nicescroll.min.js"></script>--%>
	    <script type="text/javascript">
	        $(function () {
	        	var indexLayout = $('body').layout({
	                panes: {
	                    resizable: false,
	                    spacing_open: 0,
	                    spacing_close: 0
	                }
	            });
	        	indexLayout.allowOverflow('north');
	        	//$('#nav').height($(document).height()-$('#header').height());
	        	$('#nav').mCustomScrollbar({
	        		scrollButtons:{enable:false},
	        		theme:"custom"
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
						}else{
							var nav_template = Handlebars.compile($("#nav_template").html());
					        $("#nav ul").html(nav_template(data));
							if (menuLength == 1) {
								$('.home').hide();
								$('#nav ul li').eq(0).find("dt").slideUp(100);
				                $('#nav ul li').eq(0).addClass("active");
				                var url =  $('#nav ul li').eq(0).find("dt").eq(0).find("a").attr("_href");
				                $('#nav ul li').eq(0).find("dt").eq(0).find("a").find("dl").eq(0).addClass("active");;
				                $('#nav ul li').eq(0).find("dt").slideToggle(400);
				                if (url) {
				                    $("#main").attr("src", url);
				                } 
						  }
						}
					}
				});

	            
	            //导航菜单点击展开
	            $("#nav ul li").on("click", function () {
	                $(this).siblings('li').find("dt").slideUp(100);
	                $(this).siblings('li').removeClass("active");
	                $(this).addClass("active");
	                var url = $(this).find("a").attr("_href");
	                if (url) {
	                    $("#main").attr("src", url);
	                } else {
	                    $(this).find("dt").slideToggle(400);
	                }
	                setTimeout(function(){
	                	$('#nav').mCustomScrollbar('update');
	                },400);
	                
	                //$('#nav').mCustomScrollbar('update');
	            });
	
	            $("#nav ul li dt a").on("click", function (e) {
	                e = e || window.event;
	                if (e.preventDefault) {
	                    e.preventDefault();
	                    e.stopPropagation();
	                } else {
	                    e.returnValue = false;
	                    e.cancelBubble = true;
	                }
	
	                $(this).siblings("a").find("dl").removeClass("active");
	                $(this).find("dl").addClass("active");
	                
	                var url = $(this).attr("_href");
	                if (url) {
	                    $("#main").attr("src", url);
	                } else {
	                    $(this).find("dt").slideToggle(400);
	                }
	            });
	            $('#36050').click(function() {
				$("#36050").find("dt:first").hide();
				iframe.location.href ="${ctx}/login/getSysMain";
			});
	    });
	    </script>
    	<script type="text/javascript">
			function goHome() {
				$("#main").attr("src", "${ctx}/login/getSysMain");
				$("#main").reload();
			}
			
			function loginOut() {
				$.dialog.confirm('您确认要退出系统吗?',function(){
					$.get("${ctx}/login/loginOut");
					window.location.href='${ctx}';
					
				});
			}
			
			function callSkip(state){
		    	$(".call").removeClass("selected");
				$(".callMenu").slideUp("fast");
				$(".call").css("background-color","#2d74af");
		    	$.ajax({
		    		cache: true,
    				type : "POST",
    				url : "${ctx}/adCase/callSkip",
    				dataType : "json",
    				data : {handleState : state},
    				async: false,
    				success : function(msg) {
        				$(".callDisp").show();
    					$("#fontss").html(msg.result);
    				}
		    	});
			}

			function fade(){  
				   
				  var docHeight = $(document).height(); //获取窗口高度  
				     
				  $('body').append('<div id="overlay"></div>');  
				     
				  $('#overlay')  
				    .height(docHeight)  
				    .css({  
				      'opacity': .10, //透明度  
				      'position': 'absolute',  
				      'top': 0,  
				      'left': 0,  
				      'background-color': '#E5E5E5',  
				      'width': '100%',  
				      'z-index': 5000 //保证这个悬浮层位于其它内容之上  
				    });  
				    setTimeout(function(){$('#overlay').fadeOut('slow')}, 3000); //设置3秒后覆盖层自动淡出  
			}
		</script>
	</body>
</html>
