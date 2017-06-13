package com.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import com.model.user.Permission;
import com.service.AccountServiceImpl;
import com.service.RevokedAccountServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Autowired
	private RevokedAccountServiceImpl revokedAccountServiceImpl;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "readAccounts")
	public ResponseEntity<ArrayList<Account>> getAllAccounts() {
		ArrayList<Account> accounts = accountServiceImpl.getAllAccounts();
		if (accounts != null) {
			return new ResponseEntity<ArrayList<Account>>(accounts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "writeAccount")
	public ResponseEntity<Account> createAccount(@RequestBody @Valid Account a) {
		Account account = accountServiceImpl.createAccount(a);
		if (account != null) {
			return new ResponseEntity<Account>(account, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/{bankId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "editAccounnt")
	public ResponseEntity<Account> updateAccount(@RequestBody @Valid Account a) {
		Account account = accountServiceImpl.updateAccount(a);
		if (account != null) {
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "delete/{accountId}/{transverAcc}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "deleteAccount")
	public ResponseEntity<Account> deleteAccount(@PathVariable("accountId") Long accountId,
			@PathVariable("transverAcc") String transverAcc) {
		Account account = accountServiceImpl.deleteAccount(accountId);
		if (account != null) {
			revokedAccountServiceImpl.createRevokedAccount(account, transverAcc);
			accountServiceImpl.transferAccount(account, transverAcc);
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@RequestMapping(value = "serviceRefresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> serviceRefresh() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/{bankId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "readBankAccounts")
	public ResponseEntity<ArrayList<Account>> getAllAccountsForBank(@PathVariable("bankId") Long bankId) {
		ArrayList<Account> bankAccounts = accountServiceImpl.getAllAccountsForBank(bankId);
		if (bankAccounts != null) {
			return new ResponseEntity<ArrayList<Account>>(bankAccounts, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/search/{active}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "searchAccounts")
	public ResponseEntity<Collection<Account>> getAllAccountsForBank(@RequestBody Account account, @PathVariable("active") String active) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(1900, 1, 1);
		Date openingMin = cal.getTime();
		cal.set(2100, 12, 31);
		Date openingMax = cal.getTime();
		if(account.getOpeningDate() != null){
			cal.setTime(account.getOpeningDate());
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			openingMin = cal.getTime();
			cal.setTime(account.getOpeningDate());
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			openingMax = cal.getTime();
		}
		
		String accountNumber = "";
		if(account.getAccountNumber() != null){
			accountNumber = account.getAccountNumber();
		}
		
		accountNumber = "%" + accountNumber + "%";
		
		String bankName = "";
		if(account.getBank().getName() != null){
			bankName = account.getBank().getName();
		}
		
		bankName = "%" + bankName + "%";
		
		String name = "";
		if(account.getClient().getName() != null){
			name = account.getClient().getName();
		}
		
		name = "%" + name + "%";
		
		String surname = "";
		if(account.getClient().getSurname() != null){
			surname = account.getClient().getSurname();
		}
		
		surname = "%" + surname + "%";
		
		String currency = "";
		if(account.getCurrency().getOfficialCode() != null){
			currency = account.getCurrency().getOfficialCode();
		}
		
		currency = "%" + currency + "%";
		
		Collection<Account> bankAccounts;
		
		if(active.equals("null")){
			 bankAccounts = accountServiceImpl.search(accountNumber, openingMin, openingMax, bankName, name, surname, currency);
		} else {
			if(active.equals("true")){
				 bankAccounts = accountServiceImpl.searchWithActive(accountNumber, openingMin, openingMax, bankName, name, surname, currency, true);
			} else {
				 bankAccounts = accountServiceImpl.searchWithActive(accountNumber, openingMin, openingMax, bankName, name, surname, currency, false);
			}
		}
		
		if (bankAccounts != null) {
			return new ResponseEntity<Collection<Account>>(bankAccounts, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
