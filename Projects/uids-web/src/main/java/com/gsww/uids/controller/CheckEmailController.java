//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.StringUtil;
//import com.gsww.uids.entity.ComplatOutsideuser;
//import com.gsww.uids.service.JisUserdetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping({"front"})
//public class CheckEmailController
//{
//
//  @Autowired
//  private JisUserdetailService outSideUserDetailService;
//
//  @RequestMapping({"checkemail"})
//  @ResponseBody
//  public String doCheckEmail(String loginid, String email)
//  {
//    if (StringUtil.isEmpty(email)) {
//      return "请填写您注册时的邮箱地址";
//    }
//    ComplatOutsideuser outsideUser = this.outSideUserDetailService.findByLoginName(loginid);
//    String outemail = "";
//    if (outsideUser != null) {
//      outemail = outsideUser.getEmail();
//    }
//
//    if (!outemail.equals(email)) {
//      return "邮箱地址不正确";
//    }
//    return "";
//  }
//  @RequestMapping({"checkfindloginid"})
//  @ResponseBody
//  public String checkLoginId(String loginid) {
//    if (StringUtil.isEmpty(loginid)) {
//      return "输入登录名";
//    }
//    ComplatOutsideuser outsideUser = this.outSideUserDetailService.findByLoginName(loginid);
//
//    if (outsideUser == null) {
//      return "登录名不正确";
//    }
//    return "";
//  }
//}