package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.user.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
