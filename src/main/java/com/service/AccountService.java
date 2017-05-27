package com.service;

import com.model.Account;

public interface AccountService {

	public Account createAccount(Account a);
	public Account updateAccount(Account a);
	public Account deleteAccount(Long id);
}
