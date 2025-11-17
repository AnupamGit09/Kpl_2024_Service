package com.kpl.registration.controller.LessImportant;

import com.kpl.registration.controller.Important.RegistrationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

@Autowired
RegistrationController registrationController;


	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
}