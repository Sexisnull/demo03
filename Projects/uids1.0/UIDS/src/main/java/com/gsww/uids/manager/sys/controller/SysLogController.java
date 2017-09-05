package com.gsww.uids.manager.sys.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.enums.ModuleEnum;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.sys.service.LogService;

/**
 * 系统操作日志控制类
 * @author sunbw
 *
 */

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
	
	@Autowired
	private LogService logService;
	
	/**
	 * 日志管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/logMgr", method = RequestMethod.GET)
	public String logMgr(Model model) {
		// 所有模块
		model.addAttribute("moduleList", ModuleEnum.getValueArray());
		return "manager/sys/log/logMgr";
	}
	
	/**
	 * 获得日志list
	 * @param page 页数
	 * @param size 分页数大小
	 * @param searchText 检索内容
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public String list(String module,String logDesc,String startTime,String endTime, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows) {
		return logService.findPage(page, rows,module,logDesc,startTime,endTime).toJSONString();
	}
	
}
