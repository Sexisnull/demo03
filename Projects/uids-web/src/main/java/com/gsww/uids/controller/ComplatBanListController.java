package com.gsww.uids.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gsww.uids.entity.ComplatBanlist;
import com.gsww.uids.service.ComplatBanListService;
import com.gsww.uids.service.JisLogService;

@Controller
@RequestMapping(value = "/uids")
public class ComplatBanListController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ComplatBanListController.class);
	@Autowired
	private ComplatBanListService complatBanListService;
	@Autowired
	private JisLogService jisLogService;
	
	@RequestMapping(value = "/complatBanList", method = RequestMethod.GET)
	public String complatBanList(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "logindate") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			// 初始化分页数据
			PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField, orderSort);
			PageRequest pageRequest = super.buildPageRequest(hrequest, pageUtils, ComplatBanlist.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatBanlist> spec = super.toSpecification(searchParams, ComplatBanlist.class);
			
			// 分页
			Page<ComplatBanlist> pageInfo = complatBanListService.getComplatBanPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/uids/complatBanList";
		}
		return "users/complat/complatban_list";
	}
	
	/**
	 * 删除信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatbanlistDelete", method = RequestMethod.GET)
	public ModelAndView complatbanlistDelete(String iid, HttpServletRequest request,HttpServletResponse response)  throws Exception {
		 SysUserSession session = (SysUserSession) request.getSession().getAttribute("sysUserSession");
		try {
			String[] para=iid.split(",");
			ComplatBanlist complatBanlist = null;
			for(int i=0;i<para.length;i++){
				Integer Iid = Integer.parseInt(para[i].trim());
				complatBanlist=complatBanListService.findByIid(Iid);
				complatBanListService.delete(complatBanlist);
				 String desc=session.getUserName()+"删除了"+complatBanlist.getLoginname();
	                jisLogService.save(session.getUserName(),session.getUserIp(), desc, 3, 1);
							}
			returnMsg("success","删除成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);		
		} finally{
			return  new ModelAndView("redirect:/uids/complatBanList");
		}
		
	}
}
