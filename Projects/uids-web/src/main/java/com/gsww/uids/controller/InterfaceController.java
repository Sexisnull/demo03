//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.SafeUtil;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//@RequestMapping({"front"})
//public class InterfaceController
//{
//  @RequestMapping({"per/interface"})
//  public ModelAndView perSsoLogin(HttpServletResponse response, String action, String appmark, String gotoUrl, String domain)
//  {
//    if (action == null) {
//      return null;
//    }
//    if (gotoUrl == null) {
//      gotoUrl = "";
//    }
//    if ((SafeUtil.isSqlAndXss(gotoUrl)) || (SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark)) || 
//      (SafeUtil.isSqlAndXss(domain))) {
//      gotoUrl = "";
//      action = "";
//      appmark = "gszw";
//      domain = "";
//    }
//
//    HttpSession session = SpringUtil.getRequest().getSession();
//    session.setAttribute("gotoUrl", gotoUrl);
//    session.setAttribute("appmark", appmark);
//    session.setAttribute("domain", StringUtil.getString(domain));
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.addObject("action", action);
//    String url = "";
//    if (action.indexOf("ticketLogin") != -1) {
//      url = "../perlogin.do";
//      modelAndView.addObject("appmark", appmark);
//      modelAndView.addObject("gotoUrl", gotoUrl);
//      modelAndView.addObject("domain", StringUtil.getString(domain));
//    } else if (action.indexOf("register") != -1) {
//      url = "../register/perregister.do";
//    } else if (action.indexOf("logout") != -1) {
//      url = "../perlogout.do";
//    } else if (action.equals("editPassword")) {
//      url = "../modifyperinfo_show.do ";
//    } else if (action.equals("editUser")) {
//      url = "../modifyperinfo_show.do ";
//    }
//    RedirectView redirectView = new RedirectView(url);
//    modelAndView.setView(redirectView);
//    return modelAndView;
//  }
//
//  @RequestMapping({"cor/interface"})
//  public ModelAndView corSsoLogin(HttpServletResponse response, String action, String appmark, String gotoUrl, String domain)
//  {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    if (gotoUrl == null) {
//      gotoUrl = "";
//    }
//    if ((SafeUtil.isSqlAndXss(gotoUrl)) || (SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark)) || 
//      (SafeUtil.isSqlAndXss(domain))) {
//      gotoUrl = "";
//      action = "";
//      appmark = "gszw";
//      domain = "";
//    }
//    if ((SafeUtil.isXss(action)) || (SafeUtil.isXss(appmark)) || (SafeUtil.isXss(gotoUrl)) || (SafeUtil.isXss(domain))) {
//      return null;
//    }
//    HttpSession session = SpringUtil.getRequest().getSession();
//    session.setAttribute("gotoUrl", gotoUrl);
//    session.setAttribute("appmark", appmark);
//    session.setAttribute("domain", StringUtil.getString(domain));
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.addObject("action", action);
//    String url = "";
//    if (action.indexOf("ticketLogin") != -1) {
//      url = "../corlogin.do";
//      modelAndView.addObject("appmark", appmark);
//      modelAndView.addObject("gotoUrl", gotoUrl);
//      modelAndView.addObject("domain", StringUtil.getString(domain));
//    } else if (action.indexOf("register") != -1) {
//      url = "../register/corregister.do";
//    } else if (action.indexOf("logout") != -1) {
//      url = "../corlogout.do";
//    } else if (action.equals("editPassword")) {
//      url = "../modifycorinfo_show.do ";
//    } else if (action.equals("editUser")) {
//      url = "../modifycorinfo_show.do ";
//    }
//    RedirectView redirectView = new RedirectView(url);
//    modelAndView.setView(redirectView);
//    return modelAndView;
//  }
//}