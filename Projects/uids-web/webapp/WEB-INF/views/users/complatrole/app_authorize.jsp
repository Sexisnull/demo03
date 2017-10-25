<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>

<!doctype html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery-validity.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgdialog.js"></script>
	<script type="text/javascript">
	function choseall(){
		var c=$('#checkall').attr("checked");
		if(c=="checked"){
			$("#appForm :checkbox").attr("checked","checked");
		}else{
			$("#appForm :checkbox").removeAttr("checked");
		}
	}

	function chosechk(){
		var flag = 0;
		$("input[name='apps']").each(function(){
			if(this.checked == false){
				$("#checkall").attr("checked",false);
			}else{
				flag++;
			}
		});
		var len = $("input[name='apps']").length;
		if(flag == len){
			$('#checkall').attr("checked","checked");
		}
	}
	
	$(function() {
		var len = $("input[name='apps']:checked").length;
		var applen = $("input[name='apps']").length;
		if(len == applen){
			$('#checkall').attr("checked","checked");
		}
		$("#appForm").validity(function(){},{
			success:function(result){
				if(result.success){
					
					$.dialog.alertSuccess("设置成功",function(){
						location.href = '${ctx}/complat/croleList';
					});
					
					
				}else{
					$.dialog.alert("设置失败！");
				}
			}
		});
	}); 
	function toSubmit(){
		$("#appForm").submit();
	}
</script>
	
<style>
.rightspan {
	width: 180px;
	height: 30px;
	float: left;
	padding: 5px 20px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

#dialog-content {
	padding-top: 20px;
	overflow: auto;
	position: relative;
}

.ui_buttons {
    white-space: nowrap;
    padding: 4px 0 4px 8px;
    height: 52px;
    line-height: 52px;
    text-align: right;
    background-color: #ffffff;
    border-top: solid 1px #e5e5e5;
}
.ui_buttons input::-moz-focus-inner {
    border: 0;
    padding: 0;
    margin: 0;
}

.ui_buttons input {
    padding: 4px 12px 4px 14px;
    padding: 6px 12px 6px 14px\0;
    *padding: 5px 12px 5px 12px;
    margin-left: 6px;
    cursor: pointer;
    display: inline-block;
    text-align: center;
    line-height: 1;
    height: 28px;
    letter-spacing: 3px;
    overflow: visible;
    color: #4cadf1;
    font-size: 14px;
    border: solid 1px #4cadf1;
    border-radius: 3px;
    border-radius: 0\9;
    background: #ffffff;
}

.ui_buttons input:hover {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
.ui_buttons input:active {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
#myDiv{
	border-top:1px solid #e5e5e5;
	border-left:1px solid #e5e5e5;
	border-right:1px solid #e5e5e5;
}

</style>
	</head>
	<body>
		<div id="myDiv" style="height:300px;overflow:auto">
		<form action="modifyApps" method="post" id="appForm" name="appForm">
		<input type="hidden" name="iid" id="iid" value="${iid}" />
		<div id="dialog-content">
			<div style="padding:10px;margin-left:10px;">
				<c:forEach items="${appList}" var="apps" varStatus="i">
					<span class="rightspan" title="${apps.name }">
						<input type="checkbox" name="apps" class="" value="${apps.iid }" <c:if test="${apps.approleid == 1 }">checked="checked"</c:if> onclick="chosechk()" />${apps.name }
					</span>
				</c:forEach>
			</div>
			<div style="color: red;clear: both;padding:10px 10px 10px 40px;">
				<input type="checkbox" id="checkall" onclick="choseall();"/>全选/反选
			</div>
		</div>
	</form>
	</div>
	<div class="ui_buttons">
		<input type="button" value="确定" onclick="toSubmit();">
		<input type="button" value="关闭" onclick="javascript:window.location.href='${ctx}/complat/croleList'">
	</div>
	</body>
</html>
