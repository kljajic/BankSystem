package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.CurrencyExchange;
import com.repository.CurrencyExchangeRepository;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;

	@Override
	public ArrayList<CurrencyExchange> getAll() {
		// TODO Auto-generated method stub
		return currencyExchangeRepository.findAll();
	}

	@Override
	public void save(CurrencyExchange ce) {
		// TODO Auto-generated method stub
		currencyExchangeRepository.save(ce);
	}

	@Override
	public void removeCurrencyExchange(Long id) {
		// TODO Auto-generated method stub
		currencyExchangeRepository.delete(id);
	}

	@Override
	public void updateCurrencyExchange(Long id, Double buyRate, Double middleRate, Double sellRate, Long exchangeList,
			Long primaryCurrency, Long accordingToCurrency) {
		// TODO Auto-generated method stub
		currencyExchangeRepository.updateCurrencyExchange(id, buyRate, middleRate, sellRate, exchangeList, primaryCurrency, accordingToCurrency);
	}

	@Override
	public ArrayList<CurrencyExchange> searchCurrencyExchange(Double buyRate, Double middleRate, Double sellRate,
			int numberOfExchangeList, String offCodePrimaryCurrency, String offCodeAccordingToCurrency) {
		// TODO Auto-generated method stub
		return currencyExchangeRepository.searchCurrencyExchange(buyRate, middleRate, sellRate, numberOfExchangeList, offCodePrimaryCurrency, offCodeAccordingToCurrency);
	}

}
