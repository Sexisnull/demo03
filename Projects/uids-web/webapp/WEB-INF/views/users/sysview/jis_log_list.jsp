<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript"
			src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<script type="text/javascript">
	/**搜索表单校验**/
	function checkSubmitForm() {
		var spec = $("#spec").val();
		if (spec == '' || isChinaOrNumbOrLett(spec)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#spec"), '只能包括中英文、数字、@和下划线');
		}
	}
	$(function() {
		//阻止按键盘Enter键提交表单
		var $inp = $('input');
		$inp.keypress(function(e) {
			var key = e.which;
			if (key == 13) {
				return false;
			}
		});
	});
</script>
	</head>
	<body>
		<div class="list-warper">
			<!--列表的面包屑区域-->
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/backIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a href="#">系统管理</a>
				</li>
				<li class="split"></li>
				<li class="active">
					日志管理
				</li>
			</ol>
			<!--列表内容区域-->
			<div class="list">
				<!--<div class="list-title">日志列表</div>-->
				<div class="list-topBar">
					<!-- 搜索内容开始 -->
					<div class="search-content">
						<form id="form1" name="pageForm" action="${ctx}/jisLog/logList"
							method="get">
							<table class="">
								<tr>
									<th>
										操作描述：
									</th>
									<td width="20%">
										<input type="text" id="spec" placeholder="操作描述"
											name="search_LIKE_spec" class="input"
											value="${sParams['LIKE_spec']}" />
									</td>
									<td class="btn-group">
										<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<!-- 搜索内容结束 -->

				</div>
				<!-- 提示信息开始 -->
				<div class="form-alert;">
					<tags:message msgMap="${msgMap}"></tags:message>
				</div>
				<!-- 提示信息结束 -->
				<!-- 列表开始 -->
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="list-table">
					<thead>
						<tr>
							<th width="10">
								<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="20%">
								操作描述
							</th>
							<th>
								操作时间
							</th>
							<th width="15%" class="alignL" style="text-align: center">
								操作人
							</th>
							<th width="15%" style="text-align: center">
								模块名称
							</th>
							<th width="15%" style="text-align: center">
								操作类型
							</th>
							<th width="15%" style="text-align: center">
								操作IP
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.content}" var="jisLog">
							<tr>
								<td>
									<!--判断复选框是否可用-->
									<!--<c:choose>
										<c:when test="${jisLog.operateType=='1'}">
											<div class="label">
												<input id="${jisLog.iid}" value="${jisLog.iid}"
													type="checkbox" class="check_btn" disabled="disabled" />
											</div>
										</c:when>
										<c:otherwise>
											<div class="label">
												<i class="check_btn"></i>
												<input id="${jisLog.iid}" value="${jisLog.iid}"
													type="checkbox" class="check_btn" style="display: none;"
													disabled="disabled" />
											</div>
										</c:otherwise>
									</c:choose>-->
									<div class="label">
										<i class="check_btn"></i>
										<input id="${jisLog.iid}" value="${jisLog.iid}"
											type="checkbox" class="check_btn" style="display: none;"
											disabled="disabled" />
									</div>
								</td>
								<td>
									${jisLog.spec}
								</td>
								<td style="word-break: break-all; word-wrap: break-word;">
								<fmt:formatDate value="${jisLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisLog.userId}
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">

										<c:forEach begin="0" end="${fn:length(mkmcList)}" var="idx">
											<c:if test="${mkmcList[idx].PARA_CODE==jisLog.moduleName}">
											${mkmcList[idx].PARA_NAME}
											</c:if>
										</c:forEach>
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										<c:forEach begin="0" end="${fn:length(czlxList)}" var="idx">
											<c:if test="${czlxList[idx].PARA_CODE==jisLog.operateType}">
											${czlxList[idx].PARA_NAME}
											</c:if>
										</c:forEach>
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisLog.ip}
									</div>
								</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<!-- 列表结束 -->
			</div>
			<!-- 分页 -->
			<tags:pagination page="${pageInfo}" paginationSize="5" />
		</div>
	</body>
</html>
