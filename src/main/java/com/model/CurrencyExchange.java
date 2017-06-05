package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

@Entity
public class CurrencyExchange {
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(nullable = false)
	@Digits(fraction = 4, integer = 13)
	private Double buyRate;
	
	@Column(nullable = false)
	@Digits(fraction = 4, integer = 13)
	private Double middleRate;
	
	@Column(nullable = false)
	@Digits(fraction = 4, integer = 13)
	private Double sellRate;
	
	@ManyToOne(optional = false)
	private ExchangeList exchangeList;
	
	@ManyToOne(optional = false)
	private Currency primaryCurrency;

	@ManyToOne(optional = false)
	private Currency accordingToCurrency;

	public CurrencyExchange() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Double getBuyRate() {
		return buyRate;
	}

	public Double getMiddleRate() {
		return middleRate;
	}

	public Double getSellRate() {
		return sellRate;
	}

	public ExchangeList getExchangeList() {
		return exchangeList;
	}

	public Currency getPrimaryCurrency() {
		return primaryCurrency;
	}

	public Currency getAccordingToCurrency() {
		return accordingToCurrency;
	}
	
	
}
