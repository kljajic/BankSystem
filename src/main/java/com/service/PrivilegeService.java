package com.service;

import com.model.user.Privilege;

public interface PrivilegeService {
	
	public Privilege save(Privilege privilege);
	public Privilege getOne(Long id);
}
