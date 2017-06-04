package com.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	
	public ArrayList<Account> findAccountsByBankIdOrderByOpeningDateAsc(Long id);
	
	@Query("select a from Account as a where a.accountNumber like ?1 and a.openingDate between Date(?2) and Date(?3) and a.bank.name like ?4 and a.client.name like ?5 and a.client.surname like ?6 and a.currency.officialCode like ?7") 
	public Set<Account> search(String accountNumber, Date openingMin, Date openingMax, String bankName, String name, String surname, String currency);
}
