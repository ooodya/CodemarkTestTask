package com.zaytsev.codemarkTestTask.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 626999318416777836L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public Role(String name)
	{
		this.name = name;
	}
	
}
