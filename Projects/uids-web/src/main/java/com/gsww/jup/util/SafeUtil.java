package com.gsww.jup.util;

import com.hanweb.common.util.StringUtil;

public class SafeUtil
{
  public static boolean isSqlAndXss(String str)
  {
    boolean bl = false;
    if ((isSql(str)) || (isXss(str))) {
      bl = true;
    }

    return bl;
  }

  public static boolean isSql(String str)
  {
    str = StringUtil.getString(str);
    String inj_str = "union| and |exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare| or |sleep| if |sysdate|delay|waitfor";
    String[] inj_stra = inj_str.split("\\|");
    for (int i = 0; i < inj_stra.length; i++) {
      if (str.toLowerCase().indexOf(inj_stra[i]) >= 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean isXss(String strPara)
  {
    strPara = StringUtil.getString(strPara);
    boolean bl = false;
    strPara = strPara.toLowerCase();
    if ((strPara.indexOf("%") >= 0) || (strPara.indexOf("|") >= 0) || (strPara.indexOf("+") >= 0) || 
      (strPara.indexOf("&") >= 0) || (strPara.indexOf("alert") >= 0) || (strPara.indexOf("script") >= 0) || 
      (strPara.indexOf("\"") >= 0) || (strPara.indexOf("'") >= 0) || (strPara.indexOf("<") >= 0) || 
      (strPara.indexOf("set") >= 0) || (strPara.indexOf("exec") >= 0) || (strPara.indexOf("delete") >= 0) || 
      (strPara.indexOf("update") >= 0) || (strPara.indexOf("declare") >= 0) || (strPara.indexOf("cast") >= 0) || 
      (strPara.indexOf(" and ") >= 0) || (strPara.indexOf("onmouseover") >= 0) || (strPara.indexOf("prompt") >= 0)) {
      bl = true;
    }
    return bl;
  }

  public static boolean isSafeFile(String fileName)
  {
    fileName = StringUtil.getString(fileName);
    if ((fileName == null) || ("".equals(fileName))) {
      return false;
    }
    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    String allType = ",xls,xlsx,ppt,pptx,doc,docx,wps,pdf,xml,txt,ps,trs,rar,zip,jpg,jpeg,gif,bmp,png,swf,rm,rmvb,avi,mid,mp3,wav,asf,flv,mp4,mpg,mov,wmv,ra,3gp,";
    if (allType.indexOf("," + fileType + ",") > -1) {
      return true;
    }
    return false;
  }

  public static void main(String[] args)
  {
    String param = "";
    if (isSqlAndXss(param)) {
      System.out.println("参数包含非法字符，禁止访问");

      return;
    }
    String str = "cast 1=1 ";
    boolean b = isXss(str);
    System.out.println("SafeUtil.main()~~b~11~~" + b);
    b = isSql(str);
    System.out.println("SafeUtil.main()~~b~22~~" + b);
    b = isSqlAndXss(str);
    System.out.println("SafeUtil.main()~~b~33~~" + b);
  }
}