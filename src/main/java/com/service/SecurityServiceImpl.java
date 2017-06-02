package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.model.user.User;

@Service
public class SecurityServiceImpl implements SecurityService {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	@Autowired
	public SecurityServiceImpl(AuthenticationManager authenticationManager,
							   UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override
	public void loginUser(String email, String password) {
		UserDetails userDetails = userService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		authenticationManager.authenticate(authenticationToken);
		if (authenticationToken.isAuthenticated())
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	@Override
	public void registerUser(User user) {
		String password = user.getPassword().concat("");
		userService.createUser(user);
		loginUser(user.getEmail(), password);
	}

	@Override
	public void logoutUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
