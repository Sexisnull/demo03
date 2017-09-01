//package com.gsww.uids.manager.appTest;
//
//import java.util.Map;
//
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//
//import antlr.StringUtils;
//
//public class excelUtil {
//
//	public void test{
//
//		    SXSSFWorkbook workbook = new SXSSFWorkbook(100);
//	        Sheet sheet = workbook.createSheet("测试");
//	        Row row = null;
//	        Cell cell = null;
//	        int rowIndex = 0;
//	        // 标题
//	        Font titleFont = workbook.createFont();
//	        CellStyle titleCellStyle = workbook.createCellStyle();
//	        titleCellStyle.setFont(titleFont);
//	        row = sheet.createRow(rowIndex++);
//	        int cellSize = cellMapList.size();
//	        for (int i = 0; i < cellSize; i++) {
//	            CellMap cellMap = cellMapList.get(i);
//	            String title = cellMap.getTitle();
//	            cell = row.createCell(i);
//	            cell.setCellStyle(titleCellStyle);
//	            cell.setCellValue(title);
//	            if (title != null) {
//	                sheet.setColumnWidth(i, title.getBytes().length * 2 * 172);
//	            }
//	        }
//	        // 数据
//	        CellStyle dataCellStyle = workbook.createCellStyle();
//	        int rowSize = (dataList == null) ? 0 : dataList.size();
//	        for (int i = rowIndex; i < rowSize + rowIndex; i++) {
//	            Map<String,String> obj = dataList.get(i - rowIndex);
//	            row = sheet.createRow(i);
//	            for (int j = 0; j < cellSize; j++) {
//	                CellMap cellMap = cellMapList.get(j);
//	                cell = row.createCell(j);
//	                cell.setCellStyle(dataCellStyle);
//	                String property = cellMap.getProperty();
//	                if(property.equals("rowNumber") || StringUtils.isEmpty(property)){
//	                    cell.setCellValue(i);
//	                }else{
////	                  String propertyValue = getPropertyValue(obj, property);
//	                    String propertyValue = obj.get(property);
//	                    cell.setCellValue(propertyValue);
//	                    if (propertyValue != null) {
//	                        int columnWidth = sheet.getColumnWidth(j);
//	                        int propertyValueLength = propertyValue.getBytes().length * 2 * 172;
//	                        if (columnWidth < propertyValueLength) {
//	                            sheet.setColumnWidth(j, propertyValueLength);
//	                        }
//	                    }
//	                }
//
//	            }
//	        }
//	        workbook.write(out);
//	}
//}
