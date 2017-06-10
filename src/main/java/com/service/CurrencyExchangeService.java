package com.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;

import com.model.CurrencyExchange;

public interface CurrencyExchangeService {

	public ArrayList<CurrencyExchange> getAll();
	
	public void save(CurrencyExchange ce);
	
	public void removeCurrencyExchange(Long id);

	public void updateCurrencyExchange(Long id, Double buyRate, Double middleRate, Double sellRate, Long exchangeList, Long primaryCurrency, Long accordingToCurrency);

	public ArrayList<CurrencyExchange> searchCurrencyExchange(Double buyRate, Double middleRate, Double sellRate, int numberOfExchangeList, String offCodePrimaryCurrency, String offCodeAccordingToCurrency);

	double findMiddleRateAccordingToDinars(String code);
}
