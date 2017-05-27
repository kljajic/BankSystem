package com.service;

import java.util.Collection;

import com.model.AnalyticalStatement;

public interface AnaltyicalStatementService {
	
	public AnalyticalStatement createAnalyticalStatement(AnalyticalStatement analyticalStatement);
	public Collection<AnalyticalStatement> getAnalyticalStatements();
	public AnalyticalStatement updateAnalyticalStatement(AnalyticalStatement analyticalStatement);
	public void deleteAnalyticalStatement(Long id);

}
