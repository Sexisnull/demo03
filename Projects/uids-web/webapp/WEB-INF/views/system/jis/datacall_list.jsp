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
	function checkSubmitForm(){
		var resNameSearch = $("#resNameSearch").val();
		if(resNameSearch ==  '' || isNumbOrLett(resNameSearch)){
		}else{
			$.validator.errorShow($("#resNameSearch"),'只能包括数字、字母、下划线或中文');
			return;
		}
		var remarkSearch = $("#remarkSearch").val();
		if(remarkSearch ==  '' || isNumbOrLett(remarkSearch)){
		}else{
			$.validator.errorShow($("#remarkSearch"),'只能包括数字和字母');
			return;
		}
		form1.submit();
	}
	/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett( s ){//判断是否是字母、数字组成
		var regu = /^(\w|[\u4E00-\u9FA5])*$/;/* /^([a-zA-Z0-9]+)$/ */
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
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li class="active">
				个性化设置
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
					<th style="padding-left: 300px">数据名称：</th>
						<td width="20%">
							<input type="text" maxlength="30" style="width: 170px;" placeholder="数据名称" value="${sParams['LIKE_resName']}" id="resNameSearch" name="search_LIKE_resName" />
						</td>
					<th>数据标识：</th>
						 <td>
							<input type="text" maxlength="30" style="width: 170px;" placeholder="数据标识" value="${sParams['LIKE_remark']}" id="remarkSearch" name="search_LIKE_remark" />
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
                  ID        
                    </th>
                    <th width="20%" style="text-align: center;">数据名称</th>
                    <th width="20%" style="text-align: center;">数据标识</th>
                    <th width="25%" style="text-align: center;">地址</th>
                    <th width="15%" class="alignL" style="text-align: center;">调用方式</th>
                    <th width="10%" class="alignL" style="text-align: center;">验证</th>
                    <th width="10%" style="text-align: center;">操作</th>
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
	                	<td style="text-align:center;" title="${datacall.resName}" class="word_break">
	                		<c:if test="${fn:length(datacall.resName)>=20}">
						    	${fn:substring(datacall.resName,0,20)}...
						    </c:if>
					    	<c:if test="${fn:length(datacall.resName)<20}">
						    	${datacall.resName}
						    </c:if>
	                    </td>
	                	<td style="text-align:center;" title="${datacall.remark}" class="word_break">
	                		<c:if test="${fn:length(datacall.remark)>=20}">
						    	${fn:substring(datacall.remark,0,20)}...
						    </c:if>
					    	<c:if test="${fn:length(datacall.remark)<20}">
						    	${datacall.remark}
						    </c:if>
	                    </td>
	                    <td style="text-align:center;" title="${datacall.resUrl}" class="word_break">
	                    	<c:if test="${fn:length(datacall.resUrl)>=20}">
						    	${fn:substring(datacall.resUrl,0,20)}...
						    </c:if>
					    	<c:if test="${fn:length(datacall.resUrl)<20}">
						    	${datacall.resUrl}
						    </c:if>
	                    </td>
	                    <td style="text-align: center;">
	                    	<c:if test="${datacall.callingType==1}">IFRAME调用</c:if>
							<c:if test="${datacall.callingType==2}">RSS调用</c:if>
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	<c:if test="${datacall.isVerification==2}">否</c:if>
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
