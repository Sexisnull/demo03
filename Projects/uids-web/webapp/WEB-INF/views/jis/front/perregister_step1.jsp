<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人注册</title>

<link type="text/css" rel="stylesheet" href="${ctx}/ui/css/global.css"/>
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
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/loadmask/jquery.loadmask.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/loadmask/jquery.loadmask.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/checkpwd/css/checkpwd.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/checkpwd/checkpwd.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/ui/widgets/hanweb/calendar/css/calendar.css"/>
<script type="text/javascript" src="${ctx}/ui/widgets/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/grzc.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/ui/images/syl_fpqd.css"/>

<script type="text/javascript" src="${ctx}/ui/script/idcardValidity.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/base64.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/jsencrypt.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/rsa_util.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/security.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/Barrett.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/RSA.js"></script>
<script type="text/javascript">
    
    var levelNum;	
	function trim(str){
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}//过滤左右空格
	
	function match(){
		password = trim($('#password').val());
		password2 = trim($('#password2').val());
		if (password == password2){
			return true;
		} else {
			return false;
		}
	}

	window.alert = function (msg,type,fu){
		top.$.messager.alert(' ',msg,type,fu);
	};
	
	$.validity.setup({
		outputMode : "showErr"
	}); //校验错误弹出

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
		//李德隆20160110 日曜 残业 独 访问本网页，自动加载省份。	
		
		var selProvince = document.getElementById('selProvince');
		var CselProvince = document.getElementById('CselProvince');
		$.ajax({
    		type:"post",
	    	url:"searchSubZone.do",
			data:"iid="+0,
			dataType:'json',
			success:function(json){
				if($.trim(json) != ""){
					
						selProvince.options.add(new Option("请选择省",''));
						CselProvince.options.add(new Option("请选择省",''));
					 for(var key in json) {//每次加载的个数，取决于这个省下城市的个数。					
						 selProvince.options.add(new Option(json[key], key));//添加options选项。第一个是文本值，第二个是value值。
						 CselProvince.options.add(new Option(json[key], key));
					 }					
				}else{//没有收到json
					
				}
			}
    	});
		
		$("#loginId").focus();
		
		//个人注册表单提交校验
		$('#registerform').validity(function(){	
			scriptValidity();
		},{			
			beforeSubmit:function(result) {
				//$("#enc_username").val(RSAencode($("#loginId").val()));
				//$("#enc_password").val(RSAencode($("#password").val()));
                enpwd();
			},
			success:function(result){
				if(result.success){//验证码与短信码无误：		
					top.$('body').mask('请稍等，系统正在对您的身份信息进行比对！');	
					register();
				}else{					
					alert(result.message);
					$('#verifyImg').click();
				}			
			}
		});

		$("#divinfo").toggle(function(){
			$(".main").css("height","1400px");
			$(".xxnr2").css("height","530px");
			$("#moreinfo").show();
		},function(){
			$(".main").css("height","900px");
			$("#moreinfo").hide();
		});
		
	});
	
	//RSA加密
	function enpwd() {
        $.ajax({
            type: "post",
            url: "${ctx}/sys/mybatis/getkey",
            data: {},
            dataType: "json",
            async: false,
            success: function(data) {
                //data为获取到的公钥数据
                var pubexponent = data.pubexponent;
                var pubmodules = data.pubmodules;
                setMaxDigits(200);
                var key = new RSAKeyPair(pubexponent, "", pubmodules);
                var password = $("#password").val();
                var encrypedPwd = encryptedString(key, encodeURIComponent(password));
                $("#enc_password").val(encrypedPwd);
                var name = $("#loginId").val();
                $("#enc_username").val(name);
                console.log(name);
                console.log(password);
                console.log(encrypedPwd);
            }
        });
    }
	
    function register(){		
		$.ajax({
			type:"post",
			url:"doperregister", 			
			//data: $("registerform").serialize(), 编译不报错
			//data:	randCode=$('#randCode')& $("registerform").serialize(), 编译不报错的。但带过去是空。			
			success:function(result){  			
				
				if(result.success){
					top.$('body').unmask();
					var appmark = result.message;
					window.location.href='perregsuccess_b';//五秒倒计时，跳回登陆页。
				
				}else{
					top.$('body').unmask();
					
					var message = result.message;
					alert(message);
					$('#verifyImg').click();
				}
				
			}
			
		});
		
	}
	
	/**注册前校验*/
	function scriptValidity(){
		$('#loginId').require('请填写用户名').match('username3','用户名只能由字母、数字、下划线组成,且必须由字母开头,不能以下划线开头和结尾').maxLength(30,'用户名长度不能超过30')
		/* .assert(function(){
			if($(".lg").text() == "用户名由字母、数字、下划线组成，且必须由字母开头"){
				return true;
			}else{
				$('#verifyImg').click();
				return false;
			}
		},"用户名重复")*/; 
		$('#name').require('请填写姓名');
		
		$('#password').require('请填写密码').minLength(6,'密码不少于6位').maxLength(18, "密码最多18个字");
		$('#password2').require('请填写确认密码').assert(match(),'两次输入密码不一致，请正确填写');
		
		$("#papersNumber").require("身份证号码不能为空").assert(IdCardValidate($('#papersNumber').val()),"对不起,您所输入的身份证格式不正确");
		
		$('#email').require('请填写电子邮箱').match('email','对不起，您输入的电子邮箱不合法');
		$('#mobile').require('请填写手机号码').match('mobile','对不起，您输入的手机号码错误');
		
		$('#randCode').require('请填写验证码');
		
		$('#cellphoneShortMessageRandomCodeWritenByGuest').require("请填写短信验证码");
	
	}
	
	/* function checkLoginId(obj){
		var loginid = $(obj).val();
		var sessionid = $('#random').val();
		$.ajax({
			type:"post",
			url:"checkperloginid.do?token="+sessionid,
			data:"loginid="+loginid,
			success:function(msg){
				if($.trim(msg) != ""){
					alert(msg);
					$(".lg").css("color","red");
					$(".lg").text(msg);
				}else{
					$(".lg").css("color","#424242");
					$(".lg").text("用户名由字母、数字、下划线组成");
				}
			}
		});
	} */
	/* function getToken(){
		var charactors="ab1cd2ef3gh4ij5kl6mn7opq8rst9uvw0xyz";
		var token='',i;
		for(j=1;j<=4;j++){
			i = parseInt(10*Math.random()); 　
			token = token + charactors.charAt(i);
		}
		//（HttpServletRequest ） request.getSession().setAttribute("token", token);
		return token;
		}
	 */
	/*
	function actEmail(){
		if($("#email").val() != ""){
			$("#emailspan").show();
		}
		var email = $("#email").val();
		$.ajax({
			type:"post",
			url:"activateemail.do", 
			data : "email=" + email, 
			success:function(result){  
				if(result.success){
					window.open("http://mail."+email.split("@")[1]);
				}else{
					alert(result.message);
				}
			}
		});
	}
	*/
	
	/**性别校验*/
	function sexValidate(sex, idcard){
		var s;
		if('男' == sex){
			s = 'male';
		}else{
			s = 'female';
		}
		if(s == maleOrFemalByIdCard(idcard)){
			return true;
		}else{
			return false;
		}
	}
	
	
	//根据身份证号，自动填写性别
	function fillSex(obj){
		
		var papersNumber = $(obj).val().trim();//拿到输入的身份证号		
		var isPapersNumberLegal = IdCardValidate(papersNumber);//判断身份证号是否合法		
		if(!isPapersNumberLegal&&papersNumber!=""){
			alert("请确认您的身份证号输入是否正确！");
			return;
		}
		//如果输入身份证格式正确 ，则：取第17位，自动填充性别。
		var char17 = papersNumber.charCodeAt(16); //取第17位
		var sex = char17%2;//除以2求余判断奇偶。奇为男，偶为女
		if(sex==1){//男。 eq(0)是男，eq(1)是女			
	        $("input[name='sex']").eq(0).attr("checked","checked");
	        $("input[name='sex']").eq(1).removeAttr("checked");//	       
		}else{		
			$("input[name='sex']").eq(0).removeAttr("checked");
	        $("input[name='sex']").eq(1).attr("checked","checked");
		}		
	}
	
	/*李德隆注销此方法于20160506 仕样变更 重新写
	function actSms(){
		if($("#mobile").val() != ""){
			$("#phonespan").show();
		}
		var mobile = $("#mobile").val();
		$.ajax({
			type:"post",
			url:"activatemobile.do",
			data : "mobile=" + mobile, 
			success:function(result){  
				if(result.success){
					var count = 60;
					var countdown = setInterval(CountDown, 1000);
					function CountDown() { 
						$("#smsreg").attr("disabled", true); 
						$("#smsreg").val("短信激活"+count); 
						if (count == 0) { 
							$("#smsreg").val("短信激活").removeAttr("disabled"); 
							clearInterval(countdown); 
						} 
						count--; 
					}
					alert("已发送短信，注意接收");
				}else{
					alert(result.message);
				}
			}
		});
	}
	
	
	*/

    function addCities(iid,selCityStr,selTownStr) {	//此iid，即相对上级的value。要根据上级的value，加载下级列表。
    	
    	var selCity = document.getElementById(selCityStr);//
    	var selTown = document.getElementById(selTownStr);//var arr = town['t' + iid];

    	
    	selCity.options.length = 0; //清空原来的市option
    	selTown.options.length = 0;	//清空原来的县区options//要是不加这句话，当换省时，区却不变，还是旧值。不置空。
    	selCity.options.add(new Option("请选择市",''));//要是不加这句，省一选好，则市的默认第一个就不是”请选择“三个字，而是”南京‘了。
    	selTown.options.add(new Option("请选择县区",''));
    
    	$.ajax({
			type:"post",
			url:"searchSubZone.do",
			data:"iid="+iid,
			dataType:'json',
			success:function(json){//解析json
				if($.trim(json) != ""){
					
			
					 for(var key in json) {//每次加载的个数，取决于这个省下城市的个数。
						selCity.options.add(new Option(json[key], key));//添加options选项。第一个是文本值，第二个是value值。						
					 }					
				}else{				
				}
			}
		});   	
    }      
   
  
    function addTowns(iid,selTownStr){
    	
    	var selTown = document.getElementById(selTownStr);
   
    	selTown.options.length=0;
    	selTown.options.add(new Option("请选择县区",''));	
    
        	
    	$.ajax({
    		type:"post",
	    	url:"searchSubZone.do",
			data:"iid="+iid,
			dataType:'json',
			success:function(json){
				if($.trim(json) != ""){
				
					 for(var key in json) {//每次加载的个数，取决于这个省下城市的个数。
				
						selTown.options.add(new Option(json[key], key));//添加options选项。第一个是文本值，第二个是value值。
						
					 }					
				}else{//没有收到json
				
				}
			}
    	});
    }
    /**获取短信验证码按钮*/
    function waitToGetCellphoneCode(){	//20160411李德隆写此方法。
		
		var beforeSendCheck = checkFormBeforeSendSms();
		if(!beforeSendCheck){
			return;
		}
		
		var telNum =$("#mobile").val() ;		
		if($.trim(telNum)==""){
			alert("手机号不能为空，请重新输入");
			return ;
		}	
		/* 
		var whetherTelnumExist = checkWhetherTelnumExist(telNum);
		if(whetherTelnumExist)
		if(whetherTelnumExist == "1"){
			alert("手机号码已存在，请重新填写!");
			$("#mobile").attr("value","");
			return;
		}else if(whetherTelnumExist == "0"){
			alert("网络异常!");
			$("#mobile").attr("value","");
			return;
		} */
		
		
		$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");//点击了“发送”按钮后，点击失效。

		var  sendResult= send(success_function,fail_function);		//该方法有6秒延迟	
	}
	/* 
	function checkWhetherTelnumExist(telNum){
		var isSuccess;
		$.ajax({
			async: false, //这个ajax请求则为同步请求，在没有返回值之前，ajax块外是不会执行的。
			type:"post",
			url:"checkWhetherTelnumExist.do",
			data:"telNum="+telNum,	
			dataType:'json',
			success:function(json){
				isSuccess = json.code;				
			},
			error:function(){
				isSuccess = "0";		
			}	
			
			
		});
		
		//alert(isSuccess);
		return  isSuccess;
	} */
	
	/**发送短信操作*/
	function send(success_function,fail_function){	
		var sendResult;		
		//用Ajax发短信		
		$.ajax({
		//	async: false, //这个ajax请求则为同步请求，在没有返回值之前，ajax块外是不会执行的。
	   		type:"post",		   	
	   		url:"sendCellphoneShortMessageUserRe",
	   		data:{"telNum":$("#mobile").val(),"inputByGuest":$("#loginId").val(),"randCode":$("#randCode").val()},		
			dataType:'json',
			success:function(json){//如果成功与第三方连接
			//	alert("与第三方连接成功 ");
			
				if($.trim(json) != ""){	
					var isSuccess = json.success;//此字段我没用。
					var code = json.code;////2发送短信超过次数,1无此用户，0为发短信失败,3发短线成功,4账号不能为空,6验证码不能为空,5随机验证码不正确,7手机号码不能为空,8.手机号存在
					var msg = json.msg;//
					if(code == "3"){
						//$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");
						success_function();	
					}else if(code == "4"){
						fail_function(msg);
					}else if(code == "6"){
						fail_function(msg);
					}else if(code == "7"){
						fail_function(msg);
					}else if(code == "8"){
						fail_function(msg);
					}else if(code == "1"){
						fail_function(msg);
					}else if(code == "5"){
						fail_function(msg);
					}else if(code == "0"){
						fail_function(msg);
					}else{
						alert(msg);
					}						
				}else{						
					fail_function();					
				}	
			
			}  ,
			error:function(){
				fail_function("");			
			}	
			
	   	});			
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
	
	function fail_function(msgRe){
		if(msgRe == "" || typeof(msgRe) =="undefined"){
			alert("短信验证码发送失败！请重新获取！");
			$('#verifyImg').click();
		$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
		}else{
			$("#waitForCellphoneCode").val("点击重新获取验证码").removeAttr("disabled").removeClass("disabled");
			$('#verifyImg').click();
			alert(msgRe);
		}
		
		return;
	}
	
	//在发送短信验证码之前先验证表单
	function checkFormBeforeSendSms(){
		var strAlert = "<div style=\"padding-left: 40px;\">";
		var userNameReg = /^(?!_)(?!.*?_$)[a-zA-Z][a-zA-Z0-9_]+$/;  //用户名正则
		var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;  //电子邮箱正则
		var mobileReg = /^(13|15|18|17)[0-9]{9}$/;  //手机号正则
		var result = true;
		
		var loginId = $("#loginId").val();//获取用户名
		var password = $("#password").val();//获取密码
		var password2 = $("#password2").val();//获取确认密码
		var name = $("#name").val();//获取姓名
		var papersNumber=$("#papersNumber").val();//获取身份证号
		var email = $("#email").val();//获取电子邮箱
		var mobile = $("#mobile").val();//获取手机号
		var randcode = $('#randCode').val();//获取验证码
		//验证用户名
		if(trim(loginId) == ""){
			result = false;
			strAlert += "请输入用户名<br/>";
			$("#loginId").addClass("error-border");
		}else{
			if(!userNameReg.test(trim(loginId))){
				result = false;
				strAlert += "用户名只能由字母、数字、下划线组成,且必须由字母开头<br/>";
				$("#loginId").addClass("error-border");
			}else{
				if(loginId.length > 30){
					result = false;
					strAlert += "用户名长度不能超过30个字符<br/>";
					$("#loginId").addClass("error-border");
				}else{
					$("#loginId").removeClass("error-border");
				}
			}
		}
		
		//验证密码
		if(trim(password) == ""){
			result = false;
			strAlert += "请输入密码<br/>";
			$("#password").addClass("error-border");
		}else{
			if(trim(password).length < 6 || trim(password).length > 18) {
				result = false;
				strAlert += "密码长度在6-18位之间<br/>";
				$("#password").addClass("error-border");
			}else{
				$("#password").removeClass("error-border");
			}
		}
		
		//验证确认密码
		if(trim(password2) == ""){
			result = false;
			strAlert += "请输入确认密码<br/>";
			$("#password2").addClass("error-border");
		}else{
			if(trim(password) != trim(password2)){
				result = false;
				strAlert += "两次密码输入不一致<br/>";
				$("#password2").addClass("error-border");
			}else{
				$("#password2").removeClass("error-border");
			}
		}
		
		//验证姓名
		if(trim(name) == ""){
			result = false;
			strAlert += "请输入姓名<br/>";
			$("#name").addClass("error-border");
		}else{
			$("#name").removeClass("error-border");
		}
		
		
		//验证验证码
		if(trim(randcode) == ""){
			result = false;
			strAlert += "请输入验证码<br/>";
			$("#randCode").addClass("error-border");
		}else{
			$("#randCode").removeClass("error-border");
		}
		
		//验证身份证
		if(trim(papersNumber) == ""){
			result = false;
			strAlert += "请输入身份证号<br/>";
			$("#papersNumber").addClass("error-border");
		}else{
			if(!IdCardValidate(papersNumber)){
				result = false;
				strAlert += "身份证号码格式不正确<br/>";
				$("#papersNumber").addClass("error-border");
			}else{
				$("#papersNumber").removeClass("error-border");
			}
		}
		
		//验证手机号
		if(trim(mobile) == ""){
			result = false;
			strAlert += "请输入手机号码<br/>";
			$("#mobile").addClass("error-border");
		}else{
			if(!mobileReg.test(trim(mobile))){
				result = false;
				strAlert += "手机号码格式不正确<br/>";
				$("#mobile").addClass("error-border");
			}else{
				$("#mobile").removeClass("error-border");
			}
		}
		
		//验证电子邮箱
		if(trim(email) == ""){
			result = false;
			strAlert += "请输入电子邮箱<br/>";
			$("#email").addClass("error-border");
		}else{
			if(!emailReg.test(trim(email))){
				result = false;
				strAlert += "邮箱格式不正确<br/>";
				$("#email").addClass("error-border");
			}else{
				$("#email").removeClass("error-border");
			}
		}
		
		strAlert += "</div>";
		if(!result){
			alert(strAlert, 'warning');
		}
		return result;
		
	}
</script>
<style>
.main{height:800px}
.xxnr2{height:210px}
/*下记：*/
.loadmask-msg div{padding:20px;font-size:14px;background-position: 20px center;padding: 20px 20px 20px 45px;
}
/*mask字大、框宽高。*/
</style>
</head>

<body>
<div>
  <div class="top">
    <div class="pagecon"> 
      <!--<script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101421282896.js"></script>-->
      <script type="text/javascript" src="${ctx}/ui/js/1512101421282897.js"></script>
    </div>
  </div>
  <div class=""> 
    <!--<script language="javascript" src="http://www.gszwfw.gov.cn/script/0/1512101146476750.js"></script>-->
    <script type="text/javascript" src="${ctx}/ui/js/1512101146476751.js"></script>
  </div>
  <div class="nav" style="height:5px;"> 
  </div>
  <div class="mainWth_faren back">
    <div class="main">

	<div class="tb">
    	<img src="${ctx}/ui/images/grzc_03.jpg" />
    </div>
    <div class="bt">
    	个人用户注册
        <div style="float:right;height:45px;line-height:45px;font-size:14px;"><span style="color:#ff0000;margin-right:5px;">*</span>您所填写的个人信息仅供系统使用，不对外公开。</div>
    </div>
    <div style="clear:both"></div>
    <div style="width: 100%;text-align: center;margin-top:20px;">
    <!--  进度图片隐藏起来	<img  src="./images/zhxx.jpg" />-->
    </div>
    <form action="${url}" method="post" id="registerform">
    	<input type="hidden" name="isauth" value="${isauth}" />
    	<input type="hidden" name="authState" value="${authState}" />
    	<input type="hidden" name="random" id="random" value="${random}" />
    	<input type="hidden" name="loginName" id="enc_username" />
		<input type="hidden" name="pwd" id="enc_password" />

    <div class="xxnr">
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            	<td align="right" width="235px">
                	用&nbsp;户&nbsp;&nbsp;名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="loginId" type="text" style=" width:283px; height:30px;" onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  />
                </td>
                <td width="385px" style=" font-size:14px;" class="lg">
                	用户名由字母、数字、下划线组成，且必须由字母开头
                </td>
            </tr>
            
            
            	<tr>
            	<input type="hidden" name=papersType value="1">
           	  </tr>
            
            
             <tr>
            	<td align="right" width="235px">
                	密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="password" type="password" style=" width:283px; height:30px;" onkeyup="javascript:EvalPwd(this.value);" onkeydown="if(event.keyCode==32) {event.returnValue = false;return false;}" onpaste="return false" autocomplete="off"/>
                </td>
                <td width="385px" style=" font-size:14px;">
                	密码不少于6位
                </td>
            </tr>
            <tr>
				<td align="right" class="label">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
				<table cellpadding="0" cellspacing="0" border="0" title="字母加数字加符号就会强" id="pwdpower" style="width: 90%">
					<tr>
						<td id="pweak" style="">弱</td>
						<td id="pmedium" style="">中</td>
						<td id="pstrong" style="">强</td>
					</tr>
				</table>
				</td>
			</tr>
             <tr>
            	<td align="right" width="235px">
                	确认密码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="password2" type="password" style=" width:283px; height:30px;" />
                </td>
                <td width="385px" style=" font-size:14px;">
                	两次密码必须一致
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="name" name="name" type="text"   onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  style=" width:283px; height:30px;"  />
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写与身份证一致的姓名，用于身份信息比对
                </td>
            </tr>
            
            
            
<tr>
            	<td align="right" width="235px">
                	身份证号：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="papersNumber" name="papersNumber"   onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"   onblur="fillSex(this)"  type="text"  style=" width:283px; height:30px;" />
                </td>
                 <td width="385px" style=" font-size:14px;">
                	请填写与身份证一致的身份证号，用于身份信息比对
                </td>
               
            </tr>            
            
            
            
             <tr style="display:none;" >
            	<td align="right" width="235px">
                	性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>

                	<input id = "sex" type="radio" name="sex" value="男"    />男&nbsp;&nbsp;&nbsp;  <!-- checked="checked" -->
                    <input id = "sex" type="radio" name="sex" value="女"     />女
                
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写真实性别
                </td>
            </tr>
            
            
            
             <tr>
            	<td align="right" width="235px">
                	电子邮箱：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="email" name="email"    onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  type="text" style=" width:283px; height:30px;" />
                </td>
                <td width="385px" style=" font-size:14px;">
                	请填写正确邮箱，找回密码时使用
                </td>
            </tr>
             <tr>
            	<td align="right" width="235px">
                	手机号码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="mobile" name="mobile"   onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"   type="text" style=" width:283px; height:30px;"/>
                </td>
                <td width="385px" style=" font-size:14px;">
               		 请填写正确的手机号码
                	<div >
                    	<!--  <input type="button" name="smsreg" id="smsreg" value="短信激活" onclick="actSms();" style="width:88px;height:40px;font-size:13px" class="btn" />-->
                    </div>
                    <div>
                    </div>
                </td>
            </tr>
              <tr>
            	<td align="right" width="235px">
                	验&nbsp;证&nbsp;&nbsp;码：
                </td>
                <td width="20px" style="color:#ff0000; height:50px; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="randCode" name="randCode"    onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"   type="text" style=" width:283px; height:30px;" />
                </td>
                <td width="385px" >
                	${verifycodeimg}
                </td>
            </tr>
            	<!-- 李德隆于20160407遵杨丰锋委托，添加此短信验证功能。接口未实现 。故此框隐藏。验证脚本和方法也还没写。 -->
         <tr>
            	<td align="right" width="235px">
                	手机验证码：
                </td>
                <td width="20px" style="color:#ff0000; padding-top:5px; padding-right:5px;" align="center">*</td>
                <td>
                	<input id="cellphoneShortMessageRandomCodeWritenByGuest"    onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  name="cellphoneShortMessageRandomCodeWritenByGuest" type="text" style=" width:283px; height:30px;"/>
                </td>
                
                <td width="385px" style=" font-size:14px;">
               		<input type="button" id="waitForCellphoneCode" class="btn btn-primary" value="获取短信验证码" style="height:40px;font-size:16px" onclick="waitToGetCellphoneCode();"/>
                </td>
               
            </tr>
            
            <!-- 手机验证完 -->
            <tr>
            
            </tr>
            <td colspan="4">
            
<div style=" width:755px; color:#f00; line-height:24px; font-size:14px; margin-left:140px">注：<br />
1、手机验证码功能目前仅支持甘肃省省内移动、联通、电信三大运营商的手机号，省外手机号暂不支持。<br />
2、手机验证成功后，您可接收在甘肃政务服务网网上办事、留言等的短信通知。
</div>            
            </td>
            
            
             
             <!-- 以下企业法人户籍省市区、详细地址，以及居住地所在省市区、详细地址，李德隆于201601数日做成 -->
					<tr style="display:none;" ><!-- 20160129 朱工要求暂时隐藏户籍与常住地的表单以及脚本校验 -->
						<td align="right" width="235px">户籍省市区：</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>

						<td>
							<table>
								<tr>
									<td><select id="selProvince"
										onchange="addCities(this.value,'selCity','selTown')"
										name="gpresidenceId" style="width: 94px; height: 35px;">
										
									</select>
									</td>
									<td><select id="selCity" name="presidenceId"
										onchange="addTowns(this.value,'selTown')"
										style="width: 94px; height: 35px;">
											<option value="">请选择市</option>
									</select>
									</td>
									<td><select id="selTown" name="residenceId"
										style="width: 94px; height: 35px;">
											<option value="">请选择县区</option>
									</select>
									</td>
								</tr>
							</table></td>



					</tr>

					<tr style="display:none;" >
						<td align="right" width="235px">户籍所在详细地址</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>
						<td><input type="text" id="residenceDetail"
							name="residenceDetail" style="width: 283px; height: 30px;" />
						</td>
					</tr>


					<tr style="display:none;" >
						<td align="right" width="235px">居住地省市区：</td>

						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>					
						<td>
						<table>
							<td><select id="CselProvince"
								onchange="addCities(this.value,'CselCity','CselTown')"
								name="gplivingAreaId" style="width: 94px; height: 35px;">
									

							</select>
							</td>
							<td><select id="CselCity" name="plivingAreaId"
								onchange="addTowns(this.value,'CselTown')"
								style="width: 94px; height: 35px;"">
									<option value="">请选择市</option>
							</select>
							</td>
							<td><select id="CselTown" name="livingAreaId"
								style="width: 94px; height: 35px;">
									<option value="">请选择县区</option>
							</select>
							</td>

						</table>
						</td>

					</tr>
					<tr style="display:none;" >
						<td align="right" width="235px">居住地详细地址</td>
						<td width="20px"
							style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
							align="center">*</td>
						<td><input type="text" id="livingAreaDetail"
							name="livingAreaDetail" style="width: 283px; height: 30px;" />
						</td>
					</tr>
           
            <tr>
           
                <td colspan="4" align="center" >
                <!-- 	<input type="button" class="btn btn-primary" value="下一步" style="height:40px;width:100px;font-size:16px" onclick="window.history.go(-1);"/>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
                	<input type="submit" class="btn btn-primary" value="注  册" style="height:40px;width:100px;font-size:16px" />
                	
                </td>
            </tr>
        </table>
    </div>
   </form>
</div>
  </div>
<div id="foot">
    <div> 
      <script language="javascript" src="${ctx}/ui/js/1512101421288942.js"></script>
    </div>
  </div>
  </div>
</body>
</html>

 