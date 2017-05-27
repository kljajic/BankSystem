package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@ApiModel(value = "Payment type", description= "Payment type")
public class PaymentType implements Serializable{

	private static final long serialVersionUID = 6364580000712790664L;

	@Id
	@SequenceGenerator(name = "PAYMANT_TYPE_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "PAYMANT_TYPE_ID_GEN")
	@ApiModelProperty(value = "Payment type's id.")
	private Long id;
	
	@Column
	@NotEmpty
	@Size(max = 120)
	@ApiModelProperty(value = "Payment type's name", required = true)
	private String paymentTypeName;
	
}
