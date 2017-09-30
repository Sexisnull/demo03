package com.gsww.uids.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gsww.jup.util.CellphoneShortMessageUtil;
import com.gsww.jup.util.RSAUtil;
import com.gsww.jup.util.RandomCodeUtil;
import com.gsww.uids.constant.CorporationSessionInfo;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.util.AccessUtil;
import com.gsww.uids.util.JsonResult;
import com.gsww.uids.util.ResultState;
import com.gsww.uids.util.exception.OperationException;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.security.SecurityUtil;

@Controller
@RequestMapping({"front/register"})
public class CorRegisterController
{
  private static Logger logger = LoggerFactory.getLogger(CorRegisterController.class);
  @Autowired
  private ComplatCorporationService corporationService;

  @RequestMapping({"corregister"})
  public ModelAndView corRegister_Step1(HttpServletRequest request, HttpServletResponse response, HttpSession session, String aa, String bb,Model model)
  {
    ModelAndView modelAndView = new ModelAndView("jis/front/corregister_step1");

    session.setMaxInactiveInterval(1800);
    session.setAttribute("cormobilesend", RandomCodeUtil.getRandomNumber(9));

    model.addAttribute("copyRight", JisSettings.getSettings().getCopyRight());
    model.addAttribute("url", "docorregister");
    model.addAttribute("verifycodeimg", "<img id='verifyImg' src='../../verifyCode.do?code=4&var=rand&width=162&height=40&random=" + 
    	      (int)(Math.random() * 100000000.0D) + 
    	      "'" + 
    	      " onclick=\"this.src='../../verifyCode?code=4&var=rand&width=162&height=40&random='+ Math.random()" + 
    	      ";\" style='cursor:pointer'  width='162'  height='40' />");
    return modelAndView;
  }

  @RequestMapping({"docorregister"})
  @ResponseBody
  public JsonResult corregister_submit(String randCode, HttpSession session, ComplatCorporation corPorationFormBean, String smsCode, String corsex, String inssex,HttpServletRequest request)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    JsonResult jsonResult = JsonResult.getInstance();
    boolean isSuccess = false;

    String orgNumber = "";
    String rand = (String)session.getAttribute("rand");
    //TODO 注册验证短信验证码
    //String cellphoneShortMessageRandomCodeMadeByJava = StringUtil.getString(session.getAttribute("corRegistSmsCode"));
    try {
      if ((StringUtil.isEmpty(rand)) || (StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
        session.setAttribute("rand", null);
        throw new OperationException("register.randcode.error");
      }

      /*if (!corPorationFormBean.getMobile().equals(session.getAttribute("telNum"))) {
        throw new OperationException("register.phonecode.error");
      }*/
      /*if (StringUtil.isNotEmpty(smsCode)) {
        if (!StringUtil.equals(smsCode, 
          StringUtil.getString(cellphoneShortMessageRandomCodeMadeByJava)))
          throw new OperationException("register.phonecode.error");
      }
      else {
        throw new OperationException("register.phonecodeempty.error");
      }*/

      if (corPorationFormBean.getType() == null) {
        throw new OperationException("register.nocortype.error");
      }
      String name = corPorationFormBean.getName();
      String realName = corPorationFormBean.getRealName();
      String nation = corPorationFormBean.getNation();
      String cardNumber = corPorationFormBean.getCardNumber();
      orgNumber = corPorationFormBean.getOrgNumber();

      if (corPorationFormBean.getType().intValue() == 1) {
        name = name.split(",")[0];
        realName = realName.split(",")[0];
        cardNumber = cardNumber.split(",")[0];
        nation = nation.split(",")[0];
        if (!StringUtil.equals(",", orgNumber))
          orgNumber = orgNumber.split(",")[0];
        else {
          orgNumber = "";
        }
        corPorationFormBean.setSex(corsex);
      } else {
        corPorationFormBean.setRegNumber("");
        name = name.split(",")[1];
        realName = realName.split(",")[1];
        cardNumber = cardNumber.split(",")[1];
        nation = nation.split(",")[1];
        if (!StringUtil.equals(",", orgNumber))
          orgNumber = orgNumber.split(",")[1];
        else {
          orgNumber = "";
        }
        corPorationFormBean.setSex(inssex);
      }
      corPorationFormBean.setRealName(realName);
      corPorationFormBean.setName(name);
      corPorationFormBean.setNation(nation);
      corPorationFormBean.setCardNumber(cardNumber);
      corPorationFormBean.setOrgNumber(orgNumber);

      if ((StringUtil.isEmpty(corPorationFormBean.getLoginName())) || (StringUtil.isEmpty(corPorationFormBean.getPwd()))) {
        jsonResult.setMessage("用户名或密码为空");
        jsonResult.setSuccess(false);
        return jsonResult;
      }
      String loginName = corPorationFormBean.getLoginName();
      String password = dePwd(corPorationFormBean.getPwd(),request);
      if ((StringUtil.isEmpty(loginName)) || (StringUtil.isEmpty(password))) {
        jsonResult.setMessage("用户名或密码为空");
        jsonResult.setSuccess(false);
        return jsonResult;
      }
      corPorationFormBean.setLoginName(loginName);
      corPorationFormBean.setPwd(password);

      isSuccess = this.corporationService.add(corPorationFormBean);
      if (isSuccess)
      {
        jsonResult.set(ResultState.ADD_SUCCESS);
    	jsonResult.setSuccess(true);
        CorporationSessionInfo.setCurrentCorporationInfo(corPorationFormBean);
      } else {
        jsonResult.set(ResultState.ADD_FAIL);
        jsonResult.setMessage("注册失败！");
      }
    }
    catch (OperationException e) {
      if ("register.randcode.error".equals(e.getMessage()))
        jsonResult.setMessage("验证码错误");
      else if ("register.phonecode.error".equals(e.getMessage()))
        jsonResult.setMessage("手机验证码错误");
      else if ("register.phonecodeempty.error".equals(e.getMessage()))
        jsonResult.setMessage("手机验证码为空");
      else if ("register.nocortype.error".equals(e.getMessage()))
        jsonResult.setMessage("请选择法人类型");
      else {
        jsonResult.setMessage(e.getMessage());
      }
      jsonResult.setSuccess(false);
    }
    return jsonResult;
  }
  
  @SuppressWarnings("unused")
  private String dePwd(String password,HttpServletRequest request){
	  String en_password = "";
	    try{
	        byte[] en_result = hexStringToBytes(password);  
	        byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(request).getPrivate(), en_result);  
	        StringBuffer sb = new StringBuffer();  
	        sb.append(new String(de_result));  
	        en_password= sb.reverse().toString(); 
	    } catch(Exception ex){
	  	  logger.error(ex.getMessage());
	    }
	    return en_password;
  }
  
  /**
   * 16进制 To byte[]
   * @param hexString
   * @return byte[]
   */
  public static byte[] hexStringToBytes(String hexString) {
      if (hexString == null || hexString.equals("")) {
          return null;
      }
      hexString = hexString.toUpperCase();
      int length = hexString.length() / 2;
      char[] hexChars = hexString.toCharArray();
      byte[] d = new byte[length];
      for (int i = 0; i < length; i++) {
          int pos = i * 2;
          d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
      }
      return d;
  }
  
  /**
   * Convert char to byte
   * @param c char
   * @return byte
   */
   private static byte charToByte(char c) {
      return (byte) "0123456789ABCDEF".indexOf(c);
  }

  @RequestMapping({"sendCellphoneShortMessageCorRe"})
  @ResponseBody
  public String sendCellphoneShortMessageCorRe(HttpSession session, String telNum)
  {
    Map<String,String> map1 = new HashMap<String,String>();
    Object mobileSend = session.getAttribute("cormobilesend");
    if ((StringUtil.isEmpty(telNum)) || (mobileSend == null)) {
      map1.put("message", "请刷新页面，重新注册！");
      map1.put("success", "false");
      map1.put("code", "0");
      return JsonUtil.objectToString(map1);
    }

    Object currentTimes = session.getAttribute("cormobiletimes" + telNum);
    if (currentTimes != null) {
      int times = NumberUtil.getInt(currentTimes);
      if (times > 5) {
        map1.put("message", "超过最大短信发送次数");
        map1.put("success", "false");
        map1.put("code", "0");
        return JsonUtil.objectToString(map1);
      }
    }

    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
    session.setAttribute("corRegistSmsCode", cellphoneShortMessageRandomCodeMadeByJava);
    String content = JisSettings.getSettings().getRegistCorMessageContent().trim()
      .replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
    String appBusinessId = JisSettings.getSettings().getBusinessIdForRegestingCor().trim();
    String appBusinessName = JisSettings.getSettings().getBusinessNameForRegestingCor().trim();

    int lostTime = 60;
    session.setAttribute("telNum", telNum);

    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
    String resultJson = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, lostTime);
    Map map = (Map)JsonUtil.StringToObject(resultJson, Map.class);
    if ("true".equals(map.get("success")))
    {
      if (currentTimes == null) {
        session.setAttribute("cormobiletimes" + telNum, Integer.valueOf(1));
      } else {
        int times = NumberUtil.getInt(currentTimes);
        session.setAttribute("cormobiletimes" + telNum, Integer.valueOf(times + 1));
      }
    }
    return resultJson;
  }

  /*@RequestMapping({"checkcorloginid"})
  @ResponseBody
  public String checkCorLoginId(String loginid)
  {
    if (StringUtil.isEmpty(loginid)) {
      return "";
    }
    Corporation corporation = null;
    corporation = (Corporation)CacheUtil.getValue(loginid, "corusers");
    if (corporation == null) {
      corporation = this.corporationService.findByLoginName(loginid);
    }
    if (corporation != null) {
      return "存在相同的法人用户";
    }
    return "";
  }

  @RequestMapping({"checkcormobile"})
  @ResponseBody
  public String checkCorMobile(String telNum) {
    if (StringUtil.isEmpty(telNum)) {
      return "";
    }
    Corporation corporation = null;
    corporation = this.corporationService.findByMobile(telNum);
    if (corporation == null) {
      return "";
    }
    return "存在相同的手机号码";
  }*/

  @RequestMapping({"corregsuccess"})
  public ModelAndView perRegSuccess(HttpServletRequest request, HttpSession session,Model model)
  {
    ModelAndView modelAndView = new ModelAndView("jis/front/corregsuccess");
    String html = "corregsuccess.html";
    model.addAttribute("loginurl", "../corlogin.do?appmark=gszw");
    return modelAndView;
  }
}