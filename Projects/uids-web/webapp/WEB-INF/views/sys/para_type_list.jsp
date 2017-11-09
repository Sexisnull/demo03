<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<title></title>
<script type="text/javascript"> 
/**进入新增页面，传入参数类型值**/
function addPara(){
	var paraTypeId=$(".paraTypeId").val();
		var paraId=0;
		window.location.href="${ctx}/sys/paraTypeEdit?paraId="+paraId+"&paraTypeId="+paraTypeId;
	
}
/**进入参数管理页面，传入参数类型值**/
function linkParaPage(obj){
	//var paraTypeId=$(".paraTypeId").val();
		 var singleId=$(obj).parents("td").parent().find('td:first').find('input').attr('id'); 
	
		window.location.href="${ctx}/sys/paraList?paraTypeId="+singleId;
}
function checkSubmitForm(){
	var paraTypeNameSearch=$("#paraTypeNameSearch").val();
	if(paraTypeNameSearch==''||isChinaOrNumbOrLett(paraTypeNameSearch)){
		form1.submit();
	}else{
		$.validator.errorShow($("#paraTypeNameSearch"),'只能包括中英文、数字、@和下划线');
	}
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
			window.location.href="${ctx}/sys/paraTypeDelete?paraTypeId="+ids.substring(0,ids.length-1);
		});
		
	}else{
		$.dialog.confirm('请您至少选择一条数据',function(){
			return null;
		});
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
					首页
				</li>
				<li class="split"></li>
				<li class="active">
					系统管理
				</li>
				<li class="split"></li>
				<li class="active">
					参数类型
				</li>
			</ol>
	<!--列表内容区域-->
	<div class="list">
		<!-- <div class="list-title">参数类型列表</div> 
        <div class="list-topBar">
        	<div class="list-topBar-left">
        		<label class="label">
                    <i class="check_btn check_all"></i><span>选择全部</span>
            	 </label>
            </div> -->
            
            <!-- 搜索内容开始 -->
             <div class="search-content">
				<form id="form1" name="pageForm" action="${ctx}/sys/paraTypeList" method="get">
					<table class="">
						<tr>
							<th>参数类型名称：</th>
							<td width="20%">
								<input type="text" placeholder="参数类型名称" id="paraTypeNameSearch" name="search_LIKE_paraTypeName"  value="${sParams['LIKE_paraTypeName']}" class="input"/>
							</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
             <!-- 搜索内容结束 -->
            <!-- 操作按钮开始 -->	 
            <!--  <div class="list-toolbar">
             <ul class="list-Topbtn">
            	<li class="add"><a  title="新增" onclick="addPara(); ">新增</a></li>
                <li class="del"><a  title="删除" onclick="delSome(); " >删除</a></li>
             </ul>
             </div>--> 
            
             <div class="list-toolbar">
             <gsww:opTag menuId="16" tabIndex="1" operatorType="1"></gsww:opTag>
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
                	<th width="10"> 
                	<div class="label"> 
                		<i class="check_btn check_all"></i>
                    	</div>               		
                	</th>
                    <th width="40%">参数类型名称</th>
                    <th width="40%">参数类型描述</th>
                    <%--
                      <th width="5%">状态</th>
                    --%>
                    <th width="15%" style="text-align: center">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="sysParaType">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                                <i class="check_btn"></i>
	                                <input id="${sysParaType.paraTypeId}" value="${sysParaType.paraTypeId}"   type="checkbox" style="display:none;"  class="check_btn" />
	                         </div>
	                    </td>
	                	<td>
	                    	${sysParaType.paraTypeName}
	                    </td>
	                	<td  style="word-break:break-all; word-wrap:break-word;">
	                   		${sysParaType.paraTypeDesc}
	                    </td><%--
	                    <td>
	                    <c:if test="${sysParaType.paraTypeState=='0'}" ><font color="red">无效</font></c:if>
	                   	<c:if test="${sysParaType.paraTypeState=='1'}" ><font color="#32CD32">有效</font></c:if>
	                   
	                    </td>
	                	--%>
	                	<td class="position-content">
	                		<gsww:opTag menuId="16" tabIndex="1" operatorType="2"></gsww:opTag>	                 
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
