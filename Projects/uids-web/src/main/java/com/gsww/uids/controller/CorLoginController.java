package com.gsww.uids.controller;

import com.gsww.jup.util.RSAUtil;
import com.gsww.uids.constant.CorporationSessionInfo;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.AuthLogService;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.service.JisLogService;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.LoginException;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.util.AccessUtil;
import com.hanweb.jis.util.SafeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/front")
public class CorLoginController{
	
  private static Logger logger = LoggerFactory.getLogger(CorLoginController.class);
  @Autowired
  private ComplatCorporationService corporationService;

  @Autowired
  private JisApplicationService appService;

  @Autowired
  private AuthLogService authLogService;
  
  @Autowired
  private JisLogService logService;
  
  private static JisSettings jisSettings = new JisSettings();

  @RequestMapping("/corlogin.do")
  public String corporationLogin(Model model,HttpServletResponse response, String action, String appmark, String gotoUrl, String domain)
  {
    if (action == null) {
      action = "";
    }

    if (appmark == null) {
      appmark = "";
    }

    if ((SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark)) || (SafeUtil.isSqlAndXss(gotoUrl)) || 
      (SafeUtil.isSqlAndXss(domain))) {
      return null;
    }
    
    HttpSession session = SpringUtil.getRequest().getSession();
    if (StringUtil.isEmpty(gotoUrl)) {
      gotoUrl = (String)session.getAttribute("gotoUrl");
      if (gotoUrl == null)
        gotoUrl = "";
    }
    else {
      session.setAttribute("gotoUrl", gotoUrl);
    }

    if (StringUtil.isEmpty(domain)) {
      domain = (String)session.getAttribute("domain");
      if (domain == null)
        domain = "";
    }
    else {
      session.setAttribute("domain", domain);
    }

    if ((SafeUtil.isSqlAndXss(gotoUrl)) || (SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark))) {
      gotoUrl = "";
      action = "";
      appmark = "gszw";
    }

    model.addAttribute("sysName",jisSettings.getSysName());
    model.addAttribute("url","docorlogin" );
    model.addAttribute("verifycodeimg","<img id='verifyImg' src='../verifyCode?code=4&var=rand&width=162&height=30&random=" 
    + (int)(Math.random() * 100000000.0D) + "'onclick=\"this.src='../verifyCode?code=4&var=rand&width=140&height=30&random='+ Math.random();\" style='cursor:pointer'  width='140'  height='30' />");
    model.addAttribute("username", ControllerUtil.getCookieValue("frontuser"));
    model.addAttribute("action", action);
    model.addAttribute("appmark", appmark);
    model.addAttribute("gotoUrl", gotoUrl);
    
    return "jis/front/corlogin";
  }

  @RequestMapping("/docorlogin")
  @ResponseBody
  public JsonResult corporationDoLogin(@RequestParam(value="username", required=false) String userName, @RequestParam(value="password", required=false) String password, @RequestParam(value="randomVeryfyCode", required=false) String randomVeryfyCode, @RequestParam(value="action", required=false) String action, @RequestParam(value="appmark", required=false) String appmark, @RequestParam(value="gotoUrl", required=false) String gotoUrl, HttpSession session, HttpServletRequest request,HttpServletResponse response)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    JsonResult jsonResult = JsonResult.getInstance();
    String ip = ControllerUtil.getIp();
    try
    {
      if ((String)session.getAttribute("rand") == null)
      {
        throw new LoginException("verifycode.error");
      }
      if ((randomVeryfyCode == null) || ("".equals(randomVeryfyCode)) || (randomVeryfyCode.length() == 0))
      {
        throw new LoginException("verifycode.error");
      }
      String rand = (String)session.getAttribute("rand");
      if (!rand.equalsIgnoreCase(randomVeryfyCode))
      {
        throw new LoginException("verifycode.error");
      }

      if ((StringUtil.isEmpty(userName)) || (StringUtil.isEmpty(password))) {
        throw new LoginException("login.error");
      }
      String en_password = "";
      try{
          byte[] en_result = hexStringToBytes(password);  
          byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(request).getPrivate(), en_result);  
          StringBuffer sb = new StringBuffer();  
          sb.append(new String(de_result));  
          en_password= sb.reverse().toString();
          en_password = URLDecoder.decode(en_password,"utf-8");
      } catch(Exception ex){
    	  logger.error(ex.getMessage());
      }
      password = en_password;

      ComplatCorporation corporation = this.corporationService.checkUserLogin(userName, password, ip);
      if (corporation != null)
      {
        CorporationSessionInfo.setCurrentCorporationInfo(corporation);
        jsonResult.setSuccess(true);
        ControllerUtil.addCookie(response, "corporationloginid", userName, 
          604800);
        String gotoUrlFlag = "";
        if (StringUtil.isNotEmpty(appmark))
        {
          String ticket = this.authLogService.add(corporation, appmark, 2);

          if (StringUtil.isEmpty(ticket)) {
            throw new LoginException("票据生成失败");
          }

          gotoUrlFlag = jisSettings.getCorGotoUrl();
          jsonResult.addParam("ticket", ticket);
          String domain = StringUtil.getString(session.getAttribute("domain"));
          if (StringUtil.isNotEmpty(domain)) {
            try {
				domain = URLDecoder.decode(domain,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher m = p.matcher(gotoUrlFlag);
            if (m.find()) {
              gotoUrlFlag = StringUtil.replace(gotoUrlFlag, m.group(), domain);
            }

            session.removeAttribute("domain");
          }
        }
        this.corporationService.updateLoginIpAndLoginTime(corporation);
        jsonResult.addParam("gotoUrlFlag", gotoUrlFlag);

        this.logService.add(Integer.valueOf(9), Integer.valueOf(8), userName);
      } else {
        throw new LoginException("您正在进行法人用户登录，用户名或密码不正确!");
      }
    }
    catch (LoginException e)
    {
      session.setAttribute("rand", StringUtil.getUUIDString().substring(0, 4));
      if ("adminlogin.error".equals(e.getMessage())) {
        jsonResult.setSuccess(false);
        jsonResult.addParam("adminerror", "1");
      } else if ("verifycode.error".equals(e.getMessage())) {
        jsonResult.setMessage("验证码错误，请重新输入");
        jsonResult.setSuccess(false);
      } else {
        jsonResult.setSuccess(false);
        jsonResult.setMessage("您正在进行法人用户登录，用户名或密码不正确!");
      }
    }
    return jsonResult;
  }

  @RequestMapping("/corindex")
  public String corIndex(HttpServletResponse response,Model model) {
    ComplatCorporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
    String logouturl = "";
    String loginname = "";
    if (corporation != null) {
      logouturl = "corlogout";
      loginname = corporation.getLoginName();
    }
    else {
    	return "jis/front/corlogin";
    }
    
    model.addAttribute("sysName", jisSettings.getSysName());
    model.addAttribute("logouturl", logouturl);
    model.addAttribute("loginname", loginname);
    model.addAttribute("copyRight", jisSettings.getCopyRight());
    model.addAttribute("verifycodeimg", "<img id='verifyImg' src='../verifyCode?code=4&"
    		+ "var=rand&width=162&height=55&random=" + (int)(Math.random() * 100000000.0D) 
    		+"'onclick=\"this.src='../verifyCode?code=4&var=rand&width=162&height=55&"
    		+ "random='+ Math.random();\" style='cursor:pointer'  width='162'  height='55' />");
    model.addAttribute("username", ControllerUtil.getCookieValue("frontuser"));
    
    return "jis/front/corindex";
  }

  @RequestMapping("/corlogout")
  public ModelAndView corLogout(HttpSession session) {
    if (CorporationSessionInfo.getFrontCurrentCorporationInfo() != null) {
      String loginname = CorporationSessionInfo.getFrontCurrentCorporationInfo().getLoginName();
      session.removeAttribute("corporationinfo");
      this.logService.add(Integer.valueOf(10), Integer.valueOf(8), loginname);
    }
    String appmark = StringUtil.getString(session.getAttribute("appmark"));
    ModelAndView modelAndView = new ModelAndView();
    RedirectView redirectView = new RedirectView("corlogin.do");
    modelAndView.addObject("appmark", appmark);
    modelAndView.setView(redirectView);
    return modelAndView;
  }
  @RequestMapping("/findcorurl")
  @ResponseBody
  public JsonResult findCorLogoff() {
    JsonResult jsonResult = JsonResult.getInstance();
    String logoffurl = this.appService.findURLBylogoff(1);
    if (logoffurl.length() > 0) {
      jsonResult.set(ResultState.OPR_SUCCESS);
      jsonResult.setMessage(JsonUtil.objectToString(logoffurl));
    } else {
      jsonResult.set(ResultState.OPR_FAIL);
    }
    return jsonResult;
  }

  @RequestMapping("/checkCorWhetherInputByGuestExist")
  @ResponseBody
  public JsonResult checkWhetherInputByGuestExist(HttpSession session, String inputByGuest)
  {
    JsonResult jsonResult = JsonResult.getInstance();
    if (StringUtil.isEmpty(inputByGuest)) {
      jsonResult.setSuccess(false);
      return jsonResult;
    }
    ComplatCorporation corporation = this.corporationService.findByManyWay(inputByGuest);
    if (corporation == null) {
      jsonResult.setSuccess(false);
    } else {
      session.setAttribute("corporation", corporation);
      jsonResult.setSuccess(true);
    }
    return jsonResult;
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
}