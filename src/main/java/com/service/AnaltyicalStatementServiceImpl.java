package com.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.AnalyticalStatement;
import com.repository.AnalyticalStatementRepository;

@Service
@Transactional
public class AnaltyicalStatementServiceImpl implements AnaltyicalStatementService {
	
	private final AnalyticalStatementRepository analyticalStatementRepository;
	
	public AnaltyicalStatementServiceImpl(AnalyticalStatementRepository analyticalStatementRepository){
		this.analyticalStatementRepository = analyticalStatementRepository;
	}

	@Override
	public AnalyticalStatement createAnalyticalStatement(AnalyticalStatement analyticalStatement) {
		return analyticalStatementRepository.save(analyticalStatement);
	}

	@Override
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementRepository.findAll();
	}

	@Override
	public AnalyticalStatement updateAnalyticalStatement(AnalyticalStatement analyticalStatement) {
		//AnalyticalStatement temp = analyticalStatementRepository.findOne(analyticalStatement.getId());
		
		return null;
	}

	@Override
	public void deleteAnalyticalStatement(Long id) {
		analyticalStatementRepository.delete(id);
	}
	
}
