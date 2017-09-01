package com.gsww.uids.gateway.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConvertSqlToDtoList {
	
	public static Object ExeSQL2List(JdbcTemplate jdbc,String sqlStr, Object[] objs, String dtoStr){
		Object retObj = null;
		List list = jdbc.queryForList(sqlStr, objs);
		if (list.size() == 0) {
		   retObj = new ArrayList();
		}
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			LinkedHashMap lMap = new LinkedHashMap();
			Map map = (Map) iter.next();
			Iterator mIter = map.keySet().iterator();
			while (mIter.hasNext()) {
				String key = mIter.next().toString().toLowerCase();
				Object value = (Object) map.get(key);
				if(key.contains("_")){
					key = StringHelper.replaceSpecialCharAndfirstToUpper(key, "_", "");
				}
				lMap.put(key, value);
			}
			   Object targetObj = null;
			   try {
					Class cl = Class.forName(dtoStr);
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
}
