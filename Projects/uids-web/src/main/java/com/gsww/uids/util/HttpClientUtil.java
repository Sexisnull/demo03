package com.gsww.uids.util;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
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
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtil {
	
	private static PoolingClientConnectionManager connectionManager = null;
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	 private static EncodeUtil encodeUtil = new EncodeUtil();
	 private static HttpParams httpParams = null;
	
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
}
