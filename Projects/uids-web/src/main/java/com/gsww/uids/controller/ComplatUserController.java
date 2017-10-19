package com.gsww.uids.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;
import org.springframework.web.multipart.MultipartFile;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.ExcelUtil;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.ComplatZone;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisFields;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.entity.JisSynEntity;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.entity.JisUserdetail;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatRoleService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.ComplatZoneService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisFieldsService;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewService;
import com.gsww.uids.service.JisUserdetailService;
import com.hanweb.common.util.Md5Util;


/**
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : jup-web
 * </p>
 * <p>
 * 创建时间 : 2017-09-07 下午14:18:12
 * </p>
 * <p>
 * 类描述 : 政府用户模块控制器
 * </p>
 * 
 * @version 3.0.0
 * @author <a href=" ">shenxh</a>
 */

@Controller
@RequestMapping(value = "/complat")
public class ComplatUserController extends BaseController {

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(ComplatUserController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ComplatUserService complatUserService;

	@Autowired
	private ComplatGroupService complatGroupService;

	@Autowired
	private ComplatRoleService complatRoleService;

	@Autowired
	private JisUserdetailService jisUserdetailService;

	@Autowired
	private JisFieldsService jisFieldsService;

	@Autowired
	private JisLogService jisLogService;
	
	@Autowired
	private JisSysviewService jisSysviewService;
	
	@Autowired
	private JisSysviewDetailService jisSysviewDetailService;
	
	@Autowired
	private JisApplicationService jisApplicationService;
	
	@Autowired
	private ComplatZoneService complatZoneService;
	
	@Autowired
	private  JisSettings jisSetting;
	
	/**
	 * 获取政府用户左侧机构树
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/groupOrgTree", method = RequestMethod.GET)
	public String complatList(
			Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			// 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) hrequest.getSession().getAttribute("sysUserSession");
			String deptId = sysUserSession.getDeptId();

			//点击完查询时组织机构名称回显
			String groupName = request.getParameter("groupname");
			model.addAttribute("groupName", groupName);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("机构树打开失败：" + ex.getMessage());
			returnMsg("error", "机构树打开失败", (HttpServletRequest) request);
			return "redirect:/complat/complatList";
		}
		return "users/complat/org_tree";
	}
	
	
	
	/**
	 * 获取政府用户列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/complatList", method = RequestMethod.GET)
	public String complatList(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createtime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			String orgId,
			Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			if (StringUtils.isNotBlank(request.getParameter("orderField"))) {
				orderField = (String) request.getParameter("orderField");
			}
			if (StringUtils.isNotBlank(request.getParameter("orderSort"))) {
				orderSort = (String) request.getParameter("orderSort");
			}

			// 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) hrequest.getSession().getAttribute("sysUserSession");
			String deptId = sysUserSession.getDeptId();
			// 初始化分页数据
			PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField,
					orderSort);
			PageRequest pageRequest = super.buildPageRequest(hrequest,
					pageUtils, ComplatUser.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			if(StringUtils.isNotBlank(orgId)){
				searchParams.put("EQ_groupid", orgId);
				model.addAttribute("orgId", orgId);
			}else{
				searchParams.put("EQ_groupid", deptId);
				model.addAttribute("orgId", deptId);
			}
			 
			Specification<ComplatUser> spec = super.toNewSpecification(
					searchParams, ComplatUser.class);

			// map放入
			List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
			Map<Integer, Object> groupMap = new HashMap<Integer, Object>();
			groupList = complatGroupService.getComplatGroupList();
			for (Map<String, Object> group : groupList) {
				groupMap.put((Integer) group.get("iid"), group.get("name"));
			}

			// 分页
			Page<ComplatUser> pageInfo = complatUserService.getComplatUserPage(
					spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("groupMap", groupMap);

			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets
					.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
			
			//点击完查询时组织机构名称回显
			String groupName = request.getParameter("groupname");
			model.addAttribute("groupName", groupName);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/complatList";
		}
		return "users/complat/account_list";
	}

	/**
	 * 转到政府用户新增或编辑页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatUserEdit", method = RequestMethod.GET)
	public String complatUserEdit(String iid, Model model,String orgId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {			
			ComplatUser complatUser = null;
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer.parseInt(iid));
				Date createTime = complatUser.getCreatetime();
				//对密码进行解密
				String pwd = Md5Util.md5decode(complatUser.getPwd());
				model.addAttribute("pwd",pwd);
				if (createTime != null) {
					String time = sdf.format(createTime);
					model.addAttribute("time", time);
					
				}
				// map放入
				List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
				Map<Integer, Object> groupMap = new HashMap<Integer, Object>();
				groupList = complatGroupService.getComplatGroupList();
				for (Map<String, Object> group : groupList) {
					groupMap.put((Integer) group.get("iid"), group.get("name"));
				}
				model.addAttribute("groupMap", groupMap);
				//查询扩展属性和身份证号		
				JisUserdetail userDetail = new JisUserdetail();
				Integer userId = complatUser.getIid();			
				userDetail=jisUserdetailService.findByUserid(userId);
				model.addAttribute("userDetail", userDetail);
			} else {
				complatUser = new ComplatUser();
				
			}
			String pwdLevel = jisSetting.getPpdLevel();
			model.addAttribute("complatUser", complatUser);
			model.addAttribute("pwdLevel", pwdLevel);
			this.extendsAttr(model, request, response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "users/complat/account_edit";
	}

	
	
	
	
	/**
	 * 保存政府用户信息
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatSave", method = RequestMethod.POST)
	public ModelAndView complatSave(ComplatUser complatUser, String level,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		SysUserSession sysUserSession =(SysUserSession)request.getSession().getAttribute("sysUserSession");
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Integer userId = null;
            String pwd;			
			if (complatUser != null) {	
				String iid = String.valueOf(complatUser.getIid());
				//if (StringUtils.isNotBlank(iid)) {//不为空	
				if (StringHelper.isNotBlack(iid)) {	
					pwd=complatUser.getPwd();
					//对登录全名做处理
					int groupId=complatUser.getGroupid();
					ComplatGroup complatGroup = complatGroupService.findByIid(groupId);
					String suffix = complatGroup.getSuffix();
					String loginallname=complatUser.getLoginname()+ "." + suffix;	
					complatUser.setLoginallname(loginallname);
					//对密码进行加密			
					String p = Md5Util.md5encode(pwd);
					complatUser.setPwd(p);
					//注册时间
					complatUser.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));// 创建时间
					complatUser.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));//修改时间
					//转换保存创建时间
					userId = complatUser.getIid();
					Integer power = complatUser.getEnable();
					complatUser.setEnable(power); // 是否禁用	
					complatUser.setOpersign(2);//更新操作状态
					synchronization(complatUser, 2);//修改同步
					complatUserService.save(complatUser);						
					//身份证号处理 JisUserdetail
					String cardId = request.getParameter("cardid");	
					//扩展属性
					Map<String,String> userMap = this.saveExendsAttr(userId, request);
					JisUserdetail jisUserdetail = jisUserdetailService.findByUserid(userId);
					if(jisUserdetail.getIid() != null){
						//对身份证号和用户扩展属性update
						jisUserdetailService.update(jisUserdetail.getIid(),cardId,userMap);			
					}						
					returnMsg("success", "编辑用户成功", request);
					String desc = sysUserSession.getUserName() + "修改政府用户:" + complatUser.getName(); 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,2,2);																				
				}else {
					pwd=complatUser.getPwd();
						//对登录全名做处理
						String groupid=request.getParameter("groupid");
						ComplatGroup complatGroup = complatGroupService.findByIid(Integer.parseInt(groupid));
						String suffix = complatGroup.getSuffix();
						String loginallname=complatUser.getLoginname()+ "." + suffix;	
						complatUser.setLoginallname(loginallname);
						
						complatUser.setOpersign(1);//1:新增2:修改3:删除	
						complatUser.setSynState(0);
						complatUser.setEnable(0); // 是否禁用
						complatUser.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));// 创建时间
						complatUser.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));//修改时间
						//complatUser.setAccesstime(d);//访问时间
						//对密码进行加密										
						String p = Md5Util.md5encode(pwd);
						complatUser.setPwd(p);
						synchronization(complatUser, 1);//新增同步
						complatUserService.save(complatUser);	
						//身份证号处理 JisUserdetail
						userId = complatUser.getIid();
						String cardId = request.getParameter("cardid");
						JisUserdetail jisUser = new JisUserdetail();
						jisUser.setCardid(cardId);
						jisUser.setIid(userId);
						jisUser.setUserid(userId);
						jisUserdetailService.save(jisUser);					
						//扩展属性
						Map<String,String> userMap = this.saveExendsAttr(userId, request);
						JisUserdetail jisUserdetail = jisUserdetailService.findByUserid(userId);
						if(jisUserdetail.getIid() != null){
							//对身份证号和用户扩展属性update
							jisUserdetailService.update(jisUserdetail.getIid(),cardId,userMap);			
						}						
						returnMsg("success", "新增用户成功", request);	
						String desc = sysUserSession.getUserName() + "新增政府用户:" + complatUser.getName(); 				
						jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,2,1);
				}	
			} 
		} catch (Exception e) {
			returnMsg("error", "保存用户失败", request);	
		}finally {
			return new ModelAndView("redirect:/complat/complatList");
		}

	}
	
	
	
	/**
	 * 批量删除用户信息
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatUserDelete", method = RequestMethod.GET)
	public ModelAndView complatUserDelete(String iid,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		try {
			String[] para = iid.split(",");
			ComplatUser complatUser = null;
			for (int i = 0; i < para.length; i++) {
				//Integer corId = Integer.parseInt(para[i].trim());
				Integer userId = Integer.parseInt(para[i].trim());
				complatUser = complatUserService.findByKey(userId);
				complatUser.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));//修改时间
				if (complatUser != null) {
					synchronization(complatUser, 3);//删除同步
					complatUserService.delete(complatUser);					
					returnMsg("success", "删除成功", request);
				}

				String desc=session.getUserName()+"删除了"+complatUser.getName();
	            jisLogService.save(session.getUserName(),session.getUserIp(), desc, 2, 3);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败", request);
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}

	}

	/**
	 * 将机构名首字母大写返回
	 * 
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
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/complatImport", method = RequestMethod.POST)
	public void complatImport(
			@RequestParam("files") MultipartFile multipartFile,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		Map resMap = new HashMap();
		String fileName = multipartFile.getOriginalFilename();
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("0", "name");
		fieldMap.put("1", "age");
		fieldMap.put("2", "sex");
		fieldMap.put("3", "groupName");
		fieldMap.put("4", "groupid");
		fieldMap.put("5", "headship");
		fieldMap.put("6", "phone");
		fieldMap.put("7", "mobile");
		fieldMap.put("8", "address");
		fieldMap.put("9", "post");
		fieldMap.put("10", "ip");
		fieldMap.put("11", "fax");
		fieldMap.put("12", "email");
		fieldMap.put("13", "qq");
		fieldMap.put("14", "loginname");
		fieldMap.put("15", "pwd");
		fieldMap.put("16", "pwdquestion");
		fieldMap.put("17", "pwdanswer");
		fieldMap.put("18", "pinyin");
		fieldMap.put("19", "cardid");		
		List<ComplatUser> users = ExcelUtil.readXls(fileName,multipartFile.getInputStream(), ComplatUser.class, fieldMap);		
		// 判断是哪行导入失败
		int row = 1;
	    boolean flag = true;
	    String strRow = "";
		try {
			for (ComplatUser complatUser : users) {			   
				//List<ComplatUser> list = complatUserService.findByUserAllName(complatUser.getLoginallname());
				List<Map<String,Object>> userList=complatUserService.findByLoginnameAndgroupid(complatUser.getLoginname(),complatUser.getGroupid());
				if (userList.size() == 0) {					
					if (StringHelper.isNotBlack(complatUser.getGroupName())) { // 判断excel表格导入的数据是否规范
						// 新增时将机构名汉字转换成首字母大写保存到pinyin字段中
						String daPinYin = getPinYinHeadChar(complatUser.getName());
						complatUser.setPinyin(daPinYin);
						// 设置创建时间
						complatUser.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));// 创建时间
						complatUser.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));//修改时间
						// 设置状态值
	                    complatUser.setEnable(0);
						complatUser.setOpersign(1);
						complatUser.setSynState(2);
						//对登录全名进行唯一性判断
						String loginname = complatUser.getLoginname();
						int groupId=complatUser.getGroupid();
						ComplatGroup complatGroup = complatGroupService.findByIid(groupId);
						String suffix = complatGroup.getSuffix();
						String loginallname=loginname+ "." + suffix;	
						complatUser.setLoginallname(loginallname);						
//						//对机构名称做判断
//						if(complatGroup.equals(complatUser.getGroupName())){
//							flag = false;
//							strRow = "第<" + row + ">行数据，机构名称不正确！";
//							break;
//						}
						//对密码进行加密
						String pwd = complatUser.getPwd();
						String p = Md5Util.md5encode(pwd);
						complatUser.setPwd(p);						
						complatUserService.save(complatUser);
						//身份证号
						String cardId = complatUser.getCardid();
						Integer userId = complatUser.getIid();
						complatUser.setIid(userId);
						JisUserdetail jisUser = new JisUserdetail();
						jisUser.setCardid(cardId);
						jisUser.setIid(userId);
						jisUser.setUserid(userId);
						jisUserdetailService.save(jisUser);											
//						//扩展属性
//  						String Kzsx=complatUser.getKzsx();
//  					    String kzsx="";
//						String [] fields=Kzsx.split(",");
//						//List<Map<String,String>> listFields = new ArrayList<Map<String,String>>();
//						//List<String> listFields = new ArrayList<String>();
//						Map<String,String> map = new HashMap<String,String>();
//						for (int i = 0; i < fields.length; i++) {
//							kzsx=fields[i].trim();
//							String kzsx1=kzsx.substring(1);
//							
//							int length=kzsx1.length();
//							String kzsx2=kzsx1.substring(0,length-1);
//						    String [] arr = kzsx2.split(":");
//						    
//						    for(int j=0;j<arr.length;j++){	
//						    	String str1 = arr[0].trim();
//						    	String str2 = arr[1].trim();
//						    	if(j==0){
//						    		map.put(str1, "");
//						    	}else{
//						    		map.put(str1, str2);
//						    	}
//						    }									 			
//						}
//						JisUserdetail jisUserdetail = jisUserdetailService.findByUserid(userId);
//						if(jisUserdetail.getIid() != null){
//							//对身份证号和用户扩展属性update
//							jisUserdetailService.update(jisUserdetail.getIid(),cardId,map);			
//						}											
						synchronization(complatUser, 1);//新增同步					
						String desc=session.getUserName()+"导入了"+complatUser.getName();
			            jisLogService.save(session.getUserName(),session.getUserIp(), desc, 2, 5);
					} else {
						returnMsg("error","机构名称为空导入失败",request);
					}// else
					row++;// 导入行数加一
				}// if(list
			}// for
			
			
			if(flag){
				returnMsg("success","导入成功",request);			
			}else{
				returnMsg("error","导入失败",request);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "导入失败", request);
		}
	}
	
	
	

	/**
	 * 数据导出
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@RequestMapping(value = "/complatExport", method = RequestMethod.GET)
	public void complatExport(String iid,Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		String userIid = request.getParameter("iid");
		String[] complatUserIds = userIid.split(",");
		String fileName = "政府用户信息统计列表";
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> headList = new ArrayList<String>();// 表头数据
		headList.add("用户姓名");
		headList.add("年龄");
		headList.add("性别");
		headList.add("所属机构");
		headList.add("机构编码");
		headList.add("用户职务");
		headList.add("办公电话");
		headList.add("移动电话");
		headList.add("地址");
		headList.add("邮政编码");
		headList.add("IP地址");
		headList.add("传真");
		headList.add("E-mail");
		headList.add("QQ号");
		headList.add("登录名");
		headList.add("登录全名");
		headList.add("账号密码");
		headList.add("密码找回问题");
		headList.add("密码找回问题答案");
		headList.add("姓名首字母全拼");
		headList.add("身份证号");
		headList.add("是否启用");
		headList.add("创建日期");
//        //获取fieldname
//		List<Map<String,Object>> list=jisFieldsService.findFieldName();
//		int num=33;
//		for(int i=0;i<list.size();i++){
//			Map<String,Object> fieldsMap = list.get(i);
//			String field=null;
//			for(int j=0;j<fieldsMap.size();j++){
//				field = fieldsMap.get("fieldname").toString();			
//			}
//			for(int n=0;n<fieldsMap.size();n++){
//				headList.add(field);
//			}
//		}	
		Workbook wb = new XSSFWorkbook(); // 导出 Excel为2007 工作簿对象
		ComplatUser complatUser = null;
		List dataList = new ArrayList();
		for(int i=0;i<complatUserIds.length;i++){
			Integer userId = Integer.parseInt(complatUserIds[i].trim());
			complatUser = complatUserService.findByKey(userId);
			TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
			treeMap.put("10", complatUser.getName());// 用户姓名
			treeMap.put("11", complatUser.getAge());// 年龄
			int sex = complatUser.getSex();// 性别
			if (sex == 1) {
				treeMap.put("12", "男");
			} else {
				treeMap.put("12", "女");
			}
			int groupId = complatUser.getGroupid();
			ComplatGroup complatGroup = complatGroupService.findByIid(groupId);
			if(complatGroup==null){
				treeMap.put("13", "");// 机构名称
			}else{
				treeMap.put("13", complatGroup.getName());// 机构名称
			}
			treeMap.put("14", complatUser.getGroupid());// 机构ID
			treeMap.put("15", complatUser.getHeadship());// 用户职务
			treeMap.put("16", complatUser.getPhone());// 办公电话
			treeMap.put("17", complatUser.getMobile());// 移动电话
			treeMap.put("18", complatUser.getAddress());// 地址
			treeMap.put("19", complatUser.getPost());// 邮政编码
			treeMap.put("20", complatUser.getIp());// IP地址
			treeMap.put("21", complatUser.getFax());// 传真
			treeMap.put("22", complatUser.getEmail());// E-mail
			treeMap.put("23", complatUser.getQq());// QQ号
			treeMap.put("24", complatUser.getLoginname());// 登录名
			treeMap.put("25", complatUser.getLoginallname());// 登录全名
			treeMap.put("26", complatUser.getPwd());// 账号密码
			treeMap.put("27", complatUser.getPwdquestion());// 密码找回问题
			treeMap.put("28", complatUser.getPwdanswer());// 密码找回问题答案
			treeMap.put("29", complatUser.getPinyin());// 姓名首字母全拼
			JisUserdetail userDetail = jisUserdetailService.findByUserid(userId);	//获取当前用户的身份证号
			String idCode;
			if(userDetail==null){
				idCode="";
			}else{
				idCode=userDetail.getCardid();
			}
			treeMap.put("30", idCode);// 
			int enable = complatUser.getEnable();
			if (enable == 0) {
				treeMap.put("31", "未启用");
			} else {
				treeMap.put("31", "已启用");
			} // 是否启用
			treeMap.put("32", complatUser.getCreatetime());// 创建日期
			//获取扩展属性的值		
			/*Integer id=Integer.parseInt(iid);
			List<Map<String,Object>> list=jisFieldsService.findFieldName();
			int num=33;
			for(int i=0;i<list.size();i++){
				Map<String,Object> fieldsMap = list.get(i);
				String title = fieldsMap.get("fieldname").toString();
				fieldsMap.put(title, "null");
				fieldsMap.remove("fieldname");		
				String field=null;
				for(int j=0;j<fieldsMap.size();j++){
					field = fieldsMap.get("fieldname").toString();			
				}
				System.out.println("field======="+field);
				
				for(int n=0;n<fieldsMap.size();n++){
					String number=num+"";
					treeMap.put(number, field);//扩展属性
					num++;
				}
				
			}*/			
			dataList.add(treeMap);
		}
		String desc=session.getUserName()+"导出了"+complatUser.getName();
        jisLogService.save(session.getUserName(),session.getUserIp(), desc, 2, 4);
		map.put(ExcelUtil.HEADERINFO, headList);
		map.put(ExcelUtil.DATAINFON, dataList);
		ExcelUtil.writeExcel(map, wb, response, fileName);
		//return  new ModelAndView("redirect:/complat/complatList");

	}

	
	
	
	/**
	 * 用户设置模块,点击用户设置按钮，页面跳转
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */
	@RequestMapping(value = "/userSetUpEdit", method = RequestMethod.GET)
	public String userSetUpEdit(Model model, HttpServletRequest request,
			HttpServletResponse response,String isFront) throws Exception {
		try {
			// 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) request
					.getSession().getAttribute("sysUserSession");
			String userSid = sysUserSession.getAccountId();
			if (StringHelper.isNotBlack(sysUserSession.getAccountId())) {

				// 查询用户信息
				ComplatUser complatUserEdit = complatUserService.findByKey(Integer
						.parseInt(userSid));
				String pwd = Md5Util.md5decode(complatUserEdit.getPwd());
				model.addAttribute("pwd",pwd);
				model.addAttribute("complatUser",complatUserEdit);
				
				// 查询用户身份证号
				JisUserdetail userDetail = jisUserdetailService
						.findByUserid(Integer.parseInt(userSid));
				model.addAttribute("userDetail", userDetail);
				// 根据用户ID查询所属机构
				ComplatGroup complatGroup = complatGroupService
						.findByIid(complatUserEdit.getGroupid());
				model.addAttribute("complatGroup", complatGroup);

				// 根据用户ID从ComplatRolerelation获取对应的角色ID，再根据角色ID从ComplatRole中获取对应的角色
				List<JisRoleobject> roleObjectList = complatRoleService.findByUserId(Integer.parseInt(userSid),complatUserEdit.getGroupid());
				List<ComplatRole> roleList = new ArrayList<ComplatRole>();
				for (int i = 0; i < roleObjectList.size(); i++) {
					Integer roleId = roleObjectList.get(i).getRoleid();
					ComplatRole complatRole = complatRoleService.findByKey(roleId);
					roleList.add(complatRole);
					model.addAttribute("roleList", roleList);
				}
			}
			this.extendsAttr(model, request, response);
			
			//跳转到前台页面
			String managerIcon = "display:none";
			if(checkHaveRight(sysUserSession.getAccountId(),sysUserSession.getRoleIds())){
				managerIcon="";
			}
			model.addAttribute("managerIcon", managerIcon);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if("1".equals(isFront)){
			return "users/sysview/frontIndex_user_setup";
		}else{
			return "users/sysview/user_setup";
		}
	}
		
		/**
		 * 判断权限
		 * @param iid
		 * @param roleIds
		 * @return
		 */
		private boolean checkHaveRight(String iid,String roleIds) {
			if(iid==null || iid=="" || roleIds==null || roleIds.split(",").length==0){
				return false;
			}
			List<ComplatRole> roles = new ArrayList<ComplatRole>();
			String[] roleId = roleIds.split(",");
			if(Integer.parseInt(iid)==1){
				return true;
			}
			for(int i=0;i<roleId.length;i++){
				try {
					String roleod = roleId[i];
					if(roleod!=null && roleod.trim()!=""){
						roles.add(complatRoleService.findByKey(Integer.parseInt(roleod)));
					}
					
				}catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			for(ComplatRole role : roles){
				if ((role != null) && (role.getType() != null) && (role.getType().intValue() == 0)) {
			        return true;
			    }
				if ((role != null) && (role.getType() != null) && (role.getType().intValue() == 1)) {
			        return true;
			    }
				if ((role != null) && (role.getType() != null) && (role.getType().intValue() == 2)) {
			        return true;
			    }
			}
			return false;
		}

	
	
	
	/**
	 * 保存用户设置
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */

		@SuppressWarnings("finally")
		@RequestMapping(value = "/userSetUpSave", method = RequestMethod.POST)
		public void userSetUpSave(ComplatUser complatUser,String level,HttpServletRequest request,HttpServletResponse response)  throws Exception {
			
			Map<String, Object> resMap = new HashMap<String, Object>();
			Integer intLevel = 0;
			if(StringHelper.isNotBlack(level)){
				intLevel = Integer.parseInt(level);
			}
			
			Integer intPwdLevel = 0;
			if(StringHelper.isNotBlack(jisSetting.getPpdLevel())){
				intPwdLevel = Integer.parseInt(jisSetting.getPpdLevel());
			}
			try {
				if(intLevel >= intPwdLevel){
					Integer userId = null;
					if(complatUser != null){
						userId = complatUser.getIid();
						String name = complatUser.getName();
						String pwd = Md5Util.md5encode(complatUser.getPwd());
						String headShip = complatUser.getHeadship();	
						String phone = complatUser.getPhone();//固定电话
						String mobile = complatUser.getMobile();//移动电话
						String fax = complatUser.getFax();
						String email = complatUser.getEmail();
						String qq = complatUser.getQq();
						String time = TimeHelper.getCurrentTime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date modifyTime = sdf.parse(time);
						complatUserService.updateUser(userId,name,headShip,phone,mobile,fax,email,qq,modifyTime,pwd);
						
						//身份证号处理 JisUserdetail
						String cardId = request.getParameter("cardid");
						//扩展属性
						Map<String,String> userMap = this.saveExendsAttr(userId, request);
						JisUserdetail jisUserdetail = jisUserdetailService.findByUserid(userId);
						if(jisUserdetail.getIid() != null){
							//对身份证号和用户扩展属性update
							jisUserdetailService.update(jisUserdetail.getIid(),cardId,userMap);			
						}
						
						//同步
						List<Map<String,Object>> syListMap = complatUserService.synchronizeData(userId);
						if(syListMap.size() > 0){
							for(int i=0;i<syListMap.size();i++){
								
								Random random = new Random();  
						        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数 
								JisSysview sysView = new JisSysview();
								Map<String,Object> syMap = syListMap.get(i);
								for(Map.Entry<String, Object> entry :syMap.entrySet()){
									String key = entry.getKey().toString();
									if(key.equals("userId")){
										sysView.setObjectid(entry.getValue().toString());
									}else if(key.equals("loginname")){
										sysView.setObjectname(entry.getValue().toString());
									}else if(key.equals("codeid")){
										sysView.setCodeid(entry.getValue().toString());
									}else{
										sysView.setAppid(Integer.parseInt(entry.getValue().toString()));
									}
								}
								sysView.setResult("T");//T-用户同步
								sysView.setSynctime(TimeHelper.getCurrentTime());
								sysView.setState("C");//C-验证
								sysView.setOptresult(1);//1-已同步
								sysView.setOperatetype("修改用户");
								sysView.setTimes(1);
								sysView.setTranscationId(TimeHelper.getCurrentCompactTime()+rannum);
								jisSysviewService.save(sysView);
								
								//同步详情表
								JisApplication app = jisApplicationService.findByKey(sysView.getAppid());
								JisSynEntity jisSynEntity = new JisSynEntity();
								jisSynEntity.setAppid(sysView.getAppid());
								jisSynEntity.setAppName(app.getName());
								jisSynEntity.setState("T");
								jisSynEntity.setGroupCode(sysView.getCodeid());
								ComplatGroup group = complatGroupService.findByCodeid(sysView.getCodeid());
								jisSynEntity.setGroupName(group.getName());
								jisSynEntity.setParCode(group.getParentCode());
								jisSynEntity.setParName(group.getParentName());
								jisSynEntity.setAllParCode("");
								jisSynEntity.setAllParName("");
								jisSynEntity.setLoginName(complatUser.getLoginname());
								jisSynEntity.setLoginPass(complatUser.getPwd());
								jisSynEntity.setUserName(complatUser.getName());
								jisSynEntity.setCardId(jisUserdetail.getCardid());
								jisSynEntity.setComptel("");//办公电话
								jisSynEntity.setCompfax(complatUser.getFax());
								jisSynEntity.setEmail(complatUser.getEmail());
								jisSynEntity.setQq(complatUser.getQq());
								jisSynEntity.setMsn(complatUser.getMsn());
								jisSynEntity.setMobile(complatUser.getMobile());
								jisSynEntity.setHometel(complatUser.getPhone());
								jisSynEntity.setHeadShip(complatUser.getHeadship());
								jisSynEntity.setNdlogin("");
								//实体转json，实现同步
								net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(jisSynEntity);
								PrintWriter out = response.getWriter();
								String json = object.toString();
								JisSysviewDetail sysViewDetail = new JisSysviewDetail();
								sysViewDetail.setTranscationId(sysView.getTranscationId());
								sysViewDetail.setSendmsg(json);
								jisSysviewDetailService.save(sysViewDetail);
							}
						}
						resMap.put("ret", "0");
						resMap.put("msg", "保存成功！");
						response.getWriter().write(JSONObject.toJSONString(resMap));
					}
				}else{
					if(intPwdLevel==0){
						resMap.put("msg", "密码强度至少为弱！");
					}else if(intPwdLevel==1){
						resMap.put("msg", "密码强度至少为中！");
					}else if(intPwdLevel==2){
						resMap.put("msg", "密码强度至少为强！");
					}
					resMap.put("ret", "2");
					response.getWriter().write(JSONObject.toJSONString(resMap));
				}
				
			} catch (Exception e) {
				resMap.put("ret", "1");
				resMap.put("msg", "保存失败！");
				response.getWriter().write(JSONObject.toJSONString(resMap));
			} 
			
		}

	
	
	
	
	/**
	 * 获取用户扩展属性
	 * 
	 * @author yaoxi
	 */
	private void extendsAttr(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<List<Map<String,Object>>> fieldsListMap = new ArrayList<List<Map<String,Object>>>();
		//判断是政府用户还是用户设置菜单，获取用户id。1-政府用户；2-用户设置
		String userMenu = request.getParameter("userMenu");
		Integer userId = null;
		if("2".equals(userMenu)){
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String usersId = sysUserSession.getAccountId();
			if(StringHelper.isNotBlack(usersId)){
				userId = Integer.parseInt(usersId);
			}
		}else{
			String iid = request.getParameter("iid");
			if(StringHelper.isNotBlack(iid)){
				userId = Integer.parseInt(iid);
			}
		}
		
		//1.查询类型 1-字符；2-枚举
		List<Map<String, Object>> listMap = null;
		List<JisFields> fieldsList = this.jisFieldsService.findAllJisFields();
		List<Integer> typeList = this.jisFieldsService.findFieldsType();
		for(int i=0;i<typeList.size();i++){
			Integer type = typeList.get(i);
			if(type == 1){
				listMap = this.jisFieldsService.findExtendAttr(fieldsList, userId,type);
				fieldsListMap.add(listMap);
				model.addAttribute("type",type);
			}else if(type == 2){
				listMap = this.jisFieldsService.findExtendAttr(fieldsList, userId,type);
				fieldsListMap.add(listMap);
				model.addAttribute("type",type);
			}
		}
		
		JSONArray array = JSONArray.fromObject(fieldsListMap);
		PrintWriter out = response.getWriter();
		String json = array.toString();
		out.write(json);
		System.out.println("json--"+json);
		model.addAttribute("fieldsListMap",json);
		
		//设置默认值
		if(userId != null){
			List<JisFields> fieldsTypeList= this.jisFieldsService.findByType(2);
			Map<String,Object> fieldsMap = jisFieldsService.findByUserIdAndType(fieldsTypeList,userId);
			JSONArray mapArray = JSONArray.fromObject(fieldsMap);
			PrintWriter outMap = response.getWriter();
			String jsonMap = mapArray.toString();
			outMap.write(jsonMap);
			model.addAttribute("jsonMap",jsonMap);
		}
		
		
	}
	
	
	/**
	 * 保存用户扩展属性
	 */
	private Map<String,String> saveExendsAttr(Integer userId,HttpServletRequest request){
		Map<String,String> userMap = new HashMap<String, String>();
		try {
			Integer type = 1;
			List<JisFields> fieldsList = jisFieldsService.findByType(type);
			for(int i=0;i<fieldsList.size();i++){
				 String fileleNameKey = fieldsList.get(i).getFieldname();
				 String fieldNameValue = request.getParameter(fileleNameKey);
				 userMap.put(fileleNameKey, fieldNameValue);
			}
			
			type=2;
			fieldsList = jisFieldsService.findByType(type);
			for(int i=0;i<fieldsList.size();i++){
				String fieldName = fieldsList.get(i).getFieldname();
				//根据select的name获取key值
				String fieldKey = request.getParameter(fieldName);
				userMap.put(fieldName, fieldKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMap;
	}

	/**
	 * 批量启用用户状态
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/startUserEnable", method = RequestMethod.GET)
	public ModelAndView startUserEnable(String iid,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String enable = request.getParameter("Enable");
		Integer Enable = null;
		try {
			if ("0".equals(enable)) {// 未启用状态
				enable = "1";
				Enable = Integer.parseInt(enable);
			}
			String[] para = iid.split(",");
			ComplatUser complatUser = null;
			for (int i = 0; i < para.length; i++) {
				//Integer corId = Integer.parseInt(para[i].trim());
				Integer userId = Integer.parseInt(para[i].trim());
				complatUser = complatUserService.findByKey(userId);
				if (complatUser.getEnable() == 0) {
					complatUser.setEnable(1);
					complatUserService.save(complatUser);
					returnMsg("success", "开启成功！", request);
				} else if (complatUser.getEnable() == 1) {
					returnMsg("success", "账号已开启！", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}
	}

	/**
	 * 批量停用用户状态
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/stopUserEnable", method = RequestMethod.GET)
	public ModelAndView stopUserEnable(String iid,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String enable = request.getParameter("Enable");
		Integer Enable = null;
		try {
			if ("1".equals(enable)) {// 已启用状态
				enable = "0";
				Enable = Integer.parseInt(enable);
			}
			String[] para = iid.split(",");
			ComplatUser complatUser = null;
			for (int i = 0; i < para.length; i++) {
				//Integer corId = Integer.parseInt(para[i].trim());
				Integer userId = Integer.parseInt(para[i].trim());
				complatUser = complatUserService.findByKey(userId);
				if (complatUser.getEnable() == 1) {
					complatUser.setEnable(0);
					complatUserService.save(complatUser);
					returnMsg("success", "关闭成功！", request);
				} else if (complatUser.getEnable() == 0) {
					returnMsg("success", "账号已关闭", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}
	}

	/**
	 * 启用--单条记录
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/EnableStart", method = RequestMethod.GET)
	public ModelAndView EnableStart(String iid, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComplatUser complatUser = null;
		try {
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer
						.parseInt(iid));
				int enable = complatUser.getEnable();
				if (enable == 0) {
					enable = 1;
					complatUser.setEnable(enable);
					complatUserService.save(complatUser);
					returnMsg("success", "启用成功！", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}

	}

	/**
	 * 停用--单条记录
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/EnableStop", method = RequestMethod.GET)
	public ModelAndView EnableStop(String iid, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComplatUser complatUser = null;
		try {
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer
						.parseInt(iid));
				int enable = complatUser.getEnable();
				if (enable == 1) {
					enable = 0;
					complatUser.setEnable(enable);
					complatUserService.save(complatUser);
					returnMsg("success", "停用成功！", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}

	}

	/**
	 * 删除政府用户--单条记录
	 * 
	 * @param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/deleteComplatUser", method = RequestMethod.GET)
	public ModelAndView deleteComplatUser(String iid, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComplatUser complatUser = null;
		try {
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer
						.parseInt(iid));
				if (complatUser != null) {
					complatUserService.delete(complatUser);
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new ModelAndView("redirect:/complat/complatList");
		}

	}
	
	
	
	
	/**
     * @discription    登录名唯一性校验
     * @param loginName
     * @param model
     * @param request
     * @param response  1：不重复     0：重复
     * @throws Exception
	 */
	@RequestMapping(value="/checkLoginName", method = RequestMethod.GET)
	public void checkLoginName(String loginName,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatUser complatUser = null;
			String loginname=StringUtils.trim((String)request.getParameter("loginname"));
			String oldLoginName=StringUtils.trim((String)request.getParameter("oldLoginname"));
			if(!loginname.equals(oldLoginName)){
				complatUser= complatUserService.findByLoginname(loginname);
				if(complatUser==null){					
					response.getWriter().write("1");								
				}else{
					response.getWriter().write("0");
				}
			}else{
				response.getWriter().write("1");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	
	/**
     * @discription    手机号重名校验
     * @param mobile
     * @param model
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value="/checkUserMobile", method = RequestMethod.GET)
	public void checkUserMobile(String mobile,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatUser complatUser = null;
			String mobileInput=StringUtils.trim((String)request.getParameter("mobile"));
			String oldMobile=StringUtils.trim((String)request.getParameter("oldMobile"));
			if(!mobileInput.equals(oldMobile)){
				complatUser = complatUserService.findByMobile(mobile);
				if(complatUser!=null){					
					response.getWriter().write("0");								
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	
	/**
     * @discription    身份证重复校验
     * @param papersNumber
     * @param model
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value="/checkUserCardid", method = RequestMethod.GET)
	public void checkUserCardid(String papersNumber,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			JisUserdetail jisUserdetail = null;
			String cardid=StringUtils.trim((String)request.getParameter("cardid"));
			String oldCardid=StringUtils.trim((String)request.getParameter("oldCardid"));
			if(!cardid.equals(oldCardid)){
				jisUserdetail = jisUserdetailService.findByCardid(cardid);
				if(jisUserdetail!=null){					
					response.getWriter().write("0");								
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	
	/**
	 * 
	 * 导入弹出框
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/showInport", method = RequestMethod.GET)
	public String showInport (HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String str="";
		try{
			str="users/complat/account_inport";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			return str;		
		}
	}
	
	
	/**
	 * 政府用户信息同步
	 * @param request
	 * @param response
	 * @return
	 */
	public void synchronization(ComplatUser complatUser, int type) throws Exception{
		try{
			List<JisApplication> list = jisApplicationService.findByIsSyncGroupNotNullAndLoginType(0); //查询支持同步的应用
			Random random = new Random(); //初始化随机数
			String data = TimeHelper.getCurrentCompactTime(); //获得同步时间
			String synctime = ""; //记录修改或新增的时间
			String operatetype = ""; //操作类型
			//判断同步类型,根据同步类型的不同来获取不同的数据
			if(type == 1){
				operatetype = "新增用户";
				synctime = String.valueOf(complatUser.getCreatetime());
			}else if(type == 2){
				operatetype = "修改用户";
				synctime = String.valueOf(complatUser.getModifytime());
			}else if(type == 3){
				operatetype = "删除用户";
				synctime = String.valueOf(complatUser.getModifytime());
			}
			//将所有支持同步的应用进行同步
			for(JisApplication jisApplication : list){
				JisSysview jisSysview = new JisSysview();
				jisSysview.setObjectid(String.valueOf(complatUser.getIid()));
				jisSysview.setObjectname(complatUser.getName());
				jisSysview.setState("C");
				jisSysview.setResult("T");
				jisSysview.setOptresult(1);
				jisSysview.setSynctime(synctime);
				jisSysview.setAppid(jisApplication.getIid());
				jisSysview.setCodeid(complatUser.getGroupid().toString());
				jisSysview.setTimes(1);
				jisSysview.setOperatetype(operatetype);
				int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
				jisSysview.setTranscationId(data + String.valueOf(rannum));
				jisSysviewService.save(jisSysview);
				syncDetail(complatUser, jisApplication, jisSysview);
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
	public void syncDetail(ComplatUser complatUser, JisApplication jisApplication, JisSysview sysView) throws Exception{
		
		try{
			/*Map<String,Object> jsonMap = new HashMap<String,Object>();
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
			jsonMap.put("parCode",complatGroupService.findByIid(complatGroup.getPid()).getCodeid());
			jsonMap.put("parName",complatGroupService.findByIid(complatGroup.getPid()).getName());
			jsonMap.put("qq","");
			jsonMap.put("state","T");
			jsonMap.put("userName","");
			String detail = JSONUtil.writeMapJSON(jsonMap);
			JisSysviewDetail jisSysviewDetail = new JisSysviewDetail();
			jisSysviewDetail.setSendmsg(detail);
			jisSysviewDetail.setTranscationId(jisSysview.getTranscationId());
			jisSysviewDetailService.save(jisSysviewDetail);*/
			
			
			
			
			
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			ComplatGroup group = complatGroupService.findByCodeid(sysView.getCodeid());
/*			if(group==null){
				jsonMap.put("cardId","");
			}else{
				
			}	*/		
			jsonMap.put("allParCode", "");
			jsonMap.put("allParName", "");
			
			jsonMap.put("appName",jisApplication.getName());
			jsonMap.put("cardId","");
			jsonMap.put("appid",sysView.getAppid());
			JisUserdetail jisUserdetail = new  JisUserdetail();
			jsonMap.put("compfax",complatUser.getFax());
			jsonMap.put("comptel", "");
			jsonMap.put("email",complatUser.getEmail());
			jsonMap.put("groupCode",sysView.getCodeid());
			//jsonMap.put("groupName",group.getName());
			jsonMap.put("groupName","");
			jsonMap.put("headShip",complatUser.getHeadship());
			jsonMap.put("hometel",complatUser.getPhone());
			jsonMap.put("id", "");
			jsonMap.put("loginName",complatUser.getLoginname());
			jsonMap.put("loginPass",complatUser.getPwd());
			jsonMap.put("mobile", complatUser.getMobile());
			jsonMap.put("msn","");
			jsonMap.put("ndlogin", "");
			jsonMap.put("parCode", "");
			jsonMap.put("parName", "");
			jsonMap.put("qq", complatUser.getQq());
			jsonMap.put("state","T");
			jsonMap.put("userName", complatUser.getName());
			String detail = JSONUtil.writeMapJSON(jsonMap);
			JisSysviewDetail jisSysviewDetail = new JisSysviewDetail();
			jisSysviewDetail.setSendmsg(detail);
			jisSysviewDetail.setTranscationId(sysView.getTranscationId());
			jisSysviewDetailService.save(jisSysviewDetail);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	/**
	 * 查询新增或编辑页面的机构树
	 * @param args
	 */
	
	@RequestMapping(value = "/getGroup", method = RequestMethod.POST)
	public void getGroup(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String groupId = sysUserSession.getDeptId();
			String isDisabled = request.getParameter("isDisabled");
			List<ComplatGroup> list = new ArrayList<ComplatGroup>();
			if (!"0".equals(groupId) && StringUtils.isNotBlank(groupId)) {
				list = complatGroupService.findByPid(Integer.parseInt(groupId));
				list.add(complatGroupService.findByIid(Integer.parseInt(groupId)));
			} else {
				list.add(complatGroupService.findByIid(128));
			}
			

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				for (ComplatGroup c : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", c.getIid() + "");
					map.put("name", c.getName());
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

			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}


	
	public static void main(String[] args) {
		String pwd = "f28FdHN4fHEGMgRKBTgFPwI7AE0COQ==";//
		String p = Md5Util.md5decode(pwd);
		System.out.println("解密后的密码是:"+p);
	}
}
