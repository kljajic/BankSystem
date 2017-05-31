package com.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

	private static final long serialVersionUID = -4183179901882702679L;

	@Id
	@SequenceGenerator(name = "USER_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "USER_ID_GEN")
	private Long id;
	
	@Column
	@NotEmpty
	private String name;
	
	@Column
	@NotEmpty
	private String surname;
	
	@Column
	@NotEmpty
	private String email;
	
	@Column
	@NotEmpty
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
}
