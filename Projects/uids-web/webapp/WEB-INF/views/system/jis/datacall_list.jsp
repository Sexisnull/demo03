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
	/**密码初始化**/	
	function initData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			    $.dialog.confirm('选中账号密码将初始化成"111111",您确认要初始化吗？',function(){
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
	
	function deleteData() {
		var paraTypeId=$(".userAcctId").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				$.dialog.confirm('您确认要删除吗？',function(){
					var ids = "";
					$('.list-table tbody input[type=checkbox]').each(function(i, o) {
						if($(o).attr('checked')) {
							ids += $(o).val() + ",";
						}
					});
					window.location.href="${ctx}/sys/accountDelete?userAcctId="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}

	/**批量停用系统**/	
	function stopData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要停用选中账号吗？',function(){ 
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
	/**批量启用单位**/	
	function startData(url,parm){
	
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			 $.dialog.confirm('您确认要启用选中账号吗？',function(){ 
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
				 url: "${ctx}/sys/checkAccountState",
				 data: "userAcctId=" + singleId,
				 dataType: 'text',
				 async:false,
				 success: function (data) {
				 if (data == "stop") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				$.dialog.alert("该账号已经停用"); 
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
				 url: "${ctx}/sys/checkAccountState",
				 data: "userAcctId=" + singleId,
				 dataType: 'text',
				 async:false,
				 success: function (data) {
				 if (data == "start") {
					 flag=true;					 
				 } else {
					 flag=false;
				 }
				}
             });
			if(flag==true){
				Dialog.alert("该账号已经启用");
				return false;
			}else{
				$.dialog.confirm('您确认要启用吗？',function(){ 
					window.location.href="${ctx}/"+url+"?"+parm+"="+singleId;
				 }); 
		}
	}
	
	$(function(){
		//高级搜索按钮点击事件
		$('.advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$(".advanced-search-btn").click(function(){
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
		var loginAccountSearch=$("#loginAccountSearch").val();
		if(loginAccountSearch==''||isNumbOrLett(loginAccountSearch)){
			
			form1.submit();
		}else{
			$.validator.errorShow($("#loginAccountSearch"),'只能包括数字和字母');
		}
	}
	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett( s ){//判断是否是字母、数字组成
		//var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
		var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}	
	function orderAccount(orderSort){
		switch(orderSort){
			case 'ASC':
				window.location.href="${ctx}/sys/accountList?orderField=loginAccount&orderSort=ASC";
				break;
			case 'DESC':
				window.location.href="${ctx}/sys/accountList?orderField=loginAccount&orderSort=DESC";
				break;
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
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >个性化设置</a>
			</li>
			<li class="split"></li>
			<li class="active">
				数据调用
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/datacall/datacallList" method="get">
			<table class="">
				<tr>
					<%-- <th>菜单名称：</th>
					<td width="20%">
						<input type="text" id="menuNameSerach" placeholder="菜单名称" name="search_LIKE_menuName" class="input" value="${sParams['LIKE_menuName']}"/>
					</td> --%>
					<th style="padding-left: 300px">数据名称：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="数据名称" value="${sParams['LIKE_resName']}" id="resNameSearch" name="search_LIKE_resName" />
						</td>
					<th>数据标识：</th>
						 <td>
							<input type="text" style="width: 170px;" placeholder="数据标识" value="${sParams['LIKE_remark']}" id="remarkSearch" name="search_LIKE_remark" />
						</td> 
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
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
            <!-- 操作按钮开始 -->	 
             <gsww:opTag menuId="8a929cb35e5b837b015e5b929dc50001" tabIndex="1" operatorType="1"></gsww:opTag>
             <!--<ul class="list-Topbtn">
            	<li class="add"><a title="新增" onclick="add('sys/accountEdit'); "></a></li>
                <li class="passwordRest"><a title="密码初始化" onclick="initData('sys/initPassWord','userAcctId'); " ></a></li>                
                <li class="disable"><a title="停用" onclick="stopData('sys/accountStop','userAcctId'); " ></a></li>
                <li class="enable"><a title="启用" onclick="startData('sys/accountStart','userAcctId'); " ></a></li>
                <li class="del"><a title="删除" onclick="deleteData('sys/accountDelete','userAcctId'); " ></a></li>
             </ul>-->
             <!-- 操作按钮结束 -->
           </div> 
            
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/datacall/datacallList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>名称:</label>
                <input type="text" class="" name="search_LIKE_resName" value="${sParams['LIKE_resName']}"/>
            </li>
            <li>
            	<label>标识:</label>
                <input type="text" class="" name="search_LIKE_remark" value="${sParams['LIKE_remark']}"/>
            </li>          
            <li class="advanced-search-btn">搜索</li>
        </ul>
        </form>
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
                    <th width="10%" style="text-align: center;">
	                    <%-- <div style="float: right;height:19px;margin-top: -6px;">
		                    <p onclick="orderAccount('ASC');" style="height:10px">
		                    	<img src="${ctx}/res/skin/default/image/sort-up.png" height="8" width="9" alt="升序"/>
		                    </p>
		                    <p onclick="orderAccount('DESC');" style="height:10px">
		                   		<img src="${ctx}/res/skin/default/image/sort-down.png" width="9" height="8"  alt="降序"/>
		                    </p>
	                    </div> --%>
                  ID        
                    </th>
                    <th width="15%" style="text-align: center;">名称</th>
                    <th width="15%" style="text-align: center;">标识</th>
                    <th width="15%" style="text-align: center;">地址</th>
                    <th width="15%" class="alignL" style="text-align: center;">调用方式</th>
                    <th width="10%" class="alignL" style="text-align: center;">验证</th>
                    <th width="30%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="datacall" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${datacall.iid}" value="${datacall.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${datacall.iid}" class="word_break">${datacall.iid}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${datacall.resName}" class="word_break">${datacall.resName}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${datacall.remark}" class="word_break">${datacall.remark}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${datacall.resUrl}" class="word_break">${datacall.resUrl}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<c:if test="${datacall.callingType==0}">IFRAME调用</c:if>
							<c:if test="${datacall.callingType==1}">RSS调用</c:if>
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	<c:if test="${datacall.isVerification==0}">否</c:if>
							<c:if test="${datacall.isVerification==1}">是</c:if>
	                    </td>
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a929cb35e5b837b015e5b929dc50001" tabIndex="1" operatorType="2"></gsww:opTag>
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
