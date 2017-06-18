package com.service;

import com.model.user.User;

public interface SecurityService {
	
	 void loginUser(String username, String password);
	 
	 void registerUser(User user);
	 
	 void logoutUser();
	 
	 boolean getLoggedUser();
	 
	 boolean changeUserPassword(String oldPassword, String newPassword, String username);
	
}
