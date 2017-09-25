<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>甘肃政务服务网-登录</title>
<link rel="stylesheet" type="text/css" href="./images/style.css"/>
<link rel="stylesheet" type="text/css" href="./images/syl_fpqd.css"/>
<script type="text/javascript" src="../ui/lib/security/jquery.cookie.js"></script>
<script type="text/javascript" src="../ui/lib/security/base64.js"></script>
<script type="text/javascript" src="../ui/lib/security/jsencrypt.min.js"></script>
<script type="text/javascript" src="../ui/lib/security/rsa_util.js"></script>
<script type="text/javascript" src="../ui/lib/security/security.js"></script>
<script type="text/javascript">
window.alert = function (msg,type,fu){
	top.$.messager.alert(' ',msg,type,fu);
};

$.validity.setup({
	outputMode : "showErr"
}); 

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

	$(function(){
		$("#dynamicPwd").hide();//一上来，隐藏动态获取密码的按钮 
		$('#loginform').validity(function(){
			$('#username').require('请填写用户名');
			$('#password').require('请填写密码');
			$('#randomVeryfyCode').require('请填写验证码');
		},{
			beforeSubmit:function(result) {
				$("#enc_username").val(RSAencode($("#username").val()));
				$("#enc_password").val(RSAencode($("#password").val()));
			},
			success:function(result){		
				
				if(result.success){
					var gotoUrl = $('#gotoUrl').val();
					var gotoUrlFlag = result.params.gotoUrlFlag;
					if(gotoUrlFlag == ""){						
						top.location.href = 'perindex.do';
					}else {
						var ticket = result.params.ticket;
						location.href = gotoUrlFlag + '?ticket='+ticket + "&gotoUrl="+gotoUrl;;
					}
				}else{
					if(result.params.adminerror == "1"){
						alert("系统管理员请从后台登录", "", function(){
							location.href='../login.do'; 
						});
					}else{
						
						alert(result.message);
						$('#verifyImg').click();//李德隆加于20160324
					}
				}
			}
		});
	});
	
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		    $("#submit").trigger("click");
		  }
		});
	
	//获取动态密码  李德隆于20160630 加置灰、以及倒数六十秒。
	function getDynamicPwd(){//[daɪ'næmɪk]		
		var telNum = $("#username").val();//			
		var sendUrl = "./sendDynamicPwd.do";//或直接sendDynamicPwd.do
		
		$("#dynamicPwd").val("正在发送...").attr("disabled", true).css({"color":"#000000" ,"background":"#ccc"});//点击了“发送”按钮后，点击失效。

			
			
		$.ajax({
			type:"post",//post要用引号引起来
			url: sendUrl,
			data : "telNum=" + telNum, 
			dataType:'json',
			success:function(result){					
				if(result.code=="1"){
					alert("动态密码已经发送，请在有效期内登陆！");					
					countBackwards();
				}else{//没发送成功，则重新取码
					alert(result.msg);	
					$("#dynamicPwd").val("获取动态密码").removeAttr("disabled").css({"color":"#ffffff" ,"background":"#4393dc"});
					return;
				}
			},
			error:function(result){//没发送成功，则重新取码
				//Ajax连接未通
				alert("网络故障");
				$("#dynamicPwd").val("获取动态密码").removeAttr("disabled").css({"color":"#ffffff" ,"background":"#4393dc"});
				return;
			}
		});
		
		
		
	}
	
	function countBackwards(){
		var timeLeft = 60;
		var countdown = setInterval(refresh, 1000);
		function refresh(){
			if(timeLeft<0){
				
			//	document.getElementById('waitForCellphoneCode').value="点击重新获取验证码";	
				$("#dynamicPwd").val("获取动态密码").removeAttr("disabled").css({"color":"#ffffff" ,"background":"#4393dc"});
 						
				clearInterval(countdown); 		
				return;
			}
			var waitNotice = ""+timeLeft+"秒后重新获取";			
			document.getElementById('dynamicPwd').value=waitNotice;	
			timeLeft--;
		}
	}
	
	//检验输入的是否是手机号
	function checkIfTelNum(obj){
		var inputByGuest = $(obj).val().trim();
		if(inputByGuest==""){			
			return;
		}
		var phoneReg = /^1[3578]\d{9}$/;
		if(phoneReg.test(inputByGuest) == true){//如果输入的是手机号，则跳出可以动态获取密码的按钮
			$("#dynamicPwd").show();
		}else{//如果输入的是手机号，则隐藏动态获取密码的按钮
			$("#dynamicPwd").hide();
		}
	}
	
	
</script>
</head>
<body style="font-size: 0px">
<div>
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1601131651091634.js"></script> 
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101146476750.js"></script> 
  </div>
  <div class="nav" style="height:5px;">  
  </div>
  <div class="mainWth back">
    <div class="bigtittle"></div>
    
    
    <div class="maincon">
      <div class="main">
        <div class="cx" style="min-height:237px">
            <p style="font-size:17px; padding-top:25px; padding-bottom:6px;font-size:20px;font-weight:700 ">登录说明：</p>
            <p style="font-size:15px; line-height:15px;">1、个人用户请选择个人用户登录。</p>
            <p style="font-size:15px; line-height:15px;">2、单位、组织或者企业法人、社会团体请选择法人用户登录。</p>

        </div>
        <form name=form1 action="${url}" method="post" id="loginform">
        <div class="tp"  style="margin-top:30px;">
        	<input type="hidden" name="action" id="action" value="${action}"/>
			<input type="hidden" name="appmark" id="appmark" value="${appmark}"/>
			<input type="hidden" name="gotoUrl" id="gotoUrl" value="${gotoUrl}"/>
			<input type="hidden" name="username" id="enc_username" />
			<input type="hidden" name="password" id="enc_password" />
        	<table border="0" width="100%" cellpadding="0" cellspacing="0">
        	       
            	<tr>
                	<td height="49"  style=" line-height:0px; font-size:0px"><img style="cursor:pointer;" border="0" src="./images/grdl.png" width="199" height="49" /></td>
                    <td style=" line-height:0px; font-size:0px"><img style="cursor:pointer;" id="corlogin" border="0" src="./images/frdl.png" width="198" height="49" /></td>
                </tr>
            </table>
            <table border="0" width="100%"  height="295" cellpadding="0" cellspacing="0" style="background:url(./images/lmz_dl_03.png) repeat-x;">
            	<tr>
                	<td valign="top">
                    	<table border="0" width="100%" cellpadding="0" cellspacing="0" style="margin-top:35px;">
                        	<tr>
                            	<td width="95" height="40" align="right" style="font-weight:bold; font-size:15px; padding-right:10px;">用户名：</td>
                                <td>
                                	<input id="username" placeholder="请输入登录名/身份证号/手机号" 
                                		onBlur=checkIfTelNum(this);
                                		style="line-height:40px; height:40px; width:256px; border:0px; padding-left:8px; 
                                		background:url(./images/lmz_dl_04.jpg) no-repeat;">
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                            	<td height="30" colspan="2"></td>
                            </tr>
                            <tr>
                            	<td align="right" height="40" style="font-weight:bold; font-size:15px; padding-right:10px;">密　码：</td>
                                <td style="position:relative">
                                	<input id="password" placeholder="请输入密码" type="password" style="line-height:40px; 
                                	height:40px; width:256px; border:0px; padding-left:8px; background:url(./images/lmz_dl_04.jpg) no-repeat;">
                                   <!-- 暂加动态密码于此。 --> 
								<input type=button  id="dynamicPwd" name="dynamicPwd" onclick="getDynamicPwd()" 
								style="height:36px; padding:0 5px; position:absolute;right: 38px;top: 2px; background:#4393DC; color:#fff; 
								border:none; cursor:pointer"value="或获取手机动态密码"  title="点击获取手机动态密码">

                                </td>
                               
                            </tr>
                          
                            
                            
                            <!-- 李德隆于20160323加验证码于此 -->
                             <tr>
                            	<td height="30" colspan="2"></td>
                            </tr>
                          
                            <tr>
                            	<td align="right" height="40" style="font-weight:bold; font-size:15px; padding-right:10px;">验证码：</td>
                                <td>
                                	<input id="randomVeryfyCode" name="randomVeryfyCode" placeholder="请输入验证码"  style="line-height:40px; height:40px; width:100px; border:0px; padding-left:8px; background:url(./images/lmz_dl_04.jpg) no-repeat;">
                                ${verifycodeimg}</td>
                                
                            </tr>
                            
                            
                            
                            
                            
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style=" margin-top:35px;">
                            <tr>
                                <td height="43" width="36"></td>
                                <td width="156" >
                                
                                
                                <input type="button" id="submit" class="sub" value="登&nbsp;&nbsp;录"onClick="$('#loginform').submit()" /> 							
								</td>
                                <td align="center" width="140"><a style="color:#37a1ec; font-size:14px;" href="pwdRecover_select.do?typeEntity=per">忘记密码？</a></td>
                                <td align="left" width="65"><a style="color:#37a1ec; font-size:14px;" href="register/perregister.do">注册</a></td>
                            </tr>
                            <tr><td width="36"></td><td colspan="3"><img src="./images/denglutishi.png"/></td></tr>
                        </table>
                    </td>
                </tr>               
            </table>
            
        </div>
        </form>
      </div>
    </div>
    
    
  </div>
  <div id="foot">
    <div> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421288942.js"></script> 
    </div>
  </div>
</div>
<script type="text/javascript">
$("#shixian,#shixianlist").hover(
    function(){
	$("#shixianlist").show();
	},function(){
	$("#shixianlist").hide();
	}	);
$("#shengji,#shengjilist").hover(
    function(){
	$("#shengjilist").show();
	},function(){
	$("#shengjilist").hide();
	}	);

</script> 
<script type="text/javascript">
$("#nr").css("height","0");
$("#zt").click(function(){
	$(this).addClass("zt1").removeClass("zt");
	$("#bm").addClass("bm1").removeClass("bm");
	$("#nr1").css("height","auto");$("#nr").css("height","0");
	});
	

$("#bm").click(function(){
	$(this).addClass("bm").removeClass("bm1");
	$("#zt").addClass("zt").removeClass("zt1");
	$("#nr").css("height","auto");$("#nr1").css("height","0");
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#corlogin").click(function(){
			var appmark = $('#appmark').val();
			if(appmark != "") {
				window.location.href = "./corlogin.do?appmark="+appmark;
			}else {		
				window.location.href = "./corlogin.do";
			}
		});
	});

</script>
<script language="javascript" src="./images/jquery.JPlaceholder.js"/></script>
</body>
</html>