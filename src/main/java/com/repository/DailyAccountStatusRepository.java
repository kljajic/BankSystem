package com.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.DailyAccountStatus;

@Repository
public interface DailyAccountStatusRepository extends JpaRepository<DailyAccountStatus, Long> {
	
	public Collection<DailyAccountStatus> findDailyAccountStatusesByDate(Date date);

}
