package com.gsww.jup.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil
{
  public static boolean isLogingNamelegal(String name)
  {
    String regex = "^(?!_)(?!.*?_$)[a-zA-Z0-9_一-龥]+$";
    if (name.matches(regex)) {
      return true;
    }
    return false;
  }

  public static boolean isMobilelegal(String mobile)
  {
    String regex2 = "^[1](([3]|[5]|[8]|[7])[0-9]{1})[0-9]{8}$";

    if (mobile.matches(regex2)) {
      return true;
    }
    return false;
  }

  public static boolean isEmaillegal(String email)
  {
    String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    if (email.matches(regex)) {
      return true;
    }
    return false;
  }

  public static boolean isIDnumberlegal(String IDStr)
  {
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
      if ((gc.get(1) - Integer.parseInt(strYear) > 150) || 
        (gc.getTime().getTime() - s.parse(
        strYear + "-" + strMonth + "-" + strDay).getTime() < 0L))
      {
        return false;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
    if ((Integer.parseInt(strMonth) > 12) || (Integer.parseInt(strMonth) == 0))
    {
      return false;
    }
    if ((Integer.parseInt(strDay) > 31) || (Integer.parseInt(strDay) == 0))
    {
      return false;
    }

    return true;
  }

  private static boolean isNumeric(String template)
  {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(template);
    if (isNum.matches()) {
      return true;
    }
    return false;
  }

  public static boolean isDate(String strDate)
  {
    Pattern pattern = 
      Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
    Matcher m = pattern.matcher(strDate);
    if (m.matches()) {
      return true;
    }
    return false;
  }

  @SuppressWarnings("unused")
  private static Hashtable<String, String> getAreaCode()
  {
    Hashtable<String,String> hashtable = new Hashtable<String,String>();
    hashtable.put("11", "北京");
    hashtable.put("12", "天津");
    hashtable.put("13", "河北");
    hashtable.put("14", "山西");
    hashtable.put("15", "内蒙古");
    hashtable.put("21", "辽宁");
    hashtable.put("22", "吉林");
    hashtable.put("23", "黑龙江");
    hashtable.put("31", "上海");
    hashtable.put("32", "江苏");
    hashtable.put("33", "浙江");
    hashtable.put("34", "安徽");
    hashtable.put("35", "福建");
    hashtable.put("36", "江西");
    hashtable.put("37", "山东");
    hashtable.put("41", "河南");
    hashtable.put("42", "湖北");
    hashtable.put("43", "湖南");
    hashtable.put("44", "广东");
    hashtable.put("45", "广西");
    hashtable.put("46", "海南");
    hashtable.put("50", "重庆");
    hashtable.put("51", "四川");
    hashtable.put("52", "贵州");
    hashtable.put("53", "云南");
    hashtable.put("54", "西藏");
    hashtable.put("61", "陕西");
    hashtable.put("62", "甘肃");
    hashtable.put("63", "青海");
    hashtable.put("64", "宁夏");
    hashtable.put("65", "新疆");
    hashtable.put("71", "台湾");
    hashtable.put("81", "香港");
    hashtable.put("82", "澳门");
    hashtable.put("91", "国外");
    return hashtable;
  }

  @SuppressWarnings("unused")
  private static boolean isVarifyCode(String Ai, String IDStr)
  {
    String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
    String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
    int sum = 0;
    for (int i = 0; i < 17; i++) {
      sum += Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
    }
    int modValue = sum % 11;
    String strVerifyCode = VarifyCode[modValue];
    Ai = Ai + strVerifyCode;
    if ((IDStr.length() == 18) && 
      (!Ai.equals(IDStr))) {
      return false;
    }

    return true;
  }
}