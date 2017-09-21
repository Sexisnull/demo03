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
		var loginNameSearch = $("#loginNameSearch").val();
		if (loginNameSearch == '' || isNumbOrLett(loginNameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#loginNameSearch"), '只能包括数字和字母');
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
		//var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
		var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		} else {
			return false;
		}
	}
	
	function openwindow1(){ 
	    window.showModalDialog("${ctx}/jis/fieldsOperate",window,
	    "status:no;scroll:yes; dialogWidth:400px;dialogHeight:500px");
    } 
    
    //弹出层
	function openwindow(){
	    var mybg = document.createElement("div"); 
		mybg.setAttribute("class","mybg"); 
		$(".mybg").addClass("mybg");
	    document.body.appendChild(mybg);
		document.body.style.overflow = "hidden"; 
		$("#alerttb").show(); 				
	}
	//关闭
	$(".close").click(function() {
        $("#alerttb").hide();
    });
    //全选/反选
    function chooseall(){
		var check = $('#checkall:checked').val();
		if(check == 1){
			$('[name=fieldiid]').attr('checked','checked');
		}else{
			$('[name=fieldiid]').removeAttr('checked');
		}
	}
	//获取多个选中框的值
	$('input:checkbox[name="fieldiid"]').each(function() { 
	    if ($(this).attr('checked') ==true) { 
	        alert($(this).val()); 
	    } 
	}); 
</script>
<style type="text/css">
/*设置弹出层样式*/
.alert_tb {	
	left:180px;
	top:120px;
	border:1px solid #F68A8A;
	width:600px;
	height:135px;
	background-color:#e2ecf5;
	z-index:1000;
	position:absolute;
} 
.mybg{
	background-color:#000;
	width:100%;
	height:100%;
	position:absolute;
	top:0; 
	left:0; 
	zIndex:500; 
	opacity:0.3; 
	filter:Alpha(opacity=30); 
}
.input_one i{
    float: right;
    font-style: normal;
    cursor: pointer;
}
.input_one {
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
.input_three {
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
}
</style>
</head>
<body>

	<div class="list-warper">
		<!--列表的面包屑区域-->
		<div class="position">
			<ol class="breadcrumb">
				<li><a href="${ctx}/backIndex" target="_top">首页</a></li>
				<li class="split"></li>
				<li><a>个性化设置</a></li>
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
							placeholder="请输入显示名称" value="${sParams['LIKE_showname']}"
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
						<th width="20%" style="text-align: center;">显示名称</th>
						<th width="15%" style="text-align: center;">字段名称</th>
						<th width="35%" style="text-align: center;">字段类型</th>
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
							<td style="text-align: center;">
								<div title="${jisFields.showname}" class="word_break">${jisFields.showname}</div>
							</td>
							<td style="text-align: center;">
								<div title="${jisFields.fieldname}" class="word_break">${jisFields.fieldname}</div>
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
	<!-- 弹出层 -->
    <div id="alerttb" class="alert_tb" style="display:none;"> 
      <div class="box">
      		
      </div>
      <div class="input_one">
		<span id="inputUser">用户扩展属性 - 设置 - 修改必填信息</span>
		<i class="close">X</i>
      </div>   
      <div class="input_two">
	     <form id="oprform" name="oprform" action="${ctx}/jis/fieldsOperate" method="get">
	    	<!--隐藏变量区-->
			<div id="dialog-content">
	        	<!--表单主体-->
	            <div style="padding:10px;margin-left:10px;">
					<c:forEach items="${jisFieldsList}" var="field" varStatus="i">
						<span class="rightspan" title="${field.showname }">
							<input type="checkbox" name="fieldiid" class="" value="${field.iid }" <c:if test="${field.iswrite == 1 }">checked="checked"</c:if>/>
							${field.showname}
						</span>
					</c:forEach>
				</div>
				<div style="color: red;clear: both;padding:10px 10px 10px 40px;">
					<input type="checkbox" id="checkall" value="1" onclick="chooseall();"/>全选/反选
				</div>
			</div>
	        <!--表单按钮区-->
			<div class="input_three"> 
				<p align = "center">
					<input type="submit" class="btn btn-primary" value="保存" /> 
					<input type="button" class="btn" value="取消" onclick="javascript:window.location.href='${ctx}/jis/fieldsList'" />
				</p>
			</div>
		</form>
      </div> 
    </div>
</body>
</html>
