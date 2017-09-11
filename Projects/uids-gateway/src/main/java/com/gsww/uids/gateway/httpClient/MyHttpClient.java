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
	public static Integer syncToApplication(String url,Map<String,String> map)throws Exception{
		int status =0;
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestBody(new NameValuePair[]{(NameValuePair) map});
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
	    try {
	    	status = httpClient.executeMethod(post);
			System.out.println(status);
			System.out.println(post.getResponseBodyAsString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	    post.releaseConnection(); 
	    return status;
	}
}
