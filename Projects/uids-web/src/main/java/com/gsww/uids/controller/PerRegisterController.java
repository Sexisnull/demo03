package com.gsww.uids.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.gsww.jup.util.CellphoneShortMessageUtil;
import com.gsww.jup.util.RSAUtil;
import com.gsww.jup.util.RandomCodeUtil;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.service.front.PerRealNameAuthService;
import com.gsww.uids.util.AccessUtil;
import com.gsww.uids.util.JsonResult;
import com.gsww.uids.util.MD5;
import com.gsww.uids.util.ResultState;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;

@Controller
@RequestMapping({ "front/register" })
public class PerRegisterController {
	private static Logger logger = LoggerFactory.getLogger(ComplatRoleController.class);

	@Autowired
	private ComplatOutsideuserService OutsideUserService;

	@Autowired
	private PerRealNameAuthService perRealNameAuthService;

	@Autowired
	private JisLogService logService;

	/**
	 * 个人注册页面
	 */
	@RequestMapping({ "perregister" })
	public ModelAndView perRegister_Step1(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String appmark = request.getParameter("appmark");
		if ((appmark != null) && (!appmark.equals(""))) {
			session.setAttribute("appmark", appmark);
		}

		session.setMaxInactiveInterval(1800);
		session.setAttribute("permobilesend", RandomCodeUtil.getRandomNumber(9));

		model.addAttribute("url", "checkRandomAndPhoneMessage");
		model.addAttribute("verifycodeimg", "<img id='verifyImg' src='../../verifyCode?code=4&var=rand&width=162&height=55&random=" + (int) (Math.random() * 100000000.0D) + "'"
				+ " onclick=\"this.src='../../verifyCode?code=4&var=rand&width=162&height=55&random='+ Math.random()" + ";\" style='cursor:pointer'  width='162'  height='55' />");
		model.addAttribute("papersNumber", "");
		model.addAttribute("isauth", "");
		model.addAttribute("authState", "");
		if ((appmark != null) && (!appmark.equals(""))) {
			model.addAttribute("appmark", appmark);
		}

		ModelAndView modelAndView = new ModelAndView("jis/front/perregister_step1");
		return modelAndView;
	}
	
	  /**校验电话号码是否重复*/
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
	@RequestMapping({ "checkRandomAndPhoneMessage" })
	@ResponseBody
	public JsonResult checkRandomAndPhoneMessage(String randCode, ComplatOutsideuser outsideUserFormBean, HttpSession session, String cellphoneShortMessageRandomCodeWritenByGuest, String appmark,
			HttpServletRequest request) {
		JsonResult jsonResult = JsonResult.getInstance();

		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}

		if ((StringUtil.isEmpty(outsideUserFormBean.getLoginName())) || (StringUtil.isEmpty(outsideUserFormBean.getPwd()))) {
			jsonResult.setMessage("用户名或密码为空");
			jsonResult.setSuccess(false);
			return jsonResult;
		}

		String loginName = outsideUserFormBean.getLoginName();
		String pwd = dePwd(outsideUserFormBean.getPwd(), request);
		if ((StringUtil.isEmpty(loginName)) || (StringUtil.isEmpty(pwd))) {
			jsonResult.setMessage("用户名或密码为空");
			jsonResult.setSuccess(false);
			return jsonResult;
		}
		outsideUserFormBean.setLoginName(loginName);
		outsideUserFormBean.setPwd(pwd);

		String rand = (String) session.getAttribute("rand");

		if ((StringUtil.isEmpty(rand)) || (StringUtil.isEmpty(randCode)) || (!rand.equalsIgnoreCase(randCode))) {
			session.setAttribute("rand", null);
			jsonResult.setMessage("验证码错误！");
			jsonResult.setSuccess(false);
			return jsonResult;
		}

		//TODO 注册验证短信验证码
		
		/*String cellphoneShortMessageRandomCodeMadeByJava = (String)session.getAttribute("cellphoneShortMessageRandomCodeMadeByJava");
		
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

	/**解密*/
	@SuppressWarnings("unused")
	private String dePwd(String password, HttpServletRequest request) {
		String en_password = "";
		try {
			byte[] en_result = hexStringToBytes(password);
			byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(request).getPrivate(), en_result);
			StringBuffer sb = new StringBuffer();
			sb.append(new String(de_result));
			en_password = sb.reverse().toString();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return en_password;
	}

	/**
	 * 16进制 To byte[]
	 * 
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
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param session
	 * @param telNum
	 * @return
	 */
	@RequestMapping({ "sendCellphoneShortMessageUserRe" })
	@ResponseBody
	public String sendCellphoneShortMessageUserRe(HttpSession session, String telNum) {
		Map<String, String> map1 = new HashMap<String, String>();
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

		String content = JisSettings.getSettings().getRegistPerMessageContent().trim().replace("cellphoneShortMessageRandomCodeMadeByJava", cellphoneShortMessageRandomCodeMadeByJava);

		String appBusinessId = JisSettings.getSettings().getBusinessIdForRegestingPer();
		String appBusinessName = JisSettings.getSettings().getBusinessNameForRegestingPer();
		int loseTime = 60;
		session.setAttribute("telNum", telNum);
		CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
		String resultJson = cellMesUtil.sendPhoneShortMessage(telNum, content, appBusinessId, appBusinessName, loseTime);
		Map map = (Map) JsonUtil.StringToObject(resultJson, Map.class);
		if ("true".equals(map.get("success"))) {
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
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping({ "doperregister" })
	@ResponseBody
	public JsonResult PersonalRegister(HttpSession session) {
		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}
		JsonResult jsonResult = JsonResult.getInstance();

		ComplatOutsideuser outsideUserFormBean = (ComplatOutsideuser) session.getAttribute("outsideUserFormBean");

		/*if (!outsideUserFormBean.getMobile().equals(session.getAttribute("telNum"))) {
			jsonResult.setMessage("手机验证码错误，请重新获取！");
			jsonResult.setSuccess(false);
			return jsonResult;
		}*/

		String appmark = (String) session.getAttribute("appmark");

		session.setAttribute("loginuser", outsideUserFormBean.getLoginName());

		session.setAttribute("loginpass", outsideUserFormBean.getPwd());

		// jsonResult = this.perRealNameAuthService.verifyPerRealName(outsideUserFormBean);

		if (true) {
			// if (jsonResult.isSuccess()) {
			boolean isSuccess = false;
			try {
				outsideUserFormBean.setMobile(outsideUserFormBean.getMobile().substring(0, 11));

				isSuccess = addOutUserForReg(outsideUserFormBean);
				if (isSuccess) {
					session.removeAttribute("outsideUserFormBean");
					// System.out.println(ResultState.ADD_SUCCESS);
					jsonResult.set(ResultState.ADD_SUCCESS);
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

	//插入个人用户表
	public boolean addOutUserForReg(ComplatOutsideuser outsideUser) throws Exception {
		if (outsideUser == null) {
			return false;
		}
		boolean isSuccess = false;
		String loginName = outsideUser.getLoginName();
		outsideUser.setPwd(Md5Util.md5encode(outsideUser.getPwd()));
		
		isSuccess = this.OutsideUserService.insert(outsideUser);
		
		if (!isSuccess) {
			throw new Exception("插入平台表失败！");
		}
		ComplatOutsideuser complatOutsideuser = this.OutsideUserService.findByLoginName(loginName);

		//记录日志
		this.logService.add(Integer.valueOf(1), Integer.valueOf(10), loginName);
		return isSuccess;
	}

	
	/**
	 * 注册成功
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping({ "perregsuccess_b" })
	public ModelAndView perRegSuccess(HttpServletRequest request, HttpSession session, Model model) {
		ModelAndView modelAndView = new ModelAndView("jis/front/perregsuccess");
		model.addAttribute("loginurl", "../../front/perlogin?appmark=gszw");
		model.addAttribute("indexurl", "../../login");
		return modelAndView;
	}

	@RequestMapping(value = "userresult")
	public void userResult(HttpServletRequest request,
			HttpServletResponse response, String webId, String domain,
			String callback) {

		String html = callback
				+ "({\"html\":\"<table border='0' cellpadding='0' cellspacing='0' >"
				+ "<tr><td  id='loginbtn' onmouseout='hidelogin();'  onmouseover='showlogin();'>"
				+ "<a  style='cursor:pointer; font-size:13px; text-align:center;margin:0px;' >登录</a>"
				+ "<td style='text-align:center;width:20px;' >|</td></td>"
				+ "<td  onmouseover='showregedit();' onmouseout='hideregedit();' align='center' id='regeditbtn'>"
				+ "<a style='cursor:pointer;text-decoration:none; font-size:13px; margin:0; '>注册</a></td></tr></table>"
				+ "<script>var grloginurl='http://"
				+ domain
				+ ":8080/gsjis/front/per/interface.do?action=ticketLogin&appmark=gszw&domain="
				+ domain
				+ ":8080&gotoUrl=';"
				+ "var grregediturl='http://"
				+ domain
				+ ":8080/uids-web/front/register/perregister';"
				+ "var frloginurl='http://"
				+ domain
				+ ":8080/gsjis/front/cor/interface.do?action=ticketLogin&appmark=gszw&domain="
				+ domain
				+ ":8080&gotoUrl=';"
				+ "var frregediturl='http://"
				+ domain
				+ ":8080/uids-web/front/register/corregister';"
				+ "var grinfo='http://"
				+ domain
				+ ":8080/gsjis/front/modifyperinfo_show.do';"
				+ "var frinfo='http://"
				+ domain
				+ ":8080/gsjis/front/modifycorinfo_show.do';</script>﻿"
				+ "<style>.login_li{color:#000;}  .selected{  background:#999999;  color:#FFF;  }    </style>"
				+ "<script>  function showuserinfo(){   $('#userinfomenu').show();  }    "
				+ "function hideuserinfo(){   $('#userinfomenu').hide();  }    "
				+ "function showlogin(){   $('#loginmenu').show();    }    "
				+ "function hidelogin(){   $('#loginmenu').hide();  }    "
				+ "function showregedit(){   $('#regeditmenu').show();  }    "
				+ "function hideregedit(){   $('#regeditmenu').hide();  }  "
				+ "var domain='"
				+ domain
				+ ":8080'; "
				+ "function showgrzx(){"
				+ "window.open('http://'+domain+'/gszw/member/main/index.do?webid='+webid+'&domain='+encodeURIComponent(encodeURIComponent(domain)));  }"
				+ "function showzhsz(){   if(type=='gr' || type=='个人'||type=='1'){   window.open(grinfo);}"
				+ "else if(type=='fr'||type=='法人'||type=='2'){window.open(frinfo);}}"
				+ "function exit(){var src=window.location.href;src=encodeURIComponent(src);"
				+ "var rand=Math.random();   if(type=='gr' || type=='个人'||type=='1'){"
				+ "location.href='http://'+domain+'/gszw/member/login/logout.do?domain='+encodeURIComponent(encodeURIComponent(domain));}"
				+ "else if(type=='fr'||type=='法人'||type=='2'){location.href='http://'+domain+'/gszw/member/login/logout.do?domain='+encodeURIComponent(encodeURIComponent(domain));}}"
				+ "function showgrlogin(){var src=window.location.href;src=encodeURIComponent(src);"
				+ "grloginurl=encodeURIComponent(grloginurl);   "
				+ "location.href='http://'+domain+'/uids-web/front/perlogin?action=ticketLogin&gotoUrl=&appmark=gszw&domain=www.gszwfw.gov.cn';}"
				+ " function showfrlogin(){var src=window.location.href;src=encodeURIComponent(src);   "
				+ "frloginurl=encodeURIComponent(frloginurl);   "
				+ "location.href='http://'+domain+'/uids-web/front/corlogin?action=ticketLogin&gotoUrl=&appmark=gszw&domain=www.gszwfw.gov.cn';  }"
				+ "function showgrregedit(){"
				+ "window.open(grregediturl);  }function showfrregedit(){window.open(frregediturl);}"
				+ "$(function(){var $div_li =$('div.userinfomenu ul li');"
				+ "$div_li.mouseover(function(){$(this).addClass('selected').siblings().removeClass('selected');});"
				+ "$div_li.mouseout(function(){$(this).removeClass('selected');});var $login_li =$('div.loginmenu ul li');"
				+ "$login_li.mouseover(function(){$(this).addClass('selected').siblings().removeClass('selected');});    "
				+ "$login_li.mouseout(function(){$(this).removeClass('selected');});"
				+ "var $regedit_li =$('div.regeditmenu ul li'); $regedit_li.mouseover(function(){$(this).addClass('selected').siblings().removeClass('selected'); }); "
				+ "$regedit_li.mouseout(function(){$(this).removeClass('selected');});"
				+ "$('#userinfomenu').mouseover(function(){$('#userinfomenu').show();});"
				+ " $('#userinfomenu').bind('mouseleave',function(){$('#userinfomenu').hide();});"
				+ "$('#loginmenu').mouseover(function(){$('#loginmenu').show();});"
				+ "$('#loginmenu').bind('mouseleave',function(){$('#loginmenu').hide();}); "
				+ "$('#regeditmenu').mouseover(function(){ $('#regeditmenu').show(); });"
				+ "$('#regeditmenu').bind('mouseleave',function(){$('#regeditmenu').hide();});});</script>"
				+ "<div id='userinfomenu' class='userinfomenu' style='position: absolute;z-index:999;right:-15px;;top:25px;display:none;background:url(http://www.gszwfw.gov.cn/gszw/resources/bscx/member/images/loginmenubg2.png) no-repeat;width:80px;height:65px;text-align:center;'>"
				+ "<ul style='list-style:none;height:53px;width:100%;padding:0px;margin:0px;margin-top:8px;text-align:center;'>"
				+ "<li class='login_li' style='width:98%;height:27px;line-height:27px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto;' onclick='showzhsz();'>账户设置</li> "
				+ " <li class='login_li' style='width:98%;height:26px;line-height:26px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto; '  onclick='exit();'>退出</li>  </ul>  "
				+ "</div>  <div id='loginmenu' class='loginmenu' style='position: absolute;z-index:999;left:-27px;top:25px;display:none;background:url(http://www.gszwfw.gov.cn/gszw/resources/bscx/member/images/loginmenubg2.png) no-repeat;width:80px;height:65px;text-align:center;'>  "
				+ "<ul style='list-style:none;height:100%;width:100%;padding:0px;margin:0px;margin-top:8px;text-align:center;'>  "
				+ "<li class='login_li' style='width:98%;height:27px;line-height:27px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto; '  onclick='showgrlogin();'>个人登录</li> "
				+ " <li class='login_li' style='width:98%;height:28px;line-height:28px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto; '  onclick='showfrlogin();'>法人登录</li>  </ul> </div> "
				+ " <div id='regeditmenu' class='regeditmenu' style='position: absolute;z-index:999;left:19px;top:25px;display:none;background:url(http://www.gszwfw.gov.cn/gszw/resources/bscx/member/images/loginmenubg2.png) no-repeat;width:80px;height:65px;text-align:center;'>  "
				+ "<ul style='list-style:none;height:100%;width:100%;padding:0px;margin:0px;margin-top:8px;text-align:center;'>  "
				+ "<li class='login_li' style='width:98%;height:27px;line-height:27px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto; '  onclick='showgrregedit();'>个人注册</li> "
				+ " <li class='login_li' style='width:98%;height:28px;line-height:28px;text-align:center;v-align:middle;cursor: pointer;margin:0 auto; '  onclick='showfrregedit();'>法人注册</li>  </ul>  </div>\"});";
		try {
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(html);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
	
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
	      "../perlogin?appmark=gszw");
	    modelAndView.addObject("templatehtml", templateHtml);
	    return modelAndView;
	  }*/

	  
	 /* @RequestMapping({"perauth_show"})
	  public ModelAndView perAuthShow(HttpServletRequest request, HttpSession session,Model model)
	  {
	    ModelAndView modelAndView = new ModelAndView("jis/front/perauth_show");
	    ComplatOutsideuser outuser = PersonalSessionInfo.getFrontCurrentPersonalInfo();
	    if (outuser == null)
	    {
	      RedirectView redirectView = new RedirectView("../perlogin");
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