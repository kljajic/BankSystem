package com.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ExchangeList;
import com.repository.ExchangeListRepository;

@Service
public class ExchangeListServiceImpl implements ExchangeListService {

	@Autowired
	ExchangeListRepository exchangeListRepository;
	
	@Override
	public ArrayList<ExchangeList> getAll() {
		// TODO Auto-generated method stub
		return exchangeListRepository.findAll();
	}

	@Override
	public void save(ExchangeList exchangeList) {
		// TODO Auto-generated method stub
		exchangeListRepository.save(exchangeList);
	}

	@Override
	public void removeExchangeList(Long id) {
		// TODO Auto-generated method stub
		exchangeListRepository.delete(id);
	}

	@Override
	public ExchangeList editEL(Long id, Date date, int numberOfExchangeList, Date usedSince) {
		// TODO Auto-generated method stub
		return exchangeListRepository.editEL(id, date, numberOfExchangeList, usedSince);
	}

	@Override
	public ArrayList<ExchangeList> searchEL(Date date, int numberOfExchangeList, Date usedSince) {
		// TODO Auto-generated method stub
		return exchangeListRepository.searchEL(date, numberOfExchangeList, usedSince);
	}

}
