package com.gsww.uids.manager.sys.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.enums.AuthModel;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.entity.SystemAuthParam;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.entity.SystemEmailParam;
import com.gsww.uids.manager.sys.entity.SystemSMSParam;
import com.gsww.uids.manager.sys.service.SysConfigService;
import com.gsww.uids.system.controller.SystemLog;

import net.sf.json.JSONObject;

/**
 * 系统配置
 * @author simplife
 *
 */
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 配置管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String organizationMgr(Model model) {
		model.addAttribute("smsParam", sysConfigService.getSystemSMSParam());
		model.addAttribute("basicParam", sysConfigService.getSystemBasicParam());
		model.addAttribute("emailParam", sysConfigService.getSystemEmailParam());
		model.addAttribute("authParam", sysConfigService.getSystemAuthParam());
		model.addAttribute("authModel", AuthModel.getValueArray());
		return "manager/sys/config/index";
	}

	/**
	 * 配置管理
	 * @param model
	 * @return
	 */
	@SystemLog(description = "更新系统参数", module = OperationLog.SYS_PARAM_MODULE, actionType = OperationLog.UPDATE_TYPE)
	@RequiresPermissions(WebConstants.ROLE_KEY_ADMIN)
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(String authUUID, String smsUUID, String busiUUID,String emialUUID,SystemAuthParam systemAuthParam, SystemBasicParam systemBasicParam, SystemSMSParam systemSMSParam,SystemEmailParam systemEmailParam) {
		
		systemBasicParam.setUuid(busiUUID);
		systemSMSParam.setUuid(smsUUID);
		systemEmailParam.setUuid(emialUUID);
		systemAuthParam.setUuid(authUUID);
		
		sysConfigService.saveOrUpdate(systemBasicParam);
		sysConfigService.saveOrUpdate(systemSMSParam);
		sysConfigService.saveOrUpdate(systemEmailParam);
		sysConfigService.saveOrUpdate(systemAuthParam);
		
		// 结果数据
		JSONObject resultJson = new JSONObject();
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info",  "系统参数保存成功");
		return resultJson.toString();
	}	
}
