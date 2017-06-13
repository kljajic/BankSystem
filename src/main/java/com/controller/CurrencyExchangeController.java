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

import com.service.CurrencyExchangeServiceImpl;
import com.model.CurrencyExchange;
import com.model.user.Permission;

@RequestMapping("/currencyExchange")
@RestController
public class CurrencyExchangeController {

	@Autowired
	CurrencyExchangeServiceImpl currencyExchangeServiceImpl;
	
	@RequestMapping(path="/getAllCurrencyExchanges", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "readCurrencyExchanges")
	public ArrayList<CurrencyExchange> getAll(){
		ArrayList<CurrencyExchange> ce = currencyExchangeServiceImpl.getAll();
		return ce;
	}
	
	@RequestMapping(path="/addNewCE", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "writeCurrencyExchange")
	public CurrencyExchange addNewCE(@RequestBody CurrencyExchange ce){
		currencyExchangeServiceImpl.save(ce);
		return ce;
	}
	
	@RequestMapping(path="/deleteCE", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "deleteCurrencyExchange")
	public CurrencyExchange deleteCE(@RequestBody CurrencyExchange ce){
		currencyExchangeServiceImpl.removeCurrencyExchange(ce.getId());
		return ce;
	}

	@RequestMapping(path="/editCE", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	@Permission(permissionName = "editCurrencyExchange")
	public CurrencyExchange editCE(@RequestBody CurrencyExchange ce){
		currencyExchangeServiceImpl.updateCurrencyExchange(ce.getId(), ce.getBuyRate(), ce.getMiddleRate(), ce.getSellRate(), ce.getExchangeList().getId(), ce.getPrimaryCurrency().getId(), ce.getAccordingToCurrency().getId());
		return ce;
	}

	//sacekati front, @pogledati CurrencyExchangeRepository
	@RequestMapping(path="/searchCE/{buyRate}/{middleRate}/{sellRate}/{exchangeList}/{primaryCurrency}/{accordingToCurrency}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "searchCurrencyExchange")
	public ArrayList<CurrencyExchange> searchCE(@PathVariable ("buyRate") Double buyRate, @PathVariable ("middleRate") Double middleRate, @PathVariable ("sellRate") Double sellRate, @PathVariable ("exchangeList") int exchangeList, @PathVariable ("primaryCurrency") String primaryCurrency, @PathVariable ("accordingToCurrency") String accordingToCurrency){
		
		if(buyRate == -1){
			buyRate = (double) 1000000000;
		}
		if(middleRate == -1){
			middleRate = (double) 1000000000;
		}
		if(sellRate == -1){
			sellRate = (double) 1000000000;
		}
		if(exchangeList == -1){
			exchangeList = 1000;
		}
		if(primaryCurrency.equals("AHA"))
			primaryCurrency = "";
		if(accordingToCurrency.equals("AHA"))
			accordingToCurrency = "";
		
		System.out.println(buyRate);
		System.out.println(sellRate);
		System.out.println(middleRate);
		System.out.println(exchangeList);
		System.out.println(primaryCurrency);
		System.out.println(accordingToCurrency);
		
		ArrayList<CurrencyExchange> cel = currencyExchangeServiceImpl.searchCurrencyExchange(buyRate, middleRate, sellRate, exchangeList, primaryCurrency, accordingToCurrency);
		return cel;
	}
	
}
