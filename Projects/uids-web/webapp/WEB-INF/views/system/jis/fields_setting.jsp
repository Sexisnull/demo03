<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
    //全选/反选
    function chooseall(){
		var check = $('#checkall:checked').val();
		if(check == 1){
			$('[name=fieldiid]').attr('checked','checked');
		}else{
			$('[name=fieldiid]').removeAttr('checked');
		}
	}
	//获取多个选中框的值
	$('input:checkbox[name="fieldiid"]').each(function() { 
	    if ($(this).attr('checked') ==true) { 
	        alert($(this).val()); 
	    } 
	});
	//提交设置
	var api = frameElement.api, W = api.opener;
	function oprFields() {
		$.ajax({
			cache:true,
			type:'get',
			url:'fieldsOperate',
			data:$('#editForm').serialize(),
			async:false,
			success:function() {
				api.reload(api.parent,"");
				api.close();
			}
		});
	};
</script>
<style type="text/css">
.rightspan {
    width: 80px;
    height: 30px;
    float: left;
    padding: 5px 20px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
</head>
<body>
<div class="form-warper">
	<!--表单的标题区域--> 
    <form id="editForm" method="get" action="${ctx}/jis/fieldsOperate">
	    <!--表单的主内容区域-->
	    <div style="padding:10px;margin-left:10px;">
	    	<table class="form-table">
				<c:forEach items="${jisFieldsList}" var="field" varStatus="i">
					<span title="${field.showname }" class="rightspan">
						<input type="checkbox" name="fieldiid" class="" value="${field.iid }" <c:if test="${field.iswrite == 1 }">checked="checked"</c:if>/>
						${field.showname}
					</span>
				</c:forEach>
			</table>
	    </div>
	    <div style="clear:both;"></div>
	    <div style="color: red;clear: both;padding:10px 10px 10px 40px;">
			<input type="checkbox" id="checkall" value="1" onclick="chooseall();"/>全选/反选
		</div>
    </form>
    <!--表单的按钮组区域-->
    <div class="form-btn" id="input_three">
    	<input type="button" onclick="oprFields();"  tabindex="15" value="保存" class="btn bluegreen"/>
    </div>
</div>
</body>
</html>
