package com.service;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Account;
import com.model.RevokedAccount;
import com.repository.RevokedAccountRepostory;

@Service
@Transactional
public class RevokedAccountServiceImpl implements RevokedAccountService {

	@Autowired
	private RevokedAccountRepostory revokedAccountRepository;
	
	@Override
	public RevokedAccount createRevokedAccount(RevokedAccount ra) {
		return null;
	}
	
	@Override
	public RevokedAccount createRevokedAccount(Account a,String transverAcc) {
		RevokedAccount ra = new RevokedAccount();
		ra.setAccount(a);
		ra.setRevocationDate(new Date());
		ra.setTransferAcc(transverAcc);
		return revokedAccountRepository.save(ra);
	}

	@Override
	public RevokedAccount updateRevokedAccount(RevokedAccount ra) {
		return null;
	}

	@Override
	public RevokedAccount deleteRevokedAccount(Long id) {
		RevokedAccount ra = revokedAccountRepository.findOne(id);
		if(ra != null){
			revokedAccountRepository.delete(ra);
			return ra;
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<RevokedAccount> getAllRevokedAccounts() {
		ArrayList<RevokedAccount> accounts = (ArrayList<RevokedAccount>) revokedAccountRepository.findAll();
		if(accounts != null){
			return accounts;
		} else {
			return null;
		}
	}

}
