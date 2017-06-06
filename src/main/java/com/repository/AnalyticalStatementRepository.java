package com.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.AnalyticalStatement;

public interface AnalyticalStatementRepository extends JpaRepository<AnalyticalStatement, Long> {

	Collection<AnalyticalStatement> findAnalyticalStatementsByPaymentTypeId(Long id);
	
	Collection<AnalyticalStatement> findAnalyticalStatementsByDailyAccountStatusId(Long id);
	
}
