package com.gsww.uids.manager.role.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.service.RoleService;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.system.controller.SystemLog;

/**
 * 机构管理控制类
 * @author jinwei
 *
 */

@Controller
@RequestMapping("/role")
public class RoleController{
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 机构管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/roleMgr", method = RequestMethod.GET)
	public String roleMgr(Model model) {
		return "manager/role/roleMgr";
	}
	
	/**
	 * 机构管理树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/orgTree", method = RequestMethod.GET)
	public String organizationMenu(Model model) {
		
		return "manager/org/orgTree";
	}
	
	/**
	 * 获得角色list
	 * @param page 页数
	 * @param size 分页数大小
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/roleList", method = RequestMethod.POST)
	@ResponseBody
	public String roleList(String searchText,@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows) {
		return roleService.findPage(page, rows,searchText).toJSONString();
	}
	
	
	/**
	 * 角色新增或编辑
	 * @param model
	 * @param iid 角色iid
	 * @param currentPage 当前页
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/roleNewOrEdit", method = RequestMethod.GET)
	public String roleNewOrEdit(Model model, String iid,String currentPage) {
		Role info = roleService.findById(iid);
		model.addAttribute("info", info);
		model.addAttribute("currentPage", currentPage);
		return "manager/role/roleNewOrEdit";
	}
	
	/**
	 * 保存角色
	 * @param model
	 * @param role 角色对象
	 * @param currentPage 当前页
	 * @return
	 */
	@SystemLog(description = "角色", module = OperationLog.ROLE_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/roleSaveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String roleSaveOrUpdate(Model model, Role role,String currentPage, String operate) {
		JSONObject resultJson = new JSONObject();
		// 验证唯一性
		StringBuilder errInfo = new StringBuilder();
		if ( !roleService.checkUnique(role, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("message", errInfo.toString() + "，请重新修改！");
			return resultJson.toString();
		}
		roleService.saveOrUpdate(role);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("message", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 删除
	 * @return
	 */
	@SystemLog(description = "角色", module = OperationLog.ROLE_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/roleDele", method = RequestMethod.POST)
	@ResponseBody
	public String roleDele(String ids) {
		
		JSONObject resultJson = new JSONObject();
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !roleService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，不能删除！");
			return resultJson.toString();
		}
		
		// 删除
		roleService.delete(ids);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 打开或者关闭有效状态
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/openOrClose",method = RequestMethod.POST)
	@ResponseBody
	public String openOrClose(String ids, String status) {
		
		JSONObject resultJson = new JSONObject();
		
		// 启用
		if ( "open".equalsIgnoreCase(status) ) {
			roleService.openOrClose(ids, status);
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("info", "启用成功！");
			return resultJson.toString();
		}

		// 检查能否禁用
		StringBuilder errInfo = new StringBuilder();
		if ( !roleService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，不能禁用！");
			return resultJson.toString();
		}
		
		// 禁用
		roleService.openOrClose(ids, status);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "禁用成功！");
		return resultJson.toString();
	}
}
