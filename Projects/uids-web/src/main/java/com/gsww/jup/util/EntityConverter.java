package com.gsww.jup.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 实体对象转换器，根据查询字段填充实体属性，
 * 用于提高HQL或JPQL查询效率
 * @author dingjl
 * 2015.04.20
 */
public class EntityConverter {
	/**
	 * 查询语句
	 */
	private String querySql;
	/**
	 * 查询数据结果集
	 */
	@SuppressWarnings("rawtypes")
	private List dataList;
	/**
	 * 包名+实体类名
	 */
	private String dtoName;
	
	@SuppressWarnings("rawtypes")
	public EntityConverter(String querySql,List dataList,String dtoName){
		this.querySql = querySql;
		this.dataList = dataList;
		this.dtoName = dtoName;
	}
	
	/**
	 * 获取查询字段
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private List getQueryColumns() throws Exception{
		List columns = new ArrayList();
		if(this.querySql == null){
			return columns;
		}
		return addColumns(columns);
	}
	/**
	 * 拼装查询字段
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List addColumns(List list) throws Exception{
		if(list == null){
			list = new ArrayList();
		}
		String tmpStr = this.querySql.replace(" ", "");
		//正则表达式匹配查询语句
		Pattern pattern = Pattern.compile("^select.*from.*$");
        Matcher matcher = pattern.matcher(tmpStr.toLowerCase());
        if(matcher.matches()){
        	String cols = tmpStr.substring(6, tmpStr.toLowerCase().indexOf("from"));
        	String[] colArry = cols.split(",");
        	for(String col:colArry){
        		if(col.contains(".")){
        			list.add(col.substring(col.indexOf(".")+1));
        		}else{
        			list.add(col);
        		}
        	}
        }else{
        	throw new Exception("The query is not standard");
        }

		return list;
	}
	/**
	 * 实体对象转换结果
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getConvertResult(){
		Object retObj = null;
		if (dataList.size() == 0) {
			retObj = new ArrayList();
		}
		try {
			List columns = getQueryColumns();
			for (int i = 0; i < dataList.size(); i++) {
				LinkedHashMap lMap = new LinkedHashMap();
				if(dataList.get(i) instanceof Object[]){
					Object[] object = (Object[]) dataList.get(i);
					for (int j = 0; j < object.length; j++) {
						lMap.put(columns.get(j), object[j]);
					}
				}else{
					lMap.put(columns.get(0), dataList.get(i));
				}
				Class cl = Class.forName(this.dtoName);
				Object targetObj = (Object) cl.newInstance();
				PropertyUtils.copyProperties(targetObj, lMap);
				if (retObj == null)
					retObj = new ArrayList();
				if (targetObj != null)
					((ArrayList) retObj).add(targetObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return retObj;
	}
	/**
	 * Jdbc查询结果转换
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getJdbcConvertResult(){
		Object retObj = null;
		if (dataList.size() == 0) {
		   retObj = new ArrayList();
		}
		Iterator iter = dataList.iterator();
		while (iter.hasNext()) {
			LinkedHashMap lMap = new LinkedHashMap();
			Map map = (Map) iter.next();
			Iterator mIter = map.entrySet().iterator();
			while (mIter.hasNext()) {
				Map.Entry me = (Entry) mIter.next();
				String key = me.getKey().toString().toLowerCase();
				Object value = (Object) me.getValue();
				if(key.contains("_")){
					key = replaceSpecialCharAndfirstToUpper(key, "_", "");
				}
				lMap.put(key, value);
			}
			   Object targetObj = null;
			   try {
					Class cl = Class.forName(dtoName);
					targetObj = (Object) cl.newInstance();
					PropertyUtils.copyProperties(targetObj, lMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (retObj == null)
					retObj = new ArrayList();
				if (targetObj != null)
					((ArrayList) retObj).add(targetObj);
			}
		return retObj;
	}
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String firstCharToUpper(String str) {  
		   return str.substring(0, 1).toUpperCase() + str.substring(1);  
		}
	/**
	 * 数据库字段转换成实体属性字段
	 * @param oldStr
	 * @param rex
	 * @param rep
	 * @return
	 */
	public static String replaceSpecialCharAndfirstToUpper(String oldStr,String rex,String rep){  
		   String newStr = "";  
		   int first=0;  
		   while(oldStr.indexOf(rex)!=-1)  
		   {  
		    first=oldStr.indexOf(rex);  
		    if(first!=oldStr.length())  
		    {  
		     newStr=newStr+oldStr.substring(0,first)+rep;  
		     oldStr=oldStr.substring(first+rex.length(),oldStr.length());  
		     oldStr=firstCharToUpper(oldStr);  
		    }  
		   }  
		   newStr=newStr+oldStr;  
		   return newStr;  
		}
}
