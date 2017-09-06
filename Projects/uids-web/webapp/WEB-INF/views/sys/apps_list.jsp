<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<title>甘肃万维JUP课题</title>
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
	/**停用单条数据**/
	function stopSingle(url,parm,obj){
	var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
		var flag=false;
			$.ajax({
				 type: "POST",
				 url: "${ctx}/apps/checkAppsState",
				 data: "key=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "00B") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该应用已经停用");
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
				 url: "${ctx}//apps/checkAppsState",
				 data: "key=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "00A") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该应用已经启用");
				return false;
			}else{
				$.dialog.confirm('您确认要启用吗？',function(){
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				});
		}
	}
	/**停用单条绿色通道数据**/
	function stopSingleGreen(url,parm,obj){
	var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
		var flag=false;
			$.ajax({
				 type: "POST",
				 url: "${ctx}/apps/checkAppsAppGreenChanal",
				 data: "key=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "00A") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该应用绿色通道已经停用");
				return false;
			}else{
				$.dialog.confirm('您确认要停用绿色通道吗？',function(){
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				});
		}
	}
	/**启用单条绿色通道数据**/
	function startSingleGreen(url,parm,obj){
	var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
	var flag=false;
			$.ajax({
				 type: "POST",
				 url: "${ctx}//apps/checkAppsAppGreenChanal",
				 data: "key=" + singleId,
				 async:false,
				 success: function (data) {
				 if (data == "00B") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该应用绿色通道已经启用");
				return false;
			}else{
				$.dialog.confirm('您确认要启用绿色通道吗？',function(){
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				});
		}
	}
	/**批量停用绿色通道操作**/	
	function stopDataGreen(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要停用绿色通道吗？',function(){
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
	/**批量启用绿色通道操作**/	
	function startDataGreen(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要启用绿色通道吗？',function(){
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
/**搜索表单校验**/
function checkSubmitForm(){
	var appName=$("#appName").val();
	if(appName==''||isChinaOrNumbOrLett(appName)){
		form1.submit();
	}else{
		$.validator.errorShow($("#appName"),'只能包括中英文、数字、@和下划线');
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
					<a href="${ctx}/index" target="_top">首页</a>
				</li>
				<li class="split"></li>
				<li>
					<a href="#">系统管理</a>
				</li>
				<li class="split"></li>
				<li class="active">
					应用管理
				</li>
			</ol>
	<!--列表内容区域-->
	<div class="list">
		<!-- <div class="list-title">应用列表</div>
        <div class="list-topBar">
        	<div class="list-topBar-left">
        		<div class="label">
                    <i class="check_btn check_all"></i><span>选择全部</span>
                    <input type="checkbox" class="check_btn" style="display:none;"/>
            	 </div>
            </div> 
            <!-- 操作按钮开始 -->
            <!--<ul class="list-Topbtn">
            	 <li class="add"><a title="新增" onclick="add('operator/operatorEdit'); "></a></li>
            	 <li class="enable"><a title="启用" onclick="startData('operator/operatorStart','operatorId'); " ></a></li>
               	 <li class="disable"><a title="停用" onclick="stopData('operator/operatorStop','operatorId'); " ></a></li>                
                <li class="del"><a title="删除" onclick="deleteData('operator/operatorDelete','operatorId'); " ></a></li>
             </ul>
             --><!-- 操作按钮结束 -->
             <!-- 搜索内容开始 -->
              <div class="search-content">
              <form id="form1" name="form1" action="${ctx}/apps/appsList" method="get">
					<table class="">
						<tr>
							<th>应用名称：</th>
							<td width="20%">
							<input type="text" placeholder="应用名称" id="appNameSearch" name="search_LIKE_appName"  value="${sParams['LIKE_appName']}" class="input"/>
							</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
             <!-- 搜索内容结束 -->                    	
             <div class="list-toolbar">
             <gsww:opTag menuId="18" tabIndex="1" operatorType="1"></gsww:opTag>
             </div>
        </div>
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
         <!-- 列表开始 -->
        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
        	<thead>
            	<tr>
                	<th width="10px">   
                		<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>             		
                	</th>
                    <th width="10%" style="text-align: center;">应用名称</th>
                    <th width="15%" style="text-align: center;">应用URL</th>
                    <th width="20%" style="text-align: center;">应用描述</th>
                    <th width="10%" style="text-align: center;">应用图标</th>
                    <th width="10%" style="text-align: center;">应用编码</th>
                    <th width="10%" style="text-align: center;">应用状态</th>
                    <th width="15%" style="text-align: center;">绿色通道</th>
                    <th width="9%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="sysApps">
					<tr>
	                	<td>
	                    	<div class="label">
	                                <i class="check_btn"></i>
	                                <input id="${sysApps.key}" value="${sysApps.key}" type="checkbox" class="check_btn" style="display:none;"/>
	                         </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	${sysApps.appName}
	                    </td>
	                	<td style="text-align: center;">
	                    	${sysApps.appUrl}
	                    </td>
	                    <td style="text-align: center;"> 
	                    	${sysApps.appDesc}
	                    </td>
	                	<td style="text-align: center;">
	                    	${sysApps.appLogo}
	                    </td>
	                    <td style="text-align: center;">
	                    	${sysApps.appCode}
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${sysApps.appState == '00B'}"><font color="red">禁用</font></c:if>
	                           		<c:if test="${sysApps.appState == '00A'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${sysApps.appGreenChanal == '00A'}"><font color="red">未启用</font></c:if>
	                           		<c:if test="${sysApps.appGreenChanal == '00B'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                	<td class="position-content">
	                            <gsww:opTag menuId="18" tabIndex="1" operatorType="2"></gsww:opTag>
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
