package com.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.AnalyticalStatement;

public interface AnalyticalStatementRepository extends JpaRepository<AnalyticalStatement, Long> {


	Collection<AnalyticalStatement> findAnalyticalStatementsByDailyAccountStatusId(Long id);

	@Query("select statement from AnalyticalStatement statement where statement.currency.officialCode like %?1% and "
			+ "statement.placeOfAcceptance.pttNumber like %?2% and "
			+ "statement.dailyAccountStatus.account.accountNumber like %?3% and statement.originator like %?4% and "
			+ "statement.purpose like %?5% and statement.recipient like %?6% and "
			+ "statement.dateOfReceipt between ?7 and ?8 and statement.currencyDate between ?7 and ?9 and "
			+ "statement.originatorAccount like %?10% and cast(statement.model as string) like %?11% and "
			+ "statement.debitAuthorizationNumber like %?12% and statement.recipientAccount like %?13% and "
			+ "cast(statement.approvalModel as string) like %?14% and statement.approvalAuthorizationNumber like %?15% and "
			+ "statement.urgently = ?16 and statement.amount <= ?17")
	Collection<AnalyticalStatement> searchAnalyticalStatements(String currencyCode, 
		    String cityPttNumber, String accountNumber,
			String originator, String purpose, String recipient, Date minimumDate, Date dateOfReceipt,
			Date currencyDate, String originatorAccount, String model, String debitAuthorizationNumber,
			String recipientAccount, String approvalModel, String approvalAuthorizationNumber,
			boolean urgently, Double amount);
	
}
