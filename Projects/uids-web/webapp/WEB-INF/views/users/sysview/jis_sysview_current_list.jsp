<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript">

$(document).ready(function(){	
    
	 //高级搜索按钮点击事件
	$("#advanced-btn").on('click',function(){
		$('.advanced-content').toggle('fast');
	});
	$("#advanced-search-btn").click(function(){
	    var objectnameSearch = $("#objectnameSearchHigh").val();
	    if (objectnameSearch == '' || isChinaOrNumbOrLett(objectnameSearch)) {
		    $("#form2").submit();
	    } else {
		    $.validator.errorShow($("#objectnameSearchHigh"), '只能包括中英文、数字、@和下划线');
	    }
	});
	//阻止按键盘Enter键提交表单
	var $inp = $('input');
	$inp.keypress(function (e) { 
		var key = e.which; 
		if (key == 13) {
		    return false;
		}
	});	
	
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
	var objectnameSearch = $("#objectNameSearch").val();
	if (objectnameSearch == '' || isChinaOrNumbOrLett(objectnameSearch)) {
		form1.submit();
	} else {
		$.validator.errorShow($("#objectNameSearch"), '只能包括中英文、数字、@和下划线');
	}
}

/**同步*/
function sync1(url,param,obj){
    var singleId = $(obj).parents("td").parent().find('td:first').find('input').attr('id');
    $.dialog.confirm('您确认要同步吗？',function(){
		 window.location.href="${ctx}/"+url+"?"+param+"="+singleId;
	});
}
	
 /**同步+校验**/
function sync(url,param,obj){
    var singleId = $(obj).parents("td").parent().find('td:first').find('input').attr('id');
    $.get("${ctx}/sysviewCurr/checkSyncState", {"iid":singleId,"optresult":2}, function (data) {
        if (data != null) {
            if(data.success == 'true'){
                $.dialog.alert('已同步成功，不能再次同步！',function(){ 
				     return null;
			    }); 
            }else if(data.success == 'false'){
                $.dialog.confirm('您确认要同步吗？',function(){
		             window.location.href="${ctx}/"+url+"?"+param+"="+singleId;
	            });
            }
        }
    });
}

/**校验是否已同步*/
function checkSyncState(obj){
    var singleId = $(obj).parents("td").parent().find('td:first').find('input').attr('id');
     $.get("${ctx}/sysviewCurr/checkSyncState", {"iid":singleId,"optresult":2}, function (data) {
         if (data != null) {
            if(data.success == 'true'){
                $.dialog.alert('已同步成功，不能删除和再次同步！',function(){ 
				     $(obj).removeClass("active");
				     var checkbok = $(obj).parents("td").parent().find('td:first').find('input').attr("checked",false);
			    }); 
            }
        }
     });
}

/**批量同步**/
function batchSync(url,param){
	if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
		$.dialog.confirm('您确认要同步吗？',function(){
			var ids = "";
			$('.list-table tbody input[type=checkbox]').each(function(i, o) {
				if($(o).attr('checked')) {
				     ids += $(o).val() + ",";
				}
			});
			if(ids != ""){
			   $.get("${ctx}/sysviewCurr/checkSyncListState", {"iid":ids,"optresult":2}, function (data) {
                     if (data != null) {
                          if(data.success == 'true'){
                              $.dialog.alert('存在已同步成功数据，不能再次同步，请取消同步成功的数据！',function(){
                                  ids = "";
                                  return null;
                              });
                             return false
                          }
                          if(data.success == 'false'){
                             window.location.href="${ctx}/"+url+"?"+param+"="+ids.substring(0,ids.length-1);
                          }
                     }
              });
			   
			}
		});
		
	}else{
	$.dialog.confirm('请您至少选择一条数据',function(){
	    return null;
	    });
	}
}

/**详情*/
function detail(url,param,obj){
  var singleId = $(obj).parents("td").parent().find('td:first').find('input').attr('id');
  window.location.href="${ctx}/"+url+"?"+param+"="+singleId;
}


</script>
<style type="text/css">
.select{
font-size: 12px;
border: 1px solid #dddddd;
padding: 3px 8px;
height: 30px;
width: 186px;
}
#objectnameSearchHigh{
width: 168px !important;
}
.syncTime{
width: 65px !important;
}
</style>
</head>
<body>

<div class="list-warper">
	<!--列表的面包屑区域-->
			<div class="position">
				<ol class="breadcrumb">
					<li>
						<a href="${ctx}/backIndex" target="_top">首页</a>
					</li>
					<li class="split"></li>
					<li>
						<a href="#">同步管理</a>
					</li>
					<li class="split"></li>
					<li class="active">
						当前同步列表
					</li>
				</ol>
			</div>
			
    <div class="search-content">
				<!--<form id="form1" name="pageForm" action="${ctx}/sysviewCurr/jisCurList" method="get">
						<table class="advanced-content">
							<tr>
								<th style="padding-left: 350px">操作对象名称：</th>
								<td>
									<input type="text" id="objectNameSearch" name="search_LIKE_objectname" placeholder="操作对象名称" value="${sParams['LIKE_objectname']}" class="input" />
								</td>
								<td class="btn-group">
									<a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
								</td>
								<td class="btn-group"> <a id="advanced-btn" class="btnSearch" >高级搜索</a></td>
						    </tr>
						</table>
				</form>-->
				<form id="form2" name="form2" action="${ctx}/sysviewCurr/jisCurList" >
				    <input type="hidden" name="ishigh" value=""/>
				        <table class="advanced-content">
							<tr>
				                <th>应用名称：</th>
								<td>
			                        <select name="search_EQ_appid" id="appSearch" class="select">
					                     <option value="">--请选择应用名称--</option>
					                     <c:forEach items="${applications}" var="application">
						                     <option value="${application.iid}"
							              <c:if test="${sParams['EQ_appid']==application.iid}">selected </c:if>>${application.name}</option>
					                     </c:forEach>
				                     </select>
				                     
								</td>

								<th>操作类型：</th>
								<td>
									<input id="oldOperatetypeSearch" type="hidden" value="${sParams['EQ_operatetype']}"/>
									<select id="operatetypeSearch" name="search_EQ_operatetype" class="select">
										<option value="">--请选择操作类型--</option>
									</select>
								</td>

								<th>同步结果：</th>
								<td>
									<select name="search_EQ_optresult" id="optresultSearch" class="select">
					                     <option value="">--请选择同步结果--</option>
					                     <c:forEach items="${parameters}" var="parameter">
						                     <option value="${parameter.PARA_CODE}"
							              <c:if test="${sParams['EQ_optresult']==parameter.PARA_CODE}">selected </c:if>>${parameter.PARA_NAME}</option>
					                     </c:forEach>
				                     </select>
									
								</td>
							</tr>
							<tr height="10px"></tr>
							<tr>
							    <th>操作对象名称：</th>
								<td>
									<input type="text" id="objectnameSearchHigh" name="search_LIKE_objectname" placeholder="操作对象名称" value="${sParams['LIKE_objectname']}" class="input" />
								</td>
								<th>操作时间：</th>
								<td>
									<input type="text" class="syncTime Wdate" id="synctimeSearchStart" name="search_GTE_synctime" placeholder="起始时间" value="${sParams['GTE_synctime']}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
									 至 <input class="syncTime Wdate" type="text" id="synctimeSearchEnd" name="search_LTE_synctime" placeholder="结束时间" value="${sParams['LTE_synctime']}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
									<!-- search_LIKE_ -->
								</td>
								<th></th>
								<td class="btn-group" style="text-align:right;">
								    <a class="btnSearch" id="advanced-search-btn">搜索</a>
								</td>
							</tr>
						</table>
				</form>
			</div>
	<!--列表内容区域-->
	<div class="list">
		<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
        <div class="list-topBar">
        	 <div class="list-toolbar">
	             <gsww:opTag menuId="297e40e05e5f7a4f015e5f93f7b20002" tabIndex="1" operatorType="1"></gsww:opTag>
            </div> 
        </div>
        
        <!-- 提示信息开始 -->
        <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
    	<!-- 列表开始 -->
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
					<th width="15%" style="text-align: center">
						操作对象名称
					</th>
					<th width="15%" style="text-align: center">
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
	                    	<!--onclick="checkSyncState(this);"-->
	                    	 <i class="check_btn"></i><input id="${jisCurrent.iid}" value="${jisCurrent.iid}" type="checkbox" class="check_btn" style="display:none;"/>
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
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>
</body>
</html>
