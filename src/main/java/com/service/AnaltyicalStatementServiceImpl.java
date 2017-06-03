package com.service;

import java.util.Collection;
import java.util.Date;

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
	
	
	public AnaltyicalStatementServiceImpl(AnalyticalStatementRepository analyticalStatementRepository,
										  CurrencyService currencyService,
										  PaymentTypeService paymentTypeService,
										  CityService cityService,
										  DailyAccountStatusService dailyAccountStatusService){
		this.analyticalStatementRepository = analyticalStatementRepository;
		this.currencyService = currencyService;
		this.paymentTypeService = paymentTypeService;
		this.cityService = cityService;
		this.dailyAccountStatusService = dailyAccountStatusService;
	}

	@Override
	public AnalyticalStatement createAnalyticalStatement(String currencyId, String paymentTypeId, String cityId, 
														 Long dailyAccountStatusId, Date dateOfReceipt,
														  Date currencyDate, AnalyticalStatement analyticalStatement) {
		if(currencyId != null && !currencyId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setCurrency(currencyService.getCurrency(new Long(currencyId)));
		if(paymentTypeId != null && !paymentTypeId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setPaymentType(paymentTypeService.getPaymentType(new Long(paymentTypeId)));
		if(cityId != null && !cityId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setPlaceOfAcceptance(cityService.getCity(new Long(cityId)));
		analyticalStatement.setDateOfReceipt(dateOfReceipt);
		analyticalStatement.setCurrencyDate(currencyDate);
		analyticalStatement.setDailyAccountStatus(dailyAccountStatusService.getDailyAccountStatus(dailyAccountStatusId));
		return analyticalStatementRepository.save(analyticalStatement);
	}

	@Override
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementRepository.findAll();
	}
	
	@Override
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
	
}
