package com.service;

import java.util.ArrayList;

import com.model.Currency;

public interface CurrencyService {
	
	public ArrayList<Currency> getAll();
	
	public void save(Currency currency);
	
	public void removeCurrency(Long id);
	
	public void updateCurrency(Long id, String name, String officialCode, Long country, boolean domicilna);

	public ArrayList<Currency> searchCurrency(String name, String officialCode, String country, boolean domicilna);

	Currency getCurrency(Long id);
	
	Currency getCurrencyByOfficialCode(String officialCode);
	
}
