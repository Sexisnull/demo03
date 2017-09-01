package com.gsww.uids.manager.sys.controller;

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
import com.gsww.uids.manager.sys.service.BanService;

/**
 * 系统封停控制类
 * @author sunbw
 *
 */

@Controller
@RequestMapping("/sysBan")
public class SysBanController {
	
	@Autowired
	private BanService banService;
	
	/**
	 * 封停管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/banMgr", method = RequestMethod.GET)
	public String banMgr(Model model) {
		return "manager/sys/ban/banMgr";
	}
	
	/**
	 * 获取日志list
	 * @param page 页数
	 * @param size 每页记录数
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public String list(String searchText,@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows) {
		return banService.findPage(page, rows,searchText).toJSONString();
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/dele",method = RequestMethod.POST)
	@ResponseBody
	public String dele(String ids) {
		JSONObject resultJson = new JSONObject();
		banService.delete(ids);
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
	
}
