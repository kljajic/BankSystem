package com.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Bank;
import com.service.BankServiceImpl;

@RestController
@RequestMapping("/banks")
public class BankController {
	
	@Autowired
	private BankServiceImpl bankServiceImpl;
	
	@RequestMapping (method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ArrayList<Bank>> getAllBanks(){
		ArrayList<Bank> banks = bankServiceImpl.getAllBanks();
		if(banks != null){
			return  new ResponseEntity<ArrayList<Bank>>(banks,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping (value="/{countryId}",
					method=RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Bank> createBank(@RequestBody @Valid Bank b, @PathVariable ("countryId") Long countryId){
		Bank bank = bankServiceImpl.createBank(b,countryId);
		if(bank != null){
			return  new ResponseEntity<Bank>(bank,HttpStatus.CREATED);
		} else {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping (value="/search/{countryId}",
				method=RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Bank>> searchBanks(@RequestBody @Valid Bank b, @PathVariable ("countryId") Long countryId){
		ArrayList<Bank> banks = bankServiceImpl.searchBanks(b,countryId);
		if(banks != null){
			return  new ResponseEntity<ArrayList<Bank>>(banks,HttpStatus.CREATED);
		} else {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@RequestMapping (value="/{countryId}",
					method=RequestMethod.PUT,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Bank> updateBank(@RequestBody @Valid Bank b, @PathVariable ("countryId") Long countryId){
		Bank bank = bankServiceImpl.updateBank(b,countryId);
		if(bank != null){
			return  new ResponseEntity<Bank>(bank,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping (value="/{id}",
					 method=RequestMethod.DELETE,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Bank> deleteBank(@PathVariable("id") Long id){
		Bank bank = bankServiceImpl.deleteBank(id);
		if(bank != null){
			return  new ResponseEntity<Bank>(bank,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
