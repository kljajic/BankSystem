package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.user.Client;
import com.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Collection<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public Client getClient(Long id) {
		return clientRepository.getOne(id);
	}

	@Override
	public Client save(Client client) {
		client.setPassword(passwordEncoder.encode(client.getPassword()));
		Client c = clientRepository.save(client);
		System.out.println(c.getId());
		clientRepository.addClientRole(c.getId());
		return c;
	}

}
