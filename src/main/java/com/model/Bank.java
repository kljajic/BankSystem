package com.model;

import java.io.Serializable;
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

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.xml.ClearingSettlementRequest;
import com.model.xml.RTGSRequest;
import com.model.xml.RTGSResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = { "country", "pib", "name", "address", "email", "web", "telephone", 
		"fax", "banka", "swift", "transactionAccount"})
@ToString(of = { "country", "pib", "name", "address", "email", "web", "telephone", 
		"fax", "banka", "swift", "transactionAccount"})
@Entity
@Table(name = "BANK")
@XmlType(name = "bank", namespace = "http://com/model/bank", propOrder = { "swift",
		"transactionAccount" })
@XmlAccessorType(XmlAccessType.NONE)
public class Bank implements Serializable {

	private static final long serialVersionUID = -5790070241207558112L;

	@Id
	@Column(name = "BANK_ID")
	@GeneratedValue
	private Long id;

	// lista kursnih lista...
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = ExchangeList.class, mappedBy = "bank")
	private Set<ExchangeList> exchangeLists;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bank", orphanRemoval = true, targetEntity = Account.class)
	private Set<Account> accounts;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = RTGSResponse.class, mappedBy = "bank")
	private Set<RTGSResponse> rTGSResponses;
	
	@ManyToOne
	private Country country;

	@Column(name = "BANK_PIB", nullable = false, length = 10)
	private String pib;

	@Column(name = "BANK_NAME", nullable = false, length = 120)
	private String name;

	@Column(name = "BANK_ADDRESS", nullable = false, length = 120)
	private String address;

	@Column(name = "BANK_EMAIL", length = 128)
	@Email
	private String email;

	@Column(name = "BANK_WEB", length = 128)
	private String web;

	@Column(name = "BANK_TEL", length = 20)
	private String telephone;

	@Column(name = "BANK_FAX", length = 20)
	private String fax;

	@Column(name = "BANK_ACT", nullable = false)
	private boolean banka;

	@XmlElement(name = "swift", required = true)
	@Column(name = "BANK_SWT", nullable = false, length = 8)
	private String swift;

	@XmlElement(name = "transactionAccount", required = true)
	@Column(name = "BANK_TR_ACC")
	private String transactionAccount;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = RTGSRequest.class, mappedBy="paymentBank")
	private Set<RTGSRequest> rtgsRequestsPaymentBank;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = RTGSRequest.class, mappedBy="recieverBank")
	private Set<RTGSRequest> rtgsRequestsRecieverBank;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = ClearingSettlementRequest.class, mappedBy="paymentBank")
	private Set<ClearingSettlementRequest> cSPaymentBank;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = ClearingSettlementRequest.class, mappedBy="recieverBank")
	private Set<ClearingSettlementRequest> cSRecieverBank;
	

	
	
	public String getSwift() {
		return swift;
	}

	@JsonIgnore
	public Set<ExchangeList> getExchangeLists() {
		return exchangeLists;
	}

	@JsonProperty
	public void setExchangeLists(Set<ExchangeList> exchangeLists) {
		this.exchangeLists = exchangeLists;
	}

	@JsonIgnore
	public Set<Account> getAccounts() {
		return accounts;
	}

	@JsonProperty
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@JsonIgnore
	public Set<RTGSRequest> getRtgsRequestsPaymentBank() {
		return rtgsRequestsPaymentBank;
	}

	@JsonIgnore
	public Set<RTGSRequest> getRtgsRequestsRecieverBank() {
		return rtgsRequestsRecieverBank;
	}

	@JsonIgnore
	public Set<RTGSResponse> getrTGSResponses() {
		return rTGSResponses;
	}

	@JsonIgnore
	public Set<ClearingSettlementRequest> getcSPaymentBank() {
		return cSPaymentBank;
	}
	
	@JsonIgnore
	public Set<ClearingSettlementRequest> getcSRecieverBank() {
		return cSRecieverBank;
	}
	
	
}
