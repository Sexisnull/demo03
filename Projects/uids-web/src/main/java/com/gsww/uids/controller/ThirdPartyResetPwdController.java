//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.entity.JisUser;
//import com.hanweb.jis.service.JisUserService;
//import com.hanweb.jis.service.TemplateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class ThirdPartyResetPwdController
//{
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private JisUserService jisUserService;
//
//  @RequestMapping({"thirdresetpwd"})
//  public ModelAndView showThirdResetPWD(String loginAllName)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/thirdresetpwd");
//    String copyRight = Settings.getSettings().getCopyRight();
//    String pwdLevel = StringUtil.getString(Settings.getSettings().getPwdLevel());
//    String pwdLevelTip = "";
//    if ("1".equals(pwdLevel))
//      pwdLevelTip = "中";
//    else if ("0".equals(pwdLevel))
//      pwdLevelTip = "弱";
//    else {
//      pwdLevelTip = "强";
//    }
//    String templateHtml = this.templateService.readDefaultTemplate("thirdresetpwd.html");
//    if ((loginAllName == null) || ("".equals(loginAllName)))
//      templateHtml = templateHtml.replace("${errmsgdisplay}", "display:'';").replace("${resetdisplay}", "display:none;");
//    else {
//      templateHtml = templateHtml.replace("${errmsgdisplay}", "display: none;").replace("${resetdisplay}", "display:'';");
//    }
//    templateHtml = templateHtml.replace("${loginAllName}", StringUtil.getString(loginAllName)).replace("${copyRight}", copyRight)
//      .replace("${pwdlevel}", pwdLevel).replace("${pwdleveltip}", pwdLevelTip).replace("${url}", "resetpwd_submit.do");
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//  @RequestMapping({"resetpwd_submit"})
//  @ResponseBody
//  public JsonResult submitResetPwd(String loginallname, String oldpwd, String pwd) {
//    JsonResult jsonResult = JsonResult.getInstance();
//    boolean result = false;
//    JisUser jisUser = this.jisUserService.findByLoginAllName(loginallname);
//    if (jisUser != null) {
//      if (!jisUser.getPwd().equals(oldpwd))
//        result = false;
//      else {
//        result = true;
//      }
//      if (!result) {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage("旧密码不正确！");
//        return jsonResult;
//      }
//      try {
//        result = this.jisUserService.modifyPassword(jisUser.getIid(), jisUser.getLoginName(), pwd, null);
//        if (result) {
//          jsonResult.setSuccess(true);
//        } else {
//          jsonResult.setSuccess(false);
//          jsonResult.setMessage("修改密码失败！");
//        }
//      } catch (OperationException e) {
//        jsonResult.setSuccess(false);
//        jsonResult.setMessage(e.getMessage());
//      }
//      return jsonResult;
//    }
//
//    jsonResult.setSuccess(false);
//    jsonResult.setMessage("用户不存在！");
//    return jsonResult;
//  }
//}