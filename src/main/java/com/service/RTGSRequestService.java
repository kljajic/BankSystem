package com.service;

import java.util.ArrayList;

import com.model.xml.RTGSRequest;

public interface RTGSRequestService {

	public void save(RTGSRequest req);
	
	public ArrayList<RTGSRequest> getAllRTGSRequestServices();
}
