package com.gsww.uids.manager.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.gsww.common.entity.AuthStatusEnum;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.BussinessType;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.AuthenticatePersonalRealNameUtil;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.SmsUtil;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountHomeService;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.sms.entity.SmsRecord;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.BanService;
import com.gsww.uids.manager.sys.service.SysConfigService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

import net.sf.json.JSONObject;

/**
 * 系统管理控制类
 * 
 * @author jinwei
 * @author taolm
 *
 */
@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private Producer captchaProducer;

	@Autowired
	private BanService banService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private AccountHomeService accountHomeService;
	
	/**
	 * session中保存账号id
	 */
	@SystemLog(description = "注销", module = OperationLog.LOGOUT_TYPE, actionType = OperationLog.LOGOUT_TYPE)
	@RequestMapping(value="/logoutLog", method = RequestMethod.POST)
	@ResponseBody
	public String logout(){
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("state", 1);
		return resultJson.toString();
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login/checkCode", method = RequestMethod.GET)
	public void getCheckCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 1、http头设置
		response.setDateHeader("Expires", 0);		
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");		
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");		
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		
		// 2、将文字版验证码写入session中
		// create the text for the image
		String capText = captchaProducer.createText();		
		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		
		// 3、将图片版验证码作为应答
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the image with the text
		BufferedImage imageBuffer = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		try {
			ImageIO.write(imageBuffer, "JPEG", out);
			out.flush();
		} catch ( IOException e ) {
			out.close();
			throw e;
		} finally {
			out.close();
		}
	}
	
	/**
	 * 验证登录账号的信息是否正确
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SystemLog(description = "登录系统", module = OperationLog.LOGIN_TYPE, actionType = OperationLog.LOGIN_TYPE)
	@RequestMapping(value = "/login/checkLogin", method = RequestMethod.POST)
	@ResponseBody
	public String checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 验证账号信息的结果数据
		JSONObject resultJson = new JSONObject();
		
		// 获取登录的账号数据
		String appId = request.getParameter(WebConstants.APP_ID);
		String accountName = request.getParameter(WebConstants.ACCOUNT_NAME);
		String accountPass = request.getParameter(WebConstants.ACCOUNT_PASSWORD);
		String checkCode = request.getParameter(WebConstants.CHECK_CODE);
		
		// 验证码：如果session过期，shiro会清空数据
		String standardCheckCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if ( StringUtil.isBlank(standardCheckCode) ) {
			resultJson.put("state", 0);
			resultJson.put("info",  "页面已过期，请刷新后重新登录！");
			return resultJson.toString();
		}
		
		// 验证用户输入的验证码是否一致：大小写不敏感
		if ( !standardCheckCode.equalsIgnoreCase(checkCode) ) {
			resultJson.put("state", 0);
			resultJson.put("info",  "验证码输入错误，请重新输入！");
			return resultJson.toString();
		}
		
		// 生成用户名密码token：这里的用户名是由账号名和应用组成的
		// 使用实际的认证方式：这里仅采用了用户名密码方式
		String userName = appId + accountName;
		UsernamePasswordToken token = new UsernamePasswordToken(userName, accountPass);
		// 设置rememberMe
	    token.setRememberMe(true);
	    
	    Account account = null;
	    List<Account>accountList = accountService.findByAppAndAccountName(appId, accountName);
	    if(accountList.isEmpty()){
	    	resultJson.put("state", 3);
			resultJson.put("info", "账号或密码有误, 请重新输入");
			return resultJson.toString();
	    }else{
	    	account = accountList.get(0);
	    }
	    
	    // 验证
	    Subject subject = SecurityUtils.getSubject();
	    try {
	    	subject.login(token);
	    } catch (ExcessiveAttemptsException e) {
	    	JSONObject msg = JSONObject.fromObject(e.getMessage());
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "密码错误次数已达"+msg.get("count")+"次，请"+msg.get("time")+"分钟以后再进行登录");
			return resultJson.toString();
		}  catch (AuthenticationException e) {
	    	//记录封停日志
			banService.writeBanLog(account, request);
	    	
			String message = "账号或密码有误, 请重新输入";
			Throwable rootEx = e;
			while ( rootEx.getCause() != null ) {
				rootEx = rootEx.getCause();
			}
			if ( rootEx.getMessage().contains("账号已经封停") ) {
				message = rootEx.getMessage();
			}
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", message);
			return resultJson.toString();
	    }
	    
	    // 将账号信息写入session
        subject.getSession().setAttribute(WebConstants.ONLINE_ACCOUNT_ID, account.getUuid());
        subject.getSession().setAttribute(WebConstants.LOGIN_TIME, TimeHelper.getCurrentTime());
        
        // 设置session的有效期：单位：毫秒
        long timeout = sysConfigService.getTimeoutOfSessionByAccountType(account.getType()) * 60 * 1000;
    	subject.getSession().setTimeout(timeout);

		// 返回
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info",  "登录成功！");
		return resultJson.toString();
	}
	
	/**
	 * 中间跳转页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login/jump", method = RequestMethod.GET)
	public String choiseType(Model model) {
		return "manager/sys/personal/jump";
	}
	
	/**
	 * 忘记密码页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login/forgetPass", method = RequestMethod.GET)
	public String forgetPass(Model model) {
		List<Application> apps = appService.findAll();
		model.addAttribute("apps", apps);
		return "manager/sys/personal/forgetPass";
	}
	
	/**
	 * 忘记密码-确认账号
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login/checkAccountAndApp", method = RequestMethod.POST)
	@ResponseBody
	public String checkAccountAndApp(String accountName, String appId) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		if(accountService.findByAppAndAccountName(appId, accountName).size()>0){
			resultJson.put("state", 1);
			resultJson.put("info",  "校验成功");
		}else{
			resultJson.put("state", 0);
			resultJson.put("info",  "在该应用下不存在此账号");
		}
		return resultJson.toString();
	}
	
	/**
	 * 忘记密码-验证手机号
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login/checkPhoneNumber", method = RequestMethod.POST)
	@ResponseBody
	public String checkPhoneNumber(String accountName, String appId, String phoneNumber, String code , String mark) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		Account account = accountService.findByAppAndAccountName(appId, accountName).get(0);
		
		if( ! account.getPhone().equals(phoneNumber) ){
			resultJson.put("state", 0);
			resultJson.put("info",  "请填写注册时的手机号");
			return resultJson.toString();
		}
		
		if( !accountHomeService.checkPhoneNumber(phoneNumber, code, mark) ){
			resultJson.put("state", 0);
			resultJson.put("info",  "手机验证失败");
		}else{
			resultJson.put("state", 1);
			resultJson.put("info",  "手机验证成功");
			resultJson.put("accountId",  account.getUuid());
		}
		return resultJson.toString();
	}
	
	/**
	 * 忘记密码-验证手机号
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword(String accountId, String newPassReal) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		Account account = accountService.findById(accountId);
		
		// 保存新密码
		SimpleHash newSimpleHash = new SimpleHash(account.getApp().getAccountEncryptType(), newPassReal, 
				account.getApp().getAccountEncryptSalt(), account.getApp().getAccountEncryptIterations());
		String secreteNewPass = newSimpleHash.toString();
		account.setPassword(secreteNewPass);
		accountService.saveOrUpdate(account);
		
		resultJson.put("state", 1);
		resultJson.put("info", "账号密码修改成功！");
		return resultJson.toString();
	}
	
	/**
	 * 申请注册页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login/register", method = RequestMethod.GET)
	public String register(Model model, int accountType) {
		
		Application systemApp = appService.findSystemApplication();
		if ( systemApp == null ) {
			systemApp = new Application();
		}
		model.addAttribute("systemApp", systemApp);
		
		model.addAttribute("accountType", accountType);
		//获得系统参数中设定的密码强度等级
		model.addAttribute("pwdLevel",sysConfigService.getSystemBasicParam().getPwdLevel());
		
		return "manager/sys/personal/register";
	}
	
	/**
	 * 提交账号注册信息
	 * 
	 * @param model
	 * @param account
	 * @param appId
	 * @return
	 */
	@SystemLog(description = "账号注册", module = OperationLog.ACCOUNT_REGISTER, actionType = OperationLog.ACCOUNT_REGISTER)
	@RequestMapping(value="/login/runRegister", method = RequestMethod.POST)
	@ResponseBody
	public String runRegister(HttpServletRequest request, Account account, String appId, String orgId, int accountType, String phoneNumber, String code , String mark) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		if( ! accountHomeService.checkPhoneNumber(phoneNumber, code, mark) ){
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "短信验证码输入错误！");
			return resultJson.toString();
		}
		// 设置手机号
		account.setPhone(phoneNumber);
		
		// 设置所属应用
		Application app = appService.findById(appId);
		account.setApp(app);
		
		// 设置账号类型
		account.setType(accountType);
		
		// 检查账号唯一性
		StringBuilder errInfo = new StringBuilder();
		if ( !accountService.checkUnique(account, errInfo) ) {
			resultJson.put("state", 0);
			resultJson.put("info",  "账号注册失败：" + errInfo.toString());
			return resultJson.toString();
		}

		// 加密
		try {
			SimpleHash simpleHash = new SimpleHash(app.getAccountEncryptType(), account.getPassword(), 
					app.getAccountEncryptSalt(), app.getAccountEncryptIterations());
			account.setPassword(simpleHash.toString());
		} catch (Exception e) {
			e.printStackTrace();
			
			resultJson.put("state", 0);
			resultJson.put("info",  "账号注册失败：未知的加密算法");
			return resultJson.toString();
		}
		
		// 保存账号信息
		accountService.saveOrUpdate(account);
		
		
		// 返回结果
		resultJson.put("state", 1);
		resultJson.put("info", "账号注册成功！请登录账号");
		return resultJson.toString();
		
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getUserIndex(Model model, HttpServletRequest request) throws IOException {
		
		return "index";
	}
	
	/**
	 * 账号设置页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/accountSetting", method = RequestMethod.GET)
	public String userHome(Model model, HttpServletRequest request) throws IOException {
		
		// 账号信息
		String accountId = (String) request.getSession().getAttribute(WebConstants.ONLINE_ACCOUNT_ID);		
		Account info = accountService.findById(accountId);
		model.addAttribute("info", info);
		model.addAttribute("isEditSelf", 1);

		//获得系统参数中设定的密码强度等级
		model.addAttribute("pwdLevel",sysConfigService.getSystemBasicParam().getPwdLevel());
				
		// 绑定用户
		User user = info.getUser();
		if (user == null) {
			user = new User();
		}
		model.addAttribute("user", user);

		// 所属应用
		Application app = info.getApp();
		if (null == app) {
			app = new Application();
		}
		model.addAttribute("app", app);

		// 查找所有应用
		List<Application> applications = appService.findAll();
		model.addAttribute("applications", applications);

		return "manager/account/" + AccountTypeEnum.valueOf(info.getType()) + "/newOrEdit";
	}
	
	/**
	 * 修改账号密码
	 * 
	 * @param model
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/changeAccountPass", method = RequestMethod.GET)
	public String changeAccountPass(Model model, String accountId) {
		model.addAttribute("accountId", accountId);
		
		return "manager/sys/personal/changeAccountPass";
	}
	
	/**
	 * 保存修改的账号密码
	 * 
	 * @param model
	 * @param acccountId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@SystemLog(description = "修改密码", module = OperationLog.ACCOUNT_MODULE, actionType = OperationLog.UPDATE_TYPE)
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/saveAccountPass", method = RequestMethod.POST)
	@ResponseBody
	public String saveAccountPass(Model model, String accountId, String oldPass, String newPass) throws IOException {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
				
		// 要修改密码的账号
		Account account = accountService.findById(accountId);
		
		// 先判断旧的密码是否正确
		String secretOldPass = null;
		try {
			SimpleHash oldSimpleHash = new SimpleHash(account.getApp().getAccountEncryptType(), oldPass, 
					account.getApp().getAccountEncryptSalt(), account.getApp().getAccountEncryptIterations());
			secretOldPass = oldSimpleHash.toString();
		} catch (Exception e) {
			e.printStackTrace();
			
			resultJson.put("state", 0);
			resultJson.put("info", "保存密码失败：未知的加密方式！");
			return resultJson.toString();
		}
		if ( !account.getPassword().equalsIgnoreCase(secretOldPass) ) {
			resultJson.put("state", 0);
			resultJson.put("info", "输入的旧密码错误！");
			return resultJson.toString();
		}
		
		// 保存新密码
		SimpleHash newSimpleHash = new SimpleHash(account.getApp().getAccountEncryptType(), newPass, 
				account.getApp().getAccountEncryptSalt(), account.getApp().getAccountEncryptIterations());
		String secreteNewPass = newSimpleHash.toString();
		account.setPassword(secreteNewPass);
		accountService.saveOrUpdate(account);
		
		resultJson.put("state", 1);
		resultJson.put("info", "账号密码修改成功！");
		return resultJson.toString();
	}
	
	/**
	 * 发送验证码
	 * 
	 * @param model
	 * @param identity
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/sendCode", method = RequestMethod.POST)
	@ResponseBody
	public String sendCode(Model model, String mobile, int type) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
				
		// 发送验证码
		runSendCheckCode(mobile, type, resultJson);
		
		// 返回结果
		return resultJson.toString();
	}
	
	/**
	 * 申请实名认证
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/realNamelAuth", method = RequestMethod.POST)
	@ResponseBody
	public String realNamelAuth(Model model, String userId) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 查找用户信息
		User user = userService.findById(userId);
		
		// 转换用户类型
		UserTypeEnum type = UserTypeEnum.getByNumber(user.getType());
		if ( type == null ) {
			// 返回认证结果
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "用户类型不支持，无法进行实名认证！");
			resultJson.put("data", user.getStatus());
			return resultJson.toString();
		}
		
		// 判断是否开启了实名认证服务
		if ( !sysConfigService.checkAuthRealNameOpen(type) ) {
			// 返回认证结果
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", type.getName() + "实名认证服务未开启！");
			resultJson.put("data", user.getStatus());
			return resultJson.toString();
		}
		
		// 如果是个人实名认证，则进行实名认证
		if ( UserTypeEnum.NATURAL == type ) {
			// 对用户进行实名认证，并根据结果修改其认证状态
			// TODO 应该按照系统参数中设定的认证模式进行认证
			// TODO 这里只简单地采用了 身份证+姓名 的认证方式
			if ( AuthenticatePersonalRealNameUtil.getInstance().authenticateRealName(user.getIdentity(), user.getName()) ) {
				user.setStatus(AuthStatusEnum.AUDITED.getNumber());
				userService.saveOrUpdate(user);
				
				// 返回认证结果
				resultJson.put("state", MessageInfo.STATE_SUCCESS);
				resultJson.put("info", "认证通过！");
				resultJson.put("data", user.getStatus());
				return resultJson.toString();			
			} else {
				user.setStatus(AuthStatusEnum.DENY.getNumber());
				userService.saveOrUpdate(user);
				
				// 返回认证结果
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "认证没有通过！");
				resultJson.put("data", user.getStatus());
				return resultJson.toString();	
			}	
		} else { // 法人的实名认证
			// TODO 目前法人实名认证还未实现
			// 返回认证结果
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "抱歉，目前法人实名认证服务暂未提供！");
			resultJson.put("data", user.getStatus());
			return resultJson.toString();	
		}
	}
	
	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 * @param type
	 * @param result
	 */
	private void runSendCheckCode(String mobile, int type, JSONObject result) {
		
		// 转换短信业务类型
		BussinessType busiType = BussinessType.getByNumber(type);
		if ( busiType == null ) {
			result.put("state", 0);
			result.put("info", "短信业务类型错误，无法发送短信验证码！");
			return ;
		}
		
		// 发送验证码
		SmsRecord record = SmsUtil.getInstance().sendSms(mobile, busiType);
		if ( record == null ) {
			result.put("state", 0);
			result.put("info", "发送验证码失败！");
		} else {
			result.put("state", 1);
			result.put("info", "发送验证码成功！");
			result.put("data", record.getUuid());
		}
	}
}