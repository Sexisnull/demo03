package com.gsww.uids.manager.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.enums.AreaEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

/**
 * 系统管理控制类
 * @author sunbw
 *
 */

@Controller
@RequestMapping("/sysArea")
public class SysAreaController {
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 区域树管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/areaTree", method = RequestMethod.GET)
	public String areaTree(Model model,String uuid) {
		
		return "manager/sys/area/areaTree";
	}
	
	/**
	 * 区域管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/areaMgr", method = RequestMethod.GET)
	public String areaMgr(Model model,String uuid) {
		if(uuid == null){
			uuid = "0";
		}
		model.addAttribute("rootId", uuid);
		return "manager/sys/area/areaMgr";
	}
	
	/**
	 * 获得区域list
	 * @param page 页数
	 * @param size 分页数大小
	 * @param searchText 检索内容
	 * @param uuid 要展示的父级uuid 
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public String list(String searchText,String rootId,@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows) {
		return areaService.findPage(page, rows,rootId,searchText).toJSONString();
	}
	
	
	/**
	 * 区域新增或编辑
	 * @param model
	 * @param id 角色id
	 * @param currentPage 当前页
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/newOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, String iid,String rootId,String currentPage) {
		Area info = areaService.findById(iid);
		model.addAttribute("info", info);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("parentOrChild",info == null?"":String.valueOf(info.getParentOrChild()));
		// 所有区域
		model.addAttribute("areaList", AreaEnum.getValueArray());
		//所属区域
		model.addAttribute("area", areaService.findById(rootId));
		model.addAttribute("parentUuid", rootId);
		return "manager/sys/area/areaNewOrEdit";
	}
	
	/**
	 * 保存区域
	 * @param model
	 * @param parentUuid 保存对象的父亲uuid
	 * @param area 区域对象
	 * @param currentPage 当前页
	 * @return
	 */
	@SystemLog(description = "区域", module = OperationLog.SYS_AREA_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(Model model, Area area,String parentUuid,String oldParentUuid,String currentPage, String operate) {
		JSONObject resultJson = new JSONObject();
		if(area.getUuid().equals(parentUuid)){
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("message", "上级区域不能选择自己！");
			return resultJson.toString();
		}
		Area parent = areaService.findById(parentUuid);
		parent.setParentOrChild(1);//新增时，把父设置为是父节点
		areaService.saveOrUpdate(parent);
		area.setParent(parent);//给新增儿子对象中关联父亲对象
		// 验证唯一性
		if(area.getUuid() == null){
			StringBuilder errInfo = new StringBuilder();
			if ( !areaService.checkUnique(area, errInfo) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("message", errInfo.toString() + "，请重新修改！");
				return resultJson.toString();
			}
		}
		areaService.saveOrUpdate(area);
		//判断修改后了的区域以前的父节点是否有其他的子节点，如果没有把他变成叶子节点
		if(StringUtil.isNotBlank(oldParentUuid)){
			List<Area> other = areaService.findChild(oldParentUuid);
			if(other.isEmpty()){
				Area oldParent = areaService.findById(oldParentUuid);
				oldParent.setParentOrChild(0);
				areaService.saveOrUpdate(oldParent);
			}
		}
		resultJson.put("state", 1);
		resultJson.put("message", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 
	 * @param ids 删除数组
	 * @param rootId 父级id
	 * @return
	 */
	@SystemLog(description = "区域", module = OperationLog.SYS_AREA_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/dele",method = RequestMethod.POST)
	@ResponseBody
	public String dele(String ids,String rootId) {
		
		JSONObject resultJson = new JSONObject();
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder();
		if ( !areaService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，不能删除！");
			return resultJson.toString();
		}
		
		// 删除
		areaService.delete(ids);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 树中根据父节点，逐级展开
	 * @param uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/parentSearch", method = RequestMethod.POST)
	@ResponseBody
	public String parentSearch(String uuid) {
		if(uuid == null || uuid == ""){
			uuid = "0";
		}
		List<Area>	list = 	areaService.findNextList(uuid);
		JSONArray result = new JSONArray();
		JSONObject json = null;
		for(Area area : list){
			json = new JSONObject();
			json.put("id", area.getUuid());
			json.put("name", area.getName());
			json.put("isParent", area.getParentOrChild());
			json.put("url", "areaMgr.uids?uuid="+area.getUuid());
			json.put("target", "page");
			result.put(json);
		}
		return result.toString();
	}
	
	/**
	 * 打开或者关闭有效状态
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/openOrClose", method = RequestMethod.POST)
	@ResponseBody
	public String openOrClose(String ids, String status) {
		
		JSONObject resultJson = new JSONObject();
		
		// 启用
		if ( "open".equalsIgnoreCase(status) ) {
			areaService.openOrClose(ids, status);
			resultJson.put("state", MessageInfo.STATE_SUCCESS);
			resultJson.put("info", "启用成功！");
			return resultJson.toString();
		}

		// 检查能否禁用
		StringBuilder errInfo = new StringBuilder();
		if ( !areaService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString() + "，不能禁用！");
			return resultJson.toString();
		}
		
		// 禁用
		areaService.openOrClose(ids, status);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "禁用成功！");
		return resultJson.toString();
	}

	
	/**
	 * 需要一个区域下的所有子区域
	 * 
	 * @param model
	 * @param parentId
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/findAreas", method = RequestMethod.POST)
	@ResponseBody
	public String findChildAreas(Model model, String parentId) {
		
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
				
		// 查找子区域
		List<Area> childAreas = areaService.findChild(parentId);
		
		// 返回结果
		resultJson.put("state", 1);
		resultJson.put("info", "查找成功！");
		resultJson.put("data", childAreas);
		return resultJson.toString();
	}
	
	/**
	 * input框的选择树
	 * @param uuid
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/inputInTree", method = RequestMethod.POST)
	@ResponseBody
	public String inputInTree(String uuid) {
		if(uuid == null || uuid == ""){
			uuid = "0";
		}
		List<Area>	list = 	areaService.findNextList(uuid);
		JSONArray result = new JSONArray();
		JSONObject json = null;
		for(Area area : list){
			json = new JSONObject();
			json.put("id", area.getUuid());
			json.put("name", area.getName());
			json.put("isParent", area.getParentOrChild());
			json.put("target", "page");
			result.put(json);
		}
		return result.toString();
	}
}
