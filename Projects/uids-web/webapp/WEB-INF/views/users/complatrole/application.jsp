<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>角色成员列表</title>
		<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/dragRole.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/role.css">
		
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/role.css">
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/jquery-ui.css">
		<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery-ui.js"></script>
			
		
		<script type="text/javascript">
		function choseall(){
			var c=$('#checkallUser').attr("checked");
			if(c=="checked"){
				$(".datagrid-view2 :checkbox").attr("checked","checked");
			}else{
				$(".datagrid-view2 :checkbox").removeAttr("checked");
			}
		}
		function getCheckedIds(){
			var spCodesTemp = "";
		      $('input:checkbox[name=iids]:checked').each(function(i){
		       if(0==i){
		        spCodesTemp = $(this).val();
		       }else{
		        spCodesTemp += (","+$(this).val());
		       }
		      });
		      return spCodesTemp;
		}
		function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			removeMembers(ids);
			break;
		case 'add':
			var tmp_id = 'dialog_id_' + new Date().getTime();
			$("#dialogframe").dialog({
				width : 800,
				height : 500,
				closed : false,
				cache : false,
				parentWindow : window,
				modal:true,
				title:'用户新增'
			});
			break;
		case 'clean':
			if(confirm('您将清空当前角色下的所有成员\n是否继续？')){
				cleanMembers();
			}
			break;
		}
	}

	/**
	 * 删除成员
	 */
	function removeMembers(ids) {
		var groupsArray = new Array();
		var usersArray = new Array();

		var idsArray = ids.split(',');
		$.each(idsArray, function(index, id) {
			if (id.indexOf('g_') != -1) {
				groupsArray.push(id.replace('g_', ''));
			} else {
				usersArray.push(id.replace('u_', ''));
			}
		});
		var userids = usersArray.join();
		var groupids = groupsArray.join();
		var roleId =  ${roleid};
		if(confirm("您确定要删除这" + ids.split(",").length + "条记录吗")){
			$.ajax({
				url : "${ctx}/complat/removeRelation",
				data:{"roleId":roleId,"userIds":userids,"groupIds":groupids},
				dataType : "JSON",
				async : false,
				success : function(data) {
					if(data.result){
						alert("删除成功");
						location.reload();
					}else{
						alert("删除失败");
					}
				}
			});
		}
	}

	/**
	 * 清空成员
	 */
	function cleanMembers() {
		var roleId =  ${roleid};
		$.ajax({
			url : "${ctx}/complat/emptyRelationByRoleId",
			data:{"roleId":roleId},
			dataType : "JSON",
			async : false,
			success : function(data) {
				if(data.result){
					alert("清空成功");
					location.reload();
				}else{
					alert("清空失败");
				}
			}
		});
	}
	
	$(function(){
		$(".btn-advsearch").click(function(){
			$(".grid-advsearch").css({'display':'block'});
			$(".datagrid-toolbar-search-wrap").css({'display':'none'});
		});
		$(".advsearch-cancel").click(function(){
			$(".grid-advsearch").css({'display':'none'});
			$(".datagrid-toolbar-search-wrap").css({'display':'block'});
		});
		
	});
	function toLoadUser(type){
		var roleid = ${roleid};
		var memberType = "";
		var name = "";
		if(type=="advan"){
			memberType=$("#advanceMemeberType").val();
			name=$("#advanceMemeberName").val();
		}else{
			name=$("#grid_simple_search").val();
		}
		
		$.ajax({
			url : "${ctx}/complat/roleUserAjaxShow",
			data:{"roleId":roleid,"memberName":name,"memberType":memberType},
			dataType : "JSON",
			async : false,
			success : function(data) {
				$("#datatable tbody").empty();
				if(data){
					for(var i=0;i<data.length;i++){
						var type = data[i].type;
						var name = "";
						var object = "";
						if(type==2){
							type="机构";
							name = data[i].groupname;
							object = "g_"+data[i].objectid
						}else if(type==1){
							type="用户";
							name = data[i].outname;
							object = "u_"+data[i].objectid
						}else{
							type="用户";
							name = data[i].username;
							object = "u_"+data[i].objectid
						}
						var tr = "<tr id='datagrid-row-r1-2-0' datagrid-row-index='0' class='datagrid-row'>"+
						"<td field='iid' style='width: 5px;'>"+
						"<div class='datagrid-cell-check'>"+
						"<input type='checkbox' value='"+object+"'>"+
						"</div>"+
						"</td>"+
						"<td field='name'>"+
						"<div style='text-align: left; height: auto;' class='datagrid-cell datagrid-cell-c1-name'>"+
							"<div>"+
								"<span class='user'><i class='icon-user-3'></i>"+name+"</span>"+
							"</div>"+
						"</div>"+
						"</td>"+
						"<td field='type'>"+
							"<div style='text-align: center; height: auto;' class='datagrid-cell datagrid-cell-c1-type'>"+
								"<div style='width:350px;'>"+
									"<span>"+type+"</span>"+
							"</div>"+
							"</div>"+
						"</td>"+
						"</tr>";
						$("#datatable tbody").append(tr);
						
					}
				}
			}
		});
	}
</script>
		<style>
.user {
	color: #226699;
}

.group {
	color: #F88E32;
}

.user i,.group i {
	margin-right: 5px;
}

html {
	overflow: inherit;
}

body {
	padding: 20px;
}
.ui_buttons {
    white-space: nowrap;
    padding: 4px 0 4px 8px;
    height: 52px;
    line-height: 52px;
    text-align: right;
    background-color: #ffffff;
    border-top: solid 1px #e5e5e5;
}
.ui_buttons input::-moz-focus-inner {
    border: 0;
    padding: 0;
    margin: 0;
}

.ui_buttons input {
    padding: 4px 12px 4px 14px;
    padding: 6px 12px 6px 14px\0;
    *padding: 5px 12px 5px 12px;
    margin-left: 6px;
    cursor: pointer;
    display: inline-block;
    text-align: center;
    line-height: 1;
    height: 28px;
    letter-spacing: 3px;
    overflow: visible;
    color: #4cadf1;
    font-size: 14px;
    border: solid 1px #4cadf1;
    border-radius: 3px;
    border-radius: 0\9;
    background: #ffffff;
}

.ui_buttons input:hover {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
.ui_buttons input:active {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
</style>
	</head>
	<body>
		<div id="dialogframe" style="display:none;width:100%;height:100%;" class="dialog-wrap">
			<iframe name="dialog_frame"   src="${ctx}/complat/orgselect.do?orgType=${orgType}&roleId=${roleid}" style="width:100%;height:100%;" frameborder="0"> 
			</iframe>
		</div>
	
		<div class="grid-advsearch" style="display: none;">
			<form>
				成员类型
				<select name="memberType" id="advanceMemeberType"  style="width: 100px; margin: 0 30px 0 10px;">
					<option value="0">
						全部
					</option>
					<option value="1">
						机构
					</option>
					<option value="2">
						用户
					</option>
				</select>
				成员名称
				<input name="memberName" type="text" id="advanceMemeberName" class="input-text" value="${memberName}" style="width: 150px; margin: 0 20px 0 10px;" />
				<input type="button" class="btn btn-small btn-primary" value="检索" onclick="toLoadUser('advan');" style="margin-right: 5px;margin-left: 85px;" />
				<input type="button" class="btn btn-small advsearch-cancel" value="返回" />
				<div class="line-dotted" style="border:1px solid grey;"></div>
			</form>
		</div>
		<div class="panel datagrid" style="width: auto;">
			<div class="datagrid-wrap panel-body panel-body-noheader panel-body-noborder" title="" style=" height: 300px;">
				<div id="grid_toolbar" class="datagrid-toolbar">
					<div class="datagrid-toolbar-btn-wrap">
						<a class="datagrid-toolbar-btn" onclick="toolbarAction('add')"><i class="icon-plus-sign"></i>新增</a>
						<a class="datagrid-toolbar-btn" onclick="toolbarAction('remove')"><i class="icon-minus-sign"></i>删除</a>
						<a class="datagrid-toolbar-btn" onclick="toolbarAction('clean')"><i class="icon-trash"></i>清空</a>
					</div>
					<div class="datagrid-toolbar-search-wrap" style="display: block;">
						<div class="datagrid-toolbar-simple-search">
							<form>
								<input type="text" id="grid_simple_search" class="input-text" style="width: 150px;" name="searchText" value="" placeholder="请输入成员名称">
							</form>
							<input type="button" class="btn btn-small btn-primary" style="margin-left: 5px;" value="检索" onclick="toLoadUser();">
						</div>
						<input type="button" class="btn btn-small btn-advsearch" style="margin-left: 5px;" value="高级检索">
					</div>
				</div>
				<div class="datagrid-view" style=" height: 396px;">
					
					<div class="datagrid-view2" style="width: 710px;">
						<div class="datagrid-header" style="width: 710px; height: 35px;">
							<div class="datagrid-header-inner" style="display: block;">
								<table class="datagrid-htable" border="0" cellspacing="0" cellpadding="0" style="height: 36px;">
									<tbody>
										<tr class="datagrid-header-row">
											<td field="iid">
												<div class="datagrid-header-check">
													<input type="checkbox" id="checkallUser" onclick="choseall();">
												</div>
											</td>
											<td field="name">
												<div class="datagrid-cell" style="width: 332px; text-align: left;">
													<span> 成员名称 </span><span class="datagrid-sort-icon">&nbsp;</span>
												</div>
											</td>
											<td field="type" class="">
												<div class="datagrid-cell" style="width: 350px; text-align: center;">
													<span> 成员类型 </span><span class="datagrid-sort-icon">&nbsp;</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class="datagrid-body" style="width: 710px; height: 215px; overflow-x: hidden;">
							<table id="datatable" class="datagrid-btable" cellspacing="0" cellpadding="0" border="0" style="width:710px;table-layout: auto;">
								<tbody>
									
									<c:forEach items="${userList}" var="u">
										<tr  datagrid-row-index="0" class="datagrid-row">
										<td field="iid" style="width: 5px;">
											<div style="" class="datagrid-cell-check ">
													<c:if test="${u.type==1 }">
													<input name="iids" type="checkbox" value="u_${u.objectid }">
													</c:if>
													<c:if test="${u.type==0 }">
													<input name="iids" type="checkbox" value="u_${u.objectid }">
													</c:if>
													<c:if test="${u.type==2 }">
													<input name="iids" type="checkbox" value="g_${u.objectid }">
													</c:if>
											</div>
										</td>
										<td field="name">
											<div style="text-align: left;; height: auto;" class="datagrid-cell datagrid-cell-c1-name">
												<div>
													<span class="user"><i class="icon-user-3"></i>
													<c:if test="${u.type==1 }">
													${u.outname }
													</c:if>
													<c:if test="${u.type==0 }">
													${u.username }
													</c:if>
													<c:if test="${u.type==2 }">
													${u.groupname }
													</c:if>
													</span>
												</div>
											</div>
										</td>
										<td field="type">
											<div style="text-align: center;; height: auto;" class="datagrid-cell datagrid-cell-c1-type">
												<div style="width:350px;">
													<c:if test="${u.type==1 }">
													<span>用户</span>
													</c:if>
													<c:if test="${u.type==2 }">
													<span>机构</span>
													</c:if>
													<c:if test="${u.type==0 }">
													<span>用户</span>
													</c:if>
													
												</div>
											</div>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="datagrid-footer" style="width: 546px;">
							<div class="datagrid-footer-inner" style="display: none;"></div>
						</div>
					</div>
					
					
<style type="text/css">
	.datagrid-cell-c1-name {
		width: 192px
	}

	.datagrid-cell-c1-type {
		width: 192px
	}
</style>
</div>
					

			</div>
		</div>
		<div class="ui_buttons">
			<input type="button" value="关闭" onclick="javascript:window.location.href='${ctx}/complat/croleList'">
		</div>
		
	</body>
	
</html>