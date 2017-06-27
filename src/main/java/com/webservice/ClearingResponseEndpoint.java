package com.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.bank.wsdl.Mt102Request;
import com.service.InterBankService;

@Endpoint
public class ClearingResponseEndpoint {

	private static final String NAMESPACE = "http://com/xsdSchemas/";
	private final InterBankService interBankService;
	
	@Autowired
	public ClearingResponseEndpoint(InterBankService interBankService) {
		this.interBankService = interBankService;
	}
	
	@PayloadRoot(namespace=NAMESPACE + "http://com/xsdSchemas/clearingAndSettlement", localPart="mt102Request")
	public void getRtgsMt910Response(@RequestPayload Mt102Request request){
		interBankService.receiveClearings(request);
	}
	
}
