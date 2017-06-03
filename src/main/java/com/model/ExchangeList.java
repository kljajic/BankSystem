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
import javax.validation.constraints.Digits;
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
	@Digits(integer=3, fraction = 0)
	private int numberOfExchangeList;
	
	@Column(nullable = false)
	private Date usedSince;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity=CurrencyExchange.class, mappedBy = "exchangeList")
	private Set<CurrencyExchange> currencyExchanges;
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setNumberOfExchangeList(int numberOfExchangeList) {
		this.numberOfExchangeList = numberOfExchangeList;
	}

	public void setUsedSince(Date usedSince) {
		this.usedSince = usedSince;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

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
	
}
