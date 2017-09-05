package com.gsww.jup.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * <p>
 * PageSearchEntity描述:分页查询条件对象
 * </p>
 * @company 中国电信甘肃万维公司 
 *
 * @project jup-util
 *
 * @version V2.0.0
 *
 * @author 郭磊(产品部)
 *
 * @date 2012-6-20 上午11:20:38	
 *
 * @class com.gsww.jup.util.PageSearchEntity
 *
 */

public class PageSearchEntity {
	private List<String> paraKey;
	private List<Object> paraValue; 
	private List<String> condList;
	private String orderSql = "";
	private String sql;
	
	public PageSearchEntity() {
		paraKey = new ArrayList<String>();
		paraValue = new ArrayList<Object>();
		condList = new ArrayList<String>();
	}

	public static PageSearchEntity getInstance() { 
			return new PageSearchEntity();
	}
	
	public void addSearch(String pName, Object pValue, String pType) {
		addSearch(pName, pValue, pType, false);
	}
	
	/**
	 * ��Ӳ�ѯ����
	 * @param pName �ֶ����
	 * @param pValue �ֶ�ֵ
	 * @param pType ��ѯ����{}
	 * @param isOr
	 */
	public void addSearch(String pName, Object pValue, String pType, boolean isOr) {
		if (StringUtils.isNotBlank(pName) && pValue != null && !pValue.toString().equals("")) {
			if (StringUtils.isBlank(pType)) {
				paraKey.add(pName + "=?");
				paraValue.add(pValue);
			} else if (pType.equals("like")) {
				paraKey.add(pName + " like ?");
				paraValue.add("%" + pValue + "%");
			} else if (pType.equals("blike")) {
				paraKey.add(pName + " like ?");
				paraValue.add("%" + pValue);
			} else if (pType.equals("elike")) {
				paraKey.add(pName + " like ?");
				paraValue.add(pValue + "%");
			} else if (pType.equals("in")) {
				//in��ѯ�޷���ռλ��ֻ��ֱ��ƴ��sql
				paraKey.add(pName + " in(" + pValue + ")");
			} else if (pType.equals("=")) {
				paraKey.add(pName + "=?");
				paraValue.add(pValue);
			} else if (pType.equals(">")) {
				paraKey.add(pName + ">?");
				paraValue.add(pValue);
			} else if (pType.equals("<")) {
				paraKey.add(pName + "<?");
				paraValue.add(pValue);
			} else if (pType.equals("<>")) {
				paraKey.add(pName + "<>?");
				paraValue.add(pValue);
			} else if (pType.equals("!=")) {
				paraKey.add(pName + "!=?");
				paraValue.add(pValue);
			} else {
				if (pType.indexOf("?") == -1)
					pType += "?";
				paraKey.add(pName + pType);
				paraValue.add(pValue);
			}
			condList.add(isOr ? " or " : " and ");
		}
	}
	
	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public List<String> getParaKey() {
		return paraKey;
	}

	public List<Object> getParaValue() {
		return paraValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<String> getCondList() {
		return condList;
	}

}
