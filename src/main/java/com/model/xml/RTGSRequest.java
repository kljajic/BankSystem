package com.model.xml;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.model.AnalyticalStatement;
import com.model.Bank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="RTGSRequest", namespace="http://com/model/rtgsRequest", propOrder={
		"messageId", 
		"paymentBank", 
		"recieverBank",
		"analyticalStatement"
})
@XmlRootElement(name="MT103")
@Entity
public class RTGSRequest implements Serializable{

	private static final long serialVersionUID = 1558855408444568463L;
	
	@GeneratedValue
	@Id
	private Long id;
	
	@XmlElement(name="messageId", required=true)
	@Column(nullable = false)
	private String messageId;
	
	@XmlElement(name="paymentBank", required=true)
	@ManyToOne(optional = false)
	private Bank paymentBank;
	
	@XmlElement(name="recieverBank", required=true)
	@ManyToOne(optional = false)
	private Bank recieverBank;
	
	@XmlElement(name="analyticalStatement", required=true)
	@ManyToOne(optional = false)
	private AnalyticalStatement analyticalStatement;

	
}
