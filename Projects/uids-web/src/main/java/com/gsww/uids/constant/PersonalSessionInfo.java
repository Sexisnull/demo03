package com.gsww.uids.constant;

import com.gsww.uids.entity.ComplatOutsideuser;
import com.hanweb.common.util.SpringUtil;
import com.gsww.uids.constant.JisSettings;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersonalSessionInfo
{
  private static final Log LOGGER = LogFactory.getLog(PersonalSessionInfo.class);

  public static synchronized void setCurrentPersonalInfo(ComplatOutsideuser user)
  {
    try
    {
      if (user != null) {
        HttpSession session = SpringUtil.getRequest().getSession();
        clearSession(session);
        JisSettings jisSettings = new JisSettings();
        session.setMaxInactiveInterval(60 * jisSettings.getPerSessionTime());
        session.setAttribute("personalinfo", user);
      }
    } catch (Exception e) {
      LOGGER.error("setCurrentPersonalInfo Error:", e);
    }
  }

  public static synchronized ComplatOutsideuser getFrontCurrentPersonalInfo()
  {
    HttpSession session = SpringUtil.getRequest().getSession(false);
    ComplatOutsideuser user = null;
    if ((session != null) && (session.getAttribute("personalinfo") != null)) {
      user = (ComplatOutsideuser)session.getAttribute("personalinfo");
    }
    return user;
  }

  private static synchronized void clearSession(HttpSession session)
  {
    session.removeAttribute("personalinfo");
  }
}