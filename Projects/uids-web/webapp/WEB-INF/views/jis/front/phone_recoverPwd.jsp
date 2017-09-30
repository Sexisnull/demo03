<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>甘肃政务服务网</title>
<link href="${ctx }/ui/images/lsc_findPwd.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/grzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/ui/images/syl_fpqd.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.parser.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/linkbutton.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.linkbutton.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.resizable.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.draggable.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/panel.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.panel.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/window.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.window.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/lib/easyui/themes/bootstrap/messager.css"/>
<script type="text/javascript" src="${ctx}/ui/lib/easyui/plugins/jquery.messager.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/hanweb/validity/css/validity.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/validity/validity.js"></script>
<script type="text/javascript" src="${ctx}/ui/widgets/hanweb/validity/validity.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/jquery.cookie.js"></script>
<script>


	var typeEntity ;//是法人或个人。cor,per
	
	
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}//过滤左右空格
		
		
		$.validity.setup({
			outputMode : "showErr"
		}); //校验错误弹出
		
		window.alert = function (msg,type,fu){
			top.$.messager.alert(' ',msg,type,fu);
		};
	
		window.confirm = function(msg,okCall,cancelCall){
			top.$.messager.confirm(' ',msg,function(flag){
				if(flag){
					if(typeof(okCall) != 'undefined'){
						okCall();
					}
				}else{
					if(typeof(cancelCall) != 'undefined'){
						cancelCall();
					}
				}
			});
		};
		
		//获得焦点后提示语消失 
		$(function(){//一进来即加载。
			
			
			
			var txtareaObj = document.getElementById('inputByGuest');
			txtareaObj.onfocus = function(){
			  //  this.value = "";
			}
			/*txtareaObj.onblur = function(){失去焦点后的函数 
			    this.value = "";
			}*/
	
			
		typeEntity = "${typeEntity}";//是法人或个人。cor,per
		typeEntity=$.trim(typeEntity);
		
			if("per"==typeEntity){
				$('#hint').val("请输入登录名/手机号/身份证号");
				$('#hint').text("请输入登录名/手机号/身份证号");
			}else{
				$('#hint').val("填写用户名/工商号/信用代码/组织机构码");
				$('#hint').text("填写用户名/工商号/信用代码/组织机构码");
			}
			
			$('#verifyform').validity(function(){
				
				if("per"==typeEntity){
					$('#inputByGuest').require('请填写登录名/手机号/身份证号');
				}else{
					$('#inputByGuest').require('填写用户名/工商号/信用代码/组织机构码');

				}
				$('#randCode').require('请填写验证码');
			//	$('#mobile').require('请填写移动电话').match('mobile','对不起，您输入的移动电话错误');
				$('#cellphoneShortMessageRandomCodeWritenByGuest').require("请填写短信验证码");
			},{
				success:function(result){
					if(result.success){
						alert(result.message);
						window.location.href='resetpwd_show.do';
					}else {
						alert(result.message);
						$('#verifyImg').click();
					}
				}
			});
			
		});
				
		/*function toSubmit(){
			var inputByGuest = $("#inputByGuest").val();
			var randCode = $("#randCode").val();
			var cellphoneShortMessageRandomCodeWritenByGuest = $("#cellphoneShortMessageRandomCodeWritenByGuest").val();
			var posturl="${url}";
			if(inputByGuest=='' || randCode=='' || cellphoneShortMessageRandomCodeWritenByGuest==''){
				alert("用户账号，随机验证码或短信验证码不能为空");
				return;
			}
			$.ajax({
		   		type:"post",		   	
		   		url:posturl,
		     	data:{"inputByGuest":inputByGuest,"randCode":randCode,"cellphoneShortMessageRandomCodeWritenByGuest":cellphoneShortMessageRandomCodeWritenByGuest},	
				dataType:'json',
				success:function(json){//如果成功与第三方连接					
					if(json.success){
						window.location.href='resetpwd_show.do';
					}else{
						alert(json.message);
						$('#verifyImg').click();
					}
				}  ,
				error:function(){					
					alert("系统异常，请稍后重试！");
					$('#verifyImg').click();
				}	
				
		   	});	
		}*/
		
		function waitToGetCellphoneCode(){
			$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");//点击了“发送”按钮后，点击失效。
			send(success_function,fail_function);		//该方法在Java中，有1秒延迟的模拟 效果。	
		   }
		function success_function(){
			
			alert("短信验证码已发送，请注意查收！");	
			$("#waitForCellphoneCode").attr("disabled", true);//点击了“发送”按钮后，点击失效。
			$("#waitForCellphoneCode").addClass("disabled");//置灰
			var timeLeft = 60;
			var countdown = setInterval(refresh, 1000);
			function refresh(){
				if(timeLeft<0){
					
				//	document.getElementById('waitForCellphoneCode').value="点击重新获取验证码";	
					$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
	 						
					clearInterval(countdown); 		
					return;
				}
				var waitNotice = "（"+timeLeft+"）秒后重新获取验证码";			
				document.getElementById('waitForCellphoneCode').value=waitNotice;	
				timeLeft--;
			}
		}
		
		function fail_functionnull(){
			$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
			return;
		}
		function fail_function(msgRe){
			if(msgRe == "" || typeof(msgRe) =="undefined"){
				alert("短信验证码发送失败！请重新获取！");
				$('#verifyImg').click();
			$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
			}else{
				alert(msgRe);
				$('#verifyImg').click();
				$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
			}
			return;
		}
		
					
		function send(success_function,fail_function){	
			var sendResult;	
			   if("per"==typeEntity){
			        
				  var checkUrl = "sendCellphoneShortMessageUserPwdRecover.do";
			  }else{
				  var checkUrl = "sendCellphoneShortMessageUserPwdRecover.do";
			  } 
			//用Ajax发短信		
			$.ajax({
				async: false, //这个ajax请求则为同步请求，在没有返回值之前，ajax块外是不会执行的。
		   		type:"post",		   	
		   		url:"sendCellphoneShortMessageUserPwdRecover.do",
		     	data:{"inputByGuest":$("#inputByGuest").val(),"randCode":$("#randCode").val()},	
				dataType:'json',
				success:function(json){//如果成功与第三方连接				
					if($.trim(json) != ""){	
						var isSuccess = json.success;//此字段我没用。
						var code = json.code;////0发送短信异常,1无此用户，2为发短信超过次数,3发短线成功,4账号不能为空,6验证码不能为空,5随机验证码不正确
						console.log(code);
						var msg = json.msg;//				
						if(code == "3"){
							//$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");
							success_function();	
						}else if(code == "4"){
							fail_function(msg);
						}else if(code == "6"){
							fail_function(msg);
						}else if(code == "1"){
							fail_function(msg);
						}else if(code == "5"){
							fail_function(msg);
						}else if(code == "0"){
							//$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");
							fail_function(msg);
						}else{
							alert(msg);
						}						
					}else{						
						fail_function();					
					}	
				
				}  ,
				error:function(){					
					fail_function();			
				}	
				
		   	});			
		}
</script>
<style type="text/css">
body {
.main{height:800px}
.xxnr2{height:210px}
</style>

</head>

<body>



  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="${ctx }/ui/js/1512101421282896.js"></script>
    </div>
  </div>
  <div> 
    <script language="javascript" src="${ctx }/ui/js/1512101146476750.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
  <div class="mainWth_faren back">
    <div class="main">




	
    <div class="bt" style="border-bottom:none">
    	<img src="${ctx }/ui/images/yxfind-pwd_03.png" />
    </div>

    <div style="clear:both"></div>



  <form action="${url }" method="post" id="verifyform">
    <table width="955" height="477" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-bottom:8px; margin-top:10px;" id="tt">
 
      <tr height="60" align="center">
        <td  style="background:url(${ctx}/ui/images/yxfind-pwd_04.png); background-repeat:no-repeat; background-position:center;"></td>
      </tr>
      <tr height="170" align="center">
        <td><table width="640" height="280" border="0" cellspacing="0" cellpadding="0" align="center">
            <!-- 
            <tr height="60">
              <td width="70" align="right">登录名：</td>
              <td width="20"><img src="./images/yxfind-pwd_06.png" /></td> 
              <td style="background:url(./images/yxfind-pwd_05.png);height:40px; width:283px; background-repeat:no-repeat;position: relative; background-position:center;">
             	 <input type="text" id="loginId" name="loginName" onfocus="emptyTel()"  onblur="checkLoginName(this)"; style="line-height:38px; padding-top:10px; height:38px; width:275px; position: absolute;background: transparent;outline: none; top:0px;left: 0px; border:none;  padding-left:5px;font-family:'微软雅黑'; font-size:14px; color:#a9a9a9;" /></td> 
              <td id="hint" >&nbsp;&nbsp;&nbsp;请输入登录名/手机号/身份证号</td>
            </tr>
             -->
             
           
             
            <tr height="60">
             <td width="70" align="right">帐&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
             <td width="20"><img src="${ctx }/ui/images/yxfind-pwd_06.png" /></td>   
             <td style="background:url(${ctx }/ui/images/yxfind-pwd_05.png);height:40px; width:283px; background-repeat:no-repeat;
             	position: relative;background-position:center;">             
            	
            	 <input id="inputByGuest" value=""   name="mobile" type="text" ;  
             	style="line-height:38px;  height:38px; width:275px; 
             	background: transparent;outline: none; top:0px;left: 0px; border:none;  padding-left:5px;
             	font-family:'微软雅黑'; font-size:14px; color:#a9a9a9;" placeholder="用户名/工商号/信用代码/组织机构码" /></td>
             <td id="hint"  style="padding-left:10px"></td>
             
           </tr>
            
            <tr height="60">
				<td width="70" align="right">验&nbsp;证&nbsp;&nbsp;码：</td>
				<td width="20px" style="color: #ff0000; height: 50px; padding-top: 5px; padding-right: 5px;"align="center">*</td>
				<td style="background: url(${ctx }/ui/images/yxfind-pwd_05.png); height: 40px; width: 283px; background-repeat: no-repeat; position: relative; background-position: center;">
				<input id="randCode" name="randCode" type="text" style="line-height: 38px; height: 38px; width: 275px; background: transparent; outline: none; top: 0px; left: 0px; border: none; padding-left: 5px; font-family: '微软雅黑'; font-size: 14px; color: #a9a9a9;" />
				</td>
				<td width="385px">${verifycodeimg}</td>
			</tr>
			<tr height="60">
				<td width="70" align="right">手机验证码：</td>
				<td width="20"><img src="${ctx }/ui/images/yxfind-pwd_06.png" /></td>
				<td style="background: url(${ctx }/ui/images/yxfind-pwd_05.png); height: 40px; width: 283px; background-repeat: no-repeat; position: relative; background-position: center;">
				<input name="cellphoneShortMessageRandomCodeWritenByGuest" id="cellphoneShortMessageRandomCodeWritenByGuest" type="text"
										style="line-height: 38px; height: 38px; width: 275px; background: transparent; outline: none; top: 0px; left: 0px; border: none; padding-left: 5px; font-family: '微软雅黑'; font-size: 14px; color: #a9a9a9;" />
				</td>
 				<td>&nbsp;&nbsp;&nbsp;<span><input type="button"
											name="waitForCellphoneCode" id="waitForCellphoneCode"
											value="获取验证码" onclick="waitToGetCellphoneCode()"
											style="font-size: 13px" class="btn btn-primary" />
									</span>
				</td>
			</tr>
          <tr align="center">
            <td colspan="4"><input type="submit" class="btn btn-primary" value="上一步" style="width:100px" onclick="javascript:history.go(-1)"/>&nbsp;&nbsp;
            <input type="submit"  class="btn btn-primary" value="下一步" style="width:100px" /></td>
          </tr>
          
          
          </table></td>
      </tr>
    </table>
    </form>
  </div>
  <div style="height:20px;"></div>
</div>
<div id="foot">
    <div> 
      <script language="javascript" src="${ctx }/ui/js/1512101421288942.js"></script>
    </div>
  </div>
</body>
</html>
