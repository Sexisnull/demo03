package com.gsww.common.entity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 56个民族
 * 
 * @author taolm
 *
 */
public enum NationalityEnum {
	// 所有的民族
	HAN(1, "汉族"),
	ZHUANG(2, "壮族"),
	HUI(3, "回族"),
	MAN(4, "满族"),
	WERWUER(5, "维吾尔族"),
	MIAO(6, "苗族"),
	YI(7, "彝族"),
	TUJIA(8, "土家族"),
	ZANG(9, "藏族"),
	MONGOLIA(10, "蒙古族"),
	DONG(11, "侗族"),
	BUYI(12, "布依族"),
	YAO(13, "瑶族"),
	BAI(14, "白族"),
	CHAOXIAN(15, "朝鲜族"),
	HANI(16, "哈尼族"),
	LI(17, "黎族"),
	HASAKE(18, "哈萨克族"),
	DAI(19, "傣族"), 
	SHE(20, "畲族"), 
	LISU(21, "傈僳族"),
	DONGXIANG(22, "东乡族"),
	GELAO(23, "仡佬族"),
	LAHU(24, "拉祜族"),
	WA(25, "佤族"),
	SHUI(26, "水族"),
	NAXI(27, "纳西族"),
	QIANG(28, "羌族"),
	TU(29, "土族"),
	MULAO(30, "仫佬族"),
	XIBO(31, "锡伯族"),
	KEERKEZI(32, "柯尔克孜族"),
	JINGPO(33, "景颇族"),
	DAWOER(34, "达斡尔族"),
	SALA(35, "撒拉族"),	
	BULANG(36, "布朗族"),
	MAONAN(37, "毛南族"),
	TAJIKE(38, "塔吉克族"),
	PUMI(39, "普米族"),
	ACANG(40, "阿昌族"),
	NU(41, "怒族"),
	EWENKE(42, "鄂温克族"),
	JING(43, "京族"),
	JINUO(44, "基诺族"),
	DEANG(45, "德昂族"),
	BAOAN(46, "保安族"),
	ELUOSI(47, "俄罗斯族"),
	YUGU(48, "裕固族"),
	WUZIBIEKE(49, "乌孜别克族"),
	MENBA(50, "门巴族"),
	ELUNCHUN(51, "鄂伦春族"),
	DULONG(52, "独龙族"),
	HEZHE(53, "赫哲族"),
	GAOSHAN(54, "高山族"),
	LUOBA(55, "珞巴族"),
	TATAER(56, "塔塔尔族"),
	UNKNOWN(57, "未识别民族"),
	FOREIGN(58, "入籍外国人");
	
	/**
	 * 民族编号
	 */
	private int number;
	
	/**
	 * 民族中文名称
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private NationalityEnum(int number, String value) {
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
		for ( NationalityEnum element : NationalityEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 根据中文名称获取编号
	 * 
	 * @param number
	 * @return
	 */
	public static int numberOf(String value) {
		for ( NationalityEnum element : NationalityEnum.values() ) {
			if ( element.getValue().equalsIgnoreCase(value)) {
				return element.getNumber();
			}
		}
		
		return 1;
	}
	
	/**
	 * 获得对象的json数组
	 */
	public static JSONArray getValueArray() {
		JSONArray array = new JSONArray();
		
		JSONObject object = null;
		for ( NationalityEnum element : NationalityEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
			array.put(object);
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
