<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
<link href="./images/wj.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./images/style.css"/>
<link rel="stylesheet" type="text/css" href="./images/syl_fpqd.css"/>
<style>
#scrollUp { display:none;}
</style>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>
<body>
<div id="gsbody">
  <div class="top">
    <div class="pagecon"> 
      <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421282896.js"></script>
    </div>
  </div>
  <div class=""> 
    <script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101146476750.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
  <div class="mainWth_faren back">
  <div id="main" class="mainWth_wj">
  
    <div class="content">
      <div class="top_wj">
        <div class="toplogo"><img src="./images/gszw_06.jpg" width="47" height="47" /></div>
            <div class="topmasg" style="border-bottom:2px solid #D43700">
              <div class="titlemsg">找回密码</div>
            </div>
      </div>
      <div class="zhmm_pic1">
      	<img src="./images/gszw_11.jpg" width="507" height="25" />
        <div class="yzfs">选择验证方式</div>
        <div class="aqyz">安全验证</div>
        <div class="cxmm">重置密码</div>
      </div>    
     
     
     <div class="findpd1">
          <div class="findpdstep1"><img src="./images/gszw_12.jpg" width="15" height="16" /></div>
          <div class="findpdstep2"><a href="recoverPwdByPhone_show.do "><!--  <a href="#" onclick="javascript:alert('暂时未开通手机号码找回服务')">-->1. 通过手机短信找回密码</a></div>
      </div>
     
     
      <div class="findpd2">
          <div class="findpdstep3"><img src="./images/gszw_12.jpg" width="15" height="16" /></div>
          <div class="findpdstep4"><a href="#" onclick="javascript:alert('暂时未开通电子邮件找回服务')">2. 通过电子邮件找回密码</a></div>
      </div>   
               
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
</body>
</html>
