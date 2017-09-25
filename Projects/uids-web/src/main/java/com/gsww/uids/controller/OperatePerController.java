//package com.gsww.uids.controller;
//
//import com.hanweb.common.BaseInfo;
//import com.hanweb.common.util.Md5Util;
//import com.hanweb.common.util.mvc.ControllerUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.complat.controller.outsideuser.OutsideUserFormBean;
//import com.hanweb.complat.entity.OutsideUser;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.complat.service.OutsideUserService;
//import com.hanweb.jis.listener.PersonalSessionInfo;
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
//public class OperatePerController
//{
//
//  @Autowired
//  private OutsideUserService OutsideUserService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @RequestMapping({"modifyperinfo_show"})
//  public ModelAndView modifyPerInfo(HttpServletResponse response)
//  {
//    OutsideUser user = PersonalSessionInfo.getFrontCurrentPersonalInfo();
//    String path = BaseInfo.getContextPath();
//    ModelAndView modelAndView = new ModelAndView("jis/front/modifyperinfo");
//
//    if (user != null) {
//      String password = Md5Util.md5decode(user.getPwd());
//
//      modelAndView.addObject("password", password);
//    }
//    else {
//      RedirectView redirectView = new RedirectView("./perlogin.do");
//      modelAndView.setView(redirectView);
//      return modelAndView;
//    }
//    String html = "modifyperinfo.html";
//    String templateHtml = this.templateService.readFrontTemplate(html);
//    templateHtml = templateHtml.replace("${loginName}", user.getLoginName())
//      .replace("${name}", user.getName())
//      .replace("${mobile}", user.getMobile())
//      .replace("${email}", user.getEmail())
//      .replace("${age}", String.valueOf(user.getAge()))
//      .replace("${degree}", user.getDegree() == null ? "" : user.getDegree())
//      .replace("${workUnit}", user.getWorkUnit() == null ? "" : user.getWorkUnit())
//      .replace("${headship}", user.getHeadship() == null ? "" : user.getHeadship())
//      .replace("${fax}", user.getFax() == null ? "" : user.getFax())
//      .replace("${phone}", user.getPhone() == null ? "" : user.getPhone())
//      .replace("${comptel}", user.getComptel() == null ? "" : user.getComptel())
//      .replace("${qq}", user.getQq() == null ? "" : user.getQq())
//      .replace("${msn}", user.getMsn() == null ? "" : user.getMsn())
//      .replace("${post}", user.getPost() == null ? "" : user.getPost())
//      .replace("${address}", user.getAddress() == null ? "" : user.getAddress())
//      .replace("${headpic}", user.getHeadpic() == null ? "" : user.getHeadpic())
//      .replace("${bodypic}", user.getBodypic() == null ? "" : user.getBodypic())
//      .replace("${headRenamePic}", user.getHeadRenamePic() == null ? "" : user.getHeadRenamePic())
//      .replace("${bodyRenamePic}", user.getBodyRenamePic() == null ? "" : user.getBodyRenamePic())
//      .replace("${papersNumber}", user.getPapersNumber() == null ? "" : user.getPapersNumber())
//      .replace("${rejectReason}", user.getRejectReason() == null ? "" : user.getRejectReason())
//      .replace("${sex}", user.getSex() == null ? "" : user.getSex())
//      .replace("${isauth}", String.valueOf(user.getIsauth()))
//      .replace("${isupload}", String.valueOf(user.getIsupload()))
//      .replace("${authState}", String.valueOf(user.getAuthState()))
//      .replace("${url}", "modifyperinfo_submit.do")
//      .replace("${residenceDetail}", user.getResidenceDetail() == null ? "" : user.getResidenceDetail())
//      .replace("${residenceId}", user.getResidenceId() == null ? "" : user.getResidenceId())
//      .replace("${livingAreaDetail}", user.getLivingAreaDetail() == null ? "" : user.getLivingAreaDetail())
//      .replace("${livingAreaId}", user.getLivingAreaId() == null ? "" : user.getLivingAreaId());
//
//    templateHtml = templateHtml.replace("${presidenceId}", user.getPresidenceId() == null ? "" : user.getPresidenceId())
//      .replace("${gpresidenceId}", user.getGpresidenceId() == null ? "" : user.getGpresidenceId())
//      .replace("${plivingAreaId}", user.getPlivingAreaId() == null ? "" : user.getPlivingAreaId())
//      .replace("${gplivingAreaId}", user.getGplivingAreaId() == null ? "" : user.getGplivingAreaId());
//
//    modelAndView.addObject("templatehtml", templateHtml);
//    modelAndView.addObject("url", "modifyperinfo_submit.do");
//    modelAndView.addObject("user", user);
//    modelAndView.addObject("path", path);
//    return modelAndView;
//  }
//
//  @RequestMapping({"modifyperinfo_submit"})
//  @ResponseBody
//  public JsonResult savePerInfo(String randCode, HttpSession session, HttpServletResponse response, String pwd2, OutsideUserFormBean outsideUserFormBean) {
//    OutsideUser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
//    outsideUserFormBean.setLoginName(outuser.getLoginName());
//    outsideUserFormBean.setName(outuser.getName());
//    outsideUserFormBean.setPapersNumber(outuser.getPapersNumber());
//
//    if ((pwd2 == null) || ("".equals(pwd2))) {
//      pwd2 = Md5Util.md5decode(outuser.getPwd());
//    }
//    outsideUserFormBean.setPwd(pwd2);
//    boolean isSuccess = false;
//    OutsideUser u = this.OutsideUserService.findByLoginName(outsideUserFormBean.getLoginName());
//
//    int id = u.getIid().intValue();
//    int isauth = u.getIsauth().intValue();
//    outsideUserFormBean.setIid(Integer.valueOf(id));
//    JsonResult jsonResult = JsonResult.getInstance();
//    try {
//      if ((!"".equals(pwd2)) && (pwd2.length() > 0)) {
//        outsideUserFormBean.setPwd(pwd2);
//      }
//      if (isauth == 1) {
//        outsideUserFormBean.setSex(u.getSex());
//      }
//      isSuccess = this.OutsideUserService.modify(outsideUserFormBean);
//      if (isSuccess) {
//        PersonalSessionInfo.setCurrentPersonalInfo(outsideUserFormBean);
//        ControllerUtil.addCookie(response, "personalloginid", outsideUserFormBean.getLoginName(), 
//          604800);
//        jsonResult.set(ResultState.MODIFY_SUCCESS);
//      } else {
//        jsonResult.set(ResultState.MODIFY_FAIL);
//      }
//    } catch (OperationException e) {
//      jsonResult.setMessage(e.getMessage());
//    }
//
//    return jsonResult;
//  }
//  @RequestMapping({"perauth_modify"})
//  @ResponseBody
//  public JsonResult updatePerAuth() {
//    boolean isSuccess = false;
//    JsonResult jsonResult = JsonResult.getInstance();
//    OutsideUser user = PersonalSessionInfo.getFrontCurrentPersonalInfo();
//
//    isSuccess = this.OutsideUserService.updateIsUpload(user.getIid().intValue(), 1);
//    if (isSuccess)
//      jsonResult.set(ResultState.OPR_SUCCESS);
//    else {
//      jsonResult.set(ResultState.OPR_FAIL);
//    }
//    return jsonResult;
//  }
//}