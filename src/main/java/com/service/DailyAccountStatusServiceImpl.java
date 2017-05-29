package com.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Account;
import com.model.DailyAccountStatus;
import com.repository.DailyAccountStatusRepository;

@Service
@Transactional
public class DailyAccountStatusServiceImpl implements DailyAccountStatusService{

	private final DailyAccountStatusRepository dailyAccountStatusRepository;
	private final AccountService accountService;
	
	@Autowired
	public DailyAccountStatusServiceImpl(DailyAccountStatusRepository dailyAccountStatusRepository,
										 AccountService accountService){
		this.dailyAccountStatusRepository = dailyAccountStatusRepository;
		this.accountService = accountService;
	}

	@Override
	public DailyAccountStatus createDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus) {
		Account account = accountService.getAccount(accountId);
		dailyAccountStatus.setAccount(account);
		return dailyAccountStatusRepository.save(dailyAccountStatus);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<DailyAccountStatus> getDailyAccountStatuses(Date date) {
		return dailyAccountStatusRepository.findDailyAccountStatusesByDate(date);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DailyAccountStatus getDailyAccountStatus(Long id) {
		return dailyAccountStatusRepository.findOne(id);
	}

	@Override
	public DailyAccountStatus updateDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus) {
		Account account = accountService.getAccount(accountId);
		DailyAccountStatus temp = dailyAccountStatusRepository.findOne(dailyAccountStatus.getId());
		temp.setAccount(account);
		temp.setCurrentAmmount(dailyAccountStatus.getCurrentAmmount());
		temp.setDate(dailyAccountStatus.getDate());
		temp.setNumberOfChanges(dailyAccountStatus.getNumberOfChanges());
		temp.setPreviousAmmount(dailyAccountStatus.getPreviousAmmount());
		temp.setTransferExpenses(dailyAccountStatus.getTransferExpenses());
		temp.setTransferInFavor(dailyAccountStatus.getTransferInFavor());
		return dailyAccountStatusRepository.save(temp);
	}

	@Override
	public void deleteDailyAccountStatus(Long id) {
		dailyAccountStatusRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<DailyAccountStatus> searchDailyAccountStatuses(DailyAccountStatus dailyAccountStatus) {
		return null;
	}
	
}
