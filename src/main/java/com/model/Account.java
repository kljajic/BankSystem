package com.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.user.Client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "ACCOUNT")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "account", namespace = "http://com/model/account", propOrder = { "accountNumber" })
public class Account implements Serializable {


	private static final long serialVersionUID = 4207693779878640627L;

	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue
	private Long id;

	@XmlElement(name = "accountNumber", required = true)
	@Column(name = "ACCOUNT_NUM")
	private String accountNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	@Column(name = "ACCOUNT_DATE")
	private Date openingDate;

	@Column(name = "ACCOUNT_ACTIVE")
	private boolean active;

	@ManyToOne
	private Bank bank;

	@ManyToOne
	private Client client;

	@ManyToOne
	private Currency currency;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DailyAccountStatus> dailyAccountStatuses = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
	private Set<RevokedAccount> revokedAccounts;

	@JsonIgnore
	public Set<DailyAccountStatus> getDailyAccountStatuses() {
		return dailyAccountStatuses;
	}

	@JsonProperty
	public void setDailyAccountStatuses(Set<DailyAccountStatus> dailyAccountStatuses) {
		this.dailyAccountStatuses = dailyAccountStatuses;
	}

	@JsonIgnore
	public Set<RevokedAccount> getRevokedAccounts() {
		return revokedAccounts;
	}

	@JsonProperty
	public void setRevokedAccounts(Set<RevokedAccount> revokedAccounts) {
		this.revokedAccounts = revokedAccounts;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


}
