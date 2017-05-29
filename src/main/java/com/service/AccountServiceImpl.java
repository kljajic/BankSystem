package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Account;
import com.repository.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Account createAccount(Account a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account deleteAccount(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Account> getAllAccounts() {
		ArrayList<Account> accounts = (ArrayList<Account>) accountRepository.findAll();
		if(accounts != null){
			return accounts;
		} else {
			return null;
		}
	}

	@Override
	public Account getAccount(Long id) {
		return accountRepository.findOne(id);
	}

}
