package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.User;
import com.service.SecurityService;

@RestController
@RequestMapping("/public")
public class LoginController {
	
	private final SecurityService securityService;
	
	@Autowired
	public LoginController(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@PostMapping("/login")
	public void loginUser(@RequestBody User user){
		securityService.loginUser(user.getEmail(), user.getPassword());
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody @Valid User user){
		securityService.registerUser(user);
	}
	
	@GetMapping("/logout")
	public void logoutUser(){
		securityService.logoutUser();
	}
	
}
