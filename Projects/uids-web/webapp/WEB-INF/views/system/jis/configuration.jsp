<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>甘肃万维JUP课题</title>
<style type="text/css">
</style>
<script type="text/javascript">
function loadTab(){
		//读取cardBar下面所有li标签
		var getId=document.getElementById("cardBar").getElementsByTagName("li");
		//定义一个判断是否有selected的变量
		var selectedItems=0;
		//判断方法，循环读出li标签的className，如果有则selectedItems加1
		for(i=0;i<getId.length;i++){
			if (getId[i].className == "Selected"){
				selectedItems+=1;
			}
		}
		//经过循环，如果selectedItems没有数值，那么说明没有selected的标签，因此给标签加上默认的className
		if (selectedItems==0){
			document.getElementById("cardBar").getElementsByTagName("li")[0].className="Selected";
			document.getElementById("Dcard1").style.display="block";
		}
	}
	//让窗口打开就运行他
	window.onload=loadTab;
	//设定结束
	//进行选项卡效果的触发
	function switchTab(cardBar,cardId){
		//读取cardBar下面所有li标签
		var oItems = document.getElementById(cardBar).getElementsByTagName("li");
		//循环清空li标签下面的selected效果
		for (i=0;i<oItems.length;i++ ){
			var x=oItems[i];
			x.className="";
			var y=x.getElementsByTagName("a");
			y[0].style.color="#333";
		}
		//开始选项卡效果的赋值，为选中的li标签增加selected类的属性
		document.getElementById(cardId).className="Selected";
		//读出cardContent 下面的所有div标签
		var dvs=document.getElementById("cardContent").getElementsByTagName("div");
		//循环，判断应该显示的div
		for (i=0;i<dvs.length;i++ ){
			if (dvs[i].id==("D"+cardId)){
				dvs[i].style.display="block";
			}else{
				dvs[i].style.display="none";
			}
		}
	}
//--><!]]>
/**
* 表单验证
*/
$(function(){
	$('#oprform').validity(function() {
		$('#sysname').require('系统名称必须填写');
		$('#sysurl').require('系统地址必须填写');
		$('#email_smtp').require('邮件服务器必须填写');
		$('#email_email').require('邮箱必须填写').match('email','邮箱格式不正确');
		$('#email_sender').require('寄件方名称必须填写');
		$('#email_title').require('注册邮件标题必须填写');
		$('#email_content').require('注册邮件内容必须填写');
		$('#email_passtitle').require('找回密码标题必须填写');
		$('#email_passContent').require('找回密码内容必须填写');
		$('#modifyPassTime').require('定时修改密码时间必须填写').match('num1','密码定时修改,只能为正数包括零,请重新输入!');
		
		//李德隆加于20160608
		$('#appId').require('appId内容必须填写');
		$('#appName').require('appName内容必须填写');
		$('#appAcc').require('appAcc内容必须填写');
		$('#appPwd').require('appPwd内容必须填写');
		$('#importantLevel').require('importantLevel内容必须填写');
		$('#isSendAgain').require('isSendAgain内容必须填写');
		$('#isLose').require('isLose内容必须填写');
		$('#isUpstream').require('isUpstream内容必须填写');
		$('#urlRoot').require('urlRoot内容必须填写');
		$('#businessIdForRegestingPer').require('个人注册短信业务Id必须填写');
		$('#businessNameForRegestingPer').require('个人注册短信业务名称必须填写');
		$('#registPerMessageContent').require('个人注册时的短信内容必须填写');
		$('#businessIdForRegestingCor').require('法人注册短信业务Id必须填写');
		$('#businessNameForRegestingCor').require('法人注册短信业务名称必须填写');
		$('#registCorMessageContent').require('法人注册时的短信内容必须填写');
		$('#businessIdForRecovingPwd').require('找回密码业务Id必须填写');
		$('#businessNameForRecovingPwd').require('找回密码业务名称必须填写');
		$('#businessIdForGettingDynamicPwd').require('获取动态登录密码业务Id必须填写');
		$('#businessNameForGettingDynamicPwd').require('获取动态登录密码业务名称必须填写');
		$('#dynamicPwdMessageContent').require('获取动态登录密码时的短信内容必须填写');
		
		
		
		var isrealnameauth = $('[name=realNameAuth]:checked').val();
		if(isrealnameauth == '1'){
			$('#realNameAuthUrl').require('开启实名认证后接口地址必须填写');
		}
		
		setCookie();
	},{
		success:function(result){
			if(result.success){
				location.reload();
			}
		}
	}); 

	setregister();
	setlogintime();
	
	initTabs();
	showRealNameAuthUrl();
});

function setCookie(){
	var tab = $('#tabs').tabs('getSelected');
	var index = $('#tabs').tabs('getTabIndex',tab);
	$.cookie('index', index);
}

function initTabs(){
	var index = $.cookie('index'); 
	$('#tabs').tabs('select',index*1);
}

function setregister(){
	var isregister = $('[name=isRegister]:checked').val();
	if(isregister == '1'){
	    $('[id^=isregister_tr]').show();
	}
	else{
		$('[id^=isregister_tr]').hide();
	}

}

function setlogintime(){
	var islogintime = $('[name=isLoginfail]:checked').val();
	if(islogintime == '1'){
	    $('[id^=logintimes_tr]').show();
	}
	else{
		$('[id^=logintimes_tr]').hide();
	}
}

function upfrontlogo(){
	openDialog('configuration/frontlogo_upload.do', 400, 170, {
		title : '上传-登录页logo'
	});
}

function showRealNameAuthUrl(){
	var isrealnameauth = $('[name=realNameAuth]:checked').val();
	if(isrealnameauth == '1'){
		$('#realnameauth_tr').show();
	}else{
		$('#realnameauth_tr').hide();
	}
}
</script>
<style type="text/css">
	<!--/*--><![CDATA[/*><!--*/
	body {margin:0 auto;padding:0;font:62.5%/2em "MingLiu" Arial;text-align:center;}
	img, a img {border:0;display:block;}
	.clearfix:after {content:".";display:block;height:0;clear:both;visibility:hidden;}
	.clearfix {display:inline-block;}
	/* Hides from IE-mac \*/
	* html .clearfix {height:1%;}
	.clearfix {display:block;}
	/* End hide from IE-mac */
	.tab {width:90%;margin:0 auto;}
	.nav, .nav li a, .hackBox {list-style:none;border:1px solid #ccc;}
	.nav {position:relative;margin:1em 0 0;border-width:0 0 1px;}
	.nav li {float:left;margin:0 .3em;}
	.nav li a {position:relative;display:block;float:left;margin:0 0 -1px;padding:0 .8em;background:#eee;color:#666;font-size:1.1em;line-height:1.8em;text-decoration:none;}
	/*- .nav li a:hover, -*/ .nav li.Selected a {border-bottom-color:#fff;background:#fff;color:#000;line-height:2em;}
	/*对点击下栏显示边框的代码进行美化*/
	.hackBox {display:none;padding:4px;margin:5px;border-width:0 1px 1px;}
	.hackBox p {margin:0 1em 1em;color:#333;font-size:1.1em;text-align:left;}
	.hackBox img {float:left;width:100px;margin:0 .8em .4em 0;}
	/*]]>*/-->
	</style>

</head>
<body>
<div class="tab">
	<form action="${ctx}/parameter/parameterSave" method="post" id="oprform" name="oprform">
		<input type="hidden" id="iid" name="iid" value="${jisParameter.iid}"/> 
		<ul class="nav clearfix" id="cardBar">
			<li id="card1"><a href="#" title="" onclick="javascript:switchTab('cardBar','card1');">基本参数</a></li>
			<li id="card2"><a href="#" title="" onclick="javascript:switchTab('cardBar','card2');">前台参数</a></li>
			<li id="card3"><a href="#" title="" onclick="javascript:switchTab('cardBar','card3');">邮件参数</a></li>
		    <li id="card4"><a href="#" title="" onclick="javascript:switchTab('cardBar','card4');">系统线程</a></li>
			<li id="card5"><a href="#" title="" onclick="javascript:switchTab('cardBar','card5');">发送短信接口参数</a></li>
			<li id="card6"><a href="#" title="" onclick="javascript:switchTab('cardBar','card6');">注册、密码找回 、动态密码、短信相关参数</a></li>
			<li id="card7"><a href="#" title="" onclick="javascript:switchTab('cardBar','card7');">实名认证参数</a></li>
			<li id="card8"><a href="#" title="" onclick="javascript:switchTab('cardBar','card8');">政府用户实名认证参数</a></li>
		</ul>
		<div id="cardContent">
			<div id="Dcard1" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label" width="100">系统名称</td>
						<td colspan="2">
							<input type="text" id="sysName" name="sysName" maxlength="30" class="input-text" value="${jisParameter.sysName}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">系统地址</td>
						<td colspan="2">
							<input type="text" id="sysUrl" name="sysUrl" maxlength="200" class="input-text" value="${jisParameter.sysUrl}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">版权信息</td>
						<td colspan="2">
							<input type="text" id="copyRight" name="copyRight" maxlength="19" value="${jisParameter.copyRight}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">限制登录次数</td>
						<td>
							<c:if test="${jisParameter.isLoginfail=='0'}">
								<input type="radio" name="isLoginfail" value="1" onclick="setlogintime()"/>是&nbsp;&nbsp;&nbsp;
								<input type="radio" name="isLoginfail" checked="checked" value="0" onclick="setlogintime()"/>否
							</c:if>
					
							<c:if test="${jisParameter.isLoginfail=='1'}">
								<input type="radio" name="isLoginfail" checked="checked" value="1" onclick="setlogintime()"/>是&nbsp;&nbsp;&nbsp;
								<input type="radio" name="isLoginfail" value="0" onclick="setlogintime()"/>否
							</c:if>
							<%-- <input type="radio" name="isLoginfail" value="1" data-value="${jisParameter.isLoginfail}" onclick="setlogintime()">是&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isLoginfail" value="0" data-value="${jisParameter.isLoginfail}" onclick="setlogintime()">否 --%>
						</td>
					</tr>
					<tr id="logintimes_tr1">
						<td align="right" class="label" width="100">连续错误次数</td>
						<td colspan="2">
							<input type="text" id="loginError" name="loginError" placeholder="0表示不限制" maxlength="33" value="${jisParameter.loginError}" />
						</td>
					</tr>
					<tr id="logintimes_tr2">
						<td align="right" class="label" width="100">错误限制时长</td>
						<!-- <td class="required">
							<h:tip title="错误登录次数达到限制，该用户将在配置时间内不可再登录，0不作限制，单位：分钟"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="text" id="banTimes" placeholder="单位：分钟" name="banTimes" maxlength="33" class="input-text" value="${jisParameter.banTimes}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="label">后台密码强度等级</td>
						<!-- <td class="required"><h:tip title="注册、登录、修改时，密码要求达到的强度"></h:tip></td> -->
						<td>
							<input type="radio" name="pwdLevel" value="0" data-value="${jisParameter.pwdLevel}" onclick="">弱&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pwdLevel" value="1" data-value="${jisParameter.pwdLevel}" onclick="">中&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pwdLevel" value="2" data-value="${jisParameter.pwdLevel}" onclick="">强	
						</td>
					</tr>
					<tr>
						<td align="right" class="label">系统网络类型</td>
						<!-- <td class="required"><h:tip title="选择不同的网络类型，将会同步不同网络的应用数据"></h:tip></td> -->
						<td>
							<input type="radio" name="netType" value="1" data-value="${jisParameter.netType}" >外网&nbsp;&nbsp;&nbsp;
							<input type="radio" name="netType" value="2" data-value="${jisParameter.netType}" >专网
						</td>
					</tr>
					<tr >
						<td align="right" class="label">密码定时修改时间（月）</td>
						<!-- <td class="required">
							<h:tip title="用户密码达到规定时间，如果用户未对密码修改，则提示用户修改,0不作定时修改限制,单位：月份"></h:tip>
						</td> -->
						<td colspan="2"><input type="text" id="modifyPassTime"
							name="modifyPassTime" maxlength="3" class="input-text"
							 value="${jisParameter.modifyPassTime}"  placeholder="到达规定时间提示用户修改"/></td>
					</tr>
				</table> 
			</div>
			
			<div id="Dcard2" class="hackBox">
				<table class="form-table">
					<tr id="logintimes_tr2">
						<td align="right" class="label">票据有效时间</td>
						<!-- <td class="required">
							<h:tip title="单位：秒"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="text" id="ticketEffectiveTime" name="ticketEffectiveTime" 
							 maxlength="10" class="input-text" value="${jisParameter.ticketEffectiveTime}" 
							  placeholder="单位：秒" />
						</td>
					</tr>
					<tr id="logintimes_tr2">
						<td align="right" class="label">令牌有效时间</td>
						<!-- <td class="required">
							<h:tip title="单位：秒"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="text" id="tokenEffectiveTime" name="tokenEffectiveTime" 
							 maxlength="10" class="input-text" value="${jisParameter.tokenEffectiveTime}" 
							  placeholder="单位：秒" />
						</td>
					</tr>
					<tr id="logintimes_tr2">
						<td align="right" class="label">前台个人用户登录超时时间</td>
						<!-- <td class="required">
							<h:tip title="在此时间内，如果用户未做任何操作，则当超过此时间后用户再进行操作，会跳转到登录页面，0不作限制，单位：分钟"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="text" id="perSessionTime" name="perSessionTime" 
							  maxlength="10" class="input-text" value="${jisParameter.corSessionTime}" 
							   placeholder="0不作限制，单位：分钟" />
						</td>
					</tr>
					<tr id="logintimes_tr2">
						<td align="right" class="label">前台法人用户登录超时时间</td>
						<!-- <td class="required">
							<h:tip title="在此时间内，如果用户未做任何操作，则当超过此时间后用户再进行操作，会跳转到登录页面，0不作限制，单位：分钟"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="text" id="corSessionTime" name="corSessionTime" 
							 maxlength="10" class="input-text" value="${jisParameter.corSessionTime}" 
							  placeholder="0不作限制，单位：分钟"/>
						</td>
					</tr>
				</table>
			</div>
							
			<div id="Dcard3" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label" width="100">服务器</td>
						<!-- <td class="required"><h:tip title="不能使用新浪、qq邮箱服务器作为服务器"></h:tip></td> -->
						<td colspan="2">
							<input id="emailSmtp" type="text" class="input-text" 
							 name="emailSmtp" value="${jisParameter.emailSmtp}" placeholder="不能使用新浪、qq邮箱服务器">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">端口</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="emailPort" type="text" class="input-text" name="emailPort" value="${jisParameter.emailPort}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">账号</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="emailBox" type="text" class="input-text" name="emailBox" value="${jisParameter.emailBox}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">密码</td>
						<!-- <td><h:tip title="若不修改请置空"></h:tip></td> -->
						<td colspan="2">
							<input id="emailPassword" type="password" class="input-text" name="emailPassword" value="" 
							 placeholder="若不修改请置空"/>
						</td>
					</tr> 
					<tr>
						<td align="right" class="label" width="100">寄件方名称</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="emailSender" type="text" class="input-text" name="emailSender" value="${jisParameter.emailSender}">
						</td>
					</tr>
					<tr id="n" style="display: none;">
						<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
					</tr>
					<tr style="display: none;">
						<td align="right" class="label" width="100">注册邮件标题</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="emailTitle" type="text" class="input-text" name="emailTitle" value="${jisParameter.emailTitle}">
						</td>
					</tr>
					<tr style="display: none;">
						<td align="right" class="label" width="100">注册邮件内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea id="emailContent" name="emailContent" class="input-textarea">${jisParameter.emailContent}</textarea>
						</td> 
					</tr>
					<tr id="n">
						<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
					</tr>
						<tr>
						<td align="right" class="label" width="100">找回密码标题</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="emailFindPassTitle" type="text" class="input-text" name="emailFindPassTitle" value="${jisParameter.emailFindPassTitle}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">找回密码内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea id="emailFindPassContent" name="emailFindPassContent" style="resize:none" class="input-textarea">${jisParameter.emailFindPassContent}</textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="Dcard4" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label">同步线程开启</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input type="radio" name="enableSynTask" value="1" data-value="${jisParameter.enableSynTask}" >是&nbsp;&nbsp;&nbsp;
							<input type="radio" name="enableSynTask" value="0" data-value="${jisParameter.enableSynTask}" >否
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">同步线程时间</td>
						<!-- <td class="required"><h:tip title="线程修改同步时间需要重启"></h:tip></td> -->
						<td colspan="2">
							<select id="syncTime" name="syncTime" data-value="${jisParameter.syncTime}" style="width: 160px;">
								<option value="30">30秒</option>
								<option value="60">1分钟</option>
								<option value="120">2分钟</option> 
								<option value="180">3分钟</option>
								<option value="300">5分钟</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">日志保留天数</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<select id="clearLogTime" name="clearLogTime" data-value="${jisParameter.clearLogTime}" style="width: 160px;">
								<option value="604800">7天</option> 
								<option value="1296000">15天</option>
								<option value="2592000">1个月</option>
								<option value="15552000">半年</option>
								<option value="31104000">1年</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="Dcard5" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label" width="100">appId</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="appId" type="text" class="input-text" name="appId" value="${jisParameter.appId}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">appName</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="appName" type="text" class="input-text" name="appName" value="${jisParameter.appName}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">appAcc</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="appAcc" type="text" class="input-text" name="appAcc" value="${jisParameter.appAcc}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">appPwd</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="appPwd" type="text" class="input-text" name="appPwd" value="${jisParameter.appPwd}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">importantLevel</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="importantLevel" type="text" class="input-text" name="importantLevel" value="${jisParameter.importantLevel}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">isSendAgain</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="isSendAgain" type="text" class="input-text" name="isSendAgain" value="${jisParameter.isSendAgain}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">isLose</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="isLose" type="text" class="input-text" name="isLose" value="${jisParameter.isLose}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">isUpstream</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="isUpstream" type="text" class="input-text" name="isUpstream" value="${jisParameter.isUpstream}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">urlRoot</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="urlRoot" type="text" class="input-text" name="urlRoot" value="${jisParameter.urlRoot}">
						</td>
					</tr>
				</table>
			</div>
			
			<div id="Dcard6" class="hackBox">
				<table class="form-table">		
					<tr>
						<td align="right" class="label" width="100">个人注册短信业务Id</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessIdForRegestingPer" type="text" class="input-text" 
							 name="businessIdForRegestingPer" value="${jisParameter.businessIdForRegestingPer}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">个人注册短信业务名称</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessNameForRegestingPer" type="text" class="input-text" 
							 name="businessNameForRegestingPer" value="${jisParameter.businessNameForRegestingPer}">
						 </td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">个人注册时的短信内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea  style="resize:none" id="registPerMessageContent" 
							 name="registPerMessageContent" class="input-textarea">${jisParameter.registPerMessageContent}
						 	</textarea>
						</td>
					</tr>
					<!-- 此处有一道分隔线分开 -->
					<tr>
						<td align="right" class="label" width="100">法人注册短信业务Id</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessIdForRegestingCor" type="text" class="input-text" 
							 name="businessIdForRegestingCor" value="${jisParameter.businessIdForRegestingCor}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">法人注册短信业务名称</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessNameForRegestingCor" type="text" class="input-text" 
							 name="businessNameForRegestingCor" value="${jisParameter.businessNameForRegestingCor}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">法人注册时的短信内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea style="resize:none" id="registCorMessageContent" 
							 name="registCorMessageContent" class="input-textarea">${jisParameter.registCorMessageContent}
							</textarea>
						</td>
					</tr>
					<!-- 此处有分隔线 -->
					<tr>
						<td align="right" class="label" width="100">找回密码业务Id</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessIdForRecovingPwd" type="text" class="input-text" 
							 name="businessIdForRecovingPwd" value="${jisParameter.businessIdForRecovingPwd}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">找回密码业务名称</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessNameForRecovingPwd" type="text" class="input-text" 
							 name="businessNameForRecovingPwd" value="${jisParameter.businessNameForRecovingPwd}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">找回密码时的短信内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea style="resize:none" id="recovingPwdContent" name="recovingPwdContent" 
							 class="input-textarea">${jisParameter.recovingPwdContent}
							</textarea>
						</td>
					</tr>
					<!-- 此处有分隔线 -->
					<tr>
						<td align="right" class="label" width="100">获取动态登录密码业务Id</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessIdForGettingDynamicPwd" type="text" class="input-text" 
							 name="businessIdForGettingDynamicPwd" value="${jisParameter.businessIdForGettingDynamicPwd}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">获取动态登录密码业务名称</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="businessNameForGettingDynamicPwd" type="text" class="input-text" 
							 name="businessNameForGettingDynamicPwd" value="${jisParameter.businessNameForGettingDynamicPwd}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">动态登录密码有效期</td>
						<!-- <td class="required">&nbsp;</td>  <h:tip title="单位是分钟"></h:tip></td> -->
						<td colspan="2">
							<select id="validityPeriod" name="validityPeriod" data-value="${jisParameter.validityPeriod}">
								<option value="5">5分钟</option>
								<option value="10">10分钟</option>
								<option value="15">15分钟</option> 
								<option value="20">20分钟</option>									
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="100">获取动态登录密码时的短信内容</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<textarea  style="resize:none" id="dynamicPwdMessageContent" 
							 name="dynamicPwdMessageContent" class="input-textarea">${jisParameter.dynamicPwdMessageContent}
							</textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="Dcard7" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label">实名认证开启</td>
						<!-- <td class="required">
							<h:tip title="开启后，前台个人法人注册会自动调用第三方接口验证"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="radio" name="enableCorRealNameAuth" value="1" data-value="${jisParameter.enableCorRealNameAuth}" >是&nbsp;&nbsp;&nbsp;
							<input type="radio" name="enableCorRealNameAuth" value="0" data-value="${jisParameter.enableCorRealNameAuth}" >否
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">法人认证接口参数requestcod</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corRequestCod" type="text" class="input-text" name="corRequestCod" value="${jisParameter.corRequestCod}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">法人认证接口参数username</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corRealUsername" type="text" class="input-text" 
							 name="corRealUsername" value="${jisParameter.corRealUsername}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">法人认证接口参数password</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corRealPassword" type="text" class="input-text" 
							 name="corRealPassword" value="${jisParameter.corRealPassword}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">法人认证换取token地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corExchangeTokenUrl" type="text" class="input-text" 
							 name="corExchangeTokenUrl" value="${jisParameter.corExchangeTokenUrl}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">法人认证实名比对地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corCompareRealNameUrl" type="text" 
							 class="input-text" name="corCompareRealNameUrl" value="${jisParameter.corCompareRealNameUrl}">
					 	</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">个人认证实名比对地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="perCompareRealNameUrl" type="text" class="input-text" 
							 name="perCompareRealNameUrl" value="${jisParameter.perCompareRealNameUrl}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="200">获取法人详细信息地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="corDetailRealNameUrl" type="text" class="input-text" 
							 name="corDetailRealNameUrl" value="${jisParameter.corDetailRealNameUrl}">
						</td>
					</tr>
					<tr id="n" style="display: none;">
						<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
					</tr>
				</table>
			</div>
				
			<div id="Dcard8" class="hackBox">
				<table class="form-table">
					<tr>
						<td align="right" class="label">实名认证开启</td>
						<!-- <td class="required">
							<h:tip title="开启后，政府用户注册会自动调用第三方接口验证"></h:tip>
						</td> -->
						<td colspan="2">
							<input type="radio" name="enableGovRealNameAuth" value="1" data-value="${jisParameter.enableGovRealNameAuth}" >是&nbsp;&nbsp;&nbsp;
							<input type="radio" name="enableGovRealNameAuth" value="0" data-value="${jisParameter.enableGovRealNameAuth}" >否
						</td>
					</tr>
					<tr>
						<td align="right" class="label"  width="250" >请选择认证模式</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<select name="verify_mode" data-value="${jisParameter.verify_mode}" >
								<option value="0">0:身份证号码+姓名</option>
								<option value="1">1:身份证号码+姓名+机构编码+机构名称+区域编码</option>
								<option value="2">2:身份证号码+姓名+机构编码</option>
								<option value="3">3:身份证号码+姓名+机构名称</option>
								<option value="4">4:身份证号码+姓名+区域编码</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="250">政府用户认证接口参数requestcod</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="govRequestCod" type="text" class="input-text" 
							 name="govRequestCod" value="${jisParameter.govRequestCod}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="250">政府用户认证接口参数username</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="govRealUsername" type="text" class="input-text" 
							 name="govRealUsername" value="${jisParameter.govRealUsername}">
					 	</td>
					</tr>
					<tr>
						<td align="right" class="label" width="250">政府用户认证接口参数password</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="govRealPassword" type="text" class="input-text" 
							 name="govRealPassword" value="${jisParameter.govRealPassword}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="250">政府用户认证换取token地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="govExchangeTokenUrl" type="text" class="input-text" 
							 name="govExchangeTokenUrl" value="${jisParameter.govExchangeTokenUrl}">
						</td>
					</tr>
					<tr>
						<td align="right" class="label" width="250">政府用户认证实名比对地址</td>
						<!-- <td class="required">&nbsp;</td> -->
						<td colspan="2">
							<input id="govCompareRealNameUrl" type="text" class="input-text" 
							 name="govCompareRealNameUrl" value="${jisParameter.govCompareRealNameUrl}">
						</td>
					</tr>
					<tr id="n" style="display: none;">
						<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
					</tr>
				</table>
			</div>
		
		</div>
		<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
			<tr>
				<td height="40" align="center">
					<input type="submit" class="btn btn-primary" value="保存"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
