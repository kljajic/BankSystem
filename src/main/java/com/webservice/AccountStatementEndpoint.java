package com.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.service.AnaltyicalStatementService;
import com.service.CityService;
import com.service.CurrencyService;
import com.xsdschemas.accountstatementrequest.AccountStatementRequest;
import com.xsdschemas.accountstatementsection.AccountStatementSectionResponse;
import com.xsdschemas.statement.StatementRequest;

@Endpoint
public class AccountStatementEndpoint {

	private static final String NAMESPACE_URI = "http://com/xsdSchemas";
	
	private final AnaltyicalStatementService analtyicalStatementService;
	private final CurrencyService currencyService;
	private final CityService cityService;
	
	@Autowired
	public AccountStatementEndpoint(AnaltyicalStatementService analtyicalStatementService,
									CurrencyService currencyService,
									CityService cityService){
		this.analtyicalStatementService = analtyicalStatementService;
		this.currencyService = currencyService;
		this.cityService = cityService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI + "/statement", localPart = "statementRequest")
	public void addAnalyticalStatement(@RequestPayload StatementRequest request){
		AnalyticalStatement newAnalyticalStatement = new AnalyticalStatement();
		newAnalyticalStatement.setAmount(request.getAmount().doubleValue());
		String originatorAccount = request.getOriginatorAccountNumber().trim();
		String recipientAccount = request.getRecieverAccountNumber().trim();
		if(!originatorAccount.equals("") && !recipientAccount.equals("")){
			newAnalyticalStatement.setAnalyticalStatementMode(AnalyticalStatementMode.TRANSFER);
		}else if (!recipientAccount.equals("") && originatorAccount.equals("")){
			newAnalyticalStatement.setAnalyticalStatementMode(AnalyticalStatementMode.PAYMENT);
		}else if (recipientAccount.equals("") && !originatorAccount.equals("")){
			newAnalyticalStatement.setAnalyticalStatementMode(AnalyticalStatementMode.PAYOUT);
		}
		newAnalyticalStatement.setApprovalAuthorizationNumber(request.getChargeDialNumber());
		newAnalyticalStatement.setApprovalModel(request.getClearanceModel());
		newAnalyticalStatement.setDebitAuthorizationNumber(request.getClearanceDialNumber());
		newAnalyticalStatement.setModel(request.getChargeModel());
		newAnalyticalStatement.setOriginator(request.getOriginator());
		newAnalyticalStatement.setOriginatorAccount(request.getOriginatorAccountNumber());
		newAnalyticalStatement.setPurpose(request.getPaymentPurpose());
		newAnalyticalStatement.setRecipient(request.getReciever());
		newAnalyticalStatement.setRecipientAccount(request.getRecieverAccountNumber());
		newAnalyticalStatement.setUplata(true);
		newAnalyticalStatement.setUrgently(false);
		analtyicalStatementService.createAnalyticalStatement(currencyService.getCurrencyByOfficialCode(request.getCurrency()).getId().toString(),
				this.cityService.getCityByName(request.getPlaceOfAcceptance()).getId().toString(), 
				request.getStatementDate().toGregorianCalendar().getTime(), 
				request.getCurrencyDate().toGregorianCalendar().getTime(), newAnalyticalStatement);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI + "/accountStatementRequest", localPart = "accountStatementRequest")
	@ResponsePayload
	public AccountStatementSectionResponse getAccountStatementResponse(@RequestPayload AccountStatementRequest request){
		return null;
	}
	
}
