package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.user.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{

}
