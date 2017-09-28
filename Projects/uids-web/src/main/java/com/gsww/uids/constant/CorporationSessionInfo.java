package com.gsww.uids.constant;

import com.hanweb.common.util.SpringUtil;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.constant.JisSettings;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CorporationSessionInfo
{
  private static final Log LOGGER = LogFactory.getLog(CorporationSessionInfo.class);

  public static synchronized void setCurrentCorporationInfo(ComplatCorporation corporation)
  {
    try
    {
      if (corporation != null) {
        HttpSession session = SpringUtil.getRequest().getSession();
        clearSession(session);
        JisSettings jisSettings = new JisSettings();
        session.setMaxInactiveInterval(60 * jisSettings.getCorSessionTime());
        session.setAttribute("corporationinfo", corporation);
      }
    } catch (Exception e) {
      LOGGER.error("setCurrentCorporationInfo Error:", e);
    }
  }

  public static synchronized ComplatCorporation getFrontCurrentCorporationInfo()
  {
    HttpSession session = SpringUtil.getRequest().getSession(false);
    ComplatCorporation corporation = null;
    if ((session != null) && (session.getAttribute("corporationinfo") != null)) {
      corporation = (ComplatCorporation)session.getAttribute("corporationinfo");
    }
    return corporation;
  }

  private static synchronized void clearSession(HttpSession session)
  {
    session.removeAttribute("corporationinfo");
  }
}