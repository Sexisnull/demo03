//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.JsonUtil;
//import com.hanweb.common.util.NumberUtil;
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.complat.controller.outsideuser.OutsideUserFormBean;
//import com.hanweb.complat.entity.OutsideUser;
//import com.hanweb.complat.entity.Zone;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.entity.Application;
//import com.hanweb.jis.service.AppService;
//import com.hanweb.jis.service.JisOutSideUserService;
//import com.hanweb.jis.service.OutSideUserDetailService;
//import com.hanweb.jis.service.SendMailService;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.service.ZoneService;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.CellphoneShortMessageUtil;
//import com.hanweb.jis.util.RandomCodeUtil;
//import com.hanweb.sso.ldap.util.MD5;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping({"front/register"})
//public class SingleRegisterController
//{
//
//  @Autowired
//  private ZoneService zoneService;
//
//  @Autowired
//  private OutSideUserDetailService outSideUserDetailService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private JisOutSideUserService jisOutSideUserService;
//
//  @Autowired
//  private AppService appService;
//  private final Log logger = LogFactory.getLog(getClass());
//
//  @RequestMapping({"register"})
//  public ModelAndView register(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    String appmark = request.getParameter("appmark");
//    session.setAttribute("appmark", appmark);
//    ModelAndView modelAndView = new ModelAndView("jis/front/register");
//
//    String html = "register.html";
//    String templateHtml = this.templateService.readDefaultTemplate(html);
//
//    String checkShow1 = "";
//    String checkShow2 = "";
//    String checkShow3 = "";
//    String checkShow4 = "";
//    switch (NumberUtil.getInt(Settings.getSettings().getRegisterType())) {
//    case 0:
//      checkShow1 = "block";
//      checkShow2 = "none";
//      checkShow3 = "none";
//      checkShow4 = "block";
//      break;
//    case 1:
//      checkShow1 = "none";
//      checkShow2 = "block";
//      checkShow3 = "none";
//      checkShow4 = "block";
//      break;
//    case 2:
//      checkShow1 = "block";
//      checkShow2 = "none";
//      checkShow3 = "block";
//      checkShow4 = "none";
//    }
//
//    String pwdLevel = StringUtil.getString(Settings.getSettings().getPwdLevel());
//    String pwdLevelTip = "";
//    if ("1".equals(pwdLevel))
//      pwdLevelTip = "中";
//    else {
//      pwdLevelTip = "强";
//    }
//    templateHtml = templateHtml.replace("${pwdlevel}", pwdLevel)
//      .replace("${pwdleveltip}", pwdLevelTip)
//      .replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${regType}", Settings.getSettings().getRegisterType())
//      .replace("${checkShow1}", checkShow1).replace("${checkShow2}", checkShow2)
//      .replace("${checkShow3}", checkShow3).replace("${checkShow4}", checkShow4)
//      .replace("${url}", "doregister.do")
//      .replace("${verifycodeimg}", "<img id='verifyImg' src='" + Settings.getSettings().getSysUrl() + "/verifyCode.do?" + 
//      "code=4&var=rand&width=162&height=55&random=" + (int)(Math.random() * 100000000.0D) + "'" + 
//      " onclick=\"this.src='" + Settings.getSettings().getSysUrl() + "/verifyCode.do?code=4&var=rand&width=162&height=55&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='162'  height='55' />");
//    if ((appmark != null) && (!appmark.equals(""))) {
//      templateHtml = templateHtml.replace("${appmark}", appmark);
//    }
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"doregister"})
//  @ResponseBody
//  public JsonResult doRegister(String randCode, HttpSession session, OutsideUserFormBean outsideUserFormBean, String emailcode, String mobilecode, String appmark) {
//    session.setAttribute("loginuser", outsideUserFormBean.getLoginName());
//    session.setAttribute("loginpass", outsideUserFormBean.getPwd());
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    JsonResult jsonResult = JsonResult.getInstance();
//    String rand = (String)session.getAttribute("rand");
//
//    boolean isSuccess = false;
//    try {
//      if ((StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
//        session.setAttribute("rand", null);
//        throw new OperationException("register.randcode.error");
//      }
//      if ((StringUtil.isNotEmpty(emailcode)) && 
//        (!StringUtil.equals(emailcode, 
//        StringUtil.getString(session.getAttribute("emailcode"))))) {
//        jsonResult.setMessage("邮箱验证码错误！");
//        return jsonResult;
//      }
//
//      isSuccess = this.jisOutSideUserService.addOutUserForReg(outsideUserFormBean, appmark);
//      if (isSuccess)
//      {
//        jsonResult.set(ResultState.ADD_SUCCESS);
//      } else {
//        jsonResult.set(ResultState.ADD_FAIL);
//        jsonResult.setMessage("注册失败！");
//      }
//    } catch (OperationException e) {
//      if ("register.randcode.error".equals(e.getMessage())) {
//        jsonResult.setMessage("验证码错误");
//      }
//      logger.error(e);
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"checkloginid"})
//  @ResponseBody
//  public String checkLoginId(String loginid) {
//    if (StringUtil.isEmpty(loginid)) {
//      return "";
//    }
//
//    OutsideUser outUser = null;
//    outUser = this.outSideUserDetailService.findByLoginName(loginid);
//    if (outUser == null) {
//      return "";
//    }
//    return "存在相同的用户名";
//  }
//
//  @RequestMapping({"regsuccess"})
//  public ModelAndView regSuccess(HttpServletRequest request, HttpSession session)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/regsuccess");
//    String html = "regsuccess.html";
//    String appmark = (String)session.getAttribute("appmark");
//    String loginuser = (String)session.getAttribute("loginuser");
//    String loginpass = (String)session.getAttribute("loginpass");
//    Application app = this.appService.findByMark(appmark);
//    String ssourl = "";
//    if (app != null)
//    {
//      int encrypttype = app.getEncryptType().intValue();
//
//      String strKey = app.getEncryptKey();
//      MD5 md5 = new MD5();
//      if (encrypttype == 1) {
//        loginuser = md5.encrypt(loginuser, strKey);
//        loginpass = md5.encrypt(loginpass, strKey);
//      } else if ("2".equals(Integer.valueOf(encrypttype))) {
//        loginuser = md5.encryptMB(loginuser, strKey);
//        loginpass = md5.encryptMB(loginpass, strKey);
//      } else if ("11".equals(Integer.valueOf(encrypttype))) {
//        loginpass = md5.encrypt(loginpass, strKey);
//      } else if ("21".equals(Integer.valueOf(encrypttype))) {
//        loginpass = md5.encryptMB(loginpass, strKey);
//      }
//      ssourl = app.getSsoUrl();
//      ssourl = ssourl + "?loginuser=" + loginuser + "&loginpass=" + loginpass + "&appname=" + appmark + "';";
//    }
//
//    String templateHtml = this.templateService.readDefaultTemplate(html);
//
//    if (ssourl.equals(""))
//      templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//        .replace("${url}", "../perlogin.do");
//    else {
//      templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//        .replace("${url}", ssourl);
//    }
//
//    modelAndView.addObject("templatehtml", templateHtml);
//
//    return modelAndView;
//  }
//
//  @RequestMapping({"activateemail"})
//  @ResponseBody
//  public JsonResult activateEmail(HttpSession session, String email)
//  {
//    JsonResult jsonResult = JsonResult.getInstance();
//
//    String sender = Settings.getSettings().getEmailSender();
//    String title = Settings.getSettings().getEmailTitle();
//    String content = Settings.getSettings().getEmailContent();
//    int emailcode = (int)(Math.random() * 1000000.0D);
//    boolean isSuccess = false;
//    if (StringUtil.isEmpty(email)) {
//      jsonResult.setSuccess(false);
//      jsonResult.setMessage("邮箱地址不能为空");
//      return jsonResult;
//    }
//    if (StringUtil.isEmpty(title)) {
//      title = "[统一身份认证系统]邮箱激活:";
//    }
//    if (StringUtil.isEmpty(content)) {
//      content = "尊敬的用户，您好：<br>您本次的邮箱验证码是：" + 
//        emailcode + "，有效时间30分钟。";
//    } else {
//      content = StringUtil.replace(content, "{name}", "尊敬的用户");
//      content = StringUtil.replace(content, "{emailcode}", String.valueOf(emailcode));
//    }
//
//    SendMailService mailService = new SendMailService();
//    try {
//      isSuccess = mailService.sendMail(content, email, title, sender, true);
//      if (isSuccess) {
//        session.removeAttribute("emailcode");
//        session.setAttribute("emailcode", Integer.valueOf(emailcode));
//        jsonResult.setSuccess(true);
//      } else {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("邮件发送失败，请检查邮件地址和邮件服务配置！");
//      }
//    } catch (Exception e) {
//      jsonResult.setSuccess(false);
//      jsonResult.setMessage("邮件发送失败，请检查邮件地址和邮件服务配置！");
//      return jsonResult;
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"activatemobile"})
//  @ResponseBody
//  public String sendSms(HttpSession session, String telNum)
//  {
//    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
//    session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
//    String content = "您找回密码所需要的验证码为：" + cellphoneShortMessageRandomCodeMadeByJava + 
//      "。[甘肃政务服务网]";
//    String appBusinessId = "6200000103";
//    String appBusinessName = "密码找回";
//    int lostTime = 60;
//
//    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
//    return cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, lostTime);
//  }
//
//  @RequestMapping({"searchSubZone"})
//  @ResponseBody
//  public String searchSubZone(String iid)
//  {
//    if ("".equals(iid)) {
//      return null;
//    }
//    List<Zone> childZones = this.zoneService.findChildByIid(NumberUtil.getInt(iid));
//    Map<Integer,String> map = new HashMap<>();
//    for (Zone zone : childZones) {
//      map.put(zone.getIid(), zone.getName());
//    }
//
//    String json = JsonUtil.objectToString(map);
//    return json;
//  }
//}