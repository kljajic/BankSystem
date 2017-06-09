package com.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.AnalyticalStatement;

public interface AnalyticalStatementRepository extends JpaRepository<AnalyticalStatement, Long> {

	Collection<AnalyticalStatement> findAnalyticalStatementsByPaymentTypeId(Long id);
	
	Collection<AnalyticalStatement> findAnalyticalStatementsByDailyAccountStatusId(Long id);

	@Query("select statement from AnalyticalStatement statement where statement.currency.officialCode like %?1% and "
			+ "statement.paymentType.paymentTypeName like %?2% and statement.placeOfAcceptance.pttNumber like %?3% and "
			+ "statement.dailyAccountStatus.account.accountNumber like %?4% and statement.originator like %?5% and "
			+ "statement.purpose like %?6% and statement.recipient like %?7% and "
			+ "statement.dateOfReceipt between ?8 and ?9 and statement.currencyDate between ?8 and ?10 and "
			+ "statement.originatorAccount like %?11% and statement.model = ?12 and "
			+ "statement.debitAuthorizationNumber like %?13% and statement.recipientAccount like %?14% and "
			+ "statement.approvalModel = ?15 and statement.approvalAuthorizationNumber like %?16% and "
			+ "statement.urgently = ?17 and statement.amount <= ?18")
	Collection<AnalyticalStatement> searchAnalyticalStatements(String currencyCode, 
			String paymentTypeName, String cityPttNumber, String accountNumber,
			String originator, String purpose, String recipient, Date minimumDate, Date dateOfReceipt,
			Date currencyDate, String originatorAccount, Short model, String debitAuthorizationNumber,
			String recipientAccount, Short approvalModel, String approvalAuthorizationNumber,
			boolean urgently, Double amount);
	
}
