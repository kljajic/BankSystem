package com.service;

import java.util.Collection;
import java.util.Date;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	AnalyticalStatement createAnalyticalStatement(String currencyId, 
												  String paymentTypeId, 
												  String cityId, 
												  Long dailyAccountStatusId,
												  Date dateOfReceipt,
												  Date currencyDate,
												  AnalyticalStatement analyticalStatement);
	
	Collection<AnalyticalStatement> getAnalyticalStatements();
	
	AnalyticalStatement getAnalyticalStatement(Long id);
	
	AnalyticalStatement updateAnalyticalStatement(String currencyId,
												  String paymentTypeId, 
												  String cityId, 
												  Long dailyAccountStatusId,
												  Date dateOfReceipt,
												  Date currencyDate,
			  									  AnalyticalStatement analyticalStatement);
	
	void deleteAnalyticalStatement(Long id);
	
	Collection<AnalyticalStatement> getAnalyticalStatementsByPaymentTypeId(Long id);
	
	Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(Long id);

}
