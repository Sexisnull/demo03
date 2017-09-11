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
public class MyHttpClientThread extends Thread{
	protected Logger logger = Logger.getLogger(getClass());
	private String url;
	private String jsonString;
	public MyHttpClientThread(){}
	public MyHttpClientThread(String url,String jsonString){
		this.url=url;
	}
	public void run(){
		try{
			this.syncToApplication(url, jsonString);
		}catch(Exception e){
			e.printStackTrace();
			Map<String,String> map=new HashMap<String,String>();
			map.put("resultMsg", "统一身份认证系统调用子系统失败"+e.getMessage());
//			this.returnToCrmOrVsop(returnUrl, map);
		}
		
	}
	public void syncToApplication(String url,String jsonString)throws Exception{
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
		} catch (IOException e) {
			e.printStackTrace();
			post.releaseConnection();
			throw new Exception(e.getMessage());
		}
	    post.releaseConnection(); 
	}
	public void getToURL(String url)throws Exception{
		 HttpClient client = new HttpClient();   
    	 //HttpMethod method = new GetMethod("http://10.18.12.220:8080/addrlist2/loginAction!provide.do"); 
		 client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);//设置超时时间为10s
    	 HttpMethod method = new GetMethod(url);
    	 int i=client.executeMethod(method);
    	 System.out.println("i的状态："+i);
    	 System.out.println(method.getStatusLine());
          //打印返回的信息
//    	 System.out.println(method.getResponseBodyAsString());
          //释放连接
         method.releaseConnection();
	}
	public static String packageGetParams(String url, Map<String, String> parameters)throws Exception{
		String params = "";// 编码之后的参数
		StringBuffer sb = new StringBuffer();// 存储参数
		if (parameters.size() == 1) {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=").append(
						java.net.URLEncoder.encode(parameters.get(name),
								"UTF-8"));
			}
			params = sb.toString();
		} else {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=").append(
						java.net.URLEncoder.encode(parameters.get(name),
								"UTF-8")).append("&");
			}
			String temp_params = sb.toString();
			params = temp_params.substring(0, temp_params.length() - 1);
		}
		String full_url = url + "?" + params;
		return full_url;
	}
	/**
	 * 发送GET请求
	 * 
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {

				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name),
									"UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name),
									"UTF-8")).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = url + "?" + params;
			System.out.println(full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : headers.keySet()) {
				System.out.println(key + "\t：\t" + headers.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name),
									"UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name),
									"UTF-8")).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
}
