package com.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.City;
import com.model.user.Permission;
import com.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cities")
@Api(value = "/cities")
public class CityController {

	private final CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping("/add/{countryId}")
	@ResponseBody
	@ApiOperation(value = "Add a city.", notes = "Add a single city.", response = City.class)
	public City createCity(@RequestBody @Valid City city, @PathVariable("countryId") Long countryId){
		return cityService.createCity(countryId, city);
	}
	
	@GetMapping("/getCities")
	@ResponseBody
	@ApiOperation(value = "Get all cities.", notes = "Get all cities.", response = Collection.class)
	public Collection<City> getCities(){
		return cityService.getCities();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Get a city.", notes = "Get a city with given id.", response = City.class)
	public City getCity(@PathVariable("id") Long id){
		return cityService.getCity(id);
	}
	
	@PutMapping("/update/{countryId}")
	@ResponseBody
	@ApiOperation(value = "Update a city.", notes = "Update a single city.", response = City.class)
	public City updateCity(@RequestBody @Valid City city, @PathVariable("countryId") Long countryId){
		return cityService.updateCity(countryId, city);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Delete a city.", notes = "Delete a city with given id.")
	public void deleteCity(@PathVariable("id") Long id){
		cityService.deleteCity(id);
	}
	
	@PostMapping("/search")
	@ResponseBody
	@ApiOperation(value = "Search cities.", notes = "Search cities by given fields.", response = Collection.class)
	public Collection<City> searchCities(@RequestBody City city){
		return cityService.searchCities(city);
	}
	
}
