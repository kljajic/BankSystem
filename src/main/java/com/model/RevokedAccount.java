package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"revocationDate", "transferAcc", "account"})
@ToString(of = {"revocationDate", "transferAcc", "account"})
@Table(name = "REV_ACC")
public class RevokedAccount implements Serializable {

	private static final long serialVersionUID = -4671431812398814717L;

	@Id
	@Column(name = "REV_ACC")
	@GeneratedValue
	private Long id;
	
	@Column(name = "REV_ACC_DATE")
	private Date revocationDate;
	
	@Column(name = "REV_ACC_TRN")
	private String transferAcc;
	
	@ManyToOne
	private Account account;
	
}
