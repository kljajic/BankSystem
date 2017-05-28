package com.service;

import java.util.ArrayList;

import com.model.Bank;

public interface BankService {
	
	public ArrayList<Bank> getAllBanks();
	public Bank createBank(Bank b);
	public Bank updateBank(Bank b);
	public Bank deleteBank(Long id);
	
}
