package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Bank;
import com.repository.BankRepository;

@Service
@Transactional
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public Bank createBank(Bank b) {
		return bankRepository.save(b);
	}

	@Override
	public Bank updateBank(Bank b) {
		return bankRepository.save(b);
	}

	@Override
	public Bank deleteBank(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bank> getAllBanks() {
		ArrayList<Bank> banks = (ArrayList<Bank>) bankRepository.findAll();
		if(banks != null){
			return banks;
		}
		return null;
	}

}
