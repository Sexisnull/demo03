<%@page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="/include/meta.jsp"%>
<html>
	
	<head>
		
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
		<meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- css -->
		<link href="${ctx}/res/skin/${theme }/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/res/skin/${theme }/css/index.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.css" />
	
		
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
                    <li class="speaker modify-msgs">
                    	<p>账户设置</p>
                    </li>
                    <li class="pwd modify-pwd"  onclick="javascript:window.location.href='${ctx}/jisLog/countUser'">
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
                    </div>
                </div>
            </div>
        </div>
	    
		<!--列表的面包屑区域-->
		<div class="position">
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/index" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a >在线用户</a>
				</li>
	    	</ol>
	    </div>
    
		<!--列表内容区域-->
		<div class="list">
			 <div class="list-topBar">
	        	 <div class="list-toolbar">
	            <!-- 操作按钮开始 -->	 
	             <ul class="list-Topbtn">
	            	<li class="add"><a title="刷新" onclick="javascript:window.location.href='${ctx}/jisLog/countUser'">刷新</a></li>
	             </ul>
	             <!-- 操作按钮结束 -->
	           </div> 
	        <!-- 提示信息开始 -->
	         <div class="form-alert;" >
	         	<tags:message msgMap="${msgMap}"></tags:message>
	    	</div>
	    	<!-- 提示信息结束 -->
	    	<!-- 列表开始 -->
	        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
	        	<thead>
	            	<tr>
	                    <th width="25%" style="text-align: center;">登录名</th>
	                    <th width="25%" style="text-align: center;">所属机构</th>
	                    <th width="25%" style="text-align: center;">访问IP</th>
	                    <th width="25%" style="text-align: center;">访问时间</th>
	                </tr>
	            </thead> 
	            <tbody>
	                <c:forEach items="${pageInfo.content}" var="countUser" varStatus="state">
						<tr class="myclass">
		                	<td style="text-align: center;">
		                    	${countUser.userName}
		                    </td>
		                	<td style="text-align: center;">
		                		${countUser.groupName}
		                    </td>
		                    <td style="text-align: center;">
		                    	${countUser.IP}
		                    </td>
		                	<td class="alignL" style="text-align: center;">
		                    	${countUser.operateTime}
		                    </td>
		                </tr>
					</c:forEach>
	            </tbody>       
	        </table>
	        <!-- 列表结束 -->
	    </div>
	    	<!-- 分页 -->
	   		<tags:pagination page="${pageInfo}" paginationSize="5"/> 
		</div>
	    
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js" ></script>
		<!-- Handlebars模板组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/handlebars/handlebars.js"></script>
	    
	    <script type="text/javascript" src="${ctx}/res/plugin/jquery.layout/jquery.layout-latest.min.js"></script>
	    <!-- 滚动条组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
    	<script type="text/javascript">
			
		
			
			function loginOut() {
				$.dialog.confirm('您确认要退出系统吗?',function(){
					$.get("${ctx}/login/loginOut");
					//?service=http://127.0.0.1:8080/aais
					//window.location.href='${ctx}/login';
					window.location.href='http://127.0.0.1:8080/uids-web';
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
