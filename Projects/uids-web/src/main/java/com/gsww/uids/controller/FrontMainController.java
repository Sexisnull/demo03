//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.NumberUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.common.util.mvc.Script;
//import com.hanweb.complat.controller.user.UserFormBean;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.complat.service.RoleService;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.controller.outsideuser.JisOutsideUserFormBean;
//import com.hanweb.jis.controller.user.JisUserFormBean;
//import com.hanweb.jis.entity.AbstractUser;
//import com.hanweb.jis.entity.Application;
//import com.hanweb.jis.entity.OutsideUserDetail;
//import com.hanweb.jis.entity.SingleLogin;
//import com.hanweb.jis.entity.UserDetail;
//import com.hanweb.jis.listener.UserSessionInfo;
//import com.hanweb.jis.service.AppService;
//import com.hanweb.jis.service.JisDataCallService;
//import com.hanweb.jis.service.JisOutSideUserService;
//import com.hanweb.jis.service.JisUserService;
//import com.hanweb.jis.service.LogService;
//import com.hanweb.jis.service.OutSideUserDetailService;
//import com.hanweb.jis.service.SingleLoginService;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.service.UserDetailService;
//import java.util.ArrayList;
//import java.util.List;
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
//@RequestMapping({"front/login"})
//public class FrontMainController
//{
//
//  @Autowired
//  private AppService appService;
//
//  @Autowired
//  private OutSideUserDetailService outSideUserDetailService;
//
//  @Autowired
//  private UserDetailService userDetailService;
//
//  @Autowired
//  private SingleLoginService singleLoginService;
//
//  @Autowired
//  private JisDataCallService jisDataCallService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private JisUserService jisUserService;
//
//  @Autowired
//  private JisOutSideUserService jisOutSideUserService;
//
//  @Autowired
//  private RoleService roleService;
//
//  @Autowired
//  private LogService logService;
//  int nowpage = 1;
//
//  int setnowpage = 1;
//
//  @RequestMapping({"index"})
//  public ModelAndView showIndexPage(Integer nowpage)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/index");
//    String loginName = "";
//    String manageDisplay = "display:none";
//    AbstractUser user = UserSessionInfo.getCurrentUserInfo();
//    boolean isOutSideUser = user.isOutSideUser();
//    boolean haveApp = false;
//    if ((!isOutSideUser) && (
//      (user.getIsSysAdmin().booleanValue()) || (user.getIsGroupAdmin().booleanValue()) || (this.roleService.isFrontAdminUser(user.getIid())))) {
//      manageDisplay = "";
//    }
//
//    String templateHtml = this.templateService.readTemplate(user.getIid(), "index.html");
//
//    if (templateHtml.startsWith("URL:")) {
//      RedirectView redirectView = new RedirectView(templateHtml.substring(4));
//      modelAndView.setView(redirectView);
//      return modelAndView;
//    }
//
//    loginName = user.getLoginName();
//    int frontSize = 12;
//    int specSize = 45;
//    int i = 0;
//    int perage = 6;
//
//    int apptotal = this.appService.findSingleAppCounts().intValue();
//    if (apptotal > 0) {
//      haveApp = true;
//    }
//
//    int a = apptotal % perage;
//
//    int b = apptotal / perage;
//    int talpage;
//    if (a == 0)
//      talpage = b;
//    else {
//      talpage = b + 1;
//    }
//
//    if (NumberUtil.getInt(nowpage) < 1)
//      nowpage = Integer.valueOf(1);
//    else if (nowpage.intValue() >= talpage) {
//      nowpage = Integer.valueOf(talpage);
//    }
//    if (talpage == 0) {
//      nowpage = Integer.valueOf(0);
//    }
//    String nextpage = String.valueOf(nowpage.intValue() + 1);
//    String lastpage = String.valueOf(nowpage.intValue() - 1);
//
//    List<Application> appList = this.appService.findSingleApps(Integer.valueOf(perage), nowpage);
//
//    List<Application> appListHtml2 = this.appService.findSingleApps(Integer.valueOf(100), nowpage);
//    String html = "";
//    String button = "";
//    String html2 = "";
//
//    if ((appList != null) && (appList.size() > 0)) {
//      html = this.templateService.quickLogin1(appList, talpage, i, frontSize, specSize, nowpage.intValue());
//      html2 = this.templateService.quickLogin2(appListHtml2, i);
//      button = this.templateService.getButtonHtml(talpage, i, nowpage.intValue());
//    } else {
//      html = html + "<div><p style='text-align: center;padding-top: 20px;'>没有操作应用的权限，请与管理员联系！</p></div>";
//    }
//
//    if (StringUtil.isEmpty(templateHtml)) {
//      templateHtml = "<div><p style='text-align: center;padding-top: 20px;'>没有上传模板，请与管理员联系！</p></div>";
//    }
//    else {
//      templateHtml = templateHtml.replace("${sysName}", Settings.getSettings().getSysName())
//        .replace("${loginname}", loginName).replace("{html}", html).replace("{apphtml}", html2)
//        .replace("${button}", button.toString())
//        .replace("${copyRight}", Settings.getSettings().getCopyRight())
//        .replace("${appurl}", "hidlist.do?nowpage=" + nowpage)
//        .replace("${appallurl}", 
//        "hidlist.do?nowpage=" + nowpage + "&perpage=1000")
//        .replace("${next}", "index.do?nowpage=" + nextpage)
//        .replace("${last}", "index.do?nowpage=" + lastpage)
//        .replace("${manageDisplay}", manageDisplay)
//        .replace("${appSetting}", haveApp ? "href='settings.do'" : "style='color: #ccc;'");
//    }
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"hidlist"})
//  public ModelAndView hidList(Integer nowpage, Integer perpage) {
//    ModelAndView modelAndView = new ModelAndView("jis/front/hidlist");
//    AbstractUser user = UserSessionInfo.getCurrentUserInfo();
//    if (NumberUtil.getInt(perpage) == 0) {
//      perpage = Integer.valueOf(6);
//    }
//
//    List<Application> appList = this.appService.findSingleApps(perpage, nowpage);
//
//    StringBuffer html = new StringBuffer(1024);
//    SingleLogin singleLogin = null;
//    if (appList != null) {
//      int i = 0;
//      for (Application app : appList) {
//        i++;
//        singleLogin = this.singleLoginService.setSingleLogin(app, user);
//        html.append("<form name='frm" + i + 
//          "' method='post' action='" + singleLogin.getSsoUrl() + 
//          "' target='_blank'>");
//
//        html.append(this.singleLoginService.getHiddenParam(singleLogin));
//        html.append("</form>");
//      }
//
//    }
//
//    String templateHtml = this.templateService.readTemplate(user.getIid(), "hidlist.html");
//
//    templateHtml = templateHtml.replace("${html}", html.toString());
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"modify"})
//  public ModelAndView modify()
//  {
//    AbstractUser user = UserSessionInfo.getCurrentUserInfo();
//    boolean isOutSideUser = user.isOutSideUser();
//    ModelAndView modelAndView = null;
//    String loginname = "";
//    String manageDisplay = "display:none";
//    String templateHtml = "";
//    String pwdLevel = StringUtil.getString(Settings.getSettings().getPwdLevel());
//    String pwdLevelTip = "";
//    if ("1".equals(pwdLevel))
//      pwdLevelTip = "中";
//    else {
//      pwdLevelTip = "强";
//    }
//    if (isOutSideUser) {
//      modelAndView = new ModelAndView("jis/front/modify");
//
//      loginname = user.getLoginName();
//      int userId = NumberUtil.getInt(user.getIid());
//      OutsideUserDetail outsideUserDetail = this.outSideUserDetailService.findById(userId);
//
//      templateHtml = this.templateService.readTemplate(user.getIid(), "modify.html");
//
//      templateHtml = templateHtml.replace("${pwdlevel}", pwdLevel)
//        .replace("${pwdleveltip}", pwdLevelTip)
//        .replace("${loginname}", loginname).replace("${manageDisplay}", manageDisplay)
//        .replace("${copyRight}", Settings.getSettings().getCopyRight()).replace("${usertype}", "1")
//        .replace("${url}", "domodifyout.do").replace("${outuseriid}", String.valueOf(user.getIid()))
//        .replace("${outuserdetailiid}", String.valueOf(outsideUserDetail.getIid()))
//        .replace("${outuserdetailuserid}", String.valueOf(outsideUserDetail.getUserid()));
//
//      templateHtml = this.templateService.replaceModify(templateHtml);
//    } else {
//      modelAndView = new ModelAndView("jis/front/modifyin");
//      loginname = user.getLoginName();
//      int userId = NumberUtil.getInt(user.getIid());
//      UserDetail userDetail = this.userDetailService.findByUserId(Integer.valueOf(userId));
//      if ((user.getIsSysAdmin().booleanValue()) || (user.getIsGroupAdmin().booleanValue())) {
//        manageDisplay = "";
//      }
//
//      templateHtml = this.templateService.readTemplate(user.getIid(), "modifyin.html");
//
//      templateHtml = templateHtml.replace("${pwdlevel}", pwdLevel)
//        .replace("${pwdleveltip}", pwdLevelTip)
//        .replace("${loginname}", loginname).replace("${manageDisplay}", manageDisplay)
//        .replace("${copyRight}", Settings.getSettings().getCopyRight()).replace("${usertype}", "0")
//        .replace("${url}", "domodifyin.do").replace("${userdetailiid}", String.valueOf(userDetail.getIid()))
//        .replace("${useriid}", String.valueOf(user.getIid())).replace("${userdetailuserid}", String.valueOf(userDetail.getUserid()));
//
//      templateHtml = this.templateService.replaceModify(templateHtml);
//    }
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"domodifyout"})
//  @ResponseBody
//  public JsonResult doModifyOut(JisOutsideUserFormBean jisOutUser)
//  {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    try {
//      isSuccess = this.jisOutSideUserService.modifySelf(jisOutUser);
//      if (isSuccess) {
//        jsonResult.set(ResultState.MODIFY_SUCCESS);
//        jsonResult.setMessage("修改成功");
//      } else {
//        jsonResult.set(ResultState.MODIFY_FAIL);
//        jsonResult.setMessage("修改失败");
//      }
//    } catch (OperationException e) {
//      jsonResult.setMessage(e.getMessage());
//    }
//
//    return jsonResult;
//  }
//
//  @RequestMapping({"domodifyin"})
//  @ResponseBody
//  public JsonResult doModifyIn(JisUserFormBean jisUser)
//  {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    try {
//      isSuccess = this.jisUserService.modifySelf(jisUser);
//      if (isSuccess) {
//        jsonResult.set(ResultState.MODIFY_SUCCESS);
//        jsonResult.setMessage("修改成功");
//      } else {
//        jsonResult.set(ResultState.MODIFY_FAIL);
//        jsonResult.setMessage("修改失败");
//      }
//    } catch (OperationException e) {
//      jsonResult.setMessage(e.getMessage());
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"manage_login"})
//  public ModelAndView loginManage() {
//    AbstractUser user = UserSessionInfo.getFrontCurrentUserInfo();
//    UserSessionInfo.setCurrentUserInfo(user);
//    String appUrl = Settings.getSettings().getSysUrl();
//    if (!appUrl.endsWith("/")) {
//      appUrl = appUrl + "/";
//    }
//    appUrl = appUrl + "manager/index.do";
//    RedirectView redirectView = new RedirectView(appUrl);
//    ModelAndView modelAndView = new ModelAndView(redirectView);
//    return modelAndView;
//  }
//
//  @RequestMapping({"settings"})
//  public ModelAndView settings(Integer nowpage)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/settings");
//    AbstractUser user = UserSessionInfo.getFrontCurrentUserInfo();
//    String loginName = "";
//    String manageDisplay = "display:none";
//    boolean isOutSideUser = user.isOutSideUser();
//    if ((!isOutSideUser) && (
//      (user.getIsSysAdmin().booleanValue()) || (user.getIsGroupAdmin().booleanValue()))) {
//      manageDisplay = "";
//    }
//
//    String templateHtml = "";
//    loginName = user.getLoginName();
//    List<Application> applist = new ArrayList<>();
//
//    int perage = 6;
//    if (NumberUtil.getInt(nowpage) == 0) {
//      nowpage = Integer.valueOf(1);
//    }
//    String nextpage = String.valueOf(NumberUtil.getInt(nowpage) + 1);
//    String lastpage = String.valueOf(NumberUtil.getInt(nowpage) - 1);
//
//    int apptotal = this.appService.findSingleAppCounts().intValue();
//    int a = apptotal % perage;
//    int b = apptotal / perage;
//    int talpage;
//    if (a == 0) {
//      talpage = b;
//    }
//    else {
//      talpage = b + 1;
//    }
//
//    if (NumberUtil.getInt(nowpage) < 1)
//      nowpage = Integer.valueOf(1);
//    else if (nowpage.intValue() >= talpage) {
//      nowpage = Integer.valueOf(talpage);
//    }
//    if (talpage == 0) {
//      nowpage = Integer.valueOf(0);
//    }
//
//    applist = this.appService.findSingleApps(Integer.valueOf(perage), nowpage);
//
//    StringBuffer apps = new StringBuffer(1024);
//    String color1 = "";
//    String href1 = "";
//    String color2 = "";
//    String href2 = "";
//
//    for (Application app : applist) {
//      apps.append("<tr><td class='sz_yy'>" + app.getName() + "</td><td class='sz_cs'>");
//      if ((app.getLoginType().intValue() == 0) || (app.getUserDefined().intValue() != 0))
//        apps.append("<a style='color: #ccc;'>设置</a>");
//      else {
//        apps.append("<a href='#' onclick=\"openSetting(" + app.getIid() + ",'" + app.getName() + "')\">设置</a>");
//      }
//      apps.append("</td></tr>");
//    }
//
//    if (nowpage.intValue() == 1) {
//      color1 = "style='color:#ccc'";
//      href1 = "";
//    } else if (nowpage.intValue() > 1) {
//      color1 = "";
//      href1 = "href='settings.do?nowpage='" + lastpage + "'";
//    }
//
//    if (nowpage.intValue() == talpage) {
//      color2 = "style='color:#ccc'";
//      href2 = "";
//    } else if (nowpage.intValue() < talpage) {
//      color2 = "";
//      href2 = "href='settings.do?nowpage=" + nextpage + "'";
//    }
//
//    templateHtml = this.templateService.readDefaultTemplate("settings.html");
//
//    templateHtml = templateHtml.replace("${loginname}", loginName)
//      .replace("${manageDisplay}", manageDisplay).replace("${nowpage}", String.valueOf(nowpage))
//      .replace("${apps}", apps.toString()).replace("nowpage", String.valueOf(nowpage))
//      .replace("${url}", "doSettings.do").replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${color1}", color1).replace("${href1}", href1).replace("${color2}", color2)
//      .replace("${talpage}", String.valueOf(talpage)).replace("${href2}", href2);
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"doSettings"})
//  @ResponseBody
//  public String doSettings(@RequestParam(value="loginId", required=false) String loginId, @RequestParam(value="password", required=false) String password, @RequestParam(value="name", required=false) String name, @RequestParam(value="email", required=false) String email, @RequestParam(value="mobile", required=false) String mobile, @RequestParam(value="randCode", required=false) String randCode, HttpSession session)
//  {
//    Script script = Script.getInstance();
//    UserFormBean user = new UserFormBean();
//    String rand = (String)session.getAttribute("rand");
//    try {
//      if (!rand.equalsIgnoreCase(randCode)) {
//        session.setAttribute("rand", null);
//        throw new OperationException("register.randcode.error");
//      }
//      user.setLoginName(loginId);
//      user.setPwd(password);
//      user.setName(name);
//      user.setEmail(email);
//      user.setMobile(mobile);
//    } catch (OperationException e) {
//      if ("register.randcode.error".equals(e.getMessage())) {
//        script.addAlert("验证码错误");
//        script.addScript("$('#verifyImg').click();");
//      } else if ("register.success".equals(e.getMessage())) {
//        script.addAlert("注册成功");
//      }
//    }
//
//    return script.getScript();
//  }
//
//  @RequestMapping({"singlelogin"})
//  public ModelAndView singleLogin(String loginuser, String loginpass, String appname) {
//    ModelAndView modelAndView = new ModelAndView("jis/singlelogin/" + appname);
//
//    modelAndView.addObject("loginName", loginuser);
//    modelAndView.addObject("loginPwd", loginpass);
//    return modelAndView;
//  }
//
//  @RequestMapping({"publicdatacall"})
//  public ModelAndView publicDataCall(Integer id) {
//    ModelAndView modelAndView = new ModelAndView("jis/front/publicdatacall");
//    modelAndView.addObject("content", this.jisDataCallService.generateHtml(id));
//    return modelAndView;
//  }
//
//  @RequestMapping({"quicklogin1"})
//  public ModelAndView quickLogin1(int talPage, int i, int frontSize, int specSize, int nowPage, int perage) {
//    ModelAndView modelAndView = new ModelAndView("jis/front/quicklogin1");
//
//    List<Application> appList = this.appService.findSingleApps(Integer.valueOf(perage), Integer.valueOf(this.nowpage));
//    String appHtml = "";
//    if ((appList != null) && (appList.size() > 0))
//      appHtml = this.templateService.quickLogin1(appList, talPage, i, frontSize, specSize, nowPage);
//    else {
//      appHtml = appHtml + "<div><p style='text-align: center;padding-top: 20px;'>没有操作应用的权限，请与管理员联系！</p></div>";
//    }
//
//    modelAndView.addObject("contextpath", Settings.getSettings().getSysUrl());
//    modelAndView.addObject("apphtml", appHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"recordssolog"})
//  public void recordSSOLog(String appmark) {
//    if (appmark != null)
//      this.logService.add(Integer.valueOf(11), Integer.valueOf(11), appmark);
//  }
//}