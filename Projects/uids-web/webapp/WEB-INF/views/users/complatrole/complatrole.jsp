<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
		<link rel="stylesheet" href="${ctx}/res/skin/default/css/jquery-ui.css">
		<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
		<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery-ui.js"></script>
		
		<script type="text/javascript">
		
		function btnAuthorize(obj) {
		$(".list-page").css({'position':'absolute'});
		$(".list-page").css({'bottom':'-100px'});
		$("#list-warper").addClass("window-mask");
		$(".list-footer").addClass("window-mask");
		
		//使用dialog弹出窗口显示资源树
		var roleId = $(obj).parent().parent().parent().parent()
				.find('td:first').find('input').attr('id');
		
		var xyqx = "${ctx}/complat/croleAuthorizeShow?roleId=" + roleId;
		var yyqx = "${ctx}/complat/appAuthorizeShow?roleId=" + roleId;
		var yhjg = "${ctx}/complat/roleorganizationShow?roleId=" + roleId;
		
		$("#xtqx").attr("href",xyqx);
		$("#yyqx").attr("href",yyqx);
		$("#yhjg").attr("href",yhjg);
		$("#tabs").css("display","block");
		
		 $( "#tabs" ).tabs({
		      beforeLoad: function( event, ui ) {
		        ui.jqXHR.error(function() {
		          ui.panel.html(
		            "不能加载该标签页。我们会尽快修复这个问题。" 
		            );
		        });
		      }
		   });
	};
	
	function modifyIsDefault(iid, checkbox) {
		var isDefault = checkbox.checked ? 1 : 0; //切换当前角色缺省状态
		$.ajax({
			type : "POST",
			url : "${ctx}/complat/isdefault_modify",
			data : "iid=" + iid + "&isDefault=" + isDefault,
			success : function(result) {
				if (!result.success)
					alert(result.message);
			}
		});
	}
	
	/**搜索表单校验**/
	function checkSubmitForm() {
		var nameSearch = $("#nameSearch").val();
		if (nameSearch == '' || isNumbOrLett1(nameSearch)) {
			form1.submit();
		} else {
			$.validator.errorShow($("#nameSearch"), '只能包括中英文、数字和下划线，且不能超过50个字符');
		}
	}
	function isNumbOrLett1( s ){
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,50}$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	function deleteRole() {
		var paraTypeId=$(".croleId").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要删除吗？',function(){
					var ids = "";
					$('.list-table tbody .check_btn').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/complat/croleDelete?croleId="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
	/* function add(){
	     window.location.href="${ctx}/complat/croleEdit";
	} */
</script>
<style>
.window-mask {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  filter: alpha(opacity=40);
  opacity: 0.40;
  font-size: 1px;
  *zoom: 1;
  overflow: hidden;
  background: #ccc;
}
</style>
	</head>
	<body>
		<div class="list-warper" style="position: absolute;">
			<!--列表的面包屑区域-->
			<ol class="breadcrumb">
				<li>
					<a href="${ctx}/backIndex" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a href="#">角色管理</a>
				</li>
				<li class="split"></li>
				<li class="active">
					角色列表
				</li>
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
									<th>
										角色名称：
									</th>
									<td width="20%">
										 <input type="text" placeholder="角色名称" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
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
						<%-- <gsww:opTag menuId="8a9200c05e5f797f015e5f8c0ee10003" tabIndex="1" operatorType="1"></gsww:opTag> --%>
						<ul class="list-Topbtn">
							<li class="add"><a title="新增" onclick="add('complat/croleEdit');">新增</a></li>
							<li class="del"><a title="删除" onclick="deleteRole();">删除</a></li>
						</ul>
					</div>
					<!-- 操作按钮结束 -->

				</div>
				<!-- 提示信息开始 -->
				<div class="form-alert">
					<tags:message msgMap="${msgMap}"></tags:message>
				</div>
				<!-- 提示信息结束 -->
				<!-- 列表开始 -->
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
					<thead>
						<tr>
							<th width="3%">
								<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>
							</th>
							<th width="80px" style="text-align: center">
								角色名称
							</th>
							<th width="45%" style="text-align: center">
								角色描述
							</th>
							<th width="5%" style="text-align: center">
								缺省角色
							</th>
							<th width="20%" style="text-align: center">
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
										<input id="${complatRole.iid}" value="${complatRole.iid}" type="checkbox" class="check_btn" style="display: none;" />
									</div>
								</td>
								<td style="text-align: center">
									${complatRole.name}
								</td>
								<td style="word-break: break-all; word-wrap: break-word; text-align: center">
									${complatRole.spec}
								</td>
								<td style="text-align: center">
									<input type="checkbox" <c:if test="${complatRole.isdeFault==1}">checked</c:if> onclick="modifyIsDefault(${complatRole.iid},this);"/>
								</td>
								<td class="position-content" style="text-align: center">
									<div class="listOper">
										<ul>
											
											<li class="bluegreen" onclick="btnAuthorize(this);">
												<i></i>
												<a>授权</a>
											</li>
											<li class="blue" onclick="add('complat/croleEdit','croleId',this);">
												<i></i>
												<a>编辑</a>
											</li>
											<li class="red" onclick="deleteSingle('complat/croleDelete','croleId',this);">
												<i></i>
												<a>删除</a>
											</li>
											
										</ul>
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
	
<div id="tabs" style="display:none;width: 68%;margin: auto;position: absolute;left: 165px;top: 50.5px">
 	<ul style="text-align: center;margin-left: 16px; ">
	    <li style="text-align: center;"><a id="yhjg"  href="">用户机构管理</a></li>
	    <li style="text-align: center;"><a id="yyqx"  href="">应用权限设置</a></li>
	    <li style="text-align: center;"><a id="xtqx"  href="" >系统资源授权</a></li>
  	</ul>
 <!--text-align: center;  -->
</div>
	</body>
	
	
</html>
