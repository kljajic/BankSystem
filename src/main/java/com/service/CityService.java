package com.service;

import java.util.Collection;

import com.model.City;

public interface CityService {

	City createCity(City city);
	Collection<City> getCities();
	City getCity(Long id);
	City updateCity(City city);
	void deleteCity(Long id);
	Collection<City> searchCities(City city);
	
}
