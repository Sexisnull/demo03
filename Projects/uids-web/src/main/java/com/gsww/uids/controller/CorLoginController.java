//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.JsonUtil;
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.ControllerUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.common.util.security.SecurityUtil;
//import com.hanweb.complat.entity.Corporation;
//import com.hanweb.complat.exception.LoginException;
//import com.hanweb.complat.service.CorporationService;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.listener.CorporationSessionInfo;
//import com.hanweb.jis.service.AppService;
//import com.hanweb.jis.service.AuthLogService;
//import com.hanweb.jis.service.LogService;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.SafeUtil;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping({"front"})
//public class CorLoginController
//{
////  private static final String SESSION_KEY = "frontuser";
//
//  @Autowired
//  private CorporationService corporationService;
//
//  @Autowired
//  private LogService logService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private AppService appService;
//
//  @Autowired
//  private AuthLogService authLogService;
//
//  @RequestMapping({"corlogin"})
//  public ModelAndView corporationLogin(Model model,HttpServletResponse response, String action, String appmark, String gotoUrl, String domain)
//  {
//    if (action == null) {
//      action = "";
//    }
//
//    if (appmark == null) {
//      appmark = "";
//    }
//
//    if ((SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark)) || (SafeUtil.isSqlAndXss(gotoUrl)) || 
//      (SafeUtil.isSqlAndXss(domain))) {
//      return null;
//    }
//    try
//    {
//      String publicKey = SecurityUtil.getPublicKey();
//      if ((publicKey != null) && (!"".equals(publicKey))) {
//        Cookie keyCookie = new Cookie("_pubk", URLEncoder.encode(publicKey, "UTF-8"));
//        keyCookie.setMaxAge(31536000);
//        response.addCookie(keyCookie);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    HttpSession session = SpringUtil.getRequest().getSession();
//    if (StringUtil.isEmpty(gotoUrl)) {
//      gotoUrl = (String)session.getAttribute("gotoUrl");
//      if (gotoUrl == null)
//        gotoUrl = "";
//    }
//    else {
//      session.setAttribute("gotoUrl", gotoUrl);
//    }
//
//    if (StringUtil.isEmpty(domain)) {
//      domain = (String)session.getAttribute("domain");
//      if (domain == null)
//        domain = "";
//    }
//    else {
//      session.setAttribute("domain", domain);
//    }
//
//    if ((SafeUtil.isSqlAndXss(gotoUrl)) || (SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark))) {
//      gotoUrl = "";
//      action = "";
//      appmark = "gszw";
//    }
//
//    ModelAndView modelAndView = new ModelAndView("jis/front/corlogin");
//    String html = "corlogin.html";
//
//    String templateHtml = this.templateService.readFrontTemplate(html);
//
//    templateHtml = templateHtml
//      .replace("${sysName}", Settings.getSettings().getSysName())
//      .replace("${url}", "docorlogin.do")
//      .replace(
//      "${verifycodeimg}", 
//      "<img id='verifyImg' src='../verifyCode.do?code=4&var=rand&width=162&height=30&random=" + 
//      (int)(Math.random() * 100000000.0D) + 
//      "'" + 
//      " onclick=\"this.src='../verifyCode.do?code=4&var=rand&width=140&height=30&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='140'  height='30' />")
//      .replace("${username}", ControllerUtil.getCookieValue("frontuser"))
//      .replace("${action}", action).replace("${appmark}", appmark)
//      .replace("${gotoUrl}", gotoUrl);
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"docorlogin"})
//  @ResponseBody
//  public JsonResult corporationDoLogin(@RequestParam(value="username", required=false) String userName, @RequestParam(value="password", required=false) String password, @RequestParam(value="randomVeryfyCode", required=false) String randomVeryfyCode, @RequestParam(value="action", required=false) String action, @RequestParam(value="appmark", required=false) String appmark, @RequestParam(value="gotoUrl", required=false) String gotoUrl, HttpSession session, HttpServletResponse response)
//  {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    JsonResult jsonResult = JsonResult.getInstance();
//    String ip = ControllerUtil.getIp();
//    try
//    {
//      if ((String)session.getAttribute("rand") == null)
//      {
//        throw new LoginException("verifycode.error");
//      }
//      if ((randomVeryfyCode == null) || ("".equals(randomVeryfyCode)) || (randomVeryfyCode.length() == 0))
//      {
//        throw new LoginException("verifycode.error");
//      }
//      String rand = (String)session.getAttribute("rand");
//      if (!rand.equalsIgnoreCase(randomVeryfyCode))
//      {
//        throw new LoginException("verifycode.error");
//      }
//
//      if ((StringUtil.isEmpty(userName)) || (StringUtil.isEmpty(password))) {
//        throw new LoginException("login.error");
//      }
//      userName = SecurityUtil.RSAdecode(userName);
//      password = SecurityUtil.RSAdecode(password);
//
//      Corporation corporation = this.corporationService.checkUserLogin(userName, password, ip);
//      if (corporation != null)
//      {
//        CorporationSessionInfo.setCurrentCorporationInfo(corporation);
//        jsonResult.setSuccess(true);
//        ControllerUtil.addCookie(response, "corporationloginid", userName, 
//          604800);
//        String gotoUrlFlag = "";
//        if (StringUtil.isNotEmpty(appmark))
//        {
//          String ticket = this.authLogService.add(corporation, appmark, 2);
//
//          if (StringUtil.isEmpty(ticket)) {
//            throw new LoginException("票据生成失败");
//          }
//
//          Settings settings = Settings.getSettings();
//          gotoUrlFlag = settings.getCorGotoUrl();
//          jsonResult.addParam("ticket", ticket);
//          String domain = StringUtil.getString(session.getAttribute("domain"));
//          if (StringUtil.isNotEmpty(domain)) {
//            try {
//				domain = URLDecoder.decode(domain,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
//            Matcher m = p.matcher(gotoUrlFlag);
//            if (m.find()) {
//              gotoUrlFlag = StringUtil.replace(gotoUrlFlag, m.group(), domain);
//            }
//
//            session.removeAttribute("domain");
//          }
//        }
//        this.corporationService.updateLoginIpAndLoginTime(corporation);
//        jsonResult.addParam("gotoUrlFlag", gotoUrlFlag);
//
//        this.logService.add(Integer.valueOf(9), Integer.valueOf(8), userName);
//      } else {
//        throw new LoginException("您正在进行法人用户登录，用户名或密码不正确!");
//      }
//    }
//    catch (LoginException e)
//    {
//      session.setAttribute("rand", StringUtil.getUUIDString().substring(0, 4));
//      if ("adminlogin.error".equals(e.getMessage())) {
//        jsonResult.setSuccess(false);
//        jsonResult.addParam("adminerror", "1");
//      } else if ("verifycode.error".equals(e.getMessage())) {
//        jsonResult.setMessage("验证码错误，请重新输入");
//        jsonResult.setSuccess(false);
//      } else {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("您正在进行法人用户登录，用户名或密码不正确!");
//      }
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"corindex"})
//  public ModelAndView corIndex(HttpServletResponse response) {
//    ModelAndView modelAndView = new ModelAndView("jis/front/corindex");
//    Corporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//    String logouturl = "";
//    String loginname = "";
//    if (corporation != null) {
//      logouturl = "corlogout.do";
//      loginname = corporation.getLoginName();
//    }
//    else {
//      RedirectView redirectView = new RedirectView("./corlogin.do");
//      modelAndView.setView(redirectView);
//      return modelAndView;
//    }
//
//    String html = "corindex.html";
//    String templateHtml = this.templateService.readFrontTemplate(html);
//
//    templateHtml = templateHtml
//      .replace("${sysName}", Settings.getSettings().getSysName())
//      .replace("${logouturl}", logouturl)
//      .replace("${loginname}", loginname)
//      .replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace(
//      "${verifycodeimg}", 
//      "<img id='verifyImg' src='../verifyCode.do?code=4&var=rand&width=162&height=55&random=" + 
//      (int)(Math.random() * 100000000.0D) + 
//      "'" + 
//      " onclick=\"this.src='../verifyCode.do?code=4&var=rand&width=162&height=55&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='162'  height='55' />")
//      .replace("${username}", ControllerUtil.getCookieValue("frontuser"));
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"corlogout"})
//  public ModelAndView corLogout(HttpSession session) {
//    if (CorporationSessionInfo.getFrontCurrentCorporationInfo() != null) {
//      String loginname = CorporationSessionInfo.getFrontCurrentCorporationInfo().getLoginName();
//      session.removeAttribute("corporationinfo");
//      this.logService.add(Integer.valueOf(10), Integer.valueOf(8), loginname);
//    }
//    String appmark = StringUtil.getString(session.getAttribute("appmark"));
//    ModelAndView modelAndView = new ModelAndView();
//    RedirectView redirectView = new RedirectView("corlogin.do");
//    modelAndView.addObject("appmark", appmark);
//    modelAndView.setView(redirectView);
//    return modelAndView;
//  }
//  @RequestMapping({"findcorurl"})
//  @ResponseBody
//  public JsonResult findCorLogoff() {
//    JsonResult jsonResult = JsonResult.getInstance();
//    String logoffurl = this.appService.findURLBylogoff(1);
//    if (logoffurl.length() > 0) {
//      jsonResult.set(ResultState.OPR_SUCCESS);
//      jsonResult.setMessage(JsonUtil.objectToString(logoffurl));
//    } else {
//      jsonResult.set(ResultState.OPR_FAIL);
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"checkCorWhetherInputByGuestExist"})
//  @ResponseBody
//  public JsonResult checkWhetherInputByGuestExist(HttpSession session, String inputByGuest)
//  {
//    JsonResult jsonResult = JsonResult.getInstance();
//    if (StringUtil.isEmpty(inputByGuest)) {
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//    Corporation corporation = this.corporationService.findByManyWay(inputByGuest);
//    if (corporation == null) {
//      jsonResult.setSuccess(false);
//    } else {
//      session.setAttribute("corporation", corporation);
//      jsonResult.setSuccess(true);
//    }
//    return jsonResult;
//  }
//}