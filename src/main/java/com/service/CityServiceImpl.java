package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.City;
import com.model.Country;
import com.repository.CityRepository;

@Service
@Transactional
public class CityServiceImpl implements CityService {

	private final CityRepository cityRepository;
	private final CountryService countryService;
	
	@Autowired
	public CityServiceImpl(CityRepository cityRepository, CountryService countryService) {
		this.cityRepository = cityRepository;
		this.countryService = countryService;
	}
	
	@Override
	public City createCity(Long countryId, City city) {
		Country country = countryService.getCountry(countryId);
		city.setCountry(country);
		return cityRepository.save(city);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<City> getCities() {
		return cityRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public City getCity(Long id) {
		return cityRepository.findOne(id);
	}

	@Override
	public City updateCity(Long countryId, City city) {
		City temp = cityRepository.findOne(city.getId());
		temp.setCountry(countryService.getCountry(countryId));
		temp.setName(city.getName());
		temp.setPttNumber(city.getPttNumber());
		return cityRepository.save(temp);
	}

	@Override
	public void deleteCity(Long id) {
		cityRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<City> searchCities(City city) {
		String name = "";
		if(city.getName()!=null)
			name=city.getName();
		String pttNumber = "";
		if(city.getPttNumber()!=null)
			pttNumber=city.getPttNumber();
		String countryName = "";
		if(city.getCountry()!=null){
			countryName=city.getCountry().getName();
		}
		return cityRepository.search(name, countryName, pttNumber);
	}

	@Override
	public City getCityByName(String cityName) {
		return this.cityRepository.getCityByName(cityName.trim().toUpperCase());
	}

}
