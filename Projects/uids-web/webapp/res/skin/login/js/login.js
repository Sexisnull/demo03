$(function(e) {
		//个人中心
		$('.user-center').on('click', function(event) {
			$.dialog({
				title : '个人中心',
				min : false,
				max : false,
				width : 800,
				height : 300,
				lock : true,
				content : 'url:/uids-web/login/gotoUserDetail'
			});
		});
});
function loginOut() {
	$.dialog.confirm('您确认要退出系统吗?', function() {
		$.get("/uids-web/login/loginOut");
		window.location.href="/uids-web";
	});
}
function toBack(){
	window.location.href="/uids-web/backIndex";
}
function toFront(){
	window.location.href="/uids-web/frontIndex";
}

function toCountUser(){
	document.getElementById("main").src="/uids-web/jisLog/countUser";
}