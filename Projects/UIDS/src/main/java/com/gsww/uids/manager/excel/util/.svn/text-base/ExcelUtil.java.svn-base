package com.gsww.uids.manager.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	
	 /**
     * set Sheet页头部
     * @param xWorkbook
     * @param xSheet
     */
    public static void setSheetHeader(XSSFWorkbook xWorkbook, XSSFSheet xSheet,String [] hearders) {
    	CellStyle cs = xWorkbook.createCellStyle();
    	//表头设置为文本格式
    	XSSFDataFormat format = xWorkbook.createDataFormat();  
        cs.setDataFormat(format.getFormat("@"));
        //设置字体
        Font headerFont = xWorkbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontName("宋体");
        cs.setFont(headerFont);
        cs.setWrapText(true);//是否自动换行
    	ExcelUtil.cellStyle(xWorkbook,cs);
        XSSFRow xRow0 = xSheet.createRow(0);
        
        for (int i = 0; i < hearders.length; i++) {
        	xSheet.setColumnWidth(i, 20 * 256);  
        	XSSFCell xCell0 = xRow0.createCell(i);
            xCell0.setCellStyle(cs);
            xCell0.setCellValue(hearders[i]);
        }
    }
    
	 /**
     * 设置单元格共有的样式
     * @param xWorkbook
     * @param cs
     */
    public static void cellStyle(XSSFWorkbook xWorkbook,CellStyle cs) {
   	//设置水平垂直居中
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //单元格添加边框
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
    }
    /**
     * 判断单元格属性值的类型
     * @param value
     * @param cell
     */
    public static void cellValue(Object value,XSSFWorkbook xWorkbook,XSSFCell cell,CellStyle cs){
   	 if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(Float.toString(fValue));
        } else if (value instanceof Double) {
            double dValue = (Double) value;
            cell.setCellValue(Double.toString(dValue));
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
        } else if (value instanceof String) {
       	 XSSFDataFormat format = xWorkbook.createDataFormat();  
         cs.setDataFormat(format.getFormat("@"));
       	 cell.setCellValue(value.toString());
        }
    }
    
    /**
     * 获取单元格值的时候先给单元格设置属性
     * @param sheet
     * @param cols
     */
    public static void setCellType(Sheet sheet,Row row,int cols){
        for (int i = 0; i < cols; i++) {
        	row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
        }
    }
}
