package com.gsww.uids.gateway.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
/**
 * http客户端
 * @author gaopf
 *
 */
public class MyHttpClient{
	protected Logger logger = Logger.getLogger(getClass());
	
	public static Map<String,Object> syncToApplication(String url,String jsonString)throws Exception{
		Map<String,Object> returnMap = new HashMap<String,Object>();
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(url);
		NameValuePair json = new NameValuePair("jsonString",jsonString);
		post.setRequestBody(new NameValuePair[]{json});
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(8000);
	    try {
	    	int status = httpClient.executeMethod(post);
	    	System.out.println(status);
			System.out.println(post.getResponseBodyAsString());
	    	returnMap.put("status", String.valueOf(status));
	    	returnMap.put("respMsg", post.getResponseBodyAsString());
		} catch (IOException e) {
			e.printStackTrace();
			post.releaseConnection();
			throw new Exception(e.getMessage());
		}
	    post.releaseConnection(); 
		return returnMap;
	}
	
}
