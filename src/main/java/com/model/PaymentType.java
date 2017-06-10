package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@Column(unique = true)
	@NotEmpty
	@Size(max = 120)
	@ApiModelProperty(value = "Payment type's name", required = true)
	private String paymentTypeName;
	
	@OneToMany(mappedBy = "paymentType", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AnalyticalStatement> analyticalStatements = new HashSet<>();
	
	@JsonIgnore
	public Set<AnalyticalStatement> getAnalyticalStatements(){
		return analyticalStatements;
	}
	
	@JsonProperty
	public void setAnalyticalStatements(Set<AnalyticalStatement> analyticalStatements){
		this.analyticalStatements = analyticalStatements;
	}
	
}
