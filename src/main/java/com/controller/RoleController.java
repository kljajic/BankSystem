package com.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Permission;
import com.model.user.Role;
import com.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@PostMapping("/add")
	@ResponseBody
	@Permission(permissionName = "createRole")
	public Role createRole(@RequestBody @Valid Role role){
		return roleService.save(role);
	}
	
	@PutMapping("/update")
	@ResponseBody
	@Permission(permissionName = "updateRole")
	public Role updateRole(@RequestBody @Valid Role role){
		return roleService.updateRole(role);
	}
	
	@GetMapping("/{roleId}")
	@ResponseBody
	@Permission(permissionName = "readRole")
	public Role getRole(@PathVariable("roleId") Long roleId){
		return roleService.getOne(roleId);
	}
	
	@GetMapping
	@ResponseBody
	@Permission(permissionName = "readRoles")
	public Collection<Role> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@DeleteMapping("/delete/{roleId}")
	@Permission(permissionName = "removeRole")
	public void deleteRole(@PathVariable("roleId") Long roleId){
		roleService.deleteRole(roleId);
	}
	
	@PostMapping("/addPrivilegeToRole/{roleId}/{privilegeId}")
	@ResponseBody
	@Permission(permissionName = "addPrivilegeRole")
	public Role addPrivilegeToRole(@PathVariable("roleId") Long roleId,
								   @PathVariable("privilegeId") Long privilegeId){
		return roleService.addPrivilegeToRole(roleId, privilegeId);
	}
	
	@DeleteMapping("/removePrivilegeToRole/{roleId}/{privilegeId}")
	@ResponseBody
	@Permission(permissionName = "removePrivilegeFromRole")
	public Role removePrivilegeFromRole(@PathVariable("roleId") Long roleId,
								   @PathVariable("privilegeId") Long privilegeId){
		return roleService.removePrivilegFromRole(roleId, privilegeId);
	}
	
}
