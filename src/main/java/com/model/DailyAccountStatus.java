package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@ApiModel(value = "Daily account status", description = "Daily account status.")
public class DailyAccountStatus implements Serializable{

	private static final long serialVersionUID = 8421999162724817258L;

	@Id
	@SequenceGenerator(name = "DAILY_ACCOUNT_STATUS_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "DAILY_ACCOUNT_STATUS_ID_GEN")
	@ApiModelProperty(value = "Daily account status id.")
	private Long id;
	
	@Column
	@NotNull
	@ApiModelProperty(value = "Date of daily account status.", required = true)
	private Date date;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Previous ammount on account.", required = true)
	private double previousAmmount;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Transfer in favor.", required = true)
	private double transferInFavor;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Transfer expenses.", required = true)
	private double transferExpenses;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Current ammount on account.", required = true)
	private double currentAmmount;
	
	@ManyToOne
	private Account account;
	
}
