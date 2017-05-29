package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Currency;
import com.repository.CurrencyRepository;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	@Override
	public ArrayList<Currency> getAll() {
		// TODO Auto-generated method stub
		return currencyRepository.findAll();
	}

	@Override
	public void save(Currency currency) {
		// TODO Auto-generated method stub
		currencyRepository.save(currency);
	}

	@Override
	public void removeCurrency(Long id) {
		// TODO Auto-generated method stub
		currencyRepository.delete(id);
	}

	@Override
	public void updateCurrency(Long id, String name, String officialCode, Long country, boolean domicilna) {
		// TODO Auto-generated method stub
		currencyRepository.updateCurrency(id, name, officialCode, country, domicilna);
	}

	@Override
	public ArrayList<Currency> searchCurrency(String name, String officialCode, String country, boolean domicilna) {
		// TODO Auto-generated method stub
		return currencyRepository.searchCurrency(name, officialCode, country, domicilna);
	}

	@Override
	public Currency getCurrency(Long id) {
		return currencyRepository.findOne(id);
	}

}
