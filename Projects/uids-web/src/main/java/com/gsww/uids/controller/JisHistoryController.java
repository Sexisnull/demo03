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
	import com.gsww.uids.entity.JisSysviewHistory;
	import com.gsww.uids.service.JisSysviewHistoryService;
	@Controller
	@RequestMapping(value = "/uids")
	public class JisHistoryController extends BaseController{
		private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);
		@Autowired
		private JisSysviewHistoryService jisSysviewHistoryService;
		
		@RequestMapping(value="/jisHisList",method = RequestMethod.GET)
		public String jisHisList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
				@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
				@RequestParam(value = "order.field", defaultValue = "synctime") String orderField,
				@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
				@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
				Model model,ServletRequest request,HttpServletRequest hrequest){
			try{
				//初始化分页数据
				PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
				PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisSysviewHistory.class,findNowPage);
				
				//搜索属性初始化
				Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
				Specification<JisSysviewHistory>  spec=super.toSpecification(searchParams, JisSysviewHistory.class);
				
				//分页
				Page<JisSysviewHistory> pageInfo = jisSysviewHistoryService.getJisPage(spec,pageRequest);
				model.addAttribute("pageInfo", pageInfo);
				// 将搜索条件编码成字符串，用于排序，分页的URL
				model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
				model.addAttribute("sParams", searchParams);
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error("列表打开失败："+ex.getMessage());
				returnMsg("error","列表打开失败",(HttpServletRequest) request);
				return "redirect:/uids/jisHisList";
			}
			return "users/sysview/jis_sysview_history_list";
		}

	}



