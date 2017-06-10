package com.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.AnalyticalStatement;
import com.repository.AnalyticalStatementRepository;

@Service
@Transactional
public class AnaltyicalStatementServiceImpl implements AnaltyicalStatementService {
	
	private final AnalyticalStatementRepository analyticalStatementRepository;
	private final CurrencyService currencyService;
	private final PaymentTypeService paymentTypeService;
	private final CityService cityService;
	private final DailyAccountStatusService dailyAccountStatusService;
	private final InterBankService interBankService;
	
	
	@Autowired
	public AnaltyicalStatementServiceImpl(AnalyticalStatementRepository analyticalStatementRepository,
										  CurrencyService currencyService,
										  PaymentTypeService paymentTypeService,
										  CityService cityService,
										  DailyAccountStatusService dailyAccountStatusService,
										  InterBankService interBankService
										  ){
		this.analyticalStatementRepository = analyticalStatementRepository;
		this.currencyService = currencyService;
		this.paymentTypeService = paymentTypeService;
		this.cityService = cityService;
		this.dailyAccountStatusService = dailyAccountStatusService;
		this.interBankService = interBankService;
	}

	@Override
	public Collection<AnalyticalStatement> createAnalyticalStatement(String currencyId, String paymentTypeId, String cityId, 
														 Long dailyAccountStatusId, Date dateOfReceipt,
														  Date currencyDate, AnalyticalStatement analyticalStatement) {
		Collection<AnalyticalStatement> analyticalStatements = new ArrayList<AnalyticalStatement>();
		if(currencyId != null && !currencyId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setCurrency(currencyService.getCurrency(new Long(currencyId)));
		if(paymentTypeId != null && !paymentTypeId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setPaymentType(paymentTypeService.getPaymentType(new Long(paymentTypeId)));
		if(cityId != null && !cityId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setPlaceOfAcceptance(cityService.getCity(new Long(cityId)));
		analyticalStatement.setDateOfReceipt(dateOfReceipt);
		analyticalStatement.setCurrencyDate(currencyDate);
		analyticalStatement.setDailyAccountStatus(dailyAccountStatusService.getDailyAccountStatus(dailyAccountStatusId));
		analyticalStatement.setUplata(false);
		if(!analyticalStatement.getOriginatorAccount().substring(0, 3).equals(analyticalStatement.getRecipientAccount().substring(0, 3))){
			interBankService.RTGSOrClearing(analyticalStatement);
			analyticalStatements.add(analyticalStatementRepository.save(analyticalStatement));
			dailyAccountStatusService.updateOriginatorDailyAccountStatus(analyticalStatement);
		}else{
			this.doLocaleTransfer(analyticalStatement, analyticalStatements);
		}
		return analyticalStatements;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticalStatement getAnalyticalStatement(Long id) {
		return analyticalStatementRepository.findOne(id);
	}

	@Override
	public AnalyticalStatement updateAnalyticalStatement(String currencyId, String paymentTypeId, String cityId, Long dailyAccountStatusId, 
														 Date dateOfReceipt, Date currencyDate, AnalyticalStatement analyticalStatement) {
		AnalyticalStatement temp = analyticalStatementRepository.findOne(analyticalStatement.getId());
		temp.setAmount(analyticalStatement.getAmount());
		temp.setApprovalAuthorizationNumber(analyticalStatement.getApprovalAuthorizationNumber());
		temp.setApprovalModel(analyticalStatement.getApprovalModel());
		if(currencyId != null && !currencyId.trim().equals("NOT_ENTERED"))
			temp.setCurrency(currencyService.getCurrency(new Long(currencyId)));
		if(paymentTypeId != null && !paymentTypeId.trim().equals("NOT_ENTERED"))
			temp.setPaymentType(paymentTypeService.getPaymentType(new Long(paymentTypeId)));
		if(cityId != null && !cityId.trim().equals("NOT_ENTERED"))
			temp.setPlaceOfAcceptance(cityService.getCity(new Long(cityId)));
		temp.setCurrencyDate(currencyDate);
		temp.setDailyAccountStatus(dailyAccountStatusService.getDailyAccountStatus(dailyAccountStatusId));
		temp.setDateOfReceipt(dateOfReceipt);
		temp.setDebitAuthorizationNumber(analyticalStatement.getDebitAuthorizationNumber());
		temp.setErrorType(analyticalStatement.getErrorType());
		temp.setModel(analyticalStatement.getModel());
		temp.setOriginator(analyticalStatement.getOriginator());
		temp.setOriginatorAccount(analyticalStatement.getOriginatorAccount());
		temp.setPurpose(analyticalStatement.getPurpose());
		temp.setRecipient(analyticalStatement.getRecipient());
		temp.setRecipientAccount(analyticalStatement.getRecipientAccount());
		temp.setUplata(analyticalStatement.isUplata());
		temp.setUrgently(analyticalStatement.isUrgently());
		return analyticalStatementRepository.save(temp);
	}

	@Override
	public void deleteAnalyticalStatement(Long id) {
		analyticalStatementRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByPaymentTypeId(Long id) {
		return analyticalStatementRepository.findAnalyticalStatementsByPaymentTypeId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(Long id) {
		return analyticalStatementRepository.findAnalyticalStatementsByDailyAccountStatusId(id);
	}

	@Override
	public Collection<AnalyticalStatement> searchAnalyticalStatements(Long currencyId, Long paymentTypeId,
			Long cityId, Long dailyAccountStatusId, Date dateOfReceipt, Date currencyDate,
			AnalyticalStatement analyticalStatement) {
		String currencyCode = "";
		if(currencyId != null && currencyId >= 0){
			currencyCode = currencyService.getCurrency(currencyId).getOfficialCode();
		}
		String paymentTypeName = "";
		if(paymentTypeId != null && paymentTypeId >= 0){
			paymentTypeName = paymentTypeService.getPaymentType(paymentTypeId).getPaymentTypeName();
		}
		String cityPttNumber = "";
		if(cityId != null && cityId >= 0){
			cityPttNumber = cityService.getCity(cityId).getPttNumber();
		}
		String accountNumber = "";
		if(dailyAccountStatusId != null && dailyAccountStatusId >= 0){
			accountNumber = dailyAccountStatusService.getDailyAccountStatus(dailyAccountStatusId).getAccount().getAccountNumber();
		}
		String originator = "";
		if(analyticalStatement.getOriginator() != null && !analyticalStatement.getOriginator().trim().equals("")){
			originator = analyticalStatement.getOriginator();
		}
		String purpose = "";
		if(analyticalStatement.getPurpose() != null && !analyticalStatement.getPurpose().trim().equals("")){
			purpose = analyticalStatement.getPurpose();
		}
		String recipient = "";
		if(analyticalStatement.getRecipient() != null && !analyticalStatement.getRecipient().trim().equals("")){
			recipient = analyticalStatement.getRecipient();
		}
		if(dateOfReceipt == null){
			dateOfReceipt = new Date(Long.MAX_VALUE);
		}
		if(currencyDate == null){
			currencyDate = new Date(Long.MAX_VALUE);
		}
		String originatorAccount = "";
		if(analyticalStatement.getOriginatorAccount() != null && !analyticalStatement.getOriginatorAccount().trim().equals("")){
			originatorAccount = analyticalStatement.getOriginatorAccount();
		}
		String model = "";
		if(analyticalStatement.getModel() > 0){
			model = analyticalStatement.getModel() + "";
		}
		String debitAuthorizationNumber = "";
		if(analyticalStatement.getDebitAuthorizationNumber() != null && !analyticalStatement.getDebitAuthorizationNumber().trim().equals("")){
			debitAuthorizationNumber = analyticalStatement.getDebitAuthorizationNumber();
		}
		String recipientAccount = "";
		if(analyticalStatement.getRecipientAccount() != null && !analyticalStatement.getRecipientAccount().trim().equals("")){
			recipientAccount = analyticalStatement.getRecipientAccount();
		}
		String approvalModel = "";
		if(analyticalStatement.getApprovalModel() > 0){
			approvalModel = analyticalStatement.getApprovalModel() + "";
		}
		String approvalAuthorizationNumber = "";
		if(analyticalStatement.getApprovalAuthorizationNumber() != null && !analyticalStatement.getApprovalAuthorizationNumber().trim().equals("")){
			approvalAuthorizationNumber = analyticalStatement.getApprovalAuthorizationNumber();
		}
		boolean urgently = analyticalStatement.isUrgently();
		Double amount = new Double(Double.MAX_VALUE);
		if(analyticalStatement.getAmount() > 0){
			amount = new Double(analyticalStatement.getAmount());
		}
		Date minimumDate = new Date(Long.MIN_VALUE);
		return analyticalStatementRepository.searchAnalyticalStatements(currencyCode, paymentTypeName,
								cityPttNumber, accountNumber, originator, purpose, recipient,
								minimumDate, dateOfReceipt, currencyDate, originatorAccount, model,
								debitAuthorizationNumber, recipientAccount, approvalModel, 
								approvalAuthorizationNumber, urgently, amount);
	}
	
	private void doLocaleTransfer(AnalyticalStatement analyticalStatement,
			Collection<AnalyticalStatement> analyticalStatements) {
		AnalyticalStatement payment = analyticalStatementRepository.save(analyticalStatement);
		analyticalStatement.setUplata(true);
		AnalyticalStatement payoff = analyticalStatementRepository.save(analyticalStatement);
		analyticalStatements.add(payment);
		analyticalStatements.add(payoff);
		dailyAccountStatusService.updateOriginatorDailyAccountStatus(analyticalStatement);
		dailyAccountStatusService.updateRecipiantDailyAccountStatus(analyticalStatement);
	}
	
}
