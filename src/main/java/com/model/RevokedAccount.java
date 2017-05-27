package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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
	
	public RevokedAccount(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRevocationDate() {
		return revocationDate;
	}

	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

	public String getTransferAcc() {
		return transferAcc;
	}

	public void setTransferAcc(String transferAcc) {
		this.transferAcc = transferAcc;
	}
	
	
}
