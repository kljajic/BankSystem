package com.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public void loginUser(@RequestBody User user){
		securityService.loginUser(user.getEmail(), user.getPassword());
	}
	
	@PostMapping("/register")
	@Permission(permissionName = "registerUser")
	public void registerUser(@RequestBody @Valid User user){
		securityService.registerUser(user);
	}
	
	@GetMapping("/logout")
	public void logoutUser(){
		securityService.logoutUser();
	}
	
	@GetMapping("/getLogged")
	public boolean getLoggedUser(){
		return securityService.getLoggedUser();
	}
	
	@RequestMapping(value="/changePassword/{oldPassword}/{newPassword}/{username}", method=RequestMethod.POST)
	@ResponseBody
	public boolean changePassword(@PathVariable ("oldPassword") String oldPassword, @PathVariable ("newPassword") String newPassword, @PathVariable ("username") String username){
		String last8 = username.substring(username.length()-8, username.length());
		if(last8.equals("tackacom")){
			username = username.replace("tackacom", ".com");
		}
		System.out.println("OOOOOO" + username);
		return securityService.changeUserPassword(oldPassword, newPassword, username);
	}
	
	
}
