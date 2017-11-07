package com.gsww.uids.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gsww.jup.entity.sys.SysUserSession;
import net.sf.json.JSONArray;
import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.Constants;
import com.gsww.jup.controller.BaseController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.ExcelUtil;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatZone;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatZoneService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewService;

@Controller
@RequestMapping(value = "/uids")
public class ComplatGroupController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ComplatGroupController.class);
    @Autowired
    private ComplatGroupService complatGroupService;
    @Autowired
    private SysParaService sysParaService;
    @Autowired
    private ComplatZoneService complatZoneService;
    @Autowired
    private JisApplicationService jisApplicationService;
    @Autowired
    private JisSysviewService jisSysviewService;
    @Autowired
    private JisSysviewDetailService jisSysviewDetailService;
    //节点类型下拉选择集合
    private Map<Integer, Object> nodetypeMap = new HashMap<Integer, Object>();
    //区域类型下拉选择集合
    private Map<Integer, Object> areatypeMap = new HashMap<Integer, Object>();
    
    private String strWarn;//导入失败的提示语句
    
    
    @RequestMapping(value = "/groupOrgTree", method = RequestMethod.GET)
	public String complatList(
			String jumpId,Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			// 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) hrequest.getSession().getAttribute("sysUserSession");
			String deptId = sysUserSession.getDeptId();
			//点击完查询时组织机构名称回显
			String groupName = request.getParameter("groupname");
			model.addAttribute("groupName", groupName);
			//获取跳转时的id
			if(StringUtils.isNotBlank(jumpId)){
				model.addAttribute("jumpId", jumpId);
			}else{
				String backId = request.getParameter("backId");
				model.addAttribute("jumpId", backId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("机构树打开失败：" + ex.getMessage());
			returnMsg("error", "机构树打开失败", (HttpServletRequest) request);
			return "redirect:/uids/complatList";
		}
		return "users/complat/complatgroup_tree";
	}

    /**
     * 获取用户列表
     *
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/complatgroupList", method = RequestMethod.GET)
    public String complatgroupList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
                                   @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
                                   @RequestParam(value = "order.field", defaultValue = "createtime") String orderField,
                                   @RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
                                   @RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
                                   String orgId,String jumpId,Model model, ServletRequest request, HttpServletRequest hrequest) {
        try {
            if (StringUtils.isNotBlank(request.getParameter("orderField"))) {
                orderField = (String) request.getParameter("orderField");
            }
            if (StringUtils.isNotBlank(request.getParameter("orderSort"))) {
                orderSort = (String) request.getParameter("orderSort");
            }
           
            //初始化分页数据
            PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField, orderSort);
            PageRequest pageRequest = super.buildPageRequest(hrequest, pageUtils, ComplatGroup.class, findNowPage);
            
         // 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) hrequest.getSession().getAttribute("sysUserSession");
			String deptId = sysUserSession.getDeptId();

            //搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			if(StringUtils.isNotBlank(jumpId)){//保存，删除，编辑，返回页面跳转
				searchParams.put("EQ_pid", jumpId);
			}else{
				if (StringUtils.isNotBlank(orgId)) {
					searchParams.put("EQ_pid", orgId);
					model.addAttribute("orgId", orgId);
				}else{
					if(searchParams.size()>=1&&searchParams.get("EQ_pid") != null){
						model.addAttribute("orgId", searchParams.get("EQ_pid"));
					}else{
						searchParams.put("EQ_pid", deptId);
						model.addAttribute("orgId", deptId);
					}
				}
			}
			Specification<ComplatGroup>  spec=super.toSpecification(searchParams, ComplatGroup.class);
			//分页
			Page<ComplatGroup> pageInfo = complatGroupService.getUserPage(spec,pageRequest);
			//查询上级机构名称
			for(ComplatGroup complatGroup:pageInfo.getContent()){				
				if(null != complatGroup.getPid()){
					String parentName = complatGroupService.findByIid(complatGroup.getPid()).getName();
					complatGroup.setParentName(parentName);
				}
			}
			//下拉选择查询
			//节点类型
			List<Map<String, Object>> nodetypeList = new ArrayList<Map<String, Object>>();
			nodetypeList = sysParaService.getParaList("JGJDLX");
			for (Map<String, Object> para : nodetypeList) {
				nodetypeMap.put(Integer.parseInt((String) para.get("PARA_CODE")), para.get("PARA_NAME"));
			}
			//区域类型
			List<Map<String, Object>> areatypeList = new ArrayList<Map<String, Object>>();
			areatypeList = sysParaService.getParaList("JGQYLX");
			for (Map<String, Object> para : areatypeList) {
				areatypeMap.put(Integer.parseInt((String) para.get("PARA_CODE")), para.get("PARA_NAME"));
			}
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("nodetypeMap", nodetypeMap);
			model.addAttribute("areatypeMap", areatypeMap);
			model.addAttribute("sortType", orderField);
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderSort", orderSort);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "users/complat/complatgroup_list";
	}
	
	/**
	 * 新增，编辑，删除时同步数据到表jis_sysview
	 * @param complatGroup
	 * @param type
	 * @throws Exception
	 */
	public void synchronization(ComplatGroup complatGroup, int type) throws Exception{
		try{
			List<JisApplication> list = jisApplicationService.findByIsSyncGroupNotNullAndLoginType(0); //查询支持同步的应用
			Random random = new Random(); //初始化随机数
			String data = TimeHelper.getCurrentCompactTime(); //获得同步时间
			String synctime = ""; //记录修改或新增的时间
			String operatetype = ""; //操作类型
			//判断同步类型,根据同步类型的不同来获取不同的数据
			if(type == 1){
				operatetype = "新增机构";
				synctime = String.valueOf(complatGroup.getCreatetime());
			}else if(type == 2){
				operatetype = "修改机构";
				synctime = String.valueOf(complatGroup.getModifytime());
			}else if(type == 3){
				operatetype = "删除机构";
				synctime = String.valueOf(complatGroup.getModifytime());
			}
			//将所有支持同步的应用进行同步
			for(JisApplication jisApplication : list){
				JisSysview jisSysview = new JisSysview();
				jisSysview.setObjectid(String.valueOf(complatGroup.getIid()));
				jisSysview.setObjectname(complatGroup.getName());
				jisSysview.setState("C");
				jisSysview.setResult("TG");
				jisSysview.setOptresult(1);
				jisSysview.setSynctime(synctime);
				jisSysview.setAppid(jisApplication.getIid());
				jisSysview.setCodeid(complatGroup.getCodeid());
				jisSysview.setTimes(1);
				jisSysview.setOperatetype(operatetype);
				int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
				jisSysview.setTranscationId(data + String.valueOf(rannum));
				jisSysviewService.save(jisSysview);
				syncDetail(complatGroup, jisApplication, jisSysview);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 同步数据到表jis_sysview_detail
	 * @param complatGroup
	 * @param jisApplication
	 * @throws Exception
	 */
	public void syncDetail(ComplatGroup complatGroup, JisApplication jisApplication, JisSysview jisSysview) throws Exception{
		try{
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("allParCode", complatGroup.getSuffix());
			jsonMap.put("allParName", complatGroup.getGroupallname());
			jsonMap.put("appName", jisApplication.getName());
			jsonMap.put("appid", String.valueOf(jisApplication.getIid()));
			jsonMap.put("cardId","");
			jsonMap.put("compfax","");
			jsonMap.put("comptel","");
			jsonMap.put("email","");
			jsonMap.put("groupCode",complatGroup.getCodeid());
			jsonMap.put("groupName",complatGroup.getName());
			jsonMap.put("headShip","");
			jsonMap.put("hometel","");
			jsonMap.put("id", String.valueOf(complatGroup.getIid()));
			jsonMap.put("loginName","");
			jsonMap.put("loginPass","");
			jsonMap.put("mobile","");
			jsonMap.put("msn","");
			jsonMap.put("ndlogin","");
			if(complatGroup.getPid() != null){
				jsonMap.put("parCode",complatGroupService.findByIid(complatGroup.getPid()).getCodeid());
				jsonMap.put("parName",complatGroupService.findByIid(complatGroup.getPid()).getName());
			}else{
				jsonMap.put("parCode","");
				jsonMap.put("parName","");
			}
			jsonMap.put("qq","");
			jsonMap.put("state","TG");
			jsonMap.put("userName","");
			String detail = JSONUtil.writeMapJSON(jsonMap);
			JisSysviewDetail jisSysviewDetail = new JisSysviewDetail();
			jisSysviewDetail.setSendmsg(detail);
			jisSysviewDetail.setTranscationId(jisSysview.getTranscationId());
			jisSysviewDetailService.save(jisSysviewDetail);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 编辑用户页面
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatgroupEdit", method = RequestMethod.GET)
	public String complatgroupEdit(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			ComplatGroup complatGroup = null;
			if(StringHelper.isNotBlack(iid)){
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				//将pid转换为parentName在编辑框中显示
				complatGroup = complatGroupService.findByIid(Integer.valueOf(iid));
				if(complatGroup.getPid() != null){
					String parentName = complatGroupService.findByIid(complatGroup.getPid()).getName();
					complatGroup.setParentName(parentName);
				}
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				complatGroup = new ComplatGroup();
				SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
                // 获取部门id
                String deptId = sysUserSession.getDeptId();
                String name = complatGroupService.findByIid(Integer.valueOf(deptId)).getName();
                model.addAttribute("areacode", "620000000000");
                model.addAttribute("groupid", deptId);
                model.addAttribute("groupname", name);
                
			}
			model.addAttribute("nodetypeMap", nodetypeMap);
			model.addAttribute("areatypeMap", areatypeMap);
			model.addAttribute("complatGroup", complatGroup);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "users/complat/complatgroup_edit";
	}
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatgroupSave", method = RequestMethod.POST)
	public ModelAndView complatgroupSave(String iid,ComplatGroup complatGroup,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String name = request.getParameter("name");
			String areacode = request.getParameter("groupname2");
			String suffix = request.getParameter("suffix");
			complatGroup.setAreacode(areacode); //设置区域编码
			if(StringHelper.isNotBlack(iid)){
				//编辑状态改变操作状态位（opersign）和修改时间（modifytime）
				complatGroup.setOpersign(2);
				complatGroup.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
				//判断修改过后的机构名称是否重复
				if (complatGroupService.queryNameIsUsed(name, complatGroup.getPid()) && !complatGroup.getName().equals(name)){
					returnMsg("error", "保存失败,机构名称重复", request);
				}else{
					complatGroup = complatGroupService.save(complatGroup);
					returnMsg("success", "保存成功", request);
				}
				synchronization(complatGroup, 2); //编辑同步
			}else{
				String pid = request.getParameter("groupid");
			    Integer pId = Integer.valueOf(pid);
			    //判断机构是否重复
			    if (complatGroupService.queryNameIsUsed(name, pId)){
			    	returnMsg("error", "保存失败,机构名称重复", request);
			    }else{//如果不重复
			    	//自动生成机构编码
		        	List<ComplatGroup> group = complatGroupService.findByPid(Integer.valueOf(pid));
		        	String codeId = "";
		        	if(null != group && group.size() > 0){
		        		codeId = group.get(group.size()-1).getCodeid();
		        	}else{
		        		codeId = complatGroupService.findByIid(pId).getCodeid() + "000";
		        	}
		        	int num = Integer.valueOf(codeId.substring(codeId.length() - 4, codeId.length())).intValue() + 1;
		            codeId = codeId.substring(0, codeId.length() - 4) + String.valueOf(num);
		        	complatGroup.setCodeid(codeId); //存入机构编码
		        	complatGroup.setPid(pId);  //保存pid
		        	//新增时将机构名汉字转换成首字母大写保存到pinyin字段中
					String daPinYin = getPinYinHeadChar(complatGroup.getName());
					complatGroup.setPinyin(daPinYin);
					//新增状态改变操作状态位（opersign）和创建时间（modifytime）
					complatGroup.setOpersign(1);
					complatGroup.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
					//自动处理拼接机构后缀
					suffix = suffix + "." + complatGroupService.findByIid(pId).getSuffix();
					complatGroup.setSuffix(suffix);//保存机构后缀
		        	complatGroup = complatGroupService.save(complatGroup);
		        	returnMsg("success", "保存成功", request);
			    }
			    synchronization(complatGroup, 1);//新增同步
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		}
		if(StringUtils.isNotBlank(iid)){
			return  new ModelAndView("redirect:/uids/groupOrgTree?jumpId="+String.valueOf(complatGroup.getPid()));
		}else{
			return  new ModelAndView("redirect:/uids/groupOrgTree?jumpId="+request.getParameter("groupid"));
		}
	}
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatgroupDelete", method = RequestMethod.GET)
	public ModelAndView complatgroupDelete(String iid,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		Integer pId = complatGroupService.findByIid(Integer.valueOf(iid)).getPid();
		try {
			String[] para=iid.split(",");
			ComplatGroup complatGroup = null;
			boolean flag=false;
			for(int i=0;i<para.length;i++){
				String mId = para[i].trim();
				complatGroup = complatGroupService.findByIid(Integer.valueOf(mId));
				complatGroup.setOpersign(3);//将操作状态位改为3
				complatGroup.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));//保存删除时间在同步时使用
				complatGroup = complatGroupService.save(complatGroup);
				//检测当前要删除的机构是否有下级机构，有则无法删除
				List<ComplatGroup> list=new ArrayList<ComplatGroup>();
				list=complatGroupService.findByPid(Integer.valueOf(mId));
				if(list.size()>0){
					flag=false;
					break;
				}else{
					synchronization(complatGroup, 3);//删除同步
					complatGroupService.delete(complatGroup);
					flag=true;
				}				
			}
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,该菜单下还有下级机构，请先删除下级机构",request);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/uids/groupOrgTree?jumpId="+String.valueOf(pId));
		}
		
	}
	
	/**
	 * 将机构名首字母大写返回
	 * @author LinCX
	 * @param str
	 * @return convert
	 */
    public static String getPinYinHeadChar(String str) {  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);
            } else {  
                convert += word;  
            }  
        }  
        return convert.toUpperCase();  
    }  
    
    /**
	 * 数据导入
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @RequestParam(value="excelFile")MultipartFile multipartFile,
	 */
	@RequestMapping(value = "/complatgroupImport", method = RequestMethod.POST)
	public void complatgroupImport(@RequestParam("files")MultipartFile multipartFile,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {				      
		String fileName = multipartFile.getOriginalFilename();	
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("0", "name");
	    fieldMap.put("1", "strNodeType");
	    fieldMap.put("2", "suffix");
	    fieldMap.put("3", "groupallname");
	    fieldMap.put("4", "orgcode");
	    fieldMap.put("5", "strAreaType");
	    fieldMap.put("6", "areacode");
	    fieldMap.put("7", "strIsCombine");
	    fieldMap.put("8", "parentName");
	    fieldMap.put("9", "parentCode");
	    fieldMap.put("10", "spec");
	    List<ComplatGroup> group = ExcelUtil.readXls(fileName, multipartFile.getInputStream(), ComplatGroup.class, fieldMap);
	    Collections.reverse(group);//倒序排列list，以便导入后数据顺序跟excel表格里的顺序一致
	    
		try {
			if(importCheck(group, model, request, response)){
				for (ComplatGroup complatGroup : group) {
					if (complatGroup.getStrNodeType().equals("区域")) {
						complatGroup.setNodetype(Integer.valueOf(1));
					} else if (complatGroup.getStrNodeType().equals("单位")) {
						complatGroup.setNodetype(Integer.valueOf(2));
					} else if (complatGroup.getStrNodeType().equals("部门或处室")) {
						complatGroup.setNodetype(Integer.valueOf(3));
					}
					if (complatGroup.getStrAreaType().equals("省级")) {
						complatGroup.setAreatype(Integer.valueOf(1));
					} else if ((complatGroup.getStrAreaType().equals("市（州）级"))|| (complatGroup.getStrAreaType().equals("市(州)级"))) {
						complatGroup.setAreatype(Integer.valueOf(2));
					} else if (complatGroup.getStrAreaType().equals("区县")) {
						complatGroup.setAreatype(Integer.valueOf(3));
					} else if (complatGroup.getStrAreaType().equals("乡镇街道")) {
						complatGroup.setAreatype(Integer.valueOf(4));
					} else if (complatGroup.getStrAreaType().equals("其他")) {
						complatGroup.setAreatype(Integer.valueOf(5));
					}
					if (complatGroup.getStrIsCombine().equals("否")) {
						complatGroup.setIscombine(Integer.valueOf(0));
					} else if (complatGroup.getStrIsCombine().equals("是")) {
						complatGroup.setIscombine(Integer.valueOf(1));
					}
					//自动生成机构名称首字母大写
					String daPinYin = getPinYinHeadChar(complatGroup.getName());
					complatGroup.setPinyin(daPinYin);
					complatGroup.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
					complatGroup.setOpersign(Integer.valueOf(1));
					complatGroup.setSynState(Integer.valueOf(2));
					//自动生成机构编码
					String parentCode = complatGroup.getParentCode();
					Integer pId = complatGroupService.findByCodeid(parentCode).getIid();
					String codeId = complatGroupService.findByIid(pId).getCodeid() + "001";
					while (codeId.compareTo(codeId + "001") < 0) {
						if (complatGroupService.findByCodeid(codeId) == null) {
							break;
						}
						int num = Integer.valueOf(codeId.substring(codeId.length()-4,codeId.length())).intValue() + 1;
						codeId = codeId.substring(0,codeId.length() - 4) + String.valueOf(num);
					}
					complatGroup.setCodeid(codeId);
					complatGroup.setPid(pId);
					//自动进行机构后缀拼接
					String suffix = complatGroup.getSuffix() + "." + complatGroupService.findByIid(pId).getSuffix();
                    complatGroup.setSuffix(suffix);
					complatGroup = complatGroupService.save(complatGroup);
					synchronization(complatGroup, 1);//新增同步
				}
				returnMsg("success","导入成功",request);
			}	
		} catch (Exception e) {
		e.printStackTrace();
		returnMsg("error", "导入失败",request);
		}
	}
	
	/**
	 * 导入时数据校验
	 * @param group
	 * @param model
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	public boolean importCheck(List<ComplatGroup> group,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		int row = 1;
		String allWarn = "";
		String oneWarn = "";
		boolean check = true;
		//机构名称正则式校验
		String zhengZeShi1 = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		//机构后缀正则式校验
		String zhengZeShi2 = "^[^\u4e00-\u9fa5]{0,}$";
		//组织机构代码正则式校验
		String zhengZeShi3 = "^[a-zA-Z0-9]{9}$";
		//区域编码正则式校验
		String zhengZeShi4 = "^[0-9]{0,}$";
		//进行在权限内的导入校验
		SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
		String deptId = sysUserSession.getDeptId();// 获取部门id
		String deptParentCode = complatGroupService.findByIid(Integer.valueOf(deptId)).getCodeid();
		for (ComplatGroup complatGroup : group) {
			String parentCode = complatGroup.getParentCode();//上级机构编码
			String name = complatGroup.getName();//机构名称
			Integer pId = 0; //初始化上级机构id
			if(StringUtils.isNotBlank(parentCode)){
				pId = complatGroupService.findByCodeid(parentCode).getIid();//上级机构id
			}
			//校验机构名称，机构后缀，和上级机构编码是否为空
			if(complatGroup.getName().isEmpty() || complatGroup.getSuffix().isEmpty() || complatGroup.getParentCode().isEmpty()){
				oneWarn = oneWarn + "机构名称，机构后缀和上级机构编码不能为空。";
			}
			//进行权限校验
			if((parentCode.length() < deptParentCode.length()) || !(parentCode.substring(0, deptParentCode.length()).equals(deptParentCode))){
				oneWarn = oneWarn + "不是权限内的导入。";
			}
			//重名校验
			if(complatGroupService.queryNameIsUsed(name, pId)){
				oneWarn = oneWarn + "机构名称重复。";
			}
			//每个导入字段正则式校验
			if(!Pattern.matches(zhengZeShi1, complatGroup.getName()) || complatGroup.getName().length() >= 100){
				oneWarn = oneWarn + "机构名称不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getStrNodeType()) && (!Pattern.matches(zhengZeShi1, complatGroup.getStrNodeType()) || complatGroup.getStrNodeType().length() >= 50)){
				oneWarn = oneWarn + "节点类型不符合规范。";
			}
			if(StringUtils.isEmpty(complatGroup.getSuffix()) || (!Pattern.matches(zhengZeShi2, complatGroup.getSuffix()) || complatGroup.getSuffix().length() >= 255)){
				oneWarn = oneWarn + "机构后缀不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getGroupallname()) && (!Pattern.matches(zhengZeShi1, complatGroup.getGroupallname()) || complatGroup.getGroupallname().length() >= 255)){
				oneWarn = oneWarn + "机构全名不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getOrgcode()) && !Pattern.matches(zhengZeShi3, complatGroup.getOrgcode())){
				oneWarn = oneWarn + "组织机构代码不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getStrAreaType()) && (!Pattern.matches(zhengZeShi1, complatGroup.getStrAreaType()) || complatGroup.getStrAreaType().length() >= 50)){
				oneWarn = oneWarn + "区域类型不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getAreacode()) && (!Pattern.matches(zhengZeShi4, complatGroup.getAreacode()) || complatGroup.getAreacode().length() != 12)){
				oneWarn = oneWarn + "区域编码不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getStrIsCombine()) && (!Pattern.matches(zhengZeShi1, complatGroup.getStrIsCombine()) || complatGroup.getStrIsCombine().length() >= 20)){
				oneWarn = oneWarn + "是否为合并机构不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getParentName()) && (!Pattern.matches(zhengZeShi1, complatGroup.getParentName()) || complatGroup.getParentName().length() >= 100)){
				oneWarn = oneWarn + "上级机构名称不符合规范。";
			}
			if(!Pattern.matches(zhengZeShi4, complatGroup.getParentCode()) || complatGroup.getParentCode().length() >= 255){
				oneWarn = oneWarn + "上级机构编码不符合规范。";
			}
			if(StringUtils.isNotBlank(complatGroup.getSpec()) && complatGroup.getSpec().length() >= 255){
				oneWarn = oneWarn + "机构后缀不符合规范。";
			}
			if(StringUtils.isNotBlank(oneWarn)){
				oneWarn = "第" + row + "行数据导入失败：" + oneWarn + "\n";
				allWarn = allWarn + oneWarn;
				oneWarn = "";
			}
			row++;
		}
		if(StringUtils.isNotBlank(allWarn)){
			check = false;
			strWarn = allWarn;
		}
		return check;
	}
	
	/**
	 * 将导入失败提示语传入前台
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/importWarn", method = RequestMethod.POST)
	public void importWarn(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(StringUtils.isNotBlank(strWarn)){
				response.getWriter().write(strWarn);
				strWarn = "";
			}else{
				response.getWriter().write("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.getWriter().write(-1);
		}
	}
	
    /**
     * excel文件导出
     */
	@RequestMapping(value = "/complatgroupExport", method = RequestMethod.GET)
	public void complatgroupExport(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try{
			String sId = request.getParameter("sId");
			String[] para = sId.split(",");		
			String fileName = "groupdata";
			Map<String,Object> map = new HashMap<String,Object>(); 
			List headList = new ArrayList();//表头数据  
	        headList.add("机构名称");
	        headList.add("节点类型");
	        headList.add("机构编码");
	        headList.add("机构后缀");
	        headList.add("机构全名");
	        headList.add("组织机构代码");
	        headList.add("区域类型");
	        headList.add("区域编码");
	        headList.add("上级机构名称");
	        headList.add("上级机构编码");
	        headList.add("机构描述");
			Workbook wb = new XSSFWorkbook();  // 导出 Excel为2007 工作簿对象  
			List dataList = new ArrayList();
			for(int i=0;i<para.length;i++){
				String mId = para[i].trim();
				ComplatGroup complatGroup = complatGroupService.findByIid(Integer.valueOf(mId));
				String nodeType = null;
				String areaType = null;
				if(complatGroup.getNodetype() == null){
					nodeType = "";
				}else if(complatGroup.getNodetype() == 1){
					nodeType = "区域";
				}else if(complatGroup.getNodetype() == 2){
					nodeType = "单位";
				}else if(complatGroup.getNodetype() == 3){
					nodeType = "部门或处室";
				}else{
					nodeType = "";
				}
				if(complatGroup.getAreatype() == null){
					areaType = "";
				}else if(complatGroup.getAreatype() == 1){
					areaType = "省级";
				}else if(complatGroup.getAreatype() == 2){
					areaType = "市（州）级";
				}else if(complatGroup.getAreatype() == 3){
					areaType = "区县";
				}else if(complatGroup.getAreatype() == 4){
					areaType = "乡镇街道";
				}else if(complatGroup.getAreatype() == 5){
					areaType = "其他";
				}else{
					areaType = "";
				}
				TreeMap<String,Object> treeMap = new TreeMap<String, Object>();
				treeMap.put("1", complatGroup.getName());
				treeMap.put("2", nodeType);
				treeMap.put("3", complatGroup.getCodeid());
				treeMap.put("4", complatGroup.getSuffix());	
				treeMap.put("5", complatGroup.getGroupallname());
				treeMap.put("6", complatGroup.getOrgcode());
				treeMap.put("7", areaType);
				treeMap.put("8", complatGroup.getAreacode());
				if(complatGroup.getPid() != null){
					treeMap.put("9", complatGroupService.findByIid(complatGroup.getPid()).getName());
					treeMap.put("91", complatGroupService.findByIid(complatGroup.getPid()).getCodeid());
				}else{
					treeMap.put("9", "");
					treeMap.put("91", "");
				}
				treeMap.put("92", complatGroup.getSpec());			
				dataList.add(treeMap);
			}
			map.put(ExcelUtil.HEADERINFO, headList);  
	        map.put(ExcelUtil.DATAINFON, dataList); 
	        ExcelUtil.writeExcel(map, wb,response,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "导出失败",request);			
		} 
	}
	
	/**
     * 导入弹出框
     */
    @SuppressWarnings("finally")
    @RequestMapping(value = "/showImport", method = RequestMethod.GET)
    public String showInport(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String str = "";
        try {
            str = "users/complat/complatgroup_import";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
		return str;
    }

    /**
     * 加载机构区域编码树
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCodeid", method = RequestMethod.POST)
    public void getCodeid(HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            String groupId = request.getParameter("groupId");
			SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			// 获取部门id
			String deptId = sysUserSession.getDeptId();
			//如果登陆id是非市县的部门id，则区域编码为它自身的区域编码
			if(!complatZoneService.checkToIid(Integer.valueOf(deptId))){
				String areaCode = complatGroupService.findByIid(Integer.valueOf(deptId)).getAreacode();
				deptId = String.valueOf(complatZoneService.findByCodeId(areaCode).getIid());
			}
            List<ComplatZone> list = new ArrayList<ComplatZone>();
            if (!"0".equals(groupId) && StringUtils.isNotBlank(groupId)) {
                list = complatZoneService.findByPid(Integer.parseInt(groupId));
            } else {
                list=complatZoneService.findAllByIid(Integer.parseInt(deptId));
            }

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (ComplatZone c : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", c.getIid() + "");
                map.put("name", c.getName());
                map.put("title", c.getName());
                map.put("codeid", c.getCodeId());
                map.put("icon", null);
                map.put("target", "page");
                map.put("url", null);
                map.put("isParent", true);
                map.put("isDisabled", false);
                map.put("open", true);
                map.put("nocheck", false);
                map.put("click", null);
                map.put("checked", false);
                map.put("iconClose", null);
                map.put("iconOpen", null);
                map.put("iconSkin", null);
                map.put("pId", c.getPid());
                map.put("chkDisabled", false);
                map.put("halfCheck", false);
                map.put("dynamic", null);
                map.put("moduleId", null);
                map.put("functionId", null);
                map.put("allowedAdmin", null);
                map.put("allowedGroup", null);
                result.add(map);
            }
            String groups = JSONUtil.writeListMapJSONMap(result);
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(groups);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    /**
     * @discription 获取list列表机构树信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/orgTree", method = RequestMethod.POST)
    public void zoneTree(HttpServletRequest request, HttpServletResponse response, String orgId, String pId) {
		List<ComplatGroup> list = new ArrayList<ComplatGroup>();
        try {
            SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession()
                    .getAttribute("sysUserSession");
            // 获取部门id
            String deptId = sysUserSession.getDeptId();
			if (StringUtils.isEmpty(pId)){
				if (StringUtils.isEmpty(orgId)){
					list = complatGroupService.findAllDept(deptId);
				} else {
					list = complatGroupService.findAllDept(orgId);
				}
			} else {
				list = complatGroupService.findByPid(Integer.parseInt(pId));
			}
            List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
            if (list != null && !list.isEmpty()) {
                for (ComplatGroup complatGroup : list) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", String.valueOf(complatGroup.getIid()));
                    map.put("pId", String.valueOf(complatGroup.getPid()));
                    map.put("name", complatGroup.getName());
                    map.put("title", complatGroup.getName());
                    map.put("tld", String.valueOf(complatGroup.getIid()));
                    map.put("viewtype", "1");
                    map.put("regiontype", "1");
					map.put("isParent", true);
					if (StringUtils.isEmpty(pId)){
						map.put("open", true);
					} else {
						map.put("open", false);
					}
                    treeList.add(map);
                }
            }
            JSONArray array = JSONArray.fromObject(treeList);
            PrintWriter out = response.getWriter();
            String json = array.toString();
            out.write(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
	 * 加载编辑新增页面上级机构树
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getGroup", method = RequestMethod.POST)
	public void getGroup(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String groupId = request.getParameter("groupId");
			SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession()
            .getAttribute("sysUserSession");
			// 获取部门id
			String deptId = sysUserSession.getDeptId();

			List<ComplatGroup> list = new ArrayList<ComplatGroup>();

			if (!"0".equals(groupId) && StringUtils.isNotBlank(groupId)) {
				list = complatGroupService.findByPid(Integer.parseInt(groupId));
			} else {
                list = complatGroupService.findAllDept(deptId);
			}

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for (ComplatGroup c : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", c.getIid() + "");
				map.put("name", c.getName());
				map.put("title", c.getName());
				map.put("icon", null);
				map.put("target", "page");
				map.put("url", null);
				map.put("isParent", true);
				map.put("isDisabled", false);
				map.put("open", true);
				map.put("nocheck", false);
				map.put("click", null);
				map.put("checked", false);
				map.put("iconClose", null);
				map.put("iconOpen", null);
				map.put("iconSkin", null);
				map.put("pId", c.getPid());
				map.put("chkDisabled", false);
				map.put("halfCheck", false);
				map.put("dynamic", null);
				map.put("moduleId", null);
				map.put("functionId", null);
				map.put("allowedAdmin", null);
				map.put("allowedGroup", null);
				result.add(map);
			}
			String groups = JSONUtil.writeListMapJSONMap(result);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(groups);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}


}

