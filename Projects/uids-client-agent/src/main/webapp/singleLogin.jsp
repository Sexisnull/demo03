<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<<title>单点登录页面</title>
</head>
<body>
<%
	String code = request.getParameter("code");
	String url = request.getContextPath() + "/oauth2-login.jsp?code=" + code;
	response.sendRedirect(url);
%>

</body>
</html>