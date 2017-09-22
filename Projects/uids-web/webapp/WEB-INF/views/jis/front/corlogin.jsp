<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		$('#loginform').validity(function(){
			$("#sub").toggleClass("sub");
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
						top.location.href = 'corindex.do';
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
	
</script>
<style>
	
</style>
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
        <form action="${url}" method="post" id="loginform">
        <div class="tp"  style="margin-top:30px">
        	<input type="hidden" name="username" id="enc_username" />
        	<input type="hidden" name="password" id="enc_password" />
        	<input type="hidden" name="action" id="action" value="${action}"/>
			<input type="hidden" name="appmark" id="appmark" value="${appmark}"/>
			<input type="hidden" name="gotoUrl" id="gotoUrl" value="${gotoUrl}"/>
        	<table border="0" width="100%" cellpadding="0" cellspacing="0">
            	<tr>
                	<td height="49" style=" line-height:0px; font-size:0px"><img style="cursor:pointer;" id="perlogin" border="0" src="./images/lmz_dl_01.png" width="199" height="49" /></td>
                    <td style=" line-height:0px; font-size:0px"><img style="cursor:pointer;" border="0" src="./images/lmz_dl_02.png" width="198" height="49" /></td>
                </tr>
            </table>
            <table border="0" width="100%"  height="295" cellpadding="0" cellspacing="0" style="background:url(./images/lmz_dl_03.png) repeat-x;">
            	<tr>
                	<td valign="top">
                    	<table border="0" width="100%" cellpadding="0" cellspacing="0" style="margin-top:35px;">
                        	<tr>
                            	<td width="95" height="40" align="right" style="font-weight:bold; font-size:15px; padding-right:10px;">用户名：</td>
                                <td>
                                	<input id="username" placeholder="用户名/工商号/信用代码/组织机构码"  style="line-height:40px; height:40px; width:245px; border:0px; padding-left:8px;padding-right:4px; background:url(./images/lmz_dl_04.jpg) no-repeat;">
                                </td>
                                  <td></td>
                            </tr>
                            <tr>
                            	<td height="30" colspan="2"></td>
                            </tr>
                            <tr>
                            	<td align="right" height="40" style="font-weight:bold; font-size:15px; padding-right:10px;">密　码：</td>
                                <td>
                                	<input id="password" placeholder="请输入密码" type="password" style="line-height:40px; height:40px; width:256px; border:0px; padding-left:8px; background:url(./images/lmz_dl_04.jpg) no-repeat;">
                                </td>
                                <td></td>
                            </tr>
                           
                             <tr>
                            	<td height="30" colspan="2"></td>
                            </tr>
                            <!-- 李德隆于20160323加验证码于此 -->
                            <tr>
                            	<td align="right" height="40" style="font-weight:bold; font-size:15px; padding-right:10px;">验证码：</td>
                                <td>
                                	<input id="randomVeryfyCode" name="randomVeryfyCode" placeholder="请输入验证码"  style="line-height:40px; height:40px; width:100px; border:0px; padding-left:8px; background:url(./images/lmz_dl_04.jpg) no-repeat;">
                             ${verifycodeimg}
                                </td>
                                
                            </tr>
                            
                            
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style=" margin-top:35px;">
                            <tr>
                                <td height="43" width="36"></td>
                                <td width="156">
                                <input type="button" id="submit" class="sub" value="登&nbsp;&nbsp;录"onClick="$('#loginform').submit()" /> 							
                                </td>
                                <td align="center" width="140"><a style="color:#37a1ec; font-size:14px;" href="pwdRecover_select.do?typeEntity=cor">忘记密码？</a></td>
                                <td align="left" width="65"><a style="color:#37a1ec; font-size:14px;" href="register/corregister.do">注册</a></td>
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
		$("#perlogin").click(function(){
			var appmark = $('#appmark').val();
			if(appmark != "") {
				window.location.href = "./perlogin.do?appmark="+appmark;
			}else {		
				window.location.href = "./perlogin.do";
			}
		});
		
		$("form").submit(function(){
			$("#sub").toggleClass("sub");
		});
	});

</script>
<script language="javascript" src="./images/jquery.JPlaceholder.js"/></script>

</body>
</html>