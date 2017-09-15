package com.gsww.jup.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.service.sys.DepartmentService;
import com.gsww.jup.service.sys.SysAccountService;
import com.gsww.jup.service.sys.SysRoleService;
import com.gsww.jup.util.MD5;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-6-08 下午10:55:12</p>
 * <p>类描述 :   账号管理模块控制器    </p>
 *
 * @version 3.0.0
 * @author <a href=" ">zhangtb</a>
 */
@Controller
@RequestMapping(value = "/sys")
public class SysAccountController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);
	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private DepartmentService departmentService;
	private Map<String,String> resMap=new HashMap<String,String>();

	/**
	 * 获取用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accountList",method = RequestMethod.GET)
	public String accountList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			if(StringUtils.isNotBlank(request.getParameter("orderField"))){
				orderField=(String)request.getParameter("orderField");
			}
			if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
				orderSort=(String)request.getParameter("orderSort");
			}
			//初始化分页数据
			
			

			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysAccount.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("NE_loginAccount", "admin");
			Specification<SysAccount>  spec=super.toNewSpecification(searchParams, SysAccount.class);
			
			//分页
			Page<SysAccount> pageInfo = sysAccountService.getUserPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			Map<String,String> userRolemap=new HashMap<String,String>();
			for (SysAccount sysAccount : pageInfo.getContent()) {
				List<SysRole> roleList=new ArrayList<SysRole>();
				roleList=sysAccountService.findAccountRoleList(sysAccount.getUserAcctId());
				String userRoleName="";
				for (SysRole sysRole : roleList) {
					userRoleName+=sysRole.getRoleName()+",";
				}
				//userRoleName=userRoleName.substring(0, userRoleName.length()-1);
				if(StringUtils.isNotBlank(userRoleName)){
					userRoleName=userRoleName.substring(0, userRoleName.length()-1);
					userRolemap.put(sysAccount.getUserAcctId(), userRoleName);
				}else{
					userRolemap.put("0", "");
				}
			}
			model.addAttribute("userRolemap", userRolemap);
			model.addAttribute("sortType", orderField);
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderSort", orderSort);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "sys/account_list";
	}
	
	/**
	 * 转到新增或编辑用户页面
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountEdit", method = RequestMethod.GET)
	public String accountEdit(String userAcctId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		//ModelAndView mav=new ModelAndView("sys/account_edit");
		try {
			SysAccount sysAccount = null;
			if(StringHelper.isNotBlack(userAcctId)){
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				sysAccount = sysAccountService.findByKey(userAcctId);
				Map<String,String> userRolemap=new HashMap<String,String>();
				List<SysRole> roleList=new ArrayList<SysRole>();
				roleList=sysAccountService.findAccountRoleList(sysAccount.getUserAcctId());
				String userRoleName="";
				String userRoleId="";
				for (SysRole sysRole : roleList) {
					userRoleName+=sysRole.getRoleName()+",";
					userRoleId+=sysRole.getRoleId()+",";
				}
				if(StringUtils.isNotBlank(userRoleName)){
					userRoleName=userRoleName.substring(0, userRoleName.length()-1);
					userRolemap.put(sysAccount.getUserAcctId(), userRoleName);
					userRoleId=userRoleId.substring(0,userRoleId.length()-1);
				}else{
					userRolemap.put("0", "");
				}
				model.addAttribute("userRoleList", roleList);
				model.addAttribute("userRolemap", userRolemap);
				model.addAttribute("userRoleId", userRoleId);
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				sysAccount = new SysAccount();
			}
			model.addAttribute("sysAccount", sysAccount);
			List<SysRole> list=new ArrayList<SysRole>();
			list=sysRoleService.findRoleList();
			model.addAttribute("sysRoleList",list);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/account_edit";
	}
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/accountSave", method = RequestMethod.POST)
	public ModelAndView accountSave(String userAcctId,SysAccount sysAccount,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(!StringHelper.isNotBlack(sysAccount.getUserState())){
				sysAccount.setUserState("1");
			}
			if(!StringHelper.isNotBlack(sysAccount.getLoginPassword())){
				MD5 m = new MD5();
				sysAccount.setLoginPassword(m.getMD5ofStr("111111"));
			}
			if(StringUtils.isBlank(sysAccount.getUserSex())){
				sysAccount.setUserSex("2");
			}
			SysDepartment sysDepartment=null;
			String deptId=request.getParameter("deptId");
			//sysDepartment=departmentService.findByKey(deptId);
			sysAccount.setSysDepartment(sysDepartment);
			//1、先保存用户
			sysAccount.setUserSex("0");
			sysAccount.setCreateTime(TimeHelper.getCurrentTime());
			sysAccount = sysAccountService.save(sysAccount);
			    
			//2、保存用户角色关系表
			sysAccountService.deleteAccountRole(sysAccount);
			String userRoles=request.getParameter("userRole");
			if(StringUtils.isNotBlank(userRoles)){
				String[] roles=userRoles.split(",");
				for (String roleId : roles) {
					sysAccountService.saveUserRole(sysAccount.getUserAcctId(), roleId);
				}
			}
			//System.out.println("Saveuser:"+sysAccount.getUserAcctId());
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/accountList");
		}
		
	}
	/**
	 * 初始化密码
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/initPassWord", method = RequestMethod.GET)
	public ModelAndView initPassWord(String userAcctId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=userAcctId.split(",");
			for(int i=0;i<para.length;i++){
				sysAccountService.initPassWord("111111",para[i].trim());
			}			
			returnMsg("success","初始化成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","初始化失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/accountList");
		}
	}
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/accountDelete", method = RequestMethod.GET)
	public ModelAndView accountDelete(String userAcctId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=userAcctId.split(",");
			SysAccount sysAccount = null;
			boolean flag=false;
			for(int i=0;i<para.length;i++){
				sysAccount=sysAccountService.findByKey(para[i].trim());
				if("admin".equals(sysAccount.getLoginAccount())){
					
					flag=false;
					break;
				}else{
					sysAccountService.delete(sysAccount);
					flag=true;
				}
			}
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,系统管理员不允许删除",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/sys/accountList");
		}
		
	}
	
	/**
	 * 查询账号状态
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/checkAccountState", method = RequestMethod.POST)
	public void checkAccountState(String userAcctId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=userAcctId.split(",");
			SysAccount sysAccount = null;
			for(int i=0;i<para.length;i++){
				sysAccount=sysAccountService.findByKey(para[i].trim());
			}
			if("1".equals(sysAccount.getUserState())){
				response.getWriter().write("start");
			}else if("0".equals(sysAccount.getUserState())){
				response.getWriter().write("stop");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(-1);
		}
	}
	/**
	 * 启用账号
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/accountStart", method = RequestMethod.GET)
	public ModelAndView accountStart(String userAcctId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=userAcctId.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysAccount sysAccount=sysAccountService.findByKey(para[i].trim());
				if("1".equals(sysAccount.getUserState())){
					msg += "账号为“"+sysAccount.getLoginAccount()+"”的用户已经启用！   </br>   ";
				}else if("0".equals(sysAccount.getUserState())){
					sysAccountService.startAccount(para[i].trim());
					msg += "账号为“"+sysAccount.getLoginAccount()+"”的用户启用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "启用失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/accountList");
		}
	}
	
	/**
	 * 停用账号
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/accountStop", method = RequestMethod.GET)
	public ModelAndView accountStop(String userAcctId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=userAcctId.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysAccount sysAccount=sysAccountService.findByKey(para[i].trim());
				if("0".equals(sysAccount.getUserState())){
					msg += "账号为“"+sysAccount.getLoginAccount()+"”的用户已经停用！   </br>   ";
				}else if("1".equals(sysAccount.getUserState())){
					sysAccountService.stopAccount(para[i].trim());
					msg += "账号为“"+sysAccount.getLoginAccount()+"”的用户停用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "停用失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/accountList");
		}
	}
	
	/**
	 * 查看账号情信息
	 */
	@RequestMapping(value = "/accountView", method = RequestMethod.GET)
	public String accountView(String userAcctId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		try {
			SysAccount sysAccount = null;
			if(StringHelper.isNotBlack(userAcctId)){
				sysAccount = sysAccountService.findByKey(userAcctId);
			}else{
				sysAccount = new SysAccount();
			}
			model.addAttribute("sysAccount", sysAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/account_view";
	}
	
	/**
	 * 账号是否被使用
	 * @param loginAccount
	 * @param setId
	 * @param request
	 * @param response 1表示不重复，0表示重复
	 * @throws Exception
	 */
	@RequestMapping(value="/checkAccount", method = RequestMethod.GET)
	public void checkAccount(String loginAccount,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			SysAccount sysAccount = null;
			String loginAccountInput=StringUtils.trim((String)request.getParameter("loginAccount"));
			String oldLoginAccount=StringUtils.trim((String)request.getParameter("oldLoginAccount"));
			if(!loginAccountInput.equals(oldLoginAccount)){
				sysAccount=sysAccountService.queryLoginAccountIsUsed(loginAccount);
				if(sysAccount!=null){					
					response.getWriter().write("0");								
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
