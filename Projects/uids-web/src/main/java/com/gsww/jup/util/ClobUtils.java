package com.gsww.jup.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;

import org.hibernate.Hibernate;

/**
 * 
 * <p>
 * TODO(描述这个类的作用)
 * </p>
 * @company 中国电信甘肃万维公司 
 *
 * @project jup-util
 *
 * @version V2.0.0
 *
 * @author 
 *
 * @date 2012-6-19 下午05:07:24	
 *
 * @class com.gsww.jup.util.ClobUtils
 *
 */
public class ClobUtils {
	
	/**
	 * <h4> 将Clob转为字符串 </h4>
	 * @param clob
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public static String clobToStr(Clob clob)throws Exception{
		if(clob == null){
			return "";
		}
		Reader reader = null;
		try {
			StringBuffer str = new StringBuffer();
			reader = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(reader,1024);
			String temp = br.readLine();
			while (temp != null) {
				str.append(temp);
				temp = br.readLine();
			}
			br.close();
			return str.toString();
		} catch (Exception e){
			throw e;
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
}
