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
		alert(loginNameSearch);
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
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/corporationList" method="get">
			<table class="">
				<tr>
					<th style="padding-left: 300px">用户登录名：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="用户登录名" value="${sParams['LIKE_loginName']}" id="loginNameSearch" name="search_LIKE_loginName" />
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
             <gsww:opTag menuId="402880e85e5c3efe015e5c43d4da0001" tabIndex="1" operatorType="1"></gsww:opTag>
           </div> 
            
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/complat/corporationList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>姓名:</label>
                <input type="text" class="" name="search_LIKE_realName" value="${sParams['LIKE_realName']}"/>
            </li>
            <li>
            	<label>登录名:</label>
                <input type="text" class="" name="search_LIKE_loginName" value="${sParams['LIKE_loginName']}"/>
            </li>  
            <li>
            	<label>企业或机构名称:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>身份证号码:</label>
                <input type="text" class="" name="search_LIKE_cardNumber" value="${sParams['LIKE_cardNumber']}"/>
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
                    <th width="10%" style="text-align: center;">姓名</th>
                    <th width="10%" style="text-align: center;">登录名</th>
                    <th width="10%" style="text-align: center;">法人类型</th>
                    <th width="10%" style="text-align: center;">企业（机构）名称</th>
                    <th width="10%" class="alignL" style="text-align: center;">手机号码</th>
                    <th width="5%" class="alignL" style="text-align: center;">账号开启</th>
                    <th width="5%" style="text-align: center;">审核状态</th>
                    <th width="5%" style="text-align: center;">实名认证</th>
                    <th width="15%" style="text-align: center;">注册时间</th>
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