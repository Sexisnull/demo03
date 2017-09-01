package com.gsww.uids.gateway.util;

import java.io.IOException;
import java.lang.reflect.Field;
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
	 * 灏唌ap杞崲鎴恓son瀛楃涓�  
	 */ 
	public  String writeMapJSON(Map<String, Object> map) { 
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
	 * 灏唌ap杞崲鎴恓son瀛楃涓�  
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
	 * 灏唋ist杞崲鎴恓son瀛楃涓�  
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
	 * 灏唋ist<Map>杞崲鎴恓son瀛楃涓�  
	 */ 
	public  String writeListMapJSON(List<Map<String,String>> listMap) {
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
	 * 灏嗘暟缁勫璞¤浆鎹㈡垚json瀛楃涓�
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
	 * json瀛楃涓茶浆鎹㈡垚Map  
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
	 * json瀛楃涓茶浆鎹㈡垚Map  
	 */ 
	@SuppressWarnings("unchecked")
	public  Map<String,Object> readJsonMapObject(String jsonString){
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
	 * json瀛楃涓茶浆鎹㈡垚list  
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
	 * json瀛楃涓茶浆鎹㈡垚list<map>  
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
	 * json瀛楃涓茶浆鎹㈡垚Array
	 */
	public String[] readJsonArray(String jsonString) {
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
	

	/**
	 * 鍔熻兘鎻忚堪:閫氳繃浼犲叆涓�涓垪琛ㄥ璞�,璋冪敤鎸囧畾鏂规硶灏嗗垪琛ㄤ腑鐨勬暟鎹敓鎴愪竴涓狫SON瑙勬牸鎸囧畾瀛楃涓�,璇ユ柟娉曢粯璁や笉娓呴櫎鐗规畩瀛楃锛屽鎹㈣绗︺��
	 * 
	 * @param list
	 *            瑕佺敓鎴恓son鐨勫璞￠泦鍚�
	 * @return java.lang.String
	 */
	public String listToJson(List<?> list) {

		return listToJson(list, false);

	}

	/**
	 * 鍔熻兘鎻忚堪:閫氳繃浼犲叆涓�涓垪琛ㄥ璞�,璋冪敤鎸囧畾鏂规硶灏嗗垪琛ㄤ腑鐨勬暟鎹敓鎴愪竴涓狫SON瑙勬牸鎸囧畾瀛楃涓�.
	 * 
	 * @param list
	 *            瑕佺敓鎴恓son鐨勫璞￠泦鍚�
	 * @param isClearSpecialChar
	 *            锛歵rue:娓呴櫎鐗规畩瀛楃锛宖alse涓嶆竻闄ゃ�傜洰鍓嶆敮鎸佸洖杞︼紙鎹㈣绗︼級
	 * 
	 * @return java.lang.String
	 */
	public String listToJson(List<?> list, boolean isClearSpecialChar) {

		StringBuilder json = new StringBuilder();

		json.append("[");

		if (list != null && list.size() > 0) {

			for (Object obj : list) {

				json.append(objectToJson(obj, isClearSpecialChar));

				json.append(",");

			}

			json.setCharAt(json.length() - 1, ']');

		} else {

			json.append("]");

		}

		return json.toString();

	}
	
	/**
	 * 鍔熻兘鎻忚堪:浼犲叆浠绘剰涓�涓� object 瀵硅薄鐢熸垚涓�涓寚瀹氳鏍肩殑瀛楃涓�,鐩墠鏀寔鍥炶溅锛堟崲琛岀锛�
	 * 
	 * @param object
	 *            浠绘剰瀵硅薄
	 * @return java.lang.String
	 */
	public static String objectToJson(Object object, boolean isClearSpecialChar) {

		StringBuilder json = new StringBuilder();

		if (object == null) {

			json.append("\"\"");

		} else if (object instanceof String) {

			json.append("\"").append((String) object).append("\"");

		} else if (object instanceof Boolean || object instanceof Integer) {

			json.append("").append(object).append("");

		} else {

			json.append(beanToJson(object));

		}

		if (isClearSpecialChar)

			return json.toString().replace("\n", "");

		else

			return json.toString();

	}
	
	/**
	 * 鍔熻兘鎻忚堪:浼犲叆浠绘剰涓�涓� javabean 瀵硅薄鐢熸垚涓�涓寚瀹氳鏍肩殑瀛楃涓�
	 * 
	 * @param bean
	 *            bean瀵硅薄
	 * @return String
	 */
	public static String beanToJson(Object bean) {

		StringBuilder json = new StringBuilder();

		json.append("{");

		try {

			Field[] Fields = bean.getClass().getDeclaredFields();

			for (Field field : Fields) {

				String name = objectToJson(field.getName(), false);

				String value = objectToJson(ReflectUtil.getProperty(bean, field.getName()), false);

				json.append(name);

				json.append(":");

				json.append(value);

				json.append(",");

			}

			json.setCharAt(json.length() - 1, '}');

		}

		catch (IllegalArgumentException e) {

			e.printStackTrace();

		}

		return json.toString();

	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String args[])throws Exception{
//		String jsonString  = "{\"app_code\":\"mrgs\",\"fucn_code\":\"NEWS\",\"page_size\":\"6\",\"page_num\":\"14\",\"param_list\":[{\"param_code\":\"lyxl\",\"param_value\":\"鍏板窞鍒板寳浜琝"},{\"param_code\":\"lyxl\",\"param_value\":\"鍏板窞鍒板寳浜琝"}]}";
//		//String ss = "{\"931\":[\"TOURISM\",\"MEDICAL\",\"NEWS_INFO\"],\"934\":[\"TOURISM\",\"MEDICAL\",\"NEWS_INFO\"]}";
//		Map<String,Object> maplist = JSONUtil.readJsonMapObject(jsonString);
//		System.out.println(maplist);
//		System.out.println(maplist.get("param_list"));
//		for(int i=0;i<((List)maplist.get("param_list")).size();i++){
//			System.out.println(((List)maplist.get("param_list")).get(i));
//			System.out.println(((Map)((List)maplist.get("param_list")).get(i)).get("param_code"));
//			System.out.println(((Map)((List)maplist.get("param_list")).get(i)).get("param_value"));
//			//List<Map<String,String>> ssList  = (List)JSONUtil.readJsonListMap(maplist.get("param_list"));
//		}
		//List maplist1 = (List)maplist.get("param_list").;
		//List<Map<String,String>> ssList  = JSONUtil.readJsonListMap(maplist.get("param_list"));
		//System.out.println(ssList);
		/*for(String coder:maplist.keySet()){
			List appList = (List)maplist.get(coder);
			for(int i=0; i<appList.size(); i++){
				System.out.println(appList.get(i));
			}
		}*/
		
	}
}