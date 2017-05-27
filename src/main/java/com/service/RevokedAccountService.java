package com.service;

import com.model.RevokedAccount;

public interface RevokedAccountService {
	
	public RevokedAccount createRevokedAccount(RevokedAccount ra);
	public RevokedAccount updateRevokedAccount(RevokedAccount ra);
	public RevokedAccount deleteRevokedAccount(Long id);
}
