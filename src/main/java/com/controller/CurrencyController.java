package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Currency;
import com.model.user.Permission;
import com.service.CurrencyServiceImpl;

@RequestMapping("/currencies")
@RestController
public class CurrencyController {

	@Autowired
	CurrencyServiceImpl currencyServiceImpl;
	
	@RequestMapping(path="/getAllCurrencies", method=RequestMethod.GET)
	@ResponseBody
	@Permission(permissionName = "readCurrencies")
	public ArrayList<Currency> getAll(){
		ArrayList<Currency> c = currencyServiceImpl.getAll();
		return c;
	}
	
	@RequestMapping(path="/addNewCurrency", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "writeCurrency")
	public Currency addNewCurrency(@RequestBody Currency currency){
		currencyServiceImpl.save(currency);
		return currency;
	}
	
	@RequestMapping(path="/deleteCurrency", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "deleteCurrency")
	public Currency deleteCurrency(@RequestBody Currency currency){
		currencyServiceImpl.removeCurrency(currency.getId());
		return currency;
	}
	
	@RequestMapping(path="/editCurrency", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Transactional
	@Permission(permissionName = "editCurrency")
	public Currency editCurrency(@RequestBody Currency c){
		currencyServiceImpl.updateCurrency(c.getId(), c.getName(), c.getOfficialCode(), c.getCountry().getId(), c.isDomicilna());
		return c;
	}
	
	@RequestMapping(path="/searchCurrencies/{officialCode}/{name}/{countryName}/{domicilna}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "searchCurrencies")
	public ArrayList<Currency> searchCurrencies(@PathVariable ("officialCode") String officialCode, @PathVariable ("name") String name, @PathVariable("countryName") String countryName, @PathVariable("domicilna") boolean domicilna){
		
		if(name.equals("AHA"))
			name = "";
		if(officialCode.equals("AHA"))
			officialCode = "";
		if(countryName.equals("AHA"))
			countryName = "";
		
		ArrayList<Currency> curr = currencyServiceImpl.searchCurrency(name, officialCode, countryName, domicilna);
		return curr;
	}
	
}
