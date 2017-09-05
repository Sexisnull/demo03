<%@ page contentType="text/html;charset=UTF-8" language="java" 
import="java.net.HttpURLConnection, java.io.InputStream, java.net.URL, java.net.URLConnection, java.io.OutputStreamWriter, 
java.io.IOException, org.apache.commons.io.IOUtils, net.sf.json.JSONObject, org.springframework.util.StringUtils, 
com.gsww.entity.Token, com.gsww.entity.SSOUser, org.apache.shiro.SecurityUtils, org.apache.shiro.subject.Subject, 
com.gsww.oauth2.OAuth2Token"%>
<html>
<title>退出</title>
<body>
<%
	HttpURLConnection connection = null; 

	try {   
		// post请求，获取token信息
		URL tokenUrl = new URL(request.getParameter("token_url"));   
		connection = (HttpURLConnection) tokenUrl.openConnection();   
		connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        
        if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
        	// 获取返回数据          
    		InputStream in = connection.getInputStream();
    		String tokenJsonStr = IOUtils.toString(in, "UTF-8");
    		in.close();
    		if ( StringUtils.isEmpty(tokenJsonStr) ) {
    			throw new Exception("没有获取到共享的token信息");
    		}
    		
    		// 解析token
    		JSONObject tokenJson = JSONObject.fromObject(tokenJsonStr);
    		Token token = (Token) tokenJson.toBean(tokenJson, Token.class);
    		
    		// 登录
    		Subject subject = SecurityUtils.getSubject();
    		OAuth2Token oauth2Token = new OAuth2Token();
    		oauth2Token.setToken(token);
    		subject.login(oauth2Token);
    		
    		// 将token放进session中
    		request.getSession().setAttribute("token", token);
    		
    		// 跳转到登录后的页面
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/oauth2-login.jsp");
    		dispatcher.forward(request, response);
        }		
	} catch (Exception e) {
		e.printStackTrace();
    } finally {
    	if ( connection != null ) {
    		connection.disconnect();
    	}
    }
%>

</body>
</html>