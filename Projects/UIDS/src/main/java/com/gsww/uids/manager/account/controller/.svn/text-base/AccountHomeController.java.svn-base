package com.gsww.uids.manager.account.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.entity.DegreeEnum;
import com.gsww.common.entity.NationalityEnum;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.SmsUtil;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.UrlBuilder;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.sso.entity.AuthcodeRequest;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;
import com.gsww.uids.manager.sso.service.AuthcodeRequestService;
import com.gsww.uids.manager.sso.service.AuthcodeResponseService;
import com.gsww.uids.manager.sso.service.OauthService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.sys.service.LogService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

import net.sf.json.JSONObject;
/**
 * 账号主页控制类
 * @author jinwei
 *
 */
@Controller
@RequestMapping("/accountHome")
public class AccountHomeController {
	
	private static final Logger logger = Logger.getLogger(AccountHomeController.class);
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthcodeRequestService authcodeRequestService;
	
	@Autowired
	private AuthcodeResponseService authcodeResponseService;
	
    @Autowired
    private OauthService authService;
	
	/**
	 *  账号主页
	 *  
	 * @param model
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/accountHome", method = RequestMethod.GET)
	public String getAccountHome(Model model, HttpServletRequest request) throws IOException {
		
		// 账号信息
		String accountId = (String) request.getSession().getAttribute(WebConstants.ONLINE_ACCOUNT_ID);		
		Account account = accountService.findById(accountId);
		model.addAttribute("account", account);
		
		return "manager/sys/personal/accountHome";
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String accountHome(Model model, String accountId) {
		
		Account account = accountService.findById(accountId);
		String lastLoginTime = logService.findLastTimeLoginTimeByAccountName(account.getName());
		
		model.addAttribute("account", account);
		model.addAttribute("lastLoginTime", lastLoginTime);
		model.addAttribute("sources", resourceService.findShowResourcesOfAccount(account.getUuid()));
		model.addAttribute("accountType", AccountTypeEnum.nameOf(account.getType()));
		if(account.getUser() != null){
			model.addAttribute("safte", "高");
			model.addAttribute("bindPhone", "已绑定");
			model.addAttribute("realName", account.getUser().getStatus());
		}else{
			model.addAttribute("safte", "低");
			model.addAttribute("bindPhone", "未绑定");
			model.addAttribute("realName", 1);
		}
		
		
		return "/manager/sys/personal/account/home";
	}
		
	/**
	 * 绑定用户
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/bindUser", method = RequestMethod.GET)
	public String accountBindUser(Model model, HttpServletRequest request) {
		
		String accountId = (String) request.getSession().getAttribute(WebConstants.ONLINE_ACCOUNT_ID);
		Account account = accountService.findById(accountId);
		User user = account.getUser();
		if(account.getUser() == null){
			user = new User();
		}
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		
		int userType = 0;
		//如果为政务和个人账号，则绑定自然人用户,否则绑定法人用户
		if( account.getType() == 1 || account.getType() == 2 ){
			userType = 1;
		}else{
			userType = 2;
		}
		
		return "/manager/sys/personal/account/users/"+UserTypeEnum.valueOf(userType)+"/bindUser";
	}
	
	/**
	 * 完善用户信息
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public String accountDetails(Model model, String accountId) {
		
		//如已关联用户
		Account account = accountService.findById(accountId);
		User info = new User();
		if(account.getUser() != null){
			info = account.getUser();
		}
		// 用户基本信息
		model.addAttribute("info", info);
		
		Area birthArea = info.getBirthArea();
		if ( birthArea == null ) {
			birthArea = areaService.generateAreaOfLevel(4);
		}
		model.addAttribute("birthArea", birthArea);
		
		Area liveArea = info.getLiveArea();
		if ( liveArea == null ) {
			liveArea = areaService.generateAreaOfLevel(4);
		}
		model.addAttribute("liveArea", liveArea);
		
		Organization org = info.getOrg();
		if ( org == null ) {
			org = new Organization();
		}
		model.addAttribute("org", org);
		
		// 用户详细信息
		UserDetail detail = info.getDetail();
		if ( detail == null ) {
			detail = new UserDetail();
		}
		model.addAttribute("detail", detail);

		// 要绑定的账号
		model.addAttribute("accountId", accountId);
		
		// 所有民族
		model.addAttribute("nationalities", NationalityEnum.getValueArray());
				
		// 查找所有省级区域
		List<Area> provinces = areaService.findAllByLevel(2);
		model.addAttribute("provinces", provinces);
		
		// 学历
		model.addAttribute("degrees", DegreeEnum.getValueArray());
		
		int userType = 0;
		//如果为政务和个人账号，则绑定自然人用户,否则绑定法人用户
		if( account.getType() == 1 || account.getType() == 2 ){
			userType = 1;
		}else{
			userType = 2;
		}
		
		return "/manager/sys/personal/account/users/"+UserTypeEnum.valueOf(userType)+"/index";
	}
	
	/**
	 * 修改或查看账号信息
	 * @param model
	 * @param type 账号类型
	 * @param id 账号uuid
	 * @param isEditSelf 是账号管理中新增修改账号，还是个人中心中修改自身账号（两种情况下，页面内容有所区别）
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/lookOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, int type, String id, String act) {
		
		Account info = accountService.findById(id);
		info.setType(type);
		model.addAttribute("info", info);
		model.addAttribute("act", act);
		
		// 绑定用户
		User user = info.getUser();
		if ( user == null ) {
			user = new User();
		}
		model.addAttribute("user", user); 
		
		// 所属应用
		Application app = info.getApp();
		if ( null == app ) {
			app = new Application();
		}
		model.addAttribute("app", app);
		
		// 查找所有应用
		List<Application> applications = appService.findAll();
		model.addAttribute("applications", applications);
		
		return "manager/sys/personal/account/type/" + AccountTypeEnum.valueOf(type) + "/index";
	}
	
	/**
	 * 认证手机号
	 * @param model
	 * @param id 账号uuid
	 * @param isEditSelf 是账号管理中新增修改账号，还是个人中心中修改自身账号（两种情况下，页面内容有所区别）
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/authPhone", method = RequestMethod.GET)
	public String authPhone(Model model, String id) {
		
		Account account = accountService.findById(id);
		User info = account.getUser();
		model.addAttribute("info", info);
		return "manager/sys/personal/account/auth/authPhone";
	}
	
	/**
	 * 解绑用户
	 * @param model
	 * @param id 账号uuid
	 * @param isEditSelf 是账号管理中新增修改账号，还是个人中心中修改自身账号（两种情况下，页面内容有所区别）
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/unbundledUsers", method = RequestMethod.GET)
	public String unbundledUsers(Model model, String id) {
		
		Account account = accountService.findById(id);
		User info = account.getUser();
		model.addAttribute("info", info);
		model.addAttribute("accountId", id);
		
		if( info.getType() == UserTypeEnum.NATURAL.getNumber() ){
			model.addAttribute("userType", 1);
		}else if( info.getCorporateType().equals(User.CORPORATE_TYPE) ){
			model.addAttribute("userType", 2);
		}else{
			model.addAttribute("userType", 3);
		}
		
		return "manager/sys/personal/account/users/unzip/unbundledUsers";
	}
	
	/**
	 * 更改认证手机
	 * @param model
	 * @param userId 用户uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/changePhone", method = RequestMethod.GET)
	public String changePhone(Model model, String userId) {
		
		User info = userService.findById(userId);
		model.addAttribute("info", info);
		return "manager/sys/personal/account/auth/changePhone";
	}
	
	/**
	 * 解绑用户iframe
	 * @param model
	 * @param userId 用户uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/unbundled", method = RequestMethod.GET)
	public String unbundled(Model model, String userId, String accountId) {
		
		User info = userService.findById(userId);
		model.addAttribute("info", info);
		model.addAttribute("accountId", accountId);
		return "manager/sys/personal/account/users/unzip/unbundled";
	}
	
	/**
	 * 注销账号iframe
	 * @param model
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/writeOffAccount", method = RequestMethod.GET)
	public String writeOffAccount(Model model,String accountId) {
		
		Account account = accountService.findById(accountId);
		model.addAttribute("account", account);
		return "manager/sys/personal/writeOffAccount";
	}
	
	/**
	 * 校验手机号，如检验通过则注销账号
	 * @param model
	 * @param userId 用户uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAccountPhone", method = RequestMethod.POST)
	@ResponseBody
	public String checkAccountPhone(String accountId, String phoneNumber, String code , String mark) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		// 验证验证码
		try {
			if (!SmsUtil.getInstance().checkCode(mark, phoneNumber, code)) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "短信验证码输入错误！");
				return resultJson.toString();
			}
		} catch (Exception e) {
			logger.info("短息验证异常:"+e.getMessage());
		}
		// 注销
		accountService.delete(accountId);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "注销成功");
		return resultJson.toString();
	}
	
	/**
	 * 校验手机号，如检验通过则解绑用户
	 * @param model
	 * @param userId 用户uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkUserPhone", method = RequestMethod.POST)
	@ResponseBody
	public String checkUserPhone(String accountId, String phoneNumber, String code , String mark) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		// 验证验证码
		try {
			if (!SmsUtil.getInstance().checkCode(mark, phoneNumber, code)) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "短信验证码输入错误！");
				return resultJson.toString();
			}
		} catch (Exception e) {
			logger.info("短息验证异常:"+e.getMessage());
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		
		// 解绑
		Account account = accountService.findById(accountId);
		account.setUser(null);
		accountService.saveOrUpdate(account);
	
		resultJson.put("info", "解绑成功");
		return resultJson.toString();
	}
	
	/**
	 * 校验手机号，如检验通过则改绑手机
	 * @param model
	 * @param userId 用户uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkPhone", method = RequestMethod.POST)
	@ResponseBody
	public String checkPhone(Model model, String userId , String phoneNumber, String code , String mark, String type) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		// 验证验证码
		try {
			if (!SmsUtil.getInstance().checkCode(mark, phoneNumber, code)) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "短信验证码输入错误！");
				return resultJson.toString();
			}
		} catch (Exception e) {
			logger.info("短息验证异常:"+e.getMessage());
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		//如果为新手机号则改绑
		if("new".equals(type)){
			User info = userService.findById(userId);
			info.setMobile(phoneNumber);
			userService.saveOrUpdate(info);
			resultJson.put("state", 2);
		}
		resultJson.put("info", "验证成功");
		return resultJson.toString();
	}
	
	
	/**
	 * 保存、更新账号信息
	 * 
	 * @param model
	 * @param info
	 * @param appId
	 * @param userId
	 * @return
	 */
	@SystemLog(description = "账号", module = OperationLog.ACCOUNT_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(Account info, String appId, String userId, String old_password, String new_password, String operate) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 绑定用户
		if ( StringUtil.isNotBlank(userId) ) {
			User user = userService.findById(userId);
			info.setUser(user);
		}
				
		// 所属应用
		Application app = appService.findById(appId);
		info.setApp(app);
		
		// 验证唯一性：身份证
		StringBuilder errInfo = new StringBuilder();
		if ( !accountService.checkUnique(info, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
		
		// 处理密码
		// 如果是编辑账号，且密码没修改，则使用之前的密码
		Account account = accountService.findById(info.getUuid());
		if ( StringUtil.isNotBlank(info.getUuid()) && StringUtil.isBlank(info.getPassword()) ) {
			info.setPassword(account.getPassword());
		}
		//修改密码
		if(StringUtil.isNotBlank(new_password) && StringUtil.isNotBlank(old_password)){
			if( ! old_password.equals(new_password) ){
				// 判断原始密码是否正确				
				SimpleHash simpleHash_old = new SimpleHash(app.getAccountEncryptType(), old_password, app.getAccountEncryptSalt(), app.getAccountEncryptIterations());
				if(!account.getPassword().endsWith(simpleHash_old.toString())){
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "原始密码错误！！");
					return resultJson.toString();
				}else{
					SimpleHash simpleHash_new = new SimpleHash(app.getAccountEncryptType(), new_password, app.getAccountEncryptSalt(), app.getAccountEncryptIterations());
					info.setPassword(simpleHash_new.toString());					
				}
				
			}else{
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "新密码不能与原始密码一致");
				return resultJson.toString();
			}
		}
		// 保存
		accountService.saveOrUpdate(info);
		
		// 返回保存成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.SAVE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 从个人中心实现单点登录
	 * 
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/sso", method = RequestMethod.GET)
	public String ssoLogin(HttpServletRequest request, String sid) throws Exception {
		
		// 资源
		AppResource source = resourceService.findById(sid);
		
		// 账号
		String accountId =(String) request.getSession().getAttribute(WebConstants.ONLINE_ACCOUNT_ID);
		Account account = accountService.findById(accountId);
		
		// 这些参数是为了兼容大汉的系统
		// 由于涉及安全问题，不能提供用户名和密码
		String loginuser = "";
		String loginpass = "";
		String appname = source.getApp().getMark();
		String loginallname = "";
		String groupcode = "";
		
		// 生成authcode
        String authcode = authService.generateAuthcode();
        
        // 保存请求
        AuthcodeRequest authcodeRequest = new AuthcodeRequest();
        authcodeRequest.setAccountName(account.getName());
        authcodeRequest.setClientId(source.getApp().getClientId());
        authcodeRequest.setClientIdOfAccount(account.getApp().getClientId());
        authcodeRequest.setRedirectUri(source.getUrl());
        authcodeRequest.setRequestTime(TimeHelper.getCurrentTime());
        authcodeRequest.setResponseType(ResponseType.CODE.toString());
        authcodeRequestService.persist(authcodeRequest);
        // 保存应答
        AuthcodeResponse authcodeResponse = new AuthcodeResponse();
        authcodeResponse.setAuthcode(authcode);
        authcodeResponse.setRequest(authcodeRequest);
        authcodeResponse.setResponseTime(TimeHelper.getCurrentTime());
        authcodeResponseService.persist(authcodeResponse);
		
        // 跳转到应用
        UrlBuilder urlBuilder = new UrlBuilder(source.getApp().getSingleLoginUrl())
        		.appendParameter(OAuth.OAUTH_CODE, authcode, "?")
        		.appendParameter("loginuser", loginuser, "&")
        		.appendParameter("loginpass", loginpass, "&")
        		.appendParameter("appname", appname, "&")
        		.appendParameter("groupcode", groupcode, "&")
        		.appendParameter("loginallname", loginallname, "&");		
		return "redirect:" + urlBuilder.toString();
	}
}