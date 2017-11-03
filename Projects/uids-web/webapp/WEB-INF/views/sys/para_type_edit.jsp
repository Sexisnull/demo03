<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<meta charset="utf-8"/>
<title></title>
<script type="text/javascript">
$().ready(function() {
//表单校验
 $("#editForm").validate({
    rules: {
	   paraTypeName: {
	    required: true,
	    cnRangelength: [0,64],
	    uniqueParaTypeName:true,
	    stringCheck:this
	   },
	   paraTypeDesc: {
	    required: true,
	    cnRangelength: [0,256],
	    stringCheck:this
	   }
	  }
    });
    $.uniqueValidate('uniqueParaTypeName', '${ctx}/sys/checkParaTypeName', ['paraTypeName','oldParaTypeName'], '对不起，这个参数类型名称重复了');
});
</script>
</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<ol class="breadcrumb">
				<li>
					<a href="${ctx}/backIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li class="active">
					系统管理
				</li>
				<li class="split"></li>
				<li class="active">
					<c:if test="${empty sysParaType.paraTypeId}">参数类型新增</c:if>
					<c:if test="${not empty sysParaType.paraTypeId}">参数类型编辑</c:if>
				</li>
			</ol>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/sys/paraTypeSave">
    
    <div style="display:none;">
    	<input type="hidden" id="id" name="paraTypeId" value="${sysParaType.paraTypeId}"/><%--
    	 <input type="hidden" id="id" name="paraTypeState" value="1"/>
    	
    --%></div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
        <table class="form-table single">
        	<tr>
        		<th><b class="mustbe">*</b>请输入参数类型名称：</th>
				<td>
					<input type="text" class="input right" id="paraTypeName" name="paraTypeName" value="${sysParaType.paraTypeName}"  />
					<input type="hidden" id="oldParaTypeName" class="input" name="oldParaTypeName" value="${sysParaType.paraTypeName}"  />
				</td>
        	</tr>
        	<tr>
        		<th><b class="mustbe">*</b>请输入参数类型描述：</th>
				<td>
					<textarea class="textarea" name="paraTypeDesc"  >${sysParaType.paraTypeDesc}</textarea>
				</td>
        	</tr>
        </table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" value="返回" onclick="history.go(-1);" class="btn gray"/>
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
