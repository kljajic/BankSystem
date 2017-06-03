package com.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	
	public ArrayList<Account> findAccountsByBankIdOrderByOpeningDateAsc(Long id);
	
}
