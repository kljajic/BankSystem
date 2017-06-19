package com.service;

import java.util.Collection;

import com.model.user.Client;

public interface ClientService {
	Collection<Client> getAllClients();
	Client getClient(Long id);
	Client save(Client client);
}
