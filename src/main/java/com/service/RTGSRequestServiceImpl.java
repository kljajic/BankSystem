package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.xml.RTGSRequest;
import com.repository.RTGSRequestRepository;

@Service
public class RTGSRequestServiceImpl implements RTGSRequestService {
	
	@Autowired
	private RTGSRequestRepository rTGSRequestRepository;

	@Override
	public void save(RTGSRequest req) {
		rTGSRequestRepository.save(req);
	}

	@Override
	public ArrayList<RTGSRequest> getAllRTGSRequestServices() {
		return (ArrayList<RTGSRequest>) rTGSRequestRepository.findAll();
	}

}
