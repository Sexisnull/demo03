<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<script type="text/javascript">
	/**授权弹出窗口**/
	//系统授权按钮
	function btnAuthorize(obj) {
		//使用dialog弹出窗口显示资源树
		var roleId = $(obj).parent().parent().parent().parent()
				.find('td:first').find('input').attr('id');
		dialogResTree = $.dialog({
			//Animator: false,
			content : 'url:${ctx}/sys/roleAuthorizeShow?roleId=' + roleId,
			width : 880,
			height : 450,
			title : '资源授权',
			max : false,
			min : false,
			lock : true,
			fixed : true,
			drag : false,
			resize : false
		});
		/*dialogResTree.OKEvent = function() {
			dialogResTree.innerFrame.contentWindow.accreditSubmit();	//调用弹出窗口里的accreditSubmit函数提交子页面
			dialogResTree.close();
		};*/
		dialogResTree.show();
	};
	/**搜索表单校验**/
	function checkSubmitForm() {
		var roleNameSearch = $("#roleNameSearch").val();
		if (roleNameSearch == '' || isChinaOrNumbOrLett(roleNameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#roleNameSearch"), '只能包括中英文、数字、@和下划线');
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
					<a href="${ctx}/index" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a href="#">系统管理</a>
				</li>
				<li class="split"></li>
				<li class="active">
					角色管理
				</li>
			</ol>
			<!--列表内容区域-->
			<div class="list">
				<!--<div class="list-title">角色列表</div>-->
				<div class="list-topBar">
					<!-- 搜索内容开始 -->
					<div class="search-content">
						<form id="form1" name="pageForm" action="${ctx}/sys/roleList"
							method="get">
							<table class="">
								<tr>
									<th>
										角色名称：
									</th>
									<td width="20%">
										<input type="text" id="roleNameSearch" placeholder="角色名称"
											name="search_LIKE_roleName" class="input"
											value="${sParams['LIKE_roleName']}" />
									</td>
									<td class="btn-group">
										<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<!-- 搜索内容结束 -->
					<!-- 操作按钮开始 -->
					<div class="list-toolbar">
						<gsww:opTag menuId="12" tabIndex="1" operatorType="1"></gsww:opTag>
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
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="20%">
								角色名称
							</th>
							<th>
								角色描述
							</th>
							<th width="15%" class="alignL" style="text-align: center">
								操作时间
							</th>
							<th width="6%" style="text-align: center">
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.content}" var="sysRole">
							<tr>
								<td>
									<div class="label">
										<i class="check_btn"></i>
										<input id="${sysRole.roleId}" value="${sysRole.roleId}"
											type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</td>
								<td>
									${sysRole.roleName}
								</td>
								<td style="word-break: break-all; word-wrap: break-word;">
									${sysRole.roleDesc}
								</td>
								<td class="alignL" style="text-align: center">
									<div class="list-longtext">
										${sysRole.createTime}
									</div>
								</td>
								<td class="position-content">
									<gsww:opTag menuId="12" tabIndex="1" operatorType="2"></gsww:opTag>
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
