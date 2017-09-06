<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/include/meta.jsp"%>
<meta charset="utf-8">
<title>甘肃万维JUP课题</title>
</head>
<script>

	$(document).ready(function(e) {
	
	//修改密码
		$('#changePwd').click(function() {
			var oldPwd = $("#oldPassWord").val();  
			var newPwd = $("#newPassWord").val();  
			var conNewPwd = $("#connewpwd").val(); 
			if (oldPwd == '' || newPwd == '' || conNewPwd == '') {
				var msgNull = "";
				msgNull += oldPwd == '' ? "旧密码不允许为空 \n" : "";
				msgNull += newPwd == '' ? "新密码不允许为空 \n" : "";
				msgNull += conNewPwd == '' ? "重复新密码不允许为空" : "";
				alert(msgNull);
				return false;
			}
			if (newPwd != conNewPwd) {
				alert('新密码两次输入不一致');
				return false;
			} else if (oldPwd.length<6||oldPwd.length>20) {
				alert("旧密码长度为介于6和20之间的字符串");
			} else if (newPwd.length<6||newPwd.length>20) {
				alert("新密码长度为介于6和20之间的字符串");
			} else {
				$.ajax({
					type : "POST",
					url : '${ctx}/login/changePwd',
					data : 'oldPwd=' + oldPwd + '&newPwd=' + newPwd,
					async : false,
					success : function(msg) {
						if (msg.indexOf('chanePwdSuccess') >= 0) {
							close();
							alert('修改成功，请使用新密码登录');
							parent.location.href = '${ctx}/login.jsp';
						} else {
							alert(msg);
						}
					},
					error : function() {
						alert('系统错误，修改失败');
						location.href = '${ctx}/login.jsp';
					}
				});
			}
		});
    });
</script>
<body>
<div class="repair-warper">
   	<div class="repair-form">
    <form id="submit"  method="post" class="form-horizontal">
    <div class="form-warper" style="min-width:400px;">
        	<div class="form-content" style="min-width:400px;">
        		<ul class="form-table">
        			<li>旧密码:</li>
        			<li><input type="password"  name="oldPassWord" id="oldPassWord" title="请输入旧密码"  class="input"/></li>
        			<li>设置新密码:</li>
        			<li><input type="password" name="newPassWord" id="newPassWord" title="请输入新密码"  class="input"/></li>
        			<li>重复新密码:</li>
        			<li><input type="password" class="input" id="connewpwd"  title="请输入重复新密码"/></li>
        		</ul>
            </div>
            <div class="form-btn">
                <input type="button" name="changePwd" id="changePwd" value="确定" class="btn green">
            </div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
