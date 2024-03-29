package com.gsww.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.log4j.Logger;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.enums.BussinessType;
import com.gsww.uids.manager.sms.entity.SmsRecord;
import com.gsww.uids.manager.sms.service.SmsService;
import com.gsww.uids.manager.sys.entity.SystemSMSParam;
import com.gsww.uids.manager.sys.service.SysConfigService;


/**
 * 短信验证处理
 * 
 * @author simplife
 *
 */
public class SmsUtil {

	private static final Logger logger = Logger.getLogger(SmsUtil.class);
	
	/**
	 * 验证码长度
	 */
	public static final int CHECK_CODE_LENGTH = 6;
	
	/**
	 * 短信内容中，标记验证码替换位置的字符串
	 */
	private static final String CHECK_CODE_REPLACE_MARK = "cellphoneShortMessageRandomCodeMadeByJava";
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	private static SmsUtil instance = null;
	
	public static SmsUtil getInstance() {
		if (instance == null) {
			instance = new SmsUtil();
		}
		return instance;
	}

	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 * @param type
	 * @return
	 */
	public SmsRecord sendSms(String mobile, BussinessType type) {
		
		// 生成验证码
		String code = genernateCode(CHECK_CODE_LENGTH);
		
		// 发送验证码
		StringBuilder result = new StringBuilder(100);
		if ( !runSendSms(mobile, type, code, result) ) {
			return null;
		}
		
		// 保存记录
		SmsRecord record = new SmsRecord(mobile, code, result.toString());
		smsService.saveOrUpdate(record);
		return record;
	}
	
	/**
	 * 验证
	 * 
	 * @param recordId
	 * @param mobile
	 * @param code
	 * @return
	 */
	public boolean checkCode(String recordId, String mobile, String code) throws Exception {
		
		// 根据记录id查找记录
		SmsRecord record = smsService.findById(recordId);		
		if ( StringUtil.isBlank(record.getUuid()) ) {
			logger.info("没有查询到验证码信息：【"+mobile+"】，【"+code+"】");
			return false;
		}
		
		// 验证手机号和验证码
		if ( !record.getMobile().equalsIgnoreCase(mobile) || !record.getCode().equalsIgnoreCase(code) ) {
			logger.info("验证码信息错误：【"+mobile+"】，【"+code+"】");
			return false;
		}
			
		// 判断验证码过期
		if ( TimeHelper.secondsBetween(record.getFailureTime(), TimeHelper.getCurrentTime()) > 0 ) {
			logger.info("验证码已过期：【"+mobile+"】，【"+code+"】");
			return false;
		}
		
		// 更新使用次数
		record.setUse(record.getUse() + 1);
		smsService.saveOrUpdate(record);
		return true;
	}
	
	///////////////////////////////////////// help method //////////////////////////////////////////////////
	
	/**
	 * 生成纯数字随机验证码
	 * 
	 * @param length
	 * @return
	 */
	private String genernateCode(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code += new Random().nextInt(10);
		}
		return code;
	}
	
	/**
	 * 执行发送验证码操作
	 * 
	 * @param mobile
	 * @param type
	 * @param msg
	 * @param result
	 * @return
	 */
	private boolean runSendSms(String mobile, BussinessType type, String msg, StringBuilder result) {
		
		// 第三方系统业务编码，由短信平台提供统一编码
		String appBusinessId = sysConfigService.getSMSBusinessIdByType(type);
		// 第三方系统业务名称
		String appBusinessName = sysConfigService.getSMSBusinessNameByType(type);
		// 短信内容：用指定的msg替换其中的占位内容
		String sendMsg = sysConfigService.getSMSBusinessMessageByType(type).replace(CHECK_CODE_REPLACE_MARK, msg);
		
		SystemSMSParam config = sysConfigService.getSystemSMSParam();
		// 重要级别 0:低 1:中 2:高
		String importantLevel = config.getImportantLevel(); 
		// 是否重新发送 0:否 1:是
		String isSendAgain = config.getIsSendAgain(); 
		// 是否过期作废 0:否 1:是
		String isLose = config.getIsLose();
		// 过期作废时间(秒)
		String loseTime = "60"; 
		// 是否有上行 0:否 1:是
		String isUpstream = config.getIsUpstream(); 
		
		try{	
			StringBuffer queryString = new StringBuffer();
		 	queryString.append("?appId=").append(config.getAppId());
		 	queryString.append("&appName=").append(URLEncoder.encode(config.getAppName(), "utf-8"));
		 	queryString.append("&appBusinessId=").append(appBusinessId);
		 	queryString.append("&appBusinessName=").append(URLEncoder.encode(appBusinessName, "utf-8"));
		 	queryString.append("&appAcc=").append(config.getAppAcc());
		 	queryString.append("&appPwd=").append(config.getAppPwd());
		 	queryString.append("&importantLevel=").append(importantLevel);
		 	queryString.append("&isSendAgain=").append(isSendAgain);
		 	queryString.append("&isLose=").append(isLose);
		 	queryString.append("&loseTime=").append(loseTime);
		 	queryString.append("&mobile=").append(mobile);
		 	queryString.append("&msg=").append(URLEncoder.encode(sendMsg, "utf-8"));
		 	queryString.append("&isUpstream=").append(isUpstream);
		 	
			URL url = new URL(config.getUrlRoot() + queryString);
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
			BufferedReader read = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
			
			// 循环读取
			StringBuilder backMsg = new StringBuilder(100);
			String line = null;
			while ((line = read.readLine()) != null) {
				backMsg.append(line);
			}
			
			// 解析结果
			JSONObject bakMsgJson = new JSONObject();
			result.append(bakMsgJson.get("msg").toString());
			logger.info("发送验证码结果：" + result.toString());
			
			// 返回结果
			String ret = bakMsgJson.get("success").toString();
			return ("true".equalsIgnoreCase(ret));
		} catch (Exception e){
			logger.info(e);
			return false;
		}
	}
}
