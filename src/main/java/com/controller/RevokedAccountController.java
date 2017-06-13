package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.RevokedAccount;
import com.model.user.Permission;
import com.service.RevokedAccountServiceImpl;

@RestController
@RequestMapping("/revokedAcoounts")
public class RevokedAccountController {
	@Autowired
	private RevokedAccountServiceImpl revokedAccountService;
	
	@RequestMapping (method=RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "readAllRevertedAccounts")
	public ResponseEntity<ArrayList<RevokedAccount>> getAllRevertedAccounts(){
		ArrayList<RevokedAccount> revokedAccounts = revokedAccountService.getAllRevokedAccounts();
		if(revokedAccounts != null){
			return  new ResponseEntity<ArrayList<RevokedAccount>>(revokedAccounts,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping (value = "/{id}",
					method=RequestMethod.DELETE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName = "removeRevokedAccount")
	public ResponseEntity<RevokedAccount> deleteRevokedAccount(@PathVariable("id") Long id){
		RevokedAccount revokedAccount = revokedAccountService.deleteRevokedAccount(id);
		if(revokedAccount != null){
			return  new ResponseEntity<RevokedAccount>(revokedAccount,HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
}
