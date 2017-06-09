package com.service;

import com.model.AnalyticalStatement;

public interface InterBankService {

	public void generateRTGSService(AnalyticalStatement as);
	
	public void generateClearingService(AnalyticalStatement as);
	
	public void addToClearing(AnalyticalStatement as);
	
}
