package com.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.DailyAccountStatus;

@Repository
public interface DailyAccountStatusRepository extends JpaRepository<DailyAccountStatus, Long> {
	
	@Query("select status from DailyAccountStatus status inner join status.account as account where account.accountNumber like %?1% and status.previousAmount <= ?2 and status.transferInFavor <= ?3 and status.numberOfChanges <= ?4 and status.transferExpenses <= ?5 and status.currentAmount <= ?6 and status.date between ?7 and ?8")
	Collection<DailyAccountStatus> searchDailyAccountStatuses(String accountNumber, Double previousAmount, Double transferInFavor, Integer numberOfChanges, Double transferExpenses, Double currentAmount, Date minDate, Date maxDate);
	
	@Query("select status from DailyAccountStatus status inner join status.account as account where "
			+ "account.accountNumber = ?1 and status.date = (select max(s.date) from DailyAccountStatus s where s.account.accountNumber = ?1)")
	DailyAccountStatus findDailyAccountStatusByLastDateAndAccountNumber(String accountNumber);
	
	@Query("select status from DailyAccountStatus status inner join status.account as account where "
			+ "account.id = ?1 and status.date between ?2 and ?3")
	DailyAccountStatus findDailyAccountStatusByAccountAndDate(Long accountId,Date minDate, Date maxDate);
	
}
