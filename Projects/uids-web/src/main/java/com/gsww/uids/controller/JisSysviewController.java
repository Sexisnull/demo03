package com.gsww.uids.controller;

	import java.util.Map;

	import javax.servlet.ServletRequest;
	import javax.servlet.http.HttpServletRequest;

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
	import org.springside.modules.web.Servlets;

	import com.gsww.jup.controller.BaseController;
	import com.gsww.jup.controller.sys.SysAccountController;

import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.service.JisSysviewService;



	@Controller
	@RequestMapping(value = "/uids")
	public class JisSysviewController extends BaseController{
		private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);
		@Autowired
		private JisSysviewService jisSysviewService;
		
		@RequestMapping(value="/jisSysviewList",method = RequestMethod.GET)
		public String jisSysviewList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
				@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
				@RequestParam(value = "order.field", defaultValue = "syncTime") String orderField,
				@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
				@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
				Model model,ServletRequest request,HttpServletRequest hrequest){
			try{
				//初始化分页数据
				PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
				PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisSysview.class,findNowPage);
				
				//搜索属性初始化
				Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
				Specification<JisSysview>  spec=super.toSpecification(searchParams, JisSysview.class);
				
				//分页
				Page<JisSysview> pageInfo = jisSysviewService.getJisPage(spec,pageRequest);
				model.addAttribute("pageInfo", pageInfo);
				// 将搜索条件编码成字符串，用于排序，分页的URL
				model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
				model.addAttribute("sParams", searchParams);
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error("列表打开失败："+ex.getMessage());
				returnMsg("error","列表打开失败",(HttpServletRequest) request);
				return "redirect:/sys/jisSysviewList";
			}
			return "sys/jis_sysview_list";
		}

	}



