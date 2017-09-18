package com.gsww.uids.controller;

import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.util.PageUtils;
import com.gsww.uids.entity.ComplatZone;
import com.gsww.uids.service.ComplatZoneService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: ComplatZoneController.java Description: 区域管理控制层
 * 
 * @author yangxia
 * @created 2017年9月15日 上午11:23:43
 */
@Controller
@RequestMapping(value = "/complat")
public class ComplatZoneController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ComplatZoneController.class);

	@Autowired
	private ComplatZoneService complatZoneService;

	private static final String PAGE_SIZE = "10";
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private String alertType = "";

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("checkTime", "标题");
	}

	private ComplatZone complatZone;

	public ComplatZone getComplatZone() {
		return complatZone;
	}

	public void setComplatZone(ComplatZone complatZone) {
		this.complatZone = complatZone;
	}

	@RequestMapping(value = "/zoneList1", method = RequestMethod.GET)
	public String zoneList(ServletRequest request, HttpServletRequest hrequest) {

		return "system/area/zone_list";
	}

	/**
	 * @discription 区域列表
	 * @param pageNo
	 * @param pageSize
	 * @param orderField
	 * @param orderSort
	 * @param findNowPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/zoneList", method = RequestMethod.GET)
	public ModelAndView tableList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "iid") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage, Model model,
			ServletRequest request) {
		try {
			try {
				// 初始化分页数据
				PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField, orderSort);
				PageRequest pageRequest = super.buildPageRequest((HttpServletRequest) request, pageUtils,
						ComplatZone.class, findNowPage);

				// 搜索属性初始化
				Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
				Specification<ComplatZone> spec = super.toSpecification(searchParams, ComplatZone.class);

				// 分页
				Page<ComplatZone> pageInfo = this.complatZoneService.getPage(spec, pageRequest);
				/* setName(pageInfo.getContent()); */
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("alertType", alertType);
				alertType = "";
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("system/area/zone_list");
	}

	/**
	 * @discription 获取区域树信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/zoneTree", method = RequestMethod.GET)
	public void zoneTree(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession()
					.getAttribute("sysUserSession");
			List<ComplatZone> list = complatZoneService.getAll();
			List<Map<String, String>> treeList = new ArrayList<Map<String, String>>();
			if (list != null && !list.isEmpty()) {
				for (ComplatZone complatZone : list) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(complatZone.getIid()));
					map.put("pId", String.valueOf(complatZone.getPid()));
					map.put("name", complatZone.getName());
					map.put("title", complatZone.getName());
					map.put("tld", String.valueOf(complatZone.getIid()));
					map.put("viewtype", "1");
					map.put("regiontype", "1");
					map.put("open", "true");
					map.put("seq", String.valueOf(complatZone.getType()));
					treeList.add(map);
				}
			}
			JSONArray array = JSONArray.fromObject(treeList);
			PrintWriter out = response.getWriter();
			String json = array.toString();
			out.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/zoneTree", method = RequestMethod.POST)
	public void zoneTreePost(HttpServletRequest request, HttpServletResponse response) {
		this.zoneTree(request, response);
	}

	/**
	 * @discription 区域重命名
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateName", method = RequestMethod.POST)
	public void updateName(HttpServletRequest request, HttpServletResponse response) {
		try {
			String iid = request.getParameter("key");
			ComplatZone complatZone = complatZoneService.fingByKey(Integer.parseInt(iid));
			String newName = request.getParameter("newName");
			String oldName = complatZone.getName();
			if (oldName.equals(newName)) {
				response.getWriter().write("success");
			} else {
				response.getWriter().write("success");
				complatZone.setName(newName);
				complatZoneService.save(complatZone);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(e.getLocalizedMessage());
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * 异步获取区域的完整信息并以json的格式返回
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getZoneInfo", method = RequestMethod.POST)
	public void getZoneInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String iidStr = request.getParameter("key");
			Integer iid = Integer.parseInt(iidStr);
			ComplatZone complatZone = complatZoneService.fingByKey(iid);
			if (complatZone != null) {
				JSONObject object = JSONObject.fromObject(complatZone);
				PrintWriter out = response.getWriter();
				String json = object.toString();
				out.write(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * @discription   获取区域父节点名称 
     * @param request
     * @param response
	 */
	@RequestMapping(value = { "/getZonePname" }, method = {RequestMethod.POST })
	public void getZonePname(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pidStr = request.getParameter("pid");
			Integer pid = Integer.valueOf(Integer.parseInt(pidStr));
			ComplatZone complatZone = this.complatZoneService.fingByKey(pid);
			if (complatZone != null) {
				net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(complatZone);
				PrintWriter out = response.getWriter();
				String json = object.toString();
				out.write(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在树结构上删除区域 （逻辑删除）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/zonedelete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = false;
			String iid = request.getParameter("key");
			Integer iidInteger = Integer.parseInt(iid);
			ComplatZone complatZoneDel = complatZoneService.fingByKey(iidInteger);
			// 先判断部门下是否有用户
			List<ComplatZone> complatZones = complatZoneService.getAll();
			for (ComplatZone complatZone : complatZones) {
				if (complatZone.getPid() == iidInteger) {
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				complatZoneService.delete(complatZoneDel);
				response.getWriter().write("success");
			} else {
				response.getWriter().write("exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(e.getLocalizedMessage());
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * 判断区域下是否存在子区域
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkZone", method = RequestMethod.POST)
	public void checkUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = false;
			String iid = request.getParameter("key");
			Integer iidInteger = Integer.parseInt(iid);
			// 先判断部门下是否有用户
			List<ComplatZone> complatZones = complatZoneService.getAll();
			for (ComplatZone complatZone : complatZones) {
				if (complatZone.getPid() == iidInteger) {
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				response.getWriter().write("notexist");
			} else {
				response.getWriter().write("exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(e.getLocalizedMessage());
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * @discription 树保存信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveZone", method = RequestMethod.POST)
	public void treeSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seq = request.getParameter("seq");
			String name = request.getParameter("name");
			String parId = request.getParameter("parId");
			String dcode = request.getParameter("dcode");
			String typeStr = request.getParameter("type");
			Integer type = 0;
			if (typeStr != null) {
				type = Integer.parseInt(typeStr) + 1;
			}
			if (type <= 3) {
				Map<String, Object> resMap = new HashMap<String, Object>();
				SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession()
						.getAttribute("sysUserSession");
				ComplatZone complatZone = new ComplatZone();
				complatZone.setName(name);
				complatZone.setPid(Integer.parseInt(parId));
				complatZone.setCodeId(dcode);
				complatZone.setOrderId(seq);
				complatZone.setType(type);
				complatZoneService.save(complatZone);
				ComplatZone complatZoneSave = complatZoneService.fingByKey(complatZone.getIid());
				resMap.put("ret", 1);
				resMap.put("id", complatZoneSave.getIid());
				response.getWriter().write(org.json.simple.JSONObject.toJSONString(resMap));
			} else {
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap.put("ret", 0);
				response.getWriter().write(org.json.simple.JSONObject.toJSONString(resMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap.put("ret", 0);
				response.getWriter().write(org.json.simple.JSONObject.toJSONString(resMap));
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * 验证区域名称的唯一性
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkZoneName", method = RequestMethod.GET)
	public void checkDeptName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String deptNameInput = StringUtils.trim((String) request.getParameter("regionName"));
			String deptNameold = StringUtils.trim((String) request.getParameter("oldRoleName"));
			String deptCode = StringUtils.trim((String) request.getParameter("regionCode"));
			String deptCodeNew = deptCode.substring(0, deptCode.length() - 3) + "___";
			if (!deptNameInput.equals(deptNameold)) {
				List<ComplatZone> complatZones = complatZoneService.checkUniqueDeptName(deptNameInput, deptCode);
				if (complatZones != null && complatZones.size() > 0) {
					response.getWriter().write("0");
				} else {
					response.getWriter().write("1");
				}
			} else {
				response.getWriter().write("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
