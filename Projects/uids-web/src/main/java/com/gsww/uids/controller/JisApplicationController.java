package com.gsww.uids.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.JSONUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.entity.JisUserdetail;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.service.JisRoleobjectService;
import com.gsww.uids.service.JisSysviewDetailService;
import com.gsww.uids.service.JisSysviewService;
import com.gsww.uids.service.JisUserdetailService;
import com.gsww.uids.util.HttpClientUtil;
/**
 * 应用管理控制器
 * @author Seven
 *
 */
@Controller
@RequestMapping(value = "/application")
public class JisApplicationController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(JisApplicationController.class);
	@Autowired
	private JisApplicationService jisApplicationService ;
	@Autowired
	private ComplatGroupService complatGroupService ;
	@Autowired
	private JisSysviewService jisSysviewService;
	@Autowired
	private JisSysviewDetailService jisSysviewDetailService;
	@Autowired
	private ComplatUserService complatUserService;
	@Autowired
	private JisUserdetailService jisUserdetailService;
	@Autowired
	private JisRoleobjectService jisRoleobjectService;
	@Autowired
	private JisLogService jisLogService;

	
	/**
	 * 获取应用列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/applicationList",method = RequestMethod.GET)
	public String applicationList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "iid") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "ASC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			if(StringUtils.isNotBlank(request.getParameter("orderField"))){
				orderField=(String)request.getParameter("orderField");
			}
			if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
				orderSort=(String)request.getParameter("orderSort");
			}
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,JisApplication.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			
			//分页
			SysUserSession sysUserSession = (SysUserSession) hrequest.getSession().getAttribute("sysUserSession");
			String[] roleIds = sysUserSession.getRoleIds().split(",");
			List<JisRoleobject> jisRoleobjects = new ArrayList<JisRoleobject>();
			for(String s :roleIds){
				List<JisRoleobject> jisRoleobject=jisRoleobjectService.findByRoleIdAndType(Integer.parseInt(s),3);
				jisRoleobjects.addAll(jisRoleobject);
			}
			List<Integer> appids= new ArrayList<Integer>();
			for(JisRoleobject roleobj : jisRoleobjects){
				appids.add(roleobj.getObjectid());
			}
			
//			JisApplication jisApplication=new JisApplication();
//			for(Integer appid:appids){
//				jisApplication = jisApplicationService.findByKey(appid);
//			}
			searchParams.put("IN_iid",appids);
			
			Specification<JisApplication>  spec=super.toNewSpecification(searchParams, JisApplication.class);
			Page<JisApplication> pageInfo = jisApplicationService.getApplicationPage(spec,pageRequest);

			
			List<JisApplication> apps = pageInfo.getContent();
			Map<Integer, Object> groupMap = new HashMap<Integer, Object>();

			for(JisApplication app:apps){
				ComplatGroup group = complatGroupService.findByIid(app.getGroupId());
				groupMap.put(group.getIid(), group.getName());
			}

			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("groupMap", groupMap);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			String groupName=request.getParameter("groupname");
			model.addAttribute("groupName", groupName);
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "system/jis/application_list";
	}
	
	/**
	 * 转到新增或编辑页面
	 * @param iid
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applicationEdit", method = RequestMethod.GET)
	public String applicationEdit(Integer iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			JisApplication jisApplication = null;
			
			if(iid!=null){
				
				List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
				Map<Integer, Object> groupMap = new HashMap<Integer, Object>();
				groupList = complatGroupService.getComplatGroupList();
				for (Map<String, Object> group : groupList) {
					groupMap.put((Integer) group.get("iid"), group.get("name"));
				}
				//分页
				model.addAttribute("groupMap", groupMap);
				
				// 将搜索条件编码成字符串，用于排序，分页的URL
				String groupName=request.getParameter("groupname");
				model.addAttribute("groupName", groupName);
				
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				jisApplication = jisApplicationService.findByKey(iid);
				Map<String,String> map=new HashMap<String,String>();
				List<JisApplication> list=new ArrayList<JisApplication>();
				/*userList=sysCardService.findUserNameList(cardId);*/
				/*roleList=sysUserService.findAccountRoleList(sysUser.getUserId());*/
				/*String userName="";
				String userId="";
				for (SysUser sysUser : userList) {
					userName+=sysUser.getUserName()+",";
					userId+=sysUser.getUserId()+",";
				}
				if(StringUtils.isNotBlank(userName)){
					userName=userName.substring(0, userName.length()-1);
					/*userRolemap.put(sysUser.getUserAcctId(), userRoleName);
					userId=userId.substring(0,userId.length()-1);
				}else{
					userMap.put("0", "");
				}*/
				model.addAttribute("list", list);
				model.addAttribute("map", map);
				/*model.addAttribute("userId", userId);*/
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				jisApplication = new JisApplication();
			}
			model.addAttribute("jisApplication", jisApplication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/jis/application_edit";
	}
	
	/**
	 * 保存信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/applicationSave", method = RequestMethod.POST)
	public ModelAndView applicationSave(JisApplication jisApplication,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		
		SysUserSession sysUserSession =(SysUserSession)request.getSession().getAttribute("sysUserSession");
		
		try {
			if(jisApplication != null){
				Integer iid = jisApplication.getIid();
				jisApplicationService.save(jisApplication);
				returnMsg("success","保存成功",request);
				
				
				if(iid==null){
					String desc = sysUserSession.getLoginAccount() + "新增了:" + jisApplication.getName();
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,4,1);
				}else{
					String desc = sysUserSession.getLoginAccount() + "修改了:" + jisApplication.getName();
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,4,2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return new ModelAndView("redirect:/application/applicationList");
		}
	}
	
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/applicationDelete", method = RequestMethod.GET)
	public ModelAndView applicationDelete(String iid,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		try {
			String[] para=iid.split(",");
			JisApplication jisApplication = null;
			for(int i=0;i<para.length;i++){
				Integer Iid = Integer.parseInt(para[i].trim());
				jisApplication=jisApplicationService.findByKey(Iid);
				jisApplicationService.delete(jisApplication);
							}
			returnMsg("success","删除成功",request);
			
			String desc=session.getLoginAccount()+"删除了"+jisApplication.getName();
            jisLogService.save(session.getLoginAccount(),session.getUserIp(), desc, 4, 3);
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);
		
		/*try {
			JisApplication jisApplication = null;
			jisApplication=jisApplicationService.findByKey(iid);
			jisApplicationService.delete(jisApplication);
			boolean flag=true;
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,系统管理员不允许删除",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);*/			
		} finally{
			return  new ModelAndView("redirect:/application/applicationList");
		}
		
	}
	
	/**
	 * 查看账号情信息
	 */
	/*@RequestMapping(value = "/datacallView", method = RequestMethod.GET)
	public String datacallView(int iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		try {
			JisDatacall jisDatacall = null;
			if(StringHelper.isNotBlack(iid)){
				jisDatacall = jisDatacallService.findByKey(iid);
			}else{
				jisDatacall = new JisDatacall();
			}
			model.addAttribute("jisDatacall", jisDatacall);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/jis/datacall_view";
	}*/
	
	/**
	 * 标识是否重复
	 * @param userName
	 * @param setId
	 * @param request
	 * @param response 1表示不重复，0表示重复
	 * @throws Exception
	 */
	@RequestMapping(value="/checkMark", method = RequestMethod.GET)
	public void checkMark(String mark,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			JisApplication jisApplication = null;
			String markInput=StringUtils.trim((String)request.getParameter("mark"));
			String oldMark=StringUtils.trim((String)request.getParameter("oldMark"));
			if(!markInput.equals(oldMark)){
				jisApplication=jisApplicationService.queryMarkIsUsed(mark);
				if(jisApplication!=null){					
					response.getWriter().write("0");								
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void convertObject() {
		JisSynEntity jisSynEntity=new JisSynEntity();
	        
		jisSynEntity.setName("JSON");
	        stu.setAge("23");
	        stu.setAddress("北京市西城区");
	
	        //1、使用JSONObject
	        JSONObject json = JSONObject.fromObject(stu);
	        //2、使用JSONArray
	        JSONArray array=JSONArray.fromObject(stu);
	        
	        String strJson=json.toString();
	        String strArray=array.toString();
	        
	        System.out.println("strJson:"+strJson);
	        System.out.println("strArray:"+strArray);
	}*/
	
	
	/**
	 * 机构同步
	 * @param model
	 * @param groupid2
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syngroup", method = RequestMethod.GET)
	public ModelAndView syngroup(Model model,String groupid2,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		ComplatGroup complatGroup=null;
		String[] ids=null;
		
		try {
			if(groupid2!=null && groupid2!=""){
				if(groupid2.lastIndexOf(",")>0){
					ids=groupid2.substring(0,groupid2.length()-1).split(",");
				}else{
					ids=groupid2.split(",");
				}
				String syniid=request.getParameter("syniid");
				Integer appId=Integer.parseInt(syniid);
				
				String timeId=TimeHelper.getCurrentCompactTime();
				String randomId=Integer.toString((int)(Math.random()*100000));
				if(ids.length<=500){
					for(String id:ids ){
						complatGroup=complatGroupService.findByIid(Integer.parseInt(id));
						JisApplication jisApplication=jisApplicationService.findByKey(appId);
						String transcationId=timeId+randomId;
						
						JisSysview jisSysview=new JisSysview();
						jisSysview.setObjectid(id);
						jisSysview.setObjectname(complatGroup.getName());
						jisSysview.setState("C");
						jisSysview.setResult("TG");
						jisSysview.setOptresult(1);
						jisSysview.setSynctime(TimeHelper.getCurrentTime());
						jisSysview.setAppid(appId);
						jisSysview.setCodeid(complatGroup.getCodeid());
						jisSysview.setOperatetype("修改机构");
						jisSysview.setTimes(1);
						jisSysview.setErrorspec("");
						jisSysview.setTranscationId(transcationId);
						jisSysviewService.save(jisSysview);
						
						Map<String, String> jsonMap=new HashMap<String, String>();
						jsonMap.put("allParCode", complatGroup.getSuffix());
						jsonMap.put("allParName", complatGroup.getGroupallname());
						jsonMap.put("appName", jisApplication.getName());
						jsonMap.put("appid",syniid);
						jsonMap.put("cardId","");
						jsonMap.put("compfax","");
						jsonMap.put("comptel","");
						jsonMap.put("email","");
						jsonMap.put("groupCode",complatGroup.getCodeid());
						jsonMap.put("groupName",complatGroup.getName());
						jsonMap.put("headShip","");
						jsonMap.put("hometel","");
						jsonMap.put("id","");
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
						
						JisSysviewDetail jisSysviewDetail=new JisSysviewDetail();
						JSONUtil jsonUtil=new JSONUtil();
						String synJson=jsonUtil.writeMapSJSON(jsonMap);
						jisSysviewDetail.setSendmsg(synJson);
						jisSysviewDetail.setTranscationId(transcationId);
						jisSysviewDetailService.save(jisSysviewDetail);
						
					}
					returnMsg("success","同步成功",request);
				}else{
					returnMsg("error","同步失败,同步机构数不能超过500个",request);
				}
			}else{
				returnMsg("error","同步失败,未选择机构",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","同步失败",request);
		}
		return new ModelAndView("redirect:/application/applicationList");
	}
	
	/**
	 * 用户同步
	 * @param model
	 * @param groupid2
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/synuser", method = RequestMethod.GET)
	public ModelAndView synuser(Model model,String groupid2,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		ComplatGroup complatGroup=null;
		String[] ids=null;
		
		try {
			if(groupid2!=null && groupid2!=""){
				if(groupid2.lastIndexOf(",")>0){
					ids=groupid2.substring(0,groupid2.length()-1).split(",");
				}else{
					ids=groupid2.split(",");
				}
				String syniid=request.getParameter("syniid");
				Integer appId=Integer.parseInt(syniid);
				
				String timeId=TimeHelper.getCurrentCompactTime();
				String randomId=Integer.toString((int)(Math.random()*100000));
				
				if(ids.length<=200){
					for(String id:ids ){
						complatGroup=complatGroupService.findByIid(Integer.parseInt(id));
						List<ComplatUser> complatUserList=complatUserService.findByGroupid(Integer.parseInt(id));
						if(complatUserList!=null && complatUserList.size()!=0 ){
							for(ComplatUser complatUser:complatUserList){
								JisApplication jisApplication=jisApplicationService.findByKey(appId);
								String transcationId=timeId+randomId;
								
								JisSysview jisSysview=new JisSysview();
								jisSysview.setObjectid(Integer.toString(complatUser.getIid()));
								jisSysview.setObjectname(complatUser.getName());
								jisSysview.setState("C");
								jisSysview.setResult("T");
								jisSysview.setOptresult(1);
								jisSysview.setSynctime(TimeHelper.getCurrentTime());
								jisSysview.setAppid(appId);
								jisSysview.setCodeid(complatGroup.getCodeid());
								jisSysview.setOperatetype("修改用户");
								jisSysview.setTimes(1);
								jisSysview.setErrorspec("");
								jisSysview.setTranscationId(transcationId);
								jisSysviewService.save(jisSysview);
								
								Map<String, String> jsonMap=new HashMap<String, String>();
								JisUserdetail jisUserdetail=jisUserdetailService.findByUserid(complatUser.getIid());
								jsonMap.put("allParCode", complatGroup.getSuffix());
								jsonMap.put("allParName", complatGroup.getGroupallname());
								jsonMap.put("appName", jisApplication.getName());
								jsonMap.put("appid",syniid);
								if(jisUserdetail!=null){
									jsonMap.put("cardId",jisUserdetail.getCardid());
									jsonMap.put("comptel",jisUserdetail.getComptel());
								}else{
									jsonMap.put("cardId","");
									jsonMap.put("comptel","");
								}
								
								jsonMap.put("compfax",complatUser.getFax());
								jsonMap.put("email",complatUser.getEmail());
								jsonMap.put("groupCode",complatGroup.getCodeid());
								jsonMap.put("groupName",complatGroup.getName());
								jsonMap.put("headShip",complatUser.getHeadship());
								jsonMap.put("hometel",complatUser.getPhone());
								jsonMap.put("id","");
								jsonMap.put("loginName",complatUser.getLoginname());
								jsonMap.put("loginPass",complatUser.getPwd());
								jsonMap.put("mobile",complatUser.getMobile());
								jsonMap.put("msn",complatUser.getMsn());
								jsonMap.put("ndlogin","");
								if(complatGroup.getPid() != null){
									jsonMap.put("parCode",complatGroupService.findByIid(complatGroup.getPid()).getCodeid());
									jsonMap.put("parName",complatGroupService.findByIid(complatGroup.getPid()).getName());
								}else{
									jsonMap.put("parCode","");
									jsonMap.put("parName","");
								}
								jsonMap.put("qq",complatUser.getQq());
								jsonMap.put("state","T");
								jsonMap.put("userName",complatUser.getName());
								
								JisSysviewDetail jisSysviewDetail=new JisSysviewDetail();
								JSONUtil jsonUtil=new JSONUtil();
								String synJson=jsonUtil.writeMapSJSON(jsonMap);
								jisSysviewDetail.setSendmsg(synJson);
								jisSysviewDetail.setTranscationId(transcationId);
								jisSysviewDetailService.save(jisSysviewDetail);
							}
						}
					}
					returnMsg("success","同步成功",request);
				}else{
					returnMsg("error","同步失败，同步用户选择的机构数不能超过200个",request);
				}
			}else{
				returnMsg("error","同步失败，未选择机构",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","同步失败",request);
		}
		return new ModelAndView("redirect:/application/applicationList");
	}
	
	
	@RequestMapping({"checknet"})
    @ResponseBody
    public int checkNet(String url) {
	    int overtime = 10000;
	    int code = HttpClientUtil.getStatusCode(url, overtime);
    return code;
  }
}
