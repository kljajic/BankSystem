package com.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query("select city from City city inner join city.country as c where upper(city.name) like %?1% and upper(c.name) like %?2% and city.pttNumber like %?3%")
	Collection<City> search(String name,String countryName,String pttNumber);
	
	@Query("select city from City city where upper(city.name) = ?1")
	City getCityByName(String cityName);
	
}
