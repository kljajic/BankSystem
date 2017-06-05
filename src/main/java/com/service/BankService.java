package com.service;

import java.util.ArrayList;

import com.model.Bank;

public interface BankService {
	
	public ArrayList<Bank> getAllBanks();
	public Bank createBank(Bank b,Long countryId);
	public Bank updateBank(Bank b,Long countryId);
	public Bank deleteBank(Long id);
	public ArrayList<Bank> searchBanks(Bank b, Long countryId);
	
}
