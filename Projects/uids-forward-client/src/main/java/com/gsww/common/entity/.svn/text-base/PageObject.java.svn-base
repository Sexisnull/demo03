package com.gsww.common.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gsww.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 分页对象
 * 
 * @author simplife
 *
 * @param <T>
 */
public class PageObject<T> {

	// 分页结果集
	private List<T> dataList = null;

	// 记录总数
	private int totalCount = 0;

	// 每页显示记录数
	private int pageSize = 10;

	// 当前页数
	private int currentPage = 1;

	// 总页数
	private int pageCount = 1;

	// 当前页的开始记录
	private int beginRecord = 1;

	// 当前页的结束记录
	private int endRecord = 1;
	
	// 转json时的转换类型
	private String[] types = {"java.lang.Integer",
	        "java.lang.Double",
	        "java.lang.Float",
	        "java.lang.Long",
	        "java.lang.Short",
	        "java.lang.Byte",
	        "java.lang.Boolean",
	        "java.lang.Character",
	        "java.lang.String",
	        "int","double","long","short","byte","boolean","char","float"};

	public PageObject(int totalCount, int pageSize, int currentPage,
			List<T> dataList) {
		this.dataList = dataList;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
	}

	public PageObject() {

	}

	public List<T> getDataList() {
		return dataList == null ? new ArrayList<T>() : dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	// 计算总页数，如果为0则置为1
	public int getPageCount() {
		pageCount = (int) (totalCount / pageSize);
		if (totalCount % pageSize != 0) {
			pageCount++;
		}
		return pageCount;

	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginRecord() {
		beginRecord = (currentPage - 1) * pageSize + 1;
		return beginRecord;
	}

	public void setBeginRecord(int beginRecord) {
		this.beginRecord = beginRecord;
	}

	public int getEndRecord() {
		endRecord = currentPage * pageSize;
		if (endRecord > totalCount) {
			endRecord = totalCount;
		}
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	/**
	 * 转json对象，过滤多对多集合（map、set等）、外键关联关系
	 * 
	 * @return
	 */
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("total", totalCount);
		json.put("pageSize", pageSize);
		json.put("pageNumber", currentPage);
		json.put("pageList", "[10,20,30,50]");
		
		JSONArray array = new JSONArray();
		JSONObject row = null;
		for (Object obj : dataList) {
			row = new JSONObject();
            for(Class<?> clazz = obj.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            	for (Method method : clazz.getDeclaredMethods()) {
            		//过滤出所有的get方法
                	if (method.getName().startsWith("get")) {
                		String fieldName = method.getName().substring(3);
                		fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toLowerCase())  ; 
                		if (isTrans(method.getReturnType().getName())) {
                			Object value = null;
                			try {
                				value = method.invoke(obj);
                			} catch (Exception e) {
                				e.printStackTrace();
                			}
                			row.put(fieldName, value);
                		}
                	}
            	}
            }  
            array.add(row);
		}
		json.put("rows", array);
		return json.toString();
	}
	
	/**
	 * 转json对象，不过滤任何对象关系，全部转json
	 * 
	 * @return
	 */
	public String toCompleteJSONString() {
		JSONObject json = new JSONObject();
		json.put("total", totalCount);
		json.put("pageSize", pageSize);
		json.put("pageNumber", currentPage);
		json.put("pageList", "[10,20,30,50]");
		json.put("rows", JSONArray.fromObject(dataList));
		return json.toString();
	}
	
	/**
	 * 判断字段是否需要转换
	 * @param fieldClassName
	 * @return
	 */
	private boolean isTrans(String fieldClassName) {
		for (String type : types) {
			if (StringUtil.isNotBlank(type) && type.equals(fieldClassName)) {
				return true;
			}
		}
		return false;
	}
}