package com.service;

import com.model.user.User;

public interface SecurityService {
	
	 void loginUser(User user);
	 
	 void registerUser(User user);
	 
	 void logoutUser();
	
}
