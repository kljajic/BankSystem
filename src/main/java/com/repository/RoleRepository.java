package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
}
