package com.service;

import java.util.Collection;
import java.util.Date;

import com.model.DailyAccountStatus;

public interface DailyAccountStatusService {

	DailyAccountStatus createDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus, Date date);
	Collection<DailyAccountStatus> getDailyAccountStatuses();
	DailyAccountStatus getDailyAccountStatus(Long id);
	DailyAccountStatus updateDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus, Date date);
	void deleteDailyAccountStatus(Long id);
	Collection<DailyAccountStatus> searchDailyAccountStatuses(Long accountId, DailyAccountStatus dailyAccountStatus, Date date);
	
}
