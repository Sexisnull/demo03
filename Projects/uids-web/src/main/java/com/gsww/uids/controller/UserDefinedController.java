//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.NumberUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.jis.entity.AbstractUser;
//import com.hanweb.jis.entity.JisUserDefined;
//import com.hanweb.jis.listener.UserSessionInfo;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.service.UserDefinedService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping({"front/login"})
//public class UserDefinedController
//{
//
//  @Autowired
//  private UserDefinedService userDefinedService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @RequestMapping({"show_setting"})
//  public ModelAndView showSetting(Integer appid, String appname)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/appsetting");
//    AbstractUser user = UserSessionInfo.getFrontCurrentUserInfo();
//    int appId = NumberUtil.getInt(appid);
//    String loginName = "";
//    loginName = user.getLoginName();
//    String url = "";
//    String loginAllName = user.getLoginAllName();
//    JisUserDefined jisUserDefined = this.userDefinedService.findByAppIdAndLoginAllName(appId, loginAllName);
//    if (jisUserDefined == null) {
//      jisUserDefined = new JisUserDefined();
//      url = "add_submit.do";
//    } else {
//      url = "modify_submit.do";
//    }
//
//    String html = "appsetting.html";
//    String templateHtml = "";
//    templateHtml = this.templateService.readDefaultTemplate(html);
//    templateHtml = templateHtml.replace("${url}", url).replace("${userdefinediid}", String.valueOf(jisUserDefined.getIid()))
//      .replace("${loginname}", loginName).replace("${loginallname}", loginAllName)
//      .replace("${appid}", StringUtil.getString(appid)).replace("${appname}", appname)
//      .replace("${apploginname}", StringUtil.getString(jisUserDefined.getApploginname()));
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"add_submit"})
//  @ResponseBody
//  public JsonResult addSubmit(JisUserDefined jisUserDefined)
//  {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    try {
//      isSuccess = this.userDefinedService.add(jisUserDefined);
//      if (isSuccess) {
//        jsonResult.set(ResultState.ADD_SUCCESS);
//      } else {
//        jsonResult.set(ResultState.ADD_FAIL);
//        jsonResult.setMessage("设置失败");
//      }
//    } catch (OperationException e) {
//      jsonResult.setMessage(e.getMessage());
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"modify_submit"})
//  @ResponseBody
//  public JsonResult modifySubmit(JisUserDefined jisUserDefined)
//  {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    isSuccess = this.userDefinedService.modify(jisUserDefined);
//    if (isSuccess) {
//      jsonResult.set(ResultState.MODIFY_SUCCESS);
//    } else {
//      jsonResult.set(ResultState.MODIFY_FAIL);
//      jsonResult.setMessage("设置失败");
//    }
//    return jsonResult;
//  }
//}