<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<style type="text/css">
.ztree {margin-top: 10px;border: 1px solid #cfd9db; display:none;background: #f0f6e4;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
<script type="text/javascript">
</script>

</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a>用户管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty outsideUser.iid}">用户新增</c:if><c:if test="${not empty outsideUser.iid}">用户编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/complat/outsideuserSave">
    
    <div style="display:none;">
    	<input type="hidden" id="outsideUserId" name="outsideUserId" value="${outsideUser.uuid}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
    			<th>基本属性</th>
    		</tr>
    		<tr>
				<th><b class="mustbe">*</b>登录名：</th>
				<td>
					<input type="text"  class="loginName" name="loginName" value="${outsideUser.loginName}" />
	            </td>
	        	<th><b class="mustbe">*</b>密码：</th>
				<td>
					<input type="password"  class="pwd" name="pwd" value="${outsideUser.pwd}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>姓名：</th>
				<td>
					<input type="text"  class="name" name="name" value="${outsideUser.name}" />
				</td>
				<th><b class="mustbe">*</b>邮箱：</th>
				<td>
					<input type="text"  class="email" name="email" value="${outsideUser.email}" />
				</td>
			</tr>
    		<tr>
				<th><b class="mustbe">*</b>手机号码：</th>
				<td>
					<input type="text"  class="mobile" name="mobile" value="${outsideUser.mobile}" />
				</td>
				<th><b class="mustbe">*</b>户籍省市区：</th>
				<td>
					<%-- <select name="degree" value="${outsideUser.gpresidenceId}">
						<option value="">请选择</option>
						<option value="">甘肃省</option>
					</select>
					<select name="degree" value="${outsideUser.presidenceId}">
						<option value="">请选择</option>
					</select>
					<select name="degree" value="${outsideUser.residenceId}">
						<option value="">请选择</option>
					</select> --%>
					<input type="text"  class="" name="null" value="${outsideUser.name}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>户籍详细地址：</th>
				<td>
					<input type="text"  class="residenceDetail" name="residenceDetail" value="${outsideUser.residenceDetail}" />
	            </td>
	        	<th><b class="mustbe">*</b>居住地省市区：</th>
				<td>
					<%-- <select name="degree" value="${outsideUser.gplivingAreaId}">
						<option value="">请选择</option>
						<option value="">甘肃省</option>
					</select>
					<select name="degree" value="${outsideUser.plivingAreaId}">
						<option value="">请选择</option>
					</select>
					<select name="degree" value="${outsideUser.livingAreaId}">
						<option value="">请选择</option>
					</select> --%>
					<input type="text"  class="" name="null" value="${outsideUser.name}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>居住地详细地址：</th>
				<td>
					<input type="text"  class="livingAreaDetail" name="livingAreaDetail" value="${outsideUser.livingAreaDetail}" />
				</td>
			</tr>
			<tr>
    			<th>详细属性</th>
    		</tr>
    		<tr>
				<th><b class="mustbe">*</b>性别：</th>
				<td>
					<input type="radio" name="sex" value = '男' <c:if test="${outsideUser.sex == '男'}">checked="checked" </c:if>>男&nbsp&nbsp&nbsp
    				<input type="radio" name="sex" value = '女' <c:if test="${outsideUser.sex == '女'}">checked="checked" </c:if>>女
				</td>
				<%-- <td>
					<input type="text"  class="sex" name="sex" value="${outsideUser.sex}" />
	            </td> --%>
	        	<th>年龄</th>
				<td>
					<input type="text"  class="age" name="age" value="${outsideUser.age}" />
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>身份证号：</th>
				<td>
					<input type="text"  class="papersNumber" name="papersNumber" value="${outsideUser.papersNumber}" />
				</td>
				<th>学历：</th>
				<td>
					<select name="degree" value="${outsideUser.degree}">
						<option value="">请选择学历</option>
						<option value='其他' <c:if test="${outsideUser.degree == '其他'}">selected</c:if>>其他</option>
						<option value='小学' <c:if test="${outsideUser.degree == '小学'}">selected</c:if>>小学</option>
						<option value='初中' <c:if test="${outsideUser.degree == '初中'}">selected</c:if>>初中</option>
						<option value='高中' <c:if test="${outsideUser.degree == '高中'}">selected</c:if>>高中</option>
						<option value='专科' <c:if test="${outsideUser.degree == '专科'}">selected</c:if>>专科</option>
						<option value='大学' <c:if test="${outsideUser.degree == '大学'}">selected</c:if>>大学</option>
						<option value='硕士' <c:if test="${outsideUser.degree == '硕士'}">selected</c:if>>硕士</option>
						<option value='博士' <c:if test="${outsideUser.degree == '博士'}">selected</c:if>>博士</option>
					</select>
				</td>
			</tr>
    		<tr>
				<th>工作单位：</th>
				<td>
					<input type="text"  class="workUnit" name="workUnit" value="${outsideUser.workUnit}" />
				</td>
				<th>职务：</th>
				<td>
					<input type="text"  class="headShip" name="headShip" value="${outsideUser.headShip}" />
				</td>
			</tr>
			<tr>
				<th>传真：</th>
				<td>
					<input type="text"  class="fax" name="fax" value="${outsideUser.fax}" />
				</td>
				<th>固定电话：</th>
				<td>
					<input type="text"  class="phone" name="phone" value="${outsideUser.phone}" />
				</td>
			</tr>
			<tr>
				<th>办公电话：</th>
				<td>
					<input type="text"  class="compTel" name="compTel" value="${outsideUser.compTel}" />
				</td>
				<th>QQ：</th>
				<td>
					<input type="text"  class="qq" name="qq" value="${outsideUser.qq}" />
				</td>
			</tr>
			<tr>
				<th>MSN：</th>
				<td>
					<input type="text"  class="msn" name="msn" value="${outsideUser.msn}" />
				</td>
				<th>邮编：</th>
				<td>
					<input type="text"  class="post" name="post" value="${outsideUser.post}" />
				</td>
			</tr>
			<tr>
				<th>联系地址：</th>
				<td>
					<input type="text"  class="address" name="address" value="${outsideUser.address}" />
				</td>
			</tr>
		</table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/complat/outsideuserList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
    </div>
    </form>
    <!--表单的底部区域-->
    <div class="form-footer"></div>
    <!--表单的预览或提示区域-->
    <div class="form-preview"></div>
    <!--表单的提示区域-->
    <div class="form-tip"></div>
    <!--表单的弹出层区域-->
    <div class="form-dialog"></div>
    
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	
</div>
</div>

</body>
</html>
