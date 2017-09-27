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
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
    
<style>
.select{
font-size: 12px;
border: 1px solid #dddddd;
padding: 3px 8px;
height: 30px;
width: 167px;
}
.search-content table td input[type=text] {
    border: 1px solid #dddddd;
    padding: 3px 8px;
    height: 22px;
    line-height: 22px;
    width: 89%;
}
.dialog{
	display:none;
	position:absolute;
	left:250px;
	top:100px;
	font-size: 18px;
	width: 600px;
	height: 225px;
	background: #f9f9f9;
	padding: 10px;
	border-radius: 5px;
	border: 1px solid #999;
	z-index:101;
	
}
.dialogtop{
	height: 30px;
	position: relative;
}
.upload{
	padding-top: 37px;
	height: 105px;
	background: white;
	border: 1px solid #999;
	padding-left: 60px;
}
.btnarea{
	text-align: right;
	height: 37px;
	border: 1px solid #999;
	border-top: 0px;
	padding-top: 7px;
	padding-right: 37px;
	cursor: pointer;
}
.confirm-btn{
	display: inline-block;
	width: 60px;
	height: 30px;
	background-color: #42a2f5;
	color: white;
	text-align: center;
	line-height: 30px;
	border-radius: 2px;
	cursor: pointer;
}
.councel-btn{
	display: inline-block;
	width: 60px;
	height: 30px;
	background-color: #dfdfdf;
	text-align: center;
	line-height: 30px;
	border-radius: 2px;
	cursor: pointer;
}
.file-btn{
	display: inline-block;
	width: 105px;
	height: 30px;
	line-height: 30px;
	background: #5fa60f;
	border-radius: 2px;
	color: white;
	text-align: center;
	cursor: pointer;
	padding-left: 4px;
	padding-right: 4px;
}
.download{
	color: blue;
	text-decoration: underline;
	padding-left: 15px;
}
.fileinput{
	width: 1px;
	height: 0px;
	outline: none;
}
.closeicon{
text-decoration: none;
float:right;

}
a:hover{
text-decoration: none;
}

/**遮罩层**/

.mask{
	display:none;
   width: 100%; height: 100%; position: fixed; top: 0; 
	left: 0; right: 0; bottom: 0; background: #000; opacity: 0.3; 
	filter: alpha(opacity = 30); z-index: 100;
	}

</style>
<script type="text/javascript"> 

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
					window.location.href="${ctx}/uids/complatgroupDelete?iid="+ids.substring(0,ids.length-1);
				});
				
			}else{
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
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
	var nameSerach=$("#nameSerach").val();
	if(nameSerach==''||isChinaOrNumbOrLett(nameSerach)){
		form1.submit();
	}else{
		$.validator.errorShow($("#nameSerach"),'只能包括中英文、数字、@和下划线');
	}
}
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

//搜索树的设置
$(function(){
	var groupMenu = [{"name":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];

	$('#groupname').menu({
		tree : 'groupmenu',
		height : 200,
		init : function() {
			setting('groupmenu', onClickGroup, onDbClickGroup, groupMenu);
		}
	});
	
});
function hideGroupMenu(){
	$('#groupname_menu').css('display','none');
}
function onClickGroup(event, treeId, treeNode) {
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	hideGroupMenu();
}
function onDbClickGroup(event, treeId, treeNode) {
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled )//根节点及失效节点双击无效
		return;
	$('#groupid').val(treeNode.id);
	$('#groupname').val(treeNode.name);
	$('#groupname_menu').fadeOut(50);
}

/**
 *	初始化树
 */
function setting(treeName, onClickFunction, onDblClickFunction, rootNode) {
	var setting = {
		async : {
			enable : true,
			url : '../login/getGroup',
			autoParam : [ "id=groupId", "isDisabled" ]
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClickFunction,
			onDblClick : onDblClickFunction
		}
	};
	console.log("-----"+treeName);
	$("#" + treeName).tree(setting, rootNode);
//	$("#" + treeName).tree().refreshNode('');
}
/**
 *	机构选择节点点击前回调
 */
function beforeClick(treeId, treeNode, clickFlag) {
	if (treeNode.isDisabled)
		return false;
	return (treeNode.id != 0);
}
function resetform() {
	$('form').find(':input').not(':button,:hidden,:submit,:reset').val('');
}
	
// 显示文件名在input中
//弹出层
function importGroup(){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#alerttb").show(); 				

}
    	//导入
	function importFile(){
	$('#mask').show();
	$('#importdiv').show();
	}
	
	//关闭弹窗
	function closeWindow(){
	$('#mask').hide();
	$('#importdiv').hide();
	};
	//选择文件
	 var fileBtn = $("#fileupload");
    fileBtn.on("change", function() {
        var index = $(this).val().lastIndexOf("\\"); 
        var sFileName = $(this).val().substr((index + 1));

        $("#filename").val(sFileName);
    });
//导入文件验证
function fileUpload(){
		var picPath=document.getElementById('excelFile').value;
		if(picPath==null||picPath==""){
			alert("请选择要导入的excel文件！");
			return false ;
						}
		var type=picPath.substring(picPath.lastIndexOf(".")+1,picPath.length).toLowerCase();
		if(type=="xls"||type=="xlsx"){
			$("#form3").submit();							
		}else{
			alert("请上传正确的EXCEL表格文档");
			document.getElementById("excelFile").value="";
			return false;
		}
			     
}	
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
				$.dialog.confirm('请您至少选择一条数据',function(){
					return null;
				});
			}
	}

</script>

</head>
<body>
<div class="mask" id='mask'></div>
<div class="list-warper" >

<!-- 导入数据时的弹出层 -->
   <div  class="dialog" id="importdiv" style="display:none;">
	<div class="dialogtop">
	<span>机构导入</span>
	<i class="closeicon"></i>
	<a class="ui_close closeicon" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;" onclick="closeWindow();">×</a>
	</div>
	<form id="form3" name="form3" action="${ctx}/uids/complatgroupImport" method="post" enctype="multipart/form-data">
		<div class="upload">
			<label for="fileupload">
			<input type="file" id="excelFile" class="required" name="excelFile">
			</label>
			<a class="download" href="${ctx}/uploadFile/complat/机构列表.xlsx">下载参考样例</a>
		</div>
		<div class="btnarea">
			<span class="confirm-btn" onclick="fileUpload()">确认</span>
			<span class="councel-btn" onclick="closeWindow();">取消</span>
		</div>
	</form>
</div>


	<!--列表的面包屑区域-->
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
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/uids/complatgroupList" method="get">
			<table class="advanced-content" width="100%">
			    <tr>
					<th style="padding-left: 5px">机构名称:</th>
					<td width="15%">
						<input type="text" class="input" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
					</td>
					<th style="padding-left: 5px">机构编码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_codeid" value="${sParams['LIKE_codeid']}"/>
					</td>
					<th style="padding-left: 5px">组织机构代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_orgcode" value="${sParams['LIKE_orgcode']}"/>
					</td>
					<th style="padding-left: 5px">区域代码:</th>
					<td width="15%">
              			<input type="text" class="input" name="search_LIKE_areacode" value="${sParams['LIKE_areacode']}"/>
					</td>
				</tr>
				<tr height="10px"></tr>
				<tr>
				    <th style="padding-left: 5px">上级机构:</th>
					<td width="15%">
              			<input name="groupname" id="groupname" value="${groupName}" type="text" style="cursor: pointer;" placeholder="上级机构"/>
					    <input type="hidden" id="groupid" name="search_EQ_pid">
					</td>
				    <th style="padding-left: 5px">节点类型:</th>
					<td width="15%">
	                <select id="search_EQ_nodetype"  name="search_EQ_nodetype" class="select" >
	                	<option value="">---请选择节点类型---</option>
						<c:forEach var="nodetype" items="${nodetypeMap}">
							<option value="${nodetype.key}"
							<c:if test="${sParams['EQ_nodetype']==nodetype.key}">selected </c:if>>${nodetype.value}</option>
						</c:forEach>
	                </select>
					</td>
					<th style="padding-left: 5px">区域类型:</th>
					<td width="15%">
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
				
					    <a class="btnSearch" id="advanced-search-btn">搜索</a>
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
        <div class="list-topBar  advanced-search">
           	<div class="list-toolbar">
            <!-- 操作按钮开始	 -->
              	 <!--<gsww:opTag menuId="8a92012d5e7de06a015e7de18b3a0001" tabIndex="1" operatorType="1"></gsww:opTag>-->
              	 <ul class="list-Topbtn">
						<li class="add"><a title="新增"
							onclick="add('uids/complatgroupEdit');">新增</a></li>
						<li class="del"><a title="删除"
							onclick="deleteData('uids/complatgroupDelete','iid');">删除</a></li>
						<li class="query" id="importFile" onclick="importFile()"><a title="导入">导入</a></li>
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
                    <th width="15%" style="text-align: center;">机构名称</th>
                    <th width="15%" style="text-align: center;">机构编码</th>
                    <th width="15%" style="text-align: center;">机构后缀</th>
                    <th width="10%" style="text-align: center;">节点类型</th>
                    <th width="25%" style="text-align: center;">机构全名</th>
                    <th width="10%" style="text-align: center;">上级机构</th>
                    <th width="10%" style="text-align: center;">操作</th>
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
	                    	<div title="${complatGroup.name}" class="word_break">${complatGroup.name}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatGroup.codeid}" class="word_break">${complatGroup.codeid}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatGroup.suffix}" class="word_break">${complatGroup.suffix}</div>
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
	                	<td class="alignL" style="text-align: center;">
	                    	${complatGroup.groupallname}
	                    </td>
	                	<td class="alignL" style="text-align: center;">
	                    	${complatGroup.parentName}
	                    </td>
	        
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a92012d5e7de06a015e7de18b3a0001" tabIndex="1" operatorType="2"></gsww:opTag>
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