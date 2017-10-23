<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<link rel="stylesheet" href="${ctx}/res/skin/default/css/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery-ui.js"></script>

<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
	//拒绝
	function regect() {
		$("#input_four")[0].style.display = 'none';
		$("#input_three")[0].style.display = 'table-row';
		$("#tr_reject")[0].style.display = 'table-row';
		$("#outsideUserType").attr("value", 0);
	};
	//拒绝取消
	function rejectCancel() {
		$("#tr_reject")[0].style.display = 'none';
		$("#input_four")[0].style.display = 'table-row';
		$("#input_three")[0].style.display = 'none';
		$("#outsideUserType").attr("value", 1);
	};
	//提交，关闭
	var api = frameElement.api, W = api.opener;
	function oprOutsideUser1() {//暂时未用
		var outsideUserType = $("#outsideUserType").val();
		if(outsideUserType == 0) {
			var rejectReason2 = $("#rejectReason2").val();
			if(rejectReason2 == null || rejectReason2 == ""){
				$.dialog.alert('请填写拒绝原因',function(){
					return null;
				});
				return false;
			} else {
				$.ajax({
					cache:true,
					type:'get',
					url:'outsideuserAuth',
					data:$('#editForm').serialize(),
					async:false,
					success:function() {
						api.reload(api.parent,"");
						api.close();
					}
				});
			}
		}
		if(outsideUserType == 1) {
			$.ajax({
				cache:true,
				type:'get',
				url:'outsideuserAuth',
				data:$('#editForm').serialize(),
				async:false,
				success:function() {
					api.reload(api.parent,"");
					api.close();
				}
			});
		}
	};
	
	function oprOutsideUser() {
		$.ajax({
			cache:true,
			type:'get',
			url:'outsideuserAuth',
			data:$('#editForm').serialize(),
			async:false,
			success:function() {
				api.reload(api.parent,"");
				api.close();
			}
		});
	};
</script>
</head>
<body>
<!-- 提示信息开始 -->
<div class="form-alert;" >
   <tags:message msgMap="${msgMap}"></tags:message>
</div> 
<div class="form-warper">
	<!--表单的标题区域--> 
    <form align = "center" id="editForm" method="get" action="${ctx}/complat/outsideuserAuth">
    <div style="display:none;">
    	<input id="outsideUserIid" name="iid" value="${complatOutsideuser.iid}" type="text" style="display:none;"/>
	    <input id="outsideUserType" name="outsideUserType" value="1" type="text" style="display:none;"/>
    </div>
    <!--表单的主内容区域-->
    	<table class="form-table">
			<c:if test="${complatOutsideuser.rejectReason != '' && complatOutsideuser.rejectReason != null || complatOutsideuser.authState == 2}">
				<tr>
					<th>拒绝原因：</th>
					<td style="width: 100%;">
						<textarea readonly="readonly" rows="5" cols="5" id="rejectReason1" name="rejectReason1">${complatOutsideuser.rejectReason}</textarea>
		            </td>
				</tr>
			</c:if>
			<tr>
				<th>姓名：</th>
				<td style="width: 100%;">
					<input readonly="readonly" type="text" id="name" class="name" name="name" value="${complatOutsideuser.name}"/>
				</td>
			</tr>
			<tr>
				<th>身份证号：</th>
				<td style="width: 100%;">
					<input readonly="readonly" type="text" id="papersNumber" class="papersNumber" name="papersNumber" value="${complatOutsideuser.papersNumber}"/>
				</td>
			</tr>
			<tr style="display:none;" id="tr_reject">
				<th></th>
				<td style="width: 100%;">
				<textarea placeholder="请填写拒绝原因" rows="5" cols="5" class="rejectReason" id="rejectReason2" name="rejectReason2"></textarea>
				</td>
			</tr>
		</table>
    </form>
    <!--表单的按钮组区域-->
    <div class="form-btn" id="input_three" style="display:none;">
    	<input type="button" tabindex="15" value="保存" onclick="oprOutsideUser();" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="rejectCancel();" class="btn gray"/>
    </div>
    <div class="form-btn"  id="input_four" >
    	<input type="button" tabindex="15" value="拒绝" onclick="regect();" class="btn bluegreen"/> 
    	&nbsp;&nbsp;
    	<input type="button" tabindex="15" value="通过" onclick="oprOutsideUser();" class="btn bluegreen"/>
    </div>
</div>
</body>
</html>
