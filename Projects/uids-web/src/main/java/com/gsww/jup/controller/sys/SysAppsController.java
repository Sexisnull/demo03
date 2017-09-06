package com.gsww.jup.controller.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.service.sys.SysAppsService;
import com.gsww.jup.service.sys.SysMenuService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;

/**
 * 应用系统管理控制器
 * @author taoweixin
 *
 */
@Controller
public class SysAppsController extends BaseController{


	private static Logger logger = LoggerFactory.getLogger(SysAppsController.class);
	@Autowired
	private SysAppsService appsService;
	@Autowired
	private SysMenuService sysMenuService;
	private Map<String,String> resMap=new HashMap<String,String>();

	/**
	 * 获取菜单操作列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/apps/appsList",method = RequestMethod.GET)
	public String appsList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "key") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysAccount.class,findNowPage);
			request.setCharacterEncoding("UTF-8");
			hrequest.setCharacterEncoding("UTF-8");
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<SysApps>  spec=super.toSpecification(searchParams, SysApps.class);
			
			Page<SysApps> pageInfo = appsService.getSysAppsPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);			
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "sys/apps_list";
	}
	
	/**
	 * 新增或编辑菜单操作
	 * @param operatorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/apps/appsEdit", method = RequestMethod.GET)
	public ModelAndView appsEdit(String key,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav=new ModelAndView("sys/apps_edit");
		try {
			SysApps sysApps = null;
			if(StringHelper.isNotBlack(key)){
				sysApps = appsService.findBykey(key);
			}else{
				sysApps = new SysApps();
			}			
			model.addAttribute("sysApps", sysApps);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 保存菜单操作
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsSave", method = RequestMethod.POST)
	public ModelAndView appsSave(String key,SysApps sysApps,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			sysApps.setCreateTime(df.format(new Date()));
			appsService.save(sysApps);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
		
	}
	/**
	 * 删除菜单操作
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsDelete", method = RequestMethod.GET)
	public ModelAndView appsDelete(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			SysApps sysApps = null;
			for(int i=0;i<para.length;i++){
				sysApps=appsService.findBykey(para[i].trim());
				appsService.delete(sysApps);
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
		
	}
	/**
	 * 检查应用状态
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/checkAppsState", method = RequestMethod.POST)
	public void checkAppsState(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			SysApps sysApps = null;
			for(int i=0;i<para.length;i++){
				sysApps=appsService.findBykey(para[i].trim());
			}
			response.getWriter().write(sysApps.getAppState());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(-1);
		} 
	}
	/**
	 * 停用应用信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsStop", method = RequestMethod.GET)
	public ModelAndView appsStop(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysApps sysApps=appsService.findBykey(para[i].trim());
				if("00B".equals(sysApps.getAppState())){
					msg += "名称为“"+sysApps.getAppName()+"”的应用已经停用！   </br>   ";
				}else if("00A".equals(sysApps.getAppState())){
					appsService.stop(para[i].trim());
					msg += "名称为“"+sysApps.getAppName()+"”的应用停用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "停用失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
	}
	
	/**
	 * 启用应用信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsStart", method = RequestMethod.GET)
	public ModelAndView appsStart(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysApps sysApps=appsService.findBykey(para[i].trim());
				if("00A".equals(sysApps.getAppState())){
					msg += "名称为“"+sysApps.getAppName()+"”的应用已经启用！   </br>   ";
				}else if("00B".equals(sysApps.getAppState())){
					appsService.start(para[i].trim());
					msg += "名称为“"+sysApps.getAppName()+"”的应用启用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "启用失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
	}
	/**
	 * 检查绿色通道状态
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/checkAppsAppGreenChanal", method = RequestMethod.POST)
	public void checkAppsAppGreenChanal(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			SysApps sysApps = null;
			for(int i=0;i<para.length;i++){
				sysApps=appsService.findBykey(para[i].trim());
			}
			response.getWriter().write(sysApps.getAppGreenChanal());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(-1);
		} 
	}
	/**
	 * 停用绿色通道信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsAppGreenChanalStop", method = RequestMethod.GET)
	public ModelAndView appsAppGreenChanalStop(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysApps sysApps=appsService.findBykey(para[i].trim());
				if("00A".equals(sysApps.getAppGreenChanal())){
					msg += "名称为“"+sysApps.getAppName()+"”的绿色通道已经停用！   </br>   ";
				}else if("00B".equals(sysApps.getAppGreenChanal())){
					appsService.stopAppGreenChanal(para[i].trim());
					msg += "名称为“"+sysApps.getAppName()+"”的绿色通道停用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "停用失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
	}
	
	/**
	 * 启用绿色通道信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/apps/appsAppGreenChanalStart", method = RequestMethod.GET)
	public ModelAndView appsAppGreenChanalStart(String key,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=key.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysApps sysApps=appsService.findBykey(para[i].trim());
				if("00B".equals(sysApps.getAppGreenChanal())){
					msg += "名称为“"+sysApps.getAppName()+"”的绿色通道已经启用！   </br>   ";
				}else if("00A".equals(sysApps.getAppGreenChanal())){
					appsService.startAppGreenChanal(para[i].trim());
					msg += "名称为“"+sysApps.getAppName()+"”的绿色通道启用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "启用失败",request);
		} finally{
			return  new ModelAndView("redirect:/apps/appsList");
		}
	}

}
