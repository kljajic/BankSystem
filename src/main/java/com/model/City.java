package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class City {
	
	@GeneratedValue
	@Id
	Long id;
	
	@ManyToOne(optional = false)
	private Country country;

	public City() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Country getCountry() {
		return country;
	}

}
