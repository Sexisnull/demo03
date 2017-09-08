package com.gsww.uids.controller;

import java.util.HashMap;
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
import com.gsww.jup.service.sys.DepartmentService;
import com.gsww.jup.service.sys.SysAccountService;
import com.gsww.jup.service.sys.SysRoleService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2017-09-07 下午14:18:12</p>
 * <p>类描述 :   法人管理模块控制器    </p>
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Controller
@RequestMapping(value = "/complat")
public class ComplatCorporationController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ComplatCorporationController.class);
	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private DepartmentService departmentService;
	private Map<String,String> resMap=new HashMap<String,String>();
	
	@Autowired
	private ComplatCorporationService complatCorporationService;

	/**
	 * 获取法人列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/corporationList",method = RequestMethod.GET)
	public String corporationList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatCorporation.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatCorporation>  spec=super.toNewSpecification(searchParams, ComplatCorporation.class);
			
			//分页
			Page<ComplatCorporation> pageInfo= complatCorporationService.getCorporationPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/corporationList";
		}
		return "users/corporation/corporation_list";
	}
	
	/**
	 * 转到新增法人页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/corporationEdit", method = RequestMethod.GET)
	public ModelAndView accountEdit(String corporationId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav=new ModelAndView("users/corporation/corporation_edit");
		ComplatCorporation corporation = null;
		try {
			if(StringHelper.isNotBlack(corporationId)){
				Integer iid = Integer.parseInt(corporationId);
				corporation = complatCorporationService.findByKey(iid);
			}else{
				corporation = new ComplatCorporation();
			}
			model.addAttribute("corporation",corporation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationSave", method = RequestMethod.POST)
	public ModelAndView accountSave(ComplatCorporation corporation,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(corporation != null){
				complatCorporationService.save(corporation);
				returnMsg("success","保存成功",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/corporationList");
		}
		
	}
	
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationDelete", method = RequestMethod.GET)
	public ModelAndView accountDelete(String corporationId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=corporationId.split(",");
			ComplatCorporation corporation = null;
			for(int i=0;i<para.length;i++){
				Integer corId = Integer.parseInt(para[i].trim());
				corporation=complatCorporationService.findByKey(corId);
				if(corporation != null){
					complatCorporationService.delete(corporation);
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/complat/corporationList");
		}
		
	}
	
}
