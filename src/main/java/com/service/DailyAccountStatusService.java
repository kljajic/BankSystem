package com.service;

import java.util.Collection;
import java.util.Date;

import com.model.DailyAccountStatus;

public interface DailyAccountStatusService {

	public DailyAccountStatus createDailyAccountStatus(DailyAccountStatus dailyAccountStatus);
	public Collection<DailyAccountStatus> getDailyAccountStatuses(Date date);
	public DailyAccountStatus updateDailyAccountStatus(DailyAccountStatus dailyAccountStatus);
	public void deleteDailyAccountStatus(Long id);
	
}
