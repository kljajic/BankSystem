package com.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.model.user.Privilege;
import com.repository.PrivilegeRepository;

public class PrivilegeServiceImpl implements PrivilegeService{

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Override
	public Privilege save(Privilege privilege) {
		return privilegeRepository.save(privilege);
	}

	@Override
	public Privilege getOne(Long id) {
		return privilegeRepository.getOne(id);
	}
	
}
