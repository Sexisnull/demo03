package com.gsww.uids.manager.smsTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.Assert;
import org.junit.Test;

import com.gsww.common.util.SmsUtil;
import com.gsww.uids.base.BaseTest;

/**
 * 短信接口测试
 * 
 * @author simplife
 *
 */
public class SmsTest extends BaseTest {

//	@Test
//	public void send() {
//		Assert.assertTrue(SmsUtil.getInstance().sendSms("17794287877"));
//	}
//	
//	@Test
//	public void check() {
//		Assert.assertTrue(SmsUtil.getInstance().checkCode("17794287877", "1861"));
//	}
	
	@Test
	public void test(){
		String mobile = "1779428787";
		String msg = "政务网测试短信";
		String appId = "62000099";//第三方系统编码，由短信平台提供统一编码
		String appName = "短信运维平台";//第三方系统名称
		String appBusinessId = "6200009901";//第三方系统业务编码，由短信平台提供统一编码
		String appBusinessName = "测试业务";//第三方系统业务名称
		String appAcc = "csxt";//第三方系统鉴权账号，由短信平台提供 	
		String appPwd = "111111";//第三方系统鉴权密码，由短信平台提供
		String importantLevel = "2";//重要级别 0:低 1:中 2:高
		String isSendAgain = "0";//是否重新发送 0:否 1:是
		String isLose = "1";//是否过期作废 0:否 1:是
		String loseTime = "60";//过期作废时间(秒)
		String isUpstream = "0";//是否有上行 0:否 1:是
		//String smsUrl = "http://29.22.105.84:8081/sms/sendsms";
		String smsUrl = "http://api.gszwfw.gov.cn/sms/sendsms";//外网测试接口（测试完关闭）
		String result = "";// 访问返回结果
		try{
			BufferedReader read = null;// 读取访问结果
		 	StringBuffer queryString = new StringBuffer();
		 	queryString.append("?appId=").append(appId);
		 	queryString.append("&appName=").append(URLEncoder.encode(appName, "utf-8"));
		 	queryString.append("&appBusinessId=").append(appBusinessId);
		 	queryString.append("&appBusinessName=").append(URLEncoder.encode(appBusinessName, "utf-8"));
		 	queryString.append("&appAcc=").append(appAcc);
		 	queryString.append("&appPwd=").append(appPwd);
		 	queryString.append("&importantLevel=").append(importantLevel);
		 	queryString.append("&isSendAgain=").append(isSendAgain);
		 	queryString.append("&isLose=").append(isLose);
		 	queryString.append("&loseTime=").append(loseTime);
		 	queryString.append("&mobile=").append(mobile);
		 	queryString.append("&msg=").append(URLEncoder.encode(msg, "utf-8"));
		 	queryString.append("&isUpstream=").append(isUpstream);
			URL url = new URL(smsUrl + queryString);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.setConnectTimeout(0);
			http.setInstanceFollowRedirects(true);
			http.setRequestProperty("Content-type", "text/html");
			http.setRequestProperty("Accept-Charset", "utf-8");
			http.setRequestProperty("contentType", "utf-8");
			http.setDefaultUseCaches(false);
			http.setDoOutput(true);	
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(result);
	}
}
