package com.gsww.uids.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.JisFields;
import com.gsww.uids.service.JisFieldsService;

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
			ex.printStackTrace();
			logger.error("列表打开失败：" + ex.getMessage());
			returnMsg("error", "列表打开失败", (HttpServletRequest) request);
			return "redirect:/jis/fieldsList";
		}
		return "system/jis/fields_list";
	}

}
