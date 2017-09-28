package com.gsww.uids.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gsww.uids.constant.OutsideUserFormBean;
import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;

@Controller
@RequestMapping({"front"})
public class OperatePerController
{

  @Autowired
  private ComplatOutsideuserService OutsideUserService;


  @RequestMapping({"modifyperinfo_show"})
  public ModelAndView modifyPerInfo(HttpServletResponse response)
  {
    ComplatOutsideuser user = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    String path = BaseInfo.getContextPath();
    ModelAndView modelAndView = new ModelAndView("jis/front/modifyperinfo");

    if (user != null) {
      String password = Md5Util.md5decode(user.getPwd());

      modelAndView.addObject("password", password);
    }
    else {
      RedirectView redirectView = new RedirectView("./perlogin.do");
      modelAndView.setView(redirectView);
      return modelAndView;
    }
    String html = "modifyperinfo.html";
    
    modelAndView.addObject("url", "modifyperinfo_submit.do");
    modelAndView.addObject("user", user);
    modelAndView.addObject("path", path);
    return modelAndView;
  }

  @RequestMapping({"modifyperinfo_submit"})
  @ResponseBody
  public JsonResult savePerInfo(String randCode, HttpSession session, HttpServletResponse response, String pwd2, OutsideUserFormBean outsideUserFormBean) {
    ComplatOutsideuser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    outsideUserFormBean.setLoginName(outuser.getLoginName());
    outsideUserFormBean.setName(outuser.getName());
    outsideUserFormBean.setPapersNumber(outuser.getPapersNumber());

    if ((pwd2 == null) || ("".equals(pwd2))) {
      pwd2 = Md5Util.md5decode(outuser.getPwd());
    }
    outsideUserFormBean.setPwd(pwd2);
    boolean isSuccess = false;
    ComplatOutsideuser u = this.OutsideUserService.findByLoginName(outsideUserFormBean.getLoginName());

    int id = u.getIid().intValue();
    int isauth = u.getIsAuth().intValue();
    outsideUserFormBean.setIid(Integer.valueOf(id));
    JsonResult jsonResult = JsonResult.getInstance();
    try {
      if ((!"".equals(pwd2)) && (pwd2.length() > 0)) {
        outsideUserFormBean.setPwd(pwd2);
      }
      if (isauth == 1) {
        outsideUserFormBean.setSex(u.getSex());
      }
      this.OutsideUserService.save(outsideUserFormBean);
      isSuccess = true;
      if (isSuccess) {
        PersonalSessionInfo.setCurrentPersonalInfo(outsideUserFormBean);
        ControllerUtil.addCookie(response, "personalloginid", outsideUserFormBean.getLoginName(), 
          604800);
        jsonResult.set(ResultState.MODIFY_SUCCESS);
      } else {
        jsonResult.set(ResultState.MODIFY_FAIL);
      }
    } catch (Exception e) {
      jsonResult.setMessage(e.getMessage());
    }

    return jsonResult;
  }
  @RequestMapping({"perauth_modify"})
  @ResponseBody
  public JsonResult updatePerAuth() {
    boolean isSuccess = false;
    JsonResult jsonResult = JsonResult.getInstance();
    ComplatOutsideuser user = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    user = this.OutsideUserService.findByKey(user.getIid());
    user.setIsUpload(1);
    this.OutsideUserService.save(user);
    isSuccess = true;
    if (isSuccess)
      jsonResult.set(ResultState.OPR_SUCCESS);
    else {
      jsonResult.set(ResultState.OPR_FAIL);
    }
    return jsonResult;
  }
}