<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<script type="text/javascript">
		/**搜索表单校验**/
	function checkSubmitForm() {
		var loginnameSearch = $("#loginnameSearch").val();
		if (loginnameSearch == '' || checkLoginName(loginnameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#loginnameSearch"), '只能包括字母、数字、下划线和.');
		}
	}
	
	function checkLoginName(s){
		var regu = /^[0-9a-zA-Z_\.]+$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	
	function deleteDate() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要删除吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/uids/complatbanlistDelete?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
		
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
					封停管理
				</li>
			</ol>
			<!--列表内容区域-->
			<div class="list">
				<!--<div class="list-title">角色列表</div>-->
				<div class="list-topBar">
					<!-- 搜索内容开始 -->
					<div class="search-content">
						<form id="form1" name="pageForm" action="${ctx}/uids/complatBanList"
							method="get">
							<div>
							<table class="">
								<tr>
									
									<th>
										登录全名：
									</th>
									<td width="20%">
									<input type="text" placeholder="登录全名" id="loginnameSearch" name="search_LIKE_loginname"  value="${sParams['LIKE_loginname']}" class="input"/>
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
					<div class="list-toolbar">
						<gsww:opTag menuId="8a929c3f5e567ae2015e5682990f0004" tabIndex="1" operatorType="1"></gsww:opTag>
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
							<th width="2">
								<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="35%">
						     	登录全名
							</th >
							<th width="25%" class="alignL" style="text-align: center">
								最后登录时间
							</th>
							<th width="25%" class="alignL" style="text-align: center">
								ip地址
							</th>
							<th width="15%" style="text-align: center">
								错误次数
							</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.content}" var="complatBanlist">
							<tr>
								<td>
									<div class="label">
										<i class="check_btn"></i>
										<input id="${complatBanlist.iid}" value="${complatBanlist.iid}"
											type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</td>
								<td>
									${complatBanlist.loginname}
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
									<fmt:formatDate value="${complatBanlist.logindate}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${complatBanlist.ipaddr}
									</div>
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${complatBanlist.logintimes}
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
		