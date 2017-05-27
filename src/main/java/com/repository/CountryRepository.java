package com.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	@Query("select c from Country as c")
	public ArrayList<Country> findAll();
	
	@Modifying 
	@Query("update Country c set name=?2 where id=?1")
	public void updateCountry(long id, String name);
	
	@Query("select c from Country as c where c.name like ?1%")
	public Set<Country> SearchByName(String name);
	
}
