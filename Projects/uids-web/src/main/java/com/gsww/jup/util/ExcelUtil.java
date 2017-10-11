package com.gsww.jup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 项目名称： uids-web
 * 
 * @author ZhangHL
 * @time 2017-8-1 - 下午04:47:39
 */
public class ExcelUtil {
	public static final String HEADERINFO = "headInfo";
	public static final String DATAINFON = "dataInfo";

	/**
	 * 
	 * @param <T>
	 * @param fileName 文件名称
	 * @param is 读入文件流
	 * @param entityClass
	 * @param fieldMap
	 *        fieldMap格式：map.put("0","属性名"); 从0开始，与导入模板的顺序对应
	 * @return
	 * @throws Exception
	 */
	public static<T> List<T> readXls(String fileName,InputStream is,Class<T> entityClass,LinkedHashMap<String, String> fieldMap) throws Exception {
		//定义要返回的list
        List<T> resultList=new ArrayList<T>();
		try {
			Workbook workbook = getWeebWork(fileName,is);
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
					Row hssfRow = sheet.getRow(rowNum);
					if (hssfRow != null) {
						T entity=entityClass.newInstance();						
						int columnNum=sheet.getRow(0).getPhysicalNumberOfCells();
						//循环列col
						for(int i = 0;i<columnNum;i++){
							Object content = null;
							if(hssfRow.getCell(i) != null){
								content = formatCell(hssfRow.getCell(i));
								if(i==2){
									if("男".equals(content)){
										content=1;
									}else if("女".equals(content)){
										content=0;
									}
								}
								/*if(i==5 || i== 6){
									//java导入excel文件时,数字显示	
									DecimalFormat dfs = new DecimalFormat("0");
									content = dfs.format(hssfRow.getCell(i));
								}*/
							}else{
								content = "";
							}
							
							
							String fieldName = fieldMap.get(String.valueOf(i));
							setFieldValueByName(fieldName, content, entity);
						}
						
						resultList.add(entity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导入Excel失败");
		}
		
		return resultList;
	}

	/**
	 * 
	 * 根据传入的文件名获取工作簿对象(Workbook)
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWeebWork(String filename,InputStream is) throws IOException {
		Workbook workbook = null;
		if (null != filename) {
			String fileType = filename.substring(filename.lastIndexOf("."),
					filename.length());
			//FileInputStream fileStream = new FileInputStream(new File(filename));
			if (".xls".equals(fileType.trim().toLowerCase())) {
				workbook = new HSSFWorkbook(is);// 创建 Excel 2003 工作簿对象
			} else if (".xlsx".equals(fileType.trim().toLowerCase())) {
				workbook = new XSSFWorkbook(is);// 创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}
	
	@SuppressWarnings("static-access")
	private static String formatCell(Cell cell) {
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			DecimalFormat dfs = new DecimalFormat("0");
			return String.valueOf(dfs.format(cell.getNumericCellValue()));
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	
	
	
	
	

    /**
     * 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param fieldValue    字段值
     * @param o 对象
     */
    private static void setFieldValueByName(String fieldName,Object fieldValue,Object o) throws Exception{
    	    Field field = o.getClass().getDeclaredField(fieldName);
            if(field!=null){
                field.setAccessible(true);
                //获取字段类型
                Class<?> fieldType = field.getType();  

                //根据字段类型给字段赋值
                if (String.class == fieldType) {  
                    field.set(o, String.valueOf(fieldValue));  
                } else if ((Integer.TYPE == fieldType)  
                        || (Integer.class == fieldType)) {  
                    field.set(o, Integer.parseInt(fieldValue.toString()));  
                } else if ((Long.TYPE == fieldType)  
                        || (Long.class == fieldType)) {  
                    field.set(o, Long.valueOf(fieldValue.toString()));  
                } else if ((Float.TYPE == fieldType)  
                        || (Float.class == fieldType)) {  
                    field.set(o, Float.valueOf(fieldValue.toString()));  
                } else if ((Short.TYPE == fieldType)  
                        || (Short.class == fieldType)) {  
                    field.set(o, Short.valueOf(fieldValue.toString()));  
                } else if ((Double.TYPE == fieldType)  
                        || (Double.class == fieldType)) {  
                    field.set(o, Double.valueOf(fieldValue.toString()));  
                } else if (Character.TYPE == fieldType) {  
                    if ((fieldValue!= null) && (fieldValue.toString().length() > 0)) {  
                        field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));  
                    }  
                }else if(Date.class==fieldType){
                    field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
                }else{
                    field.set(o, fieldValue);
                }
            }else{
                throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
            }
    }
    

	/**
	 * 导出下载
	 * @Title: writeExcel
	 * @param map
	 * 封装需要导出的数据
	 * HEADERINFO封装表头信息
	 * DATAINFON：封装要导出的数据信息,value使用TreeMap,TreeMap以列顺序为key，1,2,3....) 
	 * 例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 *       map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>> dataList);
	 * @param wb
	 * @return
	 * @throws IOException
	 */
	public static void writeExcel(Map<String, Object> map, Workbook wb,HttpServletResponse response,String fileName) throws IOException {
		if (null != map) {
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<TreeMap<String, Object>> dataList = (List<TreeMap<String, Object>>) map.get(ExcelUtil.DATAINFON);
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}

			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i + 1);// 创建数据行
				TreeMap<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					String strdata = String.valueOf(mapdata.get(it.next()));
					if(strdata == null || strdata.length() <= 0 || strdata.equals("null")){
						strdata = "";
					}
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					j++;
				}
			}
			Date now = new Date();
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//fileName = fileName+"111_" + sdf.format(now).toString();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+  new String(fileName.getBytes(),"iso-8859-1") + ".xlsx");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 设置表头样式
	 * @param wb
	 * @return
	 * 
	 */
	public static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
		// style.setWrapText(true);//设置自动换行
		style.setFont(font);
		return style;
	}
	
}
