package com.gsww.uids.controller;

import java.sql.Timestamp;
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

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.service.ComplatGroupService;

@Controller
@RequestMapping(value = "/uids")
public class ComplatGroupController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ComplatGroupController.class);
	@Autowired
	private ComplatGroupService complatGroupService;
	@Autowired
	private SysParaService sysParaService;
    //节点类型下拉选择集合
	private Map<Integer, Object> nodetypeMap = new HashMap<Integer, Object>();
	//区域类型下拉选择集合
	private Map<Integer, Object> areatypeMap = new HashMap<Integer, Object>();

	/**
	 * 获取用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/complatgroupList",method = RequestMethod.GET)
	public String complatgroupList(@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "order.field", defaultValue = "createtime") String orderField,
			@RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort,
			@RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage,
			Model model,ServletRequest request,HttpServletRequest hrequest) {
		try{
			if(StringUtils.isNotBlank(request.getParameter("orderField"))){
				orderField=(String)request.getParameter("orderField");
			}
			if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
				orderSort=(String)request.getParameter("orderSort");
			}
			//初始化分页数据
			PageUtils pageUtils=new PageUtils(pageNo,pageSize,orderField,orderSort);
			PageRequest pageRequest=super.buildPageRequest(hrequest,pageUtils,ComplatGroup.class,findNowPage);
			
			//搜索属性初始化
//		
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Specification<ComplatGroup>  spec=super.toSpecification(searchParams, ComplatGroup.class);
			//分页
			Page<ComplatGroup> pageInfo = complatGroupService.getUserPage(spec,pageRequest);
			//查询上级机构名称
			Map<Integer,String> parentNameMap=new HashMap<Integer,String>();
			for(ComplatGroup complatGroup:pageInfo.getContent()){				
				if(null != complatGroup.getPid()){
					String name = complatGroupService.findByKey(String.valueOf(complatGroup.getPid())).getName();
					parentNameMap.put(complatGroup.getPid(), name);
				}else{
					parentNameMap.put(null,complatGroup.getName());
				}
			}
			//下拉选择查询
			//节点类型
			List<Map<String, Object>> nodetypeList = new ArrayList<Map<String, Object>>();
			nodetypeList = sysParaService.getParaList("JGJDLX");
			for (Map<String, Object> para : nodetypeList) {
				nodetypeMap.put(Integer.parseInt((String) para.get("PARA_CODE")), para.get("PARA_NAME"));
			}
			//区域类型
			List<Map<String, Object>> areatypeList = new ArrayList<Map<String, Object>>();
			areatypeList = sysParaService.getParaList("JGQYLX");
			for (Map<String, Object> para : areatypeList) {
				areatypeMap.put(Integer.parseInt((String) para.get("PARA_CODE")), para.get("PARA_NAME"));
			}
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("parentNameMap", parentNameMap);
			model.addAttribute("nodetypeMap", nodetypeMap);
			model.addAttribute("areatypeMap", areatypeMap);
			model.addAttribute("sortType", orderField);
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderSort", orderSort);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			model.addAttribute("sParams", searchParams);			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "users/complat/complatgroup_list";
	}
	
	/**
	 * 转到新增或编辑用户页面
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/complatgroupEdit", method = RequestMethod.GET)
	public String complatgroupEdit(String iid,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			ComplatGroup complatGroup = null;
			if(StringHelper.isNotBlack(iid)){
				String orderField="";
				String orderSort="";
				if(StringUtils.isNotBlank(request.getParameter("orderField"))){
					orderField=(String)request.getParameter("orderField");
				}
				if(StringUtils.isNotBlank(request.getParameter("orderSort"))){
					orderSort=(String)request.getParameter("orderSort");
				}
				complatGroup = complatGroupService.findByKey(iid);
				if(complatGroup.getPid() != null){
					String parentName = complatGroupService.findByKey(String.valueOf(complatGroup.getPid())).getName();
					complatGroup.setParentName(parentName);
				}
				model.addAttribute("orderField", orderField);
				model.addAttribute("orderSort", orderSort);
			}else{
				complatGroup = new ComplatGroup();
			}
			model.addAttribute("nodetypeMap", nodetypeMap);
			model.addAttribute("areatypeMap", areatypeMap);
			model.addAttribute("complatGroup", complatGroup);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "users/complat/complatgroup_edit";
	}
	
	/**
	 * 保存用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatgroupSave", method = RequestMethod.POST)
	public ModelAndView complatgroupSave(String iid,ComplatGroup complatGroup,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			if(StringHelper.isNotBlack(iid)){
				complatGroup.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
			}else{
				complatGroup.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
			}
			Integer pid = complatGroupService.findByName(complatGroup.getParentName()).getIid();
			complatGroup.setPid(pid);
			complatGroup = complatGroupService.save(complatGroup);
			returnMsg("success","保存成功",request);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error","保存失败",request);
		} finally{
			return  new ModelAndView("redirect:/uids/complatgroupList");
		}
		
	}
	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatgroupDelete", method = RequestMethod.GET)
	public ModelAndView complatgroupDelete(String iid,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String[] para=iid.split(",");
			ComplatGroup complatGroup = null;
			boolean flag=false;
			for(int i=0;i<para.length;i++){
				complatGroup=complatGroupService.findByKey(para[i].trim());
				complatGroupService.delete(complatGroup);
				flag=true;
			}
			if(flag){
				returnMsg("success","删除成功",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/uids/complatgroupList");
		}
		
	}
	
	
	

}

