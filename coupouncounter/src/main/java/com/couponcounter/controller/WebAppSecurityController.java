package com.couponcounter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.couponcounter.dto.UsersDto;
import com.couponcounter.requests.AuthenticationRequest;
import com.couponcounter.service.SecurityService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/demo")
public class WebAppSecurityController {

	@Autowired
	SecurityService securityService;

	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		securityService.userLogout(response);
		return "redirect:/demo/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("login", new AuthenticationRequest());
		return "loginform";
	}

	@PostMapping("/login")
	public String login(HttpServletResponse response, @ModelAttribute AuthenticationRequest loginRequest, Model model)
			throws Exception {
		try {
			securityService.userLogin(response, loginRequest);
			model.addAttribute("message", "login succesfull");
			return "redirect:/demo/index";

		} catch (Exception e) {
			model.addAttribute("login", new AuthenticationRequest());
			model.addAttribute("error", "login failed : " + e.getMessage());
			return "loginform";
		}
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userDto", new UsersDto());
		return "registrationform";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute UsersDto userDto, Model model) {
		try {
			userDto.setRole(List.of("ROLE_USER"));
			securityService.register(userDto);
			return "redirect:/demo/login";
		} catch (Exception e) {
			model.addAttribute("userDto", new UsersDto());
			model.addAttribute("error", "registration failed : " + e.getMessage());
			return "registrationform";
		}
	}
}
