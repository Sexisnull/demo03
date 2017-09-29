package com.gsww.uids.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gsww.uids.constant.PersonalSessionInfo;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.util.JsonResult;
import com.gsww.uids.util.ResultState;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.mvc.ControllerUtil;

@Controller
@RequestMapping({ "front" })
public class OperatePerController {

	private static Logger logger = LoggerFactory
			.getLogger(OperatePerController.class);

	@Autowired
	private ComplatOutsideuserService OutsideUserService;

	@RequestMapping({ "modifyperinfo_show" })
	public ModelAndView modifyPerInfo(HttpServletResponse response) {
		ComplatOutsideuser user = PersonalSessionInfo
				.getFrontCurrentPersonalInfo();
		String path = BaseInfo.getContextPath();
		ModelAndView modelAndView = new ModelAndView("jis/front/modifyperinfo");

		if (user != null) {
		} else {
			RedirectView redirectView = new RedirectView("./perlogin.do");
			modelAndView.setView(redirectView);
			return modelAndView;
		}

		user.setDegree(user.getDegree() == null ? "" : user.getDegree());
		user.setWorkUnit(user.getWorkUnit() == null ? "" : user.getWorkUnit());
		user.setHeadShip(user.getHeadShip() == null ? "" : user.getHeadShip());
		user.setFax(user.getFax() == null ? "" : user.getFax());
		user.setPhone(user.getPhone() == null ? "" : user.getPhone());
		user.setCompTel(user.getCompTel() == null ? "" : user.getCompTel());
		user.setQq(user.getQq() == null ? "" : user.getQq());
		user.setMsn(user.getMsn() == null ? "" : user.getMsn());
		user.setPost(user.getPost() == null ? "" : user.getPost());
		user.setAddress(user.getAddress() == null ? "" : user.getAddress());
		user.setHeadPic(user.getHeadPic() == null ? "" : user.getHeadPic());
		user.setBodyPic(user.getBodyPic() == null ? "" : user.getBodyPic());
		user.setHeadRenamePic(user.getHeadRenamePic() == null ? "" : user
				.getHeadRenamePic());
		user.setBodyRenamePic(user.getHeadRenamePic() == null ? "" : user
				.getHeadRenamePic());
		user.setPapersNumber(user.getPapersNumber() == null ? "" : user
				.getPapersNumber());
		user.setRejectReason(user.getRejectReason() == null ? "" : user
				.getRejectReason());
		user.setSex(user.getSex() == null ? "" : user.getSex());
		user.setResidenceDetail(user.getResidenceDetail() == null ? "" : user
				.getResidenceDetail());
		user.setResidenceId(user.getResidenceId() == null ? "" : user
				.getResidenceId());
		user.setLivingAreaDetail(user.getLivingAreaDetail() == null ? "" : user
				.getLivingAreaDetail());
		user.setLivingAreaId(user.getLivingAreaId() == null ? "" : user
				.getLivingAreaId());
		user.setPresidenceId(user.getPresidenceId() == null ? "" : user
				.getPresidenceId());
		user.setGpresidenceId(user.getGpresidenceId() == null ? "" : user
				.getGpresidenceId());
		user.setPlivingAreaId(user.getPlivingAreaId() == null ? "" : user
				.getPlivingAreaId());
		user.setGplivingAreaId(user.getGplivingAreaId() == null ? "" : user
				.getGplivingAreaId());

		modelAndView.addObject("url", "modifyperinfo_submit.do");
		modelAndView.addObject("user", user);
		modelAndView.addObject("path", path);
		return modelAndView;
	}

	@RequestMapping({ "modifyperinfo_submit" })
	@ResponseBody
	public JsonResult savePerInfo(String randCode, HttpSession session,
			HttpServletResponse response, String pwd2,
			ComplatOutsideuser outsideUserFormBean) {
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			ComplatOutsideuser outuser = this.OutsideUserService
					.findByLoginName(outsideUserFormBean.getLoginName());
			
			if (outuser == null) {
				jsonResult.set(ResultState.OPR_FAIL);
				return jsonResult;
			}
			outuser.setName(outsideUserFormBean.getName());
			outuser.setEmail(outsideUserFormBean.getEmail());
			outuser.setMobile(outsideUserFormBean.getMobile());
			outuser.setPapersNumber(outsideUserFormBean.getPapersNumber());
			outuser.setAge(outsideUserFormBean.getAge());
			outuser.setSex(outsideUserFormBean.getSex());
			outuser.setDegree(outsideUserFormBean.getDegree());
			outuser.setWorkUnit(outsideUserFormBean.getWorkUnit());
			outuser.setHeadShip(outsideUserFormBean.getHeadShip());
			outuser.setFax(outsideUserFormBean.getFax());
			outuser.setPhone(outsideUserFormBean.getPhone());
			outuser.setCompTel(outsideUserFormBean.getCompTel());
			outuser.setQq(outsideUserFormBean.getQq());
			outuser.setMsn(outsideUserFormBean.getMsn());
			outuser.setPost(outsideUserFormBean.getPost());
			outuser.setAddress(outsideUserFormBean.getAddress());
			if ((pwd2 == null) || ("".equals(pwd2))) {
				pwd2 = Md5Util.md5decode(outuser.getPwd());
			}
			outsideUserFormBean.setPwd(pwd2);
			boolean isSuccess = false;

			if ((!"".equals(pwd2)) && (pwd2.length() > 0)) {
				outuser.setPwd(Md5Util.md5encode(pwd2));
			}

			this.OutsideUserService.save(outuser);
			isSuccess = true;
			if (isSuccess) {
				PersonalSessionInfo.setCurrentPersonalInfo(outsideUserFormBean);
				ControllerUtil.addCookie(response, "personalloginid",
						outsideUserFormBean.getLoginName(), 604800);
				jsonResult.setSuccess(true);
				jsonResult.setMessage("保存成功");
			} else {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("保存失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("保存失败");
		}

		return jsonResult;
	}

	@RequestMapping({ "perauth_modify" })
	@ResponseBody
	public JsonResult updatePerAuth() {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		ComplatOutsideuser user = PersonalSessionInfo
				.getFrontCurrentPersonalInfo();
		user = this.OutsideUserService.findByKey(user.getIid());
		user.setIsUpload(1);
		this.OutsideUserService.save(user);
		isSuccess = true;
		if (isSuccess)
			jsonResult.set(ResultState.OPR_SUCCESS);
		else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}
}