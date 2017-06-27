package com.service;

import com.bank.wsdl.Mt102Request;
import com.model.AnalyticalStatement;

public interface InterBankService {

	public void generateRTGSRequest(AnalyticalStatement as);
	
	public void generateClearingService();
	
	public void addToClearing(AnalyticalStatement as);
	
	public void RTGSOrClearing(AnalyticalStatement as);

	void receiveClearings(Mt102Request request);
	
}
