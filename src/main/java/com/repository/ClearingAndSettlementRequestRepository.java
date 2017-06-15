package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.xml.ClearingSettlementRequest;


public interface ClearingAndSettlementRequestRepository extends JpaRepository<ClearingSettlementRequest, Long> {

}
