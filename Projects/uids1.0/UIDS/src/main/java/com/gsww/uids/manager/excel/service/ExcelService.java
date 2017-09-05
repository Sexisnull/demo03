package com.gsww.uids.manager.excel.service;

import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExcelService {
	
	/**
	 * excel导出账号
	 * @return
	 */
	public XSSFWorkbook  exportAccountExcel(String ids,int type,String exportName,String exportField);
	
	/**
	 * excel导入账号
	 * @return
	 */
	public String   importAccountExcel(InputStream fis,String fileName,String appId,int type);
	
	/**
	 * excel导出机构信息
	 * @param ids
	 * @return
	 */
	public XSSFWorkbook exportOrgExcel(String ids,String exportName,String exportField);
	
	/**
	 * excel导入org机构
	 * @param fis
	 * @param fileName
	 * @return
	 */
	public String   importOrgExcel(InputStream fis,String fileName);
	
}
