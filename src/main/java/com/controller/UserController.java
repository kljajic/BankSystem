package com.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Permission;
import com.model.user.User;
import com.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(path="getCurrentlyLoggedUser", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	@Permission(permissionName = "readUser")
	public String getCurrentlyLoggedUser(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return name;
	}
	
	@GetMapping("/{userId}")
	@ResponseBody
	@Permission(permissionName = "readUser")
	public User getUser(@PathVariable("userId") Long userId){
		return userService.getUser(userId);
	}
	
	@GetMapping
	@ResponseBody
	@Permission(permissionName = "readUsers")
	public Collection<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("addRoleToUser/{userId}/{roleId}")
	@ResponseBody
	@Permission(permissionName = "addRoleToUser")
	public User addRoleToUser(@PathVariable("userId") Long userId,
							  @PathVariable("roleId") Long roleId){
		return userService.addRoleToUser(userId, roleId);
	}
	
	@DeleteMapping("removeRoleToUser/{userId}/{roleId}")
	@ResponseBody
	@Permission(permissionName = "removeRoleFromUser")
	public User removeRoleFromUser(@PathVariable("userId") Long userId,
							  @PathVariable("roleId") Long roleId){
		return userService.removeRoleFromUser(userId, roleId);
	}
	
}
