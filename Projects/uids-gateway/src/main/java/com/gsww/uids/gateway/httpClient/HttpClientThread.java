package com.gsww.uids.gateway.httpClient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HttpClientThread extends Thread  {
	protected  Logger logger = LoggerFactory.getLogger(HttpClientThread.class);
	private String httpUrl;
	private NameValuePair values;
	public HttpClientThread(String url, NameValuePair params){
		httpUrl = url;
		values = params;
	}
	public void init(){}
	public void run() {
		PostMethod post = null;
		try{
			HttpClient httpClient = new HttpClient();
			post = new PostMethod(httpUrl);
	        post.setRequestBody(new NameValuePair[]{values} );
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
	        int status = httpClient.executeMethod(post);
	        System.out.println(status);
	        System.out.println(post.getResponseBodyAsString());
	        post.releaseConnection();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			post.releaseConnection();
		}
	}
}
