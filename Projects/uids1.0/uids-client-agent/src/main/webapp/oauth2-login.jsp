<%@ page contentType="text/html;charset=UTF-8" language="java" 
import="com.gsww.entity.SSOUser, org.apache.shiro.SecurityUtils, com.gsww.entity.Token"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<title>首页</title>
<body>

<%! SSOUser user = (SSOUser)SecurityUtils.getSubject().getPrincipal(); %>

<shiro:user>
    欢迎[<%= user.getTrueName() %>]登录反向代理应用<br/>
  <a href="${pageContext.request.contextPath}/refreshToken.jsp">刷新token</a><br/>
  <a href="${pageContext.request.contextPath}/logout.jsp">退出</a><br>
</shiro:user>
<shiro:hasRole name="admin">
    您有角色admin
</shiro:hasRole>
</body>
</html>
