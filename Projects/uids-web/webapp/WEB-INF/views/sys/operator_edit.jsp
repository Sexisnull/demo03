<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<%@ include file="/include/meta.jsp"%>

	<head>
		<title></title>
		<script type="text/javascript">
			$(function() {
				//表单校验
				var operatorNameInput = $("#operatorName").val();
				$("#editForm").validate({
					rules : {
						menuId : {
							required : true
						},
						operatorName : {
							required : true,
							cnRangelength : [ 0, 64 ],
							stringCheck : operatorNameInput
						},
						operatorUrl : {
							required : true,
							maxlength : 256
						},
						operatorImage : {
							required : true,
							maxlength : 256
						},
						states : {
							required : true
						},
						operatorTypes : {
							required : true
						},
						tabIndex : {
							maxlength : 4
						},
						operatorLevel : {
							digits : true,
							maxlength : 3
						}
					},
					submitHandler : function(form) {
						var operatorState = $("#operatorState").val();
						var operatorType = $("#operatorType").val();
						if (operatorState == '') {
							$.validator.errorShow($("#operatorState"), '请选择操作状态');
							return false;
						} else if (operatorType == '') {
							$.validator.errorShow($("#operatorType"), '请选择操作类型');
							return false;
						} else {
							form.submit();
						}
					}
				});
		
			});
		</script>
	</head>
	<body>
		<div class="form-warper">
			<!-- 面包屑导航 -->
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/backIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li class="active">
					操作管理
				</li>
				<li class="split"></li>
				<li class="active">
					<c:if
						test="${sysOperator.operatorId==null}">操作新增</c:if>
					<c:if test="${sysOperator.operatorId!=null}">操作编辑</c:if>
				</li>
			</ol>
			
			<!--表单的选项卡切换-->
			<form id="editForm" method="post"
				action="${ctx}/operator/operatorSave">

				<div style="display: none;">
					<input type="hidden" id="id" name="operatorId"
						value="${sysOperator.operatorId}" />
				</div>

				<!--表单的主内容区域-->
				<div class="form-content">
					<table class="form-table single">
						<tr>
							<th>所属菜单：</th>
							<td id="resKey"><select id="menuId" name="menuId" class="input-select">
								<option value="">
									--请选择--
								</option>
								<c:forEach var="sysMenu" items="${sysMenuList}">
									<option value="${sysMenu.menuId}"
										<c:if test="${sysMenu.menuId==sysOperator.sysMenu.menuId}">selected </c:if>>
										${sysMenu.menuName}
									</option>
								</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>操作名称：</th>
							<td><input type="text" class="input" id="operatorName"
								name="operatorName" value="${sysOperator.operatorName}" /></td>
						</tr>
						<tr>
							<th>操作动作：</th>
							<td>
								<input type="text" class="input" name="operatorUrl" value="${sysOperator.operatorUrl}" />
								<p>列表单个操作：如编辑(add('operator/operatorEdit','operatorId',this);)</p>
								<p>列表批量操作：如删除(deleteDate('operator/operatorDelete','operatorId');)</p>
							</td>
						</tr>
						<tr>
							<th>按钮样式：</th>
							<td>
								<input type="text" class="input" name="operatorImage"
									value="${sysOperator.operatorImage}" />
								<div class="list-toolbar" style="width: auto; border: none;">顶部按钮：
									<ul style="display: inline; margin-left: 0;">
										<li class="new" style="margin-left: 0;"><a>新建</a></li>
										<li>new</li>
										<li class="edit"><a>修改</a></li>
										<li>edit</li>
										<li class="del"><a>删除</a></li>
										<li>del</li>
										<li class="query"><a>查询</a></li>
										<li>query</li>
									</ul>
								</div>
								<div class="listOper">
									单行按钮：
									<ul>
										<li class="bluegreen">
											<a>启用</a>
										</li>
										<span>bluegreen</span>
										<li class="yellow">
											<a>停用</a>
										</li>
										<span>yellow</span>
										<li class="blue">
											<a>编辑</a>
										</li>
										<span>blue</span>
										<li class="red">
											<a>删除</a>
										</li>
										<span>red</span>
										<li>
											<a>普通按钮</a>
										</li>
										<span>默认空</span>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th>操作状态：</th>
							<td> 
							  <gsww:checkboxTag name="operatorState" defaultValue="1" type="DATA_STATUS" inputType="radio" value="${sysOperator.operatorState }"></gsww:checkboxTag>
							</td>
						</tr>
						<tr>
							<th>操作类型：</th>
							<td>
							  <gsww:checkboxTag name="operatorType" defaultValue="1" type="OPERATOR_TYPE" inputType="radio" value="${sysOperator.operatorType }"></gsww:checkboxTag>
							</td>
						</tr>
						<tr>
							<th>标签页：</th>
							<td><input type="text" class="input" name="tabIndex"
								value="${sysOperator.tabIndex}" /></td>
						</tr>
						<tr>
							<th>排序：</th>
							<td><input type="text" class="input" name="operatorLevel"
								value="${sysOperator.operatorLevel}" /></td>
						</tr>
					</table>
				</div>
				<!--表单的按钮组区域-->
				<div class="form-btn">
					<input type="submit" value="保存" class="btn bluegreen" />
					&nbsp;&nbsp;
					<input type="button" value="返回"
						onclick="javascript:window.location.href='${ctx}/operator/operatorList?findNowPage=true'"
						class="btn gray" />
				</div>
			</form>
			<!--表单的底部区域-->
			<div class="form-footer"></div>
		</div>
	</body>
</html>
