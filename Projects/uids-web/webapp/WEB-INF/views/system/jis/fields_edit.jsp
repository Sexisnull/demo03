<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
$().ready(function() {
	//表单校验
	$("#editForm").validate({
		rules: {
			//校验内容
			showname : {//显示名
				required: true,
				cnRangelength: [0,50]
			},
			fieldname : {//字段名
				required: true,
				cnRangelength: [0,25]
			},
			defvalue : {
				cnRangelength: [0,25]
			},
			fieldkeys : {
				cnRangelength: [0,500]
			},
			fieldvalues : {
				cnRangelength: [0,1000]
			},
		   	submitHandler:function(form){
				form.submit();
			}
		}
	});
});

$(document).on("change",'select#fieldsType',function(){
	var a = $(this).val();
	if(a == '1') {
		$("#tr_3")[0].style.display = 'none';
		$("#tr_2_1")[0].style.display = 'none';
		$("#tr_2_2")[0].style.display = 'none';
	}
	if(a == '2') {
		$("#tr_3")[0].style.display = 'none';
		$("#tr_2_1")[0].style.display = 'table-row';
		$("#tr_2_2")[0].style.display = 'table-row';
	}
	if(a=='3') {
		$("#tr_3")[0].style.display = 'table-row';
		$("#tr_2_1")[0].style.display = 'none';
		$("#tr_2_2")[0].style.display = 'none';
	}
	
});
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
				<a>个性化设置</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty jisFields.iid}">用户扩展属性新增</c:if><c:if test="${not empty jisFields.iid}">用户扩展属性编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
    <form id="editForm" method="post" action="${ctx}/jis/fieldsSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${jisFields.iid}"/>
    	<input type="hidden" id="issys" name="issys" value="${jisFields.issys}"/>
    	<input type="hidden" id="iswrite" name="iswrite" value="${jisFields.iswrite}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<!-- <div align="center">基本属性</div> -->
    	<table class="form-table" >
    		<tr>
				<th><b class="mustbe">*</b>显示名：</th>
				<td>
					<input type="text"  class="showname" name="showname" value="${jisFields.showname}" />
	            </td>
	        	<th></th>
				<td></td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b>字段名：</th>
				<td>
					<input type="text"  class="fieldname" name="fieldname" <c:if test="${jisFields.fieldname == '' || jisFields.fieldname == null}">value="ex_"</c:if> value="${jisFields.fieldname}" readonly="readonly"/>
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th>字段类型：</th>
				<td>
					<select id="fieldsType" name="type" value="${jisFields.type}">
						<option value='1' <c:if test="${jisFields.type == '1'}">selected</c:if>>字符串</option>
						<option value='2' <c:if test="${jisFields.type == '2'}">selected</c:if>>枚举型</option>
						<option value='3' <c:if test="${jisFields.type == '3'}">selected</c:if>>固定值</option>
					</select>
				</td>
				<th></th>
				<td></td>
			</tr>
			<c:if test="${jisFields.type == '3'}">
				<tr id = "tr_3" style="display: table-row;">
					<th><b class="mustbe">*</b>固定值：</th>
					<td>
						<input type="text"  class="defvalue" name="defvalue" value="${jisFields.defvalue}" />
					</td>
					<th></th>
					<td></td>
				</tr>
			</c:if>
			<tr id = "tr_3" style="display: none;">
				<th><b class="mustbe">*</b>固定值：</th>
				<td>
					<input type="text"  class="defvalue" name="defvalue" value="${jisFields.defvalue}" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<c:if test="${jisFields.type == '2'}">
				<tr id = "tr_2_1" style="display: table-row;">
					<th><b class="mustbe">*</b>Key串：</th>
					<td>
						<input type="text"  class="fieldkeys" name="fieldkeys" value="${jisFields.fieldkeys}" />
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr id = "tr_2_2" style="display: table-row;">
					<th><b class="mustbe">*</b>Value串：</th>
					<td>
						<input type="text"  class="fieldvalues" name="fieldvalues" value="${jisFields.fieldvalues}" />
					</td>
					<th></th>
					<td></td>
				</tr>
			</c:if>
			<tr id = "tr_2_1" style="display: none;">
				<th><b class="mustbe">*</b>Key串：</th>
				<td>
					<input type="text"  class="fieldkeys" name="fieldkeys" value="${jisFields.fieldkeys}" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr id = "tr_2_2" style="display: none;">
				<th><b class="mustbe">*</b>Value串：</th>
				<td>
					<input type="text"  class="fieldvalues" name="fieldvalues" value="${jisFields.fieldvalues}" />
				</td>
				<th></th>
				<td></td>
			</tr>
		</table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/jis/fieldsList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
        
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
