<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	
	/**搜索表单**/
	function checkSubmitForm(){
		var loginNameSearch = $("#loginNameSearch").val();
		var realNameSearch = $("#realNameSearch").val();
		var nameSearch = $("#nameSearch").val();
		var cardNumberSearch = $("#cardNumberSearch").val();
		if(loginNameSearch == '' || isNumbOrLett1(loginNameSearch)){
			if(realNameSearch == '' || isNumbOrLett1(realNameSearch)){
				if(nameSearch == '' || isNumbOrLett1(nameSearch)){
					if(cardNumberSearch == '' || isCardNumber(cardNumberSearch)){
						form1.submit();
					}else{
						$.validator.errorShow($("#cardNumberSearch"),'只能包括数字和字母');
					}	
				}else{
					$.validator.errorShow($("#nameSearch"),'名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾');
				}
			}else{
				$.validator.errorShow($("#realNameSearch"),'名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾');
			}
 		}else{
 			$.validator.errorShow($("#loginNameSearch"),'名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾');
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
		var regu = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	/**
	检查身份证
	*/
	function isCardNumber( s ){
		var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function openwindow(iid){
		if(iid != null && "" != iid){
			 var api = $.dialog({
					title:'法人用户-用户认证',
					width:600,
					height:400,
					max : false,
					min : false,
					lock : true,
					padding : '40px 20px',
					content : 'url:${ctx}/complat/getCorporationInfo?iid='+iid,
					fixed : true,
					drag : false,
					resize : false
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
				<a >公网用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
					法人用户
			</li>
    	</ol>
    </div>
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/corporationList" method="get">
			<table class="advanced-content">
				<tr>
					<th>登录名：</th>
					<td>
						<input type="text" placeholder="登录名" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" class="input"/>
					</td>
					<th style="width: 5%">姓名:</th>
					<td>
						<input type="text" placeholder="姓名" class="input" name="search_LIKE_realName" id="realNameSearch" value="${sParams['LIKE_realName']}"/>
					</td>
					<th>企业（机构）名称:</th>
					<td>
						<input type="text" placeholder="企业（机构）名称" class="input" name="search_LIKE_name" id="nameSearch" value="${sParams['LIKE_name']}"/>
					</td>
					<th>身份证号码:</th>
					<td>
						<input type="text" placeholder="身份证号码" class="input" name="search_LIKE_cardNumber" id="cardNumberSearch" value="${sParams['LIKE_cardNumber']}"/>
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
        <div class="list-topBar">
        	 <div class="list-toolbar">
             <!-- 操作按钮开始-->	 
	             <gsww:opTag menuId="8a92e1025e5672aa015e56798c830001" tabIndex="1" operatorType="1"></gsww:opTag>
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
                    <th width="10%" style="text-align: center;">法人类型</th>
                    <th width="15%" style="text-align: center;">企业（机构）名称</th>
                    <th width="13%" class="alignL" style="text-align: center;">手机号码</th>
                    <th width="5%" class="alignL" style="text-align: center;">账号开启</th>
                    <th width="5%" style="text-align: center;">审核状态</th>
                    <th width="5%" style="text-align: center;">实名认证</th>
                    <th width="15%" style="text-align: center;">注册时间</th>
                    <th width="10%" style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatCorporation" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatCorporation.iid}" value="${complatCorporation.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	${complatCorporation.realName}
	                    </td>
	                	<td style="text-align: center;">
	                		${complatCorporation.loginName}
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
			                    	<c:if test="${complatCorporation.type=='1'}">企业法人</c:if>
			                    	<c:if test="${complatCorporation.type=='2'}">非企业法人</c:if>
	                    		</div>
	                    	</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	${complatCorporation.name}
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	${complatCorporation.mobile}
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                		<div class="alignL">
	                    		<div class="list-longtext">
			                		<c:if test="${complatCorporation.enable=='0'}">关闭</c:if>
			                		<c:if test="${complatCorporation.enable=='1'}">开启</c:if>
			                	</div>
			                </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatCorporation.authState == 0}"><font color="red">未审核</font></c:if>
	                           		<c:if test="${complatCorporation.authState == 1}"><font color="green">已通过</font></c:if>
	                           		<c:if test="${complatCorporation.authState == 2}"><font color="blue">未通过</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td class="alignL" style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
		                    		<c:if test="${complatCorporation.isAuth == '0'}">
		                    			<input type="button" style="border: none;color: white;background: green;" value="   认   证   " onclick="openwindow(${complatCorporation.iid});">
		                    		</c:if>
		                    		<c:if test="${complatCorporation.isAuth == '1'}">
		                    			已认证
		                    		</c:if>
	                    		</div>
	                    	</div>
	                    </td>
	                     <td style="text-align: center;">
	                    	${complatCorporation.createTime}
	                    </td>
	                    <td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a92e1025e5672aa015e56798c830001" tabIndex="1" operatorType="2"></gsww:opTag>
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
