package com.kpl.registration.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@Autowired
	RegistrationController registrationController;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String id, @RequestParam("password") String password, Model model)
			throws IOException {

		var response = registrationController.registrationStatus(id, password);
		if (response.getRegistrationId() != null) {
//			 session.setAttribute("response", response);
			 model.addAttribute("id", response.getRegistrationId());
			 model.addAttribute("name", response.getPlayerName());
			 model.addAttribute("registerDate",response.getRegistrationTime());
			 model.addAttribute("status", response.getPaymentValidation());
			return "playerView";
		} else {
			model.addAttribute("errorMessage", "Invalid Email/Ph No or password");
			return "login";
		}
	}

	@GetMapping("/welcome")
	public String showWelcomePage() {
		return "welcome";
	}
	
	@GetMapping("/playerView")
	public String playerView() {
		return "playerView";
	}
}
