package com.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
public class ExchangeList {

	@GeneratedValue
	@Id
	private Long id;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	@Min(3)
	@Max(3)
	private int numberOfExchangeList;
	
	@Column(nullable = false)
	private Date usedSince;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity=CurrencyExchange.class, mappedBy = "exchangeList")
	private Set<CurrencyExchange> currencyExchanges;
	
	//polje banke
	@ManyToOne(optional = false)
	private Bank bank;
	
	public Bank getBank() {
		return bank;
	}

	public ExchangeList() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getNumberOfExchangeList() {
		return numberOfExchangeList;
	}

	public Date getUsedSince() {
		return usedSince;
	}

	public Set<CurrencyExchange> getCurrencyExchanges() {
		return currencyExchanges;
	}
	
}
