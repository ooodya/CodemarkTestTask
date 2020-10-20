package com.zaytsev.codemarkTestTask.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class UserList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 388584762871643313L;
	
	@Setter
	@Getter
	private List<User> users;
	
	public UserList()
	{
		users = new ArrayList<>();
	}
	
	public UserList(List<User> users)
	{
		this.users = users;
	}
}
