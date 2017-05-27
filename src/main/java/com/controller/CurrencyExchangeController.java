package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.CurrencyExchangeServiceImpl;

@RequestMapping("/currencyExchange")
@RestController
public class CurrencyExchangeController {

	@Autowired
	CurrencyExchangeServiceImpl currencyExchangeServiceImpl;
	
}
