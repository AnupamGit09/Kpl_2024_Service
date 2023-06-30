package com.kpl.registration.controller;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kpl.registration.dto.AdminReqVO;
import com.kpl.registration.entity.AdminInfo;
import com.kpl.registration.repository.AdminRepo;

@Controller
public class AdminController {

	@Autowired
	AdminRepo adminRepo;
//	@Autowired
//	AdminService adminService;
//	@Autowired
//	ModelMapper map;

	@GetMapping(value = "/adminDashboardLogin")
	public String adminDashboardLogin() {
		return "adminLogin";
	}

	@RequestMapping(value = "/adminDashboardView", method = RequestMethod.POST)
	public String adminLogin(@RequestParam String id, @RequestParam String password, Model model) {
		String res = adminRepo.adminLoginValidation(id, password);
		if (res != null) {
			return "adminView";
		} else {
			model.addAttribute("errorMessage", "Please input valid id and pasword");
			return "adminLogin";
		}
	}
}
