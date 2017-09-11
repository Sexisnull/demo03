<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	$(function(){
		//高级搜索按钮点击事件
		$("#advanced-btn").on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$("#advancedSearch").click(function(){
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
				<a >用户管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
					法人用户
			</li>
    	</ol>
    </div>
    
	<!--列表内容区域-->
	<div class="list">
		<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
        <div class="list-topBar">
        	<div class="search-content">
				<form id="form1" name="pageForm" action="${ctx}/complat/corporationList" method="get">
					<table class="">
						<tr>
							<th>用户登录名：</th>
								<td width="20%">
									<input type="text" placeholder="用户登录名" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" class="input"/>
								</td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
							<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm2()" id="advanced-btn">高级搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
        	 <div class="list-toolbar">
             <!-- 操作按钮开始  测试：8a92e1025e5672aa015e56798c830001  本地:402880e85e5c3efe015e5c43d4da0001-->	 
	             <gsww:opTag menuId="8a92e1025e5672aa015e56798c830001" tabIndex="1" operatorType="1"></gsww:opTag>
            </div> 
        </div>
        <div class="list">
        	<input type="hidden"  name="orderField" value="${orderField}"/> 
			<input type="hidden"  name="orderSort" value="${orderSort}"/>
			<div class="list-topBar">
				<div class="search-content">
					<form id="form2" name="form2" action="${ctx}/complat/corporationList">
		        		
		        			<table class="advanced-content" style="display: none">
		        				<tr>
									<th>姓名:</th>
									<td width="20%">
										<input type="text" class="input" name="search_LIKE_realName" value="${sParams['LIKE_realName']}"/>
									</td>
									<th>登录名:</th>
									<td width="20%">
		                				<input type="text" class="input" name="search_LIKE_loginName" value="${sParams['LIKE_loginName']}"/>
									</td>
								</tr>
								<tr>
									<th>企业或机构名称:</th>
									<td width="20%">
										<input type="text" class="input" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
									</td>
									<th>身份证号码:</th>
									<td width="20%">
										<input type="text" class="input" name="search_LIKE_cardNumber" value="${sParams['LIKE_cardNumber']}"/>
									</td>
									<td class="btn-group"> <a class="btnSearch" id="advancedSearch">搜索</a></td>
								</tr>
		        			</table>  

	       			</form>
				</div>
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
			                		<c:if test="${complatCorporation.enable=='0'}">禁用</c:if>
			                		<c:if test="${complatCorporation.enable=='1'}">开启</c:if>
			                	</div>
			                </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatCorporation.authState == 0}"><font color="red">未审核</font></c:if>
	                           		<c:if test="${complatCorporation.authState == 1}"><font color="blue">已通过</font></c:if>
	                           		<c:if test="${complatCorporation.authState == 2}"><font color="green">未通过</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td class="alignL" style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
		                    		<c:if test="${complatCorporation.isAuth == '0'}">
		                    			<input type="button" style="border: none;color: white;background: green;" value="   认   证   ">
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
