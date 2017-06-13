package com.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.ExchangeList;
import com.model.user.Permission;
import com.service.ExchangeListServiceImpl;

@RequestMapping("/exchangeListController")
@RestController
public class ExchangeListController {

	@Autowired
	private ExchangeListServiceImpl exchangeListServiceImpl;
	
	@RequestMapping(path="/getAllExchangeLists", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "readExchangeLists")
	public ArrayList<ExchangeList> getAll(){
		ArrayList<ExchangeList> el = exchangeListServiceImpl.getAll();
		return el;
	}
	
	@RequestMapping(path="/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "createExchangeList")
	public ExchangeList addNewEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.save(el);
		return el;
	}
	
	@RequestMapping(path="/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "removeExchangeList")
	public ExchangeList deleteEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.removeExchangeList(el.getId());
		return el;
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "editExchangeList")
	public ExchangeList editEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.editExchangeList(el);
		return el;
	}
	
	@RequestMapping(path="/search/", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Permission(permissionName = "searchExchangeLists")
	public Collection<ExchangeList> search(@RequestBody ExchangeList el){
		int elNumberMin = 0; int elNumberMax = 2147483647;
		if(el.getNumberOfExchangeList() != -1){
			elNumberMin = el.getNumberOfExchangeList();
			elNumberMax = el.getNumberOfExchangeList();
		}

		Calendar cal = Calendar.getInstance();
		cal.set(1900, 1, 1);
		Date dateMin = cal.getTime();
		cal.set(2100, 12, 31);
		Date dateMax = cal.getTime();
		if(el.getDate() != null){
			cal.setTime(el.getDate());
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			dateMin = cal.getTime();
			cal.setTime(el.getDate());
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			dateMax = cal.getTime();
		}
		
		cal.set(1900, 1, 1);
		Date usedSinceMin = cal.getTime();
		cal.set(2100, 12, 31);
		Date usedSinceMax = cal.getTime();
		
		if(el.getUsedSince() != null){
			cal.setTime(el.getUsedSince());
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			usedSinceMin = cal.getTime();
			cal.setTime(el.getUsedSince());
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			usedSinceMax = cal.getTime();
		}
		
		
		String bankName = "";
		if(el.getBank().getName() != null){
			bankName = el.getBank().getName();
		}
		
		bankName = "%" + bankName + "%";
		
		return exchangeListServiceImpl.searchEL(elNumberMin, elNumberMax, dateMin, dateMax, usedSinceMin, usedSinceMax, bankName);
	}
}
