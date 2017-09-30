<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/jquery-ui.css">
		<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
		<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery-ui.js"></script>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	function regect() {
		$("#input_four")[0].style.display = 'none';
		$("#input_three")[0].style.display = 'table-row';
		$("#tr_reject")[0].style.display = 'table-row';
		$("#corporUserType").attr("value", 0);
	}
	//拒绝取消
	function rejectCancel() {
		$("#tr_reject")[0].style.display = 'none';
		$("#input_four")[0].style.display = 'table-row';
		$("#input_three")[0].style.display = 'none';
		$("#corporUserType").attr("value", 1);
	}
	
	//保存认证信息
	var api = frameElement.api, W = api.opener;
	function saveUser(){
		$.ajax({
			type:'POST',
			url:'${ctx}/complat/corporationAuth',
			data : $("#oprform").serialize(),
			dataType: "json",
			async:false,
			success:function(data){
				if(data.ret == 0){
					api.reload(api.parent,"");
					api.close();
				}
			}
		});
	}
	
	$().ready(function(){
		var rejectReason = $("#rejectReason").val();
		if(rejectReason != null && rejectReason != ""){
			$("#tr_reject_done")[0].style.display = 'table-row';
		}
	});
</script>
</head>
<body>
	  <!-- 提示信息开始 -->
   	  <div class="form-alert;" >
         <tags:message msgMap="${msgMap}"></tags:message>
   	  </div> 
      <div class="input_two">
	     <form align = "center" id="oprform" name="oprform">
	    	<!--隐藏变量区-->
			<div id="dialog-content">
	        	<table class="form-table">
	        		<input id="iid" name="iid" value="${complatCorporation.iid}" type="hidden"/>
	        		<input id="corporUserType" name="corporUserType" value="1" type="hidden"/>
	        		<tr style="display:none;" id="tr_reject_done">
						<th>拒绝原因：</th>
						<td style="width: 100%">
			            	<textarea readonly="readonly" rows="8" cols="8" id="rejectReason" name="rejectReason" >
			            		${complatCorporation.rejectReason}
			            	</textarea>
			            </td>
			        </tr>
					<tr>
						<th>机构负责人姓名：</th>
						<td style="width: 100%">
							<input readonly="readonly" type="text" id="name" name="name" value="${complatCorporation.realName}"/>
			            </td>
			        </tr>
			        <tr>
			        	<th>组织机构代码：</th>
						<td style="width: 100%">
							<input readonly="readonly" type="text" id="orgNumber" name="orgNumber" value="${complatCorporation.orgNumber}"/>
						</td>
					</tr>
					<tr>
			        	<th>身份证号码：</th>
						<td style="width: 100%">
							<input readonly="readonly" type="text" id="cardNumber" name="cardNumber" value="${complatCorporation.cardNumber}"/>
						</td>
					</tr>
					<tr style="display:none;" id="tr_reject">
						<th></th>
						<td style="width: 100%">
						<textarea placeholder="请填写拒绝原因" rows="8" cols="8" name="rejectReason2"></textarea>
						</td>
					</tr>
					<tr id="input_three" style="display:none;" align = "center">
						<th></th>
						<td></td>
						<td>
							<p align = "right">
								<input type="submit" class="btn btn-primary" value="保存" onclick="saveUser();"/> 
								<input type="button" class="btn" value="取消" onclick="rejectCancel();" />
							</p>
						</td>
					</tr>
					<tr id="input_four" class="input_four" align = "center">
						<th></th>
						<td style="width: 80%">
						<p align = "right">
							<input type="button" class="btn btn-primary" value="拒绝" onclick="regect();" /> 
							<input type="submit" class="btn" value="通过"/>
						</p>
						</td>
					</tr>
				</table>
			</div>
		</form>
      </div> 
	
</body>
</html>
