package com.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="Invoice item", description="Invoices item")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "invoiceItem", 
		namespace="http://com/model/invoiceItem", 
		propOrder = {"ordinate",
					 "merchandiseOrServiceName",
					 "amount",
					 "unitOfMeasure",
					 "unitPrice",
					 "value",
					 "discountPercentage",
					 "discountValue",
					 "discountTaken",
					 "totalTaxes"
					})
public class InvoiceItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7807483718505008986L;
	
	@XmlElement(name="ordinate", required=true)
	private Integer ordinate;
	
	@XmlElement(name="merchandiseOrServiceName", required=true)
	private String merchandiseOrServiceName;
	
	@XmlElement(name="amount", required=true)
	private Double amount;
	
	@XmlElement(name="unitOfMeasure", required=true)
	private String unitOfMeasure;
	
	@XmlElement(name="unitPrice", required=true)
	private Double unitPrice;
	
	@XmlElement(name="value", required=true)
	private Double value;
	
	@XmlElement(name="discountPercentage", required=true)
	private Double discountPercentage;
	
	@XmlElement(name="discountValue", required=true)
	private Double discountValue;
	
	@XmlElement(name="discountTaken", required=true)
	private Double discountTaken;
	
	@XmlElement(name="totalTaxes", required=true)
	private Double totalTaxes;
	
}
