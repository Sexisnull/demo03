package com.gsww.uids.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gsww.uids.service.JisUserdetailService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.JisFields;
import com.gsww.uids.service.JisFieldsService;
import com.gsww.uids.service.JisLogService;

/**
 * Title: JisFieldsController.java Description: 用户扩展属性controller
 * 
 * @author yangxia
 * @created 2017年9月12日 下午5:33:05
 */
@Controller
@RequestMapping(value = "/jis")
public class JisFieldsController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(JisFieldsController.class);

	@Autowired
	private JisFieldsService jisFieldsService;
	@Autowired
	private JisUserdetailService jisUserdetailService;
	@Autowired
	private JisLogService jisLogService;

	/**
	 * @discription 用户扩展属性展示列表
	 * @param pageNo
	 * @param pageSize
	 * @param orderField
	 * @param orderSort
	 * @param findNowPage
	 * @param model
	 * @param request
	 * @param hrequest
	 * @return
	 */
	@RequestMapping(value = "/fieldsList", method = RequestMethod.GET)
	public String fieldsList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "iid") String orderField,
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
			PageRequest pageRequest = super.buildPageRequest(hrequest, pageUtils, JisFields.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<JisFields> spec = super.toNewSpecification(searchParams, JisFields.class);

			// 分页
			Page<JisFields> pageInfo = jisFieldsService.getJisFieldsPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
            
		} catch (Exception ex) {
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
		}
		return "system/jis/fields_list";
	}

	/**
	 * @discription 跳转到新增或编辑页面
	 * @param outsideuserId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fieldsEdit", method = RequestMethod.GET)
	public String accountEdit(String fieldsId, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			JisFields jisFields = null;
			if (StringHelper.isNotBlack(fieldsId)) {
				jisFields = jisFieldsService.findByKey(Integer.parseInt(fieldsId));
			} else {
				jisFields = new JisFields();
			}
			model.addAttribute("jisFields", jisFields);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "system/jis/fields_edit";
	}

	/**
	 * @discription 用户扩展属性保存
	 * @param outsideUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/fieldsSave", method = RequestMethod.POST)
	public ModelAndView accountSave(JisFields jisFields, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			if (jisFields != null) {
				String iidStr = String.valueOf(jisFields.getIid());
				if (iidStr == "null" || iidStr.length() <= 0) { // 新增
					jisFields.setIssys(1);//是否为系统字段
					jisFields.setIswrite(0);//是否必填
					jisFieldsService.save(jisFields);
					jisUserdetailService.addUserField(jisFields.getFieldname());
					returnMsg("success", "保存成功", request);
					String desc = sysUserSession.getLoginAccount() + "新增 【" + jisFields.getShowname() + "】 用户扩展属性"; 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,6,1);
				} else { // 编辑
					int type = jisFields.getType();
					if (type == 1) {//字符串
						jisFields.setDefvalue("");
						jisFields.setFieldkeys("");
						jisFields.setFieldvalues("");
					} 
					if (type == 2) {//枚举值
						jisFields.setDefvalue("");
						String keys = StringUtils.substringBeforeLast(jisFields.getFieldkeys(), ",");
						String values = StringUtils.substringBeforeLast(jisFields.getFieldvalues(), ",");
						jisFields.setFieldkeys(keys);
						jisFields.setFieldvalues(values);
					}
					if (type == 3) {
						jisFields.setFieldkeys("");
						jisFields.setFieldvalues("");
						String defvalue = StringUtils.substringBeforeLast(jisFields.getDefvalue(), ",");
						jisFields.setDefvalue(defvalue);
					}
					jisFieldsService.save(jisFields);
					returnMsg("success", "编辑成功", request);
					String desc = sysUserSession.getLoginAccount() + "修改 【" + jisFields.getShowname() + "】 用户扩展属性"; 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,6,2);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMsg("error", "保存失败", request);
		} finally {
			return new ModelAndView("redirect:/jis/fieldsList");
		}
	}

	/**
	 * @discription 用户扩展属性删除
	 * @param corporationId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/fieldsDelete", method = RequestMethod.GET)
	public ModelAndView accountDelete(String fieldsId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			String[] para = fieldsId.split(",");
			JisFields jisFields = null;
			for (int i = 0; i < para.length; i++) {
				Integer iid = Integer.parseInt(para[i].trim());
				jisFields = jisFieldsService.findByKey(iid);
				if (jisFields != null) {
					String desc = sysUserSession.getLoginAccount() + "删除 【" + jisFields.getShowname() + "】 用户扩展属性"; 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,6,3);
					jisFieldsService.delete(jisFields);
					jisUserdetailService.delUserField(jisFields.getFieldname());
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMsg("error", "删除失败", request);
		} finally {
			return new ModelAndView("redirect:/jis/fieldsList");
		}
	}
	/**
     * @discription   设置是否必填项 
     * @param fieldiid
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	@RequestMapping(value = "/fieldsOperate", method = RequestMethod.GET)
	public String fieldsOperate(String fieldiid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUserSession sysUserSession =  (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			Integer iswrite = 0;//0：非必填；1：必填
			List<JisFields> jList = jisFieldsService.findAllJisFields();
			for (JisFields jisFields : jList) {
				jisFields.setIswrite(iswrite);
				jisFieldsService.save(jisFields);
				if (fieldiid == null) {
					String desc = sysUserSession.getLoginAccount() + "设置 【" + jisFields.getShowname() + "】 用户扩展属性非必填项"; 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,6,8);
				}
			}
			if(fieldiid != null) {
				String[] para = fieldiid.split(",");
				for (int i = 0; i < para.length; i++) {
					Integer iid = Integer.parseInt(para[i].trim());
					JisFields jisFields = jisFieldsService.findByKey(iid);
					jisFields.setIswrite(1);
					jisFieldsService.save(jisFields);
					returnMsg("success", "设置成功", request);
					String desc = sysUserSession.getLoginAccount() + "设置 【" + jisFields.getShowname() + "】 用户扩展属性必填项"; 
					jisLogService.save(sysUserSession.getLoginAccount(),sysUserSession.getUserIp(),desc,6,8);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMsg("error", "设置失败", request);
		}
		return "redirect:/jis/fieldsList";
	}
	
	/**
     * @discription    字段名称重命名校验
     * @param fieldname
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value = "/checkFieldname", method = RequestMethod.GET)
	public void checkFieldname(String fieldname,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String fieldnameInput = StringUtils.trim((String) request.getParameter("fieldname"));
			String oldFieldname = StringUtils.trim((String) request.getParameter("oldFieldname"));
			if(!fieldnameInput.equals(oldFieldname)){
				List<JisFields> jisFields = jisFieldsService.findByFieldname(fieldname);
				if(!jisFields.isEmpty()){					
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
     * @discription    用户扩展属性设置弹框
     * @param model
     * @param request
     * @param response
     * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/goFieldsSetting" }, method = {RequestMethod.GET })
	public String goFieldsSetting(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置项
			List<JisFields> jisFieldsList = jisFieldsService.findAllJisFields();
			model.addAttribute("jisFieldsList", jisFieldsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return "system/jis/fields_setting";
		}
	}
	
	/**
     * @discription    显示名称重名校验
     * @param fieldname
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value = "/checkShowname", method = RequestMethod.GET)
	public void checkShowname(String showname,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String shownameInput = StringUtils.trim((String) request.getParameter("showname"));
			String oldShowname = StringUtils.trim((String) request.getParameter("oldShowname"));
			if(!shownameInput.equals(oldShowname)){
				List<JisFields> jisFields = jisFieldsService.findByShowname(showname);
				if(!jisFields.isEmpty()){					
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
}
