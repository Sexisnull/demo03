<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<head>
<meta charset="utf-8" />
<title>同步历史数据</title>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<script type="text/javascript">
	
$(function(){	
    //高级搜索按钮点击事件
		$('#advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$("#advanced-search-btn").click(function(){
			$("#form2").submit();
		});
		//阻止按键盘Enter键提交表单
		var $inp = $('input');
		$inp.keypress(function (e) { 
		    var key = e.which; 
		    if (key == 13) {
		        return false;
		    }
		});	
    /**应用下拉框初始化*/
    var appSearch = $("#appSearch");
    $.get("${ctx}/uids/getApplications", {}, function (data) {
        if (data != null) {
            for(var i=0;i<data.length;i++){
                 appSearch.append("<option value='"+data[i].iid+"'>"+data[i].name+"</option>");
            }
		}
    })
    var oldAppSearch = $("#oldAppSearch").val();
	if(oldAppSearch!=null && oldAppSearch!=""){
	    selectItemByValue(document.getElementById("appSearch"),oldAppSearch);
	}
	
    /**操作类型下拉列表初始化*/
	var operatetypes = ["新增机构","修改机构","删除机构","新增用户","修改用户","删除用户","启用用户","停用用户"];
	var operatetype = $("#operatetypeSearch");
	for(var i=0;i<operatetypes.length;i++){
		operatetype.append("<option value='"+operatetypes[i]+"'>"+operatetypes[i]+"</option>");
	}
	var oldOperatetype = $("#oldOperatetypeSearch").val();
    if(oldOperatetype!=null && oldOperatetype !=""){
        selectItemByValue(document.getElementById("operatetypeSearch"),oldOperatetype);
    }
	
	/**同步结果下拉列表初始化,数据库读值，此处暂时写固定值*/
	//var optresults = ["未同步","同步成功","同步失败","网络不通"];
	var optresult = $("#optresultSearch");
	
	$.get("${ctx}/uids/getOptresult", {}, function (data) {
        if (data != null) {
              for(var i=0;i<data.length;i++){
                optresult.append("<option value='"+data[i].PARA_CODE+"'>"+data[i].PARA_NAME+"</option>");
              }
         }
    })
	var oldOptresult = $("#oldOptresultSearch").val();
	if(oldOptresult!=null && oldOptresult!=""){
	    selectItemByValue(document.getElementById("optresultSearch"),oldOptresult);
	}
	
});

/**根据值选中下拉列表*/
    function selectItemByValue(objSelect,objItemText) { 
        for(var i=1;i<objSelect.options.length+1;i++) {  
            if(objSelect.options[i].value == objItemText) {  
                objSelect.options[i].selected = true;  
                break;  
            }  
        }  
    } 

/**搜索表单校验**/
	function checkSubmitForm() {
		var objectnameSearch = $("#objectnameSearch").val();
		if (objectnameSearch == '' || isChinaOrNumbOrLett(objectnameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#objectNameSearch"), '只能包括中英文、数字、@和下划线');
		}
	}
	/**删除单条信息**/
    function delSingle(obj){
		var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
		var objectId=$(".objectId").val();
		$.dialog.confirm('您确认要删除吗？',function(){
		    window.location.href="${ctx}/uids/jisCurDelete?objectId="+singleId+"&objectId="+objectId;
		});
	}
    /**删除多条**/
    function delSome(){
	var iid=$(".objectId").val();
	if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
		$.dialog.confirm('您确认要删除吗？',function(){
			var ids = "";
			$('.list-table tbody input[type=checkbox]').each(function(i, o) {
				if($(o).attr('checked')) {
					ids += $(o).val() + ",";
				}
			});
			window.location.href="${ctx}/uids/jisCurDelete?objectId="+ids.substring(0,ids.length-1);
		});
		
	}else{
	$.dialog.confirm('请您至少选择一条数据',function(){
	    return null;
	    });
	}
	
	function sync(){
	}
}
</script>
<style type="text/css">
.select{
font-size: 12px;
border: 1px solid #dddddd;
padding: 3px 8px;
height: 30px;
width: 200px;
}
.advanced-content input[type="text"]{
width: 182px !important;
}
</style>
</head>
	<body>

		<div class="list-warper">
			<!--列表的面包屑区域-->
			<div class="position">
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
			</div>

			<div class="search-content">
				<form id="form1" name="pageForm" action="${ctx}/uids/jisCurList" method="get">
						<table class="advanced-content">
							<tr>
								<th style="padding-left: 600px">操作对象名称：</th>
								<td>
									<input type="text" id="objectnameSearch" name="search_LIKE_objectname" placeholder="操作对象名称" value="${sParams['LIKE_objectname']}" class="input" />
								</td>
								<td class="btn-group">
									<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
								</td>
								<td class="btn-group"> <a id="advanced-btn" class="btnSearch" >高级搜索</a></td>
							</tr>
						</table>
				</form>
				<form id="form2" name="form2" action="${ctx}/uids/jisCurList" >
				        <table class="advanced-content" style="display: none;">
							<tr>
				                <th>所属应用：</th>
								<td><input id="oldAppSearch" type="hidden" value="${sParams['EQ_appid']}">
									<select id="appSearch" name="search_EQ_appid" class="select">
										<option value="">--请选择--</option>
									</select>
								</td>

								<th>操作类型：</th>
								<td>
									<input id="oldOperatetypeSearch" type="hidden" value="${sParams['EQ_operatetype']}">
									<select id="operatetypeSearch" name="search_EQ_operatetype" class="select">
										<option value="">--请选择--</option>
									</select>
								</td>

								<th>同步结果：</th>
								<td>
									<input id="oldOptresultSearch" type="hidden" value="${sParams['EQ_optresult']}">
									<select id="optresultSearch" name="search_EQ_optresult" class="select">
										<option value="">--请选择--</option>
									</select>
								</td>
							</tr>
							<tr height="10px"></tr>
							<tr>
							    <th>操作时间：</th>
								<td>
									<input type="text" id="synctimeSearch" name="search_LIKE_synctime" placeholder="时间" value="${sParams['LIKE_synctime']}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
									<!-- search_LIKE_ -->
								</td>
							    <th>操作对象名称：</th>
								<td>
									<input type="text" id="objectnameSearch" name="search_LIKE_objectname" placeholder="操作对象名称" value="${sParams['LIKE_objectname']}" class="input" />
								</td>
								<th></th>
								<td >
								<!-- style="text-align:right;padding-right: 6%" -->
								    <a class="btnSearch" id="advanced-search-btn">搜索</a>
								</td>
							</tr>
						</table>
				</form>
			</div>
			
			<!-- 搜索内容结束 -->
			<!-- 操作按钮开始 -->
			<div class="list-toolbar">
				<gsww:opTag menuId="297e40e05e5f7a4f015e5f93f7b20002" tabIndex="2"
					operatorType="1"></gsww:opTag>
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
					<th width="15%">
						操作对象名称
					</th>
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

					<th width="10%" style="text-align: center">
						同步结果
					</th>
					<th width="12%" style="text-align: center">
						操作
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageInfo.content}" var="jisCurrent">
					<tr>
						<td>
							<div class="label">
								<i class="check_btn"></i>
								<input id="${jisCurrent.iid}" value="${jisCurrent.iid}"
									type="checkbox" class="check_btn" style="display: none;" />
							</div>
						</td>
						<td>
							${jisCurrent.objectname}
						</td>
						<td style="word-break: break-all; word-wrap: break-word;">
							${jisCurrent.codeid}
						</td>
						<td class="alignL" style="text-align: center">
							<div class="list-longtext">
								${jisCurrent.operatetype}
							</div>
						</td>
						<td class="alignL" style="text-align: center">
							<div class="list-longtext">
								${applicationMap[jisCurrent.appid]}
							</div>
						</td>
						<td class="alignL" style="text-align: center">
							<div class="list-longtext">
								${jisCurrent.synctime}
							</div>
						</td>
						</td>
						<td class="alignL" style="text-align: center">
							<div class="list-longtext">
								${paraMap[jisCurrent.optresult]}
							</div>
						</td>

						<td class="position-content">
							<gsww:opTag menuId="297e40e05e5f7a4f015e5f93f7b20002"
								tabIndex="1" operatorType="2"></gsww:opTag>
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
		