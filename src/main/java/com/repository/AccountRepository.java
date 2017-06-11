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
	
	@Query("select aa from Account aa where aa.accountNumber like ?1 and aa.openingDate between ?2 and ?3 and aa.bank.name like ?4 and aa.client.name like ?5 and aa.client.surname like ?6 and aa.currency.officialCode like ?7") 
	public Set<Account> search(String accountNumber, Date openingMin, Date openingMax, String bankName, String name, String surname, String currency);
	
	@Query("select aa from Account aa where aa.accountNumber like ?1 and aa.openingDate between ?2 and ?3 and aa.bank.name like ?4 and aa.client.name like ?5 and aa.client.surname like ?6 and aa.currency.officialCode like ?7 and aa.active = ?8") 
	public Set<Account> searchWithActive(String accountNumber, Date openingMin, Date openingMax, String bankName, String name, String surname, String currency, Boolean active);

	
	@Query(value="SELECT acc.account_id, acc.account_num, acc.account_active, acc.account_date, acc.bank_bank_id, "
			+ "acc.client_id, acc.currency_id from bank.account acc inner join bank.bank b on b.bank_id = acc.bank_bank_id inner join "
			+ "bank.client c on c.id = acc.client_id inner join bank.currency cur on cur.id = acc.currency_id "
			+ "inner join bank.user u on u.id = acc.client_id "
			+ "where acc.account_num like ?1 and acc.account_date between ?2 and ?3 and b.bank_name like ?4 and u.name like ?5 and u.surname like ?6 and cur.official_code like ?7"
			, nativeQuery=true)
	public Set<Account> searchNative(String accountNumber, Date openingMin, Date openingMax, String bankName, String name, String surname, String currency);

	@Query("select account from Account account where account.accountNumber = ?1")
	public Account findAccountByAccountNumber(String accountNumber);
	
	
}
