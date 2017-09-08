package com.gsww.uids.controller;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatUserService;

@Controller
@RequestMapping(value = "/complat")
public class ComplatUserController extends BaseController{

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ComplatUserService complatUserService;
	
	
	/**
	 * 获取政府用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/complatList",method = RequestMethod.GET)
	public String complatList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createtime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
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
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatUser.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			//searchParams.put("NE_loginAccount", "admin");
			Specification<ComplatUser>  spec=super.toNewSpecification(searchParams, ComplatUser.class);
			
			//分页
			Page<ComplatUser> pageInfo = complatUserService.getUserPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);			
			//model.addAttribute("sortType", orderField);
			//model.addAttribute("orderField", orderField);
			//model.addAttribute("orderSort", orderSort);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "complat/account_list";
	}
	/**
	 * 转到新增或编辑用户页面
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatEdit", method = RequestMethod.GET)
	public String complatEdit(String userAcctId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		//ModelAndView mav=new ModelAndView("sys/account_edit");
		try {
			ComplatUser complatUser = null;
			if(StringHelper.isNotBlack(userAcctId)){
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				complatUser = complatUserService.findByKey(userAcctId);				
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				complatUser = new ComplatUser();
			}
			model.addAttribute("complatUser", complatUser);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "complat/complat_edit";
	}
	/**
	 * 保存用户信息
	 */
	@RequestMapping(value = "/complatUserSave", method = RequestMethod.POST)
	public ModelAndView complatUserSave(String userAcctId,ComplatUser complatUser,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		/*try {
			if(!StringHelper.isNotBlack(complatUser.getUserState())){
				complatUser.setUserState("1");
			}
			if(!StringHelper.isNotBlack(complatUser.getLoginPassword())){
				MD5 m = new MD5();
				complatUser.setLoginPassword(m.getMD5ofStr("111111"));
			}
			if(StringUtils.isBlank(sysAccount.getUserSex())){
				complatUser.setUserSex("2");
			}
			SysDepartment sysDepartment=null;
			String deptId=request.getParameter("deptId");
			//sysDepartment=departmentService.findByKey(deptId);
			complatUser.setSysDepartment(sysDepartment);
			//1、先保存用户
			complatUser.setUserSex("0");
			complatUser.setCreateTime(TimeHelper.getCurrentTime());
			complatUser = complatUserService.save(complatUser);
			    
			//2、保存用户角色关系表
			complatUserService.deleteAccountRole(complatUser);
			String userRoles=request.getParameter("userRole");
			if(StringUtils.isNotBlank(userRoles)){
				String[] roles=userRoles.split(",");
				for (String roleId : roles) {
					complatUserService.saveUserRole(complatUser.getUserAcctId(), roleId);
				}
			}
			//System.out.println("Saveuser:"+sysAccount.getUserAcctId());
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}*/
		return  new ModelAndView("redirect:/complat/complatList");
	}
		
}


