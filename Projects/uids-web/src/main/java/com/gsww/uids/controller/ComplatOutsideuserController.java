package com.gsww.uids.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.service.JisLogService;
import com.hanweb.common.util.Md5Util;

import net.sf.json.JSONObject;

/**
 * Title: OutsideUserController.java Description: 个人用户控制层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:48:51
 */
@Controller
@RequestMapping(value = "/complat")
public class ComplatOutsideuserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ComplatOutsideuserController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ComplatOutsideuserService outsideUserService;
	@Autowired
	private JisLogService jisLogService;

	@RequestMapping(value = "/outsideuserList", method = RequestMethod.GET)
	public String accountList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createTime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage, Model model,
			ServletRequest request, HttpServletRequest hrequest) {
		try {
			if (StringUtils.isNotBlank(request.getParameter("orderField"))) {
				orderField = (String) request.getParameter("orderField");
			}
			if (StringUtils.isNotBlank(request.getParameter("orderSort"))) {
				orderSort = (String) request.getParameter("orderSort");
			}
			// 初始化分页数据
			PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField, orderSort);
			PageRequest pageRequest = super.buildPageRequest(hrequest, pageUtils, ComplatOutsideuser.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("NE_operSign", 3);
			Specification<ComplatOutsideuser> spec = super.toNewSpecification(searchParams, ComplatOutsideuser.class);

			// 分页
			Page<ComplatOutsideuser> pageInfo = outsideUserService.getOutsideUserPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/complat/outsideuserList";
		}
		return "users/outsideUser/outsideUser_list";
	}

	/**
	 * @discription 个人用户新增或编辑
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outsideuserEdit", method = RequestMethod.GET)
	public String accountEdit(String outsideuserId, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ModelAndView mav=new
		// ModelAndView("users/outsideUser/outsideUser_edit");
		try {
			ComplatOutsideuser outsideUser = null;
			if (StringHelper.isNotBlack(outsideuserId)) {
				outsideUser = outsideUserService.findByKey(Integer.parseInt(outsideuserId));
				Date createTime = outsideUser.getCreateTime();
				String time = sdf.format(createTime);
				String pwdString = outsideUser.getPwd();
				String pwd = Md5Util.md5decode(pwdString);//解密
				outsideUser.setPwd(pwd);
				model.addAttribute("time", time);
			} else {
				outsideUser = new ComplatOutsideuser();
			}
			model.addAttribute("outsideUser", outsideUser);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "users/outsideUser/outsideUser_edit";
	}

	/**
	 * @discription 个人用户保存
	 * @param outsideUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/outsideuserSave", method = RequestMethod.POST)
	public ModelAndView accountSave(ComplatOutsideuser outsideUser, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			if (outsideUser != null) {
				String iidStr = String.valueOf(outsideUser.getIid());
				if (iidStr == "null" || iidStr.length() <= 0) {
					Date d = new Date(); 
					outsideUser.setEnable(1); // 是否禁用
					outsideUser.setAuthState(0); // 审核状态
					outsideUser.setIsAuth(0); // 是否审核
					outsideUser.setCreateTime(d);//创建时间
					outsideUser.setOperSign(1);//更新操作状态
					String pwdString = outsideUser.getPwd();
					String pwd = Md5Util.md5encode(pwdString);//加密
					outsideUser.setPwd(pwd);
					outsideUserService.save(outsideUser);
					returnMsg("success", "保存成功", request);
					String desc = sysUserSession.getUserName() + "新增个人用户:" + outsideUser.getName(); 
					jisLogService.save(sysUserSession.getUserName(),sysUserSession.getUserIp(),desc,10,1);
				} else {
					//注册时间
					String time = request.getParameter("time");
					Date createTime = sdf.parse(time);
					outsideUser.setCreateTime(createTime);//转换保存创建时间
					outsideUser.setOperSign(2);//更新操作状态
					String pwdString = outsideUser.getPwd();
					String pwd = Md5Util.md5encode(pwdString);
					outsideUser.setPwd(pwd);
					outsideUserService.save(outsideUser);
					returnMsg("success", "编辑成功", request);
					String desc = sysUserSession.getUserName() + "修改个人用户:" + outsideUser.getName(); 
					jisLogService.save(sysUserSession.getUserName(),sysUserSession.getUserIp(),desc,10,2);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMsg("error", "保存失败", request);
		} finally {
			return new ModelAndView("redirect:/complat/outsideuserList");
		}
	}

	/**
	 * @discription 个人用户删除
	 * @param corporationId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/outsideuserDelete", method = RequestMethod.GET)
	public ModelAndView accountDelete(String corporationId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try {
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			String[] para = corporationId.split(",");
			for (int i = 0; i < para.length; i++) {
				Integer corId = Integer.parseInt(para[i].trim());
				outsideUserService.delete(corId);
				returnMsg("success", "删除成功", request);
				complatOutsideuser = outsideUserService.findByKey(corId);
				String desc = sysUserSession.getUserName() + "删除了姓名为：" + complatOutsideuser.getName() +"的个人用户"; 
				jisLogService.save(sysUserSession.getUserName(),sysUserSession.getUserIp(),desc,10,3);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMsg("error", "删除失败", request);
		} finally {
			return new ModelAndView("redirect:/complat/outsideuserList");
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
	@RequestMapping(value = "/outsideuserStart", method = RequestMethod.GET)
	public ModelAndView outsideuserStart(String outsideuserIid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try{			
			if (StringHelper.isNotBlack(outsideuserIid)) {
				complatOutsideuser = outsideUserService.findByKey(Integer.parseInt(outsideuserIid));
			Integer enable = complatOutsideuser.getEnable(); 
				if(enable == 0){
					complatOutsideuser.setEnable(1);
					outsideUserService.save(complatOutsideuser);
					returnMsg("success", "开启成功！", request);				
				} else {
					returnMsg("success", "账号已开启！", request);
				}
			}								
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			return  new ModelAndView("redirect:/complat/outsideuserList");
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
	@RequestMapping(value = "/outsideuserStop", method = RequestMethod.GET)
	public ModelAndView outsideuserStop(String outsideuserIid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try{			
			if (StringHelper.isNotBlack(outsideuserIid)) {
				complatOutsideuser = outsideUserService.findByKey(Integer.parseInt(outsideuserIid));
			Integer enable = complatOutsideuser.getEnable(); 
				if(enable == 1){
					complatOutsideuser.setEnable(0);
					outsideUserService.save(complatOutsideuser);
					returnMsg("success", "关闭成功！", request);				
				} else {
					returnMsg("success", "账号已关闭！", request);
				}
			}								
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			return  new ModelAndView("redirect:/complat/outsideuserList");
		}
	}
	
	/**
     * @discription  批量开启账号  
     * @param iid
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/outsideuserOperatrStart", method = RequestMethod.GET)
	public ModelAndView outsideuserOperatrStart(String outsideuserOperatorId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try{
			String[] para = outsideuserOperatorId.split(",");
			for (int i = 0; i < para.length; i++) {
				Integer corId = Integer.parseInt(para[i].trim());
				complatOutsideuser = outsideUserService.findByKey(corId);
				complatOutsideuser.setEnable(1);
				outsideUserService.save(complatOutsideuser);
			}
			returnMsg("success", "开启成功！", request);				
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			returnMsg("error", "开启失败！", request);
		}finally{
			return  new ModelAndView("redirect:/complat/outsideuserList");
		}
	}
	
	/**
     * @discription   批量关闭账号
     * @param iid
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/outsideuserOperatorStop", method = RequestMethod.GET)
	public ModelAndView outsideuserOperatorStop(String outsideuserOperatorId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try{			
			String[] para = outsideuserOperatorId.split(",");
			for (int i = 0; i < para.length; i++) {
				Integer corId = Integer.parseInt(para[i].trim());
				complatOutsideuser = outsideUserService.findByKey(corId);
				complatOutsideuser.setEnable(0);
				outsideUserService.save(complatOutsideuser);
			}
			returnMsg("success", "关闭成功！", request);								
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			returnMsg("error", "关闭失败！", request);
		}finally{
			return  new ModelAndView("redirect:/complat/outsideuserList");
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
	@RequestMapping(value = "/outsideuserAuth", method = RequestMethod.GET)
	public ModelAndView outsideuserAuth(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ComplatOutsideuser complatOutsideuser = null;
		try{
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			String iid = StringUtils.trim((String) request.getParameter("iid"));
			String outsideUserType = StringUtils.trim((String) request.getParameter("outsideUserType"));
			String rejectReason2 = StringUtils.trim((String) request.getParameter("rejectReason2"));
			int type = Integer.parseInt(outsideUserType);//1:通过  2：拒绝
			complatOutsideuser = outsideUserService.findByKey(Integer.parseInt(iid));
			if(type == 1) {
				int isAuth = complatOutsideuser.getIsAuth();
				if (isAuth == 0) {
					//认证接口调用
					complatOutsideuser.setIsAuth(1);
					complatOutsideuser.setAuthState(1);
					outsideUserService.save(complatOutsideuser);
					returnMsg("success", "用户认证成功！", request);
					String desc = sysUserSession.getUserName() + "对个人用户:" + complatOutsideuser.getName() + "认证通过"; 
					jisLogService.save(sysUserSession.getUserName(),sysUserSession.getUserIp(),desc,10,12);
				} else {
					returnMsg("success", "用户已认证！", request);
				}
				
			} else if (type == 0) {
				complatOutsideuser.setIsAuth(0);
				complatOutsideuser.setAuthState(2);
				if (rejectReason2 != null) {
					complatOutsideuser.setRejectReason(rejectReason2);
				}
				outsideUserService.save(complatOutsideuser);
				returnMsg("success", "用户认证已拒绝！", request);
				String desc = sysUserSession.getUserName() + "对个人用户:" + complatOutsideuser.getName() + "拒绝认证"; 
				jisLogService.save(sysUserSession.getUserName(),sysUserSession.getUserIp(),desc,10,12);
			} 
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			returnMsg("error", "认证失败！", (HttpServletRequest) request);
		}finally{
			return  new ModelAndView("redirect:/complat/outsideuserList");
		}
	}
	
	/**
     * @discription    获取认证用户信息
     * @param request
     * @param response
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/getOutsideuserInfo" }, method = {RequestMethod.GET })
	public String getOutsideuserInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			String pidStr = request.getParameter("iid");
			Integer pid = Integer.valueOf(Integer.parseInt(pidStr));
			ComplatOutsideuser complatOutsideuser = outsideUserService.findByKey(pid);
			if (complatOutsideuser != null) {
				model.addAttribute("complatOutsideuser", complatOutsideuser);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return "users/outsideUser/outsideUser_auth";
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
	@RequestMapping(value="/checkOutisideUserLoginName", method = RequestMethod.GET)
	public void checkLoginName(String loginName,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatOutsideuser complatOutsideuser = null;
			String loginNameInput=StringUtils.trim((String)request.getParameter("loginName"));
			String oldLoginName=StringUtils.trim((String)request.getParameter("oldLoginName"));
			if(!loginNameInput.equals(oldLoginName)){
				complatOutsideuser = outsideUserService.findByLoginNameIsUsed(loginName);
				if(complatOutsideuser!=null){					
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
     * @discription    手机号重名校验
     * @param mobile
     * @param model
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value="/checkOutisideUserMobile", method = RequestMethod.GET)
	public void checkOutisideUserMobile(String mobile,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatOutsideuser complatOutsideuser = null;
			String mobileInput=StringUtils.trim((String)request.getParameter("mobile"));
			String oldMobile=StringUtils.trim((String)request.getParameter("oldMobile"));
			if(!mobileInput.equals(oldMobile)){
				complatOutsideuser = outsideUserService.findByMobile(mobile);
				if(complatOutsideuser!=null){					
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
	@RequestMapping(value="/checkOutisideUserPapersNumber", method = RequestMethod.GET)
	public void checkOutisideUserPapersNumber(String papersNumber,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			ComplatOutsideuser complatOutsideuser = null;
			String papersNumberInput=StringUtils.trim((String)request.getParameter("papersNumber"));
			String oldPapersNumber=StringUtils.trim((String)request.getParameter("oldPapersNumber"));
			if(!papersNumberInput.equals(oldPapersNumber)){
				complatOutsideuser = outsideUserService.findByIdCard(papersNumber);
				if(complatOutsideuser!=null){					
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
     * @discription    认证调用接口实现
     * @param verifyMode 认证模式
     * @param syscode 分配给的系统代码
     * @param token token
     * @param idCard  身份证号码
     * @param name 姓名
     * @return
	 */
	public static String sendAuthInfo(Integer verifyMode, String syscode, String token, String idCard, String name) {
		HttpClient httpclient = new DefaultHttpClient();
		String gwyverifyUrl = "http://localhost:8080/gov-apis/api/gwy/verify/gwyverify/" + verifyMode + "/" + syscode + "/" + token;
		HttpPost httppost = new HttpPost(gwyverifyUrl);
		String strResult = "";
		try {  
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
            JSONObject jobj = new JSONObject();  
            jobj.put("id", idCard);  
            jobj.put("name", name);  
            nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));  
            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));  
              
            HttpResponse response = httpclient.execute(httppost);  
            if (response.getStatusLine().getStatusCode() == 200) {  
                /*读返回数据*/  
                String conResult = EntityUtils.toString(response  
                        .getEntity());  
                JSONObject sobj = new JSONObject();  
                sobj = JSONObject.fromObject(conResult);  
                String result = sobj.getString("result");  
                String code = sobj.getString("code");  
                if(result.equals("1")){  
                    strResult += "发送成功";  
                }else{  
                    strResult += "发送失败，"+code;  
                }  
                  
            } else {  
                String err = response.getStatusLine().getStatusCode()+"";  
                strResult += "发送失败:"+err;  
            }  
    } catch (ClientProtocolException e) {  
    	logger.error(e.getMessage(), e);  
    } catch (IOException e) {  
    	logger.error(e.getMessage(), e);  
    }  
    return strResult;
	}
	
	
	/**
     * @discription    字符串转json
     * @param adata
     * @return
	 */
	private static String getStringFromJson(JSONObject adata) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("{");  
        for(Object key:adata.keySet()){  
            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
        }  
        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
        return rtn;  
    }  
}
