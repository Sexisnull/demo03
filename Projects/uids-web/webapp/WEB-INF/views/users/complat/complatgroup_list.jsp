<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
	<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/plugin/uploadify/css/uploadify.css">  
    
<style>
.select{
font-size: 12px;
border: 1px solid #dddddd;
padding: 3px 8px;
height: 30px;
width: 179px;
}
</style>
<script type="text/javascript"> 
//表单校验
function checkSubmitForm(){
        valueOfIsSearch();
		var nameSearch = $("#nameSearch").val();
		var codeidSearch = $("#codeidSearch").val(); 
		var orgcodeSearch = $("#orgcodeSearch").val();
		var areacodeSearch = $("#areacodeSearch").val();
		if(nameSearch ==  '' || isNumbOrLett1(nameSearch)){
			if(codeidSearch ==  '' || isNumbOrLett2(codeidSearch)) {
				if(orgcodeSearch ==  '' || isNumbOrLett3(orgcodeSearch)) {
				    if(areacodeSearch == '' || isNumbOrLett4(areacodeSearch)) {
						form1.submit();
					} else{
						$.validator.errorShow($("#areacodeSearch"),'只能输入不超过12位的数字');
					}
				} else{
					$.validator.errorShow($("#orgcodeSearch"),'只能输入不超过9位的数字和字母');
				}
			} else{
				$.validator.errorShow($("#codeidSearch"),'只能输入不超过255位的数字');
			}
		} else{
			$.validator.errorShow($("#nameSearch"),'只能输入字母、数字、下划线、中文，并且不能超过32位');
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
		    if(s.length<32){
				return true;
			}
		}else{
			return false;
		}
	}
	
	function isNumbOrLett2( s ){
		var regu = /^\d{0,255}$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function isNumbOrLett3( s ){
		var regu = /^([a-zA-Z0-9]{0,9})$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function isNumbOrLett4( s ){
		var regu = /^\d{0,12}$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
	}
	
	function valueOfIsSearch(){
	    var check = "1";
	    $('#isSearch').val(check);
	}
	  
	$(function(){
		//高级搜索按钮点击事件
		$('#advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$("#advanced-search-btn").click(function(){
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

//机构导出
function exportGroup() {
		var paraTypeId=$(".iid").val();
		if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
				var ids = "";
				$('.list-table tbody input[type=checkbox]').each(function(i, o) {
					if($(o).attr('checked')) {
						ids += $(o).val() + ",";
					}
				});
				window.location.href="${ctx}/uids/complatgroupExport?sId="+ids.substring(0,ids.length-1);
			}else{
				$.dialog.alert('请您至少选择一条数据',function(){
					return null;
				});
			}
	}
//机构导入
function importGroup(){
	var api = $.dialog({
		title : '机构管理-机构导出',
		width : 450,
		height: 125,
		max : false,
		min : false,
		lock : true,
		padding : '40px 20px',
		content : 'url:${ctx}/uids/showImport',
		fixed : true,
		drag : false,
		resize : false
	});
}


//新增
function toAdd(){
	parent.location = "complatgroupEdit";
}

//删除
function deleteData() {
	var paraTypeId=$(".iid").val();
	if($(".check_btn:checked").length!=0&&$('.list-table tbody input:checkbox:checked').length!=0){
		$.dialog.confirm('您确认要删除吗？',function(){
			var ids = "";
			$('.list-table tbody input[type=checkbox]').each(function(i, o) {
				if($(o).attr('checked')) {
					ids += $(o).val() + ",";
						}
			});
			parent.location = "${ctx}/uids/complatgroupDelete?iid="+ids.substring(0,ids.length-1);
		});
	}else{
		$.dialog.alert('请您至少选择一条数据',function(){
			return null;
		});
	}
}
//编辑
function toEdit(iids){
	if(iids==null || iids==""){
		parent.location = "complatgroupEdit";
	}else{
		parent.location = "complatgroupEdit?iid="+iids;
	}
	
}
</script>

</head>
<body>
<div class="mask" id='mask'></div>
<div class="list-warper" >
	<!--列表的面包屑区域
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >机构管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				机构管理
			</li>
    	</ol>
    </div>-->
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/uids/complatgroupList" method="get">
			<table class="advanced-content" width="100%">
			    <tr>
					<th style="padding-left: 5px">机构名称:</th>
					<td width="20%" height="30">
						<input id="nameSearch" type="text" class="input" name="search_LIKE_name" value="${sParams['LIKE_name']}"  placeholder="机构名称" style="width: 161px;"/>
						<input type="hidden" id="isSearch" name="isSearch" value="${isSearch}">
						<input type="hidden" id="orgId" name="orgId" value="${orgId}">
						<input type="hidden" id="deptCodeid" name="deptCodeid" value="${deptCodeid}">
					</td>
					<th style="padding-left: 5px">机构编码:</th>
					<td width="20%">
              			<input id="codeidSearch" type="text" class="input" name="search_LIKE_codeid" value="${sParams['LIKE_codeid']}"  placeholder="机构编码" style="width: 161px;"/>
					</td>
					<th style="padding-left: 5px">组织机构代码:</th>
					<td width="20%">
              			<input id="orgcodeSearch" type="text" class="input" name="search_LIKE_orgcode" value="${sParams['LIKE_orgcode']}"  placeholder="组织机构代码" style="width: 161px;"/>
					</td>
				</tr>
				<tr height="10px"></tr>
				<tr>
				    <th style="padding-left: 5px">区域代码:</th>
					<td width="20%" height="30">
              			<input id="areacodeSearch" type="text" class="input" name="search_LIKE_areacode" value="${sParams['LIKE_areacode']}" placeholder="区域代码" style="width: 161px;"/>
					</td>
				    <th style="padding-left: 5px">节点类型:</th>
					<td width="20%">
	                <select id="search_EQ_nodetype"  name="search_EQ_nodetype" class="select" >
	                	<option value="">---请选择节点类型---</option>
						<c:forEach var="nodetype" items="${nodetypeMap}">
							<option value="${nodetype.key}"
							<c:if test="${sParams['EQ_nodetype']==nodetype.key}">selected </c:if>>${nodetype.value}</option>
						</c:forEach>
	                </select>
					</td>
					<th style="padding-left: 5px">区域类型:</th>
					<td width="20%">
						<select id="search_EQ_areatype"  name="search_EQ_areatype" class="select" >
	                		<option value="">---请选择区域类型---</option>
							<c:forEach var="areatype" items="${areatypeMap}">
								<option value="${areatype.key}"
								<c:if test="${sParams['EQ_areatype']==areatype.key}">selected </c:if>>${areatype.value}</option>
							</c:forEach>
	                	</select>	
					</td>
					<th style="padding-left: 5px"></th>
					<td class="btn-group" style="text-align:right;">
				
					    <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a>
					</td>
				</tr>
			</table>
		</form>
		<!-- 高级探索表单 -->
		<!--<form id="form2" name="form2" action="${ctx}/uids/complatgroupList">
      			<table class="advanced-content" style="display: none">
      			  <tr>
					<th>机构名称:</th>
					<td width="15%">
						<input type="text" class="input" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
					</td>
					<th>机构编码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_codeid" value="${sParams['LIKE_codeid']}"/>
					</td>
					<th>组织机构代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_orgcode" value="${sParams['LIKE_orgcode']}"/>
					</td>
					<th>区域代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_areacode" value="${sParams['LIKE_areacode']}"/>
					</td>
					<td class="btn-group"> <a class="btnSearch" id="advanced-search-btn">搜索</a></td>
				</tr>
				<tr>
				    <th>上级机构：</th>
					<td width="15%">
              			<input name="groupname2" id="groupname2" type="text" style="cursor: pointer;width: 193.72px"/>
						<input type="hidden" id="groupid2" name="search_EQ_pid">
					</td>
				    <th>节点类型:</th>
					<td width="15%">
	                <select id="search_EQ_nodetype"  name="search_EQ_nodetype"  style="width:198px;height:32px;font-size: 14px;">
	                	<option value="">---请选择---</option>
						<c:forEach var="nodetype" items="${nodetypeMap}">
							<option value="${nodetype.key}">${nodetype.value}</option>
						</c:forEach>
	                </select>
					</td>
					<th>区域类型:</th>
					<td width="15%">
						<select id="search_EQ_areatype"  name="search_EQ_areatype" style="width:198px;height:32px;font-size: 14px;">
	                		<option value="">---请选择---</option>
							<c:forEach var="areatype" items="${areatypeMap}">
								<option value="${areatype.key}">${areatype.value}</option>
							</c:forEach>
	                	</select>	
					</td>
				</tr>
      		</table>  
     	</form>-->
	</div>
    
    
	<!--列表内容区域-->
	<div class="list">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
	<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
	<input type="hidden" id="orgIdSave" name="orgIdSave" value="${orgIdSave}"/>
        <div class="list-topBar  advanced-search">
           	<div class="list-toolbar">
            <!-- 操作按钮开始	 -->
              	 <!--<gsww:opTag menuId="8a92012d5e7de06a015e7de18b3a0001" tabIndex="1" operatorType="1"></gsww:opTag>-->
              	 <ul class="list-Topbtn">
						<li class="add"><a title="新增"
							onclick="toAdd();">新增</a></li>
						<li class="del"><a title="删除"
							onclick="deleteData('uids/complatgroupDelete','iid');">删除</a></li>
						<li class="query" id="importGroup" onclick="importGroup()"><a title="导入">导入</a></li>
						<li class="exportData"><a title="导出"
							onclick="exportGroup()">导出</a></li>
				 </ul>
            <!-- 操作按钮结束 -->
           	</div> 
        </div>
        
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
                    <th width="18%" style="text-align: center;">机构名称</th>
                    <th width="18%" style="text-align: center;">机构编码</th>
                    <th width="18%" style="text-align: center;">机构后缀</th>
                    <th width="5%"  style="text-align: center;">节点类型</th>
                    <th width="26%" style="text-align: center;">机构全名</th>
                    <th width="10%" style="text-align: center;">上级机构</th>
                    <th width="5%"  style="text-align: center;">操作</th>
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatGroup" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatGroup.iid}" value="${complatGroup.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.name}" class="word_break">
		                    	<c:if test="${fn:length(complatGroup.name)>=7}">
									  ${fn:substring(complatGroup.name,0,7)}...
								</c:if>
								<c:if test="${fn:length(complatGroup.name)<7}">
									  ${complatGroup.name}&nbsp;
								</c:if> 
	                    	</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.codeid}" class="word_break">
		                    	<c:if test="${fn:length(complatGroup.codeid)>=15}">
									  ${fn:substring(complatGroup.codeid,0,15)}...
								</c:if>
								<c:if test="${fn:length(complatGroup.codeid)<15}">
									  ${complatGroup.codeid}&nbsp;
								</c:if> 
	                    	</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatGroup.suffix}" class="word_break">
	                    	    <c:if test="${fn:length(complatGroup.suffix)>=20}">
									  ${fn:substring(complatGroup.suffix,0,20)}...
								</c:if>
								<c:if test="${fn:length(complatGroup.suffix)<20}">
									  ${complatGroup.suffix}&nbsp;
								</c:if>
	                    	</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatGroup.nodetype == 1}">区域</c:if>
	                           		<c:if test="${complatGroup.nodetype == 2}">单位</c:if>
	                           		<c:if test="${complatGroup.nodetype == 3}">部门或处室</c:if>
	                    		</div>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.groupallname}" class="word_break">
	                    	    <c:if test="${fn:length(complatGroup.groupallname)>=15}">
									  ${fn:substring(complatGroup.groupallname,0,15)}...
								</c:if>
								<c:if test="${fn:length(complatGroup.groupallname)<15}">
									  ${complatGroup.groupallname}&nbsp;
								</c:if>
	                    	</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.parentName}" class="word_break">
	                    	    <c:if test="${fn:length(complatGroup.parentName)>=5}">
									  ${fn:substring(complatGroup.parentName,0,5)}...
								</c:if>
								<c:if test="${fn:length(complatGroup.parentName)<5}">
									  ${complatGroup.parentName}&nbsp;
								</c:if>
	                    	</div>
	                    </td>
	        
	                	<td class="listOper" >
	                        <ul>
	                        	<li class="blue" onclick="toEdit(${complatGroup.iid});"><i></i>
	                        	<a>编辑</a></li>
	                        </ul>
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