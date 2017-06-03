package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.ExchangeListServiceImpl;
import com.model.ExchangeList;

@RequestMapping("/exchangeList")
@RestController
public class ExchangeListController {

	@Autowired
	private ExchangeListServiceImpl exchangeListServiceImpl;
	
	@RequestMapping(path="/getAll", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<ExchangeList> getAll(){
		ArrayList<ExchangeList> el = exchangeListServiceImpl.getAll();
		return el;
	}
	
	@RequestMapping(path="/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ExchangeList addNewEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.save(el);
		return el;
	}
	
	@RequestMapping(path="/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ExchangeList deleteEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.removeExchangeList(el.getId());
		return el;
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ExchangeList editEL(@RequestBody ExchangeList el){
		exchangeListServiceImpl.editExchangeList(el);
		return el;
	}
	
	//search...
	
}
