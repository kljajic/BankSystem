package com.service;

import java.util.Collection;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	AnalyticalStatement createAnalyticalStatement(AnalyticalStatement analyticalStatement);
	Collection<AnalyticalStatement> getAnalyticalStatements();
	AnalyticalStatement updateAnalyticalStatement(AnalyticalStatement analyticalStatement);
	void deleteAnalyticalStatement(Long id);

}
