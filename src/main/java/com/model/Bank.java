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
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;



@Entity
@Table(name = "BANK")
public class Bank implements Serializable {

	private static final long serialVersionUID = -5790070241207558112L;
	
	@Id
	@Column(name = "BANK_ID")
	@GeneratedValue
	private Long id;
	
	//lista kursnih lista...
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = ExchangeList.class, mappedBy="bank")
	private Set<ExchangeList> exchangeLists;
	
	@Column(name = "BANK_PIB", nullable = false, length = 10)
	private String pib;
	
	@Column(name = "BANK_NAME", nullable = false, length = 120)
	private String name;
	
	@Column(name = "BANK_ADRESS", nullable = false, length = 120)
	private String adress;
	
	@Column(name = "BANK_EMAIL",  length = 128)
	@Email
	private String email;
	
	@Column(name = "BANK_WEB",  length = 128)
	private String web;
	
	@Column(name = "BANK_TEL",length = 20)
	private String telephone;
	
	@Column(name = "BANK_FAX",length = 20)
	private String fax;
	
	@Column(name = "BANK_ACT", nullable = false)
	private boolean banka;
	
	@Column(name = "BANK_SWT", nullable = false, length = 8)
	private String swift;
	
	@Column(name = "BANK_TR_ACC")
	private String transactionAccount;
	
	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getTransactionAccount() {
		return transactionAccount;
	}

	public void setTransactionAccount(String transactionAccount) {
		this.transactionAccount = transactionAccount;
	}

	public Bank(){
			
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public boolean isBanka() {
		return banka;
	}

	public void setBanka(boolean banka) {
		this.banka = banka;
	}
	
}
