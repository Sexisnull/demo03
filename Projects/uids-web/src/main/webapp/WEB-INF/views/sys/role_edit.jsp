<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/include/meta.jsp"%> 
<head>
<title></title>
<script type="text/javascript">
$().ready(function() {
//表单校验
var roleNameInput=$("#roleName").val();
 $("#editForm").validate({
    rules: {
	   roleName: {
	    required: true,
	    cnRangelength: [0,128],
	    uniqueRoleName:true,
	    stringCheck:roleNameInput
	   },
	   roleDesc: {
	    cnRangelength: [0,1024]
	   }
	  }
    });
  // Ajax重命名校验
	$.uniqueValidate('uniqueRoleName', '${ctx}/sys/checkRole', ['roleName','oldRoleName'], '对不起，这个角色重复了');
});
</script>
<style type="text/css">
.form-content textarea {
	height: 100px;
}
</style>
</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a href="#">系统管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<c:if test="${empty sysRole.roleId}">角色新增</c:if>
				<c:if test="${not empty sysRole.roleId}">角色编辑</c:if>
			</li>
		</ol>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/sys/roleSave">
    
    <div style="display:none;">
    	<input type="hidden" id="id" name="roleId" value="${sysRole.roleId}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content" >
    	<table class="form-table single">
    		<tr>
    			<th style="text-align: center;"><b class="mustbe">*</b>请输入角色名称:</th>
    			<td>
    				<input type="text" id="roleName" class="input" name="roleName" value="${sysRole.roleName}"  style="width: 92.5%;"/>
            	<input type="hidden" id="oldRoleName" class="input" name="oldRoleName" value="${sysRole.roleName}"  />
    			</td>
    		</tr>
    		<tr>
    			<th style="text-align: center;"><b class="mustbe">*</b>请输入角色描述:</th>
    			<td>
    			<textarea class="textarea" name="roleDesc" style="width: 89.1%;">${sysRole.roleDesc}</textarea>
    			</td>
    		</tr>
    	</table>        
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" value="返回" onclick="javascript:window.location.href='${ctx}/sys/roleList?findNowPage=true'" class="btn gray"/>
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
</div>
</body>
</html>