package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Bank;
import com.model.Country;
import com.repository.BankRepository;
import com.repository.CountryRepository;

@Service
@Transactional
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public Bank createBank(Bank b,Long countryId) {
		//TODO, VALIDACIJA
		Country c = countryRepository.findOne(countryId);
		b.setCountry(c);
		Bank bank = bankRepository.save(b);
		return bank;
	}

	@Override
	public Bank updateBank(Bank b,Long countryId) {
		Bank bank = bankRepository.findOne(b.getId());
		bank.setAddress(b.getAddress());
		bank.setBanka(b.isBanka());
		bank.setEmail(b.getEmail());
		bank.setFax(b.getFax());
		bank.setName(b.getName());
		bank.setPib(b.getPib());
		bank.setSwift(b.getSwift());
		bank.setTelephone(b.getTelephone());
		bank.setTransactionAccount(b.getTransactionAccount());
		bank.setWeb(b.getWeb());
		Country c = countryRepository.findOne(countryId);
		bank.setCountry(c);
		return bankRepository.save(bank);
	}

	@Override
	public Bank deleteBank(Long id) {
		Bank b = bankRepository.findOne(id);
		if(b != null){
			bankRepository.delete(b);
			return b;
		} else {
			return null;
		}
		
		
	}

	@Override
	public ArrayList<Bank> getAllBanks() {
		ArrayList<Bank> banks = (ArrayList<Bank>) bankRepository.findAll();
		if(banks != null){
			return banks;
		}
		return null;
	}

	@Override
	public ArrayList<Bank> searchBanks(Bank b, Long countryId) {
		
		Country c = countryRepository.findOne(countryId);
		
		String name = "";
		String pib = "";
		String address = "";
		String email = "";
		String web = "";
		String telephone = "";
		String fax = "";
		String swift = "";
		String transactionAccount = "";
		String countryName = "";
		
		if(b.getName() != null)
			name = "%" + b.getName() + "%";
		
		if(b.getPib() != null)
			pib = "%" + b.getPib() + "%";
		
		if(b.getAddress() != null)
			address = "%" + b.getAddress() + "%";
		
		if(b.getEmail() != null)
			email = "%" + b.getEmail() + "%";
		
		if(b.getWeb() != null)
			web = "%" + b.getWeb() + "%";

		if(b.getTelephone() != null)
			telephone = "%" + b.getTelephone() + "%";
		
		if(b.getFax() != null)
			fax = "%" + b.getFax() + "%";
		
		if(b.getSwift() != null)
			swift = "%" + b.getSwift() + "%";
		
		if(b.getTransactionAccount() != null)
			transactionAccount = "%" + b.getTransactionAccount() + "%";
		
		if(c != null){
			countryName = c.getName();
		}
		
	
		return bankRepository.searchBanks(name,pib,address,email,web,telephone,fax,swift,transactionAccount,countryName);
		
	}

}
