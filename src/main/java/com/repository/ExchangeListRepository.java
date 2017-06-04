package com.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.ExchangeList;

public interface ExchangeListRepository extends JpaRepository<ExchangeList, Long> {

	@Query("select el from ExchangeList as el")
	public ArrayList<ExchangeList> findAll();
	
	@Modifying
	@Query(value="update exchange_list el set el.date=?2, el.number_of_exchange_list=?3, el.used_since=?4 where el.id=?1", nativeQuery = true)
	public void editEL(Long id, Date date, int numberOfExchangeList, Date usedSince);
	
	@Query("select el from ExchangeList as el where el.date=?1 and el.numberOfExchangeList=?2 and el.usedSince=?3")
	public ArrayList<ExchangeList> searchEL(Date date, int numberOfExchangeList, Date usedSince);
	
	@Query("select el from ExchangeList as el where el.numberOfExchangeList between ?1 and ?2 and Date(el.date) between Date(?3) and Date(?4) and Date(el.usedSince) between Date(?5) and Date(?6) and el.bank.name like ?7")
	public Set<ExchangeList> search(int elNumberMin, int elNumberMax, Date dateMin, Date dateMax, Date usedSinceMin, Date usedSinceMax, String bankName);
}
