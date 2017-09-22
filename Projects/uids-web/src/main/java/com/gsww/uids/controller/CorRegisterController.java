//package com.gsww.uids.controller;
//
//import com.hanweb.common.util.CacheUtil;
//import com.hanweb.common.util.JsonUtil;
//import com.hanweb.common.util.NumberUtil;
//import com.hanweb.common.util.SpringUtil;
//import com.hanweb.common.util.StringUtil;
//import com.hanweb.common.util.mvc.JsonResult;
//import com.hanweb.common.util.mvc.ResultState;
//import com.hanweb.common.util.security.SecurityUtil;
//import com.hanweb.complat.controller.outsideuser.CorPorationFormBean;
//import com.hanweb.complat.entity.Corporation;
//import com.hanweb.complat.exception.OperationException;
//import com.hanweb.complat.service.CorporationService;
//import com.hanweb.jis.constant.Settings;
//import com.hanweb.jis.listener.CorporationSessionInfo;
//import com.hanweb.jis.service.TemplateService;
//import com.hanweb.jis.util.AccessUtil;
//import com.hanweb.jis.util.CellphoneShortMessageUtil;
//import com.hanweb.jis.util.RandomCodeUtil;
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
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping({"front/register"})
//public class CorRegisterController
//{
//
//  @Autowired
//  private CorporationService corporationService;
//
//  @Autowired
//  private TemplateService templateService;
//
//  @RequestMapping({"corregister"})
//  public ModelAndView corRegister_Step1(HttpServletRequest request, HttpServletResponse response, HttpSession session, String aa, String bb)
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
//    ModelAndView modelAndView = new ModelAndView("jis/front/corregister_step1");
//
//    String html = "corregister.html";
//    String templateHtml = this.templateService.readFrontTemplate(html);
//
//    session.setMaxInactiveInterval(1800);
//    session.setAttribute("cormobilesend", RandomCodeUtil.getRandomNumber(9));
//
//    templateHtml = templateHtml
//      .replace("${copyRight}", Settings.getSettings().getCopyRight())
//      .replace("${url}", "docorregister.do")
//      .replace("${verifycodeimg}", 
//      "<img id='verifyImg' src='../../verifyCode.do?code=4&var=rand&width=162&height=40&random=" + 
//      (int)(Math.random() * 100000000.0D) + 
//      "'" + 
//      " onclick=\"this.src='../../verifyCode.do?code=4&var=rand&width=162&height=40&random='+ Math.random()" + 
//      ";\" style='cursor:pointer'  width='162'  height='40' />");
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//
//  @RequestMapping({"docorregister"})
//  @ResponseBody
//  public JsonResult corregister_submit(String randCode, HttpSession session, CorPorationFormBean corPorationFormBean, String smsCode, String corsex, String inssex)
//  {
//    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
//      return null;
//    }
//    JsonResult jsonResult = JsonResult.getInstance();
//    boolean isSuccess = false;
//
//    String orgNumber = "";
//    String rand = (String)session.getAttribute("rand");
//    String cellphoneShortMessageRandomCodeMadeByJava = 
//      StringUtil.getString(session.getAttribute("corRegistSmsCode"));
//    try {
//      if ((StringUtil.isEmpty(rand)) || (StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
//        session.setAttribute("rand", null);
//        throw new OperationException("register.randcode.error");
//      }
//
//      if (!corPorationFormBean.getMobile().equals(session.getAttribute("telNum"))) {
//        throw new OperationException("register.phonecode.error");
//      }
//      if (StringUtil.isNotEmpty(smsCode)) {
//        if (!StringUtil.equals(smsCode, 
//          StringUtil.getString(cellphoneShortMessageRandomCodeMadeByJava)))
//          throw new OperationException("register.phonecode.error");
//      }
//      else {
//        throw new OperationException("register.phonecodeempty.error");
//      }
//
//      if (corPorationFormBean.getType() == null) {
//        throw new OperationException("register.nocortype.error");
//      }
//      String name = corPorationFormBean.getName();
//      String realName = corPorationFormBean.getRealName();
//      String nation = corPorationFormBean.getNation();
//      String cardNumber = corPorationFormBean.getCardNumber();
//      orgNumber = corPorationFormBean.getOrgNumber();
//
//      if (corPorationFormBean.getType().intValue() == 1) {
//        name = name.split(",")[0];
//        realName = realName.split(",")[0];
//        cardNumber = cardNumber.split(",")[0];
//        nation = nation.split(",")[0];
//        if (!StringUtil.equals(",", orgNumber))
//          orgNumber = orgNumber.split(",")[0];
//        else {
//          orgNumber = "";
//        }
//        corPorationFormBean.setSex(corsex);
//      } else {
//        corPorationFormBean.setRegNumber("");
//        name = name.split(",")[1];
//        realName = realName.split(",")[1];
//        cardNumber = cardNumber.split(",")[1];
//        nation = nation.split(",")[1];
//        if (!StringUtil.equals(",", orgNumber))
//          orgNumber = orgNumber.split(",")[1];
//        else {
//          orgNumber = "";
//        }
//        corPorationFormBean.setSex(inssex);
//      }
//      corPorationFormBean.setRealName(realName);
//      corPorationFormBean.setName(name);
//      corPorationFormBean.setNation(nation);
//      corPorationFormBean.setCardNumber(cardNumber);
//      corPorationFormBean.setOrgNumber(orgNumber);
//
//      if ((StringUtil.isEmpty(corPorationFormBean.getLoginName())) || (StringUtil.isEmpty(corPorationFormBean.getPwd()))) {
//        jsonResult.setMessage("用户名或密码为空");
//        jsonResult.setSuccess(false);
//        return jsonResult;
//      }
//      String loginName = SecurityUtil.RSAdecode(corPorationFormBean.getLoginName());
//      String password = SecurityUtil.RSAdecode(corPorationFormBean.getPwd());
//      if ((StringUtil.isEmpty(loginName)) || (StringUtil.isEmpty(password))) {
//        jsonResult.setMessage("用户名或密码为空");
//        jsonResult.setSuccess(false);
//        return jsonResult;
//      }
//      corPorationFormBean.setLoginName(loginName);
//      corPorationFormBean.setPwd(password);
//
//      isSuccess = this.corporationService.add(corPorationFormBean);
//      if (isSuccess)
//      {
//        jsonResult.set(ResultState.ADD_SUCCESS);
//        CorporationSessionInfo.setCurrentCorporationInfo(corPorationFormBean);
//      } else {
//        jsonResult.set(ResultState.ADD_FAIL);
//        jsonResult.setMessage("注册失败！");
//      }
//    }
//    catch (OperationException e) {
//      if ("register.randcode.error".equals(e.getMessage()))
//        jsonResult.setMessage("验证码错误");
//      else if ("register.phonecode.error".equals(e.getMessage()))
//        jsonResult.setMessage("手机验证码错误");
//      else if ("register.phonecodeempty.error".equals(e.getMessage()))
//        jsonResult.setMessage("手机验证码为空");
//      else if ("register.nocortype.error".equals(e.getMessage()))
//        jsonResult.setMessage("请选择法人类型");
//      else {
//        jsonResult.setMessage(e.getMessage());
//      }
//      jsonResult.setSuccess(false);
//    }
//    return jsonResult;
//  }
//
//  @RequestMapping({"sendCellphoneShortMessageCorRe"})
//  @ResponseBody
//  public String sendCellphoneShortMessageCorRe(HttpSession session, String telNum)
//  {
//    Map<String,String> map1 = new HashMap<>();
//    Object mobileSend = session.getAttribute("cormobilesend");
//    if ((StringUtil.isEmpty(telNum)) || (mobileSend == null)) {
//      map1.put("message", "请刷新页面，重新注册！");
//      map1.put("success", "false");
//      map1.put("code", "0");
//      return JsonUtil.objectToString(map1);
//    }
//
//    Object currentTimes = session.getAttribute("cormobiletimes" + telNum);
//    if (currentTimes != null) {
//      int times = NumberUtil.getInt(currentTimes);
//      if (times > 5) {
//        map1.put("message", "超过最大短信发送次数");
//        map1.put("success", "false");
//        map1.put("code", "0");
//        return JsonUtil.objectToString(map1);
//      }
//    }
//
//    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
//    session.setAttribute("corRegistSmsCode", cellphoneShortMessageRandomCodeMadeByJava);
//    String content = Settings.getSettings().getRegistCorMessageContent().trim()
//      .replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);
//    String appBusinessId = Settings.getSettings().getBusinessIdForRegestingCor().trim();
//    String appBusinessName = Settings.getSettings().getBusinessNameForRegestingCor().trim();
//
//    int lostTime = 60;
//    session.setAttribute("telNum", telNum);
//
//    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
//    String resultJson = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, lostTime);
//    Map map = (Map)JsonUtil.StringToObject(resultJson, Map.class);
//    if ("true".equals(map.get("success")))
//    {
//      if (currentTimes == null) {
//        session.setAttribute("cormobiletimes" + telNum, Integer.valueOf(1));
//      } else {
//        int times = NumberUtil.getInt(currentTimes);
//        session.setAttribute("cormobiletimes" + telNum, Integer.valueOf(times + 1));
//      }
//    }
//    return resultJson;
//  }
//
//  @RequestMapping({"checkcorloginid"})
//  @ResponseBody
//  public String checkCorLoginId(String loginid)
//  {
//    if (StringUtil.isEmpty(loginid)) {
//      return "";
//    }
//    Corporation corporation = null;
//    corporation = (Corporation)CacheUtil.getValue(loginid, "corusers");
//    if (corporation == null) {
//      corporation = this.corporationService.findByLoginName(loginid);
//    }
//    if (corporation != null) {
//      return "存在相同的法人用户";
//    }
//    return "";
//  }
//
//  @RequestMapping({"checkcormobile"})
//  @ResponseBody
//  public String checkCorMobile(String telNum) {
//    if (StringUtil.isEmpty(telNum)) {
//      return "";
//    }
//    Corporation corporation = null;
//    corporation = this.corporationService.findByMobile(telNum);
//    if (corporation == null) {
//      return "";
//    }
//    return "存在相同的手机号码";
//  }
//
//  @RequestMapping({"corregsuccess"})
//  public ModelAndView perRegSuccess(HttpServletRequest request, HttpSession session)
//  {
//    ModelAndView modelAndView = new ModelAndView("jis/front/corregsuccess");
//    String html = "corregsuccess.html";
//
//    String templateHtml = this.templateService.readFrontTemplate(html);
//
//    templateHtml = templateHtml.replace("${loginurl}", "../corlogin.do?appmark=gszw");
//    modelAndView.addObject("templatehtml", templateHtml);
//    return modelAndView;
//  }
//}