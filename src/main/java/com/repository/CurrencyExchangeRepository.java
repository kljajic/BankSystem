package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{

}
