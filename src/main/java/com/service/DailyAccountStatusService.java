package com.service;

import java.util.Collection;
import java.util.Date;

import com.model.DailyAccountStatus;

public interface DailyAccountStatusService {

	DailyAccountStatus createDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus);
	Collection<DailyAccountStatus> getDailyAccountStatuses(Date date);
	DailyAccountStatus getDailyAccountStatus(Long id);
	DailyAccountStatus updateDailyAccountStatus(Long accountId, DailyAccountStatus dailyAccountStatus);
	void deleteDailyAccountStatus(Long id);
	Collection<DailyAccountStatus> searchDailyAccountStatuses(DailyAccountStatus dailyAccountStatus);
	
}
