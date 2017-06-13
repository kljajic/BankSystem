package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Permission;
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
	@Permission(permissionName = "loginUser")
	public void loginUser(@RequestBody User user){
		securityService.loginUser(user.getEmail(), user.getPassword());
	}
	
	@PostMapping("/register")
	@Permission(permissionName = "registerUser")
	public void registerUser(@RequestBody @Valid User user){
		securityService.registerUser(user);
	}
	
	@GetMapping("/logout")
	@Permission(permissionName = "logoutUser")
	public void logoutUser(){
		securityService.logoutUser();
	}
	
}
