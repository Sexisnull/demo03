package com.gsww.uids.controller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.gsww.uids.util.JsonResult;
import com.gsww.uids.util.ResultState;
import com.hanweb.complat.exception.LoginException;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.AuthLogService;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.util.AccessUtil;
import com.gsww.jup.util.CellphoneShortMessageUtil;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.RSAUtil;
import com.gsww.jup.util.RandomCodeUtil;
import com.gsww.jup.util.SafeUtil;
import com.gsww.jup.util.UserUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/front")
public class PerLoginController {
	private static Logger logger = LoggerFactory
			.getLogger(ComplatRoleController.class);

	@Autowired
	private ComplatCorporationService corporationService;

	@Autowired
	private JisApplicationService appService;

	@Autowired
	private ComplatOutsideuserService OutsideUserService;

	@Autowired
	private AuthLogService authLogService;

	@Autowired
	private JisSettings jisSettings;

	@RequestMapping(value = "/perlogin.do")
	public String personalLogin(HttpServletResponse response, String action,
			String appmark, String gotoUrl, String domain, Model model) {
		if (action == null) {
			action = "";
		}
		if (appmark == null) {
			appmark = "";
		}
		if ((SafeUtil.isSqlAndXss(action)) || (SafeUtil.isSqlAndXss(appmark))
				|| (SafeUtil.isSqlAndXss(gotoUrl))
				|| (SafeUtil.isSqlAndXss(domain))) {
			return null;
		}
		HttpSession session = SpringUtil.getRequest().getSession();
		if (StringUtil.isEmpty(gotoUrl)) {
			gotoUrl = (String) session.getAttribute("gotoUrl");
			if (gotoUrl == null)
				gotoUrl = "";
		} else {
			session.setAttribute("gotoUrl", gotoUrl);
		}
		if (StringUtil.isEmpty(domain)) {
			domain = (String) session.getAttribute("domain");
			if (domain == null)
				domain = "";
		} else {
			session.setAttribute("domain", domain);
		}

		if ((SafeUtil.isSqlAndXss(gotoUrl)) || (SafeUtil.isSqlAndXss(action))
				|| (SafeUtil.isSqlAndXss(appmark))) {
			gotoUrl = "";
			action = "";
			appmark = "gszw";
		}
		session.setAttribute("appmark", appmark);
		model.addAttribute("sysName", jisSettings.getSysName());
		model.addAttribute("url", "doperlogin");
		model.addAttribute(
				"verifycodeimg",
				"<img id='verifyImg' src='../verifyCode?code=4&var=rand&width=162&height=30&"
						+ "random="
						+ (int) (Math.random() * 100000000.0D)
						+ "'"
						+ " onclick=\"this.src='../verifyCode?code=4&"
						+ "var=rand&width=140&height=30&random='+ Math.random();\" style='cursor:pointer'  width='140' "
						+ "height='30' />");
		model.addAttribute("username",
				ControllerUtil.getCookieValue("frontuser"));
		model.addAttribute("action", action);
		model.addAttribute("appmark", appmark);
		model.addAttribute("gotoUrl", gotoUrl);

		return "jis/front/perlogin";
	}

	@POST
	@RequestMapping(value = "/doperlogin")
	@ResponseBody
	public JsonResult personalDoLogin(
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "randomVeryfyCode", required = false) String randomVeryfyCode,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "appmark", required = false) String appmark,
			@RequestParam(value = "gotoUrl", required = false) String gotoUrl,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}
		JsonResult jsonResult = JsonResult.getInstance();
		String ip = ControllerUtil.getIp();
		try {
			session.setAttribute("appmark", appmark);

			if ((String) session.getAttribute("rand") == null){
				logger.error("verifycode.error");
				throw new LoginException("verifycode.error");
			}
			if ((randomVeryfyCode == null) || ("".equals(randomVeryfyCode))
					|| (randomVeryfyCode.length() == 0)) {
				logger.error("verifycode.error");
				throw new LoginException("verifycode.error");
			}
			String rand = (String) session.getAttribute("rand");
			if (!rand.equalsIgnoreCase(randomVeryfyCode)) {
				logger.error("verifycode.error");
				throw new LoginException("verifycode.error");
			}

			if ((StringUtil.isEmpty(userName))
					|| (StringUtil.isEmpty(password))) {
				logger.error("login.error");
				throw new LoginException("login.error");
			}

			String en_password = "";
			try {
				byte[] en_result = hexStringToBytes(password);
				byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(request)
						.getPrivate(), en_result);
				StringBuffer sb = new StringBuffer();
				sb.append(new String(de_result));
				en_password = sb.reverse().toString();
				en_password = URLDecoder.decode(en_password, "utf-8");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				throw new LoginException("login.error");
			}
			password = en_password;

			int wayOfLogin = 0;
			ComplatOutsideuser outsideUser;
			if (UserUtil.isMobilelegal(userName)) {
				outsideUser = this.OutsideUserService.findByMobile(userName);
				wayOfLogin = 0;
			} else if (UserUtil.isIDnumberlegal(userName)) {
				outsideUser = this.OutsideUserService.findByIdCard(userName);
				wayOfLogin = 2;
			} else {
				outsideUser = this.OutsideUserService.findByLoginName(userName);
			}

			if (outsideUser == null) {
				logger.error("login.error");
				throw new LoginException("login.error");
			}
			userName = outsideUser.getLoginName();

			ComplatOutsideuser user = this.OutsideUserService.checkUserLogin(
					userName, password, ip);

			if ((user == null) && (wayOfLogin == 0)) {
				user = this.OutsideUserService.checkUserLogin(session,
						userName, password, ip);
			}

			if (user != null) {
				PersonalSessionInfo.setCurrentPersonalInfo(user);
				jsonResult.setSuccess(true);
				ControllerUtil.addCookie(response, "personalloginid", userName,
						604800);

				String gotoUrlFlag = "";
				if (StringUtil.isNotEmpty(appmark)) {
					System.out.println("====user" + user);

					String ticket = this.authLogService.add(user, appmark, 1);
					System.out.println("====tickt" + ticket);
					if (StringUtil.isEmpty(ticket)) {
						logger.error("票据生成失败");
						throw new LoginException("票据生成失败");
					}

					String domain = StringUtil.getString(session
							.getAttribute("domain"));

					gotoUrlFlag = jisSettings.getPerGotoUrl();
					if (StringUtil.isNotEmpty(domain)) {
						try {
							domain = URLDecoder.decode(domain, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");

						Matcher m = p.matcher(gotoUrlFlag);
						if (m.find()) {
							gotoUrlFlag = StringUtil.replace(gotoUrlFlag,
									m.group(), domain);
						}
						session.removeAttribute("domain");
					}
					jsonResult.addParam("ticket", ticket);
				}
				jsonResult.addParam("gotoUrlFlag", gotoUrlFlag);
				this.OutsideUserService.save(user);
			} else {
				logger.error("您正在进行个人用户登录，用户名或密码不正确");
				throw new LoginException("您正在进行个人用户登录，用户名或密码不正确");
			}
		} catch (Exception e) {
			Map<String,String> map = new HashMap<String, String>();

			session.setAttribute("rand",
					StringUtil.getUUIDString().substring(0, 4));
			if ("adminlogin.error".equals(e.getMessage())) {
				jsonResult.setSuccess(false);
				jsonResult.addParam("adminerror", "1");
			} else if ("verifycode.error".equals(e.getMessage())) {
				jsonResult.setMessage("验证码错误，请重新输入");
				jsonResult.setSuccess(false);
			} else {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("您正在进行个人用户登录，用户名或密码不正确!");
			}
		}
		System.out.println(jsonResult.toString());
		return jsonResult;
	}

	@RequestMapping(value = "/perindex")
	public String perIndex(HttpServletResponse response, Model model) {
		ComplatOutsideuser user = PersonalSessionInfo
				.getFrontCurrentPersonalInfo();
		String logouturl = "";
		String loginname = "";
		if (user != null) {
			logouturl = "perlogout";
			loginname = user.getLoginName();
		} else {
			return "jis/front/perlogin";
		}

		model.addAttribute("sysName", jisSettings.getSysName());
		model.addAttribute("logouturl", logouturl);
		model.addAttribute("loginname", loginname);
		model.addAttribute("copyRight", jisSettings.getCopyRight());
		model.addAttribute(
				"verifycodeimg",
				"<img id='verifyImg' src='../verifyCode?code=4&"
						+ "var=rand&width=162&height=55&random="
						+ (int) (Math.random() * 100000000.0D)
						+ "'onclick=\"this.src='../verifyCode?code=4&var=rand&width=162&height=55&"
						+ "random='+ Math.random();\" style='cursor:pointer'  width='162'  height='55' />");
		model.addAttribute("username",
				ControllerUtil.getCookieValue("frontuser"));

		return "jis/front/perindex";
	}

	@RequestMapping(value = "/perlogout")
	public ModelAndView perLogout(HttpSession session) {
		if (PersonalSessionInfo.getFrontCurrentPersonalInfo() != null) {
			session.removeAttribute("personalinfo");
		}
		String appmark = StringUtil.getString(session.getAttribute("appmark"));
		ModelAndView modelAndView = new ModelAndView();
		RedirectView redirectView = new RedirectView("perlogin.do");
		modelAndView.addObject("appmark", appmark);
		modelAndView.setView(redirectView);
		return modelAndView;
	}

	@RequestMapping(value = "/findperurl")
	@ResponseBody
	public JsonResult findPerLogoff() {
		JsonResult jsonResult = JsonResult.getInstance();
		String logoffurl = this.appService.findURLBylogoff(1);
		if (logoffurl.length() > 0) {
			jsonResult.set(ResultState.OPR_SUCCESS);
			jsonResult.setMessage(JsonUtil.objectToString(logoffurl));
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}

	@RequestMapping(value = "/sendDynamicPwd")
	@ResponseBody
	public String sendDynamicPwd(String telNum, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		if ((telNum == null) || ("".equals(telNum.trim()))) {
			map.put("success", "false");
			map.put("code", "0");
			map.put("msg", "手机号不能为空");
			return JsonUtil.objectToString(map);
		}
		if (!UserUtil.isMobilelegal(telNum)) {
			map.put("success", "false");
			map.put("code", "0");
			map.put("msg", "请输入正确的手机号");
			return JsonUtil.objectToString(map);
		}

		ComplatOutsideuser outsideUser = this.OutsideUserService
				.findByMobile(telNum);
		if (outsideUser == null) {
			map.put("success", "false");
			map.put("code", "0");
			map.put("msg", "无此帐号，请确认");
			return JsonUtil.objectToString(map);
		}

		String cellphoneDynamicPwdMadeByJava = RandomCodeUtil
				.getRandomNumber(6);
		session.setAttribute("cellphoneDynamicPwdMadeByJava",
				cellphoneDynamicPwdMadeByJava);

		int validityPeriodInt = Integer.parseInt(jisSettings
				.getValidityPeriod().trim());

		String content = jisSettings
				.getDynamicPpdMessageContent()
				.trim()
				.replace("cellphoneDynamicPwdMadeByJava",
						cellphoneDynamicPwdMadeByJava)
				.replace("validityPeriod", String.valueOf(validityPeriodInt));
		String appBusinessId = jisSettings.getBusinessIdForGettingDynamicPpd()
				.trim();
		String appBusinessName = jisSettings
				.getBusinessNameForGettingDynamicPpd().trim();
		int loseTime = 60;
		CellphoneShortMessageUtil cellMesUtil = new CellphoneShortMessageUtil();
		String jsonRe = cellMesUtil.sendPhoneShortMessage(telNum, content,
				appBusinessId, appBusinessName, loseTime);
		map = (Map) JsonUtil.StringToObject(jsonRe, Map.class);
		if (Integer.parseInt(map.get("code")) == 1) {
			session.setAttribute("timeSendDynamicPwd",
					Long.valueOf(System.currentTimeMillis()));
			int validityPeriod = Integer.parseInt(jisSettings
					.getValidityPeriod()) * 60;
			session.setMaxInactiveInterval(validityPeriod);
		}
		return jsonRe;
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

	@RequestMapping(value = "/pwdRecover_select")
	public String pwdRecover_selectForPer(HttpSession session,
			String typeEntity, Model model) {
		if ("per".equals(typeEntity))
			session.setAttribute("typeEntity", "per");
		else {
			session.setAttribute("typeEntity", "cor");
		}
		return "jis/front/pwdRecover_select";
	}

	@RequestMapping(value = "/recoverPwdByPhone_show")
	public String verifyByPhone(HttpSession session, Model model) {
		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}

		session.setAttribute("mobilesend", RandomCodeUtil.getRandomNumber(9));

		String typeEntity = (String) session.getAttribute("typeEntity");
		if (typeEntity == null) {
			return null;
		}
		String varicode = "<img id='verifyImg' src='../verifyCode.do?code=4&var=rand&width=162&height=30&random="
				+ (int) (Math.random() * 100000000.0D)
				+ "'"
				+ " onclick=\"this.src='../verifyCode.do?code=4&var=rand&width=140&height=30&random='+ Math.random()"
				+ ";\" style='cursor:pointer'  width='140'  height='30' />";
		model.addAttribute("url", "recoverPwdByPhone_submit");
		model.addAttribute("verifycodeimg", varicode);
		model.addAttribute("typeEntity", typeEntity);

		return "jis/front/phone_recoverPwd";
	}

	@RequestMapping(value = "/recoverPwdByEmail_show")
	public String verifyByEmail(HttpSession session, Model model) {
		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}

		model.addAttribute("url", "emailverify_submit");

		return "jis/front/email_verify";
	}

	@RequestMapping(value = "/emailverify_submit")
	@ResponseBody
	public JsonResult submitEmailverify(HttpSession session, String loginName,
			String emailcode) {
		if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
			return null;
		}
		if ((SafeUtil.isSqlAndXss(loginName))
				|| (SafeUtil.isSqlAndXss(emailcode))) {
			return null;
		}
		JsonResult jsonResult = JsonResult.getInstance();
		int type = ((Integer) session.getAttribute("type")).intValue();
		if (type == 1) {
			ComplatCorporation corporation = this.corporationService
					.findByLoginName(loginName);
			if (corporation == null) {
				jsonResult.setMessage("用户名不存在！");
				jsonResult.set(ResultState.OPR_FAIL);
				return jsonResult;
			}
			session.setAttribute("username", corporation.getLoginName());
		} else {
			ComplatOutsideuser outuser = this.OutsideUserService
					.findByLoginName(loginName);
			if (outuser == null) {
				jsonResult.setMessage("用户名不存在！");
				jsonResult.set(ResultState.OPR_FAIL);
				return jsonResult;
			}
			session.setAttribute("username", outuser.getLoginName());
		}

		if (StringUtil.isNotEmpty(emailcode)) {
			if (!StringUtil.equals(emailcode,
					StringUtil.getString(session.getAttribute("emailcode")))) {
				jsonResult.setMessage("邮箱验证码错误！");
				jsonResult.set(ResultState.OPR_FAIL);
				return jsonResult;
			}
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}

		return jsonResult;
	}

	@RequestMapping(value = "/checkPerWhetherInputByGuestExist")
	@ResponseBody
	public JsonResult checkWhetherInputByGuestExist(HttpSession session,
			String inputByGuest) {
		JsonResult jsonResult = JsonResult.getInstance();
		if (StringUtil.isEmpty(inputByGuest)) {
			jsonResult.setSuccess(false);
			return jsonResult;
		}
		ComplatOutsideuser outsideUser;
		if (UserUtil.isMobilelegal(inputByGuest)) {
			outsideUser = this.OutsideUserService.findByMobile(inputByGuest);
		} else {
			if (UserUtil.isIDnumberlegal(inputByGuest))
				outsideUser = this.OutsideUserService
						.findByIdCard(inputByGuest);
			else {
				outsideUser = this.OutsideUserService
						.findByLoginName(inputByGuest);
			}
		}
		if (outsideUser == null) {
			jsonResult.setSuccess(false);
		} else {
			session.setAttribute("outsideUser", outsideUser);
			jsonResult.setSuccess(true);
		}
		return jsonResult;
	}

	@RequestMapping(value = "/sendCellphoneShortMessageUserPwdRecover")
	@ResponseBody
	public String sendCellphoneShortMessageUserPwdRecover(HttpSession session,
			String inputByGuest, String randCode) {
		String resultJson = "";
		try {

			if (!AccessUtil.checkAccess(SpringUtil.getRequest())) {
				return null;
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			String typeEntity = (String) session.getAttribute("typeEntity");
			ComplatCorporation corporation = null;

			if (StringUtil.isEmpty(inputByGuest)) {
				map1.put("msg", "账号不能为空");
				map1.put("success", "false");
				map1.put("code", "4");
				return JsonUtil.objectToString(map1);
			}
			if (StringUtil.isEmpty(randCode)) {
				map1.put("msg", "验证码不能为空");
				map1.put("success", "false");
				map1.put("code", "6");
				return JsonUtil.objectToString(map1);
			}
			ComplatOutsideuser outsideUser = null;

			if ("per".equals(typeEntity)) {
				if (UserUtil.isMobilelegal(inputByGuest))
					outsideUser = this.OutsideUserService
							.findByMobile(inputByGuest);
				else if (UserUtil.isIDnumberlegal(inputByGuest)) {
					outsideUser = this.OutsideUserService
							.findByIdCard(inputByGuest);
				} else
					outsideUser = this.OutsideUserService
							.findByLoginName(inputByGuest);

				if (outsideUser == null) {
					map1.put("msg", "用户不存在");
					map1.put("success", "false");
					map1.put("code", "1");
					return JsonUtil.objectToString(map1);
				}

				String sessionCode = StringUtil.getString(session
						.getAttribute("rand"));
				if (!randCode.equalsIgnoreCase(sessionCode)) {
					map1.put("msg", "验证码不正确");
					map1.put("success", "false");
					map1.put("code", "5");
					return JsonUtil.objectToString(map1);
				}

				session.setAttribute("outsideUser", outsideUser);

				Object mobileSend = session.getAttribute("mobilesend");
				if (mobileSend == null) {
					map1.put("msg", "参数为空");
					map1.put("success", "false");
					map1.put("code", "0");
					return JsonUtil.objectToString(map1);
				}

				Object currentTimes = session.getAttribute("mobiletimes");
				if (currentTimes != null) {
					int times = NumberUtil.getInt(currentTimes);
					if (times > 10) {
						map1.put("msg", "超过最大短信发送次数");
						map1.put("success", "false");
						map1.put("code", "2");
						return JsonUtil.objectToString(map1);
					}

				}

				outsideUser = (ComplatOutsideuser) session
						.getAttribute("outsideUser");
				String phoneNumber = outsideUser.getMobile();

				String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil
						.getRandomNumber(6);
				session.setAttribute(
						"cellphoneShortMessageRandomCodeMadeByJava",
						cellphoneShortMessageRandomCodeMadeByJava);
				String content = JisSettings
						.getSettings()
						.getRecovingPpdContent()
						.trim()
						.replace("cellphoneShortMessageRandomCodeMadeByJava",
								cellphoneShortMessageRandomCodeMadeByJava);
				String appBusinessId = JisSettings.getSettings()
						.getBusinessIdForRecovingPpd().trim();
				String appBusinessName = JisSettings.getSettings()
						.getBusinessNameForRecovingPpd().trim();

				int loseTime = 60;
				// CellphoneShortMessageUtil cellMesUtil = new
				// CellphoneShortMessageUtil();
				resultJson = "{'success':'true'}";// cellMesUtil.sendPhoneShortMessage(phoneNumber,content,
													// appBusinessId,
													// appBusinessName,
													// loseTime);
				Map map = (Map) JsonUtil.StringToObject(resultJson, Map.class);
				if (StringUtil.getString(map.get("success")).equals("true")) {
					if (currentTimes == null) {
						session.setAttribute("mobiletimes", Integer.valueOf(1));
					} else {
						int times = NumberUtil.getInt(currentTimes);
						session.setAttribute("mobiletimes",
								Integer.valueOf(times + 1));
					}
					map1.put("success", "true");
					map1.put("code", "3");
					return JsonUtil.objectToString(map1);
				}

			} else {
				corporation = this.corporationService
						.findByManyWay(inputByGuest);
				if (corporation == null) {
					map1.put("msg", "用户不存在");
					map1.put("success", "false");
					map1.put("code", "1");
					return JsonUtil.objectToString(map1);
				}

				String sessionCode = StringUtil.getString(session
						.getAttribute("rand"));
				if (!randCode.equalsIgnoreCase(sessionCode)) {
					map1.put("msg", "验证码不正确");
					map1.put("success", "false");
					map1.put("code", "5");
					return JsonUtil.objectToString(map1);
				}

				session.setAttribute("corporation", corporation);

				Object currentTimes = session.getAttribute("mobiletimes");
				if (currentTimes != null) {
					int times = NumberUtil.getInt(currentTimes);
					if (times > 10) {
						map1.put("msg", "超过最大短信发送次数");
						map1.put("success", "false");
						map1.put("code", "2");
						return JsonUtil.objectToString(map1);
					}
				}

				corporation = (ComplatCorporation) session
						.getAttribute("corporation");
				String phoneNumber = corporation.getMobile();

				String cellphoneShortMessageRandomCodeMadeByJava = RandomCodeUtil
						.getRandomNumber(6);
				session.setAttribute(
						"cellphoneShortMessageRandomCodeMadeByJava",
						cellphoneShortMessageRandomCodeMadeByJava);
				String content = JisSettings
						.getSettings()
						.getRecovingPpdContent()
						.trim()
						.replace("cellphoneShortMessageRandomCodeMadeByJava",
								cellphoneShortMessageRandomCodeMadeByJava);
				String appBusinessId = JisSettings.getSettings()
						.getBusinessIdForRecovingPpd().trim();
				String appBusinessName = JisSettings.getSettings()
						.getBusinessNameForRecovingPpd().trim();

				int loseTime = 60;
				// CellphoneShortMessageUtil cellMesUtil = new
				// CellphoneShortMessageUtil();
				resultJson = "{'success':'true'}";// cellMesUtil.sendPhoneShortMessage(phoneNumber,content,
													// appBusinessId,
													// appBusinessName,
													// loseTime);
				Map map = (Map) JsonUtil.StringToObject(resultJson, Map.class);
				if (StringUtil.getString(map.get("success")).equals("true")) {
					if (currentTimes == null) {
						session.setAttribute("mobiletimes", Integer.valueOf(1));
					} else {
						int times = NumberUtil.getInt(currentTimes);
						session.setAttribute("mobiletimes",
								Integer.valueOf(times + 1));
					}
					map1.put("success", "true");
					map1.put("code", "3");
					return JsonUtil.objectToString(map1);
				}

			}

			return resultJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/recoverPwdByPhone_submit")
	@ResponseBody
	public void submitPhoneverify(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = false) String userName,
			HttpSession session,
			String cellphoneShortMessageRandomCodeWritenByGuest, String randCode) {
		try {

			if (!AccessUtil.checkAccess(request)) {
				return;
			}
			if ((SafeUtil
					.isSqlAndXss(cellphoneShortMessageRandomCodeWritenByGuest))
					|| (SafeUtil
							.isSqlAndXss(cellphoneShortMessageRandomCodeWritenByGuest))) {
				return;
			}
			Map<String, Object> result = new HashMap<String, Object>();
			session.setAttribute(
					"cellphoneShortMessageRandomCodeWritenByGuest",
					cellphoneShortMessageRandomCodeWritenByGuest);

			if (StringUtil.isNotEmpty(randCode)) {
				String rand = (String) session.getAttribute("rand");
				if (!rand.equalsIgnoreCase(randCode)) {
					result.put("success", false);
					result.put("message", "验证码错误,请重新输入!");
					response.setContentType("text/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSONUtil.writeMapJSON(result));
					return;
				}
			}
			if (StringUtil
					.isNotEmpty(cellphoneShortMessageRandomCodeWritenByGuest)) {
				if (!StringUtil
						.equals(cellphoneShortMessageRandomCodeWritenByGuest,
								StringUtil.getString(session
										.getAttribute("cellphoneShortMessageRandomCodeMadeByJava")))) {
					result.put("success", false);
					result.put("message", "短信验证码错误,请重新输入!");
					response.setContentType("text/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSONUtil.writeMapJSON(result));
					return;
				}
				result.put("success", true);
				result.put("message", "验证成功!");
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
				return;
			} else {
				result.put("success", false);
				result.put("message", "短信验证码错误,请重新输入!");
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/resetpwd_show")
	public String resetPwd(HttpSession session, Model model) {
		String typeEntity = (String) session.getAttribute("typeEntity");
		String loginName = "";
		if (StringUtil.isNotEmpty(typeEntity)) {
			if ("per".equals(typeEntity)) {
				ComplatOutsideuser outsideUser = (ComplatOutsideuser) session
						.getAttribute("outsideUser");
				if (outsideUser != null)
					loginName = outsideUser.getLoginName();
			} else {
				ComplatCorporation corporation = (ComplatCorporation) session
						.getAttribute("corporation");
				if (corporation != null)
					loginName = corporation.getLoginName();
			}
		} else {
			model.addAttribute("appmark", "gszw");
			return "jis/front/perlogin";
		}
		Object randCode = session
				.getAttribute("cellphoneShortMessageRandomCodeMadeByJava");
		if ((randCode == null) || (StringUtil.isEmpty(loginName))) {
			model.addAttribute("appmark", "gszw");
			return "jis/front/perlogin";
		}
		Object randCode2 = session
				.getAttribute("cellphoneShortMessageRandomCodeWritenByGuest");
		if (!randCode.equals(randCode2)) {
			model.addAttribute("appmark", "gszw");
			return "jis/front/perlogin";
		}

		String appmark = (String) session.getAttribute("appmark");
		if (appmark == null) {
			appmark = "";
		}
		model.addAttribute("loginName", loginName);
		model.addAttribute("url", "resetpwd_submit");
		model.addAttribute("typeEntity", typeEntity);
		model.addAttribute("appmark", appmark);

		return "jis/front/resetpwd";
	}

	@RequestMapping(value = "resetpwd_submit")
	@ResponseBody
	public void submitResetPwd(HttpSession session, String pwd,
			HttpServletResponse response) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String typeEntity = (String) session.getAttribute("typeEntity");

			boolean isSuccess = false;
			if (("".equals(typeEntity)) || (typeEntity == null)) {
				result.put("success", false);
				result.put("message", "验证超时，请重新尝试！");
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
				return;
			}
			if ("per".equals(typeEntity)) {
				ComplatOutsideuser outsideUser = (ComplatOutsideuser) session
						.getAttribute("outsideUser");
				isSuccess = this.OutsideUserService.updatePwd(
						outsideUser.getIid(), Md5Util.md5encode(pwd));
			} else {
				ComplatCorporation corporation = (ComplatCorporation) session
						.getAttribute("corporation");
				String loginName = corporation.getLoginName();
				isSuccess = this.corporationService.updatePwd(loginName,
						Md5Util.md5encode(pwd));
			}
			if (isSuccess) {
				session.invalidate();
				result.put("success", true);
				result.put("message", "密码修改成功！");
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
				return;
			} else {
				result.put("success", false);
				result.put("message", "密码修改失败！");
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
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
				+ ":8080/gsjis/front/register/perregister.do';"
				+ "var frloginurl='http://"
				+ domain
				+ ":8080/gsjis/front/cor/interface.do?action=ticketLogin&appmark=gszw&domain="
				+ domain
				+ ":8080&gotoUrl=';"
				+ "var frregediturl='http://"
				+ domain
				+ ":8080/gsjis/front/register/corregister.do';"
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
				+ "location.href='http://'+domain+'/gszw/member/login/login.do?url='+grloginurl+'&src='+src+'&domain='+encodeURIComponent(encodeURIComponent(domain));}"
				+ " function showfrlogin(){var src=window.location.href;src=encodeURIComponent(src);   "
				+ "frloginurl=encodeURIComponent(frloginurl);   "
				+ "location.href='http://'+domain+'/gszw/member/login/login.do?url1='+frloginurl+'&src='+src+'&domain='+encodeURIComponent(encodeURIComponent(domain));  }"
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
	
	public static void main(String[] args) {
		String pwd =Md5Util.md5decode("dXZ8G3Z0AA4EQwdDBzM=");
		System.out.println(pwd);
	
	}
}
