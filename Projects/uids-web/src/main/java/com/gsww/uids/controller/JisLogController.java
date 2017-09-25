package com.gsww.uids.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.JisLogService;

/**
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : jup-core
 * </p>
 * <p>
 * 创建时间 : 2017-09-12 下午14:30:23
 * </p>
 * <p>
 * 类描述 : 在线用户模块controller层
 * </p>
 * 
 * 
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Controller
@RequestMapping(value = "/jisLog")
public class JisLogController extends BaseController {

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private JisLogService jisLogService;
	@Autowired
	private SysParaService sysParaService;

	public JisLogService getJisLogService() {
		return jisLogService;
	}

	public void setJisLogService(JisLogService jisLogService) {
		this.jisLogService = jisLogService;
	}

	@RequestMapping(value = "/countUser", method = RequestMethod.GET)
	public String countUser(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			Model model, ServletRequest request) {
		try {
			Page<Map<String, String>> pageInfo = jisLogService.getJisLogPage(
					pageNo, pageSize, this.getSearchCondition(request));
			model.addAttribute("pageInfo", pageInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/jisLog/countUserList";
		}
		return "users/jisLog/countUser_List";
	}

	/**
	 * 
	 * 拼装查询条件
	 */
	public List<String> searchConditionInstal(String name, String value,
			String type) {

		List<String> conditionList = new ArrayList<String>();
		conditionList.add(name);
		conditionList.add(value);
		conditionList.add(type);
		return conditionList;
	}

	/**
	 * 获取查询条件
	 * 
	 * @param request
	 * @return
	 */
	public List<List<String>> getSearchCondition(ServletRequest request) {

		List<List<String>> searchList = new ArrayList<List<String>>();

		return searchList;
	}

	/**
	 * 获取日志列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logList", method = RequestMethod.GET)
	public String logList(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "spec") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model, ServletRequest request, HttpServletRequest hrequest) {
		try {
			// 初始化分页数据
			// 初始化分页数据
			PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField,
					orderSort);
			PageRequest pageRequest = super.buildPageRequest(hrequest,
					pageUtils, JisLog.class, findNowPage);

			// 搜索属性初始化
			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			Specification<JisLog> spec = super.toSpecification(searchParams,
					JisLog.class);

			// 分页
			Page<JisLog> pageInfo = jisLogService.getJisLogPage(spec,
					pageRequest);
			model.addAttribute("pageInfo", pageInfo);
			// 测试获取结果
			model.addAttribute("czlxList", sysParaService.getParaList("czlx"));
			model.addAttribute("mkmcList", sysParaService.getParaList("mkmc"));

			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets
					.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/uids/logList";
		}
		return "users/sysview/jis_log_list";
	}
}
