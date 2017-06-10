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
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
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

}
