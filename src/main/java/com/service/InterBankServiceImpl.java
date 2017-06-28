package com.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bank.wsdl.ClearingAndSettlementItem;
import com.bank.wsdl.Mt102Request;
import com.bank.wsdl.Mt103Request;
import com.bank.wsdl.Mt900Response;
import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.model.Bank;
import com.model.Currency;
import com.model.xml.ClearingSettlementRequest;
import com.model.xml.RTGSRequest;
import com.model.xml.RTGSResponse;
import com.model.xml.RTGSResponseType;
import com.repository.CurrencyExchangeRepository;
import com.repository.CurrencyRepository;
import com.repository.RTGSRequestRepository;
import com.webservice.client.CentralBankClient;

@Service
public class InterBankServiceImpl implements InterBankService {

	private ConcurrentHashMap<String,Mt102Request> clearingSettlementRequests = new ConcurrentHashMap<>();
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@Autowired
	private RTGSRequestRepository rtgsRequestRepository;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private ClearingSettlementRequestService clearingAndSettlementRequestService;
	
	@Autowired
	private CentralBankClient centralBankClient;
	
	@Autowired
	private RTGSResponseService rtgsResponseService;
	
	@Override
	public void generateRTGSRequest(AnalyticalStatement as) {
		RTGSRequest req = new RTGSRequest();
		req.setAnalyticalStatement(as);
		Bank paymentBank = bankService.findBankByLeadNumber(as.getOriginatorAccount().substring(0, 3));
		req.setPaymentBank(paymentBank);
		Bank receiverBank = bankService.findBankByLeadNumber(as.getRecipientAccount().substring(0, 3));
		req.setRecieverBank(receiverBank);
		req.setMessageId("MT103");
		
		rtgsRequestRepository.save(req);

		try{
			Mt103Request mt103Request = mapAnalyticalStatementToMt103Request(as, paymentBank, receiverBank);
			Mt900Response mt900Response = centralBankClient.getRtgsResponse(mt103Request);
			RTGSResponse rtgsResponse = new RTGSResponse();
			rtgsResponse.setMessageId(mt900Response.getMessageId());
			rtgsResponse.setBank(paymentBank);
			rtgsResponse.setCurrencyCode(mt900Response.getCurrency());
			rtgsResponse.setCurrencyDate(mt900Response.getCurrencyDate().toGregorianCalendar().getTime());
			rtgsResponse.setRequestId(mt900Response.getRequestMessageId());
			rtgsResponse.setValue(mt900Response.getAmount().doubleValue());
			rtgsResponse.setResponseType(RTGSResponseType.PaymentAccount);
			rtgsResponseService.save(rtgsResponse);
		}catch (DatatypeConfigurationException | XmlMappingException | IOException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 120000)
	@Override
	public void generateClearingService() {
		if(!clearingSettlementRequests.isEmpty()){
			for(Map.Entry<String, Mt102Request> pair : clearingSettlementRequests.entrySet()){
				Mt900Response response =centralBankClient.getMt102Request(pair.getValue());
				System.out.println(response);
				System.out.println("VRATIO MT900");
			}
		}
		clearingSettlementRequests.clear();
	}

	@Override
	public void addToClearing(AnalyticalStatement as) {
		Bank receiverBank = bankService.findBankByLeadNumber(as.getRecipientAccount().substring(0, 3));
		Bank paymentBank = bankService.findBankByLeadNumber(as.getOriginatorAccount().substring(0, 3));
		if(!clearingSettlementRequests.contains(receiverBank.getSwift()+":"+as.getCurrency().getOfficialCode())){
			ClearingSettlementRequest csr = new ClearingSettlementRequest();
			csr.setPaymentBank(paymentBank);
			csr.setRecieverBank(receiverBank);
			csr.setTotalAmount(as.getAmount());
			csr.setCurrency(as.getCurrency());
			csr.setCurrencyDate(as.getCurrencyDate());
			csr.setDate(new Date());
			HashSet<AnalyticalStatement> tempAS = new HashSet<>();
			tempAS.add(as);
			csr.setAnalyticalStatements(tempAS);
			clearingAndSettlementRequestService.save(csr);
			try{
				makeMt102Request(paymentBank, receiverBank, as.getCurrency(), as.getCurrencyDate());
			}catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
		}else{
			ClearingSettlementRequest clearingSettlementRequest = clearingAndSettlementRequestService.search(paymentBank.getId(), receiverBank.getId());
			clearingSettlementRequest.getAnalyticalStatements().add(as);
			clearingAndSettlementRequestService.save(clearingSettlementRequest);
		}
		
		try{
			ClearingAndSettlementItem clearingAndSettlementItem = this.mapAnalyticalStatementToClearingAndSetlmentItem(as);
			Mt102Request mt102Request = clearingSettlementRequests.get(receiverBank.getSwift()+":"+as.getCurrency().getOfficialCode());
			mt102Request.setAmount(new BigDecimal(mt102Request.getAmount().doubleValue()+as.getAmount()));
			mt102Request.getStatementItems().add(clearingAndSettlementItem);
		}catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void RTGSOrClearing(AnalyticalStatement as){
		double sum;
		if(!as.getCurrency().getOfficialCode().equals("DIN")){
			sum = as.getAmount() * currencyExchangeRepository.findMiddleRateAccordingToDinars(as.getCurrency().getOfficialCode());
		} else {
			sum = as.getAmount();
		}
		if(sum >= 250000 || as.isUrgently()){
			generateRTGSRequest(as);
		} else {
				addToClearing(as);
		}
	}
		
	private Mt103Request mapAnalyticalStatementToMt103Request(AnalyticalStatement as, Bank originatorBank, Bank recieverBank) throws DatatypeConfigurationException{
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		
		Mt103Request mt103Request = new Mt103Request();
		mt103Request.setMessageId("mt103");
		mt103Request.setAmount(new BigDecimal(as.getAmount()));
		mt103Request.setChargeDialNumber(as.getDebitAuthorizationNumber());
		mt103Request.setChargeModel(as.getModel());
		mt103Request.setClearanceDialNumber(as.getApprovalAuthorizationNumber());
		mt103Request.setClearanceModel(as.getApprovalModel());
		mt103Request.setCurrency(as.getCurrency().getOfficialCode());
		
		gregorianCalendar.setTime(as.getCurrencyDate());
		XMLGregorianCalendar currencyDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		mt103Request.setCurrencyDate(currencyDateXml);
		
		mt103Request.setOriginator(as.getOriginator());
		mt103Request.setOriginatorAccountNumber(as.getOriginatorAccount());
		mt103Request.setOriginatorBankSwiftCode(originatorBank.getSwift());
		mt103Request.setOriginatorBankTransactionAccount(originatorBank.getTransactionAccount());
		mt103Request.setPaymentPurpose(as.getPurpose());
		mt103Request.setReciever(as.getRecipient());
		mt103Request.setRecieverAccountNumber(as.getRecipientAccount());
		mt103Request.setRecieverBankSwiftCode(recieverBank.getSwift());
		mt103Request.setRecieverBankTransactionAccount(recieverBank.getTransactionAccount());
		
		gregorianCalendar.setTime(as.getDateOfReceipt());
		XMLGregorianCalendar dateOfReceit = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		mt103Request.setStatementDate(dateOfReceit);
		
		return mt103Request;
	}
	
	private ClearingAndSettlementItem mapAnalyticalStatementToClearingAndSetlmentItem(AnalyticalStatement as) throws DatatypeConfigurationException{
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		
		ClearingAndSettlementItem clearingAndSettlementItem = new ClearingAndSettlementItem();
		clearingAndSettlementItem.setRequestId("requestId");
		clearingAndSettlementItem.setAmount(new BigDecimal(as.getAmount()));
		clearingAndSettlementItem.setChargeDialNumber(as.getDebitAuthorizationNumber());
		clearingAndSettlementItem.setChargeModel(as.getModel());
		clearingAndSettlementItem.setClearanceDialNumber(as.getApprovalAuthorizationNumber());
		clearingAndSettlementItem.setClearanceModel(as.getApprovalModel());
		clearingAndSettlementItem.setCurrency(as.getCurrency().getOfficialCode());
		
		clearingAndSettlementItem.setOriginator(as.getOriginator());
		clearingAndSettlementItem.setOriginatorAccountNumber(as.getOriginatorAccount());
		clearingAndSettlementItem.setPaymentPurpose(as.getPurpose());
		clearingAndSettlementItem.setReciever(as.getRecipient());
		clearingAndSettlementItem.setRecieverAccountNumber(as.getRecipientAccount());
		
		gregorianCalendar.setTime(as.getDateOfReceipt());
		XMLGregorianCalendar dateOfReceit = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		clearingAndSettlementItem.setStatementDate(dateOfReceit);
		
		return clearingAndSettlementItem;
	}
	
	private void makeMt102Request(Bank paymentBank, Bank receiverBank, Currency currency, Date currencyDate) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		
		Mt102Request mt102request = new Mt102Request();
		mt102request.setAmount(new BigDecimal(0));
		mt102request.setCurrency(currency.getOfficialCode());
		
		gregorianCalendar.setTime(currencyDate);
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		mt102request.setCurrencyDate(date);
		
		gregorianCalendar.setTime(new Date());
		XMLGregorianCalendar todayDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		mt102request.setDate(todayDate);
		mt102request.setMessageId("mt102Request");
		mt102request.setOriginatorBankSwiftCode(paymentBank.getSwift());
		mt102request.setOriginatorBankTransactionAccount(paymentBank.getTransactionAccount());
		mt102request.setRecieverBankSwiftCode(receiverBank.getSwift());
		mt102request.setRecieverBankTransactionAccount(receiverBank.getTransactionAccount());
		
		clearingSettlementRequests.put(receiverBank.getSwift()+":"+currency.getOfficialCode(), mt102request);
	}

	@Override
	public void receiveClearings(Mt102Request request) {
		// TODO unwrap account statements, save them and update daily account statuses
		for(ClearingAndSettlementItem item : request.getStatementItems()){
			AnalyticalStatement as = mapToAnalyticalStatement(request,item);
			//as.save()
			//azurirate dailyAccountStatuse
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
		as.setCurrency(currencyRepository.getCurrencyByOfficialCode(request.getCurrency()));
		as.setDateOfReceipt(request.getDate().toGregorianCalendar().getTime());
		as.setOriginator(item.getOriginator());
		as.setOriginatorAccount(item.getOriginatorAccountNumber());
		as.setUplata(true);
		as.setRecipient(item.getReciever());
		as.setRecipientAccount(item.getRecieverAccountNumber());
		as.setPurpose(item.getPaymentPurpose());
		as.setUrgently(false);
		//as.setDailyAccountStatus(dailyAccountStatus);
		//as.setClearingSettlementRequest(clearingSettlementRequest);
		//as.setPlaceOfAcceptance(request.);
		//as.setRtgsRequests(rtgsRequests);
		return as;
	}
	
}
