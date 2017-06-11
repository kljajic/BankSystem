package com.model.user;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"address", "dateOfBirth"}, callSuper = true)
@ToString(of = {"address", "dateOfBirth"})
@Table(name = "CLIENT")
public class Client extends User {
	
	private static final long serialVersionUID = 3025173065489902929L;
	
	@Column
	private String address;
	
	@Column
	private Date dateOfBirth;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade=CascadeType.ALL, orphanRemoval = true)
	private Set<Account> accounts;

	@JsonIgnore
	public Set<Account> getAccounts() {
		return accounts;
	}
	@JsonProperty
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	
}
