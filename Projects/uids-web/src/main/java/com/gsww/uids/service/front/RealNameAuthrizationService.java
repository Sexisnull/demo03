package com.gsww.uids.service.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.util.HttpClientUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;

@Transactional
@Service("perRealNameAuthService")
public class RealNameAuthrizationService {
	private final Log logger = LogFactory.getLog(getClass());

	 public JsonResult verifyCorRealName(ComplatCorporation corPorationFormBean)
	  {
	    boolean bl = false;
	    String charset = "utf-8";
	    JsonResult jsonResult = JsonResult.getInstance();
	    String syscode = JisSettings.getSettings().getCorRequestCod();
	    String corAuthUsername = JisSettings.getSettings().getCorRealUsername();
	    String corAuthUserPassword = JisSettings.getSettings().getCorRealPassword();
	    int isRealNameAuth = NumberUtil.getInt(JisSettings.getSettings()
	      .getEnableCorRealNameAuth());
	    int corType = NumberUtil.getInt(corPorationFormBean.getType());
	    if ((isRealNameAuth == 1) && (corType == 1)) {
	      String tokenUrl = StringUtil.getString(JisSettings.getSettings().getCorExchangeTokenUrl());
	      String verifyUrl = StringUtil.getString(JisSettings.getSettings().getCorCompareRealNameUrl());
	      String verifyUrl1 = StringUtil.getString(JisSettings.getSettings().getCorDetailRealNameUrl());
	      if ((StringUtil.isEmpty(tokenUrl)) || (StringUtil.isEmpty(verifyUrl)) || (StringUtil.isEmpty(syscode)) || 
	        (StringUtil.isEmpty(corAuthUsername)) || (StringUtil.isEmpty(corAuthUserPassword))) {
	        corPorationFormBean.setisAuth(Integer.valueOf(0));
	        corPorationFormBean.setauthState(Integer.valueOf(0));
	        jsonResult.setSuccess(true);
	        jsonResult.setMessage("1");
	      } else {
	        String tokenJson = "";
	        String connectError = "connect error";
	        try
	        {
	          List nameValuePairList = new ArrayList();
	          nameValuePairList.add(new BasicNameValuePair("syscode", syscode));
	          nameValuePairList.add(new BasicNameValuePair("username", corAuthUsername));
	          nameValuePairList.add(new BasicNameValuePair("password", corAuthUserPassword));
	          tokenJson = HttpClientUtil.postInfo(tokenUrl, nameValuePairList, charset);
	          this.logger.debug("tokenJson ==== " + tokenJson);
	          if (tokenJson.contains(connectError))
	            throw new Exception("token获取接口网络连接异常");
	        }
	        catch (Exception e) {
	          corPorationFormBean.setisAuth(Integer.valueOf(0));
	          corPorationFormBean.setauthState(Integer.valueOf(0));
	          jsonResult.setMessage("2");
	          jsonResult.setSuccess(true);
	          this.logger.error("法人实名认证接口异常：" + e);
	          return jsonResult;
	        }
	        try
	        {
	          String gszch = "";
	          String zzjgdm = "";

	          String verify_mode = "1";
	          String tyshxydm = "";
	          String qymcqc = "";
	          Map tokenMap = (Map)JsonUtil.StringToObject(tokenJson, Map.class);
	          if (StringUtil.equals("0", StringUtil.getString(((Map)tokenMap.get("error")).get("code")))) {
	            String token = StringUtil.getString(((Map)tokenMap.get("rst")).get("token"));
	            if (StringUtil.isNotEmpty(token))
	            {
	              gszch = corPorationFormBean.getRegNumber();
	              tyshxydm = corPorationFormBean.getRegNumber();
	              zzjgdm = corPorationFormBean.getOrgNumber();
	              qymcqc = corPorationFormBean.getName();
	              if (corPorationFormBean.getType().intValue() == 1) {
	                if (("9".equals(gszch.substring(0, 1))) && (gszch.length() == 18))
	                  verify_mode = "3";
	                else {
	                  verify_mode = "4";
	                }

	              }

	              List nameValuePairList = new ArrayList();
	              nameValuePairList.add(new BasicNameValuePair("zzjgdm", zzjgdm));
	              nameValuePairList.add(new BasicNameValuePair("tyshxydm", tyshxydm));
	              nameValuePairList.add(new BasicNameValuePair("gszch", gszch));
	              nameValuePairList.add(new BasicNameValuePair("qymcqc", qymcqc));
	              String tmpUrl = "/" + verify_mode + "/" + syscode + "/" + token;
	              verifyUrl = verifyUrl + tmpUrl;
	              this.logger.debug("corRealNameAuth verifyUrl====" + verifyUrl);
	              String verifyJson = HttpClientUtil.postInfo(verifyUrl, nameValuePairList, charset);
	              this.logger.debug("corRealNameAuth verifyJson====" + verifyJson);
	              if (verifyJson.contains(connectError)) {
	                throw new Exception("实名认证网络连接异常");
	              }
	              Map verifyMap = (Map)JsonUtil.StringToObject(verifyJson, Map.class);
	              String code = StringUtil.getString(((Map)verifyMap.get("error")).get("code"));
	              String rst = StringUtil.getString(verifyMap.get("rst"));
	              if ((StringUtil.equals("0", code)) && (StringUtil.equals("true", rst))) {
	                this.logger.debug("法人实名认证成功");
	                verifyUrl1 = verifyUrl1 + tmpUrl;
	                String verifyJson1 = HttpClientUtil.postInfo(verifyUrl1, nameValuePairList, charset);
	                if (verifyJson1.contains(connectError)) {
	                  throw new Exception("实名认证网络连接异常");
	                }
	                Map verifyMap1 = (Map)JsonUtil.StringToObject(verifyJson1, Map.class);
	                String code1 = StringUtil.getString(((Map)verifyMap1.get("error")).get("code"));
	                String rst1 = StringUtil.getString(verifyMap1.get("rst"));
	                if ((StringUtil.equals("0", code1)) && (!"".equals(rst1))) {
	                  String address = StringUtil.getString(((Map)verifyMap1.get("rst")).get("fr_dz"));
	                  if (StringUtil.isNotEmpty("address")) {
	                    corPorationFormBean.setAddress(address);
	                  }
	                }
	                corPorationFormBean.setisAuth(Integer.valueOf(1));
	                corPorationFormBean.setauthState(Integer.valueOf(1));
	                jsonResult.setSuccess(true);
	                jsonResult.setMessage("5"); 
	                //break label1286;
	              }
	              this.logger.debug("法人实名认证失败");
	              corPorationFormBean.setisAuth(Integer.valueOf(1));
	              corPorationFormBean.setauthState(Integer.valueOf(2));
	              jsonResult.setSuccess(false);
	              jsonResult.setMessage("4"); 
	              //break label1286;
	            }

	            this.logger.debug("法人实名认证token为空");
	            corPorationFormBean.setisAuth(Integer.valueOf(0));
	            corPorationFormBean.setauthState(Integer.valueOf(0));
	            jsonResult.setSuccess(true);
	            jsonResult.setMessage("3");
	            return jsonResult;
	          }

	          this.logger.debug("法人实名认证调用token接口失败");
	          corPorationFormBean.setisAuth(Integer.valueOf(0));
	          corPorationFormBean.setauthState(Integer.valueOf(0));
	          jsonResult.setSuccess(true);
	          jsonResult.setMessage("3");
	          return jsonResult;
	        }
	        catch (Exception e) {
	          this.logger.debug("法人实名认证调用接口异常" + e);
	          corPorationFormBean.setisAuth(Integer.valueOf(0));
	          corPorationFormBean.setauthState(Integer.valueOf(0));
	          jsonResult.setSuccess(true);
	          jsonResult.setMessage("6");
	        }
	      }
	    } else {
	      corPorationFormBean.setisAuth(Integer.valueOf(0));
	      corPorationFormBean.setauthState(Integer.valueOf(0));
	      jsonResult.setSuccess(true);
	      jsonResult.setMessage("0");
	    }
	    return jsonResult;
	  }
}
