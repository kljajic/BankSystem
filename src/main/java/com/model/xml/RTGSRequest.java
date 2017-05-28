package com.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.model.AnalyticalStatement;
import com.model.Bank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RTGSRequest", namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
		"messageId", 
		"paymentBank", 
		"recieverBank",
		"analyticalStatement"
})
public class RTGSRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1558855408444568463L;
	
	@XmlElement(name="messageId", required=true)
	private String messageId;
	
	@XmlElement(name="paymentBank", required=true)
	private Bank paymentBank;
	
	@XmlElement(name="recieverBank", required=true)
	private Bank recieverBank;
	
	@XmlElement(name="analyticalStatement", required=true)
	private AnalyticalStatement analyticalStatement;
	
	/*
	 * 
	 * @XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="bank", namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
		"swift",
		"transactionAccount"
	 */
	
}
