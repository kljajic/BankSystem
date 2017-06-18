package com.webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.model.Account;
import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.model.DailyAccountStatus;
import com.service.AccountService;
import com.service.AnaltyicalStatementService;
import com.service.CityService;
import com.service.CurrencyService;
import com.service.DailyAccountStatusService;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.xsdschemas.accountstatementrequest.AccountStatementRequest;
import com.xsdschemas.accountstatementsection.AccountStatementSectionResponse;
import com.xsdschemas.accountstatementsectionitem.AccountStatementSectionItem;
import com.xsdschemas.statement.StatementRequest;

@Endpoint
public class AccountStatementEndpoint {

	private static final String NAMESPACE_URI = "http://com/xsdSchemas";
	
	private final AnaltyicalStatementService analyticalStatementService;
	private final CurrencyService currencyService;
	private final CityService cityService;
	private final AccountService accountService;
	private final DailyAccountStatusService dailyAccountStatusService;
	
	@Autowired
	public AccountStatementEndpoint(AnaltyicalStatementService analyticalStatementService,
									CurrencyService currencyService,
									CityService cityService,
									AccountService accountService,
									DailyAccountStatusService dailyAccountStatusService){
		this.analyticalStatementService = analyticalStatementService;
		this.currencyService = currencyService;
		this.cityService = cityService;
		this.accountService = accountService;
		this.dailyAccountStatusService = dailyAccountStatusService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI + "/statement", localPart = "statementRequest")
	public void addAnalyticalStatement(@RequestPayload StatementRequest request){
		AnalyticalStatement newAnalyticalStatement = getAnalyticalStatement(request);
		analyticalStatementService.createAnalyticalStatement(currencyService.getCurrencyByOfficialCode(request.getCurrency()).getId().toString(),
				this.cityService.getCityByName(request.getPlaceOfAcceptance()).getId().toString(), 
				request.getStatementDate().toGregorianCalendar().getTime(), 
				request.getCurrencyDate().toGregorianCalendar().getTime(), newAnalyticalStatement);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI + "/accountStatementRequest", localPart = "accountStatementRequest")
	@ResponsePayload
	public AccountStatementSectionResponse getAccountStatementResponse(@RequestPayload AccountStatementRequest request){
		Account clientAccount = accountService.getAccountByAccountNumber(request.getAccountNumber());
		if(clientAccount == null){
			return null;
		}
		DailyAccountStatus dailyAccountStatus = dailyAccountStatusService.getDailyAccountStatusForAccount(clientAccount.getId(), request.getDate().toGregorianCalendar().getTime());
		if(dailyAccountStatus == null){
			return null;
		}
		List<AnalyticalStatement> statements=
				analyticalStatementService.getAnalyticalStatementsForDailyAccountStatusId(new PageRequest(request.getSectionOrdinate(), 5), dailyAccountStatus.getId());
		Collection<AnalyticalStatement> allStatements=analyticalStatementService.getAnalyticalStatementsByDailyAccountStatusId(dailyAccountStatus.getId());
		if(statements != null && statements.size() > 0){
			AccountStatementSectionResponse response = new AccountStatementSectionResponse();
			response.setAccountNumber(clientAccount.getAccountNumber());
			
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			
			try {
				response.setRequestDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			} catch (DatatypeConfigurationException e) {
				response.setRequestDate(request.getDate());
			}

			response.setSectionOrdinate(request.getSectionOrdinate());
			response.setSectionOrdinate((short)dailyAccountStatus.getNumberOfChanges());
			response.setPreviousBalance(new BigDecimal(dailyAccountStatus.getPreviousAmount()));
			response.setCurrentBalance(new BigDecimal(dailyAccountStatus.getCurrentAmount()));
			int numberOfChangesProfit = getNumberOfChangesInFavor(allStatements);
			response.setNumberOfChangesProfit(new BigInteger(numberOfChangesProfit+""));
			int numberOfChangesDue = allStatements.size() - numberOfChangesProfit;
			response.setNumberOfChangesDue(new BigInteger(numberOfChangesDue+""));
			response.setTotalDue(new BigDecimal(dailyAccountStatus.getTransferExpenses()));
			response.setTotalProfit(new BigDecimal(dailyAccountStatus.getTransferInFavor()));
			for(AnalyticalStatement statement : statements){
				AccountStatementSectionItem sectionItem = getAccountStatementSectionItem(statement);
				response.getAccountStatementSectionItems().add(sectionItem);
			}
			return response;
		}
		return null;
	}
	
	private AnalyticalStatement getAnalyticalStatement(StatementRequest request){
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
		return newAnalyticalStatement;
	}
	
	private AccountStatementSectionItem getAccountStatementSectionItem(AnalyticalStatement analyticalStatement){
		AccountStatementSectionItem sectionItem = new AccountStatementSectionItem();
		sectionItem.setAmount(new BigDecimal(analyticalStatement.getAmount()));
		sectionItem.setChargeDialNumber(analyticalStatement.getDebitAuthorizationNumber());
		sectionItem.setChargeModel(analyticalStatement.getModel());
		sectionItem.setClearanceDialNumber(analyticalStatement.getApprovalAuthorizationNumber());
		sectionItem.setClearanceModel(analyticalStatement.getApprovalModel());
		sectionItem.setCurrency(analyticalStatement.getCurrency().getOfficialCode());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(analyticalStatement.getCurrencyDate());
		sectionItem.setCurrencyDate(XMLGregorianCalendarImpl.createDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
		sectionItem.setOriginator(analyticalStatement.getOriginator());
		sectionItem.setOriginatorAccountNumber(analyticalStatement.getOriginatorAccount());
		sectionItem.setPaymentPurpose(analyticalStatement.getPurpose());
		sectionItem.setReciever(analyticalStatement.getRecipient());
		sectionItem.setRecieverAccountNumber(analyticalStatement.getRecipientAccount());
		calendar.setTime(analyticalStatement.getDateOfReceipt());
		sectionItem.setStatementDate(XMLGregorianCalendarImpl.createDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
		return sectionItem;
	}
	
	private int getNumberOfChangesInFavor(Collection<AnalyticalStatement> analyticalStatements){
		int numberOfChangesInFavor = 0;
		for(AnalyticalStatement analyticalStatement : analyticalStatements){
			if(analyticalStatement.isUplata()){
				numberOfChangesInFavor++;
			}
		}
		return numberOfChangesInFavor;
	}
	
}
