<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<%-- <script type="text/javascript" src="${ctx}/res/skin/default/plugin/z-tree/js/jquery.ztree.core-3.5.min.js"></script> --%>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<style type="text/css">
.ztree {margin-top: 10px;border: 1px solid #cfd9db; display:none;background: #f0f6e4;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
<script type="text/javascript">
$().ready(function() {

//表单校验
var userNameInput=$("#userName").val();
 $("#editForm").validate({
    rules: {
    	resName: {
	    required: true,
	    userName:true,
	    cnRangelength: [0,32]
	   },
	   remark: {
	    required: true,
	    charNo:true,
	    maxlength: 32,
	    uniqueRemark:true
	   },
	   callingType: {
	    required: true
	   },
	   resUrl:{
	   required: true,
	   maxlength: 32
	   },
	   isVerification:{
	   required: true
	   }
	  },submitHandler:function(form){
            var callingType=$("#callingType").val(); 
            var isVerification=$("#isVerification").val();
            if(callingType==''){
            	$.validator.errorShow($("#callingType"),'请选择角色');
            	return false;
            }else if(isVerification=='0'){
            	$.validator.errorShow($("#isVerification"),'请选择机构');
            	return false;
            }else{
				 form.submit();
			}
        } 
    });
});


$(function(){
	$("#appChoose").hide();
});

    
function selectIsVerification(value){
	if(value == '2'){
		$("#appChoose").css("display",'none');
	}
	if(value == '1'){
		$("#appChoose").css("display",'');
	}
	if(value == '0'){
		$("#appChoose").css("display",'none');
	}
	if(value = null){
		$("#appChoose").css("display",'none');
	}
}

//光标位置插入
function insertAtCursor(fieldName, myValue) { 
	var myField=document.getElementById(fieldName);
	//IE support
	if (document.selection) {
		myField.focus();
		sel = document.selection.createRange();
		sel.text = myValue;
		sel.select();
	}
	//MOZILLA/NETSCAPE support
	else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		// save scrollTop before insert
		var restoreTop = myField.scrollTop;
		myField.value = myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length);
		if (restoreTop > 0) {
		myField.scrollTop = restoreTop;
		}
		myField.focus();
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	} else {
	    myField.value += myValue;
	    myField.focus();
  	}
}






$(function(){
//点击页面其它部分收缩选择角色下拉框
	$('body').on('click',function(event){
		if (!$(event.target).is($('.user-role>ul'))
			&& !$(event.target).is($('#areaRole'))
			&& !$(event.target).is($('.user-role').parent())
			&& !$(event.target).is($('.user-role>ul li'))
			&& !$(event.target).is($('.user-role>ul li input'))
			&& !$(event.target).is($('.user-role>ul li span'))
			&& !$(event.target).is($('.user-role .form-selectinput i'))
			&& !$(event.target).is($('.user-role'))
			&& !$(event.target).is($('.user-role .form-selectinput'))) {
				$('.user-role>ul').slideUp(200);
			}
	});
	 //角色下拉框点击效果
	$(".ulRoleList li label").on("click", function() {
					var str = '';
					var val = '';
					$(".ulRoleList li input:checkbox").each(function(index, element) {
						if ($(this).attr('checked') == 'checked') {
							if (str) {
								str += ',' + $(this).attr('id');
								val += ',' + $(this).attr('roleName');
							} else {
								str = $(this).attr('id');
								val = $(this).attr('roleName');
							}
						}
					});
		
					$("#userRole").val(str);
					$("#roleNames").val(val);
				});
	//选择角色
	$(".user-role").on("click",function(event){
			$('.error-info-checkedbox').remove();
			$(this).removeClass('erro');
	});
	
	// Ajax重命名校验
	$.uniqueValidate('uniqueRemark', '${ctx}/datacall/checkRemark', ['remark','oldRemark'], '对不起，此标识已存在');
	$("#roleNames").Popup($(".ulRoleList"), { width: "auto" });
	$(".icon-date-r").click(function(){ $(this).prev("input").click(); }); 
});

</script>

</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >个性化设置</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty jisDatacall.iid}">数据调用新增</c:if><c:if test="${not empty jisDatacall.iid}">数据调用编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
	<%-- <div class="form-title"><c:if test="${empty sysAccount.userAcctId}">用户新增</c:if><c:if test="${not empty sysAccount.userAcctId}">用户编辑</c:if></div> --%>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/datacall/datacallSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${jisDatacall.iid}"/>
    	<%-- <input type="hidden" id="resName" name="resName" value="${jisDatacall.resName}"/> --%>	
    	<input type="hidden" name="setId" value="1"  />
    	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
        		<th><b class="mustbe">*</b> 请输入数据调用名称：</th>
	        	<td>
					<input type="text" placeholder="字母、数字、下划线或中文" id="resName" name="resName" value="${jisDatacall.resName}" />
				</td>
				<th><b class="mustbe">*</b> 请输入标识：</th>
				<td>
					<input type="text" placeholder="英文名或数字且唯一" id="remark" name="remark" value="${jisDatacall.remark}" />
					<input type="hidden" id="oldRemark" name="oldRemark" value="${jisDatacall.remark}"/>
				</td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 请选择调用方式：</th>
                <td>
                	<select name="callingType" onChange="calltype(this.value)" >   
						<option value="">--请选择--</option>   
						<option value="1"<c:if test="${jisDatacall.callingType==1}">selected</c:if>>IFRAME调用</option>   
						<option value="2"<c:if test="${jisDatacall.callingType==2}">selected</c:if>>RSS调用</option>
					</select>
                </td>
                <th><b class="mustbe">*</b>请输入地址：</th>
				<td>
					<textarea class="textarea" name="resUrl"  >${jisDatacall.resUrl}</textarea>
				</td>
			</tr>
			<tr>
				<th>排序方式：</th>
				<td>
				<select name="orderType" id="orderType" data-value="${jisDatacall.orderType}" >  
					<option value="">--请选择--</option>   
					<option value="1"<c:if test="${jisDatacall.orderType==1}">selected</c:if>>按时间正序</option>   
					<option value="2"<c:if test="${jisDatacall.orderType==2}">selected</c:if>>按时间倒序</option>
				</select>
				</td>
				<th>信息数量：</th>
				<td>
					<input type="text" id="infoNum" name="infoNum" maxlength="33"
                    class="input-text" value="${jisDatacall.infoNum}" placeholder="信息数量范围为1~30"/>
                </td>
			</tr>
			<tr>
				<th>时间格式：</th>
				<td>
				<select name="timeFormat" data-value="${jisDatacall.timeFormat}">
					<option value="MM-dd"<c:if test="${jisDatacall.timeFormat=='MM-dd'}">selected</c:if>>MM-dd</option>
					<option value="yyyy-MM-dd"<c:if test="${jisDatacall.timeFormat=='yyyy-MM-dd'}">selected</c:if>>yyyy-MM-dd</option>
					<option value="yyyy/MM/dd"<c:if test="${jisDatacall.timeFormat=='yyyy/MM/dd'}">selected</c:if>>yyyy/MM/dd</option>
					<option value="yyyy/MM/dd HH:mm"<c:if test="${jisDatacall.timeFormat=='yyyy/MM/dd HH:mm'}">selected</c:if>>yyyy/MM/dd HH:mm</option>
					<option value="yyyy-MM-dd HH:mm"<c:if test="${jisDatacall.timeFormat=='yyyy-MM-dd HH:mm'}">selected</c:if>>yyyy-MM-dd HH:mm</option>
					<option value="yyyy/MM/dd HH:mm:ss"<c:if test="${jisDatacall.timeFormat=='yyyy/MM/dd HH:mm:ss'}">selected</c:if>>yyyy/MM/dd HH:mm:ss</option>
					<option value="yyyy-MM-dd HH:mm:ss"<c:if test="${jisDatacall.timeFormat=='yyyy-MM-dd HH:mm:ss'}">selected</c:if>>yyyy-MM-dd HH:mm:ss</option>
                </select>
                </td>
			</tr>
			<tr>
				<th><b class="mustbe">*</b> 是否验证：</th>
				<td colspan="2">
					<c:if test="${jisDatacall.isVerification=='2'}">
						<input type="radio" name="isVerification" value="1" onclick="selectIsVerification(this.value);"/>是
						<input type="radio" name="isVerification" checked="checked" value="2" onclick="selectIsVerification(this.value);"/>否
					</c:if>
					
					<c:if test="${jisDatacall.isVerification=='1'}">
						<input type="radio" name="isVerification" checked="checked" value="1" onclick="selectIsVerification(this.value);"/>是
						<input type="radio" name="isVerification" value="2" onclick="selectIsVerification(this.value);"/>否
					</c:if>
					
					<c:if test="${jisDatacall.isVerification=='0'}">
						<input type="radio" name="isVerification" value="1" onclick="selectIsVerification(this.value);"/>是
						<input type="radio" name="isVerification" value="2" onclick="selectIsVerification(this.value);"/>否
					</c:if>
				</td>
				<td id="appChoose" colspan="2">
					<select id="appid" name="appid" style="width:120px" class="" data-value="${datacall.appid }" onChange="" >
                          <option value="0">－选择应用－</option>
                     	<c:forEach items="${app}" var="app" varStatus="i">
                     		<option value="${app.iid}" <c:if test="${app.iid==datacall.appid }">selected="selected"</c:if> >${app.name }</option>
                     	</c:forEach>
                     </select>
				</td>
			</tr>
			
			<tr id="rsshtmltr"> 
				<th><b class="mustbe">*</b>Rss信息模板</th>  
	            <td colspan="5" class="opr_open_tdright">
	                <fieldset style="border: 1px red solid;">
	                <legend style="margin-left:15px"><b><font color="blue">HTML标签</font></b></legend>
	                    <table style="margin-left: 15px;"> 
	                        <tr class="opr_tr"> 
	                        	<td class="label" width="70" align='left'>更多链接</td> 
	                        	<td colspan="2" class=""  width="160"><a href="###" onClick="insertAtCursor('content','{tpl_morelink}')">{tpl_morelink}</a></td>
	                        	<td class="label" width="70" align='left'>资源名称</td>
	                        	<td colspan="2" class=""  width="160"><a href="###" onClick="insertAtCursor('content','{tpl_resname}')">{tpl_resname}</a></td>
	                        	<td class="label" width="70" align='left'>文章标题</td>
	                        	<td colspan="2" class=""  width="160"><a href="###" onClick="insertAtCursor('content','{tpl_title}')">{tpl_title}</a></td>
	                        </tr> 
	                        <tr class="opr_tr">   
	                            <td class="label" width="70" align='left'>文章日期</td>
	                            <td colspan="2" class="" ><a href="###" onClick="insertAtCursor('content','{tpl_pubdate}')">{tpl_pubdate}</a></td>
	                            <td class="label" width="70" align='left'>文章连接</td>
	                            <td colspan="2" class="" ><a href="###" onClick="insertAtCursor('content','{tpl_link}')">{tpl_link}</a></td>
	                            <td class="label" width="70" align='left'>文章作者</td>
	                            <td colspan="2" class="" ><a href="###" onClick="insertAtCursor('content','{tpl_author}')">{tpl_author}</a></td>
	                        </tr> 
	                        <tr class="opr_tr"> 
	                           </tr> 
	                    </table>
	                </fieldset>
	
	                <fieldset style="border: 1px red solid;">
	                <legend style="margin-left:15px"><b><font color="blue">HTML模板编辑</font></b></legend>
	                <textarea style="margin-left:15px ;width:850px;height:300px" id="content" name="content" rows="20">${rsshtml}</textarea>
	                </fieldset> 
	        	</td> 
	        </tr>
			
		</table>
	        <!-- </ul> -->
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    	<input type="submit" tabindex="15" id="submit-btn" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/datacall/datacallList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
    </div>
    </form>
    <!--表单的底部区域-->
    <div class="form-footer"></div>
    <!--表单的预览或提示区域-->
    <div class="form-preview"></div>
    <!--表单的提示区域-->
    <div class="form-tip"></div>
    <!--表单的弹出层区域-->
    <div class="form-dialog"></div>
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
</div>
</div>
</body>
</html>
