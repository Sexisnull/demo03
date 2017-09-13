package com.gsww.uids.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

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
import com.gsww.jup.util.ExcelUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.CheckExcelService;
import com.gsww.uids.service.ComplatUserService;

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
	private CheckExcelService checkExcelService;
	
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
	 * 删除用户信息
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
	@RequestMapping("/downloadComplaUserTemplate")
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
	
	
	/**
	 * 数据导入
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatImport", method = RequestMethod.POST)
	public String complatImport(@RequestParam("filename") MultipartFile file,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String Msg = "";	
		String fileName = file.getOriginalFilename(); // 获取文件名
		long maxSize = 1024 * 1024 * 10; // 允许最大上传文件大小

		// 判断文件大小、即名称
		long size = file.getSize();
		if (fileName == null || ("").equals(fileName) && size == 0) {
			Msg = "文件内容为空，请选择合适的文件！";
			model.addAttribute("msg", Msg);
			return "users/complat/account_list";
		}
		if (size > maxSize) {
			Msg = "上传文件大小超过10M，请选择合适的文件！";
			model.addAttribute("msg", Msg);
			return "users/complat/account_list";
		}
		// 获取项目根目录
		String ctxPath = request.getSession().getServletContext().getRealPath("");
		String modelFileName = "政府用户信息统计列表.xlsx";

		// 获取模板文件路径
		String modelPath = ctxPath + "/uploadFile/complat/" + modelFileName;
		// 读取 excel 文件名
		File modelFile = new File(modelPath);	 
		// 上传文件表头和模板表头做对比
		/**
		 * 返回值： 0--> 检验成功 1--> 文件名不合适 2--> 文件表头不匹配
		 */
		int j = checkExcelService.compareModelAndReal(modelFileName, modelFile,
				fileName, file);
		if (j == 0) {
			try {
				// 把文件转换成字节流形式
				// InputStream in = file.getInputStream();
				LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
				 fieldMap.put("0", "用户姓名");
				 fieldMap.put("1", "年龄");
				 fieldMap.put("2", "性别");
				 fieldMap.put("3", "用户职务");
				 fieldMap.put("4", "传真");
				 fieldMap.put("5", "固定电话");
				 fieldMap.put("6", "移动电话");
				 fieldMap.put("7", "E-mail");
				 fieldMap.put("8", "MSN");
				 fieldMap.put("9", "QQ");
				 fieldMap.put("10", "地址");
				 fieldMap.put("11", "邮政编码");
				 fieldMap.put("12", "登录名");
				 fieldMap.put("13", "登录全名");
				 fieldMap.put("14", "密码");
				 fieldMap.put("15", "密码找回问题");
				 fieldMap.put("16", "密码找回问题答案");
				 List<ComplatUser> resultList = ExcelUtil.readXls(fileName, ComplatUser.class, fieldMap);
				if (fieldMap.get("err_null") != null) {
					Msg = "EXCEL数据为空！";
					model.addAttribute("msg", Msg);
					return "users/complat/account_list";
				} else if (fieldMap.get("err_row") != null) {
						Msg = "第" + fieldMap.get("err_row") + "行，第"
								+ fieldMap.get("err_cell") + "列数据有误，批量导入EXCEL数据失败！";
					model.addAttribute("msg", Msg);
					return "users/complat/account_list";
				} else {
					Msg = "批量导入EXCEL数据成功,共" + fieldMap.get("num") + "条！";
					model.addAttribute("msg", Msg);
					return "users/complat/account_list";
				}
			} catch (IOException e) {
				e.printStackTrace();
				Msg = "批量导入EXCEL数据失败！";
				model.addAttribute("msg", Msg);
				return "users/complat/account_list";
			}
		} else if (j == 1) {
			Msg = "导入的文件不是Excel文件！";
			model.addAttribute("msg", Msg);
			return "users/complat/account_list";
		} else if (j == 2) {
			Msg = "导入的文件表头和模板不匹配！";
			model.addAttribute("msg", Msg);
			return "users/complat/account_list";
		} else {
			Msg = "导入文件失败！";
			model.addAttribute("msg", Msg);
			return "users/complat/account_list";
		}				
		
	}
	
	
	
}


