package com.gsww.uids.controller;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.controller.sys.SysAccountController;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisSysviewCurrentService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewService;

@Controller
@RequestMapping(value = "/uids")
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
			Map<String,Object> map = convertBean(sysviewCurrent);
			JisSysview sysview = (JisSysview)convertMap(JisSysview.class,map);
			jisSysviewService.save(sysview);
			jisSysviewCurrentService.delete(sysviewCurrent);
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
				Map<String,Object> map = convertBean(sysviewCurrent);
				JisSysview sysview = (JisSysview)convertMap(JisSysview.class,map);
				jisSysviewService.save(sysview);
				jisSysviewCurrentService.delete(sysviewCurrent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView("redirect:/uids/jisCurList");
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
		
		model.addAttribute("applicationMap", applicationMap);
		model.addAttribute("paraMap", paraMap);
		model.addAttribute("jisSysviewCurrent",sysviewCurrent);
		model.addAttribute("jisSysviewDetail",jisSysviewDetail);
		return "users/sysview/jis_sysview_detail";
	} 
	
    /**  
     * 将一个 JavaBean 对象转化为一个  Map  
     * @param bean 要转化的JavaBean 对象  
     * @return 转化出来的  Map 对象  
     * @throws IntrospectionException 如果分析类属性失败  
     * @throws IllegalAccessException 如果实例化 JavaBean 失败  
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
     */    
    @SuppressWarnings({ "rawtypes", "unchecked" })    
    public static Map convertBean(Object bean)    
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {    
        Class type = bean.getClass();    
        Map returnMap = new HashMap();    
        BeanInfo beanInfo = Introspector.getBeanInfo(type);    
    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            String propertyName = descriptor.getName();    
            if (!propertyName.equals("class")) {    
                Method readMethod = descriptor.getReadMethod();    
                Object result = readMethod.invoke(bean, new Object[0]);    
                if (result != null) {    
                    returnMap.put(propertyName, result);    
                } else {    
                    returnMap.put(propertyName, "");    
                }    
            }    
        }    
        return returnMap;    
    }  
  
  
  
/**  
     * 将一个 Map 对象转化为一个 JavaBean  
     * @param type 要转化的类型  
     * @param map 包含属性值的 map  
     * @return 转化出来的 JavaBean 对象  
     * @throws IntrospectionException 如果分析类属性失败  
     * @throws IllegalAccessException 如果实例化 JavaBean 失败  
     * @throws InstantiationException 如果实例化 JavaBean 失败  
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
     */    
    @SuppressWarnings("rawtypes")    
    public static Object convertMap(Class type, Map map)    
            throws IntrospectionException, IllegalAccessException,    
            InstantiationException, InvocationTargetException {    
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性    
        Object obj = type.newInstance(); // 创建 JavaBean 对象    
    
        // 给 JavaBean 对象的属性赋值    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            String propertyName = descriptor.getName();    
    
            if (map.containsKey(propertyName)) {    
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。    
                Object value = map.get(propertyName);    
    
                Object[] args = new Object[1];    
                args[0] = value;    
    
                descriptor.getWriteMethod().invoke(obj, args);    
            }    
        }    
        return obj;    
    }  
}
