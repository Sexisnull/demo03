
package com.gsww.uids.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatRoleService;
import com.gsww.uids.service.ComplatUserService;

@Controller
@RequestMapping(value = "/complat")
public class ComplatUserController extends BaseController{

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ComplatUserService complatUserService;
	
	@Autowired
	private ComplatGroupService complatGroupService;
	
	@Autowired
	private ComplatRoleService complatRoleService;
	
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
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatUser.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatUser>  spec=super.toNewSpecification(searchParams, ComplatUser.class);
			
			//分页
			Page<ComplatUser> pageInfo = complatUserService.getComplatUserPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);						
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/complatList";
		}
		return "users/complat/account_list";
	}
		
	
	
	/**
	 * 转到新增政府用户页面
	 */
	@RequestMapping(value = "/complatUserEdit", method = RequestMethod.GET)
	public ModelAndView complatUserEdit(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav=new ModelAndView("users/complat/account_edit");
		try {
			ComplatUser complatUser = new ComplatUser();			
			model.addAttribute("complatUser",complatUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatSave", method = RequestMethod.POST)
	public ModelAndView complatSave(ComplatUser complatUser,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(complatUser != null){
				complatUserService.save(complatUser);
				returnMsg("success","保存成功",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatUserDelete", method = RequestMethod.GET)
	public ModelAndView complatUserDelete(String complatUserId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=complatUserId.split(",");
			ComplatUser complatUser = null;
			for(int i=0;i<para.length;i++){
				Integer corId = Integer.parseInt(para[i].trim());
				complatUser=complatUserService.findByKey(corId);
				if(complatUser != null){
					complatUserService.delete(complatUser);
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	/**
	 * 用户设置模块,点击用户设置按钮，页面跳转
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */
	@RequestMapping(value="/userSetUpEdit",method = RequestMethod.GET)
	public ModelAndView userSetUpEdit(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ModelAndView mav = new ModelAndView("users/sysview/user_setup");
		try{
			//获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String userSid = sysUserSession.getAccountId();
			if(StringHelper.isNotBlack(sysUserSession.getAccountId())){
				
				//查询用户信息
				ComplatUser complatUser = complatUserService.findByKey(Integer.parseInt(userSid));
				model.addAttribute("complatUser",complatUser);
				
				//根据用户ID查询所属机构
				ComplatGroup complatGroup = complatGroupService.findByIid(complatUser.getGroupid());
				model.addAttribute("complatGroup",complatGroup);
				
				//根据用户ID从ComplatRolerelation获取对应的角色ID，再根据角色ID从ComplatRole中获取对应的角色
				List<ComplatRolerelation> roleRelationList = complatRoleService.findByUserId(Integer.parseInt(userSid));
				List<ComplatRole> roleList = new ArrayList<ComplatRole>();
				for(int i = 0;i < roleRelationList.size();i++){
					Integer roleId = roleRelationList.get(i).getRoleId();
					ComplatRole complatRole = complatRoleService.findByKey(roleId);
					roleList.add(complatRole);
					model.addAttribute("roleList",roleList);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return mav;
	}
	
	
	/**
	 * 保存用户设置
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/userSetUpSave", method = RequestMethod.POST)
	public ModelAndView userSetUpSave(ComplatUser complatUser,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String pwd = request.getParameter("pwd");
			if(complatUser != null){
				Integer iid = complatUser.getIid();
				String name = complatUser.getName();
				String headShip = complatUser.getHeadship();
				String phone = complatUser.getPhone();//固定电话
				String mobile = complatUser.getMobile();//移动电话
				String fax = complatUser.getFax();
				String email = complatUser.getEmail();
				String qq = complatUser.getQq();
				String time = TimeHelper.getCurrentTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date modifyTime = sdf.parse(time);
				complatUserService.updateUser(iid,name,headShip,phone,mobile,fax,email,qq,modifyTime,pwd);
				returnMsg("success","保存成功",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
}


