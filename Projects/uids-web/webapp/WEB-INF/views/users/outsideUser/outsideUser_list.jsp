<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>

<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>

<script type="text/javascript"> 
	
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
			<table class="">
				<tr>
					<%-- <th>菜单名称：</th>
					<td width="20%">
						<input type="text" id="menuNameSerach" placeholder="菜单名称" name="search_LIKE_menuName" class="input" value="${sParams['LIKE_menuName']}"/>
					</td> --%>
					<th style="padding-left: 300px">请输入个人用户登录名：</th>
						<td width="20%">
							<input type="text"  style="width: 170px;" placeholder="请输入个人用户登录名" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
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
             <gsww:opTag menuId="11" tabIndex="1" operatorType="1"></gsww:opTag>
           </div> 
            
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/complat/outsideuserList">
        
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
                    <th width="10%" style="text-align: center;">手机号码</th>
                    <th width="10%" style="text-align: center;">邮箱</th>
                    <th width="5%" style="text-align: center;">账号开启</th>
                    <th width="5%" style="text-align: center;">审核状态</th>
                    <th width="10%" class="alignL" style="text-align: center;">实名认证</th>
                    <th width="15%" class="alignL" style="text-align: center;">注册时间</th>
                    <th width="30%" style="text-align: center;">操作</th>
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
	                           		<c:if test="${outsideUser.enable == '1'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.authState == '0'}"><font color="red">未审核</font></c:if>
	                           		<c:if test="${outsideUser.authState == '1'}"><font color="#32CD32">审核通过</font></c:if>
	                           		<c:if test="${outsideUser.authState == '2'}"><font color="#32CD32">审核未通过</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${outsideUser.isAuth == '0'}"><font color="red">未认证</font></c:if>
	                           		<c:if test="${outsideUser.isAuth == '1'}"><font color="#32CD32">已认证</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${outsideUser.createTime}" class="word_break">${outsideUser.createTime}</div>
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
