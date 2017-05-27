package com.service;

import java.util.Collection;

import com.model.City;

public interface CityService {

	public City createCity(City city);
	public Collection<City> getCities();
	public City getCity(Long id);
	public City updateCity(City city);
	public void deleteCity(Long id);
	public Collection<City> searchCities(City city);
	
}
