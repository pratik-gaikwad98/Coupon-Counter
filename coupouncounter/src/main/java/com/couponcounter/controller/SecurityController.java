package com.couponcounter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couponcounter.dto.UsersDto;
import com.couponcounter.requests.AuthenticationRequest;
import com.couponcounter.response.AuthenticationResponse;
import com.couponcounter.response.Responses;
import com.couponcounter.security.JwtRequestFilter;
import com.couponcounter.service.SecurityService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class SecurityController {

	@Autowired
	SecurityService securityService;

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> token(@RequestBody AuthenticationRequest userLogin) {	
		AuthenticationResponse jwtResponse = securityService.login(userLogin);
		return ResponseEntity.ok(jwtResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsersDto userDto) throws Exception {
		Map<String,String> message = new HashMap<>();
		userDto.setRole(List.of("ROLE_USER"));
		message  = securityService.register(userDto);
		return new ResponseEntity<>(new Responses("success", message,JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}

	@PostMapping("/register_admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> registerAdmin(@RequestBody UsersDto userDto) throws Exception {
		Map<String,String> message = new HashMap<>();
		message  = securityService.register(userDto);
			return new ResponseEntity<>(new Responses("success", message,JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> destroySession(HttpServletRequest request) {
		securityService.logout();
		Map<String,String> message = new HashMap<>();
		message.put("message", "logout succesfully");
		return new ResponseEntity<>(new Responses("success", message,JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now()),
				HttpStatus.OK);
	}
	
	
}
