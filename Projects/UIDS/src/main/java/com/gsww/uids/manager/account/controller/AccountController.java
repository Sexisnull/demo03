package com.gsww.uids.manager.account.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.SmsUtil;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.entity.AccountErrorTemp;
import com.gsww.uids.manager.account.entity.AccountMergeTemp;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.excel.service.ExcelService;
import com.gsww.uids.manager.excel.util.ExportMapping;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.sys.service.SysConfigService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

import net.sf.json.JSONObject;

/**
 * 账号管理控制类
 * 
 * @author jinwei
 * @author taolm 修改
 *
 */

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger logger = Logger.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 展示账号
	 * 
	 * @param model
	 * @param type
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, int type) {

		model.addAttribute("type", type);		

		return "manager/account/" + AccountTypeEnum.valueOf(type) + "/index";
	}

	/**
	 * 获取账号列表
	 * 
	 * @param model
	 * @param type
	 * 			  账号类型
	 * @param orgId
	 *            机构：公务账号有效
	 * @param name
	 *            姓名
	 * @param identity
	 *            身份证号
	 * @param corporateName
	 *            企业/机构名称：法人账号有效
	 * @param accountName
	 *            账号名
	 * @param currentPage
	 * @param size
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(Model model, int type, String name, String identity, String corporateName, String accountName, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		
		PageObject<Account> pageList = accountService.findPage(type, name, identity, corporateName, accountName, page, rows);
		
		// 给敏感信息增加掩码
		accountService.addMaskToSeceretInfo(pageList);
				
		return pageList.toJSONString();
	}

	/**
	 * 新增或修改
	 * 
	 * @param model
	 * @param type
	 *            账号类型
	 * @param id
	 *            账号uuid
	 * @param isEditSelf
	 *            是账号管理中新增修改账号，还是个人中心中修改自身账号（两种情况下，页面内容有所区别）
	 * @return
	 */
	@RequiresPermissions(value = { WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN }, logical = Logical.OR)
	@RequestMapping(value = "/newOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, int type, String id, int isEditSelf) {
		// 账号信息
		Account info = accountService.findById(id);
		info.setType(type);
		model.addAttribute("info", info);
		model.addAttribute("isEditSelf", isEditSelf);
		
		// 获得系统参数中设定的密码强度等级
		model.addAttribute("pwdLevel",sysConfigService.getSystemBasicParam().getPwdLevel());
		
		// 绑定的用户
		User user = info.getUser();
		if (user == null) {
			user = new User();
		}
		model.addAttribute("user", user);

		// 账号所属应用
		Application app = info.getApp();
		if (null == app) {
			app = new Application();
		}
		model.addAttribute("app", app);

		// 查找所有应用
		List<Application> applications = appService.findAll();
		model.addAttribute("applications", applications);

		return "manager/account/" + AccountTypeEnum.valueOf(type) + "/newOrEdit";
	}

	/**
	 * 保存、更新
	 * 
	 * @param model
	 * @param info
	 * @param appId
	 * @param userId
	 * @param operate
	 * @return
	 */
	@SystemLog(description = "账号", module = OperationLog.ACCOUNT_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = { WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN }, logical = Logical.OR)
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(Account info, String appId, String userId, String operate) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		// 绑定用户
		if (StringUtil.isNotBlank(userId)) {
			User user = userService.findById(userId);
			info.setUser(user);
		}

		// 所属应用
		Application app = appService.findById(appId);
		info.setApp(app);

		// 验证唯一性：身份证
		StringBuilder errInfo = new StringBuilder();
		if (!accountService.checkUnique(info, errInfo)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}

		// 处理密码
		// 如果是编辑账号，且密码没修改，则使用之前的密码
		if (StringUtil.isNotBlank(info.getUuid()) && StringUtil.isBlank(info.getPassword())) {
			Account account = accountService.findById(info.getUuid());
			info.setPassword(account.getPassword());
		} else {
			try {
				SimpleHash simpleHash = new SimpleHash(app.getAccountEncryptType(), info.getPassword(), app.getAccountEncryptSalt(),
						app.getAccountEncryptIterations());
				info.setPassword(simpleHash.toString());
			} catch (Exception e) {
				e.printStackTrace();

				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "未知的加密方式，请确保账号所属应用的加密方式配置正确！");
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
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@SystemLog(description = "账号", module = OperationLog.ACCOUNT_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if (!accountService.checkDelete(ids, errInfo)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}

		// 删除
		accountService.delete(ids);

		// 返回删除成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}

	/**
	 * 修改封停账号状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/modifyStatus", method = RequestMethod.POST)
	@ResponseBody
	public String modifyStatus(String id, String status) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		// 状态类型转换
		int iStatus = -1;
		try {
			iStatus = Integer.parseInt(status);
		} catch (NumberFormatException e) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "状态参数" + status + "错误，不能修改");
			return resultJson.toString();
		}

		// 修改状态
		Account account = accountService.findById(id);
		account.setStatus(iStatus);

		// 保存
		accountService.saveOrUpdate(account);

		// 返回修改成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "修改状态成功！");
		return resultJson.toString();
	}

	/**
	 * 账号解绑
	 * 
	 * @param model
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions(value = { WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN }, logical = Logical.OR)
	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	@ResponseBody
	public String unbind(Model model, String accountId) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();

		// 解绑
		Account account = accountService.findById(accountId);
		account.setUser(null);
		accountService.saveOrUpdate(account);

		// 返回认证结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "解绑成功！");
		return resultJson.toString();
	}

	/**
	 * 新增用户并绑定
	 * 
	 * @param model
	 * @param accountId
	 * @param user
	 * @param detail
	 * @param birthAreaId
	 * @param liveAreaId
	 * @param mark
	 * @param code
	 * @return
	 */
	@RequiresPermissions(value = { WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN }, logical = Logical.OR)
	@RequestMapping(value = "/runBindNewUser", method = RequestMethod.POST)
	@ResponseBody
	public String runBindNewUser( String accountId, User user, UserDetail detail, String birthAreaId,
			String liveAreaId, String orgId, String mark, String code ) throws Exception {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 检查账号和用户的类型匹配性
		Account account = accountService.findById(accountId);
		if ( !accountService.isAccountTypeCoincidentWithUserType(account.getType(), user.getType()) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "账号和用户类型不一致，不能绑定！");
			return resultJson.toString();
		}

		// 验证验证码是否正确
		if (!SmsUtil.getInstance().checkCode(mark, user.getMobile(), code)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "短信验证码输入错误！");
			return resultJson.toString();
		}

		// 用户信息
		if ( user.getType() == UserTypeEnum.NATURAL.getNumber() ) {
			user.setBirthArea(areaService.findById(birthAreaId));
			user.setLiveArea(areaService.findById(liveAreaId));
			
			if ( StringUtil.isNotBlank(orgId) ) {
				Organization org = organizationService.findById(orgId);
				user.setOrg(org);
			}
		}

		// 验证用户的唯一性
		StringBuilder errInfo = new StringBuilder();
		if (!userService.checkUnique(user, errInfo)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}

		// 新增用户并绑定
		if ( accountService.bindNewUser(accountId, user, detail) ) {
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("info", "操作成功！");
			resultJson.put("data", account.getUser().getUuid());
			return resultJson.toString();
		} else {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "操作失败！");
			return resultJson.toString();
		}
	}
	
	/**
	 * 保存、更新用户信息
	 * 
	 * @param model
	 * @param info
	 * @param areaId
	 * @return
	 */
	@SystemLog(description = "用户", module = OperationLog.USER_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser( String operate, String userId, String detailId, User user, UserDetail detail, String birthAreaId, String liveAreaId
			, String orgId, String mark, String code ) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 验证验证码是否正确
		try {
			if (!SmsUtil.getInstance().checkCode(mark, user.getMobile(), code)) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "短信验证码输入错误！");
				return resultJson.toString();
			}
		} catch (Exception e) {
			logger.info("短信接口异常"+e.getMessage());
		}
		
		// 处理详细信息
		detail.setUuid(detailId);
		
		// 处理基本信息
		user.setUuid(userId);
		user.setDetail(detail);
		
		// 自然人用户才有的属性
		if ( user.getType() == UserTypeEnum.NATURAL.getNumber() ) {
			user.setBirthArea(areaService.findById(birthAreaId));
			user.setLiveArea(areaService.findById(liveAreaId));
			if ( StringUtil.isNotBlank(orgId) ) {
				user.setOrg(organizationService.findById(orgId));
			}
		}
		
		// 验证唯一性
		StringBuilder errInfo = new StringBuilder();
		if ( !userService.checkUnique(user, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
			
		// 保存
		userService.saveOrUpdateCompleteUser(user, detail);
		
		// 返回保存成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.SAVE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 通过验证码方式绑定某个已经存在的用户
	 * 
	 * @param model
	 * @param accountId
	 *            要绑定的账号
	 * @param identity
	 *            绑定用户的身份证号：自然人用户有效
	 * @param orgCode 
	 * 			    组织机构代码：法人用户有效
	 * @param corporateName 
	 * 			    企业/机构名称
	 * @param mark
	 *            验证码发送方的身份识别码
	 * @param code
	 *            提交的验证码
	 * @return
	 */
	@RequiresPermissions(value = { WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN }, logical = Logical.OR)
	@RequestMapping(value = "/runBindExistUser", method = RequestMethod.POST)
	@ResponseBody
	public String runBindExistUser(Model model, String accountId, String identity, String orgCode, String corporateName,
			String mark, String code, String phoneNumber) throws Exception {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		
		// 根据查找用户记录
		Account account = accountService.findById(accountId);
		User user = null;
		if ( account.getType() == AccountTypeEnum.CORPORATE.getNumber() ) {
			user = userService.findCorporateUserByOrgCode(orgCode);
			if (user == null) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "组织机构代码不存在！");
				return resultJson.toString();
			}
			
			if ( !user.getCorporateName().equals(corporateName) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "企业/法人名称或组织机构代码错误！");
				return resultJson.toString();
			}
		} else {
			user = userService.findNaturalUserByIdentity(identity);
			if (user == null) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "身份证号不存在！");
				return resultJson.toString();
			}
		}

		// 验证手机号码的一致性
		if (!user.getMobile().equals(phoneNumber)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "此手机号与用户信息不匹配！");
			return resultJson.toString();
		}

		// 验证验证码
		if (!SmsUtil.getInstance().checkCode(mark, user.getMobile(), code)) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "短信验证码输入错误！");
			return resultJson.toString();
		}

		// 绑定用户
		boolean ret = true;
		if ( account.getType() == AccountTypeEnum.CORPORATE.getNumber() ) {
			ret = accountService.bindExistCorporateUser(accountId, orgCode);
		} else {
			ret = accountService.bindExistNaturalUser(accountId, identity);
		}
		if ( ret ) {
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("info", "绑定用户成功！");
			resultJson.put("data", account.getUser().getUuid());
			return resultJson.toString();
		} else {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "绑定用户失败！");
			return resultJson.toString();
		}
	}

	/**
	 * 导入账号
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value = "/importAccounts", method = RequestMethod.POST)
	@ResponseBody
	public String importAccounts(HttpServletRequest request, HttpServletResponse response,Model model, int type,String appId) {
		//返回前天的json数据
		String json = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		try {
			@SuppressWarnings("rawtypes")
			List fileList = upload.parseRequest(request);
			@SuppressWarnings("unchecked")
			Iterator<FileItem> it = fileList.iterator();
			 while (it.hasNext()){
				 FileItem item = (FileItem)it.next();
				 if (!item.isFormField()){
					 InputStream is = item.getInputStream();
					json =  excelService.importAccountExcel(is,item.getName(),appId,type);
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 导出账号
	 * 
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value = "/exportAccounts", method = RequestMethod.GET)
	@ResponseBody
	public String exportAccounts(HttpServletRequest request, HttpServletResponse response, Model model, int type,
			String ids,String exportField) {
		JSONObject resultJson = new JSONObject();
		// 输出Excel文件
		OutputStream output = null;
		String name = AccountTypeEnum.nameOf(type);
		try {
			String exportName = ExportMapping.getAccountPropertiesName(exportField);
			output = response.getOutputStream();
			response.reset();
			String downLoadName = new String((name+"列表.xlsx").getBytes("gbk"), "iso8859-1");
			response.setHeader("Content-disposition", "attachment; filename=" + downLoadName);
			response.setContentType("application/x-download");
			XSSFWorkbook wb = excelService.exportAccountExcel(ids,type,exportName,exportField);
			// 保存Excel文件
			wb.write(output);
		} catch (IOException e) {
			logger.info("账号导出失败：" + e);
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", "导出账号信息失败！");
			return resultJson.toString();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "账号导出成功！");
		return resultJson.toString();	
	}

	// 账号导入模板的下载
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,int type) throws IOException {
		String url = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		url += "static/template/account/" + AccountTypeEnum.valueOf(type) + ".xlsx";
		File file = new File(url);
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		String downLoadName = new String(file.getName().getBytes("gbk"), "iso8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + downLoadName);

		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		br.close();
		out.close();
	}
	
	/**
	 * 初始化表单
	 * @param model
	 * @param type
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value = { "/initImportExcel" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String initImportExcel(Model model, int type) {
		List<Application> list = appService.findAll();
		model.addAttribute("appLists", list);
		model.addAttribute("type", type);
		return "manager/account/" + AccountTypeEnum.valueOf(type) + "/import";
	}
	
	/**
	 * 合并账号
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/mergeAccounts", method=RequestMethod.POST)
	@ResponseBody
	public String mergeAccounts(Model model,String tableinfo1, int type) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		accountService.mergeAfterUpdate(tableinfo1);
		// 返回保存成功结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.SAVE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 关闭窗口
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/closeWindows", method=RequestMethod.POST)
	@ResponseBody
	public String closeWindows(Model model, int type) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		boolean flag = accountService.closeWindows();
		if(flag){
			resultJson.put("state", MessageInfo.STATE_SUCCESS);//成功
		}else{
			resultJson.put("state", MessageInfo.STATE_FAIL);//失败
			resultJson.put("info", "程序异常");
		}
		return resultJson.toString();
	}
	
	/**
	 * 初始化修改表单
	 * @param model
	 * @param type 模式
	 * @return
	 */
	 @RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	 @RequestMapping(value={"/initProblemAndMerge"}, method=RequestMethod.GET)
	 public String initProblemAndMerge(Model model, int type) {
	    model.addAttribute("type", Integer.valueOf(type));
	    return "manager/account/" + AccountTypeEnum.valueOf(type) + "/modify";
	  }
	 
	 /**
	  * 返回有问题的账号提示信息和需要合并的账号信息
	  * @param model
	  * @param type
	  * @return
	  */
	 @RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	 @RequestMapping(value = "/errorOrMergeData", method = RequestMethod.POST)
	 @ResponseBody
	 public String errorOrMergeData(Model model, int type) {
		JSONObject resultJson = new JSONObject();
		List<AccountErrorTemp> errorList = accountService.findErrAll();
		List<AccountMergeTemp> MergeList = accountService.findMergeAll();
		net.sf.json.JSONArray errArr = net.sf.json.JSONArray.fromObject(errorList);
		net.sf.json.JSONArray mergeArr = net.sf.json.JSONArray.fromObject(MergeList);
	    resultJson.put("problem", net.sf.json.JSONArray.toArray(errArr, AccountErrorTemp.class));
	    resultJson.put("merge", net.sf.json.JSONArray.toArray(mergeArr, AccountMergeTemp.class));
		return resultJson.toString();
	 }
}
