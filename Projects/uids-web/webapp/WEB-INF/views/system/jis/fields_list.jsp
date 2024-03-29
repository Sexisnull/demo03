<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript"
	src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>

<head>
<meta charset="utf-8" />
<title>甘肃万维JUP课题</title>

<script type="text/javascript">
	function checkSubmitForm() {
		var shownameSearch = $("#shownameSearch").val();
		if (shownameSearch == '' || isNumbOrLett(shownameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#shownameSearch"), '只能包括字母、数字、下划线、中文,且不能超过50个字符');
		}
	}

	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett(s) { //判断是否是字母、数字组成
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,50}$/;
		//var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		} else {
			return false;
		}
	}
    
    //设置弹框
    function openwindow(){
		var api = $.dialog({
			title : '设置-修改信息必填',
			width : 420,
			height: 290,
			max : false,
			min : false,
			lock : true,
			padding : '40px 20px',
			content : 'url:${ctx}/jis/goFieldsSetting',
			fixed : false,
			drag : false,
			resize : false,
			fixed : false,
			drag : false,
			resize : false
		});
	}
</script>

</head>
<body>

	<div class="list-warper">
		<!--列表的面包屑区域-->
		<div class="position">
			<ol class="breadcrumb">
				<li>首页</li>
				<li class="split"></li>
				<li class="active">个性化设置</li>
				<li class="split"></li>
				<li class="active">用户扩展属性</li>
			</ol>
		</div>

		<div class="search-content">
			<form id="form1" name="pageForm" action="${ctx}/jis/fieldsList"
				method="get">
				<table class="advanced-content">
					<tr>
						<th style="padding-left: 300px">显示名称：</th>
						<td width="20%"><input type="text" style="width: 170px;"
							placeholder="显示名称" value="${sParams['LIKE_showname']}"
							id="shownameSearch" name="search_LIKE_showname" /></td>
						<td class="btn-group"><a class="btnSearch"
							onclick="javascript:checkSubmitForm()">搜索</a></td>
					</tr>
				</table>
			</form>
		</div>
		<!--列表内容区域-->
		<div class="list">
			<input type="hidden" id="orderField" name="orderField"
				value="${orderField}" /> <input type="hidden" id="orderSort"
				name="orderSort" value="${orderSort}" />

			<div class="list-topBar  advanced-search">
				<div class="list-toolbar">
					<!-- 操作按钮开始 -->
					<%-- <gsww:opTag menuId="8a929c4a5e7508c1015e75512fa40066" tabIndex="1" operatorType="1"></gsww:opTag> --%>
					<ul class="list-Topbtn">
						<li class="add"><a title="新增"
							onclick="add('jis/fieldsEdit');">新增</a></li>
						<li class="del"><a title="删除"
							onclick="deleteData('jis/fieldsDelete','fieldsId');">删除</a></li>
						<li class="enable"><a title="设置"
							onclick="openwindow();">设置</a></li>
					</ul>
					<!-- 操作按钮结束 -->
				</div>
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
						<th width="10px">
							<div class="label">
								<i class="check_btn check_all"></i> <input type="checkbox"
									class="check_btn" style="display: none;" />
							</div>
						</th>
						<th width="25%" style="text-align: center;">显示名称</th>
						<th width="25%" style="text-align: center;">字段名称</th>
						<th width="20%" style="text-align: center;">字段类型</th>
						<th width="15%" style="text-align: center;">必填</th>
						<th width="15%" style="text-align: center;">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageInfo.content}" var="jisFields"
						varStatus="state">
						<tr class="myclass">
							<td>
								<div class="label">
									<i class="check_btn"></i><input id="${jisFields.iid}"
										value="${jisFields.iid}" type="checkbox" class="check_btn"
										style="display:none;" />
								</div>
							</td>
							<td align="center" title="${jisFields.showname}" class="box_main_td" nowrap="nowrap">
								<c:if test="${fn:length(jisFields.showname)>20}">
								  ${fn:substring(jisFields.showname,0,20)}...
								</c:if>
								<c:if test="${fn:length(jisFields.showname)<=20}">
								   ${jisFields.showname}&nbsp;
								 </c:if> 
							</td>
							<td align="center" title="${jisFields.fieldname}" class="box_main_td" nowrap="nowrap">
								<c:if test="${fn:length(jisFields.fieldname)>20}">
								  ${fn:substring(jisFields.fieldname,0,20)}...
								</c:if>
								<c:if test="${fn:length(jisFields.fieldname)<=20}">
								   ${jisFields.fieldname}&nbsp;
								 </c:if> 
							</td>
							<td style="text-align: center;">
								<div class="alignL">
									<div class="list-longtext">
										<c:if test="${jisFields.type == '1'}">字符串</c:if>
										<c:if test="${jisFields.type == '2'}">枚举型</c:if>
										<c:if test="${jisFields.type == '3'}">固定值</c:if>
									</div>
								</div>
							</td>
							<td style="text-align: center;">
								<div class="alignL">
									<div class="list-longtext">
										<c:if test="${jisFields.iswrite == '0'}">否</c:if>
										<c:if test="${jisFields.iswrite == '1'}">是</c:if>
									</div>
								</div>
							</td>
							<td class="position-content" style="text-align: center;"><gsww:opTag
									menuId="8a929c4a5e7508c1015e75512fa40066" tabIndex="1"
									operatorType="2"></gsww:opTag></td>
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
