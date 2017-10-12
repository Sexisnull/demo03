<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
$().ready(function() {
	//表单校验
	//var fieldName=$("#fieldname").val();
	$("#editForm").validate({
		rules: {
			//校验内容
			showname: {
		     required: true,
		   	 maxlength: 50,
		   	 isDefvalue:true
		   	},
		    fieldname : {
		     required: true,
		     maxlength: 50,
		     isFieldname: true,
		     uniqueName: true
		    },
			defvalue : {
				maxlength: 50,
				isDefvalue:true
			},
			fieldkeys : {
				cnRangelength: [0,500],
				isFieldkeyAndvalues: true
			},
			fieldvalues : {
				cnRangelength: [0,1000],
				isFieldkeyAndvalues: true
			}
		}
	});
	// Ajax重命名校验
	$.uniqueValidate('uniqueName', '${ctx}/jis/checkFieldname', ['fieldname','oldFieldname'], '对不起，这个字段名称重复了');

	// 字段名称校验
	jQuery.validator.addMethod("isFieldname", function(value, element) { 
           var corporName = /ex_[a-zA-Z0-9_]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "字段名称只能由字母、数字、下划线组成且只能以ex_开头");
    //固定值校验
	jQuery.validator.addMethod("isDefvalue", function(value, element) { 
           var corporName = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "固定值只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾");
    //建，值
    jQuery.validator.addMethod("isFieldkeyAndvalues", function(value, element) { 
           var corporName = /^(?!.*(^|,)([^,]*),(\2|.*,\2)(,|$))[^,]+(,[^,]+)+$/;   
           return this.optional(element) || (corporName.test(value));     
    }, "此名称只能由字母、数字、下划线、中文、逗号组成，且至少为两个");
});

function checkAndSave() {
	var fieldkeysEdit = $("#fieldkeysEdit").val();
	var fieldvaluesEdit = $("#fieldvaluesEdit").val();
	var fieldkeysNew = $("#fieldkeysNew").val();
	var fieldvaluesNew = $("#fieldvaluesNew").val();
	var type = $("#fieldsType").val();
	//alert("type" + type + "--" + fieldkeysEdit + "-" + fieldvaluesEdit + "-" + fieldkeysNew + "-" + fieldvaluesNew);
	if(type==2) {
		if((fieldkeysEdit != "" && fieldkeysEdit != undefined) && (fieldvaluesEdit != "" && fieldvaluesEdit != undefined)) {
			//alert("编辑" + fieldkeysEdit.split(",").length + "==" + fieldvaluesEdit.split(",").length);
			if(fieldkeysEdit.split(",").length == fieldvaluesEdit.split(",").length) {
				$("#editForm").submit();
			} else {
				//alert("编辑失败");
				alert("Value串和Key串个数请保持一致!");
				return false;
			}
		} else if ((fieldkeysNew != "" && fieldkeysNew != undefined) && (fieldvaluesNew != "" && fieldvaluesNew != undefined)) {
			//alert("新增" + fieldkeysNew.split(",").length + "==" + fieldvaluesNew.split(",").length);
			if(fieldkeysNew.split(",").length == fieldvaluesNew.split(",").length) {
				editForm.submit();
			} else {
				//alert("新增失败");
				alert("Value串和Key串个数请保持一致!");
				return false;
			}
		} else if((fieldkeysEdit != "" || fieldkeysEdit != undefined) && (fieldvaluesEdit == "" || fieldvaluesEdit == undefined)) {
			alert("请正确填写Value串和Key串!");
			return false;
		} else if((fieldkeysEdit == "" || fieldkeysEdit == undefined) && (fieldvaluesEdit != "" || fieldvaluesEdit != undefined)) {
			alert("请正确填写Value串和Key串!");
			return false;
		} else if((fieldkeysNew != "" || fieldkeysNew != undefined) && (fieldvaluesNew == "" || fieldvaluesNew == undefined)) {
			alert("请正确填写Value串和Key串!");
			return false;
		} else if((fieldkeysNew == "" || fieldkeysNew == undefined) && (fieldvaluesNew != "" || fieldvaluesNew != undefined)) {
			alert("请正确填写Value串和Key串!");
			return false;
		}
	} else {
		//alert("其他");
		$("#editForm").submit();
	}
}

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
				<a href="${ctx}/backIndex" target="_top">首页</a>
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
					<input type="text"  class="fieldname" id="fieldname" name="fieldname" <c:if test="${jisFields.fieldname != '' && jisFields.fieldname != null}">value="${jisFields.fieldname}" readonly="readonly"</c:if> value="ex_"/>
					<input type="hidden"  class="oldFieldname" id="oldFieldname" name="oldFieldname" value="${jisFields.fieldname}"/>
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th>字段类型：</th>
				<td>
					<select id="fieldsType" id="type" name="type" value="${jisFields.type}">
						<option value="1" <c:if test="${jisFields.type == '1'}">selected</c:if>>字符串</option>
						<option value="2" <c:if test="${jisFields.type == '2'}">selected</c:if>>枚举型</option>
						<option value="3" <c:if test="${jisFields.type == '3'}">selected</c:if>>固定值</option>
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
					<input type="text"  class="defvalue" name="defvalue" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<c:if test="${jisFields.type == '2'}">
				<tr id = "tr_2_1" style="display: table-row;">
					<th><b class="mustbe">*</b>Key串：</th>
					<td>
						<input type="text"  class="fieldkeys" id="fieldkeysEdit" name="fieldkeys" value="${jisFields.fieldkeys}" />
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr id = "tr_2_2" style="display: table-row;">
					<th><b class="mustbe">*</b>Value串：</th>
					<td>
						<input type="text"  class="fieldvalues" id="fieldvaluesEdit" name="fieldvalues" value="${jisFields.fieldvalues}" />
					</td>
					<th></th>
					<td></td>
				</tr>
			</c:if>
			<tr id = "tr_2_1" style="display: none;">
				<th><b class="mustbe">*</b>Key串：</th>
				<td>
					<input type="text"  class="fieldkeys" id="fieldkeysNew" name="fieldkeys" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr id = "tr_2_2" style="display: none;">
				<th><b class="mustbe">*</b>Value串：</th>
				<td>
					<input type="text"  class="fieldvalues" id="fieldvaluesNew" name="fieldvalues" />
				</td>
				<th></th>
				<td></td>
			</tr>
		</table>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="button" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen" onclick="checkAndSave();"/>
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
