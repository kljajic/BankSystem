package com.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Country;
import com.repository.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public ArrayList<Country> getAll() {
		// TODO Auto-generated method stub
		return countryRepository.findAll();
	}

	@Override
	public void save(Country country) {
		// TODO Auto-generated method stub
		countryRepository.save(country);
	}

	@Override
	public void delete(Country country) {
		// TODO Auto-generated method stub
		countryRepository.delete(country);
	}

	@Override
	public void removeCountry(Long id) {
		// TODO Auto-generated method stub
		countryRepository.delete(id);
	}

	@Override
	public void updateCountry(long id, String name) {
		// TODO Auto-generated method stub
		countryRepository.updateCountry(id, name);
	}

	@Override
	public Set<Country> searchByName(String name) {
		// TODO Auto-generated method stub
		return countryRepository.SearchByName(name);
	}

}
