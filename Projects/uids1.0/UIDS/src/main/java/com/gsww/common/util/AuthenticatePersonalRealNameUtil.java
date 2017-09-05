package com.gsww.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.uids.manager.sys.entity.SystemAuthParam;
import com.gsww.uids.manager.sys.service.SysConfigService;


/**
 * 个人实名认证
 * 
 * @author simplife
 *
 */
public class AuthenticatePersonalRealNameUtil {

	private static final Logger logger = Logger.getLogger(AuthenticatePersonalRealNameUtil.class);
	
	@Autowired
	private SysConfigService sysConfigService;
	
	private static AuthenticatePersonalRealNameUtil instance = null;
	
	public static AuthenticatePersonalRealNameUtil getInstance() {
		if (instance == null) {
			instance = new AuthenticatePersonalRealNameUtil();
		}
		return instance;
	}

	/**
	 * 实名认证
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean authenticateRealName(String id, String name) {
		
		// 先获取个人实名认证参数
		SystemAuthParam config = sysConfigService.getSystemAuthParam();
		if ( config == null ) {
			return false;
		}
		
		// 获取个人实名认证的token
		String token = getToken(config.getPersonalAuthTokenUrl(), config.getPersonalAuthCode(),
				config.getPersonalAuthUserName(), config.getPersonalAuthPwd());
		if ( StringUtil.isBlank(token) ) {
			return false;
		}
		
		// 比对个人实名认证信息
		return comparePersonalRealName(config.getPersonalAuthCompareUrl(), token, id, name);
	}
	
	/**
	 * 获取个人实名认证的token
	 * 
	 * @param url
	 * @param code
	 * @param username
	 * @param password
	 * @return
	 */
	private String getToken(String url, String code, String username, String password) {
		
		// 获取token的完整url
		String fullUrl = generateFullUrlOfToken(url, code, username, password);
		
		// 请求token
		String tokenInfo = requestAuthRealName(fullUrl, "实名认证token");
		if ( StringUtil.isBlank(tokenInfo) ) {
			return null;
		}
		
		// 解析token返回信息，获取token
		String token = parseToken(tokenInfo);
		
		return token;
	}
	
	/**
	 * 拼接获取token的完整地址
	 * 
	 * @param url
	 * @param code
	 * @param username
	 * @param password
	 * @return
	 */
	private String generateFullUrlOfToken(String url, String code, String username, String password) {
		return url + "?syscode=" + code + "&username=" + username + "&password=" + password;
	} 
	
	/**
	 * 请求实名认证信息
	 * 
	 * @param url
	 * @param requestType
	 * @return
	 */
	private String requestAuthRealName(String url, String requestType) {
		
		logger.info("======= " + requestType + "请求开始： " + url + " =======");
		
		HttpURLConnection httpConnection = null;
		BufferedReader reader = null;
		try {
			// 设置请求参数
			URL tokenUrl = new URL(url);
	        httpConnection = (HttpURLConnection) tokenUrl.openConnection();
	        httpConnection.setRequestMethod("GET");
	        httpConnection.setConnectTimeout(0);
	        httpConnection.setInstanceFollowRedirects(true);
	        httpConnection.setRequestProperty("Content-type", "text/html");
	        httpConnection.setRequestProperty("Accept-Charset", "utf-8");
	        httpConnection.setRequestProperty("contentType", "utf-8");
	        httpConnection.setDefaultUseCaches(false);
	        httpConnection.setDoOutput(true);
			
	        // 取得输入流，并使用Reader读取
	        reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
	        
	        // 读取返回的数据
	        String result = "";
	        String line;
            while ( (line = reader.readLine()) != null ) {
                result += line;
            }
            
            // 关闭输入流，断开连接
	        reader.close();
	        httpConnection.disconnect();
	        
	        logger.info("======= " + requestType + "请求结束：" + result + " =======");
	        
	        return result;
	        
		} catch (Exception e) {
			logger.info("实名认证错误", e);
			
			// 关闭输入流
			if ( reader != null ) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.info("关闭输入流失败", e1);
				}
			}
			
			// 断开连接
			if ( httpConnection != null ) {
				httpConnection.disconnect();
			}
			
			return null;
		}
	}
	
	/**
	 * 从返回结果中解析token
	 * 
	 * @param tokenInfo
	 * @return
	 */
	private String parseToken(String tokenInfo) {
		
		try {
			JSONObject resultJson = new JSONObject(tokenInfo);
			String rst = resultJson.get("rst").toString();
			JSONObject rstJson = new JSONObject(rst);
			String token = rstJson.get("token").toString();
			
			return token;
			
		} catch (Exception e) {
			
			logger.info("实名认证token请求返回数据格式错误：" + tokenInfo, e);
			return null;
		}
	}
	
	/**
	 * 比对实名认证信息
	 * 
	 * @param url
	 * @param token
	 * @param id
	 * @param name
	 * @return
	 */
	private boolean comparePersonalRealName(String url, String token, String id, String name) {
		
		// 获取实名认证的完整url
		String fullUrl = generateCompareUrl(url, token, id, name);
		
		// 请求比对实名信息
		String compareResult = requestAuthRealName(fullUrl, "实名认证比对");
		if ( StringUtil.isBlank(compareResult) ) {
			return false;
		}
		
		// 解析比对结果
		return parseCompareResult(compareResult);
	}
	
	/**
	 * 生成实名认证比对url
	 * 
	 * @param url
	 * @param token
	 * @param id
	 * @param name
	 * @return
	 */
	private String generateCompareUrl(String url, String token, String id, String name) {
		return url + "/" + token + "?id=" + id + "&name=" + name;
	}
	
	/**
	 * 解析实名认证比对请求结果
	 * 
	 * @param compareResult
	 * @return
	 */
	private boolean parseCompareResult(String compareResult) {
		
		try {
			JSONObject compareResultJson = new JSONObject(compareResult); 
			String rst = compareResultJson.get("rst").toString();
			
			if ( "true".equalsIgnoreCase(rst) ) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			
			logger.info("实名认证比对请求返回数据格式错误：" + compareResult, e);
			return false;
		}
	}
}
