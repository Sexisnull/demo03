<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>

<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	function checkSubmitForm(){
		var loginNameSearch = $("#loginNameSearch").val();
		if(loginNameSearch ==  '' || isNumbOrLett(loginNameSearch)){
			form1.submit();
		}else{
			$.validator.errorShow($("#loginNameSearch"),'只能包括数字和字母');
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
	
	$(function(){
		//高级搜索按钮点击事件
		$('#advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$("#advanced-search-btn").click(function(){
			$("#form2").submit();
		});
		$("#advanced-search-btn-cancel").on('click',function(){
			$('.advanced-content').toggle('fast');
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
				<a >公网用户</a>
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
					<th style="padding-left: 300px">请输入登录名：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="请输入个人用户登录名" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" />
						</td>
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
					<td class="btn-group"> <a id="advanced-btn" class="btnSearch" >高级搜索</a></td>
				</tr>
			</table>
		</form>
		<!-- 高级探索表单 -->
    	<div class="search-content">
			<form id="form2" name="form2" action="${ctx}/complat/outsideuserList" method="get">
				<table class="advanced-content" style="display:none;">
					<tr>
						<th style="padding-left: 10px">姓名：</th>
							<td width="20%">
								<input type="text"  style="width: 170px;" placeholder="请输姓名:" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
							</td>
						<th style="padding-left: 5px">登录名：</th>
							<td width="20%">
								<input type="text"  style="width: 170px;" placeholder="请输入登录名:" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" />
							</td>
						<th style="padding-left: 10px">身份证号码：</th>
							<td width="20%">
								<input type="text"  style="width: 170px;" placeholder="请输入身份证号码:" value="${sParams['LIKE_papersNumber']}" id="degreeSearch" name="search_LIKE_papersNumber" />
							</td>
						<td class="btn-group"> <a id="advanced-search-btn" class="btnSearch" >高级搜索</a></td>
						<td class="btn-group"> <a id="advanced-search-btn-cancel" class="btnSearch" >取消</a></td>
					</tr>
				</table>
			</form>
		</div>
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
        
        <%-- <form id="form2" name="form2" action="${ctx}/complat/outsideuserList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>用户账号:</label>
                <input type="text" class="" name="search_LIKE_loginName" value="${sParams['LIKE_loginName']}"/>
            </li>
            <li>
            	<label>用户姓名:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>          
            <li class="advanced-search-btn">搜索</li>
        </ul>
        </form> --%>
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
	                	<td style="text-align: center;">
	                    	<div title="${outsideUser.name}" class="word_break">${outsideUser.name}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${outsideUser.loginName}" class="word_break">${outsideUser.loginName}</div>
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
	                    			<c:if test="${outsideUser.enable == '0'}"><font color="red">禁用</font></c:if>
	                           		<c:if test="${outsideUser.enable == '1'}">开启</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.authState == '0'}"><font color="red">未审核</font></c:if>
	                           		<c:if test="${outsideUser.authState == '1'}"><font color="blue">审核通过</font></c:if>
	                           		<c:if test="${outsideUser.authState == '2'}"><font color="green">审核未通过</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.isAuth == '0'}">
		                    			<input type="button" style="border: none;color: white;background: green;" value="   认   证   ">
		                    		</c:if>
		                    		<c:if test="${outsideUser.isAuth == '1'}">
		                    			已认证
		                    		</c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${outsideUser.createTime}" class="word_break">${outsideUser.createTime}</div>
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
