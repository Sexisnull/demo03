

package com.gsww.uids.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import org.springframework.web.multipart.MultipartFile;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.ExcelUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisUserdetail;
import com.gsww.uids.service.ComplatGroupService;
import com.gsww.uids.service.ComplatRoleService;
import com.gsww.uids.service.ComplatUserService;
import com.gsww.uids.service.JisFieldsService;
import com.gsww.uids.service.JisUserdetailService;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2017-09-07 下午14:18:12</p>
 * <p>类描述 :   政府用户模块控制器    </p>
 *
 * @version 3.0.0
 * @author <a href=" ">shenxh</a>
 */

@Controller
@RequestMapping(value = "/complat")
public class ComplatUserController extends BaseController{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ComplatUserController.class);
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
	/**
	 * 获取政府用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/complatList",method = RequestMethod.GET)
	public String complatList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createtime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			if (StringUtils.isNotBlank(request.getParameter("orderField"))) {
				orderField = (String) request.getParameter("orderField");
			}
			if (StringUtils.isNotBlank(request.getParameter("orderSort"))) {
				orderSort = (String) request.getParameter("orderSort");
			}
			
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatUser.class,findNowPage);
			
			//搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatUser>  spec=super.toNewSpecification(searchParams, ComplatUser.class);
			
			//分页
			Page<ComplatUser> pageInfo = complatUserService.getComplatUserPage(spec,pageRequest);
			model.addAttribute("pageInfo", pageInfo);			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);						
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("列表打开失败："+ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/complatList";
		}
		return "users/complat/account_list";
	}
		
	
	
	/**
	 * 转到新增政府用户页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatUserEdit", method = RequestMethod.GET)
	public String complatUserEdit(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {		
		try {
			ComplatUser complatUser = null;	
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer.parseInt(iid));
				
				Date createTime = complatUser.getCreatetime();
				if(createTime != null){
					String time = sdf.format(createTime);
					model.addAttribute("time",time);
				}
			} else {
				complatUser = new ComplatUser();
			}
			model.addAttribute("complatUser",complatUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "users/complat/account_edit";
	}
	
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatSave", method = RequestMethod.POST)
	public ModelAndView complatSave(ComplatUser complatUser,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if (complatUser != null) {
				String iidStr = String.valueOf(complatUser.getIid());
				System.out.println(iidStr);
				if (iidStr == "null" || iidStr.length() <= 0) {
					Date d = new Date(); 
					complatUser.setEnable(1); // 是否禁用
					//complatUser.setAuthState(0); // 审核状态
					//complatUser.setIsAuth(0); // 是否审核
					complatUser.setCreatetime(d);//创建时间
					complatUserService.save(complatUser);
					returnMsg("success", "保存成功", request);
				} else {
					//注册时间
					complatUser.setEnable(1); // 是否禁用
					String time = request.getParameter("time");
					Date createTime = sdf.parse(time);
					complatUser.setCreatetime(createTime);
					complatUserService.save(complatUser);
					returnMsg("success", "编辑成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	/**
	 * 批量删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatUserDelete", method = RequestMethod.GET)
	public ModelAndView complatUserDelete(String complatUserId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=complatUserId.split(",");
			ComplatUser complatUser = null;
			for(int i=0;i<para.length;i++){
				Integer corId = Integer.parseInt(para[i].trim());
				complatUser=complatUserService.findByKey(corId);
				if(complatUser != null){
					complatUserService.delete(complatUser);
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	
	
	/**
	 * 模板下载
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
/*	@RequestMapping("/downloadComplaUserTemplate")
	public void downloadComplaUserTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/complat")+ "/";// 取系统当前路径
		String realName = "政府用户信息统计列表.xlsx";
		// 设置response参数，可以打开下载页面
		//response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(realName, "utf-8"));

		FileInputStream in = null;
		BufferedInputStream binpu = null;
		BufferedOutputStream bout = null;

		OutputStream output = null;
		output = response.getOutputStream();
		FileInputStream fis = null;
		try {
			// 如果是中文路径，在此处抛出异常
			File f = new File(realPath + realName);
			if (f.exists()) {
				in = new FileInputStream(realPath + realName);
			}
			binpu = new BufferedInputStream(in);
			bout = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[1024];

			int i = 0;
			while ((i = binpu.read(b, 0, b.length)) > 0) {
				bout.write(b, 0, i);
			}
			bout.flush();
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
		if (output != null) {
			output.close();
			output = null;
		}
	}
	*/
	
	
	

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
	@RequestMapping(value = "/complatImport", method = RequestMethod.POST)
	public ModelAndView complatImport(@RequestParam("excelFile")MultipartFile multipartFile,
			HttpServletRequest request,Model model,
			HttpServletResponse response) throws Exception {				
        Map<String,String> fileMap = new HashMap<String, String>();//返回Map
		final String UPLOAD_EXCEL_PATH = "uploadFile\\complat\\";
		String absoluteFilePath = request.getSession().getServletContext().getRealPath(UPLOAD_EXCEL_PATH);
		String fileName = multipartFile.getOriginalFilename();
		//FileUtil.upFile(multipartFile.getInputStream(), uuidFileName, absoluteFilePath);
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("0", "name");
		fieldMap.put("1", "loginname");
		fieldMap.put("2", "loginallname");
		fieldMap.put("3", "mobile");
		fieldMap.put("4", "email");
		//List<ComplatUser> users= ExcelUtil.readXls(absoluteFilePath+"/"+uuidFileName,ComplatUser.class,fieldMap);
		List<ComplatUser> users= ExcelUtil.readXls(absoluteFilePath+"/"+fileName,ComplatUser.class,fieldMap);
		try {
			for(ComplatUser complatUser:users){
				List<ComplatUser> list = complatUserService.findByUserName(complatUser.getName());
				//String mobile=complatUser.getMobile();
				if(null != list && list.size()>0){
					fileMap.put("flag", "0");
				}else{
					complatUser.setEnable(0);
					Date date=new Date();
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=format.format(date);
					Date createTime = sdf.parse(time);
					complatUser.setCreatetime(createTime);
					complatUserService.save(complatUser);
					fileMap.put("flag", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fileMap.put("error", "1");
		}
		return new ModelAndView("redirect:/complat/complatList");
	}
	
	
	
	/**
	 * 数据导出
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value = "/complatExport", method = RequestMethod.GET)
	public ModelAndView complatExport(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String complatUserId = request.getParameter("complatUserId");
		String[] complatUserIds = complatUserId.split(",");		
		String data = request.getParameter("data");
		//String title = request.getParameter("title");
		String fileName = "政府用户信息统计列表";
		Map<String,Object> map = new HashMap<String,Object>(); 
		List headList = new ArrayList();//表头数据  
        headList.add("用户姓名");
        headList.add("登录名");
        headList.add("登录全名");
        headList.add("手机号码");
        headList.add("邮箱");
        headList.add("账号开启");
        headList.add("注册时间");
		Workbook wb = new XSSFWorkbook();  // 导出 Excel为2007 工作簿对象  
        
		List dataList = new ArrayList();
		for(String iid:complatUserIds){
			ComplatUser complatUser = complatUserService.findByKey(Integer.parseInt(iid));
			TreeMap<String,Object> treeMap = new TreeMap<String, Object>();
			treeMap.put("1", complatUser.getName());
			treeMap.put("2", complatUser.getLoginname());
			treeMap.put("3", complatUser.getLoginallname());
			treeMap.put("4", complatUser.getMobile());
			treeMap.put("5", complatUser.getEmail());	
			int enable = complatUser.getEnable();
			if(enable==0){
				treeMap.put("6","未启用");
			}else{
				treeMap.put("6","已启用");
			}
			
			treeMap.put("7", complatUser.getCreatetime());			
			dataList.add(treeMap);
		}
		map.put(ExcelUtil.HEADERINFO, headList);  
        map.put(ExcelUtil.DATAINFON, dataList); 
        ExcelUtil.writeExcel(map, wb,response,fileName);
        
		return new ModelAndView("redirect:/complat/complatList");
	}				
		
	/**
	 * 用户设置模块,点击用户设置按钮，页面跳转
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */
	@RequestMapping(value="/userSetUpEdit",method = RequestMethod.GET)
	public ModelAndView userSetUpEdit(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ModelAndView mav = new ModelAndView("users/sysview/user_setup");
		try{
			//获取系统当前登录用户
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			String userSid = sysUserSession.getAccountId();
			if(StringHelper.isNotBlack(sysUserSession.getAccountId())){
				
				//查询用户信息
				ComplatUser complatUser = complatUserService.findByKey(Integer.parseInt(userSid));
				model.addAttribute("complatUser",complatUser);
				
				//查询用户身份证号
				JisUserdetail userDetail = jisUserdetailService.findByUserid(Integer.parseInt(userSid));
				model.addAttribute("userDetail",userDetail);
				//根据用户ID查询所属机构
				ComplatGroup complatGroup = complatGroupService.findByIid(complatUser.getGroupid());
				model.addAttribute("complatGroup",complatGroup);
				
				//根据用户ID从ComplatRolerelation获取对应的角色ID，再根据角色ID从ComplatRole中获取对应的角色
				List<ComplatRolerelation> roleRelationList = complatRoleService.findByUserId(Integer.parseInt(userSid));
				List<ComplatRole> roleList = new ArrayList<ComplatRole>();
				for(int i = 0;i < roleRelationList.size();i++){
					Integer roleId = roleRelationList.get(i).getRoleId();
					ComplatRole complatRole = complatRoleService.findByKey(roleId);
					roleList.add(complatRole);
					model.addAttribute("roleList",roleList);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return mav;
	}
	
	
	/**
	 * 保存用户设置
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">yaoxi</a>
	 */
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/userSetUpSave", method = RequestMethod.POST)
	public ModelAndView userSetUpSave(ComplatUser complatUser,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			Integer userId = null;
			if(complatUser != null){
				userId = complatUser.getIid();
				String name = complatUser.getName();
				String pwd = complatUser.getPwd();
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
				JisUserdetail jisUserdetail = jisUserdetailService.findByUserid(userId);
				if(jisUserdetail.getIid() == null){
					jisUserdetailService.save(jisUserdetail);
				}else{
					jisUserdetailService.update(jisUserdetail.getIid(),cardId);
				}
				
			}			
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	/**
	 * 获取用户扩展属性
	 * @author yaoxi
	 */
	public void extendAttr(Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//获取当前用户
		SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		if(StringHelper.isNotBlack(sysUserSession.getAccountId())){
			Integer userId = Integer.parseInt(sysUserSession.getAccountId());
		}
	}
	
	
	
	/**
	 * 批量修改用户启用状态
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/changeUserEnable", method = RequestMethod.GET)
	public ModelAndView changeUserEnable(String complatUserId,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		//int Enable = Integer.parseInt(request.getParameter("Enable"));
		/*Map<String, String> returnMsg = new HashMap<String, String>();
		returnMsg.put("code", "1");*/
		String enable = request.getParameter("Enable");		
		Integer Enable = null;
		int count = 0;
		try {
			if("0".equals(enable)){//未启用状态
				enable="1";
				Enable = Integer.parseInt(enable);
			}
			if("1".equals(enable)){//已启用状态
				enable="0";
				Enable = Integer.parseInt(enable);
			}
			String[] para=complatUserId.split(",");
			ComplatUser complatUser = null;
			for(int i=0;i<para.length;i++){
				Integer corId = Integer.parseInt(para[i].trim());
				complatUser=complatUserService.findByKey(corId);
				if(complatUser.getEnable()==1){
					complatUser.setEnable(0); 
					//complatUserService.changeComplatEnable(corId,Enable);
					complatUserService.save(complatUser);
					returnMsg("success", "停用成功", request);
					count++;
				}else if(complatUser.getEnable()==0){
					complatUser.setEnable(1); 
					//complatUserService.changeComplatEnable(corId,Enable);
					complatUserService.save(complatUser);
					returnMsg("success", "启用成功", request);
					count++;
				}
			}			
		}catch(Exception e){
			e.printStackTrace();			
		} finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}			
	}
	
	
	
	/**
	 * 启用--单条记录
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/EnableStart", method = RequestMethod.GET)
	public ModelAndView EnableStart(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatUser complatUser = null;
		try{			
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer.parseInt(iid));
			int enable = complatUser.getEnable(); 
				if(enable==0){
					enable=1;
					complatUser.setEnable(enable);
					complatUserService.save(complatUser);
					returnMsg("success", "启用成功！", request);				
				}
			}								
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	
	
	/**
	 * 停用--单条记录
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/EnableStop", method = RequestMethod.GET)
	public ModelAndView EnableStop(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatUser complatUser = null;
		try{			
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer.parseInt(iid));
				int enable = complatUser.getEnable(); 
				if(enable==1){
					enable=0;
					complatUser.setEnable(enable);
					complatUserService.save(complatUser);
					returnMsg("success", "停用成功！", request);
				}
			}								
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
	
	
	
	
	/**
	 * 删除政府用户--单条记录
	 *@param complatUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <a href=" ">shenxh</a>
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/deleteComplatUser", method = RequestMethod.GET)
	public ModelAndView deleteComplatUser(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatUser complatUser = null;
		try{
			if (StringHelper.isNotBlack(iid)) {
				complatUser = complatUserService.findByKey(Integer.parseInt(iid));
				if(complatUser != null){
					complatUserService.delete(complatUser);
					returnMsg("success", "删除成功", request);
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return  new ModelAndView("redirect:/complat/complatList");
		}
		
	}
}


