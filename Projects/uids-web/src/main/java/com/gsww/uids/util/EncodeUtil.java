package com.gsww.uids.util;

import java.net.URLEncoder;

public class EncodeUtil
{

  @SuppressWarnings("deprecation")
public String encodeStr(String str, String charset)
  {
    StringBuffer strbuf = new StringBuffer();
    if (str == null) {
      return null;
    }
    if ((charset == null) || ("".equals(charset))) {
      charset = "GBK";
    }
    char[] ch = str.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      if (isChinese(ch[i]))
		strbuf.append(URLEncoder.encode(charset));
	else {
        strbuf.append(ch[i]);
      }
    }

    return strbuf.toString();
  }

  public boolean isChinese(char c)
  {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || 
      (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || 
      (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || 
      (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) || 
      (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || 
      (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
      return true;
    }
    return false;
  }
  
}