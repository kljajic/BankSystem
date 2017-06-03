package com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@ApiModel(value = "Analytical statment", description = "Analytical statment.")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="analytical_statement", namespace="", propOrder={
	"id", 
	"originator", 
	"purpose",
	"recipient",
	"dateOfReceipt",
	"currencyDate",
	"originatorAccount",
	"model",
	"debitAuthorizationNumber",
	"recipientAccount",
	"approvalModel",
	"approvalAuthorizationNumber",
	"amount",
	"currency",
	"urgently",
	"direction"
})

public class AnalyticalStatement {
	
	@XmlElement(name="id", required=true)
	@Id
	@SequenceGenerator(name = "ANALYTICAL_STATEMENT_ID_GEN")
	@GeneratedValue(generator = "ANALYTICAL_STATEMENT_ID_GEN")
	@ApiModelProperty(value = "Analtyical statement's id.")
	private Long id;
	
	@XmlElement(name="originator", required=true)
	@Column
	@NotEmpty
	@Size(max = 256)
	@ApiModelProperty(value = "Analtyical statement's originator.", required = true)
	private String originator;
	
	@XmlElement(name="purpose", required=true)
	@Column
	@NotEmpty
	@Size(max = 256)
	@ApiModelProperty(value = "Analtyical statement's purpose.", required = true)
	private String purpose;
	
	@XmlElement(name="recipient", required=true)
	@Column
	@NotEmpty
	@Size(max = 256)
	@ApiModelProperty(value = "Analtyical statement's recipient.", required = true)
	private String recipient;
	
	@XmlElement(name="dateOfReceipt", required=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	@Column
	@NotNull
	@ApiModelProperty(value = "Analtyical statement's date of receipt.", required = true)
	private Date dateOfReceipt;
	
	@XmlElement(name="currencyDate", required=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	@Column
	@NotNull
	@ApiModelProperty(value = "Currency date.", required = true)
	private Date currencyDate;
	
	@XmlElement(name="originatorAccount", required=true)
	@Column
	@Size(min = 0 , max = 18)
	@ApiModelProperty(value = "Originator's account.")
	private String originatorAccount;
	
	@XmlElement(name="model", required=true)
	@Column
	@Digits(integer = 2, fraction = 0)
	@ApiModelProperty(value = "Analtyical statement's model.")
	private short model;
	
	@XmlElement(name="debitAuthorizationNumber", required=true)
	@Column
	@Size(min = 0, max = 20)
	@ApiModelProperty(value = "Debit autorization number.")
	private String debitAuthorizationNumber;
	
	@XmlElement(name="recipientAccount", required=true)
	@Column
	@Size(min = 0 , max = 18)
	@ApiModelProperty(value = "Recipient account.")
	private String recipientAccount;
	
	@XmlElement(name="approvalModel", required=true)
	@Column
	@Digits(integer = 2 , fraction = 0)
	@ApiModelProperty(value = "Approval model.")
	private short approvalModel;
	
	@XmlElement(name="approvalAuthorizationNumber", required=true)
	@Column
	@Size(min = 0, max = 20)
	@ApiModelProperty(value = "Approval authorization number.")
	private String approvalAuthorizationNumber;
	
	@XmlElement(name="urgently", required=true)
	@Column
	@NotNull
	@ApiModelProperty(value = "Urgent analtyical statement.", required = true)
	private boolean urgently;
	
	@XmlElement(name="amount", required=true)
	@Column
	@NotNull
	@Digits(integer = 15, fraction = 2)
	@ApiModelProperty(value = "Amount.", required = true)
	private double amount;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "Analtyical statement's error type.", required = true)
	private AnalyticalErrors errorType;
	
	//status
	
	@ManyToOne
	private DailyAccountStatus dailyAccountStatus;
	
	@ManyToOne
	private City placeOfAcceptance;
	
	@ManyToOne
	private PaymentType paymentType;
	
	@XmlElement(name="currency", required=true)
	@ManyToOne
	private Currency currency;
	
	@XmlElement(name="direction", required=false)
	private boolean uplata;
	
}
