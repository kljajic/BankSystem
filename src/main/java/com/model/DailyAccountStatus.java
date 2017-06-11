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
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="daily_account_status", namespace="http://com/model/dailyAccountStatus", propOrder={
	"id", 
	"date", 
	"previousAmount",
	"transferInFavor",
	"numberOfChanges",
	"transferExpenses",
	"currentAmount",
	"account"
})
public class DailyAccountStatus implements Serializable{

	private static final long serialVersionUID = 8421999162724817258L;

	@Id
	@XmlElement(name="id", required=true)
	@SequenceGenerator(name = "DAILY_ACCOUNT_STATUS_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "DAILY_ACCOUNT_STATUS_ID_GEN")
	@ApiModelProperty(value = "Daily account status id.")
	private Long id;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	@ApiModelProperty(value = "Date of daily account status.", required = true)
	@XmlElement(name="date", required=true)
	private Date date;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Previous amount on account.", required = true)
	@XmlElement(name="previousAmount", required=true)
	private double previousAmount;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Transfer in favor.", required = true)
	@XmlElement(name="transferInFavor", required=true)
	private double transferInFavor;
	
	@Column
	@NotNull
	@Digits(integer=6, fraction=0)
	@ApiModelProperty(value = "Number of changes.", required = true)
	@XmlElement(name="numberOfChanges", required=true)
	private int numberOfChanges;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Transfer expenses.", required = true)
	@XmlElement(name="transferExpenses", required=true)
	private double transferExpenses;
	
	@Column
	@NotNull
	@Digits(integer=15, fraction=2)
	@ApiModelProperty(value = "Current amount on account.", required = true)
	@XmlElement(name="currentAmount", required=true)
	private double currentAmount;
	
	@ManyToOne
	@XmlElement(name="account", required=true)
	private Account account;
	
	@OneToMany(mappedBy = "dailyAccountStatus", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AnalyticalStatement> analyticalStatements = new HashSet<>();
	
	@JsonIgnore
	public Set<AnalyticalStatement> getAnalyticalStatements(){
		return analyticalStatements;
	}
	
	@JsonProperty
	public void setAnalyticalStatements(Set<AnalyticalStatement> analyticalStatements){
		this.analyticalStatements = analyticalStatements;
	}
	
}
