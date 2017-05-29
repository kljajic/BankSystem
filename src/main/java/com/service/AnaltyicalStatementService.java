package com.service;

import java.util.Collection;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	AnalyticalStatement createAnalyticalStatement(String currencyId, String paymentTypeId, String cityId, Long dailyAccountStatusId, 
												  AnalyticalStatement analyticalStatement);
	Collection<AnalyticalStatement> getAnalyticalStatements();
	AnalyticalStatement getAnalyticalStatement(Long id);
	AnalyticalStatement updateAnalyticalStatement(String currencyId, String paymentTypeId, String cityId, Long dailyAccountStatusId, 
			  									  AnalyticalStatement analyticalStatement);
	void deleteAnalyticalStatement(Long id);

}
