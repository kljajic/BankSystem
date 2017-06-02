package com.service;

import com.model.user.User;

public interface SecurityService {
	
	 void loginUser(String username, String password);
	 
	 void registerUser(User user);
	 
	 void logoutUser();
	
}
