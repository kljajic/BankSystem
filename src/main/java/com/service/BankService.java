package com.service;

import com.model.Bank;

public interface BankService {
	
	public Bank createBank(Bank b);
	public Bank updateBank(Bank b);
	public Bank deleteBank(Long id);
	
}
