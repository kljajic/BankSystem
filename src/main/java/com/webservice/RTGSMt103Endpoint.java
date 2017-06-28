package com.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bank.wsdl.Mt103Request;
import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.model.Bank;
import com.model.xml.RTGSRequest;
import com.service.AnaltyicalStatementService;
import com.service.BankService;
import com.service.CurrencyService;


@Endpoint
public class RTGSMt103Endpoint {
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private AnaltyicalStatementService analtyicalStatementService;
	
	//@Autowired
	//private RTGSRequestService rtgsRequestService;
	
	@Autowired
	private BankService bankService;

	private static final String NAMESPACE_URI = "http://com/xsdSchemas/";

	@PayloadRoot(namespace = NAMESPACE_URI+"rtgsRequest", localPart= "mt103Request")
	@ResponsePayload
	public Mt103Request getMt103Request(@RequestPayload Mt103Request req) {
		
		AnalyticalStatement as = new AnalyticalStatement();
		as.setOriginator(req.getOriginator());
		as.setPurpose(req.getPaymentPurpose());
		as.setRecipient(req.getReciever());
		as.setDateOfReceipt(req.getStatementDate().toGregorianCalendar().getTime());
		as.setCurrencyDate(req.getCurrencyDate().toGregorianCalendar().getTime());
		as.setOriginatorAccount(req.getOriginatorAccountNumber());
		as.setModel(req.getChargeModel());
		as.setDebitAuthorizationNumber(req.getChargeDialNumber());
		
		as.setRecipientAccount(req.getRecieverAccountNumber());
		as.setApprovalModel(req.getClearanceModel());
		as.setApprovalAuthorizationNumber(req.getClearanceDialNumber());
		as.setUrgently(true);
		as.setAmount(req.getAmount().doubleValue());
		as.setCurrency(currencyService.getCurrencyByOfficialCode(req.getCurrency()));
		as.setUplata(true);
		as.setAnalyticalStatementMode(AnalyticalStatementMode.TRANSFER);
		
		analtyicalStatementService.receiveClearingOrRtgs(as);
		
		RTGSRequest rtgs = new RTGSRequest();
		rtgs.setMessageId("MT103");
		
		Bank paymentBank = bankService.findBankByLeadNumber(req.getOriginatorBankTransactionAccount().substring(0, 3));
		Bank recieverBank = bankService.findBankByLeadNumber(req.getRecieverBankTransactionAccount().substring(0, 3));
		
		rtgs.setPaymentBank(paymentBank);
		rtgs.setRecieverBank(recieverBank);
		rtgs.setAnalyticalStatement(as);
		
		//rtgsRequestService.save(rtgs);
		return req;
	}
	
}
