package com.gsww.uids.controller;

import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.gsww.uids.util.JsonResult;
import com.gsww.uids.util.ResultState;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.jup.util.Dbid;
import com.gsww.uids.constant.CorporationSessionInfo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/front")
public class OperateCorController{

  @Autowired
  private ComplatCorporationService corporationService;

  @RequestMapping("/modifycorinfo_show")
  public String modifyCorInfo(String username, String pwd,Model model)
  {
    ComplatCorporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
    if (corporation == null)
    {
      return "corlogin";
    }
    int type = corporation.getType().intValue();
    String corname;
    if (type == 1)
      corname = "企业法人";
    else {
      corname = "非企业法人";
    }
    
    model.addAttribute("loginName", corporation.getLoginName());
    model.addAttribute("iid", String.valueOf(corporation.getIid()));
    model.addAttribute("name", corporation.getName());
    model.addAttribute("type", String.valueOf(corporation.getType()));
    model.addAttribute("isauth", String.valueOf(corporation.getisAuth()));
    model.addAttribute("corname", StringUtil.getString(corname));
    model.addAttribute("regNumber", StringUtil.getString(corporation.getRegNumber()));
    model.addAttribute("realName", StringUtil.getString(corporation.getRealName()));
    model.addAttribute("sex", StringUtil.getString(corporation.getSex()));
    model.addAttribute("nation",  corporation.getNation() == null ? "" : corporation.getNation());
    model.addAttribute("scope", corporation.getScope() == null ? "" : corporation.getScope());
    model.addAttribute("phone", corporation.getPhone() == null ? "" : corporation.getPhone());
    model.addAttribute("cardNumber", corporation.getCardNumber());
    model.addAttribute("orgNumber", corporation.getOrgNumber());
    model.addAttribute("loginName", corporation.getLoginName());
    model.addAttribute("mobile", corporation.getMobile());
    model.addAttribute("email", corporation.getEmail());
    model.addAttribute("url", "modifycorinfo_submit");
    model.addAttribute("cardPic", corporation.getcardPic() == null ? "" : corporation.getcardPic());
    model.addAttribute("licencePic", corporation.getlicencePic() == null ? "" : corporation.getlicencePic());
    model.addAttribute("orgPic", corporation.getorgPic() == null ? "" : corporation.getorgPic());
    model.addAttribute("cardRenamePic", corporation.getcardreNamePic() == null ? "" : corporation.getcardreNamePic());
    model.addAttribute("licenceRenamePic", corporation.getlicencereNamePic() == null ? "" : corporation.getlicencereNamePic());
    model.addAttribute("orgRenamePic", corporation.getorgreNamePic() == null ? "" : corporation.getorgreNamePic());
    model.addAttribute("rejectReason", corporation.getrejectReason() == null ? "" : corporation.getrejectReason());
    model.addAttribute("isupload", String.valueOf(corporation.getisUpload()));
    model.addAttribute("isauth", String.valueOf(corporation.getisAuth()));
    model.addAttribute("authState", String.valueOf(corporation.getauthState()));
    model.addAttribute("isshoworgnumber", StringUtil.isEmpty(corporation.getOrgNumber()) ? "none" : "");
    model.addAttribute("address", StringUtil.getString(corporation.getAddress()));
    model.addAttribute("residenceDetail", corporation.getResidenceDetail() == null ? "" : corporation.getResidenceDetail());
    model.addAttribute("residenceId", corporation.getResidenceId() == null ? "" : corporation.getResidenceId());
    model.addAttribute("livingAreaDetail", corporation.getLivingAreaDetail() == null ? "" : corporation.getLivingAreaDetail());
    model.addAttribute("livingAreaId", corporation.getLivingAreaId() == null ? "" : corporation.getLivingAreaId());
    model.addAttribute("presidenceId", corporation.getPresidenceId() == null ? "" : corporation.getPresidenceId());
    model.addAttribute("gpresidenceId", corporation.getGpresidenceId() == null ? "" : corporation.getGpresidenceId());
    model.addAttribute("plivingAreaId", corporation.getPlivingAreaId() == null ? "" : corporation.getPlivingAreaId());
    model.addAttribute("gplivingAreaId", corporation.getGplivingAreaId() == null ? "" : corporation.getGplivingAreaId());
    
    return "jis/front/modifycorinfo";
  }

  @RequestMapping("/modifycorinfo_submit")
  @ResponseBody
  public JsonResult saveCorInfo(String randCode, HttpSession session, HttpServletRequest request,HttpServletResponse response, String pwd2, String corsex, String inssex, ComplatCorporation corporation) {
    boolean isSuccess = false;
    JsonResult jsonResult = JsonResult.getInstance();

    ComplatCorporation cp = CorporationSessionInfo.getFrontCurrentCorporationInfo();
    corporation.setLoginName(cp.getLoginName());

    ComplatCorporation cor = this.corporationService.findByLoginName(corporation.getLoginName());
    if ((pwd2 == null) || ("".equals(pwd2))) {
      pwd2 = Md5Util.md5decode(cor.getPwd());
    }
    corporation.setPwd(pwd2);
    int id = cor.getIid().intValue();
    corporation.setIid(Integer.valueOf(id));
    try {
      if (corporation.getType().intValue() == 1) {
        corporation.setName(cp.getName());
        corporation.setRegNumber(cp.getRegNumber());
        corporation.setOrgNumber(cp.getOrgNumber());
        corporation.setRealName(cp.getRealName());
        corporation.setNation(cp.getNation());
        corporation.setSex(cp.getSex());
        corporation.setCardNumber(cp.getCardNumber());
        corporation.setUuid((cor.getUuid()==null || "".equals(cor.getUuid()))?Dbid.getID():cor.getUuid());
        corporation.setEnable(1);
        corporation.setLoginTime(new Date(System.currentTimeMillis()));
        corporation.setLoginIp(request.getRemoteAddr());
      } else {
        corporation.setName(cp.getName());
        corporation.setRegNumber(cp.getRegNumber());
        corporation.setOrgNumber(cp.getOrgNumber());
        corporation.setRealName(cp.getRealName());
        corporation.setSex(cp.getSex());
        corporation.setNation(cp.getNation());
        corporation.setCardNumber(cp.getCardNumber());
        corporation.setUuid((cor.getUuid()==null || "".equals(cor.getUuid()))?Dbid.getID():cor.getUuid());
        corporation.setEnable(1);
        corporation.setLoginTime(new Date(System.currentTimeMillis()));
        corporation.setLoginIp(request.getRemoteAddr());
      }

      if ((!"".equals(pwd2)) && (pwd2.length() > 0)) {
        corporation.setPwd(Md5Util.md5encode(pwd2));
      }
      isSuccess = this.corporationService.modify(corporation);
      if (isSuccess) {
        ComplatCorporation cor2 = this.corporationService.findByLoginName(corporation.getLoginName());
        CorporationSessionInfo.setCurrentCorporationInfo(cor2);
        ControllerUtil.addCookie(response, "corporationloginid", corporation.getLoginName(), 
          604800);
        jsonResult.set(ResultState.MODIFY_SUCCESS);
      } else {
        jsonResult.set(ResultState.MODIFY_FAIL);
      }
    } catch (Exception e) {
      jsonResult.setMessage("未知错误，保存失败！");
    }
    return jsonResult;
  }
  @RequestMapping("/corauth_modify")
  @ResponseBody
  public JsonResult updatePerAuth() {
    boolean isSuccess = false;
    JsonResult jsonResult = JsonResult.getInstance();
    ComplatCorporation corporation = CorporationSessionInfo.getFrontCurrentCorporationInfo();
    isSuccess = this.corporationService.updateIsUpload(corporation.getIid().intValue(), 1);
    if (isSuccess)
      jsonResult.set(ResultState.OPR_SUCCESS);
    else {
      jsonResult.set(ResultState.OPR_FAIL);
    }
    return jsonResult;
  }
}