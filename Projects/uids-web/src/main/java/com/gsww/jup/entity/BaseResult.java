package com.gsww.jup.entity;



/**
 * 爱校园升级接口返回实体基类,定义所有返回码.
 * 
 * @author sxg
 */

public class BaseResult {

	// -- 返回代码定义 --//
	// 按项目的规则进行定义, 比如4xx代表客户端参数错误，5xx代表服务端业务错误等.
	public static final String SUCESS = "0";
	public static final String SUCESS_MESSAGE = "数据请求成功！";
	public static final String PARAMETER_ERROR = "400";
	public static final String PARAMETER_ERROR_MESSAGE = "参数错误";
	public static final String SYSTEM_ERROR = "500";
	public static final String SYSTEM_ERROR_MESSAGE = "服务器内部异常。";

	// -- WSResult基本属性 --//
	protected String ret = SUCESS;
	protected String msg = SUCESS_MESSAGE;
	
//	@JsonProperty("MESSAGE")
/*	@JSONField(name="content")
	protected Object content;*/

	/**
	 * 创建结果.
	 */
	public void setError(String ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}

	/**
	 * 创建参数错误结果.
	 */
	public void setParameterError() {
		setError(PARAMETER_ERROR, PARAMETER_ERROR_MESSAGE);
	}
	/**
	 * 创建默认异常结果.
	 */
	public void setDefaultError() {
		setError(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE);
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
//	public Object getContent() {
//		return content;
//	}
//
//	public void setContent(Object content) {
//		this.content = content;
//	}

//	@Override
//	public String toString() {
//		
//		 SerializerFeature[] features = {
//		    SerializerFeature.WriteMapNullValue, // 输出空置字段
//			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是nullSerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是nullSerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
//			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
//			}; // 序列化为和JSON-LIB兼容的字符串
//		return JSON.toJSONString(this,features);
//	}

}