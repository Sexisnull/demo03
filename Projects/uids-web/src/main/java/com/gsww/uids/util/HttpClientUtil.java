package com.gsww.uids.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;

public class HttpClientUtil {
	
	private static PoolingClientConnectionManager connectionManager = null;
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	 private static EncodeUtil encodeUtil = new EncodeUtil();
	 private static HttpParams httpParams = null;
	 private static int overtime = 30000;
	
    public static int getStatusCode(String strUrl, int overtime)
	  {
	    HttpClient httpClient = getHttpClient(overtime);
	    HttpGet getMethod = getGetMethod(overtime);
	    int statusCode = 0;
	    try {
	      getMethod.setURI(formatURI(strUrl, null));
	      HttpResponse response = httpClient.execute(getMethod);
	      statusCode = response.getStatusLine().getStatusCode();
	    } catch (Exception e) {
	      log.debug("getStatusCode:" + e);
	    } finally {
	      getMethod.abort();
	    }
	    return statusCode;
	  }

    private static URI formatURI(String strUrl, String charset)
    {
      URL url = null;
      URI uri = null;
      try {
        strUrl = strUrl.replaceAll("&amp;", "&");
        strUrl = strUrl.replaceAll(" ", "%20");
        strUrl = encodeUtil.encodeStr(strUrl, charset);
        url = new URL(strUrl.trim());
        uri = null;
        try {
          uri = URI.create(strUrl.trim());
        } catch (Exception e) {
          log.debug("URL不符合规范：" + strUrl);
        }
        if (uri == null)
          uri = new URI(url.getProtocol(), url.getAuthority(), url.getPath(), url.getQuery(), null);
      }
      catch (Exception e) {
        log.debug("formatURI：" + e);
      }
      return uri;
    }

    private static HttpGet getGetMethod(int overtime)
    {
      HttpGet getMethod = new HttpGet();
      getMethod.setHeader("Connection", "close");
      getMethod.getParams().setParameter("http.socket.timeout", 
        Integer.valueOf(overtime));
      getMethod.getParams().setParameter("http.connection.timeout", 
        Integer.valueOf(overtime));
      getMethod.getParams().setParameter("http.protocol.head-body-timeout", 
        Integer.valueOf(overtime));
      getMethod.getParams().setParameter(
        "http.protocol.expect-continue", Boolean.valueOf(false));
      return getMethod;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static HttpClient getHttpClient(int overtime)
    {
      if (connectionManager == null) {
        SchemeRegistry supportedSchemes = new SchemeRegistry();
        supportedSchemes.register(new Scheme("http", 80, 
          PlainSocketFactory.getSocketFactory()));
        supportedSchemes.register(new Scheme("https", 443, 
          SSLSocketFactory.getSocketFactory()));
        connectionManager = new PoolingClientConnectionManager(
          supportedSchemes);

        connectionManager.setMaxTotal(200);

        connectionManager.setDefaultMaxPerRoute(20);

        HttpHost localhost = new HttpHost("locahost", 80);
        connectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);
      }
      if (httpParams == null)
      {
        List headers = new ArrayList();
        headers.add(new BasicHeader("User-Agent", 
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        headers.add(new BasicHeader("Accept-Language", "zh,en"));
        httpParams = new BasicHttpParams();
        httpParams.setParameter("http.default-headers", headers);
        HttpProtocolParams.setContentCharset(httpParams, "GB18030");
        HttpProtocolParams.setHttpElementCharset(httpParams, "GB18030");
        httpParams.setParameter("http.connection.timeout", 
          Integer.valueOf(overtime));
        httpParams.setParameter("http.socket.timeout", Integer.valueOf(overtime));
        httpParams.setParameter("http.protocol.expect-continue", Boolean.valueOf(false));

        httpParams.setParameter("http.protocol.cookie-policy", "compatibility");
      }
      return new DefaultHttpClient(connectionManager, httpParams);
    }
    
    public static String postInfo(String strUrl, List<NameValuePair> qparams, String charset)
    {
      HttpClient httpClient = getHttpClient(overtime);
      HttpPost post = getPostMethod(overtime);
      HttpEntity entity = null;
      String htmlcontent = "";
      try {
        if ((charset == null) || ("".equals(charset))) {
          charset = encodeUtil.getURLEncoding(new URL(strUrl));
        }
        post.setURI(formatURI(strUrl, charset));
        post.setEntity(new UrlEncodedFormEntity(qparams, charset));

        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200)
          htmlcontent = httpEnToString(entity, charset);
        else {
          htmlcontent = "connect error:" + statusCode;
        }
        EntityUtils.consume(entity);
      } catch (Exception e) {
        log.debug("postInfo：" + e);
        htmlcontent = "connect error";
      } finally {
        post.abort();
      }
      return htmlcontent;
    }
    
    private static String httpEnToString(HttpEntity httpEntity, String charset)
    {
      StringBuffer html = new StringBuffer();
      BufferedReader reader = null;
      InputStreamReader isr = null;
      try {
    	isr = new InputStreamReader(httpEntity.getContent(), charset);
        reader = new BufferedReader(isr);

        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
          html.append(inputLine);
          html.append("\n");
        }
      }
      catch (Exception e) {
        log.debug("httpEnToString：" + e);
        html.delete(0, html.length());
        html.append("connect error");

        if (reader != null)
          try {
            reader.close();
          }
          catch (IOException localIOException)
          {
          }
      }
      finally
      {
        if (reader != null)
          try {
            reader.close();
            isr.close();
          }
          catch (IOException localIOException1) {
          }
      }
      return html.toString();
    }
    
    private static HttpPost getPostMethod(int overtime)
    {
      HttpPost postMethod = new HttpPost();
      postMethod.setHeader("Connection", "close");
      postMethod.getParams().setParameter("http.socket.timeout", 
        Integer.valueOf(overtime));
      postMethod.getParams().setParameter("http.protocol.head-body-timeout", 
        Integer.valueOf(overtime));
      postMethod.getParams().setParameter(
        "http.protocol.expect-continue", Boolean.valueOf(false));
      return postMethod;
    }
}
