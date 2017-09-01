//package com.gsww.uids.manager.appTest;
//
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.junit.Test;
//
//import com.gsww.uids.manager.user.entity.Account;
//
//public class XlsUtil {
//	@Test
//	public void ExcelTest() throws Exception{
//		SXSSFWorkbook workbook = new SXSSFWorkbook(1);
//	    // 创建一个工作表
//	    Sheet sheet = workbook.createSheet("学生表一");
//	    System.out.println(sheet.getPhysicalNumberOfRows());
//	    // 添加表头行
//	    Row hssfRow = sheet.createRow(0);
//	    // 设置单元格格式居中
//	   // HSSFCellStyle cellStyle = workbook.createCellStyle();
//	    //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//	    // 添加表头内容
//	    Cell headCell = hssfRow.createCell(0);
//	    headCell.setCellValue("姓名");
//	    //headCell.setCellStyle(cellStyle);
//
//	    headCell = hssfRow.createCell(1);
//	    headCell.setCellValue("年龄");
//	   // headCell.setCellStyle(cellStyle);
//
//	    headCell = hssfRow.createCell(2);
//	    headCell.setCellValue("年级");
//	    //headCell.setCellStyle(cellStyle);
//	    // 添加数据内容
//	    List<Account> list = new ArrayList<>();
//
//	    Account account = new Account();
//	    account.setName("统一身份认证");
//	    account.setStatus(1);
//	    account.setNickname("test");
//
//	    Account account1 = new Account();
//
//	    account1.setName("统一身份认证1");
//	    account1.setStatus(2);
//	    account1.setNickname("test1");
//
//	    list.add(account);
//	    list.add(account1);
//	    for (int i = 0; i < list.size(); i++) {
//	      hssfRow = sheet.createRow((int) i + 1);
//
//	      // 创建单元格，并设置值
//	      Cell cell = hssfRow.createCell(0);
//	      cell.setCellValue(list.get(i).getName());
//
//	      cell = hssfRow.createCell(1);
//	      cell.setCellValue(list.get(i).getStatus());
//
//	      cell = hssfRow.createCell(2);
//	      cell.setCellValue(list.get(i).getNickname());
//	    }
//
//	    // 保存Excel文件
//	    try {
//	      OutputStream outputStream = new FileOutputStream("D:/students.xls");
//	      workbook.write(outputStream);
//	      outputStream.close();
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }
//	}
//
//}
