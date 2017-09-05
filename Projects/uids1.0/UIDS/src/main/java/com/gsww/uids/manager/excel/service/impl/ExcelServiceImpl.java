package com.gsww.uids.manager.excel.service.impl;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.OrgTypeEnum;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.ValidateUtil;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.entity.AccountErrorTemp;
import com.gsww.uids.manager.account.entity.AccountMergeTemp;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.excel.service.ExcelService;
import com.gsww.uids.manager.excel.util.ExcelUtil;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationErrorTemp;
import com.gsww.uids.manager.org.entity.OrganizationMergeTemp;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.service.AreaService;

import net.sf.json.JSONObject;

/**
 * excel导入、导出
 * 
 * @author lich
 *
 */
@Service
public class ExcelServiceImpl  implements ExcelService {
	
	private static final Logger logger = Logger.getLogger(ExcelServiceImpl.class);
	//定义一个excel所容纳的初始数据量(防止数据过多，因为一个excel表格最多只能存65535行记录(excel2003的))，所以这里取40000
    private static Integer initial_data = 4000;
	//累计遍历的数量，用来判断是否超过初始数据，如果超过则新建一个sheet
    private int length = 0;
    //sheet页的创建
    private int sheetNum = 0;	
	@Autowired
	private AccountService accountService;

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AreaService areaService;
	
	@Override
	public XSSFWorkbook exportAccountExcel(String ids, int type,String exportName,String exportField) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个工作表
		XSSFSheet xSSFSheet = workbook.createSheet("账号信息表" + sheetNum);
		String [] hearders = exportName.split(",");
		//添加表头
		ExcelUtil.setSheetHeader(workbook,xSSFSheet,hearders);
		// 查找所有要导出的账号
		List<Account> list = new ArrayList<Account>();
		for ( String id : ids.split(",") ) {
			Account account = accountService.findById(id);
			list.add(account);
		}
		//导出表格数据
		setAccountSheetContent(workbook,xSSFSheet,list,exportField,hearders);
		return workbook;
	}

	public String importAccountExcel(InputStream fis, String fileName, String appId, int type)
	  {
	    int inputSum = 1;
	    Application application = null;
	    if (!StringUtil.isBlank(appId)) {
	      application = this.applicationService.findById(appId);
	    }
	    JSONObject resultJson = new JSONObject();
	    boolean excelType = true;

	    Workbook workbook = null;
	    Row row = null;
	    try
	    {
	      if (excelType)
	        workbook = new XSSFWorkbook(fis);
	      else {
	        workbook = new HSSFWorkbook(fis);
	      }
	      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	       Sheet sheet = workbook.getSheetAt(i);
	        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
	          Account account = null;
	          AccountErrorTemp aet = new AccountErrorTemp();//临时暂存有问题的账号
	          //把错误信息存成字符串
	          StringBuilder strErr = new StringBuilder();
	          row = sheet.getRow(j);
	          account = new Account();
	          account.setApp(application);
	          int cols = row.getPhysicalNumberOfCells();
	     	  ExcelUtil.setCellType(sheet,row,cols);
	          if (cols > 0) {
	        	  if (type == 1) {//公务账号导入
	        		  //导入账号共有的信息
	        		  importCommon(strErr, sheet, row, account, application,j);  
	        	  }
	        	  
	        	  if (strErr.toString().indexOf("过长") >= 0 || strErr.toString().indexOf("为空") >= 0 ||
	        			  strErr.toString().indexOf("不存在") >= 0 || strErr.toString().indexOf("不匹配") >= 0 ) {
	        		  aet.setErrorMessage(strErr.toString());
	        		  accountService.saveOrUpdate(aet);
	        	  } else {
	        		  List<Account> account1 = accountService.findByAppAndAccountName(appId, account.getName());
	        		  if (account1.isEmpty() || account1 == null) {//表示没有重复的记录
	        			  accountService.saveOrUpdate(account);
	        			  inputSum++;
	        		  } else {//表示有重复记录
	        			  if(account1.get(0).getUser() != null){
		            		   strErr.append("绑定的用户的账号不能被合并");
		            		   aet.setErrorMessage(strErr.toString());
		   	            	   accountService.saveOrUpdate(aet);
		 	            	   continue;
	        			  }
			            	AccountMergeTemp amt = new  AccountMergeTemp();
			                amt.setId(account1.get(0).getUuid());
			                AccountTOAccountMergeINfo(amt,account);//把excel中的信息存入临时表
			                AccountMergeTemp amt1 = new  AccountMergeTemp();
			                amt1.setId(account1.get(0).getUuid());
			                AccountTOAccountMergeINfo(amt1,account1.get(0));//把数据库中的信息存入临时表
		               } 
	            	
	            }
	          }
	        }
	        if (inputSum == sheet.getPhysicalNumberOfRows()) {
		         resultJson.put("state", "0");
		         resultJson.put("info", "全部导入成功");
		      }else{
		    	 resultJson.put("state", "1");
			     resultJson.put("info", "未全部导入");
		      }
	      }
	    } catch (Exception e) {
	    	resultJson.put("state", MessageInfo.STATE_FAIL);
	    	resultJson.put("info", "导入账号失败,请核查导入表格式");
	    	e.printStackTrace();
	      logger.info("导入账号失败：" + e.getMessage());
	    }
	    return resultJson.toString();
	  }

	@Override
	public XSSFWorkbook exportOrgExcel(String ids,String exportName,String exportField ) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个工作表
		XSSFSheet xSSFSheet = workbook.createSheet("机构信息表" + sheetNum);
		String [] hearders = exportName.split(",");
		//添加表头
		ExcelUtil.setSheetHeader(workbook,xSSFSheet,hearders);
		// 查找所有要导出的账号
		List<Organization> list = new ArrayList<Organization>();
		for ( String id : ids.split(",") ) {
			Organization org = organizationService.findById(id);
			list.add(org);
		}
		//添加导出excel数据
		setOrgSheetContent(workbook, xSSFSheet, list, exportField, hearders);
		return workbook;
	}
	
	@Override
	public String importOrgExcel(InputStream fis, String fileName) {
		int inputSum = 1;
		JSONObject resultJson = new JSONObject();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		//准备workbook
		Workbook workbook = null;
		Row row = null; 
		//根据文件格式来初始化
		try {
			if ("xls".equals(suffix) || "XLS".equals(suffix)) {
				workbook = new HSSFWorkbook(fis);
			} else if ("xlsx".equals(suffix) || "XLSX".equals(suffix)) {
				workbook = new XSSFWorkbook(fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
			for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
				 sheet = workbook.getSheetAt(i);
				 for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
					//把错误信息存成字符串
					 StringBuilder strErr = new StringBuilder();
					 OrganizationErrorTemp  oet = new OrganizationErrorTemp();
					 row = sheet.getRow(j);
					 //此处需要判断
					 int cols = row.getPhysicalNumberOfCells();
					if (cols > 0) {
						//给每一个单元格设置属性
						ExcelUtil.setCellType(sheet,row,cols);
						Organization parentOrg = new Organization();
						Organization org = new Organization();
						Area area = new Area();
						if (row.getCell((short)0).toString().length() != 0) {
							  org.setShortName(row.getCell((short)0).getStringCellValue());
			                  if (ValidateUtil.validateLenght(row.getCell(0).getStringCellValue(), "shortName", org)){
			                	  org.setShortName(row.getCell(0).getStringCellValue());
			                  }else{
			                	  strErr.append("第"+j+"行第1列机构简称字符过长；");
			                  }
						} else {
							strErr.append("第"+j+"行第1列机构简称不能为空；");
						}
						if (row.getCell((short)1).toString().length() != 0) {
							org.setFullName(row.getCell((short)1).getStringCellValue());
			                if (ValidateUtil.validateLenght(row.getCell(1).getStringCellValue(), "fullName", org)){
			                	  org.setFullName(row.getCell(1).getStringCellValue());
			                }else{
			                	strErr.append("第"+j+"行第2列机构全称字符过长；");
			                }
						} 
						if (row.getCell((short)2).toString().length() != 0) {
							if (ValidateUtil.validateLenght(row.getCell(2).getStringCellValue(), "type", org)){
								org.setType(OrgTypeEnum.nameOfOrgType(row.getCell(2).getStringCellValue().toString()));
							}else{
								strErr.append("第"+j+"行第3列机构节点类型字符过长；");
							}
						} else {
							strErr.append("第"+j+"行第3列机构节点类型不能为空；");
						}
						if (row.getCell((short)3).toString().length() != 0) {
							if(row.getCell(3).getStringCellValue().toString().matches("\\d{1,10}")){
								org.setSequence(Integer.parseInt(row.getCell((short)3).getStringCellValue()));
							}else{
								org.setSequence(j);
							}
						} else {
							strErr.append("第"+j+"行第4列排序字段字符过长；");
						}
						if (row.getCell((short)4).toString().length() != 0) {
							if(ValidateUtil.validateLenght(row.getCell((short)4).getStringCellValue(), "name", area)){
								List<Area> areas = areaService.findByCode(row.getCell((short)5).getStringCellValue().toString());
								if (areas.isEmpty()) {
									strErr.append("第"+j+"行第6列区域编码不存在请先在区域管理中添加区域；");
								}else {
									if (!areas.get(0).getName().equals(row.getCell((short)4).getStringCellValue().toString())) {
										strErr.append("第"+j+"行第5第6列区域名称不区域编码不匹配；");
									} else {
										org.setArea(areas.get(0));
									}
								}
							}else{
								strErr.append("第"+j+"行第5列区域名称不存在；");
							}
						} else {
							strErr.append("第"+j+"行第5列区域名称不能为空；");
						}
						if (row.getCell((short)6).toString().length() != 0) {
							if(ValidateUtil.validateLenght(row.getCell((short)6).getStringCellValue(), "suffix", org)){
								org.setSuffix(row.getCell((short)6).getStringCellValue());
							}else{
								strErr.append("第"+j+"行第7列机构后缀字符过长；");
							}
						} else {
							strErr.append("第"+j+"行第7列机构后缀不能为空；");
						}
						if (row.getCell((short)7).toString().length() != 0) {
							if(row.getCell((short)7).getStringCellValue().matches("^[A-Za-z0-9]{1,12}$")){
								org.setCode(row.getCell((short)7).getStringCellValue().toString());
							}else{
								strErr.append("第"+j+"行第8列机构编码长度过长或者不是数字和字母；");
							}
						} else {
							strErr.append("第"+j+"行第8列机构编码不能为空；");
						}
						if (row.getCell((short)9).toString().length() != 0) {
							if(ValidateUtil.validateLenght(row.getCell((short)9).getStringCellValue(), "fullName", parentOrg)){
								parentOrg = organizationService.findByCode(row.getCell((short)9).getStringCellValue().toString());
								if(parentOrg == null){
									strErr.append("第"+j+"行第10列父机构名称不存在；");
								}else{
									if (!parentOrg.getShortName().equals(row.getCell((short)8).getStringCellValue().toString())) {
										strErr.append("第"+j+"行第10列机构编码与第9列父机构的编码不匹配！；");
									} else {
										org.setParent(parentOrg);
									}
								}
							}else{
								strErr.append("第"+j+"行第10列父机构名称字符过长；");
							}
						}else{
							strErr.append("第"+j+"行第10列父机构名称不能为空；");
						}
						if (row.getCell((short)10).toString().length() != 0) {
							row.getCell((short)10).getStringCellValue().toString();
							if (row.getCell((short)10).getStringCellValue().toString().equals("是")) {
								org.setParentIs(1);
							} else if(row.getCell((short)10).getStringCellValue().toString().equals("否")) {
								org.setParentIs(2);
							} else {
								strErr.append("第"+j+"行第11列只能填写是或者否；");
							}
						} else{
							strErr.append("第"+j+"行第11列不能为空；");
						}
						if (row.getCell((short)11).toString().length() != 0) {
							if(ValidateUtil.validateLenght(row.getCell((short)11).getStringCellValue(), "desc", org)){
								org.setDesc(row.getCell((short)11).getStringCellValue());
							}else{
								strErr.append("第"+j+"行第12列机构描述字符过长；");
							}
						} 
			            	if (strErr.toString().indexOf("过长") >= 0 || strErr.toString().indexOf("为空") >= 0 ||
			            	strErr.toString().indexOf("不存在") >= 0 || strErr.toString().indexOf("不匹配") >= 0 ) {
			            		oet.setErrorMessage(strErr.toString());
				            	organizationService.saveOrUpdate(oet);
				            	 continue;
				            } else {
				            	Organization org1 = this.organizationService.findByFullName(org.getFullName());
				            	if (org1 == null) {//表示没有重复的记录
				            		org.setCreateTime(TimeHelper.getCurrentTime());
					                this.organizationService.saveOrUpdate(org);
					                inputSum++;
				            	} else{//表示有重复记录
					            	OrganizationMergeTemp omt = new  OrganizationMergeTemp();
					            	omt.setId(org1.getUuid());
					            	orgToOrgMergeTemp(omt,org);//把excel中的信息存入临时表
					            	OrganizationMergeTemp omt1 = new  OrganizationMergeTemp();
					            	omt1.setId(org1.getUuid());
					            	orgToOrgMergeTemp(omt1,org1);//把数据库中的信息存入临时表
				               }
				            }
					}
					
				 }
			 }
			 if (inputSum == sheet.getPhysicalNumberOfRows()) {
	   	         resultJson.put("state", "0");
	   	         resultJson.put("info", "全部导入成功");
	   	      }else{
	   	    	 resultJson.put("state", "1");
	   		     resultJson.put("info", "未全部导入");
	   	      }
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("state", 2);
			resultJson.put("info", "机构导入失败，请检查表格格式");
			return resultJson.toString();
		} 
		return resultJson.toString();
	}
	
	/**
	 * 将Account对象转化成AccountMergeTemp对象
	 * @return
	 */
	private void AccountTOAccountMergeINfo(AccountMergeTemp amt,Account account){
		amt.setAppId(account.getApp().getUuid());
		amt.setAppName(account.getAppName());
		amt.setCreateTime(account.getCreateTime());
		amt.setDel(account.getDel());
		amt.setName(account.getName());
		amt.setNickname(account.getNickname());
		amt.setPassword(account.getPassword());
		amt.setStatus(account.getStatus());
		amt.setType(account.getType());
		amt.setCorporateName(account.getCorporateName());
		accountService.saveOrUpdate(amt);
	}
	
	private void orgToOrgMergeTemp(OrganizationMergeTemp omt,Organization org){
		if(org.getArea() != null){
			omt.setAreaId(org.getArea().getUuid());
			omt.setAreaName(org.getArea().getName());
		}
		if(org.getParent() != null){
			omt.setParentId(org.getParentUuid());
			omt.setParentName(org.getParentName());
		}
		omt.setCode(org.getCode());
		omt.setCreateTime(org.getCreateTime());
		omt.setDel(org.getDel());
		omt.setDesc(org.getDesc());
		omt.setFullName(org.getFullName());
		omt.setParentIs(org.getParentIs());
		omt.setSequence(org.getSequence());
		omt.setShortName(org.getShortName());
		omt.setSuffix(org.getSuffix());
		omt.setType(org.getType());
		organizationService.saveOrUpdate(omt);
	}
	
    /**
     * 获取用户省市县三级地址
     * @param user
     * @return
     */
    public String getAreaValue(Area area){
   	 String userArea = "";
   	 if (area != null) {
   		 userArea =area.getName() + userArea;
   		 if (area.getParent() != null) {
   			 userArea =area.getParentName() + userArea;
   			 if (area.getParent().getParent() != null) {
   				 userArea =area.getParent().getParentName() + userArea;
   			 }
   		 }
   	 }
   	 return userArea;
    }
    /**
     * set Sheet页内容
	 * @param xWorkbook
      * @param xSheet
      */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void setAccountSheetContent(XSSFWorkbook workbook, XSSFSheet sheet,List<Account> list,String exportField,String [] hearders) {
       try {
           CellStyle cs = workbook.createCellStyle();
           ExcelUtil.cellStyle(workbook,cs);
           //行数定义
           int index = 0;
           if (null != list && list.size() > 0) {
              for (int i = 0; i < list.size(); i++) {
                   // 遍历每条数据的每一个字段值，产生一行数据
                   index++;
                   length++;
                   XSSFRow row = sheet.createRow(index);
                   Account t = list.get(i);
                   //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                   String[] fields = exportField.split(",");
                   for (int j = 0; j < fields.length; j++) {
                	   //处理关联信息的导出
                       XSSFCell cell = row.createCell(j);
                       cell.setCellStyle(cs); 
                       //获取get方法
                       String getMethodName = "";
                       if (fields[j].indexOf(".") == -1) {
                    	   getMethodName = "get" + fields[j].substring(0, 1).toUpperCase() + fields[j].substring(1);
                       } else {
                    	   getMethodName = "get" + (fields[j].split("\\."))[0].substring(0, 1).toUpperCase() + (fields[j].split("\\."))[0].substring(1);
                       }
  					   Class tCls = t.getClass();
  					   Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                       Object value = getMethod.invoke(t, new Object[] {});
                       // 判断值的类型后进行强制类型转换
                       String textValue = null;
                       if (null != value) {
                    	       if (getMethodName.equalsIgnoreCase("getType")) {
                    	    	   ExcelUtil.cellValue(AccountTypeEnum.nameOf((int)value),workbook,cell,cs);
                    	       } else if (value instanceof Application) {
                    	    	   ExcelUtil.cellValue(((Application) value).getName(),workbook,cell,cs);
                               } else if (value instanceof Organization) {
                            	   getMethodName = "get" + (fields[j].split("\\."))[1].substring(0, 1).toUpperCase() + (fields[j].split("\\."))[1].substring(1);
                            	   if (getMethodName.equalsIgnoreCase("getCode")) {
                            		   ExcelUtil.cellValue(((Organization) value).getCode(),workbook,cell,cs);
                            	   }
                                   if (getMethodName.equalsIgnoreCase("getFullName")) {
                                	   ExcelUtil.cellValue(((Organization) value).getFullName(),workbook,cell,cs);
                            	   }
                               } else if (getMethodName.equalsIgnoreCase("getStatus")){
                            	   if ((int)value == 1) {
                            		   ExcelUtil.cellValue("正常",workbook,cell,cs);
                            	   } else if ((int)value == 2) {
                            		   ExcelUtil.cellValue("冻结",workbook,cell,cs);
                            	   } 
                               } else if (getMethodName.equalsIgnoreCase("getType")){
                            	   ExcelUtil.cellValue(AccountTypeEnum.valueOf((int)value),workbook,cell,cs);
                               }else {
                            	   ExcelUtil.cellValue(value,workbook,cell,cs);
                               }
                         }else{
                               textValue = "";
                               cell.setCellValue(textValue);
                           }
                   }
                   if(length%initial_data ==0){
                	   if (i != list.size() - 1) {
                		   sheetNum ++;
                     	  // 创建一个工作表
                   		   sheet = workbook.createSheet("账号信息表" + sheetNum);
                           ExcelUtil.setSheetHeader(workbook, sheet, hearders);
                           index = 0;
                	   }
                   }
               
               }            
           }
           sheetNum = 0;
	} catch (Exception e) {
		e.printStackTrace();
		logger.info("账号导出失败："+ e);
	}    	 
     }
    /**
     * 机构导出数据内容
     * @param workbook
     * @param sheet
     * @param list
     * @param exportField
     * @param hearders
     */
    @SuppressWarnings("rawtypes")
	private void setOrgSheetContent(XSSFWorkbook workbook, XSSFSheet sheet,List<Organization> list,String exportField,String [] hearders) {
    	try {
    		 CellStyle cs = workbook.createCellStyle();
    		 ExcelUtil.cellStyle(workbook,cs);
             //行数定义
             int index = 0;
             if (null != list && list.size() > 0) {

                 for (int i = 0; i < list.size(); i++) {
                      // 遍历每条数据的每一个字段值，产生一行数据
                      index++;
                      length++;
                      XSSFRow row = sheet.createRow(index);
                      Organization t = list.get(i);
                      //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                      String[] fields = exportField.split(",");
                      for (int j = 0; j < fields.length; j++) {
                   	   //处理关联信息的导出
                          XSSFCell cell = row.createCell(j);
                          cell.setCellStyle(cs); 
                          //获取get方法
                          String getMethodName = "";
                          if (fields[j].indexOf(".") == -1) {
                       	   getMethodName = "get" + fields[j].substring(0, 1).toUpperCase() + fields[j].substring(1);
                          } else {
                       	   getMethodName = "get" + (fields[j].split("\\."))[0].substring(0, 1).toUpperCase() + (fields[j].split("\\."))[0].substring(1);
                          }
     					  Class tCls = t.getClass();
     					  @SuppressWarnings("unchecked")
						  Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                          Object value = getMethod.invoke(t, new Object[] {});
                          // 判断值的类型后进行强制类型转换
                          String textValue = null;
                          if (null != value) {
                        	  if (value instanceof Area) {
                        		  Area area = t.getArea();
                        		  getMethodName = "get" + (fields[j].split("\\."))[1].substring(0, 1).toUpperCase() + (fields[j].split("\\."))[1].substring(1);
                        		  if (getMethodName.equalsIgnoreCase("getName")) {
                        			  ExcelUtil.cellValue(area.getName(),workbook,cell,cs);
                        		  } else if (getMethodName.equalsIgnoreCase("getCode")) {
                        			  ExcelUtil.cellValue(area.getCode(),workbook,cell,cs);
                        		  }
                        	  } else if (value instanceof Organization) {
                        		  ExcelUtil.cellValue(t.getParentName(),workbook,cell,cs);
                        	  } else {
                        		  if (getMethodName.equalsIgnoreCase("getParentIs")) {
                            		  if ((int)value == 0) {
                            			  ExcelUtil.cellValue("否",workbook,cell,cs);
                            		  } else if ((int)value == 1){
                            			  ExcelUtil.cellValue("是",workbook,cell,cs);
                            		  }
                            	  } else if (getMethodName.equalsIgnoreCase("getType")) {
                            		  ExcelUtil.cellValue(OrgTypeEnum.valueOfOrgType(value.toString()),workbook,cell,cs);
                            	  }else {
                            		  ExcelUtil.cellValue(value,workbook,cell,cs);
                            	  }
                        	  } 
                          }else{
                                  textValue = "";
                                  cell.setCellValue(textValue);
                              }
                      }
                      if(length%initial_data ==0){
                    	  if (i != list.size() -1) {
	                    		  sheetNum ++;
	                           	  // 创建一个工作表
                    		     sheet = workbook.createSheet("账号信息表" + sheetNum);
                         		 ExcelUtil.setSheetHeader(workbook, sheet, hearders);
                                 index = 0;
                    	  } 
                      }
                  
                  }            
             }
             sheetNum = 0;
		} catch (Exception e) {
			logger.info("机构导出失败！"+ e);
		}
    }
    public void importCommon (StringBuilder strErr, Sheet sheet,Row row,Account account,Application application,int j) {
    	if (row.getCell(0).toString().length() != 0) {
            if (ValidateUtil.validateLenght(row.getCell(0).getStringCellValue(), "type", account)){
              account.setType(AccountTypeEnum.numberOf(row.getCell(0).getStringCellValue().toString()));
            } else {
              strErr.append("第"+j+"行第1列账号类型过长；");
            }
          } else {
        	  account.setType(1);
          } 
         if (row.getCell(2).toString().length() != 0) {
        	if (ValidateUtil.validateLenght(row.getCell(2).getStringCellValue(), "name", account)) {
        	    account.setName(row.getCell(2).getStringCellValue().toString());
        	} else {
        		strErr.append("第"+j+"行第3列账号名称过长；");
        	}
        } else {
        	strErr.append("第"+j+"行第3列账号名称不能为空；");
        }
         if (row.getCell(3).toString().length() != 0) {
         	if (ValidateUtil.validateLenght(row.getCell(3).getStringCellValue(), "nickname", account)) {
         	    account.setNickname(row.getCell(3).getStringCellValue().toString());
         	} else {
         		strErr.append("第"+j+"行第4列账号昵称过长；");
         	}
         } else {
        	 strErr.append("第"+j+"行第4列账号昵称不能为空；");
         }
         if (row.getCell(4).toString().length() != 0) {
          	if (ValidateUtil.validateLenght(row.getCell(4).getStringCellValue(), "password", account)) {
          		SimpleHash simpleHash = new SimpleHash(application.getAccountEncryptType(), row.getCell(4).getStringCellValue().toString(), null,
          				application.getAccountEncryptIterations());
          		account.setPassword(simpleHash.toString());
          	} else {
          		strErr.append("第"+j+"行第5列账号密码过长；");
          	}
          } else {
        	  strErr.append("第"+j+"行第5列账号密码不能为空；");
          }
         if (row.getCell(5).toString().length() != 0) {
            	    if (row.getCell(5).getStringCellValue().toString().equalsIgnoreCase("正常")) {
            	    	account.setStatus(1);
            	    } else if (row.getCell(5).getStringCellValue().toString().equalsIgnoreCase("冻结")) {
            	    	account.setStatus(2);
            	    }
            } else {
            	strErr.append("第"+j+"行第6列账号状态为空；");
            }
    }
}
