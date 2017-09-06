<!doctype html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>个人中心</title>
		<!-- 初始化样式 -->
		<link href="${ctx}/res/skin/default/css/reset.css" rel="stylesheet"	type="text/css">
		<link href="${ctx}/res/skin/default/css/common.css" rel="stylesheet" type="text/css">
		<link href="${ctx}/res/skin/default/css/list.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="list-warper">
			<!--列表的面包屑区域-->
			<div class="position">
				
			</div>
			<!--列表内容区域-->
			<div class="list">
				<div class="list-title">
					个人资料
				</div>
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="list-table">
					<tbody>
						<tr>
							<td class="alignC title">
								用户姓名
							</td>
							<td class="alignL">
								${sysAccount.userName}
							</td>
							<td class="alignC title">
								性别
							</td>
							<td class="alignL">
								<c:if test="${sysAccount.userSex==0}">女</c:if>
								<c:if test="${sysAccount.userSex==1}">男</c:if>  
							</td>
						</tr>
						<tr>
							<td class="alignC title">
								联系电话
							</td>
							<td class="alignL">
								${sysAccount.userTele}
							</td>
							<td class="alignC title">
								用户生日
							</td>
							<td class="alignL">
								${sysAccount.userBirthday}
							</td>
						</tr>
						<tr>
							<td class="alignC title">
								身份证
							</td>
							<td class="alignL">
								${sysAccount.userIdentityCode}
							</td>
							<td class="alignC title">
								邮箱
							</td>
							<td class="alignL">
								${sysAccount.userEmail}
							</td>
						</tr>
						<tr>
							<td class="alignC title">
								家庭地址
							</td>
							<td colspan="3">
								${sysAccount.userAddress}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
</body>
</html>