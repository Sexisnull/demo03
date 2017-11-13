package com.gsww.uids.service.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.util.HttpClientUtil;
import com.gsww.uids.util.JsonResult;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

@Transactional
@Service("realNameAuthService")
public class PerRealNameAuthService
{
  private final Log logger = LogFactory.getLog(getClass());

  @Autowired
  private JisLogService jisLogService;

  @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public JsonResult verifyPerRealName(ComplatOutsideuser outsideUserFormBean) { boolean b = true;
    String charset = "utf-8";
    JsonResult jsonResult = JsonResult.getInstance();
    String syscode = JisSettings.getSettings().getCorRequestCod();
    String perAuthUserName = JisSettings.getSettings().getCorRealUsername();
    String perAuthUserPassword = JisSettings.getSettings().getCorRealPassword();
    int isRealNameAuth = NumberUtil.getInt(JisSettings.getSettings().getEnableCorRealNameAuth());

    if (isRealNameAuth == 1) {
      String tokenUrl = StringUtil.getString(JisSettings.getSettings().getCorExchangeTokenUrl());
      String verifyUrl = StringUtil.getString(JisSettings.getSettings().getPerCompareRealNameUrl());

      if ((StringUtil.isEmpty(verifyUrl)) || (StringUtil.isEmpty(tokenUrl)) || (StringUtil.isEmpty(syscode)) || 
        (StringUtil.isEmpty(perAuthUserPassword)) || (StringUtil.isEmpty(perAuthUserName))) {
    	  outsideUserFormBean.setIsAuth(Integer.valueOf(0));
        outsideUserFormBean.setAuthState(Integer.valueOf(0));
        jsonResult.setSuccess(true);
        jsonResult.setMessage("1");
      } else {
        String tokenJson = "";
        String connectError = "connect error";
        try
        {
          List nameValuePairList = new ArrayList();
          nameValuePairList.add(new BasicNameValuePair("syscode", syscode));
          nameValuePairList.add(new BasicNameValuePair("username", perAuthUserName));
          nameValuePairList.add(new BasicNameValuePair("password", perAuthUserPassword));
          tokenJson = HttpClientUtil.postInfo(tokenUrl, nameValuePairList, charset);
          this.logger.debug("tokenJson ==== " + tokenJson);
          if (tokenJson.contains(connectError))
            throw new Exception("token获取接口网络连接异常");
        }
        catch (Exception e) {
          outsideUserFormBean.setIsAuth(Integer.valueOf(0));
          outsideUserFormBean.setAuthState(Integer.valueOf(0));
          jsonResult.setSuccess(true);
          jsonResult.setMessage("2");
          this.logger.error("个人实名认证接口异常：" + e);
          return jsonResult;
        }
        try
        {
          String name = "";
          String id = "";
          Map tokenMap = (Map)JsonUtil.StringToObject(tokenJson, Map.class);
          if (StringUtil.equals("0", StringUtil.getString(((Map)tokenMap.get("error")).get("code")))) {
            String token = StringUtil.getString(((Map)tokenMap.get("rst")).get("token"));
            if (StringUtil.isNotEmpty(token)) {
              name = outsideUserFormBean.getName();
              id = outsideUserFormBean.getPapersNumber();

              List nameValuePairList = new ArrayList();
              nameValuePairList.add(new BasicNameValuePair("name", name));
              nameValuePairList.add(new BasicNameValuePair("id", id));

              String tmpUrl = "/" + syscode + "/" + token;
              verifyUrl = verifyUrl + tmpUrl;

              this.logger.debug("perRealNameAuth verifyUrl====" + verifyUrl);
              String verifyJson = HttpClientUtil.postInfo(verifyUrl, nameValuePairList, charset);
              this.logger.debug("corRealNameAuth verifyJson====" + verifyJson);
              if (verifyJson.contains(connectError)) {
                throw new Exception("实名认证网络连接异常");
              }
              Map verifyMap = (Map)JsonUtil.StringToObject(verifyJson, Map.class);
              String code = StringUtil.getString(((Map)verifyMap.get("error")).get("code"));
              String rst = StringUtil.getString(verifyMap.get("rst"));
              if ((StringUtil.equals("0", code)) && (StringUtil.equals("true", rst))) {
                this.logger.debug("个人实名认证成功");
                outsideUserFormBean.setIsAuth(Integer.valueOf(0));
                outsideUserFormBean.setAuthState(Integer.valueOf(1));
                jsonResult.setSuccess(true);
                jsonResult.setMessage("5"); 
               // break;
              }
              this.logger.debug("个人实名认证失败");
              outsideUserFormBean.setIsAuth(Integer.valueOf(0));
              outsideUserFormBean.setAuthState(Integer.valueOf(2));
              jsonResult.setSuccess(false);
              jsonResult.setMessage("实名认证失败，请仔细检查姓名和身份证！"); 
             // break;
            }

            this.logger.debug("个人实名认证token为空");
            outsideUserFormBean.setIsAuth(Integer.valueOf(0));
            outsideUserFormBean.setAuthState(Integer.valueOf(0));
            jsonResult.setSuccess(true);
            jsonResult.setMessage("3");
            return jsonResult;
          }

          this.logger.debug("个人实名认证调用token接口失败");
          outsideUserFormBean.setIsAuth(Integer.valueOf(0));
          outsideUserFormBean.setAuthState(Integer.valueOf(0));
          jsonResult.setSuccess(true);
          jsonResult.setMessage("3");
          return jsonResult;
        }
        catch (Exception e) {
          this.logger.debug("个人实名认证调用接口异常" + e);
          outsideUserFormBean.setIsAuth(Integer.valueOf(0));
          outsideUserFormBean.setAuthState(Integer.valueOf(0));
          jsonResult.setSuccess(true);
          jsonResult.setMessage("6");
        }
      }
    } else {
    	outsideUserFormBean.setIsAuth(Integer.valueOf(0));
      outsideUserFormBean.setAuthState(Integer.valueOf(0));
      jsonResult.setSuccess(true);
      jsonResult.setMessage("0");
    }
    //label956: 
    return jsonResult;
  }
}
