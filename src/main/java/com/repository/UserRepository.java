package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmail(String username);
	
}
