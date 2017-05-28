package com.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="company", namespace="http://informatika.ftn.ns.ac.yu/ws/model", propOrder={
		"name", 
		"pib", 
		"address"
})
public class Company implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5675230633531765526L;
	
	@XmlElement(name="name", required=true)
	private String name;
	
	@XmlElement(name="pib", required=true)
	private String pib;
	
	@XmlElement(name="address", required=true)
	private String address;
	
}
