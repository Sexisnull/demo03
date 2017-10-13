package com.gsww.uids.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.service.ComplatRoleService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.service.JisRoleobjectService;

@Controller
@RequestMapping(value = "/complat")
public class ComplatRoleController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ComplatRoleController.class);
	@Autowired
	private ComplatRoleService complatRoleService;
	@Autowired
	private JisApplicationService jisApplicationService;
	@Autowired
	private JisRoleobjectService jisRoleobjectService;
	@Autowired
	private ComplatGroupService complatGroupService;
	@Autowired
	private ComplatOutsideuserService complatOutsideuserService;
	@Autowired
	private ComplatUserService complatUserService;
	@Autowired
	private JisLogService jisLogService;

	@RequestMapping(value = "/croleList", method = RequestMethod.GET)
	public String roleList(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "name") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			// 初始化分页数据
			PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField,
					orderSort);
			PageRequest pageRequest = super.buildPageRequest(hrequest,
					pageUtils, ComplatRole.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			Specification<ComplatRole> spec = super.toSpecification(
					searchParams, ComplatRole.class);

			// 分页
			Page<ComplatRole> pageInfo = complatRoleService.getRolePage(spec,
					pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets
					.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		} catch (Exception ex) {
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("msgMap", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/roleList";
		}
		return "users/complatrole/complatrole";
	}

	// 新增或编辑，页面跳转
	@RequestMapping(value = "/croleEdit", method = RequestMethod.GET)
	public ModelAndView roleEdit(String croleId, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("users/complatrole/crole_edit");
		try {
			ComplatRole cRole = null;
			if (StringHelper.isNotBlack(croleId)) {
				cRole = complatRoleService.findByKey(Integer.parseInt(croleId));
			} else {
				cRole = new ComplatRole();
			}
			model.addAttribute("complatRole", cRole);
		} catch (Exception e) {
			logger.error("编辑页面打开失败：" + e.getMessage());
		}
		return mav;
	}
	
	@RequestMapping(value = "/isdefault_modify", method = RequestMethod.POST)
	public void isdefaultModify(String iid, String isDefault,HttpServletRequest request,
			HttpServletResponse response){
		SysUserSession sysUserSession =(SysUserSession)request.getSession().getAttribute("sysUserSession");
		if(!StringHelper.isNotBlack(iid)){
			return;
		}
		try {
			ComplatRole cr = complatRoleService.findByKey(Integer.parseInt(iid));
			cr.setIsdeFault(Integer.parseInt(isDefault));
			complatRoleService.save(cr);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("success", true);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(result));
			String desc=sysUserSession.getUserName()+"修改了"+cr.getName();
			jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(), desc, 3, 2);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		
	}
	
	// 保存信息
	@SuppressWarnings("finally")
	@RequestMapping(value = "/croleSave", method = RequestMethod.POST)
	public ModelAndView roleSave(ComplatRole cRole, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUserSession sysUserSession =(SysUserSession)request.getSession().getAttribute("sysUserSession");
		try {
			if(cRole.getIid()==null){
				String desc=sysUserSession.getUserName()+"新增了"+cRole.getName();
				jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(), desc, 3, 1);
			}else{
				String desc=sysUserSession.getUserName()+"修改了"+cRole.getName();
				jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(), desc, 3, 2);
			}
			cRole.setType(6);
			complatRoleService.save(cRole);
			returnMsg("success", "保存成功", request);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnMsg("error", "保存失败", request);
		} finally {
			return new ModelAndView("redirect:/complat/croleList");
		}

	}

	// 删除角色
	@SuppressWarnings("finally")
	@RequestMapping(value = "/croleDelete", method = RequestMethod.GET)
	public ModelAndView roleDelete(String croleId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resMsg = "";
        SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		try {
			String[] para = croleId.split(",");
			for (int i = 0; i < para.length; i++) {
                ComplatRole role = complatRoleService.findByKey(Integer.parseInt(para[i].trim()));
                complatRoleService.delete(Integer.parseInt(para[i].trim()));
                jisRoleobjectService.deleteByRoleId(Integer.parseInt(para[i].trim()));
                String desc=session.getUserName()+"删除了"+role.getName();
                jisLogService.save(session.getLoginAccount(),session.getUserIp(), desc, 3, 3);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			resMsg = "删除失败";
		} finally {
			if (resMsg.equals("")) {
				returnMsg("success", "删除成功", request);
			} else {
				returnMsg("error", resMsg, request);
			}
			return new ModelAndView("redirect:/complat/croleList");
		}

	}

	// 账号是否被使用
	@RequestMapping(value = "/ccheckcRole", method = RequestMethod.GET)
	public void checkRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String roleNameInput = StringUtils.trim((String) request
					.getParameter("name"));
			String roleNameold = StringUtils.trim((String) request
					.getParameter("oldRoleName"));
			if (!roleNameInput.equals(roleNameold)) {
				List<ComplatRole> cRoleList = complatRoleService
						.findByName(roleNameInput);
				if (cRoleList != null && cRoleList.size() > 0) {
					response.getWriter().write("0");
				} else {
					response.getWriter().write("1");
				}
			} else {
				response.getWriter().write("1");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 
	 * 用户机构授权相关方法
	 */

	// 用户机构权限
	@RequestMapping(value = "/roleorganizationShow", method = RequestMethod.GET)
	public String roleAuthorizeShow(String roleId, String memberType,
			String memberName, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		try {
			model.addAttribute("roleid", roleId);
			if (!StringHelper.isNotBlack(memberType)) {
				memberType = "0";
			}
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = complatRoleService.findRoleMember(roleId, memberType,
					memberName);
			model.addAttribute("userList", result);
			model.addAttribute("memberName", memberName);
			model.addAttribute("memberType", memberType);
			model.addAttribute("roleid", roleId);
			model.addAttribute("orgType", "u,g");

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "users/complatrole/application";
	}

	@RequestMapping(value = "/roleUserAjaxShow", method = RequestMethod.GET)
	public void roleUserAjaxShow(String roleId, String memberType,
			String memberName, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		try {
			model.addAttribute("roleid", roleId);
			if (!StringHelper.isNotBlack(memberType)) {
				memberType = "0";
			}
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = complatRoleService.findRoleMember(roleId, memberType,
					memberName);
			model.addAttribute("userList", result);
			model.addAttribute("memberName", memberName);
			model.addAttribute("memberType", memberType);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeListMapJSONMap(result));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@RequestMapping(value = "/emptyRelationByRoleId", method = RequestMethod.GET)
	public void emptyRoleRelation(String roleId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringHelper.isNotBlack(roleId)) {
				complatRoleService.deleteByRoleId(roleId);
			}
			result.put("result", true);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(result));

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("result", false);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(JSONUtil.writeMapJSON(result));
			} catch (IOException e1) {
				logger.error(e1.getMessage(),e1);
			}
		}

	}

	@RequestMapping(value = "/removeRelation", method = RequestMethod.GET)
	public void removeRelation(String roleId, String userIds, String groupIds,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] users = userIds != null ? userIds.split(",")
					: new String[0];
			String[] groups = groupIds != null ? groupIds.split(",")
					: new String[0];
			if (StringHelper.isNotBlack(roleId)) {
				complatRoleService.deleteUsersByRoleId(roleId, users, groups);
			}

			result.put("result", true);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(result));

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("result", false);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(JSONUtil.writeMapJSON(result));
			} catch (IOException e1) {
				logger.error(e1.getMessage(),e1);
			}
		}

	}

	/**
	 * 跳转至组织选择页面
	 * 
	 * @param orgType
	 * @param roleId
	 * @param request
	 * @param response
	 * @param models
	 * @return
	 */
	@RequestMapping(value = "/orgselect", method = RequestMethod.GET)
	public String orgselect(String orgType, String roleId,
			HttpServletRequest request, HttpServletResponse response,
			Model models) {
		models.addAttribute("orgType", orgType);
		models.addAttribute("roleid", roleId);
		return "/users/complatrole/orgselect";
	}

	/**
	 * 角色加载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/role_load", method = RequestMethod.POST)
	public void roleLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			List<ComplatRole> list = complatRoleService.findRoleList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for (ComplatRole role : list) {
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("id", role.getIid());
				r.put("text", role.getName());
				result.add(r);
			}
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("roles", result);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(res));

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	/**
	 * 用户加载
	 * 
	 * @param id
	 * @param roleid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user_load", method = RequestMethod.POST)
	public void user_load(String id, String roleid, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> allNodes = new HashMap<String, Object>();
		List<Map<String, Object>> groupNodes = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> userNodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> groupNode = new HashMap<String, Object>();
		Map<String, Object> userNode = new HashMap<String, Object>();
		Integer pid = null;
		try {
			ComplatGroup gg = complatGroupService.findByIid(Integer
					.parseInt(id));
			if (gg != null) {
				pid = gg.getPid();
				if (pid == null) {
					pid = 0;
				}
			}
			if ("0".equals(id)) {
				pid = null;
			}

			List<Map<String, Object>> groupList = complatGroupService
					.findChildGroupByIid(Integer.parseInt(id));
			if (!"1".equals(roleid) && !"2".equals(roleid)) {
				if ("0".equals(id)) {
					HashMap<String, Object> outNode = new HashMap<String, Object>();
					outNode.put("id", Integer.valueOf(-1));
					outNode.put("text", "前台用户");
					outNode.put("ic", "qtyh");
					groupNodes.add(outNode);

					for (Map<String, Object> g : groupList) {
						groupNode = new HashMap<String, Object>();
						groupNode.put("id", g.get("iid"));
						groupNode.put("text", g.get("name"));
						groupNode.put("ic", g.get("codeid"));
						groupNodes.add(groupNode);
					}

				} else if (!"-1".equals(id)) {
					for (Map<String, Object> g : groupList) {
						groupNode = new HashMap<String, Object>();
						groupNode.put("id", g.get("iid"));
						groupNode.put("text", g.get("name"));
						groupNode.put("ic", g.get("codeid"));
						groupNodes.add(groupNode);
					}
				}
			} else {
				for (Map<String, Object> g : groupList) {
					groupNode = new HashMap<String, Object>();
					groupNode.put("id", g.get("iid"));
					groupNode.put("text", g.get("name"));
					groupNode.put("ic", g.get("codeid"));
					groupNodes.add(groupNode);
				}
			}
			if (Integer.parseInt(id) != 0) {
				if (Integer.parseInt(id) == -1) {
					List<ComplatOutsideuser> outuserlist = complatOutsideuserService
							.findAll();
					for (ComplatOutsideuser outuser : outuserlist) {
						userNode = new HashMap<String, Object>();
						userNode.put("id", outuser.getIid() + "_1");
						userNode.put("text", outuser.getName());
						userNode.put("ic", outuser.getLoginName());
						userNodes.add(userNode);
					}
				} else {
					List<Map<String, Object>> userList = complatUserService
							.findByGroupIds(id);
					for (Map<String, Object> user : userList) {
						userNode = new HashMap<String, Object>();
						userNode.put("id", user.get("iid") + "_0");
						userNode.put("text", user.get("ANAME"));
						userNode.put("ic", user.get("loginname"));
						userNodes.add(userNode);
					}
				}
			}

			allNodes.put("id", id);
			allNodes.put("pid", pid);
			allNodes.put("groups", groupNodes);
			allNodes.put("users", userNodes);

			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(allNodes));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	/**
	 * 组织加载
	 * 
	 * @param id
	 * @param roleid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/group_load", method = RequestMethod.POST)
	public void loadGroupData(String id, String roleid,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> groupNodes = new ArrayList<Map<String, Object>>();

		HashMap<String, Object> groupNode = null;
		try {
			Integer groupId = null;
			if (id == null) {
				groupId = 0;
			} else {
				groupId = Integer.parseInt(id);
			}

			List<Map<String, Object>> groupList = complatGroupService
					.findChildGroupByIid(groupId);

			for (Map<String, Object> group : groupList) {
				groupNode = new HashMap<String, Object>();
				groupNode.put("id", group.get("iid"));
				String text = "<span class='optionname'>" + group.get("name")
						+ "</span>";
				String codeid = (String) group.get("codeid");
				if ((codeid != null) && (codeid.length() > 0)) {
					text = text + " &lt;<span class='ic'>" + codeid
							+ "</span>&gt;";
				}
				groupNode.put("text", text);
				long is = (Long) group.get("isparent");
				if (1 == is) {
					groupNode.put("state", "closed");
				} else {
					groupNode.put("state", "");
				}

				groupNodes.add(groupNode);
			}

			if (groupId.intValue() == 0) {
				if ((Integer.parseInt(roleid) != 1)
						&& (Integer.parseInt(roleid) != 2)) {
					HashMap<String, Object> outNode = new HashMap<String, Object>();
					groupNode = new HashMap<String, Object>();
					outNode.put("id", Integer.valueOf(-1));
					outNode.put("text",
							"<span class='optionname'>前台用户</span> &lt;<span class='ic'>qtyh</span>&gt;");
					outNode.put("state", "");
					groupNodes.add(outNode);
				}

				groupNode = new HashMap<String, Object>();
				groupNode.put("id", groupId);
				groupNode.put("text", "<span class='optionname'>全部机构</span>");
				groupNode.put("children", groupNodes);

				groupNodes = new ArrayList<Map<String, Object>>();
				groupNodes.add(groupNode);
			}

			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write(JSONUtil.writeListMapJSONMap(groupNodes));

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 用户组织机构查询
	 * 
	 * @param keyword
	 * @param roleid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/group_search", method = RequestMethod.POST)
	public void searchGroupDate(String keyword, String roleid,
			HttpServletRequest request, HttpServletResponse response) {
		if (!StringHelper.isNotBlack(keyword)) {
			return;
		}
		try {

			Map<String, Object> allNode = new HashMap<String, Object>();
			List<Map<String, Object>> groupNodes = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> groupNode = new HashMap<String, Object>();
			List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
			groupList = complatGroupService.findByNameOrPinYin(keyword);
			HashMap<String, Object> group = new HashMap<String, Object>();
			if (Integer.parseInt(roleid) != 1 && Integer.parseInt(roleid) != 2) {
				String strs = "前台用户qtyhQTYH";
				if (strs.indexOf(keyword) != -1) {
					HashMap<String, Object> outNode = new HashMap<String, Object>();
					groupNode = new HashMap<String, Object>();
					outNode.put("id", Integer.valueOf(-1));
					outNode.put("text",
							"<span class='optionname'>前台用户</span> &lt;<span class='ic'>qtyh</span>&gt;");
					outNode.put("state", "");
					groupNodes.add(outNode);
				}

				for (Map<String, Object> map : groupList) {
					groupNode = new HashMap<String, Object>();
					groupNode.put("id", map.get("iid"));
					groupNode.put("text", map.get("name"));
					groupNode.put("ic", map.get("codeid"));
					groupNodes.add(groupNode);
				}
			} else {
				for (Map<String, Object> g : groupList) {
					groupNode = new HashMap<String, Object>();
					groupNode.put("id", g.get("iid"));
					groupNode.put("text", g.get("name"));
					groupNode.put("ic", g.get("codeid"));
					groupNodes.add(groupNode);
				}
			}

			allNode.put("groups", groupNodes);

			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(allNode));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 用户组织机构查询
	 * 
	 * @param keyword
	 * @param roleid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user_search", method = RequestMethod.POST)
	public void searchUserDate(String keyword, String roleid,
			HttpServletRequest request, HttpServletResponse response) {
		if (!StringHelper.isNotBlack(keyword)) {
			return;
		}
		try {

			Map<String, Object> allNode = new HashMap<String, Object>();

			List<Map<String, Object>> groupNodes = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> userNodes = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> groupNode = new HashMap<String, Object>();
			HashMap<String, Object> userNode = new HashMap<String, Object>();

			List<Map<String, Object>> groupList = complatGroupService
					.findByNameOrPinYin(keyword);
			HashMap<String, Object> group = new HashMap<String, Object>();
			if (Integer.parseInt(roleid) != 1 && Integer.parseInt(roleid) != 2) {
				String strs = "前台用户qtyhQTYH";
				if (strs.indexOf(keyword) != -1) {
					HashMap<String, Object> outNode = new HashMap<String, Object>();
					groupNode = new HashMap<String, Object>();
					outNode.put("id", Integer.valueOf(-1));
					outNode.put("text",
							"<span class='optionname'>前台用户</span> &lt;<span class='ic'>qtyh</span>&gt;");
					outNode.put("state", "");
					groupNodes.add(outNode);
				}

				for (Map<String, Object> map : groupList) {
					groupNode = new HashMap<String, Object>();
					groupNode.put("id", map.get("iid"));
					groupNode.put("text", map.get("name"));
					groupNode.put("ic", map.get("codeid"));
					groupNodes.add(groupNode);
				}
			} else {
				for (Map<String, Object> g : groupList) {
					groupNode = new HashMap<String, Object>();
					groupNode.put("id", g.get("iid"));
					groupNode.put("text", g.get("name"));
					groupNode.put("ic", g.get("codeid"));
					groupNodes.add(groupNode);
				}
			}

			List<Map<String, Object>> userList = complatUserService
					.findByNameOrPinYin(keyword);
			for (Map<String, Object> user : userList) {
				userNode = new HashMap<String, Object>();
				userNode.put("id", user.get("iid") + "_0");
				userNode.put("text", user.get("name"));
				userNode.put("ic", user.get("loginname"));
				userNodes.add(userNode);
			}

			List<Map<String, Object>> outUserList = complatOutsideuserService
					.findByNameOrPinYin(keyword);
			for (Map<String, Object> outuser : outUserList) {
				userNode = new HashMap<String, Object>();
				userNode.put("id", outuser.get("iid") + "_1");
				userNode.put("text", outuser.get("name"));
				userNode.put("ic", outuser.get("loginname"));
				userNodes.add(userNode);
			}

			allNode.put("groups", groupNodes);
			allNode.put("users", userNodes);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(allNode));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * 保存用户机构角色关系
	 * @param roleId
	 * @param users
	 * @param groups
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/modify_submit", method = RequestMethod.POST)
	public void modifySubmit(String roleId, String users, String groups,
			HttpServletRequest request, HttpServletResponse response) {
		if (!StringHelper.isNotBlack(roleId)) {
			return;
		}
		List<String> groupList = new ArrayList<String>();
		List<String> userIdList = new ArrayList<String>();
		try {

			if (StringHelper.isNotBlack(groups)) {
				groupList = Arrays.asList(groups.split(","));
			}
			if (StringHelper.isNotBlack(users)) {
				userIdList = Arrays.asList(users.split(","));
			}
			boolean isSuccess = true;
			List<Integer> groupids = new ArrayList<Integer>();
			List<Map<String, Object>> groupslist = complatGroupService
					.findAllIidsAndName();
			for (Map<String, Object> group : groupslist) {
				groupids.add((Integer) group.get("iid"));
			}
			for (String groupid : groupList) {
				if (Integer.parseInt(groupid) == 0) {
					for (Integer gid : groupids) {
						if (jisRoleobjectService.findObjectSize(
								Integer.parseInt(roleId), gid.intValue(), 2) == 0) {
							isSuccess = jisRoleobjectService
									.add(Integer.parseInt(roleId),
											gid.intValue(), 2);
						}
					}
					break;
				}
				if (jisRoleobjectService.findObjectSize(
						Integer.parseInt(roleId), Integer.parseInt(groupid), 2) == 0) {
					isSuccess = jisRoleobjectService.add(
							Integer.parseInt(roleId),
							Integer.parseInt(groupid), 2);
				}

			}
			for (String userid : userIdList) {
				int tempindex = userid.indexOf("_");
				int type = Integer.parseInt(userid.substring(tempindex + 1));
				int userId = Integer.parseInt(userid.substring(0, tempindex));
				if (type == 0) {
					if (jisRoleobjectService.findObjectSize(
							Integer.parseInt(roleId), userId, 0) == 0)
						isSuccess = jisRoleobjectService.add(
								Integer.parseInt(roleId), userId, 0);
				} else if ((type == 1)
						&& (jisRoleobjectService.findObjectSize(
								Integer.parseInt(roleId), userId, 1) == 0)) {
					isSuccess = jisRoleobjectService.add(
							Integer.parseInt(roleId), userId, 1);
				}
			}
			Map<String,Object> result = new HashMap<String,Object>();
			if(isSuccess){
				result.put("result", true);
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
			}else{
				result.put("result", false);
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSONUtil.writeMapJSON(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	};
	
	
	
	/**
	 * 
	 * 应用相关授权方法
	 */

	// 应用权限
	@RequestMapping(value = "/appAuthorizeShow", method = RequestMethod.GET)
	public String appAuthorizeShow(String roleId, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		try {

			List<JisRoleobject> objects = jisRoleobjectService
					.findByRoleIdAndType(Integer.parseInt(roleId), 3);
			List<JisApplication> apps = jisApplicationService.findAll();
			List<Map<String, Object>> show = new ArrayList<Map<String, Object>>();
			for (JisApplication app : apps) {
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("name", app.getName());
				values.put("iid", app.getIid());
				values.put("approleid", 0);
				for (JisRoleobject object : objects) {
					if (app.getIid().equals(object.getObjectid())) {
						values.put("approleid", 1);
					}

				}
				show.add(values);
			}

			model.addAttribute("appList", show);
			model.addAttribute("iid", roleId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "users/complatrole/app_authorize";
	}

	@RequestMapping(value = "/modifyApps", method = RequestMethod.POST)
	public void submitModifyApps(String iid, String apps,
			HttpServletRequest request, HttpServletResponse response) {
		if (apps == null) {
			apps = "";
		}
		boolean flag = jisRoleobjectService.modifyRoleApps(
				Integer.parseInt(iid), apps);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			if (flag) {
				result.put("success", true);
			} else {
				result.put("success", false);
			}
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONUtil.writeMapJSON(result));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 系统菜单相关授权方法
	 * 
	 */

	// 系统权限
	@RequestMapping(value = "/croleAuthorizeShow", method = RequestMethod.GET)
	public String roleAuthorizeShow(String roleId, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		try {
			model.addAttribute("roleId", roleId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "users/complatrole/role_authorize";
	}

	// 获取权限树json
	@RequestMapping(value = "/croleAuthorizeList", method = RequestMethod.POST)
	public void roleAuthorizeList(String roleId, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		try {
			// SysUserSession sysUserSession = (SysUserSession)
			// request.getSession().getAttribute("sysUserSession");
			String treeJson = complatRoleService.getAuthorizeTree(roleId);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(treeJson);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	// 保存权限
	@RequestMapping(value = "/roleAuthorizeSave", method = RequestMethod.POST)
	public void roleAuthorizeSave(String roleId, String keys, String types,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			if (roleId == null || keys == null || types == null) {
				response.getWriter().write("error");
			} else {
				complatRoleService.saveAuthorize(roleId, keys.trim(),
						types.trim());
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				response.getWriter().write("error");
			} catch (IOException e1) {
				logger.error(e1.getMessage(),e1);
			}
		}
	}

}
