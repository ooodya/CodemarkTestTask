package com.zaytsev.codemarkTestTask.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class User implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7315727838298231011L;
	
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String login;
	
	private String password;
	
	public User(String name, String login, String password)
	{
		this.name = name;
		this.login = login;
		this.password = password;
	}

}
