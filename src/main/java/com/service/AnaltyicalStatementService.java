package com.service;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	Collection<AnalyticalStatement> createAnalyticalStatement(String currencyId, 
												  			  String cityId,
												  			  Date dateOfReceipt,
												  			  Date currencyDate,
												  			  AnalyticalStatement analyticalStatement);
	
	Collection<AnalyticalStatement> getAnalyticalStatements();
	
	AnalyticalStatement getAnalyticalStatement(Long id);
	
	Collection<AnalyticalStatement> updateAnalyticalStatement(String currencyId,
												  			  String cityId,
												  			  Date dateOfReceipt,
												  			  Date currencyDate,
												  			  AnalyticalStatement analyticalStatement);
	
	Collection<AnalyticalStatement> deleteAnalyticalStatement(Long id);
	
	
	Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(Long id);
	
	Collection<AnalyticalStatement> searchAnalyticalStatements(Long currencyId,
			  												   Long cityId, 
			  												   Long dailyAccountStatusId,
			  												   Date dateOfReceipt,
			  												   Date currencyDate,
			  												   AnalyticalStatement analyticalStatement);
	
	void exportToPdf(Long accountId,Date startDate,Date endDate,HttpServletResponse response);
	
	Collection<AnalyticalStatement> doTransaction(AnalyticalStatement analyticalStatement);

}
