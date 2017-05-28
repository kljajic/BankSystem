package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ACCOUNT")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="account", namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
	"accountNumber"
})
public class Account implements Serializable {


	private static final long serialVersionUID = 4207693779878640627L;
	
	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue
	private Long id;
	
	@XmlElement(name="accountNumber", required=true)
	@Column(name = "ACCOUNT_NUM")
	private String accountNumber;
	
	@Column(name = "ACCOUNT_DATE")
	private Date openingDate;
	
	@Column(name = "ACCOUNT_ACTIVE")
	private boolean active;

	public Account(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
