<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<meta charset="utf-8"/>
<title></title>
<script type="text/javascript"> 

/**进入新增页面，传入参数类型值**/
function addPara(){
	var paraTypeId=$(".paraTypeId").val();
		var paraId=0;
		window.location.href="${ctx}/sys/paraEdit?paraId="+paraId+"&paraTypeId="+paraTypeId;
	
}
/**进入编辑页面，传入参数值**/
function editPara(obj){
		 var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
	var paraTypeId=$(".paraTypeId").val();
	window.location.href="${ctx}/sys/paraEdit?paraId="+singleId+"&paraTypeId="+paraTypeId;
}

function editDoublePara(obj){
	 var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
	var paraTypeId=$(".paraTypeId").val();
	window.location.href="${ctx}/sys/doubleParaList?paraId="+singleId+"&paraTypeId="+paraTypeId;
}
/**删除单条信息**/

function delSingle(obj){
		var singleId=$(obj).parent().parent().parent().parent().find('td:first').find('input').attr('id'); 
		var paraTypeId=$(".paraTypeId").val();
		$.dialog.confirm('您确认要删除吗？',function(){
		window.location.href="${ctx}/sys/paraDelete?paraId="+singleId+"&paraTypeId="+paraTypeId;
	});
}
/**删除多条**/
function delSome(){
	var paraTypeId=$(".paraTypeId").val();
if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
		$.dialog.confirm('您确认要删除吗？',function(){
			var ids = "";
			$('.list-table tbody input[type=checkbox]').each(function(i, o) {
				if($(o).attr('checked')) {
					ids += $(o).val() + ",";
				}
			});
			window.location.href="${ctx}/sys/paraDelete?paraId="+ids.substring(0,ids.length-1)+"&paraTypeId="+paraTypeId;
		});
		
	}else{
		$.dialog.confirm('请您至少选择一条数据',function(){
			return null;
		});
	}
}
function checkSubmitForm(){
	var paraNameSearch=$("#paraNameSearch").val();
	if(paraNameSearch==''||isChinaOrNumbOrLett(paraNameSearch)){
		pageForm.submit();
	}else{
		$.validator.errorShow($("#paraNameSearch"),'只能包括中英文、数字、@和下划线');
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
			<a href="${ctx}/sys/paraTypeList">参数类型</a>
		</li>
		<li class="split"></li>
		<li class="active">
			参数管理
		</li>
	</ol>
	<!--列表内容区域-->
	<div class="list">
        <div class="list-topBar">
             <!-- 搜索内容开始 -->
             <div class="search-content">
             	<form id="form1" name="pageForm" action="${ctx}/sys/paraList">
             	<input id="serchParaTypeId" type="hidden" name="search_EQ_sysParaType.paraTypeId" value="${sParams['EQ_sysParaType.paraTypeId']}"/>
	            <input id="${sysPara.sysParaType.paraTypeId}" type="hidden" value="${paraTypeId['paraTypeId']}"   class="paraTypeId" />
	           		<table style="width: 540px; float: right;">
						<tr>
							<th>参数名称：</th>
							<td>
								<input type="text" placeholder="参数名称" id="paraNameSearch" name="search_LIKE_paraName" class="input" value="${sParams['LIKE_paraName']}"/>
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
             <ul class="list-Topbtn">
            	<li class="add"><a  title="新增" onclick="addPara(); ">新增</a></li>
                <li class="del"><a  title="删除" onclick="delSome(); " >删除</a></li>
             </ul>
             </div>
             <!-- 操作按钮结束 -->
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
                	<th width="10%"> 
                	<div class="label"> 
                		<i class="check_btn check_all"></i>
                    	</div>               		
                	</th>
                    <th width="40%">参数编码</th>
                    <th width="45%">参数名称</th>
                    <th width="5%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="sysPara">
					<tr  class="myclass">
	                	<td>
	                    	<div class="label">
	                                <i class="check_btn"></i>
	                                <input id="${sysPara.paraId}" value="${sysPara.paraId}" type="checkbox" class="check_btn"  style="display:none;"  />
	                         </div>
	                    </td>
	                	<td>
	                    	${sysPara.paraCode}
	                    </td>
	                	<td>
	                   		${sysPara.paraName}
	                    </td>
	                	<td class="position-content">
	                    	<div class="listOper">
	                            <ul >
	                            	<li class="list-line"></li>
	                            	<li class="blue" onclick="editPara(this);"><i></i><a>编辑</a></li>
 	                                <li class="red" onclick="delSingle(this);"><i></i><a>删除</a></li>
	                                <li class="list-line"></li>
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
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>
</body>
</html>
