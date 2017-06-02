package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.user.Privilege;
import com.model.user.Role;
import com.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepository roleRepository;
	private final PrivilegeService privilegeService;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, PrivilegeService privilegeService) {
		this.roleRepository = roleRepository;
		this.privilegeService = privilegeService;
	}
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	@Override
	public Role updateRole(Role role) {
		Role oldRole = roleRepository.findOne(role.getId());
		oldRole.setName(role.getName());
		return roleRepository.save(oldRole);
	}

	@Override
	public Role getOne(Long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Collection<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void deleteRole(Long id) {
		roleRepository.delete(id);
	}

	@Override
	public Role addPrivilegeToRole(Long roleId, Long privilegeId) {
		Role role = roleRepository.findOne(roleId);
		Privilege privilege = privilegeService.getOne(privilegeId);
		role.addPrivilege(privilege);
		return roleRepository.save(role);
	}

	@Override
	public Role removePrivilegFromRole(Long roleId, Long privilegeId) {
		Role role = roleRepository.findOne(roleId);
		Privilege privilege = privilegeService.getOne(privilegeId);
		role.removePrivilege(privilege);
		return roleRepository.save(role);
	}
	
}
