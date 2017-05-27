package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {


	private static final long serialVersionUID = 4207693779878640627L;
	
	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue
	private Long id;
	
	@Column(name = "ACCOUNT_NUM")
	private String accountNumber;
	
	
}
