package com.gsww.common.enums;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 区域类型枚举类
 * 
 * @author sunbw
 *
 */
public enum AreaEnum {
	
	
	GUOJIA(1, "中国"),
	//省级行政区
	SHENG(2,"省"),
	ZHIXIASHI(22,"直辖市"),
	ZIZHIQU(222,"自治区"),
	//地级行政区划单位
	DIJISHI(3,"地级市"),
	ZIZHIZHOU(33,"自治州"),
	MENG(333,"盟"),
	//县级行政区划单位
	SHIXIAQU(4,"市辖区"),
	XIANJISHI(44,"县级市"),
	XIAN(444,"县"),
	ZIZHIXIAN(4444,"自治县"),
	QI(44444,"旗"),
	ZIZHIQI(444444,"自治旗"),
	TEQU(4444444,"特区"),
	LINQU(44444444,"林区"),
	//乡级行政区划单位
	QUGONGSUO(5,"区公所"),
	JIEDAO(55,"街道"),
	ZHEN(555,"镇"),
	XIANG(5555,"乡"),
	MINZUXIANG(555555,"民族乡"),
	SUMU(5555555,"苏木"),
	MINZUSUMU(55555555,"民族苏木"),
	QITA(6, "其他");
	
	/**
	 * 区编号
	 */
	private int number;
	
	/**
	 * 区域中文名称
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private AreaEnum(int number, String value) {
		this.number = number;
		this.value = value;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOf(int number) {
		for ( AreaEnum element : AreaEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 获得对象的json数组
	 */
	public static JSONArray getValueArray() {
		JSONArray array = new JSONArray();
		
		JSONObject object = null;
		for ( AreaEnum element : AreaEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
			array.put(object);
		}
		
		return array;
	}
	
	
	/**
	 * 获取上一级列表
	 * @param number 传入等级
	 * @return
	 */
	public static JSONArray findLastList(String level){
		int lastlevel = Integer.parseInt(level)%10 - 1;
		return findList(lastlevel);
	}
	
	/**
	 * 获取当前列表
	 * @param number 传入等级
	 * @return
	 */
	public static JSONArray findNowList(String level){
		int lastlevel = Integer.parseInt(level)%10;
		return findList(lastlevel);
	}
	
	/**
	 * 获取下一级列表
	 * @param number 传入等级
	 * @return
	 */
	public static JSONArray findNextList(String level){
		int lastlevel = Integer.parseInt(level)%10 + 1;
		return findList(lastlevel);
	}
	
	/**
	 * 根据级别获得上级列表
	 * @param level 等级
	 * @return
	 */
	private static  JSONArray findList(int level){
		JSONArray array = new JSONArray();
		JSONObject object = null;
		if(level > 6){
			JSONObject other = new JSONObject();
			other.put("number", level);
			other.put("value", "其他");
			array.put(other);
			return array;
		}else{
			for ( AreaEnum element : AreaEnum.values() ) {
				object = new JSONObject();
				if(String.valueOf(element.number).startsWith(String.valueOf(level))){
					object.put("number", element.number);
					object.put("value", valueOf(element.number));
				}
				if(object.length() > 0){
					array.put(object);
				}
			}
		}
		return array;
	}
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
