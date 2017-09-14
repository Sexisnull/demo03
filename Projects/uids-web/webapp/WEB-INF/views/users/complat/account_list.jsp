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
				<a >政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				政府用户列表
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/complatList" method="get">
			<table class="">
				<tr>
					<%-- <th>菜单名称：</th>
					<td width="20%">
						<input type="text" id="menuNameSerach" placeholder="菜单名称" name="search_LIKE_menuName" class="input" value="${sParams['LIKE_menuName']}"/>
					</td> --%>
					<th style="padding-left: 300px">用户姓名：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="用户姓名" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
						</td>
					<th>用户登录名：</th>
						 <td>
							<input type="text" style="width: 170px;" placeholder="用户登录名" value="${sParams['LIKE_loginname']}" id="loginnameSearch" name="search_LIKE_loginname" />
						</td> 
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
					
					
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
            <!-- 操作按钮开始 -->	 
             <gsww:opTag menuId="11" tabIndex="1" operatorType="1"></gsww:opTag>
             <!-- 操作按钮结束 -->
           </div> 
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/complat/complatList">
        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>姓名:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>登录名:</label>
                <input type="text" class="" name="search_LIKE_loginname" value="${sParams['LIKE_loginname']}"/>
            </li><!--  
            <li>
            	<label>企业或机构名称:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>身份证号码:</label>
                <input type="text" class="" name="search_LIKE_cardNumber" value="${sParams['LIKE_cardNumber']}"/>
            </li>          
            --><li class="advanced-search-btn">搜索</li>
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
                    <th width="10%" style="text-align: center;">登录全名</th>
                    <th width="10%" style="text-align: center;">手机号码</th>
                    <th width="10%" style="text-align: center;">邮箱</th>
                    <th width="5%" style="text-align: center;">账号开启</th>                                                     
                    <!--<th width="15%" class="alignL" style="text-align: center;">用户职务</th>-->
                    <th width="15%" class="alignL" style="text-align: center;">注册时间</th>    
                    <!--<th width="5%" style="text-align: center;">办公电话</th> -->
                    <th width="30%" style="text-align: center;">操作</th> 
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatUser" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatUser.iid}" value="${complatUser.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.name}" class="word_break">${complatUser.name}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.loginname}" class="word_break">${complatUser.loginname}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.loginallname}" class="word_break">${complatUser.loginallname}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.mobile}" class="word_break">${complatUser.mobile}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.email}" class="word_break">${complatUser.email}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatUser.enable == '0'}"><font color="red">禁用</font></c:if>
	                           		<c:if test="${complatUser.enable == '1'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    
	                    
	                     <td style="text-align: center;">
	                    	<div title="${complatUser.createtime}" class="word_break">${complatUser.createtime}</div>
	                    </td>	                    	                   
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="11" tabIndex="1" operatorType="2"></gsww:opTag>
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
