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
	public void loginUser(User user) {
		UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, user.getPassword(), userDetails.getAuthorities());
		authenticationManager.authenticate(authenticationToken);
		if (authenticationToken.isAuthenticated())
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	@Override
	public void registerUser(User user) {
		userService.createUser(user);
		loginUser(user);
	}

	@Override
	public void logoutUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
