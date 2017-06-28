package com.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.bank.wsdl.ClearingAndSettlementItem;
import com.bank.wsdl.Mt102Request;
import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.service.AnaltyicalStatementService;
import com.service.CurrencyService;

@Endpoint
public class ClearingResponseEndpoint {

	private static final String NAMESPACE = "http://com/xsdSchemas/";
	private final AnaltyicalStatementService analyticalStatementService;
	private final CurrencyService currencyService;
	
	@Autowired
	public ClearingResponseEndpoint(AnaltyicalStatementService analyticalStatementService, CurrencyService currencyService) {
		this.analyticalStatementService = analyticalStatementService;
		this.currencyService = currencyService;
	}
	
	@PayloadRoot(namespace=NAMESPACE + "clearingAndSettlement", localPart="mt102Request")
	public void getRtgsMt910Response(@RequestPayload Mt102Request request){
		for(ClearingAndSettlementItem item : request.getStatementItems()){
			AnalyticalStatement analyticalStatement = mapToAnalyticalStatement(request,item);
			analyticalStatementService.receiveClearingOrRtgs(analyticalStatement);
		}	
	}
	
	private AnalyticalStatement mapToAnalyticalStatement(Mt102Request request, ClearingAndSettlementItem item) {
		AnalyticalStatement as = new AnalyticalStatement();
		as.setAmount(item.getAmount().doubleValue());
		as.setAnalyticalStatementMode(AnalyticalStatementMode.TRANSFER);
		as.setApprovalAuthorizationNumber(item.getClearanceDialNumber());
		as.setDebitAuthorizationNumber(item.getChargeDialNumber());
		as.setApprovalModel(item.getClearanceModel());
		as.setModel(item.getChargeModel());
		as.setCurrency(currencyService.getCurrencyByOfficialCode(request.getCurrency()));
		as.setCurrencyDate(request.getCurrencyDate().toGregorianCalendar().getTime());
		as.setDateOfReceipt(request.getDate().toGregorianCalendar().getTime());
		as.setOriginator(item.getOriginator());
		as.setOriginatorAccount(item.getOriginatorAccountNumber());
		as.setUplata(true);
		as.setRecipient(item.getReciever());
		as.setRecipientAccount(item.getRecieverAccountNumber());
		as.setPurpose(item.getPaymentPurpose());
		as.setUrgently(false);
		//as.setClearingSettlementRequest(clearingSettlementRequest);
		//as.setPlaceOfAcceptance(cityService.getCityByName());
		//as.setRtgsRequests(rtgsRequests);
		return as;
	}
	
}
