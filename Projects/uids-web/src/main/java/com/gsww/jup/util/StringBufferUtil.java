/**
 * Copyright @2011 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 * 日期 2011-3-4 上午11:06:43
 */
package com.gsww.jup.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * <p>
 * TODO(描述这个类的作用)
 * </p>
 * @company 中国电信甘肃万维公司 
 *
 * @project jup-web
 *
 * @version V2.0.0
 *
 * @author 
 *
 * @date 2012-6-15 上午08:17:19	
 *
 * @class com.gsww.jup.common.sys.util.StringBufferUtil
 *
 */
public class StringBufferUtil
{
	/**
	 * @description 根据Object数组创建StringBuffer对象
	 * @param strs
	 * @return StringBuffer对象
	 * @return StringBuffer
	 * @throws 创建失败
	 */
	public static StringBuffer createStringBuffer(Object... strs)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++)
		{
			sb.append(strs[i] == null ? "null" : strs[i] );
		}
		return sb;
	}
	
	/**
	 * @description 根据Object数组创建String对象
	 * @param strs
	 * @return String对象
	 * @return String
	 * @throws 创建失败
	 */
	public static String createString(Object... strs)
	{
		return createStringBuffer(strs).toString();
	}	
	
	/**
	 * @description 将传入的Set字符串集合进行自然排序,然后拼装为逗号分隔的字符串
	 * @param strs 字符串集合
	 * @return String
	 * @throws 解析失败
	 */
	public static String createSplitStringBySet(Set<String> strs)
	{
		StringBuffer res = new StringBuffer();
		if(CollectionUtils.isNotEmpty(strs))
		{
			String[] strArray = new String[strs.size()];
			strs.toArray(strArray);
			//进行排序
			Arrays.sort(strArray);
			for (String str : strArray)
			{
				res.append(str);
				res.append(",");
			}
			//删除最后一个逗号
			res.deleteCharAt(res.length() - 1);
		}
		return res.toString();
	}
	
	/**
	 * @description 将传入的逗号分隔的字符串进行分解,返回Set集合
	 * @param splitString 传入的含有逗号的字符串
	 * @return Set<String>
	 * @throws 解析失败
	 */
	public static Set<String> createSetBySplitString(String splitString)
	{
		Set<String> strs = new HashSet<String>(1);
		if(StringUtils.isNotBlank(splitString))
		{
			String[] strArray = splitString.split(",");
			//进行排序
			Arrays.sort(strArray);
			strs = new HashSet<String>(strArray.length);
			for (String str : strArray)
			{
				strs.add(str);
			}
		}
		return strs;
	}
}
