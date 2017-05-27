package com.service;

import java.util.ArrayList;
import java.util.Set;

import com.model.Country;


public interface CountryService {
	
	ArrayList<Country> getAll();
 	
	public void save(Country country);

	public void delete(Country country);
	
	public void removeCountry(Long id);
	
	public void updateCountry(long id, String name);
	
	public Set<Country> searchByName(String name);
}
