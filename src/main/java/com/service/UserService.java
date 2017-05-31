package com.service;

import com.model.user.User;

public interface UserService {

	User createUser(User user);
	User getUser(String username);
	
}
