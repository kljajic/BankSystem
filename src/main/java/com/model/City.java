package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(exclude = "id")
@ApiModel(value = "City", description = "City in country.")
public class City implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CITY_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "CITY_ID_GEN")
	@ApiModelProperty(value = "City's id.")
	private Long id;
	
	@Column
	@NotEmpty
	@Size(max = 60)
	@ApiModelProperty(value = "City's name.", required = true)
	private String name;
	
	@Column
	@NotEmpty
	@Size(max = 12)
	@ApiModelProperty(value = "City's PTT number", required = true)
	private String pttNumber;
	
	@ManyToOne
	private Country country;
	
}
