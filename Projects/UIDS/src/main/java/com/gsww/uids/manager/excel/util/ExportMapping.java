package com.gsww.uids.manager.excel.util;

import java.util.HashMap;
import java.util.Map;

public  class ExportMapping {
	public static String getOrgPropertiesName (String exportFileds) {
		Map<String, Object> map = new HashMap<String, Object>();
		String exportName = "";
		map.put("shortName", "机构简称");
		map.put("fullName", "机构全称");
		map.put("type", "节点类型");
		map.put("sequence", "机构排序");
		map.put("area.name", "所属区域");
		map.put("area.code", "区域编码");
		map.put("suffix", "机构后缀");
		map.put("code", "机构编码");
		map.put("parent.name", "父级机构名称");
		map.put("parentIs", "是否父级机构");
		map.put("desc", "机构描述");
		String [] files = exportFileds.split(",");
		for (String property : files) {
			exportName = exportName + "," + map.get(property);
		}
		return exportName.substring(1, exportName.length());
	}
	public static String getAccountPropertiesName (String exportFileds) {
		Map<String, Object> map = new HashMap<String, Object>();
		String exportName = "";
		map.put("type", "账号类型");
		map.put("app.name", "账号来源系统");
		map.put("name", "账号名称");
		map.put("nickname", "账号昵称");
		map.put("password", "账号密码");
		map.put("status", "账号状态");
		map.put("org.fullName", "所属机构");
		map.put("org.code", "所属机构编码");
		map.put("post", "账号职务");
		String [] files = exportFileds.split(",");
		for (String property : files) {
			exportName = exportName + "," + map.get(property);
		}
		return exportName.substring(1, exportName.length());
	}
}
