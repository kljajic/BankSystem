package com.model;

import java.util.HashSet;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="currency", namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
		"officialCode"
	} 
)
public class Currency {
	
	@GeneratedValue
	@Id
	private Long id;

	@XmlElement(name="officialCode", required=true)
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
	@OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
	private Set<AnalyticalStatement> analyticalStatement = new HashSet<>();
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

	@JsonIgnore
	public Set<AnalyticalStatement> getAnalyticalStatement() {
		return analyticalStatement;
	}

	@JsonProperty
	public void setAnalyticalStatement(Set<AnalyticalStatement> analyticalStatement) {
		this.analyticalStatement = analyticalStatement;
	}
	
}
