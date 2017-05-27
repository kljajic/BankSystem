package com.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	
	@Query("select c from Currency as c")
	public ArrayList<Currency> findAll();
	
	@Modifying
	@Query(value="update currency c set c.name=?2, c.official_code=?3, c.country_id=?4, c.domicilna=?5 where c.id=?1", nativeQuery=true)
	public void updateCurrency(Long id, String name, String officialCode, Long country, boolean domicilna);

	@Query(value="select distinct c.id, c.name, c.official_code, c.country_id, c.domicilna from currency c inner join country d on c.country_id = d.id where c.name like ?1% and c.official_code like ?2% and c.domicilna like ?4% and d.name like ?3%" ,nativeQuery=true)
	public ArrayList<Currency> searchCurrency(String name, String officialCode, String country, boolean domicilna);
	
}
