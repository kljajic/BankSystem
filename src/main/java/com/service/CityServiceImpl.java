package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.City;
import com.repository.CityRepository;

@Service
@Transactional
public class CityServiceImpl implements CityService {

	private final CityRepository cityRepository;
	
	@Autowired
	public CityServiceImpl(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	@Override
	public City createCity(City city) {
		return cityRepository.save(city);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<City> getCities() {
		return cityRepository.findAll();
	}
	
	@Override
	public City getCity(Long id) {
		return cityRepository.findOne(id);
	}

	@Override
	public City updateCity(City city) {/*
		City temp = cityRepository.findOne(city.getId());
		temp.setCountry(city.getCountry());
		temp.setName(city.getName());
		temp.setPttNumber(city.getPttNumber());
		return cityRepository.save(temp);
		*/
		return null;
	}

	@Override
	public void deleteCity(Long id) {
		cityRepository.delete(id);
	}

	@Override
	public Collection<City> searchCities(City city) {
		/*String name="";
		if(city.getName()!=null)
			name=city.getName().toUpperCase();
		String pttNumber="";
		if(city.getPttNumber()!=null)
			pttNumber=city.getPttNumber();
		String countryName="";
		if(city.getCountry()!=null){
			countryName=city.getCountry().toString();//getCountryName
		}
		return cityRepository.search(name, countryName, pttNumber);*/
		return null;
	}

}
