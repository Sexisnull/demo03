
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
				//表单校验
				var userNameInput=$("#name").val();
				 $("#editForm").validate({
				    rules: {
					   name: {
					    required: true,
					    cnRangelength: [0,33],
					    stringCheck:userNameInput  
					   },
					   loginname:{
					   	required: true,
					   	cnRangelength:[0,33]
					   },
					   pwd:{
					   	required:true
					   },
					   confPwd:{
					   	required:true,
					   	equalTo:"#pwd"
					   },
					   cardid:{
					   	isIdCardNo:true,
					   	 maxlength: 18
					   },
					   mobile:{
					   	isMobile:true
					   },
					   pwd:{
					   	required: true
					   },
					   email:{
					   	email:true
					   },
					   phone:{
					   	isPhone:true
					   }
					  }
				    }); 
				    
				    //获取用户扩展属性
				    var htmlString=[];
				    var count = 1;
				    var table = $(".form-table");
				    var fieldsListMap = eval('${fieldsListMap}');
				    for(var i=0;i<fieldsListMap.length;i++){
				    	var fieldsList = fieldsListMap[i];
				    	for(var j = 0;j<fieldsList.length;j++){
				    		var fields = fieldsList[j];
				    		if(fields.type==1){
				    		   for(var key in fields){
				    			var value = fields[key];
				    			if(key!='type' && key !='userid'){
				    			    if(count%2==1){
				    			       htmlString.push("<tr><th>"+key+"</th><td><input name='"+key+"' type='text' value='"+value+"'></td>");
				    			    }
				    			    if(count%2==0){
				    			       htmlString.push("<th>"+key+"</th><td><input type='text' value='"+value+"'></td></tr>");
				    			    }
				    			    count++;
				    			}
				    		   } 
				    		}
				    		
				    		var values;
				    		var keys;
				    		if(fields.type==2){
				    		   for(var key in fields){
				    			var value = fields[key];
				    			if(key == 'fieldkeys'){
				    				keys = value.split(",");
				    			}
				    			
				    			if(key == 'fieldvalues'){
				    				values = value.split(",");
				    			}
				    			
				    			if(key!='type' && key !='userid'){
				    			    if(key == 'fieldname'){
				    			    if(count%2==1){
				    			    alert("1111");
				    			    	htmlString.push("<tr><th>"+value+"</th><td><select id= '"+value+"'>");
				    			    	
				    			    	//循环key；
				    			        for(var i=0;i<keys.length;i++){
				    					  htmlString.push("<option value='"+keys[i]+"'>"+values[i]+"</option>");
				    				    }
				    			       htmlString.push("</select></td>");
				    			    }
				    			    if(count%2==0){
				    			    alert("0000000");
				    			    	htmlString.push("<th>"+value+"</th><td><select id= '"+value+"'>");
				    			    	
				    			    	//循环key；
				    			        for(var i=0;i<keys.length;i++){
				    					  htmlString.push("<option value='"+keys[i]+"'>"+values[i]+"</option>");
				    				    }
				    			       htmlString.push("</select></td></tr>");
				    			    }
				    			    count++;
				    			   	}
				    			}
				    		  } 
				    		}
				    	}
				    }
				    table.append(htmlString.join(""));
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
					<a href="${ctx}/backIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a>用户设置</a>
				</li>
				<li class="split"></li>
	   		</ol>
    	</div>
    	<!--表单的标题区域--> 
	    <form id="editForm" method="post" action="${ctx}/complat/userSetUpSave">
	    
		    <div style="display:none;">
		    	<input type="hidden" id="iid" name="iid" value="${complatUser.iid}"/>
		    	<input type="hidden" id="userDetailIid" name="userDetailIid" value="${userDetail.iid}">
		    </div>
		    
		    <!--表单的主内容区域-->
		    <div class="form-content">
		    	<table class="form-table">
		    		<tr>
			        	 <th><b class="mustbe">*</b>姓名：</th>
			        	 <td>
							<input type="text" id="name" name="name" value="${complatUser.name}" maxlength="33"/>
						</td>
						<th>登录名：</th>
						<td>
							<input type="text" id="loginname" name="loginname" value="${complatUser.loginname}" readonly="readonly"  maxlength="33"/>
						</td>
					</tr>
					<tr>
						<th>密码：</th>
		                <td>
		                	<input type="password" id="pwd" name="pwd" onkeyup="javascript:EvalPwd(this.value);"/>
		                </td>
						<th>所属机构：</th>
			        	<td>
			        		<input type="text" class="input" name="groupName" value="${complatGroup.name}" readonly="readonly"/>
			        	</td>
					</tr>
					<tr>
						<th>密码强度：</th>
						<td>
							<table id="pwdpower" style="width: 84%">
								<tbody>
									<tr>
										<td id="pweak" style="">弱</td>
										<td id="pmedium" style="">中</td>
										<td id="pstrong" style="">强</td>
									</tr>
								</tbody>
							</table>
						</td>
						<th>职务：</th>
			        	<td>
			        		<input type="text" class="input" name="headship" id="headship" value="${complatUser.headship}" />
			        	</td>
					</tr>
					<tr>
						<th>重复密码：</th>
						<td>
							<input type="password"  name="confPwd" id="confPwd">
			            </td>
			            <th>身份证号：</th>
						<td>
							<input type="text"  class="input" name="cardid" id="cardid" value="${userDetail.cardid}"  />
						</td>
					</tr>
					<tr>
						<th>办公电话：</th>
						<td>
							<input type="text"  class="input" name="phone" id="phone" value="${complatUser.phone}" />
						</td>
						<th>移动电话：</th>
						<td>
							<input type="text" class="input" name="mobile" id="mobile" value="${complatUser.mobile}" />
						</td>
					</tr>
					<tr>
						<th>传真：</th>
						<td>
							<input type="text"  class="input" name="fax" id="fax" value="${complatUser.fax}" />
						</td>
						<th>Email：</th>
						<td>
							<input type="text"  class="input" name="email" id="email" value="${complatUser.email}"  />
						</td>
					</tr>
					<tr>
						<th>QQ：</th>
						<td>
							<input type="text"  class="input" name="qq" id="qq"  value="${complatUser.qq}"  />
						</td>
						<th>角色：</th>
						<td>
							<textarea rows="3" cols="3" id="roles" name="roles" readonly="readonly">
								<c:forEach items="${roleList}" var="complatRole">
									${complatRole.name}&nbsp;&nbsp;
								</c:forEach>
							</textarea>
			            </td>
					</tr>
				</table>
		    </div>
	    	<div style="clear:both;"></div>
		    <!--表单的按钮组区域-->
		    <div class="form-btn">
		    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
		    	&nbsp;&nbsp;
		        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/corporationList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
		    </div>
	    </form>
		
	    
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js" ></script>
		<!-- Handlebars模板组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/handlebars/handlebars.js"></script>
	    
	    <script type="text/javascript" src="${ctx}/res/plugin/jquery.layout/jquery.layout-latest.min.js"></script>
	    <!-- 滚动条组件 -->
	    <script type="text/javascript" src="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
		<!-- 密码强度校验 -->
		<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
		
		<!-- 用户扩展属性 -->
		<script type="text/javascript" src="${ctx}/res/js/region/userFields.js"></script>
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
