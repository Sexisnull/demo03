package com.gsww.uids.gateway.util;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil {

	public static boolean isIDnumberlegal(String IDStr) {
		if ((IDStr.length() != 15) && (IDStr.length() != 18)) {
			return false;
		}

		String template = "";
		if (IDStr.length() == 18)
			template = IDStr.substring(0, 17);
		else if (IDStr.length() == 15) {
			template = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (!isNumeric(template)) {
			return false;
		}

		String strYear = template.substring(6, 10);
		String strMonth = template.substring(10, 12);
		String strDay = template.substring(12, 14);

		if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
			return false;
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(1) - Integer.parseInt(strYear) > 150)
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime() < 0L)) {
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		if ((Integer.parseInt(strMonth) > 12) || (Integer.parseInt(strMonth) == 0)) {
			return false;
		}
		if ((Integer.parseInt(strDay) > 31) || (Integer.parseInt(strDay) == 0)) {
			return false;
		}

		return true;
	}

	private static boolean isNumeric(String template) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(template);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	public static boolean isMobilelegal(String mobile) {
		String regex1 = "^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String regex2 = "^[1](([3]|[5]|[8]|[7])[0-9]{1})[0-9]{8}$";

		if (mobile.matches(regex2)) {
			return true;
		}
		return false;
	}

}
