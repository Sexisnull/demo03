/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysRoleService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-7-28 下午05:34:43</p>
 * <p>类描述 : 角色管理模块控制器        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Controller
@RequestMapping(value = "/sys")
public class SysRoleController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	@Autowired
	private SysRoleService sysRoleService;
		
	/**获取角色列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/roleList",method = RequestMethod.GET)
	public String roleList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest){
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysRole.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<SysRole>  spec=super.toSpecification(searchParams, SysRole.class);
			
			//分页
			Page<SysRole> pageInfo = sysRoleService.getRolePage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error","列表打开失败",(HttpServletRequest) request);
			return "redirect:/sys/roleList";
		}
		return "sys/role_list";
	}
	
	/**新增或编辑角色
	 * @param roleId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/roleEdit", method = RequestMethod.GET)
	public ModelAndView roleEdit(String roleId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView("sys/role_edit");
		try {
			SysRole sysRole = null;
			if(StringHelper.isNotBlack(roleId)){
				sysRole = sysRoleService.findByKey(roleId);
			}else{
				sysRole = new SysRole();
			}
			model.addAttribute("sysRole", sysRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑页面打开失败："+e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 保存角色信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/roleSave", method = RequestMethod.POST)
	public ModelAndView roleSave(String roleId,SysRole sysRole,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			SysRole role = new SysRole();
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			if(StringHelper.isNotBlack(roleId)){
				role = sysRoleService.findByKey(roleId);
			}else{
				role.setRoleState("1");
			}
			role.setRoleName(sysRole.getRoleName());
			role.setRoleDesc(sysRole.getRoleDesc());
			role.setCreateTime(TimeHelper.getCurrentTime());
			sysRoleService.save(role);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/roleList");
		}
		
	}

	/**
	 * 删除角色信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/roleDelete", method = RequestMethod.GET)
	public ModelAndView roleDelete(String roleId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		String resMsg = "";
		try {
			String[] para=roleId.split(",");
			for(int i=0;i<para.length;i++){
				List<SysRoleAcctRel> acct = sysRoleService.findAcctByroleId(para[i].trim());
				SysRole role = sysRoleService.findByKey(para[i].trim());
				if(acct!=null && acct.size()>0){
					resMsg += "名称为“"+role.getRoleName()+"”的角色下存在用户，不能删除！   </br>   ";
				}else{
					sysRoleService.delete(para[i].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMsg="删除失败";
		} finally{
			if(resMsg.equals("")){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error",resMsg,request);
			}
			return  new ModelAndView("redirect:/sys/roleList");
		}
		
	}
	
	/**
	 * 账号是否被使用
	 * @param loginAccount
	 * @param setId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checkRole", method = RequestMethod.GET)
	public void checkRole(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String roleNameInput=StringUtils.trim((String)request.getParameter("roleName"));
			String roleNameold=StringUtils.trim((String)request.getParameter("oldRoleName"));
			if(!roleNameInput.equals(roleNameold)){
				List<SysRole> sysRoleList = sysRoleService.findByRoleNameAndRoleState(roleNameInput,"1");
				if(sysRoleList!=null && sysRoleList.size()>0){
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
	
	/**获取权限弹出窗
	 * @param roleId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/roleAuthorizeShow",method = RequestMethod.GET)
	public String roleAuthorizeShow(String roleId,HttpServletRequest request,HttpServletResponse response,Model model)  throws Exception {
		try {
			model.addAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role_authorize";
	}
	
	/**获取权限树Json
	 * @param roleId
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value="/roleAuthorizeList",method = RequestMethod.POST)
	public void roleAuthorizeList(String roleId,HttpServletRequest request,HttpServletResponse response,Model model)  throws Exception {
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String treeJson= sysRoleService.getAuthorizeTree(roleId);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(treeJson);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**保存权限
	 * @param roleId
	 * @param nodes
	 * @throws Exception
	 */
	@RequestMapping(value="/roleAuthorizeSave",method = RequestMethod.POST)
	public void roleAuthorizeSave(String roleId,String keys,String types,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			if (roleId == null || keys == null || types == null) {
				response.getWriter().write("error");
			} else {
				sysRoleService.saveAuthorize(roleId, keys.trim(), types.trim());
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
