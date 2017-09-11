package com.gsww.uids.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisDatacall;
import com.gsww.uids.service.JisDatacallService;
/**
 * 数据调用控制器
 * @author Seven
 *
 */
@Controller
@RequestMapping(value = "/datacall")
public class JisDatacallController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(JisDatacallController.class);
	@Autowired
	private JisDatacallService jisDatacallService ;
	
	/**
	 * 获取数据调用列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/datacallList",method = RequestMethod.GET)
	public String datacallList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "iid") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			if(StringUtils.isNotBlank(request.getParameter("orderField"))){
				orderField=(String)request.getParameter("orderField");
			}
			if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
				orderSort=(String)request.getParameter("orderSort");
			}
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisDatacall.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<JisDatacall>  spec=super.toNewSpecification(searchParams, JisDatacall.class);
			
			//分页
			Page<JisDatacall> pageInfo = jisDatacallService.getDatacallPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "system/jis/datacall_list";
	}
	
	/**
	 * 转到新增或编辑页面
	 * @param iid
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/datacallEdit", method = RequestMethod.GET)
	public String datacallEdit(Integer iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			JisDatacall jisDatacall = null;
			if(iid!=null){
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				jisDatacall = jisDatacallService.findByKey(iid);
				Map<String,String> map=new HashMap<String,String>();
				List<JisDatacall> list=new ArrayList<JisDatacall>();
				/*userList=sysCardService.findUserNameList(cardId);*/
				/*roleList=sysUserService.findAccountRoleList(sysUser.getUserId());*/
				/*String userName="";
				String userId="";
				for (SysUser sysUser : userList) {
					userName+=sysUser.getUserName()+",";
					userId+=sysUser.getUserId()+",";
				}
				if(StringUtils.isNotBlank(userName)){
					userName=userName.substring(0, userName.length()-1);
					/*userRolemap.put(sysUser.getUserAcctId(), userRoleName);
					userId=userId.substring(0,userId.length()-1);
				}else{
					userMap.put("0", "");
				}*/
				model.addAttribute("list", list);
				model.addAttribute("map", map);
				/*model.addAttribute("userId", userId);*/
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				jisDatacall = new JisDatacall();
			}
			model.addAttribute("jisDatacall", jisDatacall);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/jis/datacall_edit";
	}
	
	/**
	 * 保存信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/datacallSave", method = RequestMethod.POST)
	public ModelAndView datacallSave(JisDatacall jisDatacall,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		
		try {
			if(jisDatacall != null){
				jisDatacallService.save(jisDatacall);
				returnMsg("success","保存成功",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return new ModelAndView("redirect:/datacall/datacallList");
		}
		
		/*try {
			jisDatacall = jisDatacallService.save(jisDatacall);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/datacall/datacallList");
		}*/
		
	}
	
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/datacallDelete", method = RequestMethod.GET)
	public ModelAndView datacallDelete(Integer iid,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			JisDatacall jisDatacall = null;
			jisDatacall=jisDatacallService.findByKey(iid);
			jisDatacallService.delete(jisDatacall);
			boolean flag=true;
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,系统管理员不允许删除",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/datacall/datacallList");
		}
		
	}
	
	/**
	 * 查看账号情信息
	 */
	/*@RequestMapping(value = "/datacallView", method = RequestMethod.GET)
	public String datacallView(int iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		try {
			JisDatacall jisDatacall = null;
			if(StringHelper.isNotBlack(iid)){
				jisDatacall = jisDatacallService.findByKey(iid);
			}else{
				jisDatacall = new JisDatacall();
			}
			model.addAttribute("jisDatacall", jisDatacall);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/jis/datacall_view";
	}*/
	
	/**
	 * 标识是否重复
	 * @param userName
	 * @param setId
	 * @param request
	 * @param response 1表示不重复，0表示重复
	 * @throws Exception
	 */
	@RequestMapping(value="/checkRemark", method = RequestMethod.GET)
	public void checkRemark(String remark,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			JisDatacall jisDatacall = null;
			String remarkInput=StringUtils.trim((String)request.getParameter("remark"));
			String oldRemark=StringUtils.trim((String)request.getParameter("oldRemark"));
			if(!remarkInput.equals(oldRemark)){
				jisDatacall=jisDatacallService.queryRemarkIsUsed(remark);
				if(jisDatacall!=null){					
					response.getWriter().write("0");								
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
