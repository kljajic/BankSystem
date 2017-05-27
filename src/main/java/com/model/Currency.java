package com.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Currency {
	
	@GeneratedValue
	@Id
	private Long id;

	@Column(nullable = false)
	@Size(min = 3, max = 3)
	private String officialCode;
	
	@Column(nullable = false)
	@Size(max = 30)
	private String name;
	
	@Column(nullable = false)
	private boolean domicilna;
	
	@ManyToOne(optional = false)
	private Country country;
	
	//lista analitika izvoda
	//lista racuna pravnih lica
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = CurrencyExchange.class, mappedBy="primaryCurrency")
	private Set<CurrencyExchange> primaryCurrencyExchanges;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = CurrencyExchange.class, mappedBy="accordingToCurrency")
	private Set<CurrencyExchange> accordingTocurrencyExchanges;
	
	public Country getCountry() {
		return country;
	}

	public Currency() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getOfficialCode() {
		return officialCode;
	}

	public String getName() {
		return name;
	}

	public boolean isDomicilna() {
		return domicilna;
	}
	
}
