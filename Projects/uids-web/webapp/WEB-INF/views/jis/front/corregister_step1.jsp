<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法人注册</title>
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

<script type="text/javascript" src="../../ui/script/idcardValidity.js"></script>
<script type="text/javascript" src="../../ui/lib/security/jquery.cookie.js"></script>
<script type="text/javascript" src="../../ui/lib/security/base64.js"></script>
<script type="text/javascript" src="../../ui/lib/security/jsencrypt.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/security/rsa_util.js"></script>
<script type="text/javascript" src="../../ui/lib/security/security.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/Barrett.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/plugin/rsa/RSA.js"></script>
<script>
	var levelNum;
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}//过滤左右空格

	window.alert = function(msg, type, fu) {
		top.$.messager.alert(' ', msg, type, fu);
	};

	$.validity.setup({
		outputMode : "showErr"
	}); //校验错误弹出

	window.confirm = function(msg, okCall, cancelCall) {
		top.$.messager.confirm(' ', msg, function(flag) {
			if (flag) {
				if (typeof (okCall) != 'undefined') {
					okCall();
				}
			} else {
				if (typeof (cancelCall) != 'undefined') {
					cancelCall();
				}
			}
		});
	};
	$(function() {
		$('#registerform')
				.validity(
						function() {
							var username3Reg = /^(?!_)(?!.*?_$)[a-zA-Z][a-zA-Z0-9_]+$/;
							var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
							var mobileReg = /^(13|15|18|17)[0-9]{9}$/;
							var type = $('input:radio[name="type"]:checked')
									.val();
							$("input:radio[name='type']").require('请选择法人类型');
							var loginId = $("#loginId").val();//用户名
							var password = $("#password").val(); //密码
							var password2 = $("#password2").val(); //重复密码
							var mobile = $("#mobile").val(); //手机号
							var smscode = $("#smsCode").val(); //手机验证码
							var email = $("#email").val(); //邮箱
							var randCode = $("#randCode").val(); //图片验证码
							var isUsernameConform = username3Reg.test(loginId);
							var isMobileConform = mobileReg.test(mobile);
							var isEmailConform = emailReg.test(email);
							var isPwdLengthCheck = false;
							var isPwdEquals = (password == password2);
							if ($.trim(password).length >= 6
									&& $.trim(password).length <= 18) {
								isPwdLengthCheck = true;
							}
							//1 企业法人  |2 非企业法人
							if (type == 1) {
								var strName = $('#name').val(); //获得企业名称	
								var strRegNumber = $('#regNumber').val(); //企业工商注册号或统一社会信用代码	
								var strRealName = $('#realName').val(); //获得企业法人姓名	
								var strCardNumber = $('#cardNumber').val(); //获得企业法人身份证号	
								var orgNumber1 = $("#orgNumber1").val(); //组织机构代码

								$('#name').require('请填写企业名称');
								$('#regNumber').require('请填写企业工商注册号或统一社会信用代码');
								//判断组织机构编码是否必填
								//统一社会信用代码 18位9开头  工商注册号不是9开头的 统一社会信用代码情况下无需填写
								if (strRegNumber.substring(0, 1) == '9'
										&& strRegNumber.length == 18) {

								} else {
									if (orgNumber1.length != 10
											|| orgNumber1.substring(8, 9) != '-') {
										$('#orgNumber1').require('请填写组织机构代码')
												.assert(false, "组织机构代码格式不正确");
									}
								}
								$('#realName').require('请填写企业法人姓名');
								$('#cardNumber').require('请填写企业法人身份证号码')
										.assert(
												IdCardValidate($('#cardNumber')
														.val()),
												"对不起,您输入的身份证格式不正确");
								var corSex = maleOrFemalByIdCard(strCardNumber); //法人性别 male or female
								if (corSex == 'male') {
									$("#corsex").val("男");
								} else {
									$("#corsex").val("女");
								}
								$("#loginId").require('请填写用户名').match(
										'username3',
										'登录名只能由字母、数字、下划线组成,且必须由字母开头 ')
										.maxLength(30, '登录名长度不能超过30');
								$("#password").require('请填写密码').minLength(6,
										'密码不少于6位').maxLength(18, "密码最多18个字");
								$("#password2").require('请填写确认密码').assert(
										password == password2, '两次输入密码必须一致');
								$("#mobile").require('请填写手机号码').match('mobile',
										'对不起，您输入的手机号码错误');
								$("#smsCode").require('请填写手机验证码');
								$("#email").require('请填写电子邮箱').match('email',
										'对不起，您输入的电子邮箱不合法');
								$("#randCode").require('请填写验证码');
								var isIdConform = IdCardValidate($(
										'#cardNumber').val());//身份证号合法
								if (strName != "" && strRegNumber != ""
										&& strRealName != ""
										&& strCardNumber != "" && loginId != ""
										&& password != "" && password2 != ""
										&& mobile != "" && smscode != ""
										&& email != "" && randCode != ""
										&& isIdConform && isUsernameConform
										&& isMobileConform && isEmailConform
										&& isPwdLengthCheck && isPwdEquals) { //当能过脚本校验时，提示正在认证中请等待。	
									if (strRegNumber.substring(8, 9) == '9'
											&& strRegNumber.length == 18) {
										top.$('body').mask('正在实名认证中，请稍后...');
									} else {
										if (orgNumber1.length != 10
												|| orgNumber1.substring(8, 9) != '-') {
										} else {
											top.$('body')
													.mask('正在实名认证中，请稍后...');
										}
									}
								}
							} else {
								var strName = $('#name2').val(); //获得机构名称
								var orgNumber = $("#orgNumber").val(); //组织机构代码
								var strRealName = $('#realName2').val(); //机构负责人姓名	
								var strCardNumber = $('#cardNumber2').val(); //机构负责人身份证号	

								$('#name2').require('请填写机构名称');
								if (orgNumber.indexOf("-") != -1) {
									$("#orgNumber").attr("name", "orgNumber");
									if (orgNumber.length != 10
											|| orgNumber.substring(8, 9) != '-') {
										$('#orgNumber')
												.require('请填写组织机构代码')
												.assert(false,
														'组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）');
									}
								} else {
									$("#orgNumber").attr("name", "regNumber");
									if ( orgNumber.length != 18) {
										$('#orgNumber')
												.require('请填写统一社会信用代码')
												.assert(false,
														'组织机构代码或统一社会信用代码填写错误，请重新填写！（组织机构代码需包含短横杠“-”）');
									}
								}
								$('#realName2').require('请填写机构负责人姓名');
								$('#cardNumber2')
										.require('请填写机构负责人身份证号')
										.assert(
												IdCardValidate($('#cardNumber2')
														.val()),
												"对不起,您输入的身份证格式不正确");
								var insSex = maleOrFemalByIdCard(strCardNumber); //法人性别 male or female
								if (insSex == 'male') {
									$("#inssex").val("男");
								} else {
									$("#inssex").val("女");
								}

								$("#loginId").require('请填写用户名').match(
										'username3',
										'登录名只能由字母、数字、下划线组成,且必须由字母开头')
										.maxLength(30, '登录名长度不能超过30');
								;
								$("#password").require('请填写密码').minLength(6,
										'密码不少于6位').maxLength(18, "密码最多18个字");
								$("#password2").require('请填写确认密码').assert(
										password == password2, '两次输入密码必须一致');
								$("#mobile").require('请填写手机号码').match('mobile',
										'对不起，您输入的手机号码错误');
								$("#smsCode").require('请填写手机验证码');
								$("#email").require('请填写电子邮箱').match('email',
										'对不起，您输入的电子邮箱不合法');
								$("#randCode").require('请填写验证码');
								var isIdConform = IdCardValidate(strCardNumber);//身份证号合法
								if (strName != "" 
										&& strRealName != ""
										&& strCardNumber != "" && loginId != ""
										&& password != "" && password2 != ""
										&& mobile != "" && smscode != ""
										&& email != "" && randCode != ""
										&& isIdConform && isUsernameConform
										&& isMobileConform && isEmailConform
										&& isPwdLengthCheck && isPwdEquals) { //当能过脚本校验时，提示正在认证中请等待。	
									if (orgNumber.length != 10
											|| orgNumber.substring(8, 9) != '-') {
										
									} else if(orgNumber.length != 18
											|| orgNumber.substring(0, 1) != '9') {
									}else{
										top.$('body').mask('正在实名认证中，请稍后...');
									}

								}
							}
						},
						{

							beforeSubmit : function(result) {
								$("#enc_username").val(
										RSAencode($("#loginId").val()));
								$("#enc_password").val(
										RSAencode($("#password").val()));
							},

							success : function(result) {
								top.$('body').unmask();
								if (result.success) {
									var resultMessage = result.message;
									/*if(resultMessage == 1 || resultMessage == 2 || resultMessage == 3 || resultMessage == 6){
										alert('注册成功，但是实名认证接口可能存在异常，但是不影响您继续使用！','info',function(){
											top.location.href = 'corregsuccess.do';
										});
									}else{*/
									top.location.href = 'corregsuccess.do';
									//}

								} else {
									var resultMessage = result.message;
									/**if(resultMessage == 1 || resultMessage == 2 || resultMessage == 3 || resultMessage == 6){
										alert("实名认证接口异常，请联系网站管理员！",'warning');
									}else */
									if (resultMessage == 4) {
										alert(
												"实名认证失败，请检查企业/机构名称或企业工商注册号/统一社会信用代码是否正确！",
												'warning');
									} else {
										alert(resultMessage, 'warning');
									}
									$('#verifyImg').click();
								}
							}
						});

		$(".fqyfr").hide();
		$("#qyRadio").click(function() {
			$("#frType").val("1");
			$(".fqyfr").hide();
			$(".qyfr").show();
			showOrHideRegNumber();
		});
		$("#fqyRadio").click(function() {
			$("#frType").val("0");
			$(".fqyfr").show();
			$(".qyfr").hide();
		});
	});

	function sexValidate(sex, idcard) {
		var s;
		if ('男' == sex) {
			s = 'male';
		} else {
			s = 'female';
		}
		if (s == maleOrFemalByIdCard(idcard)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *企业工商注册号或统一社会信用代码  信用代码是18位， 工商码为13或15位 ，输入工商码显示：组织机构代码
	 */
	$(function() {
		$("#regNumber").bind(
				"input propertychange",
				function() {
					if ($(this).val().substring(0, 1) == '9'
							&& $(this).val().length == 18) {
						$("#tr_orgNumber1").hide();
					} else {
						$("#tr_orgNumber1").show();
					}
				});

	});

	function showOrHideRegNumber() {
		var orgNumber = $("#regNumber").val();
		if (orgNumber.substring(0, 1) == '9' && orgNumber.length == 18) {
			$("#tr_orgNumber1").hide();
		} else {
			$("#tr_orgNumber1").show();
		}
	}

	/*
	 *检查登录名是否被注册
	 */
	/* function checkLoginId(obj){
		var loginid = $(obj).val();
		$.ajax({
			type:"post",
			url:"checkcorloginid.do",
			data:"loginid="+loginid,
			success:function(msg){
				if($.trim(msg)!= ""){
					$(".lg").css("color","red");
					$(".lg").text(msg);
				}else{
					$(".lg").css("color","#424242");
					$(".lg").text("由字母、数字和下划线组成，且必须由字母开头");
				}
			}
		});
	}
	 */
	/*
	 *检查手机号是否被注册(不再检查，用户可能有多个公司)
	 */
	/*function checkMobileExist(mobileCheck){
		var telNum = $("#mobile").val();
		$.ajax({
			type:"post",
			url:"checkcormobile.do",
			async: false,
			data:"telNum="+telNum,
			success:function(result){
				if($.trim(result)!= ""){
					mobileCheck = true;
				}else{
					mobileCheck = false;
				}
			}
		});
		return mobileCheck;
	}*/

	//短信发送
	function waitToGetCellphoneCode() {
		var type = $('input:radio[name="type"]:checked').val();
		/*var mobileCheck = false;
		mobileCheck = checkMobileExist(mobileCheck);
		 if(mobileCheck){
			alert("该手机号已被注册！");
			return ;
		} */
		var beforeSendCheck = checkFormBeforeSendSms();
		if (!beforeSendCheck) {
			return;
		}
		var telNum = $("#mobile").val();
		if ($.trim(telNum) == "") {
			alert("手机号不能为空，请重新输入");
			return;
		}
		$("#sendSms").val("正在发送短信验证码...").attr("disabled", true).addClass(
				"disabled");//点击了"发送"按钮后，点击失效。

		var sendResult = send(success_function, fail_function); //该方法有6秒延迟	
	}

	function send(success_function, fail_function) {
		var sendResult;
		//用Ajax发短信		
		$.ajax({
			//	async: false, //这个ajax请求则为同步请求，在没有返回值之前，ajax块外是不会执行的。
			type : "post",
			url : "sendCellphoneShortMessageCorRe.do",
			data : {
				"telNum" : $("#mobile").val(),
				"inputByGuest" : $("#loginId").val(),
				"randCode" : $("#randCode").val()
			},
			dataType : 'json',
			success : function(json) {//如果成功与第三方连接
				//	alert("与第三方连接成功 ");

				if ($.trim(json) != "") {
					var isSuccess = json.success;//此字段我没用。
					var code = json.code;////2发送短信超过次数,1无此用户，0为发短信失败,3发短线成功,4账号不能为空,6验证码不能为空,5随机验证码不正确
					var msg = json.msg;//				
					if (code == "3") {
						//$("#waitForCellphoneCode").val("正在发送短信验证码...").attr("disabled", true).addClass("disabled");
						success_function();
					} else if (code == "4") {
						fail_function(msg);
					} else if (code == "6") {
						fail_function(msg);
					} else if (code == "1") {
						fail_function(msg);
					} else if (code == "5") {
						fail_function(msg);
					} else if (code == "0") {
						fail_function(msg);
					} else {
						alert(msg);
					}
				} else {
					fail_function();
				}

			},
			error : function() {
				fail_function("");
			}

		});
	}

	function success_function() {
		alert("短信验证码已发送，请注意查收！");
		$("#sendSms").attr("disabled", true);//点击了"发送"按钮后，点击失效。
		$("#sendSms").addClass("disabled");//置灰
		var timeLeft = 60;
		var countdown = setInterval(refresh, 1000);
		function refresh() {
			if (timeLeft < 0) {
				$("#sendSms").val("点击重新获取验证码").removeAttr("disabled")
						.removeClass("disabled");
				clearInterval(countdown);
				return;
			}
			var waitNotice = "（" + timeLeft + "）秒后重新获取验证码";
			document.getElementById('sendSms').value = waitNotice;
			timeLeft--;
		}
	}

	function fail_function(msgRe) {
		//alert("短信验证码发送失败！请重新获取！");
		if (msgRe == "" || typeof (msgRe) == "undefined") {
			alert("短信验证码发送失败！请重新获取！");
			$('#verifyImg').click();
			$("#sendSms").val("点击重新获取验证码").removeAttr("disabled").removeClass(
					"disabled");
		} else {
			$("#sendSms").val("点击重新获取验证码").removeAttr("disabled").removeClass(
					"disabled");
			$('#verifyImg').click();
			alert(msgRe);
		}

		return;
	}

	/*
	 *发送短信验证码之前验证表单
	 */
	function checkFormBeforeSendSms() {
		//1 企业法人  |2 非企业法人
		var strAlert = "<div style=\"padding-left: 40px;\">";
		var userNameReg = /^(?!_)(?!.*?_$)[a-zA-Z][a-zA-Z0-9_]+$/;
		var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		var mobileReg = /^(13|15|18|17)[0-9]{9}$/;
		var result = true;
		var type = $('input:radio[name="type"]:checked').val();
		if (type == 1) {
			var strName = $('#name').val(); //获得企业名称	
			var strRegNumber = $('#regNumber').val(); //企业工商注册号或统一社会信用代码	
			var strRealName = $('#realName').val(); //获得企业法人姓名	
			var strCardNumber = $('#cardNumber').val(); //获得企业法人身份证号	
			var orgNumber1 = $("#orgNumber1").val(); //组织机构代码
			if (trim(strName) == "") {
				result = false;
				strAlert += "请输入企业名称<br/>";
				$("#name").addClass("error-border");
			} else {
				$("#name").removeClass("error-border");
			}

			if (trim(strRegNumber) == "") {
				result = false;
				strAlert += "请输入企业工商注册号或统一社会信用代码<br/>";
				$("#regNumber").addClass("error-border");
			} else {
				$("#regNumber").removeClass("error-border");
			}
			if (strRegNumber.substring(0, 1) == '9'
					&& strRegNumber.length == 18) {
				$("#orgNumber1").removeClass("error-border");
			} else {
				if (trim(orgNumber1) == "") {
					result = false;
					strAlert += "请输入组织机构代码<br/>";
					$("#orgNumber1").addClass("error-border");
				} else {
					if (orgNumber1.length != 10
							|| orgNumber1.substring(8, 9) != '-') {
						result = false;
						strAlert += "组织机构代码格式不正确<br/>";
						$("#orgNumber1").addClass("error-border");
					} else {
						$("#orgNumber1").removeClass("error-border");
					}
				}
			}
			if (trim(strRealName) == "") {
				result = false;
				strAlert += "请输入企业法人姓名<br/>";
				$("#realName").addClass("error-border");
			} else {
				$("#realName").removeClass("error-border");
			}
			if (trim(strCardNumber) == "") {
				result = false;
				strAlert += "请输入企业法人身份证号码<br/>";
				$("#cardNumber").addClass("error-border");
			} else {
				if (!IdCardValidate(strCardNumber)) {
					result = false;
					strAlert += "企业法人身份证号码格式不正确<br/>";
					$("#cardNumber").addClass("error-border");
				} else {
					$("#cardNumber").removeClass("error-border");
				}
			}
		} else {
			var strName = $('#name2').val(); //获得机构名称
			var orgNumber = $("#orgNumber").val(); //组织机构代码
			var strRealName = $('#realName2').val(); //机构负责人姓名	
			var strCardNumber = $('#cardNumber2').val(); //机构负责人身份证号
			if (trim(strName) == "") {
				result = false;
				strAlert += "请输入机构名称<br/>";
				$("#name2").addClass("error-border");
			} else {
				$("#name2").removeClass("error-border");
			}
			if (trim(orgNumber) == "") {
				result = false;
				strAlert += "请输入组织机构代码或统一社会信用代码<br/>";
				$("#orgNumber").addClass("error-border");
			} else {
				if (orgNumber.indexOf("-") != -1) {
					$("#orgNumber").attr("name", "orgNumber");
					if (orgNumber.length != 10
							|| orgNumber.substring(8, 9) != '-') {
						result = false;
						
						strAlert += "组织机构代码格式不正确<br/>";
						$("#orgNumber").addClass("error-border");
					} else {
						$("#orgNumber").removeClass("error-border");
					}
				} else {
					$("#orgNumber").attr("name", "regNumber");
					if (orgNumber.length != 18) {
						result = false;
						strAlert += "统一社会信用代码格式不正确<br/>";
						$("#orgNumber").addClass("error-border");
					} else {
						$("#orgNumber").removeClass("error-border");
					}
				}
			}
			if (trim(strRealName) == "") {
				result = false;
				strAlert += "请输入机构负责人姓名<br/>";
				$("#realName2").addClass("error-border");
			} else {
				$("#realName2").removeClass("error-border");
			}
			if (trim(strCardNumber) == "") {
				result = false;
				strAlert += "请输入机构负责人身份证号<br/>";
				$("#cardNumber2").addClass("error-border");
			} else {
				if (!IdCardValidate(strCardNumber)) {
					result = false;
					strAlert += "机构负责人身份证号码格式不正确<br/>";
					$("#cardNumber2").addClass("error-border");
				} else {
					$("#cardNumber2").removeClass("error-border");
				}
			}
		}
		var loginId = $("#loginId").val();//用户名
		var password = $("#password").val(); //密码
		var password2 = $("#password2").val(); //重复密码
		var mobile = $("#mobile").val(); //手机号
		var email = $("#email").val(); //邮箱
		if (trim(loginId) == "") {
			result = false;
			strAlert += "请输入用户名<br/>";
			$("#loginId").addClass("error-border");
		} else {
			if (!userNameReg.test(loginId)) {
				result = false;
				strAlert += "用户名只能由字母、数字、下划线组成,且必须由字母开头<br/>";
				$("#loginId").addClass("error-border");
			} else {
				if (loginId.length > 30) {
					result = false;
					strAlert += "用户名长度不能超过30个字符<br/>";
					$("#loginId").addClass("error-border");
				} else {
					$("#loginId").removeClass("error-border");
				}
			}
		}
		if (trim(password) == "") {
			result = false;
			strAlert += "请输入密码<br/>";
			$("#password").addClass("error-border");
		} else {
			if (trim(password).length < 6 || trim(password).length > 18) {
				result = false;
				strAlert += "密码长度在6-18位之间<br/>";
				$("#password").addClass("error-border");
			} else {
				$("#password").removeClass("error-border");
			}
		}

		if (trim(password2) == "") {
			result = false;
			strAlert += "请输入确认密码<br/>";
			$("#password2").addClass("error-border");
		} else {
			if (trim(password) != trim(password2)) {
				result = false;
				strAlert += "两次密码输入不一致<br/>";
				$("#password2").addClass("error-border");
			} else {
				$("#password2").removeClass("error-border");
			}
		}

		if (trim(mobile) == "") {
			result = false;
			strAlert += "请输入手机号码<br/>";
			$("#mobile").addClass("error-border");
		} else {
			if (!mobileReg.test(trim(mobile))) {
				result = false;
				strAlert += "手机号码格式不正确<br/>";
				$("#mobile").addClass("error-border");
			} else {
				$("#mobile").removeClass("error-border");
			}
		}
		if (trim(email) == "") {
			result = false;
			strAlert += "请输入电子邮箱<br/>";
			$("#email").addClass("error-border");
		} else {
			if (!emailReg.test(trim(email))) {
				result = false;
				strAlert += "邮箱格式不正确<br/>";
				$("#email").addClass("error-border");
			} else {
				$("#email").removeClass("error-border");
			}
		}
		strAlert += "</div>";
		if (!result) {
			alert(strAlert, 'warning');
		}
		return result;

	}
</script>
<style>
.main {
	min-height: 1200px
}

.zhxxnr {
	width: 955px;
	height: 150px;
	color: #4d4d4d;
	margin-top: 20px;
	margin-left: 20px;
	float: left;
}

.frzc {
	width: 1000px;
	height: 750px;
	color: #4d4d4d;
	margin-top: 25px;
	margin-left: 0px;
	float: left;
}

.frzc td {
	height: 40px;
}
/*下记：*/
.loadmask-msg div {
	padding: 20px;
	font-size: 14px;
	background-position: 20px center;
	padding: 20px 20px 20px 45px;
}
</style>
</head>
<body>

	<div>
		<div class="top">
			<div class="pagecon">
				<script language="javascript"
					src="http://www.gszwfw.gov.cn/script/0/1512101421282896.js"></script>
			</div>
		</div>
		<div class="">
			<script language="javascript"
				src="http://www.gszwfw.gov.cn/script/0/1512101146476750.js"></script>
		</div>
		<div class="nav" style="height: 5px;"></div>
		<div class="mainWth_faren back">
			<div class="main" style="height: 1250px">

				<div class="tb">
					<img src="${ctx}/ui/images/frzc_03.jpg" />
				</div>
				<div class="bt">
					法人用户注册
					<div
						style="float: right; height: 45px; line-height: 45px; font-size: 14px;">
						<span style="color: #ff0000; margin-right: 5px;">*</span>您所填写的个人信息仅供系统使用，不对外公开。
					</div>
				</div>
				<div class="zhxx2"
					style="width: 955px; height: 30px; background-color: #f7f7f7; text-indent: 20px; line-height: 30px; margin-top: 20px; margin-left: 20px; float: left; font-weight: bold;">认证信息</div>
				<form action="${url}" method="post" id="registerform"
					name="registerform">
					<!-- 隐藏表单值 -->
					<input type="hidden" id="corsex" name="corsex" value="" />
					<!-- 企业法人性别 -->
					<input type="hidden" id="inssex" name="inssex" value="" />
					<!-- 非企业法人性别 -->

					<input type="hidden" name="loginName" id="enc_username" /> <input
						type="hidden" name="pwd" id="enc_password" />

					<div class="frzc">
						<table width="85%" height="100%" border="0" cellspacing="0"
							cellpadding="0" align="center">


							<tr>
								<td width="350px" align="right">注：</td>
								<td width="20px" align="center"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"></td>
								<td colspan="2">工商注册的企业或个体户请选择“企业法人”进行注册！</td>
							</tr>

							<tr>
								<td align="right" width="350px">法人类型：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="radio" id="qyRadio" name="type" value="1"
									checked="checked" />企业法人 <input type="radio" id="fqyRadio"
									name="type" value="2" />非企业法人</td>
							</tr>
							<tr class="qyfr">
								<td align="right" width="295px">企业名称：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="name" name="name"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填写正确的企业全称</td>
							</tr>
							<tr class="qyfr">
								<td align="right" width="280px">企业工商注册号/统一社会信用代码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="regNumber" name="regNumber"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
							</tr>
							<tr id="tr_orgNumber1" class="qyfr" style="display: none">
								<td align="right" width="235px">组织机构代码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="orgNumber1" name="orgNumber"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填写短横杠，如有字母须大写</td>

							</tr>
							<tr class="qyfr">
								<td align="right" width="235px">企业法人姓名：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="realName" name="realName"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填写与身份证一致的姓名，用于身份信息比对</td>
							</tr>
							<tr class="qyfr">
								<td align="right" width="235px">企业法人民族：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><select id="nation" name="nation"
									style="width: 288px; height: 35px;">
										<option value="汉族">汉族</option>
										<option value="蒙古族">蒙古族</option>
										<option value="回族">回族</option>
										<option value="藏族">藏族</option>
										<option value="维吾尔族">维吾尔族</option>
										<option value="苗族">苗族</option>
										<option value="彝族">彝族</option>
										<option value="壮族">壮族</option>
										<option value="布依族">布依族</option>
										<option value="朝鲜族">朝鲜族</option>
										<option value="满族">满族</option>
										<option value="侗族">侗族</option>
										<option value="瑶族">瑶族</option>
										<option value="白族">白族</option>
										<option value="土家族">土家族</option>
										<option value="哈尼族">哈尼族</option>
										<option value="哈萨克族">哈萨克族</option>
										<option value="傣族">傣族</option>
										<option value="黎族">黎族</option>
										<option value="傈僳族">傈僳族</option>
										<option value="佤族">佤族</option>
										<option value="畲族">畲族</option>
										<option value="高山族">高山族</option>
										<option value="拉祜族">拉祜族</option>
										<option value="水族">水族</option>
										<option value="东乡族">东乡族</option>
										<option value="纳西族">纳西族</option>
										<option value="景颇族">景颇族</option>
										<option value="柯尔克孜族">柯尔克孜族</option>
										<option value="土族">土族</option>
										<option value="达斡尔族">达斡尔族</option>
										<option value="仫佬族">仫佬族</option>
										<option value="羌族">羌族</option>
										<option value="布朗族">布朗族</option>
										<option value="撒拉族">撒拉族</option>
										<option value="毛南族">毛南族</option>
										<option value="仡佬族">仡佬族</option>
										<option value="锡伯族">锡伯族</option>
										<option value="阿昌族">阿昌族</option>
										<option value="普米族">普米族</option>
										<option value="塔吉克族">塔吉克族</option>
										<option value="怒族">怒族</option>
										<option value="乌孜别克族">乌孜别克族</option>
										<option value="俄罗斯族">俄罗斯族</option>
										<option value="鄂温克族">鄂温克族</option>
										<option value="德昂族">德昂族</option>
										<option value="保安族">保安族</option>
										<option value="裕固族">裕固族</option>
										<option value="京族">京族</option>
										<option value="塔塔尔族">塔塔尔族</option>
										<option value="独龙族">独龙族</option>
										<option value="鄂伦春族">鄂伦春族</option>
										<option value="赫哲族">赫哲族</option>
										<option value="门巴族">门巴族</option>
										<option value="珞巴族">珞巴族</option>
										<option value="基诺族">基诺族</option>
								</select></td>
							</tr>
							<tr class="qyfr">
								<td align="right" width="235px">企业法人身份证号码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="cardNumber" name="cardNumber"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填正确的身份证号，用于身份信息比对</td>
							</tr>
							<!--  非企业法人块开始 -->
							<tr class="fqyfr">
								<td align="right" width="235px">机构名称：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="name2" name="name"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填正确的机构名称</td>
							</tr>
							<tr class="fqyfr">
								<td align="right" width="235px">组织机构代码/统一社会信用代码</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="orgNumber" name="orgNumber"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填写组织机构代码或统一社会信用代码</td>
							</tr>
							<tr class="fqyfr">
								<td align="right" width="235px">机构负责人姓名：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="realName2" name="realName"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填写与身份证一致的姓名，用于身份信息比对</td>
							</tr>
							<tr class="fqyfr">
								<td align="right" width="235px">机构负责人民族：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><select id="nation" name="nation"
									style="width: 288px; height: 35px;">
										<option value="汉族">汉族</option>
										<option value="蒙古族">蒙古族</option>
										<option value="回族">回族</option>
										<option value="藏族">藏族</option>
										<option value="维吾尔族">维吾尔族</option>
										<option value="苗族">苗族</option>
										<option value="彝族">彝族</option>
										<option value="壮族">壮族</option>
										<option value="布依族">布依族</option>
										<option value="朝鲜族">朝鲜族</option>
										<option value="满族">满族</option>
										<option value="侗族">侗族</option>
										<option value="瑶族">瑶族</option>
										<option value="白族">白族</option>
										<option value="土家族">土家族</option>
										<option value="哈尼族">哈尼族</option>
										<option value="哈萨克族">哈萨克族</option>
										<option value="傣族">傣族</option>
										<option value="黎族">黎族</option>
										<option value="傈僳族">傈僳族</option>
										<option value="佤族">佤族</option>
										<option value="畲族">畲族</option>
										<option value="高山族">高山族</option>
										<option value="拉祜族">拉祜族</option>
										<option value="水族">水族</option>
										<option value="东乡族">东乡族</option>
										<option value="纳西族">纳西族</option>
										<option value="景颇族">景颇族</option>
										<option value="柯尔克孜族">柯尔克孜族</option>
										<option value="土族">土族</option>
										<option value="达斡尔族">达斡尔族</option>
										<option value="仫佬族">仫佬族</option>
										<option value="羌族">羌族</option>
										<option value="布朗族">布朗族</option>
										<option value="撒拉族">撒拉族</option>
										<option value="毛南族">毛南族</option>
										<option value="仡佬族">仡佬族</option>
										<option value="锡伯族">锡伯族</option>
										<option value="阿昌族">阿昌族</option>
										<option value="普米族">普米族</option>
										<option value="塔吉克族">塔吉克族</option>
										<option value="怒族">怒族</option>
										<option value="乌孜别克族">乌孜别克族</option>
										<option value="俄罗斯族">俄罗斯族</option>
										<option value="鄂温克族">鄂温克族</option>
										<option value="德昂族">德昂族</option>
										<option value="保安族">保安族</option>
										<option value="裕固族">裕固族</option>
										<option value="京族">京族</option>
										<option value="塔塔尔族">塔塔尔族</option>
										<option value="独龙族">独龙族</option>
										<option value="鄂伦春族">鄂伦春族</option>
										<option value="赫哲族">赫哲族</option>
										<option value="门巴族">门巴族</option>
										<option value="珞巴族">珞巴族</option>
										<option value="基诺族">基诺族</option>
								</select></td>
							</tr>
							<tr class="fqyfr">
								<td align="right" width="235px">机构负责人身份证号码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="cardNumber2" name="cardNumber"
									style="width: 283px; height: 30px;"
									onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" />
								</td>
								<td width="" style="font-size: 14px; padding-left: 20px;">请填正确的身份证号，用于身份信息比对</td>
							</tr>
							<!-- 正式注册项 -->
							<tr>
								<td colspan="4">
									<div>
										<div
											style="width: 955px; height: 30px; background-color: #f7f7f7; text-indent: 20px; line-height: 30px; margin-top: 20px; margin-left: 20px; float: left; font-weight: bold; margin-bottom: 20px;">注册信息</div>

									</div></td>
							</tr>
							<tr>
								<td align="right" width="235px">用户名：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="text" id="loginId"
									style="width: 283px; height: 30px;" /></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;"
									class="lg">由字母、数字和下划线组成，且必须由字母开头</td>
							</tr>
							<tr>
								<td align="right" width="235px">密码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input type="password" id="password"
									style="width: 283px; height: 30px;"
									onKeyUp="javascript:EvalPwd(this.value);"
									onKeyDown="if(event.keyCode==32) {event.returnValue = false;return false;}"
									onpaste="return false" autocomplete="off" /></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;">
									密码不少于6位</td>
							</tr>

							<tr>
								<td align="right" class="label">&nbsp;</td>
								<td width="20px" style="color: #ff0000;" align="center"></td>
								<td>
									<table cellpadding="0" cellspacing="0" border="0"
										title="字母加数字加符号就会强" id="pwdpower" style="width: 100%">
										<tr>
											<td id="pweak" style="height: 20px;">弱</td>
											<td id="pmedium" style="height: 20px;">中</td>
											<td id="pstrong" style="height: 20px;">强</td>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td align="right" width="235px">确认密码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input id="password2" type="password"
									style="width: 283px; height: 30px;" /></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;">
									两次密码必须一致</td>
							</tr>


							<tr>
								<td align="right" width="235px">电子邮箱：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input id="email" name="email" type="text"
									style="width: 283px; height: 30px;" /></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;"
									class="lg_email">请填写正确的电子邮箱</td>
							</tr>



							<tr>
								<td align="right" width="235px">手机号码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input id="mobile" name="mobile" type="text"
									style="width: 283px; height: 30px;" />
								<!-- onblur="checkMobile(this)"取消 --></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;"
									class="lg_mobile">请填写正确的手机号码</td>
							</tr>

							<tr>
								<td align="right" width="235px">验证码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input id="randCode" name="randCode" type="text"
									style="width: 283px; height: 30px;" /></td>
								<td width="385px">${verifycodeimg}</td>
							</tr>

							<tr>
								<td align="right" width="235px">手机验证码：</td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center">*</td>
								<td><input id="smsCode" name="smsCode" type="text"
									style="width: 283px; height: 30px;" /></td>
								<td width="385px" style="font-size: 14px;">
									&nbsp;&nbsp;&nbsp;<input type="button" id="sendSms"
									class="btn btn-primary" value="获取短信验证码"
									style="height: 40px; font-size: 16px"
									onClick="waitToGetCellphoneCode();" /></td>
							</tr>

							<tr>
								<td colspan="4" style="width: 660px;">
									<div
										style="color: #f00; line-height: 24px; font-size: 14px; margin-left: 185px; margin-bottom: 10px;">
										<div>注：</div>
										<div>1. 手机验证码功能目前仅支持甘肃省省内移动、联通、电信三大运营商的手机号，省外手机号暂不支持。</div>
										<div>2. 手机验证成功后，您可接收在甘肃政务服务网网上办事、留言等的短信通知。</div>
									</div></td>
							</tr>



							<tr>
								<td align="right" width="235px"></td>
								<td width="20px"
									style="color: #ff0000; padding-top: 5px; padding-right: 5px;"
									align="center"></td>
								<td></td>
								<td width="385px"></td>
							</tr>

							<tr>
								<td align="right" width="235px"></td>
								<td width="20px"
									style="color: #ff0000; padding-top: 10px; padding-right: 5px;"
									align="center"></td>
								<td align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="submit" class="btn btn-primary" value="注册"
									style="height: 40px; width: 100px; font-size: 16px" /></td>
								<td width="385px" style="font-size: 14px; padding-left: 20px;"></td>
							</tr>

							<tr align="center">
								<td colspan="4" align="left"
									style="font-size: 14px; padding: 40px 200px;">温馨提示：<br>
									1. 公民身份号码长度应是十八位。<br> 2.
									前十七位是数字,第十八位是校验位(由0到9十个数字或X构成)，字母X必须是大写。<br> 3.
									姓名长度应不少于两个汉字。<br> 4. 姓名中间不允许有空格。<br> 5.
									少数民族姓名间隔符用"•"（GB 13000编码为00B7）表示，但不许连续使用。<br> 6.
									姓名中不应含有非法字符。</td>

							</tr>
						</table>
					</div>
				</form>

				<div class="">
					<table width="100%" height="100%" border="0" cellspacing="0"
						cellpadding="0"
						style="border-collapse: separate; border-spacing: 3px 15px;">

					</table>
				</div>
			</div>
		</div>
		<div id="foot">
			<div>
				<script language="javascript"
					src="http://www.gszwfw.gov.cn/script/0/1512101421288942.js"></script>
			</div>
		</div>
	</div>
</body>

</html>
