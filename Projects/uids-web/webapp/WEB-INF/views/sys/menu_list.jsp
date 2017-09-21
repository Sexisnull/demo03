<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx }/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>

<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
/**搜索表单校验**/
function checkSubmitForm(){
	var menuNameSerach=$("#menuNameSerach").val();
	if(menuNameSerach==''||isChinaOrNumbOrLett(menuNameSerach)){
		form1.submit();
	}else{
		$.validator.errorShow($("#menuNameSerach"),'只能包括中英文、数字、@和下划线');
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
function deleteData() {
	var paraTypeId=$(".menuId").val();
	if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要删除吗？',function(){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/menu/menuDelete?menuId="+ids.substring(0,ids.length-1);
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
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >系统管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				菜单管理
			</li>
    	</ol>
    	<%-- <a href="${ctx}/backIndex" target="_top">首页</a><em>></em><a>系统管理</a><em>></em><a class="last-position">菜单管理</a> --%>
    </div>
    
    <div class="search-content">
				<form id="form1" name="pageForm" action="${ctx}/menu/menuList" method="get">
					<table class="">
						<tr>
							<th>菜单名称：</th>
							<td width="20%">
								<input type="text" id="menuNameSerach" placeholder="菜单名称" name="search_LIKE_menuName" class="input" value="${sParams['LIKE_menuName']}"/>
							</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
    
    
    
	<!--列表内容区域-->
	<div class="list">
		
        <div class="list-topBar">
           
    	<!-- 提示信息结束 --> 	
    	 <!-- 操作按钮开始 -->	
    	 <div class="list-topBar  advanced-search">
        	 <div class="list-toolbar"> 
            <gsww:opTag menuId="17" tabIndex="1" operatorType="1"></gsww:opTag>
            </div>
         </div>
         <!-- 操作按钮结束 -->
             <!--<ul class="list-Topbtn">
             <li class="add"><a title="新增" onclick="add('menu/menuEdit'); "></a></li>
             <li class="del"><a title="删除" onclick="deleteData('menu/menuDelete','menuId'); " ></a></li>
             </ul>
             -->
        </div>
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
        	<thead>
            	<tr>
                	<th width="10px">
                		<div class="label">
							<i class="check_btn check_all"></i>
							<input type="checkbox" class="check_btn" style="display: none;" />
						</div> 
                	</th>
                	<th width="15%" style="text-align: center;">菜单ID</th>
                    <th width="15%" style="text-align: center;">菜单名称</th>
                    <th width="15%" style="text-align: center;">父菜单</th>
                    <th width="35%" class="alignL" style="text-align: center;">菜单URL</th>
                    <th width="10%" style="text-align: center;">排序值</th>
                    <th width="10%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="sysMenu">
					<tr>
					
						<td style="text-align: center;">
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${sysMenu.menuId}" value="${sysMenu.menuId}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	
	                    <td style="text-align: center;">
	                    	${sysMenu.menuId}
	                    </td>
	                	<td style="text-align: center;">
	                    	${sysMenu.menuName}
	                    </td>
	                	<td style="text-align: center;">
	                    	${parentmap[sysMenu.parentMenuId]}
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	<div class="list-longtext">${sysMenu.menuUrl}</div>
	                    </td>
	                    <td class="alignL" style="text-align: center;">
	                    	<div class="list-longtext">${sysMenu.menuSeq}</div>
	                    </td>
	                	
	                	<td class="position-content">
	                            <!-- 操作按钮开始 -->	 
            					<gsww:opTag menuId="17" tabIndex="1" operatorType="2"></gsww:opTag>
         						<!-- 操作按钮结束 -->
	                    </td>
	                </tr>
				</c:forEach>
                
            </tbody>       
        </table>        
    </div>
    
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>

</body>
</html>
