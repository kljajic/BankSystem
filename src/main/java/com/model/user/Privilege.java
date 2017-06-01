package com.model.user;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Privilege {
	
	@Id
	private Long id;
	
	@Column
	@NotNull
	private String name;
	
	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;
	
}
