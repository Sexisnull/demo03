package com.gsww.uids.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.gsww.jup.controller.sys.SysAccountController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisSysviewCurrentService;

@Controller
@RequestMapping(value = "/uids")
public class JisSysviewCurrentController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);
	@Autowired
	private JisSysviewCurrentService jisSysviewCurrentService;
	@Autowired
	private JisApplicationService jisApplicationService;
	@Autowired
	private SysParaService sysParaService;
	
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
			//String synctime = hrequest.getParameter("synctime");
			
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
		
			//分页
			Page<JisSysviewCurrent> pageInfo = jisSysviewCurrentService.getJisPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("applicationMap", applicationMap);
			model.addAttribute("paraMap", paraMap);

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
	 * 删除参数信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/jisCurDelete", method = RequestMethod.GET)
	public ModelAndView jisCurDelete(String objectId, HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=objectId.split(",");
			JisSysviewCurrent jisCurrent = null;
			for(int i=0;i<para.length;i++){
				Integer Iid = Integer.parseInt(para[i].trim());
				jisCurrent=jisSysviewCurrentService.findByIid(Iid);
				jisSysviewCurrentService.delete(jisCurrent);
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);		
		} finally{
			return  new ModelAndView("redirect:/uids/jisCurList");
		}
		
	}
	
	@RequestMapping(value="/getApplications",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getApplications(){
		List<Map<String, Object>> appMap = null;
		try {
			appMap = jisApplicationService.getJisApplicationList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appMap;
	}
	
	@RequestMapping(value="/getOptresult",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOptresult(){
		List<Map<String, Object>> paraMap = null;
		try {
			paraMap = sysParaService.getParaList("OPT_RESULT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paraMap;
	}
	
	/*@RequestMapping(value="syncSysview")
	public ModelAndView syncSysview(int iid, HttpServletRequest request,HttpServletResponse response){
		//String[] iids = iid.split(",");
		for(String id:iids){
			jisSysviewCurrentService.findByIid(iid);
		}
		return null;
	}*/
}
