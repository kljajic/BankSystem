package com.model.xml;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.model.Bank;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="RTGSResponse",  namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
		"messageId", 
		"bank", 
		"requestId", 
		"currencyDate",
		"value",
		"currencyCode",
})
public class RTGSResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1104060011581400898L;
	
	@XmlElement(name="messageId", required=true)
	private String messageId;
	
	@XmlElement(name="responseType", required=true)
	private RTGSResponseType responseType;
	
	@XmlElement(name="bank", required=true)
	private Bank bank;
	
	@XmlElement(name="requestId", required=true)
	private String requestId;
	
	@XmlElement(name="currencyDate", required=true)
	private Date currencyDate;
	
	@XmlElement(name="value", required=true)
	private Double value;
	
	@XmlElement(name="currencyCode", required=true)
	private String currencyCode;
	
}
