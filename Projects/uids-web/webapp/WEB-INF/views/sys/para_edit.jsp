<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title></title>
<script type="text/javascript">
$().ready(function() {
//表单校验
var paraNameInput=$("#paraName").val();
var paraTypeId=$("#paraTypeId").val();

 $("#editForm").validate({
    rules: {
	   paraCode: {
	    required: true,
	    maxlength: 32,
	    charNo:this,
	    uniqueParaCode:true
	   },
	   paraName: {
	    required: true,
	    cnRangelength: [0,64],
	    uniqueParaName:true
	   },
	   paraSeq:{
	   	digits:true,
	    maxlength: 3
	   }
	  }
    });
    // 参数编码重命名校验
	$.uniqueValidate('uniqueParaCode', '${ctx}/sys/checkParaCode', ['paraCode','oldParaCode','paraTypeId'], '对不起，这个参数编码重复了');
    // 参数名称重命名校验
	$.uniqueValidate('uniqueParaName', '${ctx}/sys/checkParaName', ['paraName','oldParaName','paraTypeId'], '对不起，这个参数名称重复了');
});

</script>

</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<ol class="breadcrumb">
			<li>
				首页
			</li>
			<li class="split"></li>
			<li>
				<a href="#">系统管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<c:if test="${empty sysPara.paraId}">参数新增</c:if>
				<c:if test="${not empty sysPara.paraId}">参数编辑</c:if>
			</li>
		</ol>
	<!--表单的标题区域--> 
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/sys/paraSave">
    
    <div >
    	<input type="hidden" id="id" name="paraId" value="${sysPara.paraId}"/>
    	<input type="hidden" id="paraTypeId" class="input right" name="sysParaType.paraTypeId" value="${sysPara.sysParaType.paraTypeId}" />
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content double fn-left">
    	<table class="form-table">
    		<tr>
    			<th>请输入参数编码:</th>
    			<td>
    				<input type="text" class="input" id="paraCode" name="paraCode" value="${sysPara.paraCode}"  />
            		<input type="hidden" id="oldParaCode" class="input" name="oldParaCode" value="${sysPara.paraCode}"  />
    			</td>
    			<th>请输入参数名称:</th>
    			<td>
    				<input type="text" id="paraName" class="input" name="paraName" value="${sysPara.paraName}" />
            		<input type="hidden" id="oldParaName" class="input" name="oldParaName" value="${sysPara.paraName}"  />
    			</td>
    			<th>请输入排序值:</th>
    			<td>
    				<input type="text" class="input" name="paraSeq" value="${sysPara.paraSeq}" />
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
