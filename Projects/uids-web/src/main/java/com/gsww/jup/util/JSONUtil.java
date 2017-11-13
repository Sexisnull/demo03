package com.gsww.jup.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {

	/**  
	 * 将map转换成json字符串  
	 */ 
	public static String writeMapJSON(Map<String, Object> map) { 
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(map); 
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return jsonString;
	} 
	
	/**  
	 * 将map转换成json字符串  
	 */ 
	public  String writeMapSJSON(Map<String, String> map) { 
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(map); 
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return jsonString;
	} 
	
	/**  
	 * 将list转换成json字符串  
	 */ 
	public  String writeListJSON(List<String> list) {
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(list);  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return jsonString;
	} 
	
	/**  
	 * 将list<Map>转换成json字符串  
	 */ 
	public static  String writeListMapJSON(List<Map<String,String>> listMap) {
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(listMap);  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return jsonString;
	} 
	/**  
	 * 将list<Map>转换成json字符串  
	 */ 
	public static String writeListMapJSONMap(List<Map<String,Object>> listMap) {
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(listMap);  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return jsonString;
	} 
	/**
	 * 将数组对象转换成json字符串
	 */
	public  String writeArrayJSON(String[] array) {
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
	    try {
	    	jsonString = objectMapper.writeValueAsString(array);    
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return jsonString;
	}
	
	/**  
	 * json字符串转换成Map  
	 */ 
	@SuppressWarnings("unchecked")
	public  Map<String,String> readJsonMap(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,String> map = new LinkedHashMap<String,String>();
		try{
			map = objectMapper.readValue(jsonString, Map.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return map;
	}
	
	/**  
	 * json字符串转换成Map  
	 */ 
	@SuppressWarnings("unchecked")
	public static  Map<String,Object> readJsonMapObject(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		try{
			map = objectMapper.readValue(jsonString, Map.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return map;
	}
	
	/**  
	 * json字符串转换成list  
	 */ 
	@SuppressWarnings("unchecked")
	public  List<String> readJsonList(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> list = new ArrayList<String>();
		try{
			list = objectMapper.readValue(jsonString, List.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public  List<Object> readJsonListObject(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Object> list = new ArrayList<Object>();
		try{
			list = objectMapper.readValue(jsonString, List.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return list;
	}
	
	/**  
	 * json字符串转换成list<map>  
	 */ 
	@SuppressWarnings("unchecked")
	public  List<Map<String, String>> readJsonListMap(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try{
			list = objectMapper.readValue(jsonString, List.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return list;
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> readJsonListMapObj(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			list = objectMapper.readValue(jsonString, List.class);  
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return list;
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, Object>> readJsonToMapObj(String jsonString){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Map<String, Object>> list = null;
		try{
			list = objectMapper.readValue(jsonString,Map.class); 
		}catch (JsonParseException e) {  
			e.printStackTrace();  
		} catch (JsonMappingException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return list;
	}
	/**
	 * json字符串转换成Array
	 */
	public static String[] readJsonArray(String jsonString) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    String[] array = null;
	    try {
	        array = objectMapper.readValue(jsonString, String[].class);
	    } catch (JsonParseException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return array;
	}
	
	public static String appendJsonString(String jsonStr,Map<String,String> map){
		String jsonString = "";
		String jsonTemp = "";
		try{
			if(!map.isEmpty()){
				Set<String> key = map.keySet();
		        Iterator<String> iter = key.iterator();
		        while (iter.hasNext()) {
		            String field = iter.next();
		        	jsonTemp += "\""+field+"\":"+"\""+map.get(field)+"\",";
		        }
		        jsonTemp = jsonTemp.substring(0, jsonTemp.lastIndexOf(","));
			}
			if(StringUtils.isNotBlank(jsonStr)){
				jsonString = jsonStr.substring(0, jsonStr.lastIndexOf("}"))+","+jsonTemp+"}";
			}else{
				jsonString = jsonTemp;
			}
		}catch(Exception e) {
	        e.printStackTrace();
	    }
		return jsonString;
	}
	
	public String writeListMapSJSON(List<Map<String, Object>> firstList) {
		String jsonString ="";
		ObjectMapper objectMapper = new ObjectMapper();
		try {  
			jsonString = objectMapper.writeValueAsString(firstList);  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return jsonString;
	}
}