package com.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.model.user.Role;
import com.repository.RoleRepository;

public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role getOne(Long id) {
		return roleRepository.findOne(id);
	}
	
}
