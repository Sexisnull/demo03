//package com.gsww.uids.controller;
//
//import com.hanweb.common.BaseInfo;
//import com.hanweb.common.util.CacheUtil;
//import com.hanweb.common.util.JsonUtil;
//import com.hanweb.common.util.NumberUtil;
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.ControllerUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.MultipartFileInfo;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.common.util.mvc.Script;
//import com.hanweb.common.util.security.SecurityUtil;
//import com.hanweb.complat.controller.outsideuser.OutsideUserFormBean;
//import com.hanweb.complat.entity.OutsideUser;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.complat.service.OutsideUserService;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.listener.PersonalSessionInfo;
//import com.hanweb.jis.service.JisOutSideUserService;
//import com.hanweb.jis.service.PerRealNameAuthService;
//import com.hanweb.jis.service.RealNameAuthrizationService;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.CellphoneShortMessageUtil;
//import com.hanweb.jis.util.RandomCodeUtil;
//import java.io.File;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping({"front/register"})
//public class PerRegisterController
//{
//
//  @Autowired
//  private OutsideUserService OutsideUserService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private JisOutSideUserService jisOutSideUserService;
//
//  @Autowired
//  private RealNameAuthrizationService realNameAuthService;
//
//  @Autowired
//  private PerRealNameAuthService perRealNameAuthService;
//
//  @RequestMapping({"perregister"})
//  public ModelAndView perRegister_Step1(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//  {
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
//    String appmark = request.getParameter("appmark");
//    if ((appmark != null) && (!appmark.equals(""))) {
//      session.setAttribute("appmark", appmark);
//    }
//    ModelAndView modelAndView = new ModelAndView(
//      "jis/front/perregister_step1");
//
//    String html = "perregister_step1_b.html";
//
//    session.setMaxInactiveInterval(1800);
//    session.setAttribute("permobilesend", RandomCodeUtil.getRandomNumber(9));
//
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    templateHtml = templateHtml.replace("${url}", "checkRandomAndPhoneMessage.do")
//      .replace(
//      "${verifycodeimg}", 
//      "<img id='verifyImg' src='../../verifyCode.do?code=4&var=rand&width=162&height=55&random=" + 
//      (int)(Math.random() * 100000000.0D) + 
//      "'" + 
//      " onclick=\"this.src='../../verifyCode.do?code=4&var=rand&width=162&height=55&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='162'  height='55' />")
//      .replace("${papersNumber}", "")
//      .replace("${isauth}", "")
//      .replace("${authState}", "");
//
//    if ((appmark != null) && (!appmark.equals(""))) {
//      templateHtml = templateHtml.replace("${appmark}", appmark);
//    }
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"checkWhetherTelnumExist"})
//  @ResponseBody
//  public JsonResult checkWhetherTelnumExist(String telNum)
//  {
//    JsonResult jsonRe = JsonResult.getInstance();
//    Integer count = this.OutsideUserService.findNumSameMobile(telNum, Integer.valueOf(-1));
//    if (count.intValue() > 0)
//      jsonRe.setCode("1");
//    else {
//      jsonRe.setCode("2");
//    }
//    return jsonRe;
//  }
//
//  @RequestMapping({"checkRandomAndPhoneMessage"})
//  @ResponseBody
//  public JsonResult checkRandomAndPhoneMessage(String randCode, OutsideUserFormBean outsideUserFormBean, HttpSession session, String cellphoneShortMessageRandomCodeWritenByGuest, String appmark)
//  {
//    JsonResult jsonResult = JsonResult.getInstance();
//
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//
//    if ((StringUtil.isEmpty(outsideUserFormBean.getLoginName())) || (StringUtil.isEmpty(outsideUserFormBean.getPwd()))) {
//      jsonResult.setMessage("用户名或密码为空");
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//    String loginName = SecurityUtil.RSAdecode(outsideUserFormBean.getLoginName());
//    String pwd = SecurityUtil.RSAdecode(outsideUserFormBean.getPwd());
//    if ((StringUtil.isEmpty(loginName)) || (StringUtil.isEmpty(pwd))) {
//      jsonResult.setMessage("用户名或密码为空");
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//    outsideUserFormBean.setLoginName(loginName);
//    outsideUserFormBean.setPwd(pwd);
//
//    String rand = (String)session.getAttribute("rand");
//
//    if ((StringUtil.isEmpty(rand)) || (StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
//      session.setAttribute("rand", null);
//      jsonResult.setMessage("验证码错误！");
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//
//    String cellphoneShortMessageRandomCodeMadeByJava = (String)session.getAttribute("cellphoneShortMessageRandomCodeMadeByJava");
//
//    if (!StringUtil.isEmpty(cellphoneShortMessageRandomCodeWritenByGuest)) {
//      if (cellphoneShortMessageRandomCodeWritenByGuest.equals(cellphoneShortMessageRandomCodeMadeByJava)) {
//        session.setAttribute("appmark", appmark);
//        outsideUserFormBean.setIsCellphoneVerified(Integer.valueOf(1));
//        session.setAttribute("outsideUserFormBean", outsideUserFormBean);
//        jsonResult.setSuccess(true);
//        return jsonResult;
//      }
//      session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", "-1");
//      jsonResult.setMessage("手机短信验证码错误，请点击重新获得！");
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//
//    session.setAttribute("appmark", appmark);
//    jsonResult.setSuccess(false);
//    jsonResult.setMessage("请输入手机短信验证码！");
//    return jsonResult;
//  }
//
//  @RequestMapping({"sendCellphoneShortMessageUserRe"})
//  @ResponseBody
//  public String sendCellphoneShortMessageUserRe(HttpSession session, String telNum)
//  {
//    Map<String,String> map1 = new HashMap<>();
//    Object mobileSend = session.getAttribute("permobilesend");
//    if ((StringUtil.isEmpty(telNum)) || (mobileSend == null)) {
//      map1.put("message", "请刷新页面，重新注册！");
//      map1.put("success", "false");
//      map1.put("code", "0");
//      return JsonUtil.objectToString(map1);
//    }
//
//    Object currentTimes = session.getAttribute("permobiletimes" + telNum);
//    if (currentTimes != null) {
//      int times = NumberUtil.getInt(currentTimes);
//      if (times > 0) {
//        map1.put("message", "超过最大短信发送次数");
//        map1.put("success", "false");
//        map1.put("code", "0");
//        return JsonUtil.objectToString(map1);
//      }
//    }
//
//    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
//    session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
//
//    String content = Settings.getSettings().getRegistPerMessageContent().trim()
//      .replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
//
//    String appBusinessId = Settings.getSettings().getBusinessIdForRegestingPer();
//    String appBusinessName = Settings.getSettings().getBusinessNameForRegestingPer();
//    int loseTime = 60;
//    session.setAttribute("telNum", telNum);
//    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
//    String resultJson = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, loseTime);
//    Map map = (Map)JsonUtil.StringToObject(resultJson, Map.class);
//    if ("true".equals(map.get("success")))
//    {
//      if (currentTimes == null) {
//        session.setAttribute("permobiletimes" + telNum, Integer.valueOf(1));
//      } else {
//        int times = NumberUtil.getInt(currentTimes);
//        session.setAttribute("permobiletimes" + telNum, Integer.valueOf(times + 1));
//      }
//    }
//
//    return resultJson;
//  }
//
//  @RequestMapping({"doperregister"})
//  @ResponseBody
//  public JsonResult PersonalRegister(HttpSession session)
//  {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    JsonResult jsonResult = JsonResult.getInstance();
//
//    OutsideUserFormBean outsideUserFormBean = (OutsideUserFormBean)session.getAttribute("outsideUserFormBean");
//
//    if (!outsideUserFormBean.getMobile().equals(session.getAttribute("telNum"))) {
//      jsonResult.setMessage("手机验证码错误，请重新获取！");
//      jsonResult.setSuccess(false);
//      return jsonResult;
//    }
//
//    String appmark = (String)session.getAttribute("appmark");
//
//    session.setAttribute("loginuser", outsideUserFormBean.getLoginName());
//
//    session.setAttribute("loginpass", outsideUserFormBean.getPwd());
//
//    jsonResult = this.perRealNameAuthService.verifyPerRealName(outsideUserFormBean);
//
//    if (jsonResult.isSuccess()) {
//      boolean isSuccess = false;
//      try
//      {
//        outsideUserFormBean.setMobile(outsideUserFormBean.getMobile().substring(0, 11));
//
//        isSuccess = this.jisOutSideUserService.addOutUserForReg(
//          outsideUserFormBean, appmark);
//        if (isSuccess) {
//          session.removeAttribute("outsideUserFormBean");
//          jsonResult.set(ResultState.ADD_SUCCESS);
//          jsonResult.setSuccess(true);
//          PersonalSessionInfo.setCurrentPersonalInfo(outsideUserFormBean);
//          session.setAttribute("outsideUserFormBean", outsideUserFormBean);
//        } else {
//          jsonResult.set(ResultState.ADD_FAIL);
//          jsonResult.setSuccess(false);
//          jsonResult.setMessage("注册失败！");
//        }
//      } catch (Exception e) {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("注册失败：" + e.getMessage());
//      }
//    }
//
//    return jsonResult;
//  }
//
//  @RequestMapping({"perregsuccess_b"})
//  public ModelAndView perRegSuccess(HttpServletRequest request, HttpSession session)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/perregsuccess");
//    String html = "perregsuccess_b.html";
//
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    templateHtml = templateHtml.replace("${loginurl}", 
//      "../../front/perlogin.do?appmark=gszw")
//      .replace("${indexurl}", "../../login.do");
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"perrealnameauth"})
//  @ResponseBody
//  public JsonResult realNameAuthCheck(OutsideUserFormBean outsideUserFormBean, HttpSession session, String name, String papersNumber)
//  {
//    JsonResult jsonResult = JsonResult.getInstance();
//    int isRealNameAuth = NumberUtil.getInt(Settings.getSettings()
//      .getRealNameAuth());
//    if (isRealNameAuth == 1)
//    {
//      String result = this.realNameAuthService.verifyRealname(name, papersNumber);
//      if ("TRUE".equals(result)) {
//        outsideUserFormBean.setIsauth(Integer.valueOf(1));
//        outsideUserFormBean.setAuthState(Integer.valueOf(1));
//        jsonResult.setSuccess(true);
//        jsonResult.setMessage("2");
//      } else if ("FALSE".equals(result)) {
//        outsideUserFormBean.setIsauth(Integer.valueOf(1));
//        outsideUserFormBean.setAuthState(Integer.valueOf(2));
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("3");
//      } else if ("4".equals(result)) {
//        outsideUserFormBean.setIsauth(Integer.valueOf(1));
//        outsideUserFormBean.setAuthState(Integer.valueOf(0));
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("4");
//      }
//    } else if (isRealNameAuth == 0) {
//      outsideUserFormBean.setIsauth(Integer.valueOf(0));
//      outsideUserFormBean.setAuthState(Integer.valueOf(0));
//      jsonResult.setSuccess(false);
//      jsonResult.setMessage("1");
//    }
//
//    return jsonResult;
//  }
//
//  @RequestMapping({"checkperloginid"})
//  @ResponseBody
//  public String checkPerLoginId(String loginid)
//  {
//    if (StringUtil.isEmpty(loginid)) {
//      return "";
//    }
//    OutsideUser outUser = null;
//    outUser = (OutsideUser)CacheUtil.getValue(loginid, "perusers");
//    if (outUser == null)
//    {
//      outUser = this.OutsideUserService.findByLoginName(loginid);
//    }
//    if (outUser != null)
//    {
//      return "存在相同的用户名";
//    }
//    return "";
//  }
//
//  @RequestMapping({"perauth_success"})
//  public ModelAndView perAtuhSuccess(HttpServletRequest request, HttpSession session)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/authsuccess");
//    String html = "authsuccess.html";
//
//    String templateHtml = this.templateService.readFrontTemplate(html);
//
//    templateHtml = templateHtml.replace("${url}", 
//      "../perlogin.do?appmark=gszw");
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"perauth_show"})
//  public ModelAndView perAuthShow(HttpServletRequest request, HttpSession session)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/perauth_show");
//    OutsideUser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
//    if (outuser == null)
//    {
//      RedirectView redirectView = new RedirectView("../perlogin.do");
//      modelAndView.setView(redirectView);
//      return modelAndView;
//    }
//    int isupload = outuser.getIsupload().intValue();
//    String html = "";
//    if (isupload == 0)
//      html = "perauth.html";
//    else {
//      html = "perauthstate.html";
//    }
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    String contextPath = BaseInfo.getContextPath();
//    String headPicPath = contextPath + outuser.getHeadRenamePic();
//    String bodyPicPath = contextPath + outuser.getBodyRenamePic();
//
//    templateHtml = templateHtml
//      .replace("${url}", "perauth_submit.do")
//      .replace(
//      "${rejectReason}", 
//      outuser.getRejectReason() == null ? "" : outuser
//      .getRejectReason())
//      .replace("${name}", 
//      outuser.getName() == null ? "" : outuser.getName())
//      .replace("${authstate}", String.valueOf(outuser.getAuthState()))
//      .replace("${isupload}", String.valueOf(outuser.getIsupload())) 
//      .replace("${headPicPath}", headPicPath)
//      .replace("${bodyPicPath}", bodyPicPath)
//      .replace(
//      "${papersNumber}", 
//      outuser.getPapersNumber() == null ? "" : outuser
//      .getPapersNumber());
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"perauth_submit"})
//  @ResponseBody
//  public String perAuthSubmit(String name, String papersNumber, MultipartFile headpic, MultipartFile bodypic) {
//    Script script = Script.getInstanceWithJsLib();
//    OutsideUser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
//    outuser.setName(name);
//    outuser.setPapersNumber(papersNumber);
//
//    String message = "";
//
//    MultipartFileInfo headpicinfo = MultipartFileInfo.getInstance(headpic);
//    MultipartFileInfo bodypicinfo = MultipartFileInfo.getInstance(bodypic);
//    String perimgpath = BaseInfo.getRealPath() + "/files/per/images/" + 
//      outuser.getIid() + "/";
//    File perFile = new File(perimgpath);
//    if (!perFile.exists())
//      perFile.mkdir();
//    try
//    {
//      if (headpicinfo != null) {
//        if (headpicinfo.getSize() > 5242880L) {
//          throw new OperationException("手持身份证头部照不能大于5M");
//        }
//        String filetype = headpicinfo.getFileType();
//        if ((filetype.equals("jpg")) || (filetype.equals("jpeg")) || 
//          (filetype.equals("bmp"))) {
///*          String path = perimgpath + headpicinfo.getFileName() + "." + 
//            headpicinfo.getFileType();*/
//          String path2 = perimgpath + papersNumber + "_head." + 
//            headpicinfo.getFileType();
//          File headfile = new File(path2);
//          outuser.setHeadpic("/files/per/images/" + outuser.getIid() + 
//            "/" + headpicinfo.getFileName() + "." + 
//            headpicinfo.getFileType());
//          outuser.setHeadRenamePic("/files/per/images/" + 
//            outuser.getIid() + "/" + papersNumber + "_head." + 
//            headpicinfo.getFileType());
//          ControllerUtil.writeMultipartFileToFile(headfile, headpic);
//        }
//        else
//        {
//          throw new OperationException("手持身份证头部照类型不正确");
//        }
//      }
//      if (bodypicinfo != null) {
//        if (bodypicinfo.getSize() > 5242880L) {
//          throw new OperationException("身份证正面照不能大于5M");
//        }
//        String filetype = bodypicinfo.getFileType();
//        if ((filetype.equals("jpg")) || (filetype.equals("jpeg")) || 
//          (filetype.equals("bmp"))) {
///*          String path = perimgpath + bodypicinfo.getFileName() + "." + 
//            bodypicinfo.getFileType();*/
//          String path2 = perimgpath + papersNumber + "_body." + 
//            bodypicinfo.getFileType();
//          File bodyfile = new File(path2);
//          outuser.setBodypic("/files/per/images/" + outuser.getIid() + 
//            "/" + bodypicinfo.getFileName() + "." + 
//            bodypicinfo.getFileType());
//          outuser.setBodyRenamePic("/files/per/images/" + 
//            outuser.getIid() + "/" + papersNumber + "_body." + 
//            bodypicinfo.getFileType());
//          ControllerUtil.writeMultipartFileToFile(bodyfile, bodypic);
//        }
//        else
//        {
//          throw new OperationException("身份证正面照类型不正确");
//        }
//      }
//      outuser.setIsupload(Integer.valueOf(1));
//      Boolean isSuccess = Boolean.valueOf(this.OutsideUserService.modifyAuthing(outuser));
//      if (isSuccess.booleanValue())
//        message = "success";
//    }
//    catch (OperationException e) {
//      message = e.getMessage();
//      if ("身份证号已存在,请重新设置！".equals(message)) {
//        File headfile = new File(perimgpath + papersNumber + "_head." + 
//          headpicinfo.getFileType());
//        File bodyfile = new File(perimgpath + papersNumber + "_body." + 
//          bodypicinfo.getFileType());
//        headfile.delete();
//        bodyfile.delete();
//      }
//    }
//    if (!"success".equals(message)) {
//      script.addAlert(message);
//      return script.getScript();
//    }
//    script.addScript("parent.auth", new Object[] { message });
//    return script.getScript();
//  }
//}