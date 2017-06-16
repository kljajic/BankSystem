package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.xml.RTGSResponse;
import com.repository.RTGSResponseRepository;

@Service
public class RTGSResponseServiceImpl implements RTGSResponseService {
	
	private final RTGSResponseRepository rtgsResponseRepository;
	
	@Autowired
	public RTGSResponseServiceImpl(RTGSResponseRepository rtgsResponseRepository) {
		this.rtgsResponseRepository = rtgsResponseRepository;
	}
	
	@Override
	public RTGSResponse save(RTGSResponse save) {
		return rtgsResponseRepository.save(save);
	}
	
}
