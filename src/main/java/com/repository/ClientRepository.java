package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.user.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {

	@Query(value="insert into users_roles(user_id, role_id) values (?1, 3)", nativeQuery=true)
	@Modifying
	void addClientRole(Long id);
	
}
