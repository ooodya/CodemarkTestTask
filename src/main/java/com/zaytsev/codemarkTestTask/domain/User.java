package com.zaytsev.codemarkTestTask.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7315727838298231011L;

	@Column(name = "name", nullable = false)
	private String name;

	@Setter(AccessLevel.NONE)
	@Id
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	@Getter(AccessLevel.NONE)
	private Set<Role> roles = new HashSet<>();

	public User(String name, String login, String password)
	{
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public Set<Role> getRoles()
	{
		return Collections.unmodifiableSet(roles);
	}

}
