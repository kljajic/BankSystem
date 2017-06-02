package com.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.user.Privilege;
import com.model.user.Role;
import com.model.user.User;
import com.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
						   RoleService roleService,
						   PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUser(String username) {
		return userRepository.findUserByEmail(username);
	}
	
	@Override
	public User getUser(Long userId) {
		return userRepository.findOne(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(
					username, user.getPassword(), getAuthorities(user.getRoles()));
		}
		return null;
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {
		List<String> privileges = new ArrayList<>();
		if(roles == null)
			return privileges;
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(privileges == null)
			return authorities;
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.delete(userId);
	}

	@Override
	public User addRoleToUser(Long userId, Long roleId) {
		User user = this.getUser(userId);
		Role role = roleService.getOne(roleId);
		user.addRole(role);
		return userRepository.save(user);
	}

	@Override
	public User removeRoleFromUser(Long userId, Long roleId) {
		User user = this.getUser(userId);
		Role role = roleService.getOne(roleId);
		user.removeRole(role);
		return userRepository.save(user);
	}

}
