package com.gsww.jup.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsww.jup.controller.BaseController;

//userInterface
@Controller
@RequestMapping(value = "/userInterface")
public class UserRestController extends BaseController {

	@RequestMapping(value = "/userSave", method = RequestMethod.GET)
	public String accountEdit(String userAcctId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		//ModelAndView mav=new ModelAndView("sys/account_edit");
		return "sys/user_save";
	}
}
