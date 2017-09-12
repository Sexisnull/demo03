<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<script type="text/javascript">
		/**搜索表单校验**/
function checkSubmitForm() {
		var objectnameSearch = $("#objectnameSearch").val();
		if (objectnameSearch == '' || isChinaOrNumbOrLett(objectnameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#objectnameSearch"), '只能包括中英文、数字、@和下划线');
		}
	}
		</script>
	</head>
	<body>
		<div class="list-warper">
			<!--列表的面包屑区域-->
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/index" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a href="#">系统管理</a>
				</li>
				<li class="split"></li>
				<li class="active">
					同步列表
				</li>
			</ol>
			<!--列表内容区域-->
			<div class="list">
				<!--<div class="list-title">角色列表</div>-->
				<div class="list-topBar">
					<!-- 搜索内容开始 -->
					<div class="search-content">
						<form id="form1" name="pageForm" action="${ctx}/uids/jisHisList"
							method="get">
							<div>
							<table class="">
								<tr>
									
									<th>
										操作对象名：
									</th>
									<td width="20%">
										<input type="text" id="objectnameSearch" placeholder="操作对象名"
										<input type="text" placeholder="操作对象名称" id="objectnameSearch" name="search_LIKE_objectname"  value="${sParams['LIKE_objectname']}" class="input"/>
									</td>
									<td class="btn-group">
										<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
									</td>
								</tr>
							</table>
							</div>
						</form>
					</div>
					<!-- 搜索内容结束 -->
					<!-- 操作按钮开始 -->
					<!--<div class="list-toolbar">
						<gsww:opTag menuId="12" tabIndex="1" operatorType="1"></gsww:opTag>
					</div>
					--><!-- 操作按钮结束 -->

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
							<th width="2">
								<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="10%">
								操作对象名称
							</th >
							<th width="15%">
								机构编码
							</th>
							<th width="10%" class="alignL" style="text-align: center">
								操作类型
							</th>
							<th width="15%" style="text-align: center">
								应用名称
							</th>
							<th width="20%" style="text-align: center">
								创建时间
							</th>
							
							<th width="6%" style="text-align: center">
								同步结果
							</th>
							<th width="15%" style="text-align: center">
								详细情况
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.content}" var="jisHistory">
							<tr>
								<td>
									<div class="label">
										<i class="check_btn"></i>
										<input id="${jisHistory.iid}" value="${jisHistory.iid}"
											type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</td>
								<td>
									${jisHistory.objectname}
								</td>
								<td style="word-break: break-all; word-wrap: break-word;">
									${jisHistory.codeid}
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisHistory.operatetype}
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisHistory.appid}
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisHistory.synctime}
									</div>
								</td>
								
									
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisHistory.optresult}
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${jisHistory.errorspec}
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
		