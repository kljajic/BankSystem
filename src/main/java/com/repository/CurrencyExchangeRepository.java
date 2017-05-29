package com.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{

	@Query("select ce from CurrencyExchange as ce")
	public ArrayList<CurrencyExchange> findAll();
	
	@Modifying
	@Query(value="update currency_exchange ce set ce.buy_rate=?2, ce.middle_rate=?3, ce.sell_rate=?4, ce.exchange_list_id=?5, ce.primary_currency_id=?6, ce.according_to_currency_id=?7 where ce.id=?1", nativeQuery=true)
	public void updateCurrencyExchange(Long id, Double buyRate, Double middleRate, Double sellRate, Long exchangeList, Long primaryCurrency, Long accordingToCurrency);

	
	//@Query(value="select distinct ce.id, ce.buy_rate, ce.middle_rate, ce.sell_rate, ce.exchange_list_id, ce.primary_currency_id, ce.according_to_currency_id from currency_exchange ce inner join country d on c.country_id = d.id where c.name like ?1% and c.official_code like ?2% and c.domicilna like ?4% and d.name like ?3%" ,nativeQuery=true)
	//public ArrayList<CurrencyExchange> searchCurrencyExchange(Double buyRate, Double middleRate, Double sellRate, int numberOfExchangeList, String offCodePrimaryCurrency, String offCodeAccordingToCurrency);

	//da li and, or, between ...
	@Query("select ce from CurrencyExchange as ce where ce.buyRate=?1 and ce.middleRate=?2 and ce.sellRate=?3 and ce.exchangeList.numberOfExchangeList=?4 and ce.primaryCurrency.officialCode like ?5% and ce.accordingToCurrency.officialCode like ?6%")
	public ArrayList<CurrencyExchange> searchCurrencyExchange(Double buyRate, Double middleRate, Double sellRate, int numberOfExchangeList, String offCodePrimaryCurrency, String offCodeAccordingToCurrency);
}
