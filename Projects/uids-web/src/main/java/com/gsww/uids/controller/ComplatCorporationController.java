package com.gsww.uids.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.service.JisLogService;
import com.hanweb.common.util.Md5Util;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2017-09-07 下午14:18:12</p>
 * <p>类描述 :   法人管理模块控制器    </p>
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Controller
@RequestMapping(value = "/complat")
public class ComplatCorporationController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ComplatCorporationController.class);
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	@Autowired
	private ComplatCorporationService complatCorporationService;
	
	@Autowired
	private JisLogService jisLogService;
	
	/**
	 * 获取法人列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/corporationList",method = RequestMethod.GET)
	public String corporationList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatCorporation.class,findNowPage);
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("NE_operSign", 3);
			Specification<ComplatCorporation>  spec=super.toNewSpecification(searchParams, ComplatCorporation.class);
			
			//分页
			Page<ComplatCorporation> pageInfo= complatCorporationService.getCorporationPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/corporationList";
		}
		return "users/corporation/corporation_list";
	}
	
	/**
	 * 转到新增法人页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/corporationEdit", method = RequestMethod.GET)
	public ModelAndView accountEdit(String corporationId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView mav=new ModelAndView("users/corporation/corporation_edit");
		ComplatCorporation corporation = new ComplatCorporation();   
		try {
			if(StringHelper.isNotBlack(corporationId)){
				Integer iid = Integer.parseInt(corporationId);
				corporation = complatCorporationService.findByKey(iid);
				String corNation = corporation.getNation();
				model.addAttribute("corNation", corNation);
				//对注册时间进行转换
				Date createTime = corporation.getCreateTime();
				if(createTime != null){
					String time = sdf.format(createTime);
					model.addAttribute("time",time);
				}
				
				//判断密码是否存在，若存在则解密
				String pwd = corporation.getPwd();
				if(StringHelper.isNotBlack(pwd)){
					String minWenPwd = Md5Util.md5decode(pwd);
					corporation.setPwd(minWenPwd);
				}
			}else{
				corporation = new ComplatCorporation();
			}
			model.addAttribute("corporation",corporation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 保存用户信息
	 * @return 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationSave", method = RequestMethod.POST)
	public void corporationSave(ComplatCorporation corporation,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Integer operType = null;
			if(corporation != null){
				if(corporation.getauthState() == null){
					corporation.setauthState(0);
				}
				if(corporation.getisAuth() == null){
					corporation.setisAuth(0);
				}
				if(corporation.getEnable() == null){
					corporation.setEnable(1);
				}
				if(corporation.getOperSign() == null){
					corporation.setOperSign(1);
					operType = 1;
				}else{
					corporation.setOperSign(2);
					operType = 2;
				}
				
				//对注册时间进行转换
				Date createTime = null;
				String time = request.getParameter("time");
				if(time == null || "".equals(time)){
					time = TimeHelper.getCurrentTime();
					createTime = sdf.parse(time);
				}else{
					createTime = sdf.parse(time);
				}
				corporation.setCreateTime(createTime);
				
				//对密码加密
				String JiaMiPWD = Md5Util.md5encode(corporation.getPwd());
				corporation.setPwd(JiaMiPWD);
				
				corporation.setLoginIp(this.getIpAddr(request));
				//最后一次登录时间
				corporation.setLoginTime(sdf.parse(TimeHelper.getCurrentTime()));
				
				if(corporation.getType() == 1){
					
					//对民族处理  企业法人
					String qyNation = request.getParameter("qyNation");
					if(StringHelper.isNotBlack(qyNation)){
						corporation.setNation(qyNation);
					}
					//对企业名称处理，企业法人
					String qyName = request.getParameter("qyName");
					if(StringHelper.isNotBlack(qyName)){
						corporation.setName(qyName);
					}
					
					//对企业负责人处理，企业法人
					String qyRealName = request.getParameter("qyRealName");
					if(StringHelper.isNotBlack(qyRealName)){
						corporation.setRealName(qyRealName);
					}
					
					//对企业负责人身份证号处理，企业法人
					String qyCardNumber = request.getParameter("qyCardNumber");
					if(StringHelper.isNotBlack(qyCardNumber)){
						corporation.setCardNumber(qyCardNumber);
					}
					
					//对企业组织机构代码处理，企业法人
					String qyOrgNumber = request.getParameter("qyOrgNumber");
					if(StringHelper.isNotBlack(qyOrgNumber)){
						corporation.setOrgNumber(qyOrgNumber);
					}
					
				}else{
				
					//对民族处理 非 企业法人
					String fqyNation = request.getParameter("fqyNation");
					if(StringHelper.isNotBlack(fqyNation)){
						corporation.setNation(fqyNation);
					}
					
					//对企业名称处理，非企业法人
					String fqyName = request.getParameter("fqyName");
					if(StringHelper.isNotBlack(fqyName)){
						corporation.setName(fqyName);
					}
					
					//对企业名称处理，非企业法人
					String fqyRealName = request.getParameter("fqyRealName");
					if(StringHelper.isNotBlack(fqyRealName)){
						corporation.setRealName(fqyRealName);
					}
					
					//对企业负责人身份证号处理，非企业法人
					String fqyCardNumber = request.getParameter("fqyCardNumber");
					if(StringHelper.isNotBlack(fqyCardNumber)){
						corporation.setCardNumber(fqyCardNumber);
					}
					
					//对企业组织机构代码处理，非企业法人
					String fqyOrgNumber = request.getParameter("fqyOrgNumber");
					if(StringHelper.isNotBlack(fqyOrgNumber)){
						corporation.setOrgNumber(fqyOrgNumber);
					}else{
						corporation.setRegNumber(corporation.getRegNumber().substring(1, corporation.getRegNumber().length()));
					}
				}
				//重复校验
				if(corporation.getIid() == null){
					Integer checkData = complatCorporationService.checkUnique(corporation.getLoginName(), corporation.getRegNumber(), corporation.getOrgNumber());
					if(checkData == 1){
						resMap.put("ret", "0");
						resMap.put("msg", "法人用户重复，保存失败！");
						response.getWriter().write(JSONObject.toJSONString(resMap));
						//returnMsg("error","法人用户重复，保存失败",request);
					}else{
						complatCorporationService.save(corporation);
						resMap.put("ret", "1");
						resMap.put("msg", "保存成功！");
						response.getWriter().write(JSONObject.toJSONString(resMap));
						//returnMsg("success","保存成功",request);
						//记录日志
						this.addJisLog(corporation, request,operType);
					}
					
				}else{
					complatCorporationService.save(corporation);
					resMap.put("ret", "1");
					resMap.put("msg", "保存成功！");
					response.getWriter().write(JSONObject.toJSONString(resMap));
					//returnMsg("success","保存成功",request);
					//记录日志
					this.addJisLog(corporation, request,operType);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put("ret", "2");
			resMap.put("msg", "保存失败！");
			response.getWriter().write(JSONObject.toJSONString(resMap));
			logger.error(e.getMessage(), e);

		}
		
	}
	
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationDelete", method = RequestMethod.GET)
	public ModelAndView accountDelete(String corporationId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=corporationId.split(",");
			ComplatCorporation corporation = new ComplatCorporation();
			for(int i=0;i<para.length;i++){
				Integer corId = Integer.parseInt(para[i].trim());
				corporation=complatCorporationService.findByKey(corId);
				if(corporation != null){
					Integer iid = corporation.getIid();
					complatCorporationService.updateCorporation(iid);
					//complatCorporationService.delete(corporation);
					returnMsg("success", "删除成功", request);
					
					//记录日志
					Integer operType = 3;
					this.addJisLog(corporation, request,operType);
				}
				
			}
		} catch (Exception e) {
			returnMsg("error", "删除失败",request);
			logger.error(e.getMessage(), e);

		} finally{
			return  new ModelAndView("redirect:/complat/corporationList");
		}
		
	}
	
	/**
     * @discription  开启账号  
     * @param iid
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationStart", method = RequestMethod.GET)
	public ModelAndView corporationStart(String corporationIid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatCorporation complatCorporation = new ComplatCorporation();
		try{
			String[] para = corporationIid.split(",");
			for(int i=0;i<para.length;i++){
				complatCorporation = complatCorporationService.findByKey(Integer.parseInt(para[i]));
				Integer enable = complatCorporation.getEnable(); 
				if(enable == 0){
					complatCorporation.setEnable(1);
					complatCorporationService.save(complatCorporation);
					returnMsg("success", "启用成功！", request);				
				} else {
					returnMsg("success", "账号已启用！", request);
				}
			}
		}catch(Exception e){
			returnMsg("error", "账号启用失败！", request);
			logger.error(e.getMessage(), e);

		}finally{
			return  new ModelAndView("redirect:/complat/corporationList");
		}
	}
	
	/**
     * @discription   关闭账号
     * @param iid
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/corporationStop", method = RequestMethod.GET)
	public ModelAndView corporationStop(String corporationIid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatCorporation complatCorporation = new ComplatCorporation();
		try{
			String[] para = corporationIid.split(",");
			for(int i=0;i<para.length;i++){
				complatCorporation = complatCorporationService.findByKey(Integer.parseInt(para[i]));
				Integer enable = complatCorporation.getEnable(); 
				if(enable == 1){
					complatCorporation.setEnable(0);
					complatCorporationService.save(complatCorporation);
					returnMsg("success", "关闭成功！", request);				
				} else {
					returnMsg("success", "账号已关闭！", request);
				}
			}
		}catch(Exception e){
			returnMsg("error", "账号关闭失败！", request);
			logger.error(e.getMessage(), e);

		}finally{
			return  new ModelAndView("redirect:/complat/corporationList");
		}
	}
	
	/**
     * @discription   用户认证 
     * @param outsideuserIid
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@SuppressWarnings("finally") 
	@RequestMapping(value = "/corporationAuth", method = RequestMethod.POST)
	public void corporationAuth(ComplatCorporation corporation,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatCorporation complatCorporation = new ComplatCorporation();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try{
			int type = 1;
			String corporationType = StringUtils.trim((String) request.getParameter("corporUserType"));
			String rejectReason2 = StringUtils.trim((String) request.getParameter("rejectReason2"));
			if(StringHelper.isNotBlack(corporationType)){
				type = Integer.parseInt(corporationType);//1:通过  0：拒绝
			}
			if(corporation.getIid() != null){ 
				complatCorporation = complatCorporationService.findByKey(corporation.getIid());
				if(type == 1) {
					int isAuth = complatCorporation.getisAuth();
					if (isAuth == 0) {
						//认证通过调接口，还没写
						
						complatCorporation.setisAuth(1);
						complatCorporation.setauthState(1);
						complatCorporationService.save(complatCorporation);
						returnMsg("success", "用户认证成功！", request);
						resMap.put("ret", "0");
						response.getWriter().write(JSONObject.toJSONString(resMap));
					}
				} else if (type == 0) {
					complatCorporation.setisAuth(0);
					complatCorporation.setauthState(2);
					if (rejectReason2 != null) {
						complatCorporation.setrejectReason(rejectReason2);
					}
					complatCorporationService.save(complatCorporation);
					returnMsg("success", "用户认证已拒绝！", request);
					resMap.put("ret","0");
					response.getWriter().write(JSONObject.toJSONString(resMap));
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			returnMsg("error", "认证失败！", (HttpServletRequest) request);
		}
	}
	
	/**
     * @discription    获取认证用户信息
     * @param request
     * @param response
	 */
	@RequestMapping(value="/getCorporationInfo",method = RequestMethod.GET)
	public String getCorporationInfo(Model model,HttpServletRequest request, HttpServletResponse response) {
		try {
			String pidStr = request.getParameter("iid");
			Integer pid = Integer.valueOf(Integer.parseInt(pidStr));
			ComplatCorporation complatCorporation = complatCorporationService.findByKey(pid);
			model.addAttribute("complatCorporation",complatCorporation);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "users/corporation/corporation_authen_list";
	}
	
	/**
     * @discription    登录名唯一性校验
     * @param loginName
     * @param model
     * @param request
     * @param response  
     * @throws Exception
	 */
	@RequestMapping(value="/checkCorporationLoginName", method = RequestMethod.GET)
	public void checkLoginName(String loginName,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatCorporation complatCorporation = new ComplatCorporation();
			String loginNameInput=StringUtils.trim((String)request.getParameter("loginName"));
			String oldLoginName=StringUtils.trim((String)request.getParameter("oldLoginName"));
			if(!loginNameInput.equals(oldLoginName)){
				complatCorporation = complatCorporationService.findByLoginNameIsUsed(loginName);
				if(complatCorporation!=null){					
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
	 * 获取客户端IP
	 */
	  private String getIpAddr(HttpServletRequest request) {     
	      String ip = request.getHeader("x-forwarded-for");     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("Proxy-Client-IP");     
	     }     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("WL-Proxy-Client-IP");     
	      }     
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	          ip = request.getRemoteAddr();     
	     }     
	     return ip;     
	}  
	
	  
	/**
	 * 日志记录
	 * @param corporation
	 * @param request
	 * @param operType
	 * @throws Exception
	 */
	private void addJisLog(ComplatCorporation corporation,HttpServletRequest request,Integer operType) throws Exception{
		//日志记录JisLog
		//获取当前登录用户,即操作用户
		SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		String userName = sysUserSession.getLoginAccount();
		JisLog jisLog = new JisLog();
		jisLog.setUserId(userName);
		jisLog.setIp(sysUserSession.getUserIp());
		jisLog.setOperateTime(sdf.parse(TimeHelper.getCurrentTime()));
		jisLog.setModuleName(10);//10-法人管理
		String spec = "";
		if(operType == 1){
			jisLog.setOperateType(operType);
			spec = userName+"增加【"+corporation.getLoginName()+"】法人用户";
		}else if(operType == 2){
			jisLog.setOperateType(operType);
			spec = userName+"修改【"+corporation.getLoginName()+"】法人用户";
		}else{
			jisLog.setOperateType(operType);
			spec = userName+"删除【"+corporation.getLoginName()+"】法人用户";
		}
		jisLog.setSpec(spec);
		jisLogService.save(jisLog);
	}
}
