package com.model.xml;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.model.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="statement")
@XmlType(name = "invoice", 
		namespace="http://com/model/invoice", 
		propOrder = {"messageId",
					 "supplier",
					 "purchaser",
					 "billingNumber",
					 "billingDate",
					 "merchandiseValue",
					 "serviceValue",
					 "merchandiseAndServiceValue",
					 "discount",
					 "taxes",
					 "amountForPayment",
					 "accountNumber",
					 "currency",
					 "currencyDate",
					 "invoiceItems"
					})
public class Invoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7679221176377334279L;
	
	@XmlElement(name="messageId", required=true)
	private Long messageId;
	
	@XmlElement(name="supplier", required=true)
	private Company supplier;
	
	@XmlElement(name="purchaser", required=true)
	private Company purchaser;
	
	@XmlElement(name="billingNumber", required=true)
	private Integer billingNumber;
	
	@XmlElement(name="billingDate", required=true)
	private Date billingDate;
	
	@XmlElement(name="merchandiseValue", required=true)
	private Double merchandiseValue;
	
	@XmlElement(name="serviceValue", required=true)
	private Double serviceValue;
	
	@XmlElement(name="merchandiseAndServiceValue", required=true)
	private Double merchandiseAndServiceValue;
	
	@XmlElement(name="discount", required=true)
	private Double discount;
	
	@XmlElement(name="taxes", required=true)
	private Double taxes;
	
	@XmlElement(name="amountForPayment", required=true)
	private Double amountForPayment;
	
	@XmlElement(name="invoiceItems", required=true)
	private Set<InvoiceItem> invoiceItems;
	
	@XmlElement(name="accountNumber", required=true)
	private Account accountNumber;
	
	@XmlElement(name="currency", required=true)
	private String currency;
	
	@XmlElement(name="currencyDate", required=true)
	private Date currencyDate;
	
}
