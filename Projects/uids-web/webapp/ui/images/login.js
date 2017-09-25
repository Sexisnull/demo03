$(function(){
	if($('#username').val() == ""){
		$('#username').prev().show();
	}
	if($('#password').val() == ""){
		$('#password').prev().show();
	}
	if($('#randCode').val() == ""){
		$('#randCode').prev().show();
	}
	$('#username,#password,#randCode').on('blur',function(){
		if($(this).val() == ""){
			$(this).prev().show();
		}
	});
	$('#username,#password,#randCode').on('focus',function(){
		if($(this).val() == ""){
			$(this).prev().hide();
		}
	});
	$('.log_dlm label').on('click',function(){
		$(this).hide();
		$(this).next().focus();
	});
	$('.log_mm label').on('click',function(){
		$(this).hide();
		$(this).next().focus();
	});
});