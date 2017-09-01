<%@ page contentType="text/html;charset=UTF-8" language="java" 
import="java.net.HttpURLConnection, java.io.InputStream, java.net.URL, java.net.URLConnection, java.io.OutputStreamWriter, 
java.io.IOException, org.apache.commons.io.IOUtils, net.sf.json.JSONObject, org.springframework.util.StringUtils, 
com.gsww.entity.Token, com.gsww.entity.SSOUser, org.apache.shiro.SecurityUtils, org.apache.shiro.subject.Subject, 
com.gsww.oauth2.OAuth2Token, org.apache.oltu.oauth2.common.OAuth, java.net.URLEncoder"%>
<html>
<head>
<title>退出</title>
</head>
<body>

<% 
	final String serverLogoutUrl = "http://10.18.27.74:8080/uids/sso/logout.uids";
	final String clientId = "725eabbf8e0d4fc6bc9aacaac04e0701";
	final String clientSecret = "781a2339a1b14bb68d456ab85300f647";
	
	// 获取token
	Token token = (Token)request.getSession().getAttribute("token");

	HttpURLConnection connection = null; 
	try {   
		// post请求，刷新token信息
		URL tokenUrl = new URL(serverLogoutUrl);   
		connection = (HttpURLConnection) tokenUrl.openConnection();   
		connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        //设置请求属性
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        connection.setRequestProperty("Charset", "UTF-8");

		// 准备参数      
        String params = OAuth.OAUTH_ACCESS_TOKEN + "=" + URLEncoder.encode(token.getAccessToken(), "UTF-8")
        + "&" + OAuth.OAUTH_CLIENT_ID + "=" + URLEncoder.encode(clientId, "UTF-8")
        + "&" + OAuth.OAUTH_CLIENT_SECRET + "=" + URLEncoder.encode(clientSecret, "UTF-8");
        
		// 建立输入流，向指向的URL传入参数
        OutputStreamWriter outWriter = new OutputStreamWriter (connection.getOutputStream());
        outWriter.write(params);
        outWriter.flush();
        outWriter.close();
        
        if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
        	// 获取返回数据          
    		InputStream in = connection.getInputStream();
    		String resultJsonStr = IOUtils.toString(in, "UTF-8");
    		in.close();
    		
    		// 解析返回结果
    		JSONObject tokenJson = JSONObject.fromObject(resultJsonStr);
    		int state = tokenJson.getInt("state");
    		String info = tokenJson.getString("info");
    		
    		// shiro退出
    		SecurityUtils.getSubject().logout();
    		
    		// 跳转到index页面
    		if ( state == 1 ) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
    		} else {
    			System.out.println(info);
    		}
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