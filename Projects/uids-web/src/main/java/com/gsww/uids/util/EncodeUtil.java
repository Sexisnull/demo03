package com.gsww.uids.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class EncodeUtil
{

	private String encoding = "";

	//private String cset = System.getProperty("file.encoding");

	//private boolean found = false;
	
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
  
  public String getURLEncoding(URL url)
  {
    String returnValue = "UTF-8";
    try {
      returnValue = getCharEncoding(url.openStream());
    }
    catch (IOException localIOException)
    {
    }
    return returnValue;
  }
  
  public String getCharEncoding(InputStream in)
  {
    nsDetector det = new nsDetector(3);
    det.Init(new nsICharsetDetectionObserver() {
      public void Notify(String charset) {
        HtmlCharsetDetector.found = true;
        EncodeUtil.this.encoding = charset;
      }
    });
    BufferedInputStream imp = null;
    try {
      imp = new BufferedInputStream(in);
      byte[] buf = new byte[2048];

      boolean done = false;

      boolean isAscii = false;
      int len = 0;
      boolean found = false;
      while (((len = imp.read(buf, 0, buf.length)) != -1) && (!isAscii) && (!
        done))
      {
        if (isAscii) {
          isAscii = det.isAscii(buf, len);
        }

        if ((!isAscii) && (!done)) {
          done = det.DoIt(buf, len, false);
        }
      }

      det.DataEnd();
      if (!HtmlCharsetDetector.found) {
        if (isAscii) {
          found = true;
        }

        if (!found) {
          String[] prob = det.getProbableCharsets();
          if (prob.length > 0)
            this.encoding = prob[0];
        }
      }
    }
    catch (Exception e)
    {
      this.encoding = "gb2312";

      if (imp != null)
        try {
          imp.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
    }
    finally
    {
      if (imp != null) {
        try {
          imp.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if ((this.encoding != null) && (this.encoding.indexOf("GB") > -1)) {
      this.encoding = "gb2312";
    }
    return this.encoding;
  }
}