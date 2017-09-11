package com.gsww.uids.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.entity.OutsideUser;
import com.gsww.uids.service.OutsideUserService;

/**
 * Title: OutsideUserController.java Description: 个人用户控制层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:48:51
 */
@Controller
@RequestMapping(value = "/complat")
public class OutsideUserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(OutsideUserController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private OutsideUserService outsideUserService;

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
			PageRequest pageRequest = super.buildPageRequest(hrequest, pageUtils, OutsideUser.class, findNowPage);

			// 搜索属性初始化
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<OutsideUser> spec = super.toNewSpecification(searchParams, OutsideUser.class);

			// 分页
			Page<OutsideUser> pageInfo = outsideUserService.getOutsideUserPage(spec, pageRequest);
			model.addAttribute("pageInfo", pageInfo);

			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);

		} catch (Exception ex) {
			ex.printStackTrace();
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
			OutsideUser outsideUser = null;
			if (StringHelper.isNotBlack(outsideuserId)) {
				outsideUser = outsideUserService.findByKey(Integer.parseInt(outsideuserId));
				Date createTime = outsideUser.getCreateTime();
				String time = sdf.format(createTime);
				model.addAttribute("time", time);
			} else {
				outsideUser = new OutsideUser();
			}
			model.addAttribute("outsideUser", outsideUser);
		} catch (Exception e) {
			e.printStackTrace();
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
	public ModelAndView accountSave(OutsideUser outsideUser, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (outsideUser != null) {
				String iidStr = String.valueOf(outsideUser.getIid());
				System.out.println(iidStr);
				if (iidStr == "null" || iidStr.length() <= 0) {
					Date d = new Date(); 
					outsideUser.setEnable(1); // 是否禁用
					outsideUser.setAuthState(0); // 审核状态
					outsideUser.setIsAuth(0); // 是否审核
					outsideUser.setCreateTime(d);//创建时间
					outsideUserService.save(outsideUser);
					returnMsg("success", "保存成功", request);
				} else {
					//注册时间
					String time = request.getParameter("time");
					Date createTime = sdf.parse(time);
					outsideUser.setCreateTime(createTime);
					outsideUserService.save(outsideUser);
					returnMsg("success", "编辑成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			String[] para = corporationId.split(",");
			OutsideUser outsideUser = null;
			for (int i = 0; i < para.length; i++) {
				Integer corId = Integer.parseInt(para[i].trim());
				outsideUser = outsideUserService.findByKey(corId);
				if (outsideUser != null) {
					outsideUserService.delete(outsideUser);
					returnMsg("success", "删除成功", request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败", request);
		} finally {
			return new ModelAndView("redirect:/complat/outsideuserList");
		}
	}
}
