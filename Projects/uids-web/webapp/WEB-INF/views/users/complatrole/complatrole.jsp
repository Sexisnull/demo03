<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<script type="text/javascript">
			/* function deleteData() {
				var paraTypeId=$(".iid").val();
				if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
						$.dialog.confirm('您确认要删除吗？',function(){
							var ids = "";
							$('.list-table tbody input[type=checkbox]').each(function(i, o) {
								if($(o).attr('checked')) {
									ids += $(o).val() + ",";
								}
							});
							window.location.href="${ctx}/complat/croleDelete?iid="+ids.substring(0,ids.length-1);
						});
						
					}else{
						$.dialog.confirm('请您至少选择一条数据',function(){
							return null;
						});
					} */
			/**搜索表单校验**/
			function checkSubmitForm() {
				var nameSearch = $("#nameSearch").val();
				if (nameSearch == '' || isChinaOrNumbOrLett(nameSearch)) {
					form1.submit();
				} else {
					$.validator.errorShow($("#nameSearch"), '只能包括中英文、数字、@和下划线');
				}
			}
		
		</script>
		
	</head>
	<body>
		<div class="list-warper">
			<!--列表的面包屑区域-->
			<ol class="breadcrumb">
				<li><a href="${ctx}/index" target="_top">首页</a></li>
				<li class="split"></li>
				<li><a href="#">系统管理</a></li>
				<li class="split"></li>
				<li class="active">角色管理</li>
			</ol>
			<!--列表内容区域-->
			<div class="list">
				<!-- <div class="list-title">角色列表</div> -->
				<div class="list-topBar">
					<!-- 搜索内容开始 -->
					<div class="search-content">
						<form id="form1" name="pageForm" action="${ctx}/complat/croleList" method="get">
							<table class="">
								<tr>
									<th>角色名称：</th>
									<td width="20%">
										<input type="text"  placeholder="角色名称" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name"/>
									</td>
									<td class="btn-group">
										<a class="btnSearch" onclick="javascript:checkSubmitForm();">搜索</a>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<!-- 搜索内容结束 -->
					<!-- 操作按钮开始 -->
					<div class="list-toolbar">
						<gsww:opTag menuId="8a9200c05e5f797f015e5f8c0ee10003" tabIndex="1" operatorType="1"></gsww:opTag>
					</div>
					<!-- 操作按钮结束 -->

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
									<input id="${complatRole.iid}" value="${complatRole.iid}" type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="20%">
								角色名称
							</th>
							<th>
								角色描述
							</th>
							<th width="6%" style="text-align: center">
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.content}" var="complatRole">
							<tr>
								<td>
									<div class="label">
										<i class="check_btn"></i>
										<input id="${complatRole.iid}" value="${complatRole.iid}"
											type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</td>
								<td>
									${complatRole.name}
								</td>
								<td style="word-break: break-all; word-wrap: break-word;">
									${complatRole.spec}
								</td>
								<td class="position-content">
									<gsww:opTag menuId="8a9200c05e5f797f015e5f8c0ee10003" tabIndex="1" operatorType="2"></gsww:opTag>
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
