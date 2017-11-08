<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>

<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	function checkSubmitForm(){
		var nameSearch = $("#nameSearch").val();
		var loginNameSearch = $("#loginNameSearch").val(); 
		var papersNumberSearch = $("#papersNumberSearch").val();
		var papersNumberLength = papersNumberSearch.length;
		if(nameSearch ==  '' || isNumbOrLett1(nameSearch)){
			if(loginNameSearch ==  '' || isNumbOrLett2(loginNameSearch)) {
				if(papersNumberSearch ==  '' || (isNumbOrLett3(papersNumberSearch) && papersNumberLength <= 18)) {
					form1.submit();
				} else{
					$.validator.errorShow($("#papersNumberSearch"),'只能包括数字和字母,且不能超过18个字符');
				}
			} else{
				$.validator.errorShow($("#loginNameSearch"),'只能包括字母、数字、下划线,且不能超过255个字符');
			}
		} else{
			$.validator.errorShow($("#nameSearch"),'只能包括字母、数字、下划线、中文,且不能超过255个字符');
		}
	}
	
	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett1( s ){
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,255}$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function isNumbOrLett2( s ){
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]{1,255}$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function isNumbOrLett3( s ){
		var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	//dialog弹框
	function openwindow(userAcctId){
		if(null != userAcctId && "" != userAcctId){
			var api = $.dialog({
				title : '个人用户-用户认证',
				width : 470,
				height: 358,
				max : false,
				min : false,
				lock : true,
				padding : '40px 20px',
				content : 'url:${ctx}/complat/getOutsideuserInfo?iid='+userAcctId,
				fixed : false,
				drag : false,
				resize : false
			});
		}
	}
    
    /**批量关闭操作**/	
	function stopData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要关闭吗？',function(){
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
	/**批量开启操作**/	
	function startData(url,parm){
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
			$.dialog.confirm('您确认要开启吗？',function(){
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
</script>
<style type="text/css">
/*设置弹出层样式*/
.alert_tb {	
	left:180px;
	top:120px;
	border:1px solid #F68A8A;
	width:auto;
	height:auto;
	background-color:#e2ecf5;
	z-index:1000;
	position:absolute;
} 
.mybg{
	background-color:#000;
	width:100%;
	height:100%;
	position:absolute;
	top:0; 
	left:0; 
	zIndex:500; 
	opacity:0.3; 
	filter:Alpha(opacity=30); 
}
.input_one i{
    float: right;
    font-style: normal;
    cursor: pointer;
}
.input_one {
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
.input_three {
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
.input_four {
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
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
			<li class="active">
				公网用户
			</li>
			<li class="split"></li>
			<li class="active">
				个人用户
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/outsideuserList" method="get">
			<table class="advanced-content">
				<tr>
					<th style="padding-left: 30px">姓名：</th>
						<td width="33%">
							<input type="text"  style="width: 170px;" placeholder="姓名" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
						</td>
					<th style="padding-left: 20px">登录名：</th>
						<td width="33%">
							<input type="text"  style="width: 170px;" placeholder="登录名" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" />
						</td>
					<th style="padding-left: 20px">身份证号码：</th>
						<td width="33%">
							<input type="text"  style="width: 170px;" placeholder="身份证号码" value="${sParams['LIKE_papersNumber']}" id="papersNumberSearch" name="search_LIKE_papersNumber" />
						</td>
					<td style="padding-right: 30px" class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
				</tr>
			</table>
		</form>
		<!-- 高级探索表单 -->
		<form id="form2" name="form2" action="${ctx}/complat/outsideuserList" method="get">
			<table class="advanced-content" style="display:none;">
				<tr>
					
					<td class="btn-group"> <a id="advanced-search-btn" class="btnSearch" >搜索</a></td>
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
             <gsww:opTag menuId="8a929c4a5e56e308015e571a965a0002" tabIndex="1" operatorType="1"></gsww:opTag>
           	 <!-- 操作按钮结束 -->
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
                    <th width="10%" style="text-align: center;">姓名</th>
                    <th width="10%" style="text-align: center;">登录名</th>
                    <th width="10%" style="text-align: center;">手机号码</th>
                    <th width="20%" style="text-align: center;">邮箱</th>
                    <th width="5%" style="text-align: center;">账号开启</th>
                    <th width="5%" style="text-align: center;">审核状态</th>
                    <th width="10%" class="alignL" style="text-align: center;">实名认证</th>
                    <th width="20%" class="alignL" style="text-align: center;">注册时间</th>
                    <th width="15%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="outsideUser" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${outsideUser.iid}" value="${outsideUser.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td align="center" title="${outsideUser.name}" class="box_main_td" nowrap="nowrap">
							<c:if test="${fn:length(outsideUser.name)>6}">
							  ${fn:substring(outsideUser.name,0,6)}...
							</c:if>
							<c:if test="${fn:length(outsideUser.name)<=6}">
							   ${outsideUser.name}&nbsp;
							 </c:if> 
						</td>
	                    <td align="center" title="${outsideUser.loginName}" class="box_main_td" nowrap="nowrap">
							<c:if test="${fn:length(outsideUser.loginName)>15}">
							  ${fn:substring(outsideUser.loginName,0,15)}...
							</c:if>
							<c:if test="${fn:length(outsideUser.loginName)<=15}">
							   ${outsideUser.loginName}&nbsp;
							 </c:if> 
						</td>
	                    <td style="text-align: center;">
	                    	<div title="${outsideUser.mobile}" class="word_break">${outsideUser.mobile}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${outsideUser.email}" class="word_break">${outsideUser.email}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.enable == '0'}"><font color="red">关闭</font></c:if>
	                           		<c:if test="${outsideUser.enable == '1'}">开启</c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.authState == '0'}"><font color="red">未审核</font></c:if>
	                           		<c:if test="${outsideUser.authState == '1'}"><font color="green">已通过</font></c:if>
	                           		<c:if test="${outsideUser.authState == '2'}"><font color="blue">未通过</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.isAuth == '0'}">
		                    			<input type="button"  style="border: none;color: white;background: green;cursor:pointer;" value="   认   证   " onclick="openwindow(${outsideUser.iid});">
		                    		</c:if>
		                    		<c:if test="${outsideUser.isAuth == '1'}">
		                    			已认证
		                    		</c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${outsideUser.createTime}" class="word_break">
								<fmt:formatDate value="${outsideUser.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</div>
	                    </td>
	                    
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a929c4a5e56e308015e571a965a0002" tabIndex="1" operatorType="2"></gsww:opTag>
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
