package com.model.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Role implements Serializable {
	
	private static final long serialVersionUID = 953041359463644906L;

	@Id
	@SequenceGenerator(name = "ROLE_ID_GEN", allocationSize = 10)
	@GeneratedValue(generator = "ROLE_ID_GEN")
	private Long id;
	
	@Column
	@NotNull
	private String name;
		
	@ManyToMany(mappedBy="roles",
				cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Collection<User> users;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="roles_privileges",
			joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"), 
			inverseJoinColumns = @JoinColumn(name="privilege_id", referencedColumnName="id")
			)
	private Collection<Privilege> privileges;
	
	public void addPrivilege(Privilege privilege){
		privileges.add(privilege);
		privilege.getRoles().add(this);
	}
	
	public void removePrivilege(Privilege privilege){
		privileges.remove(privilege);
		privilege.getRoles().remove(this);
	}

	@JsonIgnore
	public Collection<User> getUsers() {
		return users;
	}

	@JsonProperty
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	@JsonProperty
	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
