<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<style type="text/css">
.ztree {margin-top: 10px;border: 1px solid #cfd9db; display:none;background: #f0f6e4;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<%-- <link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" /> --%>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
<script type="text/javascript">
function generateKey(){
	$('#encryptKey').val(randomChar(12));
}
function randomChar(num) 	
{
		var jsSeed="0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		var jsTmp="";
		if(num=='' || num<=0){
			num=16;
		}
		for(var i=0;i<num;i++)  {
		jsTmp+=jsSeed.charAt(Math.ceil(Math.random()*1000000)%jsSeed.length);
		}
		return jsTmp;
}
function selpic(){
	var page="pic_show.do";
	var pic = document.getElementById("icon").src;
	var picsrc; 
	openDialog('app/pic_show.do', 600, 350, {
		title : '图标'
	});
}
function cleariocn(){
	$("#icon").attr("src","${contextPath}${app.icon}");
}
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

$().ready(function() {

//表单校验
var userNameInput=$("#userName").val();
 $("#editForm").validate({
    rules: {
    	resName: {
	    required: true,
	    /* cnRangelength: [0,32],
	    stringCheck:userNameInput */
	   },
	   remark: {
	    required: true,
	    uniqueRemark:true
	   },
	   callingType: {
	    required: true,
	   /*  chrnum:true,
	    uniqueLoginAccount:true,
	    maxlength: 32 */
	   },
	   resUrl:{
	   required: true
	   },
	   isVerification:{
	   required: true
	   },
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
	
	// Ajax标识校验
	$.uniqueValidate('uniqueRemark', '${ctx}/datacall/checkRemark', ['remark','oldRemark'], '对不起，此标识已存在');
	/* $("#roleNames").Popup($(".ulRoleList"), { width: "auto" }); */
	$(".icon-date-r").click(function(){ $(this).prev("input").click(); }); 
	
	
	function fastwrite() { 
		var transtype = $('input[name="transtype"]:checked').val(); 
		openDialog('app/fastwrite_show.do?transtype='+transtype, 500, 300, {
			title : '便捷录入'
		});
	}
	function checknet(){
		var url = $('#appUrl').val();
		if(url == ""){
			alert("接口地址不能为空！");
			return false;
		}
		var reg = /[\u0391-\uFFE5]+$/;
		if ( reg.test(url)) {
			alert("接口地址中不能含有汉字！");
			return false;
	    }
		$.ajax({
	         url: "checknet.do",
	         data: "url=" + url, 
	         type: 'POST',
	         success: function(msg){
			   if(msg == 200) {
				     alert("网络正常！");
				     return true;
			   } else if(msg == 0) {
				     alert("网络不通！");
				     return false;
			   } else{
				   	 alert( "测试异常("+msg+")！");  
				     return false; 
			   }
			}
	 	});	
	}
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
				<a >应用管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				<a class="last-position"><c:if test="${empty jisApplication.iid}">应用列表新增</c:if><c:if test="${not empty jisApplication.iid}">应用列表编辑</c:if></a>
			</li>
   		</ol>
    </div>
	<!--表单的标题区域--> 
	<%-- <div class="form-title"><c:if test="${empty sysAccount.userAcctId}">用户新增</c:if><c:if test="${not empty sysAccount.userAcctId}">用户编辑</c:if></div> --%>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/application/applicationSave">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${jisApplication.iid}"/>
    	<%-- <input type="hidden" id="resName" name="resName" value="${jisDatacall.resName}"/> --%>	
    	<input type="hidden" name="setId" value="1"  />
    	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table">
    		<tr>
	        	 <th style="text-align: left;"><b class="mustbe">*</b> 应用名称：</th>
	        	 <td>
					<input type="text" placeholder="请输入应用名称" id="name" name="name" value="${jisApplication.name}" />
				</td>
				<th style="text-align: center;"><b class="mustbe">*</b> 应用标识：</th>
				<td>
					<input type="text" placeholder="请输入应用标识" id="mark" name="mark" value="${jisApplication.mark}" />
					<input type="hidden" id="oldMark" name="oldMark" value="${jisApplication.mark}"/>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;"><b class="mustbe">*</b> 所属机构：</th>
				<td>
					<input id="groupname" value="${groupName}" name="groupname" type="text" style="cursor: pointer;"/> 
				</td>
				<th style="text-align:center;"><b class="mustbe">*</b> 同步用户：</th>
                <td>
                	<select name="synUser"  >   
						<option value="">--请选择--</option>   
						<option value="1"<c:if test="${jisApplication.synUser==1}">selected</c:if>>同步后台、前台用户</option>   
						<option value="2"<c:if test="${jisApplication.synUser==2}">selected</c:if>>只同步后台</option>
						<option value="3"<c:if test="${jisApplication.synUser==3}">selected</c:if>>不同步</option>
					</select>
                </td>
            </tr>
            <tr>
				<th style="text-align:center;"><b class="mustbe">*</b>数据传送方式：</th>
				<td>
					<c:if test="${jisApplication.transType=='2'}">
						<input type="radio" name="transType" value="1"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" checked="checked" value="2"/>WebService
					</c:if>
					
					<c:if test="${jisApplication.transType=='1'}">
						<input type="radio" name="transType" checked="checked" value="1"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" value="2"/>WebService
					</c:if>
					
					<c:if test="${jisApplication.transType==null}">
						<input type="radio" name="transType" value="1"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" value="2"/>WebService
					</c:if>
				</td>
				
				<th style="text-align:center;"><b class="mustbe">*</b>加密设置：</th>
				<td>
				
					<c:if test="${jisApplication.encryptType=='2'}">
						<input type="radio" name="encryptType" value="2" checked="checked"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="3"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="1"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType=='3'}">
						<input type="radio" name="encryptType" value="2"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="3" checked="checked"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="1"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType=='1'}">
						<input type="radio" name="encryptType" value="2"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="3"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="1" checked="checked"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType==null}">
						<input type="radio" name="encryptType" value="2"/>MD5
						<input type="radio" name="encryptType" value="3"/>MD5+base64
						<input type="radio" name="encryptType" value="1"/>不加密
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="text-align: left;"><b class="mustbe">*</b> 加密密钥：</th>
	        	 <td>
					<input type="text" id="encryptKey" name="encryptKey"
						maxlength="16" value="${jisApplication.encryptKey}" />
					<input type="button" name="button"
						onclick="generateKey()" value="重新生成"/>
				</td>
				
				<th style="text-align: center;"><b class="mustbe">*</b> 应用图标：</th>
					<td><input id="iconFile" type="file" class="input-text" name="iconFile" 
						input-width="200"/>
						<input id="seletpic" type="button" class="btn btn-success btn-small" 
						onclick="selpic()" value="选择图标"/>
						<input style="display:none;" type="button" class="btn btn-success btn-small" 
							onclick="cleariocn()" value="默认图片"/>
					</td>
			</tr>
			<tr>
				<th style="text-align:center;"><b class="mustbe">*</b> 接口地址：</th>
				<td>
					<input type="text" id="appUrl" name="appUrl" maxlength="100" value="${jisApplication.appUrl }" />
					<input id="id_fastwrite" type="button" 
						onclick="fastwrite()" value="便捷录入">
				</td>
						
				<th style="text-align:center;"><b class="mustbe">*</b> 登录地址：</th>
				<td><input type="text" id="ssoUrl" name="ssoUrl" maxlength="100"
						value="${jisApplication.ssoUrl }" />
					<input id="id_checknet" type="button"
						onclick="checknet()" value="测试网络"></td>
			</tr>
			<tr>
				<th style="text-align:center;"><b class="mustbe">*</b>网络类型：</th>
				<td>
					<c:if test="${jisApplication.netType=='1'}">
						<input type="radio" name="netType" value="0"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" checked="checked" value="1"/>专网
					</c:if>
					
					<c:if test="${jisApplication.netType=='0'}">
						<input type="radio" name="netType" checked="checked" value="0"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" value="1"/>专网
					</c:if>
					
					<c:if test="${jisApplication.netType==null}">
						<input type="radio" name="netType" value="0"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" value="1"/>专网
					</c:if>
				</td>
				
				<th style="text-align:center;"><b class="mustbe">*</b>前台是否显示：</th>
				<td>
					<c:if test="${jisApplication.isShow=='0'}">
						<input type="radio" name="isShow" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isShow" checked="checked" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isShow=='1'}">
						<input type="radio" name="isShow" checked="checked" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isShow" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isShow==null}">
						<input type="radio" name="isShow" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isShow" value="0"/>否
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="text-align:center;"><b class="mustbe">*</b>是否统一注销：</th>
				<td>
					<c:if test="${jisApplication.isLogOff=='0'}">
						<input type="radio" name="isLogOff" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" checked="checked" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isLogOff=='1'}">
						<input type="radio" name="isLogOff" checked="checked" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isLogOff==null}">
						<input type="radio" name="isLogOff" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" value="0"/>否
					</c:if>
				</td>
				
				<th style="text-align:center;"><b class="mustbe">*</b>是否统一注册：</th>
				<td>
					<c:if test="${jisApplication.isUnifyRegister=='0'}">
						<input type="radio" name="isUnifyRegister" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isUnifyRegister" checked="checked" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isUnifyRegister=='1'}">
						<input type="radio" name="isUnifyRegister" checked="checked" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isUnifyRegister" value="0"/>否
					</c:if>
					
					<c:if test="${jisApplication.isUnifyRegister==null}">
						<input type="radio" name="isUnifyRegister" value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="isUnifyRegister" value="0"/>否
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="text-align: left;"><b class="mustbe">*</b> 注销地址：</th>
	        	 <td>
					<input type="text" id="logOffUrl" name="logOffUrl" value="${jisApplication.logOffUrl}" />
				</td>
			</tr>
			<tr>
				<th style="text-align: left;"><b class="mustbe">*</b>应用描述：</th>
				<td colspan="6">
					<textarea class="textarea" name="spec" style="width:884px;height:80px">${jisApplication.spec}</textarea>
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
        <input type="button" tabindex="16" value="返回" onclick="javascript:window.location.href='${ctx}/application/applicationList?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}'" class="btn gray"/>
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
