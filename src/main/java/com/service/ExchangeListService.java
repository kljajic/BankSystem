package com.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.model.ExchangeList;

public interface ExchangeListService {

	public ArrayList<ExchangeList> getAll();
	
	public void save(ExchangeList exchangeList);
	
	public void removeExchangeList(Long id);
	
	public ExchangeList editExchangeList(ExchangeList el);

	public Collection<ExchangeList> searchEL(int elNumberMin, int elNumberMax, Date dateMin, Date dateMax, Date usedSinceMin, Date usedSinceMax, String bankName);

	
}
