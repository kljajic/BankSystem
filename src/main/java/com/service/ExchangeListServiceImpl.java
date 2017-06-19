package com.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ExchangeList;
import com.repository.ExchangeListRepository;

@Service
public class ExchangeListServiceImpl implements ExchangeListService {

	@Autowired
	private ExchangeListRepository exchangeListRepository;
	
	@Override
	public ArrayList<ExchangeList> getAll() {
		return exchangeListRepository.findAll();
	}

	@Override
	public void save(ExchangeList exchangeList) {
		exchangeListRepository.save(exchangeList);
	}

	@Override
	public void removeExchangeList(Long id) {
		exchangeListRepository.delete(id);
	}

	@Override
	public ExchangeList editExchangeList(ExchangeList el) {
		exchangeListRepository.delete(el.getId());
		return exchangeListRepository.save(el);
		
	}

	@Override
	public Collection<ExchangeList> searchEL(int elNumberMin, int elNumberMax, Date dateMin, Date dateMax,
			Date usedSinceMin, Date usedSinceMax, String bankName) {
		return exchangeListRepository.search(elNumberMin, elNumberMax, dateMin, dateMax, usedSinceMin, usedSinceMax, bankName);
	}
}
