package com.gsww.uids.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AccessUtil
{
  private static String allowaccess = "";

  private static boolean allowall = false;

  private static String crossdomain = "/WEB-INF/config/crossdomain.xml";

  @SuppressWarnings("rawtypes")
public static boolean checkAccess(HttpServletRequest request)
  {
    try
    {
      if ((allowaccess == null) || (allowaccess.equals("")))
      {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(request.getSession().getServletContext().getRealPath(crossdomain));
        List l = document.selectNodes("/cross-domain-policy/allow-access-from");
        for (int i = 0; i < l.size(); i++) {
          Element e = (Element)l.get(i);
          String from = "";
          if (e != null) {
            from = e.attributeValue("domain");
          }

          if ("*".equals(from)) {
            allowall = true;
          }
          if ((from != null) && (!from.equals(""))) {
            allowaccess = allowaccess + "^" + from + "$|";
          }

        }

        if (allowaccess.length() == 0) {
          allowall = true;
        }
        if (allowaccess.endsWith("|")) {
          allowaccess = allowaccess.substring(0, allowaccess.length() - 2);
        }
        allowaccess = allowaccess.replaceAll("\\.", "\\\\.");
        allowaccess = allowaccess.replaceAll("\\*", ".*");
      }

      if (allowall) {
        return true;
      }

      String url = request.getHeader("Referer");
      if ((url == null) || (url.equals("")))
      {
        return false;
      }

      String regEx = "http://([^|/]*)";
      Pattern pattern = Pattern.compile(regEx, 2);
      Matcher matcher = pattern.matcher(url);
      String domain = "";
      if (matcher.find()) {
        domain = matcher.group(1);
      }

      if (domain.indexOf(":") > 0) {
        domain = domain.substring(0, domain.indexOf(":"));
      }
      pattern = Pattern.compile(allowaccess, 2);
      matcher = pattern.matcher(domain);
      if (matcher.find()) {
        return true;
      }
      System.out.println("非法访问：url=" + url);
      return false;
    }
    catch (Exception e)
    {
      System.out.println("域名访问控制 AccessUtil checkAccess 异常" + e);
    }return false;
  }

  public String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}