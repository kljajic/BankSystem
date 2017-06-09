package com.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Country implements Serializable{
	
	private static final long serialVersionUID = -3785043376020101373L;

	@GeneratedValue
	@Id
	private Long id;

	@Size(max = 40)
	@Column(nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = City.class, mappedBy="country")
	private Set<City> cities;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Bank.class, mappedBy="country")
	private Set<Bank> banks;

	public Country() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Set<City> getCities() {
		return cities;
	}
	
	@JsonProperty
	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	@JsonIgnore
	public Set<Bank> getBanks() {
		return banks;
	}

	@JsonProperty
	public void setBanks(Set<Bank> banks) {
		this.banks = banks;
	}
	
}
