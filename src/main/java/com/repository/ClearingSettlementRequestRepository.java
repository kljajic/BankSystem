package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.xml.ClearingSettlementRequest;


public interface ClearingSettlementRequestRepository extends JpaRepository<ClearingSettlementRequest, Long> {

	@Query("select request from ClearingSettlementRequest request where request.paymentBank.id = ?1 and request.recieverBank.id = ?2 and "
			+ "request.date = (select max(cs.date) from ClearingSettlementRequest cs where cs.paymentBank.id = ?1 and cs.recieverBank.id = ?2)")
	ClearingSettlementRequest search(Long paymentBankId, Long receiverBankId);

}
