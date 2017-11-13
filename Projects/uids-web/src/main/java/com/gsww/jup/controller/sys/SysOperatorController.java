package com.gsww.jup.controller.sys;

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
import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.service.sys.SysMenuService;
import com.gsww.jup.service.sys.SysOperatorService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-7-28 下午15:55:12</p>
 * <p>类描述 :   操作管理模块控制器    </p>
 *
 * @version 3.0.0
 * @author <a href=" ">zhangjy</a>
 */
@Controller
public class SysOperatorController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SysOperatorController.class);
	@Autowired
	private SysOperatorService operatorService;
	@Autowired
	private SysMenuService sysMenuService;
	@SuppressWarnings("unused")
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
	@RequestMapping(value = "/operator/operatorList",method = RequestMethod.GET)
	public String operatorList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "sysMenu.menuSeq,operatorType,operatorLevel,operatorId") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC,ASC,ASC,ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,SysOperator.class,findNowPage);
			request.setCharacterEncoding("UTF-8");
			hrequest.setCharacterEncoding("UTF-8");
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<SysOperator>  spec=super.toSpecification(searchParams, SysOperator.class);
			
			Page<SysOperator> pageInfo = operatorService.getSysOperatorPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);			
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "sys/operator_list";
	}
	
	/**
	 * 新增或编辑保存菜单操作
	 * @param operatorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/operator/operatorEdit", method = RequestMethod.GET)
	public ModelAndView operatorEdit(String operatorId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav=new ModelAndView("sys/operator_edit");
		try {
			SysOperator sysOperator = null;
			if(StringHelper.isNotBlack(operatorId)){
				sysOperator = operatorService.findByOperatorId(operatorId);
			}else{
				sysOperator = new SysOperator();
			}			
			model.addAttribute("sysOperator", sysOperator);
			List<SysMenu> list=new ArrayList<SysMenu>();
			list=sysMenuService.findSecondMenu();
			mav.addObject("sysMenuList",list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 保存菜单操作
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/operator/operatorSave", method = RequestMethod.POST)
	public ModelAndView operatorSave(String operatorId,SysOperator sysOperator,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String menuId=request.getParameter("menuId");
			SysMenu sysMenu=sysMenuService.findByKey(menuId);
			sysOperator.setSysMenu(sysMenu);
			operatorService.save(sysOperator);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/operator/operatorList");
		}
		
	}
	/**
	 * 删除菜单操作
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/operator/operatorDelete", method = RequestMethod.GET)
	public ModelAndView operatorDelete(String operatorId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=operatorId.split(",");
			SysOperator sysOperator = null;
			for(int i=0;i<para.length;i++){
				sysOperator=operatorService.findByOperatorId(para[i].trim());
				operatorService.delete(sysOperator);
			}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);
		} finally{
			return  new ModelAndView("redirect:/operator/operatorList");
		}
		
	}
	/**
	 * 检查操作状态
	 */
	@RequestMapping(value = "/operator/checkOperatorState", method = RequestMethod.POST)
	public void checkOperatorStat(String operatorId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=operatorId.split(",");
			SysOperator sysOperator = null;
			for(int i=0;i<para.length;i++){
				sysOperator=operatorService.findByOperatorId(para[i].trim());
			}
			response.getWriter().write(sysOperator.getOperatorState());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(-1);
		} 
	}
	/**
	 * 停用操作信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/operator/operatorStop", method = RequestMethod.GET)
	public ModelAndView operatorStop(String operatorId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=operatorId.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysOperator sysOperator=operatorService.findByOperatorId(para[i].trim());
				if("0".equals(sysOperator.getOperatorState())){
					msg += "名称为“"+sysOperator.getOperatorName()+"”的操作已经停用！   </br>   ";
				}else if("1".equals(sysOperator.getOperatorState())){
					operatorService.stop(para[i].trim());
					msg += "名称为“"+sysOperator.getOperatorName()+"”的操作停用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "停用失败",request);
		} finally{
			return  new ModelAndView("redirect:/operator/operatorList");
		}
	}
	
	/**
	 * 启用操作信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/operator/operatorStart", method = RequestMethod.GET)
	public ModelAndView operatorStart(String operatorId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=operatorId.split(",");
			String msg="";
			for(int i=0;i<para.length;i++){
				SysOperator sysOperator=operatorService.findByOperatorId(para[i].trim());
				if("1".equals(sysOperator.getOperatorState())){
					msg += "名称为“"+sysOperator.getOperatorName()+"”的操作已经启用！   </br>   ";
				}else if("0".equals(sysOperator.getOperatorState())){
					operatorService.start(para[i].trim());
					msg += "名称为“"+sysOperator.getOperatorName()+"”的操作启用成功！   </br>   ";
				}
			}
			returnMsg("success",msg,request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "启用失败",request);
		} finally{
			return  new ModelAndView("redirect:/operator/operatorList");
		}
	}
	/*@SuppressWarnings("finally")
	@RequestMapping(value = "/operator/getMenuBySysId", method = RequestMethod.POST)
	public void getMenuBySysId(String sysId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		String strHtml = "";
		List<Map<String,Object>> sysMenuList = operatorService.getSysMenuList(sysId);
		strHtml += "<select id='menuId' name='menuId' class='input-select' validate='{required:true}' >"
			+ "<option value=''>--请选择--</option>";
			if (sysMenuList.size() != 0) {
				for (int i = 0; i < sysMenuList.size(); i++) {
					Map map =sysMenuList.get(i);
					String menu_id=map.get("menu_id")==null?"":map.get("menu_id").toString();
					String menu_name=map.get("menu_name")==null?"":map.get("menu_name").toString();
					strHtml += "<option value='" + menu_id + "'";				
						strHtml += " >" + menu_name;				
					strHtml += "</option>";
				}
			}
			strHtml += "</select>";
			response.setContentType("text/plain");
			response.getWriter().write(strHtml);
	}*/

}
