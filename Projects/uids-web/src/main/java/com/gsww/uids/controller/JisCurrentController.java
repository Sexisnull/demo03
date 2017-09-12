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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.controller.sys.SysAccountController;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.entity.sys.SysRole;

import com.gsww.jup.util.PageUtils;

import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.service.JisCurrentService;


@Controller
@RequestMapping(value = "/uids")
public class JisCurrentController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);
	@Autowired
	private JisCurrentService jisCurrentService;
	
	@RequestMapping(value="/jisCurList",method = RequestMethod.GET)
	public String jisCurList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "syncTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest){
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisCurrent.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<JisCurrent>  spec=super.toSpecification(searchParams, JisCurrent.class);
			//
		
			//分页
			Page<JisCurrent> pageInfo = jisCurrentService.getJisPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			Map<String,String> userAppsmap=new HashMap<String,String>();
				List<SysApps> appsList=new ArrayList<SysApps>();
			//	appsList=jisCurrentService.findAppsList();
				String appsName="";
				Map<String,Object> appMap=new HashMap<String,Object>();
				for (SysApps sysApps : appsList) {
					appMap.put("key", sysApps.getKey());
					appMap.put("appName", sysApps.getAppName());
				}							
						
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error","列表打开失败",(HttpServletRequest) request);
			return "redirect:/sys/jisCurList";
		}
		return "sys/jis_current_list";
	}

	
	/**
	 * 删除参数信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/jisCurDelete", method = RequestMethod.GET)
	public ModelAndView jisCurDelete( String objectId, HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=objectId.split(",");
			JisCurrent jisCurrent = null;
			for(int i=0;i<para.length;i++){
				Integer Iid = Integer.parseInt(para[i].trim());
				jisCurrent=jisCurrentService.findByIid(Iid);
				jisCurrentService.delete(jisCurrent);
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);		
		} finally{
			return  new ModelAndView("redirect:/uids/jisCurList");
		}
		
	}
}
