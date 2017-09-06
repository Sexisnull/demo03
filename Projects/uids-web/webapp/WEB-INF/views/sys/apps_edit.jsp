<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<style type="text/css">
	.icon {
		width: 30px;
	}
	.span{
		display: block;
		color: #aaa;
	}	
</style>
<script type="text/javascript">
	$().ready(function() {
//表单校验
var operatorNameInput=$("#appName").val();
 $("#editForm").validate({
    rules: {
	   appName: {
	    required: true,
	    cnRangelength: [0,64],
	    stringCheck:operatorNameInput
	   },
	   appUrl: {
	    required: true,
	    maxlength: 256
	   },
	   appLogo: {
	    required: true,	    
	    maxlength: 256
	   },
	   appDesc: {
	    required: true,	    
	    maxlength: 256
	   },
	   appCode: {
	    required: true,	    
	    maxlength: 256
	   }
	  },submitHandler:function(form){
            /*var operatorState=$("#operatorState").val(); 
            var operatorType=$("#operatorType").val();
            if(operatorState==''){
            	$.validator.errorShow($("#operatorState"),'请选择操作状态');
            	return false;
            }else if(operatorType==''){
            	$.validator.errorShow($("#operatorType"),'请选择操作类型');
            	return false;
            }
            else{
				 form.submit();
			}*/
			form.submit();
        } 
    });
    

});
</script>
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
					<c:if test="${empty sysApps.key}">应用新增</c:if>
					<c:if test="${not empty sysApps.key}">应用编辑</c:if>
				</li>
			</ol>
    <form id="editForm" method="post" action="${ctx}/apps/appsSave">
    
    <div style="display:none;">
    	<input type="hidden" id="id" name="key" value="${sysApps.key}"/>	
    </div>
    
    <!--表单的主内容区域-->
     <div class="form-content">
        <table class="form-table single">
        	<tr>
        		<th><b class="mustbe">*</b>请输入应用名称：</th>
				<td>
					<input type="text" class="input" id="appName" name="appName" value="${sysApps.appName}" />
				</td>
        	</tr>
        	<tr>
        		<th><b class="mustbe">*</b>请输入应用URL：</th>
				<td>
					<input type="text" class="input" id="appUrl" name="appUrl" value="${sysApps.appUrl}" />
				</td>
        	</tr>
        	<tr hidden>
        		<th><b class="mustbe">*</b>请输入应用状态：</th>
				<td>
					<input type="hidden" class="input" id="appState" name="appState" value="00A" />
				</td>
        	</tr>
        	<tr hidden>
        		<th><b class="mustbe">*</b>请输入绿色通道：</th>
				<td>
					<input type="hidden" class="input" id="appGreenChanal" name="appGreenChanal" value="00A" />
				</td>
        	</tr>
        	<tr>
        		<th><b class="mustbe">*</b>请输入应用图标：</th>
				<td>
					<input type="text" class="input" id="appLogo" name="appLogo" value="${sysApps.appLogo}" />
				</td>
        	</tr>
        	<tr>
        		<th><b class="mustbe">*</b>请输入应用描述：</th>
				<td>
					<input type="text" class="input" id="appDesc" name="appDesc" value="${sysApps.appDesc}" />
				</td>
        	</tr>
        	<tr>
        		<th><b class="mustbe">*</b>请输入应用编码：</th>
				<td>
					<input type="text" class="input" id="appCode" name="appCode" value="${sysApps.appCode}" />
				</td>
        	</tr>
        </table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" value="保存" class="btn green"/>
    	&nbsp;&nbsp;
        <input type="button" value="返回" onclick="javascript:window.location.href='${ctx}/apps/appsList?findNowPage=true'" class="btn orange"/>
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
