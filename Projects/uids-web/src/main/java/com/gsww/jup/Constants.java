package com.gsww.jup;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
	/** 保存在session中的用户信息的键名 */
	public static final String SESSION_USER_KEY = "___session_user_key";// 登录帐号对象
	public static final String SESSION_USER_IP = "___session_user_ip"; // 登录IP地址
	public static final String SESSION_USER_AUTHCODE = "___session_user_authcode"; // 登录验证码
	
	/** 日志类型-业务操作日志	 */
	public static final String LOG_TYPE_OPT = "opt"; 
	/** 日志类型-异常日志  */
	public static final String LOG_TYPE_EXP = "exp"; 

	/** 默认分页页数为10 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 默认帐套全部资源权限ID	ACCOUNT_PERMISSION_ID = "P001" */
	public static final String ACCOUNT_PERMISSION_ID = "P001";
	
	/** 系统自动执行是所使用的名称	SYS_NAME = "jup-system" */
	public static final String SYS_NAME = "jup-system";

	/** 用户组状态 */
	public static final String EFFERT = "1";
	public static final String UNEFFERT = "0";

	/** 数据有效状态 */
	public static final String DATA_STATE_AVAIL = "1";
	public static final String DATA_STATE_INVALID = "0";

	/** 参数等级 系统级 00A */
	public static final String PARAMETER_LEVEL_SYS = "00A";
	/** 参数等级 全局级 00B */
	public static final String PARAMETER_LEVEL_GLOBAL = "00B";
	/** 参数等级 帐套级 00C */
	public static final String PARAMETER_LEVEL_SET = "00C";
	/** 参数等级 用户级 00E */
	public static final String PARAMETER_LEVEL_USER = "00D";

	/** 参数类别 二进制 00A */
	public static final String PARAMETER_TYPE_BYTE = "00A";
	/** 参数类别 文本 00B */
	public static final String PARAMETER_TYPE_TEXT = "00B";
	/** 参数类别 有序列表 00C */
	public static final String PARAMETER_TYPE_LIST = "00C";
	/** 参数类别 键值对 00D */
	public static final String PARAMETER_TYPE_MAP = "00D";
	
	/** 存放二进制的路径名称  parameterImgs */
	public static final String PARAMETER_DIR_NAME = "parameterImgs";

	/** 参数无归属对象时,其OWNER_ID列的默认值-1 系统级以及全局级使用 */
	public static final String PARAMETER_NO_OWNER_ID = "-1";

	/** 参数归属ID的分隔符, OWNER_SPLIT_CHAR = "," 格式：帐套ID,组织ID,用户ID */
	public static final String OWNER_SPLIT_CHAR = ",";

	/** 参数类级别显示映射 */
	public static final Map<String, String> PARAMETER_LEVEL_LABEL = new LinkedHashMap<String, String>(5);

	/** 参数类类别显示映射 */
	public static final Map<String, String> PARAMETER_TYPE_LABEL = new LinkedHashMap<String, String>(4);

	/** 有效状态 */
	public static final String STATE_VALID = "00A";
	/** 无效状态 */
	public static final String STATE_INVALID = "00B";

	public static final String GROUP_TYPE_UNIT = "00A";
	public static final String GROUP_TYPE_USER = "00B";
	
	/** 无图片是显示的图片路径 */
	public static final String NULL_IMG_PATH = "res/images/nullimage.jpg";
	
	static {
		// 初始化参数级别资源
		PARAMETER_LEVEL_LABEL.put(PARAMETER_LEVEL_SYS, "系统级");
		PARAMETER_LEVEL_LABEL.put(PARAMETER_LEVEL_GLOBAL, "全局级");
		PARAMETER_LEVEL_LABEL.put(PARAMETER_LEVEL_SET, "帐套级");
		PARAMETER_LEVEL_LABEL.put(PARAMETER_LEVEL_USER, "用户级");

		// 初始化参数类别资源
		PARAMETER_TYPE_LABEL.put(PARAMETER_TYPE_BYTE, "图片");
		PARAMETER_TYPE_LABEL.put(PARAMETER_TYPE_TEXT, "文本");
		PARAMETER_TYPE_LABEL.put(PARAMETER_TYPE_LIST, "有序列表");
		PARAMETER_TYPE_LABEL.put(PARAMETER_TYPE_MAP, "键值对");
	}
}
