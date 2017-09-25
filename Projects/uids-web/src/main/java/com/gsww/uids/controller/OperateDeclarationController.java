//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.complat.entity.Corporation;
//import com.hanweb.complat.service.CorporationService;
//import com.hanweb.jis.entity.JisDeclarationInfoFormBean;
//import com.hanweb.jis.listener.CorporationSessionInfo;
//import com.hanweb.jis.service.AuthLogService;
//import com.hanweb.jis.service.JisDeclarationInfoService;
//import com.hanweb.jis.service.TemplateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping({"front"})
//public class OperateDeclarationController
//{
//
//  @Autowired
//  private JisDeclarationInfoService jisDeclarationInfoService;
//
//  @Autowired
//  private CorporationService corporationService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private AuthLogService authLogService;
//
//  @RequestMapping({"showdeclarationinfo"})
//  public ModelAndView showDeclaration(String appmark, String gotoUrl)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/showdeclaration");
//    Corporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//    if (corporation == null) {
//      ModelAndView mav = new ModelAndView();
//      RedirectView redirectView = new RedirectView("./corlogin.do");
//      mav.setView(redirectView);
//      return mav;
//    }
//    String html = "completedeclarationinfo.html";
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    if ((appmark != null) && (gotoUrl != null) && (!"".equals(appmark)) && (!"".equals(gotoUrl))) {
//      templateHtml = templateHtml.replace("${appmark}", appmark)
//        .replace("${gotoUrl}", gotoUrl);
//    }
//    templateHtml = templateHtml.replace("${url}", "./submitdeclarationinfo.do")
//      .replace("${realName}", corporation.getRealName())
//      .replace("${type}", corporation.getType().intValue() == 1 ? "企业法人" : "非企业法人")
//      .replace("${mobile}", corporation.getMobile())
//      .replace("${regNumber}", corporation.getRegNumber())
//      .replace("${email}", corporation.getEmail())
//      .replace("${name}", corporation.getName())
//      .replace("${loginName}", corporation.getLoginName())
//      .replace("${nation}", corporation.getNation())
//      .replace("${cardNumber}", corporation.getCardNumber())
//      .replace("${sex}", corporation.getSex());
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @ResponseBody
//  @RequestMapping({"submitdeclarationinfo"})
//  public JsonResult submitDeclarationInfo(JisDeclarationInfoFormBean jisDeclarationFormBean, String appmark, String gotoUrl) {
//    JsonResult jsonResult = JsonResult.getInstance();
//    try {
//      Integer iid = this.jisDeclarationInfoService.add(jisDeclarationFormBean);
//      String url = "";
//      String ticket = "";
//      if (iid.intValue() > 0) {
//        Corporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//
//        if ((corporation.getDeclarationIid() != null) && (corporation.getDeclarationIid().intValue() > 0)) {
//          corporation = this.corporationService.findByIid(corporation.getIid().intValue());
//          this.jisDeclarationInfoService.delete(corporation.getDeclarationIid());
//        }
//        corporation = this.corporationService.findByIid(corporation.getIid().intValue());
//        corporation.setDeclarationIid(iid);
//        int useriid = corporation.getIid().intValue();
//        boolean result = this.corporationService.modifyDeclarationIid(iid.intValue(), useriid);
//        if (!result) {
//          throw new Exception("操作失败");
//        }
//        System.out.println(appmark + "   " + gotoUrl + "   ");
//        if ((StringUtil.isEmpty(appmark)) || (gotoUrl.indexOf("${gotoUrl}") != -1) || (appmark.indexOf("${appmark}") != -1)) {
//          if (StringUtil.isEmpty(ticket))
//            throw new Exception("票据生成失败");
//        }
//        else {
//          ticket = this.authLogService.add(corporation, appmark, 2);
//        }
//        url = gotoUrl + "?ticket=" + ticket;
//        jsonResult.set(ResultState.ADD_SUCCESS);
//        jsonResult.setMessage(url);
//      } else {
//        jsonResult.set(ResultState.ADD_FAIL);
//        jsonResult.setMessage("新增信息失败");
//      }
//    }
//    catch (Exception e) {
//      jsonResult.set(ResultState.ADD_FAIL);
//      jsonResult.setMessage(e.getMessage());
//    }
//    return jsonResult;
//  }
//}