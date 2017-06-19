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
		return countryRepository.findAll();
	}

	@Override
	public void save(Country country) {
		countryRepository.save(country);
	}

	@Override
	public void delete(Country country) {
		countryRepository.delete(country);
	}

	@Override
	public void removeCountry(Long id) {
		countryRepository.delete(id);
	}

	@Override
	public void updateCountry(long id, String name) {
		countryRepository.updateCountry(id, name);
	}

	@Override
	public Set<Country> searchByName(String name) {
		return countryRepository.SearchByName(name);
	}

	@Override
	public Country getCountry(Long id) {
		return countryRepository.findOne(id);
	}

}
