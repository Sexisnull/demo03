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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisSysviewCurrentService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewService;

@Controller
@RequestMapping(value = "/sysviewCurr")
public class JisSysviewCurrentController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(JisSysviewCurrentController.class);
	@Autowired
	private JisSysviewCurrentService jisSysviewCurrentService;
	@Autowired
	private JisSysviewService jisSysviewService;
	@Autowired
	private JisApplicationService jisApplicationService;
	@Autowired
	private SysParaService sysParaService;
	@Autowired
	private JisSysviewDetailService jisSysviewDetailService;
	
	@RequestMapping(value="/jisCurList",method = RequestMethod.GET)
	public String jisCurList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
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
			Specification<JisSysviewCurrent>  spec=super.toSpecification(searchParams, JisSysviewCurrent.class);
			
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
			Page<JisSysviewCurrent> pageInfo = jisSysviewCurrentService.getJisPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);

			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error","列表打开失败",(HttpServletRequest) request);
			return "redirect:/uids/jisCurList";
		}
		return "users/sysview/jis_sysview_current_list";
	}

	
	/**
	 * 删除同步信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/jisCurDelete", method = RequestMethod.GET)
	public ModelAndView jisCurDelete(String iid, HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=iid.split(",");
			JisSysviewCurrent jisCurrent = null;
			for(int i=0;i<para.length;i++){
				Integer Iid = Integer.parseInt(para[i].trim());
				jisCurrent=jisSysviewCurrentService.findByIid(Iid);
				jisSysviewCurrentService.delete(jisCurrent);
				//级联删除明细
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);		
		} finally{
			return  new ModelAndView("redirect:/uids/jisCurList");
		}
		
	}
	
	/**
	 * 同步
	 * @param iid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/syncSysview")
	public ModelAndView syncSysview(int iid, HttpServletRequest request,HttpServletResponse response){
		
		try {
			JisSysviewCurrent sysviewCurrent = jisSysviewCurrentService.findByIid(iid);
			if(null != sysviewCurrent){
				//Map<String,Object> map = convertBean(sysviewCurrent);
				//JisSysview sysview = (JisSysview)convertMap(JisSysview.class,map);
				JisSysview sysview = convertToJisSysview(sysviewCurrent);
				jisSysviewService.save(sysview);
				jisSysviewCurrentService.delete(sysviewCurrent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return  new ModelAndView("redirect:/uids/jisCurList");
	}
	
	/**
	 * 批量同步
	 * @param iid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/batchSyncSysview")
	public ModelAndView batchSyncSysview(String iid, HttpServletRequest request,HttpServletResponse response){
		try {
			String[] para=iid.split(",");
			for(int i=0;i<para.length;i++){
				JisSysviewCurrent sysviewCurrent = jisSysviewCurrentService.findByIid(Integer.valueOf(para[i]));
				if(sysviewCurrent!=null){
					//Map<String,Object> map = convertBean(sysviewCurrent);
					//JisSysview sysview = (JisSysview)convertMap(JisSysview.class,map);
					JisSysview sysview = convertToJisSysview(sysviewCurrent);
					jisSysviewService.save(sysview);
					jisSysviewCurrentService.delete(sysviewCurrent);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView("redirect:/uids/jisCurList");
	}
	
	public JisSysview convertToJisSysview(JisSysviewCurrent sysviewCurrent){
		JisSysview jisSysview = null;
		if(null!=sysviewCurrent){
			jisSysview = new JisSysview();
			jisSysview.setIid(sysviewCurrent.getIid());
			jisSysview.setObjectid(sysviewCurrent.getObjectid());
			jisSysview.setObjectname(sysviewCurrent.getObjectname());
			jisSysview.setState(sysviewCurrent.getState());
			jisSysview.setResult(sysviewCurrent.getResult());
			jisSysview.setOptresult(sysviewCurrent.getOptresult());
			jisSysview.setSynctime(sysviewCurrent.getSynctime());
			jisSysview.setAppid(sysviewCurrent.getAppid());
			jisSysview.setCodeid(sysviewCurrent.getCodeid());
			jisSysview.setOperatetype(sysviewCurrent.getOperatetype());
			jisSysview.setTimes(sysviewCurrent.getTimes());
			jisSysview.setErrorspec(sysviewCurrent.getErrorspec());
		}
		return jisSysview;
	}
	
	/**
	 * 查看同步详情
	 * @param iid
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysviewDetail", method = RequestMethod.GET)
	public String accountEdit(int iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		
		JisSysviewCurrent sysviewCurrent = jisSysviewCurrentService.findByIid(iid); 
		JisSysviewDetail jisSysviewDetail = jisSysviewDetailService.findByIid(iid);
		
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
		detailMap.put("returnUrl", "sysviewCurr/jisCurList");
		detailMap.put("syncType", "current");
		model.addAttribute("detailMap",detailMap);
		model.addAttribute("applicationMap", applicationMap);
		model.addAttribute("paraMap", paraMap);
		model.addAttribute("jisSysview",sysviewCurrent);
		model.addAttribute("jisSysviewDetail",jisSysviewDetail);
		return "users/sysview/jis_sysview_detail";
	} 
	
	
	@RequestMapping(value="/checkSyncState",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> checkSyncState(@RequestParam("iid")int iid,@RequestParam("optresult")int optresult,Model model) throws Exception{
		Map<String,Object> returnMap = new HashMap<String, Object>();
		JisSysviewCurrent jisSysviewCurrent = jisSysviewCurrentService.findByIid(iid);
		if(null!=jisSysviewCurrent){
			if(optresult == jisSysviewCurrent.getOptresult()){
				returnMap.put("success", "true") ;
			}else{
				returnMap.put("success", "false") ;
			}
		}
		return returnMap;
		//model.addAttribute("returnMap", returnMap);
	}
	
}