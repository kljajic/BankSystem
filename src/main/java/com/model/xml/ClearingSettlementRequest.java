package com.model.xml;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.model.Bank;
import com.model.CurrencyExchange;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="clearing_settlement_request", namespace="http://informatika.ftn.ns.ac.yu/ws/model")
public class ClearingSettlementRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6440091870687987147L;
	
	private Bank paymentBank;
	
	private Bank recieverBank;
	
	private Double totalAmount;
	
	private CurrencyExchange currencyExchange;
	
	private Date date;

}
