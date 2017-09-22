//package com.gsww.uids.controller;
//
//import com.hanweb.common.BaseInfo;
//import com.hanweb.common.util.Md5Util;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.ControllerUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.complat.entity.Corporation;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.complat.service.CorporationService;
//import com.hanweb.jis.listener.CorporationSessionInfo;
//import com.hanweb.jis.service.TemplateService;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping({"front"})
//public class OperateCorController
//{
//
//  @Autowired
//  private TemplateService templateService;
//
//  @Autowired
//  private CorporationService corporationService;
//
//  @RequestMapping({"modifycorinfo_show"})
//  public ModelAndView modifyCorInfo(String username, String pwd)
//  {
//    Corporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//    ModelAndView modelAndView = new ModelAndView("jis/front/modifycorinfo");
//    if (corporation == null)
//    {
//      RedirectView redirectView = new RedirectView("./corlogin.do");
//      modelAndView.setView(redirectView);
//      return modelAndView;
//    }
//    String path = BaseInfo.getContextPath();
//    int type = corporation.getType().intValue();
//    String corname;
//    if (type == 1)
//      corname = "企业法人";
//    else {
//      corname = "非企业法人";
//    }
//    String html = "modifycorinfo.html";
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    templateHtml = templateHtml.replace("${loginName}", corporation.getLoginName())
//      .replace("${iid}", String.valueOf(corporation.getIid()))
//      .replace("${name}", corporation.getName())
//      .replace("${type}", String.valueOf(corporation.getType()))
//      .replace("${isauth}", String.valueOf(corporation.getIsauth()))
//      .replace("${corname}", StringUtil.getString(corname))
//      .replace("${regNumber}", StringUtil.getString(corporation.getRegNumber()))
//      .replace("${realName}", StringUtil.getString(corporation.getRealName()))
//      .replace("${sex}", StringUtil.getString(corporation.getSex()))
//      .replace("${nation}", corporation.getNation() == null ? "" : corporation.getNation())
//      .replace("${scope}", corporation.getScope() == null ? "" : corporation.getScope())
//      .replace("${phone}", corporation.getPhone() == null ? "" : corporation.getPhone())
//      .replace("${cardNumber}", corporation.getCardNumber())
//      .replace("${orgNumber}", corporation.getOrgNumber())
//      .replace("${loginName}", corporation.getLoginName())
//      .replace("${mobile}", corporation.getMobile())
//      .replace("${email}", corporation.getEmail())
//      .replace("${url}", "modifycorinfo_submit.do")
//      .replace("${cardPic}", corporation.getCardPic() == null ? "" : corporation.getCardPic())
//      .replace("${licencePic}", corporation.getLicencePic() == null ? "" : corporation.getLicencePic())
//      .replace("${orgPic}", corporation.getOrgPic() == null ? "" : corporation.getOrgPic())
//      .replace("${cardRenamePic}", corporation.getCardRenamePic() == null ? "" : corporation.getCardRenamePic())
//      .replace("${licenceRenamePic}", corporation.getLicenceRenamePic() == null ? "" : corporation.getLicenceRenamePic())
//      .replace("${orgRenamePic}", corporation.getOrgRenamePic() == null ? "" : corporation.getOrgRenamePic())
//      .replace("${rejectReason}", corporation.getRejectReason() == null ? "" : corporation.getRejectReason())
//      .replace("${isupload}", String.valueOf(corporation.getIsupload()))
//      .replace("${isauth}", String.valueOf(corporation.getIsauth()))
//      .replace("${authState}", String.valueOf(corporation.getAuthState()))
//      .replace("${isshoworgnumber}", StringUtil.isEmpty(corporation.getOrgNumber()) ? "none" : "")
//      .replace("${address}", StringUtil.getString(corporation.getAddress()));
//
//    templateHtml = templateHtml.replace("${residenceDetail}", corporation.getResidenceDetail() == null ? "" : corporation.getResidenceDetail())
//      .replace("${residenceId}", corporation.getResidenceId() == null ? "" : corporation.getResidenceId())
//      .replace("${livingAreaDetail}", corporation.getLivingAreaDetail() == null ? "" : corporation.getLivingAreaDetail())
//      .replace("${livingAreaId}", corporation.getLivingAreaId() == null ? "" : corporation.getLivingAreaId());
//
//    templateHtml = templateHtml.replace("${presidenceId}", corporation.getPresidenceId() == null ? "" : corporation.getPresidenceId())
//      .replace("${gpresidenceId}", corporation.getGpresidenceId() == null ? "" : corporation.getGpresidenceId())
//      .replace("${plivingAreaId}", corporation.getPlivingAreaId() == null ? "" : corporation.getPlivingAreaId())
//      .replace("${gplivingAreaId}", corporation.getGplivingAreaId() == null ? "" : corporation.getGplivingAreaId());
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    modelAndView.addObject("path", path);
//    return modelAndView;
//  }
//
//  @RequestMapping({"modifycorinfo_submit"})
//  @ResponseBody
//  public JsonResult saveCorInfo(String randCode, HttpSession session, HttpServletResponse response, String pwd2, String corsex, String inssex, Corporation corporation) {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//
//    Corporation cp = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//    corporation.setLoginName(cp.getLoginName());
//
//    Corporation cor = this.corporationService.findByLoginName(corporation.getLoginName());
//    if ((pwd2 == null) || ("".equals(pwd2))) {
//      pwd2 = Md5Util.md5decode(corporation.getPwd());
//    }
//    corporation.setPwd(pwd2);
//    int id = cor.getIid().intValue();
//    corporation.setIid(Integer.valueOf(id));
//    try {
//      if (corporation.getType().intValue() == 1) {
//        corporation.setName(cp.getName());
//        corporation.setRegNumber(cp.getRegNumber());
//        corporation.setOrgNumber(cp.getOrgNumber());
//        corporation.setRealName(cp.getRealName());
//        corporation.setNation(cp.getNation());
//        corporation.setSex(cp.getSex());
//        corporation.setCardNumber(cp.getCardNumber());
//      } else {
//        corporation.setName(cp.getName());
//        corporation.setRegNumber(cp.getRegNumber());
//        corporation.setOrgNumber(cp.getOrgNumber());
//        corporation.setRealName(cp.getRealName());
//        corporation.setSex(cp.getSex());
//        corporation.setNation(cp.getNation());
//        corporation.setCardNumber(cp.getCardNumber());
//      }
//
//      if ((!"".equals(pwd2)) && (pwd2.length() > 0)) {
//        corporation.setPwd(pwd2);
//      }
//      isSuccess = this.corporationService.modify(corporation);
//      if (isSuccess) {
//        Corporation cor2 = this.corporationService.findByLoginName(corporation.getLoginName());
//        CorporationSessionInfo.setCurrentCorporationInfo(cor2);
//        ControllerUtil.addCookie(response, "corporationloginid", corporation.getLoginName(), 
//          604800);
//        jsonResult.set(ResultState.MODIFY_SUCCESS);
//      } else {
//        jsonResult.set(ResultState.MODIFY_FAIL);
//      }
//    } catch (OperationException e) {
//      jsonResult.setMessage(e.getMessage());
//    }
//    return jsonResult;
//  }
//  @RequestMapping({"corauth_modify"})
//  @ResponseBody
//  public JsonResult updatePerAuth() {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    Corporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
//    isSuccess = this.corporationService.updateIsUpload(corporation.getIid().intValue(), 1);
//    if (isSuccess)
//      jsonResult.set(ResultState.OPR_SUCCESS);
//    else {
//      jsonResult.set(ResultState.OPR_FAIL);
//    }
//    return jsonResult;
//  }
//}