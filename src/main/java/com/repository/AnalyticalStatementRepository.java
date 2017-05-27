package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.AnalyticalStatement;

public interface AnalyticalStatementRepository extends JpaRepository<AnalyticalStatement, Long> {

}
