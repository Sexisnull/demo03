package com.gsww.uids.controller;

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
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisParameter;
import com.gsww.uids.service.JisParameterService;
/**
 * 系统参数控制器
 * @author Seven
 *
 */
@Controller
@RequestMapping(value = "/parameter")
public class JisParameterController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(JisParameterController.class);
	@Autowired
	private JisParameterService jisParameterService ;
	
	/**
	 * 获取列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/parameterList",method = RequestMethod.GET)
	public String parameterList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "iid") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC") String orderSort,
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
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisParameter.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<JisParameter>  spec=super.toNewSpecification(searchParams, JisParameter.class);
			
			//分页
			Page<JisParameter> pageInfo = jisParameterService.getParameterPage(spec,pageRequest);
			JisParameter jisParameter = pageInfo.getContent().get(0);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			model.addAttribute("jisParameter", jisParameter);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "system/jis/configuration";
	}
	
	/**
	 * 保存信息
	 */
	@RequestMapping(value = "/parameterSave", method = RequestMethod.POST)
	public ModelAndView parameterSave(JisParameter jisParameter,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		jisParameterService.save(jisParameter);
		return new ModelAndView("redirect:/parameter/parameterList");
	}
}
