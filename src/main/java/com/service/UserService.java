package com.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.model.user.User;

public interface UserService extends UserDetailsService {

	User createUser(User user);
	User getUser(String username);
	
}
