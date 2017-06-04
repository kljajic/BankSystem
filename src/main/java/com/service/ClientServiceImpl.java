package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.user.Client;
import com.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public Collection<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public Client getClient(Long id) {
		return clientRepository.getOne(id);
	}

}
