package com.gsww.uids.gateway.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper {

	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	/**
	 * 灏嗗瓧绗︿覆閲嶅Copy鍑犳锛屽舰鎴愭柊鐨勫瓧绗︿�?
	 * 
	 * @param string
	 *            瀛楃涓�?	 * @param times
	 *            閲嶅娆℃暟
	 * 
	 * 
	 */
	public static String repeat(String string, int times) {
		StringBuffer buf = new StringBuffer(string.length() * times);
		for (int i = 0; i < times; i++)
			buf.append(string);
		return buf.toString();
	}

	/**
	 * 灏嗗瓧绗︿覆涓殑鏌愪釜瀛楃涓叉浛鎹负鍙�?��涓�釜�?楃涓�
	 * 
	 * @param template
	 *            鎿嶄綔鐨勫瓧绗︿�?
	 * @param placeholder
	 *            琚浛鎹㈢殑瀛楃涓�?	 * @param replacement
	 *            鏇挎崲鐨勭殑瀛楃涓�?	 * 
	 */
	public static String replace(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc)).append(
					replacement).append(
					replace(template.substring(loc + placeholder.length()),
							placeholder, replacement)).toString();
		}
	}

	/**
	 * 灏嗗瓧绗︿覆涓殑鏌愪釜瀛楃涓叉浛鎹负鍙�?��涓�釜�?楃涓诧紝浠呰鏇挎崲涓�
	 * 
	 * @param template
	 *            鎿嶄綔鐨勫瓧绗︿�?
	 * @param placeholder
	 *            琚浛鎹㈢殑瀛楃涓�?	 * @param replacement
	 *            鏇挎崲鐨勭殑瀛楃涓�?	 * 
	 */

	public static String replaceOnce(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc)).append(
					replacement).append(
					template.substring(loc + placeholder.length())).toString();
		}
	}

	/**
	 * 灏嗗瓧绗︿覆鍒嗗壊锛岃繑鍥炴暟缁�?	 * 
	 * @param seperators
	 *            鍒嗗壊�?楃
	 * @param list
	 *            瀛楃涓�?	 * 
	 * 
	 * 
	 */
	public static String[] split(String seperators, String list) {
		StringTokenizer tokens = new StringTokenizer(list, seperators);
		// System.out.println(tokens.countTokens());
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}

	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}

	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0) {
			return "";
		} else {
			return qualifiedName.substring(0, loc);
		}
	}

	public static String root(String qualifiedName) {
		int loc = qualifiedName.indexOf(".");
		return (loc < 0) ? qualifiedName : qualifiedName.substring(0, loc);
	}

	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	public static String toString(Object[] array) {
		int len = array.length;
		if (len == 0)
			return "";
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(", ");
		}
		return buf.append(array[len - 1]).toString();
	}

	public static String[] multiply(String string, Iterator placeholders,
			Iterator replacements) {
		String[] result = new String[] { string };
		while (placeholders.hasNext()) {
			result = multiply(result, (String) placeholders.next(),
					(String[]) replacements.next());
		}
		return result;
	}

	private static String[] multiply(String[] strings, String placeholder,
			String[] replacements) {
		String[] results = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++) {
				results[n++] = replaceOnce(strings[j], placeholder,
						replacements[i]);
			}
		}
		return results;
	}

	/**
	 * 鍙栧緱鏌愪竴涓狢har瀛楃鍦ㄥ瓧绗︿覆涓殑涓�?
	 * 
	 */
	public static int getValueCount(String str, char c) {
		int n = 0;
		for (int i = 0; i < str.length(); i++) {
			char a = str.charAt(i);
			if (a == c) {
				n++;
			}
		}
		return n;
	}

	public static String toString(Object obj) {
		if (obj == null || obj.toString().equals("")
				|| obj.toString().equals("null")) {
			return "";
		} else {
			String objValue = obj.toString().trim();
			return objValue;
		}
	}

	/** 鎴彇姹夊瓧鎴栨眽�?椼�瀛楃娣峰悎涓茬殑鍓峮浣嶏紝濡傛灉绗琻浣嶄负鍙屽瓧鑺傚瓧绗︼紝鍒欐埅鍙栧墠n-1浣�
	 * @throws UnsupportedEncodingException */
	public static String leftCut(String str, int n) throws UnsupportedEncodingException {
		byte[] b_str = str.getBytes("UTF-8");
		byte[] new_str;
		int k;
		if (b_str.length < n) {
			return str;
		} else {
			if (b_str[n] < 0 && b_str[n - 1] > 0) {
				k = n - 1;
			} else {
				k = n;
			}
			new_str = new byte[k];
			for (int i = 0; i < k; i++) {
				new_str[i] = b_str[i];
			}
			return new String(new_str,"utf-8");
		}
	}

	/** 灏�a,b,c,d, 杞崲鎴�?a','b','c','d' */
	public static String addSingleMark(String strContent) {
		String[] str = StringHelper.split(",", strContent);
		String newStr = "";
		for (int i = 0; i < str.length; i++) {
			newStr += "'" + str[i] + "',";
		}
		newStr = newStr.substring(0, newStr.length() - 1);
		return newStr;
	}

	/** 灏嗗洖杞︽崲琛岀鏇挎崲鎴�?TML鎹㈣绗�?*/
	public static String addBr(String Content) {
		String makeContent = new String();
		StringTokenizer strToken = new StringTokenizer(Content, "\n");
		while (strToken.hasMoreTokens()) {
			makeContent = makeContent + "<br>" + strToken.nextToken();
		}
		return makeContent;
	}

	/** 灏咹TML鎹㈣绗︽浛鎹㈡垚鍥炶溅鎹㈣绗�?*/
	public static String subtractBr(String Content) {
		String makeContent = new String();
		StringTokenizer strToken = new StringTokenizer(Content, "<br>");
		while (strToken.hasMoreTokens()) {
			makeContent = makeContent + "\n" + strToken.nextToken();
		}
		return makeContent;
	}

	/**
	 * 妫�煡�?楃涓叉槸鍚︿负闈為浂闀垮害鐨勬湁鏁堝瓧绗︿�?
	 * 
	 * @param var
	 *            闇�鏌ョ殑瀛楃涓�?	 * @return 涓嶄负绌哄瓧绗︿覆杩斿洖true锛屽惁鍒欒繑鍥�?alse
	 */
	public static boolean checkString(String var) {
		boolean bRtn = true;
		if (var == null) {
			bRtn = false;
		} else {
			if (var.length() < 1)
				bRtn = false;
		}
		return bRtn;
	}

	/**
	 * 妫�煡�?楃涓叉槸鍚︽槸鍚堟硶鏁存暟
	 * 
	 * @param var
	 *            浼犲叆闇�妫�煡鐨勫瓧绗︿�?
	 * @return 濡傛灉涓哄悎娉曟暣鏁拌繑鍥�?rue锛屽惁鍒欒繑鍥�?alse
	 */
	public static boolean checkInt(String var) {
		boolean bRtn = true;
		try {
			if (Integer.parseInt(var) > Integer.MAX_VALUE
					|| Integer.parseInt(var) < Integer.MIN_VALUE)
				bRtn = false;
		} catch (Exception e) {
			bRtn = false;
		}
		return bRtn;
	}

	public static boolean checkLong(String var) {
		boolean bRtn = true;
		try {
			Long.parseLong(var);
			bRtn = true;
		} catch (Exception e) {
			bRtn = false;
		}
		return bRtn;
	}

	public static boolean checkFloat(String var) {
		boolean bRtn = true;
		try {
			Float.parseFloat(var);
			bRtn = true;
		} catch (Exception e) {
			bRtn = false;
		}
		return bRtn;
	}

	public static boolean checkDouble(String var) {
		boolean bRtn = true;
		try {
			Double.parseDouble(var);
			bRtn = true;
		} catch (Exception e) {
			bRtn = false;
		}
		return bRtn;
	}

	public static boolean isNumeric(String str) {
		int begin = 0;
		boolean once = true;
		if (str == null || str.trim().equals("")) {
			return false;
		}
		str = str.trim();
		if (str.startsWith("+") || str.startsWith("-")) {
			if (str.length() == 1) {
				// "+" "-"
				return false;
			}
			begin = 1;
		}
		for (int i = begin; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				if (str.charAt(i) == '.' && once) {
					// '.' can only once
					once = false;
				} else {
					return false;
				}
			}
		}
		if (str.length() == (begin + 1) && !once) {
			// "." "+." "-."
			return false;
		}
		return true;
	}

	/**
	 * 妫�煡鏄惁鏄湁鏁堢殑鐢靛瓙閭欢鏍煎�?
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkMail(String str) {
		Pattern p = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑鐢佃瘽鍙风爜
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPhone(String str) {
		Pattern p = Pattern
				.compile("^(\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})?(-\\d+)?$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean checkMobile(String str) {
		Pattern p = Pattern.compile("^13\\d{9}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑韬唤璇佸彿鐮�
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIDCard(String str) {

		class IDCard {
			final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
					4, 2, 1 };

			final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };

			private int[] ai = new int[18];

			public IDCard() {
			}

			public boolean Verify(String idcard) {
				if (idcard.length() == 15) {
					idcard = uptoeighteen(idcard);
				}
				if (idcard.length() != 18) {
					return false;
				}
				String verify = idcard.substring(17, 18);
				if (verify.equals(getVerify(idcard))) {
					return true;
				}
				return false;
			}

			public String getVerify(String eightcardid) {
				int remaining = 0;
				if (eightcardid.length() == 18) {
					eightcardid = eightcardid.substring(0, 17);
				}
				if (eightcardid.length() == 17) {
					int sum = 0;
					for (int i = 0; i < 17; i++) {
						String k = eightcardid.substring(i, i + 1);
						ai[i] = Integer.parseInt(k);
					}
					for (int i = 0; i < 17; i++) {
						sum = sum + wi[i] * ai[i];
					}
					remaining = sum % 11;
				}
				return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
			}

			public String uptoeighteen(String fifteencardid) {
				String eightcardid = fifteencardid.substring(0, 6);
				eightcardid = eightcardid + "19";
				eightcardid = eightcardid + fifteencardid.substring(6, 15);
				eightcardid = eightcardid + getVerify(eightcardid);
				return eightcardid;
			}

		}

		IDCard idCard = new IDCard();
		return idCard.Verify(str);
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑缃戦〉鍦板潃
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkURL(String str) {
		Pattern p = Pattern.compile("^[\\w\\.]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean checkURL2(String str) {
		Pattern p = Pattern
				.compile("^[a-zA-Z]+://([\\w\\-\\+%]+\\.)+[\\w\\-\\+%]+(:\\d+)?(/[\\w\\-\\+%])*(/.*)?$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑閭斂缂栫爜
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkZIP(String str) {
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑瀹㈡埛甯愬彿
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkAccount(String str) {
		Pattern p = Pattern.compile("^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,30}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑瀹㈡埛甯愬彿
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkID(String str) {
		Pattern p = Pattern.compile("^[0-9]{4,32}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static List getDiff(List<String> list) {
		List<String> result = new ArrayList<String>();
		String temp;
		for (int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			if (isExist(result, temp))
				continue;
			else
				result.add(temp);

		}
		return result;
	}

	private static boolean isExist(List<String> list, String str) {
		for (String temp : list)
			if (str.equals(temp))
				return true;
		return false;
	}

	/** 鏍￠獙闀垮害灏忎簬len鐨勫瓧绗︿覆;0,鍙互涓虹┖,1,涓嶈兘涓虹┖* */
	public static boolean checkStringLen(String Content, int len,
			String emptyState) {
		try {
			if (emptyState == null || emptyState.equals("0")) {
				if (Content == null)
					return true;
				else if (Content.length() >= len)
					return false;
				else
					return true;
			} else if (emptyState.equals("1")) {
				if (Content == null || Content.trim().equals("")
						|| Content.length() >= len)
					return false;
				else
					return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 楠岃瘉�?楃涓叉槸鍚︿负涓枃�?楃
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		String strTemp = null;
		try {
			strTemp = new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 濡傛灉鍊间负绌猴紝閫氳繃鏍￠�?
		if ("".equals("==") )
			return true;
		Pattern p = Pattern.compile("/[^\u4E00-\u9FA5]/g,''");
		Matcher m = p.matcher(strTemp);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓烘湁鏁堢殑瀹㈡埛甯愬彿
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkEntAccount(String str) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9][\\w]{1,32}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁鍏ㄩ儴涓鸿嫳鏂囧瓧姣�?	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkEn(String str) {
		Pattern p = Pattern.compile("^[A-Za-z]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 妫�煡鏄惁涓哄悎娉曠殑IP
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIp(String str) {
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/** 妫�獙杈撳叆鐨勫�鏁存暟浣嶆渶澶�浣嶏紝灏忔暟浣嶆渶澶�浣�*/
	public static boolean checkMoney(String str) {
		String money = "^(\\d?\\d?\\d?\\d?\\d?\\d?[.]\\d?\\d?)|(\\d?\\d?\\d?\\d?\\d?\\d?)$";
		Pattern p = Pattern.compile(money);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean chenkbanj(String str) {
		String ints = "^(\\d{1,32})$";
		Pattern p = Pattern.compile(ints);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean checkNumBan(String str) {
		Pattern p = Pattern.compile("^(\\d+,)*\\d*$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String showInt(int i) {
		String intStr = String.valueOf(i);
		String[] strArr = intStr.split(",");
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j <= strArr.length; j++) {
			sb.append(strArr[j]);
		}
		return sb.toString();
	}

	public static String splitListToString(String seperators, List list) {
		String temp = list.toString();
		StringBuffer strSb = new StringBuffer();
		String[] arr = split(",", temp);
		for (int i = 0; i < arr.length; i++) {
			strSb.append(arr[i].trim());
		}

		return strSb.toString().substring(1, strSb.length() - 1);
	}

	public static String splitListToStringIncDou(String seperators, List list) {
		String temp = list.toString();
		StringBuffer strSb = new StringBuffer();
		String[] arr = split(",", temp);
		for (int i = 0; i < arr.length; i++) {
			strSb.append(arr[i].trim()).append("\n");
		}

		return strSb.toString().substring(1, strSb.length() - 2);
	}

	public static String numberFormat(Object obj, String pattern) {
		if (obj == null)
			return "";

		if (pattern == null || "".equals(pattern.trim())) {
			pattern = "#.00";
		}
		String str = String.valueOf(obj);
		if (obj instanceof String) {
			DecimalFormat df = new DecimalFormat(pattern);
				try {
					str = df.format(NumberFormat.getInstance().parse(str));
				} catch (ParseException e) {
					e.printStackTrace();
				}
		} else {
			DecimalFormat df = new DecimalFormat(pattern);
			str = df.format(obj);
		}
		return str;
	}
	/**
	 * 
	* @Title: firstCharToUpper
	* @Description: 棣栧瓧姣嶈浆鎴愬ぇ鍐�?	 */
	public static String firstCharToUpper(String str) {  
		   return str.substring(0, 1).toUpperCase() + str.substring(1);  
		}
	/**
	 * 
	* @Title: replaceUnderlineAndfirstToUpper
	* @Description: 鏇挎崲鐗规畩瀛楃骞惰鐗规畩�?楃鍚庝竴涓瓧姣嶅ぇ鍐�?	 */
	public static String replaceSpecialCharAndfirstToUpper(String oldStr,String rex,String rep){  
	   String newStr = "";  
	   int first=0;  
	   while(oldStr.indexOf(rex)!=-1)  
	   {  
	    first=oldStr.indexOf(rex);  
	    if(first!=oldStr.length())  
	    {  
	     newStr=newStr+oldStr.substring(0,first)+rep;  
	     oldStr=oldStr.substring(first+rex.length(),oldStr.length());  
	     oldStr=firstCharToUpper(oldStr);  
	    }  
	   }  
	   newStr=newStr+oldStr;  
	   return newStr;  
	}
	
	/**
     * @param is InputStream 要转换的数据流
     * @param charset String 数据流的编码格式（例如：UTF-8）
     * @return String 读取数据流得到的字符串
     * @throws IOException
     * */
    public static String inputStream2String(InputStream is, String charset) throws IOException{
        StringBuffer sb = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset==null?"UTF-8":charset));
            String line = null;
            sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if (is != null)
                is.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("数据读取时出现异常：" + e.getMessage());
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		String str = "product_code";
		System.out.println(replaceSpecialCharAndfirstToUpper(str,"_",""));
	}
}
