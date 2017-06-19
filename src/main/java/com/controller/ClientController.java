package com.controller;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Client;
import com.model.user.Permission;
import com.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping
	@ResponseBody
	@Permission(permissionName = "readClients")
	public ResponseEntity<Collection<Client>> getAllClients(){
		Collection<Client> clients = clientService.getAllClients();
		if(clients != null){
			return  new ResponseEntity<Collection<Client>>(clients, HttpStatus.OK);
		} else {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path = "/registerClient", method = RequestMethod.POST)
	@ResponseBody
	@Permission(permissionName = "registerClient")
	@Transactional
	public Client registerClient(@RequestBody Client client){
		clientService.save(client);
		return client;
	}
	
}
