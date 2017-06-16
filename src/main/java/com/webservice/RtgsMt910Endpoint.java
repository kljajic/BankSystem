package com.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bank.wsdl.Mt910Response;
import com.model.xml.RTGSResponse;
import com.model.xml.RTGSResponseType;
import com.service.BankService;
import com.service.RTGSResponseService;

@Endpoint
public class RtgsMt910Endpoint {
		
	private static final String NAMESPACE = "http://com/xsdSchemas/";
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private RTGSResponseService rtgsResponseService;
	
	@PayloadRoot(namespace=NAMESPACE + "rtgsResponseReciever", localPart="mt910Response")
	@ResponsePayload
	public Mt910Response getRtgsMt910Response(@RequestPayload Mt910Response receivedReqeust){
		
		RTGSResponse response = new RTGSResponse();
		response.setCurrencyCode(receivedReqeust.getCurrency());
		response.setCurrencyDate(receivedReqeust.getCurrencyDate().toGregorianCalendar().getTime());
		response.setMessageId(receivedReqeust.getMessageId());
		response.setRequestId(receivedReqeust.getRequestMessageId());
		response.setResponseType(RTGSResponseType.RecieverAccount);
		response.setValue(receivedReqeust.getAmount().doubleValue());
		response.setBank(bankService.findBankByLeadNumber(receivedReqeust.getRecieverBankTransactionAccount().substring(0, 3)));
		
		
		rtgsResponseService.save(response);
		return receivedReqeust;
	}
	
}
