package com.gsww.uids.controller;

import com.gsww.jup.util.CellphoneShortMessageUtil;
import com.gsww.jup.util.RSAUtil;
import com.gsww.jup.util.RandomCodeUtil;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.service.front.JisOutSideUserService;
import com.gsww.uids.service.front.PerRealNameAuthService;
import com.gsww.uids.util.AccessUtil;
import com.gsww.uids.util.exception.OperationException;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.CacheUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.util.security.SecurityUtil;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping({"front/register"})
public class PerRegisterController
{
	private static Logger logger = LoggerFactory.getLogger(ComplatRoleController.class);

  @Autowired
  private ComplatOutsideuserService OutsideUserService;

  @Autowired
  private JisOutSideUserService jisOutSideUserService;

//  @Autowired
//  private RealNameAuthrizationService realNameAuthService;

  @Autowired
  private PerRealNameAuthService perRealNameAuthService;

  /**
   * 个人注册页面
   */
  @RequestMapping({"perregister"})
  public ModelAndView perRegister_Step1(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session)
  {
    
    //应用
    String appmark = request.getParameter("appmark");
    if ((appmark != null) && (!appmark.equals(""))) {
      session.setAttribute("appmark", appmark);
    }
    ModelAndView modelAndView = new ModelAndView("jis/front/perregister_step1");

    session.setMaxInactiveInterval(1800);
    session.setAttribute("permobilesend", RandomCodeUtil.getRandomNumber(9));
    
    int maxNum = 36;
    int count = 0;
    char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
      'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 
      'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    StringBuffer pwd = new StringBuffer("");
    Random r = new Random();
    while (count < 8) {
      int i = Math.abs(r.nextInt(maxNum));
      if ((i >= 0) && (i < str.length)) {
        pwd.append(str[i]);
        count++;
      }
    }
    String random = pwd.toString();
    session.setAttribute("random", random);
    
    model.addAttribute("url", "checkRandomAndPhoneMessage");
    model.addAttribute("verifycodeimg", "<img id='verifyImg' src='../../verifyCode?code=4&var=rand&width=162&height=55&random=" + 
      (int)(Math.random() * 100000000.0D) + 
      "'" + 
      " onclick=\"this.src='../../verifyCode?code=4&var=rand&width=162&height=55&random='+ Math.random()" + 
      ";\" style='cursor:pointer'  width='162'  height='55' />");
    model.addAttribute("papersNumber", "");
    model.addAttribute("isauth", "");
    model.addAttribute("authState", "");
    model.addAttribute("random", random);
    if ((appmark != null) && (!appmark.equals(""))) {
      model.addAttribute("appmark", appmark);
    }
    return modelAndView;
  }

 /* @RequestMapping({"checkWhetherTelnumExist"})
  @ResponseBody
  public JsonResult checkWhetherTelnumExist(String telNum)
  {
    JsonResult jsonRe = JsonResult.getInstance();
    Integer count = this.OutsideUserService.findNumSameMobile(telNum, Integer.valueOf(-1));
    if (count.intValue() > 0)
      jsonRe.setCode("1");
    else {
      jsonRe.setCode("2");
    }
    return jsonRe;
  }*/

  /**
   * 校验RandomAndPhoneMessage
   */
  @RequestMapping({"checkRandomAndPhoneMessage"})
  @ResponseBody
  public JsonResult checkRandomAndPhoneMessage(String randCode, ComplatOutsideuser outsideUserFormBean, HttpSession session, String cellphoneShortMessageRandomCodeWritenByGuest, String appmark,HttpServletRequest request)
  {
    JsonResult jsonResult = JsonResult.getInstance();

    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }

    if ((StringUtil.isEmpty(outsideUserFormBean.getLoginName())) || (StringUtil.isEmpty(outsideUserFormBean.getPwd()))) {
      jsonResult.setMessage("用户名或密码为空");
      jsonResult.setSuccess(false);
      return jsonResult;
    }
    
    //String loginName = SecurityUtil.RSAdecode(outsideUserFormBean.getLoginName());
    String loginName = outsideUserFormBean.getLoginName();
    String pwd = dePwd(outsideUserFormBean.getPwd(),request);
    if ((StringUtil.isEmpty(loginName)) || (StringUtil.isEmpty(pwd))) {
      jsonResult.setMessage("用户名或密码为空");
      jsonResult.setSuccess(false);
      return jsonResult;
    }
    outsideUserFormBean.setLoginName(loginName);
    outsideUserFormBean.setPwd(pwd);

    String rand = (String)session.getAttribute("rand");

    if ((StringUtil.isEmpty(rand)) || (StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
      session.setAttribute("rand", null);
      jsonResult.setMessage("验证码错误！");
      jsonResult.setSuccess(false);
      return jsonResult;
    }

   /* String cellphoneShortMessageRandomCodeMadeByJava = (String)session.getAttribute("cellphoneShortMessageRandomCodeMadeByJava");

    if (!StringUtil.isEmpty(cellphoneShortMessageRandomCodeWritenByGuest)) {
      if (cellphoneShortMessageRandomCodeWritenByGuest.equals(cellphoneShortMessageRandomCodeMadeByJava)) {
        session.setAttribute("appmark", appmark);
        outsideUserFormBean.setIsCellphoneVerified(Integer.valueOf(1));
        session.setAttribute("outsideUserFormBean", outsideUserFormBean);
        jsonResult.setSuccess(true);
        return jsonResult;
      }
      session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", "-1");
      jsonResult.setMessage("手机短信验证码错误，请点击重新获得！");
      jsonResult.setSuccess(false);
      return jsonResult;
    }

    session.setAttribute("appmark", appmark);
    jsonResult.setSuccess(false);
    jsonResult.setMessage("请输入手机短信验证码！");
    return jsonResult;*/
    session.setAttribute("appmark", appmark);
    outsideUserFormBean.setIsCellphoneVerified(Integer.valueOf(1));
    session.setAttribute("outsideUserFormBean", outsideUserFormBean);
    jsonResult.setSuccess(true);
    return jsonResult;
  }

  @SuppressWarnings("unused")
  private String dePwd(String password,HttpServletRequest request){
	  String en_password = "";
	    try{
	        byte[] en_result = hexStringToBytes(password);  
	        byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(request).getPrivate(), en_result);  
	        StringBuffer sb = new StringBuffer();  
	        sb.append(new String(de_result));  
	        en_password= sb.reverse().toString(); 
	    } catch(Exception ex){
	  	  logger.error(ex.getMessage());
	    }
	    return en_password;
  }
  
  /**
   * 16进制 To byte[]
   * @param hexString
   * @return byte[]
   */
  public static byte[] hexStringToBytes(String hexString) {
      if (hexString == null || hexString.equals("")) {
          return null;
      }
      hexString = hexString.toUpperCase();
      int length = hexString.length() / 2;
      char[] hexChars = hexString.toCharArray();
      byte[] d = new byte[length];
      for (int i = 0; i < length; i++) {
          int pos = i * 2;
          d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
      }
      return d;
  }
  
  /**
   * Convert char to byte
   * @param c char
   * @return byte
   */
   private static byte charToByte(char c) {
      return (byte) "0123456789ABCDEF".indexOf(c);
  }
  /**
   * 发送短信验证码
   * @param session
   * @param telNum
   * @return
   */
  @RequestMapping({"sendCellphoneShortMessageUserRe"})
  @ResponseBody
  public String sendCellphoneShortMessageUserRe(HttpSession session, String telNum)
  {
    Map<String,String> map1 = new HashMap<String,String>();
    Object mobileSend = session.getAttribute("permobilesend");
    if ((StringUtil.isEmpty(telNum)) || (mobileSend == null)) {
      map1.put("message", "请刷新页面，重新注册！");
      map1.put("success", "false");
      map1.put("code", "0");
      return JsonUtil.objectToString(map1);
    }

    Object currentTimes = session.getAttribute("permobiletimes" + telNum);
    if (currentTimes != null) {
      int times = NumberUtil.getInt(currentTimes);
      if (times > 0) {
        map1.put("message", "超过最大短信发送次数");
        map1.put("success", "false");
        map1.put("code", "0");
        return JsonUtil.objectToString(map1);
      }
    }

    String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil.getRandomNumber(6);
    session.setAttribute("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);

    String content = JisSettings.getSettings().getRegistPerMessageContent().trim()
      .replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);

    String appBusinessId = JisSettings.getSettings().getBusinessIdForRegestingPer();
    String appBusinessName = JisSettings.getSettings().getBusinessNameForRegestingPer();
    int loseTime = 60;
    session.setAttribute("telNum", telNum);
    CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
    String resultJson = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, loseTime);
    Map map = (Map)JsonUtil.StringToObject(resultJson, Map.class);
    if ("true".equals(map.get("success")))
    {
      if (currentTimes == null) {
        session.setAttribute("permobiletimes" + telNum, Integer.valueOf(1));
      } else {
        int times = NumberUtil.getInt(currentTimes);
        session.setAttribute("permobiletimes" + telNum, Integer.valueOf(times + 1));
      }
    }

    return resultJson;
  }

  
  /**
   * 个人注册功能
   * @param session
   * @return
   */
  @RequestMapping({"doperregister"})
  @ResponseBody
  public JsonResult PersonalRegister(HttpSession session)
  {
    if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
      return null;
    }
    JsonResult jsonResult = JsonResult.getInstance();

    ComplatOutsideuser outsideUserFormBean = (ComplatOutsideuser)session.getAttribute("outsideUserFormBean");

    /*if (!outsideUserFormBean.getMobile().equals(session.getAttribute("telNum"))) {
      jsonResult.setMessage("手机验证码错误，请重新获取！");
      jsonResult.setSuccess(false);
      return jsonResult;
    }*/

    String appmark = (String)session.getAttribute("appmark");

    session.setAttribute("loginuser", outsideUserFormBean.getLoginName());

    session.setAttribute("loginpass", outsideUserFormBean.getPwd());

    //jsonResult = this.perRealNameAuthService.verifyPerRealName(outsideUserFormBean);

    if (true) {
    //if (jsonResult.isSuccess()) {
      boolean isSuccess = false;
      try
      {
        outsideUserFormBean.setMobile(outsideUserFormBean.getMobile().substring(0, 11));

        isSuccess = this.jisOutSideUserService.addOutUserForReg(outsideUserFormBean, appmark);
        if (isSuccess) {
          session.removeAttribute("outsideUserFormBean");
          //System.out.println(ResultState.ADD_SUCCESS);
          //jsonResult.set(ResultState.ADD_SUCCESS);
          jsonResult.setSuccess(true);
          PersonalSessionInfo.setCurrentPersonalInfo(outsideUserFormBean);
          session.setAttribute("outsideUserFormBean", outsideUserFormBean);
        } else {
          jsonResult.set(ResultState.ADD_FAIL);
          jsonResult.setSuccess(false);
          jsonResult.setMessage("注册失败！");
        }
      } catch (Exception e) {
        jsonResult.setSuccess(false);
        jsonResult.setMessage("注册失败：" + e.getMessage());
      }
    }

    return jsonResult;
  }

  @RequestMapping({"perregsuccess_b"})
  public ModelAndView perRegSuccess(HttpServletRequest request, HttpSession session,Model model)
  {
    ModelAndView modelAndView = new ModelAndView("jis/front/perregsuccess");
    model.addAttribute("loginurl", "../../front/perlogin.do?appmark=gszw");
    model.addAttribute("indexurl", "../../login");
    return modelAndView;
  }

  /**大汉未使用*/
  /*@RequestMapping({"perrealnameauth"})
  @ResponseBody
  public JsonResult realNameAuthCheck(OutsideUserFormBean outsideUserFormBean, HttpSession session, String name, String papersNumber)
  {
    JsonResult jsonResult = JsonResult.getInstance();
    int isRealNameAuth = NumberUtil.getInt(Settings.getSettings()
      .getRealNameAuth());
    if (isRealNameAuth == 1)
    {
      String result = this.realNameAuthService.verifyRealname(name, papersNumber);
      if ("TRUE".equals(result)) {
        outsideUserFormBean.setIsauth(Integer.valueOf(1));
        outsideUserFormBean.setAuthState(Integer.valueOf(1));
        jsonResult.setSuccess(true);
        jsonResult.setMessage("2");
      } else if ("FALSE".equals(result)) {
        outsideUserFormBean.setIsauth(Integer.valueOf(1));
        outsideUserFormBean.setAuthState(Integer.valueOf(2));
        jsonResult.setSuccess(false);
        jsonResult.setMessage("3");
      } else if ("4".equals(result)) {
        outsideUserFormBean.setIsauth(Integer.valueOf(1));
        outsideUserFormBean.setAuthState(Integer.valueOf(0));
        jsonResult.setSuccess(false);
        jsonResult.setMessage("4");
      }
    } else if (isRealNameAuth == 0) {
      outsideUserFormBean.setIsauth(Integer.valueOf(0));
      outsideUserFormBean.setAuthState(Integer.valueOf(0));
      jsonResult.setSuccess(false);
      jsonResult.setMessage("1");
    }

    return jsonResult;
  }

  @RequestMapping({"checkperloginid"})
  @ResponseBody
  public String checkPerLoginId(String loginid)
  {
    if (StringUtil.isEmpty(loginid)) {
      return "";
    }
    OutsideUser outUser = null;
    outUser = (OutsideUser)CacheUtil.getValue(loginid, "perusers");
    if (outUser == null)
    {
      outUser = this.OutsideUserService.findByLoginName(loginid);
    }
    if (outUser != null)
    {
      return "存在相同的用户名";
    }
    return "";
  }

  @RequestMapping({"perauth_success"})
  public ModelAndView perAtuhSuccess(HttpServletRequest request, HttpSession session)
  {
    ModelAndView modelAndView = new ModelAndView("jis/front/authsuccess");
    String html = "authsuccess.html";

    String templateHtml = this.templateService.readFrontTemplate(html);

    templateHtml = templateHtml.replace("${url}", 
      "../perlogin.do?appmark=gszw");
    modelAndView.addObject("templatehtml", templateHtml);
    return modelAndView;
  }*/

  /**大汉未使用*/
 /* @RequestMapping({"perauth_show"})
  public ModelAndView perAuthShow(HttpServletRequest request, HttpSession session,Model model)
  {
    ModelAndView modelAndView = new ModelAndView("jis/front/perauth_show");
    ComplatOutsideuser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    if (outuser == null)
    {
      RedirectView redirectView = new RedirectView("../perlogin.do");
      modelAndView.setView(redirectView);
      return modelAndView;
    }
    int isupload = outuser.getIsUpload().intValue();
    String html = "";
    if (isupload == 0)
      html = "perauth.html";
    else {
      html = "perauthstate.html";
    }
    
    String contextPath = BaseInfo.getContextPath();
    String headPicPath = contextPath + outuser.getHeadRenamePic();
    String bodyPicPath = contextPath + outuser.getBodyRenamePic();

    model.addAttribute("url","perauth_submit");
    model.addAttribute("rejectReason",outuser.getRejectReason() == null ? "" : outuser.getRejectReason());
    model.addAttribute("name",outuser.getName() == null ? "" : outuser.getName());
    model.addAttribute("authstate",String.valueOf(outuser.getAuthState()));
    model.addAttribute("isupload",String.valueOf(outuser.getAuthState()));
    model.addAttribute("headPicPath",headPicPath);
    model.addAttribute("bodyPicPath",bodyPicPath);
    model.addAttribute("papersNumber",outuser.getPapersNumber() == null ? "" : outuser.getPapersNumber());
    return modelAndView;
  }*/

  /**大汉未使用*/
  /*@RequestMapping({"perauth_submit"})
  @ResponseBody
  public String perAuthSubmit(String name, String papersNumber, MultipartFile headpic, MultipartFile bodypic) {
    Script script = Script.getInstanceWithJsLib();
    ComplatOutsideuser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
    outuser.setName(name);
    outuser.setPapersNumber(papersNumber);

    String message = "";

    MultipartFileInfo headpicinfo = MultipartFileInfo.getInstance(headpic);
    MultipartFileInfo bodypicinfo = MultipartFileInfo.getInstance(bodypic);
    String perimgpath = BaseInfo.getRealPath() + "/files/per/images/" + 
      outuser.getIid() + "/";
    File perFile = new File(perimgpath);
    if (!perFile.exists())
      perFile.mkdir();
    try
    {
      if (headpicinfo != null) {
        if (headpicinfo.getSize() > 5242880L) {
          throw new OperationException( "手持身份证头部照不能大于5M");
        }
        String filetype = headpicinfo.getFileType();
        if ((filetype.equals("jpg")) || (filetype.equals("jpeg")) || 
          (filetype.equals("bmp"))) {
          String path = perimgpath + headpicinfo.getFileName() + "." + 
            headpicinfo.getFileType();
          String path2 = perimgpath + papersNumber + "_head." + 
            headpicinfo.getFileType();
          File headfile = new File(path2);
          outuser.setHeadPic("/files/per/images/" + outuser.getIid() + 
            "/" + headpicinfo.getFileName() + "." + 
            headpicinfo.getFileType());
          outuser.setHeadRenamePic("/files/per/images/" + 
            outuser.getIid() + "/" + papersNumber + "_head." + 
            headpicinfo.getFileType());
          ControllerUtil.writeMultipartFileToFile(headfile, headpic);
        }
        else
        {
          throw new OperationException("手持身份证头部照类型不正确");
        }
      }
      if (bodypicinfo != null) {
        if (bodypicinfo.getSize() > 5242880L) {
          throw new OperationException("身份证正面照不能大于5M");
        }
        String filetype = bodypicinfo.getFileType();
        if ((filetype.equals("jpg")) || (filetype.equals("jpeg")) || 
          (filetype.equals("bmp"))) {
          String path = perimgpath + bodypicinfo.getFileName() + "." + 
            bodypicinfo.getFileType();
          String path2 = perimgpath + papersNumber + "_body." + 
            bodypicinfo.getFileType();
          File bodyfile = new File(path2);
          outuser.setBodyPic("/files/per/images/" + outuser.getIid() + 
            "/" + bodypicinfo.getFileName() + "." + 
            bodypicinfo.getFileType());
          outuser.setBodyRenamePic("/files/per/images/" + 
            outuser.getIid() + "/" + papersNumber + "_body." + 
            bodypicinfo.getFileType());
          ControllerUtil.writeMultipartFileToFile(bodyfile, bodypic);
        }
        else
        {
          throw new OperationException("身份证正面照类型不正确");
        }
      }
      outuser.setIsUpload(Integer.valueOf(1));
      Boolean isSuccess = Boolean.valueOf(this.OutsideUserService.modifyAuthing(outuser));
      if (isSuccess.booleanValue())
        message = "success";
    }
    catch (OperationException e) {
      message = e.getMessage();
      if ("身份证号已存在,请重新设置！".equals(message)) {
        File headfile = new File(perimgpath + papersNumber + "_head." + 
          headpicinfo.getFileType());
        File bodyfile = new File(perimgpath + papersNumber + "_body." + 
          bodypicinfo.getFileType());
        headfile.delete();
        bodyfile.delete();
      }
    }
    if (!"success".equals(message)) {
      script.addAlert(message);
      return script.getScript();
    }
    script.addScript("parent.auth", new Object[] { message });
    return script.getScript();
  }*/
  
}