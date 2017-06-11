package com.model.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name"})
@NoArgsConstructor
public class Privilege implements Serializable{
	
	private static final long serialVersionUID = -4271835374814065634L;

	@Id
	@SequenceGenerator(name = "PRIVILEGE_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "PRIVILEGE_ID_GEN")
	private Long id;
	
	@Column
	@NotNull
	private String name;
	
	@ManyToMany(mappedBy = "privileges",
				cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Collection<Role> roles;

	@JsonIgnore
	public Collection<Role> getRoles() {
		return roles;
	}

	@JsonProperty
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
}
