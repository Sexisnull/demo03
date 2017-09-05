package com.gsww.jup.controller.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.DepartmentService;
import com.gsww.jup.service.sys.SysAccountService;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;

/**
 * <p>
 * Title:JUP-部门管理
 * </p>
 * 
 * <p>
 * Description: JUP-部门管理
 * </p>
 * 
 * @author wangjl
 * @date 2014-7-10
 */
@Controller
public class SysDeptController extends BaseController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private SysAccountService sysAccountService;
	private static final String PAGE_SIZE = "10";
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private String alertType = "";
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("checkTime", "标题");
	}

	private SysDepartment sysDepartment;

	public SysDepartment getSysDepartment() {
		return sysDepartment;
	}

	public void setSysDepartment(SysDepartment sysDepartment) {
		this.sysDepartment = sysDepartment;
	}

	/**
	 * 表单保存信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/sys/departmentSave", method = RequestMethod.POST)
	public ModelAndView systemSave(String deptId, SysDepartment sysDepartment, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if (!StringHelper.isNotBlack(sysDepartment.getDeptState())) {
				sysDepartment.setDeptState("1");
				sysDepartment.setCreateTime(TimeHelper.getCurrentTime());
			}
			departmentService.save(sysDepartment);
			alertType = "1";
		} catch (Exception e) {
			e.printStackTrace();
			alertType = "2";
		} finally {
			return new ModelAndView("redirect:/sys/departmentTableList");
		}
	}

	/**
	 * 2014-07-29
	 * guolei
	 * 新版本部门管理
	 */
	@RequestMapping(value = "/sys/departmentTableList", method = RequestMethod.GET)
	public ModelAndView tableList(@RequestParam(value = "page", defaultValue = "1") int pageNo, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize, @RequestParam(value = "order.field", defaultValue = "createTime") String orderField, @RequestParam(value = "order.sort", defaultValue = "DESC") String orderSort, @RequestParam(value = "findNowPage", defaultValue = "false") String findNowPage, Model model, ServletRequest request) {
		try {
			try {

				//初始化分页数据
				PageUtils pageUtils = new PageUtils(pageNo, pageSize, orderField, orderSort);
				PageRequest pageRequest = super.buildPageRequest((HttpServletRequest) request, pageUtils, SysDepartment.class, findNowPage);

				//搜索属性初始化
				Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
				searchParams.put("EQ_deptState", "1");
				Specification<SysDepartment> spec = super.toSpecification(searchParams, SysDepartment.class);

				//分页
				Page<SysDepartment> pageInfo = this.departmentService.getPage(spec, pageRequest);
				setName(pageInfo.getContent());
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("alertType", alertType);
				alertType = "";
				// 将搜索条件编码成字符串，用于排序，分页的URL
				//model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("sys/department_table_list");
	}

	private void setName(List<SysDepartment> content) {
		// TODO Auto-generated method stub
		for (SysDepartment sysDepartment : content) {
			try {
				setName(sysDepartment);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setName(SysDepartment sysDepartment) throws Exception {
		if (StringHelper.isNotBlack(sysDepartment.getParentDeptCode())) {
			SysDepartment pDepartment = this.departmentService.findByCode(sysDepartment.getParentDeptCode());
			if (pDepartment != null) {
				sysDepartment.setParentDepartment(pDepartment);
			}
		}
		/*SysAccount sysAccount = this.sysAccountService.findByKey(sysDepartment.getUserAcctId());
		if (sysAccount != null) {
			sysDepartment.setSysAccount(sysAccount);
		}*/
	}

	/**
	 * 返回完整的组织机构树的数据
	 * 按照setid（帐套的物理id过滤）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sys/departmenttree", method = RequestMethod.GET)
	public void departmenttree(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
			List<SysDepartment> list = departmentService.getAll();
			List<Map<String, String>> treeList = new ArrayList<Map<String, String>>();
			if (list != null && !list.isEmpty()) {
				for (SysDepartment sysDepartment : list) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", sysDepartment.getDeptCode());
					map.put("pId", sysDepartment.getParentDeptCode());
					map.put("name", sysDepartment.getDeptName());
					map.put("title", sysDepartment.getDeptName());
					map.put("tld", String.valueOf(sysDepartment.getDeptId()));
					map.put("viewtype", "1");
					map.put("regiontype", "1");
					map.put("open", "true");
					map.put("seq", String.valueOf(sysDepartment.getDeptSeq()));
					treeList.add(map);
				}
			}
			JSONArray array = JSONArray.fromObject(treeList);
			PrintWriter out = response.getWriter();
			String json = array.toString();
			//System.out.println(json);
			out.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/sys/departmenttree", method = RequestMethod.POST)
	public void departmenttreePost(HttpServletRequest request, HttpServletResponse response) {
		this.departmenttree(request, response);
	}

	/**
	 * 异步获取组织机构的完整信息并以json的格式返回
	 * 只需要传过组织机构的id（物理主键deptId即可）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sys/department/getRegion", method = RequestMethod.POST)
	public void getRegion(HttpServletRequest request, HttpServletResponse response) {
		try {
			String deptId = request.getParameter("key");
			SysDepartment sysDepartment = this.departmentService.findByKey(deptId);
			if (sysDepartment != null) {
				if (StringHelper.isNotBlack(sysDepartment.getParentDeptCode())) {
					SysDepartment pDepartment = this.departmentService.findByCode(sysDepartment.getParentDeptCode());
					if (pDepartment != null) {
						sysDepartment.setParentDeptName(pDepartment.getDeptName());
					}
				}
				JSONObject object = JSONObject.fromObject(sysDepartment);
				PrintWriter out = response.getWriter();
				String json = object.toString();
				out.write(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断部门下是否存在用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sys/department/checkUser", method = RequestMethod.POST)
	public void checkUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			String deptId = request.getParameter("key");
			//先判断部门下是否有用户
			List<SysAccount> list=new ArrayList<SysAccount>();
			sysDepartment=departmentService.findByKey(deptId);
			list=sysAccountService.findSysAccountListByDept(sysDepartment);
			if(CollectionUtils.isNotEmpty(list)){
				response.getWriter().write("exist");
			}else{				
				response.getWriter().write("notexist");
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
	 * 在树结构上删除组织机构
	 * （逻辑删除）
	 * 只需要传过组织机构的id（物理主键deptId即可）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sys/department/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			String deptId = request.getParameter("key");
			//先判断部门下是否有用户
			List<SysAccount> list=new ArrayList<SysAccount>();
			sysDepartment=departmentService.findByKey(deptId);
			list=sysAccountService.findSysAccountListByDept(sysDepartment);
			if(CollectionUtils.isNotEmpty(list)){
				response.getWriter().write("exist");
			}else{
				this.departmentService.delete(deptId);
				response.getWriter().write("success");
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
	 * 方法描述 : 部门重命名
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sys/department/updateName", method = RequestMethod.POST)
	public void updateName(HttpServletRequest request, HttpServletResponse response) {
		try {
			String deptId = request.getParameter("key");
			SysDepartment dept = departmentService.findByKey(deptId);
			String newName = request.getParameter("newName");
			//获取部门根节点
			SysUserSession sysUserSession = (SysUserSession) request.getSession().getAttribute("sysUserSession");
			List<SysDepartment> deptGenList=
				departmentService.findByParentDeptCode("0");
			SysDepartment deptGen=deptGenList.get(0);
			String deptGenCode=deptGen.getDeptCode();
			String deptCode=dept.getDeptCode();
			int count=deptCode.replace(deptGenCode, "000").length()/3;
			List<SysDepartment> sysDeptListAll=new ArrayList<SysDepartment>();
			for(int i=0;i<count-1;i++){
					List<SysDepartment> sysDeptListZi=
						departmentService.checkUniqueDeptName(
								newName, deptCode.substring(0,deptCode.length()-(i+1)*3));
					if(sysDeptListZi.size()==1){
						sysDeptListAll.add(sysDeptListZi.get(0));
					}else{
						//提示部门有重复,有脏数据
				}
			}
			String oldDeptName=dept.getDeptName();
			String deptCodeNew=deptCode.substring(0, deptCode.length()-3)+"___";
			if(!newName.equals(oldDeptName)){
				List<SysDepartment> sysDeptList = departmentService.checkUniqueDeptName(newName, deptCodeNew);
				//sysDeptList 同级节点验证    sysDeptListAll 父级节点验证
				if((sysDeptList!=null && sysDeptList.size()>0)||(sysDeptListAll!=null && sysDeptListAll.size()>0)){
					response.setContentType("text/plain");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write("repeat");
				}else{
					response.getWriter().write("success");
					dept.setDeptName(newName);
					departmentService.save(dept);
				}
			}else{
				response.getWriter().write("success");
				dept.setDeptName(newName);
				departmentService.save(dept);
			}
/*			SysDepartment sysDepartment = departmentService.findByKey(Integer.parseInt(deptId));
			sysDepartment.setDeptName(newName);
			departmentService.save(sysDepartment);
			response.getWriter().write("success");*/
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(e.getLocalizedMessage());
			} catch (Exception ex) {

			}
		}
	}

	/**
	 * 树保存信息
	 */
	@RequestMapping(value = "/sys/department/save", method = RequestMethod.POST)
	public void treeSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seq = request.getParameter("seq");
			String name = request.getParameter("name");
			String parId = request.getParameter("parId");
			String dcode = request.getParameter("dcode");
			SysDepartment dept = departmentService.findByCode(dcode);
			Map<String, Object> resMap = new HashMap<String, Object>();
			if(dept!=null && dept.getDeptId()!=null){
				resMap.put("ret", 2);
				response.getWriter().write(org.json.simple.JSONObject.toJSONString(resMap));
			}else{
				SysUserSession sysUserSession = (SysUserSession) ((HttpServletRequest) request).getSession().getAttribute("sysUserSession");
				SysDepartment sysDept = new SysDepartment();
				sysDept.setDeptName(name);
				sysDept.setParentDeptCode(parId);
				sysDept.setDeptCode(dcode);
				sysDept.setDeptSeq(Integer.parseInt(seq));
				sysDept.setCreateTime(TimeHelper.getCurrentTime());
				sysDept.setDeptState("1");
				//sysDept.setSetId(1);
				SysDepartment saveDept = departmentService.save(sysDept);
				
				resMap.put("ret", 1);
				resMap.put("id", saveDept.getDeptId());
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
	 * 验证部门名称的唯一性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/department/checkDeptName", method = RequestMethod.GET)
	public void checkDeptName(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String deptNameInput=StringUtils.trim((String)request.getParameter("regionName"));
			String deptNameold=StringUtils.trim((String)request.getParameter("oldRoleName"));
			String deptCode=StringUtils.trim((String)request.getParameter("regionCode"));
			String deptCodeNew=deptCode.substring(0, deptCode.length()-3)+"___";
			if(!deptNameInput.equals(deptNameold)){
				List<SysDepartment> sysDeptList = departmentService.checkUniqueDeptName(deptNameInput, deptCodeNew);
				if(sysDeptList!=null && sysDeptList.size()>0){
					response.getWriter().write("0");
				}else{
					response.getWriter().write("1");
				}
			}else{
				response.getWriter().write("1");
			}
/*			String id=(String)request.getParameter("key");
			String newName=(String)request.getParameter("newName");
			System.out.println(id+","+newName);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
