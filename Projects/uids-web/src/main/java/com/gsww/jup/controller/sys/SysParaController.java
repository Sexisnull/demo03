/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

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
import com.gsww.jup.entity.sys.SysPara;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.service.sys.SysParaTypeService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-9-9 下午04:38:45</p>
 * <p>类描述 : 参数管理模块控制器        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
@Controller
@RequestMapping(value = "/sys")
public class SysParaController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysParaController.class);
	@Autowired
	private SysParaService sysParaService;
	@Autowired
	private SysParaTypeService sysParaTypeService;
	private String alertType = "";//提示控制参数
		
	/**获取参数列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/paraList",method = RequestMethod.GET)
	public String paraList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "paraSeq,paraId") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC,ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest){
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysPara.class,findNowPage);
			
			//搜索属性初始化 LIKE_paraName=ce
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("EQ_paraState", "1");
			searchParams.remove("paraTypeId");
			Specification<SysPara>  spec=null;
			if(searchParams.containsKey("EQ_sysParaType.paraTypeId")){
				//已经点击简单搜索了，非首次进入页面
				 spec=super.toSpecification(searchParams, SysPara.class);
			}else{
				//首次进入页面
				searchParams.put("EQ_sysParaType.paraTypeId", request.getParameter("paraTypeId"));
				  spec=super.toSpecification(searchParams, SysPara.class);
			}
			searchParams.put("paraTypeId",  (String)searchParams.get("EQ_sysParaType.paraTypeId"));
			model.addAttribute("paraTypeId", searchParams);
			//分页
			Page<SysPara> pageInfo = sysParaService.getParaPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("alertType", alertType);
			alertType = "";
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "sys/para_list";
	}
	/**新增或编辑参数
	 * @param paraTypeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paraEdit", method = RequestMethod.GET)
	public ModelAndView paraEdit(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView("sys/para_edit");
		try {
			String paraId=(String)request.getParameter("paraId");
			String paraTypeId=(String)request.getParameter("paraTypeId");
			SysParaType sysParaType=null;
			SysPara sysPara = new SysPara();
			if(!paraId.equals("") && !paraId.equals("0")){
				//修改
				sysPara = sysParaService.findByKey(paraId);
			}else{
				//新增
				if(!paraTypeId.equals("")){
					sysParaType=sysParaTypeService.findByKey(paraTypeId);
					sysPara.setSysParaType(sysParaType);
				}
			}
			mav.addObject("sysPara",sysPara);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑页面打开失败："+e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 保存参数信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/paraSave", method = RequestMethod.POST)
	public ModelAndView paraSave(String paraId,SysPara sysPara,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			SysPara s=new SysPara();
			if(StringHelper.isNotBlack(paraId)){
				s=sysParaService.findByKey(paraId);
			}else{
				s.setParaState("1");
			}
			s.setParaName(sysPara.getParaName());
			SysParaType sysParaType=null;
			sysParaType=sysParaTypeService.findByKey(sysPara.getSysParaType().getParaTypeId());
			s.setSysParaType(sysParaType);
			s.setParaCode(sysPara.getParaCode());
			s.setParaSeq(sysPara.getParaSeq());
			sysParaService.save(s);
			returnMsg("success","保存成功",request);			
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/sys/paraList?paraTypeId="+sysPara.getSysParaType().getParaTypeId());
		}
	}
	/**
	 * 删除参数信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/paraDelete", method = RequestMethod.GET)
	public ModelAndView paraTypeDelete(HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String paraId=(String)request.getParameter("paraId");
			String[] para=paraId.split(",");
			for(int i=0;i<para.length;i++){
				sysParaService.delete("0", para[i]);
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);		
		} finally{
			return  new ModelAndView("redirect:/sys/paraList?paraTypeId="
					+request.getParameter("paraTypeId"));
		}
		
	}
	/**
	 * 参数编码是否已使用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checkParaCode", method = RequestMethod.GET)
	public void checkParaCode(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String paraCodeInput=StringUtils.trim((String)request.getParameter("paraCode"));
			String oldParaCode=StringUtils.trim((String)request.getParameter("oldParaCode"));
			String paraTypeId=StringUtils.trim((String)request.getParameter("paraTypeId"));
			SysParaType sysParaType=null;
			if(paraTypeId!=null){
				sysParaType=sysParaTypeService.findByKey(paraTypeId);
			}
			if(!paraCodeInput.equals(oldParaCode)){
				List<SysPara> sysParaList = sysParaService
					.findByParaCodeAndParaStateAndSysParaType(paraCodeInput, "1", sysParaType);
				if(sysParaList!=null && sysParaList.size()>0){
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
	/**
	 * 参数名称是否已使用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checkParaName", method = RequestMethod.GET)
	public void checkParaName(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String paraNameInput=StringUtils.trim((String)request.getParameter("paraName"));
			String oldParaName=StringUtils.trim((String)request.getParameter("oldParaName"));
			String paraTypeId=StringUtils.trim((String)request.getParameter("paraTypeId"));
			SysParaType sysParaType=null;
			if(paraTypeId!=null){
				sysParaType=sysParaTypeService.findByKey(paraTypeId);
			}
			if(!paraNameInput.equals(oldParaName)){
				List<SysPara> sysParaList = sysParaService
					.findByParaNameAndParaStateAndSysParaType(paraNameInput, "1", sysParaType);
				if(sysParaList!=null && sysParaList.size()>0){
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
