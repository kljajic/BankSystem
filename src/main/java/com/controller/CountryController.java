package com.controller;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Country;
import com.service.CountryServiceImpl;

@RequestMapping("/countries")
@RestController
public class CountryController {

	@Autowired
	private CountryServiceImpl countryServiceImpl;
	
	@RequestMapping(path="/getAllCountries", method=RequestMethod.GET)
	public ArrayList<Country> getAllCountries(){
		ArrayList<Country> c =  countryServiceImpl.getAll();
		return  c;
	}
	
	
	@RequestMapping(path="/addNewCountry/{name}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Country addNewCountry(@PathVariable ("name") String name){
		Country c = new Country();
		c.setName(name);
		countryServiceImpl.save(c);
		return c;
	}
	
	@RequestMapping(path="/deleteCountry", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Country deleteCountry(@RequestBody Country country){
		countryServiceImpl.removeCountry(country.getId());
		return country;
	}
	
	@RequestMapping(path="/editCountry/{id}/{name}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Transactional
	public String editCountry(@PathVariable ("id") Long id, @PathVariable ("name") String name){
		countryServiceImpl.updateCountry(id, name);
		return "1";
	}
	
	@RequestMapping(path="/searchCountries/{naziv}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashSet<Country> searchCountries(@PathVariable ("name") String name){
		HashSet<Country> countries = (HashSet<Country>) countryServiceImpl.searchByName(name); 
		return countries;
	}
	
}
