package com.gsww.uids.manager.org.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.gsww.common.enums.OrgTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.excel.service.ExcelService;
import com.gsww.uids.manager.excel.util.ExportMapping;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationErrorTemp;
import com.gsww.uids.manager.org.entity.OrganizationMergeTemp;
import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.entity.OrganizationRelationType;
import com.gsww.uids.manager.org.service.OrganizationRelationService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.shiro.realm.WebRealm;
import com.gsww.uids.system.controller.SystemLog;

/**
 * 机构管理控制类
 * @author jinwei
 *
 */
@Controller
@RequestMapping("/org")
public class OrgController {
	
	private static final Logger logger = Logger.getLogger(OrgController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OrganizationRelationService organizationRelationService;
	
	@Autowired
	private ExcelService excelService;
	
	/**
	 * 机构管理
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String organizationMgr(Model model, String pId, String code) {
		
		model.addAttribute("pId", pId);
		model.addAttribute("relationCode", code);
		model.addAttribute("areaList", AreaEnum.getValueArray());
		return "manager/org/index";
	}
	
	/**
	 * list数据
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public String getData(String pId, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int rows,
			String orgName, String nodeType, String areaType, String areaCode, String code, String relationCode) {
		if(StringUtil.isNotBlank(relationCode)){
			return organizationRelationService.findPage(page, rows, pId, relationCode);
		}else{
			return organizationService.findPage(page, rows , pId, orgName, nodeType, areaCode, areaType, code).toJSONString();
		}
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
		model.addAttribute("classifyList", organizationRelationService.findAll());
		return "manager/org/orgTree";
	}
	
	/**
	 * 机构管理树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/orgTree/detailData", method = RequestMethod.POST)
	@ResponseBody
	public String detailData(String pId, String code) {
		
		List<Organization>	list = null;
		
		if(StringUtil.isBlank(pId)){
			list = new ArrayList<Organization>();
			list.add(organizationService.getRootOrganization());
		}else{
			list = organizationService.findChild(pId);
		}
		
		JSONArray result = new JSONArray();
		JSONObject json = null;
	
		for(Organization org : list){
			json = new JSONObject();
			json.put("id", org.getUuid());
			json.put("pId", (org.getParent() == null) ? null : org.getParent().getUuid());
			json.put("name", org.getShortName());
			json.put("url", "index.uids?pId="+org.getUuid());
			json.put("target", "page");
			json.put("isParent", (org.getParentIs() == 1));
			result.put(json);
		}

		return result.toString();
	}
	
	/**
	 * 查询当前用户能否操作某些机构
	 * 
	 * @param orgIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/checkAdminPermission", method = RequestMethod.POST)
	@ResponseBody
	public String checkAdminPermission(String orgIds) {
		
		JSONObject resultJson = new JSONObject();
		
		for ( String orgId : orgIds.split(",") ) {
			Organization org = organizationService.findById(orgId);
			if ( !organizationService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", "对不起，您没有权限操作这些记录！");
				return resultJson.toString();
			}
		}
		
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "允许操作");		
		return resultJson.toString();
	}
	
	/**
	 * 新增或者编辑
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/newOrEdit", method = RequestMethod.GET)
	public String newOrEdit(Model model, String id, String orgId) {
		
		Organization info = organizationService.findById(id);
		
		
		//所属父机构
		if ( info.getParent() == null ) {
			Organization parent = organizationService.findById(orgId);
			model.addAttribute("parent", parent);
		} else {
			model.addAttribute("parent", info.getParent());
		}
		//所属区域
		model.addAttribute("area", info.getArea() == null ? new Area() : info.getArea());
				
		model.addAttribute("info", info);
		
		model.addAttribute("classifyList", organizationRelationService.findAll());
		
		List<JSONObject> typeList = new ArrayList<JSONObject>();
		for (OrgTypeEnum type: OrgTypeEnum.values()){
			JSONObject json = new JSONObject();
			json.put("name", type.getName());
			json.put("value", type.getValue());
			typeList.add(json);
		}
		
		model.addAttribute("typeList", typeList);
		
		return "manager/org/newOrEdit";
	}
	
	/**
	 * 保存
	 */
	@SystemLog(description = "机构", module = OperationLog.ORGANIZATION_MODULE, actionType = OperationLog.INSERT_UPDATE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(Organization org, String parentId, String areaId, String oldParentId, String parentOrgId, String relationTypeId, String deleteRelationId, String operate) {
		// 返回结果
		JSONObject resultJson = new JSONObject();
		
		try {
			// 检查上级机构的访问权限
			Organization parent = organizationService.findById(parentId);
			if ( !organizationService.canCurrentLoginUserAccessOrganization(parent, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
				resultJson.put("state", MessageInfo.STATE_FAIL);
				resultJson.put("info", String.format("对不起，您没有权限使用上级机构【%s】", parent.getShortName()));
				return resultJson.toString();
			}
			
			// 检查条线树的上级机构的访问权限
			if ( StringUtil.isNotBlank(parentOrgId) ) {
				String parentIdsOfClissify[] = parentOrgId.split(",");
				for ( String parentIdOfClissify : parentIdsOfClissify ) {
					Organization parentOfClissify = organizationService.findById(parentIdOfClissify);
					if ( !organizationService.canCurrentLoginUserAccessOrganization(parentOfClissify, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
						resultJson.put("state", MessageInfo.STATE_FAIL);
						resultJson.put("info", String.format("对不起，您没有权限使用上级机构【%】", parentOfClissify.getShortName()));
						return resultJson.toString();
					}
				}
			}			
			
			if(org.getUuid().endsWith(parentId)){
				resultJson.put("state", 7);
				resultJson.put("info", "父节点不可以为自己");
				return resultJson.toString();
			}
			
			//暂时区划及父机构给默认值
			StringBuilder errInfo = new StringBuilder(100);
			if (!organizationService.checkUnique(org, errInfo)) {
				resultJson.put("state", 7);
				resultJson.put("info", errInfo.toString() + "，请重新配置信息！");
				return resultJson.toString();
			}
			//所属父机构
			if(parent.getParentIs() == 0){
				parent.setParentIs(1);
				organizationService.saveOrUpdate(parent);
			}
			org.setParent(parent);
			//所属区域
			if (StringUtil.isNotBlank(areaId)) {
				org.setArea(areaService.findById(areaId));
			}
			
			Organization org_new = organizationService.saveOrUpdate(org);
			//判断修改后了的机构以前的父节点是否有其他的子节点，如果没有把他变成叶子节点
			if(StringUtil.isNotBlank(oldParentId)){
				List<Organization> other = organizationService.findChild(oldParentId);
				if(other.size() == 0){
					Organization oldParent = organizationService.findById(oldParentId);
					oldParent.setParentIs(0);
					organizationService.saveOrUpdate(oldParent);
				}
			}
			//保存按类型划分上级机构
			if(StringUtil.isNotBlank(parentOrgId)){
				String[] parentOrgIds = parentOrgId.split(",");
				String[] relationTypeIds = relationTypeId.split(",");
				
				OrganizationRelation orgRelation = null;
				
				for (int i = 0; i < parentOrgIds.length; i++) {
					orgRelation = new OrganizationRelation();
					
					orgRelation.setOrganization(org_new);
					
					Organization parentOfClissify = organizationService.findById(parentOrgIds[i]);
					orgRelation.setParentOrganization(parentOfClissify);
					
					OrganizationRelationType classify = organizationRelationService.findTypeById(relationTypeIds[i]);
					orgRelation.setClassify(classify);
					
					organizationRelationService.saveOrUpdate(orgRelation);
				}		
			}
			if(StringUtil.isNotBlank(deleteRelationId)){
				organizationRelationService.delete(deleteRelationId);
			}
			
			
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
	@SystemLog(description = "机构", module = OperationLog.ORGANIZATION_MODULE, actionType = OperationLog.DELETE_TYPE)
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids){
		
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(ids) ) {
			String idArray[] = ids.split(",");
			for ( String id : idArray ) {
				Organization org = organizationService.findById(id);
				if ( !organizationService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限删除这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 检查能否删除
		StringBuilder errInfo = new StringBuilder(100);
		if ( !organizationService.checkDelete(ids, errInfo) ) {
			resultJson.put("state", MessageInfo.STATE_FAIL);
			resultJson.put("info", errInfo.toString());
			return resultJson.toString();
		}
		
		// 删除
		organizationService.delete(ids);
		
		// 删除机构关系
		organizationRelationService.deleteByOrgId(ids);
		
		//返回结果
		resultJson.put("state", MessageInfo.STATE_SUCCESS);
		resultJson.put("info", "删除成功！");
		return resultJson.toString();
	}
	
	
	/**
	 * 区域编码树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/areaCodeSearch", method = RequestMethod.POST)
	@ResponseBody
	public String areaCodeSearch(String pId) {
		
		List<Area> children = null;
		if ( StringUtil.isBlank(pId) ) {
			children = new ArrayList<Area>();
			Area root = areaService.getRootOrganization();
			children.add(root);
		} else {
			children = areaService.findChild(pId);
		}

		JSONArray result = new JSONArray();
		JSONObject json = null;
	
		for(Area area : children){
			json = new JSONObject();
			json.put("id", area.getUuid());
			json.put("name", area.getName());
			json.put("isParent", area.getParentOrChild());
			json.put("code", area.getCode());
			result.put(json);
		}

		return result.toString();
	}
	
	/**
	 * 上级机构树
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebRealm.ACCOUNT_PERMISSION, WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/parentSearch", method = RequestMethod.POST)
	@ResponseBody
	public String parentSearch(String pId) {
		
		// 获取所有子节点
		List<Organization> children = null;
		if ( StringUtil.isBlank(pId) ) {
			children = new ArrayList<Organization>();
			Organization root = organizationService.getRootOrganization();
			children.add(root);
		} else {
			children = organizationService.findChild(pId);
		}
		
		JSONArray result = new JSONArray();
		JSONObject json = null;
	
		for(Organization org : children){
			json = new JSONObject();
			json.put("id", org.getUuid());
			json.put("pId", (org.getParent() == null) ? "" : org.getParent().getUuid());
			json.put("name", org.getShortName());
			json.put("isParent", (org.getParentIs() == 1));
			json.put("isDisabled", (org.getParent() == null));
			
			result.put(json);
		}

		return result.toString();
	}
	
	/**
	 * 导出机构信息
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@ResponseBody
	@RequestMapping(value="/orgExport", method = RequestMethod.GET)
	public String orgExport(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String exportField){
		
		JSONObject resultJson = new JSONObject();
		
		// 检查权限
		if ( StringUtil.isNotBlank(ids) ) {
			String idArray[] = ids.split(",");
			for ( String id : idArray ) {
				Organization org = organizationService.findById(id);
				if ( !organizationService.canCurrentLoginUserAccessOrganization(org, WebConstants.ROLE_KEY_ORG_ADMIN) ) {
					resultJson.put("state", MessageInfo.STATE_FAIL);
					resultJson.put("info", "对不起，您没有权限导出这些记录！");
					return resultJson.toString();
				}
			}
		}
		
		// 输出Excel文件
		OutputStream output = null;
		try {
			String exportName = ExportMapping.getOrgPropertiesName(exportField);
			output = response.getOutputStream();
			response.reset();
			String downLoadName = new String(("机构列表.xlsx").getBytes("gbk"), "iso8859-1");
			response.setHeader("Content-disposition", "attachment; filename=" + downLoadName);
			response.setContentType("application/x-download");
			XSSFWorkbook wb = excelService.exportOrgExcel(ids,exportName,exportField);
			// 保存Excel文件
			wb.write(output);
		} catch (IOException e) {
			logger.info("机构导出失败：" + e);
			resultJson.put("state", 0);
			resultJson.put("info", "导出机构信息失败！");
			return resultJson.toString();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		resultJson.put("state", 1);
		resultJson.put("info", "账号导出成功！");
		return resultJson.toString();	
	}
	
	/**
	 * 导入
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@ResponseBody
	@RequestMapping(value="/orgImport", method = RequestMethod.POST)
	public String orgImport(HttpServletRequest request, HttpServletResponse response,Model model) {
		// TODO 导入的时候，也要判断权限
		
		//返回前天的json数据
		String json = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		try {
			@SuppressWarnings("rawtypes")
			List fileList = upload.parseRequest(request);
			@SuppressWarnings("unchecked")
			Iterator<FileItem> it = fileList.iterator();
			 while (it.hasNext()){
				 FileItem item = (FileItem)it.next();
				 if (!item.isFormField()){
					 InputStream is = item.getInputStream();
					json =  excelService.importOrgExcel(is,item.getName());
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 机构排序
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/sort", method = RequestMethod.GET)
	public String orgSort(Model model, String pId) {
		
		List<Organization>	list = null;
		
		if(StringUtil.isBlank(pId)){
			pId = organizationService.getRootOrganization().getUuid();
		}
		list = organizationService.findChild(pId);
		
		model.addAttribute("orgList", list);
		
		return "manager/org/sort";
	}
	
	/**
	 * 保存排序
	 * @param orgOrderIds
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/sort/save",method = RequestMethod.POST)
	@ResponseBody
	public String saveOrder(String orgOrderIds) {
		return organizationService.saveOrderResult(orgOrderIds);
	}
	
	/**
	 * 查询机构树
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_USER_ADMIN, 
			WebConstants.ROLE_KEY_APP_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/searchParentOrgOrType", method = RequestMethod.POST)
	@ResponseBody
	public String searchParentOrgOrType(String pId, String typeId) {
		
		Organization org = organizationService.findById(pId);
		OrganizationRelationType type = organizationRelationService.findTypeById(typeId);
		
		//返回结果
		JSONObject resultJson = new JSONObject();
		resultJson.put("name", org.getShortName());
		resultJson.put("code", org.getCode());
		resultJson.put("relation", type.getName());
		
		resultJson.put("state", 1);
		resultJson.put("info", "查询成功！");
		return resultJson.toString();
	}
	
	/**
	 * 初始化机构导入窗口
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/initImport", method = RequestMethod.GET)
	public String initImport(Model model){
		return "manager/org/import";
	}
	
    /**
     * 机构导入模板下载
     * @param request
     * @param response
     * @param type
     * @throws IOException
     */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/")+"static/template/account/noncorporate.xlsx";
		File f = new File(url);
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		String downLoadName = new String(f.getName().getBytes("gbk"), "iso8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + downLoadName);

		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}
	
	/**
	 * 初始化修改表单
	 * @param model
	 * @return	
	 */
	 @RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	 @RequestMapping(value={"/initProblemAndMerge"}, method=RequestMethod.GET)
	 public String initProblemAndMerge(Model model) {
	    return "manager/org/modify";
	 }
	 
	 /**
	  * 返回有问题的账号提示信息和需要合并的账号信息
	  * @param model
	  * @return
	  */
	 @RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	 @RequestMapping(value = "/errorOrMergeData", method = RequestMethod.POST)
	 @ResponseBody
	 public String errorOrMergeData(Model model) {
		JSONObject resultJson = new JSONObject();
		List<OrganizationErrorTemp> errorList = organizationService.findErrAll();
		List<OrganizationMergeTemp> MergeList = organizationService.findMergeAll();
		net.sf.json.JSONArray errArr = net.sf.json.JSONArray.fromObject(errorList);
		net.sf.json.JSONArray mergeArr = net.sf.json.JSONArray.fromObject(MergeList);
	    resultJson.put("problem", net.sf.json.JSONArray.toArray(errArr, OrganizationErrorTemp.class));
	    resultJson.put("merge", net.sf.json.JSONArray.toArray(mergeArr, OrganizationMergeTemp.class));
		return resultJson.toString();
	 }
	 
	 /**
	 * 合并账号
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/mergeAccounts", method=RequestMethod.POST)
	@ResponseBody
	public String mergeAccounts(Model model,String tableinfo1) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		organizationService.mergeAfterUpdate(tableinfo1);
		// 返回保存成功结果
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();
	}
	
	/**
	 * 关闭窗口
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = {WebConstants.ROLE_KEY_ADMIN, WebConstants.ROLE_KEY_ORG_ADMIN}, logical = Logical.OR)
	@RequestMapping(value="/closeWindows", method=RequestMethod.POST)
	@ResponseBody
	public String closeWindows(Model model) {
		// 保存返回结果
		JSONObject resultJson = new JSONObject();
		boolean flag = organizationService.closeWindows();
		if(flag){
			resultJson.put("state", 1);//成功
		}else{
			resultJson.put("state", 0);//失败
			resultJson.put("info", "程序异常");
		}
		return resultJson.toString();
	}
}
