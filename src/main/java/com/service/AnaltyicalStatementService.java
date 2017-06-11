package com.service;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	Collection<AnalyticalStatement> createAnalyticalStatement(String currencyId, 
												  String paymentTypeId, 
												  String cityId,
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
	
	Collection<AnalyticalStatement> searchAnalyticalStatements(Long currencyId,
			  Long paymentTypeId, 
			  Long cityId, 
			  Long dailyAccountStatusId,
			  Date dateOfReceipt,
			  Date currencyDate,
			  AnalyticalStatement analyticalStatement);
	
	void exportToPdf(Long accountId,Date startDate,Date endDate,HttpServletResponse response);
	
	public Collection<AnalyticalStatement> doTransaction(AnalyticalStatement analyticalStatement);

}
