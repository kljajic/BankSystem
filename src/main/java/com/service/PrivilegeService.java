package com.service;

import java.util.Collection;

import com.model.user.Privilege;

public interface PrivilegeService {
	
	Privilege save(Privilege privilege);
	Privilege updatePrivilege(Privilege privilege);
	Privilege getOne(Long id);
	Collection<Privilege> getAllPrivileges();
	void deletePrivilege(Long id);
	
}
