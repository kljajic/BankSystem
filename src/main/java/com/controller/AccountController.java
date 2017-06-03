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

import com.model.Account;
import com.service.AccountServiceImpl;
import com.service.RevokedAccountServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountServiceImpl accountServiceImpl;
	
	@Autowired
	private RevokedAccountServiceImpl revokedAccountServiceImpl;
	
	@RequestMapping (method=RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Account>> getAllAccounts(){
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
	
	@RequestMapping (value = "/{bankId}",
					method=RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Account> updateAccount(@RequestBody @Valid Account a){
		Account account = accountServiceImpl.updateAccount(a);
		if(account != null){
			return  new ResponseEntity<Account>(account,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping (value = "delete/{accountId}/{transverAcc}",
					method=RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Account> deleteAccount(@PathVariable("accountId") Long accountId,@PathVariable("transverAcc") String transverAcc){
		Account account = accountServiceImpl.deleteAccount(accountId);
		if(account != null){
			revokedAccountServiceImpl.createRevokedAccount(account,transverAcc);
			return  new ResponseEntity<Account>(account,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping (value = "/{bankId}",
				method=RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Account>> getAllAccountsForBank(@PathVariable("bankId") Long bankId){
		ArrayList<Account> bankAccounts = accountServiceImpl.getAllAccountsForBank(bankId);
		if(bankAccounts != null){
			return  new ResponseEntity<ArrayList<Account>>(bankAccounts,HttpStatus.CREATED);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
