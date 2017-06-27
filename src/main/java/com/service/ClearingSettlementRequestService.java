package com.service;

import java.util.ArrayList;

import com.model.xml.ClearingSettlementRequest;

public interface ClearingSettlementRequestService {

	void save(ClearingSettlementRequest csr);
	
	ArrayList<ClearingSettlementRequest> getAllClearingSettlementRequests();
	
	ClearingSettlementRequest search(Long paymentBankId, Long recipientBankId);

}
