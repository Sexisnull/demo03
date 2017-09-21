<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">

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
				<a >同步管理</a>
			</li>
			<li class="split"></li>
			<li>
				<a ><c:if test="${detailMap.syncType == 'sysview'}">实时</c:if><c:if test="${detailMap.syncType == 'current'}">当前</c:if><c:if test="${detailMap.syncType == 'history'}">历史</c:if>同步列表</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position">同步详情</a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
	<!-- <div class="form-title">同步详细信息</div> -->
    
    <!--表单的主内容区域-->
    <div class="form-content">
        <table class="form-table" style="border-bottom:1px solid #c6e6ff">
    		<tr>
    		     <th rowspan="3" style="border-right:1px solid #c6e6ff;" width="3%" align="left">同步基本信息</th>
	        	 <th> 操作对象名称：</th>
	        	 <td>
					<input type="text" id="objectname" value="${jisSysview.objectname}" readonly="readonly"/>
				</td>
				<th>机构编码：</th>
				<td>
					<input type="text" id="codeid" value="${jisSysview.codeid}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th> 操作类型：</th>
                <td>
                	<input type="text" id="operatetype" value="${jisSysview.operatetype}" readonly="readonly"/>
                </td>
				<th> 应用名称：</th>
				
				<td>
					<input type="text" id="appid" value="${applicationMap[jisSysview.appid]}" readonly="readonly"/>
				</td>
			</tr>
			<tr style="border-bottom:1px solid #c6e6ff">
				<th> 创建时间：</th>
				<td>
					<input type="text" class="input" id="synctime" value="${jisSysview.synctime}" readonly="readonly"/>
	            </td>
	        	<th>同步结果：</th>
	        	<td>
	        		<input type="text" class="input" id="optresult" value="${paraMap[jisSysview.optresult]}" readonly="readonly"/>
	            	<i class="form-icon-clear"></i>
	        	</td>
			</tr>
    		<tr style="border-bottom:1px solid #c6e6ff">
    		     <th rowspan="1" style="border-right:1px solid #c6e6ff;" width="3%">请求报文</th>
	        	 <td colspan="4" align="center">
					<textarea rows="5" cols="50" readonly="readonly" style="width: 87%;">${jisSysviewDetail.sendmsg}</textarea>
				</td>
			</tr>
    		<tr>
    		     <th rowspan="1" style="border-right:1px solid #c6e6ff;" width="3%">响应报文</th>
				<td colspan="4" align="center">
					<textarea rows="5" cols="50" readonly="readonly" style="width: 87%;">${jisSysviewDetail.sendmsg}</textarea>
				</td>
			</tr>
		</table>
			
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/${detailMap.returnUrl}?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
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
