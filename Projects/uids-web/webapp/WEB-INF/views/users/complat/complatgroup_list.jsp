<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	
	function deleteData() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要删除吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/uids/complatgroupDelete?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}

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
		
	});
	/**搜索表单校验**/
function checkSubmitForm(){
	var nameSerach=$("#nameSerach").val();
	if(nameSerach==''||isChinaOrNumbOrLett(nameSerach)){
		form1.submit();
	}else{
		$.validator.errorShow($("#nameSerach"),'只能包括中英文、数字、@和下划线');
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
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >机构管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				机构管理
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/uids/complatgroupList" method="get">
			<table class="advanced-content">
				<tr>
					<th style="padding-left: 300px">机构名称：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="机构名称" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
						</td>
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
					<td class="btn-group"> <a id="advanced-btn" class="btnSearch" >高级搜索</a></td>
				</tr>
			</table>
		</form>
		<!-- 高级探索表单 -->
		<form id="form2" name="form2" action="${ctx}/uids/complatgroupList">
      			<table class="advanced-content" style="display: none">
      			  <tr>
					<th>机构名称:</th>
					<td width="15%">
						<input type="text" class="input" name="search_LIKE_groupallname" value="${sParams['LIKE_groupallname']}"/>
					</td>
					<th>机构编码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_codeid" value="${sParams['LIKE_codeid']}"/>
					</td>
					<th>组织机构代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_orgcode" value="${sParams['LIKE_orgcode']}"/>
					</td>
					<th>区域代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_areacode" value="${sParams['LIKE_areacode']}"/>
					</td>
					<td class="btn-group"> <a class="btnSearch" id="advanced-search-btn">搜索</a></td>
				</tr>
				<tr>
				    <th>节点类型:</th>
					<td width="15%">
					    <select name="search_LIKE_nodetype" value="${sParams['LIKE_nodetype']}">
						     <option value="">请选择</option>
						     <option value='1'>区域</option>
						     <option value='2'>单位</option>
						     <option value='3'>部门或处室</option>
						     <option value='4'>下属单位</option>
					    </select>
					</td>
					<th>区域类型:</th>
					<td width="15%">
						<select name="search_LIKE_areatype" value="${sParams['LIKE_areatype']}">
						     <option value="">请选择</option>
						     <option value="1">省级</option>
						     <option value="2">市（州）级</option>
						     <option value="3">区县</option>
						     <option value="4">乡镇街道</option>
						     <option value="5">其他</option>
					    </select>
					</td>
				</tr>
      		</table>  
     	</form>
	</div>
    
    
	<!--列表内容区域-->
	<div class="list">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
	<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
		
        <div class="list-topBar  advanced-search">
        	 <div class="list-toolbar">
            <!-- 操作按钮开始	 -->
              <gsww:opTag menuId="8a92012d5e7de06a015e7de18b3a0001" tabIndex="1" operatorType="1"></gsww:opTag>
             <!-- 操作按钮结束 -->
           </div> 
            
        </div>
        <!-- 高级探索表单 
        <form id="form2" name="form2" action="${ctx}/uids/complatgroupList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>用户账号:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>用户姓名:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>          
            <li class="advanced-search-btn">搜索</li>
        </ul>
        </form>
        -->
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
                    <th width="15%" style="text-align: center;">机构名称</th>
                    <th width="15%" style="text-align: center;">机构编码</th>
                    <th width="15%" style="text-align: center;">机构后缀</th>
                    <th width="10%" style="text-align: center;">节点类型</th>
                    <th width="25%" style="text-align: center;">机构全名</th>
                    <th width="10%" style="text-align: center;">上级机构</th>
                    <th width="10%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatGroup" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatGroup.iid}" value="${complatGroup.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.name}" class="word_break">${complatGroup.name}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.codeid}" class="word_break">${complatGroup.codeid}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatGroup.suffix}" class="word_break">${complatGroup.suffix}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatGroup.nodetype == 1}">区域</c:if>
	                           		<c:if test="${complatGroup.nodetype == 2}">单位</c:if>
	                           		<c:if test="${complatGroup.nodetype == 3}">部门或处室</c:if>
	                           		<c:if test="${complatGroup.nodetype == 4}">下属单位</c:if>
	                    		</div>
	                        </div>
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	${complatGroup.groupallname}
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	${parentNameMap[complatGroup.pid]}
	                    </td>
	        
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a92012d5e7de06a015e7de18b3a0001" tabIndex="1" operatorType="2"></gsww:opTag>
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