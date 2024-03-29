package com.gsww.uids.controller;

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
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.entity.JisSysviewHistory;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewHistoryService;
	@Controller
	@RequestMapping(value = "/sysviewHis")
	public class JisSysviewHistoryController extends BaseController{
		private static Logger logger = LoggerFactory.getLogger(JisSysviewHistoryController.class);
		@Autowired
		private JisSysviewHistoryService jisSysviewHistoryService;
		@Autowired
		private JisApplicationService jisApplicationService;
		@Autowired
		private SysParaService sysParaService;
		@Autowired
		private JisSysviewDetailService jisSysviewDetailService;
		
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
				PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisSysviewCurrent.class,findNowPage);
				
				//搜索属性初始化
				
				Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
				Specification<JisSysviewHistory>  spec=super.toSpecification(searchParams, JisSysviewHistory.class);
				
				//map放入
				List<Map<String, Object>> applicationList =new ArrayList<Map<String,Object>>() ;
				List<Map<String, Object>> paraList =new ArrayList<Map<String,Object>>() ;
				Map<Integer,Object> applicationMap = new HashMap<Integer,Object>();
				Map<Integer,Object> paraMap = new HashMap<Integer,Object>();
				applicationList=jisApplicationService.getJisApplicationList();
				paraList=sysParaService.getParaList("OPT_RESULT");
				for(Map<String,Object> application:applicationList){
					applicationMap.put((Integer) application.get("iid"), application.get("name"));
				}			
				for(Map<String,Object> para:paraList){
					paraMap.put(Integer.parseInt((String) para.get("PARA_CODE")),  para.get("PARA_NAME"));
				}
				model.addAttribute("applicationMap", applicationMap);
				model.addAttribute("paraMap", paraMap);
			
				/**下拉列表初始化*/
				List<Map<String, Object>> applications = jisApplicationService.getJisApplicationList();
				List<Map<String, Object>> parameters = sysParaService.getParaList("OPT_RESULT");
				model.addAttribute("applications", applications);
				model.addAttribute("parameters", parameters);
				
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
				return "redirect:/sysviewHis/jisHisList";
			}
			return "users/sysview/jis_sysview_history_list";
		}

		
		/**
		 * 同步详情
		 * @param iid
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/sysviewDetail", method = RequestMethod.GET)
		public String accountEdit(int iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
			
			JisSysviewHistory sysviewHistory = jisSysviewHistoryService.findByIid(iid); 
			
			//map放入
			List<Map<String, Object>> applicationList =new ArrayList<Map<String,Object>>() ;
			List<Map<String, Object>> paraList =new ArrayList<Map<String,Object>>() ;
			Map<Integer,Object> applicationMap = new HashMap<Integer,Object>();
			Map<Integer,Object> paraMap = new HashMap<Integer,Object>();
			applicationList=jisApplicationService.getJisApplicationList();
			paraList=sysParaService.getParaList("OPT_RESULT");
			for(Map<String,Object> application:applicationList){
				applicationMap.put((Integer) application.get("iid"), application.get("name"));
			}			
			for(Map<String,Object> para:paraList){
				paraMap.put(Integer.parseInt((String) para.get("PARA_CODE")),  para.get("PARA_NAME"));
			}
			
			Map<String,String> detailMap = new HashMap<String, String>();
			detailMap.put("returnUrl", "sysviewHis/jisHisList");
			detailMap.put("syncType", "history");
			model.addAttribute("detailMap",detailMap);
			model.addAttribute("applicationMap", applicationMap);
			model.addAttribute("paraMap", paraMap);
			model.addAttribute("jisSysview",sysviewHistory);
			if( null != sysviewHistory){
				JisSysviewDetail jisSysviewDetail = jisSysviewDetailService.findByTranscationId(sysviewHistory.getTranscationId());
				model.addAttribute("jisSysviewDetail",jisSysviewDetail);
			}
			return "users/sysview/jis_sysview_detail";
		} 
	}



