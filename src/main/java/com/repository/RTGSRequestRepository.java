package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.xml.RTGSRequest;

public interface RTGSRequestRepository extends JpaRepository<RTGSRequest, Long> {

}
