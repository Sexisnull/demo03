package com.gsww.jup.util;

import com.gsww.uids.constant.JisSettings;
import com.hanweb.common.util.JsonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CellphoneShortMessageUtil
{
  public String sendPhoneShortMessage(String telNum, String content, String appBusinessId, String appBusinessName, int loseTime)
  {
    JisSettings settings = new JisSettings();
    String appId = settings.getAppId().trim();
    String appName = settings.getAppName().trim();
    String appAcc = settings.getAppAcc().trim();
    String appPwd = settings.getAppPpd().trim();
    String importantLevel = settings.getImportantLevel().trim();
    String isSendAgain = settings.getIsSendAgain().trim();
    String isLose = settings.getIsLose().trim();
    String isUpstream = settings.getIsUpstream().trim();
    String urlRoot = settings.getUrlRoot().trim();
    System.out.println(content);

    StringBuffer queryString = new StringBuffer();
    try
    {
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
      queryString.append("&mobile=").append(telNum);
      queryString.append("&msg=").append(URLEncoder.encode(content, "utf-8"));
      queryString.append("&isUpstream=").append(isUpstream);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      String error = "发短信读取配置文件参数连接连接抛异常。there is a exception of reading connection parameters when sending Cellphoneshortmessage ";

      System.out.println(error);
      Map<String,String> map = new HashMap<String,String>();
      map.put("success", "false");
      map.put("code", "0");
      map.put("msg", error);
      return JsonUtil.objectToString(map);
    }

    try
    {
      String result = "";
      BufferedReader read = null;
      URL url = new URL(urlRoot + queryString);
      HttpURLConnection http = (HttpURLConnection)url.openConnection();
      http.setRequestMethod("GET");
      http.setConnectTimeout(0);
      http.setInstanceFollowRedirects(true);
      http.setRequestProperty("Content-type", "text/html");
      http.setRequestProperty("Accept-Charset", "utf-8");
      http.setRequestProperty("contentType", "utf-8");
      http.setDefaultUseCaches(false);
      http.setDoOutput(true);

      //TODO 注释短信下发访问
//      read = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
//      String line;
//      while ((line = read.readLine()) != null)
//      {
//        result = result + line;
//      }
//      return result;
      Map<String,String> map = new HashMap<String,String>();
      String success = "短信密码下发成功！";
      map.put("success", "true");
      map.put("code", "1");
      map.put("msg", success);
      return JsonUtil.objectToString(map);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      String error = "发短信连接抛异常或读取短信接口的返回值异常。there is a exception of creating connection when sending Cellphoneshortmessage ";

      System.out.println(error);
      Map<String,String> map = new HashMap<String,String>();
      map.put("success", "false");
      map.put("code", "0");
      map.put("msg", error);
      return JsonUtil.objectToString(map);
    }
  }
}