package com.service;

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.user.User;
import com.repository.UserRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;


	@Autowired
	public SecurityServiceImpl(AuthenticationManager authenticationManager,
							   UserService userService,
							   UserRepository userRepository, 
							   PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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

	@Override
	public boolean getLoggedUser() {
		if(SecurityContextHolder.getContext().getAuthentication() != null){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean changeUserPassword(String oldPassword, String newPassword, String username) {
		// TODO Auto-generated method stub
		User user = userRepository.findUserByEmail(username);
		System.out.println("EMAILLL" + username);
		System.out.println("USERRR:" + user.getName() + "  " + user.getPassword());
		if(user == null){
			return false;
		} else {
			boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
			System.out.println("MATCHEEEES" + matches);
			if(matches){
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
				return true;
			} else {
				return false;
			}
		}
	}

}
