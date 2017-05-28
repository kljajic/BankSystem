package com.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Account;
import com.service.AccountServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountServiceImpl accountServiceImpl;
	
	@RequestMapping (method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ArrayList<Account>> getAllAccount(){
		ArrayList<Account> accounts = accountServiceImpl.getAllAccounts();
		if(accounts != null){
			return  new ResponseEntity<ArrayList<Account>>(accounts,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping (method=RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Account> createAccount(@RequestBody @Valid Account a){
		Account account = accountServiceImpl.createAccount(a);
		if(account != null){
			return  new ResponseEntity<Account>(account,HttpStatus.CREATED);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	

}
