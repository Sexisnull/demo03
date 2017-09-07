package com.gsww.jup.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSetToJson {

	public static String resultSetToJson(ResultSet rs) throws Exception
	 
    {
 
       // json数组
 
       JSONArray array = new JSONArray();
 
       
 
       // 获取列数
 
       ResultSetMetaData metaData = rs.getMetaData();
 
       int columnCount = metaData.getColumnCount();
 
       
 
       // 遍历ResultSet中的每条数据
 
        while (rs.next()) {
 
            JSONObject jsonObj = new JSONObject();
 
            
 
            // 遍历每一列
 
            for (int i = 1; i <= columnCount; i++) {
 
                String columnName =metaData.getColumnLabel(i);
 
                String value = rs.getString(columnName);
 
                jsonObj.put(columnName, value);
 
            } 
 
            array.put(jsonObj); 
 
        }
 
       
 
       return array.toString();
 
    }
}
