package com.gsww.uids.manager.org.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.entity.OrganizationRelationType;
import com.gsww.uids.manager.org.service.OrganizationRelationService;
import com.gsww.uids.manager.org.service.OrganizationService;

/**
 * 机构关系控制类
 * @author jinwei
 *
 */
@Controller
@RequestMapping("/org/relation")
public class OrgRelationController {
	
	@Autowired
	private OrganizationRelationService organizationRelationService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 上级机构
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/upper", method = RequestMethod.GET)
	public String getUpper(Model model, String orgId) {
		if(StringUtil.isBlank(orgId)){
			orgId = "0";
		}
		Organization org = organizationService.findById(orgId);
		model.addAttribute("org", org);
		model.addAttribute("parent", org.getParent() == null ? new Organization() : org.getParent());
		model.addAttribute("classifyList", organizationRelationService.findAll());
		
		
		return "manager/org/relation/upperOrg";
	}
	
	/**
	 * 下级机构
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/down", method = RequestMethod.GET)
	public String getDown(Model model, String orgId) {
		if(null == orgId){
			orgId = "0";
		}
		model.addAttribute("orgId", orgId);
		
		return "manager/org/relation/downOrg";
	}
	
	/**
	 * list数据
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(String orgId, String type) {
		
		JSONArray result = new JSONArray();
		JSONObject json = null;
		
		List<OrganizationRelation> list	= null;	

		if("upper".equals(type)){
			list = organizationRelationService.finParent(orgId, null);
			for(OrganizationRelation relation : list){
				json = new JSONObject();
				json.put("uuid", relation.getUuid());
				json.put("relation", relation.getClassify().getName());
				json.put("code", relation.getParentOrganization().getCode());
				json.put("name", relation.getParentOrganization().getShortName());	
				result.put(json);
			}
		}else{
			list = organizationRelationService.findChildByPIdAndCode(orgId, null);
			for(OrganizationRelation relation : list){
				json = new JSONObject();
				json.put("shortName", relation.getOrganization().getShortName());
				json.put("code", relation.getOrganization().getCode());
				json.put("suffix", relation.getOrganization().getSuffix());
				json.put("type", relation.getOrganization().getType());
				json.put("fullName", relation.getOrganization().getFullName());
				result.put(json);
			}
		}
		return result.toString();
	}

	/**
	 * 保存
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(OrganizationRelation orgRelation, String orgId, String parentOrgId, String classifyId) {
		//返回结果
		JSONObject resultJson = new JSONObject();
		try {			
			//所属父机构
			if (StringUtil.isNotBlank(parentOrgId)) {
				Organization parent = organizationService.findById(parentOrgId);
				
				// 检查权限
				if ( !organizationService.canCurrentLoginUserAccessOrganization(parent, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", String.format("对不起，您没有权限使用上级机构【%s】！", parent.getShortName()));
					return resultJson.toString();
				}
				
				orgRelation.setParentOrganization(parent);
			}
			//所属子机构
			if (StringUtil.isNotBlank(orgId)) {
				Organization child = organizationService.findById(orgId);
				orgRelation.setOrganization(child);
			}
			//分类类型
			if(StringUtil.isNotBlank(classifyId)){
				OrganizationRelationType classify = organizationRelationService.findTypeById(classifyId);
				orgRelation.setClassify(classify);
			}
			organizationRelationService.saveOrUpdate(orgRelation);
		} catch (Exception e) {
			resultJson.put("state", 7);
			resultJson.put("info", "保存失败，请重新配置信息！");
			return resultJson.toString();
		}
		
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids){
		
		JSONObject resultJson = new JSONObject();
		
		StringBuilder errInfo = new StringBuilder(100);
		if ( !organizationRelationService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		organizationRelationService.deleteByOrgId(ids);
		
		//返回结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", MessageInfo.DELETE_SUCCESS);
		return resultJson.toString();
	}
	
	/**
	 * 机构管理树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/detailData", method = RequestMethod.POST)
	@ResponseBody
	public String detailData(String pId, String code) {
		

		JSONArray result = new JSONArray();
		JSONObject json = null;
		List<OrganizationRelation>	list = null;

		if(StringUtil.isBlank(pId)){
			Organization org = organizationService.getRootOrganization();
			OrganizationRelation relation = new OrganizationRelation();
			list = new ArrayList<OrganizationRelation>();
			relation.setOrganization(org);
			list.add(relation);
		}else{
			list = organizationRelationService.findChildByPIdAndCode(pId, code);
		}
		
		for(OrganizationRelation relation : list){
			json = new JSONObject();
			json.put("id", relation.getOrganization().getUuid());
			json.put("pId", (relation.getParentOrganization() == null) ? null : relation.getParentOrganization().getUuid());
			json.put("name", relation.getOrganization().getShortName());
			json.put("url", "index.uids?pId="+relation.getOrganization().getUuid()+"&code="+code);
			json.put("target", "page");
			//是否为父节点
			
			List<OrganizationRelation>	list1 = organizationRelationService.findChildByPIdAndCode(relation.getOrganization().getUuid(), code);
			if(list1.size() > 0){
				json.put("isParent", true);
			}else{
				json.put("isParent", false);
			}
			result.put(json);
		}
		return result.toString();
	}
}
