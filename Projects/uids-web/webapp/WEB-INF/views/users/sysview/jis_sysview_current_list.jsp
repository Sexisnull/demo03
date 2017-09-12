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
	
			<div class="list">
				
				<div class="list-topBar">
			
					 <div class="search-content">
					 <form id="form1" name="pageForm" action="${ctx}/uids/jisCurList" method="get">
					     <div>
							<table class="">
								<tr>
									<th>
										操作类型：
									</th>
									<td width=130px>
						                  <select id="operateType" name="operateType"  class="select right" >
						          	  		<option value="0">------请选择------</option>
							          		<option value="新增用户">新增用户</option>
							          		<option value="新增机构">新增机构</option>
							          		<option value="修改用户">删除用户</option>
							          		<option value="修改机构">删除机构</option>
							            	        	</option>		          		
						          		</select>
									</td>
					
									<th>
										同步结果：
									</th>
									<td width=130px>
									<select id="optresult" name="optresult"  class="select right" >
	          	  		               <option value="0">------请选择------</option>
		          		               <option value="未同步">未同步</option>
		          		               <option value="同步失败">同步失败</option>
		          		               <option value="同步成功">同步成功</option>
		          		               <option value="网络不通">网络不通</option>
		            	        	</option>		          		
	          		               </select>
									</td>
							 
						         <th>
										操作时间：
									</th>
									<td width=150px>
										<input type="text" id="jisNameSearch" placeholder="时间"
											 onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"
											/>
									</td>
					
						            <th>
										操作对象名称：
									</th>
										<td width=120px>
								<input type="text" placeholder="操作对象名称" id="objectnameSearch" 
								name="search_LIKE_objectname"  value="${sParams['LIKE_objectname']}" class="input"/>
							</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
								
									</td>
								</tr>
							</table>
							</div>
						</form>
					</div>
					<!-- 搜索内容结束 -->
					<!-- 操作按钮开始 -->
					<div class="list-toolbar">
						<gsww:opTag menuId="297e40e05e5f7a4f015e5f93f7b20002" tabIndex="2" operatorType="1"></gsww:opTag>
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
										${jisCurrent.appid}
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
										${jisCurrent.optresult}
									</div>
								</td>
								
	                          	<td class="position-content">
										<gsww:opTag menuId="297e40e05e5f7a4f015e5f93f7b20002" tabIndex="1" operatorType="2"></gsww:opTag>
									
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
		