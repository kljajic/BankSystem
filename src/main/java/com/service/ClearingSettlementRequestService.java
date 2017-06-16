package com.service;

import java.util.ArrayList;

import com.model.xml.ClearingSettlementRequest;

public interface ClearingSettlementRequestService {

	public void save(ClearingSettlementRequest csr);
	
	public ArrayList<ClearingSettlementRequest> getAllClearingSettlementRequests();

}
