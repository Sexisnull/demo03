<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<title></title>
<script type="text/javascript"> 	
	/**批量停用操作**/	
	function stopData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要停用吗？',function(){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/"+url+"?"+parm+"="+ids.substring(0,ids.length-1);
			});
		}else{
			$.dialog.alert('请您至少选择一条数据',function(){
				return null;
			});
		}
	}
	/**批量启用操作**/	
	function startData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要启用吗？',function(){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/"+url+"?"+parm+"="+ids.substring(0,ids.length-1);
			});
		}else{
			$.dialog.alert('请您至少选择一条数据',function(){
				return null;
			});
		}
	}
	/** 批量删除* */
	function deleteData(url, parm) {
		if ($(".check_btn:checked").length != 0 && $('.list-table tbody input:checkbox:checked').length != 0) {
			$.dialog.confirm('您确认要删除吗？', function() {
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if ($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href = path + url + "?" + parm + "="
						+ ids.substring(0, ids.length - 1);
			});
	
		} else {
			$.dialog.alert('请您至少选择一条数据', function() {
				return null;
			});
		}
	}
	/**停用单条数据**/
	function stopSingle(url,parm,obj){
	var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id');
		var flag=false;
			$.ajax({
				 type: "POST",
				 url: "${ctx}/operator/checkOperatorState",
				 data: "operatorId=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "0") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该操作已经停用");
				return false;
			}else{
				$.dialog.confirm('您确认要停用吗？',function(){
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				});
		}
	}
	/**启用单条数据**/
	function startSingle(url,parm,obj){
	var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
	var flag=false;
			$.ajax({
				 type: "POST",
				 url: "${ctx}//operator/checkOperatorState",
				 data: "operatorId=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "1") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该操作已经启用");
				return false;
			}else{
				$.dialog.confirm('您确认要启用吗？',function(){
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				});
		}
	}
/**搜索表单校验**/
function checkSubmitForm(){
	var operName=$("#operatorName").val();
	if(operName==''||isChinaOrNumbOrLett(operName)){
		form1.submit();
	}else{
		$.validator.errorShow($("#operatorName"),'只能包括中英文、数字、@和下划线');
	}
}
$(function(){
		//阻止按键盘Enter键提交表单
		var $inp = $('input');
		$inp.keypress(function (e) { 
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
			<a href="${ctx}/backIndex" target="_top">首页</a>
		</li>
		<li class="split"></li>
		<li>
			<a href="#">系统管理</a>
		</li>
		<li class="split"></li>
		<li class="active">
			操作管理
		</li>
	</ol>
	<!--列表内容区域-->
	<div class="list">
		<!--<div class="list-title">操作列表</div>-->
        <div class="list-topBar">
             <!-- 搜索内容开始 -->
               <div class="search-content">
				<form id="form1" name="form1" action="${ctx}/operator/operatorList" method="get">
					<table class="">
						<tr>
							<th>操作名称：</th>
							<td width="20%">
								<input type="text" id="operatorName" name="search_LIKE_operatorName" placeholder="操作名称" class="input" value="${sParams['LIKE_operatorName']}"/>
							</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
	    <!-- 操作按钮 -->
	    <div class="list-toolbar">
            <gsww:opTag menuId="13" tabIndex="1" operatorType="1"></gsww:opTag>
        </div>  
        </div>
        <!-- 搜索内容结束 -->
   
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
         <!-- 列表开始 -->
        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
        	<thead>
            	<tr>
                	<th width="10">
                	   <div class="label"> 
                		<i class="check_btn check_all"></i>
                       </div>
                    </th>
                    <th width="10%">所属菜单</th>
                    <th width="9%">操作名称</th>
                    <th width="25%">操作的名称地址</th>
                    <th width="15%">操作按钮图片地址</th>
                    <th width="10%">排序</th>
                    <th width="10%">状态</th>
                    <th width="10%">操作类型</th>
                    <th width="10%">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="sysOperator">
					<tr>
	                	<td>
	                    	<div class="label">
	                                <i class="check_btn"></i>
	                                <input id="${sysOperator.operatorId}" value="${sysOperator.operatorId}" type="checkbox" class="check_btn" style="display:none;"/>
	                         </div>
	                    </td>
	                	
	                	<td>
	                    	${sysOperator.sysMenu.menuName}
	                    </td>
	                	<td >
	                    	${sysOperator.operatorName}
	                    </td>
	                	<td >
	                    	${sysOperator.operatorUrl}
	                    </td>
	                	<td >
	                    	${sysOperator.operatorImage}
	                    </td>
	                	<td >
	                    	${sysOperator.operatorLevel}
	                    </td>
	                	<td>
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${sysOperator.operatorState == '0'}"><font color="red">无效</font></c:if>
	                           		<c:if test="${sysOperator.operatorState == '1'}"><font color="#32CD32">有效</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                	<td >
	                		  <div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${sysOperator.operatorType == '1'}"><font color="red">顶部按钮</font></c:if>
	                           		<c:if test="${sysOperator.operatorType == '2'}"><font color="#32CD32">单行按钮</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                	<td class="position-content">
	                        <gsww:opTag menuId="13" tabIndex="1" operatorType="2"></gsww:opTag>
	                    </td>
	                </tr>
				</c:forEach>
                
            </tbody>       
        </table>
        <!-- 列表结束 -->
    </div>
   <!-- 分页 -->
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>

</body>
</html>
