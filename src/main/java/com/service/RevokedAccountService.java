package com.service;

import java.util.ArrayList;

import com.model.Account;
import com.model.RevokedAccount;

public interface RevokedAccountService {
	
	public RevokedAccount createRevokedAccount(RevokedAccount ra);
	public ArrayList<RevokedAccount> getAllRevokedAccounts();
	public RevokedAccount createRevokedAccount(Account a,String transverAcc);
	public RevokedAccount updateRevokedAccount(RevokedAccount ra);
	public RevokedAccount deleteRevokedAccount(Long id);
}
