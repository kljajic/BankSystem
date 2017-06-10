package com.model.xml;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.model.AnalyticalStatement;
import com.model.Bank;
import com.model.Currency;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="clearing_settlement_request", namespace="http://com/model/clearingAndSettlement", propOrder={
		"paymentBank",
		"recieverBank",
		"totalAmount",
		"currency",
		"currencyDate",
		"date",
		"analyticalStatements",
})
@XmlRootElement(name="MT102")
public class ClearingSettlementRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6440091870687987147L;
	
	@XmlElement(name="paymentBank", required=true)
	private Bank paymentBank;
	
	@XmlElement(name="recieverBank", required=true)
	private Bank recieverBank;
	
	@XmlElement(name="totalAmount", required=true)
	private Double totalAmount;
	
	@XmlElement(name="currency", required=true)
	private Currency currency;
	
	@XmlElement(name="currencyDate", required=true)
	private Date currencyDate;
	
	@XmlElement(name="date", required=true)
	private Date date;
	
	@XmlElement(name="analyticalStatements", required=true)
	private Set<AnalyticalStatement> analyticalStatements;

}
