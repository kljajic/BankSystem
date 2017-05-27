package com.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;


@Entity
//@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"code"}))
public class Country {
	
	public void setName(String name) {
		this.name = name;
	}

	@GeneratedValue
	@Id
	private Long id;

	@Size(max = 40)
	@Column(nullable = false)
	private String name;
	
	/*@Size(min = 3, max = 3)
	@Column(nullable = false)
	String code;
	*/
	
	//napravi country atribut u City !!!!!
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = City.class, mappedBy="country")
	private Set<City> cities;

	public Country() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/*public String getCode() {
		return code;
	}*/

	public Set<City> getCities() {
		return cities;
	}
	
	
}
