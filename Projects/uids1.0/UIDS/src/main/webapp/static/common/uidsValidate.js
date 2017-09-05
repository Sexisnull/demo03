/**
 * @sunbw
 * 在input中加入对应的标签即可
 * 因为是弹出框提示信息，要做到让用户明确那个位置的信息填错了
 * 要做到，每个input框中都要有name属性，name属性的值作为自定义时的标签
 * 
 */
//validate 自定义校验
function gswwValidate(formObject){
	formObject.validate({
		 debug: true,
		 rules:{//自定义校验规则
			 ip:{
				 checkIp:["a","f"],
			 },
			 identity:{
				 isChinaChar:["a","f"],
				 isNumberOrLetter:["a","f"],
				 checkIdCard:["a","f"]
			 },
			 mobile:{
				 digits:true,
				 checkMobile:["a","f"]
			 },
			 email:{
				 checkEmail:["a","f"]
			 },
			 url:{
				 checkUrl:["a","f"]
			 },
			 "account-pass":{
				 checkPwd:["a","f"],
			 },
			 "tb-account-name":{
				 checkName:["a","f"]
			 },
			 "tb-account-pass":{
				 required:true,
				 checkPwd:["a","f"],
			 },
			 "tb-repeat-pass": {
				 required:true,
			     equalTo: "#tb-account-pass"
			 },
			 "tb-account-nickname":{
				 checkName:["a","f"]
			 },
			 name:{
				 checkXingMing:["a","f"]
			 },
			 allname:{
				 checkName:["a","f"]
			 },
			 "account-name":{
				 checkName:["a","f"]
			 },
			 "account-nickname":{
				 checkName:["a","f"]
			 },
			 desc:{
				 required:false,
				 checkDesc:["a","f"]
			 },
			 mark:{
				 checkName:["a","f"]
			 },
			 code:{
				 checkName:["a","f"],
				 isChinaChar:["a","f"]
			 },
			 "org-code":{
				 checkOrgCode:["a","f"]
			 },
			 fax:{
				 required:false,
				 checkfax:["a","f"]
			 },
			 "work-phone":{
				 required:false,
				 checkWphone:["a","f"]
			 },
			 "home-tel":{
				 required:false,
				 checkWphone:["a","f"]
			 },
			 "company-tel":{
				 required:false,
				 checkWphone:["a","f"]
			 },
			 appDesc:{
				 required:false,
				 checkDesc:["a","f"]
			 },
			 msn:{
				 isChinaChar:["a","f"]
			 },
			 copyright:{
				 required:true,
				 checkName:["a","f"]
			 },
			 regSmsBusiContent:{
				 required:true,
				 checkDesc:["a","f"]
			 },
			 findPwdSmsContent:{
				 required:true,
				 checkDesc:["a","f"]
			 },
			 bindUserSmsContent:{
				 required:true,
				 checkDesc:["a","f"]
			 },
			 sysName:{
				 checkName:["a","f"]
			 },
			 sysUrl:{
				 required:true,
				 checkUrl:["a","f"]
			 },
			 "corporate-name":{
				 checkName:["a","f"]
			 },
			 "person-name":{
				 checkName:["a","f"]
			 },
			 "business-scope":{
				 checkfw:["a","f"]
			 },
			 "credit-code":{
				 gszcOrXydm:["a","f"]
			 },
			 "user-post":{
				 checkfw:["a","f"]
			 },
			 "add-code":{
				 isNumberOrLetter:["a","f"]
			 },
		 },
		 messages:{//自定义消息
			 "tb-repeat-pass": {
			     equalTo: "5",
			 },
			 regSmsBusiContent:{
				 required:"1",
			 },
			 findPwdSmsContent:{
				 required:"1",
			 },
			 bindUserSmsContent:{
				 required:"1",
			 },
		 },
		 invalidHandler: function(form, validator) {
	        $.each(validator.invalid,function(key,value){
	        	debugger;
	        	var s = key;
//	        	debugger;
	        	if(key == 'tb-account-name' || key == 'tb-account-nickname' || key == 'tb-account-pass'
	        	 || key == 'tb-repeat-pass' || key == 'tb-app'){
	        		if(value == "8" || value == "1"){
	        			value = "8";
	        		}
	        		key = outPutMessage(key,value);
	        		layer.tips(key, '#'+s+'',{tips:[2,'red'],time: 40000000});
	        	}else{
	        		key = outPutMessage(key,value);
	        		layer.msg(key, {icon: 7,time: 1500 });
	        	}
	            return false;
	        }); //这里循环错误map，只报错第一个
		 },
		 errorPlacement: function(error, element) {
		 },
	     onfocusout:false
	 });
	$.validator.addMethod("checkIp",function(value,element,params){
		return checkIp(value);
	},"ip地址格式错误");
	$.validator.addMethod("checkIdCard",function(value,element,params){
		return checkIdCard(value);
	},"身份证格式错误");
	$.validator.addMethod("checkMobile",function(value,element,params){
		return checkMobile(value);
	},"手机号格式错误");
	$.validator.addMethod("checkUrl",function(value,element,params){
		return checkUrl(value);
	},"地址格式错误");
	var n = "";
	$.validator.addMethod("checkPwd",function(value,element,params){
		return checkPwd(value,element.getAttribute("checkpwd"));
	},"14");
	$.validator.addMethod("checkName",function(value,element,params){
		return checkName(value);
	},"10");
	var s ;
	$.validator.addMethod("checkDesc",function(value,element,params){
		return checkDesc(value);
	},"9");
	$.validator.addMethod("isChinaChar",function(value,element,params){
		return isChinaChar(value);
	},"11");
	$.validator.addMethod("isNumberOrLetter",function(value,element,params){
		return isNumberOrLetter(value);
	},"12");
	$.validator.addMethod("checkfax",function(value,element,params){
		return checkfax(value);
	},"传真号格式不正确");
	$.validator.addMethod("checkfw",function(value,element,params){
		return checkfw(value);
	},"13");
	$.validator.addMethod("checkXingMing",function(value,element,params){
		return checkXingMing(value);
	},"15");
	$.validator.addMethod("gszcOrXydm",function(value,element,params){
		return checkGsdm(value);
	},"16");
	$.validator.addMethod("checkEmail",function(value,element,params){
		return checkEmail(value);
	},"3");
	$.validator.addMethod("checkOrgCode",function(value,element,params){
		return checkOrgCode(value);
	},"17");
	$.validator.addMethod("checkWphone",function(value,element,params){
		return checkfax(value);
	},"18");
	
}
//输出提示消息,常用的消息，请(输入+字段)名称请调用此方方法即可
function outPutMessage(key,value){
	debugger;
	switch(value)
	{
		case "1":
				if($('input[name='+key+']').attr("type") == "radio"){
					return "请选择"+findLabel($('input[name='+key+']:eq(0)').parent())+"";
				}else if($('select[name='+key+']').length > 0){
					return "请选择"+findLabel($('select[name='+key+']:eq(0)').parent())+"";
				}else if($('textarea[name='+key+']').length > 0){
					return "请输入"+findLabel($('textarea[name='+key+']:eq(0)').parent())+"";
				}else{
					return "请输入"+findLabel($('input[name='+key+']:eq(0)').parent())+"";
				}
				break;
		case "2":return  "请修正"+findLabel($('input[name='+key+']:eq(0)').parent());break;
		case "3":return  "请输入有效的"+findLabel($('input[name='+key+']:eq(0)').parent());break;
		case "4":return  findLabel($('input[name='+key+']:eq(0)').parent())+"只能输入数字";break;
		case "5":return  "两次输入不相同";break;
		case "6":return  "请输入有效的"+findLabel($('input[name='+key+']:eq(0)').parent())+"数字";break;
		case "7":return  "请输入有效的"+findLabel($('input[name='+key+']:eq(0)').parent())+"后缀";break;
		case "8":
				if($('select[name='+key+']').length > 0){
					return "请选择"+$('select[name='+key+']').parent().siblings('label').text().replace(/:*$/,"")+"";
				}else{
					return "请输入"+$('input[name='+key+']:eq(0)').parent().siblings('label').text().replace(/:*$/,"")+"";
				}
				break;
		case "9":return  findLabel($('textarea[name='+key+']').parent())+"中只能含中英文、数字，常见标点符号以及空格等";break;
		case "10":return findLabel($('input[name='+key+']').parent())+"中只能含有中英文、数字、连接符等";break;
		case "11":return findLabel($('input[name='+key+']').parent())+"中不能含有汉字";break;
		case "12":return findLabel($('input[name='+key+']').parent())+"中只能包含数字和字母";break;
		case "13":return findLabel($('input[name='+key+']').parent())+"中只能含有中英文、数字、连接符等";break;
		case "14":
				if($('input[name='+key+']').attr("checkPwd") == "1"){
					return "密码强度不够，至少强度达到强级";break;
				}
				if($('input[name='+key+']').attr("checkPwd") == "2"){
					return "密码强度不够，至少强度达到中级";break;
				}
				if($('input[name='+key+']').attr("checkPwd") == "3"){
					return "密码强度不够，至少强度达到弱级";break;
				}
		case "15":return findLabel($('input[name='+key+']').parent())+"中只能含有中英文、以及空格";break;
		case "16":return findLabel($('input[name='+key+']').parent())+"中只能输入15位纯数字或者18位数字和字母";break;
		case "17":return findLabel($('input[name='+key+']').parent())+"正确格式为xxxxxxxx-x";break;
		case "18":return findLabel($('input[name='+key+']').parent())+"格式不正确";break;
		default:return findLabel($('input[name='+key+']:eq(0)').parent())+value;
	}
	
}

//验证ip地址格式
function checkIp(ip) {
	if ( ip == '' ) {
		return false;
	}
	var ipRegExp = new RegExp(/^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-4])))\.)(((\d{1,2}|1\d\d|2[0-4]\d|25[0-4]))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-4])))$/);
	if ( ipRegExp.test(ip) ) {
		return true;
	}
	return false;
}
//验证字段类型：只含字母、数字
function checkType(type) {
	var typeRegExp = new RegExp(/^([a-zA-Z0-9])+$/);
	if ( typeRegExp.test(type) ) {
		return true;
	}
	return false;
}
//验证后缀
function checkSuffix(string){
	var typeRegExp = new RegExp(/^\w.+$/);
	if(typeRegExp.test(string)){
		return true
	}
	return false;
}
//验证身份证格式
function checkIdCard(idCard){
	var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {//进行15位身份证的验证    
    	if(!isValidityBrithBy15IdCard(idCard)){
    		return false;
    	}
    	return true;       
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {
            return false;   
        }   
    } else {   
        return false;   
    }   
}
//验证手机号码格式
function checkMobile(String){
	var regExp = new RegExp(/^((\+86)|(86))?^1[3|4|5|7|8]\d{9}$/ );
	if ( regExp.test(String) ) {
		return true;
	}else{
		return false;
	}
}
//web地址验证
function checkUrl(String){
	var regExp = new RegExp(/^((ht|f)tps|http|ftp|mms?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/);
	if ( regExp.test(String) ) {
		return true;
	}else{
		return false;
	}
}
//密码强度验证
function checkPwd(string,pwdLevel){
	// 强：密码为八位及以上并且字母数字特殊字符三项都包括
	var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
	// 中：密码为七位及以上并且字母、数字、特殊字符三项中有两项
	var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
	// 弱：6位
	var weakRegex = new RegExp("(?=.{6,}).*", "g");
	
	var flag = 0;
	if ( strongRegex.test(string) ) {
		$('#weak-pass').css("background-color", "#1AE61A");
		$('#middle-pass').css("background-color", "#1AE61A");
		$('#strong-pass').css("background-color", "#1AE61A");
		flag = 1;
	} else if ( mediumRegex.test(string) ) {
		$('#weak-pass').css("background-color", "#1AE61A");
		$('#middle-pass').css("background-color", "#1AE61A");
		$('#strong-pass').css("background-color", "#CCCCCC");
		flag = 2;
	} else if ( weakRegex.test(string) ) {			
		$('#weak-pass').css("background-color", "#1AE61A");
		$('#middle-pass').css("background-color", "#CCCCCC");
		$('#strong-pass').css("background-color", "#CCCCCC");
		flag = 3;
	} else {
		$('#weak-pass').css("background-color", "#CCCCCC");
		$('#middle-pass').css("background-color", "#CCCCCC");
		$('#strong-pass').css("background-color", "#CCCCCC");
		flag = 3;
	}
	if(flag - pwdLevel <= 0){
		return true;
	}else{
		return false;
	}
}
//
//验证名称类：只含有中英文、数字、连接符等
function checkName(name) {
	var nameRegExp = new RegExp(/^([\u4e00-\u9fa5a-zA-Z0-9_\-\.——])+$/);
	if ( nameRegExp.test(name) ) {
		return true;
	}
	return false;
}
//验证姓名
function checkXingMing(name){
	var nameRegExp = new RegExp(/^([\u4e00-\u9fa5a-zA-Z\s*])+$/);
	if ( nameRegExp.test(name) ) {
		return true;
	}
	return false;
}
//验证名称类：只含有中英文、数字、连接符等，可以为空
function checkfw(name) {
	if(name == ''){
		return true;
	}
	var nameRegExp = new RegExp(/^([\u4e00-\u9fa5a-zA-Z0-9_\-\.——])+$/);
	if ( nameRegExp.test(name) ) {
		return true;
	}
	return false;
}

//验证描述类：只含中英文、数字，常见标点符号以及空格等
function checkDesc(desc) {
	if(desc == ''){
		return true;
	}
	var descRegExp = new RegExp(/^([\u4e00-\u9fa5a-zA-Z0-9_\-——'"‘’“”\[\]（）(),\.，。!！  ])+$/);
	if (descRegExp.test(desc) ) {
		return true;
	}
	return false;
}
//统一社会信用代码/工商注册号
function checkGsdm(string){
	var descRegExp = new RegExp(/^\d{15}$/);
	var descRegExp1 = new RegExp(/^[A-Za-z0-9]{18}$/);
	if (descRegExp.test(string) ) {
		return true;
	}else if(descRegExp1.test(string)){
		return true;
	}
	return false;
}
//验证不能输入汉字
function isChinaChar(string){
	var descRegExp = new RegExp(/^[^\u4e00-\u9fa5]{0,}$/);
	if (descRegExp.test(string) ) {
		return true;
	}
	return false;
}

//只能包含字母和数字
function isNumberOrLetter(string){
	var regExp = new RegExp(/^[A-Za-z0-9]+$/);
	if (regExp.test(string) ) {
		return true;
	}
	return false;
}
//包含数字和字母和-
function isCode(string){
	var regExp = new RegExp(/[a-zA-Z0-9]/);
	if (regExp.test(string) ) {
		return true;
	}
	return false;
}
//传真验证
function checkfax(string){
	if(string == ''){
		return true;
	}
	var regExp = new RegExp(/(\d{3,4}-)?\d{7,8}$/);
	if (regExp.test(string) ) {
		return true;
	}
	return false;
}
//邮箱验证
function checkEmail(string){
	var regExp = new RegExp(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/);
	var regExp1 = new RegExp(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/);
	if(regExp.test(string)){
		return true;
	}else if(regExp1.test(string)){
		return true;
	}
	return false;
}
//组织机构代码
function checkOrgCode(string){
	debugger;
	var regExp = new RegExp(/^([0-9a-zA-Z]{8})-([0-9a-zA-Z])$/);
	if(regExp.test(string)){
		return true;
	}
	return false;
}
function findLabel(obj){
	debugger;
	if(obj.parent().parent().siblings("label").hasClass("layui-form-label")){
		 return obj.parent().parent().siblings("label").text().replace(/\:*$/,"");
	}
	var classLabel = obj.siblings(".label").text();
	if(classLabel == "undefined" || classLabel == ""){
		var ElementLabel = obj.siblings("label").text().replace(/\:*$/,"");
		return ElementLabel.replace("*","");
	}else{
		return classLabel.replace(/\:*$/,"");
	}
}
