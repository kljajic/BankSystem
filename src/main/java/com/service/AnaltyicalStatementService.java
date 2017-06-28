package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import com.model.AnalyticalStatement;

import net.sf.jasperreports.engine.JRException;

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
	
	void exportToPdf(Long accountId,Date startDate,Date endDate,HttpServletResponse response) throws JRException, SQLException, IOException;
	
	Collection<AnalyticalStatement> doTransaction(AnalyticalStatement analyticalStatement);
	
	List<AnalyticalStatement> getAnalyticalStatementsForDailyAccountStatusId(Pageable pageable, Long dailyAccountStatusId);

	void receiveClearingOrRtgs(AnalyticalStatement analyticalStatement);

}
