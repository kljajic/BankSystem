package com.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.DailyAccountStatus;
import com.repository.DailyAccountStatusRepository;

@Service
@Transactional
public class DailyAccountStatusServiceImpl implements DailyAccountStatusService{

	private final DailyAccountStatusRepository dailyAccountStatusRepository;
	
	@Autowired
	public DailyAccountStatusServiceImpl(DailyAccountStatusRepository dailyAccountStatusRepository){
		this.dailyAccountStatusRepository = dailyAccountStatusRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public DailyAccountStatus createDailyAccountStatus(DailyAccountStatus dailyAccountStatus) {
		return dailyAccountStatusRepository.save(dailyAccountStatus);
	}

	@Override
	public Collection<DailyAccountStatus> getDailyAccountStatuses(Date date) {
		return dailyAccountStatusRepository.findDailyAccountStatusesByDate(date);
	}

	@Override
	public DailyAccountStatus updateDailyAccountStatus(DailyAccountStatus dailyAccountStatus) {
		return null;
	}

	@Override
	public void deleteDailyAccountStatus(Long id) {
		dailyAccountStatusRepository.delete(id);
	}
	
}
