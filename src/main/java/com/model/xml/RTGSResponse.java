package com.model.xml;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.model.Bank;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="MT900")
@XmlType(name="RTGSResponse",  namespace="http://com/model/rtgsResponse", propOrder={
		"messageId",
		"responseType",
		"bank", 
		"requestId", 
		"currencyDate",
		"value",
		"currencyCode",
})
@Entity
public class RTGSResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1104060011581400898L;
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(nullable = false)
	@Size(min=1, max=50)
	@XmlElement(name="messageId", required=true)
	private String messageId;
	
	@Column(nullable = false)
	@XmlElement(name="responseType", required=true)
	private RTGSResponseType responseType;
	
	@ManyToOne(optional = true)
	@XmlElement(name="bank", required=true)
	private Bank bank;
	
	@Column(nullable = false)
	@Size(min=1, max=50)
	@XmlElement(name="requestId", required=true)
	private String requestId;
	
	@Column(nullable = false)
	@XmlElement(name="currencyDate", required=true)
	private Date currencyDate;
	
	@Column(nullable = false)
	@Digits(integer = 17, fraction = 2)
	@XmlElement(name="value", required=true)
	private Double value;
	
	@Column(nullable = false)
	@Size(min=1, max=3)
	@XmlElement(name="currencyCode", required=true)
	private String currencyCode;
	
}
