/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysMenuService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-7-21 上午8:41:56</p>
 * <p>类描述 :菜单管理模块控制器         </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangxj</a>
 */

@Controller
public class SysMenuController  extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysMenuController.class);
	@Autowired
	private SysMenuService sysMenuService;
	//页面消息展示类型
	@SuppressWarnings("unused")
	private Map<String,String> resMap=new HashMap<String,String>();
	private SysMenu sysMenu;
	
	/**
	 * 菜单分页方法
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @param orderField 排序字段，可通过",”传递多个
	 * @param orderSort 排序顺序 DESC倒序 ASC正序
	 * @param findNowPage 是否需要返回当前页
	 * @param model
	 * @param request
	 * @param hrequest
	 * @return
	 */
	@RequestMapping(value = "/menu/menuList",method = RequestMethod.GET)
	public String menuList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "parentMenuId,menuSeq,menuId") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC,ASC,ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysMenu.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			
			Specification<SysMenu>  spec=super.toSpecification(searchParams, SysMenu.class);
			
			/*
			 * //复杂条件可以用这个方法
			Specification<SysMenu>  spec = super.toNewSpecification(searchParams, SysMenu.class);
			//or条件查询可以参考以下方法
			Criterion cr1 = buildPropertyFilterCriterion(Operator.EQ,"menuName","用户管理");
			Criterion cr2 = buildPropertyFilterCriterion(Operator.EQ,"menuName","部门管理");
			Criterion cr3 = buildPropertyFilterCriterion(Operator.EQ,"menuName","角色管理");
			Criterion cr = Restrictions.or(cr1,cr2,cr3); 
			((Criteria<SysMenu>) spec).add(cr);
			*/
			
			//分页
			Page<SysMenu> pageInfo = sysMenuService.getMenuPage(spec,pageRequest);
			
			//查询父菜单
			Map<String,String> parentmap=new HashMap<String,String>();
			for(SysMenu menu:pageInfo.getContent()){				
				if(!"0".equals(menu.getParentMenuId())){
					parentmap.put(menu.getParentMenuId(), sysMenuService.findByKey(menu.getParentMenuId()).getMenuName());
				}else{
					parentmap.put("0","");
				}
			}
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("parentmap", parentmap);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
		}
		return "sys/menu_list";
	}
	
	/**
	 * 新增或编辑菜单
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu/menuEdit", method = RequestMethod.GET)
	public String accountEdit(String menuId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(StringHelper.isNotBlack(menuId)){
				sysMenu = sysMenuService.findByKey(menuId);
			}else{
				sysMenu = new SysMenu();
			}
			model.addAttribute("sysMenu", sysMenu);
			List<SysMenu> parentMenuList = sysMenuService.findFirstMenu();
			model.addAttribute("parentMenuList", parentMenuList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑页面打开失败："+e.getMessage());
		}
		return "sys/menu_edit";
	}
	
	/**
	 * 菜单保存
	 * @param menuId
	 * @param sysMenu
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally", "unused" })
	@RequestMapping(value = "/menu/menuSave", method = RequestMethod.POST)
	public ModelAndView menuSave(String menuId,SysMenu sysMenu,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			sysMenu.setMenuState("1");
			SysMenu menu = sysMenuService.save(sysMenu);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/menu/menuList");
		}
	}
	/**
	 * 删除菜单信息
	 * @param menuId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally", "unused" })
	@RequestMapping(value = "/menu/menuDelete", method = RequestMethod.GET)
	public ModelAndView menuDelete(String menuId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String[] para=menuId.split(",");
			boolean flag=false;
			for(int i=0;i<para.length;i++){
				String mId = para[i].trim();
				SysMenu sysMenu = sysMenuService.findByKey(mId);
				List<SysMenu> list=new ArrayList<SysMenu>();
				list=sysMenuService.findMenuByParentMenuId(mId);
				if(list.size()>0){
					flag=false;
					break;
				}else{
					sysMenuService.deleteOper(sysMenu);
					sysMenuService.deleteRoleMenu(sysMenu);
					sysMenuService.delete(mId);
					flag=true;
				}				
			}
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,该菜单下还有下级菜单，请先删除下级菜单",request);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","删除失败",request);
		} finally{
			return  new ModelAndView("redirect:/menu/menuList");
		}
	}

	
}
