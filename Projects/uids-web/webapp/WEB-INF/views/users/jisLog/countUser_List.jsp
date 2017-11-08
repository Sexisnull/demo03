<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
<script type="text/javascript"> 

</script>

</head>
<body>

<div class="list-warper">
	<!--列表的面包屑区域-->
	<div class="position">
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
				在线用户
			</li>
    	</ol>
    </div>
    
    <!--列表内容区域-->
		<div class="list">
			 <div class="list-topBar">
	        	 <div class="list-toolbar">
	            <!-- 操作按钮开始 -->	 
	             <ul class="list-Topbtn">
	            	<li class="add"><a onclick="javascript:window.location.href='${ctx}/jisLog/countUser'">刷新</a></li>
	             </ul>
	             <!-- 操作按钮结束 -->
	           </div> 
	        <!-- 提示信息开始 -->
	         <div class="form-alert;" >
	         	<tags:message msgMap="${msgMap}"></tags:message>
	    	</div>
	    	<!-- 提示信息结束 -->
	    	<!-- 列表开始 -->
	        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
	        	<thead>
	            	<tr>
	                    <th width="25%" style="text-align: center;">登录名</th>
	                    <th width="25%" style="text-align: center;">所属机构</th>
	                    <th width="25%" style="text-align: center;">访问IP</th>
	                    <th width="25%" style="text-align: center;">访问时间</th>
	                </tr>
	            </thead> 
	            <tbody>
	                <c:forEach items="${pageInfo.content}" var="countUser" varStatus="state">
						<tr class="myclass">
		                	<td style="text-align: center;">
		                    	${countUser.userName}
		                    </td>
		                	<td style="text-align: center;">
		                		${countUser.groupName}
		                    </td>
		                    <td style="text-align: center;">
		                    	${countUser.IP}
		                    </td>
		                	<td class="alignL" style="text-align: center;">
		                    	${countUser.operateTime}
		                    </td>
		                </tr>
					</c:forEach>
	            </tbody>       
	        </table>
	        <!-- 列表结束 -->
	    </div>
	    	<!-- 分页 -->
	   		<tags:pagination page="${pageInfo}" paginationSize="5"/> 
		</div>
    
	

</body>
</html>
