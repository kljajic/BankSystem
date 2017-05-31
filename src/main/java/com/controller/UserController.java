package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.User;
import com.service.SecurityService;
import com.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final SecurityService securityService;
	
	@Autowired
	public UserController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}
	
	@PostMapping("/register")
	@ResponseBody
	public User registerUser(@RequestBody @Valid User user){
		User newUser = userService.createUser(user);
		securityService.autologin(newUser.getEmail(), newUser.getPassword());
		return newUser;
	}

}
