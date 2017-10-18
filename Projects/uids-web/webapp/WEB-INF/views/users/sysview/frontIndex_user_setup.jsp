<%@page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ include file="/include/meta.jsp"%>
<html>

	<head>
		<meta name=”renderer” content=”webkit|ie-comp|ie-stand” />  
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="-1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- css -->
		<link href="${ctx}/res/skin/${theme }/css/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/res/skin/${theme }/css/index.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.css" />

		<title>统一身份认证系统</title>
		<script>
	$(document).ready(
					function() {
						//表单校验
						var userNameInput = $("#name").val();
						$("#editForm").validate({
							rules : {
								name : {
									required : true,
									cnRangelength : [ 0, 33 ],
									stringCheck : userNameInput
								},
								loginname : {
									required : true,
									cnRangelength : [ 0, 33 ]
								},
								pwd : {
									required : true
								},
								confPwd : {
									required : true,
									equalTo : "#pwd"
								},
								cardid : {
									isIdCardNo : true,
									maxlength : 18
								},
								mobile : {
									isMobile : true
								},
								pwd : {
									required : true
								},
								email : {
									email : true
								},
								phone : {
									isPhone : true
								},
								fax : {
									isFax : true
								},
								qq : {
									isQQ : true
								}
							},
							submitHandler : function() {
								$.ajax({
									type : "POST",
									async : false,
									url : '${ctx}/complat/userSetUpSave',
									data : $("#editForm").serialize(),
									dataType : "json",
									success : function(data) {
										if (data.ret == 0) {
											alert(data.msg);
										} else if (data.ret == 1) {
											alert(data.msg);
										}else if(data.ret == 2){
											alert(data.msg);
										}
									}
								});
							}
						});

						//获取用户扩展属性
						var htmlString = [];
						var count = 1;
						var table = $(".form-table");
						var fieldsListMap = eval('${fieldsListMap}');
						var textName;
						var textValue;
						for ( var i = 0; i < fieldsListMap.length; i++) {
							var fieldsList = fieldsListMap[i];
							for ( var j = 0; j < fieldsList.length; j++) {
								var fields = fieldsList[j];
								if (fields.type == 1) {
									for ( var key in fields) {
										if(key == "fieldname"){
											textName = fields[key];//类似ex_test
											textValue = fields[textName];
											if(textValue == null){
												textValue = "";
											}
										}
										if(key == "showname"){
											var textTitle = fields[key];
											if (count % 2 == 1) {
													htmlString
															.push("<tr><th>"
																	+ textTitle
																	+ "</th><td><input name='"+textName+"' type='text' value='"+textValue+"'></td>");
												}
												if (count % 2 == 0) {
													htmlString
															.push("<th>"
																	+ textTitle
																	+ "</th><td><input type='text' name='"+textName+"' value='"+textValue+"'></td></tr>");
												}
												count++;
										}
										
									}
								}


								var values;
								var keys;
								var selectTitle;
								if (fields.type == 2) {
									for ( var key in fields) {
										var value = fields[key];
										if(key == "showname"){
											selectTitle = fields[key];
										}
										if (key == 'fieldkeys') {
											keys = value.split(",");
										}

										if (key == 'fieldvalues') {
											values = value.split(",");
										}
										
										if (key != 'type' && key != 'userid') {										
											if (key == 'fieldname') {
												if (count % 2 == 1) {

													htmlString
															.push("<tr><th>"
																	+ selectTitle
																	+ "</th><td><select id='"+value+"' name= '"+value+"'>");
													//循环key；
													for ( var i = 0; i < keys.length; i++) {
														htmlString
																.push("<option value='"
																		+ keys[i]
																		+ "'");
														//获取下拉列表默认值
														var select = eval('${jsonMap}');
														for ( var selectKey in select[0]) {
															var selectValue = select[0][selectKey];
															if (selectValue == keys[i]) {
																htmlString
																		.push("selected = 'selected'");
															}
														}
														htmlString.push(">"
																+ values[i]
																+ "</option>");
													}
													htmlString
															.push("</select></td>");

												}
												if (count % 2 == 0) {
													htmlString
															.push("<th>"
																	+ selectTitle
																	+ "</th><td><select id='"+value+"' name= '"+value+"'>");
													//循环key；
													for ( var i = 0; i < keys.length; i++) {
														htmlString
																.push("<option value='"
																		+ keys[i]
																		+ "'");
														//获取下拉列表默认值
														var select = eval('${jsonMap}');
														for ( var selectKey in select[0]) {
															var selectValue = select[0][selectKey];
															if (selectValue == keys[i]) {
																htmlString
																		.push("selected = 'selected'");
															}
														}
														htmlString.push(">"
																+ values[i]
																+ "</option>");
													}
													htmlString
															.push("</select></td></tr>");
												}
												count++;
											}
										}
									}
								}
							}
						}
						table.append(htmlString.join(""));
						
						//编辑页面密码强度显示
						var pwding = $("#pwd").val();
    					EvalPwd(pwding);
    					
    					//传真
					    jQuery.validator.addMethod("isFax", function(value, element) { 
					           var corporName = /^(\d{3,4}-)?\d{7,8}$/;   
					           return this.optional(element) || (corporName.test(value));     
					    }, "传真格式错误");
					    //qq
					    jQuery.validator.addMethod("isQQ", function(value, element) { 
					           var corporName = /[1-9][0-9]{4,}/;   
					           return this.optional(element) || (corporName.test(value));     
					    }, "QQ格式错误");
					});


</script>
		<style type="text/css">
.logo-font {
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
			<div class="header_userinfo" style="width: 575px;">
				<ul class="header_nav">
					<li class="home" onclick="toFront();" style="${managerIcon}">
						<p>
							返回首页
						</p>
					</li>
					<li class="speaker modify-msgs"   onclick="javascript:window.location.href='${ctx}/complat/userSetUpEdit?userMenu=2'">
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

		<!--列表的面包屑区域-->
		<div class="position">
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/frontIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a>账户设置</a>
				</li>
				<li class="split"></li>
			</ol>
		</div>
		<!--表单的标题区域-->
		<form id="editForm">

			<div style="display: none;">
				<input type="hidden" id="iid" name="iid" value="${complatUser.iid}" />
				<input type="hidden" id="userDetailIid" name="userDetailIid" value="${userDetail.iid}">
				<input type = "hidden" id="level"  name="level">
			</div>

			<!--表单的主内容区域-->
			<div class="form-content">
				<table class="form-table">
					<tr>
						<th>
							<b class="mustbe">*</b>姓名：
						</th>
						<td>
							<input type="text" id="name" name="name"
								value="${complatUser.name}" maxlength="33" />
						</td>
						<th>
							登录名：
						</th>
						<td>
							<input type="text" id="loginname" name="loginname"
								value="${complatUser.loginname}" readonly="readonly"
								maxlength="33" />
						</td>
					</tr>
					<tr>
						<th>
							密码：
						</th>
						<td>
							<input type="password" id="pwd" name="pwd" value="${pwd}"
								onkeyup="javascript:EvalPwd(this.value);" />
						</td>
						<th>
							所属机构：
						</th>
						<td>
							<input type="text" class="input" name="groupName"
								value="${complatGroup.name}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th>
							密码强度：
						</th>
						<td>
							<table id="pwdpower" style="width: 84%" cellspacing="0"
								cellpadding="0" border="0">
								<tbody>
									<tr>
										<td id="pweak"
											style="text-align: center; width: 100px; border: 1px solid grey">
											弱
										</td>
										<td id="pmedium"
											style="text-align: center; width: 100px; border: 1px solid grey">
											中
										</td>
										<td id="pstrong"
											style="text-align: center; width: 100px; border: 1px solid grey">
											强
										</td>
									</tr>
								</tbody>
							</table>
						</td>
						<th>
							职务：
						</th>
						<td>
							<input type="text" class="input" name="headship" id="headship"
								value="${complatUser.headship}" />
						</td>
					</tr>
					<tr>
						<th>
							重复密码：
						</th>
						<td>
							<input type="password" name="confPwd" id="confPwd" value="${pwd}">
						</td>
						<th>
							身份证号：
						</th>
						<td>
							<input type="text" class="input" name="cardid" id="cardid"
								value="${userDetail.cardid}" />
						</td>
					</tr>
					<tr>
						<th>
							办公电话：
						</th>
						<td>
							<input type="text" class="input" name="phone" id="phone"
								value="${complatUser.phone}" />
						</td>
						<th>
							移动电话：
						</th>
						<td>
							<input type="text" class="input" name="mobile" id="mobile"
								value="${complatUser.mobile}" />
						</td>
					</tr>
					<tr>
						<th>
							传真：
						</th>
						<td>
							<input type="text" class="input" name="fax" id="fax"
								value="${complatUser.fax}" />
						</td>
						<th>
							Email：
						</th>
						<td>
							<input type="text" class="input" name="email" id="email"
								value="${complatUser.email}" />
						</td>
					</tr>
					<tr>
						<th>
							QQ：
						</th>
						<td>
							<input type="text" class="input" name="qq" id="qq"
								value="${complatUser.qq}" />
						</td>
						<th>
							角色：
						</th>
						<td>
							<textarea rows="3" cols="10" id="roles" name="roles"
								readonly="readonly">
								<c:forEach items="${roleList}" var="complatRole">
									${complatRole.name}&nbsp;&nbsp;
								</c:forEach>
							</textarea>
						</td>
					</tr>
				</table>
			</div>
			<div style="clear: both;"></div>
			<!--表单的按钮组区域-->
			<div class="form-btn">
				<input type="submit" tabindex="15" id="submit-btn" value="保存"
					class="btn bluegreen" />
				&nbsp;&nbsp;
				<input type="button" tabindex="16" value="返回"
					onclick="javascript:window.history.back();" class="btn gray" />
			</div>
		</form>


		<script type="text/javascript"
			src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<!-- Handlebars模板组件 -->
		<script type="text/javascript"
			src="${ctx}/res/plugin/handlebars/handlebars.js"></script>

		<script type="text/javascript"
			src="${ctx}/res/plugin/jquery.layout/jquery.layout-latest.min.js"></script>
		<!-- 滚动条组件 -->
		<script type="text/javascript"
			src="${ctx}/res/plugin/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="${ctx}/res/skin/login/js/login.js"></script>
		<!-- 密码强度校验 -->
		<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
		<script type="text/javascript">
	function loginOut() {
		$.dialog.confirm('您确认要退出系统吗?', function() {
			$.get("${ctx}/login/loginOut");
			//?service=http://127.0.0.1:8080/aais
			//window.location.href='${ctx}/login';
			window.location.href = 'http://127.0.0.1:8080/uids-web';
		});
	}

	function callSkip(state) {
		$(".call").removeClass("selected");
		$(".callMenu").slideUp("fast");
		$(".call").css("background-color", "#2d74af");
		$.ajax({
			cache : true,
			type : "POST",
			url : "${ctx}/adCase/callSkip",
			dataType : "json",
			data : {
				handleState : state
			},
			async : false,
			success : function(msg) {
				$(".callDisp").show();
				$("#fontss").html(msg.result);
			}
		});
	}

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
