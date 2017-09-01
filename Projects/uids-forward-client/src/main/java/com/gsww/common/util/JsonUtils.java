package com.gsww.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.json.JSONObject;

import net.sf.json.JSONArray;

public class JsonUtils {
	/**
	 * 拼接easyui中datagride所需的json串，主要是因为jsonArray的fromObject方法转换json时候存在死循环问题
	 */
	public String datagridJson (JSONArray jsonArray) {
		String json = jsonArray.toString().replace("dataList", "rows");
		String jsonAndProperties = json.substring(1, json.length() - 2) + "," + "\"pageList\"" + ":" + "[10,20,30,50]" + "}";
		String jsonChange = jsonAndProperties.replace("totalCount", "total");
		return jsonChange.replace("currentPage", "pageNumber");
	}
	
	public static <T> JSONObject  montage(T entity){
		JSONObject obj = new JSONObject();
		@SuppressWarnings("unchecked")
		Class<? extends String> cls = (Class<? extends String>) entity.getClass();
        // 获取类中的所有定义字段  
        Field[] fields =cls.getDeclaredFields( );
		for(int i=0 ;i<fields.length;i++){
			Field field = fields[i];//获得属性名
			String s1 = field.toString();
			if(s1.indexOf( "static" ) >= 0){
				continue;
			}
			// 如果不为空，设置可见性，然后返回  
			field.setAccessible( true );
			String columnName = field.getName( );
			try {
				Field tableColumn = cls.getDeclaredField(columnName);
				Type s2 = tableColumn.getGenericType();
				if ( s2.toString().indexOf("entity") == -1 ){//说明该列不是实体
					Object object = getFieldValueByName(columnName, entity);
					obj.put(columnName, object);
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//根据属性名获得当前列
//			if(){
//				obj.put(""+s+"", getFieldValueByName(s,cls));
//			}
		}
		return obj;
	}
	/** 
	 * 根据属性名获取属性值 
	 * */  
	   private static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	           return null;    
	       }    
	   }  
}
