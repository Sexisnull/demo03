package com.gsww.uids.controller;

import java.io.IOException;
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
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.service.ComplatRoleService;
@Controller
@RequestMapping(value="/complat")
public class ComplatRoleController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ComplatRoleController.class);
	@Autowired
	private ComplatRoleService roleService;
	
	@RequestMapping(value="/croleList",method = RequestMethod.GET)
	public String roleList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "name") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest){
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatRole.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatRole>  spec=super.toSpecification(searchParams, ComplatRole.class);
			
			//分页
			Page<ComplatRole> pageInfo = roleService.getRolePage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("cParams", searchParams);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("msgMap","列表打开失败",(HttpServletRequest) request);
			return "redirect:/complat/roleList";
		}
		return "users/complatrole/complatrole";
	}
	//新增或编辑，页面跳转
	@RequestMapping(value = "/croleEdit", method = RequestMethod.GET)
	public ModelAndView roleEdit(String croleId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView("users/complatrole/crole_edit");
		try {
			ComplatRole cRole = null;
			if(StringHelper.isNotBlack(croleId)){
				cRole = roleService.findByKey(Integer.parseInt(croleId));
			}else{
				cRole = new ComplatRole();
			}
			model.addAttribute("complatRole", cRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑页面打开失败："+e.getMessage());
		}
		return mav;
	}
	//保存信息
	@SuppressWarnings("finally")
	@RequestMapping(value = "/croleSave", method = RequestMethod.POST)
	public ModelAndView roleSave(ComplatRole cRole,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			/*ComplatRole role = new ComplatRole();
			if(StringHelper.isNotBlack(croleId)){
				role = roleService.findByKey(Integer.parseInt(croleId));
			}
			role.setName(cRole.getName());
			role.setSpec(cRole.getSpec());*/
			roleService.save(cRole);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/croleList");
		}
		
	}
	//删除角色
	@SuppressWarnings("finally")
	@RequestMapping(value = "/croleDelete", method = RequestMethod.GET)
	public ModelAndView roleDelete(String croleId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		String resMsg = "";
		try {
			String[] para=croleId.split(",");
			for(int i=0;i<para.length;i++){
				List<ComplatRolerelation> acct = roleService.findAcctByroleId(Integer.parseInt(para[i].trim()));
				ComplatRole role = roleService.findByKey(Integer.parseInt(para[i].trim()));
				if(acct!=null && acct.size()>0){
					resMsg += "名称为“"+role.getName()+"”的角色下存在用户，不能删除！   </br>   ";
				}else{
					roleService.delete(Integer.parseInt(para[i].trim()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMsg="删除失败";
		} finally{
			if(resMsg.equals("")){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error",resMsg,request);
			}
			return  new ModelAndView("redirect:/complat/croleList");
		}
		
	}
	//账号是否被使用
	@RequestMapping(value="/ccheckcRole", method = RequestMethod.GET)
	public void checkRole(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String roleNameInput=StringUtils.trim((String)request.getParameter("name"));
			String roleNameold=StringUtils.trim((String)request.getParameter("oldRoleName"));
			if(!roleNameInput.equals(roleNameold)){
				List<ComplatRole> cRoleList = roleService.findByName(roleNameInput);
				if(cRoleList!=null && cRoleList.size()>0){
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
	//获取权限弹出窗
	@RequestMapping(value="/croleAuthorizeShow",method = RequestMethod.GET)
	public String roleAuthorizeShow(String roleId,HttpServletRequest request,HttpServletResponse response,Model model)  throws Exception {
		try {
			model.addAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role_authorize";
	}
	//获取权限树json
	@RequestMapping(value="/croleAuthorizeList",method = RequestMethod.POST)
	public void roleAuthorizeList(String roleId,HttpServletRequest request,HttpServletResponse response,Model model)  throws Exception {
		try {
			//SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String treeJson= roleService.getAuthorizeTree(roleId);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(treeJson);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//保存权限
	@RequestMapping(value="/roleAuthorizeSave",method = RequestMethod.POST)
	public void roleAuthorizeSave(String roleId,String keys,String types,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			if (roleId == null || keys == null || types == null) {
				response.getWriter().write("error");
			} else {
				roleService.saveAuthorize(roleId, keys.trim(), types.trim());
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
