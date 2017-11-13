/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

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
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.service.sys.SysParaTypeService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-9-9 下午03:18:26</p>
 * <p>类描述 : 参数类型管理模块控制器        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
@Controller
@RequestMapping(value = "/sys")
public class SysParaTypeController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysParaTypeController.class);
	@Autowired
	private SysParaTypeService sysParaTypeService;
	@Autowired
	private SysParaService sysParaService;
	private String alertType = "";//提示控制参数
		
	/**获取参数类型列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/paraTypeList",method = RequestMethod.GET)
	public String paraTypeList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "paraTypeName,paraTypeId") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC,ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,

			Model model,ServletRequest request,HttpServletRequest hrequest){
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysParaType.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("EQ_paraTypeState", "1");
			Specification<SysParaType>  spec=super.toSpecification(searchParams, SysParaType.class);
			
			//分页
			Page<SysParaType> pageInfo = sysParaTypeService.getParaTypePage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("alertType", alertType);
			alertType = "";
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "sys/para_type_list";
	}
	/**新增或编辑参数类型
	 * @ParaTypem ParaTypeId
	 * @ParaTypem model
	 * @ParaTypem request
	 * @ParaTypem response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paraTypeEdit", method = RequestMethod.GET)
	public ModelAndView roleEdit(String paraTypeId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView("sys/para_type_edit");
		try {
			SysParaType sysParaType = null;
			if(paraTypeId!=null){
				sysParaType = sysParaTypeService.findByKey(paraTypeId);
			}
			model.addAttribute("sysParaType", sysParaType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑页面打开失败："+e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 保存参数类型
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/paraTypeSave", method = RequestMethod.POST)
	public ModelAndView paraTypeSave(String paraTypeId,SysParaType sysParaType,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			SysParaType s=new SysParaType();
			if(StringHelper.isNotBlack(paraTypeId)){
				s=sysParaTypeService.findByKey(paraTypeId);
			}else{
				s.setParaTypeState("1");
			}
			s.setParaTypeName(sysParaType.getParaTypeName());
			s.setParaTypeDesc(sysParaType.getParaTypeDesc());
			sysParaTypeService.save(s);
			returnMsg("success","保存成功",request);	
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/paraTypeList");
		}
		
	}
	
	/**
	 * 删除参数类型信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/paraTypeDelete", method = RequestMethod.GET)
	public ModelAndView paraTypeDelete(String paraTypeId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=paraTypeId.split(",");			
			for(int i=0;i<para.length;i++){
				//1、先删除参数
				SysParaType s=new SysParaType();
				s=sysParaTypeService.findByKey(para[i]);
				sysParaService.updateStateBySysParaType(s);
				//2、再删除参数类型
				sysParaTypeService.delete("0",para[i].trim());
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);	
		} finally{
			return  new ModelAndView("redirect:/sys/paraTypeList");
		}
		
	}
	/**
	 * 参数编码是否已使用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checkParaTypeName", method = RequestMethod.GET)
	public void checkParaCode(String paraTypeName,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			SysParaType sysParaType = null;
			String paraTypeNameInput=StringUtils.trim((String)request.getParameter("paraTypeName"));
			String oldParaTypeName=StringUtils.trim((String)request.getParameter("oldParaTypeName"));
			if(!paraTypeNameInput.equals(oldParaTypeName)){
				sysParaType = sysParaTypeService.checkParaTypeName(paraTypeName);
				if(sysParaType!=null){
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
