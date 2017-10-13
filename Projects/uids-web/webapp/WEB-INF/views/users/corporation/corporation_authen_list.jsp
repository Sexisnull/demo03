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
		/*$("#input_four")[0].style.display = 'none';
		$("#input_three")[0].style.display = 'table-row';
		$("#tr_reject")[0].style.display = 'table-row';
		$("#corporUserType").attr("value", 0);*/
		$("#input_four").hide();
		$("#input_three").show();
		$("#tr_reject").show();
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
</script>
</head>
<body>
	  <!-- 提示信息开始 -->
   	  <div class="form-alert;" >
         <tags:message msgMap="${msgMap}"></tags:message>
   	  </div> 
      <div class="form-warper">
	     <form align = "center" id="oprform" name="oprform">
	    	<!--隐藏变量区-->
	    	<div style="display:none;">
				<input id="iid" name="iid" value="${complatCorporation.iid}" type="hidden"/>
	        	<input id="corporUserType" name="corporUserType" value="1" type="hidden"/>
    		</div>
			<div id="form-content">
	        	<table class="form-table">
	        		<c:if test="${complatCorporation.rejectReason != null && complatCorporation.rejectReason != ''}">
		        		<tr>
							<th>拒绝原因：</th>
							<td style="width: 100%">
				            	<textarea readonly="readonly" rows="8" cols="8" id="rejectReason" name="rejectReason" >
				            		${complatCorporation.rejectReason}
				            	</textarea>
				            </td>
				        </tr>
				     </c:if>
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
						<th>拒绝原因：</th>
						<td style="width: 100%">
						<textarea placeholder="请填写拒绝原因" rows="8" cols="8" name="rejectReason2"></textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<!--表单的按钮组区域-->
	   <div class="form-btn" id="input_three" style="display:none;">
	   	<input type="button" tabindex="15" value="保存" onclick="saveUser();" class="btn bluegreen"/>
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
