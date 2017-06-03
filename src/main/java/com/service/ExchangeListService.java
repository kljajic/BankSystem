package com.service;

import java.util.ArrayList;
import java.util.Date;

import com.model.ExchangeList;

public interface ExchangeListService {

	public ArrayList<ExchangeList> getAll();
	
	public void save(ExchangeList exchangeList);
	
	public void removeExchangeList(Long id);
	
	public ExchangeList editExchangeList(ExchangeList el);

	public ArrayList<ExchangeList> searchEL(Date date, int numberOfExchangeList, Date usedSince);

	
}