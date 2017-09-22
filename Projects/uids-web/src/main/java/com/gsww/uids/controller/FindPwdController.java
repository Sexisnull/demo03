//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.DateUtil;
//import com.hanweb.common.util.Md5Util;
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.complat.entity.OutsideUser;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.entity.AbstractUser;
//import com.hanweb.jis.service.JisUsersService;
//import com.hanweb.jis.service.OutSideUserDetailService;
//import com.hanweb.jis.service.SendMailService;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.FilterUtil;
//import java.net.URLEncoder;
//import java.util.Date;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping({"front/findpwd"})
//public class FindPwdController
//{
//
//  @Autowired
//  private OutSideUserDetailService outSideUserDetailService;
//  private String usermail = "";
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private JisUsersService jisUsersService;
//
//  @RequestMapping({"findpwd"})
//  public ModelAndView findPWD(String timeout) { if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    ModelAndView modelAndView = new ModelAndView("jis/front/findpwd");
//
//    String templateHtml = this.templateService.readDefaultTemplate("findpwd.html");
//
//    templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${url}", "dofindpwd.do").replace("${verifycodeimg}", "<img id='verifyImg' src='" + 
//      Settings.getSettings().getSysUrl() + "/verifyCode.do?" + 
//      "code=4&var=rand&width=162&height=55&random=" + (int)(Math.random() * 100000000.0D) + "'" + 
//      " onclick=\"this.src='" + Settings.getSettings().getSysUrl() + 
//      "/verifyCode.do?code=4&var=rand&width=162&height=55&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='162'  height='55' />");
//    templateHtml = templateHtml.replace("${timeout}", StringUtil.getString(timeout));
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView; }
//
//  @RequestMapping({"dofindpwd"})
//  @ResponseBody
//  public JsonResult doFindPWD(String loginId, String email, String randCode, HttpSession session) {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    JsonResult jsonResult = JsonResult.getInstance();
//    String message = "";
//    try {
//      String pwd = "";
//      String rand = (String)session.getAttribute("rand");
//      if (!rand.equalsIgnoreCase(randCode)) {
//        session.setAttribute("rand", null);
//        jsonResult.setSuccess(false);
//        jsonResult.addParam("randerror", "验证码错误");
//        return jsonResult;
//      }
//
//      OutsideUser outsideUser = this.outSideUserDetailService.findByLoginName(loginId);
//      if (outsideUser != null) {
//        pwd = outsideUser.getPwd();
//      }
//
//      String sender = Settings.getSettings().getEmailSender();
//      this.usermail = email;
//      String username = loginId;
//      String title = Settings.getSettings().getEmailFindPassTitle();
//      String content = Settings.getSettings().getEmailFindPassContent();
//      if ("".equals(title)) {
//        title = "[" + Settings.getSettings().getSysName() + "]密码重置说明:";
//      }
//      if ("".equals(content)) {
//        content = username + 
//          "你好:<br>&nbsp;&nbsp;&nbsp;&nbsp;<br>" + 
//          "<a href='" + 
//          Settings.getSettings().getSysUrl() + "/front/findpwd/resetpwd.do?uri=" + 
//          URLEncoder.encode(Md5Util.md5encode(new StringBuilder(
//          String.valueOf(DateUtil.dateToString(new Date(), 
//          "yyyy-MM-dd HH:mm:ss")))
//          .append("&").append(username).append("&").append(pwd).toString()), 
//          "utf-8") + 
//          "'>请点击链接重置密码。</a><br>";
//      } else {
//        String uri = "<a href='" + Settings.getSettings().getSysUrl() + 
//          "/front/findpwd/resetpwd.do?uri=" + 
//          URLEncoder.encode(Md5Util.md5encode(new StringBuilder(
//          String.valueOf(DateUtil.dateToString(new Date(), 
//          "yyyy-MM-dd HH:mm:ss")))
//          .append("&").append(username).append("&").append(pwd).toString()), 
//          "utf-8") + 
//          "'>请点击链接重置密码。</a>";
//        content = StringUtil.replace(content, "{name}", username);
//        content = StringUtil.replace(content, "{uri}", uri);
//      }
//      SendMailService mailService = new SendMailService();
//      try
//      {
//        boolean b = mailService.sendMail(content, this.usermail, title, sender, false);
//        if (!b) {
//          jsonResult.setSuccess(false);
//          jsonResult.addParam("email", email);
//          return jsonResult;
//        }
//      } catch (Exception e) {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage(e.getMessage());
//        jsonResult.addParam("email", email);
//        return jsonResult;
//      }
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//      jsonResult.setSuccess(false);
//      jsonResult.setMessage(e.getMessage());
//      return jsonResult;
//    }
//    if (StringUtil.isEmpty(message)) {
//      jsonResult.setSuccess(true);
//      jsonResult.addParam("email", email);
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"success"})
//  public ModelAndView success(String email) {
//    email = FilterUtil.filterJs(email);
//    ModelAndView modelAndView = new ModelAndView("jis/front/success");
//    String templateHtml = this.templateService.readDefaultTemplate("success.html");
//
//    templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${email}", email);
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//  @RequestMapping({"error"})
//  public ModelAndView error(String email) {
//    email = FilterUtil.filterJs(email);
//    ModelAndView modelAndView = new ModelAndView("jis/front/error");
//    String templateHtml = this.templateService.readDefaultTemplate("error.html");
//
//    templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${email}", email);
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"resetpwd"})
//  public ModelAndView showResetPwd(String uri)
//  {
//    String[] params = Md5Util.md5decode(uri).split("&");
//
//    Date currentDate = DateUtil.stringtoDate(params[0], "yyyy-MM-dd HH:mm:ss");
//    String url = "jis/front/resetpwd";
//
//    long time = DateUtil.dayDiff(currentDate, new Date());
//    AbstractUser abstractUser = this.jisUsersService.findByLoginName(params[1]);
//    ModelAndView modelAndView = new ModelAndView();
//    if (time >= 1L) {
//      url = "findpwd.do?timeout=1";
//      modelAndView.setViewName("forward:" + url);
//    } else if ((abstractUser != null) && (!StringUtil.equals(params[2], abstractUser.getPwd()))) {
//      url = "findpwd.do?timeout=1";
//      modelAndView.setViewName("forward:" + url);
//    } else {
//      modelAndView.setViewName(url);
//    }
//    String pwdLevel = StringUtil.getString(Settings.getSettings().getPwdLevel());
//    String pwdLevelTip = "";
//    if ("1".equals(pwdLevel))
//      pwdLevelTip = "中";
//    else {
//      pwdLevelTip = "强";
//    }
//
//    String templateHtml = this.templateService.readDefaultTemplate("resetpwd.html");
//
//    templateHtml = templateHtml.replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${url}", "resetpwd_submit.do")
//      .replace("${pwdlevel}", pwdLevel)
//      .replace("${loginName}", params[1])
//      .replace("${pwdleveltip}", pwdLevelTip);
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"resetpwd_submit"})
//  @ResponseBody
//  public JsonResult resetPwd(String loginName, String pwd)
//  {
//    JsonResult jsonResult = JsonResult.getInstance();
//    boolean isSuccess = false;
//    isSuccess = this.jisUsersService.updatePwdByLoginName(loginName, pwd);
//    if (isSuccess) {
//      jsonResult.setSuccess(true);
//    } else {
//      jsonResult.setSuccess(false);
//      jsonResult.setMessage("重置失败");
//    }
//    return jsonResult;
//  }
//}