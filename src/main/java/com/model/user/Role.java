package com.model.user;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Role {
	
	@Id
	private Long id;
	
	@Column
	@NotNull
	private String name;
	
	
	@ManyToMany(mappedBy="roles")
	private Collection<User> users;
	
	@ManyToMany
	@JoinTable(
			name="roles_privileges",
			joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"), 
			inverseJoinColumns = @JoinColumn(name="privilege_id", referencedColumnName="id")
			)
	private Collection<Privilege> privileges;
	
	
}
