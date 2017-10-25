<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<title>甘肃万维JUP课题</title>
<link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/webuploader/webuploader.css">
<script type="text/javascript" src="${ctx}/res/plugin/webuploader/webuploader.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
<style type="text/css">
/* .ztree {margin-top: 10px;border: 1px solid #cfd9db; display:none;background: #f0f6e4;height:360px;overflow-y:scroll;overflow-x:auto;} */
.mybg{
	background-color:#000;
	width:100%;
	height:200%;
	position:absolute;
	top:0; 
	left:0; 
	zIndex:500; 
	opacity:0.3; 
	filter:Alpha(opacity=30); 
}
.pic {
    width: 115px;
    height: 75px;
    text-align: center;
    line-height: 180px;
    cursor: pointer;
}
.title {
    width: 120px;
    height: 120px;
    text-align: center;
    line-height: 120px;
    font-size: 12pt;
    cursor: pointer;
}
.alert_tb {	
	left:180px;
	top:120px;
	border:1px solid #F68A8A;
	width:600px;
	height:350px;
	background-color:white;
	z-index:1000;
	position:absolute;
} 
</style>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
<script type="text/javascript">
$(function(){
//	var groupMenu = [{"name":"单位选择","title":"单位选择","id":"0","icon":null,"target":"page","url":null,"attr":{},"isParent":true,"isDisabled":false,"open":true,"nocheck":false,"click":null,"font":{},"checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"pId":"menu","chkDisabled":false,"halfCheck":false,"dynamic":null,"moduleId":null,"functionId":null,"allowedAdmin":null,"allowedGroup":null}];

	$('#groupname').menu({
		tree : 'groupmenu',
		height : 200,
		init : function() {
			setting('groupmenu', onClickGroup, onDbClickGroup);
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
function setting(treeName, onClickFunction, onDblClickFunction) {
	var setting = {
		async : {
			enable : true,
			url : '../uids/getGroup',
			autoParam : [ "id=groupId", "isDisabled" ]
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClickFunction,
			onDblClick : onDblClickFunction
		}
	};
	$("#" + treeName).tree(setting);
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
$(function(){
	if("${jisApplication.isLogOff}" == '1'){
		$("#tr_logoffUrl").show();
	}
	 if("${jisApplication.userDefined}" == '1'){
		$("#tr_ssoLogin_1").show();
		$("#tr_ssoLogin_2").show();
	} 
	 if("${jisApplication.transType}" == '0'){
		$("#ssoUrlh").hide();
		$("#ssoUrld").hide();
		$("#tr_isUniRgh").hide();
		$("#tr_isUniRgd").hide();
		$("#id_checknet").hide();
	 }
	 if("${jisApplication.transType}" == '1'){
			$("#ssoUrlh").show();
			$("#ssoUrld").show();
			$("#tr_isUniRgh").show();
			$("#tr_isUniRgd").show();
			$("#id_checknet").show();
	}
	 if("${jisApplication.icon}" != null){
		 $("#showpic").show();
	 }
	 if("${jisApplication.icon}" == null){
		 $("#showpic").hide();
	 }
	 if("${jisApplication.icon}" == ""){
		 $("#showpic").hide();
	 }
	setLoginType();
	setSsoLogin();
	 // 初始化Web Uploader
    var uploader = WebUploader.create({
    	// 自动上传。
        auto: true,
        // swf文件路径
        swf: '${ctx}/res/plugin/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: '${ctx}/fileUpload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    uploader.on( 'uploadSuccess', function( file, a ) {
    	$("#icon").val("/resources/jis/front/app/"+file.name);
    	$("#icon2").attr("src","${ctx}/resources/jis/front/app/"+file.name);
        $( '#'+file.id ).find('p.state').text('已上传');
        $("#showpic").show();
    });
    
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });
});

//统一注销
function selectlogoff(value){
	if(value == '0'){
		$("#tr_logoffUrl").css("display",'none');
	}
	if(value== '1'){
		$("#tr_logoffUrl").css("display",'');
		$("#logOffUrl").rules("add",{required:true});
		$("#logOffUrl").rules("add",{url:true});
		$("#logOffUrl").rules("add",{maxlength: 255});
	}
	if(value=null){
		$("#tr_logoffUrl").css("display",'none');
	}
} 

function setSsoLogin(value){
	/* var ssoLogin = $('[name=userDefined]:checked').val(); */
	if(value == '1'){
		$('[id^=tr_ssoLogin_]').show();   
		$("#allLoginIid").rules("add",{required:true});
		$("#allPwd").rules("add",{required:true});
	}
	if(value == '0'){
		$('[id^=tr_ssoLogin_]').hide();
	}
	if(value == '2'){
		$('[id^=tr_ssoLogin_]').hide();
	}
	if(value == null){
		$('[id^=tr_ssoLogin_]').hide();
	}
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

//默认图片弹出
function selectpic(){
	var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#alertpic").show();
}

function changSel(num){
	var picName;
	if(num==1){
		picName="/jcms.jpg";
	}else if(num==2){
		picName="/jact.jpg";
	}else if(num==3){
		picName="/oa.jpg";
	}else if(num==4){
		picName="/email.jpg";
	}else if(num==5){
		picName="/xxgk.jpg";
	}
	var obj=document.getElementsByName("selectpicname");
	obj[num-1].checked = true;
	$("#icon").val(picName);
	$("#icon2").attr("src","${ctx}/resources/jis/front/app/"+picName);
	$("#showpic").show();
}

function selectpicSubmit(){
	 
	 var mybg = document.createElement("div"); 
		$(".mybg").removeClass("mybg");
	    document.body.appendChild(mybg);
		document.body.style.overflow = "show"; 
		document.getElementById("alertpic").style.display="none"; 
}

//便捷录入弹出层
function fastwrite(){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#alerttb").show();			
}

function group(num){
	var fasturl=document.getElementById("fasturl");
	var fastinter=document.getElementById("fastinter");
	var fasturlIn;
	var fastinterIn;
	if(num==1){
		fasturlIn='http://127.0.0.1:8080/jcms';
		fastinterIn='/interface/ldap/synchro.jsp';
	}else if(num==2){
		fasturlIn='http://127.0.0.1:8080/jcms';
		fastinterIn='/jcms_files/jcms1/webid/site/module/webuser/receive.jsp?app=individuation';
	}else if(num==3){
		fasturlIn='http://127.0.0.1:8080/xxgk';
		fastinterIn='/interface/ldap/synchro.jsp';
	}else if(num==4){
		fasturlIn='http://127.0.0.1:8080/jact';
		fastinterIn='/receive/ldap/synchro.action';
	}else if(num==5){
		fasturlIn='http://127.0.0.1:8080/jact';
		fastinterIn='/receive/ldap/synchro.action?isfrontlogin=1';
	}else if(num==6){
		fasturlIn='http://127.0.0.1:8080/jphoto';
		fastinterIn='/jphoto/sys/interface/ldap/synchro.jsp';
	}else if(num==7){
		fasturlIn='http://127.0.0.1:8080/jphoto';
		fastinterIn='/interface/ldap/synchro.jsp';
	}
	fasturl.value=fasturlIn;
	fastinter.value=fastinterIn;
}

 function fastwriteSubmit(){
	 var appUrl=document.getElementById("appUrl");
	 var fasturl=document.getElementById("fasturl").value;
	 var fastinter=document.getElementById("fastinter").value;
	 appUrl.value=fasturl+fastinter;
	 
	 var mybg = document.createElement("div"); 
		/* mybg.setAttribute("class","mybg"); */ 
		$(".mybg").removeClass("mybg");
	    document.body.appendChild(mybg);
		document.body.style.overflow = "show"; 
		document.getElementById("alerttb").style.display="none"; 
 }

//生成密钥
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

function selecttranstype(value){
	/* var transtype=$('input[name="transtype"]:checked').val(); 0:H  1:W*/
	if(value == '0'){
		$("#ssoUrlh").css("display",'none');
		$("#ssoUrld").css("display",'none');
		$("#tr_isUniRgh").css("display",'none');
		$("#tr_isUniRgd").css("display",'none');
		$("#ssoUrl").rules("remove","required");
	}
	if(value == '1'){
		$("#ssoUrlh").css("display",'');
		$("#ssoUrld").css("display",'');
		$("#tr_isUniRgh").css("display",'');
		$("#tr_isUniRgd").css("display",'');
		$("#ssoUrl").rules("add",{required:true});
	}
	if(value == null || value == ""){
		$("#ssoUrl").rules("remove","required");
	}
}

function setLoginType(){
	var loginType = $('#synctype').val();
	if(loginType == '01'){  //同步前后台
		$('#tr_encryptType').show();
		$('#tr_isSyncGroup').show();
		$('#ssoUrlh').show();
		$('#ssoUrld').show();
		$('#tr_ssoLogin').hide();  
		$('#tr_encryptKey').show();
		$('#id_setsso').hide();
		$('#id_checknet').show();
		$('#id_fastwrite').show();
		$('#loginType').val(0);
		$('#isSyncGroup').val(1);
	}else if(loginType == '00'){  //只同步后台
		$('#tr_encryptType').show();  
		$('#tr_isSyncGroup').show();
		$('#ssoUrlh').show();
		$('#ssoUrld').show();
		$('#tr_ssoLogin').hide();
		$('#tr_encryptKey').show();
		$('#id_setsso').hide();
		$('#id_fastwrite').show();
		$('#id_checknet').show();
		$('#loginType').val(0);
		$('#isSyncGroup').val(0);
	}else if(loginType == '11'){  //不同步
		$('#tr_encryptType').show();
		$('#tr_isSyncGroup').hide();
		$('#ssoUrlh').hide();
		$('#ssoUrld').hide();
		$('#tr_ssoLogin').show();  
		$('#tr_encryptKey').show();
		$('#id_setsso').show();
		$('#id_fastwrite').hide();
		$('#id_checknet').hide();  
		$('#loginType').val(1);
		$('#isSyncGroup').val(1);
	}else{   //
		$('#tr_ssoLogin').hide();
		$('#id_fastwrite').show();
		$('#ssoUrlh').hide();
		$('#ssoUrld').hide();
		$('#tr_isUniRgh').hide();
		$('#tr_isUniRgd').hide();
	}
	
	setSsoLogin();
}

$().ready(function() { 
//表单校验
 $("#editForm").validate({
    rules: {
    	name: {
	    required: true,
	    userName:true,
	    maxlength: 32
	   },
	   mark: {
	    required: true,
	    charNo:true,
	    maxlength: 32,
	    uniqueMark:true
	   },
	   encryptKey: {
	    required: true,
	    chrnum:true,
	    maxlength: 19
	   },
	   appUrl:{
	   required: true,
	   url:true,
	   maxlength: 255
	   },
	  /*  ssoUrl:{
	   required: true,
	   url:true,
	   maxlength: 255
	   }, */
	   netType:{
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
	/* $(".user-role").on("click",function(event){
			$('.error-info-checkedbox').remove();
			$(this).removeClass('erro');
	}); */
	// Ajax标识校验
	$.uniqueValidate('uniqueMark', '${ctx}/application/checkMark', ['mark','oldMark'], '对不起，此标识已存在');
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
    <form id="editForm" method="post" action="${ctx}/application/applicationSave" enctype="multipart/form-data">
    
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${jisApplication.iid}"/>
    	<%-- <input type="hidden" id="resName" name="resName" value="${jisDatacall.resName}"/> --%>	
    	<input type="hidden" name="setId" value="1"  />
    	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
		<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
		<input type="hidden" id="groupid" name="groupId" value="${jisApplication.groupId}"/>
		<input type="hidden" id="icon" name="icon" value="${jisApplication.icon}"/>
		<input type="hidden" id="loginType" name="loginType" value="${jisApplication.loginType}"/>
		<input type="hidden" id="isSyncGroup" name="isSyncGroup" value="${jisApplication.isSyncGroup}"/>
		<input type="hidden" id="orderId" name="orderId" value="${jisApplication.orderId}"/>
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
				<th style="text-align:left;"><b class="mustbe">&nbsp;&nbsp;</b>所属机构：</th>
				<td>
					<input id="groupname" value="${groupMap[jisApplication.groupId]}" placeholder="请选择所属机构" name="groupname" type="text" style="cursor: pointer;" readonly="readonly"/>
				</td>
				<th style="text-align:center;"><b class="mustbe">&nbsp;&nbsp;</b> 同步用户：</th>
                <td>   
                	<select name="synctype" id="synctype" data-value="${jisApplication.loginType}${jisApplication.isSyncGroup}" 
						onchange="setLoginType();">  
						<option value="">--请选择--</option>   
						<option value="01"<c:if test="${jisApplication.loginType==0 && jisApplication.isSyncGroup==1}">selected</c:if>>同步后台、前台用户</option>   
						<option value="00"<c:if test="${jisApplication.loginType==0 && jisApplication.isSyncGroup==0}">selected</c:if>>只同步后台</option>
						<option value="11"<c:if test="${jisApplication.loginType==1 && jisApplication.isSyncGroup==1}">selected</c:if>>不同步</option>
					</select>
                </td>
            </tr>
            <tr>
				<th style="text-align:left;"><b class="mustbe">&nbsp;&nbsp;</b>数据传送方式：</th>
				<td>
					<c:if test="${jisApplication.transType=='1'}">
						<input type="radio" name="transType" value="0" onclick="selecttranstype(this.value);"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" checked="checked" value="1" onclick="selecttranstype(this.value);"/>WebService
					</c:if>
					
					<c:if test="${jisApplication.transType=='0'}">
						<input type="radio" name="transType" checked="checked" value="0" onclick="selecttranstype(this.value);"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" value="1" onclick="selecttranstype(this.value);"/>WebService
					</c:if>
					
					<c:if test="${jisApplication.transType==null}">
						<input type="radio" name="transType" value="0" onclick="selecttranstype(this.value);" checked="checked"/>HTTP&nbsp;&nbsp;
						<input type="radio" name="transType" value="1" onclick="selecttranstype(this.value);"/>WebService
					</c:if>
				</td>
				
				<th style="text-align:center;"><b class="mustbe">&nbsp;&nbsp;</b>加密设置：</th>
				<td>
				
					<c:if test="${jisApplication.encryptType=='1'}">
						<input type="radio" name="encryptType" value="1" checked="checked"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="2"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="0"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType=='2'}">
						<input type="radio" name="encryptType" value="1"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="2" checked="checked"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="0"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType=='0'}">
						<input type="radio" name="encryptType" value="1"/>MD5&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="2"/>MD5+base64&nbsp;&nbsp;
						<input type="radio" name="encryptType" value="0" checked="checked"/>不加密
					</c:if>
					
					<c:if test="${jisApplication.encryptType==null}">
						<input type="radio" name="encryptType" value="1" checked="checked"/>MD5
						<input type="radio" name="encryptType" value="2"/>MD5+base64
						<input type="radio" name="encryptType" value="0"/>不加密
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="text-align: left;"><b class="mustbe">*</b> 加密密钥：</th>
	        	 <td>
					<input type="text" id="encryptKey" name="encryptKey"
						maxlength="16" value="${jisApplication.encryptKey}" />
					<input type="button" name="button" class="btnother blue"
						   onclick="generateKey()" value="重新生成"/>
				</td>
				
				<th style="text-align: center;"><b class="mustbe">&nbsp;&nbsp;</b> 应用图标：</th>
					<td>
						    <div id="fileList" class="uploader-list"></div>
						    <div id="filePicker">选择图片</div>
						    <input type="button" class="btnother blue" onclick="selectpic()" value="默认图片">
					</td>
					<td id="showpic" name="showpic" colspan="2" style="text-align:left;display:none;">
						<img id="icon2" name="icon2" width="100" height="100" src="${ctx}${jisApplication.icon}">
					</td>
					
			</tr>
			<tr>
				<th style="text-align:left;"><b class="mustbe">*</b> 接口地址：</th>
				<td>
					<input type="text" id="appUrl" name="appUrl" maxlength="100" value="${jisApplication.appUrl }" />
					<input id="id_fastwrite" type="button" class="btnother blue"
						   onclick="fastwrite()" value="便捷录入"/>
					<!-- <input id="id_setsso" type="button" class="btn btn-success btn-small" 
					onclick="setsso()" value="接口配置"/></td> -->
				</td>
				
				<th id="ssoUrlh" style="text-align:center;"><b class="mustbe">*</b> 登录地址：</th>
				<td id="ssoUrld"><input type="text" id="ssoUrl" name="ssoUrl" maxlength="100"
						value="${jisApplication.ssoUrl }" />
					<input id="id_checknet" type="button" class="btnother blue"
						onclick="checknet()" value="测试网络"></td>
			</tr>
			<tr>
				<th style="text-align:left;"><b class="mustbe">*</b>网络类型：</th>
				<td>
					<c:if test="${jisApplication.netType=='2'}">
						<input type="radio" name="netType" value="1"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" checked="checked" value="2"/>专网
					</c:if>
					
					<c:if test="${jisApplication.netType=='1'}">
						<input type="radio" name="netType" checked="checked" value="1"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" value="2"/>专网
					</c:if>
					
					<c:if test="${jisApplication.netType==null}">
						<input type="radio" name="netType" value="1" checked="checked"/>外网&nbsp;&nbsp;
						<input type="radio" name="netType" value="2"/>专网
					</c:if>
				</td>
				
				<tr id="tr_ssoLogin">
					<th style="text-align:left;"><b class="mustbe">&nbsp;&nbsp;</b>账号设置：</th>
					<td>
						<c:if test="${jisApplication.userDefined=='0'}">
							<input type="radio" name="userDefined" value="0"
							checked="checked" onclick="setSsoLogin(this.value);">用户预设账号&nbsp;
							<input type="radio" name="userDefined" value="2"
							 onclick="setSsoLogin(this.value);">统一用户账号&nbsp;
							<input type="radio" name="userDefined" value="1"
							 onclick="setSsoLogin(this.value);">固定账号
						</c:if>
						<c:if test="${jisApplication.userDefined=='2'}">
							<input type="radio" name="userDefined" value="0"
							 onclick="setSsoLogin(this.value);">用户预设账号&nbsp;
							<input type="radio" name="userDefined" value="2"
							checked="checked" onclick="setSsoLogin(this.value);">统一用户账号&nbsp;
							<input type="radio" name="userDefined" value="1"
							 onclick="setSsoLogin(this.value);">固定账号
						</c:if>
						<c:if test="${jisApplication.userDefined=='1'}">
							<input type="radio" name="userDefined" value="0"
							 onclick="setSsoLogin(this.value);">用户预设账号&nbsp;
							<input type="radio" name="userDefined" value="2"
							 onclick="setSsoLogin(this.value);">统一用户账号&nbsp;
							<input type="radio" name="userDefined" value="1"
							checked="checked" onclick="setSsoLogin(this.value);">固定账号
						</c:if>
						<c:if test="${jisApplication.userDefined==null}">
							<input type="radio" name="userDefined" value="0" 
							 checked="checked" onclick="setSsoLogin(this.value);">用户预设账号&nbsp;
							<input type="radio" name="userDefined" value="2"
							 onclick="setSsoLogin(this.value);">统一用户账号&nbsp;
							<input type="radio" name="userDefined" value="1"
							 onclick="setSsoLogin(this.value);">固定账号
						</c:if>
					</td>
				</tr>
				<tr id="tr_ssoLogin_1" style="display:none">
					<th style="text-align:left;"><b class="mustbe">*</b>固定用户名：</th>
					<td><input type="text" id="allLoginIid" name="allLoginIid" maxlength="20"
						class="input-text" value="${jisApplication.allLoginIid }" /></td>
					<td></td>
				</tr>
				<tr id="tr_ssoLogin_2" style="display:none">
					<th style="text-align:left;"><b class="mustbe">*</b>固定密码：</th>
					<td><input type="password" id="allPwd" name="allPwd" maxlength="20"
						class="input-text" value="${jisApplication.allPwd }" /></td>
					<!-- <td></td> -->
				</tr>
				
				
				<th style="text-align:left;"><b class="mustbe">&nbsp;&nbsp;</b>前台是否显示：</th>
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
						<input type="radio" name="isShow" value="0" checked="checked"/>否
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;"><b class="mustbe">&nbsp;&nbsp;</b>是否统一注销：</th>
				<td>
					<c:if test="${jisApplication.isLogOff=='0'}">
						<input type="radio" name="isLogOff" value="1" onclick="selectlogoff(this.value);"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" checked="checked" value="0" onclick="selectlogoff(this.value);"/>否
					</c:if>
					
					<c:if test="${jisApplication.isLogOff=='1'}">
						<input type="radio" name="isLogOff" checked="checked" value="1" onclick="selectlogoff(this.value);"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" value="0" onclick="selectlogoff(this.value);"/>否
					</c:if>
					
					<c:if test="${jisApplication.isLogOff==null}">
						<input type="radio" name="isLogOff" value="1" onclick="selectlogoff(this.value);"/>是&nbsp;&nbsp;
						<input type="radio" name="isLogOff" value="0" checked="checked" onclick="selectlogoff(this.value);"/>否
					</c:if>
				</td>
				
				<th id="tr_isUniRgh" style="text-align:center;"><b class="mustbe">&nbsp;&nbsp;</b>是否统一注册：</th>
				<td id="tr_isUniRgd">
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
						<input type="radio" name="isUnifyRegister" value="0" checked="checked"/>否
					</c:if>
				</td>
			</tr>
				<tr id="tr_logoffUrl" style="display:none">
					<th style="text-align: left;"><b class="mustbe">*</b> 注销地址：</th>
		        	 <td>
						<input type="text" id="logOffUrl" name="logOffUrl" value="${jisApplication.logOffUrl}" />
					</td>
				</tr>
			<tr>
				<th style="text-align: left;"><b class="mustbe">&nbsp;&nbsp;</b>应用描述：</th>
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
    <div id="alerttb" class="alert_tb" style="display:none;"> 
    	<form action="${ctx}/application/applicationSave">
    	<table>
    	<div style="font-size: 20px;padding-top: 15px;padding-bottom: 10px;cursor: auto;padding-left: 5px;">&nbsp;便捷录入
    		<a href="${ctx}/application/applicationEdit?findNowPage=true&orderField=
					 ${orderField}&orderSort=${orderSort}&iid=${jisApplication.iid}" title="关闭" 
					 style="padding-left: 466px;font-size: 23px;color: black;text-decoration:none">x</a>
    	</div>
    	<hr/><br/>
    		<tr>
    			<td style="font-size: 17px;" align="left">&nbsp;&nbsp;应用名称：&nbsp;&nbsp;</td>
    			<td style="font-size: 15px;"><input  type="radio" id="jcms" name="fast" value="jcms" onclick="group(1);"/>&nbsp;JCMS后台用户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td style="font-size: 15px;"><input type="radio" id="jcms2" name="fast" value="jcms2" onclick="group(2);"/>&nbsp;JCMS个性化定制&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td style="font-size: 15px;"><input type="radio" id="xxgk" name="fast" value="xxgk" onclick="group(3);"/>&nbsp;XXGK</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td style="font-size: 15px;"><input type="radio" id="jact" name="fast" value="jact" onclick="group(4);"/>&nbsp;JACT后台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td style="font-size: 15px;"><input type="radio" id="jact2" name="fast" value="jact2" onclick="group(5);"/>&nbsp;JACT前台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td style="font-size: 15px;"><input type="radio" id="jphoto" name="fast" value="jphoto" onclick="group(6);"/>&nbsp;JPHOTO前台</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td style="font-size: 15px;"><input type="radio" id="jphoto2" name="fast" value="jphoto2" onclick="group(7);"/>&nbsp;JPHOTO后台</td>
    		</tr>
			<tr style="line-height: 70px;">
				<td style="font-size: 17px;" align="left" >&nbsp;&nbsp;应用域名：&nbsp;&nbsp;</td>
				<td colspan="3"><input size="60" type="text" name="fasturl" id="fasturl" value=""></td>
			</tr>
			<tr>
				<td style="font-size: 17px;" align="left">&nbsp;&nbsp;接口地址：&nbsp;&nbsp;</td>
				<td colspan="3"><input size="60" type="text" name="fastinter" id="fastinter" value=""></td>
			</tr>
		</table>
		<div id="dialog-toolbar" style="text-align: center;padding-top: 40px;">
			<div id="dialog-toolbar-panel">
				<input type="button" class="btn bluegreen" value="保存" 
				 onclick="fastwriteSubmit();" style="background-color: #36c6d3;color:#ffffff;font-size: 15px;"/>
				<%-- <input type="button" class="btn" value="取消" 
					 onclick="javascript:window.location.href=
					 '${ctx}/application/applicationEdit?findNowPage=true&orderField=
					 ${orderField}&orderSort=${orderSort}&iid=${jisApplication.iid}'" /> --%>
			</div>
		</div>
		</form>
    </div>
    
    <div id="alertpic" class="alert_tb" style="display:none;"> 
    	<form action="${ctx}/application/applicationSave">
    	<table>
    	<div style="font-size: 20px;padding-top: 15px;padding-bottom: 10px;cursor: auto;padding-left: 5px;">&nbsp;默认图标
    		<a href="${ctx}/application/applicationEdit?findNowPage=true&orderField=${orderField}&orderSort=${orderSort}&iid=${jisApplication.iid}" 
    		title="关闭" style="padding-left: 466px;font-size: 23px;color: black;text-decoration:none">x</a>
    	</div>
    	<hr/>
    		<tr>
				<td ><div class="pic"><img id="img1" onclick="changSel(1);"  src="${ctx}/uploads/jcms.jpg"></div></td>
				<td ><div class="pic"><img id="img2" onclick="changSel(2);"  src="${ctx}/uploads/jact.jpg"></div></td>
				<td ><div class="pic"><img id="img3" onclick="changSel(3);"  src="${ctx}/uploads/oa.jpg"></div></td>
				<td ><div class="pic"><img id="img4" onclick="changSel(4);"  src="${ctx}/uploads/email.jpg"></div></td>
				<td ><div class="pic"><img id="img5" onclick="changSel(5);"  src="${ctx}/uploads/xxgk.jpg"></div></td>
			</tr>
			<tr>
				<td><div class="title" onclick="changSel(1);"><input type="radio" name="selectpicname" value="img1" checked onfocus="this.blur()"/>JCMS</div></td>
				<td><div class="title" onclick="changSel(2);"><input type="radio" name="selectpicname" value="img2" onfocus="this.blur()"/>互动平台</div></td>
				<td><div class="title" onclick="changSel(3);"><input type="radio" name="selectpicname" value="img3" onfocus="this.blur()"/>OA</div></td>
				<td><div class="title" onclick="changSel(4);"><input type="radio" name="selectpicname" value="img4" onfocus="this.blur()"/>信息公开</div></td>
				<td><div class="title" onclick="changSel(5);"><input type="radio" name="selectpicname" value="img5" onfocus="this.blur()"/>电子邮件</div></td>
			</tr>
		</table>
		<div id="dialogpic-toolbar" style="text-align: center;">
			<div id="dialogpic-toolbar-panel">
				<input type="button" class="btn bluegreen" value="保存" 
				 onclick="selectpicSubmit();" style="background-color: #36c6d3;color:#ffffff;font-size: 15px;"/> 
			</div>
		</div>
		</form>
    </div>
    
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
