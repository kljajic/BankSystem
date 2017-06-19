package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Currency;
import com.repository.CurrencyRepository;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public ArrayList<Currency> getAll() {
		return currencyRepository.findAll();
	}

	@Override
	public void save(Currency currency) {
		currencyRepository.save(currency);
	}

	@Override
	public void removeCurrency(Long id) {
		currencyRepository.delete(id);
	}

	@Override
	public void updateCurrency(Long id, String name, String officialCode, Long country, boolean domicilna) {
		currencyRepository.updateCurrency(id, name, officialCode, country, domicilna);
	}

	@Override
	public ArrayList<Currency> searchCurrency(String name, String officialCode, String country, boolean domicilna) {
		return currencyRepository.searchCurrency(name, officialCode, country, domicilna);
	}

	@Override
	public Currency getCurrency(Long id) {
		return currencyRepository.findOne(id);
	}

	@Override
	public Currency getCurrencyByOfficialCode(String officialCode) {
		return this.currencyRepository.getCurrencyByOfficialCode(officialCode.trim().toUpperCase());
	}

}
