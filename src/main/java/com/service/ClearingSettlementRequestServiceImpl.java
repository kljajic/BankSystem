package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.xml.ClearingSettlementRequest;
import com.repository.ClearingAndSettlementRequestRepository;

@Service
public class ClearingSettlementRequestServiceImpl implements ClearingSettlementRequestService {
	
	@Autowired
	private ClearingAndSettlementRequestRepository clearingSettlementRequestRepository;

	@Override
	public void save(ClearingSettlementRequest csr) {
		clearingSettlementRequestRepository.save(csr);
	}

	@Override
	public ArrayList<ClearingSettlementRequest> getAllClearingSettlementRequests() {
		return (ArrayList<ClearingSettlementRequest>) clearingSettlementRequestRepository.findAll();
	}

}
