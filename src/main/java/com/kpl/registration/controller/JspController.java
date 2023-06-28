package com.kpl.registration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kpl.registration.dto.RegistrationResponse;

@Controller
public class JspController {

@Autowired
RegistrationController registrationController;


	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}
	@RequestMapping("/login/{searchParam}")
	public String login(@PathVariable String searchParam,Model model) throws IOException {
		RegistrationResponse registrationResponse=registrationController.registrationStatus(searchParam);
		 model.addAttribute("message", registrationResponse.getPlayerName());
		return "login";
	}
}