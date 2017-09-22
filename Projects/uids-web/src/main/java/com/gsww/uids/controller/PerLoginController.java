package com.gsww.uids.controller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.security.SecurityUtil;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.AuthLogService;
import com.gsww.uids.util.AccessUtil;
import com.gsww.jup.util.CellphoneShortMessageUtil;
import com.gsww.jup.util.RandomCodeUtil;
import com.gsww.jup.util.SafeUtil;
import com.gsww.jup.util.UserUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@RequestMapping(value="/front")
public class PerLoginController{
  private static Logger logger = LoggerFactory.getLogger(ComplatRoleController.class);

  @Autowired
  private ComplatCorporationService corporationService;

  @Autowired
  private JisApplicationService appService;

  @Autowired
  private ComplatOutsideuserService OutsideUserService;

  @Autowired
  private AuthLogService authLogService;

  @RequestMapping(value = "/perlogin")
  public String personalLogin(HttpServletResponse response, String action, String appmark, 
		  String gotoUrl, String domain,Model model)
  {
    if (action == null) {
      action = "";
    }
    if (appmark == null) {
      appmark = "";
    }
    if ((SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark)) || (SafeUtil.isSqlAndXss(gotoUrl)) || 
      (SafeUtil.isSqlAndXss(domain)))
      return null;
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
    session.setAttribute("appmark", appmark);
    model.addAttribute("sysName",JisSettings.getSettings().getSysName());
    model.addAttribute("url","doperlogin.do" );
    model.addAttribute("verifycodeimg","<img id='verifyImg' src='../verifyCode.do?code=4&var=rand&width=162&height=30&"
    		+ "random=" +  (int)(Math.random() * 100000000.0D) + "'" + " onclick=\"this.src='../verifyCode.do?code=4&"
    				+ "var=rand&width=140&height=30&random='+ Math.random();\" style='cursor:pointer'  width='140' "
    				+ "height='30' />");
    model.addAttribute("username", ControllerUtil.getCookieValue("frontuser"));
    model.addAttribute("action", action);
    model.addAttribute("appmark", appmark);
    model.addAttribute("gotoUrl", gotoUrl);
    
    return "jis/front/perlogin";
  }

  @RequestMapping(value="/doperlogin")
  @ResponseBody
  public JsonResult personalDoLogin(@RequestParam(value="username", required=false) String userName, @RequestParam(value="password", required=false) String password, @RequestParam(value="randomVeryfyCode", required=false) String randomVeryfyCode, @RequestParam(value="action", required=false) String action, @RequestParam(value="appmark", required=false) String appmark, @RequestParam(value="gotoUrl", required=false) String gotoUrl, HttpSession session, HttpServletResponse response)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    JsonResult jsonResult = JsonResult.getInstance();
    String ip = ControllerUtil.getIp();
    try
    {
      session.setAttribute("appmark", appmark);

      if ((String)session.getAttribute("rand") == null)
        logger.error("verifycode.error");
      if ((randomVeryfyCode == null) || ("".equals(randomVeryfyCode)) || (randomVeryfyCode.length() == 0)) {
    	  logger.error("verifycode.error");
      }
      String rand = (String)session.getAttribute("rand");
      if (!rand.equalsIgnoreCase(randomVeryfyCode)) {
    	  logger.error("verifycode.error");
      }

      if ((StringUtil.isEmpty(userName)) || (StringUtil.isEmpty(password))) {
    	  logger.error("login.error");
      }
      userName = SecurityUtil.RSAdecode(userName);
      password = SecurityUtil.RSAdecode(password);

      int wayOfLogin = 0;
      ComplatOutsideuser outsideUser;
      if (UserUtil.isMobilelegal(userName)) {
        outsideUser = this.OutsideUserService.findByMobile(userName);
        wayOfLogin = 0;
      } else if (UserUtil.isIDnumberlegal(userName)) {
        outsideUser = this.OutsideUserService.findByIdCard(userName);
        wayOfLogin = 2;
      } else {
        outsideUser = this.OutsideUserService.findByLoginName(userName);
      }

      if (outsideUser == null) {
        logger.error("login.error");
      }
      userName = outsideUser.getLoginName();

      ComplatOutsideuser user = this.OutsideUserService.checkUserLogin(userName, password, ip);

      if ((user == null) && (wayOfLogin == 0)) {
        user = this.OutsideUserService.checkUserLogin(session, userName, password, ip);
      }

      if (user != null)
      {
        PersonalSessionInfo.setCurrentPersonalInfo(user);
        jsonResult.setSuccess(true);
        ControllerUtil.addCookie(response, "personalloginid", userName, 
          604800);

        String gotoUrlFlag = "";
        if (StringUtil.isNotEmpty(appmark)) {
          System.out.println("====user" + user);

          String ticket = this.authLogService.add(user, appmark, 1);
          System.out.println("====tickt" + ticket);
          if (StringUtil.isEmpty(ticket)) {
            logger.error("票据生成失败");
          }

          String domain = StringUtil.getString(session.getAttribute("domain"));

          JisSettings settings = JisSettings.getSettings();
          gotoUrlFlag = settings.getPerGotoUrl();
          if (StringUtil.isNotEmpty(domain)) {
            try {
				domain = URLDecoder.decode(domain,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");

            Matcher m = p.matcher(gotoUrlFlag);
            if (m.find()) {
              gotoUrlFlag = StringUtil.replace(gotoUrlFlag, m.group(), domain);
            }
            session.removeAttribute("domain");
          }
          jsonResult.addParam("ticket", ticket);
        }
        jsonResult.addParam("gotoUrlFlag", gotoUrlFlag);
        this.OutsideUserService.updateLoginIpAndLoginTime(user);
      }
      else {
        logger.error("您正在进行个人用户登录，用户名或密码不正确");
      }
    }
    catch (Exception e) {
      session.setAttribute("rand", StringUtil.getUUIDString().substring(0, 4));
      if ("adminlogin.error".equals(e.getMessage())) {
        jsonResult.setSuccess(false);
        jsonResult.addParam("adminerror", "1");
      } else if ("verifycode.error".equals(e.getMessage())) {
        jsonResult.setMessage("验证码错误，请重新输入");
        jsonResult.setSuccess(false);
      } else {
        jsonResult.setSuccess(false);
        jsonResult.setMessage("您正在进行个人用户登录，用户名或密码不正确!");
      }
    }
    return jsonResult;
  }

  @RequestMapping(value="/perindex")
  public String perIndex(HttpServletResponse response,Model model) {
    ComplatOutsideuser user = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    String logouturl = "";
    String loginname = "";
    if (user != null) {
      logouturl = "perlogout.do";
      loginname = user.getLoginName();
    }
    else {
      return "jis/front/perlogin";
    }
    
    model.addAttribute("sysName", JisSettings.getSettings().getSysName());
    model.addAttribute("logouturl", logouturl);
    model.addAttribute("loginname", loginname);
    model.addAttribute("copyRight", JisSettings.getSettings().getCopyRight());
    model.addAttribute("verifycodeimg", "<img id='verifyImg' src='../verifyCode.do?code=4&"
    		+ "var=rand&width=162&height=55&random=" + (int)(Math.random() * 100000000.0D) 
    		+"'onclick=\"this.src='../verifyCode.do?code=4&var=rand&width=162&height=55&"
    		+ "random='+ Math.random();\" style='cursor:pointer'  width='162'  height='55' />");
    model.addAttribute("username", ControllerUtil.getCookieValue("frontuser"));
    
    return "jis/front/perindex";
  }

  @RequestMapping(value="/perlogout")
  public ModelAndView perLogout(HttpSession session) {
    if (PersonalSessionInfo.getFrontCurrentPersonalInfo() != null) {
      session.removeAttribute("personalinfo");
    }
    String appmark = StringUtil.getString(session.getAttribute("appmark"));
    ModelAndView modelAndView = new ModelAndView();
    RedirectView redirectView = new RedirectView("perlogin.do");
    modelAndView.addObject("appmark", appmark);
    modelAndView.setView(redirectView);
    return modelAndView;
  }
  
  @RequestMapping(value="/findperurl")
  @ResponseBody
  public JsonResult findPerLogoff() {
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

  @RequestMapping(value="/sendDynamicPwd")
  @ResponseBody
  public String sendDynamicPwd(String telNum, HttpSession session)
  {
    Map<String,String> map = new HashMap<String,String>();
    if ((telNum == null) || ("".equals(telNum.trim())))
    {
      map.put("success", "false");
      map.put("code", "0");
      map.put("msg", "手机号不能为空");
      return JsonUtil.objectToString(map);
    }
    if (!UserUtil.isMobilelegal(telNum)) {
      map.put("success", "false");
      map.put("code", "0");
      map.put("msg", "请输入正确的手机号");
      return JsonUtil.objectToString(map);
    }

    ComplatOutsideuser outsideUser = this.OutsideUserService.findByMobile(telNum);
    if (outsideUser == null) {
      map.put("success", "false");
      map.put("code", "0");
      map.put("msg", "无此帐号，请确认");
      return JsonUtil.objectToString(map);
    }

    String cellphoneDynamicPwdMadeByJava = RandomCodeUtil.getRandomNumber(6);
    session.setAttribute("cellphoneDynamicPwdMadeByJava", cellphoneDynamicPwdMadeByJava);

    int validityPeriodInt = Integer.parseInt(JisSettings.getSettings().getValidityPeriod().trim());

    String content = JisSettings.getSettings().getDynamicPwdMessageContent().trim()
      .replace("cellphoneDynamicPwdMadeByJava", cellphoneDynamicPwdMadeByJava)
      .replace("validityPeriod", String.valueOf(validityPeriodInt));
    String appBusinessId = JisSettings.getSettings().getBusinessIdForGettingDynamicPwd().trim();
    String appBusinessName = JisSettings.getSettings().getBusinessNameForGettingDynamicPwd().trim();
    int loseTime = 60;
    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
    String jsonRe = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, loseTime);
    map = (Map)JsonUtil.StringToObject(jsonRe, Map.class);
    if (Integer.parseInt(map.get("code")) == 1) {
      session.setAttribute("timeSendDynamicPwd", Long.valueOf(System.currentTimeMillis()));
      int validityPeriod = Integer.parseInt(JisSettings.getSettings().getValidityPeriod()) * 60;
      session.setMaxInactiveInterval(validityPeriod);
    }
    return jsonRe;
  }

  @RequestMapping(value="/pwdRecover_select")
  public String pwdRecover_selectForPer(HttpSession session, String typeEntity,Model model)
  {
    if ("per".equals(typeEntity))
      session.setAttribute("typeEntity", "per");
    else {
      session.setAttribute("typeEntity", "cor");
    }
    return "jis/front/pwdRecover_select";
  }

  @RequestMapping(value="/recoverPwdByPhone_show")
  public String verifyByPhone(HttpSession session,Model model)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }

    session.setAttribute("mobilesend", RandomCodeUtil.getRandomNumber(9));

    String typeEntity = (String)session.getAttribute("typeEntity");
    if (typeEntity == null) {
      return null;
    }
    
    model.addAttribute("url", "recoverPwdByPhone_submit.do");
    model.addAttribute("typeEntity", typeEntity);

    return "jis/front/phone_recoverPwd";
  }

  @RequestMapping(value="/recoverPwdByEmail_show")
  public String verifyByEmail(HttpSession session,Model model) {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    
    model.addAttribute("url", "emailverify_submit.do");
    
    return "jis/front/email_verify";
  }
  
  @RequestMapping(value="/emailverify_submit")
  @ResponseBody
  public JsonResult submitEmailverify(HttpSession session, String loginName, String emailcode) {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    if ((SafeUtil.isSqlAndXss(loginName)) || (SafeUtil.isSqlAndXss(emailcode))) {
      return null;
    }
    JsonResult jsonResult = JsonResult.getInstance();
    int type = ((Integer)session.getAttribute("type")).intValue();
    if (type == 1) {
      ComplatCorporation corporation = this.corporationService.findByLoginName(loginName);
      if (corporation == null) {
        jsonResult.setMessage("用户名不存在！");
        jsonResult.set(ResultState.OPR_FAIL);
        return jsonResult;
      }
      session.setAttribute("username", corporation.getLoginName());
    }
    else {
      ComplatOutsideuser outuser = this.OutsideUserService.findByLoginName(loginName);
      if (outuser == null) {
        jsonResult.setMessage("用户名不存在！");
        jsonResult.set(ResultState.OPR_FAIL);
        return jsonResult;
      }
      session.setAttribute("username", outuser.getLoginName());
    }

    if (StringUtil.isNotEmpty(emailcode)) {
      if (!StringUtil.equals(emailcode, 
        StringUtil.getString(session.getAttribute("emailcode")))) {
        jsonResult.setMessage("邮箱验证码错误！");
        jsonResult.set(ResultState.OPR_FAIL);
        return jsonResult;
      }
      jsonResult.set(ResultState.OPR_SUCCESS);
    }
    else {
      jsonResult.set(ResultState.OPR_FAIL);
    }

    return jsonResult;
  }

  @RequestMapping(value="/checkPerWhetherInputByGuestExist")
  @ResponseBody
  public JsonResult checkWhetherInputByGuestExist(HttpSession session, String inputByGuest)
  {
    JsonResult jsonResult = JsonResult.getInstance();
    if (StringUtil.isEmpty(inputByGuest)) {
      jsonResult.setSuccess(false);
      return jsonResult;
    }
    ComplatOutsideuser outsideUser;
    if (UserUtil.isMobilelegal(inputByGuest)) {
      outsideUser = this.OutsideUserService.findByMobile(inputByGuest);
    }
    else
    {
      if (UserUtil.isIDnumberlegal(inputByGuest))
        outsideUser = this.OutsideUserService.findByIdCard(inputByGuest);
      else {
        outsideUser = this.OutsideUserService.findByLoginName(inputByGuest);
      }
    }
    if (outsideUser == null) {
      jsonResult.setSuccess(false);
    } else {
      session.setAttribute("outsideUser", outsideUser);
      jsonResult.setSuccess(true);
    }
    return jsonResult;
  }

  @RequestMapping(value="/sendCellphoneShortMessageUserPwdRecover")
  @ResponseBody
  public String sendCellphoneShortMessageUserPwdRecover(HttpSession session)
  {
    Map<String,String> map1 = new HashMap<String,String>();
    Object mobileSend = session.getAttribute("mobilesend");
    if (mobileSend == null) {
      map1.put("msg", "参数为空");
      map1.put("success", "false");
      map1.put("code", "0");
      return JsonUtil.objectToString(map1);
    }

    Object currentTimes = session.getAttribute("mobiletimes");
    if (currentTimes != null) {
      int times = NumberUtil.getInt(currentTimes);
      if (times > 5) {
        map1.put("message", "超过最大短信发送次数");
        map1.put("success", "false");
        map1.put("code", "0");
        return JsonUtil.objectToString(map1);
      }
    }

    String typeEntity = (String)session.getAttribute("typeEntity");
    ComplatOutsideuser outsideUser = null;
    ComplatCorporation corporation = null;
    String phoneNumber;
    if ("per".equals(typeEntity)) {
      outsideUser = (ComplatOutsideuser)session.getAttribute("outsideUser");
      phoneNumber = outsideUser.getMobile();
    }
    else {
      corporation = (ComplatCorporation)session.getAttribute("corporation");
      phoneNumber = corporation.getMobile();
    }

    if ((outsideUser == null) && (corporation == null)) {
      Map<String,String> mapInfo = new HashMap<String,String>();
      mapInfo.put("success", "false");
      mapInfo.put("code", "0");
      mapInfo.put("msg", "无此用户");
      return JsonUtil.objectToString(mapInfo);
    }
    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
    session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
    String content = JisSettings.getSettings().getRecovingPwdContent().trim()
      .replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
    String appBusinessId = JisSettings.getSettings().getBusinessIdForRecovingPwd().trim();
    String appBusinessName = JisSettings.getSettings().getBusinessNameForRecovingPwd().trim();

    int loseTime = 60;
    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
    String resultJson = cellMesUtil.sendPhoneShortMessage(phoneNumber, content, appBusinessId, appBusinessName, loseTime);
    Map map = (Map)JsonUtil.StringToObject(resultJson, Map.class);
    if ("true".equals(map.get("success")))
    {
      if (currentTimes == null) {
        session.setAttribute("mobiletimes", Integer.valueOf(1));
      } else {
        int times = NumberUtil.getInt(currentTimes);
        session.setAttribute("mobiletimes", Integer.valueOf(times + 1));
      }
    }

    return resultJson;
  }

  @RequestMapping(value="/recoverPwdByPhone_submit")
  @ResponseBody
  public JsonResult submitPhoneverify(HttpSession session, String cellphoneShortMessageRandomCodeWritenByGuest)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    if ((SafeUtil.isSqlAndXss(cellphoneShortMessageRandomCodeWritenByGuest)) || 
      (SafeUtil.isSqlAndXss(cellphoneShortMessageRandomCodeWritenByGuest))) {
      return null;
    }

    JsonResult jsonResult = JsonResult.getInstance();
    session.setAttribute("cellphoneShortMessageRandomCodeWritenByGuest", cellphoneShortMessageRandomCodeWritenByGuest);

    if (StringUtil.isNotEmpty(cellphoneShortMessageRandomCodeWritenByGuest)) {
      if (!StringUtil.equals(cellphoneShortMessageRandomCodeWritenByGuest, 
        StringUtil.getString(session.getAttribute("cellphoneShortMessageRandomCodeMadeByJava")))) {
        jsonResult.setMessage("手机验证码错误！");
        jsonResult.set(ResultState.OPR_FAIL);
        return jsonResult;
      }
      jsonResult.set(ResultState.OPR_SUCCESS);
    }
    else {
      jsonResult.set(ResultState.OPR_FAIL);
    }
    return jsonResult;
  }

  @RequestMapping(value="/resetpwd_show")
  public String resetPwd(HttpSession session,Model model)
  {
    String typeEntity = (String)session.getAttribute("typeEntity");
    String loginName = "";
    if (StringUtil.isNotEmpty(typeEntity)) {
      if ("per".equals(typeEntity)) {
        ComplatOutsideuser outsideUser = (ComplatOutsideuser)session.getAttribute("outsideUser");
        if (outsideUser != null)
          loginName = outsideUser.getLoginName();
      }
      else {
        ComplatCorporation corporation = (ComplatCorporation)session.getAttribute("corporation");
        if (corporation != null)
          loginName = corporation.getLoginName();
      }
    }
    else {
      model.addAttribute("appmark", "gszw");
      return "jis/front/perlogin";
    }
    Object randCode = session.getAttribute("cellphoneShortMessageRandomCodeMadeByJava");
    if ((randCode == null) || (StringUtil.isEmpty(loginName))) {
        model.addAttribute("appmark", "gszw");
        return "jis/front/perlogin";
    }
    Object randCode2 = session.getAttribute("cellphoneShortMessageRandomCodeWritenByGuest");
    if (!randCode.equals(randCode2)) {
        model.addAttribute("appmark", "gszw");
        return "jis/front/perlogin";
    }

    String appmark = (String)session.getAttribute("appmark");
    if (appmark == null) {
      appmark = "";
    }
    model.addAttribute("loginName", loginName);
    model.addAttribute("url", "resetpwd_submit.do");
    model.addAttribute("typeEntity", typeEntity);
    model.addAttribute("appmark", appmark);
    
    return "jis/front/resetpwd";
  }

  @RequestMapping(value="resetpwd_submit")
  @ResponseBody
  public JsonResult submitResetPwd(HttpSession session, String pwd)
  {
    JsonResult jsonResult = JsonResult.getInstance();
    String typeEntity = (String)session.getAttribute("typeEntity");

    boolean isSuccess = false;
    if (("".equals(typeEntity)) || (typeEntity == null)) {
      jsonResult.set(ResultState.MODIFY_FAIL);
      return jsonResult;
    }
    if ("per".equals(typeEntity)) {
      ComplatOutsideuser outsideUser = (ComplatOutsideuser)session.getAttribute("outsideUser");
      String loginName = outsideUser.getLoginName();
      isSuccess = this.OutsideUserService.updatePwd(Integer.parseInt(loginName), Md5Util.md5encode(pwd));
    } else {
      ComplatCorporation corporation = (ComplatCorporation)session.getAttribute("corporation");
      String loginName = corporation.getLoginName();
      isSuccess = this.corporationService.updatePwd(loginName, Md5Util.md5encode(pwd));
    }

    if (isSuccess) {
      session.invalidate();
      jsonResult.set(ResultState.MODIFY_SUCCESS);
    } else {
      jsonResult.set(ResultState.MODIFY_FAIL);
    }
    return jsonResult;
  }
}