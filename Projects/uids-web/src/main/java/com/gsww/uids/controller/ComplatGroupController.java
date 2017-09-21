package com.gsww.uids.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.service.sys.SysParaService;
import com.gsww.jup.util.ExcelUtil;
import com.gsww.jup.util.PageUtils;
import com.gsww.jup.util.StringHelper;
import com.gsww.jup.util.TimeHelper;
import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.entity.ComplatUser;
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
			for(ComplatGroup complatGroup:pageInfo.getContent()){				
				if(null != complatGroup.getPid()){
					String parentName = complatGroupService.findByKey(String.valueOf(complatGroup.getPid())).getName();
					complatGroup.setParentName(parentName);
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
				//将pid转换为parentName在编辑框中显示
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
				//编辑状态改变操作状态位（opersign）和修改时间（modifytime）
				complatGroup.setOpersign(2);
				complatGroup.setModifytime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
			}else{
				//新增时将机构名汉字转换成首字母大写保存到pinyin字段中
				String daPinYin = getPinYinHeadChar(complatGroup.getName());
				complatGroup.setPinyin(daPinYin);
				//新增状态改变操作状态位（opersign）和创建时间（modifytime）
				complatGroup.setOpersign(1);
				complatGroup.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
			}
			//将上级机构名称转换成pid
			String parentName = request.getParameter("groupname");
			if(StringHelper.isNotBlack(parentName)){
				complatGroup.setPid(complatGroupService.findByName(parentName).getIid());
			}
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
				String mId = para[i].trim();
				complatGroup=complatGroupService.findByKey(mId);
				//将操作状态位改为3
				complatGroup.setOpersign(3);
				//检测当前要删除的机构是否有下级机构，有则无法删除
				List<ComplatGroup> list=new ArrayList<ComplatGroup>();
				list=complatGroupService.findByPid(Integer.valueOf(mId));
				if(list.size()>0){
					flag=false;
					break;
				}else{
					complatGroupService.delete(complatGroup);
					flag=true;
				}				
			}
			if(flag){
				returnMsg("success","删除成功",request);
			}else{
				returnMsg("error", "删除失败,该菜单下还有下级机构，请先删除下级机构",request);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "删除失败",request);			
		} finally{
			return  new ModelAndView("redirect:/uids/complatgroupList");
		}
		
	}
	
	/**
	 * 将机构名首字母大写返回
	 * @author LinCX
	 * @param str
	 * @return convert
	 */
    public static String getPinYinHeadChar(String str) {  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert.toUpperCase();  
    }  
    
    /**
	 * 数据导入
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @RequestParam(value="excelFile")MultipartFile multipartFile,
	 */
	@RequestMapping(value = "/complatgroupImport", method = RequestMethod.POST)
	public ModelAndView complatgroupImport(@RequestParam("excelFile")MultipartFile multipartFile,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {				      
		String fileName = multipartFile.getOriginalFilename();	
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("0", "name");
		fieldMap.put("1", "strNodeType");
		fieldMap.put("2", "codeid");
		fieldMap.put("3", "suffix");
		fieldMap.put("4", "groupallname");
		fieldMap.put("5", "orgcode");
		fieldMap.put("6", "orgtype");
		fieldMap.put("7", "strAreaType");
		fieldMap.put("8", "areacode");
		fieldMap.put("9", "strIsCombine");
		fieldMap.put("10", "parentName");
		fieldMap.put("11", "spec");
		//List<ComplatUser> users= ExcelUtil.readXls(absoluteFilePath+"/"+uuidFileName,ComplatUser.class,fieldMap);
		List<ComplatGroup> group= ExcelUtil.readXls(fileName,multipartFile.getInputStream(),ComplatGroup.class,fieldMap);
		//判断是哪行导入失败
		int row = 1;
		boolean flag=true;
		String strRow = "";
		try {
			for(ComplatGroup complatGroup:group){
				List<ComplatGroup> list = complatGroupService.findByAllName(complatGroup.getName());	
				if(list.size()==0){		
					if(StringHelper.isNotBlack(complatGroup.getName())){  //判断excel表格导入的数据是否规范
						//判断并设置节点类型的值
						if(complatGroup.getStrNodeType().equals("区域")){
							complatGroup.setNodetype(1);
						}else if(complatGroup.getStrNodeType().equals("单位")){
							complatGroup.setNodetype(2);
						}else if(complatGroup.getStrNodeType().equals("部门或处室")){
							complatGroup.setNodetype(3);
						}else{	}
						//判断并设置区域类型的值
						if(complatGroup.getStrAreaType().equals("省级")){
							complatGroup.setAreatype(1);
						}else if((complatGroup.getStrAreaType().equals("市（州）级")) || (complatGroup.getStrAreaType().equals("市(州)级"))){
							complatGroup.setAreatype(2);
						}else if(complatGroup.getStrAreaType().equals("区县")){
							complatGroup.setAreatype(3);
						}else if(complatGroup.getStrAreaType().equals("乡镇街道")){
							complatGroup.setAreatype(3);
						}else if(complatGroup.getStrAreaType().equals("其他")){
							complatGroup.setAreatype(3);
						}else{  }
						//判断是否为合并单位，并设置值
						if(complatGroup.getStrIsCombine().equals("否")){
							complatGroup.setIscombine(0);
						}else if(complatGroup.getStrIsCombine().equals("是")){
							complatGroup.setIscombine(1);
						}
						//新增时将机构名汉字转换成首字母大写保存到pinyin字段中
						String daPinYin = getPinYinHeadChar(complatGroup.getName());
						complatGroup.setPinyin(daPinYin);
						//设置创建时间
						complatGroup.setCreatetime(Timestamp.valueOf(TimeHelper.getCurrentTime()));
						//设置pid
						complatGroup.setPid(complatGroupService.findByName(complatGroup.getParentName()).getIid());
						//设置状态值
						complatGroup.setOpersign(1);
						complatGroup.setSynState(2);
						complatGroupService.save(complatGroup);
					}else{
						flag = false;
						strRow = strRow + "<" + row + ">"; //记录第几行数据导入失败
					}//else
					row++;//导入行数加一
				}//if(list
			}//for
			if(flag){
				returnMsg("success","导入成功",request);
			}else{
				returnMsg("error", "导入失败,第" + strRow + "数据不符合规范！",request);
			}
		} catch (Exception e) {
		e.printStackTrace();
		returnMsg("error", "导入失败",request);
		}
		return new ModelAndView("redirect:/uids/complatgroupList");
	}
	
    /**
     * excel文件导出
     */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/complatgroupExport", method = RequestMethod.GET)
	public ModelAndView complatgroupExport(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try{
			String sId = request.getParameter("sId");
			String[] para = sId.split(",");		
			String fileName = "机构列表";
			Map<String,Object> map = new HashMap<String,Object>(); 
			List headList = new ArrayList();//表头数据  
	        headList.add("机构名称");
	        headList.add("节点类型");
	        headList.add("机构编码");
	        headList.add("机构后缀");
	        headList.add("机构全名");
	        headList.add("组织机构代码");
	        headList.add("区域类型");
	        headList.add("区域编码");
	        headList.add("上级机构名称");
	        headList.add("上级机构编码");
	        headList.add("机构描述");
			Workbook wb = new XSSFWorkbook();  // 导出 Excel为2007 工作簿对象  
			List dataList = new ArrayList();
			for(int i=0;i<para.length;i++){
				String mId = para[i].trim();
				ComplatGroup complatGroup = complatGroupService.findByKey(mId);
				String nodeType = null;
				String areaType = null;
				if(complatGroup.getNodetype() == 1){
					nodeType = "区域";
				}else if(complatGroup.getNodetype() == 2){
					nodeType = "单位";
				}else if(complatGroup.getNodetype() == 3){
					nodeType = "部门或处室";
				}else{
					nodeType = "";
				}
				if(complatGroup.getAreatype() == 1){
					areaType = "省级";
				}else if(complatGroup.getAreatype() == 2){
					areaType = "市（州）级";
				}else if(complatGroup.getAreatype() == 3){
					areaType = "区县";
				}else if(complatGroup.getAreatype() == 4){
					areaType = "乡镇街道";
				}else if(complatGroup.getAreatype() == 5){
					areaType = "其他";
				}else{
					areaType = "";
				}
				TreeMap<String,Object> treeMap = new TreeMap<String, Object>();
				treeMap.put("1", complatGroup.getName());
				treeMap.put("2", nodeType);
				treeMap.put("3", complatGroup.getCodeid());
				treeMap.put("4", complatGroup.getSuffix());	
				treeMap.put("5", complatGroup.getGroupallname());
				treeMap.put("6", complatGroup.getOrgcode());
				treeMap.put("7", areaType);
				treeMap.put("8", complatGroup.getAreacode());
				if(complatGroup.getPid() != null){
					treeMap.put("9", complatGroupService.findByIid(complatGroup.getPid()).getName());
					treeMap.put("91", complatGroupService.findByIid(complatGroup.getPid()).getCodeid());
				}else{
					treeMap.put("9", "");
					treeMap.put("91", "");
				}
				treeMap.put("92", complatGroup.getSpec());			
				dataList.add(treeMap);
			}
			map.put(ExcelUtil.HEADERINFO, headList);  
	        map.put(ExcelUtil.DATAINFON, dataList); 
	        ExcelUtil.writeExcel(map, wb,response,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg("error", "导出失败",request);			
		} finally{
			return  new ModelAndView("redirect:/uids/complatgroupList");
		}
	}
}

