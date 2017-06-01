package com.service;

import com.model.user.Role;

public interface RoleService {
	
	
	public Role save(Role role);
	
	public Role getOne(Long id);
}
